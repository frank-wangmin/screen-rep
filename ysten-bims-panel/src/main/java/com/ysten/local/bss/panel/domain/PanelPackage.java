package com.ysten.local.bss.panel.domain;

/**
 * Created by frank on 14-5-9.
 */

public class PanelPackage extends BaseDomain {
    private static final long serialVersionUID = -3529234775274936779L;


    //面板包名称
    private String packageName;

    //面板包描述
    private String packageDesc;

    //是否为默认面板包
    private Integer isDefault;

    private String districtCode;

    //平台ID
    private Long platFormId;

    //数据的有效性（依据播控数据的删除判断）
    private Integer onlineStatus;

    public Integer getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(Integer onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    // epg_package_id
    private Long epgPackageId;
    // 兼容epg
    private String name;
    // 兼容epg
    private Long oprUserID;
    // 兼容epg
    private Long platformId;
    //页面展示设备分组
    private String deviceGroupIds;
    //页面展示设备编号
    private String deviceCodes;
    //页面展示用户分组
    private String userGroupIds;
    //页面展示用户外部编号
    private String userIds;
    //EPG1 style.xml
    private String epgStyle1;
    //EPG2 style.xml
    private String epgStyle2;
    //Navigation data.xml
    private String navsData;
    // 面板包类型[推荐类型:实时生成,内部数据,外部数据，第三方推荐]
    private Integer packageType;

    /*江苏移动*/

    //版本
    private Long version;

    //配置的最大栏目页数
    private Integer maxPageNumber;

    //面板默认背景--1080p
    private String defaultBackground1080p;
    
    //面板默认背景--720p
    private String defaultBackground720p;
    
    //通用的上部导航
    private String commonTopNav;

    private String zipUrl;

    private String zipUrl1080p;

    public String getZipUrl1080p() {
        return zipUrl1080p;
    }

    public void setZipUrl1080p(String zipUrl1080p) {
        this.zipUrl1080p = zipUrl1080p;
    }

    /*江苏移动*/

    public String getZipUrl() {
        return zipUrl;
    }

    public void setZipUrl(String zipUrl) {
        this.zipUrl = zipUrl;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Integer getMaxPageNumber() {

        return maxPageNumber;
    }

    public void setMaxPageNumber(Integer maxPageNumber) {
        this.maxPageNumber = maxPageNumber;
    }

    public String getDefaultBackground1080p() {
		return defaultBackground1080p;
	}

	public void setDefaultBackground1080p(String defaultBackground1080p) {
		this.defaultBackground1080p = defaultBackground1080p;
	}

	public String getDefaultBackground720p() {
		return defaultBackground720p;
	}

	public void setDefaultBackground720p(String defaultBackground720p) {
		this.defaultBackground720p = defaultBackground720p;
	}

	public String getCommonTopNav() {
        return commonTopNav;
    }

    public void setCommonTopNav(String commonTopNav) {
        this.commonTopNav = commonTopNav;
    }

    public Integer getPackageType() {
        return packageType;
    }

    public void setPackageType(Integer packageType) {
        this.packageType = packageType;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


    public void setEpgStyle1(String epgStyle1) {
        this.epgStyle1 = epgStyle1;
    }

    public void setNavsData(String navsData) {
        this.navsData = navsData;
    }

    public String getEpgStyle2() {
        return epgStyle2;
    }

    public String getNavsData() {
        return navsData;
    }

    public String getEpgStyle1() {
        return epgStyle1;
    }

    public void setEpgStyle2(String epgStyle2) {
        this.epgStyle2 = epgStyle2;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageDesc() {
        return packageDesc;
    }

    public void setPackageDesc(String packageDesc) {
        this.packageDesc = packageDesc;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public Long getPlatFormId() {
        return platFormId;
    }

    public void setPlatFormId(Long platFormId) {
        this.platFormId = platFormId;
    }

    public Long getEpgPackageId() {
        return epgPackageId;
    }

    public void setEpgPackageId(Long epgPackageId) {
        this.epgPackageId = epgPackageId;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOprUserID() {
        return oprUserID;
    }

    public void setOprUserID(Long oprUserID) {
        this.oprUserID = oprUserID;
    }

    public Long getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Long platformId) {
        this.platformId = platformId;
    }

    public String getDeviceGroupIds() {
        return deviceGroupIds;
    }

    public void setDeviceGroupIds(String deviceGroupIds) {
        this.deviceGroupIds = deviceGroupIds;
    }

    public String getDeviceCodes() {
        return deviceCodes;
    }

    public void setDeviceCodes(String deviceCodes) {
        this.deviceCodes = deviceCodes;
    }

    public String getUserGroupIds() {
        return userGroupIds;
    }

    public void setUserGroupIds(String userGroupIds) {
        this.userGroupIds = userGroupIds;
    }

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }

}
