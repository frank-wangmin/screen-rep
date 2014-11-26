package com.ysten.local.bss.panel.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ysten.boss.systemconfig.repository.ISystemConfigRepository;
import com.ysten.cache.annotation.MethodFlushAllPanel;
import com.ysten.cache.annotation.MethodFlushInterfacePackage;
import com.ysten.local.bss.panel.domain.*;
import com.ysten.local.bss.panel.enums.IsOrNotDefault;
import com.ysten.local.bss.panel.enums.OnlineStatus;
import com.ysten.local.bss.panel.enums.YesOrNo;
import com.ysten.local.bss.panel.repository.*;
import com.ysten.local.bss.panel.service.IPanelSyncService;
import com.ysten.local.bss.util.DateUtils;
import com.ysten.local.bss.util.HttpUtils;
import com.ysten.local.bss.util.HttpUtils.HttpResponseWrapper;
import com.ysten.local.bss.util.JsonUtil;
import com.ysten.local.bss.util.NewCollectionsUtils;
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
 * @author cwang
 * @version 2014-5-30 上午11:35:48
 */
@Service
public class PanelSyncServiceImpl implements IPanelSyncService {
    private static final String UTF8 = "utf-8";
    @Autowired
    private IPanelPackageRepository panelPackageRepository;
    @Autowired
    private IPanelRepository panelRepository;
    @Autowired
    private INavigationRepository navigationRepository;
    @Autowired
    private IPanelPackageMapRepository panelPackageMapRepository;
    @Autowired
    private IPanelPanelItemMapRepository panelPanelItemMapRepository;
    @Autowired
    private IPreviewItemRepository previewItemRepository;
    @Autowired
    private IPreviewTemplateRepository previewTemplateRepository;
    @Autowired
    private IPreviewItemDataRepository previewItemDataRepository;
    @Autowired
    private ISystemConfigRepository systemConfigRepository;

    @Autowired
    private IPanelItemRepository panelItemRepository;

    private transient Map<Long, PanelPackage> panelPackageMap;
    private transient Map<Long, Panel> panelMap;
    private transient Map<Long, PanelItem> panelItemMap;
    private transient Map<Long, PreviewTemplate> previewTemplateMap;
    private transient List<Navigation> epgNavList;
    private transient Map<Long, Navigation> existedMap;
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    private static final String QUESTION_MARK = "?";
    private static final String EQUAL_SIGN = "=";
    private transient List<PanelPackage> panelPackageList;
    private transient List<Panel> panelList;
    private transient List<PanelItem> panelItemList;
    private transient List<Navigation> navigationList;
    private transient List<PanelPanelItemMap> panelItemMapList;
    private transient List<PanelPackageMap> panelPackageMapList;
    private transient List<PreviewItemData> previewItemDataList;
    private transient List<Long> notOnlinePanelList;

/*    private static final String ELEMENT_ID = "id";
    private static final String ELEMENT_TITLE = "title";
    private static final String ELEMENT_PANEL = "panel";
    private static final String ELEMENT_BOX = "box";
    private static final String ELEMENT_HEAD_NAV = "nav";
    private static final String ELEMENT_FOOT_NAV = "bottomnav";
    private static final String ELEMENT_IMGURL = "imgurl";
    private static final String ELEMENT_ACTION = "action";
    private static final String ELEMENT_ACTION_URL = "actionurl";
    private static final String ELEMENT_FOCUS_TEXT = "focustext";*/


    private static final Logger LOGGER = LoggerFactory.getLogger(PanelSyncServiceImpl.class);

    public void loadDatas() {
        //get panelPackage data
        panelPackageList = getData("sysPanelPackageUrl", new TypeToken<List<PanelPackage>>() {
        });
        //get panel data
        panelList = getData("sysPanelUrl", new TypeToken<List<Panel>>() {
        });
        //get panelItem data
        panelItemList = getData("sysPanelItemUrl", new TypeToken<List<PanelItem>>() {
        });
        //get navigation data
        navigationList = getData("sysNavUrl", new TypeToken<List<Navigation>>() {
        });
        //get PanelPanelItemMap data
        panelItemMapList = getData("sysPanelItemMapUrl", new TypeToken<List<PanelPanelItemMap>>() {
        });
        //get panelPackageMap data
        panelPackageMapList = getData("sysPanelPackageMapUrl", new TypeToken<List<PanelPackageMap>>() {
        });
        //get previewItemData data
        previewItemDataList = getData("sysPreviewItemDataUrl", new TypeToken<List<PreviewItemData>>() {
        });

    }

    private <T> List<T> getData(String key, TypeToken t) {
        String url = getValue(key);
        String param = "";
        HttpResponseWrapper wrapper = HttpUtils.doPost(url, param, UTF8);
        int returnCode = wrapper.getHttpStatus();
        if (returnCode == 200) {
            String rsp = wrapper.getResponse();
            List<T> tList = gson.fromJson(rsp, t.getType());
            return tList;
        }
        return null;
    }

    @MethodFlushAllPanel
    @MethodFlushInterfacePackage
//    public void syncEpgPanelDatas(Long oprUserId) throws Exception {
    public void syncEpgPanelDatas() throws Exception {
        init();
        sysPanelPackage();
//        sysPreviewTemplate();
        sysPanel();
        sysPanelItem();
        sysNav();
        sysPanelItemMap();
        sysPanelPackageMap();
//        sysPreviewItem();
        sysPreviewItemData();
//        sysPreviewItemData(oprUserId);
//        setPanelItemContenType();
    }

    private void init() {
        panelPackageMap = NewCollectionsUtils.map();
        panelMap = NewCollectionsUtils.map();
        panelItemMap = NewCollectionsUtils.map();
        previewTemplateMap = NewCollectionsUtils.map();
        epgNavList = NewCollectionsUtils.list();
        existedMap = NewCollectionsUtils.map();
        notOnlinePanelList = NewCollectionsUtils.list();
    }

    @Override
    public void syncCenterPanelDatas() throws Exception {
        init();
        String url = getValue("syncCenterPanelData");
        if (StringUtils.isNotBlank(url)) {
            insertPanelPackage(url);
//            insertPreviewTemplate(url);
            insertPanel(url);
            insertPanelItem(url);
            insertNavigation(url);
            insertPanelPanelItemMap(url);
            insertPanelPackageMap(url);
//            insertPreviewItem(url);
            insertPreviewItemData(url);
        }
    }

    private String getData(String url) {
        String param = "";
        String rsp = "";
        HttpResponseWrapper wrapper = HttpUtils.doPost(url, param, UTF8);
        int returnCode = wrapper.getHttpStatus();
        if (returnCode == 200) {
            rsp = wrapper.getResponse();
        }
        return rsp;
    }

