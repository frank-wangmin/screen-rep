package com.ysten.local.bss.panel.service;

import java.util.List;

import com.ysten.local.bss.panel.domain.Navigation;
import com.ysten.local.bss.panel.vo.PanelQueryCriteria;
import com.ysten.utils.page.Pageable;

/**
 * @author cwang
 * @version 2014-5-23 下午3:16:18
 * 
 */
public interface INavigationService {
	
	Boolean saveNavigation(Navigation navigation);

	Navigation getNavigationById(Long id);
	
	boolean updateNavigation(Navigation navigation);
	
	String deleteNavDefinesByCondition(List<Long> ids);
	
	List<Navigation> findNavigationByNavType(int navType);

    List<Navigation> findNavigationByNavTypeAndDpi(int navType,String dpi);

    List<Navigation> findNavigationByNavIds(List<Long> ids);

	
	Navigation findNavigationByName(String title);
	
	Navigation getNavigationByEpgNavId(Long epgNavId);
	 
	Pageable<Navigation> getNavigationListByCondition(PanelQueryCriteria panelQueryCriteria);
}
