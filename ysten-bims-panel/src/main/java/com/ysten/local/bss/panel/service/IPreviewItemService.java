package com.ysten.local.bss.panel.service;

import com.ysten.local.bss.panel.domain.PreviewItem;
import com.ysten.utils.page.Pageable;

import java.util.List;

/**
 * Created by frank on 14-5-16.
 */
public interface IPreviewItemService {

    /**
     * 根据ID查询previewItem
     *
     * @param previewItemId
     * @return
     */
    PreviewItem getPreviewItemById(Long previewItemId);

    /**
     * 新增previewItem
     *
     * @param previewItem
     * @return
     */
    void insert(PreviewItem previewItem);

    /**
     * 根据IDs批量删除previewItem
     *
     * @param ids
     * @return
     */
    void deleteByIds(List<Long> ids);

    /**
     * 更新panel
     *
     * @param previewItem
     * @return
     */
    void updatePreviewItem(PreviewItem previewItem);

    /**
     * get all previewItem list
     *
     * @return
     */
    Pageable<PreviewItem> getPreviewItemList(Integer pageNo, Integer pageSize);

    void insertBatchPreviewItem(PreviewItem[] previewItems);

    List<PreviewItem> getPreviewItemListByTemplateId(Long templateId);

    /**
     *  delete all the epg data
     * @return
     */
    void deleteByEpgIoid();
}
