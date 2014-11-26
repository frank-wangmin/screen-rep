package com.ysten.local.bss.device.repository;

import java.util.List;

import com.ysten.local.bss.device.domain.AppUpgradeMap;
import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.device.domain.AppSoftwarePackage;
import com.ysten.local.bss.device.domain.AppUpgradeTask;
import com.ysten.local.bss.device.domain.UserAppUpgradeMap;
import com.ysten.utils.page.Pageable;
/**
 * 
 * @author cwang
 *
 */
public interface IAppUpgradeTaskRepository {
	
	Pageable<AppUpgradeTask> findAppUpgradeTaskListByCondition(Long softwarePackageId,Long softCodeId,Integer pageNo,Integer pageSize);
	
	Pageable<AppUpgradeTask> findAppUpgradeTaskListByCondition(String softwarePackageName,String softCodeName,Integer pageNo,Integer pageSize);
	
	boolean deleteAppUpgradeTaskByIds(List<Long> ids);

	AppUpgradeTask getAppUpgradeTaskById(long id);

	AppSoftwarePackage findLatestSoftwarePackageByIds(List<Long> upgradeIds,int versionSeq,int sdkVersion,long softCodeId);
	
	boolean saveAppUpgradeTask(AppUpgradeTask upgradeTask);
	
	boolean updateAppUpgradeTask(AppUpgradeTask upgradeTask);
	
	void deleteUserUpgradeMapByUserGroupId(Long userGroupId);
	
	void deleteAppUpgradeMapByUserId(String userId);
	
	boolean saveUserAppMap(UserAppUpgradeMap userAppUpgradeMap);
	
	List<UserAppUpgradeMap> findMapByUpgradeIdAndType(Long upgradeId,String type);
	
	List<UserAppUpgradeMap> getUserUpgradeMapByUserGroupId(Long userGroupId);
	
	List<UserAppUpgradeMap> getUserUpgradeMapByUserId(String userId);

    /**
     * find AppUpgradeMap according the upgradeId
     * @param upgradeId
     * @return list
     */
    List<AppUpgradeMap>  findMapByUpgradeId(@Param("upgradeId")Long upgradeId);

    /**
     * find UserAppUpgradeMap according the upgradeId
     * @param upgradeId
     * @return list
     */
    List<UserAppUpgradeMap>  findUserMapByUpgradeId(@Param("upgradeId")Long upgradeId);

}
