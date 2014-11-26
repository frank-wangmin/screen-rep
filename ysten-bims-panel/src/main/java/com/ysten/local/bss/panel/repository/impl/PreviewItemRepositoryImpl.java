package com.ysten.local.bss.panel.repository.impl;

import com.ysten.cache.annotation.KeyParam;
import com.ysten.cache.annotation.MethodCache;
import com.ysten.cache.annotation.MethodFlush;
import com.ysten.cache.annotation.MethodFlushAllPanel;
import com.ysten.local.bss.panel.domain.PreviewItem;
import com.ysten.local.bss.panel.repository.IPreviewItemRepository;
import com.ysten.local.bss.panel.repository.mapper.PreviewItemMapper;
import com.ysten.local.bss.util.bean.Constant;
import com.ysten.utils.page.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by frank on 14-5-16.
 */
@Repository
public class PreviewItemRepositoryImpl implements IPreviewItemRepository {

    @Autowired
    private PreviewItemMapper previewItemMapper;

    private static final String PREVIEW_ITEM_ID = Constant.PANEL_PACKAGE + "preview_item:id:";

//    private static final String PREVIEW_ITEM_TEMPLATE_ID = Constant.PANEL_PACKAGE + "preview_item:template_id:";

    @Override
    @MethodCache(key = PREVIEW_ITEM_ID + "#{previewItemId}")
    public PreviewItem getPreviewItemById(@KeyParam("previewItemId") Long previewItemId) {
        return previewItemMapper.getPreviewItemById(previewItemId);
    }

    @Override
    public void insert(PreviewItem previewItem) {
        previewItemMapper.insert(previewItem);
    }

    @Override
    public void batchSavePreviewItem(List<PreviewItem> list) {
        previewItemMapper.batchSavePreviewItem(list);
    }


    @Override
    @MethodFlush(keys = {PREVIEW_ITEM_ID + "#{previewItem.id}"})
    public void deleteTarget(@KeyParam("previewItem") PreviewItem previewItem) {
        previewItemMapper.deleteById(previewItem.getId());
    }

    @Override
    @MethodFlush(keys = {PREVIEW_ITEM_ID + "#{previewItem.id}"})
    public void updatePreviewItem(@KeyParam("previewItem") PreviewItem previewItem) {
        previewItemMapper.updatePreviewItem(previewItem);
    }


    @Override
    public Pageable<PreviewItem> getPreviewItemList(Integer pageNo, Integer pageSize) {
        List<PreviewItem> itemList = previewItemMapper.getPreviewItemList(pageNo, pageSize);
        Integer total = previewItemMapper.getPreviewItemCount();
        return new Pageable<PreviewItem>().instanceByPageNo(itemList, total, pageNo, pageSize);
    }


    @Override
    public List<PreviewItem> getPreviewItemListByTemplateId(Long templateId) {
        return previewItemMapper.getPreviewItemListByTemplateId(templateId);
    }

    @Override
    public List<PreviewItem> findAllItems() {
        return previewItemMapper.findAllItems();
    }

    @Override
    @MethodFlushAllPanel
    public void deleteByEpgIoid() {
        previewItemMapper.deleteByEpgIoid();
    }


}
