package com.ysten.local.bss.device.service;

import com.ysten.local.bss.bean.tb.SyncDataByDevice;

import java.util.List;

public interface ISyncLbssDataToBimsService {

    void saveSyncData(List<SyncDataByDevice> syncDataList) throws Exception;

    void saveAcceptDataByDevice(SyncDataByDevice syncData) throws Exception;

}
