package com.ysten.local.bss.device.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.device.domain.AppUpgradeTask;

public interface AppUpgradeTaskMapper {

	AppUpgradeTask getById(long id);
	
	List<AppUpgradeTask> getListByCondition(@Param("softwarePackageId")Long softwarePackageId,@Param("softCodeId")Long softCodeId,
			@Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);
	
	List<AppUpgradeTask> findAppUpgradeTaskListByCondition(@Param("softwarePackageName")String softwarePackageName,@Param("softCodeName")String softCodeName,
			@Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);
	
	int getCountByCondition(@Param("softwarePackageId")Long softwarePackageId,
			@Param("softCodeId")Long softCodeId);
	
	int getCountBySoftCodeAndPackage(@Param("softwarePackageName")String softwarePackageName,@Param("softCodeName")String softCodeName);
	
	int delete(@Param("ids")List<Long> ids);
	
	int updateById(AppUpgradeTask appUpgradeTask);
	
	int save(AppUpgradeTask appUpgradeTask);
	
	AppUpgradeTask getByVersionSeq(@Param("versionSeq")int versionSeq);
}
