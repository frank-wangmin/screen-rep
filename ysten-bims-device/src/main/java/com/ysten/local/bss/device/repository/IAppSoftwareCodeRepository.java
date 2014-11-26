package com.ysten.local.bss.device.repository;


import java.util.List;

import com.ysten.local.bss.device.bean.DeviceUpgradeCondition;
import com.ysten.local.bss.device.domain.AppSoftwareCode;
import com.ysten.utils.page.Pageable;

public interface IAppSoftwareCodeRepository {
	
	/**
	 * 根据条件查询对象集合
	 * @param condition
	 * @return
	 */
	Pageable<AppSoftwareCode> findAppSoftwareCodesByCondition(DeviceUpgradeCondition condition);
	
	/**
	 * 根据主键查询记录
	 * @param softCodeId
	 * @return
	 */
	AppSoftwareCode getAppSoftwareCodeBySoftCodeId(Long softCodeId);
	
	boolean deleteAppSoftwareCodeByIds(List<Long> ids);
	
	boolean saveAppSoftwareCode(AppSoftwareCode appSoftwareCode);
	
	boolean updateAppSoftwareCode(AppSoftwareCode appSoftwareCode);	
	
	List<AppSoftwareCode> findAllAppSoftwareCode();
	
	AppSoftwareCode getAppSoftwareCodeByCode(String code);

    AppSoftwareCode findSoftwareCodesByName(String name);
	
    AppSoftwareCode getBySoftwareCode(String code);
    
    boolean updateDistributeStateById(Long id,String distributeState);
}
