package com.ysten.local.bss.device.domain;

import com.ysten.local.bss.device.utils.EnumConstantsInterface;

import java.util.Date;

public class ApkUpgradeResultLog implements java.io.Serializable{
	private static final long serialVersionUID = -4474502514520670916L;

	private Long id;

    private String deviceCode;

    private String ystenId;
    
    private String distCode;

    private String softCode;

    private Integer deviceVersionSeq;

    private Integer versionSeq;

    private EnumConstantsInterface.UpgradeResultStatus status;
    
    private String resultStatus;

    private Long duration;

    private Date createDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getYstenId() {
        return ystenId;
    }

    public String getDistCode() {
		return distCode;
	}

	public void setDistCode(String distCode) {
		this.distCode = distCode;
	}

	public void setYstenId(String ystenId) {
        this.ystenId = ystenId;
    }

    public String getSoftCode() {
        return softCode;
    }

    public void setSoftCode(String softCode) {
        this.softCode = softCode;
    }

    public Integer getDeviceVersionSeq() {
        return deviceVersionSeq;
    }

    public void setDeviceVersionSeq(Integer deviceVersionSeq) {
        this.deviceVersionSeq = deviceVersionSeq;
    }

    public Integer getVersionSeq() {
        return versionSeq;
    }

    public void setVersionSeq(Integer versionSeq) {
        this.versionSeq = versionSeq;
    }

    public EnumConstantsInterface.UpgradeResultStatus getStatus() {return status; }

    public void setStatus(EnumConstantsInterface.UpgradeResultStatus status) { this.status = status;}

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

	public String getResultStatus() {
		return resultStatus;
	}

	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}
}