package com.ysten.local.bss.logger.service.impl;

import com.ysten.local.bss.logger.domain.InterfaceLog;
import com.ysten.local.bss.logger.repository.IInterfaceLogRepository;
import com.ysten.message.MessageListener;
import com.ysten.message.redis.Topic;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Neil on 2014-04-28.
 */
public class InterfaceLogListener implements MessageListener {

    @Autowired
    private IInterfaceLogRepository interfaceLogRepository;

    public IInterfaceLogRepository getInterfaceLogRepository() {
        return interfaceLogRepository;
    }

    public void setInterfaceLogRepository(IInterfaceLogRepository interfaceLogRepository) {
        this.interfaceLogRepository = interfaceLogRepository;
    }

    @Override
    public void onMessage(Topic topic, Object o) {
        if(o instanceof  InterfaceLog){
            InterfaceLog interfaceLog = (InterfaceLog) o;
            this.interfaceLogRepository.saveInterfaceLog(interfaceLog);
        }
    }
}
