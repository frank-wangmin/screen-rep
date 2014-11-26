package com.ysten.local.bss.device.domain;

import java.io.Serializable;
import java.util.Date;

import com.ysten.utils.bean.IEnumDisplay;

/**
 * 
 * @author 
 * 
 */
public class UserAppUpgradeMap implements Serializable {
	private static final long serialVersionUID = 2858541811447048759L;
	// 主键id
	private Long id;
	// 应用升级id
	private Long upgradeId;
	
	private String userId;
	
    private Long userGroupId;

	private Type type;
	
	private Date createDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUpgradeId() {
		return upgradeId;
	}

	public void setUpgradeId(Long upgradeId) {
		this.upgradeId = upgradeId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(Long userGroupId) {
		this.userGroupId = userGroupId;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 终端分组类型
	 * 
	 * @author 
	 * 
	 */
	public enum Type implements IEnumDisplay {
		GROUP("分组"), USER("终端");
		private String msg;

		private Type(String msg) {
			this.msg = msg;
		}

		@Override
		public String getDisplayName() {
			return this.msg;
		}
	}
}
