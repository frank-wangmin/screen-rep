package com.ysten.local.bss.system.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.system.domain.Operator;

/**
 * 
 * @author xuyesheng
 * @date 2011-12.23
 * 
 */
public interface OperatorMapper extends BaseMapper<Operator, Long> {

    /**
     * 查询系统操作员信息(支持分页)
     * 
     * @param pageNo
     *            起始页
     * @param pageSize
     *            每页显示多少条
     * @return List <Operator>
     */
    List<Operator> findOperators(@Param("operatorName") String operatorName,
            @Param("loginName") String loginName, @Param("currentPage") int currentPage, @Param("pageSize") int pageSize);

    /**
     * 通过登录名称查询操作员信息
     * 
     * @param loginName
     *            登录名称
     * @return
     */
    Operator getOperatorByLoginName(@Param("loginName") String loginName);

    /**
     * 根据名称获取总数
     * 
     * @param operatorName
     * @param loginName
     * @return
     */
    int getCountByName(@Param("operatorName") String operatorName, @Param("loginName") String loginName);

    int getOperatorLastId();
}