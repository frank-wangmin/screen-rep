package com.ysten.local.bss.device.domain;

import java.util.Date;

public class BootAnimation implements java.io.Serializable{

	private static final long serialVersionUID = -9119203625760164978L;
	//主键id
    private Long id;
    //名称
    private String name="";
    private String url="";
    private String md5="";
    private Date createDate;
    private String state="";
    private int isDefault;
    //页面展示设备分组
    private String deviceGroupIds;
    //页面展示设备编号
    private String deviceCodes;
    //页面展示用户分组
    private String userGroupIds;
    //页面展示用户外部编号
    private String userIds;
    private Date updateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    public static String toXML(BootAnimation bootAnimation) {
        StringBuilder sb = new StringBuilder();

        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
        sb.append("<Service id=\"bootAnimationInfo\">");
        sb.append("<state>" + ((bootAnimation == null) ? "" : bootAnimation.getState()) + "</state>");
        sb.append("<animation>");
        sb.append("<name>" + ((bootAnimation == null) ? "" : bootAnimation.getName()) + "</name>");
        sb.append("<url>" + ((bootAnimation == null) ? "" : bootAnimation.getUrl()) + "</url>");
        sb.append("<md5>" + ((bootAnimation == null) ? "" : bootAnimation.getMd5()) + "</md5>");
        sb.append("</animation>");
        sb.append("</Service>");

        return sb.toString();
    }

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
