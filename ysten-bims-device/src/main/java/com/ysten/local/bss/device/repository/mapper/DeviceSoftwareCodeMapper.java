package com.ysten.local.bss.device.repository.mapper;

import com.ysten.local.bss.device.bean.DeviceUpgradeCondition;
import com.ysten.local.bss.device.domain.DeviceSoftwareCode;

import java.util.List;

import org.apache.ibatis.annotations.Param;



public interface DeviceSoftwareCodeMapper {
	
	DeviceSoftwareCode getById(long id);

	/**
	 * 插入记录
	 * @param deviceSoftwareCode
	 * @return
	 */
	int insert(DeviceSoftwareCode deviceSoftwareCode);

    /**
     * 数据同步
     * @param deviceSoftwareCode
     * @return
     */
    Integer insertSynchronization(DeviceSoftwareCode deviceSoftwareCode);

	/**
	 * 根据主键查询记录
	 * @param softCodeId
	 * @return
	 */
	DeviceSoftwareCode selectByPrimaryKey(Long softCodeId);

	/**
	 * 根据主键修改记录
	 * @param deviceSoftwareCode
	 * @return
	 */
	int updateByPrimaryKey(DeviceSoftwareCode deviceSoftwareCode);

	/**
	 * 根据主键删除记录
	 * @param softCodeId
	 * @return
	 */
	int deleteByPrimaryKey(Long softCodeId);
	
	/**
	 * 根据条件查询对象集合
	 * @param condition
	 * @return
	 */
	List<DeviceSoftwareCode> findEpgSoftwareCodesByCondition(DeviceUpgradeCondition condition);

	/**
	 * 根据条件查询对象个数
	 * @param condition
	 * @return
	 */
	int countEpgSoftwareCodesByCondition(DeviceUpgradeCondition condition);
	
	/**
	 * 根据软件编码查询
	 * @param code
	 * @return
	 */
	DeviceSoftwareCode selectByCode(@Param("code")String code);
	
	int delete(@Param("ids")List<Long> ids);
	/**
	 * 查询状态为可用的软件号【status='USABLE'】
	 * @return
	 */
	List<DeviceSoftwareCode> getAll();
	
	DeviceSoftwareCode findEpgSoftwareCodesByName(@Param("name")String name);
	
	int updateDistributeStateById(@Param("id")Long id,@Param("distributeState")String distributeState);
	
}
