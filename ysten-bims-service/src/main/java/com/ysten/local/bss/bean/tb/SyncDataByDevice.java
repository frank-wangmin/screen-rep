package com.ysten.local.bss.bean.tb;



import com.ysten.local.bss.device.domain.Customer;
import com.ysten.local.bss.device.domain.Device;
import com.ysten.local.bss.device.domain.DeviceCustomerAccountMap;


/**
 * @author chenxiang
 * @date 2014-8-15 下午5:04:56 
 */
public class SyncDataByDevice {

    private Long id;
    private String backUrl;
    private String type;
	private Customer customer;
	private DeviceCustomerAccountMap map;
	private Device device;
	public SyncDataByDevice(String type, Customer customer, DeviceCustomerAccountMap map,
			Device device) {
		super();
        this.type = type;
		this.customer = customer;
		this.map = map;
		this.device = device;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public DeviceCustomerAccountMap getMap() {
		return map;
	}
	public void setMap(DeviceCustomerAccountMap map) {
		this.map = map;
	}
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getBackUrl() {
        return backUrl;
    }
    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
