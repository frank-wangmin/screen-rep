package com.ysten.local.bss.device.api.domain.request;

import java.util.List;

public class DefaultBindMultipleDeviceRequest implements IBindMultipleDeviceRequest {
	private String systemCode;
	private List<MultipleBindDeviceSno> snoList;
	private String userName;
	private String phone;
	private String startDate;
	private String endDate;
	private String region;
	private String county;
	private String address;
	private String rate;
	private String userId;
	private String isGroup;
	public String getSystemCode() {
		return systemCode;
	}
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}
	public List<MultipleBindDeviceSno> getSnoList() {
		return snoList;
	}
	public void setSnoList(List<MultipleBindDeviceSno> snoList) {
		this.snoList = snoList;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getIsGroup() {
		return isGroup;
	}
	public void setIsGroup(String isGroup) {
		this.isGroup = isGroup;
	}
	@Override
	public List<MultipleBindDeviceSno> getSnos() {
		return this.snoList;
	}
}
