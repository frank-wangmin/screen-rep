package com.ysten.local.bss.device.domain;

public class ServiceCollectDeviceGroupMap implements java.io.Serializable {
	
    private static final long serialVersionUID = -7059331243549997464L;
    
    private Long id;
    private Long serviceCollectId;
    private Long deviceGroupId;
    private String ystenId;
	public Long getId() {
		return id;
	}
	public String getYstenId() {
		return ystenId;
	}
	public void setYstenId(String ystenId) {
		this.ystenId = ystenId;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getServiceCollectId() {
		return serviceCollectId;
	}
	public void setServiceCollectId(Long serviceCollectId) {
		this.serviceCollectId = serviceCollectId;
	}
	public Long getDeviceGroupId() {
		return deviceGroupId;
	}
	public void setDeviceGroupId(Long deviceGroupId) {
		this.deviceGroupId = deviceGroupId;
	}

}