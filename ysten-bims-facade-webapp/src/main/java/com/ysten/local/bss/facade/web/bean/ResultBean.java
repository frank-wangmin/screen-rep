package com.ysten.local.bss.facade.web.bean;

public class ResultBean {
//	private String deviceCode;
	private String state;
	private boolean valid;
	private String customerCode;
	private String message;
//	public String getDeviceCode() {
//		return deviceCode;
//	}
//
//	public void setDeviceCode(String deviceCode) {
//		this.deviceCode = deviceCode;
//	}

	
	
	public String getState() {
		return state;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setState(String state) {
		this.state = state;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

}
