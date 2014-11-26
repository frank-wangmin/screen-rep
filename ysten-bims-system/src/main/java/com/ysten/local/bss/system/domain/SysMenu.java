package com.ysten.local.bss.system.domain;

import java.io.Serializable;

/**
 *    
 * 项目名称：yst-oms
 * 类名称：     SysMenu   
 * 类描述：    系统菜单
 * 创建人：     jiangzhengyi   
 * 创建时间：2011-08-09   
*/
public class SysMenu implements Serializable {
	
	private static final long serialVersionUID = -7059331243549997464L;
	
    /**
     * 主键
     */
    private Long id;

    /**
     * 权限ID
     */
    private Long authorityId;

    /**
     * 名称
     */
    private String name;

    /**
     * 路径
     */
    private String url;

    /**
     * 父ID
     */
    private Long parentId;

   /**
    * 描述
    */
    private String description;
    /**
     * 导航排序
     */
    private Long orderSq;
    
    public Long getOrderSq() {
        return orderSq;
    }

    public void setOrderSq(Long orderSq) {
        this.orderSq = orderSq;
    }

    public SysMenu()
    {}
    
    public SysMenu(Long id,Long authorityId,String name,String url,String description,Long orderSq)
    {
    	this.id = id;
    	this.authorityId = authorityId;
    	this.name = name;
    	this.url = url;
    	this.description = description;
    	this.orderSq = orderSq;
    }
    public Long getId() {
        return id;
    }

    /**
     * 主键
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取权限ID
     * @return
     */
    public Long getAuthorityId() {
        return authorityId;
    }

    /**
     * 设置权限ID
     * @param authorityId
     */
    public void setAuthorityId(Long authorityId) {
        this.authorityId = authorityId;
    }

    /**
     * 获取名称
     * @return
     */
    public String getName() {
        return name;
    }

   /**
    * 设置名称
    * @param name
    */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取路径
     * @return
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置路径
     * @param url
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

   /**
    * 获取父ID
    * @return
    */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 设置父ID
     * @param parentId
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}