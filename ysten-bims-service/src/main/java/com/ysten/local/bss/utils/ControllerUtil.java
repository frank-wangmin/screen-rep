package com.ysten.local.bss.utils;

import javax.servlet.http.HttpServletRequest;

import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.system.domain.Operator;

public final class ControllerUtil {
    private ControllerUtil(){
        
    }
    public static Operator getLoginOperator(HttpServletRequest  request) {
        return  (Operator) request.getSession().getAttribute(Constants.LOGIN_SESSION_OPERATOR);
    }
    
    public static String returnString(boolean bool){
        String result = "";
        if (bool) {
            result = Constants.SUCCESS;
        } else {
            result = Constants.FAILED;
        }
        return result;
    }
}
