package com.ysten.local.bss.device.redis.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.ysten.local.bss.device.core.SpringConfig;
import com.ysten.local.bss.device.domain.Device;
import com.ysten.local.bss.device.redis.IDeviceRedis;

/**
 * @author cwang
 * @version 2014-3-20 下午12:04:25
 *
 */
@ContextConfiguration(locations = { SpringConfig.SPRING_CONFIG_REPOSITORY })
public class DeviceRedisTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private IDeviceRedis deviceRedis;

    @Before
    public void before() throws Exception {
    }

    @Test
    public void crud() {
        // -------------- Create ---------------
        String uid = "123";
        Device device = new Device();
        device.setId((long) 123);
        device.setCode("new");
        device.setSno("sno");
        deviceRedis.save(device);
        //
        // // ---------------Read ---------------
        Device dRedis = deviceRedis.getDeviceByCodeIdSno(uid);
        //

        assertEquals(device.getId(), dRedis.getId());

        //update
        device.setCode("test");
        deviceRedis.update(device);
        Device dRedisUpdate = deviceRedis.getDeviceByCodeIdSno(uid);
        assertEquals(device.getCode(), dRedisUpdate.getCode());

        //delete
        deviceRedis.delete(device);
        Device dR1 = deviceRedis.getDeviceByCodeIdSno(uid);
        assertNull(dR1);
    }
}
