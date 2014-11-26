package com.ysten.local.bss.device.repository;

import java.util.List;

import com.ysten.local.bss.device.bean.DeviceUpgradeCondition;
import com.ysten.local.bss.device.domain.AppSoftwarePackage;
import com.ysten.utils.page.Pageable;

public interface IAppSoftwarePackageRepository {
	
	Pageable<AppSoftwarePackage> findAppSoftwarePackageListByCondition(String versionName,Integer pageNo,Integer pageSize);
	
	AppSoftwarePackage getAppSoftwarePackageById(Long id);
	
	Pageable<AppSoftwarePackage> findAppSoftwaresByCondition(DeviceUpgradeCondition condition);
	
	boolean deleteAppSoftwarePackageByIds(List<Long> ids);
	
	boolean saveAppSoftwarePackage(AppSoftwarePackage appSoftwarePackage);
	
	boolean updateAppSoftwarePackage(AppSoftwarePackage appSoftwarePackage);
	
	List<AppSoftwarePackage> findAllAppSoftwarePackage();

    AppSoftwarePackage getAppSoftwarePackageByName(String versionName);
    
    List<AppSoftwarePackage> findSoftwarePackageBySoftCode(Long softwareCodeId);
    
    boolean updateDistributeStateById(Long id,String distributeState);
} 