    private void saveOrUpdatePanelPackage(boolean b, List<PanelPackage> panelPackageList) {
        //add or update the not deleted panelPackage
        Map<Long, PanelPackage> currMap = new HashMap<Long, PanelPackage>();//store the new epg data to check which data need to by deleted
        Map<Long, PanelPackage> oldMap = new HashMap<Long, PanelPackage>();
        List<PanelPackage> oldPackageList = panelPackageRepository.findAllEpgList();//The old epg data in our db
        for (PanelPackage panelPackage : oldPackageList) {
            oldMap.put(panelPackage.getEpgPackageId(), panelPackage);
        }
        for (PanelPackage panelPackage : panelPackageList) {
            if (panelPackage.getPackageType().intValue() != 4) {
                if (b) {//if true, convertCenterPackage
                    convertCenterPackage(panelPackage);
                } else {
                    convertPackage(panelPackage);
                }
                PanelPackage existedPanelPackage = oldMap.get(panelPackage.getEpgPackageId());
                if (existedPanelPackage != null && existedPanelPackage.getId() != null) {
                    panelPackage.setId(existedPanelPackage.getId());
                    if (panelPackage.getUpdateTime() == null) {
                        panelPackage.setUpdateTime(new Date());
                    }
                    panelPackageRepository.updateEpgPanelPackage(panelPackage);
                } else {
                    panelPackageRepository.savePanelPackage(panelPackage);
                }
                currMap.put(panelPackage.getEpgPackageId(), panelPackage);
            }
        }

        //update the deleted data
//        List<PanelPackage> oldPackageList = panelPackageRepository.findAllEpgList();//The old epg data in our db
        if (!CollectionUtils.isEmpty(oldPackageList)) {
            for (PanelPackage panelPackage : oldPackageList) {
                if (currMap.get(panelPackage.getEpgPackageId()) == null) {
                    panelPackage.setOnlineStatus(OnlineStatus.DOWNLINE.getValue());
                    panelPackageRepository.updateEpgPanelPackage(panelPackage);
                }
            }
        }

       /* for (PanelPackage panelPackage : panelPackageList) {
            if (panelPackage.getPackageType().intValue() != 4) {
            }
            if (b) {//if true, convertCenterPackage
                convertCenterPackage(panelPackage);
            } else {
                convertPackage(panelPackage);
            }
            PanelPackage existedPanelPackage = panelPackageRepository.getPackageByEpgPackageId(panelPackage.getEpgPackageId());
            if (existedPanelPackage != null && existedPanelPackage.getId() != null) {
                panelPackage.setId(existedPanelPackage.getId());
                if (panelPackage.getUpdateTime() == null) {
                    panelPackage.setUpdateTime(new Date());
                }
                panelPackageRepository.update(panelPackage);
            } else {
                panelPackageRepository.savePanelPackage(panelPackage);
            }
        }*/
    }

    private void insertPanelPackage(String url) throws Exception {
        String rsp = getData(url + QUESTION_MARK + Constant.DISTRIBUTE_TYPE + EQUAL_SIGN + Constant.DISTRIBUTE_TYPE_PANEL_PACKAGE);
        if (StringUtils.isNotBlank(rsp)) {
            List<PanelPackage> panelPackageList = gson.fromJson(rsp, new TypeToken<List<PanelPackage>>() {
            }.getType());
            if (!CollectionUtils.isEmpty(panelPackageList)) {
                saveOrUpdatePanelPackage(true, panelPackageList);
            }
            LOGGER.info("#### after insert panelPackage ####");
        }
    }

    private void insertPreviewTemplate(String url) throws Exception {
        String rsp = getData(url + QUESTION_MARK + Constant.DISTRIBUTE_TYPE + EQUAL_SIGN + Constant.DISTRIBUTE_TYPE_PREVIEW_TEMPLATE);
        if (StringUtils.isNotBlank(rsp)) {
            List<PreviewTemplate> previewTemplateList = gson.fromJson(rsp, new TypeToken<List<PreviewTemplate>>() {
            }.getType());
            previewTemplateRepository.deleteByEpgIds();
            if (!CollectionUtils.isEmpty(previewTemplateList)) {
                for (PreviewTemplate previewTemplate : previewTemplateList) {
                    convertCenterPreviewTemplate(previewTemplate);
                }
                previewTemplateRepository.batchSavePreviewTemplate(previewTemplateList);
            }
            LOGGER.info("#### after insert PreviewTemplate ####");
        }
    }

    private void saveOrUpdatePanel(boolean b, List<Panel> panelList) {
        //add or update the epg panel data
        Map<Long, Panel> currMap = new HashMap<Long, Panel>();//store the new epg data to check which data need to by deleted
        Map<Long, Panel> oldMap = new HashMap<Long, Panel>();
        List<Panel> oldPanelList = panelRepository.findAllEpgListByDistrictCode(Constant.EPG_DISTRICT_CODE);
        for (Panel panel : oldPanelList) {
            oldMap.put(panel.getEpgPanelId(), panel);
        }
        notOnlinePanelList = new ArrayList<Long>();
        for (Panel panel : panelList) {
            if (panel.getType().intValue() != 4) {
                if (b) {
                    convertCenterPanel(panel);
                } else {
                    convertPanel(panel);
                }
                if (panel.getStatus() != null && panel.getStatus() == OnlineStatus.ONLINE.getValue()) {
                    Panel pa = oldMap.get(panel.getEpgPanelId());
                    if (pa != null && pa.getId() != null) {
                        panel.setId(pa.getId());
                        if (panel.getUpdateTime() == null) {
                            panel.setUpdateTime(new Date());
                        }
                        panel.setIsCustom(pa.getIsCustom());
//                        panel.setDisplay(pa.getDisplay());
//                        panel.setIsLock(pa.getIsLock());
                        panel.setBigimg(pa.getBigimg());
                        panel.setSmallimg(pa.getSmallimg());
                        panelRepository.updateEpgPanel(panel);
                    } else {
//                        panel.setIsLock("true");
//                        panel.setDisplay("true");
                        panel.setIsCustom(0);
                        panelRepository.savePanel(panel);
                    }
                } else
                /*if (panel.getStatus() == OnlineStatus.INIT.getValue())*/ {
                    Panel pa = oldMap.get(panel.getEpgPanelId());
                    if (pa == null) {
                        panel.setIsCustom(0);
                        panelRepository.savePanel(panel);
                    } else {
                        if (pa.getStatus() == OnlineStatus.ONLINE.getValue()) {//只更新面板由上线转为非上线的
                            notOnlinePanelList.add(panel.getId());
                        }
                    }
                }
                currMap.put(panel.getEpgPanelId(), panel);
            }
        }

        //update the deleted data

        if (!CollectionUtils.isEmpty(oldPanelList)) {
            for (Panel panel : oldPanelList) {
                if (currMap.get(panel.getEpgPanelId()) == null) {
                    panel.setOnlineStatus(OnlineStatus.DOWNLINE.getValue());
                    panelRepository.updateEpgPanel(panel);
                }
            }
        }

        LOGGER.info("###########更新面板（refID）#########  begin");
        //update the parentItemId by epPanelitemParentid
        for (Panel panel : panelList) {
            if (panel.getType().intValue() != 4) {
                if (panel.getEpgRefPanelId() != null) {
                    convertUpdatedPanel(panel);
                    panelRepository.updateEpgPanel(panel);
                }
            }
        }
        LOGGER.info("###########更新面板（refID）#########  end");
    }


