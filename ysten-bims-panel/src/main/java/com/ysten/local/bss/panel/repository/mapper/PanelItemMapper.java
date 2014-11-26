package com.ysten.local.bss.panel.repository.mapper;

import java.util.List;
import java.util.Map;

import com.ysten.local.bss.panel.vo.PanelQueryCriteria;
import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.panel.domain.PanelItem;

/**
 * Created by frank on 14-5-9.
 */
public interface PanelItemMapper {

    List<PanelItem> getPanelItemListByPanelId(@Param("panelId") Long panelId, @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

    List<PanelItem> selectPanelItemListByDpi(@Param("dpi") String dpi);

    List<PanelItem> getSublItemListByPanelItemParentId(@Param("panelItemParentId") Long panelItemParentId);

    Integer getPanelItemCountByPanelId(@Param("panelId") Long panelId);

    List<PanelItem> selectPanelItemListByPanelId(List<Long> epgPanelId);

    Integer save(PanelItem panelItem);

    Integer deleteByIds(@Param("ids") List<Long> ids);

    Integer deleteById(Long panelItemId);

    Integer update(PanelItem panelItem);

    Integer batchUpdatePanelItem(List<PanelItem> list);

    List<PanelItem> getTargetList(PanelQueryCriteria panelQueryCriteria);

    Integer getTargetCount(PanelQueryCriteria panelQueryCriteria);

    PanelItem getTargetById(@Param("id") Long id);

    /**
     * 获取内容类型为list（列表）的面板项
     *
     * @param contentType
     * @return
     */
//    List<PanelItem> selectParentItemList(@Param("contentType") String contentType, @Param("hasSubItem") Integer hasSubItem);

    List<PanelItem> selectParentItemList(Map<String, Object> map);

    List<PanelItem> selectRelatedItemList(Map<String, Object> map);

    List<PanelItem> selectRelatedOrParentItemList(@Param("panelItemId") Long panelItemId);

    List<PanelItem> getPanelItemByHasSubItem(@Param("hasSubItem") Integer hasSubItem);

    List<PanelItem> getNotRefPanelItemList(@Param("hasSubItem") Integer hasSubItem, @Param("refType") String refType);

    List<PanelItem> getNotRefPanelItemListExcludeSelf(@Param("hasSubItem") Integer hasSubItem, @Param("refType") String refType, @Param("editPanelItemId") Long editPanelItemId);

    List<PanelItem> getAllTargetList();

    List<PanelItem> getAllEpgList(@Param("districtCode")String districtCode);

    List<PanelItem> findAllProvinceList();

    List<PanelItem> findIdAndEpgIdList(@Param("districtCode")String districtCode);

    /**
     * get panelItem by epgPanelItemId
     * @param epgPanelItemId
     * @return panelItem
     */
    PanelItem getPanelItemByEpgItemId(@Param("epgPanelItemId") Long epgPanelItemId);

    /**
     * delete all the epg data
     * @return
     */
    Integer deleteByEpgIds();
    
    PanelItem getPanelItemByName(@Param("name") String name);

    Integer batchSavePanelItem(List<PanelItem> list);

    Integer batchUpdateRel(List<PanelItem> list);

}
