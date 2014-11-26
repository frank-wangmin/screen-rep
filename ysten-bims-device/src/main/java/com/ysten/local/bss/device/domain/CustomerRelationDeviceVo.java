package com.ysten.local.bss.device.domain;

import java.util.Date;

import com.ysten.area.domain.Area;
import com.ysten.local.bss.device.domain.Customer.IdentityType;
import com.ysten.local.bss.device.domain.Customer.Sex;

public class CustomerRelationDeviceVo {
	private String deviceCode;
	private String ystenId;
    private String deviceSno;
	private String deviceMac;
	private DeviceVendor deviceVendor;
	private DeviceType deviceType;
	private Device.State deviceState;
	private Area deviceArea;
	private City deviceCity;
	private Date deviceCreateDate;
	private Date deviceActivateDate;
	private Date deviceExpireDate;
	private Date relationCreateDate;
	private String customerCode;
	private Long customerId;
	private String customerUserId;
	private String customerLoginName;
	private String customerRealName;
	private Area customerArea;
	private City customerCity;
	private String customerNickName;
	private IdentityType customerIdentityType;
	private Customer.State customerState;
	private Customer.CustomerType customerType;
	private String customerIdentityCode;
	private String customerPhone;
	private Sex customerSex;
	private String customerAddress;
	private String customerZipCode;
	private Long deviceId;
	private LockType deviceIsLock;
	private Long mapId;
	public String getYstenId() {
        return ystenId;
    }

    public void setYstenId(String ystenId) {
        this.ystenId = ystenId;
    }
	public Area getCustomerArea() {
		return customerArea;
	}

	public void setCustomerArea(Area customerArea) {
		this.customerArea = customerArea;
	}

	public City getCustomerCity() {
		return customerCity;
	}

	public void setCustomerCity(City customerCity) {
		this.customerCity = customerCity;
	}

	public DeviceType getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}

	public Area getDeviceArea() {
		return deviceArea;
	}

	public void setDeviceArea(Area deviceArea) {
		this.deviceArea = deviceArea;
	}

	public City getDeviceCity() {
		return deviceCity;
	}

	public void setDeviceCity(City deviceCity) {
		this.deviceCity = deviceCity;
	}

	public Date getDeviceActivateDate() {
		return deviceActivateDate;
	}

	public void setDeviceActivateDate(Date deviceActivateDate) {
		this.deviceActivateDate = deviceActivateDate;
	}

	public Date getDeviceExpireDate() {
		return deviceExpireDate;
	}

	public void setDeviceExpireDate(Date deviceExpireDate) {
		this.deviceExpireDate = deviceExpireDate;
	}

	public Date getRelationCreateDate() {
		return relationCreateDate;
	}

	public void setRelationCreateDate(Date relationCreateDate) {
		this.relationCreateDate = relationCreateDate;
	}

	public Long getMapId() {
		return mapId;
	}

	public void setMapId(Long mapId) {
		this.mapId = mapId;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerUserId() {
		return customerUserId;
	}

	public void setCustomerUserId(String customerUserId) {
		this.customerUserId = customerUserId;
	}

	public LockType getDeviceIsLock() {
		return deviceIsLock;
	}

	public void setDeviceIsLock(LockType deviceIsLock) {
		this.deviceIsLock = deviceIsLock;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerLoginName() {
		return customerLoginName;
	}

	public void setCustomerLoginName(String customerLoginName) {
		this.customerLoginName = customerLoginName;
	}

	public String getCustomerRealName() {
		return customerRealName;
	}

	public void setCustomerRealName(String customerRealName) {
		this.customerRealName = customerRealName;
	}

	public String getCustomerNickName() {
		return customerNickName;
	}

	public void setCustomerNickName(String customerNickName) {
		this.customerNickName = customerNickName;
	}

	public IdentityType getCustomerIdentityType() {
		return customerIdentityType;
	}

	public void setCustomerIdentityType(IdentityType customerIdentityType) {
		this.customerIdentityType = customerIdentityType;
	}

	public String getCustomerIdentityCode() {
		return customerIdentityCode;
	}

	public void setCustomerIdentityCode(String customerIdentityCode) {
		this.customerIdentityCode = customerIdentityCode;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public Sex getCustomerSex() {
		return customerSex;
	}

	public void setCustomerSex(Sex customerSex) {
		this.customerSex = customerSex;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getCustomerZipCode() {
		return customerZipCode;
	}

	public void setCustomerZipCode(String customerZipCode) {
		this.customerZipCode = customerZipCode;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public String getDeviceSno() {
		return deviceSno;
	}

	public void setDeviceSno(String deviceSno) {
		this.deviceSno = deviceSno;
	}

	public String getDeviceMac() {
		return deviceMac;
	}

	public void setDeviceMac(String deviceMac) {
		this.deviceMac = deviceMac;
	}

	public Date getDeviceCreateDate() {
		return deviceCreateDate;
	}

	public void setDeviceCreateDate(Date deviceCreateDate) {
		this.deviceCreateDate = deviceCreateDate;
	}

	public DeviceVendor getDeviceVendor() {
		return deviceVendor;
	}

	public void setDeviceVendor(DeviceVendor deviceVendor) {
		this.deviceVendor = deviceVendor;
	}

	public Device.State getDeviceState() {
		return deviceState;
	}

	public void setDeviceState(Device.State deviceState) {
		this.deviceState = deviceState;
	}

	public Customer.State getCustomerState() {
		return customerState;
	}

	public void setCustomerState(Customer.State customerState) {
		this.customerState = customerState;
	}

	public Customer.CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(Customer.CustomerType customerType) {
		this.customerType = customerType;
	}

}
