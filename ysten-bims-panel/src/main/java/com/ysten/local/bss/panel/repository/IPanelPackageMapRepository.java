
package com.ysten.local.bss.panel.repository;

import com.ysten.local.bss.panel.domain.Panel;
import com.ysten.local.bss.panel.domain.PanelPackageMap;

import java.util.List;
import java.util.Map;

/**
 * @author cwang
 * @version 2014-5-23 下午4:28:17
 */
public interface IPanelPackageMapRepository {
	
    void savePanelPackageMap(PanelPackageMap panelPackageMap);

    void batchSavePanelPackageMap(List<PanelPackageMap> list);

    void batchSaveEpgMap(List<PanelPackageMap> list);

    String getNavIds(Long panelId, Long packageId);

    void deletePanelPackageMapByPanelId(Long panelId);

    void deletePanelPackageMapByPackagelId(Long packageId);

    List<PanelPackageMap> findPanelPackageMapByPackageId(Long packageId);
    List<PanelPackageMap> getMapByPanelIds(Map<String, Object> map);
    List<PanelPackageMap> findPaginationMapByPackageId(Long packageId, Integer pageNo, Integer pageSize);

    List<PanelPackageMap> verifyIfExistBinded(Long panelId, Long packageId);

    List<PanelPackageMap> findMapByNavId(Long navId);

    void deleteByPackagelIdAndPanelId(Long packageId, List<Long> panelId);

    void deleteMapByPackagelIdAndPanelId(Long packageId,Long panelId);

    /**
     * find the custom maps with epgPanel or epgNav
     *
     * @return list
     */
    List<PanelPackageMap> findCustomMapsWithEpgData();

    void updateMap(PanelPackageMap panelPackageMap);

    void updateSort(PanelPackageMap panelPackageMap);

    List<PanelPackageMap> findMapList();

    /**
     * delete all the epg data
     *
     * @return
     */
    void deleteByEpgRelId();

    public void deleteByEpgRelationId();

    PanelPackageMap getMapByPackageIdAndPanelId(Long packageId,Long panelId);
}

