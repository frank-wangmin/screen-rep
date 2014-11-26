package com.ysten.local.bss.device.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 设备软件号未找到异常
 * @author hxy
 *
 */
public class DeviceSoftwareCodeNotFoundException extends Throwable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 7061125603079484437L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceSoftwareCodeNotFoundException.class);
    
    public DeviceSoftwareCodeNotFoundException(String message) {
        LOGGER.error(message);
    }
}
