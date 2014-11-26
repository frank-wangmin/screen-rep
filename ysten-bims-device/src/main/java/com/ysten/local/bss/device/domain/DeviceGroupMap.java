package com.ysten.local.bss.device.domain;

import java.util.Date;

public class DeviceGroupMap implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3494922715568602146L;
	
	private Long id;
	private String deviceCode;
	private String ystenId;
	private Long deviceGroupId;
	private Date createDate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDeviceCode() {
		return deviceCode;
	}
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	public Long getDeviceGroupId() {
		return deviceGroupId;
	}
	public void setDeviceGroupId(Long deviceGroupId) {
		this.deviceGroupId = deviceGroupId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getYstenId() {
		return ystenId;
	}
	public void setYstenId(String ystenId) {
		this.ystenId = ystenId;
	}
	
}
