package com.ysten.local.bss.device.domain;

import java.util.Date;

public class PanelPackageDeviceMap {
    private Long id;
    private Long panelPackageId;
    private String ystenId;
    private Long deviceGroupId;
    private Date createDate;
    private String type;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getPanelPackageId() {
        return panelPackageId;
    }
    public void setPanelPackageId(Long panelPackageId) {
        this.panelPackageId = panelPackageId;
    }
    public String getYstenId() {
		return ystenId;
	}
	public void setYstenId(String ystenId) {
		this.ystenId = ystenId;
	}
	public Long getDeviceGroupId() {
        return deviceGroupId;
    }
    public void setDeviceGroupId(Long deviceGroupId) {
        this.deviceGroupId = deviceGroupId;
    }
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}