package com.ysten.local.bss.panel.repository.mapper;

import com.ysten.local.bss.device.domain.Device;
import com.ysten.local.bss.panel.domain.Panel;
import com.ysten.local.bss.panel.domain.PanelPackageMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by frank on 14-5-20.
 */
public interface PanelPackageMapMapper {

    Integer insert(PanelPackageMap panelPackageMap);

    Integer batchInsert(List<PanelPackageMap> list);

    List<PanelPackageMap> verifyIfExistBinded(@Param("panelId") Long panelId, @Param("packageId") Long packageId);

    String getNavIds(@Param("panelId") Long panelId, @Param("packageId") Long packageId);

    Integer deleteByPanelId(@Param("panelId") Long panelId);

    Integer deleteByPackagelId(@Param("packageId") Long packageId);

    List<PanelPackageMap> getMapByPackageId(@Param("packageId") Long packageId);
    
    List<PanelPackageMap> getMapByPanelIds(Map<String, Object> map);

    PanelPackageMap getMapByPackageIdAndPanelId(@Param("packageId") Long packageId,@Param("panelId") Long panelId);

    List<PanelPackageMap> getPaginationMapByPackageId(@Param("packageId") Long packageId, @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

    Integer deleteByPackagelIdAndPanelId(@Param("packageId") Long packageId, @Param("panelIds") List<Long> panelIds);

    Integer deleteMapByPackagelIdAndPanelId(@Param("packageId") Long packageId, @Param("panelId") Long panelId);

    List<PanelPackageMap> getMapByNavId(@Param("navIdBetween") String navIdBetween, @Param("navIdEnd") String navIdEnd, @Param("navIdStart") String navIdStart, @Param("navId") String navId);

    Integer updateMap(PanelPackageMap panelPackageMap);

    void updateSort(PanelPackageMap panelPackageMap);

    List<PanelPackageMap> findMapList();

    /**
     * delete all the epg data
     *
     * @return
     */
    int deleteByEpgRelId();

    /**
     * find the custom maps with epgPanel or epgNav
     *
     * @return list
     */
    List<PanelPackageMap> findCustomMapsWithEpgData();
}
