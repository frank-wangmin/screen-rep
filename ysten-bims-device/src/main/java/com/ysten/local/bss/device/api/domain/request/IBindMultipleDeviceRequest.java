package com.ysten.local.bss.device.api.domain.request;

import java.util.List;

public interface IBindMultipleDeviceRequest extends IRequest {
	public List<MultipleBindDeviceSno> getSnos();
}
