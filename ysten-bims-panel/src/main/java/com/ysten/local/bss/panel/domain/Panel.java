package com.ysten.local.bss.panel.domain;

import java.util.Date;

/**
 * Created by frank on 14-5-9.
 */
public class Panel extends BaseDomain {

    /**
     *
     */
    private static final long serialVersionUID = 7415044482197257232L;

    // 面板字典值
    private String panelMark;

    private String districtCode;

    private Long panelId;

    private String areaName;

    // 模板ID
    private Long templateId;

    private Long areaId;

    // 面板名称
    private String panelName;

    // 面板标题
    private String panelTitle;

    // 面板屏代码
    private String panelStyle;

    // 面板图标
    private String panelIcon;

    // 链接地址
    private String linkUrl;

    // 背景图片
    private String imgUrl;

    // 在线状态
    private Integer onlineStatus;

    // 在线状态时间
    private Date onlineStatueTime;
    // 状态
    private Integer status;

    // epg1数据
    private String epg1Data;

    // epg1样式
    private String epg1Style;

    // epg2数据
    private String epg2Data;

    // epg2样式
    private String epg2Style;

    // 是否显示，当面板类型为自定义的时候，返回相应的自定义页面是否显示。
    private String display;

    //定制页大图
    private String bigimg;

    //定制页小图
    private String smallimg;

    //是否锁定
    private String isLock;

    // 兼容epg epg的面板ID
    private Long epgPanelId;

    // 兼容epg epg的模板id
    private Long epgTemplateId;
    /*// 兼容epg
    private Integer type;*/
    // 兼容epg
    private String style;
    // 兼容epg
    private String name;
    // 兼容epg
    private String title;
    // 兼容epg
    private String icon;
    // 兼容epg
    private Date onlineStatusTime;
    // 兼容epg
    private String imageUrl;
    // 兼容epg
    private Long oprUserid;
    //是否是自定义数据
    private Integer isCustom;
    //兼容epg
    private Integer type;

    /**
     * 引用面板Id
     */
    private Long refPanelId;

    private Long epgRefPanelId;

    private String panelLogo;


    /* 冗余 */

    //用于显示包关联面板的下部导航，便于解绑操作
    private String rootNavTitle;

    private Integer sort;

    /* 冗余 */

    /*江苏移动*/

    //版本
    private Long version;
    //分辨率
    private String resolution;

    private PanelPackageMap panelPackageMap;

    /*江苏移动*/

    public PanelPackageMap getPanelPackageMap() {
        return panelPackageMap;
    }

    public void setPanelPackageMap(PanelPackageMap panelPackageMap) {
        this.panelPackageMap = panelPackageMap;
    }

    public Long getVersion() {
        return version;
    }

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public void setVersion(Long version) {
        this.version = version;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getRootNavTitle() {
        return rootNavTitle;
    }

    public void setRootNavTitle(String rootNavTitle) {
        this.rootNavTitle = rootNavTitle;
    }

    public Integer getIsCustom() {
        return isCustom;
    }

    public void setIsCustom(Integer isCustom) {
        this.isCustom = isCustom;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getPanelMark() {
        return panelMark;
    }

    public void setPanelMark(String panelMark) {
        this.panelMark = panelMark;
    }

    public String getPanelName() {
        return panelName;
    }

    public void setPanelName(String panelName) {
        this.panelName = panelName;
    }

    public String getPanelTitle() {
        return panelTitle;
    }

    public void setPanelTitle(String panelTitle) {
        this.panelTitle = panelTitle;
    }

    public String getPanelStyle() {
        return panelStyle;
    }

    public void setPanelStyle(String panelStyle) {
        this.panelStyle = panelStyle;
    }

    public String getPanelIcon() {
        return panelIcon;
    }

    public void setPanelIcon(String panelIcon) {
        this.panelIcon = panelIcon;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(Integer onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public Date getOnlineStatueTime() {
        return onlineStatueTime;
    }

    public void setOnlineStatueTime(Date onlineStatueTime) {
        this.onlineStatueTime = onlineStatueTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getEpg1Data() {
        return epg1Data;
    }

    public void setEpg1Data(String epg1Data) {
        this.epg1Data = epg1Data;
    }

    public String getEpg1Style() {
        return epg1Style;
    }

    public void setEpg1Style(String epg1Style) {
        this.epg1Style = epg1Style;
    }

    public String getEpg2Data() {
        return epg2Data;
    }

    public void setEpg2Data(String epg2Data) {
        this.epg2Data = epg2Data;
    }

    public String getEpg2Style() {
        return epg2Style;
    }

    public void setEpg2Style(String epg2Style) {
        this.epg2Style = epg2Style;
    }

    public Long getEpgPanelId() {
        return epgPanelId;
    }

    public void setEpgPanelId(Long epgPanelId) {
        this.epgPanelId = epgPanelId;
    }

    public Long getEpgTemplateId() {
        return epgTemplateId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setEpgTemplateId(Long epgTemplateId) {
        this.epgTemplateId = epgTemplateId;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Date getOnlineStatusTime() {
        return onlineStatusTime;
    }

    public void setOnlineStatusTime(Date onlineStatusTime) {
        this.onlineStatusTime = onlineStatusTime;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getOprUserid() {
        return oprUserid;
    }

    public void setOprUserid(Long oprUserid) {
        this.oprUserid = oprUserid;
    }


    public String getBigimg() {
        return bigimg;
    }

    public void setBigimg(String bigimg) {
        this.bigimg = bigimg;
    }

    public String getSmallimg() {
        return smallimg;
    }

    public void setSmallimg(String smallimg) {
        this.smallimg = smallimg;
    }

    public Long getRefPanelId() {
        return refPanelId;
    }

    public void setRefPanelId(Long refPanelId) {
        this.refPanelId = refPanelId;
    }

    public Long getEpgRefPanelId() {
        return epgRefPanelId;
    }

    public void setEpgRefPanelId(Long epgRefPanelId) {
        this.epgRefPanelId = epgRefPanelId;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getIsLock() {
        return isLock;
    }

    public void setIsLock(String isLock) {
        this.isLock = isLock;
    }

    public String getPanelLogo() {
        return panelLogo;
    }

    public void setPanelLogo(String panelLogo) {
        this.panelLogo = panelLogo;
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

    public Long getPanelId() {
        return panelId;
    }

    public void setPanelId(Long panelId) {
        this.panelId = panelId;
    }
}
