package com.ysten.local.bss.logger.repository.impl;

import com.ysten.local.bss.logger.domain.DeviceOperateLog;
import com.ysten.local.bss.logger.repository.IDeviceOperateLogRepository;
import com.ysten.local.bss.logger.repository.mapper.DeviceOperateLogMapper;
import com.ysten.utils.page.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by frank on 14-7-8.
 */
@Repository
public class DeviceOperateLogRepositoryImpl implements IDeviceOperateLogRepository {

    @Autowired
    private DeviceOperateLogMapper deviceOperateLogMapper;

    @Override
    public void deleteByPrimaryKey(Long id) {
        deviceOperateLogMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void insertSelective(DeviceOperateLog record) {
        deviceOperateLogMapper.insertSelective(record);
    }

    @Override
    public DeviceOperateLog selectByPrimaryKey(Long id) {
        return deviceOperateLogMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateByPrimaryKeySelective(DeviceOperateLog record) {
        deviceOperateLogMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public Pageable<DeviceOperateLog> findDeviceOperateLogPagination(String deviceCode, String ip, String status,String result, Integer pageNo, Integer pageSize) {
        List<DeviceOperateLog> deviceOperateLogList = deviceOperateLogMapper.findDeviceOperateLogPagination(deviceCode,ip,status,result,pageNo, pageSize);
        int total = deviceOperateLogMapper.findDeviceOperateLogCount(deviceCode,ip,status,result);
        return new Pageable<DeviceOperateLog>().instanceByStartRow(deviceOperateLogList, total, pageNo, pageSize);
    }

    @Override
    public List<DeviceOperateLog> findDeviceOperateLog(String deviceCode, String ip, String status,String result) {
        return deviceOperateLogMapper.findDeviceOperateLog(deviceCode,ip,status,result);
    }
}
