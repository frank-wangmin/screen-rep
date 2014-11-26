package com.ysten.local.bss.device.service.impl;

import org.apache.commons.lang.StringUtils;

import com.ysten.local.bss.device.api.domain.request.DefaultBindDeviceRequest;
import com.ysten.local.bss.device.api.domain.request.IBindDeviceRequest;
import com.ysten.local.bss.device.api.domain.response.DefaultResponse;
import com.ysten.local.bss.device.api.domain.response.IResponse;
import com.ysten.local.bss.device.service.IJxydDeviceService;

public class JxydDeviceServiceImpl extends DefaultDeviceNewServiceImpl implements IJxydDeviceService {
	@Override
	public IResponse checkBindDeviceParam(IBindDeviceRequest request) throws Exception{
		DefaultBindDeviceRequest _request = (DefaultBindDeviceRequest)request;
		IResponse response = super.checkBindDeviceParam(request);
		if(response != null){
			return response;
		}
		if(StringUtils.isBlank(_request.getUserId())){
			return new DefaultResponse(false, "device bind failed because userId is null!");
		}
		return null;
	}

	@Override
	public void updateServiceStop() {
		// TODO Auto-generated method stub
		
	}
}
