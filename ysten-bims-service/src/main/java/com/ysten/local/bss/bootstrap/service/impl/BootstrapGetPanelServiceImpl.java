package com.ysten.local.bss.bootstrap.service.impl;

import com.ysten.local.bss.bootstrap.service.IBootstrapGetPanelService;
import com.ysten.local.bss.panel.domain.PanelPackage;
import com.ysten.local.bss.panel.service.IPanelPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BootstrapGetPanelServiceImpl implements IBootstrapGetPanelService {
    @Autowired
    private IPanelPackageService panelPackageService;

    public String getDefaultPanelPackage(){
        // 获取默认的PANEL
        PanelPackage  panelPackage = panelPackageService.getDefaultPackage();
        String screenId = (panelPackage == null ? "" : panelPackage.getId().toString().trim());
        return screenId;
    }

    public String getPanelPackageByYstenId(String ystenId){
        // 根据 用户 用户组 终端 终端组 查询PANEL包【优先级顺序依次】
        PanelPackage panelPackage = panelPackageService.getPanelPackageForBootstrapByYstenId(ystenId);
        String screenId = (panelPackage == null ? "" : panelPackage.getId().toString().trim());
        return screenId;
    }

}