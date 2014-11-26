package com.ysten.local.bss.device.domain;

import java.util.Date;

public class ApkUpgradeTask implements java.io.Serializable{
	private static final long serialVersionUID = -3469576116134709361L;

	private Long id;

    private ApkSoftwareCode softCodeId;

    private ApkSoftwarePackage softwarePackageId;
    
    private String taskName;

    private Date createDate;

    private String vendorIds;

    private Integer versionSeq;

    private Integer maxNum;

    private Integer timeInterval;

    private Integer isAll;

    private Date lastModifyTime;

    private String operUser;

    private Date startDate;

    private Date endDate;

    private String deviceGroupIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ApkSoftwareCode getSoftCodeId() {
		return softCodeId;
	}

	public void setSoftCodeId(ApkSoftwareCode softCodeId) {
		this.softCodeId = softCodeId;
	}

	public ApkSoftwarePackage getSoftwarePackageId() {
		return softwarePackageId;
	}

	public void setSoftwarePackageId(ApkSoftwarePackage softwarePackageId) {
		this.softwarePackageId = softwarePackageId;
	}

    public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Date getCreateDate() { return createDate; }

    public void setCreateDate(Date createDate) { this.createDate = createDate; }

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

    public String getDeviceGroupIds() {return deviceGroupIds; }

    public void setDeviceGroupIds(String deviceGroupIds) { this.deviceGroupIds = deviceGroupIds;}
}