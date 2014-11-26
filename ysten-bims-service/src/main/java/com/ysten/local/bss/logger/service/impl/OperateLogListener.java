package com.ysten.local.bss.logger.service.impl;

import com.ysten.local.bss.logger.domain.OperateLog;
import com.ysten.local.bss.logger.repository.IOperatorLogRepository;
import com.ysten.message.MessageListener;
import com.ysten.message.redis.Topic;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Neil on 2014-04-28.
 */
public class OperateLogListener implements MessageListener {

    @Autowired
    private IOperatorLogRepository operatorLogRepository;

    public IOperatorLogRepository getOperatorLogRepository() {
        return operatorLogRepository;
    }

    public void setOperatorLogRepository(IOperatorLogRepository operatorLogRepository) {
        this.operatorLogRepository = operatorLogRepository;
    }

    @Override
    public void onMessage(Topic topic, Object o) {
        if(o instanceof  OperateLog){
            OperateLog operateLog = (OperateLog) o;
            this.operatorLogRepository.saveOperateLog(operateLog);
        }
    }
}
