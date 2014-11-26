package com.ysten.local.bss.web.service;

import java.util.List;
import com.ysten.local.bss.device.domain.AppUpgradeMap;
import com.ysten.local.bss.device.domain.AppUpgradeTask;
import com.ysten.local.bss.device.domain.UserAppUpgradeMap;
import com.ysten.utils.page.Pageable;

public interface IAppUpgradeTaskService {
	
	Pageable<AppUpgradeTask> getListByCondition(Long softwarePackageId,Long softCodeId,Integer pageNo,Integer pageSize);
	
	Pageable<AppUpgradeTask> findAppUpgradeTaskListByCondition(String softwarePackageName,String softCodeName,Integer pageNo,Integer pageSize);

	String deleteByIds(List<Long> ids);
	
	AppUpgradeTask getById(Long id);
	
	boolean save(AppUpgradeTask upgradeTask);
//	
//	UpgradeTask getByVersionSeq(int versionSeq);
//	boolean updateById(AppUpgradeTask upgradeTask,String deviceGroupIds);
	boolean updateById(AppUpgradeTask upgradeTask);
	
	String saveMap(AppUpgradeTask upgradeTask,String type,String deviceGroupIds,String deviceCodes);
	
	String saveUserMap(AppUpgradeTask upgradeTask,String type,String userGroupIds,String userId);
	
	List<AppUpgradeMap>  findMapByUpgradeIdAndType(Long upgradeId,String type);
	
    List<UserAppUpgradeMap> findUserMapByUpgradeIdAndType(Long upgradeId,String type);

    /**
     * save deviceMap and userMap bound with upgradeTask
     * @param upgradeTask
     * @param deviceGroupIds
     * @param deviceCodes
     * @param userGroupIds
     * @param userId
     * @return result
     */
   String saveDeviceAndUserMap(AppUpgradeTask upgradeTask, String areaIds,String deviceGroupIds,String deviceCodes,String userGroupIds,String userId);

    /**
     * delete the deviceAppUpgrade map and userAppUpgrade map
     * @param upgradeTask
     * @return b
     */
    String deleteDeviceAndUserMap(AppUpgradeTask upgradeTask);

}
