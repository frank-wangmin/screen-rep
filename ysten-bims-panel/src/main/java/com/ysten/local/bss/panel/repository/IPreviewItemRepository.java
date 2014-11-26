package com.ysten.local.bss.panel.repository;

import com.ysten.local.bss.panel.domain.PreviewItem;
import com.ysten.utils.page.Pageable;
import java.util.List;

/**
 * Created by frank on 14-5-16.
 */
public interface IPreviewItemRepository {

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

    void batchSavePreviewItem(List<PreviewItem> list);

    void deleteTarget(PreviewItem previewItem);

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
    Pageable<PreviewItem> getPreviewItemList(Integer pageNo,Integer pageSize);


    List<PreviewItem> getPreviewItemListByTemplateId(Long templateId);

    List<PreviewItem> findAllItems();

    /**
     *  delete all the epg data
     * @return
     */
    void deleteByEpgIoid();
}
