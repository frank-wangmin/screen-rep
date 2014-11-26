package com.ysten.local.bss.panel.repository;

import com.ysten.local.bss.panel.domain.PreviewTemplate;
import com.ysten.utils.page.Pageable;

import java.util.List;

/**
 * Created by frank on 14-5-16.
 */
public interface IPreviewTemplateRepository {

    PreviewTemplate getTargetById(Long id);

    void insert(PreviewTemplate previewTemplate);

    void deleteTarget(PreviewTemplate previewTemplate);

    void update(PreviewTemplate previewTemplate);

    Pageable<PreviewTemplate> getTargetList(Long Id,String name, Integer pageNo, Integer pageSize);

    List<PreviewTemplate> findAllCustomedTargetList();

    List<PreviewTemplate> findAllOuterList();

    List<PreviewTemplate> findAllEpgList();

    List<PreviewTemplate> findIdAndEpgIdList();

    /**
     * Get the template by epgTemplateId
     * @param epgTemplateId
     * @return previewTemplate
     */
    PreviewTemplate getTemplateByEpgTempId(Long epgTemplateId);


    /**
     *  delete all the epg data
     * @return
     */
    void deleteByEpgIds();

    void batchSavePreviewTemplate(List<PreviewTemplate> list);

    void batchUpdate(List<PreviewTemplate> list);

}
