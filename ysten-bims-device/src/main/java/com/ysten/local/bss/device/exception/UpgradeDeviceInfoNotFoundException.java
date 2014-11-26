package com.ysten.local.bss.device.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ysten.core.AppErrorCodeException;

/**
 * 设备升级信息未找到异常
 * @author hxy
 *
 */
public class UpgradeDeviceInfoNotFoundException extends AppErrorCodeException {
    private static final long serialVersionUID = -2932151579119383140L;
    private static final int ERROR_CODE = 4;
    private static final Logger LOGGER = LoggerFactory.getLogger(UpgradeDeviceInfoNotFoundException.class);
    
    public UpgradeDeviceInfoNotFoundException() {
        super(ERROR_CODE);
    }
    public UpgradeDeviceInfoNotFoundException(String message) {
    	LOGGER.error(message);
        setErrorCode(ERROR_CODE);
    }
}
