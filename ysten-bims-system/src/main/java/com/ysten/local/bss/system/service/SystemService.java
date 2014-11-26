package com.ysten.local.bss.system.service;

import java.util.List;

import com.ysten.local.bss.system.domain.*;
import com.ysten.local.bss.system.exception.SystemException;
import com.ysten.local.bss.system.vo.ResultInfo;
import com.ysten.local.bss.system.vo.Tree;
import com.ysten.utils.page.Pageable;

public interface SystemService {
	
	/**
     * 新增系统操作员
     */
    boolean addOperator(Operator operator);

    /**
     * 修改系统操作员
     */
    boolean updateOperator(Operator operator);
    
    /**
     * 删除系统操作员
     */
    boolean deleteOperator(Long id);
    
    /**
     * 查询系统操作员信息(支持分页)
     * @param pageNo    起始页
     * @param pageSize  每页显示多少条
     * @return Pageable<Operator>
     */
    Pageable<Operator> findOperators(String operatorName,String loginName,int pageNo, int pageSize);

    Pageable<SysVersion> findSysVersions(String versionId, String versionName, Integer pageNo,Integer pageSize);
    
    /**
     * 通过ID查询操作员信息
     */
    Operator getOperatorById(Long id);
    
    /**
     * 通过登录名称查询操作员信息
     * @param loginName 登录名称
     */
    Operator getOperatorByLoginName(String loginName);
    
    /**
     * 新增角色信息
     */
    boolean addRole(Role role);
    
    /**
     * 修改角色信息
     */
    boolean updateRole(Role role);

    /**
     * 删除角色信息
     */
    boolean deleteRole(Long id); 
    
    /**
     * 查询角色信息(支持分页)
     * @param roleName 
     * @param pageNo   起始页
     * @param pageSize 每页显示多少条
     * @return Pageable<Role>
     */
    Pageable<Role> findRoles(String roleName, int pageNo, int pageSize);
    
    /**
     * 查询角色信息
     */
    List<Role> findAllRole();
    
    /**
     * 通过ID角色信息
     */
    Role getRoleById(Long id);

    /**
     * 新增权限信息
     */
    boolean addAuthority(Authority authority);

    /**
     * 修改权限信息
     */
    boolean updateAuthority(Authority authority);
    
    /**
     * 级联删除权限信息
     */
    boolean deleteAuthority(Long id);

    /**
     * 查询权限信息(支持分页)
     * @param pageNo    起始页
     * @param pageSize  每页显示多少条
     * @return Pageable<Authority>
     */
    Pageable<Authority> findAuthoritys(int pageNo, int pageSize);
    
    /**
     * 通过ID查询权限信息
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
     * @param pageNo    起始页
     * @param pageSize  每页显示多少条
     * @return Pageable<SysMenu>
     */
    Pageable<SysMenu> findSysMenus(int pageNo, int pageSize);
    
    /**
     * 获取角色的权限信息
     * @param roleIds 角色Ids
     */
    List<Authority> findByRoleIds(List<Long> roleIds);

    /**
     * 获取Operator id
     */
    int getOperatorLastId();

    /**
     * 获取Role id
     */
    int getRoleLastId();
    /**
     * 获取权限树状菜单列表
     * @return
     */
    List<Tree> getAuthorityTree();
    /**
     * 操作员与角色关联
     * @param operatorId
     * @param roleIds
     * @return
     */
    Boolean addOperatorRoleMap(Long operatorId,List<Long> roleIds);
    /**
     * 角色与权限关联
     * @param operatorId
     * @param roleIds
     * @return
     * @throws SystemException 
     */
    ResultInfo assignAuthorityForRole(Long roleId,List<Long> authorityIds) throws SystemException;
    
    /**
     * 获取角色所有的权限叶子结点填充权限树
     * @param roleId 角色ID
     */
    List<RoleAuthorityMap> findAuthorityForDisplayTree(Long roleId);

}
