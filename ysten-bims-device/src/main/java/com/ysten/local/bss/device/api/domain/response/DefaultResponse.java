package com.ysten.local.bss.device.api.domain.response;

public class DefaultResponse implements IResponse {
    /**
     * true or false
     */
    private String result;
    /**
     * 返回信息
     */
    private String message;

    /**
     * 详细的错误信息，可以用来判定出错原因
     */
    private transient String detailMessage;

    public DefaultResponse(String message) {
        this(true, message);
    }

    public DefaultResponse(boolean result, String message) {
        this(result ? "true" : "false", message);
    }

    public DefaultResponse(String result, String message) {
        this.result = result;
        this.message = message;
        this.detailMessage = message;
    }

    public DefaultResponse(String result, String message, String detailMessage) {
        this.result = result;
        this.message = message;
        this.detailMessage = detailMessage;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    public void setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
    }

    @Override
    public String toString() {
        return "result=" + this.result + ", message=" + this.message + ", detail message=" + detailMessage;
    }
}
