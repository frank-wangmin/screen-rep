package com.ysten.local.bss.device.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysten.local.bss.device.domain.AccountDetail;
import com.ysten.local.bss.device.domain.AccountDetail.ConsumType;
import com.ysten.local.bss.device.domain.AccountDetail.Result;
import com.ysten.local.bss.device.domain.Account;
import com.ysten.local.bss.device.domain.RechargeType;
import com.ysten.local.bss.device.exception.NotSufficientFundsException;
import com.ysten.local.bss.device.repository.IAccountRepository;
import com.ysten.local.bss.device.service.IAccountService;

@Service
public class AccountServiceImpl implements IAccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private IAccountRepository accountRepository;

    @Override
    public boolean createAccount(Account account) {
        return this.accountRepository.saveAccount(account);
    }

    @Override
    public boolean updateAccount(Account account) {
        return this.accountRepository.updateAccount(account);
    }

    @Override
    public Account getAccountByCustomerId(String customerId) {
        LOGGER.debug("get account by customer code {}.", customerId);
        return this.accountRepository.getAccountByCustomerId(customerId);
    }

    @Override
    public Account getAccountByAccountCode(String accountCode) {
        LOGGER.debug("get account by account code {}.", accountCode);
        return this.accountRepository.getAccountByAccountCode(accountCode);
    }
    
    @Override
    public Account getAccountByCustomerId(Long customerId) {
        LOGGER.debug("get account by id {}.", customerId);
        return this.accountRepository.getById(customerId);
    }

    @Override
    public boolean accountConsume(Account account, Long charge, String outCode, ConsumType consumType, Result result)
            throws NotSufficientFundsException {
        return this.accountRepository.accountConsume(account, charge, outCode, consumType, result);
    }

    @Override
    public List<AccountDetail> findNotSynAccountDetails() {
        return this.accountRepository.findNotSynAccountDetails();
    }

    @Override
    public boolean setAccountDetailSyn(Long detailId) {
        AccountDetail accountDetail = this.accountRepository.getAccountDetailById(detailId);
        if (accountDetail != null) {
            accountDetail.setSynFlag(1L);
            return this.accountRepository.updateAccountDetail(accountDetail);
        }
        return false;
    }

    @Override
    public Account getAccountById(Long id) {
        return this.accountRepository.getById(id);
    }

    @Override
    public boolean accountRecharge(Account account, Long cardMoney, String outCode, RechargeType rechargeType,
            Result result) {
        return this.accountRepository.accountRecharge(account, cardMoney, outCode, rechargeType, result);
    }
}
