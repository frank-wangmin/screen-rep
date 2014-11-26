package com.ysten.local.bss.device.api.domain.request;

public class DefaultRebindDeviceRequest implements IRebindDeviceRequest {
	private String systemCode;
	private String phone;
	private String oldSno;
	private String newSno;
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
	public String getOldSno() {
		return oldSno;
	}
	public void setOldSno(String oldSno) {
		this.oldSno = oldSno;
	}
	public String getNewSno() {
		return newSno;
	}
	public void setNewSno(String newSno) {
		this.newSno = newSno;
	}
}
