package com.ysten.local.bss.device.repository.mapper;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ysten.local.bss.device.basic.SpringTestContext;
import com.ysten.local.bss.device.domain.Account;
import com.ysten.local.bss.device.domain.AccountDetail;
import com.ysten.local.bss.device.domain.AccountDetail.ConsumType;
import com.ysten.local.bss.device.domain.AccountDetail.OperateType;
import com.ysten.local.bss.device.domain.RechargeType;



public class AccountDetailMapperTest extends SpringTestContext {
	
	@Autowired
	private AccountDetailMapper detailMapper;
	
	private AccountDetail save(){
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
		d.setSynFlag(0L);
		d.setCustomerId("1");
		this.detailMapper.save(d);
		return d;
	}
	
	@Test
	public void testGetById() {
		Assert.assertNotNull(this.detailMapper.getById(save().getId()));
	}

	@Test
	public void testSave() {
		Assert.assertNotNull(save().getId());
	}

	@Test
	public void testUpdate() {
		AccountDetail d = save();
		Account account = new Account();
		account.setId(2L);
		d.setAccount(account);
        d.setBusinessNo("businessN22o");
        d.setCost(12022l);
        d.setCurrentBalance(2212200l);
        d.setOperateDate(new Date());
        d.setOperateNo("operateNo22");
        d.setConsumType(ConsumType.ORDER);
        d.setOperateType(OperateType.RECHARGE);
        d.setOuterCode("outerCodsse");
        d.setRechargeType(RechargeType.YSTEN);
        d.setResult(AccountDetail.Result.FAIL);
		this.detailMapper.update(d);
		AccountDetail d1 = this.detailMapper.getById(d.getId());
		Assert.assertEquals(d.getAccount(), d1.getAccount());
		Assert.assertEquals(d.getBusinessNo(), d1.getBusinessNo());
		Assert.assertEquals(d.getCost(), d1.getCost());
		Assert.assertEquals(d.getCurrentBalance(), d1.getCurrentBalance());
		Assert.assertEquals(d.getOperateNo(), d1.getOperateNo());
		Assert.assertEquals(d.getOperateType(), d1.getOperateType());
		Assert.assertEquals(d.getOperateNo(), d1.getOperateNo());
		Assert.assertEquals(d.getConsumType(), d1.getConsumType());
		Assert.assertEquals(d.getRechargeType(), d1.getRechargeType());
		Assert.assertEquals(d.getResult(), d1.getResult());
		Assert.assertEquals(d.getOuterCode(), d1.getOuterCode());
	}
	
	@Test
	public void testFindNotSynAccountDetails(){
	    for(int i=0;i<20;i++){
            save();
        }
	    Assert.assertNotNull(this.detailMapper.findNotSynAccountDetails());
	    Assert.assertTrue(this.detailMapper.findNotSynAccountDetails().size()>0);
	}
}
