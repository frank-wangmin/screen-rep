package com.ysten.local.bss.device.repository.impl;

import com.ysten.local.bss.device.domain.DeviceAbnormalLog;
import com.ysten.local.bss.device.repository.IDeviceAbnormalLogRepository;
import com.ysten.local.bss.device.repository.mapper.DeviceAbnormalLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DeviceAbnormalLogRepositoryImpl implements IDeviceAbnormalLogRepository {
    @Autowired
    private DeviceAbnormalLogMapper deviceAbnormalLogMapper;

    @Override
    public void saveDeviceAbnormalLog(DeviceAbnormalLog deviceAbnormalLog) {
        this.deviceAbnormalLogMapper.save(deviceAbnormalLog);
    }
}
