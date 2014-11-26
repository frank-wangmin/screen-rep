package com.ysten.local.bss.device.repository;

import java.util.List;

import com.ysten.local.bss.device.domain.AppUpgradeMap;
import com.ysten.local.bss.device.domain.DeviceGroup;
import com.ysten.local.bss.device.domain.UserGroup;

/**
 * 
 * @author cwang
 * 
 */
public interface IAppUpgradeMapRepository {

	List<AppUpgradeMap> findpUpgradeMapByGroupIdAndType(Long groupId,String type);
	
	List<AppUpgradeMap> findAppUpgradeMapByDeviceCode(String deviceCode);
	
	AppUpgradeMap getByYstenIdAndUpgradeId(String ystenId, long upgradeId);

	boolean save(AppUpgradeMap appUpgradeMap);

	boolean deleteByUpgradeId(List<Long> upgradeIds);

	/**
	 * 根据分组Id删除开机动画设备分组信息
	 * 
	 * @param id
	 */
	void deleteAppUpgradeMapByGroupId(Long id);

	void deleteAppUpgradeMapByCode(String deviceCode);

	List<AppUpgradeMap> findMapByUpgradeIdAndType(Long upgradeId, String type);

	/**
	 * get the deviceGroups by upgradeId
	 * 
	 * @param upgradeId
	 * @param type
	 * @return list
	 */
	List<DeviceGroup> findDeviceGroupByAppIdAndType(String type);

	/**
	 * get the userGroups by upgradeId
	 * 
	 * @param type
	 * @return list
	 */
	List<UserGroup> findUserGroupByAppIdAndType(String type);

	boolean deleteAppUpgradeMapByUpgradeId(Long upgradeId);

	boolean deleteUserAppUpgradeMapByUpgradeId(Long upgradeId);
}
