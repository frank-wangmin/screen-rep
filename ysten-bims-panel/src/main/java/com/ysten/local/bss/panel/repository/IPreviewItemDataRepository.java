
package com.ysten.local.bss.panel.repository;

import com.ysten.local.bss.panel.domain.PreviewItemData;
import java.util.List;

/**  
 * @author cwang
 * @version 
 * 2014-5-23 上午11:55:53  
 * 
 */
public interface IPreviewItemDataRepository {

    void deletePreviewItemData(PreviewItemData previewItemData);

	void savePreviewItemData(PreviewItemData record);

    void batchSavePreviewItemData(List<PreviewItemData> list);

    void batchSaveEpgPreviewItemData(List<PreviewItemData> list);

	PreviewItemData selectByPrimaryKey(Long id);

	PreviewItemData selectByPanelItemId(Long contentItemId,Long panelId);

    void updatePreviewItemData(PreviewItemData record);

    List<PreviewItemData> getItemDataByPanelId(Long panelId);

    List<PreviewItemData> getItemDataByPanelIdAndTemplateId(Long templateId,Long panelId);

    List<PreviewItemData> checkMapBwtPanelIdAndPanelItem(Long panelId);

    List<PreviewItemData> findAllDatas();

    List<PreviewItemData> findAllProvinceDatas();


//    void deleteByEpgIoid();

    public void deleteByEpgID(String districtCode);
}

