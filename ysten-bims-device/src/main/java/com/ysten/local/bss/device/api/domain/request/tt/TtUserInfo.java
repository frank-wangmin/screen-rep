package com.ysten.local.bss.device.api.domain.request.tt;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ysten.local.bss.device.domain.Customer;
import com.ysten.local.bss.device.domain.DeviceCustomerAccountMap;


public class TtUserInfo {
	private String userId = "NULL";
	private String userGroupNMB = "01";
	private String stbId = "NULL";
	private String cpId = "NULL";
	private String area = "NULL";
	private String city = "NULL";
	private String userType = "02";
	private String services = "NULL";
	private String timeStamp = "NULL";
	public TtUserInfo(Customer customer, String services) throws ParseException{
	    this(customer, null, services);
	}
	public TtUserInfo(Customer customer, DeviceCustomerAccountMap map, String services) throws ParseException{
	    this(customer.getUserId(), 
	            map.getDeviceCode(), 
	            customer.getArea().getId().toString(), 
	            customer.getRegion().getId().toString(),
	            services
	            );
	}
	public TtUserInfo(String userId, String stbId, String area, String city, String services) throws ParseException{
		this(userId, "01", stbId, "", area, city, "02", services, new Date());
	}
	public TtUserInfo(String userId, String userGroupNMB, String stbId, String cpId, String area, String city,
			String userType, String services, Date createDate) throws ParseException{
		this.userId = userId;
		this.userGroupNMB = userGroupNMB;
		this.stbId = stbId;
		this.cpId = cpId;
		this.area = area;
		this.city = city;
		this.userType = userType;
		this.services = services;
		Date oldDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1970-1-1 00:00:00");
		this.timeStamp = String.valueOf(createDate.getTime() - oldDate.getTime());
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserGroupNMB() {
		return userGroupNMB;
	}
	public void setUserGroupNMB(String userGroupNMB) {
		this.userGroupNMB = userGroupNMB;
	}
	public String getStbId() {
		return stbId;
	}
	public void setStbId(String stbId) {
		this.stbId = stbId;
	}
	public String getCpId() {
		return cpId;
	}
	public void setCpId(String cpId) {
		this.cpId = cpId;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getServices() {
		return services;
	}
	public void setServices(String services) {
		this.services = services;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	@Override
	public String toString(){
		return userId + "|"
			 + userGroupNMB + "|"
			 + stbId + "|"
			 + cpId + "|"
			 + area + "|"
			 + city + "|"
			 + userType + "|"
			 + services + "|"
			 + timeStamp;
	}
}
