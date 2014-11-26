package com.ysten.local.bss.device.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.device.domain.ApkUpgradeTask;

public interface ApkUpgradeTaskMapper {
    int deleteByPrimaryKey(Long id);
    int insert(ApkUpgradeTask record);
    ApkUpgradeTask selectByPrimaryKey(Long id);
    int updateByPrimaryKey(ApkUpgradeTask record);
    ApkUpgradeTask getById(long id);
    
    List<ApkUpgradeTask> findApkUpgradeTaskListBySoftCodeAndPackage(@Param("softwarePackageName")String softwarePackageName,@Param("softCodeName")String softCodeName,
			@Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);
    
    int getCountBySoftCodeAndPackage(@Param("softwarePackageName")String softwarePackageName,@Param("softCodeName")String softCodeName);

    List<ApkUpgradeTask> findAllUpgradeTask();

    List<ApkUpgradeTask> findUpgradeTaskByYstenIdOrGroupId(@Param("ystenId")String ystenId, @Param("groupId")Long groupId);
}