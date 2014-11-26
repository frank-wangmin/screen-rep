package com.ysten.local.bss.device.service;


import java.util.List;

import com.ysten.local.bss.device.domain.Customer;
import com.ysten.local.bss.device.domain.DeviceCustomerAccountMap;


/**
 * DeviceService
 * 
 * @fileName IDeviceService.java
 */
public interface IDeviceCustomerAccountMapService {
	/**
	 * 根据用户ID查找用户设备关系
	 * @param deviceCode
	 * @return
	 */
    List<DeviceCustomerAccountMap> findDeviceCustomerAccountMapByCustomerId(Long customerId);

    List<DeviceCustomerAccountMap> findDeviceCustomerAccountMapByCustomerCode(String customerCode);
	
	/**
	 * 查询前一天的用户设备关系
	 * 
	 * @return
	 */
	List<DeviceCustomerAccountMap> findDeviceCustomerAccountMapCreatedLastDay();
	
	/**
	 * 按照地市及终端类型进行分组统计
	 * @return
	 */
	Long getTotalCountByCityAndDeviceType(String cityId, String deviceTypeCode);

	/**
	 * 根据设备编号查找用户设备关系
	 * @param deviceId
	 * @return
	 */
	List<DeviceCustomerAccountMap> findDeviceCustomerAccountMapByDeviceId(Long deviceId);
	
	/**
	 * 根据sno customerCode查询
	 * @param sno
	 * @param customerCode
	 * @return
	 */
	List<DeviceCustomerAccountMap> findDeviceCustomerAccountMapBySnoCustomerCode(String sno,String customerCode);
	
	DeviceCustomerAccountMap getDeviceCustomerAccountMapByDeviceCode(String deviceCode);
	
	DeviceCustomerAccountMap getFirstReplacmentMapUnderCustomerCust(String custId);
	
	 /**
     * 更新设备用户信息
     * @param device
     * @return
     */
    boolean updateDeviceCustomerAccountMap(DeviceCustomerAccountMap deviceCustomerMap);
    
    /**
     * 保存设备用户信息
     * @param device
     * @return
     */
    boolean saveDeviceCustomerAccountMap(DeviceCustomerAccountMap deviceCustomerMap);
    
    /**
     * 根据deviceId删除设备用户信息
     * @param deviceId
     * @return
     */
    boolean deleteDeviceCustomerAccountMap(Long deviceId) ;
    
    boolean deleteDeviceCustomerAccountMap(DeviceCustomerAccountMap map);
    
    Customer getCustomerByYstenId(String ystenId);
}
