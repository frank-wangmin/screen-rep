package com.ysten.local.bss.system.domain;

import java.io.Serializable;

/**
 *    
 * 项目名称：yst-oms
 * 类名称：     OperatorRoleMap   
 * 类描述：     操作员和角色关系
 * 创建人：     jiangzhengyi   
 * 创建时间：2011-08-09   
*/
public class OperatorRoleMap implements Serializable{
    private static final long serialVersionUID = 8033217372679134641L;


    /**
     * 主键
     */
    private Long id;

    /**
     * 操作员
     */
    private Operator operator;

    /**
     * 角色
     */
    private Role role;

    /**
     * 获取主键
     * @return
     *          主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取操作员
     * @return
     *          操作员
     */
    public Operator getOperator() {
        return operator;
    }

    /**
     * 设置操作员
     * @param operator
     *          操作员
     */
    public void setOperator(Operator operator) {
        this.operator = operator;
    }
    
    /**
     * 获取角色
     * @return
     *          角色
     */
    public Role getRole() {
        return role;
    }

    /**
     * 设置角色
     * @param role
     *          角色
     */
    public void setRole(Role role) {
        this.role = role;
    }
}