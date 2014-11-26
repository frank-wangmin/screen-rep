package com.ysten.local.bss.device.domain;

import java.util.Date;

import com.ysten.local.bss.device.utils.EnumConstantsInterface;

public class ApkSoftwareCode implements java.io.Serializable{
	private static final long serialVersionUID = -5311804149756272667L;
	private Long id;
    private String name;
    private String code;
    private EnumConstantsInterface.Status status;
    private Date createDate;
    private String description;
    private String operUser;
    private Date lastModifyTime;
    public ApkSoftwareCode(){
    	
    }
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
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public EnumConstantsInterface.Status getStatus() {
		return status;
	}
	public void setStatus(EnumConstantsInterface.Status status) {
		this.status = status;
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
    public String getOperUser() {
        return operUser;
    }
    public void setOperUser(String operUser) {
        this.operUser = operUser;
    }
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }
}