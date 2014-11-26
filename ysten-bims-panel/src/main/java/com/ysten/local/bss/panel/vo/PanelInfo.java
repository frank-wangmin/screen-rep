package com.ysten.local.bss.panel.vo;

import com.ysten.local.bss.panel.domain.Navigation;
import com.ysten.local.bss.panel.domain.Panel;
import com.ysten.local.bss.panel.domain.PanelPackage;
import com.ysten.local.bss.panel.domain.PreviewItemData;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by frank on 14-11-5.
 */
public class PanelInfo implements Serializable {

    private PanelPackage panelPackage;

    //面板：预览面板项数据列表
    private LinkedHashMap<Panel, List<PreviewItemData>> panelListMap;

    //面板包的通用上部导航
    private List<Navigation> commonNavigationList;

    //面板id：面板和面板包的导航（上部导航+下部导航）
    private Map<Long, List<Navigation>> navigationMap;

    //面板id：面板最大版本
    public Map<Long, Long> panelVersionMap;

    public Map<Long, Long> getPanelVersionMap() {
        return panelVersionMap;
    }

    public void setPanelVersionMap(Map<Long, Long> panelVersionMap) {
        this.panelVersionMap = panelVersionMap;
    }

    public Map<Long, List<Navigation>> getNavigationMap() {
        return navigationMap;
    }

    public void setNavigationMap(Map<Long, List<Navigation>> navigationMap) {
        this.navigationMap = navigationMap;
    }

    public PanelPackage getPanelPackage() {
        return panelPackage;
    }

    public void setPanelPackage(PanelPackage panelPackage) {
        this.panelPackage = panelPackage;
    }

    public LinkedHashMap<Panel, List<PreviewItemData>> getPanelListMap() {
        return panelListMap;
    }

    public void setPanelListMap(LinkedHashMap<Panel, List<PreviewItemData>> panelListMap) {
        this.panelListMap = panelListMap;
    }

    public List<Navigation> getCommonNavigationList() {
        return commonNavigationList;
    }

    public void setCommonNavigationList(List<Navigation> commonNavigationList) {
        this.commonNavigationList = commonNavigationList;
    }
}
