package com.ysten.local.bss.device.api.domain.request;


public class DefaultDisableDeviceRequest implements IDisableDeviceRequest {
	private String systemCode;
	private String sno;
    private String userId;
    private String type="ONLY";
    private String phone;
	public DefaultDisableDeviceRequest(){}
	public DefaultDisableDeviceRequest(String systemCode, String sno, String userId, String phone, String type){
		this.systemCode = systemCode;
		this.sno = sno;
		this.userId = userId;
		this.phone = phone;
		this.type = type;
	}
	public String getSystemCode() {
		return systemCode;
	}
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}
	public String getSno() {
		return sno;
	}
	public void setSno(String sno) {
		this.sno = sno;
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
    public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("systemCode="+systemCode+",")
			.append("sno="+sno+",")
			.append("userId="+userId+",")
			.append("type="+type+",")
			.append("phone="+phone);
		return sb.toString();
	}
}