    private void saveOrUpdateNav(boolean b, List<Navigation> list) {
        //add or update the not deleted panelPackage
        Map<Long, Navigation> currMap = new HashMap<Long, Navigation>();//store the new epg data to check which data need to by deleted
        List<Navigation> oldNavList = navigationRepository.findEpgNavList();
        Map<Long, Navigation> oldMap = new HashMap<Long, Navigation>();
        for (Navigation nav : oldNavList) {
            oldMap.put(nav.getEpgNavId(), nav);
        }
        for (Navigation nav : list) {
            if (b) {
                convertCenterNavigation(nav);
            } else {
                convertNavigation(nav);
            }
            Navigation navigation = oldMap.get(nav.getEpgNavId());
            if (navigation != null && navigation.getId() != null) {
                nav.setId(navigation.getId());
                if (nav.getUpdateTime() == null) {
                    nav.setUpdateTime(new Date());
                }
                navigationRepository.updateEpgNavDefine(nav);
            } else {
                navigationRepository.saveNavigation(nav);
            }
            currMap.put(nav.getEpgNavId(), nav);
        }

        //update the deleted data

        if (!CollectionUtils.isEmpty(oldNavList)) {
            for (Navigation navigation : oldNavList) {
                if (currMap.get(navigation.getEpgNavId()) == null) {
                    navigation.setOnlineStatus(OnlineStatus.DOWNLINE.getValue());
                    navigationRepository.updateEpgNavDefine(navigation);
                }
            }
        }
    }

    private void saveOrUpdatePanelItem(boolean b, List<PanelItem> panelItemList) {
        //add or update the not deleted panelPackage
        Map<Long, PanelItem> currMap = new HashMap<Long, PanelItem>();//store the new epg data to check which data need to by deleted
        Map<Long, PanelItem> noNeedUpdateMap = new HashMap<Long, PanelItem>();
        List<PanelItem> oldPanelItemList = panelItemRepository.findAllPanelItemEpgList(Constant.EPG_DISTRICT_CODE);
        List<PanelItem> notHandledList = new ArrayList<PanelItem>();
        if (notOnlinePanelList != null && notOnlinePanelList.size() > 0) {
            notHandledList = panelItemRepository.findPanelItemListByPanelIdList(notOnlinePanelList);
        }

        //handle the no need updated panelItem
        for (PanelItem item : notHandledList) {
            noNeedUpdateMap.put(item.getEpgPanelitemId(), item);
        }
        Map<Long, PanelItem> oldMap = new HashMap<Long, PanelItem>();
        for (PanelItem item : oldPanelItemList) {
            oldMap.put(item.getEpgPanelitemId(), item);
        }
        for (PanelItem panelItem : panelItemList) {
            if (b) {
                convertCenterPanelItem(panelItem);
            } else {
                convertPanelItem(panelItem);
            }
            PanelItem item = oldMap.get(panelItem.getEpgPanelitemId());
            if (item != null && item.getId() != null) {
                if (noNeedUpdateMap.get(panelItem.getEpgPanelitemId()) == null) {//handle the updated panelItem
                    panelItem.setId(item.getId());
                    if (panelItem.getUpdateTime() == null) {
                        panelItem.setUpdateTime(new Date());
                    }
                    panelItemRepository.updateEpgPanelItem(panelItem);
                } else {
                    LOGGER.info("no need updated item :" + panelItem.getEpgPanelitemId());
                }
            } else {
                panelItemRepository.savePanelItem(panelItem);
            }
            currMap.put(panelItem.getEpgPanelitemId(), panelItem);
        }

        currMap.putAll(noNeedUpdateMap);//handle the deleted panelItem

        //update the deleted data

        if (!CollectionUtils.isEmpty(oldPanelItemList)) {
            for (PanelItem panelItem : oldPanelItemList) {
                if (currMap.get(panelItem.getEpgPanelitemId()) == null) {
                    panelItem.setOnlineStatus(OnlineStatus.DOWNLINE.getValue());
                    panelItemRepository.updateEpgPanelItem(panelItem);
                }
            }
        }
        LOGGER.info("###########同步面板项#########  end");

        LOGGER.info("###########更新面板项（refItem and parentItem）#########  begin");
        //update the parentItemId by epPanelitemParentid
        for (PanelItem panelItem : panelItemList) {
            if (noNeedUpdateMap.get(panelItem.getEpgPanelitemId()) == null) {//handle the updated panelItem related to the panel with the status is not online
                //set the refItemId and parentItemParentId
                if (panelItem.getEpgRefItemId() != null || panelItem.getEpgPanelitemParentid() != null) {
                    convertUpdatedPanelItem(panelItem);
                }
                String contentType = "";
                if (panelItem.getHasSubItem() == YesOrNo.YES.getValue()) {//parentItem
                    if (panelItem.getContentType().equals(Constant.PANEL_ITEM_CONTENT_TYPE)) {
                        PanelItem refItem = currMap.get(panelItem.getEpgRefItemId());
                        if (refItem != null) {
                            contentType = refItem.getContentType();
                            panelItem.setContentType(contentType);
                        }
                    }
                } else {//subitem
                    if (StringUtils.isBlank(panelItem.getContentType())) {
                        PanelItem parentItem = currMap.get(panelItem.getEpgPanelitemParentid());//parent panelItem
                        if (parentItem != null) {
                            contentType = parentItem.getContentType();
                            if (contentType.equals(Constant.PANEL_ITEM_CONTENT_TYPE)) {
                                PanelItem refItem = currMap.get(parentItem.getEpgRefItemId());//refItem of parentItem
                                if (refItem != null) {
                                    contentType = refItem.getContentType();
                                }
                            }
                            panelItem.setContentType(contentType);
                        }
                    }
                }
                panelItemRepository.updateEpgPanelItem(panelItem);
            }
        }
        LOGGER.info("###########更新面板项（refItem and parentItem）#########  end");
    }

    private void insertPanel(String url) throws Exception {
        String rsp = getData(url + QUESTION_MARK + Constant.DISTRIBUTE_TYPE + EQUAL_SIGN + Constant.DISTRIBUTE_TYPE_PANEL);
        if (StringUtils.isNotBlank(rsp)) {
            List<Panel> panelList = gson.fromJson(rsp, new TypeToken<List<Panel>>() {
            }.getType());
            if (!CollectionUtils.isEmpty(panelList)) {
                saveOrUpdatePanel(true, panelList);
            }
            LOGGER.info("#### after insertPanel ####");
        }
    }

    private void insertPanelItem(String url) throws Exception {
        String rsp = getData(url + QUESTION_MARK + Constant.DISTRIBUTE_TYPE + EQUAL_SIGN + Constant.DISTRIBUTE_TYPE_PANEL_ITEM);
        if (StringUtils.isNotBlank(rsp)) {
            List<PanelItem> panelItemList = gson.fromJson(rsp, new TypeToken<List<PanelItem>>() {
            }.getType());
            if (!CollectionUtils.isEmpty(panelItemList)) {
                saveOrUpdatePanelItem(true, panelItemList);
            }
            LOGGER.info("###########更新面板项（refItem and parentItem）#########  end");
        }
    }

    private void insertNavigation(String url) throws Exception {
        String rsp = getData(url + QUESTION_MARK + Constant.DISTRIBUTE_TYPE + EQUAL_SIGN + Constant.DISTRIBUTE_TYPE_NAVIGATION);
        if (StringUtils.isNotBlank(rsp)) {
            List<Navigation> navigationList = gson.fromJson(rsp, new TypeToken<List<Navigation>>() {
            }.getType());
            if (!CollectionUtils.isEmpty(navigationList)) {
                saveOrUpdateNav(true, navigationList);
            }
            LOGGER.info("insert Navigation success");
        }
    }

