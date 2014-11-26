package com.ysten.local.bss.device.domain;

import java.util.Date;

public class PanelPackageUserMap {
    private Long id;

    private Long panelPackageId;
    private String code;

    private Long userGroupId;
    private Date createDate;

    private String type;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPanelPackageId() {
        return panelPackageId;
    }
    public void setPanelPackageId(Long panelPackageId) {
        this.panelPackageId = panelPackageId;
    }

    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(Long userGroupId) {
        this.userGroupId = userGroupId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
}