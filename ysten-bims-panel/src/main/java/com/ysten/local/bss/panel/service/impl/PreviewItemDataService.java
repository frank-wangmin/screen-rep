package com.ysten.local.bss.panel.service.impl;

import com.ysten.local.bss.panel.domain.PreviewItemData;
import com.ysten.local.bss.panel.repository.IPreviewItemDataRepository;
import com.ysten.local.bss.panel.service.IPreviewItemDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author cwang
 * @version 2014-5-23 下午12:01:27
 */
@Service
public class PreviewItemDataService implements IPreviewItemDataService {
	
    @Autowired
    private IPreviewItemDataRepository previewItemDataRepository;

    @Override
    public void deleteByPrimaryKey(Long id) {
        PreviewItemData previewItemData = previewItemDataRepository.selectByPrimaryKey(id);
        if (previewItemData != null) {
            previewItemDataRepository.deletePreviewItemData(previewItemData);
        }
    }

    @Override
    public void savePreviewItemData(PreviewItemData record) {
        previewItemDataRepository.savePreviewItemData(record);
    }

    @Override
    public PreviewItemData getPreviewItemDataById(Long id) {
        return previewItemDataRepository.selectByPrimaryKey(id);
    }

    @Override
    public void updateByPrimaryKeySelective(PreviewItemData record) {
        previewItemDataRepository.updatePreviewItemData(record);
    }

    @Override
    public void updateByPrimaryKey(PreviewItemData record) {
        previewItemDataRepository.updatePreviewItemData(record);
    }

    @Override
    public PreviewItemData getPreviewItemDataByPanelItemId(Long contentItemId, Long panelId) {
        return previewItemDataRepository.selectByPanelItemId(contentItemId, panelId);
    }
}