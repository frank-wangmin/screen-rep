package com.ysten.local.bss.device.domain;

import java.io.Serializable;
import java.util.Date;

import com.ysten.local.bss.device.domain.Device.DistributeState;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import com.ysten.local.bss.util.bean.Required;

/**
 * 应用升级软件包信息
 *
 * @author cwang
 */
public class AppSoftwarePackage implements Serializable {

    private static final long serialVersionUID = 4585701863541632609L;
    // 主键id
    @Required
    private Long id;
    // 应用软件信息
    @Required
    private AppSoftwareCode softCodeId;
    // 版本名称
    @Required
    private String versionName;
    // 版本号
    @Required
    private Integer versionSeq;
    //当前版本号
    @Required
    private Integer appVersionSeq;
    @Required
    private Integer sdkVersion;
    // 升级包类型
    @Required
    private EnumConstantsInterface.PackageType packageType;
    // 升级地址
    @Required
    private String packageLocation;
    // 升级包md5值
    @Required
    private String md5;
    // 是否强制升级
    @Required
    private EnumConstantsInterface.MandatoryStatus mandatoryStatus;
    // 升级包状态
    @Required
    private EnumConstantsInterface.PackageStatus packageStatus;
    // 创建时间
    @Required
    private Date createDate;

    /**
     * 全量包软件ID
     */
    private Long fullSoftwareId;
    private Date lastModifyTime;
    @Required
    private String operUser;
    @Required
    private DistributeState distributeState = DistributeState.UNDISTRIBUTE;
    
    public DistributeState getDistributeState() {
		return distributeState;
	}

	public void setDistributeState(DistributeState distributeState) {
		this.distributeState = distributeState;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppSoftwareCode getSoftCodeId() {
        return softCodeId;
    }

    public void setSoftCodeId(AppSoftwareCode softCodeId) {
        this.softCodeId = softCodeId;
    }

    public Long getFullSoftwareId() {
        return fullSoftwareId;
    }

    public void setFullSoftwareId(Long fullSoftwareId) {
        this.fullSoftwareId = fullSoftwareId;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public Integer getVersionSeq() {
        return versionSeq;
    }

    public void setVersionSeq(Integer versionSeq) {
        this.versionSeq = versionSeq;
    }

    public Integer getAppVersionSeq() {
        return appVersionSeq;
    }

    public void setAppVersionSeq(Integer appVersionSeq) {
        this.appVersionSeq = appVersionSeq;
    }

    public Integer getSdkVersion() {
        return sdkVersion;
    }

    public void setSdkVersion(Integer sdkVersion) {
        this.sdkVersion = sdkVersion;
    }

    public EnumConstantsInterface.PackageType getPackageType() {
        return packageType;
    }

    public void setPackageType(EnumConstantsInterface.PackageType packageType) {
        this.packageType = packageType;
    }

    public String getPackageLocation() {
        return packageLocation;
    }

    public void setPackageLocation(String packageLocation) {
        this.packageLocation = packageLocation;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public EnumConstantsInterface.MandatoryStatus getMandatoryStatus() {
        return mandatoryStatus;
    }

    public void setMandatoryStatus(EnumConstantsInterface.MandatoryStatus mandatoryStatus) {
        this.mandatoryStatus = mandatoryStatus;
    }

    public EnumConstantsInterface.PackageStatus getPackageStatus() {
        return packageStatus;
    }

    public void setPackageStatus(EnumConstantsInterface.PackageStatus packageStatus) {
        this.packageStatus = packageStatus;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public String getOperUser() {
        return operUser;
    }

    public void setOperUser(String operUser) {
        this.operUser = operUser;
    }

    public AppSoftwarePackage() {
    }
}