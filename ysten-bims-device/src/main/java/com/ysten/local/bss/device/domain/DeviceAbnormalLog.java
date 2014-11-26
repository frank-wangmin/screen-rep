package com.ysten.local.bss.device.domain;


import java.io.Serializable;
import java.util.Date;


public class DeviceAbnormalLog implements Serializable{

    private static final long serialVersionUID = -6196262846114960121L;

    private Long id;
    private Long originalDeviceId;
    private String deviceCode;
    private String ystenId;
    private String mac;
    private String status;
    private String originalDataStatus;
    private String operation;
    private Date createDate;
    private String serviceName;
    private String message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOriginalDeviceId() {
        return originalDeviceId;
    }

    public void setOriginalDeviceId(Long originalDeviceId) {
        this.originalDeviceId = originalDeviceId;
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

    public void setYstenId(String ystenId) {
        this.ystenId = ystenId;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOriginalDataStatus() {
        return originalDataStatus;
    }

    public void setOriginalDataStatus(String originalDataStatus) {
        this.originalDataStatus = originalDataStatus;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
