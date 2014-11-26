package com.ysten.local.bss.system.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.system.domain.RoleAuthorityMap;

/**
 * 
 * @author xuyesheng
 * @date 2011-12.23
 * 
 */
public interface RoleAuthorityMapMapper extends BaseMapper<RoleAuthorityMap, Long> {
    /**
     * 获取角色所有的权限
     * 
     * @param roleId
     *            角色ID
     * @return
     */
    List<RoleAuthorityMap> findByRoleId(@Param("roleId") Long roleId);

    /**
     * 通过角色ID删除角色与权限关联关系
     * 
     * @param roleId
     * @return
     */
    int deleteRoleAuthorityMapByRoleId(@Param("roleId") Long roleId);

}