    private void insertPanelPanelItemMap(String url) throws Exception {
        String rsp = getData(url + QUESTION_MARK + Constant.DISTRIBUTE_TYPE + EQUAL_SIGN + Constant.DISTRIBUTE_TYPE_PANEL_ITEM_MAP);
        if (StringUtils.isNotBlank(rsp)) {
            List<PanelPanelItemMap> panelItemMapList = gson.fromJson(rsp, new TypeToken<List<PanelPanelItemMap>>() {
            }.getType());
//            panelPanelItemMapRepository.deleteByEpgRelId();
            if (!CollectionUtils.isEmpty(panelItemMapList)) {
                for (PanelPanelItemMap panelItemMap : panelItemMapList) {
                    convertCenterPanelItemMap(panelItemMap);
                }
                panelPanelItemMapRepository.batchSavePanelPanelItemMap(panelItemMapList);
            }
            LOGGER.info("insert PanelPanelItemMap success");
        }
    }


    private void insertPanelPackageMap(String url) throws Exception {
        String rsp = getData(url + QUESTION_MARK + Constant.DISTRIBUTE_TYPE + EQUAL_SIGN + Constant.DISTRIBUTE_TYPE_PANEL_PACKAGE_MAP);
        if (StringUtils.isNotBlank(rsp)) {
            List<PanelPackageMap> panelPackageMapList = gson.fromJson(rsp, new TypeToken<List<PanelPackageMap>>() {
            }.getType());
            panelPackageMapRepository.deleteByEpgRelId();
            if (!CollectionUtils.isEmpty(panelPackageMapList)) {
                for (PanelPackageMap map : panelPackageMapList) {
                    convertCenterPanelPackageMap(map);
                }
                panelPackageMapRepository.batchSavePanelPackageMap(panelPackageMapList);
            }
            //get the customMap with epgPanel or epgNavId and update the relationship
            List<PanelPackageMap> mapList = panelPackageMapRepository.findCustomMapsWithEpgData();
            if (!CollectionUtils.isEmpty(mapList)) {
                for (PanelPackageMap map : mapList) {
                 /*   if (map.getEpgPanelId() != null) {
                        Panel panel = panelRepository.getPanelByEpgPanelId(map.getEpgPanelId());
                        if (panel != null) {//if the panel is existed,update the onlineStatus of the panel
                            panel.setOnlineStatus(OnlineStatus.ONLINE.getValue());
                            panelRepository.updatePanel(panel);
                        }
                    }*/
                    if (StringUtils.isNotBlank(map.getEpgNavId())) {
                        List<Long> navList = StringUtil.splitToLong(map.getEpgNavId());
                        List<Navigation> navigationList = findNavListByNavIds(navList);//find the navList by epg_nav_ids
                        if (!CollectionUtils.isEmpty(navigationList)) {//if the navigationList is not null,then update the onlineStatus of the nav
                            for (Navigation nav : navigationList) {
                                nav.setOnlineStatus(OnlineStatus.ONLINE.getValue());
                                navigationRepository.updateNavDefine(nav);//将删除的导航状态改为上线
                            }
                        }
                    }
                }
            }
            LOGGER.info("insert PanelPackageMap success");
        }
    }

    private void insertPreviewItem(String url) throws Exception {
        String rsp = getData(url + QUESTION_MARK + Constant.DISTRIBUTE_TYPE + EQUAL_SIGN + Constant.DISTRIBUTE_TYPE_PREVIEW_ITEM);
        if (StringUtils.isNotBlank(rsp)) {
            List<PreviewItem> previewItemList = JsonUtil.getList4Json(rsp, PreviewItem.class, null);

            previewItemRepository.deleteByEpgIoid();
            if (!CollectionUtils.isEmpty(previewItemList)) {
                for (PreviewItem item : previewItemList) {
                    convertCenterPreviewItem(item);
                }
                previewItemRepository.batchSavePreviewItem(previewItemList);
            }
            LOGGER.info("insert PreviewItem success");
        }
    }

    private void insertPreviewItemData(String url) throws Exception {
        String rsp = getData(url + QUESTION_MARK + Constant.DISTRIBUTE_TYPE + EQUAL_SIGN + Constant.DISTRIBUTE_TYPE_PREVIEW_ITEM_DATA);
        if (StringUtils.isNotBlank(rsp)) {
            List<PreviewItemData> previewItemDataList = gson.fromJson(rsp, new TypeToken<List<PreviewItemData>>() {
            }.getType());
//            previewItemDataRepository.deleteByEpgIoid();
            if (!CollectionUtils.isEmpty(previewItemDataList)) {
                for (PreviewItemData data : previewItemDataList) {
                    convertCenterPreviewItemData(data);
                }
                previewItemDataRepository.batchSavePreviewItemData(previewItemDataList);
            }
            LOGGER.info("insert PreviewItemData success");
        }
    }


    // 1.同步面板包
    private void sysPanelPackage() throws Exception {

        LOGGER.info("###########同步面板包#########  begin");
        Long startTime = System.currentTimeMillis();
        if (!CollectionUtils.isEmpty(panelPackageList)) {
            saveOrUpdatePanelPackage(false, panelPackageList);
        }
        LOGGER.info("the interval of sync panel package :" + (System.currentTimeMillis() - startTime));
        LOGGER.info("###########同步面板包#########   end");

    }

    //2.同步预览模板
    /*private void sysPreviewTemplate() throws Exception {

        String url = systemConfigService.getSystemConfigByConfigKey("sysPreviewTemplateUrl");
        String param = "";
        HttpResponseWrapper wrapper = HttpUtils.doPost(url, param, UTF8);
        int returnCode = wrapper.getHttpStatus();
        if (returnCode == 200) {
            String rsp = wrapper.getResponse();
            List<PreviewTemplate> list = gson.fromJson(rsp, new TypeToken<List<PreviewTemplate>>() {
            }.getType());
            LOGGER.info("###########同步预览模板#########  begin");
            if (!CollectionUtils.isEmpty(list)) {
                Map<Long, PreviewTemplate> currMap = new HashMap<Long, PreviewTemplate>();//store the new epg data to check which data need to by deleted
                List<PreviewTemplate> updatedList = new ArrayList<PreviewTemplate>();//store the updated data
                List<PreviewTemplate> saveList = new ArrayList<PreviewTemplate>();//store the data need to be save
                for (PreviewTemplate pi : list) {
                    convertPreviewTemplate(pi);
                    PreviewTemplate template = previewTemplateRepository.getTemplateByEpgTempId(pi.getEpgTemplateId());
                    if (template != null && template.getId() != null) {
                        updatedList.add(pi);
                    } else {
                        saveList.add(pi);
                    }
                    currMap.put(pi.getEpgTemplateId(), pi);
                }

                //update the deleted data
                List<PreviewTemplate> oldPreviewTemplateList = previewTemplateRepository.findAllEpgList();//The old epg data in our db
                if (!CollectionUtils.isEmpty(oldPreviewTemplateList)) {
                for (PreviewTemplate previewTemplate : oldPreviewTemplateList) {
                    if (currMap.get(previewTemplate.getEpgTemplateId())==null) {
                        previewTemplateRepository.deleteTarget(previewTemplate);
                    }
                }
                }
                if (!CollectionUtils.isEmpty(saveList)) {
                    previewTemplateRepository.batchSavePreviewTemplate(saveList);
                }
                if (!CollectionUtils.isEmpty(updatedList)) {
                    previewTemplateRepository.batchUpdate(updatedList);
                }

            }
            LOGGER.info("###########同步预览模板#########  end");
        }
    }*/

    //3.同步面板
    private void sysPanel() throws Exception {

        LOGGER.info("###########同步面板#########  begin");
        Long startTime = System.currentTimeMillis();
        if (!CollectionUtils.isEmpty(panelList)) {
            saveOrUpdatePanel(false, panelList);
        }
        LOGGER.info("the interval of sync panel:" + (System.currentTimeMillis() - startTime));
        LOGGER.info("###########同步面板#########  end");
    }

