package com.ysten.local.bss.logger.service.impl;

import com.ysten.local.bss.logger.domain.DeviceOperateLog;
import com.ysten.local.bss.logger.repository.IDeviceOperateLogRepository;
import com.ysten.message.MessageListener;
import com.ysten.message.redis.Topic;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Neil on 2014-04-28.
 */
public class DeviceOperateLogListener implements MessageListener {

    @Autowired
    private IDeviceOperateLogRepository deviceOperateLogRepository;

    public IDeviceOperateLogRepository getDeviceOperateLogRepository() {
        return deviceOperateLogRepository;
    }

    public void setDeviceOperateLogRepository(IDeviceOperateLogRepository deviceOperateLogRepository) {
        this.deviceOperateLogRepository = deviceOperateLogRepository;
    }

    @Override
    public void onMessage(Topic topic, Object o) {
        if(o instanceof DeviceOperateLog){
            DeviceOperateLog log = (DeviceOperateLog) o;
            this.deviceOperateLogRepository.insertSelective(log);
        }
    }
}
