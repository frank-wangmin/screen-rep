package com.ysten.local.bss.panel.repository;

import com.ysten.local.bss.panel.domain.PanelPanelItemMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**  
 * @author cwang
 * @version 
 * 2014-5-23 下午4:15:27  
 * 
 */
public interface IPanelPanelItemMapRepository {

    int deletePanelPanelItemMap(Long id);

    int savePanelPanelItemMap(PanelPanelItemMap record);

    int batchSavePanelPanelItemMap(List<PanelPanelItemMap> list);

    int batchSaveEpgMap(List<PanelPanelItemMap> list);

    PanelPanelItemMap selectByPrimaryKey(Long id);

    int updateByPrimaryKey(PanelPanelItemMap record);

    Integer deleteByPanelId(Long panelId);

    Integer deleteByPanelItemId(Long panelItemId);

    PanelPanelItemMap selectByPanelIdAndPanelItemId(Long panelId,Long panelItemId);

    List<PanelPanelItemMap> findMapListByPanelId(@Param("panelId") Long panelId);

    List<PanelPanelItemMap> findMapList();

    List<PanelPanelItemMap> findProvinceMapList();

    /**
     * delete all the epg data
     * @return
     */
//    int deleteByEpgRelId();

    public int deleteByEpgRelationId(String districtCode);
    List<PanelPanelItemMap> findListByEpgPanelItem(List<Long> panelIdList);

}

