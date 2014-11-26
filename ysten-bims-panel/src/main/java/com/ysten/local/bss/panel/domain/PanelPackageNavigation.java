package com.ysten.local.bss.panel.domain;

/**
 * Created by yaoqy on 14-9-24.
 */
public class PanelPackageNavigation extends BaseDomain {

    //用于显示包关联面板的下部导航，便于解绑操作
    private String rootNavTitle;

    //用于显示包关联面板的下部导航名称，便于面板包的配置
    private Long rootNavId;

    //用于显示包关联面板的上部导航名称，便于面板包的配置
    private String headNavIds;

    private Integer sort;

    private String panelLogo;
    private String isLock;
    private String display;

    private String panelTitle;

    public String getPanelTitle() {
        return panelTitle;
    }

    public void setPanelTitle(String panelTitle) {
        this.panelTitle = panelTitle;
    }

    public String getRootNavTitle() {
        return rootNavTitle;
    }

    public void setRootNavTitle(String rootNavTitle) {
        this.rootNavTitle = rootNavTitle;
    }

    public Long getRootNavId() {
        return rootNavId;
    }

    public void setRootNavId(Long rootNavId) {
        this.rootNavId = rootNavId;
    }

    public String getHeadNavIds() {
        return headNavIds;
    }

    public void setHeadNavIds(String headNavIds) {
        this.headNavIds = headNavIds;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getPanelLogo() {
        return panelLogo;
    }

    public void setPanelLogo(String panelLogo) {
        this.panelLogo = panelLogo;
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
