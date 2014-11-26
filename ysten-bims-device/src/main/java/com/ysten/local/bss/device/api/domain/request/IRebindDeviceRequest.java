package com.ysten.local.bss.device.api.domain.request;

public interface IRebindDeviceRequest extends IRequest {
	public String getOldSno();
	public String getNewSno();
	public String getPhone();
}
