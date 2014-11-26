package com.ysten.local.bss.panel.repository.mapper;

import com.ysten.local.bss.panel.domain.PreviewItemData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * @author cwang
 *
 */
public interface PreviewItemDataMapper {

	int deleteByPrimaryKey(Long id);

	int save(PreviewItemData record);

    int batchSave(List<PreviewItemData> list);

    PreviewItemData selectByPrimaryKey(Long id);

	PreviewItemData selectByPanelItemIdAndPanelId(@Param("contentItemId")Long contentItemId,@Param("panelId")Long panelId);
	
	int update(PreviewItemData record);

    Integer deleteByPanelId(Long panelId);

    List<PreviewItemData> getItemDataByPanelIdAndTemplateId(@Param("templateId") Long templateId,@Param("panelId") Long panelId);

    List<PreviewItemData> getItemDataByPanelId(@Param("panelId") Long panelId);

    List<PreviewItemData> checkMapBwtPanelIdAndPanelItem(@Param("panelId") Long panelId);

    List<PreviewItemData> findAllDatas();

    List<PreviewItemData> findAllProvinceDatas();


    /**
     * delete all the epg data
     * @return
     */
    int deleteByEpgIoid(String districtCode);

}