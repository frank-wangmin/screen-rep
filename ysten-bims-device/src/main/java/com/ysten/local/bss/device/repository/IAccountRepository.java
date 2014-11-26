package com.ysten.local.bss.device.repository;

import java.util.List;

import com.ysten.local.bss.device.domain.AccountDetail;
import com.ysten.local.bss.device.domain.AccountDetail.ConsumType;
import com.ysten.local.bss.device.domain.AccountDetail.Result;
import com.ysten.local.bss.device.domain.Account;
import com.ysten.local.bss.device.domain.RechargeType;
import com.ysten.local.bss.device.exception.NotSufficientFundsException;

/**
 * IAccountRepository interface
 * 
 * @fileName IAccountRespository.java
 */
public interface IAccountRepository {
    /**
     * 新增账户信息
     * 
     * @param account
     * @return
     */
    boolean saveAccount(Account account);

    /**
     * 通过编号查询账户信息
     * 
     * @param code
     * @return
     */
    Account getAccountByAccountCode(String code);

    /**
     * 通过用户id查询账户信息
     * 
     * @param customerId
     * @return
     */
    Account getAccountByCustomerId(String customerId);

    /**
     * 通过id查询账户信息
     * 
     * @param id
     * @return
     */
    Account getById(Long id);

    /**
     * 更新账户信息
     * 
     * @param account
     * @return
     */
    boolean updateAccount(Account account);

    /**
     * save accountDetail
     * 
     * @param accountDetail
     * @return
     */
    boolean saveAccountDetail(AccountDetail accountDetail);

    /**
     * 记录账号的消费信息 1.创建并写入消费详情 2.从账号中扣除消费金额
     * 
     * @param account
     *            消费账号
     * @param customer
     *            消费用户
     * @param charge
     *            消费金额
     * @param outCode
     *            生的订单ID
     * @param consumType
     *            消费类型
     * @param result
     *            消费结果
     * @return
     * @throws NotSufficientFundsException
     *             余额不足
     */
    boolean accountConsume(Account account, Long charge, String outCode, ConsumType consumType, Result result)
            throws NotSufficientFundsException;

    /**
     * 账号充值
     * 
     * @param account
     *            充值信息
     * @param customer
     *            用户信息
     * @param cardMoney
     *            充值金额
     * @param outCode
     *            外部编码
     * @param rechargeType
     *            充值类型
     * @param result
     *            充值结果
     * @return
     */
    boolean accountRecharge(Account account, Long cardMoney, String outCode, RechargeType rechargeType, Result result);

    /**
     * 获取未同步的账户详情
     * 
     * @return
     */
    List<AccountDetail> findNotSynAccountDetails();

    /**
     * 通过id查询账户详情
     * 
     * @param detailId
     * @return
     */
    AccountDetail getAccountDetailById(Long detailId);

    /**
     * 修改账户详情
     * 
     * @param accountDetail
     * @return
     */
    boolean updateAccountDetail(AccountDetail accountDetail);
}
