package com.ysten.local.bss.device.repository;

import com.ysten.local.bss.device.domain.DeviceHistory;

public interface IDeviceHistoryRepository {

    /**
     * 保存设备历史信息
     * 
     * @param deviceHistory
     * @return
     */
    
    boolean save(DeviceHistory deviceHistory);
}
