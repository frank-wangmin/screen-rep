package com.ysten.local.bss.panel.service.impl;

import com.ysten.cache.annotation.MethodFlushAllPanel;
import com.ysten.cache.annotation.MethodFlushBootsrap;
import com.ysten.cache.annotation.MethodFlushInterfacePackage;
import com.ysten.local.bss.panel.domain.*;
import com.ysten.local.bss.panel.enums.OnlineStatus;
import com.ysten.local.bss.panel.enums.YesOrNo;
import com.ysten.local.bss.panel.repository.*;
import com.ysten.local.bss.panel.service.IPanelDistributeService;
import com.ysten.local.bss.util.JsonUtil;
import com.ysten.local.bss.util.bean.Constant;
import com.ysten.utils.string.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by yaoqy on 14-7-3.
 */
@Service
public class PanelDistributeServiceImpl implements IPanelDistributeService {

    @Autowired
    private IPanelPackageRepository panelPackageRepository;
    @Autowired
    private IPreviewTemplateRepository previewTemplateRepository;
    @Autowired
    private IPanelRepository panelRepository;
    @Autowired
    private INavigationRepository navigationRepository;
    @Autowired
    private IPanelPanelItemMapRepository panelPanelItemMapRepository;
    @Autowired
    private IPanelPackageMapRepository panelPackageMapRepository;
    @Autowired
    private IPreviewItemRepository previewItemRepository;
    @Autowired
    private IPreviewItemDataRepository previewItemDataRepository;
    @Autowired
    private IPanelItemRepository panelItemRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(PanelDistributeServiceImpl.class);
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    //    private transient List<Navigation> epgNavList = new ArrayList<Navigation>();//store all the epg nav datas
//    private transient Map<Long, Navigation> existedMap = new HashMap<Long, Navigation>();
    private transient Map<Long, PanelPackage> panelPackageMap;
    private transient Map<Long, Panel> panelMap;
    private transient Map<Long, PanelItem> panelItemMap;
    private transient Map<Long, PreviewTemplate> previewTemplateMap;

    @Override
    @MethodFlushAllPanel
    @MethodFlushInterfacePackage
    public void savePanelData(List<PanelPackage> panelPackageList, List<PreviewTemplate> previewTemplateList, List<Panel> panelList, List<PanelItem> panelItemList, List<Navigation> navigationList, List<PanelPanelItemMap> panelItemMapList, List<PanelPackageMap> panelPackageMapList, List<PreviewItem> previewItemList, List<PreviewItemData> previewItemDataList) throws Exception {
        panelPackageMap = new HashMap<Long, PanelPackage>();
        panelMap = new HashMap<Long, Panel>();
        panelItemMap = new HashMap<Long, PanelItem>();
        previewTemplateMap = new HashMap<Long, PreviewTemplate>();
//        insertPanelPackage(panelPackageList);
//        insertPreviewTemplate(previewTemplateList);
        insertPanel(panelList);
        insertPanelItem(panelItemList);
//        insertNavigation(navigationList);
        insertPanelPanelItemMap(panelItemMapList);
//        insertPanelPackageMap(panelPackageMapList);
//        insertPreviewItem(previewItemList);
        insertPreviewItemData(previewItemDataList);
    }

    @Override
    public String findPanelDatas(String type) {
        List<?> panelDataList = new ArrayList<Object>();
      /*  if (Constant.DISTRIBUTE_TYPE_PANEL_PACKAGE.equals(type)) {
            panelDataList = panelPackageRepository.findAllEpgList();
        } else if (Constant.DISTRIBUTE_TYPE_PREVIEW_TEMPLATE.equals(type)) {
            panelDataList = previewTemplateRepository.findAllEpgList();
        } else if (Constant.DISTRIBUTE_TYPE_PANEL.equals(type)) {
            panelDataList = panelRepository.findAllEpgList();
        } else if (Constant.DISTRIBUTE_TYPE_PANEL_ITEM.equals(type)) {
            panelDataList = panelItemRepository.findAllPanelItemEpgList();
        } else if (Constant.DISTRIBUTE_TYPE_NAVIGATION.equals(type)) {
            panelDataList = navigationRepository.findEpgNavList();
        } else if (Constant.DISTRIBUTE_TYPE_PANEL_ITEM_MAP.equals(type)) {
            panelDataList = panelPanelItemMapRepository.findMapList();
        } else if (Constant.DISTRIBUTE_TYPE_PANEL_PACKAGE_MAP.equals(type)) {
            panelDataList = panelPackageMapRepository.findMapList();
        } else if (Constant.DISTRIBUTE_TYPE_PREVIEW_ITEM.equals(type)) {
            panelDataList = previewItemRepository.findAllItems();
        } else if (Constant.DISTRIBUTE_TYPE_PREVIEW_ITEM_DATA.equals(type)) {
            panelDataList = previewItemDataRepository.findAllDatas();
        }*/
        return JsonUtil.getGsonString4List(panelDataList);
    }

