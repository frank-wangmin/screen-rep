package com.ysten.local.bss.device.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.ysten.local.bss.device.domain.DeviceHistory;
import com.ysten.local.bss.device.repository.IDeviceHistoryRepository;
import com.ysten.local.bss.device.repository.mapper.DeviceHistoryMapper;
@Repository
public class DeviceHistoryRepositoryImpl implements IDeviceHistoryRepository{
    @Autowired
    private DeviceHistoryMapper deviceHistoryMapper;
    @Override
    public boolean save(DeviceHistory deviceHistory) {
        return 1 == this.deviceHistoryMapper.save(deviceHistory);
    }
    
}
