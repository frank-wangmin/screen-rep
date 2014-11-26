package com.ysten.local.bss.device.repository;

import com.ysten.local.bss.device.domain.SyncDataLog;

public interface ISyncDataLogRepository {
    void saveSyncDataLog(SyncDataLog syncDataLog);
}
