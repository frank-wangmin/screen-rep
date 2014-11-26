package com.ysten.local.bss.device.repository.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.device.bean.DeviceUpgradeCondition;
import com.ysten.local.bss.device.domain.AppSoftwarePackage;


public interface AppSoftwarePackageMapper {
	
	AppSoftwarePackage getById(long id);
	
	List<AppSoftwarePackage> findAll();
	
	List<AppSoftwarePackage> getListByCondition(@Param("versionName") String versionName,@Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);
	
	int getCountByCondition(@Param("versionName") String versionName);
	
	AppSoftwarePackage selectByPrimaryKey(Long softwareId);
	
	int delete(@Param("ids")List<Long> ids);
	
	int save(AppSoftwarePackage appSoftwarePackage);
	
	int update(AppSoftwarePackage appSoftwarePackage);
	
	AppSoftwarePackage findAppSoftwarePackageByVersion(@Param("version")String version);
	
	List<AppSoftwarePackage> findAppSoftwaresByCondition(DeviceUpgradeCondition condition);
	
	int countAppSoftwaresByCondition(DeviceUpgradeCondition condition);

    AppSoftwarePackage getAppSoftwarePackageByName(@Param("versionName") String versionName);
    
    List<AppSoftwarePackage> getSoftwarePackageBySoftCode(@Param("softwareCodeId") Long softwareCodeId);
    
    int updateDistributeStateById(@Param("id")Long id,@Param("distributeState")String distributeState);
    
    AppSoftwarePackage findLatestSoftwarePackageByIds(Map<String, Object> paramters);
}
