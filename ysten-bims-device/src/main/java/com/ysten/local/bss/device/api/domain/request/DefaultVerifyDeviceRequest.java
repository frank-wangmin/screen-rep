package com.ysten.local.bss.device.api.domain.request;

public class DefaultVerifyDeviceRequest implements IVerifyDeviceRequest {
	private String systemCode;
	private String sno;
	public DefaultVerifyDeviceRequest(){}
	public DefaultVerifyDeviceRequest(String systemCode, String sno){
		this.systemCode = systemCode;
		this.sno = sno;
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
	@Override
	public String toString(){
		return "systemCode="+systemCode+",sno="+sno;
	}
}
