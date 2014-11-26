package com.ysten.local.bss.logger.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.logger.domain.OperateLog;

/**
 * 
 * @author xuyesheng
 * @date 2011-12.23
 * 
 */
public interface OperateLogMapper {
    /**
     * 删除
     * 
     * @param id
     * @return
     */
    int delete(Integer id);

    /**
     * 保存
     * 
     * @param AuditLog
     * @return
     */
    int save(OperateLog operatorLog);

    /**
     * 通过id查询
     * 
     * @param id
     * @return
     */
    OperateLog getById(Integer id);

    /**
     * 更新
     * 
     * @param auditLog
     * @return
     */
    int update(OperateLog operateLog);
    
    /**
     * 查询日志条数
     * @param modelName
     * @param operator
     * @param operatorDate
     * @return
     */
    int getCountByCondition(@Param("modelName")String modelName, @Param("operator")String operator, @Param("beginDate")String beginDate,
            @Param("endDate")String endDate);
    
    /**
     * 查询日志
     * @param modelName
     * @param operator
     * @param operatorDate
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<OperateLog> findOperateLogger(@Param("modelName")String modelName, 
            @Param("operator")String operator, 
            @Param("operatorId")String operatorId,
            @Param("beginDate")String beginDate,
            @Param("endDate")String endDate,
            @Param("pageNo")int pageNo,
            @Param("pageSize")int pageSize);
    
    /**
     * 获取Module Name列表，这个列表是记录Log时各自定义的。不是从数据库从取的固定的名字，所以需要从bss_operate_log表中获取这个名字列表
     * @return
     */
    List<String> getModuleNameList();
}