package com.ysten.local.bss.panel.repository.impl;

import com.ysten.cache.annotation.*;
import com.ysten.local.bss.panel.domain.Navigation;
import com.ysten.local.bss.panel.repository.INavigationRepository;
import com.ysten.local.bss.panel.repository.mapper.NavigationMapper;
import com.ysten.local.bss.panel.vo.PanelQueryCriteria;
import com.ysten.local.bss.util.NewCollectionsUtils;
import com.ysten.local.bss.util.bean.Constant;
import com.ysten.utils.page.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @author cwang
 */
@Repository
public class NavigationRepositoryImpl implements INavigationRepository {
	
    @Autowired
    private NavigationMapper navigationMapper;

    private static final String NAVIGATION_ID = Constant.PANEL_PACKAGE + "navigation:id:";

    @Override
    public boolean saveNavigation(Navigation navigation) {
        return 1 == navigationMapper.insert(navigation);
    }

    @Override
//    @MethodFlushBootsrap
    @MethodFlushInterfacePackage
    public Integer batchSaveNavigation(List<Navigation> list) {
        return navigationMapper.batchInsert(list);
    }

/*    @Override
//    @MethodFlushAllPanel
//    @MethodFlushBootsrap
    @MethodFlushInterfacePackage
    public Integer batchUpdate(List<Navigation> list) {
        return navigationMapper.batchUpdate(list);
    }*/


    @Override
    @MethodCache(key = NAVIGATION_ID + "#{id}")
    public Navigation getNavigationById(@KeyParam("id") Long id) {
        return navigationMapper.selectByPrimaryKey(id);
    }

    @Override
    public Navigation getNavigationByEpgNavId(Long epgNavId) {
        return this.navigationMapper.getByEpgNavId(epgNavId);
    }


    @Override
    public Pageable<Navigation> findNavigationByCondition(PanelQueryCriteria panelQueryCriteria) {
        List<Navigation> navList = this.navigationMapper.getListByCondition(panelQueryCriteria);
        int total = this.navigationMapper.getCountByCondition(panelQueryCriteria);
        return new Pageable<Navigation>().instanceByPageNo(navList, total, panelQueryCriteria.getStart(), panelQueryCriteria.getLimit());
    }

    @Override
    @MethodFlush(keys = {NAVIGATION_ID + "#{navigation.id}"})
//    @MethodFlushBootsrap
    @MethodFlushInterfacePackage
    public boolean updateNavDefine(@KeyParam("navigation") Navigation navigation) {
        return 1 == this.navigationMapper.updateByPrimaryKey(navigation);
    }


    @Override
    public boolean updateEpgNavDefine(Navigation navigation){
        return 1 == this.navigationMapper.updateByPrimaryKey(navigation);
    }

    @Override
    @MethodFlush(keys = {NAVIGATION_ID + "#{navId}"})
//    @MethodFlushBootsrap
    @MethodFlushInterfacePackage
    public boolean deleteNavigation(@KeyParam("navId") Long navId) {
        return 1 == this.navigationMapper.deleteByPrimaryKey(navId);
    }

    @Override
    public List<Navigation> findNavigationByNavType(int navType) {
        return this.navigationMapper.selectByNavType(navType);
    }

    @Override
    public List<Navigation> findNavigationByNavTypeAndDpi(int navType, String dpi) {
        return this.navigationMapper.findNavigationByNavTypeAndDpi(navType,dpi);
    }

    @Override
    public List<Navigation> findEpgNavList() {
        return this.navigationMapper.findEpgNavList();
    }


    @Override
    public Navigation getNavigationByName(String navName) {
        return this.navigationMapper.selectByNavName(navName);
    }

    @Override
    public List<Navigation> findNavigationListByIds(List<Long> ids) {
        List<Navigation> navigationList = NewCollectionsUtils.list();
        if (!CollectionUtils.isEmpty(ids)) {
            for (Long id : ids) {
                Navigation navigation = this.getNavigationById(id);
                if (navigation != null) navigationList.add(navigation);
            }
        }
        return navigationList;
    }

    @Override
    public List<Navigation> findNavigationListByEpgNavIds(List<Long> ids) {
        return navigationMapper.findNavListByEpgNavIds(ids);
    }

    @Override
//    @MethodFlushAllPanel
//    @MethodFlushBootsrap
    @MethodFlushInterfacePackage
    public int deleteByEpgNavId() {
        return navigationMapper.deleteByEpgNavId();
    }

    @Override
    @MethodFlushInterfacePackage
    public int updateNavStatus(Map<String,Object> updateMap) {
        return navigationMapper.updateNavStatus(updateMap);
    }
}
