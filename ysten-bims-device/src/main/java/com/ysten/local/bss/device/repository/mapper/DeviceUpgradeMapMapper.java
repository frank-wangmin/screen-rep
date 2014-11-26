package com.ysten.local.bss.device.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.device.domain.DeviceUpgradeMap;

public interface DeviceUpgradeMapMapper {
	
	DeviceUpgradeMap getByDeviceCode(@Param("ystenId")String deviceCode);
	
	List<DeviceUpgradeMap>  findMapByYstenId(@Param("ystenId")String ystenId);
	
	DeviceUpgradeMap getByGroupId(@Param("groupId")long groupId);

    DeviceUpgradeMap getByYstenIdAndUpgradeId(@Param("ystenId")String ystenId, @Param("upgradeId")long upgradeId);

    DeviceUpgradeMap getByGroupIdAndUpgradeId(@Param("deviceGroupId") long deviceGroupId, @Param("upgradeId")long upgradeId);

	int deleteByUpgradeId(@Param("ids")List<Long> ids);
	
	int deleteByUpgradeIdAndType(@Param("upgradeId")Long upgradeId,@Param("type")String type);
	
	int save(DeviceUpgradeMap deviceUpgradeMap);
	
	void bulkSaveDeviceUpgradeMap(List<DeviceUpgradeMap> list);
	
	void deleteMapByYstenIdsAndUpgradeId(@Param("ystenIds")String[] ystenIds, @Param("upgradeId")long upgradeId);
	
	int updateByUpgradeId(DeviceUpgradeMap deviceUpgradeMap);
	
	List<DeviceUpgradeMap>  findMapByUpgradeIdAndType(@Param("upgradeId")Long upgradeId,@Param("type")String type);

	/**
	 * 根据分组Id删除升级设备分组信息
	 * @param id
	 */
	void deleteUpgradeTaskMapByGroupId(@Param("groupId")Long id);
	
	int deleteUpgradeTaskMapByCode(@Param("ystenId")String ystenId);
	
	
}
