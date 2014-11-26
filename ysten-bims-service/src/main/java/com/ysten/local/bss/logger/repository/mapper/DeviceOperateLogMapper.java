package com.ysten.local.bss.logger.repository.mapper;

import com.ysten.local.bss.logger.domain.DeviceOperateLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeviceOperateLogMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table bss_device_operate_log
	 * 
	 * @mbggenerated Tue Jul 08 14:16:53 CST 2014
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table bss_device_operate_log
	 * 
	 * @mbggenerated Tue Jul 08 14:16:53 CST 2014
	 */
	int insert(DeviceOperateLog record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table bss_device_operate_log
	 * 
	 * @mbggenerated Tue Jul 08 14:16:53 CST 2014
	 */
	int insertSelective(DeviceOperateLog record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table bss_device_operate_log
	 * 
	 * @mbggenerated Tue Jul 08 14:16:53 CST 2014
	 */
	DeviceOperateLog selectByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table bss_device_operate_log
	 * 
	 * @mbggenerated Tue Jul 08 14:16:53 CST 2014
	 */
	int updateByPrimaryKeySelective(DeviceOperateLog record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table bss_device_operate_log
	 * 
	 * @mbggenerated Tue Jul 08 14:16:53 CST 2014
	 */
	int updateByPrimaryKey(DeviceOperateLog record);

    List<DeviceOperateLog> findDeviceOperateLogPagination(@Param("deviceCode") String deviceCode,@Param("ip") String ip,@Param("status") String status,@Param("result") String result,@Param("pageNo")Integer pageNo,@Param("pageSize")Integer pageSize);

    int findDeviceOperateLogCount(@Param("deviceCode") String deviceCode,@Param("ip") String ip,@Param("status") String status,@Param("result") String result);

    List<DeviceOperateLog> findDeviceOperateLog(@Param("deviceCode") String deviceCode,@Param("ip") String ip,@Param("status") String status,@Param("result") String result);

}