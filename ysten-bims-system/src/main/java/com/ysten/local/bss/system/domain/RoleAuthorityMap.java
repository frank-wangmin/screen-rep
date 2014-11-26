package com.ysten.local.bss.system.domain;

import java.io.Serializable;

/**
 *    
 * 项目名称：yst-oms
 * 类名称：     RoleAuthorityMap   
 * 类描述：    角色和权限关系
 * 创建人：     jiangzhengyi   
 * 创建时间：2011-08-09   
*/
public class RoleAuthorityMap implements Serializable{
    private static final long serialVersionUID = 1466293182042672105L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 角色
     */
    private Role role;

    /**
     * 权限
     */
    private Authority authority;

    /**
     * 获取主键
     * @return
     *          主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键
     * @param id
     *          主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取角色
     * @return
     *      角色
     */
    public Role getRole() {
        return role;
    }

    /**
     * 设置角色
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * 获取权限
     * @return
     *          权限
     */
    public Authority getAuthority() {
        return authority;
    }

    /**
     * 设置权限
     * @param authority 
     *          权限
     */
    public void setAuthority(Authority authority) {
        this.authority = authority;
    }
}