package com.ysten.local.bss.device.redis;

import java.util.List;

import com.ysten.local.bss.device.domain.DeviceCustomerAccountMap;

/**
 * @author cwang
 * @version 2014-3-21 上午10:58:09
 * 
 */
public interface IDeviceCustomerAccountMapRedis {
	boolean save(final DeviceCustomerAccountMap bean);

	List<DeviceCustomerAccountMap> readByType(final String type);

	boolean delete(final DeviceCustomerAccountMap bean);

	boolean update(final DeviceCustomerAccountMap newBean);

	public boolean saveAsSet(final DeviceCustomerAccountMap bean);

	public List<DeviceCustomerAccountMap> readAll();

	public boolean deleteFromSet(final DeviceCustomerAccountMap bean);
}
