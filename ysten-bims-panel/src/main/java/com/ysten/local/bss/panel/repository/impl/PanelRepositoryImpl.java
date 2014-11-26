package com.ysten.local.bss.panel.repository.impl;

import com.ysten.cache.annotation.*;
import com.ysten.local.bss.panel.domain.Panel;
import com.ysten.local.bss.panel.domain.PanelPackageMap;
import com.ysten.local.bss.panel.repository.IPanelPackageMapRepository;
import com.ysten.local.bss.panel.repository.IPanelRepository;
import com.ysten.local.bss.panel.repository.mapper.PanelMapper;
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
public class PanelRepositoryImpl implements IPanelRepository {

    @Autowired
    private PanelMapper panelMapper;

    @Autowired
    private IPanelPackageMapRepository panelPackageMapRepository;

    private static final String PANEL_ID = Constant.PANEL_PACKAGE + "panel:id:";

//    private static final String EPG_PANEL_ID = Constant.PANEL_PACKAGE + "panel:epg_panel_id:";

    @Override
    @MethodCache(key = PANEL_ID + "#{panelId}")
    public Panel getPanelById(@KeyParam("panelId") Long panelId) {
        return panelMapper.getPanelById(panelId);
    }

    @Override
    public List<Panel> getPanelByName(String name){
        return panelMapper.getPanelByName(name);
    }

    @Override
    public void savePanel(Panel panel) {
        panelMapper.insert(panel);
    }

    @Override
    @MethodFlush(keys = {PANEL_ID + "#{panel.id}"})
//    @MethodFlushBootsrap
    @MethodFlushInterfacePackage
    public void deletePanel(@KeyParam("panel") Panel panel) {
        panelMapper.deleteById(panel.getId());
    }

    @Override
    @MethodFlush(keys = {PANEL_ID + "#{panel.id}"})
//    @MethodFlushBootsrap
    @MethodFlushInterfacePackage
    public void updatePanel(@KeyParam("panel") Panel panel) {
        panelMapper.updatePanel(panel);
    }

    @Override
    public void updateEpgPanel(Panel panel) {
        panelMapper.updatePanel(panel);
    }


    @Override
    public Pageable<Panel> findPanelList(PanelQueryCriteria panelQueryCriteria) {
        List<Panel> panels = panelMapper.getPanelList(panelQueryCriteria);
        Integer total = panelMapper.getPanelCount(panelQueryCriteria);
        return new Pageable<Panel>().instanceByPageNo(panels, total, panelQueryCriteria.getStart(), panelQueryCriteria.getLimit());
    }

    @Override
//    @MethodFlushBootsrap
    @MethodFlushInterfacePackage
    public void savePanelPackageMap(PanelPackageMap panelPackageMap) {
        panelPackageMapRepository.savePanelPackageMap(panelPackageMap);
    }

    @Override
    public List<PanelPackageMap> verifyIfExistBinded(Long panelId, Long packageId) {
        return panelPackageMapRepository.verifyIfExistBinded(panelId, packageId);
    }

    @Override
    public List<Panel> findAllEpgListByDistrictCode(String districtCode) {
        return panelMapper.findAllEpgList(districtCode);
    }

    @Override
    public List<Panel> findAllList() {
        return panelMapper.findAllList();
    }

    @Override
    public List<Panel> findIdAndEpgIdList(String districtCode) {
        return panelMapper.findIdAndEpgIdList(districtCode);
    }

    @Override
//    @MethodFlushAllPanel
//    @MethodFlushBootsrap
    @MethodFlushInterfacePackage
    public void batchUpdate(List<Panel> list) {
        panelMapper.batchUpdate(list);
    }


    @Override
    public List<Panel> findPanelListByPackageId(Map<String, Object> map) {
        return panelMapper.selectPanelListByPackageId(map);
    }

    @Override
    public List<Panel> getAllOnlinePanels(int onlineStatus,String dpi) {
        return panelMapper.getAllOnlinePanels(onlineStatus,dpi);
    }

    @Override
    public Integer getPanelCountByPackageId(Long packageId) {
        return panelMapper.getPanelCountByPackageId(packageId);
    }
}
