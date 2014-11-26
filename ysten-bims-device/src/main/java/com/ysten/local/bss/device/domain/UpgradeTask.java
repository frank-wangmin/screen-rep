package com.ysten.local.bss.device.domain;

import java.util.Date;

public class UpgradeTask implements java.io.Serializable {
	
	private static final long serialVersionUID = -6191944069668870182L;
	
	private Long id;
    private String taskName;
	private DeviceSoftwareCode softCodeId;
	private String deviceGroupIds;
	private String deviceCode;
	 //页面展示用户分组
    private String userGroupIds;
    //页面展示用户外部编号
    private String userIds;
	private DeviceSoftwarePackage softwarePackageId;
	private Date createDate;
	private String vendorIds;    
    private Integer versionSeq;
    private Integer maxNum;  //最大升级终端数
    private Integer timeInterval;  //间隔时间
    private Integer isAll; //是否全量升级
    private Date lastModifyTime;    
    private String operUser;
    private Date startDate;
    private Date endDate;
	
	public DeviceSoftwareCode getSoftCodeId() {
		return softCodeId;
	}
	public void setSoftCodeId(DeviceSoftwareCode softCodeId) {
		this.softCodeId = softCodeId;
	}
	public DeviceSoftwarePackage getSoftwarePackageId() {
		return softwarePackageId;
	}
	public void setSoftwarePackageId(DeviceSoftwarePackage softwarePackageId) {
		this.softwarePackageId = softwarePackageId;
	}
	public String getDeviceGroupIds() {
		return deviceGroupIds;
	}
	public void setDeviceGroupIds(String deviceGroupIds) {
		this.deviceGroupIds = deviceGroupIds;
	}

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDeviceCode() {
		return deviceCode;
	}
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
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
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getVendorIds() {
		return vendorIds;
	}
	public void setVendorIds(String vendorIds) {
		this.vendorIds = vendorIds;
	}
	public Integer getVersionSeq() {
		return versionSeq;
	}
	public void setVersionSeq(Integer versionSeq) {
		this.versionSeq = versionSeq;
	}
	public Integer getMaxNum() {
		return maxNum;
	}
	public void setMaxNum(Integer maxNum) {
		this.maxNum = maxNum;
	}
	public Integer getTimeInterval() {
		return timeInterval;
	}
	public void setTimeInterval(Integer timeInterval) {
		this.timeInterval = timeInterval;
	}
	public Integer getIsAll() {
		return isAll;
	}
	public void setIsAll(Integer isAll) {
		this.isAll = isAll;
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
}
