package com.ysten.local.bss.device.domain;

import java.util.Date;


public class AnimationDeviceMap implements java.io.Serializable{
	private static final long serialVersionUID = 5300660828537299638L;
	private Long id;
    private Long bootAnimationId;
    private String ystenId;
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

	public Long getBootAnimationId() {
        return bootAnimationId;
    }

    public void setBootAnimationId(Long bootAnimationId) {
        this.bootAnimationId = bootAnimationId;
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