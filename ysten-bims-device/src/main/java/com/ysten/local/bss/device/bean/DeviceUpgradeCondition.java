package com.ysten.local.bss.device.bean;


import com.ysten.local.bss.device.utils.EnumConstantsInterface;

/**
 * 设备升级条件类
 * @author hxy
 *
 */
public class DeviceUpgradeCondition extends BaseCondition {
	
	private EnumConstantsInterface.PackageType packageType;
	
	private EnumConstantsInterface.Status status;
	
	private Long softCodeId;
	

	public Long getSoftCodeId() {
		return softCodeId;
	}

	public void setSoftCodeId(Long softCodeId) {
		this.softCodeId = softCodeId;
	}

	public EnumConstantsInterface.Status getStatus() {
		return status;
	}

	public void setStatus(EnumConstantsInterface.Status status) {
		this.status = status;
	}

	public EnumConstantsInterface.PackageType getPackageType() {
		return packageType;
	}

	public void setPackageType(EnumConstantsInterface.PackageType packageType) {
		this.packageType = packageType;
	}
	
	
	
	

}
