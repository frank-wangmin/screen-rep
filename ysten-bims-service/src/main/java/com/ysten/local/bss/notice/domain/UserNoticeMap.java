package com.ysten.local.bss.notice.domain;

import java.util.Date;


/**
 * 消息与用户分组 用户关系表
 * 
 * @author 
 * 
 */
public class UserNoticeMap implements java.io.Serializable {

	private static final long serialVersionUID = 3039176282519928955L;

	/**
     * 通知id
     */
    private Long id;

    /**
     * 用户编号
     */
    private String code;

    /**
     * 消息
     */
    private Long noticeId;
    /**
     * 用户分组
     */
    private Long userGroupId;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 状态
     */
    private String type;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(Long noticeId) {
		this.noticeId = noticeId;
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