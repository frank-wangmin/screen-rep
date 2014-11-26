package com.ysten.local.bss.system.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.system.domain.OperatorRoleMap;

/**
 * 
 * @author xuyesheng
 * @date 2011-12.23
 * 
 */
public interface OperatorRoleMapMapper extends BaseMapper<OperatorRoleMap, Long> {

    /**
     * 查找操作人员所具有的权限
     * 
     * @param operatorId
     *            操作员ID
     * @return
     * 
     */
    List<OperatorRoleMap> findByOperatorId(@Param("operatorId") Long operatorId);

    /**
     * 通过操作员ID和角色ID查找操作员与角色对应关系
     * 
     * @param operatorId
     *            ,
     * @param roleId
     * @return
     */
    OperatorRoleMap getOperatorRoleMapByRoleId(@Param("operatorId") String operatorId, @Param("roleId") String roleId);

    /**
     * 通过操作员ID删除操作员与角色对应关系
     * 
     * @param operatorId
     * @return
     */
    int deleteOperatorRoleMapByOperatorId(@Param("operatorId") Long operatorId);

}