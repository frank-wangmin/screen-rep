package com.ysten.local.bss.panel.service.impl;

import com.ysten.boss.systemconfig.repository.ISystemConfigRepository;
import com.ysten.local.bss.panel.domain.*;
import com.ysten.local.bss.panel.enums.NavigationType;
import com.ysten.local.bss.panel.enums.OnlineStatus;
import com.ysten.local.bss.panel.enums.PanelItemContentType;
import com.ysten.local.bss.panel.enums.YesOrNo;
import com.ysten.local.bss.panel.repository.*;
import com.ysten.local.bss.panel.service.IPanelService;
import com.ysten.local.bss.panel.vo.PanelQueryCriteria;
import com.ysten.local.bss.util.DateUtils;
import com.ysten.local.bss.util.HttpUtils;
import com.ysten.local.bss.util.JsonUtil;
import com.ysten.local.bss.util.NewCollectionsUtils;
import com.ysten.local.bss.util.bean.Constant;
import com.ysten.utils.page.Pageable;
import com.ysten.utils.string.StringUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by frank on 14-5-19.
 */
@Service
public class PanelServiceImpl implements IPanelService {

    @Autowired
    private IPanelRepository panelRepository;

    @Autowired
    private IPanelPackageRepository panelPackageRepository;

    @Autowired
    private IPanelPackageMapRepository panelPackageMapRepository;

    @Autowired
    private IPreviewItemRepository previewItemRepository;

    @Autowired
    private IPreviewItemDataRepository previewItemDataRepository;

    @Autowired
    private IPanelPanelItemMapRepository panelPanelItemMapRepository;

    @Autowired
    private IPanelItemRepository panelItemRepository;

    @Autowired
    private INavigationRepository navigationRepository;

    @Autowired
    private IPreviewTemplateRepository previewTemplateRepository;

    @Autowired
    private ISystemConfigRepository systemConfigRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(PanelServiceImpl.class);
    private static final String QUESTION_MARK = "?";
    private static final String EQUAL_SIGN = "=";

    @Override
    public Pageable<Panel> getPanelList(PanelQueryCriteria panelQueryCriteria) {
        return panelRepository.findPanelList(panelQueryCriteria);
    }

    @Override
    public Panel getPanelById(Long panelId) {
        return panelRepository.getPanelById(panelId);
    }

    @Override
    public List<PreviewItemData> getPreviewItemDataListByPanelId(Long panelId) {

        //处理Panel为引用类型
        Panel panel = panelRepository.getPanelById(panelId);
        if (panel != null && panel.getRefPanelId() != null) {
            Panel refPanel = panelRepository.getPanelById(panel.getRefPanelId());
            if (refPanel == null) return null;
            panelId = refPanel.getId();
        }

        //use panelId to get preview item data list
        List<PreviewItemData> previewItemDataList = previewItemDataRepository.getItemDataByPanelId(panelId);
//        if (panel != null && panel.getTemplateId() != null) {
//            previewItemDataList = previewItemDataRepository.getItemDataByPanelIdAndTemplateId(panel.getTemplateId(), panelId);
//        }
        if (!CollectionUtils.isEmpty(previewItemDataList)) {
            for (PreviewItemData previewItemData : previewItemDataList) {
                if (previewItemData.getContentItemId() != null) {
                    PanelItem panelItem = panelItemRepository.getPanelItemById(previewItemData.getContentItemId());

                    //处理播控数据删除或不存在
                    if (panelItem == null) {
                        continue;
                    }
                    if (panelItem.getHasSubItem() != null && panelItem.getHasSubItem().equals(YesOrNo.YES.getValue())) {
                        List<PanelItem> childrenList = panelItemRepository.findSublItemListByPanelItemParentId(panelItem.getId());
                        if (!CollectionUtils.isEmpty(childrenList)) {
                            panelItem.setChildrenList(childrenList);
                        }
                    }
                    previewItemData.setPanelItem(panelItem);
                }
            }
            return previewItemDataList;
        }
        return null;
    }

