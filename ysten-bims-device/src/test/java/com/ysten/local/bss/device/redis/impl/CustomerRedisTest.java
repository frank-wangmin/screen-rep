package com.ysten.local.bss.device.redis.impl;


import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.ysten.local.bss.device.core.SpringConfig;
import com.ysten.local.bss.device.domain.Customer;
import com.ysten.local.bss.device.redis.ICustomerRedis;

/**
 * @author cwang
 * @version 2014-3-20 下午12:04:25
 *
 */
@ContextConfiguration(locations = { SpringConfig.SPRING_CONFIG_REPOSITORY })
public class CustomerRedisTest extends AbstractTransactionalJUnit4SpringContextTests {
    Customer vo = new Customer();
    @Autowired
    ICustomerRedis customerRedis;

    @Before
    public void before() throws Exception {
        vo.setId((long)803);
        vo.setCode("11");
    }

    public void save() {
        customerRedis.save(vo);
    }

    public void read() {
        Customer customer=customerRedis.getCustomerByCodeOrId(vo.getId()+"");
        System.out.println(customer);
    }


    public void del() {
        customerRedis.delete(vo);
//		System.out.println(list);
    }
    public void update() {
        vo.setCode("45678");
        customerRedis.update(vo);
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
