package com.ysten.local.bss.device.domain;

import java.io.Serializable;
import java.util.Date;

import com.ysten.utils.bean.IEnumDisplay;

/**
 * 
 * @author cwang
 * 
 */
public class AppUpgradeMap implements Serializable {
	private static final long serialVersionUID = 2858541811447048759L;
	// 主键id
	private Long id;
	// 应用升级id
	private Long upgradeId;
	// 易视腾编号
	private String ystenId;
	// 设备分组编号
	private Long deviceGroupId;

	private Type type;
	private Date createDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUpgradeId() {
		return upgradeId;
	}

	public void setUpgradeId(Long upgradeId) {
		this.upgradeId = upgradeId;
	}

	public String getYstenId() {
		return ystenId;
	}

	public void setYstenId(String ystenId) {
		this.ystenId = ystenId;
	}

	public Long getDeviceGroupId() {
		return deviceGroupId;
	}

	public void setDeviceGroupId(Long deviceGroupId) {
		this.deviceGroupId = deviceGroupId;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 终端分组类型
	 * 
	 * @author cwang
	 * 
	 */
	public enum Type implements IEnumDisplay {
		GROUP("分组"), DEVICE("终端");
		private String msg;

		private Type(String msg) {
			this.msg = msg;
		}

		@Override
		public String getDisplayName() {
			return this.msg;
		}
	}
}
