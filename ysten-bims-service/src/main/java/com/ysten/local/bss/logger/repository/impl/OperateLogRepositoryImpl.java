package com.ysten.local.bss.logger.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysten.local.bss.logger.domain.OperateLog;
import com.ysten.local.bss.logger.repository.IOperatorLogRepository;
import com.ysten.local.bss.logger.repository.mapper.OperateLogMapper;
import com.ysten.utils.page.Pageable;

@Repository
public class OperateLogRepositoryImpl implements IOperatorLogRepository {

    @Autowired
    private OperateLogMapper operateLogMapper;

    @Override
    public Pageable<OperateLog> findOperateLogger(String modelName, String operator,String operatorId, String beginDate, String endDate,
            int start, int limit) {
        List<OperateLog> auditLogList = this.operateLogMapper.findOperateLogger(modelName, operator,operatorId, beginDate, endDate,
                start, limit);
        int total = this.operateLogMapper.getCountByCondition(modelName, operator, beginDate, endDate);
        return new Pageable<OperateLog>().instanceByStartRow(auditLogList, total, start, limit);
    }

    @Override
    public boolean saveOperateLog(OperateLog log) {
        return 1 == this.operateLogMapper.save(log);
    }

    @Override
	public List<String> getModuleNameList() {
		return operateLogMapper.getModuleNameList();
	}
}
