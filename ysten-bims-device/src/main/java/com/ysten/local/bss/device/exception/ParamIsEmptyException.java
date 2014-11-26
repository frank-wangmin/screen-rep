package com.ysten.local.bss.device.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ysten.core.AppErrorCodeException;

/**
 * 参数为空异常
 * @author hxy
 *
 */
public class ParamIsEmptyException extends AppErrorCodeException{
	
	private static final long serialVersionUID = -2932151579119383140L;
    private static final int ERROR_CODE = 1;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ParamIsEmptyException.class);
    
    public ParamIsEmptyException() {
        super(ERROR_CODE);
    }
    public ParamIsEmptyException(String message) {
    	LOGGER.error(message);
        setErrorCode(ERROR_CODE);
    }

}
