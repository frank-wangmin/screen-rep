package com.ysten.local.bss.device.domain;

import org.apache.commons.collections.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by Neil on 2014-05-06.
 */
public class BackgroundImage implements java.io.Serializable{

	private static final long serialVersionUID = 2878893707322770038L;

	private Long id;
    
    //名称
    private String name;

    private String url;

    private String blurUrl;

    private Date createDate;

    private Integer isDefault;
    
    private String state;
  
	private Integer loopTime;
    private Integer deviceLoopTime;
    private Integer groupLoopTime;
    private Integer userLoopTime;
    private Integer userGroupLoopTime;
    //页面展示设备分组
    private String deviceGroupIds;
    //页面展示设备编号
    private String deviceCodes;
    //页面展示用户分组
    private String userGroupIds;
    //页面展示用户外部编号
    private String userIds;
    private Date updateDate;
    //高清  MD5
//    private String md5Hd;//deleted by joyce on 2014-6-12
//    标清  MD5
//    private String md5Sd;
    
    public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

/*	deleted by joyce on 2014-6-12
public String getMd5Hd() {
		return md5Hd;
	}

	public void setMd5Hd(String md5Hd) {
		this.md5Hd = md5Hd;
	}

	public String getMd5Sd() {
		return md5Sd;
	}

	public void setMd5Sd(String md5Sd) {
		this.md5Sd = md5Sd;
	}*/

	public Integer getUserLoopTime() {
		return userLoopTime;
	}

	public void setUserLoopTime(Integer userLoopTime) {
		this.userLoopTime = userLoopTime;
	}

	public Integer getUserGroupLoopTime() {
		return userGroupLoopTime;
	}

	public void setUserGroupLoopTime(Integer userGroupLoopTime) {
		this.userGroupLoopTime = userGroupLoopTime;
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

	private String type;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBlurUrl() {
        return blurUrl;
    }

    public void setBlurUrl(String blurUrl) {
        this.blurUrl = blurUrl;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public Integer getLoopTime() {
        return loopTime;
    }

    public void setLoopTime(Integer loopTime) {
        this.loopTime = loopTime;
    }

    public Integer getDeviceLoopTime() {
		return deviceLoopTime;
	}

	public void setDeviceLoopTime(Integer deviceLoopTime) {
		this.deviceLoopTime = deviceLoopTime;
	}

	public Integer getGroupLoopTime() {
		return groupLoopTime;
	}

	public void setGroupLoopTime(Integer groupLoopTime) {
		this.groupLoopTime = groupLoopTime;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public static String toXML(List<BackgroundImage> list) {
        StringBuilder sb = new StringBuilder();

        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
        sb.append("<Service id=\"panelBackground\">");
        if (CollectionUtils.isEmpty(list)) {
            sb.append("<backgrounds time=\"0\">");
        } else {
            Integer loopTime = list.get(0).getLoopTime();
            sb.append("<backgrounds time=\"" + ((loopTime == null) ? 5000 : loopTime) + "\">");
            for (BackgroundImage backgroundImage : list) {
                sb.append("<url blur=\"" + backgroundImage.getBlurUrl() + "\">" + backgroundImage.getUrl() + "</url>");
            }
        }
        sb.append("</backgrounds>");
        sb.append("</Service>");

        return sb.toString();
    }
}
