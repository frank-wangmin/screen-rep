package com.ysten.local.bss.web.service;

import com.ysten.local.bss.device.domain.DeviceUpgradeMap;
import com.ysten.local.bss.device.domain.UpgradeTask;
import com.ysten.local.bss.device.domain.UserUpgradeMap;
import com.ysten.utils.page.Pageable;

import java.util.List;

public interface IUpgradeTaskService {
	
	Pageable<UpgradeTask> getListByCondition(Long softwarePackageId,Long softCodeId,Integer pageNo,Integer pageSize);
	
	Pageable<UpgradeTask> findUpgradeTaskListByCondition(String softwarePackageName,String softCodeName,Integer pageNo,Integer pageSize);
	
	List<UpgradeTask> findUpgradeTasksByCondition(Long softwarePackageId,Long softCodeId);

    List<UpgradeTask> findAllUpgradeTask();

	boolean deleteByIds(List<Long> ids);
	
	String deleteUpgradeTaskByIds(List<Long> ids);
	
	boolean deleteUpgradeTaskMap(List<Long> ids);
	
	UpgradeTask getByVersionSeq(int versionSeq);
	
	boolean save(UpgradeTask upgradeTask);
	
	boolean updateById(UpgradeTask upgradeTask);
	
//	boolean updateById(UpgradeTask upgradeTask,String deviceGroupIds);
	
	UpgradeTask getById(long id);
	
	String saveMap(UpgradeTask upgradeTask,String type,String deviceGroupIds,String deviceCodes);
	
	String saveMap(Long upgradeTaskId,String type,String deviceGroupIds,String deviceCodes);
	
	String saveUpgradeTaskMap(Long upgradeTaskId,String areaIds,String deviceGroupIds,String deviceCodes,String userGroupIds,String userIds);
	
	String saveUserMap(UpgradeTask upgradeTask,String type,String userGroupIds,String userId);
	
	String saveUserMap(Long upgradeTaskId,String type,String userGroupIds,String userIds);
	
	List<DeviceUpgradeMap>  findMapByUpgradeIdAndType(Long upgradeId,String type);
	
	List<UserUpgradeMap> findUserMapByUpgradeIdAndType(Long upgradeId,String type);

}
