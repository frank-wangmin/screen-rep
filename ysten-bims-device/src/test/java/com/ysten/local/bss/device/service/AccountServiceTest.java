package com.ysten.local.bss.device.service;

import java.text.ParseException;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.ysten.local.bss.device.core.SpringConfig;
import com.ysten.local.bss.device.domain.Account;
import com.ysten.local.bss.device.domain.Account.State;
import com.ysten.utils.code.MD5;

@ContextConfiguration(locations = {SpringConfig.SPRING_CONFIG_REPOSITORY})
public class AccountServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
    private static int index = 0;
    @Autowired
    private IAccountService accountService;

    private Account getAccount() throws ParseException {
        Account account = new Account();
        account.setCode("accountId"+index++);
        account.setBalance(new Long(1000));
        account.setCustomerId("code"+index++);
        account.setCreateDate(new Date());
        account.setState(State.NORMAL);
        account.setPayPassword("2011061600000"+index++);
        account.setPayPassword(MD5.encrypt("albert"));
        return account;
    }

    @Test
    public void getAccountByCustomerCode() throws ParseException {
        Account account = this.getAccount();
        account.setCustomerId("1");
        this.accountService.createAccount(account);
        Assert.assertNotNull(this.accountService.getAccountByCustomerId("1"));
        Assert.assertNull(this.accountService.getAccountByCustomerId("dddd"));
    }
}
