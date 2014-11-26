package com.ysten.local.bss.device.exception;

import com.ysten.core.AppErrorCodeException;

/**
 * 未找到设备
 * @author LI.T
 * @date 2011-4-30
 * @fileName DeviceNotFoundException.java
 */
public class DeviceNotFoundException extends AppErrorCodeException {
    
	private static final long serialVersionUID = 8792309551739461928L;
	private static final int ERROR_CODE = 4;
    
    public DeviceNotFoundException() {
        super(ERROR_CODE);
    }
    
    public DeviceNotFoundException(String msg){
        super(msg);
        setErrorCode(ERROR_CODE);
    }
}