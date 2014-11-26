package com.ysten.local.bss.device.domain;

import com.ysten.local.bss.device.domain.Customer.State;

public class CustomerDeviceHistoryVo {
	/**
	 * 用户
	 */
	private String userCode;
	/**
	 * 用户-用户外部编号
	 */
	private String userId;
	/**
	 * 用户-登录名
	 */
	private String userName;
	/**
	 * 用户-用户状态
	 */
	private State state;
	/**
	 * 用户-用户电话
	 */
	private String phone;
	/**
	 * 用户-用户创建时间
	 */
	private String userCreateDate;
	/**
	 * 用户-所属地市
	 */
	private String region;
	/**
	 * 用户-激活时间
	 */
	private String activateDate;
	/**
	 * 用户编号
	 */
	private String userNo;
	/**
	 * 旧设备
	 */
	private String oldDeviceCode;
	/**
	 * 旧易视腾编号
	 */
	private String oldYstenId;
	/**
	 * 旧设备-终端序列号
	 */
	private String oldDeviceSno;
	/**
	 * 旧设备-创建时间
	 */
	private String oldDeviceCreateDate;
	/**
	 * 旧设备-激活时间
	 */
	private String oldDeviceActivateDate;
	/**
	 * 旧设备-设备状态
	 */
	private com.ysten.local.bss.device.domain.Device.State oldDeviceState;
	/**
	 * 旧设备-绑定类型
	 */
	private String oldDeviceBindType;
	/**
	 * 旧设备编号
	 */
	private String oldDeviceNo;
	/**
	 * 新设备
	 */
	private String newDeviceCode;
	/**
	 * 新易视腾编号
	 */
	 private String newYstenId;
	/**
	 * 新设备-终端序列号
	 */
	private String newDeviceSno;
	/**
	 * 新设备-创建时间
	 */
	private String newDeviceCreateDate;
	/**
	 * 新设备-激活时间
	 */
	private String newDeviceActivateDate;
	/**
	 * 新设备-设备状态
	 */
	private com.ysten.local.bss.device.domain.Device.State newDeviceState;
	/**
	 * 新设备-绑定状态
	 */
	private String newDeviceBindType;
	/**
	 * 新设备编号
	 */
	private String newDeviceNo;
	/**
	 * 创建时间
	 */
	private String createDate;
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getUserCreateDate() {
		return userCreateDate;
	}
	public void setUserCreateDate(String userCreateDate) {
		this.userCreateDate = userCreateDate;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getActivateDate() {
		return activateDate;
	}
	public void setActivateDate(String activateDate) {
		this.activateDate = activateDate;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getOldDeviceCode() {
		return oldDeviceCode;
	}
	public void setOldDeviceCode(String oldDeviceCode) {
		this.oldDeviceCode = oldDeviceCode;
	}
	public String getOldDeviceSno() {
		return oldDeviceSno;
	}
	public void setOldDeviceSno(String oldDeviceSno) {
		this.oldDeviceSno = oldDeviceSno;
	}
	public String getOldDeviceCreateDate() {
		return oldDeviceCreateDate;
	}
	public void setOldDeviceCreateDate(String oldDeviceCreateDate) {
		this.oldDeviceCreateDate = oldDeviceCreateDate;
	}
	public String getOldDeviceActivateDate() {
		return oldDeviceActivateDate;
	}
	public void setOldDeviceActivateDate(String oldDeviceActivateDate) {
		this.oldDeviceActivateDate = oldDeviceActivateDate;
	}
	public com.ysten.local.bss.device.domain.Device.State getOldDeviceState() {
		return oldDeviceState;
	}
	public void setOldDeviceState(
			com.ysten.local.bss.device.domain.Device.State oldDeviceState) {
		this.oldDeviceState = oldDeviceState;
	}
	public String getOldDeviceBindType() {
		return oldDeviceBindType;
	}
	public void setOldDeviceBindType(String oldDeviceBindType) {
		this.oldDeviceBindType = oldDeviceBindType;
	}
	public String getOldDeviceNo() {
		return oldDeviceNo;
	}
	public void setOldDeviceNo(String oldDeviceNo) {
		this.oldDeviceNo = oldDeviceNo;
	}
	public String getNewDeviceCode() {
		return newDeviceCode;
	}
	public void setNewDeviceCode(String newDeviceCode) {
		this.newDeviceCode = newDeviceCode;
	}
	public String getNewDeviceSno() {
		return newDeviceSno;
	}
	public void setNewDeviceSno(String newDeviceSno) {
		this.newDeviceSno = newDeviceSno;
	}
	public String getNewDeviceCreateDate() {
		return newDeviceCreateDate;
	}
	public void setNewDeviceCreateDate(String newDeviceCreateDate) {
		this.newDeviceCreateDate = newDeviceCreateDate;
	}
	public String getNewDeviceActivateDate() {
		return newDeviceActivateDate;
	}
	public void setNewDeviceActivateDate(String newDeviceActivateDate) {
		this.newDeviceActivateDate = newDeviceActivateDate;
	}
	public com.ysten.local.bss.device.domain.Device.State getNewDeviceState() {
		return newDeviceState;
	}
	public void setNewDeviceState(
			com.ysten.local.bss.device.domain.Device.State newDeviceState) {
		this.newDeviceState = newDeviceState;
	}
	public String getNewDeviceBindType() {
		return newDeviceBindType;
	}
	public void setNewDeviceBindType(String newDeviceBindType) {
		this.newDeviceBindType = newDeviceBindType;
	}
	public String getNewDeviceNo() {
		return newDeviceNo;
	}
	public void setNewDeviceNo(String newDeviceNo) {
		this.newDeviceNo = newDeviceNo;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
    public String getOldYstenId() {
        return oldYstenId;
    }
    public void setOldYstenId(String oldYstenId) {
        this.oldYstenId = oldYstenId;
    }
    public String getNewYstenId() {
        return newYstenId;
    }
    public void setNewYstenId(String newYstenId) {
        this.newYstenId = newYstenId;
    }
}
