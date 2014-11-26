package com.ysten.local.bss.device.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 终端升级任务
 * @author HanksXu
 *
 */
public class UserUpgradeMap implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3870491371661511524L;

	private long id;
    private String userId;
    private Long userGroupId;
	private long upgradeId;
	private String type;
	private Date createDate;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getUpgradeId() {
		return upgradeId;
	}
	public void setUpgradeId(long upgradeId) {
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	};
}
