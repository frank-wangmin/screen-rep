package com.ysten.local.bss.device.repository;

import java.util.List;

import com.ysten.local.bss.device.domain.DeviceGroupMap;

/**
 */
public interface IDeviceGroupMapRepository {
	
	List<DeviceGroupMap> getByYstenId(String ystenId);

	List<DeviceGroupMap> getDeviceGroupMapByGroupId(Long GroupId);

	void deleteDeviceGroupMapByCodeAndGroup(String code, Long groupId);
	
	void deleteMapByYstenIdsAndGroups(String[] ystenIds, List<Long> groupIds);

    List<DeviceGroupMap> findDeviceGroupMapByGroupId(Long deviceGroupId);
}
