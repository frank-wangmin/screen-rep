package com.ysten.local.bss.device.repository;

import com.ysten.local.bss.device.domain.DeviceAbnormalLog;

public interface IDeviceAbnormalLogRepository {

    void saveDeviceAbnormalLog(DeviceAbnormalLog deviceAbnormalLog);
}
