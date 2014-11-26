package com.ysten.local.bss.device.bean;

/**
 * 业务平台返回的结果
 * @author Chen Yun
 *
 */
public class UserDataUpdateResponse {

    private String seqNo;
    private String resultCode;
    private String resultMessage;

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

}
