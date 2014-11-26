package com.ysten.local.bss.device.domain;

import java.util.Date;

import com.ysten.local.bss.device.domain.Customer.State;

public class CustomerGroup implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4037942813353993607L;

	private Long id;;
	private String groupId;	
	private String groupName;
	private String linkName;
	private String linkTel;
	private State state = State.NORMAL;;
	private Date createDate;
	private Date updateDate;
	private String additionalInfo;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	public String getLinkTel() {
		return linkTel;
	}
	public void setLinkTel(String linkTel) {
		this.linkTel = linkTel;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getAdditionalInfo() {
		return additionalInfo;
	}
	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	@Override
	public String toString() {
		if(null == this) {
			return "null";
		}
		return "groupId[" + this.groupId + "] groupName[" + this.groupName + "] linkName[" + this.linkName + "] linkTel[" + 
			this.linkTel + "] state[" + this.state + "]";		
	}
	
	
	
}
