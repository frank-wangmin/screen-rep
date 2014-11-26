package com.ysten.local.bss.device.api.domain.request;

public interface IDisableDeviceRequest extends IRequest {
	public String getSno();
    public String getUserId();
    public String getType();
}
