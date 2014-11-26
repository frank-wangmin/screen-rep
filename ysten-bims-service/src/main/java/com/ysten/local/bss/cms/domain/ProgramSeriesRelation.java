package com.ysten.local.bss.cms.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 节目与节目集关系表
 * 
 * 
 */
public class ProgramSeriesRelation implements Serializable {

	private static final long serialVersionUID = -1220796956843118728L;
	/**
	 * 主键
	 */
	private Long relId;
	/**
	 * 节目集ID
	 */
	private Long programSeriesId;
	/**
	 * 节目ID
	 */
	private Long programId;
	/**
	 * 关联时间
	 */
	private Date relTime;
	/**
	 * 关联人
	 */
	private String relUser;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 更新人
	 */
	private String updateUser;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 排序字段，级别
	 */
	private Integer taxis;
	/**
	 * 排序字段，时间
	 */
	private Date lastModifyDate;
	
	public Long getRelId() {
		return relId;
	}

	public void setRelId(Long relId) {
		this.relId = relId;
	}

	public Long getProgramSeriesId() {
		return programSeriesId;
	}

	public void setProgramSeriesId(Long programSeriesId) {
		this.programSeriesId = programSeriesId;
	}

	public Long getProgramId() {
		return programId;
	}

	public void setProgramId(Long programId) {
		this.programId = programId;
	}

	public Date getRelTime() {
		if (relTime == null) {
			return null;
		}
		return (Date)relTime.clone();
	}

	public void setRelTime(Date relTime) {
		if (relTime == null) {
			this.relTime = null;
		} else {
			this.relTime = (Date) relTime.clone();
		}
	}

	public String getRelUser() {
		return relUser;
	}

	public void setRelUser(String relUser) {
		this.relUser = relUser;
	}

	public Date getUpdateTime() {
		if (updateTime == null) {
			return null;
		}
		return (Date)updateTime.clone();
	}

	public void setUpdateTime(Date updateTime) {
		if (updateTime == null) {
			this.updateTime = null;
		} else {
			this.updateTime = (Date) updateTime.clone();
		}
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getTaxis() {
		return taxis;
	}

	public void setTaxis(Integer taxis) {
		this.taxis = taxis;
	}

	public Date getLastModifyDate() {
		return lastModifyDate;
	}

	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}

}