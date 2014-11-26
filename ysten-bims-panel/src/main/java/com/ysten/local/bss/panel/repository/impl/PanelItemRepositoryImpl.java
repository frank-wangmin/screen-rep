package com.ysten.local.bss.panel.repository.impl;

import com.ysten.cache.annotation.*;
import com.ysten.local.bss.panel.domain.PanelItem;
import com.ysten.local.bss.panel.repository.IPanelItemRepository;
import com.ysten.local.bss.panel.repository.mapper.PanelItemMapper;
import com.ysten.local.bss.panel.vo.PanelQueryCriteria;
import com.ysten.local.bss.util.bean.Constant;
import com.ysten.utils.page.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by frank on 14-5-9.
 */
@Repository
public class PanelItemRepositoryImpl implements IPanelItemRepository {

    @Autowired
    private PanelItemMapper panelItemMapper;

    private static final String PANEL_ITEM_ID = Constant.PANEL_PACKAGE + "panel_item:id:";

//    private static final String PANEL_ITEM_EPG_ID = Constant.PANEL_PACKAGE + "panel_item:epg_id:";

    @Override
    public Pageable<PanelItem> findPanelItemListByPanelId(Long panelId, Integer pageNo, Integer pageSize) {
        List<PanelItem> panelItems = panelItemMapper.getPanelItemListByPanelId(panelId, pageNo, pageSize);
        Integer total = panelItemMapper.getPanelItemCountByPanelId(panelId);
        return new Pageable<PanelItem>().instanceByPageNo(panelItems, total, pageNo, pageSize);
    }

    @Override
    public void savePanelItem(PanelItem panelItem) {
        panelItemMapper.save(panelItem);
    }

    @Override
    @MethodFlush(keys = {PANEL_ITEM_ID + "#{panelItem.id}"})
//    @MethodFlushBootsrap
    @MethodFlushInterfacePackage
    public void deletePanelItem(@KeyParam("panelItem") PanelItem panelItem) {
        panelItemMapper.deleteById(panelItem.getId());
    }

    @Override
//    @MethodFlushBootsrap
    @MethodFlushInterfacePackage
    @MethodFlush(keys = {PANEL_ITEM_ID + "#{panelItem.id}"})
    public void updatePanelItem(@KeyParam("panelItem") PanelItem panelItem) {
        panelItemMapper.update(panelItem);
    }

    @Override
    public void updateEpgPanelItem(PanelItem panelItem){
        panelItemMapper.update(panelItem);
    }

    @Override
//    @MethodFlushAllPanel
//    @MethodFlushBootsrap
    @MethodFlushInterfacePackage
    public void batchUpdateRel(List<PanelItem> list) {
        panelItemMapper.batchUpdateRel(list);
    }

    @Override
//    @MethodFlushAllPanel
//    @MethodFlushBootsrap
    @MethodFlushInterfacePackage
    public void batchUpdatePanelItem(List<PanelItem> list) {
        panelItemMapper.batchUpdatePanelItem(list);
    }


    @Override
    public Pageable<PanelItem> findPanelItemList(PanelQueryCriteria panelQueryCriteria) {
        List<PanelItem> panelItems = panelItemMapper.getTargetList(panelQueryCriteria);
        Integer total = panelItemMapper.getTargetCount(panelQueryCriteria);
        return new Pageable<PanelItem>().instanceByPageNo(panelItems, total, panelQueryCriteria.getStart(), panelQueryCriteria.getLimit());
    }

    @Override
    @MethodCache(key = PANEL_ITEM_ID + "#{id}")
    public PanelItem getPanelItemById(@KeyParam("id") Long id) {
        return panelItemMapper.getTargetById(id);
    }

    @Override
    public List<PanelItem> findParentItemList(Map<String, Object> map) {
        return panelItemMapper.selectParentItemList(map);
    }

    @Override
    public List<PanelItem> findRelatedItemList(Map<String, Object> map) {
        return panelItemMapper.selectRelatedItemList(map);
    }

    @Override
    public List<PanelItem> findRelatedOrParentItemList(Long panelItemId) {
        return panelItemMapper.selectRelatedOrParentItemList(panelItemId);
    }

    @Override
    public List<PanelItem> findAllPanelItemList() {
        return panelItemMapper.getAllTargetList();
    }


    @Override
    public List<PanelItem> findAllProvinceList() {
        return panelItemMapper.findAllProvinceList();
    }

    @Override
    public List<PanelItem> findAllPanelItemEpgList(String districtCode) {
        return panelItemMapper.getAllEpgList(districtCode);
    }

    @Override
    public List<PanelItem> findIdAndEpgIdList(String districtCode) {
        return panelItemMapper.findIdAndEpgIdList(districtCode);
    }


    @Override
    public List<PanelItem> findPanelItemListByDpi(String dpi) {
        return panelItemMapper.selectPanelItemListByDpi(dpi);
    }

    @Override
    public List<PanelItem> findSublItemListByPanelItemParentId(Long panelItemParentId) {
        return panelItemMapper.getSublItemListByPanelItemParentId(panelItemParentId);
    }

    @Override
    public List<PanelItem> findPanelItemByHasSubItem(Integer hasSubItem) {
        return panelItemMapper.getPanelItemByHasSubItem(hasSubItem);
    }

    @Override
    public List<PanelItem> findNotRefPanelItemList(Integer hasSubItem, String refType) {
        return panelItemMapper.getNotRefPanelItemList(hasSubItem, refType);
    }

    @Override
    public List<PanelItem> findNotRefPanelItemListExcludeSelf(Integer hasSubItem, String refType, Long editPanelItemId) {
        return panelItemMapper.getNotRefPanelItemListExcludeSelf(hasSubItem, refType, editPanelItemId);
    }

    @Override
    public PanelItem getPanelItemByEpgItemId(Long epgPanelItemId) {
        return panelItemMapper.getPanelItemByEpgItemId(epgPanelItemId);
    }

    @Override
//    @MethodFlushAllPanel
//    @MethodFlushBootsrap
    @MethodFlushInterfacePackage
    public void deleteByEpgIds() {
        panelItemMapper.deleteByEpgIds();
    }

    @Override
    public PanelItem getPanelItemByName(String name) {
        return this.panelItemMapper.getPanelItemByName(name);
    }

    @Override
//    @MethodFlushBootsrap
    @MethodFlushInterfacePackage
    public void batchSavePanelItem(List<PanelItem> list) {
        panelItemMapper.batchSavePanelItem(list);
    }

    @Override
    public List<PanelItem> findPanelItemListByPanelIdList(List<Long> epgPanelId) {
        return panelItemMapper.selectPanelItemListByPanelId(epgPanelId);
    }
}
