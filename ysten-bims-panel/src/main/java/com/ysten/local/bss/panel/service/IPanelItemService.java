package com.ysten.local.bss.panel.service;

import com.ysten.local.bss.panel.domain.PanelItem;
import com.ysten.local.bss.panel.vo.PanelQueryCriteria;
import com.ysten.utils.page.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Created by frank on 14-5-19.
 */
public interface IPanelItemService {

    Pageable<PanelItem> getPanelItemListByPanelId(Long panelId, Integer pageNo, Integer pageSize);

    List<PanelItem> findPanelItemListByDpi(String dpi);

    List<PanelItem> findSublItemListByPanelItemParentId(Long panelItemParentId);

    /**
     * success:新增成功
     * fail：新增失败
     * related：关联失败（不可重复关联面板项）
     *
     * @param panelItem
     * @return
     */
    String savePanelItemData(PanelItem panelItem);

    void savePanelItem(PanelItem panelItem);

    void deletePanelItemByIds(List<Long> ids);

    void updatePanelItem(PanelItem panelItem);

    /**
     * success:更新成功
     * fail：更新失败
     * related：关联失败（不可重复关联面板项）
     *
     * @param panelItem
     * @return
     */
    String updatePanelItemData(PanelItem panelItem,Long oprUserId);

    Pageable<PanelItem> findgetPanelItemList(PanelQueryCriteria panelQueryCriteria);

    PanelItem getPanelItemById(Long id);

    PanelItem getPanelItemDetail(Long id);
    
    PanelItem getPanelItemByName(String name);

    /**
     * 获取父面板项
     *
     * @return
     */
    List<PanelItem> selectParentItemList(Map<String, Object> map);

    List<PanelItem> selectRelatedItemList(Map<String, Object> map);

    List<PanelItem> getAllTargetList();

//    List<PanelItem> getNotRefPanelItemList();

//    List<PanelItem> getNotRefPanelItemListExcludeSelf(Long editPanelItemId);
}
