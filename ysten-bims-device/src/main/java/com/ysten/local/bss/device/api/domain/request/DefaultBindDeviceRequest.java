package com.ysten.local.bss.device.api.domain.request;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DefaultBindDeviceRequest implements IBindDeviceRequest{
	private String systemCode;
	private String sno;
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
	@Override
	public String getDevice() {
		return sno;
	}
	public String getSno() {
		return sno;
	}
	public void setSno(String sno) {
		this.sno = sno;
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
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("systemCode="+systemCode+",")
			.append("sno="+sno+",")
			.append("userName="+userName+",")
			.append("phone="+phone+",")
			.append("startDate="+startDate+",")
			.append("endDate="+endDate+",")
			.append("region="+region+",")
			.append("county="+county+",")
			.append("address="+address+",")
			.append("rate="+rate+",")
			.append("userId="+userId+",")
			.append("isGroup="+isGroup);
		return sb.toString();
	}
	
	/*public static void main(String[] args) {
		String json = "{\"systemCode\":\"ICNTV-BOSS\", \"sno\":\"5M038912120017135\", \"userName\":\"李白\",\"phone\":\"13131313131\",\"startDate\":\"20130107122233\", \"endDate\":\"20130107122233\",\"region\":\"123456\",\"county\":\"22\", \"address\":\"甘肃天水秦安县\", \"rate\": \"10\", \"userId\": \"010445576\"}";
		Gson gson = new GsonBuilder().create();
		DefaultBindDeviceRequest request = gson.fromJson(json, DefaultBindDeviceRequest.class);
		System.out.println(request.getSno());
	}*/
}
