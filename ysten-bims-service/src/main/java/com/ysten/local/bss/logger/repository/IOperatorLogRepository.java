package com.ysten.local.bss.logger.repository;

import java.util.List;

import com.ysten.local.bss.logger.domain.OperateLog;
import com.ysten.utils.page.Pageable;

public interface IOperatorLogRepository {

    /**
     * 日志查询
     * @param modelName
     * @param operator
     * @param operatorDate
     * @param pageNo
     * @param pageSize
     * @return
     */
    Pageable<OperateLog> findOperateLogger(String modelName, String operator,String operatorId, String beginDate, String endDate, int start, int limit);

    /**
     * 保存系统操作日志
     * @param log
     * @return
     */
    boolean saveOperateLog(OperateLog operateLog);
    
    /**
     * 获取Module Name列表，这个列表是记录Log时各自定义的。不是从数据库从取的固定的名字，所以需要从bss_operate_log表中获取这个名字列表
     * @return
     */
    List<String> getModuleNameList();
}
