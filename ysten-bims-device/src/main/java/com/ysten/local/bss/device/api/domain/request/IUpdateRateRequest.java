package com.ysten.local.bss.device.api.domain.request;

public interface IUpdateRateRequest extends IRequest {
	public String getUserId();
	public String getRate();
	public String getType();
}
