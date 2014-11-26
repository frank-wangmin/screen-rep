
package com.ysten.local.bss.panel.service.impl;

import com.ysten.local.bss.device.repository.IDeviceGroupRepository;
import com.ysten.local.bss.panel.domain.Navigation;
import com.ysten.local.bss.panel.domain.Panel;
import com.ysten.local.bss.panel.domain.PanelPackage;
import com.ysten.local.bss.panel.domain.PanelPackageMap;
import com.ysten.local.bss.panel.domain.PanelPackageNavigation;
import com.ysten.local.bss.panel.repository.INavigationRepository;
import com.ysten.local.bss.panel.repository.IPanelPackageMapRepository;
import com.ysten.local.bss.panel.repository.IPanelPackageRepository;
import com.ysten.local.bss.panel.repository.IPanelRepository;
import com.ysten.local.bss.panel.service.IPanelPackageMapService;
import com.ysten.local.bss.util.DateUtils;
import com.ysten.local.bss.util.NewCollectionsUtils;
import com.ysten.utils.string.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author cwang
 * @version 2014-5-23 下午4:31:14
 */
@Service
public class PanelPackageMapService implements IPanelPackageMapService {
    @Autowired
    private IPanelPackageMapRepository panelPackageMapRepository;
    @Autowired
    IPanelRepository panelRepository;
    @Autowired
    INavigationRepository navigationRepository;
    @Autowired
    IDeviceGroupRepository deviceGroupRepository;
    @Autowired
    IPanelPackageRepository panelPackageRepository;

    @Override
    public List<PanelPackageMap> getPanelPackageMapByPackageId(Long packageId) {
        return panelPackageMapRepository.findPanelPackageMapByPackageId(packageId);
    }

    @Override
    public PanelPackageMap getMapByPackageIdAndPanelId(Long packageId, Long panelId) {
        return panelPackageMapRepository.getMapByPackageIdAndPanelId(packageId, panelId);
    }

 /*   @Override
    public void updateSort(PanelPackageMap panelPackageMap) {
        if (panelPackageMapRepository.getMapByPackageIdAndPanelId(panelPackageMap.getPackageId(), panelPackageMap.getPanelId()) != null) {
            panelPackageMapRepository.updateSort(panelPackageMap);
        } else {
            panelPackageMapRepository.savePanelPackageMap(panelPackageMap);
        }
    }
*/
    @Override
    public boolean deletePanelPackageMapByPackagelId(Long packageId, List<Long> panelIds) {
        if (panelIds.size() == 0) {
            panelPackageMapRepository.deletePanelPackageMapByPackagelId(packageId);
        } else {
            panelPackageMapRepository.deleteByPackagelIdAndPanelId(packageId, panelIds);
        }
        //面板包和面板关系表变更时，更新面板包的version版本号
        PanelPackage panelPackage =this.panelPackageRepository.getPanelPackageById(packageId);
        panelPackage.setVersion(new Date().getTime()); //版本--时间戳
        this.panelPackageRepository.update(panelPackage);

        return true;
    }


    @Override
    public void updatePanelPackageConfig(Long panelPackageId, List<PanelPackageNavigation> list,Long oprUserId,String dpi) {

        //TODO 根据panelPackageId 和 DPI查询出所有的面板ids，然后根据这些ids删除panel_panel_package_map
//        panelPackageMapRepository.deletePanelPackageMapByPackagelId(panelPackageId);
        List<PanelPackageMap> mapList = panelPackageMapRepository.findPanelPackageMapByPackageId(panelPackageId);
        Panel deletePanel = null;
        for (PanelPackageMap panelPackageMap : mapList) {
            deletePanel = panelRepository.getPanelById(panelPackageMap.getPanelId());
            if(deletePanel != null && StringUtils.equals(dpi,deletePanel.getResolution())){
                panelPackageMapRepository.deleteMapByPackagelIdAndPanelId(panelPackageId,deletePanel.getId());
            }
        }
        for (PanelPackageNavigation panelPackageNav : list) {
            PanelPackageMap panelPackageMap = new PanelPackageMap();
            //用于同步播控数据需要
            Panel panel = panelRepository.getPanelById(panelPackageNav.getId());
            if (panel.getEpgPanelId() != null) {
                panelPackageMap.setEpgPanelId(panel.getEpgPanelId());
            }
            String epg_ids = "";
            String navIdStr;
            List<Navigation> navigationList = NewCollectionsUtils.list();
            String upNavIds = panelPackageNav.getHeadNavIds();
            Long downNavId = panelPackageNav.getRootNavId();
            if (StringUtils.isNotBlank(upNavIds)) {
                List<Long> upNavIdList = StringUtil.splitToLong(upNavIds);
                for (Long navId : upNavIdList) {
                    Navigation navigation = navigationRepository.getNavigationById(navId);
                    if (navigation != null) navigationList.add(navigation);
                }
                navIdStr = upNavIds + "," + downNavId;
            } else {
                navIdStr = downNavId + "";
            }
            Navigation navigation = navigationRepository.getNavigationById(downNavId);
            if (navigation != null) navigationList.add(navigation);
            for (Navigation navigationTemp : navigationList) {
                if (navigationTemp.getEpgNavId() != null) {
                    epg_ids += navigationTemp.getEpgNavId() + ",";
                }
            }
            if (StringUtils.isNotBlank(epg_ids)) {
                panelPackageMap.setEpgNavId(StringUtils.substring(epg_ids, 0, epg_ids.length() - 1));
            }
            panelPackageMap.setSortNum(panelPackageNav.getSort());
            panelPackageMap.setIsLock(panelPackageNav.getIsLock());
            panelPackageMap.setDisplay(panelPackageNav.getDisplay());
            panelPackageMap.setPanelLogo(StringUtils.isNotBlank(panelPackageNav.getPanelLogo()) ? panelPackageNav.getPanelLogo() : null);
            panelPackageMap.setPanelId(panelPackageNav.getId());
            panelPackageMap.setPackageId(panelPackageId);
            panelPackageMap.setNavId(navIdStr);
            panelPackageMap.setOprUserId(oprUserId);
            panelPackageMap.setCreateTime(DateUtils.getCurrentDate());
            panelPackageMap.setUpdateTime(DateUtils.getCurrentDate());
            panelPackageMapRepository.savePanelPackageMap(panelPackageMap);//done
        }
        
        //循环外---面板包和面板关系表变更时，更新面板包的version版本号
        PanelPackage panelPackage =this.panelPackageRepository.getPanelPackageById(panelPackageId);
        panelPackage.setVersion(new Date().getTime()); //版本--时间戳
        this.panelPackageRepository.update(panelPackage);
    }

    @Override
    public List<PanelPackageMap> getMapByPanelPanelPackage(Map<String, Object> map) {
        return panelPackageMapRepository.getMapByPanelIds(map);
    }

    @Override
    public List<PanelPackageMap> verifyIfExistBinded(Long panelId, Long packageId) {
        return panelRepository.verifyIfExistBinded(panelId, packageId);
    }

}

