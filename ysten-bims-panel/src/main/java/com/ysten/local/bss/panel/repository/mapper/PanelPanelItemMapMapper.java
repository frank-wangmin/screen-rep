package com.ysten.local.bss.panel.repository.mapper;

import com.ysten.local.bss.panel.domain.PanelPanelItemMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PanelPanelItemMapMapper {

    int deleteByPrimaryKey(Long id);

    int insert(PanelPanelItemMap record);

    PanelPanelItemMap selectByPrimaryKey(Long id);

    PanelPanelItemMap selectByPanelIdAndPanelItemId(@Param("panelId") Long panelId,@Param("panelItemId") Long panelItemId);

    int updateByPrimaryKey(PanelPanelItemMap record);

    Integer deleteByPanelId(Long panelId);

    Integer deleteByPanelItemId(Long panelItemId);

    List<PanelPanelItemMap> findMapListByPanelId(@Param("panelId") Long panelId);

    List<PanelPanelItemMap> findMapList();

    List<PanelPanelItemMap> findProvinceMapList();


    int deleteByEpgRelId(@Param("districtCode")String districtCode);

    int batchInsert(List<PanelPanelItemMap> list);
    
    List<PanelPanelItemMap> findListByEpgPanelItem(@Param("panelIdList")List<Long> panelIdList);
}