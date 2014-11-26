package com.ysten.local.bss.device.repository;

import java.util.List;

import com.ysten.local.bss.device.domain.DeviceCustomerAccountMap;

public interface IDeviceCustomerAccountMapRepository {
	
	List<DeviceCustomerAccountMap> findDeviceCustomerAccountMapByCustomerCode(String customerCode);
	
	boolean delete(DeviceCustomerAccountMap map);
	
	boolean deleteDeviceCustomerAccountMap(DeviceCustomerAccountMap map);
    /**
     * 更新设备与用户的对应关系
     */
    boolean updateDeviceCustomerAccountMap(DeviceCustomerAccountMap deviceCustomerAccountMap);
    /**
     * 根据deviceId获得DeviceCustomerAccountMap对象
     */
    List<DeviceCustomerAccountMap> getDeviceCustomerAccountMapByDeviceId(Long deviceId);
    /**
     * 保存设备 用户 账户的映射关系
     * 
     * @param map
     * @return
     */
    boolean saveDeviceCustomerAccountMap(DeviceCustomerAccountMap map);

    /**
     * 根据指定的日期，查询用户设备关系
     * @return
     */
    List<DeviceCustomerAccountMap> getAllDeviceCustomerAccountMapCreatedLastDay();
    /**
     * 
     * @param cityId
     * @param deviceTypeCode
     * @return
     */
    Long getTotalCountByCityAndDeviceType(String cityId, String deviceTypeCode);
    
    DeviceCustomerAccountMap getDeviceCustomerAccountMapById(Long id);
    
    /**
     * 通过设备编号查找用户
     * 
     * @param deviceCode
     * @return
     */
    DeviceCustomerAccountMap getDeviceCustomerAccountMapByDeviceCode(String deviceCode);

    DeviceCustomerAccountMap getByYstenId(String ystenId);
    
    /**
     * 根据用户ID查找用户设备关系
     * 
     * @param deviceId
     * @return
     */
    List<DeviceCustomerAccountMap> getDeviceCustomerAccountMapByCustomerId(Long customerId);
    
    DeviceCustomerAccountMap getFirstReplacmentMapUnderCustomerCust(String custId);
    
    DeviceCustomerAccountMap getDeviceCustomerAccountMapByDeviceCodeAndUserId(String deviceCode, String userId);

}
