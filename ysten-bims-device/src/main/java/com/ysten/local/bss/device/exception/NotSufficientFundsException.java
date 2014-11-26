package com.ysten.local.bss.device.exception;

import com.ysten.core.AppErrorCodeException;


/**
 * 余额不足
 *
 */
public class NotSufficientFundsException extends AppErrorCodeException {

    private static final long serialVersionUID = 1781056799762090931L;
    
    private static final int ERROR_CODE = 9;
    
    public NotSufficientFundsException(){
        super(ERROR_CODE);
    }
    
    public NotSufficientFundsException(String msg){
        super(msg);
        setErrorCode(ERROR_CODE);
    }

}
