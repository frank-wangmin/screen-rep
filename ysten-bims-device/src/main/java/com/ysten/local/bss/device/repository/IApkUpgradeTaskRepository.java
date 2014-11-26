package com.ysten.local.bss.device.repository;

import com.ysten.local.bss.device.domain.*;
import com.ysten.utils.page.Pageable;

import java.util.List;

public interface IApkUpgradeTaskRepository {

    ApkUpgradeTask getById(long id);
    
    ApkUpgradeTask selectByPrimaryKey(long id);

    List<ApkUpgradeTask> findAllUpgradeTask();

    List<ApkUpgradeTask> findUpgradeTaskByYstenIdOrGroupId(String ystenId, Long groupId);
    
    Pageable<ApkUpgradeTask> findApkUpgradeTaskListBySoftCodeAndPackage(String softwarePackageName,String softCodeName,Integer pageNo,Integer pageSize);
	
    boolean save(ApkUpgradeTask apkUpgradeTask);
    
    boolean updateById(ApkUpgradeTask apkUpgradeTask);
    
    boolean deleteById(long id);
    
}
