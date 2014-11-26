package com.ysten.local.bss.device.domain;

import java.util.Date;


public class AnimationUserMap implements java.io.Serializable{
	private static final long serialVersionUID = 4005615477571177902L;
	private Long id;
    private Long bootAnimationId;
    private String code;
    private Long userGroupId;
    private String type;
    private Date createDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBootAnimationId() {
        return bootAnimationId;
    }

    public void setBootAnimationId(Long bootAnimationId) {
        this.bootAnimationId = bootAnimationId;
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

	public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}