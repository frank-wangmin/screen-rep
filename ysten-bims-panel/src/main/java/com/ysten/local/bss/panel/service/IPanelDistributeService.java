package com.ysten.local.bss.panel.service;

import com.ysten.local.bss.panel.domain.*;

import java.util.List;

/**
 * Created by Joyce on 14-7-3.
 */
public interface IPanelDistributeService {

    void savePanelData(List<PanelPackage> panelPackageList,List<PreviewTemplate> previewTemplateList,
                                   List<Panel> panelList,List<PanelItem> panelItemList,List<Navigation> navigationList,
                                   List<PanelPanelItemMap> panelItemMapList,List<PanelPackageMap> panelPackageMapList,
                                   List<PreviewItem> previewItemList,List<PreviewItemData> previewItemDataList) throws Exception;

    public String findPanelDatas(String type);
}
