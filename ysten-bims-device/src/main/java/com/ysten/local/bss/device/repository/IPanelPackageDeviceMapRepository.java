package com.ysten.local.bss.device.repository;

import java.util.List;
import com.ysten.local.bss.device.domain.PanelPackageDeviceMap;


public interface IPanelPackageDeviceMapRepository {
	
	PanelPackageDeviceMap getPanelDeviceMapByGroupId(Long deviceGroupId);
    
    public PanelPackageDeviceMap getPanelDeviceMapByYstenId(String ystenId);
	
	void deletePanelDeviceMapByDeviceGroupId(Long deviceGroupId);
	
	void deletePanelDeviceMapByCode(String deviceCode);
	
	void deletePanelDeviceMapByGroupIds(List<Long> groupIds,String tableName,String character);
	
	void deletePanelDeviceMapByYstenIds(List<String> ystenIds,String tableName,String character);
	
    void bulkSaveDevicePanelMap(List<PanelPackageDeviceMap> list);
	
//	void deletePanelDeviceMapByPanelId(Long panelPackageId,String isAll);

    void deletePanelPackageMapByPanelId(Long panelPackageId,String type,String tableName);
	
	boolean saveDeviceMap(PanelPackageDeviceMap panelPackageDeviceMap);
	
	boolean updateDeviceMapByYstenId(PanelPackageDeviceMap panelPackageDeviceMap);
	
    List<PanelPackageDeviceMap> findPanelPackageDeviceMapByPanelIdAndType(Long panelPackageId,String type);
}
