package com.ysten.local.bss.device.api.domain.request.tt;

import com.ysten.local.bss.device.api.domain.request.DefaultBindMultipleDeviceRequest;

public class TtBindMultipleDeviceRequest extends DefaultBindMultipleDeviceRequest {
	/**
	 * 套餐标识 （企业代码）
	 */
	private String serviceId;
	/**
	 * 套餐名称
	 */
	private String serviceName;
	/**
	 * 订购时间
	 */
	private String orderDate;
	/**
	 * 订购类型: 0:按次订购  1:包月订购 2:包年订购
	 */
	private String orderType;
	/**
	 * 费用（分）
	 */
	private String free;
	/**
	 * 订购生效时间（格式YYYYMMDDHHMMSS）
	 */
	private String validDate;
	/**
	 * 订购失效时间（格式YYYYMMDDHHMMSS）
	 */
	private String expireDate;
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getFree() {
		return free;
	}
	public void setFree(String free) {
		this.free = free;
	}
	public String getValidDate() {
		return validDate;
	}
	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}
	public String getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("serviceId="+serviceId+"|")
			.append("serviceName="+serviceName+"|")
			.append("orderDate="+orderDate+"|")
			.append("orderType="+orderType+"|")
			.append("free="+free+"|")
			.append("validDate="+validDate+"|")
			.append("expireDate="+expireDate+"|");
		return sb.toString();
	}
}
