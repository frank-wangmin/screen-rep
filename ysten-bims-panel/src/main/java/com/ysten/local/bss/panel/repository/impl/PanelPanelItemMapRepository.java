package com.ysten.local.bss.panel.repository.impl;

import com.ysten.cache.annotation.MethodFlushAllPanel;
import com.ysten.cache.annotation.MethodFlushBootsrap;
import com.ysten.cache.annotation.MethodFlushInterfacePackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysten.local.bss.panel.domain.PanelPanelItemMap;
import com.ysten.local.bss.panel.repository.IPanelPanelItemMapRepository;
import com.ysten.local.bss.panel.repository.mapper.PanelPanelItemMapMapper;

import java.util.List;

/**
 * @author cwang
 * @version 2014-5-23 下午4:16:16
 */
@Repository
public class PanelPanelItemMapRepository implements IPanelPanelItemMapRepository {

    @Autowired
    private PanelPanelItemMapMapper panelPanelItemMapMapper;

    @Override
//    @MethodFlushBootsrap
    @MethodFlushInterfacePackage
    public int deletePanelPanelItemMap(Long id) {
        return panelPanelItemMapMapper.deleteByPrimaryKey(id);
    }

    @Override
//    @MethodFlushBootsrap
//    @MethodFlushInterfacePackage
    public int savePanelPanelItemMap(PanelPanelItemMap record) {
        return panelPanelItemMapMapper.insert(record);
    }

    @Override
//    @MethodFlushBootsrap
    @MethodFlushInterfacePackage
    public int batchSavePanelPanelItemMap(List<PanelPanelItemMap> list) {
        return panelPanelItemMapMapper.batchInsert(list);
    }

    @Override
    public int batchSaveEpgMap(List<PanelPanelItemMap> list) {
        return panelPanelItemMapMapper.batchInsert(list);
    }

    @Override
    public PanelPanelItemMap selectByPrimaryKey(Long id) {
        return panelPanelItemMapMapper.selectByPrimaryKey(id);
    }

    @Override
//    @MethodFlushBootsrap
    @MethodFlushInterfacePackage
    public int updateByPrimaryKey(PanelPanelItemMap record) {
        return panelPanelItemMapMapper.updateByPrimaryKey(record);
    }

    @Override
//    @MethodFlushBootsrap
    @MethodFlushInterfacePackage
    public Integer deleteByPanelId(Long panelId) {
        return panelPanelItemMapMapper.deleteByPanelId(panelId);
    }

    @Override
//    @MethodFlushBootsrap
    @MethodFlushInterfacePackage
    public Integer deleteByPanelItemId(Long panelItemId) {
        return panelPanelItemMapMapper.deleteByPanelItemId(panelItemId);
    }

    @Override
    public PanelPanelItemMap selectByPanelIdAndPanelItemId(Long panelId, Long panelItemId) {
        return panelPanelItemMapMapper.selectByPanelIdAndPanelItemId(panelId, panelItemId);
    }

    @Override
    public List<PanelPanelItemMap> findMapListByPanelId(Long panelId) {
        return panelPanelItemMapMapper.findMapListByPanelId(panelId);
    }


    @Override
    public List<PanelPanelItemMap> findProvinceMapList() {
        return panelPanelItemMapMapper.findProvinceMapList();
    }

    @Override
    public List<PanelPanelItemMap> findMapList() {
        return panelPanelItemMapMapper.findMapList();
    }

  /*  @Override
//    @MethodFlushAllPanel
//    @MethodFlushBootsrap
    @MethodFlushInterfacePackage
    public int deleteByEpgRelId() {
        return panelPanelItemMapMapper.deleteByEpgRelId();
    }*/

    public int deleteByEpgRelationId(String districtCode) {
        return panelPanelItemMapMapper.deleteByEpgRelId(districtCode);
    }

	@Override
	public List<PanelPanelItemMap> findListByEpgPanelItem(List<Long> panelIdList) {
		return this.panelPanelItemMapMapper.findListByEpgPanelItem(panelIdList);
	}


}