    // 4.同步面板项
    private void sysPanelItem() throws Exception {

        LOGGER.info("###########同步面板项#########  begin");
        Long startTime = System.currentTimeMillis();
        if (!CollectionUtils.isEmpty(panelItemList)) {
            saveOrUpdatePanelItem(false, panelItemList);
        }
        LOGGER.info("the interval of sync panel item :" + (System.currentTimeMillis() - startTime));
        LOGGER.info("###########批量更新面板项#########  end");
    }

    //5.同步导航

    private void sysNav() throws Exception {
        LOGGER.info("###########同步导航#########  begin");
        Long startTime = System.currentTimeMillis();
        if (!CollectionUtils.isEmpty(navigationList)) {
            saveOrUpdateNav(false, navigationList);
        }
        LOGGER.info("the interval of sync navigation :" + (System.currentTimeMillis() - startTime));
        LOGGER.info("###########同步导航#########  end");
    }

    //6.同步面板与面板项的关系

    private void sysPanelItemMap() throws Exception {
        if (!CollectionUtils.isEmpty(panelItemMapList)) {
            LOGGER.info("##########同步面板与面板项的关系 #########  begin");
            panelPanelItemMapRepository.deleteByEpgRelationId(Constant.EPG_DISTRICT_CODE);
            LOGGER.info("########## 成功删除面板与面板项的关系 #########");
            for (PanelPanelItemMap pim : panelItemMapList) {
                convertPanelItemMap(pim);
            }
            panelPanelItemMapRepository.batchSaveEpgMap(panelItemMapList);
        }
        LOGGER.info("##########同步面板与面板项的关系 #########  end");
    }

    // 7.同步面板、导航、面板包三者的关系
    private void sysPanelPackageMap() throws Exception {
        if (!CollectionUtils.isEmpty(panelPackageMapList)) {
            LOGGER.info("##############同步面板、导航、面板包三者的关系######  begin");
            panelPackageMapRepository.deleteByEpgRelationId();
            LOGGER.info("##############成功删除面板、导航、面板包三者的关系######");
            for (PanelPackageMap ppm : panelPackageMapList) {
                convertPanelPackageMap(ppm);
            }
            panelPackageMapRepository.batchSaveEpgMap(panelPackageMapList);

            //get the customMap with epgPanel or epgNavId and update the relationship
            List<PanelPackageMap> mapList = panelPackageMapRepository.findCustomMapsWithEpgData();
            /*if (!CollectionUtils.isEmpty(mapList)) {
                for (PanelPackageMap map : mapList) {
                 *//*   if (map.getEpgPanelId() != null) {
                        Panel panel = panelRepository.getPanelByEpgPanelId(map.getEpgPanelId());
                        if (panel != null) {//if the panel is existed,update the onlineStatus of the panel
                            panel.setOnlineStatus(OnlineStatus.ONLINE.getValue());
                            panelRepository.updatePanel(panel);
                        }
                    }*//*
                    if (StringUtils.isNotBlank(map.getEpgNavId())) {
                        List<Long> navList = StringUtil.splitToLong(map.getEpgNavId());
                        List<Navigation> navigationList = findNavListByNavIds(navList);//find the navList by epg_nav_ids
                        if (!CollectionUtils.isEmpty(navigationList)) {//if the navigationList is not null,then update the onlineStatus of the nav
                            for (Navigation nav : navigationList) {
                                nav.setOnlineStatus(OnlineStatus.ONLINE.getValue());
                                navigationRepository.updateEpgNavDefine(nav);//将删除的导航状态改为上线
                            }
                        }
                    }
                }
            }*/
        }
        LOGGER.info("##############同步面板、导航、面板包三者的关系######  end");
    }


    // 8.同步预览模块表
    /*private void sysPreviewItem() throws Exception {
        String url = systemConfigService.getSystemConfigByConfigKey("sysPreviewItemUrl");
        String param = "";
        HttpResponseWrapper wrapper = HttpUtils.doPost(url, param, UTF8);
        int returnCode = wrapper.getHttpStatus();
        if (returnCode == 200) {
            String rsp = wrapper.getResponse();
            List<PreviewItem> list = gson.fromJson(rsp, new TypeToken<List<PreviewItem>>() {
            }.getType());
            LOGGER.info("#########  同步预览模块表 ###########  begin");

            previewItemRepository.deleteByEpgIoid();
            LOGGER.info("###### 成功删除预览模块表 #########");
            if (!CollectionUtils.isEmpty(list)) {
                for (PreviewItem previewItem : list) {
                    convertPreviewItem(previewItem);
                }
                previewItemRepository.batchSavePreviewItem(list);
            }
            LOGGER.info("#########  同步预览模块表 ###########  end");
        }
    }
*/
    // 9.同步预览模块表-实例表
    private void sysPreviewItemData() throws Exception {
//        private void sysPreviewItemData(Long oprUserId) throws Exception {
        if (!CollectionUtils.isEmpty(previewItemDataList)) {
            LOGGER.info("#########  同步预览模块表-实例表 ###########  begin");
            previewItemDataRepository.deleteByEpgID(Constant.EPG_DISTRICT_CODE);
            LOGGER.info("#########  成功删除预览模块表-实例表 ###########");
            for (PreviewItemData pi : previewItemDataList) {
                convertPreviewItemData(pi);
//                pi.setOprUserId(oprUserId);
                pi.setCreateTime(DateUtils.getCurrentDate());
                pi.setUpdateTime(DateUtils.getCurrentDate());
            }
            previewItemDataRepository.batchSaveEpgPreviewItemData(previewItemDataList);
        }
        LOGGER.info("#########  同步预览模块表-实例表 ###########  end");
    }

/*    private void setPanelItemContenType() {
        List<PanelItem> panelItemList = panelItemRepository.findAllPanelItemEpgList();
        List<PanelItem> typeList = new ArrayList<PanelItem>();
        for (PanelItem panelItem : panelItemList) {
            if (panelItem.getHasSubItem() == YesOrNo.YES.getValue()) {
                //get all the subItem of the parentItem
                List<PanelItem> subPanelItems = panelItemRepository.findSublItemListByPanelItemParentId(panelItem.getId());
                //if the contentType of parentItem is list
                String contentType = "";
                if (panelItem.getContentType().equals(Constant.PANEL_ITEM_CONTENT_TYPE)) {
                    if (panelItem.getEpgRefItemId() != null) {//if the refItem is not null
                        contentType = getPanelItemByEpgId(panelItem.getEpgRefItemId()).getContentType();//get the contentType of the refItem
//                        if (!CollectionUtils.isEmpty(subPanelItems)) {
                        subPanelItems.add(panelItem);
//                        }
                    }
                } else {
                    contentType = panelItem.getContentType();//get the contentType of the parentItem
                }
                if (StringUtils.isNotBlank(contentType)) {
                    for (PanelItem item : subPanelItems) {
                        item.setContentType(contentType);
                    }
                    typeList.addAll(subPanelItems);
                }
            }
        }

        for (PanelItem panelItem : typeList) {
            panelItemRepository.updatePanelItem(panelItem);
        }
    }*/


