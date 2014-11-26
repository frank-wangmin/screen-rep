package com.ysten.local.bss.logger.service.impl;

import com.ysten.local.bss.logger.domain.InterfaceLog;
import com.ysten.local.bss.logger.repository.IInterfaceLogRepository;
import com.ysten.local.bss.logger.service.IInterfaceLogService;
import com.ysten.message.queue.QueueMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class InterfaceLogServiceImpl implements IInterfaceLogService {

    @Autowired
    private QueueMessageProducer logProducer;

    @Autowired
    private IInterfaceLogRepository interfaceLogRepository;

    @Override
    public boolean saveInterfaceLog(InterfaceLog interfaceLog,String output) {
        interfaceLog.setOutput(output);
        try {
            logProducer.publish(interfaceLog);
        } catch (Exception e) {
           return false;
        }
        return true;
    }

    @Override
    public boolean saveInterfaceLog(List<InterfaceLog> logs) {
        try {
            for (InterfaceLog log : logs) {
                logProducer.publish(log);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public InterfaceLog getInterfaceLog(String caller, String input) {
        InterfaceLog log = new InterfaceLog();
        log.setCaller(caller);
        log.setInput(input);
        log.setCallTime(new Date());
        return log;
    }

    @Override
    public InterfaceLog getInterfaceLog(String caller, String interfaceName, String input) {
        InterfaceLog log = new InterfaceLog();
        log.setCaller(caller);
        log.setInterfaceName(interfaceName);
        log.setInput(input);
        log.setCallTime(new Date());
        return log;
    }

	@Override
	public InterfaceLog getByDeviceSno(String deviceId) {
		return this.interfaceLogRepository.getByDeviceSno("deviceAuth", deviceId);
	}

    @Override
    public InterfaceLog getByUserId(String userId) {
        return this.interfaceLogRepository.getByUserId("deviceAuth", userId);
    }
}
