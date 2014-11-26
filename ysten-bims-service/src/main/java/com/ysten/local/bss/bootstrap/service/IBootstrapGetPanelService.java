package com.ysten.local.bss.bootstrap.service;

public interface IBootstrapGetPanelService {

    String getDefaultPanelPackage();

    String getPanelPackageByYstenId(String ystenId);
}