   /* private void insertPanelPackage(List<PanelPackage> panelPackageList) throws Exception {
        panelPackageRepository.deleteByEpgPackageId();
        if (!CollectionUtils.isEmpty(panelPackageList)) {
            for (PanelPackage panelPackage : panelPackageList) {
                convertPackage(panelPackage);
            }
            panelPackageRepository.batchSavePanelPackage(panelPackageList);
        }
        LOGGER.info("#### after insert panelPackage ####");
    }*/

   /* private void insertPreviewTemplate(List<PreviewTemplate> previewTemplateList) throws Exception {
        previewTemplateRepository.deleteByEpgIds();
        if (!CollectionUtils.isEmpty(previewTemplateList)) {
            for (PreviewTemplate previewTemplate : previewTemplateList) {
                convertPreviewTemplate(previewTemplate);
            }
            previewTemplateRepository.batchSavePreviewTemplate(previewTemplateList);
        }
        LOGGER.info("#### after insert PreviewTemplate ####");
    }*/

    private void saveOrUpdatePanel(List<Panel> panelList) {
        //add or update the epg panel data
        Map<String, Panel> currMap = new HashMap<String, Panel>();//store the new epg data to check which data need to by deleted
        Map<String, Panel> oldMap = new HashMap<String, Panel>();
        List<Panel> oldPanelList = panelRepository.findAllEpgListByDistrictCode(panelList.get(0).getDistrictCode());// need to handle
        for (Panel panel : oldPanelList) {
            oldMap.put(panel.getDistrictCode()+panel.getEpgPanelId(), panel);
        }
        for (Panel panel : panelList) {
            convertPanel(panel);
//            if (panel.getStatus() != null && panel.getStatus() == OnlineStatus.ONLINE.getValue()) {
                Panel pa = oldMap.get(panel.getDistrictCode()+panel.getEpgPanelId());
                if (pa != null && pa.getId() != null) {
                    panel.setId(pa.getId());
                    if (panel.getUpdateTime() == null) {
                        panel.setUpdateTime(new Date());
                    }
                    panel.setIsCustom(pa.getIsCustom());
                    panel.setBigimg(pa.getBigimg());
                    panel.setSmallimg(pa.getSmallimg());
                    panelRepository.updateEpgPanel(panel);
                } else {
                    panel.setIsCustom(0);
                    panelRepository.savePanel(panel);
                }
//            }
            currMap.put(panel.getDistrictCode()+panel.getEpgPanelId(), panel);
        }

        //update the deleted data

        if (!CollectionUtils.isEmpty(oldPanelList)) {
            for (Panel panel : oldPanelList) {
                if (currMap.get(panel.getDistrictCode()+panel.getEpgPanelId()) == null) {
                    panel.setOnlineStatus(OnlineStatus.DOWNLINE.getValue());
                    panelRepository.updateEpgPanel(panel);
                }
            }
        }
    }


    private void insertPanel(List<Panel> panelList) throws Exception {
//        panelRepository.deleteByEpgPanelId();
        if (!CollectionUtils.isEmpty(panelList)) {
            saveOrUpdatePanel(panelList);
        }
        LOGGER.info("#### after insertPanel ####");
    }

    private void insertPanelItem(List<PanelItem> panelItemList) throws Exception {
//        panelItemRepository.deleteByEpgIds();
        if (!CollectionUtils.isEmpty(panelItemList)) {
            saveOrUpdatePanelItem(panelItemList);
        }
        LOGGER.info("### after insert PanelItem ####");
    }

