package com.ysten.local.bss.panel.service.impl;

import com.ysten.boss.systemconfig.repository.ISystemConfigRepository;
import com.ysten.local.bss.panel.domain.PreviewItem;
import com.ysten.local.bss.panel.domain.PreviewTemplate;
import com.ysten.local.bss.panel.repository.IPreviewItemRepository;
import com.ysten.local.bss.panel.repository.IPreviewTemplateRepository;
import com.ysten.local.bss.panel.service.IPreviewTemplateService;
import com.ysten.local.bss.util.DateUtils;
import com.ysten.utils.page.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by frank on 14-5-16.
 */
@Service
public class PreviewTemplateServiceImpl implements IPreviewTemplateService {

    @Autowired
    private IPreviewTemplateRepository previewTemplateRepository;

    @Autowired
    private ISystemConfigRepository systemConfigRepository;

    @Autowired
    private IPreviewItemRepository previewItemRepository;

    @Override
    public PreviewTemplate getTargetById(Long id) {
        return previewTemplateRepository.getTargetById(id);
    }

    private String getCurrentDistrictCode() {
        return systemConfigRepository.getSystemConfigByConfigKey("deployDistrictCode").getConfigValue();
    }

    @Override
    public void insert(PreviewTemplate previewTemplate) {
        previewTemplate.setDistrictCode(getCurrentDistrictCode());
        previewTemplateRepository.insert(previewTemplate);
    }

    @Override
    public void deleteByIds(List<Long> ids) {

        //delete related preview items
        for (Long templateId : ids) {
            List<PreviewItem> previewItemList = previewItemRepository.getPreviewItemListByTemplateId(templateId);
            if (!CollectionUtils.isEmpty(previewItemList)) {
                for (PreviewItem previewItem : previewItemList) {
                    previewItemRepository.deleteTarget(previewItem);
                }
            }

            PreviewTemplate previewTemplate = previewTemplateRepository.getTargetById(templateId);
            if (previewTemplate != null) previewTemplateRepository.deleteTarget(previewTemplate);
        }
    }

    @Override
    public void update(PreviewTemplate previewTemplate,Long oprUserId) {
        previewTemplate.setOprUserId(oprUserId);
        previewTemplate.setCreateTime(DateUtils.getCurrentDate());
        previewTemplate.setUpdateTime(DateUtils.getCurrentDate());
        previewTemplateRepository.update(previewTemplate);
    }

    @Override
    public Pageable<PreviewTemplate> getTargetList(Long Id,String name, Integer pageNo, Integer pageSize) {
        return previewTemplateRepository.getTargetList(Id,name, pageNo, pageSize);
    }

    @Override
    public List<PreviewTemplate> findAllCustomedTargetList() {
        return previewTemplateRepository.findAllCustomedTargetList();
    }

    @Override
    public List<PreviewTemplate> findAllOuterList() {
        return previewTemplateRepository.findAllOuterList();
    }

    @Override
    public void deleteByEpgIds() {
        previewTemplateRepository.deleteByEpgIds();
    }
}
