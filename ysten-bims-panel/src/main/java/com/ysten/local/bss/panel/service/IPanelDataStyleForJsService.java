package com.ysten.local.bss.panel.service;

import com.ysten.local.bss.panel.domain.PanelPackage;
import com.ysten.local.bss.panel.vo.PanelResponseForJsMobile;

import java.util.List;

/**
 * Created by frank on 14-11-3.
 */
public interface IPanelDataStyleForJsService {

    String getCustomerPanel(String templateId,String dpi) throws Exception;

    public void createAllZips(List<Long> panelPackageIdList) throws Exception;

    void createZip(PanelPackage panelPackage,String dpi) throws Exception;

    public PanelResponseForJsMobile getPanelInfo(Long panelPackageId, String dpi, Long launcherVersion);
}