    @Override
    public void insert(Panel panel, Long oprUserId) {
        String districtCode = getCurrentDistrictCode();
        panel.setDistrictCode(districtCode);
        panel.setOprUserid(oprUserId);
        panel.setCreateTime(DateUtils.getCurrentDate());
        panel.setUpdateTime(DateUtils.getCurrentDate());
        panel.setVersion(new Date().getTime()); //版本--时间戳
        panelRepository.savePanel(panel);
        //插入预览模块实例表
        if (panel.getTemplateId() != null) {
            List<PreviewItem> previewItemList = previewItemRepository.getPreviewItemListByTemplateId(panel.getTemplateId());
            for (PreviewItem previewItem : previewItemList) {
                PreviewItemData previewItemData = new PreviewItemData();
                previewItemData.setDistrictCode(districtCode);
                previewItemData.setContentId(panel.getId());
                previewItemData.setLeft(previewItem.getLeft());
                previewItemData.setTop(previewItem.getTop());
                previewItemData.setWidth(previewItem.getWidth());
                previewItemData.setHeight(previewItem.getHeight());
                previewItemData.setType(previewItem.getType());
                previewItemData.setSort(previewItem.getSort());
                previewItemData.setTemplateId(previewItem.getTemplateId());
                previewItemData.setCreateTime(DateUtils.getCurrentDate());
                previewItemData.setUpdateTime(DateUtils.getCurrentDate());
                previewItemData.setOprUserId(oprUserId);
                previewItemDataRepository.savePreviewItemData(previewItemData);
            }
        }
    }

