package com.ysten.local.bss.device.redis;

import com.ysten.local.bss.device.domain.Device;

/**
 * @author cwang
 * @version 2014-3-20 上午11:21:53
 * 
 */
public interface IDeviceRedis {
	boolean save(final Device bean);

	Device getDeviceByCodeIdSno(final String codeIdSno);

	boolean delete(final Device bean);

	boolean update(final Device newBean);
}
