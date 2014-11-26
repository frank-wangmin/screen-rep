package com.ysten.local.bss.device.repository;

import com.ysten.local.bss.device.domain.DeviceUpgradeMap;

/**
 * Created by hujing on 14-8-15.
 */
public interface IDeviceUpgradeMapRepository {
    DeviceUpgradeMap getDeviceUpgradeMapYstenId(String ystenId, long upgradeId);

    DeviceUpgradeMap getDeviceUpgradeMapByGroupId(long groupId, long upgradeId);
}
