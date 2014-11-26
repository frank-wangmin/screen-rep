package com.ysten.local.bss.panel.repository;

import com.ysten.local.bss.panel.domain.Panel;
import com.ysten.local.bss.panel.domain.PanelPackageMap;
import com.ysten.local.bss.panel.vo.PanelQueryCriteria;
import com.ysten.utils.page.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Created by frank on 14-5-9.
 */
public interface IPanelRepository {

	Panel getPanelById(Long panelId);

    List<Panel> getPanelByName(String name);

    void savePanel(Panel panel);

    void deletePanel(Panel panel);

    void updatePanel(Panel panel);

    void updateEpgPanel(Panel panel);

    Pageable<Panel> findPanelList(PanelQueryCriteria panelQueryCriteria);

    List<Panel> findPanelListByPackageId(Map<String, Object> map);

    List<Panel> getAllOnlinePanels(int onlineStatus,String dpi);

    void savePanelPackageMap(PanelPackageMap panelPackageMap);

    List<PanelPackageMap> verifyIfExistBinded(Long panelId, Long packageId);

    List<Panel> findAllEpgListByDistrictCode(String districtCode);

    /*
     Get all data from province
     */
    List<Panel> findAllList();

    List<Panel>  findIdAndEpgIdList(String districtCode);

    void batchUpdate(List<Panel> list);

    Integer getPanelCountByPackageId(Long packageId);
}
