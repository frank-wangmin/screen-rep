package com.ysten.local.bss.util.bean;

/**
 * Created by Neil on 2014-07-25.
 */
public class ReturnBean {

    private String result;

    private String message;

    public ReturnBean() {
    }

    public ReturnBean(String result, String message) {
        this.result = result;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResult() {

        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
