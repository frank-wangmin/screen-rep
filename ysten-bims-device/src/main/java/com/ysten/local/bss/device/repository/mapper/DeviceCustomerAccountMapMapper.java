package com.ysten.local.bss.device.repository.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.device.domain.CustomerRelationDeviceVo;
import com.ysten.local.bss.device.domain.DeviceCustomerAccountMap;

/**
 * DeviceCustomerAccountMapMapper接口
 * 
 * @fileName DeviceCustomerAccountMapMapper.java
 */
public interface DeviceCustomerAccountMapMapper {

    /**
     * 修改
     * 
     * @param deviceCustomerAccountMap
     * @return
     */
    int update(DeviceCustomerAccountMap deviceCustomerAccountMap);

    /**
     * 保存
     * 
     * @param deviceCustomerAccountMap
     * @return
     */
    int save(DeviceCustomerAccountMap deviceCustomerAccountMap);

    /**
     * 根据deviceId查询对应的DeviceCustomerAccountMap对象
     */
    DeviceCustomerAccountMap getById(Long id);

    /**
     * 根据设备编号查找用户
     * 
     * @param deviceCode
     * @return
     */
    DeviceCustomerAccountMap getByDeviceCode(@Param("deviceCode") String deviceCode);

    DeviceCustomerAccountMap getByYstenId(@Param("ystenId") String ystenId);
    
    /**
     * 根据设备编号查找用户设备关系
     * 
     * @param deviceId
     * @return
     */
    List<DeviceCustomerAccountMap> getByDeviceId(@Param("deviceId") Long deviceId);
    
    /**
     * 查询指定日期的用户设备关系
     * @param date
     * @return
     */
    List<DeviceCustomerAccountMap> getCreatedLastDay(Map<String, String> map);

    /**
     * 根据用户code查找关联关系
     * 
     * @param customerCode
     * @return
     */
    List<DeviceCustomerAccountMap> getByCustomerCode(@Param("customerCode") String customerCode);

    /**
     * 根据用户Id查找关联关系
     * 
     * @param customerId
     * @return
     */
    List<DeviceCustomerAccountMap> getByCustomerId(@Param("customerId") Long customerId);
    
    DeviceCustomerAccountMap getFirstReplacmentMapUnderCustomerCust(String custId);

    /**
     * 删除设备 用户 账户的映射关系
     * 
     * @param deviceCustomerAccountMap
     * @return
     */
    int deleteDeviceCustomerAccountMap(@Param("deviceId") Long deviceId);
    
    /**
     * 删除设备 用户 账户的映射关系
     * 
     * @param deviceCustomerAccountMap
     * @return
     */
    int delete(@Param("id") Long id);
    
    /**
     * 以地市/终端类型对激活进行统计
     * @param cityId
     * @param deviceTypeCode
     * @return
     */
    public Long getTotalCountByCityAndDeviceType(Map<String, Object> params);

    public Long getCountByUidDid(@Param("end")Date  end,@Param("customerIds") List<Long> customerIds,@Param("deviceIds") List<Long> deviceIds);
    
    public Long getCountByCreateDateAndUidDid(@Param("start")Date  start,@Param("end")Date  end,@Param("customerIds") List<Long> customerIds,@Param("deviceIds") List<Long> deviceIds);

    public Long getCountByCityAndDeviceType(@Param("start")Date  start,@Param("end")Date  end,@Param("province") int province,@Param("source") String source,@Param("state") String state,@Param("deviceType") int deviceType,@Param("cityId") long cityId);
    
    Long getCountByAera(@Param("start")Date  start,@Param("end")Date  end,@Param("province") int province,@Param("source") String source);
    
    Long getCountByRegion(@Param("start")Date  start,@Param("end")Date  end,@Param("city") long city,@Param("province") int province,@Param("source") String source);
    
    List<CustomerRelationDeviceVo> findCustomerRelationByCondition(Map<String, Object> params);
    
    List<CustomerRelationDeviceVo> findCustomerRelationByDeviceSno(Map<String, Object> params);
    
    int getCountCustomerRelationByCondition(Map<String, Object> params);
    
    int getCountCustomerRelationByDeviceSno(@Param("snos")String  snos);
    
    DeviceCustomerAccountMap getDeviceCustomerAccountMapByDeviceCodeAndUserId(@Param("deviceCode")String deviceCode, @Param("userId")String userId);
}