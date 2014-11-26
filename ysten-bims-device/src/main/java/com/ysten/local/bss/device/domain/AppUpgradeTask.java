package com.ysten.local.bss.device.domain;

import java.io.Serializable;
import java.util.Date;

import com.ysten.utils.bean.IEnumDisplay;

/**
 * 应用升级任务
 * 
 * @author 
 */
public class AppUpgradeTask implements Serializable {

	private static final long serialVersionUID = 3624722738664069957L;
	// 主键id
	private Long id;
	// 应用升级id
	private AppSoftwareCode softCodeId;
	// 软件包id
	private AppSoftwarePackage softwarePackageId;
	private String vendorIds;
	// 升级版本号
	private Integer versionSeq;
	// 创建时间
	private Date createDate;
	//
	private Date startDate;
	//
	private Date endDate;
    private Integer isAll; //是否全量升级
	private Integer timeInterval;
	private Integer maxNum;
	private Date lastModifyTime;
	private String operUser;
	// 设备编号
	private String deviceCode;
	// 设备分组编号
	private String deviceGroupIds;
	 //页面展示用户分组
    private String userGroupIds;
    //页面展示用户外部编号
    private String userIds;
	
	public AppUpgradeTask() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public AppSoftwareCode getSoftCodeId() {
		return softCodeId;
	}

	public void setSoftCodeId(AppSoftwareCode softCodeId) {
		this.softCodeId = softCodeId;
	}

	public AppSoftwarePackage getSoftwarePackageId() {
		return softwarePackageId;
	}

	public void setSoftwarePackageId(AppSoftwarePackage softwarePackageId) {
		this.softwarePackageId = softwarePackageId;
	}

	public Integer getVersionSeq() {
		return versionSeq;
	}

	public void setVersionSeq(Integer versionSeq) {
		this.versionSeq = versionSeq;
	}

	public Integer getIsAll() {
		return isAll;
	}

	public void setIsAll(Integer isAll) {
		this.isAll = isAll;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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

	public String getVendorIds() {
		return vendorIds;
	}

	public void setVendorIds(String vendorIds) {
		this.vendorIds = vendorIds;
	}

	public Integer getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(Integer maxNum) {
		this.maxNum = maxNum;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public String getOperUser() {
		return operUser;
	}

	public void setOperUser(String operUser) {
		this.operUser = operUser;
	}

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public String getDeviceGroupIds() {
		return deviceGroupIds;
	}

	public void setDeviceGroupIds(String deviceGroupIds) {
		this.deviceGroupIds = deviceGroupIds;
	}

	public Integer getTimeInterval() {
		return timeInterval;
	}

	public void setTimeInterval(Integer timeInterval) {
		this.timeInterval = timeInterval;
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

	public enum Status implements IEnumDisplay {
		USABLE("可用"), UNUSABLE("不可用");
		private String msg;

		private Status(String msg) {
			this.msg = msg;
		}

		@Override
		public String getDisplayName() {
			return this.msg;
		}

	}

}