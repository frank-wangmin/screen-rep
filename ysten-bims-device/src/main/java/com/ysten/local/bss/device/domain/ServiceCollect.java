package com.ysten.local.bss.device.domain;

import com.ysten.local.bss.device.utils.EnumConstantsInterface;

import java.util.Date;

/**
 * Created by frank on 14-5-6.
 */
public class ServiceCollect implements java.io.Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2970901091476325574L;

	private Long id;

    private Date createDate;

    private Date updateDate;
    
    private String description;

    private EnumConstantsInterface.ServiceCollectType serviceType;

  //页面展示设备分组
    private String deviceGroupIds;
    //页面展示易视腾编号
    private String ystenIds;
    
    public EnumConstantsInterface.ServiceCollectType getServiceType() {
        return serviceType;
    }

    public void setServiceType(EnumConstantsInterface.ServiceCollectType serviceType) {
        this.serviceType = serviceType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

    public String getDeviceGroupIds() {
        return deviceGroupIds;
    }

    public void setDeviceGroupIds(String deviceGroupIds) {
        this.deviceGroupIds = deviceGroupIds;
    }

    public String getYstenIds() {
        return ystenIds;
    }

    public void setYstenIds(String ystenIds) {
        this.ystenIds = ystenIds;
    }
	
}