    private void convertPreviewItemData(PreviewItemData pi) {
        pi.setEpgIoid(pi.getIoid());
        pi.setIoid(null);

        pi.setEpgContentId(pi.getContentId());
        pi.setContentId(null);

        pi.setEpgContentItemId(pi.getContentItemId());
        pi.setContentItemId(null);

        pi.setEpgTemplateId(pi.getTemplateId());
        pi.setTemplateId(null);

        if (pi.getItemLeft() == null) {
            pi.setLeft(0);
        } else {
            pi.setLeft(pi.getItemLeft() - 1);
        }
        pi.setItemLeft(null);

        if (pi.getItemTop() == null) {
            pi.setTop(0);
        } else {
            pi.setTop(pi.getItemTop() - 1);
        }
        pi.setItemTop(null);

        pi.setWidth(pi.getItemWidth());
        pi.setItemWidth(null);

        pi.setHeight(pi.getItemHeight());
        pi.setItemHeight(null);

        pi.setSort(pi.getSortNum());
        pi.setSortNum(null);

        pi.setDistrictCode(Constant.EPG_DISTRICT_CODE);

        //get panelId from table panel by epgContentId
        if (pi.getEpgContentId() != null) {
            if (panelMap.size() == 0) {
                getPanelByEpgPanelId();
            }
            Panel panel = panelMap.get(pi.getEpgContentId());
            if (panel != null) {
                pi.setContentId(panel.getId());
            }
        }
        //get panelItemId from tabel panel_item by epgContentItemId
        if (pi.getEpgContentItemId() != null) {
            PanelItem panelItem = getPanelItemByEpgId(pi.getEpgContentItemId());
            if (panelItem != null) {
                pi.setContentItemId(panelItem.getId());
            }
        }
    }

    private void convertCenterPreviewTemplate(PreviewTemplate pi) {
        pi.setId(null);
    }

    private void convertCenterPanelItemMap(PanelPanelItemMap pim) {
        pim.setId(null);

        pim.setPanelId(null);

        pim.setPanelItemId(null);

        //set panel_id from table panel by epg_panel_id
        if (pim.getEpgPanelId() != null) {
            if (panelMap.size() == 0) {
                getPanelByEpgPanelId();
            }
            Panel panel = panelMap.get(pim.getEpgPanelId());
            if (panel != null) {
                pim.setPanelId(panel.getId());
            }
        }

        //set panel_item_id from table panel_item by epg_panelItem_id
        if (pim.getEpgPanelitemId() != null) {
            PanelItem panelItem = getPanelItemByEpgId(pim.getEpgPanelitemId());
            if (panelItem != null) {
                pim.setPanelItemId(panelItem.getId());
            }
        }
    }

    private void convertCenterNavigation(Navigation nav) {
        nav.setId(null);

        if (nav.getCreateTime() == null) {
            nav.setCreateTime(new Date());
        }

        if (nav.getUpdateTime() == null) {
            nav.setUpdateTime(new Date());
        }

        nav.setOnlineStatus(OnlineStatus.ONLINE.getValue());
    }

    private void convertPreviewTemplate(PreviewTemplate pi) {
        pi.setEpgTemplateId(pi.getTemplateId());
        pi.setTemplateId(null);

        pi.setType(pi.getTemplateType());
        pi.setTemplateType(null);

        pi.setContainsPs(pi.getContainPs());
        pi.setContainPs(null);
    }

