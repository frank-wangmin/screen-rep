package com.ysten.local.bss.panel.repository;

import java.util.List;
import java.util.Map;

import com.ysten.local.bss.panel.domain.Navigation;
import com.ysten.local.bss.panel.vo.PanelQueryCriteria;
import com.ysten.utils.page.Pageable;

/**
 * @author cwang
 */
public interface INavigationRepository {
    /**
     * @param navigation
     * @return
     */
    boolean saveNavigation(Navigation navigation);

    Integer batchSaveNavigation(List<Navigation> list);

    Navigation getNavigationById(Long id);

    Navigation getNavigationByEpgNavId(Long epgNavId);

    Navigation getNavigationByName(String navName);

    boolean updateNavDefine(Navigation navigation);

    boolean updateEpgNavDefine(Navigation navigation);

    boolean deleteNavigation(Long navId);

    List<Navigation> findNavigationByNavType(int navType);

    List<Navigation> findNavigationByNavTypeAndDpi(int navType, String dpi);

    List<Navigation> findEpgNavList();

    Pageable<Navigation> findNavigationByCondition(PanelQueryCriteria panelQueryCriteria);

    List<Navigation> findNavigationListByIds(List<Long> ids);

    /**
     * @param ids
     * @return list
     */
    List<Navigation> findNavigationListByEpgNavIds(List<Long> ids);

    /**
     * delete all the epg data
     *
     * @return
     */
    int deleteByEpgNavId();

    int updateNavStatus(Map<String,Object> updateMap);

}
