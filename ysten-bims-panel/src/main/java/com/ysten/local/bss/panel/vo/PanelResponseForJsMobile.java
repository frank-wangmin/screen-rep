package com.ysten.local.bss.panel.vo;

import java.io.Serializable;

/**
 * Created by frank on 14-11-5.
 */
public class PanelResponseForJsMobile implements Serializable {

    private static final long serialVersionUID = -7830051843104833941L;

    private String resultDesc;

    private String zipURL;

    private String resultCode;

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    public String getZipURL() {
        return zipURL;
    }

    public void setZipURL(String zipURL) {
        this.zipURL = zipURL;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
}
