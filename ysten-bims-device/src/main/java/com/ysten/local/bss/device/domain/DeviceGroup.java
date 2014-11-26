package com.ysten.local.bss.device.domain;

import java.util.Date;

import com.ysten.area.domain.Area;
import com.ysten.local.bss.device.utils.EnumConstantsInterface.DeviceGroupType;

/**
 * 
 * 类名称：DeviceGroup 类描述： 设备信息
 * 
 * @version
 */

public class DeviceGroup implements java.io.Serializable {


    private static final long serialVersionUID = 6389856626502070998L;
	private Long id;
	private String name;
	private Long areaId;
	private String areaName;
	private Long pDeviceGroupId;
	private String description;
	private Date createDate;
	//分组的类型 按业务功能分为消息、升级、Panel、背景、开机动画、区域、通用分组
	private DeviceGroupType type;
	//是否是动态分组标识
	private Long dynamicFlag;
	//sql表达式
	private String sqlExpression;
	private Date updateDate;
	private String ystenId;
	

	public DeviceGroup() {
	}

	public DeviceGroup(Long id, String name, Date createDate, String description, Long pDeviceGroupId,DeviceGroupType type,Long dynamicFlag,String sqlExpression) {
		this.id = id;
		this.name = name;
		this.pDeviceGroupId = pDeviceGroupId;
		this.createDate = createDate;
		this.description = description;
		this.type=type;
		this.dynamicFlag=dynamicFlag;
		this.sqlExpression=sqlExpression;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getpDeviceGroupId() {
		return pDeviceGroupId;
	}

	public void setpDeviceGroupId(Long pDeviceGroupId) {
		this.pDeviceGroupId = pDeviceGroupId;
	}

	public DeviceGroupType getType() {
		return type;
	}

	public void setType(DeviceGroupType type) {
		this.type = type;
	}

	public Long getDynamicFlag() {
		return dynamicFlag;
	}

	public void setDynamicFlag(Long dynamicFlag) {
		this.dynamicFlag = dynamicFlag;
	}

	public String getSqlExpression() {
		return sqlExpression;
	}

	public void setSqlExpression(String sqlExpression) {
		this.sqlExpression = sqlExpression;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getYstenId() {
		return ystenId;
	}

	public void setYstenId(String ystenId) {
		this.ystenId = ystenId;
	}
	
	
}