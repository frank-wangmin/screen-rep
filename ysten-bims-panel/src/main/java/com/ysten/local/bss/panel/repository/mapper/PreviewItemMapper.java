package com.ysten.local.bss.panel.repository.mapper;

import com.ysten.local.bss.panel.domain.PreviewItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by frank on 14-5-16.
 */
public interface PreviewItemMapper {

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
    Integer insert(PreviewItem previewItem);


    Integer batchSavePreviewItem(List<PreviewItem> list);


    /**
     * 根据IDs批量删除previewItem
     *
     * @param ids
     * @return
     */
    Integer deleteByIds(@Param("ids") List<Long> ids);

    Integer deleteById(Long id);

    Integer deleteByTemplateId(@Param("templateId") Long templateId);

    List<PreviewItem> getPreviewItemListByTemplateId(@Param("templateId") Long templateId);

    /**
     * 更新panel
     *
     * @param previewItem
     * @return
     */
    Integer updatePreviewItem(PreviewItem previewItem);

    /**
     * get all previewItem list
     *
     * @return
     */
    List<PreviewItem> getPreviewItemList(@Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

    /**
     * get all previewItem number
     *
     * @return
     */
    Integer getPreviewItemCount();

    List<PreviewItem> findAllItems();

    /**
     *  delete all the epg data
     * @return
     */
    int deleteByEpgIoid();
}
