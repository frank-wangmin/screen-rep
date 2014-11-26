package com.ysten.local.bss.device.api.domain.request;

public interface IPauseOrRecoverDeviceRequest extends IRequest {
	public String getSno();
	public String getAction();
	public String getUserId();
	public String getType();
}
