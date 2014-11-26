package com.ysten.local.bss.panel.repository.mapper;

import java.util.List;
import java.util.Map;

import com.ysten.local.bss.panel.vo.PanelQueryCriteria;
import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.panel.domain.Navigation;

/**
 * 
 * @author cwang
 *
 */
public interface NavigationMapper {

    Integer insert(Navigation navigation);

    Integer batchInsert(List<Navigation> list);

    Navigation selectByPrimaryKey(@Param("id")Long id);

    Navigation getByEpgNavId(@Param("epgNavId") Long epgNavId);
    
    Navigation selectByNavName(@Param("navName") String navName);
    
    List<Navigation> selectByNavType(@Param("navType")int navType);

    List<Navigation> findNavigationByNavTypeAndDpi(@Param("navType")int navType,@Param("dpi")String dpi);

    List<Navigation> findEpgNavList();
    
    int updateByPrimaryKey(Navigation navigation);
    
    int deleteByPrimaryKey(@Param("navId")Long navId);
    
    List<Navigation> getListByCondition(PanelQueryCriteria panelQueryCriteria);

    int  getCountByCondition(PanelQueryCriteria panelQueryCriteria);

    List<Navigation> findNavListByEpgNavIds(@Param("ids") List<Long> ids);

    int deleteByEpgNavId();

    int updateNavStatus(Map<String,Object> updateMap);
}
