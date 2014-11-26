package com.ysten.local.bss.logger.service.impl;

import com.ysten.local.bss.logger.domain.DeviceOperateLog;
import com.ysten.local.bss.logger.repository.IDeviceOperateLogRepository;
import com.ysten.local.bss.logger.service.IDeviceOperateLogService;
import com.ysten.message.queue.QueueMessageProducer;
import com.ysten.utils.page.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by frank on 14-7-8.
 */
@Service
public class DeviceOperateLogServiceImpl implements IDeviceOperateLogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceOperateLogServiceImpl.class);

    @Autowired
    private QueueMessageProducer logProducer;
    @Autowired
    private IDeviceOperateLogRepository deviceOperateLogRepository;

    @Override
    public void deleteByPrimaryKey(Long id) {
        if(id != null) deviceOperateLogRepository.deleteByPrimaryKey(id);
    }

    @Override
    public void insertSelective(DeviceOperateLog record) {
        if(record != null) {
//            deviceOperateLogRepository.insertSelective(record);
            try {
                logProducer.publish(record);
            } catch (Exception e) {
                LOGGER.error("Save device operate log error", e);
            }
        }
    }

    @Override
    public void insertSelective(DeviceOperateLog record, String result) {
        if(record != null){
            record.setResult(result);
//            deviceOperateLogRepository.insertSelective(record);
            try {
                logProducer.publish(record);
            } catch (Exception e) {
                LOGGER.error("Save device operate log error", e);
            }
        }
    }

    @Override
    public DeviceOperateLog selectByPrimaryKey(Long id) {
        if(id != null) return deviceOperateLogRepository.selectByPrimaryKey(id);
        return null;
    }

    @Override
    public void updateByPrimaryKeySelective(DeviceOperateLog record) {
        if(record != null) deviceOperateLogRepository.updateByPrimaryKeySelective(record);
    }

    @Override
    public Pageable<DeviceOperateLog> findDeviceOperateLogPagination(String deviceCode, String ip, String status,String result, Integer pageNo, Integer pageSize) {
        return deviceOperateLogRepository.findDeviceOperateLogPagination(deviceCode,ip,status,result,pageNo,pageSize);
    }

    @Override
    public List<DeviceOperateLog> findDeviceOperateLog(String deviceCode, String ip, String status,String result) {
        return deviceOperateLogRepository.findDeviceOperateLog(deviceCode,ip,status,result);
    }

    @Override
    public DeviceOperateLog getDeviceOperateLog(String deviceCode, String ip, String status) {
        DeviceOperateLog log = new DeviceOperateLog();
        log.setDeviceCode(deviceCode);
        log.setIp(ip);
        log.setStatus(status);
        log.setCreateDate(new Date());
        return log;
    }
}
