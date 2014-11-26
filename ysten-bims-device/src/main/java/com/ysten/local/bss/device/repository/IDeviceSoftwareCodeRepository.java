package com.ysten.local.bss.device.repository;


import java.util.List;

import com.ysten.local.bss.device.bean.DeviceUpgradeCondition;
import com.ysten.local.bss.device.domain.DeviceSoftwareCode;
import com.ysten.utils.page.Pageable;

public interface IDeviceSoftwareCodeRepository {
	
	/**
	 * 插入或根据主键修改
	 * @param deviceSoftwareCode
	 * @return
	 * 		true 成功 or false 失败
	 */
	Boolean insertOrUpdate(DeviceSoftwareCode deviceSoftwareCode);


	/**
	 * 根据主键删除记录
	 * @param softCodeId
	 * @return
	 */
	Boolean deleteByPrimaryKey(Long softCodeId);
	
	/**
	 * 根据主键查询记录
	 * @param softCodeId
	 * @return
	 */
	DeviceSoftwareCode selectByPrimaryKey(Long softCodeId);
	
	/**
	 * 根据条件查询对象集合
	 * @param condition
	 * @return
	 */
	Pageable<DeviceSoftwareCode> findEpgSoftwareCodesByCondition(DeviceUpgradeCondition condition);
	
	/**
	 * 根据软件编码查询
	 * @param code
	 * @return
	 */
	DeviceSoftwareCode selectByCode(String code);
	
	DeviceSoftwareCode findSoftwareCodesByName(String name);
	
	boolean deleteByIds(List<Long> ids);
	
	boolean insert(DeviceSoftwareCode deviceSoftwareCode);
	
	boolean update(DeviceSoftwareCode deviceSoftwareCode);
	
	List<DeviceSoftwareCode> getAll();

    /**
     * 数据同步
     * @param deviceSoftwareCode
     * @return
     */
    public Boolean insertSynchronization(DeviceSoftwareCode deviceSoftwareCode);
    
    boolean updateDistributeStateById(Long id,String distributeState);

}
