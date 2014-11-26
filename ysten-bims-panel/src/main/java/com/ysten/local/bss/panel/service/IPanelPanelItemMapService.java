package com.ysten.local.bss.panel.service;

import java.util.List;

import com.ysten.local.bss.panel.domain.PanelPanelItemMap;

/**
 * @author cwang
 * @version 2014-5-23 下午4:14:21
 * 
 */
public interface IPanelPanelItemMapService {
	
	int deleteByPrimaryKey(Long id);

	int insert(PanelPanelItemMap record);

	PanelPanelItemMap selectByPrimaryKey(Long id);

//	int updateByPrimaryKey(PanelPanelItemMap record);
	
	List<Long> findListByEpgPanelItem(List<Long> panelIdList);
}
