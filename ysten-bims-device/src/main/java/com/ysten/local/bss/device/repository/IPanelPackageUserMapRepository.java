package com.ysten.local.bss.device.repository;

import java.util.List;

import com.ysten.local.bss.device.domain.PanelPackageUserMap;


public interface IPanelPackageUserMapRepository {
	
	PanelPackageUserMap getMapByUserGroupId(Long userGroupId);
	
	PanelPackageUserMap getMapByUserCode(String code);
	
	void deletePanelUserMapByUserGroupId(Long userGroupId);
	
	void deletePanelUserMapByUserCode(String code);
	
//	void deletePanelUserMapByPanelId(Long panelPackageId);
	
	boolean saveUserMap(PanelPackageUserMap panelPackageUserMap);
     void bulkSaveUserPanelMap(List<PanelPackageUserMap> list);
	
    List<PanelPackageUserMap> findPanelPackageUserMapByPanelIdAndType(Long panelPackageId,String type);
    
}
