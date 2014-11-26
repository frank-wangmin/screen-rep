package com.ysten.local.bss.bean.tt;

import java.util.List;

import com.ysten.local.bss.device.domain.Customer;
import com.ysten.local.bss.device.domain.Device;
import com.ysten.local.bss.device.domain.DeviceCustomerAccountMap;

/**
 * 铁通省份同步数据到中心domain类<br/>
 * 包括用户、设备、订单信息
 * @author XuSemon
 * @since 2014-6-9
 */
public class TtSyncData {
	private Customer customer;
	private List<DeviceCustomerAccountMap> maps;
	private List<Device> devices;
	public TtSyncData(Customer customer, List<DeviceCustomerAccountMap> maps){
		this.customer = customer;
		this.maps = maps;
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
}
