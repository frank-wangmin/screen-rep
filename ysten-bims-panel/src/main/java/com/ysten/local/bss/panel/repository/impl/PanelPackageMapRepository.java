
package com.ysten.local.bss.panel.repository.impl;

import com.ysten.cache.annotation.MethodFlushAllPanel;
import com.ysten.cache.annotation.MethodFlushBootsrap;
import com.ysten.cache.annotation.MethodFlushInterfacePackage;

import com.ysten.utils.string.StringUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysten.local.bss.panel.domain.Panel;
import com.ysten.local.bss.panel.domain.PanelPackageMap;
import com.ysten.local.bss.panel.repository.IPanelPackageMapRepository;
import com.ysten.local.bss.panel.repository.mapper.PanelPackageMapMapper;

import java.util.List;
import java.util.Map;

/**
 * @author cwang
 * @version 2014-5-23 下午4:28:57
 */
@Repository
public class PanelPackageMapRepository implements IPanelPackageMapRepository {
    @Autowired
    private PanelPackageMapMapper panelPackageMapMapper;

    @Override
//    @MethodFlushBootsrap
    @MethodFlushInterfacePackage
    public void savePanelPackageMap(PanelPackageMap panelPackageMap) {
        panelPackageMapMapper.insert(panelPackageMap);
    }

    @Override
//    @MethodFlushBootsrap
    @MethodFlushInterfacePackage
    public void batchSavePanelPackageMap(List<PanelPackageMap> list) {
        panelPackageMapMapper.batchInsert(list);
    }

    @Override
    public void batchSaveEpgMap(List<PanelPackageMap> list) {
        panelPackageMapMapper.batchInsert(list);
    }

    @Override
    public String getNavIds(Long panelId, Long packageId) {
        return panelPackageMapMapper.getNavIds(panelId, packageId);
    }


    @Override
//    @MethodFlushBootsrap
    @MethodFlushInterfacePackage
    public void deletePanelPackageMapByPanelId(Long panelId) {
        panelPackageMapMapper.deleteByPanelId(panelId);
    }

    @Override
//    @MethodFlushBootsrap
    @MethodFlushInterfacePackage
    public void deletePanelPackageMapByPackagelId(Long packageId) {
        panelPackageMapMapper.deleteByPackagelId(packageId);
    }

    @Override
    public List<PanelPackageMap> findPanelPackageMapByPackageId(Long packageId) {
        return panelPackageMapMapper.getMapByPackageId(packageId);
    }
    @Override
    public List<PanelPackageMap> findPaginationMapByPackageId(Long packageId, Integer pageNo, Integer pageSize) {
        return panelPackageMapMapper.getPaginationMapByPackageId(packageId,pageNo,pageSize);
    }

    @Override
//    @MethodFlushBootsrap
    @MethodFlushInterfacePackage
    public void deleteByPackagelIdAndPanelId(Long packageId, List<Long> panelId) {
        panelPackageMapMapper.deleteByPackagelIdAndPanelId(packageId, panelId);
    }

    @Override
    @MethodFlushInterfacePackage
    public void deleteMapByPackagelIdAndPanelId(Long packageId, Long panelId) {
        panelPackageMapMapper.deleteMapByPackagelIdAndPanelId(packageId,panelId);
    }

    @Override
    public List<PanelPackageMap> verifyIfExistBinded(Long panelId, Long packageId) {
        return panelPackageMapMapper.verifyIfExistBinded(panelId, packageId);
    }

    @Override
    public List<PanelPackageMap> findMapByNavId(Long navId) {
        String navIdBetween = "," + navId + ",";
        String navIdEnd = navId + ",";
        String navIdStart = "," + navId;
        return this.panelPackageMapMapper.getMapByNavId(navIdBetween, navIdEnd, navIdStart, navId + "");
    }

    @Override
    public List<PanelPackageMap> findCustomMapsWithEpgData() {
        return panelPackageMapMapper.findCustomMapsWithEpgData();
    }


    @Override
//    @MethodFlushBootsrap
    @MethodFlushInterfacePackage
    public void updateMap(PanelPackageMap panelPackageMap) {
        panelPackageMapMapper.updateMap(panelPackageMap);
    }

    @Override
    @MethodFlushInterfacePackage
    public void updateSort(PanelPackageMap panelPackageMap) {
        panelPackageMapMapper.updateSort(panelPackageMap);
    }

    @Override
    public List<PanelPackageMap> findMapList() {
        return panelPackageMapMapper.findMapList();
    }

    @Override
//    @MethodFlushAllPanel
//    @MethodFlushBootsrap
    @MethodFlushInterfacePackage
    public void deleteByEpgRelId() {
        panelPackageMapMapper.deleteByEpgRelId();
    }

    public void deleteByEpgRelationId() {
        panelPackageMapMapper.deleteByEpgRelId();
    }

    @Override
    public PanelPackageMap getMapByPackageIdAndPanelId(Long packageId, Long panelId) {
        return panelPackageMapMapper.getMapByPackageIdAndPanelId(packageId,panelId);
    }

	@Override
	public List<PanelPackageMap> getMapByPanelIds(Map<String, Object> map) {
		return panelPackageMapMapper.getMapByPanelIds(map);
	}
}

