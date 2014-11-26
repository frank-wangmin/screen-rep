package com.ysten.local.bss.bean.tb;


import java.util.List;

import com.ysten.local.bss.device.domain.Customer;
import com.ysten.local.bss.device.domain.CustomerCust;
import com.ysten.local.bss.device.domain.CustomerGroup;
import com.ysten.local.bss.device.domain.Device;
import com.ysten.local.bss.device.domain.DeviceCustomerAccountMap;


/**
 * 同步数据到中心domain类<br/>
 * 包括用户、设备、订单信息
 * @author chenxiang
 * @date 2014-8-13 下午2:43:05 
 */
public class SyncData {
	private Customer customer;
	private List<DeviceCustomerAccountMap> maps;
	private List<Device> devices;
	
	private CustomerCust customerCust;
	private CustomerGroup customerGroup;
	public SyncData(Customer customer, List<DeviceCustomerAccountMap> maps){
		this.customer = customer;
		this.maps = maps;
	}
	
	
	public SyncData(Customer customer, List<DeviceCustomerAccountMap> maps,
			CustomerCust customerCust, CustomerGroup customerGroup) {
		this.customer = customer;
		this.maps = maps;
		this.customerCust = customerCust;
		this.customerGroup = customerGroup;
	}


	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public List<DeviceCustomerAccountMap> getMaps() {
		return maps;
	}
	public void setMaps(List<DeviceCustomerAccountMap> maps) {
		this.maps = maps;
	}
	public List<Device> getDevices() {
		return devices;
	}
	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}
	
	public CustomerCust getCustomerCust() {
		return customerCust;
	}


	public void setCustomerCust(CustomerCust customerCust) {
		this.customerCust = customerCust;
	}


	public CustomerGroup getCustomerGroup() {
		return customerGroup;
	}


	public void setCustomerGroup(CustomerGroup customerGroup) {
		this.customerGroup = customerGroup;
	}
	
}