    private void saveOrUpdatePanelItem(List<PanelItem> panelItemList) {
        //add or update the not deleted panelPackage
        Map<String, PanelItem> currMap = new HashMap<String, PanelItem>();//store the new epg data to check which data need to by deleted
        List<PanelItem> oldPanelItemList = panelItemRepository.findAllPanelItemEpgList(panelItemList.get(0).getDistrictCode());//need to do
        Map<String, PanelItem> oldMap = new HashMap<String, PanelItem>();
        for(PanelItem item : oldPanelItemList){
            oldMap.put(item.getDistrictCode()+item.getEpgPanelitemId(),item);
        }
        for (PanelItem panelItem : panelItemList) {
                convertPanelItem(panelItem);
            PanelItem item = oldMap.get(panelItem.getDistrictCode()+panelItem.getEpgPanelitemId());
            if (item != null && item.getId() != null) {
                panelItem.setId(item.getId());
                if (panelItem.getUpdateTime() == null) {
                    panelItem.setUpdateTime(new Date());
                }
                panelItemRepository.updateEpgPanelItem(panelItem);
            } else {
                panelItemRepository.savePanelItem(panelItem);
            }
            currMap.put(panelItem.getDistrictCode()+panelItem.getEpgPanelitemId(), panelItem);
        }

        //update the deleted data

        if (!CollectionUtils.isEmpty(oldPanelItemList)) {
            for (PanelItem panelItem : oldPanelItemList) {
                if (currMap.get(panelItem.getDistrictCode()+panelItem.getEpgPanelitemId()) == null) {
                    panelItem.setOnlineStatus(OnlineStatus.DOWNLINE.getValue());
                    panelItemRepository.updateEpgPanelItem(panelItem);
                }
            }
        }
        LOGGER.info("###########同步面板项#########  end");

        LOGGER.info("###########更新面板项（refItem and parentItem）#########  begin");
        //update the parentItemId by epPanelitemParentid
        for (PanelItem panelItem : panelItemList) {
            //set the refItemId and parentItemParentId
            if (panelItem.getEpgRefItemId() != null || panelItem.getEpgPanelitemParentid() != null) {
                convertUpdatedPanelItem(panelItem,currMap);
            }
            panelItemRepository.updateEpgPanelItem(panelItem);
        }
        LOGGER.info("###########更新面板项（refItem and parentItem）#########  end");
    }

   /* private void insertNavigation(List<Navigation> navigationList) throws Exception {
       *//* epgNavList = navigationRepository.findNavigationList();
        if (!CollectionUtils.isEmpty(epgNavList)) {
            for (Navigation nav : epgNavList) {
                existedMap.put(nav.getEpgNavId(), nav);
            }
        }*//*
        navigationRepository.deleteByEpgNavId();
        if (!CollectionUtils.isEmpty(navigationList)) {
            for (Navigation navigation : navigationList) {
                convertNavigation(navigation);
            }
            navigationRepository.batchSaveNavigation(navigationList);
        }
        LOGGER.info("insert Navigation success");
    }
*/
    private void insertPanelPanelItemMap(List<PanelPanelItemMap> panelItemMapList) throws Exception {
        if (!CollectionUtils.isEmpty(panelItemMapList)) {
            LOGGER.info("##########同步面板与面板项的关系 #########  begin");
            panelPanelItemMapRepository.deleteByEpgRelationId(panelItemMapList.get(0).getDistrictCode());// need to do
            LOGGER.info("########## 成功删除面板与面板项的关系 #########");
            for (PanelPanelItemMap pim : panelItemMapList) {
                convertPanelItemMap(pim);
            }
            panelPanelItemMapRepository.batchSaveEpgMap(panelItemMapList);
        }
        LOGGER.info("##########同步面板与面板项的关系 #########  end");
    }

  /*  private void insertPanelPackageMap(List<PanelPackageMap> panelPackageMapList) throws Exception {
        panelPackageMapRepository.deleteByEpgRelId();
        if (!CollectionUtils.isEmpty(panelPackageMapList)) {
            for (PanelPackageMap map : panelPackageMapList) {
                convertPanelPackageMap(map);
            }
            panelPackageMapRepository.batchSavePanelPackageMap(panelPackageMapList);
        }
        //get the customMap with epgPanel or epgNavId
        List<PanelPackageMap> mapList = panelPackageMapRepository.findCustomMapsWithEpgData();
        if (!CollectionUtils.isEmpty(mapList)) {
            for (PanelPackageMap map : mapList) {
                convertMapPanelAndNav(map);
                panelPackageMapRepository.updateMap(map);
            }
        }
        LOGGER.info("insert PanelPackageMap success");
    }*/

