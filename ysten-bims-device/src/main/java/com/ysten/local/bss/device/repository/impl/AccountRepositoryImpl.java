package com.ysten.local.bss.device.repository.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ysten.cache.annotation.KeyParam;
import com.ysten.cache.annotation.MethodCache;
import com.ysten.cache.annotation.MethodFlush;
import com.ysten.local.bss.device.domain.Account;
import com.ysten.local.bss.device.domain.AccountDetail;
import com.ysten.local.bss.device.domain.AccountDetail.ConsumType;
import com.ysten.local.bss.device.domain.AccountDetail.OperateType;
import com.ysten.local.bss.device.domain.AccountDetail.Result;
import com.ysten.local.bss.device.domain.RechargeType;
import com.ysten.local.bss.device.exception.NotSufficientFundsException;
import com.ysten.local.bss.device.redis.IAccountRedis;
import com.ysten.local.bss.device.repository.IAccountRepository;
import com.ysten.local.bss.device.repository.mapper.AccountDetailMapper;
import com.ysten.local.bss.device.repository.mapper.AccountMapper;
import com.ysten.message.MessageProducer;

/**
 * AccountRespository实现
 * 
 * @author LI.T
 * @date 2011-5-9
 * @fileName AccountRespository.java
 */
@Repository
public class AccountRepositoryImpl implements IAccountRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountRepositoryImpl.class);

	private static final String BASE_DOMAIN = "ysten:local:bss:device:";
	private static final String ACCOUNT_ID = BASE_DOMAIN + "account:id:";
	private static final String ACCOUNT_CODE = BASE_DOMAIN + "account:code:";
	@Autowired
	private AccountMapper accountMapper;
	@Autowired
	private AccountDetailMapper accountDetailMapper;
	@Autowired(required = false)
	@Qualifier("accountDetailProducer")
	private MessageProducer accountDetailProducer;
	@Autowired
	private IAccountRedis accountRedis;

	@Override
	@MethodFlush(keys = { ACCOUNT_ID + "#{account.id}", ACCOUNT_CODE + "#{account.code}" })
	public boolean updateAccount(@KeyParam("account") Account account) {
		this.accountRedis.update(account);
		return (1 == this.accountMapper.update(account));
	}

	@Override
	public boolean saveAccountDetail(AccountDetail accountDetail) {
		return 1 == this.accountDetailMapper.save(accountDetail);
	}

	@Override
	public boolean saveAccount(Account account) {
		int i = this.accountMapper.save(account);
		this.accountRedis.save(account);
		return (1 == i);
	}

	@Override
	@MethodCache(key = ACCOUNT_CODE + "#{code}")
	public Account getAccountByAccountCode(@KeyParam("code") String code) {
		Account account = this.accountRedis.readByIdOrCode(code);
		if (account != null) {
			return account;
		} else {
			account = accountMapper.getByAccountCode(code);
			accountRedis.save(account);
			return account;
		}
	}

	@Override
	public Account getAccountByCustomerId(@KeyParam("customerId") String customerId) {
		return this.accountMapper.getAccountByCustomerId(customerId);
	}

	@Override
	@MethodCache(key = ACCOUNT_ID + "#{id}")
	public Account getById(@KeyParam("id") Long id) {
		Account account = accountRedis.readByIdOrCode(id + "");
		if (null != account) {
			return account;
		} else {
			account = accountMapper.getById(id);
			accountRedis.save(account);
			return account;
		}
	}

	@Override
	@MethodFlush(keys = { ACCOUNT_ID + "#{account.id}", ACCOUNT_CODE + "#{account.accountId}" })
	public boolean accountConsume(@KeyParam("account") Account account, Long charge, String outCode,
			ConsumType consumType, Result result) throws NotSufficientFundsException {
		AccountDetail accountDetail = createConsumAccountDetail(account, charge, outCode, consumType, result);
		if (this.saveAccountDetail(accountDetail) && result == Result.SUCCESS) {
			account.consum(charge);
			if (this.updateAccount(account)) {
				publishAccountDetail(accountDetail);
				return true;
			}
		}
		return false;
	}

	@Override
	@MethodFlush(keys = { ACCOUNT_ID + "#{account.id}", ACCOUNT_CODE + "#{account.code}" })
	public boolean accountRecharge(@KeyParam("account") Account account, Long cardMoney, String outCode,
			RechargeType rechargeType, Result result) {
		AccountDetail accountDetail = createRechargeAccountDetail(account, cardMoney, outCode, rechargeType, result);
		if (this.saveAccountDetail(accountDetail) && result == Result.SUCCESS) {
			account.recharge(cardMoney);
			if (this.updateAccount(account)) {
				publishAccountDetail(accountDetail);
				return true;
			}
		}
		return false;
	}

	@Override
	public List<AccountDetail> findNotSynAccountDetails() {
		return this.accountDetailMapper.findNotSynAccountDetails();
	}

	private void publishAccountDetail(AccountDetail accountDetail) {
		if (this.accountDetailProducer != null) {
			try {
				this.accountDetailProducer.publish(accountDetail);
			} catch (Exception e) {
				LOGGER.error("publish account detial exception.{}", e);
			}
		}
	}

	/**
	 * 创建账号充值记录
	 * 
	 * @return
	 */
	private AccountDetail createRechargeAccountDetail(Account account, Long cardMoney, String outCode,
			RechargeType rechargeType, Result result) {
		AccountDetail accountDetail = new AccountDetail();
		accountDetail.setCurrentBalance(account.getBalance());
		accountDetail.setCost(cardMoney);
		accountDetail.setOperateDate(new Date());
		accountDetail.setOperateType(OperateType.RECHARGE);
		accountDetail.setAccount(account);
		accountDetail.setBusinessNo("");
		accountDetail.setOperateNo("");
		accountDetail.setRechargeType(rechargeType);
		accountDetail.setResult(result);
		accountDetail.setOuterCode(outCode);
		return accountDetail;
	}

	/**
	 * 创建账号消费详情
	 * 
	 * @param account
	 *            账号信息
	 * @param customer
	 *            用户信息
	 * @param charge
	 *            消费金额
	 * @param outCode
	 *            外部编码
	 * @param consumType
	 *            消费类型
	 * @param result
	 *            消费结果
	 * @return
	 */
	private AccountDetail createConsumAccountDetail(Account account, Long charge, String outCode,
			ConsumType consumType, Result result) {
		AccountDetail accountDetail = new AccountDetail();
		accountDetail.setAccount(account);
		accountDetail.setCurrentBalance(account.getBalance());
		accountDetail.setCost(charge);
		accountDetail.setBusinessNo("");
		accountDetail.setConsumType(consumType);
		accountDetail.setOperateNo("");
		accountDetail.setOperateDate(new Date());
		accountDetail.setOperateType(OperateType.CONSUM);
		accountDetail.setOuterCode(outCode);
		accountDetail.setResult(result);
		return accountDetail;
	}

	@Override
	public AccountDetail getAccountDetailById(Long detailId) {
		return this.accountDetailMapper.getById(detailId);
	}

	@Override
	public boolean updateAccountDetail(AccountDetail accountDetail) {
		return 1 == this.accountDetailMapper.update(accountDetail);
	}
}
