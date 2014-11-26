package com.ysten.local.bss.device.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by frank on 14-5-6.
 */
public class ServiceInfo implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4276911965521731793L;

	private Long id;

    private String serviceName;

    private String serviceType;

    private String serviceUrl;

    private String serviceIp;

    private Long serviceCollectId;

    private Date createDate;
    
    private Date updateDate;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getServiceIp() {
        return serviceIp;
    }

    public void setServiceIp(String serviceIp) {
        this.serviceIp = serviceIp;
    }

    public Long getServiceCollectId() {
        return serviceCollectId;
    }

    public void setServiceCollectId(Long serviceCollectId) {
        this.serviceCollectId = serviceCollectId;
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
}
