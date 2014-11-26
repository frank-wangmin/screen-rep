package com.ysten.local.bss.panel.repository.impl;

import com.ysten.cache.annotation.*;
import com.ysten.local.bss.util.bean.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.ysten.local.bss.panel.domain.PreviewItemData;
import com.ysten.local.bss.panel.repository.IPreviewItemDataRepository;
import com.ysten.local.bss.panel.repository.mapper.PreviewItemDataMapper;

import java.util.List;

/**
 * @author cwang
 * @version 2014-5-23 上午11:57:26
 */
@Repository
public class PreviewItemDataRepository implements IPreviewItemDataRepository {
    @Autowired
    PreviewItemDataMapper previewItemDataMapper;

    private static final String PREVIEW_ITEM_DATA_ID = Constant.PANEL_PACKAGE + "preview_item_data:id:";

    private static final String PREVIEW_ITEM_DATA_PANEL = Constant.PANEL_PACKAGE + "preview_item_data:panelId:";

    @Override
    @MethodFlush(keys = {PREVIEW_ITEM_DATA_ID + "#{previewItemData.id}"})
//    @MethodFlushBootsrap
    @MethodFlushInterfacePackage
    public void deletePreviewItemData(@KeyParam("previewItemData") PreviewItemData previewItemData) {
        previewItemDataMapper.deleteByPrimaryKey(previewItemData.getId());
    }

    @Override
    public void savePreviewItemData(PreviewItemData record) {
        previewItemDataMapper.save(record);
    }

    @Override
//    @MethodFlushBootsrap
    @MethodFlushInterfacePackage
    public void batchSavePreviewItemData(List<PreviewItemData> list) {
        previewItemDataMapper.batchSave(list);
    }

    @Override
    public void batchSaveEpgPreviewItemData(List<PreviewItemData> list) {
        previewItemDataMapper.batchSave(list);
    }


    @Override
    @MethodCache(key = PREVIEW_ITEM_DATA_ID + "#{id}")
    public PreviewItemData selectByPrimaryKey(@KeyParam("id") Long id) {
        return previewItemDataMapper.selectByPrimaryKey(id);
    }

    @Override
    @MethodFlush(keys = {PREVIEW_ITEM_DATA_ID + "#{record.id}"})
//    @MethodFlushBootsrap
    @MethodFlushInterfacePackage
    public void updatePreviewItemData(@KeyParam("record") PreviewItemData record) {
        previewItemDataMapper.update(record);
    }

    @Override
    public PreviewItemData selectByPanelItemId(Long contentItemId, Long panelId) {
        return previewItemDataMapper.selectByPanelItemIdAndPanelId(contentItemId, panelId);
    }

    @Override
    public List<PreviewItemData> getItemDataByPanelId(@KeyParam("panelId") Long panelId) {
        return previewItemDataMapper.getItemDataByPanelId(panelId);
    }

    @Override
    public List<PreviewItemData> getItemDataByPanelIdAndTemplateId(Long templateId, Long panelId) {
        return previewItemDataMapper.getItemDataByPanelIdAndTemplateId(templateId, panelId);
    }

    @Override
    public List<PreviewItemData> checkMapBwtPanelIdAndPanelItem(Long panelId) {
        return previewItemDataMapper.checkMapBwtPanelIdAndPanelItem(panelId);
    }


    @Override
    public List<PreviewItemData> findAllProvinceDatas() {
        return previewItemDataMapper.findAllProvinceDatas();
    }

    @Override
    public List<PreviewItemData> findAllDatas() {
        return previewItemDataMapper.findAllDatas();
    }

/*    @Override
//    @MethodFlushAllPanel
//    @MethodFlushBootsrap
    @MethodFlushInterfacePackage
    public void deleteByEpgIoid() {
        previewItemDataMapper.deleteByEpgIoid();
    }*/

    public void deleteByEpgID(String districtCode) {
        previewItemDataMapper.deleteByEpgIoid(districtCode);
    }
}