   /* private void insertPreviewItem(List<PreviewItem> previewItemList) throws Exception {
        previewItemRepository.deleteByEpgIoid();
        if (!CollectionUtils.isEmpty(previewItemList)) {
            for (PreviewItem item : previewItemList) {
                convertPreviewItem(item);
            }
            previewItemRepository.batchSavePreviewItem(previewItemList);
        }
        LOGGER.info("insert PreviewItem success");
    }*/

    private void insertPreviewItemData(List<PreviewItemData> previewItemDataList) throws Exception {
        if (!CollectionUtils.isEmpty(previewItemDataList)) {
            LOGGER.info("#########  同步预览模块表-实例表 ###########  begin");
            previewItemDataRepository.deleteByEpgID(previewItemDataList.get(0).getDistrictCode());
            LOGGER.info("#########  成功删除预览模块表-实例表 ###########");
            for (PreviewItemData pi : previewItemDataList) {
                convertPreviewItemData(pi);
            }
            previewItemDataRepository.batchSaveEpgPreviewItemData(previewItemDataList);
        }
        LOGGER.info("#########  同步预览模块表-实例表 ###########  end");
    }

    private void convertPreviewItemData(PreviewItemData pi) {
        pi.setEpgIoid(pi.getIoid());
        pi.setIoid(null);

        pi.setEpgContentId(pi.getContentId());
        pi.setContentId(null);

        pi.setEpgContentItemId(pi.getContentItemId());
        pi.setContentItemId(null);

        pi.setEpgTemplateId(pi.getTemplateId());
        pi.setTemplateId(null);

       /* if (pi.getItemLeft() == null) {
            pi.setLeft(0);
        } else {
            pi.setLeft(pi.getItemLeft() - 1);
        }*/
        pi.setLeft(pi.getItemLeft());
        pi.setItemLeft(null);

      /*  if (pi.getItemTop() == null) {
            pi.setTop(0);
        } else {
            pi.setTop(pi.getItemTop() - 1);
        }*/
        pi.setTop(pi.getItemTop());
        pi.setItemTop(null);

        pi.setWidth(pi.getItemWidth());
        pi.setItemWidth(null);

        pi.setHeight(pi.getItemHeight());
        pi.setItemHeight(null);

        pi.setSort(pi.getSortNum());
        pi.setSortNum(null);

        //get panelId from table panel by epgContentId
        if (pi.getEpgContentId() != null) {
            if (panelMap.size() == 0) {
                getPanelByEpgPanelId(pi.getDistrictCode());
            }
            Panel panel = panelMap.get(pi.getEpgContentId());
            if (panel != null) {
                pi.setContentId(panel.getId());
            }
        }
        //get panelItemId from tabel panel_item by epgContentItemId
        if (pi.getEpgContentItemId() != null) {
            PanelItem panelItem = getPanelItemByEpgId(pi.getEpgContentItemId(),pi.getDistrictCode());
            if (panelItem != null) {
                pi.setContentItemId(panelItem.getId());
            }
        }
    }

    /*private void convertPreviewItem(PreviewItem pi) {
        pi.setId(null);

        pi.setTemplateId(null);
        //get templateId from preview_template by epgTemplateId
        if (pi.getEpgTemplateId() != null) {
            if (previewTemplateMap.size() == 0) {
                getTemplateByEpgTempId();
            }
            PreviewTemplate template = previewTemplateMap.get(pi.getEpgTemplateId());
            if (template != null) {
                pi.setTemplateId(template.getId());
            }
        }
    }*/

    private void convertPanelItemMap(PanelPanelItemMap pim) {
        pim.setEpgRelId(pim.getId());
        pim.setId(null);

        pim.setEpgPanelId(pim.getPanelId());
        pim.setPanelId(null);

        pim.setEpgPanelitemId(pim.getPanelItemId());
        pim.setPanelItemId(null);

        //set panel_id from table panel by epg_panel_id
        if (pim.getEpgPanelId() != null) {
            if (panelMap.size() == 0) {
                getPanelByEpgPanelId(pim.getDistrictCode());
            }
            Panel panel = panelMap.get(pim.getEpgPanelId());
            if (panel != null) {
                pim.setPanelId(panel.getId());
            }
        }

        //set panel_item_id from table panel_item by epg_panelItem_id
        if (pim.getEpgPanelitemId() != null) {
            PanelItem panelItem = getPanelItemByEpgId(pim.getEpgPanelitemId(),pim.getDistrictCode());
            if (panelItem != null) {
                pim.setPanelItemId(panelItem.getId());
            }
        }

        if (pim.getCreateTime() == null) {
            pim.setCreateTime(new Date());
        }
    }

