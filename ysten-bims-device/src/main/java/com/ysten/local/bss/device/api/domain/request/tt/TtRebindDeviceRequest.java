package com.ysten.local.bss.device.api.domain.request.tt;

import com.ysten.local.bss.device.api.domain.request.DefaultRebindDeviceRequest;

public class TtRebindDeviceRequest extends DefaultRebindDeviceRequest {
	/**
	 * 用户标识
	 */
	private String userId;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
