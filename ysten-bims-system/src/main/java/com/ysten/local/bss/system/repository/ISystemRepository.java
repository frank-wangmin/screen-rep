package com.ysten.local.bss.system.repository;

import java.util.List;

import com.ysten.local.bss.system.domain.*;
import com.ysten.utils.page.Pageable;
 
public interface ISystemRepository {
	
	/**
     * 新增系统操作员
     * @param operator
     */
    boolean addOperator(Operator operator);

    /**
     * 修改系统操作员
     * @param operator
     */
    boolean updateOperator(Operator operator);

    /**
     * 删除系统操作员
     * @param id
     */
    boolean deleteOperator(Long id);
    
    /**
     * 查询系统操作员信息(支持分页)
     * @param pageNo 起始页
     * @param pageSize 每页显示多少条
     * @return Pageable<Operator>
     */
    Pageable<Operator> findOperators(String operatorName,String loginName,int pageNo, int pageSize);

    Pageable<SysVersion> findSysVersions(String versionId, String versionName, Integer pageNo, Integer pageSize);
    
    /**
     * 通过ID查询操作员信息
     * @param id
     */
    Operator getOperatorById(Long id);

    /**
     * 通过登录名称查询操作员信息
     * @param loginName 登录名称
     */
    Operator getOperatorByLoginName(String loginName);
    
    /**
     * 新增角色信息
     * @param role
     */
    boolean addRole(Role role);
    
    /**
     * 修改角色信息
     * @param role
     */
    boolean updateRole(Role role);

    /**
     * 删除角色信息
     * @param id
     */
    boolean deleteRole(Long id);
    
    /**
     * 查询角色信息(支持分页)
     * @param roleName 
     * @param pageNo 起始页
     * @param pageSize 每页显示多少条
     * @return Pageable<Operator>
     */
    Pageable<Role> findRoles(String roleName, int pageNo, int pageSize);
    
    /**
     * 通过ID角色信息
     * @param id
     */
    Role getRoleById(Long id);
    
    /**
     * 新增权限信息
     * @param authority
     */
    boolean addAuthority(Authority authority);

    /**
     * 修改权限信息
     * @param authority
     */
    boolean updateAuthority(Authority authority);

    /**
     * 删除权限信息
     * @param id
     */
    boolean deleteAuthority(Long id);

    /**
     * 查询权限信息(支持分页)
     * @param pageNo   起始页
     * @param pageSize 每页显示多少条
     * @return Pageable<Authority>
     */
    Pageable<Authority> findAuthoritys(int pageNo, int pageSize);
    
    /**
     * 通过ID查询权限信息
     * @param id
     */
    Authority getAuthorityById(Long id);

    /**
     * 查询所有系统操作员
     */
    List<Operator> findAllOperator();

    /**
     * 查找操作人员所具有的权限
     * @param operatorId 操作员ID
     */
    List<OperatorRoleMap> findByOperatorId(Long operatorId);

    /**
     * 查询角色信息
     * @return List<Role>
     */
    List<Role> findAllRole();
    
    /**
     * 新增操作员角色信息
     * @param operatorRoleMap
     */
    boolean addOperatorRoleMap(OperatorRoleMap operatorRoleMap);

    /**
     * 通过操作员ID和角色ID查找操作员与角色对应关系
     * @param operatorId,
     * @param roleId
     */
    OperatorRoleMap getOperatorRoleMapByRoleId(String operatorId,String roleId);

    /**
     * 通过操作员ID删除操作员与角色对应关系
     * @param operatorId
     */
    boolean deleteOperatorRoleMapByOperatorId(Long operatorId);

    /**
     * 查询所有权限
     */
    List<Authority> findAllAuthority();

    /**
     * 获取角色所有的权限
     * @param roleId 角色ID
     */
    List<RoleAuthorityMap> findByRoleId(Long roleId);

    /**
     * 通过角色ID删除角色与权限关联关系
     * @param roleId
     */
    boolean deleteRoleAuthorityMapByRoleId(Long roleId);

    /**
     * 新增角色与权限关联关系
     * @param roleAuthorityMap
     */
    boolean addRoleAuthorityMap(RoleAuthorityMap roleAuthorityMap);

    /**
     * 查询所有权限操作菜单
     */
    List<SysMenu> findSysMenus();

    /**
     * 通过操作菜单ID和权限ID查询操作菜单
     * @param sysMenuId 操作菜单ID
     * @param authorityId 权限ID
     */
    SysMenu getSysMenuBySysIdAndAuthorityId(String sysMenuId,String authorityId);

    /**
     * 通过操作菜单ID查询操作菜单
     * @param sysMenuId 操作菜单ID
     */

    SysMenu getSysMenuBySysMenuId(Long sysMenuId);

    /**
     * 更新操作菜单
     * @param sysMenu 
     */
    boolean updateSysMenu(SysMenu sysMenu);
    
    /**
     * 通过操作权限ID查询操作菜单
     * @param authorityId 操作菜单ID
     */
    SysMenu getSysMenuByAuthorityId(Long authorityId);

    /**
     * 通过父ID查询操作菜单
     * @param parentId 操作菜单ID
     * @param authorityId　权限ID
     */
    SysMenu getSysMenuByParentId(Long parentId,Long authorityId);

    /**
     * 查询操作方法(支持分页)
     * @param pageNo   起始页
     * @param pageSize 每页显示多少条
     * @return Pageable<SysMenu>
     */
    Pageable<SysMenu> findSysMenus(int pageNo, int pageSize);
    
    /**
     * 获取角色的权限信息
     * @param roleIds 角色Ids
     */
    List<Authority> findByRoleIds(List<Long> roleIds);

    /**
     * 获取OperatorID  
     */
    int getOperatorLastId();

    /**
     * 获取RoleID 
     */
    int getRoleLastId();

    /**
     * 查询所有子权限
     * @param condition
     * @return
     */
	List<Authority> findRelatedAuthorities(List<Authority> condition);
	/**
	 * 批量删除权限
	 * @param ids
	 * @return
	 */
	boolean deleteAuthorityByIds(List<Long> ids);
	/**
	 * 查询父节点下的一级子节点
	 * @param parentId
	 * @return
	 */
	List<Authority> findAuthoritiesByParentId(Long parentId);
    
}
