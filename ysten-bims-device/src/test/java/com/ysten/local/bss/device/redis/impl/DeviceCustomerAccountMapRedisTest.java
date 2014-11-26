package com.ysten.local.bss.device.redis.impl;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.ysten.local.bss.device.core.SpringConfig;
import com.ysten.local.bss.device.domain.DeviceCustomerAccountMap;
import com.ysten.local.bss.device.redis.IDeviceCustomerAccountMapRedis;

/**
 * @author cwang
 * @version 2014-3-20 下午12:04:25
 * 
 */
@ContextConfiguration(locations = { SpringConfig.SPRING_CONFIG_REPOSITORY })
public class DeviceCustomerAccountMapRedisTest extends AbstractTransactionalJUnit4SpringContextTests {
	DeviceCustomerAccountMap vo = new DeviceCustomerAccountMap();
	@Autowired
	IDeviceCustomerAccountMapRedis deviceCustomerAccountMapRedis;

	@Before
	public void before() throws Exception {
		vo.setId((long)803);
		
		vo.setDeviceId((long) 123);
		vo.setDeviceCode("456");
		vo.setDeviceSno("5M065113420008838");
		
		vo.setCustomerId((long)789);
		vo.setCustomerCode("20140112104931001367");
	}

	public void save() {
		deviceCustomerAccountMapRedis.save(vo);
	}

	public void read() {
		List<DeviceCustomerAccountMap> list=deviceCustomerAccountMapRedis.readByType(vo.getId()+"");
		System.out.println(list);
	}

	public void readAll() {
		List<DeviceCustomerAccountMap> list = deviceCustomerAccountMapRedis.readAll();
		System.out.println(list);
	}

	public void addSet() {
		DeviceCustomerAccountMap dca = new DeviceCustomerAccountMap();
		dca.setId((long) 123);
		deviceCustomerAccountMapRedis.saveAsSet(dca);
		List<DeviceCustomerAccountMap> list = deviceCustomerAccountMapRedis.readAll();
		System.out.println(list);
	}

	public void del() {
		deviceCustomerAccountMapRedis.delete(vo);
//		System.out.println(list);
	}
	public void update() {
		vo.setDeviceCode("45678");
		deviceCustomerAccountMapRedis.update(vo);
	}
	@Test
	public void crud() {
		save();
		read();
		update();
		read();
		del();
		read();
	}
}
