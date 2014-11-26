package com.ysten.local.bss.device.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 设备分组未找到异常
 * @author hxy
 *
 */
public class DeviceGroupNotFoundException extends Throwable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 7061125603079484437L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceGroupNotFoundException.class);
    
    public DeviceGroupNotFoundException(String message) {
        LOGGER.error(message);
    }
}
