
package com.ysten.local.bss.panel.service;

import com.ysten.local.bss.device.domain.DeviceGroup;
import com.ysten.local.bss.panel.domain.Panel;
import com.ysten.local.bss.panel.domain.PanelPackageMap;
import com.ysten.local.bss.panel.domain.PanelPackageNavigation;

import java.util.List;
import java.util.Map;

/**
 * @author cwang
 * @version 2014-5-23 下午4:30:58
 */
public interface IPanelPackageMapService {

    List<PanelPackageMap> getPanelPackageMapByPackageId(Long packageId);

    PanelPackageMap getMapByPackageIdAndPanelId(Long packageId,Long panelId);

//    void updateSort(PanelPackageMap panelPackageMap);
    void updatePanelPackageConfig(Long panelPackageId,List<PanelPackageNavigation> list,Long oprUserId,String dpi);
    
    List<PanelPackageMap> getMapByPanelPanelPackage(Map<String, Object> map);

    List<PanelPackageMap> verifyIfExistBinded(Long panelId, Long packageId);

    boolean deletePanelPackageMapByPackagelId(Long packageId,List<Long>  panelIds);

}

