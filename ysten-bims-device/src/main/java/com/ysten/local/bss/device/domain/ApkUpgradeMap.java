package com.ysten.local.bss.device.domain;

import java.util.Date;

public class ApkUpgradeMap implements java.io.Serializable{
	private static final long serialVersionUID = -8000212707702392718L;
	private Long id;
    private String ystenId;
    private Long upgradeId;
    private Long deviceGroupId;
    private String type;
    private Date createDate;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getYstenId() {
		return ystenId;
	}
	public void setYstenId(String ystenId) {
		this.ystenId = ystenId;
	}
	public Long getUpgradeId() {
        return upgradeId;
    }
    public void setUpgradeId(Long upgradeId) {
        this.upgradeId = upgradeId;
    }
    public Long getDeviceGroupId() {
        return deviceGroupId;
    }
    public void setDeviceGroupId(Long deviceGroupId) {
        this.deviceGroupId = deviceGroupId;
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
    }
}