package com.ysten.local.bss.panel.vo;

import com.ysten.local.bss.panel.domain.Navigation;
import com.ysten.local.bss.panel.domain.PreviewItemData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by frank on 14-5-30.
 */
public class PanelPreview implements Serializable {

    private List<Navigation> headNavList;

    private List<PreviewItemData> previewItemDataList;

    private Navigation rootNavigation;

    private String logo;

    private String defaultBackImag720p;

    private String defaultBackImag1080p;

    public String getDefaultBackImag720p() {
        return defaultBackImag720p;
    }

    public void setDefaultBackImag720p(String defaultBackImag720p) {
        this.defaultBackImag720p = defaultBackImag720p;
    }

    public String getDefaultBackImag1080p() {
        return defaultBackImag1080p;
    }

    public void setDefaultBackImag1080p(String defaultBackImag1080p) {
        this.defaultBackImag1080p = defaultBackImag1080p;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<Navigation> getHeadNavList() {
        return headNavList;
    }

    public void setHeadNavList(List<Navigation> headNavList) {
        this.headNavList = headNavList;
    }

    public List<PreviewItemData> getPreviewItemDataList() {
        return previewItemDataList;
    }

    public void setPreviewItemDataList(List<PreviewItemData> previewItemDataList) {
        this.previewItemDataList = previewItemDataList;
    }

    public Navigation getRootNavigation() {
        return rootNavigation;
    }

    public void setRootNavigation(Navigation rootNavigation) {
        this.rootNavigation = rootNavigation;
    }
}
