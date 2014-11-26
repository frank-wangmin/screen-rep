package com.ysten.local.bss.device.repository.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ysten.local.bss.device.basic.SpringTestContext;
import com.ysten.local.bss.device.domain.Account;

/**
 * AccountMapper 单元测试
 * @fileName AccountMapperTest.java
 */
public class AccountMapperTest extends SpringTestContext {
    private static int index = 0;
    
    @Autowired
    private AccountMapper bssAccounterMapper;
    
    private Account save(){
        Account a = new Account();
        a.setBalance(120l);
        a.setCode("account_id" + index ++);
        a.setCreateDate(new Date());
        a.setCustomerId("customer_id" + index++);
        a.setPayPassword("password");
        a.setState(Account.State.NORMAL);
        this.bssAccounterMapper.save(a);
        return a;
    }
    
    @Test
    public void testGetById() {
        Assert.assertNotNull(this.bssAccounterMapper.getById(save().getId()));
    }

    @Test
    public void testDeleteById() {
        Account a = save();
        Assert.assertNotNull(this.bssAccounterMapper.getById(a.getId()));
        this.bssAccounterMapper.deleteById(a.getId());
        Assert.assertNull(this.bssAccounterMapper.getById(a.getId()));
    }

    @Test
    public void testSave() {
        Assert.assertNotNull(save().getId());
    }
    
    @Test
    public void testUpdate() {
        Account a = save();
        a.setBalance(20l);
        a.setCode("code1");
        a.setCustomerId("login1Name");
        a.setPayPassword("passw1ord");
        a.setState(Account.State.NORMAL);
        this.bssAccounterMapper.update(a);
        Account a1 = this.bssAccounterMapper.getById(a.getId());
        Assert.assertEquals(a.getCode(), a1.getCode());
        Assert.assertEquals(a.getCustomerId(), a1.getCustomerId());
        Assert.assertEquals(a.getBalance(), a1.getBalance());
        Assert.assertEquals(a.getPayPassword(), a1.getPayPassword());
        Assert.assertEquals(a.getState(), a1.getState());
    }

    @Test
    public void testGetByAccountId(){
        Account a = save();
        Assert.assertNotNull(this.bssAccounterMapper.getByAccountCode(a.getCode()));
    }
    
    @Test
    public void testUpdateState(){
        List<Long> ids = new ArrayList<Long>();
        ids.add(save().getId());
        ids.add(save().getId());
        ids.add(save().getId());
        ids.add(save().getId());
        Assert.assertTrue(this.bssAccounterMapper.updateState(Account.State.CANCEL,ids) == ids.size());
        for(int i=0;i<ids.size();i++){
            Assert.assertEquals(this.bssAccounterMapper.getById(ids.get(i)).getState(), Account.State.CANCEL);
        }
    }
    
    
}
