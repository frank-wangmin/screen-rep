package com.ysten.local.bss.device.exception;

import com.ysten.core.AppErrorCodeException;


/**
 * 账号未找到异常
 * @fileName AccountNotFoundException.java
 */
public class AccountNotFoundException extends AppErrorCodeException{

    private static final long serialVersionUID = 830566541101266402L;
    private static final int ERROR_CODE = 2;
    
    public AccountNotFoundException() {
        super(ERROR_CODE);
    }
    
    public AccountNotFoundException(String msg) {
        super(msg);
        setErrorCode(ERROR_CODE);
    }

}
