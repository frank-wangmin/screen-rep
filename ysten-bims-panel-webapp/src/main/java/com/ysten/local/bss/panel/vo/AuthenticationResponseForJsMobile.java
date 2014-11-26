package com.ysten.local.bss.panel.vo;

import java.io.Serializable;

/**
 * Created by frank on 14-11-5.
 */
public class AuthenticationResponseForJsMobile implements Serializable {

    private static final long serialVersionUID = -7830051843104833941L;

    private String resultDesc;

    private String resultCode;

    private Long panelPackageID;

    public Long getPanelPackageID() {
        return panelPackageID;
    }

    public void setPanelPackageID(Long panelPackageID) {
        this.panelPackageID = panelPackageID;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
}
