package com.ysten.local.bss.panel.domain;

import com.ysten.local.bss.util.bean.Required;

import java.io.Serializable;

/**
 * Created by frank on 14-5-9.
 */
public class PanelPackageMap extends BaseDomain {

    private static final long serialVersionUID = -741596407453660208L;

    private Long id;
    private String panelMark;
    // 面板ID
    private Long panelId;

    // 面板包ID
    private Long packageId;

    // 导航Id，逗号分隔
    private String navId;

    // 排序号
    private Integer sortNum;

    private String panelLogo;

    // epg_rel_id
    private Long epgRelId;
    // epg_panel_id
    private Long epgPanelId;
    // epg_package_id
    private Long epgPackageId;
    // epg_nav_id
    private String epgNavId;
    //
    private String navIds;

    //是否锁定
    private String isLock;

    // 是否显示，当面板类型为自定义的时候，返回相应的自定义页面是否显示。
    private String display;

    public String getPanelLogo() {
        return panelLogo;
    }

    public void setPanelLogo(String panelLogo) {
        this.panelLogo = panelLogo;
    }

    public String getNavId() {
        return navId;
    }

    public void setNavId(String navId) {
        this.navId = navId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPanelId() {
        return panelId;
    }

    public void setPanelId(Long panelId) {
        this.panelId = panelId;
    }

    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public Long getEpgRelId() {
        return epgRelId;
    }

    public void setEpgRelId(Long epgRelId) {
        this.epgRelId = epgRelId;
    }

    public Long getEpgPanelId() {
        return epgPanelId;
    }

    public void setEpgPanelId(Long epgPanelId) {
        this.epgPanelId = epgPanelId;
    }

    public Long getEpgPackageId() {
        return epgPackageId;
    }

    public void setEpgPackageId(Long epgPackageId) {
        this.epgPackageId = epgPackageId;
    }

    public String getPanelMark() {
        return panelMark;
    }

    public void setPanelMark(String panelMark) {
        this.panelMark = panelMark;
    }

    public String getEpgNavId() {
        return epgNavId;
    }

    public void setEpgNavId(String epgNavId) {
        this.epgNavId = epgNavId;
    }

    public String getNavIds() {
        return navIds;
    }

    public void setNavIds(String navIds) {
        this.navIds = navIds;
    }

    public String getIsLock() {
        return isLock;
    }

    public void setIsLock(String isLock) {
        this.isLock = isLock;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }
}
