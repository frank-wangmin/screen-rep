package com.ysten.local.bss.device.api.domain.request;

public class DefaultUpdateServicesStopRequest implements IUpdateServicesStopRequest {
	private String systemCode;
	private String phone;
	private String sno;
	private String serviceStop;
    private String userId;
    private String type="ONLY";
	public String getSystemCode() {
		return systemCode;
	}
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSno() {
		return sno;
	}
	public void setSno(String sno) {
		this.sno = sno;
	}
	public String getServiceStop() {
		return serviceStop;
	}
	public void setServiceStop(String serviceStop) {
		this.serviceStop = serviceStop;
	}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
