package com.ysten.local.bss.panel.repository.impl;

import com.ysten.cache.annotation.KeyParam;
import com.ysten.cache.annotation.MethodCache;
import com.ysten.cache.annotation.MethodFlush;
import com.ysten.cache.annotation.MethodFlushAllPanel;
import com.ysten.local.bss.panel.domain.PreviewTemplate;
import com.ysten.local.bss.panel.repository.IPreviewTemplateRepository;
import com.ysten.local.bss.panel.repository.mapper.PreviewTemplateMapper;
import com.ysten.local.bss.util.bean.Constant;
import com.ysten.utils.page.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by frank on 14-5-16.
 */
@Repository
public class PreviewTemplateRepositoryImpl implements IPreviewTemplateRepository {

    @Autowired
    private PreviewTemplateMapper previewTemplateMapper;

    private static final String PREVIEW_TEMPLATE_ID = Constant.PANEL_PACKAGE + "preview_template:id:";

//    private static final String PREVIEW_TEMPLATE_EPG_ID = Constant.PANEL_PACKAGE + "preview_template:epg_template_id:";

    @Override
    @MethodCache(key = PREVIEW_TEMPLATE_ID + "#{id}")
    public PreviewTemplate getTargetById(@KeyParam("id") Long id) {
        return previewTemplateMapper.getTargetById(id);
    }

    @Override
    public void insert(PreviewTemplate previewTemplate) {
        previewTemplateMapper.insert(previewTemplate);
    }



    @Override
    @MethodFlush(keys = {PREVIEW_TEMPLATE_ID + "#{previewTemplate.id}"})
    public void deleteTarget(@KeyParam("previewTemplate") PreviewTemplate previewTemplate) {
        previewTemplateMapper.deleteById(previewTemplate.getId());
    }

    @Override
    @MethodFlush(keys = {PREVIEW_TEMPLATE_ID + "#{previewTemplate.id}"})
    public void update(@KeyParam("previewTemplate") PreviewTemplate previewTemplate) {
        previewTemplateMapper.update(previewTemplate);
    }


    @Override
    public Pageable<PreviewTemplate> getTargetList(Long Id,String name, Integer pageNo, Integer pageSize) {
        List<PreviewTemplate> templates = previewTemplateMapper.getTargetList(Id,name, pageNo, pageSize);
        Integer total = previewTemplateMapper.getTargetCount(Id,name);
        return new Pageable<PreviewTemplate>().instanceByPageNo(templates, total, pageNo, pageSize);
    }

    @Override
    public List<PreviewTemplate> findAllCustomedTargetList() {
        return previewTemplateMapper.findAllCustomedTargetList();
    }

    @Override
    public List<PreviewTemplate> findAllOuterList() {
        return previewTemplateMapper.findAllOuterList();
    }

    @Override
    public List<PreviewTemplate> findAllEpgList() {
        return previewTemplateMapper.findAllEpgList();
    }

    @Override
    public List<PreviewTemplate> findIdAndEpgIdList() {
        return previewTemplateMapper.findIdAndEpgIdList();
    }


    @Override
    public PreviewTemplate getTemplateByEpgTempId(Long epgTemplateId) {
        return previewTemplateMapper.getTemplateByEpgTempId(epgTemplateId);
    }


    @Override
    @MethodFlushAllPanel
    public void deleteByEpgIds() {
        previewTemplateMapper.deleteByEpgIds();
    }

    @Override
    public void batchSavePreviewTemplate(List<PreviewTemplate> list) {
        previewTemplateMapper.batchSavePreviewTemplate(list);
    }

    @Override
    @MethodFlushAllPanel
    public void batchUpdate(List<PreviewTemplate> list) {
        previewTemplateMapper.batchUpdate(list);
    }


}
