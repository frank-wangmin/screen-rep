package com.ysten.local.bss.panel.service;

import com.ysten.local.bss.panel.domain.PreviewTemplate;
import com.ysten.utils.page.Pageable;

import java.util.List;

/**
 * Created by frank on 14-5-16.
 */
public interface IPreviewTemplateService {

    PreviewTemplate getTargetById(Long id);

    void insert(PreviewTemplate previewTemplate);

    void deleteByIds(List<Long> ids);

    void update(PreviewTemplate previewTemplate,Long oprUserId);

    Pageable<PreviewTemplate> getTargetList(Long Id,String name, Integer pageNo, Integer pageSize);

    List<PreviewTemplate> findAllCustomedTargetList();

    public List<PreviewTemplate> findAllOuterList();

    /**
     *  delete all the epg data
     * @return
     */
    void deleteByEpgIds();
}
