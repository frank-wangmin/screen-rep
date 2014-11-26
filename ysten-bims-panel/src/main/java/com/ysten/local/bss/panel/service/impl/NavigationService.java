
package com.ysten.local.bss.panel.service.impl;

import com.ysten.local.bss.panel.domain.Navigation;
import com.ysten.local.bss.panel.domain.PanelPackageMap;
import com.ysten.local.bss.panel.repository.INavigationRepository;
import com.ysten.local.bss.panel.repository.IPanelPackageMapRepository;
import com.ysten.local.bss.panel.service.INavigationService;
import com.ysten.local.bss.panel.vo.PanelQueryCriteria;
import com.ysten.utils.page.Pageable;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author cwang
 * @version 2014-5-23 下午3:16:36
 */
@Service
public class NavigationService implements INavigationService {
    @Autowired
    private INavigationRepository navigationRepository;
    @Autowired
    private IPanelPackageMapRepository panelPackageMapRepository;

    @Override
    public Boolean saveNavigation(Navigation navigation) {
        return navigationRepository.saveNavigation(navigation);
    }

    @Override
    public Navigation getNavigationById(Long id) {
        return navigationRepository.getNavigationById(id);
    }

    @Override
    public Pageable<Navigation> getNavigationListByCondition(PanelQueryCriteria panelQueryCriteria) {
        return this.navigationRepository.findNavigationByCondition(panelQueryCriteria);
    }

    @Override
    public boolean updateNavigation(Navigation navigation) {
        return this.navigationRepository.updateNavDefine(navigation);
    }



    @Override
    public String deleteNavDefinesByCondition(List<Long> ids) {
        StringBuilder sb = null;
        for (Long id : ids) {
            List<PanelPackageMap> map = this.panelPackageMapRepository.findMapByNavId(id);
            if (CollectionUtils.isEmpty(map)) {
                this.navigationRepository.deleteNavigation(id);
            } else {
                if (sb == null) {
                    sb = new StringBuilder();
                }
                Navigation nav = this.navigationRepository.getNavigationById(id);
                sb.append("导航标题：" + nav.getTitle() + "被使用了，不可删除!<br/>");
            }
        }
        if (sb != null && StringUtils.isNotBlank(sb.toString())) {
            return sb.toString();
        }
        return null;
    }

  /*  @Override
    public boolean deleteNavDefinesByIds(List<Long> ids) {
//		StringBuilder sb = null;
//		
//		if(){
//			
//		}else{
//			if(sb == null){
//				sb = new StringBuilder();
//			}
//			sb.append("导航ID："+"被使用了，不可删除!<br/>");
//		}
        int sum = 0;
        for (Long id : ids) {
            boolean bool = this.navigationRepository.deleteByPrimaryKey(id);
            if (bool == true) {
                sum++;
            }
        }
        if (sum == ids.size()) {
            return true;
        }
        return false;
    }*/

    @Override
    public List<Navigation> findNavigationByNavType(int navType) {
        return this.navigationRepository.findNavigationByNavType(navType);
    }

    @Override
    public List<Navigation> findNavigationByNavTypeAndDpi(int navType, String dpi) {
        return this.navigationRepository.findNavigationByNavTypeAndDpi(navType,dpi);
    }

    @Override
    public List<Navigation> findNavigationByNavIds(List<Long> ids) {
        return this.navigationRepository.findNavigationListByIds(ids);
    }

    @Override
    public Navigation findNavigationByName(String navName) {
        return this.navigationRepository.getNavigationByName(navName);
    }

    @Override
    public Navigation getNavigationByEpgNavId(Long epgNavId) {
        return this.navigationRepository.getNavigationByEpgNavId(epgNavId);
    }

}

