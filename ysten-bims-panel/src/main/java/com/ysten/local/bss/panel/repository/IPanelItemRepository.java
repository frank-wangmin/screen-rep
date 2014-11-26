package com.ysten.local.bss.panel.repository;

import com.ysten.local.bss.panel.domain.PanelItem;
import com.ysten.local.bss.panel.vo.PanelQueryCriteria;
import com.ysten.utils.page.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Created by frank on 14-5-9.
 */
public interface IPanelItemRepository {

    Pageable<PanelItem> findPanelItemListByPanelId(Long panelId, Integer pageNo, Integer pageSize);

    List<PanelItem> findPanelItemListByDpi(String dpi);

    List<PanelItem> findPanelItemListByPanelIdList(List<Long> panelId);

    List<PanelItem> findSublItemListByPanelItemParentId(Long panelItemParentId);

    void savePanelItem(PanelItem panelItem);

    void deletePanelItem(PanelItem panelItem);

    void updatePanelItem(PanelItem panelItem);

    void updateEpgPanelItem(PanelItem panelItem);

    void batchUpdatePanelItem(List<PanelItem> list);

    void batchUpdateRel(List<PanelItem> list);

    Pageable<PanelItem> findPanelItemList(PanelQueryCriteria panelQueryCriteria);

    PanelItem getPanelItemById(Long id);

    List<PanelItem> findParentItemList(Map<String, Object> map);

    List<PanelItem> findRelatedItemList(Map<String, Object> map);

    List<PanelItem> findRelatedOrParentItemList(Long panelItemId);

    List<PanelItem> findAllPanelItemList();

    List<PanelItem> findIdAndEpgIdList(String districtCode);

    List<PanelItem> findAllPanelItemEpgList(String districtCode);

    List<PanelItem> findAllProvinceList();

    List<PanelItem> findPanelItemByHasSubItem(Integer hasSubItem);

    List<PanelItem> findNotRefPanelItemList(Integer hasSubItem, String refType);

    List<PanelItem> findNotRefPanelItemListExcludeSelf(Integer hasSubItem, String refType, Long editPanelItemId);

    PanelItem getPanelItemByEpgItemId(Long epgPanelItemId);

    void deleteByEpgIds();

    PanelItem getPanelItemByName(String name);

    void batchSavePanelItem(List<PanelItem> list);
}
