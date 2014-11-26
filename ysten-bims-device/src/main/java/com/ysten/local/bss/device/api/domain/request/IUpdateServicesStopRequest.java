package com.ysten.local.bss.device.api.domain.request;

public interface IUpdateServicesStopRequest extends IRequest {
	/**
	 * 返回终端序列号
	 * @return
	 */
	public String getSno();
	public String getServiceStop();
    public String getUserId();
    public String getType();
}
