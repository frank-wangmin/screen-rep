package com.ysten.local.bss.device.domain;

import java.util.List;

public class Bootstrap implements java.io.Serializable{

	private static final long serialVersionUID = 3741348022128008933L;
	
	private String resultcode;
	private String deviceId;
	private String mac;
	private String ystenId;
	private String token;
	private String screenId;
	private List<ServiceInfo> serviceInfos;
	
    public static String toXML(Bootstrap bootstrap) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?><services>"); 
        sb.append("<resultcode>"+((bootstrap.getResultcode() == null) ? "" : bootstrap.getResultcode())  +"</resultcode>");
        if(bootstrap != null){
            sb.append("<deviceId>"+((bootstrap.getDeviceId() == null) ? "" : bootstrap.getDeviceId())  +"</deviceId>");
            sb.append("<ystenId>"+((bootstrap.getYstenId() == null) ? "" : bootstrap.getYstenId())  +"</ystenId>");
            sb.append("<token>"+((bootstrap.getToken() == null) ? "" : bootstrap.getToken())  +"</token>");
            sb.append("<screenId>"+((bootstrap.getScreenId() == null) ? "" : bootstrap.getScreenId())  +"</screenId>");
            sb.append("<sysconfig>");    
            if(bootstrap.getServiceInfos() != null){
                for (ServiceInfo info : bootstrap.getServiceInfos()) {
                	if(null != info)
                	sb.append("<config key=\"" + info.getServiceName() + "\">" + info.getServiceUrl() + "</config>");
                }
            }
            sb.append("</sysconfig>"); 
        }
        else{//保留之前的返回格式
        	sb.append("<sysconfig></sysconfig>"); 
        }
        sb.append("</services>");

        return sb.toString();
    }
	
	public String getResultcode() {
		return resultcode;
	}
	public void setResultcode(String resultcode) {
		this.resultcode = resultcode;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getYstenId() {
		return ystenId;
	}
	public void setYstenId(String ystenId) {
		this.ystenId = ystenId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getScreenId() {
		return screenId;
	}
	public void setScreenId(String screenId) {
		this.screenId = screenId;
	}
	public List<ServiceInfo> getServiceInfos() {
		return serviceInfos;
	}
	public void setServiceInfos(List<ServiceInfo> serviceInfos) {
		this.serviceInfos = serviceInfos;
	}
	
}
