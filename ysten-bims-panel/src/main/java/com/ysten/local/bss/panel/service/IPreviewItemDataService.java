package com.ysten.local.bss.panel.service;

import com.ysten.local.bss.panel.domain.PreviewItemData;

/**
 * @author cwang
 * @version 2014-5-23 下午12:01:04
 * 
 */
public interface IPreviewItemDataService {
	
    void deleteByPrimaryKey(Long id);

    void savePreviewItemData(PreviewItemData record);

	PreviewItemData getPreviewItemDataById(Long id);

	PreviewItemData getPreviewItemDataByPanelItemId(Long contentItemId,Long panelId);

    void updateByPrimaryKeySelective(PreviewItemData record);

    void updateByPrimaryKey(PreviewItemData record);

    /**
     * delete all the epg data
     * @return
     */
//    void deleteByEpgIoid();
}
