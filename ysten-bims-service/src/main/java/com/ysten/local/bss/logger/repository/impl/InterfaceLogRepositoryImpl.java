package com.ysten.local.bss.logger.repository.impl;

import com.ysten.local.bss.logger.domain.InterfaceLog;
import com.ysten.local.bss.logger.repository.IInterfaceLogRepository;
import com.ysten.local.bss.logger.repository.mapper.InterfaceLogMapper;
import com.ysten.utils.page.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InterfaceLogRepositoryImpl implements IInterfaceLogRepository {
    
    @Autowired
    private InterfaceLogMapper interfaceLogMapper;

    @Override
    public boolean saveInterfaceLog(InterfaceLog interfaceLog) {
        return this.interfaceLogMapper.save(interfaceLog)==1;
    }

    @Override
    public boolean saveInterfaceLog(List<InterfaceLog> logs) {
        return logs.size() == this.interfaceLogMapper.saveList(logs);
    }

    @Override
	public InterfaceLog getByDeviceSno(String interfaceName, String deviceId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("interfaceName", interfaceName);
		params.put("deviceId", "%=" + deviceId + "%");
		
		return this.interfaceLogMapper.getByDeviceSno(params);
	}

    @Override
    public InterfaceLog getByUserId(String interfaceName, String userId) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("interfaceName", interfaceName);
        params.put("userId", "%=" + userId + "%");

        return this.interfaceLogMapper.getByUserName(params);
    }

    @Override
    public Pageable<InterfaceLog> findAllInterfaceLog(String interfaceName, String caller, String input, String output,
            int pageNo, int pageSize) {
        List<InterfaceLog> interfaceLogList = this.interfaceLogMapper.findAllInterfaceLog(interfaceName,caller,input,output,pageNo, pageSize);
        int total = this.interfaceLogMapper.getCountByCondition(interfaceName,caller,input,output);
        return new Pageable<InterfaceLog>().instanceByStartRow(interfaceLogList, total, pageNo, pageSize);
    }

	@Override
	public List<InterfaceLog> findAllInterfaceLog() {
		return this.interfaceLogMapper.findAllInterfaceLog(null,null,null,null,null, null);
	}

	@Override
	public List<InterfaceLog> findExportInterfaceLog(String interfaceName,
			String caller, String input, String output) {
		return this.interfaceLogMapper.findAllInterfaceLog(interfaceName, caller, input, output, null, null);
	}

	@Override
	public List<InterfaceLog> findInterfaceLogByIds(List<Long> ids) {
		return this.interfaceLogMapper.findInterfaceLogByIds(ids);
	}
}
