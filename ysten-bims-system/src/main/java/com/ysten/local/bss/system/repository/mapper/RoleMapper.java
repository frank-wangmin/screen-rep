/**
 * 
 * @author xuyesheng
 * @date 2011-12.23
 * 
 */package com.ysten.local.bss.system.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.system.domain.Role;

/**
 * 
 * @author xuyesheng
 * @date 2011-12.23
 * 
 */
public interface RoleMapper extends BaseMapper<Role, Long> {
    /**
     * 查询角色信息(支持分页)
     * @param roleName 
     * 
     * @param pageNo
     *            起始页
     * @param pageSize
     *            每页显示多少条
     * @return List <Operator>
     */
    List<Role> findRoles( @Param("roleName")String roleName, @Param("currentPage") int currentPage, @Param("pageSize") int pageSize);

    /**
     * 获取操作员的所有角色信息
     * 
     * @param operatorId
     *            操作员ID
     * @return
     */
    List<Role> findByOperatorId(@Param("operatorId") Long operatorId);

    /**
     * 获取操作员的ID
     * 
     * @return id
     */
    int getRoleLastId();

    /**
     * 根据名称模糊查询角色个数
     * @param roleName
     * @return
     */
	int getCountByCondition(@Param("roleName")String roleName);
}