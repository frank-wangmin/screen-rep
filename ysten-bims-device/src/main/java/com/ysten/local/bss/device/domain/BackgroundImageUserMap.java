package com.ysten.local.bss.device.domain;

import java.util.Date;

/**
 * Created  on 2014-05-19.
 */
public class BackgroundImageUserMap implements java.io.Serializable{

	private static final long serialVersionUID = 4544815641548183424L;

	private Long id;

    private Long backgroundImageId;

    private String code;
    
    private Long userGroupId;
    
    private Date createDate;
    
    private String type;
    
    private Integer loopTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBackgroundImageId() {
        return backgroundImageId;
    }

    public void setBackgroundImageId(Long backgroundImageId) {
        this.backgroundImageId = backgroundImageId;
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

	public Integer getLoopTime() {
		return loopTime;
	}

	public void setLoopTime(Integer loopTime) {
		this.loopTime = loopTime;
	}
}
