package com.ysten.local.bss.panel.repository.mapper;

import com.ysten.local.bss.panel.domain.PanelPackage;
import com.ysten.local.bss.panel.vo.PanelQueryCriteria;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by frank on 14-5-9.
 */
public interface PanelPackageMapper {

    /**
     * @param panelPackageId
     * @return
     */
    PanelPackage getPanelPackageById(Long panelPackageId);

    PanelPackage getPackageByYstenIdOrGroupId(@Param("ystenId")String ystenId, @Param("groupId")Long groupId);

    PanelPackage getPackageByCustomerCodeOrGroupId(@Param("customerCode")String customerCode, @Param("groupId")Long groupId);

    PanelPackage getDefaultPackage(@Param("isDefault") Integer isDefault);

    /**
     * 新增panel
     *
     * @param panelPackage
     * @return
     */
    Integer save(PanelPackage panelPackage);

    /**
     * 根据ID删除panelPackage
     *
     * @param id
     * @return
     */
    Integer deleteById(Long id);

    /**
     * 根据IDs批量删除panelPackage
     *
     * @param ids
     * @return
     */
    Integer deleteByIds(@Param("ids") List<Long> ids);

    /**
     * delete panel/panel_package map by package ids
     * @param ids
     * @return
     */
    Integer deletePanelPackageMapByPackageIds(@Param("ids") List<Long> ids);

    /**
     * 更新panel
     *
     * @param panelPackage
     * @return
     */
    Integer update(PanelPackage panelPackage);


    /**
     * get all panel package list
     * @return
     */
    List<PanelPackage> getTargetList(PanelQueryCriteria panelQueryCriteria);

    Integer getTargetCount(PanelQueryCriteria panelQueryCriteria);

    List<PanelPackage> getAllCustomedTargetList();

    List<PanelPackage> findAllEpgList();

    List<PanelPackage> findIdAndEpgIdList();

    List<PanelPackage> findAllPanelPackageList();

    /**
     * get panelPackage by epgPackageId
     * @param epgPackageId
     * @return panelPackage
     */
    PanelPackage getPackageByEpgPackageId(@Param("epgPackageId") Long epgPackageId);

    Integer deleteByEpgPackageId();

    Integer batchSavePanelPackage(List<PanelPackage> list);

    Integer batchUpdate(List<PanelPackage> list);
    
    List<PanelPackage> findPanelPackageListOfArea(@Param("distCode")String distCode);

}
