package com.ysten.local.bss.device.domain;


import com.ysten.local.bss.device.domain.Device.DistributeState;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import com.ysten.local.bss.util.bean.Required;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * 终端升级软件信息
 * 
 * @author hxy
 *
 */
public class DeviceSoftwarePackage implements Comparable<DeviceSoftwarePackage>, java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7374247865003405958L;

	/**
     * 软件包ID
     */
	@Required
    private Long id;

    /**
     * 软件版本序号
     */
	@Required
    private Integer versionSeq;

    /**
     * 软件版本名称
     */
	@Required
    private String versionName;

    /**
     * 软件号
     */
	@Required
    private DeviceSoftwareCode softCodeId;

    /**
     * 软件包类型
     */
	@Required
    private EnumConstantsInterface.PackageType packageType;

    /**
     * 软件包绝对路径
     */
	@Required
    private String packageLocation;

    /**
     * 软件包状态
     */
	@Required
    private EnumConstantsInterface.PackageStatus packageStatus;

    /**
     * 是否强制升级
     */
	@Required
    private EnumConstantsInterface.MandatoryStatus mandatoryStatus;

    /**
     * MD5
     */
	@Required
    private String md5;

    /**
     * 创建时间
     */
	@Required
    private Date createDate;

    /**
     * 终端当前版本号
     */
	@Required
    private Integer deviceVersionSeq;

    /**
     * 全量包软件ID
     */
	@Required
    private Long fullSoftwareId;
       
    private Date lastModifyTime;
	@Required
    private String operUser;
    @Required
    private DistributeState distributeState = DistributeState.UNDISTRIBUTE;

    private Integer isAll;

    private long upgradeTaskId;

    public static String toXML(DeviceSoftwarePackage deviceSoftwarePackage) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
        sb.append("<Service id=\"getUpgradeInfor\">");

        if(null != deviceSoftwarePackage){
            sb.append("<state>upgrade</state>");
            sb.append("<upgrades>");

            String softCode ="";
            if(null != deviceSoftwarePackage.getSoftCodeId()
                    && StringUtils.isNotBlank(deviceSoftwarePackage.getSoftCodeId().getCode())){
                softCode = deviceSoftwarePackage.getSoftCodeId().getCode().trim();
            }
            sb.append("<upgrade platformId=\""+ softCode + "\"" );
            sb.append(" versionId=\"" +
                    (deviceSoftwarePackage.getVersionSeq() == null ? "" : deviceSoftwarePackage.getVersionSeq()) + "\"");
            sb.append(" versionName=\"" +
                    (deviceSoftwarePackage.getVersionName() == null ? "" : deviceSoftwarePackage.getVersionName().trim()) + "\"");
            sb.append(" packageType=\"" +
                    (deviceSoftwarePackage.getPackageType() == null ? "" : deviceSoftwarePackage.getPackageType().toString()) + "\"");
            sb.append(" packageLocation=\"" +
                    (deviceSoftwarePackage.getPackageLocation() == null ? "" : deviceSoftwarePackage.getPackageLocation().trim()) + "\"");
            sb.append(" packageStatus=\"" +
                    (deviceSoftwarePackage.getPackageStatus() == null ? "" : deviceSoftwarePackage.getPackageStatus().toString()) + "\"");
            String isForce = "false";
            if(null != deviceSoftwarePackage.getMandatoryStatus()
                    && deviceSoftwarePackage.getMandatoryStatus().toString().trim().equalsIgnoreCase("MANDATORY")){
                isForce = "true";
            }
            sb.append(" isForce=\"" + isForce + "\"");
            sb.append(" md5=\"" +
                    (deviceSoftwarePackage.getMd5() == null ? "" : deviceSoftwarePackage.getMd5().toString().trim()) + "\"");
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
    public int compareTo(DeviceSoftwarePackage other) {
        if (other == null)
            return 1;
        int value = this.versionSeq - other.versionSeq;
        if (value == 0){
            value = this.packageType.ordinal() - other.packageType.ordinal();
        }
        return value;
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

	public DeviceSoftwareCode getSoftCodeId() {
		return softCodeId;
	}

	public void setSoftCodeId(DeviceSoftwareCode softCodeId) {
		this.softCodeId = softCodeId;
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
}