    private void convertPanelPackageMap(PanelPackageMap ppm) {
        ppm.setId(null);

        ppm.setPackageId(null);

        ppm.setPanelId(null);

        ppm.setNavId(null);

        //epg_package_id from table panel_package by the epgPanelPackageId
        if (ppm.getEpgPackageId() != null) {
            if (panelPackageMap.size() == 0) {
                getPackageByEpgPackageId();
            }
            PanelPackage panelPackage = panelPackageMap.get(ppm.getEpgPackageId());
            if (panelPackage != null) {
                ppm.setPackageId(panelPackage.getId());
            }
        }

        //epg_nav_id from Navigation by epgNavId
        if (StringUtils.isNotBlank(ppm.getEpgNavId())) {
            List<Long> epgNavIds = StringUtil.splitToLong(ppm.getEpgNavId());
            List<Navigation> navList = findNavListByNavIds(epgNavIds);
            if (navList != null) {
                StringBuilder sb = new StringBuilder("");
                for (Navigation navigation : navList) {
                    sb.append(navigation.getId()).append(",");
                }
                if (StringUtils.isBlank(sb.toString())) {
                    ppm.setNavId(sb.toString());
                } else {
                    ppm.setNavId(sb.toString().substring(0, sb.toString().length() - 1));
                }
            }
        }
        //set epg_panel_id from table panel by the epgPanelId
        if (ppm.getEpgPanelId() != null) {
            if (panelMap.size() == 0) {
//                getPanelByEpgPanelId();
            }
            Panel panel = panelMap.get(ppm.getEpgPanelId());
            if (panel != null) {
                ppm.setPanelId(panel.getId());
            }
        }
    }

    /*private void convertMapPanelAndNav(PanelPackageMap ppm) {
        //set epg_panel_id from table panel by the epgPanelId
        if (ppm.getEpgPanelId() != null) {
            if (panelMap.size() == 0) {
                getPanelByEpgPanelId();
            }
            Panel panel = panelMap.get(ppm.getEpgPanelId());
            if (panel != null) {
                ppm.setPanelId(panel.getId());
            }
        }

        //epg_nav_id from Navigation by epgNavId
       *//* if (StringUtils.isNotBlank(ppm.getEpgNavId())) {
            List<Long> epgNavIds = StringUtil.splitToLong(ppm.getEpgNavId());
            List<Navigation> navList = findNavListByNavIds(epgNavIds);
            Map<Long, Navigation> currMap = new HashMap<Long, Navigation>();
            for (Navigation nav : navList) {
                currMap.put(nav.getEpgNavId(), nav);
            }
            String navIds = ppm.getNavId();
            if (!CollectionUtils.isEmpty(navList)) {
                for (Long epgNavId : epgNavIds) {
                    String existedNavId = existedMap.get(epgNavId).getId().toString();
                    String curNavId = currMap.get(epgNavId).getId().toString();
                    navIds = navIds.replace(existedNavId, curNavId);
                }
                ppm.setNavId(navIds);
            }
        }*//*
    }*/

    private void convertNavigation(Navigation nav) {
        nav.setId(null);

        if (nav.getCreateTime() == null) {
            nav.setCreateTime(new Date());
        }

        if (nav.getUpdateTime() == null) {
            nav.setUpdateTime(new Date());
        }
    }

    private void convertPackage(PanelPackage pp) {
        pp.setId(null);

        if (pp.getCreateTime() == null) {
            pp.setCreateTime(new Date());
        }
    }

    private void convertPanelItem(PanelItem pi) {
        pi.setEpgPanelitemId(pi.getId());
        pi.setId(null);

        pi.setEpgContentId(pi.getContentId());
        pi.setContentId(null);

        pi.setEpgRefItemId(pi.getRefItemId());
        pi.setRefItemId(null);

        pi.setEpgPanelitemParentid(pi.getParentId());
        pi.setParentId(null);

        pi.setOprUserId(pi.getUserId());
        pi.setUserId(null);

        pi.setOnlineStatus(OnlineStatus.ONLINE.getValue());

        //If the hasSubItem has the default value
        if (pi.getHasSubitem() == null) {
            pi.setHasSubItem(0);
        } else {
            pi.setHasSubItem(pi.getHasSubitem());
        }

       /* if (pi.getEpgContentId() != null) {
            if (pi.getType() != null && (Constant.PANEL_ITEM_FIRST_CLASS_PROGRAM_TYPE == pi.getType() || Constant.PANEL_ITEM_SECOND_CLASS_PROGRAM_TYPE == pi.getType())) {
                pi.setActionUrl(getValue("programActionUrl") + pi.getEpgContentId());
            } else if (pi.getType() != null && Constant.PANEL_ITEM_TVSET_TYPE == pi.getType()) {
                pi.setActionUrl(getValue("TVsetActionUrl") + pi.getEpgContentId());
            }
        }*/
    }

