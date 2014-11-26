package com.ysten.local.bss.device.domain;

import java.util.Date;

public class UserGroupMap implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3494922715568602146L;
	
	private Long id;
	private String code;
	private Long userGroupId;
	private Date createDate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Long getUserGroupId() {
		return userGroupId;
	}
	public void setUserGroupId(Long userGroupId) {
		this.userGroupId = userGroupId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
