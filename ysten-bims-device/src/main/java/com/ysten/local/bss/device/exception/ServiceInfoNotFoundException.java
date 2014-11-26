package com.ysten.local.bss.device.exception;

import com.ysten.core.AppErrorCodeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by frank on 14-5-7.
 */
public class ServiceInfoNotFoundException extends AppErrorCodeException {

    private static final long serialVersionUID = -2932151579119383140L;

    private static final int ERROR_CODE = 4;

    private static final Logger LOGGER = LoggerFactory.getLogger(UpgradeDeviceInfoNotFoundException.class);

    public ServiceInfoNotFoundException() {
        super(ERROR_CODE);
    }

    public ServiceInfoNotFoundException(String message) {
        LOGGER.error(message);
        setErrorCode(ERROR_CODE);
    }
}
