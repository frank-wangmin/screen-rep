package com.ysten.local.bss.device.domain;

public class DeviceActiveStatistics {
	private Long cityId;
	private Long deviceTypeId;
	private Integer rowTotal;
	private String flag;
    public Long getCityId() {
        return cityId;
    }
    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }
    public Long getDeviceTypeId() {
        return deviceTypeId;
    }
    public void setDeviceTypeId(Long deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }
    public Integer getRowTotal() {
        return rowTotal;
    }
    public void setRowTotal(Integer rowTotal) {
        this.rowTotal = rowTotal;
    }
    public String getFlag() {
        return flag;
    }
    public void setFlag(String flag) {
        this.flag = flag;
    }
}
