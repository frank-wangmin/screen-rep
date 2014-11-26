package com.ysten.local.bss.device.repository;

import java.text.ParseException;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.ysten.local.bss.device.core.SpringConfig;
import com.ysten.local.bss.device.domain.Account;
import com.ysten.local.bss.device.domain.Account.State;
import com.ysten.local.bss.device.domain.AccountDetail;
import com.ysten.local.bss.device.domain.AccountDetail.ConsumType;
import com.ysten.local.bss.device.domain.AccountDetail.OperateType;
import com.ysten.local.bss.device.domain.RechargeType;
import com.ysten.utils.code.EncryptException;



/**
 * AccountRepository 单元测试
 * @author sunguangqi
 * @date 2011-06-16
 * @fileName AccountRepositoryTest.java
 */
@ContextConfiguration(locations={SpringConfig.SPRING_CONFIG_REPOSITORY})
public class AccountRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private static int index = 0;
    @Autowired
    private IAccountRepository bssAccountRepository;
    
    private Account saveAccount() {
        Account account = new Account();
        account.setCode("accountId"+index++);
        account.setBalance(new Long(1000));
        account.setCustomerId("code"+index++);
        account.setCreateDate(new Date());
        account.setState(State.NORMAL);
        account.setPayPassword("2011061600000"+index++);
        this.bssAccountRepository.saveAccount(account);
        return account;
    }
    
    @Test
    public void testSaveAccount() throws ParseException{
        Assert.assertNotNull(saveAccount().getId());
    }
    
    @Test
    public void testGetAccountById() throws EncryptException, ParseException{
        Assert.assertNotNull(this.bssAccountRepository.getById(saveAccount().getId()));
    }
    
    @Test
    public void testUpdateAccount() throws EncryptException, ParseException{
        Account a = saveAccount();
        a.setBalance(20l);
        a.setCode("code1");
        a.setCustomerId("login1Name");
        a.setPayPassword("passw1ord");
        a.setState(Account.State.NORMAL);
        this.bssAccountRepository.updateAccount(a);
        Account a1 = this.bssAccountRepository.getById(a.getId());
        Assert.assertEquals(a.getCode(), a1.getCode());
        Assert.assertEquals(a.getCustomerId(), a1.getCustomerId());
        Assert.assertEquals(a.getBalance(), a1.getBalance());
        Assert.assertEquals(a.getPayPassword(), a1.getPayPassword());
        Assert.assertEquals(a.getState(), a1.getState());
    }
    
    @Test
    public void testGetAccountByCode(){
        Account a = saveAccount();
        Assert.assertNotNull(this.bssAccountRepository.getAccountByAccountCode(a.getCode()));
    }
    
    @Test
    public void testGetById(){
        Account a = saveAccount();
        Assert.assertNotNull(this.bssAccountRepository.getById(a.getId()));
    }
    
    public AccountDetail saveAccountDetail(){
        AccountDetail d = new AccountDetail();
        Account account = new Account();
        account.setId(1L);
        d.setAccount(account);
        d.setBusinessNo("businessNo");
        d.setCost(120l);
        d.setCurrentBalance(12200l);
        d.setOperateDate(new Date());
        d.setOperateNo("operateNo");
        d.setConsumType(ConsumType.BASIC);
        d.setOperateType(OperateType.CONSUM);
        d.setOuterCode("outerCode");
        d.setRechargeType(RechargeType.YEEPAY);
        d.setResult(AccountDetail.Result.SUCCESS);
        d.setCustomerCode("xj");
        d.setCustomerId("1");
        this.bssAccountRepository.saveAccountDetail(d);
        return d;
    }
}
