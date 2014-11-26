package com.ysten.local.bss.device.remote.domain;

/**
 * 用户信息
 * 
 * @author xuyesheng
 * 
 */
public class RemoteUserActivateSum implements java.io.Serializable {

	private static final long serialVersionUID = -7830051843104833941L;

	private String date; // 日期

	private String provinceId; // 省份

	private String cityId; // 地市

	private String telecomId; // 运营商

	private String vendorId; // 厂商

	private String terminalId; // 终端类型

	private Long activateAll; // 总激活终端数

	private Long activateDay; // 当日激活终端数

	private Long userAll = 0l; // 总开户数

	private Long userDay = 0l; // 新增开户数

	private Long stbReturnDay = 0l; // 退订用户数

	private Long stbReceiveDay = 0l; // 到货终端数

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getTelecomId() {
		return telecomId;
	}

	public void setTelecomId(String telecomId) {
		this.telecomId = telecomId;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public Long getActivateAll() {
		return activateAll;
	}

	public void setActivateAll(Long activateAll) {
		this.activateAll = activateAll;
	}

	public Long getActivateDay() {
		return activateDay;
	}

	public void setActivateDay(Long activateDay) {
		this.activateDay = activateDay;
	}

	public Long getUserAll() {
		return userAll;
	}

	public void setUserAll(Long userAll) {
		this.userAll = userAll;
	}

	public Long getUserDay() {
		return userDay;
	}

	public void setUserDay(Long userDay) {
		this.userDay = userDay;
	}

	public Long getStbReturnDay() {
		return stbReturnDay;
	}

	public void setStbReturnDay(Long stbReturnDay) {
		this.stbReturnDay = stbReturnDay;
	}

	public Long getStbReceiveDay() {
		return stbReceiveDay;
	}

	public void setStbReceiveDay(Long stbReceiveDay) {
		this.stbReceiveDay = stbReceiveDay;
	}
}