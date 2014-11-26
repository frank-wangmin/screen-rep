package com.ysten.local.bss.system.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.system.domain.SysMenu;

/**
 * 
 * @author xuyesheng
 * @date 2011-12.23
 * 
 */
public interface SysMenuMapper extends BaseMapper<SysMenu, Long> {
    /**
     * 通过操作菜单ID和权限ID查询操作菜单
     * 
     * @param sysMenuId
     *            操作菜单ID
     * @param authorityId
     *            权限ID
     * @return
     */
    SysMenu getSysMenuBySysIdAndAuthorityId(@Param("sysMenuId") String sysMenuId,
            @Param("authorityId") String authorityId);

    /**
     * 通过操作权限ID查询操作菜单
     * 
     * @param authorityId
     *            操作菜单ID
     * @return
     */
    SysMenu getSysMenuByAuthorityId(@Param("authorityId") Long authorityId);

    /**
     * 通过父ID查询操作菜单
     * 
     * @param parentId
     *            操作菜单ID
     * @return
     */
    SysMenu getSysMenuByParentId(@Param("parentId") Long parentId, @Param("authorityId") Long authorityId);

    /**
     * 查询操作方法(支持分页)
     * 
     * @param pageNo
     *            起始页
     * @param pageSize
     *            每页显示多少条
     * @return List<SysMenu>
     */
    List<SysMenu> findSysMenus(@Param("currentPage") int currentPage, @Param("pageSize") int pageSize);
}