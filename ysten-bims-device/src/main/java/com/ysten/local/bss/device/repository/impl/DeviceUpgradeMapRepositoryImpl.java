package com.ysten.local.bss.device.repository.impl;

import com.ysten.local.bss.device.domain.DeviceUpgradeMap;
import com.ysten.local.bss.device.repository.IDeviceUpgradeMapRepository;
import com.ysten.local.bss.device.repository.mapper.DeviceUpgradeMapMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by hujing on 14-8-15.
 */
@Repository
public class DeviceUpgradeMapRepositoryImpl implements IDeviceUpgradeMapRepository{

    @Autowired
    private DeviceUpgradeMapMapper deviceUpgradeMapMapper;

    public DeviceUpgradeMap getDeviceUpgradeMapYstenId(String ystenId, long upgradeId){
        DeviceUpgradeMap deviceUpgradeMap = null;
        deviceUpgradeMap = this.deviceUpgradeMapMapper.getByYstenIdAndUpgradeId(ystenId, upgradeId);
        return deviceUpgradeMap;
    }

    public DeviceUpgradeMap getDeviceUpgradeMapByGroupId(long deviceGroupId, long upgradeId){
        DeviceUpgradeMap deviceUpgradeMap = null;
        deviceUpgradeMap = this.deviceUpgradeMapMapper.getByGroupIdAndUpgradeId(deviceGroupId, upgradeId);
        return deviceUpgradeMap;
    }
}
