package com.ysten.local.bss.device.exception;

import com.ysten.core.AppErrorCodeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 参数为空异常
 * @author hxy
 *
 */
public class ParamIsInvalidException extends AppErrorCodeException{

    private static final long serialVersionUID = -7186251908843391892L;

    private static final int ERROR_CODE = 1;

    private static final Logger LOGGER = LoggerFactory.getLogger(ParamIsInvalidException.class);

    public ParamIsInvalidException() {
        super(ERROR_CODE);
    }
    public ParamIsInvalidException(String message) {
    	LOGGER.error(message);
        setErrorCode(ERROR_CODE);
    }

}
