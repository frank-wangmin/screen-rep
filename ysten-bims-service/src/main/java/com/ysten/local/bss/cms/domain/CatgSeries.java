package com.ysten.local.bss.cms.domain;

import java.util.Date;

public class CatgSeries {
	private Long relId;
	private Long catgItemId;
	private Long programSeriesId;
	private Date createTime;
	private String createUser;
	private Date updateTime;
	private String updateUser;
	private int status;        		//0：无效 1：有效
	private int taxis;
	private Date lastModifyDate;
	public Long getRelId() {
		return relId;
	}
	public void setRelId(Long relId) {
		this.relId = relId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getCatgItemId() {
		return catgItemId;
	}
	public void setCatgItemId(Long catgItemId) {
		this.catgItemId = catgItemId;
	}
	public Long getProgramSeriesId() {
		return programSeriesId;
	}
	public void setProgramSeriesId(Long programSeriesId) {
		this.programSeriesId = programSeriesId;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getTaxis() {
		return taxis;
	}
	public void setTaxis(int taxis) {
		this.taxis = taxis;
	}
	public Date getLastModifyDate() {
		return lastModifyDate;
	}
	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}
	
}
