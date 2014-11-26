package com.ysten.local.bss.system.domain;

import java.io.Serializable;

/**
  *    
  * 项目名称：yst-oms
  * 类名称：     authority   
  * 类描述：     权限信息
  * 创建人：     jiangzhengyi   
  * 创建时间：2011-08-09   
 */
public class Authority implements Serializable {
	private static final long serialVersionUID = -7059331243549997464L;
   
    /**
     * 主键
     */
    private Long id;
    
    /**
     * 权限编号
     */
    private String code;
    
    /**
     * 权限名称
     */
    private String name;
    
    /**
     * 权限描述
     */
    private String description;
    /**
     * 父节点ID
     */
    private Long parentId;
    
    private Long sortOrder;
    
    public Authority()
    {}
    
    public Authority(Long id,String code,String name,String description,Long parentId,Long sortOrder)
    {
    	this.id = id;
    	this.code = code;
    	this.name = name;
    	this.description = description;
    	this.parentId = parentId;
    	this.sortOrder = sortOrder;
    }
    /**
     * 获取主键
     * @return
     * 		主键
     */
    public Long getId() {
        return id;
    }
    
    /**
     * 设置
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取权限编号
     * @return 
     * 		权限编号
     */
    public String getCode() {
        return code;
    }

   /**
    * 设置权限编号
    * @param code
    * 		权限编号
    */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }
    
    /**
     * 获取权限名称
     * @return
     */
    public String getName() {
        return name;
    }

   /**
    * 设置权限名称
    * @param name
    */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取权限描述
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置权限描述
     * @param description
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }


	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}


	public Long getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Long sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Authority [id=").append(id).append(", code=").append(code).append(", name=").append(name).append(", description=").append(description)
				.append(", parentId=").append(parentId).append(", sortOrder=").append(sortOrder).append("]");
		return builder.toString();
	}
}