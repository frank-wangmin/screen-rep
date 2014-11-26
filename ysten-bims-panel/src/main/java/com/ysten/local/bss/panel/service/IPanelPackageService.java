package com.ysten.local.bss.panel.service;

import com.ysten.local.bss.device.domain.PanelPackageDeviceMap;
import com.ysten.local.bss.device.domain.PanelPackageUserMap;
import com.ysten.local.bss.device.exception.DeviceNotFoundException;
import com.ysten.local.bss.panel.domain.PanelPackage;
import com.ysten.local.bss.panel.vo.PanelPreview;
import com.ysten.local.bss.panel.vo.PanelQueryCriteria;
import com.ysten.utils.page.Pageable;

import java.util.List;

/**
 * Created by frank on 14-5-19.
 */
public interface IPanelPackageService {

    PanelPackage getPanelPackageById(Long id);

    void savePanelPackage(PanelPackage panelPackage);

    void deleteByIds(List<Long> ids);
    
    void deleteMapByIds(List<Long> ids,String isUser);

    void update(PanelPackage panelPackage);

    Pageable<PanelPackage> getTargetList(PanelQueryCriteria panelQueryCriteria);

    List<PanelPackage> getAllCustomedTargetList();

    List<PanelPreview> getPanelPreviewByPackageId(Long packageId,String dpi);

    List<PanelPackage> findAllPanelPackageList();
    
    List<PanelPackage> findPanelPackageListOfArea(String distCode);

    PanelPackage getDefaultPackage();

    PanelPackage getPanelPackageForBootstrapByYstenId(String ystenId);
    
    String bindMap(Long id,String areaIds,String deviceGroupIds,String deviceCodes,String userGroupIds,String userIds);

    String bindUserMap(Long id,String areaIds,String userGroupIds,String userIds);
    
    String checkAreaDevices(String areaIds,String[] ystenIds);
    
    String bindPanelPackage(String packageId , String deviceCode)throws DeviceNotFoundException;

    List<PanelPackageDeviceMap> findPanelPackageDeviceMapByPanelIdAndType(Long panelPackageId,String type);
    
    List<PanelPackageUserMap> findPanelPackageUserMapByPanelIdAndType(Long panelPackageId,String type);

    public PanelPackage getBindedPanelPackageByStdId(String stdId);
}
