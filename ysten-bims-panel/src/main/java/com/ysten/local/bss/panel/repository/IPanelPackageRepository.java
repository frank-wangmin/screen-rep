package com.ysten.local.bss.panel.repository;

import com.ysten.local.bss.panel.domain.PanelPackage;
import com.ysten.local.bss.panel.vo.PanelQueryCriteria;
import com.ysten.utils.page.Pageable;

import java.util.List;

/**
 * Created by frank on 14-5-9.
 */
public interface IPanelPackageRepository {

    PanelPackage getPanelPackageById(Long id);

    PanelPackage getPanelPackageByYstenIdOrGroupId(String ystenId, Long groupId);

    PanelPackage getPackageByCustomerCodeOrGroupId(String customerCode, Long groupId);

    void savePanelPackage(PanelPackage panelPackage);

    void deletePanelPackageById(PanelPackage panelPackage);

    void update(PanelPackage panelPackage);

    void updateEpgPanelPackage(PanelPackage panelPackage);

    Pageable<PanelPackage> getTargetList(PanelQueryCriteria panelQueryCriteria);

    List<PanelPackage> getAllCustomedTargetList();

    List<PanelPackage> findAllEpgList();

    List<PanelPackage> findIdAndEpgIdList();

    List<PanelPackage> findAllPanelPackageList();
    
    List<PanelPackage> findPanelPackageListOfArea(String distCode);

    PanelPackage getDefaultPackage(Integer isDefault);

    /**
     * get panelPackage by epgPackageId
     * @param epgPackageId
     * @return panelPackage
     */
    PanelPackage getPackageByEpgPackageId(Long epgPackageId);

    /**
     *  delete all the epg data
     * @return
     */
    void deleteByEpgPackageId();

    void batchSavePanelPackage(List<PanelPackage> list);

    void batchUpdatePanelPackage(List<PanelPackage> list);
}
