package com.ysten.local.bss.device.repository.impl;

import java.util.List;

import com.ysten.cache.annotation.MethodFlushAnimation;
import com.ysten.cache.annotation.MethodFlushBackgroundImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysten.local.bss.device.domain.DeviceGroupMap;
import com.ysten.local.bss.device.repository.IDeviceGroupMapRepository;
import com.ysten.local.bss.device.repository.mapper.DeviceGroupMapMapper;

/**
 * @author cwang
 * @version 2014-6-26 下午4:25:02
 * 
 */
@Repository
public class DeviceGroupMapRepositoryImpl implements IDeviceGroupMapRepository {
	@Autowired
	private DeviceGroupMapMapper deviceGroupMapMapper;

	@Override
	public List<DeviceGroupMap> getByYstenId(String ystenId) {
		return this.deviceGroupMapMapper.getByYstenId(ystenId);
	}
	
	@Override
    @MethodFlushAnimation
    @MethodFlushBackgroundImage
	public void deleteDeviceGroupMapByCodeAndGroup(String code, Long groupId) {
		this.deviceGroupMapMapper.deleteDeviceGroupMapByCodeAndGroup(code, groupId);
	}

    @Override
    public List<DeviceGroupMap> findDeviceGroupMapByGroupId(Long deviceGroupId) {
        return this.deviceGroupMapMapper.getDeviceGroupMapByGroupId(deviceGroupId);
    }

    @Override
	public List<DeviceGroupMap> getDeviceGroupMapByGroupId(Long groupId) {
		return this.deviceGroupMapMapper.getDeviceGroupMapByGroupId(groupId);
	}

	@Override
	@MethodFlushAnimation
    @MethodFlushBackgroundImage
	public void deleteMapByYstenIdsAndGroups(String[] ystenIds,
			List<Long> groupIds) {
		this.deviceGroupMapMapper.deleteMapByYstenIdsAndGroups(ystenIds, groupIds);
	}
}
