package com.ysten.local.bss.panel.service.impl;

import com.ysten.local.bss.panel.domain.PreviewItem;
import com.ysten.local.bss.panel.repository.IPreviewItemRepository;
import com.ysten.local.bss.panel.repository.IPreviewTemplateRepository;
import com.ysten.local.bss.panel.service.IPreviewItemService;
import com.ysten.utils.page.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by frank on 14-5-16.
 */
@Service
public class PreviewItemServiceImpl implements IPreviewItemService {

    @Autowired
    private IPreviewItemRepository previewItemRepository;
    @Autowired
    IPreviewTemplateRepository previewTemplateRepository;

    @Override
    public PreviewItem getPreviewItemById(Long previewItemId) {
        return previewItemRepository.getPreviewItemById(previewItemId);
    }

    @Override
    public void insert(PreviewItem previewItem) {
        previewItemRepository.insert(previewItem);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        for (Long id : ids) {
            PreviewItem previewItem = previewItemRepository.getPreviewItemById(id);
            if (previewItem != null) previewItemRepository.deleteTarget(previewItem);
        }
    }

    @Override
    public void updatePreviewItem(PreviewItem previewItem) {
        previewItemRepository.updatePreviewItem(previewItem);
    }

    @Override
    public Pageable<PreviewItem> getPreviewItemList(Integer pageNo, Integer pageSize) {
        return previewItemRepository.getPreviewItemList(pageNo, pageSize);
    }

    @Override
    public void insertBatchPreviewItem(PreviewItem[] previewItems) {
        if (previewItems != null && previewItems.length > 0) {
            Long templateId = previewItems[0].getTemplateId();
            List<PreviewItem> previewItemList = previewItemRepository.getPreviewItemListByTemplateId(templateId);
            if (!CollectionUtils.isEmpty(previewItemList)) {
                for (PreviewItem previewItem : previewItemList) {
                    previewItemRepository.deleteTarget(previewItem);
                }
            }
            for (PreviewItem previewItem : previewItems) {
                previewItemRepository.insert(previewItem);
            }
        }
    }

    @Override
    public List<PreviewItem> getPreviewItemListByTemplateId(Long templateId) {
        return previewItemRepository.getPreviewItemListByTemplateId(templateId);
    }

    @Override
    public void deleteByEpgIoid() {
        previewItemRepository.deleteByEpgIoid();
    }


}
