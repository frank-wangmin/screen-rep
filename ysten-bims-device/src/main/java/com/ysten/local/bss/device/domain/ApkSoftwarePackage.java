package com.ysten.local.bss.device.domain;

import java.util.Date;

import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import org.apache.commons.lang.StringUtils;

public class ApkSoftwarePackage implements Comparable<ApkSoftwarePackage>, java.io.Serializable{
	private static final long serialVersionUID = -8009210755003616464L;
	private Long id;
    private ApkSoftwareCode softCodeId;
    private Integer versionSeq;
    private String versionName;
    private EnumConstantsInterface.PackageType packageType;
    private EnumConstantsInterface.PackageStatus packageStatus;
    private String packageLocation;
    private EnumConstantsInterface.MandatoryStatus mandatoryStatus;
    private String md5;
    private Date createDate;
    private Integer deviceVersionSeq;
    private Long fullSoftwareId;
    private Date lastModifyTime;
    private String operUser;

    private Integer isAll;
    private long upgradeTaskId;
    
    public ApkSoftwarePackage(){
    	
    }

    public static String toXML(ApkSoftwarePackage apkSoftwarePackage) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
        sb.append("<Service id=\"getApkUpgradeInfor\">");

        if(null != apkSoftwarePackage){
            sb.append("<state>upgrade</state>");
            sb.append("<upgrades>");

            String softCode ="";
            if(null != apkSoftwarePackage.getSoftCodeId()
                    && StringUtils.isNotBlank(apkSoftwarePackage.getSoftCodeId().getCode())){
                softCode = apkSoftwarePackage.getSoftCodeId().getCode().trim();
            }
            sb.append("<upgrade platformId=\""+ softCode + "\"" );
            sb.append(" versionId=\"" +
                    (apkSoftwarePackage.getVersionSeq() == null ? "" : apkSoftwarePackage.getVersionSeq()) + "\"");
            sb.append(" versionName=\"" +
                    (apkSoftwarePackage.getVersionName() == null ? "" : apkSoftwarePackage.getVersionName().trim()) + "\"");
            sb.append(" packageType=\"" +
                    (apkSoftwarePackage.getPackageType() == null ? "" : apkSoftwarePackage.getPackageType().toString()) + "\"");
            sb.append(" packageLocation=\"" +
                    (apkSoftwarePackage.getPackageLocation() == null ? "" : apkSoftwarePackage.getPackageLocation().trim()) + "\"");
            sb.append(" packageStatus=\"" +
                    (apkSoftwarePackage.getPackageStatus() == null ? "" : apkSoftwarePackage.getPackageStatus().toString()) + "\"");
            String isForce = "false";
            if(null != apkSoftwarePackage.getMandatoryStatus()
                    && apkSoftwarePackage.getMandatoryStatus().toString().trim().equalsIgnoreCase("MANDATORY")){
                isForce = "true";
            }
            sb.append(" isForce=\"" + isForce + "\"");
            sb.append(" md5=\"" +
                    (apkSoftwarePackage.getMd5() == null ? "" : apkSoftwarePackage.getMd5().toString().trim()) + "\"");
            sb.append(">");
            sb.append("</upgrade>");

            sb.append("</upgrades>");
        }else{
            sb.append("<state>notupgrade</state>");
        }

        sb.append("</Service>");
        return sb.toString();
    }

    @Override
    public int compareTo(ApkSoftwarePackage other) {
        if (other == null)
            return 1;
        int value = this.versionSeq - other.versionSeq;
        if (value == 0){
            value = this.packageType.ordinal() - other.packageType.ordinal();
        }
        return value;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Integer getVersionSeq() {
        return versionSeq;
    }
    public void setVersionSeq(Integer versionSeq) {
        this.versionSeq = versionSeq;
    }
    public String getVersionName() {
        return versionName;
    }
    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }
    public String getPackageLocation() {
        return packageLocation;
    }
    public void setPackageLocation(String packageLocation) {
        this.packageLocation = packageLocation;
    }
    public ApkSoftwareCode getSoftCodeId() {
		return softCodeId;
	}
	public void setSoftCodeId(ApkSoftwareCode softCodeId) {
		this.softCodeId = softCodeId;
	}
	public EnumConstantsInterface.PackageType getPackageType() {
		return packageType;
	}
	public void setPackageType(EnumConstantsInterface.PackageType packageType) {
		this.packageType = packageType;
	}
	public EnumConstantsInterface.PackageStatus getPackageStatus() {
		return packageStatus;
	}
	public void setPackageStatus(EnumConstantsInterface.PackageStatus packageStatus) {
		this.packageStatus = packageStatus;
	}

    public EnumConstantsInterface.MandatoryStatus getMandatoryStatus() {
        return mandatoryStatus;
    }

    public void setMandatoryStatus(EnumConstantsInterface.MandatoryStatus mandatoryStatus) {
        this.mandatoryStatus = mandatoryStatus;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public Integer getDeviceVersionSeq() {
        return deviceVersionSeq;
    }
    public void setDeviceVersionSeq(Integer deviceVersionSeq) {
        this.deviceVersionSeq = deviceVersionSeq;
    }

    public Long getFullSoftwareId() {
        return fullSoftwareId;
    }

    public void setFullSoftwareId(Long fullSoftwareId) {
        this.fullSoftwareId = fullSoftwareId;
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

    public long getUpgradeTaskId() {
        return upgradeTaskId;
    }

    public void setUpgradeTaskId(long upgradeTaskId) {
        this.upgradeTaskId = upgradeTaskId;
    }

    public Integer getIsAll() {
        return isAll;
    }

    public void setIsAll(Integer isAll) {
        this.isAll = isAll;
    }

}