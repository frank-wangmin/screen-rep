package com.ysten.local.bss.notice.domain;

import java.util.Date;

import com.google.gson.annotations.Expose;

/**
 * 系统通知
 * 
 * @author chengy
 * 
 */
public class SysNotice implements java.io.Serializable {
	private static final long serialVersionUID = 8544668206292492263L;

	public enum NoticeType {
		SYSTEM, CUSTOM
	};
	/**
	 * 通知id
	 */
	@Expose(serialize = false)
	private Long id;

	/**
	 * 标题
	 */
	@Expose(serialize = false)
	private String title;

	/**
	 * 内容
	 */
	@Expose
	private String content;
	/**
	 * 开始时间
	 */
	@Expose(serialize = false)
	private Date startDate;
	/**
	 * 结束时间
	 */
	@Expose(serialize = false)
	private Date endDate;
	/**
	 * 是否默认
	 */
	@Expose(serialize = false)
	private Integer isDefault;
	/**
	 * 操作人
	 */
	@Expose(serialize = false)
	private String operateUser;
	/**
	 * 创建时间
	 */
	@Expose(serialize = false)
	private Date createDate;
	/**
	 * 状态
	 */
	@Expose(serialize = false)
	private Integer status;
	/**
	 * 最后更新时间
	 */
	@Expose(serialize = false)
	private Date updateDate;

	@Expose(serialize = false)
	private String deviceGroupIds;

	@Expose(serialize = false)
	private String deviceCodes;

	@Expose(serialize = false)
	private String userGroupIds;

	@Expose(serialize = false)
	private String userIds;

	private String type;

	private String districtCode;

	public String getDeviceGroupIds() {
		return deviceGroupIds;
	}

	public void setDeviceGroupIds(String deviceGroupIds) {
		this.deviceGroupIds = deviceGroupIds;
	}

	public String getDeviceCodes() {
		return deviceCodes;
	}

	public void setDeviceCodes(String deviceCodes) {
		this.deviceCodes = deviceCodes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

	public String getOperateUser() {
		return operateUser;
	}

	public void setOperateUser(String operateUser) {
		this.operateUser = operateUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUserGroupIds() {
		return userGroupIds;
	}

	public void setUserGroupIds(String userGroupIds) {
		this.userGroupIds = userGroupIds;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

}