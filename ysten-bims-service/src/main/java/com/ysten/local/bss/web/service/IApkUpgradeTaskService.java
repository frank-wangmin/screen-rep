package com.ysten.local.bss.web.service;

import java.util.List;

import com.ysten.local.bss.device.domain.ApkUpgradeMap;
import com.ysten.local.bss.device.domain.ApkUpgradeTask;
import com.ysten.utils.page.Pageable;

public interface IApkUpgradeTaskService {
	
	Pageable<ApkUpgradeTask> findApkUpgradeTaskListBySoftCodeAndPackage(String softwarePackageName,String softCodeName,Integer pageNo,Integer pageSize);

    List<ApkUpgradeTask> findAllUpgradeTask();

    ApkUpgradeTask getById(Long id);

	boolean save(ApkUpgradeTask apkUpgradeTask);

	boolean updateById(ApkUpgradeTask apkUpgradeTask);

	String deleteUpgradeTaskByIds(List<Long> ids);

	List<ApkUpgradeMap> findMapByUpgradeIdAndType(Long upgradeId,String type);

	String saveUpgradeTaskMap(Long upgradeTaskId,String areaIds,String deviceGroupIds,String deviceCodes,String userGroupIds,String userIds);

    boolean deleteUpgradeTaskMap(List<Long> ids);
}
