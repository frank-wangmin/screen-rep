package com.ysten.local.bss.device.repository;

import java.util.List;

import com.ysten.local.bss.device.domain.DeviceUpgradeMap;
import com.ysten.local.bss.device.domain.UpgradeTask;
import com.ysten.local.bss.device.domain.UserUpgradeMap;
import com.ysten.utils.page.Pageable;

public interface IUpgradeTaskRepository {
	
	Pageable<UpgradeTask> getListByCondition(Long softwarePackageId,Long softCodeId,Integer pageNo,Integer pageSize);
	
	Pageable<UpgradeTask> findUpgradeTaskListByCondition(String softwarePackageName,String softCodeName,Integer pageNo,Integer pageSize);
	
	List<UpgradeTask> findUpgradeTasksByCondition(Long softwarePackageId,Long softCodeId);

    List<UpgradeTask> findUpgradeTaskByYstenIdOrGroupId(String ystenId, Long groupId);

    List<UpgradeTask> findAllUpgradeTask();
	
	boolean deleteByIds(List<Long> ids);
	
	boolean deleteUpgradeTaskById(Long id);
	
	boolean deleteDeviceUpgradeMap(List<Long> upgradeIds);
	
	boolean deleteDeviceUpgradeMapByType(Long upgradeId,String type);
	
	boolean deleteUserUpgradeMap(List<Long> upgradeIds);
	
	UpgradeTask getByVersionSeq(int versionSeq);
	
	UpgradeTask getById(long id);
	
	boolean save(UpgradeTask upgradeTask);
	
	boolean updateById(UpgradeTask upgradeTask);
	
	boolean saveDeviceUpgradeMap(DeviceUpgradeMap deviceUpgradeMap);
	
	void bulkSaveDeviceUpgradeMap(List<DeviceUpgradeMap> list);
	
	boolean saveUserUpgradeMap(UserUpgradeMap userUpgradeMap);
	
	boolean updateDeviceUpgradeMap(DeviceUpgradeMap deviceUpgradeMap);
	
	List<DeviceUpgradeMap>  findMapByYstenId(String ystenId);
	
	List<DeviceUpgradeMap>  findMapByUpgradeIdAndType(Long upgradeId,String type);
	
	List<UserUpgradeMap> findUserMapByUpgradeIdAndType(Long upgradeId,String type);
	
	DeviceUpgradeMap getByGroupId(Long deviceGroupId);
	
	DeviceUpgradeMap getByYstenIdAndUpgradeId(String ystenId,long upgradeId);
	
	UserUpgradeMap getUserUpgradeMapByUserGroupId(Long userGroupId);
	
	UserUpgradeMap getUserUpgradeMapByUserId(String userId);
	/**
	 * 根据分组Id删除升级终端分组信息
	 * @param id
	 */
	void deleteUpgradeTaskMapByGroupId(Long id);
	void deleteUserUpgradeMapByUserGroupId(Long userGroupId);
	void deleteUserUpgradeMapByUserId(String userId);
	void deleteUpgradeTaskMapByCode(String deviceCode);
	
	void deleteMapByYstenIdsAndUpgradeId(String[] ystenIds,long upgradeId);

    List<UserUpgradeMap> findUserMapBySoftCodeIdAndVersionSeq(Long softCodeId,int versionSeq);
}
