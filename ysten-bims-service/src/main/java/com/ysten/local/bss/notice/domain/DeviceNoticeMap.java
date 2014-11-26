package com.ysten.local.bss.notice.domain;

import java.util.Date;


/**
 * 消息与设备分组 设备关系表
 * 
 * @author 
 * 
 */
public class DeviceNoticeMap implements java.io.Serializable {

	private static final long serialVersionUID = 3039176282519928955L;

	/**
     * 通知id
     */
    private Long id;

    /**
     * 设备编号
     */
    private String ystenId;
    /**
     * 消息
     */
    private Long noticeId;
    /**
     * 设备分组
     */
    private Long deviceGroupId;
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
	public String getYstenId() {
		return ystenId;
	}
	public void setYstenId(String ystenId) {
		this.ystenId = ystenId;
	}
	public Long getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(Long noticeId) {
		this.noticeId = noticeId;
	}
	public Long getDeviceGroupId() {
		return deviceGroupId;
	}
	public void setDeviceGroupId(Long deviceGroupId) {
		this.deviceGroupId = deviceGroupId;
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