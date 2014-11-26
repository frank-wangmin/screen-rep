package com.ysten.local.bss.device.exception;

import com.ysten.core.AppErrorCodeException;

/**
 * 用户未找到异常
 * @author LI.T
 * @date 2011-4-28
 * @fileName CustomerNotFoundExceprion.java
 */
public class CustomerNotFoundException extends AppErrorCodeException{
    
	private static final long serialVersionUID = 7205114727905616772L;
	private static final int ERROR_CODE = 2;
    public CustomerNotFoundException() {
        super(ERROR_CODE);
    }
    
    public CustomerNotFoundException(String msg){
        super(msg);
        setErrorCode(ERROR_CODE);
    }
}