    private void convertPreviewItem(PreviewItem pi) {
        pi.setEpgIoid(pi.getIoid());
        pi.setIoid(null);

        pi.setEpgTemplateId(pi.getTemplateId());
        pi.setTemplateId(null);

        pi.setLeft(pi.getItemLeft());

        pi.setTop(pi.getItemTop());

        pi.setWidth(pi.getItemWidth());

        pi.setHeight(pi.getItemHeight());

        pi.setSort(pi.getSortNum());

        //get templateId from preview_template by epgTemplateId
       /* if (pi.getEpgTemplateId() != null) {
            if (previewTemplateMap.size() == 0) {
                getTemplateByEpgTempId();
            }
            PreviewTemplate template = previewTemplateMap.get(pi.getEpgTemplateId());
            if (template != null) {
                pi.setTemplateId(template.getId());
            }
        }*/
    }


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
                getPanelByEpgPanelId();
            }
            Panel panel = panelMap.get(pim.getEpgPanelId());
            if (panel != null) {
                pim.setPanelId(panel.getId());
            }
        }

        //set panel_item_id from table panel_item by epg_panelItem_id
        if (pim.getEpgPanelitemId() != null) {
            PanelItem panelItem = getPanelItemByEpgId(pim.getEpgPanelitemId());
            if (panelItem != null) {
                pim.setPanelItemId(panelItem.getId());
            }
        }

        if (pim.getCreateTime() == null) {
            pim.setCreateTime(new Date());
        }

        pim.setDistrictCode(Constant.EPG_DISTRICT_CODE);
    }


    private void convertPanelPackageMap(PanelPackageMap ppm) {
        ppm.setEpgRelId(ppm.getId());
        ppm.setId(null);

        ppm.setEpgPackageId(ppm.getPackageId());
        ppm.setPackageId(null);

        ppm.setEpgPanelId(ppm.getPanelId());
        ppm.setPanelId(null);

        ppm.setEpgNavId(ppm.getNavIds());
        ppm.setNavId(null);

        ppm.setDisplay("true");
        ppm.setIsLock("true");

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
    }


    private void convertNavigation(Navigation nav) {
        nav.setEpgNavId(nav.getNavId());
        nav.setNavId(null);

        if (nav.getCreateTime() == null) {
            nav.setCreateTime(new Date());
        }

        if (nav.getUpdateTime() == null) {
            nav.setUpdateTime(new Date());
        }
        nav.setNavName(nav.getEpgNavId() + nav.getTitle());
        nav.setOnlineStatus(OnlineStatus.ONLINE.getValue());
    }

    private void convertCenterPackage(PanelPackage pp) {
        pp.setId(null);

        if (pp.getCreateTime() == null) {
            pp.setCreateTime(new Date());
        }
        pp.setOnlineStatus(OnlineStatus.ONLINE.getValue());
    }


    private void convertPackage(PanelPackage pp) {
        pp.setEpgPackageId(pp.getId());
        pp.setId(null);

        pp.setPlatFormId(pp.getPlatformId());
        pp.setPlatformId(null);

        if (StringUtils.isNotBlank(pp.getName())) {
            pp.setPackageName(pp.getName());
            pp.setName(null);
        }

        pp.setOprUserID(pp.getOprUserId());
        pp.setOprUserId(null);

        if (pp.getCreateTime() == null) {
            pp.setCreateTime(new Date());
        }

        pp.setOnlineStatus(OnlineStatus.ONLINE.getValue());

        pp.setIsDefault(IsOrNotDefault.NO_DEFAULT.getValue());
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

        pi.setDistrictCode(Constant.EPG_DISTRICT_CODE);

        if (pi.getEpgContentId() != null) {
            if (pi.getType() != null && (Constant.PANEL_ITEM_FIRST_CLASS_PROGRAM_TYPE == pi.getType() || Constant.PANEL_ITEM_SECOND_CLASS_PROGRAM_TYPE == pi.getType())) {
                pi.setActionUrl(getValue("programActionUrl") + pi.getEpgContentId());
            } else if (pi.getType() != null && Constant.PANEL_ITEM_TVSET_TYPE == pi.getType()) {
                pi.setActionUrl(getValue("TVsetActionUrl") + pi.getEpgContentId());
            }
        }
    }

    private void convertUpdatedPanel(Panel panel) {
        if (panelMap.size() == 0) {
            getPanelByEpgPanelId();
        }
        Panel epgPanel = panelMap.get(panel.getEpgRefPanelId());
        if (epgPanel != null) {
            panel.setRefPanelId(epgPanel.getId());
        }
    }

    private void convertUpdatedPanelItem(PanelItem panelItem) {
        //set panelItemParentId
        if (panelItem.getEpgPanelitemParentid() != null) {
            PanelItem item = getPanelItemByEpgId(panelItem.getEpgPanelitemParentid());
            if (item != null) {
                panelItem.setPanelItemParentId(item.getId());
            }
        }
        //set refItemId
        if (panelItem.getEpgRefItemId() != null) {
            PanelItem item = getPanelItemByEpgId(panelItem.getEpgRefItemId());
            if (item != null) {
                panelItem.setRefItemId(item.getId());
            }
        }
    }

    private void convertCenterPanel(Panel pa) {
        pa.setId(null);

        pa.setTemplateId(null);

        if (pa.getCreateTime() == null) {
            pa.setCreateTime(new Date());
        }

        //find the template_id by epg_template_id from the previewTemplate table
       /* if (pa.getEpgTemplateId() != null) {
            if (previewTemplateMap.size() == 0) {
                getTemplateByEpgTempId();
            }
            PreviewTemplate template = previewTemplateMap.get(pa.getEpgTemplateId());
            if (template != null) {
                pa.setTemplateId(template.getId());
            }
        }*/

        pa.setOnlineStatus(OnlineStatus.ONLINE.getValue());
    }

    private void convertCenterPanelItem(PanelItem pi) {
        pi.setId(null);

        pi.setContentId(null);

        pi.setRefItemId(null);

        pi.setParentId(null);

        pi.setOnlineStatus(OnlineStatus.ONLINE.getValue());

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

        //find the template_id by epg_template_id from the previewTemplate table
       /* if (pa.getEpgTemplateId() != null) {
            if (previewTemplateMap.size() == 0) {
                getTemplateByEpgTempId();
            }
            PreviewTemplate template = previewTemplateMap.get(pa.getEpgTemplateId());
            if (template != null) {
                pa.setTemplateId(template.getId());
            }
        }*/
        pa.setDistrictCode(Constant.EPG_DISTRICT_CODE);
        pa.setOnlineStatus(OnlineStatus.ONLINE.getValue());
    }


    private void convertCenterPreviewItem(PreviewItem pi) {
        pi.setId(null);

        pi.setTemplateId(null);
        //get templateId from preview_template by epgTemplateId
      /*  if (pi.getEpgTemplateId() != null) {
            if (previewTemplateMap.size() == 0) {
                getTemplateByEpgTempId();
            }
            PreviewTemplate template = previewTemplateMap.get(pi.getEpgTemplateId());
            if (template != null) {
                pi.setTemplateId(template.getId());
            }
        }*/
    }


    private void convertCenterPreviewItemData(PreviewItemData pi) {
        pi.setId(null);

        pi.setContentId(null);

        pi.setContentItemId(null);

        pi.setTemplateId(null);

        //get templateId from table preview_template by epgTemplateId
      /*  if (pi.getEpgTemplateId() != null) {
            if (previewTemplateMap.size() == 0) {
                getTemplateByEpgTempId();
            }
            PreviewTemplate template = previewTemplateMap.get(pi.getEpgTemplateId());
            if (template != null) {
                pi.setTemplateId(template.getId());
            }
        }*/
        //get panelId from table panel by epgContentId
        if (pi.getEpgContentId() != null) {
            if (panelMap.size() == 0) {
                getPanelByEpgPanelId();
            }
            Panel panel = panelMap.get(pi.getEpgContentId());
            if (panel != null) {
                pi.setContentId(panel.getId());
            }
        }
        //get panelItemId from tabel panel_item by epgContentItemId
        if (pi.getEpgContentItemId() != null) {
            PanelItem panelItem = getPanelItemByEpgId(pi.getEpgContentItemId());
            if (panelItem != null) {
                pi.setContentItemId(panelItem.getId());
            }
        }
    }

    private void convertCenterPanelPackageMap(PanelPackageMap ppm) {
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
                getPanelByEpgPanelId();
            }
            Panel panel = panelMap.get(ppm.getEpgPanelId());
            if (panel != null) {
                ppm.setPanelId(panel.getId());
            }
        }
    }

 /*   private void convertMapPanelAndNav(PanelPackageMap ppm,List<Long> list) {
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
        if (StringUtils.isNotBlank(ppm.getEpgNavId())) {
            LOGGER.info("existed in package ==" + ppm.getEpgNavId());
            //get the navList by epgNavIds from the current db
            List<Long> epgNavIds = StringUtil.splitToLong(ppm.getEpgNavId());
            List<Navigation> navList = findNavListByNavIds(epgNavIds);
            Map<Long, Navigation> currMap = new HashMap<Long, Navigation>();
            if (!CollectionUtils.isEmpty(navList)) {
                list.addAll(epgNavIds);
                for (Navigation nav : navList) {
                    currMap.put(nav.getEpgNavId(), nav);
                }
                String navIds = ppm.getNavId();//get the navIds of epgNavs bound by custom package
                //replace the existed old navIds
                for (Long epgNavId : epgNavIds) {
                    String existedNavId = existedMap.get(epgNavId).getId().toString();
                    String curNavId = currMap.get(epgNavId).getId().toString();
                    navIds = navIds.replace(existedNavId, curNavId);
                }
                ppm.setNavId(navIds);
            }
        }
    }*/

    /*private Navigation getNavigationDataByIdFromNavDatas(String id, String navDatas, int navType) {
        Navigation nav = null;
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(navDatas);
            Element rootEle = doc.getRootElement();
            Iterator nodeIterator;
            if (1 == navType) {
                nodeIterator = rootEle.elementIterator(ELEMENT_HEAD_NAV);
            } else {
                nodeIterator = rootEle.elementIterator(ELEMENT_FOOT_NAV);
            }
            while (nodeIterator.hasNext()) {
                Element navElement = (Element) nodeIterator.next();
                if (id.equals(navElement.attributeValue(ELEMENT_ID))) {
                    nav = new Navigation();
                    nav.setImageUrl(navElement.elementTextTrim(ELEMENT_IMGURL));
                    nav.setActionType(navElement.elementTextTrim(ELEMENT_ACTION));
                    nav.setActionUrl(navElement.elementTextTrim(ELEMENT_ACTION_URL));
                    if (NavigationType.HEAD_NAV.getValue() == navType) {
                        nav.setTitle(navElement.elementTextTrim(ELEMENT_FOCUS_TEXT));
                    } else {
                        nav.setTitle(navElement.elementTextTrim(ELEMENT_TITLE));
                    }
                    nav.setNavType(navType);
                    LOGGER.info("title ==" + nav.getTitle());
                    LOGGER.info("imgurl ==" + nav.getImageUrl());
                    LOGGER.info("action ==" + nav.getActionType());
                    LOGGER.info("actionurl ==" + nav.getActionUrl());
                    break;
                }
            }
        } catch (DocumentException e) {
            nav = null;
            e.printStackTrace();
            LOGGER.error("Parse navDatas to doc error");
        }
        return nav;
    }
*/
   /* private boolean parsePanelPackStyleByStyle(String egpStyle, List<PanelPackageMap> panelPackageMaps, String navDatas) {
        Document doc;
        try {
            doc = DocumentHelper.parseText(egpStyle);
            Element rootElt = doc.getRootElement(); // get the rootNode
            LOGGER.info("rootNode:" + rootElt.getName());
            Iterator iterPanel = rootElt.elementIterator(ELEMENT_PANEL); // get the subNodes of the rootNode
            int panelNum = 0;
            //get the each panel node
            LOGGER.info("############## traverse the panel ######");
            while (iterPanel.hasNext()) {
                Element panelElement = (Element) iterPanel.next();
                if (panelPackageMaps != null && panelPackageMaps.size() >= panelNum + 1) {
                    PanelPackageMap map = panelPackageMaps.get(panelNum);
                    Long panelId = map.getPanelId();
                    String title = panelElement.attributeValue(ELEMENT_TITLE);
                    LOGGER.info("title of panel =" + title);
                    LOGGER.info("panelId ==" + panelId);
                    //get the navNodes of the panel
                    LOGGER.info("##############get the navIds by traversing the navNodes ######");
                    Iterator iteratorNav = panelElement.elementIterator(ELEMENT_HEAD_NAV);
                    StringBuilder sb;
                    sb = new StringBuilder("");
                    //get the navIds by traversing the iteratorNav
                    int navNum = 0;
                    while (iteratorNav.hasNext()) {
                        LOGGER.info("########## nav  begin #######");
                        Element navElement = (Element) iteratorNav.next();
                        String id = navElement.attributeValue(ELEMENT_ID);
                        LOGGER.info("id of nav =" + id);
                        LOGGER.info("navDatas ==" + navDatas);
                        Navigation nav = getNavigationDataByIdFromNavDatas(id, navDatas, NavigationType.HEAD_NAV.getValue());
                        navNum++;
                        nav.setSortNum(navNum);
                        if (nav == null) {
                            return false;
                        }
                        if (!navigationRepository.insert(nav)) {
                            return false;
                        }
                        sb.append(nav.getId()).append(",");
                        LOGGER.info("########## nav  end #######");
                    }

                    //get panelItemId by panelId
                    LOGGER.info("##############set the previewItemData by traversing the boxNodes ######");
                    Iterator iteratorBox = panelElement.elementIterator(ELEMENT_BOX);
                    List<PanelPanelItemMap> mapList = panelPanelItemMapRepository.findMapListByPanelId(panelId);
                    int sortBox = 0;
                    while (iteratorBox.hasNext()) {
                        Element boxElement = (Element) iteratorBox.next();
                        String boxId = boxElement.attributeValue(ELEMENT_ID);
                        LOGGER.info("####### box begin #####" + boxId);

                        if (mapList != null && mapList.size() >= sortBox + 1) {
                            PreviewItemData data = new PreviewItemData();
                            data.setContentId(panelId);
                            data.setContentItemId(mapList.get(sortBox).getPanelItemId());
                            data.setHeight(Integer.parseInt(boxElement.attributeValue("height")));
                            data.setLeft(Integer.parseInt(boxElement.attributeValue("left")));
                            data.setTop(Integer.parseInt(boxElement.attributeValue("top")));
                            data.setWidth(Integer.parseInt(boxElement.attributeValue("width")));
                            sortBox++;
                            data.setSort(sortBox);
                            previewItemDataRepository.insert(data);
                            LOGGER.info("####### box end #####");
                        } else {
                            LOGGER.info("Panel Item data is missing");
                            return false;
                        }
                    }

                    //get the bottomNav nodes
                    Iterator iterBottomnav = panelElement.elementIterator(ELEMENT_FOOT_NAV);
                    while (iterBottomnav.hasNext()) {
                        Element bottomNav = (Element) iterBottomnav.next();
                        String id = bottomNav.attributeValue(ELEMENT_ID);
                        Navigation nav = getNavigationDataByIdFromNavDatas(id, navDatas, NavigationType.ROOT_NAV.getValue());
                        if (nav == null) {
                            return false;
                        }
                        if (!navigationRepository.insert(nav)) {
                            return false;
                        }
                        sb.append(nav.getId()).append(",");
                        LOGGER.info("id of bottomNav is " + id);
                    }
                    if (StringUtils.isBlank(sb.toString())) {

                    } else {
                        map.setNavId(sb.substring(0, sb.length() - 1).toString());
                    }
                    LOGGER.info("update the PanelPackageMap begin");
                    panelPackageMapRepository.updateMap(map);
                    LOGGER.info("update the PanelPackageMap end");
                    panelNum++;
                } else {
                    LOGGER.info("The panel data is missing");
                    return false;
                }
            }
        } catch (DocumentException e) {
            LOGGER.error("parse text to xml error" + e);
        }
        return true;
    }*/

  /*  private String createDocToXML(String doc) {
        StringBuilder sb = new StringBuilder("");
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        sb.append("\n");
        sb.append("<navs>\n");
        sb.append(doc);
        sb.append("</navs>\n");
        return sb.toString();
    }
*/
    //parse the epgStyle2

    /*public boolean parsePanelPackStyle() {
        List<PanelPackage> panelPackageList = panelPackageRepository.getAllTargetList();
        for (PanelPackage panelPackage : panelPackageList) {
            //for the version 2.0 just the epgStyle2 has value
            if (StringUtils.isNotBlank(panelPackage.getEpgStyle2())) {
                *//*Long packageId = panelPackage.getId();
                List<PanelPackageMap> panelPackageMaps = panelPackageMapService.getMapByPackageId(packageId);
                String navDatas = createDocToXML(panelPackage.getNavsData());
                if (StringUtils.isNotBlank(panelPackage.getEpgStyle1())) {
                    parsePanelPackStyleByStyle(panelPackage.getEpgStyle1(), panelPackageMaps, navDatas);
                }
                *//*
                if (StringUtils.isNotBlank(panelPackage.getEpgStyle2())) {
                    Long packageId = panelPackage.getId();
                    List<PanelPackageMap> panelPackageMaps = panelPackageMapRepository.getMapByPackageId(packageId);
                    String navDatas = createDocToXML(panelPackage.getNavsData());
                    parsePanelPackStyleByStyle(panelPackage.getEpgStyle2(), panelPackageMaps, navDatas);
                }
            }
        }
        return true;
    }*/

    private void getPanelByEpgPanelId() {
        List<Panel> panelList = panelRepository.findIdAndEpgIdList(Constant.EPG_DISTRICT_CODE);
        for (Panel panel : panelList) {
            panelMap.put(panel.getEpgPanelId(), panel);
        }
    }

/*    private void getTemplateByEpgTempId() {
        List<PreviewTemplate> templateList = previewTemplateRepository.findIdAndEpgIdList();
        if (!CollectionUtils.isEmpty(templateList)) {
            for (PreviewTemplate template : templateList) {
                previewTemplateMap.put(template.getEpgTemplateId(), template);
            }
        }
    }*/

    private void getPanelItemByEpgItemId() {
        List<PanelItem> panelItemList = panelItemRepository.findIdAndEpgIdList(Constant.EPG_DISTRICT_CODE);
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

    private PanelItem getPanelItemByEpgId(Long epgRefId) {
        if (panelItemMap.size() == 0) {
            getPanelItemByEpgItemId();
        }
        PanelItem item = panelItemMap.get(epgRefId);
        return item;
    }

    private String getValue(String key) {
        return systemConfigRepository.getSystemConfigByConfigKey(key).getConfigValue();
    }
}
