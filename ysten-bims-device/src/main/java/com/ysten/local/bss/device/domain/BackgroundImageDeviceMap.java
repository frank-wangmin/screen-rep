package com.ysten.local.bss.device.domain;

import java.util.Date;

/**
 * Created by Neil on 2014-05-06.
 */
public class BackgroundImageDeviceMap implements java.io.Serializable{

	private static final long serialVersionUID = -656407680563175615L;

	private Long id;

    private Long backgroundImageId;

    private String ystenId;
    
    private Long deviceGroupId;

    private Date createDate;
    
    private String type;
    private Integer loopTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBackgroundImageId() {
        return backgroundImageId;
    }

    public void setBackgroundImageId(Long backgroundImageId) {
        this.backgroundImageId = backgroundImageId;
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

	public Integer getLoopTime() {
		return loopTime;
	}

	public void setLoopTime(Integer loopTime) {
		this.loopTime = loopTime;
	}

	public String getYstenId() {
		return ystenId;
	}

	public void setYstenId(String ystenId) {
		this.ystenId = ystenId;
	}
}
