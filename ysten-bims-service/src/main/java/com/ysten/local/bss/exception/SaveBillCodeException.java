package com.ysten.local.bss.exception;

import com.ysten.core.AppErrorCodeException;

/**
 * 保存账单号失败
 * @author sunguangqi
 * @since 2011-10-18
 */
public class SaveBillCodeException extends AppErrorCodeException {

    private static final long serialVersionUID = 1L;
    private static final int ERROR_CODE = 6;
    
    public SaveBillCodeException(){
        super(ERROR_CODE);
    }
    
    public SaveBillCodeException(String msg){
        super(msg);
        setErrorCode(ERROR_CODE);
    }
}
