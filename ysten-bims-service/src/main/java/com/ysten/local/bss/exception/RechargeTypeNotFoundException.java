package com.ysten.local.bss.exception;

import com.ysten.core.AppErrorCodeException;

/**
 * 充值类型未找到（未找到相应的第三方接口进行处理）
 * @author LI.T
 * @date 2011-4-28
 * @fileName RechargeTypeNotFoundException.java
 */
public class RechargeTypeNotFoundException extends AppErrorCodeException {

    private static final long serialVersionUID = -1591736981643823700L;
    private static final int ERROR_CODE = 8;
    
    public RechargeTypeNotFoundException(){
        super(ERROR_CODE);
    }
    
    public RechargeTypeNotFoundException(String msg) {
        super(msg);
        setErrorCode(ERROR_CODE);
    }
}