    @Override
    public void updatePanel(Panel panel, Long oprUserId) {
        Panel oldPanel = panelRepository.getPanelById(panel.getId());
        if (oldPanel == null || panel == null) return;

        //edit epg data
        if (oldPanel.getEpgPanelId() != null) {
            oldPanel.setIsCustom(panel.getIsCustom());
//            oldPanel.setDisplay(panel.getDisplay());
//            oldPanel.setIsLock(panel.getIsLock());
            oldPanel.setBigimg(panel.getBigimg());
            oldPanel.setSmallimg(panel.getSmallimg());
            oldPanel.setOprUserid(oprUserId);
            oldPanel.setUpdateTime(DateUtils.getCurrentDate());
            oldPanel.setVersion(new Date().getTime()); //版本--时间戳
            panelRepository.updatePanel(oldPanel);
            return;
        }
        //update preview item data
        if (!panel.getTemplateId().equals(oldPanel.getTemplateId())) {

            //delete old preview item data
            List<PreviewItemData> previewItemDataList = previewItemDataRepository.getItemDataByPanelId(oldPanel.getId());
            if (!CollectionUtils.isEmpty(previewItemDataList)) {
                for (PreviewItemData previewItemData : previewItemDataList) {
                    previewItemDataRepository.deletePreviewItemData(previewItemData);
                }
            }

            //delete panel vs panelItem relationship
            panelPanelItemMapRepository.deleteByPanelId(oldPanel.getId());

            //insert new preview item data
            List<PreviewItem> previewItemList = previewItemRepository.getPreviewItemListByTemplateId(panel.getTemplateId());
            for (PreviewItem previewItem : previewItemList) {
                PreviewItemData previewItemData = new PreviewItemData();
                previewItemData.setContentId(panel.getId());
                previewItemData.setLeft(previewItem.getLeft());
                previewItemData.setTop(previewItem.getTop());
                previewItemData.setWidth(previewItem.getWidth());
                previewItemData.setHeight(previewItem.getHeight());
                previewItemData.setType(previewItem.getType());
                previewItemData.setSort(previewItem.getSort());
                previewItemData.setTemplateId(previewItem.getTemplateId());
                previewItemData.setCreateTime(DateUtils.getCurrentDate());
                previewItemData.setUpdateTime(DateUtils.getCurrentDate());
                previewItemData.setOprUserId(oprUserId);
                previewItemDataRepository.savePreviewItemData(previewItemData);
            }
        }

        panel.setOprUserid(oprUserId);
        panel.setUpdateTime(DateUtils.getCurrentDate());
        panel.setVersion(new Date().getTime()); //版本--时间戳
        panelRepository.updatePanel(panel);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) return;
        //删除panel_package_map
        //删除panel_item_map
        //删除预览模块实例表
        for (Long panelId : ids) {
            panelPackageMapRepository.deletePanelPackageMapByPanelId(panelId);
            panelPanelItemMapRepository.deleteByPanelId(panelId);
            List<PreviewItemData> previewItemDataList = previewItemDataRepository.getItemDataByPanelId(panelId);
            if (!CollectionUtils.isEmpty(previewItemDataList)) {
                for (PreviewItemData previewItemData : previewItemDataList) {
                    previewItemDataRepository.deletePreviewItemData(previewItemData);
                }
            }
            Panel panel = panelRepository.getPanelById(panelId);
            if (panel != null) {
                panelRepository.deletePanel(panel);
            }
        }
    }

    @Override
    public String onlinePanel(List<Long> ids, Long oprUserId) {
        String result = Constant.SUCCESS;
        for (Long panelId : ids) {
            Panel panel = panelRepository.getPanelById(panelId);
            if (panel != null) {
                List<PreviewItemData> panelList = previewItemDataRepository.getItemDataByPanelId(panelId);
                if (!CollectionUtils.isEmpty(panelList)) {
                    for (PreviewItemData previewItemData : panelList) {
                        //如果任何面板项为空，不允许上线
                        if (previewItemData.getContentItemId() == null) {
                            return Constant.FAILED;
                        }
                    }
                    panel.setOprUserid(oprUserId);
                    panel.setCreateTime(DateUtils.getCurrentDate());
                    panel.setUpdateTime(DateUtils.getCurrentDate());
                    panel.setOnlineStatus(OnlineStatus.ONLINE.getValue());
                    panelRepository.updatePanel(panel);

                } else {
                    result = Constant.FAILED;
                }
            }
        }
        return result;
    }

    @Override
    public void downLinePanel(List<Long> ids, Long oprUserId) {
        if (CollectionUtils.isEmpty(ids)) return;
        for (Long panelId : ids) {
            Panel panel = panelRepository.getPanelById(panelId);
            if (panel != null) {
                panel.setOprUserid(oprUserId);
                panel.setCreateTime(DateUtils.getCurrentDate());
                panel.setUpdateTime(DateUtils.getCurrentDate());
                panel.setOnlineStatus(OnlineStatus.DOWNLINE.getValue());
                panelRepository.updatePanel(panel);
            }
        }
    }

    /*public Pageable<Panel> getPanelListByPackageId(Long packageId, Integer pageNo, Integer pageSize) {
        List<PanelPackageMap> panelPackageMapList = panelPackageMapRepository.findPaginationMapByPackageId(packageId, pageNo, pageSize);
        Integer total = panelRepository.getPanelCountByPackageId(packageId);
        List<Panel> panelList = NewCollectionsUtils.list();
        if (panelPackageMapList != null && panelPackageMapList.size() > 0) {
            for (PanelPackageMap panelPackageMap : panelPackageMapList) {
                Panel panel = panelRepository.getPanelById(panelPackageMap.getPanelId());
                if (panel == null || (panel.getOnlineStatus() != null && OnlineStatus.ONLINE.getValue() != panel.getOnlineStatus())) {
                    continue;
                }
                panel.setSort(panelPackageMap.getSortNum());
                panel.setIsLock(panelPackageMap.getIsLock());
                panel.setDisplay(panelPackageMap.getDisplay());
                panel.setPanelLogo(panelPackageMap.getPanelLogo());
                if (StringUtils.isNotBlank(panelPackageMap.getNavId())) {
                    List<Long> navIds = StringUtil.splitToLong(panelPackageMap.getNavId());
                    for (Long navId : navIds) {
                        Navigation navigation = navigationRepository.getNavigationById(navId);
                        if (navigation == null || navigation.getNavType() == null) continue;
                        if (navigation.getNavType().equals(NavigationType.ROOT_NAV.getValue())) {
                            panel.setRootNavTitle(navigation.getTitle());
                            panel.setRootNavId(navigation.getId());
                            panel.setRootNavName(navigation.getNavName());
                            break;
                        }
                    }
                    navIds.remove(panel.getRootNavId());
                    panel.setHeadNavIds(StringUtils.join(navIds, ","));
                }
                panelList.add(panel);
            }
        }
        return new Pageable<Panel>().instanceByPageNo(panelList, total, pageNo, pageSize);
    }*/

    @Override
    public Pageable<PanelPackageNavigation> getPanelListByPackageId(Long packageId,String dpi,Integer pageNo, Integer pageSize) {
        List<PanelPackageMap> panelPackageMapList = panelPackageMapRepository.findPaginationMapByPackageId(packageId, pageNo, pageSize);
        Integer total = panelRepository.getPanelCountByPackageId(packageId);
        List<PanelPackageNavigation> panelList = NewCollectionsUtils.list();
        if (panelPackageMapList != null && panelPackageMapList.size() > 0) {
            for (PanelPackageMap panelPackageMap : panelPackageMapList) {
                PanelPackageNavigation map = new PanelPackageNavigation();
                Panel panel = panelRepository.getPanelById(panelPackageMap.getPanelId());
                if (panel == null || (panel.getOnlineStatus() != null && OnlineStatus.ONLINE.getValue() != panel.getOnlineStatus()) || !StringUtils.equals(panel.getResolution(),dpi)) {
                    continue;
                }
                map.setId(panel.getId());
                map.setPanelTitle(panel.getPanelTitle());
                map.setSort(panelPackageMap.getSortNum());
                map.setIsLock(panelPackageMap.getIsLock());
                map.setDisplay(panelPackageMap.getDisplay());
                map.setPanelLogo(panelPackageMap.getPanelLogo());
                if (StringUtils.isNotBlank(panelPackageMap.getNavId())) {
                    List<Long> navIds = StringUtil.splitToLong(panelPackageMap.getNavId());
                    for (Long navId : navIds) {
                        Navigation navigation = navigationRepository.getNavigationById(navId);
                        if (navigation == null || navigation.getNavType() == null) continue;
                        if (navigation.getNavType().equals(NavigationType.ROOT_NAV.getValue())) {
                            map.setRootNavTitle(navigation.getTitle());
                            map.setRootNavId(navigation.getId());
//                            map.setRootNavName(navigation.getNavName());
                            break;
                        }
                    }

                    navIds.remove(map.getRootNavId());
                    map.setHeadNavIds(StringUtils.join(navIds, ","));
                }
                panelList.add(map);
            }
        }
        return new Pageable<PanelPackageNavigation>().instanceByPageNo(panelList, total, pageNo, pageSize);
    }

    @Override
    public void insertPanelPackageMap(PanelPackageMap panelPackageMap) {
        panelRepository.savePanelPackageMap(panelPackageMap);
    }

    @Override
    public List<PanelPackageMap> verifyIfExistBinded(Long panelId, Long packageId) {
        return panelRepository.verifyIfExistBinded(panelId, packageId);
    }

    @Override
    public List<Panel> getPanelListByPackageId(Map<String, Object> map) {
        return panelRepository.findPanelListByPackageId(map);
    }

    @Override
    public List<Panel> getAllOnlinePanels(int onlineStatus,String dpi) {
        return panelRepository.getAllOnlinePanels(onlineStatus,dpi);
    }


    @Override
    public List<Long> getNavIds(Long panelId, Long packageId) {
        String ids = panelPackageMapRepository.getNavIds(panelId, packageId);
        return StringUtil.splitToLong(ids, ",");

    }

    @Override
    public void unBindPanelPackage(Long packageId, List<Long> panelIds) {
//        for (Long panelId : panelIds) {
        panelPackageMapRepository.deleteByPackagelIdAndPanelId(packageId, panelIds);
//        }
    }

    @Override
    public void submitPreviewItemDatas(PreviewItemData[] previewItemDatas, Long oprUserId) {
        long panelId = 0;
        if (previewItemDatas != null && previewItemDatas.length > 0) {
            panelPanelItemMapRepository.deleteByPanelId(previewItemDatas[0].getContentId());
            for (PreviewItemData previewItemData : previewItemDatas) {
                if (previewItemData.getContentId() != null && previewItemData.getContentItemId() != null) {
                    PanelPanelItemMap panelPanelItemMap = panelPanelItemMapRepository.selectByPanelIdAndPanelItemId(previewItemData.getContentId(), previewItemData.getContentItemId());
                    if (panelPanelItemMap == null) {
                        PanelPanelItemMap panelPanelItemMapObj = new PanelPanelItemMap();
                        panelPanelItemMapObj.setDistrictCode(getCurrentDistrictCode());
                        panelPanelItemMapObj.setPanelId(previewItemData.getContentId());
                        if (StringUtils.isNotBlank(panelId + "")) {
                            panelId = previewItemData.getContentId();
                        }
                        panelPanelItemMapObj.setPanelItemId(previewItemData.getContentItemId());
                        panelPanelItemMapObj.setCreateTime(DateUtils.getCurrentDate());
                        panelPanelItemMapObj.setUpdateTime(DateUtils.getCurrentDate());
                        panelPanelItemMapObj.setOprUserId(oprUserId);
                        panelPanelItemMapRepository.savePanelPanelItemMap(panelPanelItemMapObj);//done
                    }
                }
                previewItemData.setOprUserId(oprUserId);
                previewItemData.setUpdateTime(DateUtils.getCurrentDate());
                previewItemDataRepository.updatePreviewItemData(previewItemData);
            }
        }

        //面板和面板项关系变更时，更新面板的version版本号
        if (panelId != 0) {
            Panel panel = this.panelRepository.getPanelById(panelId);
            panel.setVersion(new Date().getTime());
            this.panelRepository.updatePanel(panel);
        }
    }

    private String getCurrentDistrictCode() {
        return systemConfigRepository.getSystemConfigByConfigKey("deployDistrictCode").getConfigValue();
    }

    @Override
    public boolean pushPanelData() {
        String url = systemConfigRepository.getSystemConfigByConfigKey("syncProvincePanelData").getConfigValue();
        LOGGER.info("url is :" + url);
        String districtCode = getCurrentDistrictCode();
        LOGGER.info("");
        //distribute panelPackage
       /* List<PanelPackage> panelPackageList = panelPackageRepository.findAllEpgList();
        if (!this.distributePanelDataToProvince(panelPackageList, url + QUESTION_MARK + Constant.DISTRIBUTE_TYPE + EQUAL_SIGN + Constant.DISTRIBUTE_TYPE_PANEL_PACKAGE)) {
            return false;
        }*/
        //distribute PreviewTemplate
      /*  List<PreviewTemplate> previewTemplateList = previewTemplateRepository.findAllEpgList();
        if (!this.distributePanelDataToProvince(previewTemplateList, url + QUESTION_MARK + Constant.DISTRIBUTE_TYPE + EQUAL_SIGN + Constant.DISTRIBUTE_TYPE_PREVIEW_TEMPLATE)) {
            return false;
        }*/
        //distribute Panel
        List<Panel> panelList = panelRepository.findAllList();
        for (Panel panel : panelList) {
            panel.setPanelName(districtCode + panel.getPanelName());
           /* String panelId = String.valueOf(panel.getAreaId())+String.valueOf(panel.getId());
            panel.setId(Long.parseLong(panelId));*/
        }
        if (!this.distributePanelDataToProvince(panelList, url + QUESTION_MARK + Constant.DISTRIBUTE_TYPE + EQUAL_SIGN + Constant.DISTRIBUTE_TYPE_PANEL)) {
            return false;
        }
        //distribute PanelItem
        List<PanelItem> panelItemList = panelItemRepository.findAllProvinceList();
        if (!this.distributePanelDataToProvince(panelItemList, url + QUESTION_MARK + Constant.DISTRIBUTE_TYPE + EQUAL_SIGN + Constant.DISTRIBUTE_TYPE_PANEL_ITEM)) {
            return false;
        }
        //distribute Navigation
     /*   List<Navigation> navigationList = navigationRepository.findEpgNavList();
        if (!this.distributePanelDataToProvince(navigationList, url + QUESTION_MARK + Constant.DISTRIBUTE_TYPE + EQUAL_SIGN + Constant.DISTRIBUTE_TYPE_NAVIGATION)) {
            return false;
        }*/
        //distribute PanelItemMap
        List<PanelPanelItemMap> panelItemMaps = panelPanelItemMapRepository.findProvinceMapList();
        if (!this.distributePanelDataToProvince(panelItemMaps, url + QUESTION_MARK + Constant.DISTRIBUTE_TYPE + EQUAL_SIGN + Constant.DISTRIBUTE_TYPE_PANEL_ITEM_MAP)) {
            return false;
        }
        //distribute PanelPackageMap
      /*  List<PanelPackageMap> panelPackageMaps = panelPackageMapRepository.findMapList();
        if (!this.distributePanelDataToProvince(panelPackageMaps, url + QUESTION_MARK + Constant.DISTRIBUTE_TYPE + EQUAL_SIGN + Constant.DISTRIBUTE_TYPE_PANEL_PACKAGE_MAP)) {
            return false;
        }*/
        //distribute PreviewItem
      /*  List<PreviewItem> previewItems = previewItemRepository.findAllItems();
        if (!this.distributePanelDataToProvince(previewItems, url + QUESTION_MARK + Constant.DISTRIBUTE_TYPE + EQUAL_SIGN + Constant.DISTRIBUTE_TYPE_PREVIEW_ITEM)) {
            return false;
        }*/
        //distribute PreviewItemData
        List<PreviewItemData> previewItemDataList = previewItemDataRepository.findAllProvinceDatas();
        if (!this.distributePanelDataToProvince(previewItemDataList, url + QUESTION_MARK + Constant.DISTRIBUTE_TYPE + EQUAL_SIGN + Constant.DISTRIBUTE_TYPE_PREVIEW_ITEM_DATA)) {
            return false;
        }
        return true;
    }

    @Override
    public List<Panel> getPanelByName(String name) {
        return panelRepository.getPanelByName(name);
    }


    private boolean distributePanelDataToProvince(List<?> panelList, String url) {

        String param = JsonUtil.getJsonString4List(panelList);
        LOGGER.info("Distribute panel data Url: " + url);
        LOGGER.info("Distribute panel data param: " + param);
        HttpUtils.HttpResponseWrapper wrapper = HttpUtils.doPost(url, param, Constant.UTF_ENCODE);
        int returnCode = wrapper.getHttpStatus();
        if (returnCode == 200) {
            String rsp = wrapper.getResponse();
            JSONObject object = JSONObject.fromObject(rsp);
            String result = object.getString("result");
            if (StringUtils.equalsIgnoreCase(result, Constant.TRUE)) {
                return true;
            }
            return false;
        }
        return false;
    }

}
