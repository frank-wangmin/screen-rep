package com.ysten.local.bss.device.domain;

public class DeviceActiveStatisticsVo {
	private String cityName;
	private String deviceTypeName;
	private Integer rowTotal;
	private String flag;
    public String getCityName() {
        return cityName;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    public String getDeviceTypeName() {
        return deviceTypeName;
    }
    public void setDeviceTypeName(String deviceTypeName) {
        this.deviceTypeName = deviceTypeName;
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
