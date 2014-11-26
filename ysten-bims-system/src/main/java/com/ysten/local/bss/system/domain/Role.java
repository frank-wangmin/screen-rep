package com.ysten.local.bss.system.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 项目名称：yst-oms 类名称： Role 类描述： 角色信息 创建人： jiangzhengyi 创建时间：2011-08-09
 */
public class Role implements Serializable {
	private static final long serialVersionUID = -7059331243549997464L;
	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 角色名称
	 */
	private String name;

	/**
	 * 角色描述
	 */
	private String description;
	/**
	 * 创建时间
	 */
	private Date createDate;

	public Role() {
	}


	/**
	 * 获取主键
	 * 
	 * @return
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 获取名称
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public Role(Long id, String name, String description, Date createDate) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.createDate = createDate;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Role [id=").append(id).append(", name=").append(name).append(", description=").append(description).append(", createDate=")
				.append(createDate).append("]");
		return builder.toString();
	}


	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}