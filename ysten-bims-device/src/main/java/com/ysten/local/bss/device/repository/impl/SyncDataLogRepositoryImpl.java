package com.ysten.local.bss.device.repository.impl;

import com.ysten.local.bss.device.domain.SyncDataLog;
import com.ysten.local.bss.device.repository.ISyncDataLogRepository;
import com.ysten.local.bss.device.repository.mapper.SyncDataLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SyncDataLogRepositoryImpl implements ISyncDataLogRepository {
    @Autowired
    private SyncDataLogMapper syncDataLogMapper;

    @Override
    public void saveSyncDataLog(SyncDataLog syncDataLog) {
        this.syncDataLogMapper.save(syncDataLog);
    }
}