    private PanelItem getPanelItemByEpgId(Long epgRefId,String districtCode) {
        if (panelItemMap.size() == 0) {
            getPanelItemByEpgItemId(districtCode);
        }
        PanelItem item = panelItemMap.get(epgRefId);
        return item;
    }

    private void convertUpdatedPanelItem(PanelItem panelItem,Map<String,PanelItem> map) {
        //set panelItemParentId
        if (panelItem.getEpgPanelitemParentid() != null) {
            PanelItem item = getPanelItemByEpgId(panelItem.getEpgPanelitemParentid(),panelItem.getDistrictCode());
//            PanelItem item = map.get(panelItem.getDistrictCode()+panelItem.getEpgPanelitemParentid());
            if (item != null) {
                panelItem.setPanelItemParentId(item.getId());
            }
        }
        //set refItemId
        if (panelItem.getEpgRefItemId() != null) {
            PanelItem item = getPanelItemByEpgId(panelItem.getEpgRefItemId(),panelItem.getDistrictCode());
//            PanelItem item = map.get(panelItem.getDistrictCode()+panelItem.getEpgRefItemId());
            if (item != null) {
                panelItem.setRefItemId(item.getId());
            }
        }
    }


    private void convertPanel(Panel pa) {
        pa.setEpgPanelId(pa.getId());
        pa.setId(null);

        pa.setEpgTemplateId(pa.getTemplateId());
        pa.setTemplateId(null);

        pa.setEpgRefPanelId(pa.getRefPanelId());
        pa.setRefPanelId(null);

        if (StringUtils.isNotBlank(pa.getStyle())) {
            pa.setPanelStyle(pa.getStyle());
            pa.setStyle(null);
        }

        if (StringUtils.isNotBlank(pa.getName())) {
            pa.setPanelName(pa.getName());
            pa.setName(null);
        }
        if (StringUtils.isNotBlank(pa.getTitle())) {

            pa.setPanelTitle(pa.getTitle());
            pa.setTitle(null);
        }

        if (StringUtils.isNotBlank(pa.getIcon())) {
            pa.setPanelIcon(pa.getIcon());
            pa.setIcon(null);
        }

        if (StringUtils.isNotBlank(pa.getImageUrl())) {
            pa.setImgUrl(pa.getImageUrl());
            pa.setImageUrl(null);
        }
        pa.setOprUserId(pa.getOprUserid());
        pa.setOprUserid(null);

        if (pa.getCreateTime() == null) {
            pa.setCreateTime(new Date());
        }
        pa.setOnlineStatus(OnlineStatus.ONLINE.getValue());
    }

    private void getPanelByEpgPanelId(String districtCode) {
        List<Panel> panelList = panelRepository.findIdAndEpgIdList(districtCode);
        for (Panel panel : panelList) {
            panelMap.put(panel.getEpgPanelId(), panel);
        }
    }

    private void getTemplateByEpgTempId() {

        List<PreviewTemplate> templateList = previewTemplateRepository.findIdAndEpgIdList();
        for (PreviewTemplate template : templateList) {
            previewTemplateMap.put(template.getEpgTemplateId(), template);
        }
    }

    private void getPanelItemByEpgItemId(String districtCode) {
        List<PanelItem> panelItemList = panelItemRepository.findIdAndEpgIdList(districtCode);
        for (PanelItem panelItem : panelItemList) {
            panelItemMap.put(panelItem.getEpgPanelitemId(), panelItem);
        }
    }

    private void getPackageByEpgPackageId() {
        List<PanelPackage> panelPackageList = panelPackageRepository.findIdAndEpgIdList();
        for (PanelPackage panelPackage : panelPackageList) {
            panelPackageMap.put(panelPackage.getEpgPackageId(), panelPackage);
        }
    }

    private List<Navigation> findNavListByNavIds(List<Long> ids) {
        return navigationRepository.findNavigationListByEpgNavIds(ids);
    }

}
