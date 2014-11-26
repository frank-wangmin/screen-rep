package com.ysten.local.bss.panel.service.impl;

import com.ysten.boss.systemconfig.service.SystemConfigService;
import com.ysten.local.bss.panel.domain.*;
import com.ysten.local.bss.panel.enums.OnlineStatus;
import com.ysten.local.bss.panel.enums.PanelItemContentType;
import com.ysten.local.bss.panel.enums.PanelResolution;
import com.ysten.local.bss.panel.service.*;
import com.ysten.local.bss.panel.vo.PanelInfo;
import com.ysten.local.bss.panel.vo.PanelResponseForJsMobile;
import com.ysten.local.bss.util.FtpUtils;
import com.ysten.local.bss.util.NewCollectionsUtils;
import com.ysten.local.bss.util.ZipCompress;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.Serializable;
import java.util.*;

/**
 * Created by frank on 14-11-3.
 */
@Service
public class PanelDataStyleForJsServiceImpl implements IPanelDataStyleForJsService {

    public static final String LOCAL_ZIP_PATH = "localZipPath";
    @Autowired
    private IPanelPackageService panelPackageService;
    @Autowired
    private IPanelService panelService;
    @Autowired
    private IPanelItemService panelItemService;
    @Autowired
    private INavigationService navigationService;
    @Autowired
    private IPanelPackageMapService panelPackageMapService;
    @Autowired
    private RedisTemplate<Serializable, Serializable> redisTemplate;
    @Autowired
    private SystemConfigService systemConfigService;

    static String NO_NAVIGATION = "No navigation found.";
    static String NO_TOP_NAVIGATION = "No top navigation found.";
    static String NO_BOTTOM_NAVIGATION = "No bottom navigation found.";
    static String NO_TEMPLATE = "No template found.";
    static String NO_PANEL = "No panel found.";
    static String NO_PANEL_MAP = "No panel map found.";
    static String NO_PREVIEW_ITEM_DATA = "No Preview Item Data found.";
    static String NO_PANEL_ITEM = "No panel item found.";

    @Override
    public String getCustomerPanel(String templateId, String dpi) throws Exception {

        // 1.获取面板包
        PanelPackage pp = panelPackageService.getPanelPackageById(Long.parseLong(templateId));
        if (pp == null) {
            return NO_TEMPLATE;
        }

        // 2.面板包下的面板
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("packageId", pp.getId());
        map.put("onlineStatus", OnlineStatus.ONLINE.getValue());
        map.put("dpi", dpi);
        List<Panel> panelList = panelService.getPanelListByPackageId(map);
        if (CollectionUtils.isEmpty(panelList)) {
            return NO_PANEL;
        }

        Long version = getMaxPanelVersion(panelList);
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        sb.append("<Pages version=\"" + String.valueOf(version) + "\">\n");
        for (int i = 0; i < panelList.size(); i++) {
            Panel panel = panelList.get(i);
            sb.append("<Page id=\"" + getPanelIdAttr(panel) + "\" width=\"5\" height=\"3\" title=\"" + panel.getPanelTitle() + "\"" +
                    " pagename=\"" + panel.getPanelName() + "\"");
            PanelPackageMap panelPackageMap = panelPackageMapService.getMapByPackageIdAndPanelId(Long.parseLong(templateId), panel.getId());
            sb.append(" defaultpanel=\"" + panelPackageMap.getDisplay() + "\"");
            sb.append(" lock=\"" + panelPackageMap.getIsLock() + "\"");
            sb.append(">\n");
            sb.append("<bigimg><![CDATA[" + panel.getBigimg() + "]]></bigimg>\n");
            sb.append("<smallimg><![CDATA[" + panel.getSmallimg() + "]]></smallimg>\n");
            sb.append("</Page>\n");
        }
        sb.append("</Pages>");
        return sb.toString();
    }


    /**
     * 获得面板最大的版本号
     *
     * @param panelList
     * @return
     */
    private Long getMaxPanelVersion(List<Panel> panelList) {
        Long maxVersion = 0L;
        for (Panel panel : panelList) {
            if (panel.getVersion() > maxVersion) {
                maxVersion = panel.getVersion();
            }
        }
        return maxVersion;
    }

    /**
     * 获取面板信息
     *
     * @param panelPackageId
     * @param dpi
     * @param launcherVersion
     * @return
     */
    @Override
    public PanelResponseForJsMobile getPanelInfo(Long panelPackageId, String dpi, Long launcherVersion) {
        PanelResponseForJsMobile panelResponseForJsMobile = new PanelResponseForJsMobile();
        PanelPackage panelPackage = panelPackageService.getPanelPackageById(panelPackageId);
        if (panelPackage == null) {
            panelResponseForJsMobile.setResultCode("112002");
            panelResponseForJsMobile.setResultDesc("未找到指定的面板包");
            return panelResponseForJsMobile;
        }
        String filePath = systemConfigService.getSystemConfigByConfigKey(LOCAL_ZIP_PATH);
        String fileName = panelPackageId + "_" + dpi + "_" + panelPackage.getVersion() + ".zip";
        String dirs = filePath + fileName;
        File path = new File(dirs);

        //如果版本号为空，取最新的版本
        if (launcherVersion == null) {
            if (!path.exists()) {
                panelResponseForJsMobile.setResultCode("112001");
                panelResponseForJsMobile.setResultDesc("面板包ZIP包路径获取失败");
                return panelResponseForJsMobile;
            }
        } else {
            if (panelPackage.getVersion() != null && panelPackage.getVersion().equals(launcherVersion)) {
                panelResponseForJsMobile.setResultCode("100000");
                panelResponseForJsMobile.setResultDesc("当前已经是最新版本，不需要更新");
                return panelResponseForJsMobile;
            }
            if (!path.exists()) {
                panelResponseForJsMobile.setResultCode("112001");
                panelResponseForJsMobile.setResultDesc("面板包ZIP包路径获取失败,请先创建zip包");
                return panelResponseForJsMobile;
            }
        }
        panelResponseForJsMobile.setResultCode("000000");
        panelResponseForJsMobile.setResultDesc("成功");
        if (StringUtils.equals(dpi, PanelResolution.RESOLUTION_720p.getValue())) {
            panelResponseForJsMobile.setZipURL(panelPackage.getZipUrl());
        } else {
            panelResponseForJsMobile.setZipURL(panelPackage.getZipUrl1080p());
        }
        return panelResponseForJsMobile;
    }

    /**
     * 创建所有分辨率的Zip文件
     *
     * @param panelPackageIdList
     * @throws Exception
     */
    public void createAllZips(List<Long> panelPackageIdList) throws Exception {
        PanelPackage panelPackage = null;
        for (Long aLong : panelPackageIdList) {
            panelPackage = panelPackageService.getPanelPackageById(aLong);
            if (panelPackage == null) continue;
//            createZip(aLong,"720p");
            for (PanelResolution panelResolution : PanelResolution.values()) {
                createZip(panelPackage, panelResolution.getValue());
            }
            panelPackageService.update(panelPackage);
        }
    }

    /**
     * 处理面板xml
     *
     * @param panelPackage
     * @param dpi
     * @return
     */
    @Override
    public void createZip(PanelPackage panelPackage, String dpi) throws Exception {

        //面板包的版本号
        Long maxPackageVersion = 0L;
        PanelInfo panelInfo = new PanelInfo();
        if (panelPackage == null) return;

        //面板包下的面板
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("packageId", panelPackage.getId());
        map.put("onlineStatus", OnlineStatus.ONLINE.getValue());
        map.put("display", "true");
        map.put("dpi", dpi);
        List<Panel> panelList = panelService.getPanelListByPackageId(map);
        if (CollectionUtils.isEmpty(panelList)) {
            return;
        }

        panelInfo.setPanelPackage(panelPackage);
        LinkedHashMap<Panel, List<PreviewItemData>> panelListMap = NewCollectionsUtils.linkedMap();
        Map<Long, List<Navigation>> navigationMap = NewCollectionsUtils.map();
        Map<Long, Long> panelVersionMap = NewCollectionsUtils.map();
        panelInfo.setPanelListMap(panelListMap);
        panelInfo.setNavigationMap(navigationMap);
        panelInfo.setCommonNavigationList(new ArrayList<Navigation>());
        panelInfo.setPanelVersionMap(panelVersionMap);

        //记录通用的上部导航
        String commonNavigation = panelPackage.getCommonTopNav();
        if (!StringUtils.isEmpty(commonNavigation)) {
            String[] navigationIds = StringUtils.split(commonNavigation, ",");
            for (String navigationId : navigationIds) {
                if (StringUtils.isBlank(navigationId)) continue;
                Navigation navigation = navigationService.getNavigationById(Long.valueOf(navigationId));

                //只有符合对应分辨率和上部导航
                if (navigation != null && StringUtils.equals(navigation.getResolution(), dpi) && navigation.getNavType() == 1) {
                    panelInfo.getCommonNavigationList().add(navigation);
                }
            }
        }

        for (Panel panel : panelList) {
            List<PreviewItemData> previewItemDataList = panelService.getPreviewItemDataListByPanelId(panel.getId());
            PanelPackageMap panelPackageMap = panelPackageMapService.getMapByPackageIdAndPanelId(panelPackage.getId(), panel.getId());
            if (panelPackageMap != null) {
                panel.setPanelPackageMap(panelPackageMap);
            }
            panelInfo.getPanelListMap().put(panel, previewItemDataList);
            List<Long> navIds = panelService.getNavIds(panel.getId(), panelPackage.getId());
            if (!CollectionUtils.isEmpty(navIds)) {

                //面板和面板包的关系导航（上部+下部）
                List<Navigation> navigationList = NewCollectionsUtils.list();
                for (Long navId : navIds) {
                    Navigation nav = navigationService.getNavigationById(navId);

                    //符合对应分辨率
                    if (nav != null && StringUtils.equals(nav.getResolution(), dpi)) {
                        navigationList.add(nav);
                    }
                }
                panelInfo.getNavigationMap().put(panel.getId(), navigationList);
            }
        }
        maxPackageVersion = getMaxPackageVersion(panelInfo);

        String zipName = getFileDirsZip(panelInfo.getPanelPackage(), dpi, maxPackageVersion) + ".zip";
        File zipFile = new File(zipName);

        //如果zip文件存在，说明已是最新的
        if (zipFile.exists()) return;

        writeLauncherXml(maxPackageVersion, dpi, panelInfo);
        writeCommonDataXml(maxPackageVersion, dpi, panelInfo);
        writePanelInfo(maxPackageVersion, dpi, panelInfo);
        writeLayoutXml(maxPackageVersion, dpi, panelInfo);
        ZipCompress zipCompress = new ZipCompress(zipName);
        zipCompress.createZipOut();
        zipCompress.packToolFiles(getFileDirsZip(panelInfo.getPanelPackage(), dpi, maxPackageVersion), "");
        zipCompress.closeZipOut();

        //给面板包赋值最大的版本号
        panelPackage.setVersion(maxPackageVersion);
        if (StringUtils.equals(PanelResolution.RESOLUTION_720p.getValue(), dpi)) {
            panelPackage.setZipUrl(getZipUrl(panelPackage.getId(), dpi, maxPackageVersion));
        }
        if (StringUtils.equals(PanelResolution.RESOLUTION_1080p.getValue(), dpi)) {
            panelPackage.setZipUrl1080p(getZipUrl(panelPackage.getId(), dpi, maxPackageVersion));
        }
//        panelPackageService.update(panelPackage);
    }

    /**
     * 获得zipURL
     *
     * @param panelPackageId
     * @param dpi
     * @param maxPackageVersion
     * @return
     */
    private String getZipUrl(Long panelPackageId, String dpi, Long maxPackageVersion) {
//        String filePath = systemConfigService.getSystemConfigByConfigKey(LOCAL_ZIP_PATH);
        String fileName = panelPackageId + "_" + dpi + "_" + maxPackageVersion + ".zip";
//        String dirs = filePath + fileName;
//        File path = new File(dirs);
        String zipPrexUrl = systemConfigService.getSystemConfigByConfigKey("zipPrexUrl");
        return zipPrexUrl + fileName;
    }

    /**
     * 写入launcherXml
     *
     * @param maxPackageVersion
     * @param dpi
     * @param panelInfo
     */
    private void writeLauncherXml(Long maxPackageVersion, String dpi, PanelInfo panelInfo) {

        StringBuilder launcherXml = new StringBuilder();
        launcherXml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        launcherXml.append("<Launcher version=\"" + maxPackageVersion + "\">\n");
        launcherXml.append("<Layout version=\"" + maxPackageVersion + "\" file=\"" + dpi + "/launcher_layout.xml\"/>\n");
        launcherXml.append("<CommonData version=\"" + maxPackageVersion + "\" file=\"" + dpi + "/launcher_data_common.xml\"/>\n");
        launcherXml.append("<Pages>\n");
        for (Panel panel : panelInfo.getPanelListMap().keySet()) {
            Long panelVersion = getMaxPanelVersion(panelInfo, panel);
            panelInfo.getPanelVersionMap().put(panel.getId(), panelVersion);
            launcherXml.append("<Page id=\"" + getPanelIdAttr(panel) + "\" version=\"" + panelVersion + "\" file=\"" + dpi + "/launcher_data_page_" + getPanelIdAttr(panel) + ".xml\"/>\n");
        }
        launcherXml.append("</Pages>\n");
        launcherXml.append("</Launcher>");
        String xml = launcherXml.toString();

        String fileName = "launcher_" + dpi + ".xml";
        FtpUtils.writeXmlToFile(xml, getFileDirs(panelInfo.getPanelPackage(), dpi, maxPackageVersion) + fileName);
    }

    /**
     * 获取或创建文件目录
     *
     * @param panelPackage
     * @param dpi
     * @param version
     * @return
     */
    private String getFileDirs(PanelPackage panelPackage, String dpi, Long version) {

        String filePath = systemConfigService.getSystemConfigByConfigKey(LOCAL_ZIP_PATH);
        String dirs = filePath + panelPackage.getId() + "_" + dpi + "_" + version + File.separator;
        File path = new File(dirs);
        if (!path.exists()) {
            path.mkdirs();
        }
        return dirs;
    }

    /**
     * 获取zip文件目录
     *
     * @param panelPackage
     * @param dpi
     * @param version
     * @return
     */
    private String getFileDirsZip(PanelPackage panelPackage, String dpi, Long version) {

        String filePath = systemConfigService.getSystemConfigByConfigKey(LOCAL_ZIP_PATH);
        String dirs = filePath + panelPackage.getId() + "_" + dpi + "_" + version;
        File path = new File(dirs);
        if (!path.exists()) {
            path.mkdirs();
        }
        return dirs;
    }

    /**
     * 获取或创建dpi目录
     *
     * @param path
     * @param dpi
     * @return
     */
    private String getDpiDirs(String path, String dpi) {
        String dirs = path + dpi + File.separator;
        File dpiPath = new File(dirs);
        if (!dpiPath.exists()) {
            dpiPath.mkdirs();
        }
        return dirs;
    }

    private void writeCommonDataXml(Long maxPackageVersion, String dpi, PanelInfo panelInfo) {

        StringBuilder commonDataXml = new StringBuilder();
        commonDataXml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        commonDataXml.append("<LauncherData version=\"" + maxPackageVersion + "\">\n");

        //底部导航
        commonDataXml.append("<Navs>\n");
        Set<Panel> keyPanelSet = panelInfo.getPanelListMap().keySet();
        for (Panel panel : keyPanelSet) {
            commonDataXml.append("<Nav id=\"" + getPanelIdAttr(panel) + "\">\n");
            List<Navigation> navigationList = panelInfo.getNavigationMap().get(panel.getId());
            if (CollectionUtils.isEmpty(navigationList)) continue;
            Navigation bottom = new Navigation();
            for (Navigation navigation : navigationList) {

                //过滤底部导航
                if (navigation.getNavType() == 2) {
                    bottom = navigation;
                    break;
                }
            }
            if (!StringUtils.isEmpty(bottom.getImageUrl())) {
                commonDataXml.append("<Img><![CDATA[" + bottom.getImageUrl() + "]]></Img>\n");
            }
            if (!StringUtils.isEmpty(bottom.getFocusImg())) {
                commonDataXml.append("<FocusImg><![CDATA[" + bottom.getFocusImg() + "]]></FocusImg>\n");
            }
            if (!StringUtils.isEmpty(bottom.getCurrentPageImg())) {
                commonDataXml.append("<CurrentPageImg><![CDATA[" + bottom.getCurrentPageImg() + "]]></CurrentPageImg>\n");
            }
            commonDataXml.append("</Nav>\n");
        }
        commonDataXml.append("</Navs>\n");
        commonDataXml.append("<Shortcuts>\n");
        appendShortcuts(commonDataXml, panelInfo.getCommonNavigationList());
        commonDataXml.append("</Shortcuts>\n");
        commonDataXml.append("</LauncherData>");

        String fileName = "launcher_data_common.xml";
        FtpUtils.writeXmlToFile(commonDataXml.toString(), getDpiDirs(getFileDirs(panelInfo.getPanelPackage(), dpi, maxPackageVersion), dpi) + fileName);

    }

    /**
     * 写入面板信息
     *
     * @param dpi
     * @param panelInfo
     */
    private void writePanelInfo(Long maxPackageVersion, String dpi, PanelInfo panelInfo) {
        StringBuilder panelInfoXml = new StringBuilder();
        Set<Panel> keyPanelSet = panelInfo.getPanelListMap().keySet();
        Integer panelMaxLength = Math.min(keyPanelSet.size(), panelInfo.getPanelPackage().getMaxPageNumber());
        Integer index = 0;
        for (Panel panel : keyPanelSet) {
            panelInfoXml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
            index++;
            if (index > panelMaxLength) break;
            panelInfoXml.append("<LauncherData id=\"" + getPanelIdAttr(panel) + "\" version=\"" + panelInfo.getPanelVersionMap().get(panel.getId()) + "\">\n");
            panelInfoXml.append("<Shortcuts>\n");
            List<Navigation> specialTopNavList = panelInfo.getNavigationMap().get(panel.getId());

            //只有上部导航
            if (!CollectionUtils.isEmpty(specialTopNavList)) {
                appendShortcuts(panelInfoXml, specialTopNavList);
            }
            panelInfoXml.append("</Shortcuts>\n");
            panelInfoXml.append("<Elements>\n");
            List<PreviewItemData> previewItemDataList = panelInfo.getPanelListMap().get(panel);
            if (CollectionUtils.isEmpty(previewItemDataList)) {
                panelInfoXml.append("</Elements>\n");
                panelInfoXml.append("</LauncherData>");
                String fileName = "launcher_data_page_" + getPanelIdAttr(panel) + ".xml";
                FtpUtils.writeXmlToFile(panelInfoXml.toString(), getDpiDirs(getFileDirs(panelInfo.getPanelPackage(), dpi, maxPackageVersion), dpi) + fileName);
                panelInfoXml = new StringBuilder();
                continue;
            }
            createElement(panelInfoXml, index, previewItemDataList);
            panelInfoXml.append("</Elements>\n");
            panelInfoXml.append("</LauncherData>");
            String fileName = "launcher_data_page_" + getPanelIdAttr(panel) + ".xml";
            FtpUtils.writeXmlToFile(panelInfoXml.toString(), getDpiDirs(getFileDirs(panelInfo.getPanelPackage(), dpi, maxPackageVersion), dpi) + fileName);
            panelInfoXml = new StringBuilder();
        }
    }

    /**
     * 创建面板项节点
     *
     * @param panelInfoXml
     * @param index
     * @param previewItemDataList
     */
    private void createElement(StringBuilder panelInfoXml, Integer index, List<PreviewItemData> previewItemDataList) {
        int boxIndex = 0;
        String canfocus, autoRun;
        Map<String, String> listMap = NewCollectionsUtils.map();
        Map<String, String> typeMap = NewCollectionsUtils.map();
        String idIndex;
        StringBuilder elementXml = new StringBuilder();
        String elementType;
        for (PreviewItemData previewItemData : previewItemDataList) {
            boxIndex++;
            if (previewItemData.getPanelItem() == null) continue;
            PanelItem panelItem = previewItemData.getPanelItem();
            idIndex = index + "_" + boxIndex;
            elementType = panelItem.getHasSubItem() == 1 ? "list" : panelItem.getContentType();
            elementXml.append("<Element id=\"" + idIndex + "\" type=\"" + elementType + "\" ");
            if (panelItem.getFocusRun() != null) {
                canfocus = panelItem.getFocusRun() == 1 ? ("canfocus=\"true\" ") : ("canfocus=\"false\" ");
                elementXml.append(canfocus);
            }
            if (!StringUtils.isEmpty(panelItem.getDefaultfocus())) {
                elementXml.append("defaultfocus=\"" + panelItem.getDefaultfocus() + "\" ");
            }
            if (StringUtils.equals(panelItem.getContentType(), PanelItemContentType.VIDEO.getValue()) && panelItem.getAutoRun() != null) {
                autoRun = panelItem.getAutoRun() == 1 ? ("auto=\"true\" ") : ("auto=\"false\" ");
                elementXml.append(autoRun);
            }

            if (panelItem.getHasSubItem() != null && panelItem.getHasSubItem() == 1 && panelItem.getRefItemId() != null) {
                elementXml.append(" link=listLink" + panelItem.getId() + "");
                typeMap.put("typeLink" + panelItem.getId(), idIndex);
            } else if (panelItem.getHasSubItem() != null && panelItem.getHasSubItem() == 0 && panelItem.getRefItemId() != null
                    && panelItem.getContentType().equals(PanelItemContentType.IMAGE.getValue())) {
                elementXml.append(" link=typeLink" + panelItem.getRefItemId() + "");
                listMap.put("listLink" + panelItem.getRefItemId(), idIndex);
            } else if (panelItem.getHasSubItem() != null && panelItem.getHasSubItem() == 0 && panelItem.getRefItemId() != null
                    && panelItem.getContentType().equals(PanelItemContentType.VIDEO.getValue())) {
                elementXml.append(" link=typeLink" + panelItem.getRefItemId() + "");
                listMap.put("listLink" + panelItem.getRefItemId(), idIndex);
            }
            elementXml.append(">\n");
            elementXml.append("<ElementDatas>\n");

            //TODO ?order
            elementXml.append("<ElementData order=\"0\">\n");
            if (!StringUtils.isEmpty(panelItem.getName())) {
                elementXml.append("<ElementName><![CDATA[" + panelItem.getName() + "]]></ElementName>\n");
            }
            if (!StringUtils.isEmpty(panelItem.getTitleComment())) {
                elementXml.append("<ElementDesc><![CDATA[" + panelItem.getTitleComment() + "]]></ElementDesc>\n");
            }
            if (StringUtils.equals(panelItem.getContentType(), PanelItemContentType.IMAGE.getValue()) && !StringUtils.isEmpty(panelItem.getImageUrl())) {
                elementXml.append("<ContentURL><![CDATA[" + panelItem.getImageUrl() + "]]></ContentURL>\n");
            }

            //TODO ?当类型为custom时作为图片自定义单元格小元素网页加载地址；
            if (StringUtils.equals(panelItem.getContentType(), PanelItemContentType.CUSTOM.getValue()) && !StringUtils.isEmpty(panelItem.getContent())) {
                elementXml.append("<ContentURL><![CDATA[" + panelItem.getContent() + "]]></ContentURL>\n");
            }
            if (StringUtils.equals(panelItem.getContentType(), PanelItemContentType.VIDEO.getValue()) && !StringUtils.isEmpty(panelItem.getVideoUrl())) {
                elementXml.append("<ContentURL><![CDATA[" + panelItem.getVideoUrl() + "]]></ContentURL>");
            }
            if (panelItem.getActionType() != null) {
                elementXml.append("<ElementAction>\n");
                elementXml.append("<ActionType>" + panelItem.getActionType() + "</ActionType>\n");
                if (!StringUtils.isEmpty(panelItem.getActionUrl())) {
                    elementXml.append("<ActionURL><![CDATA[" + panelItem.getActionUrl() + "]]></ActionURL>\n");
                }
                if (!StringUtils.isEmpty(panelItem.getParams())) {
                    elementXml.append("<Params>\n");
                    String[] params = StringUtils.split(panelItem.getParams(), ";");
                    for (String param : params) {
                        if (!StringUtils.isEmpty(param)) {
                            String[] valuesArr = StringUtils.split(param, ":");
                            if (valuesArr.length == 2) {
                                elementXml.append("<Param name=\"" + valuesArr[0] + "\">" + valuesArr[1] + "</Param>\n");
                            }
                        }
                    }
                    elementXml.append("</Params>\n");
                }
                elementXml.append("</ElementAction>\n");
            }

            elementXml.append("</ElementData>\n");

            elementXml.append("</ElementDatas>\n");

            elementXml.append("</Element>\n");
        }
        panelInfoXml.append(transformBoxLink(listMap, typeMap, elementXml.toString()));
        /*String xml = transformBoxLink(listMap,typeMap,elementXml.toString());
        panelInfoXml.append(xml);*/
    }


    /**
     * 处理image和video的link
     *
     * @param listMap
     * @param typeMap
     * @param elementXml
     * @return
     */
    private String transformBoxLink(Map<String, String> listMap, Map<String, String> typeMap, String elementXml) {
        if (listMap.size() != 0 && typeMap.size() != 0) {
            Set<String> listKey = listMap.keySet();
            for (String s : listKey) {
                String sa = "link=" + s;
                elementXml = elementXml.replace(sa, "link=" + "\"" + listMap.get(s) + "\"");
            }
            Set<String> typeKey = typeMap.keySet();
            for (String s : typeKey) {
                String sa = "link=" + s;
                elementXml = elementXml.replace(sa, "link=" + "\"" + typeMap.get(s) + "\"");
            }
        }
        return elementXml;
    }

    /**
     * 拼接快捷入口
     *
     * @param xml
     * @param navigationList
     */
    private void appendShortcuts(StringBuilder xml, List<Navigation> navigationList) {
        String align;
        String canfocus;
        for (Navigation navigation : navigationList) {

            //过滤掉下部导航
            if (navigation.getNavType() == 2) continue;
            align = StringUtils.isEmpty(navigation.getAlign()) ? "" : ("align=\"" + navigation.getAlign() + "\"");
            canfocus = StringUtils.isEmpty(navigation.getCanfocus()) ? "" : ("canfocus=\"" + navigation.getCanfocus() + "\"");
            xml.append("<Shortcut id=\"" + getNavIdAttrubute(navigation) + "\" type=\"" +
                    navigation.getTopNavType() + "\" " + align + " " + canfocus + ">\n");
            if (!StringUtils.isEmpty(navigation.getNavName())) {
                xml.append("<Name><![CDATA[" + navigation.getTitle() + "]]></Name>\n");
            }
            if (!StringUtils.isEmpty(navigation.getImageUrl())) {
                xml.append("<Img><![CDATA[" + navigation.getImageUrl() + "]]></Img>\n");
            }
            if (!StringUtils.isEmpty(navigation.getFocusImg())) {
                xml.append("<FocusImg><![CDATA[" + navigation.getFocusImg() + "]]></FocusImg>\n");
            }
            if (!StringUtils.isEmpty(navigation.getActionType())) {
                xml.append("<ElementAction>\n");
                xml.append("<ActionType>" + navigation.getActionType() + "</ActionType>\n");
                if (!StringUtils.isEmpty(navigation.getActionUrl())) {
                    xml.append("<ActionURL><![CDATA[" + navigation.getActionUrl() + "]]></ActionURL>\n");
                }
                if (!StringUtils.isEmpty(navigation.getParams())) {
                    xml.append("<Params>\n");
                    String[] params = StringUtils.split(navigation.getParams(), ";");
                    for (String param : params) {
                        if (!StringUtils.isEmpty(param)) {
                            String[] valuesArr = StringUtils.split(param, ":");
                            if (valuesArr.length == 2) {
                                xml.append("<Param name=\"" + valuesArr[0] + "\">" + valuesArr[1] + "</Param>\n");
                            }
                        }
                    }
                    xml.append("</Params>\n");
                }
                xml.append("</ElementAction>\n");
            }
            xml.append("</Shortcut>\n");
        }
    }

    /**
     * 获得panel id属性
     *
     * @param panel
     * @return
     */
    private String getPanelIdAttr(Panel panel) {
        if (panel != null) {
//            return panel.getPanelName() + "_" + panel.getId();
            return panel.getId() + "";
        }
        return "";
    }

    /**
     * 写入layoutXml
     *
     * @param maxPackageVersion
     * @param dpi
     * @param panelInfo
     */
    private void writeLayoutXml(Long maxPackageVersion, String dpi, PanelInfo panelInfo) {
        StringBuilder layoutXml = new StringBuilder();
        layoutXml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        layoutXml.append("<LauncherLayout  version=\"" + maxPackageVersion + "\">\n");
        layoutXml.append("<PageCommon>\n");
        if (StringUtils.equals(dpi, PanelResolution.RESOLUTION_720p.getValue()) && !StringUtils.isEmpty(panelInfo.getPanelPackage().getDefaultBackground720p())) {
            layoutXml.append("<DefaultBackground><![CDATA[" + panelInfo.getPanelPackage().getDefaultBackground720p() + "]]></DefaultBackground>\n");
        }
        if (StringUtils.equals(dpi, PanelResolution.RESOLUTION_1080p.getValue()) && !StringUtils.isEmpty(panelInfo.getPanelPackage().getDefaultBackground1080p())) {
            layoutXml.append("<DefaultBackground><![CDATA[" + panelInfo.getPanelPackage().getDefaultBackground1080p() + "]]></DefaultBackground>\n");
        }
        layoutXml.append("</PageCommon>\n");
        layoutXml.append("<PageList MaxPageNumber=\"" + panelInfo.getPanelPackage().getMaxPageNumber() + "\">\n");
        Set<Panel> keyPanelSet = panelInfo.getPanelListMap().keySet();
        Integer panelMaxLength = Math.min(keyPanelSet.size(), panelInfo.getPanelPackage().getMaxPageNumber());
        Integer index = 0;
        for (Panel panel : keyPanelSet) {
            index++;
            if (index > panelMaxLength) break;
            layoutXml.append("<Page id=\"" + getPanelIdAttr(panel) + "\" order=\"" + (index - 1) + "\" width=\"5\" height=\"3\">\n");
            if (!StringUtils.isEmpty(panel.getPanelName())) {
                layoutXml.append("<PageName><![CDATA[" + panel.getPanelName() + "]]></PageName>\n");
            }
            if (!StringUtils.isEmpty(panel.getImgUrl())) {
                layoutXml.append("<PageBackground><![CDATA[" + panel.getImgUrl() + "]]></PageBackground>\n");
            }
            if (!StringUtils.isEmpty(panel.getPanelPackageMap().getPanelLogo())) {
                layoutXml.append("<Logo><![CDATA[" + panel.getPanelPackageMap().getPanelLogo() + "]]></Logo>\n");
            }
            layoutXml.append("<Shortcuts>\n");
            if (!CollectionUtils.isEmpty(panelInfo.getCommonNavigationList())) {
                for (Navigation topNav : panelInfo.getCommonNavigationList()) {
                    layoutXml.append("<Shortcut id=\"" + getNavIdAttrubute(topNav) + "\" type=\"" + topNav.getTopNavType() + "\"/>\n");
                }
            }
            List<Navigation> specialTopNavList = panelInfo.getNavigationMap().get(panel.getId());
            if (!CollectionUtils.isEmpty(specialTopNavList)) {
                removeDuplicateNavigation(panelInfo.getCommonNavigationList(), specialTopNavList);
                for (Navigation navigation : specialTopNavList) {
                    layoutXml.append("<Shortcut id=\"" + getNavIdAttrubute(navigation) + "\" type=\"" + navigation.getTopNavType() + "\"/>\n");
                }
            }
            layoutXml.append("</Shortcuts>\n");
            layoutXml.append("<Elements>\n");
            List<PreviewItemData> previewItemDataList = panelInfo.getPanelListMap().get(panel);
            if (!CollectionUtils.isEmpty(previewItemDataList)) {
                int boxIndex = 0;
                for (PreviewItemData previewItemData : previewItemDataList) {
                    boxIndex++;
                    if (previewItemData.getPanelItem() != null) {
                        layoutXml.append("<Element id=\"" + index + "_" + boxIndex + "\" type=\"" + previewItemData.getPanelItem().getContentType() + "\" left=\"" +
                                (previewItemData.getLeft() + 1) + "\" top=\"" + (previewItemData.getTop() + 1) + "\" width=\"" + previewItemData.getWidth() + "\" height=\"" +
                                previewItemData.getHeight() + "\" />\n");
                    } else {
                        layoutXml.append("<Element id=\"" + index + "_" + boxIndex + "\" left=\"" +
                                (previewItemData.getLeft() + 1) + "\" top=\"" + (previewItemData.getTop() + 1) + "\" width=\"" + previewItemData.getWidth() + "\" height=\"" +
                                previewItemData.getHeight() + "\" />\n");
                    }
                }
            }
            layoutXml.append("</Elements>\n");
            layoutXml.append("</Page>\n");
        }
        layoutXml.append("</PageList>\n");
        layoutXml.append("</LauncherLayout>");

        String fileName = "launcher_layout.xml";
        FtpUtils.writeXmlToFile(layoutXml.toString(), getDpiDirs(getFileDirs(panelInfo.getPanelPackage(), dpi, maxPackageVersion), dpi) + fileName);
    }

    private String getNavIdAttrubute(Navigation navigation) {
        if (navigation != null) {
//            return navigation.getNavName() + "_" + navigation.getId();
            return navigation.getId() + "";
        }
        return "";
    }

    /**
     * 去掉重复的上部导航
     *
     * @param commonList
     * @param specialList
     */
    private void removeDuplicateNavigation(List<Navigation> commonList, List<Navigation> specialList) {
        List<Navigation> existTopNavOrBottomNavList = NewCollectionsUtils.list();
        for (Navigation navigation : specialList) {

            //过滤下部导航
            if (navigation.getNavType() == 2) {
                existTopNavOrBottomNavList.add(navigation);
                continue;
            }
            for (Navigation navigation1 : commonList) {
                if (navigation1.getId().equals(navigation.getId())) {
                    existTopNavOrBottomNavList.add(navigation);
                }
            }
        }
        if (!CollectionUtils.isEmpty(existTopNavOrBottomNavList)) {
            specialList.removeAll(existTopNavOrBottomNavList);
        }
    }

    /**
     * 获得总的版本号
     *
     * @param panelInfo
     * @return
     */
    private Long getMaxPackageVersion(PanelInfo panelInfo) {
        Long result = panelInfo.getPanelPackage().getVersion();
        if (!CollectionUtils.isEmpty(panelInfo.getCommonNavigationList())) {
            for (Navigation nav : panelInfo.getCommonNavigationList()) {
                if (nav.getVersion() != null && result < nav.getVersion()) {
                    result = nav.getVersion();
                }
            }
        }
        Set<Panel> panelSet = panelInfo.getPanelListMap().keySet();
        for (Panel panel : panelSet) {
            if (result < panel.getVersion()) {
                result = panel.getVersion();
            }
            result = getPanelVersion(panelInfo, result, panel);
        }
        return result;
    }

    private Long getPanelVersion(PanelInfo panelInfo, Long result, Panel panel) {
        List<PreviewItemData> previewItemDataList = panelInfo.getPanelListMap().get(panel);
        for (PreviewItemData previewItemData : previewItemDataList) {
            if (previewItemData.getPanelItem() != null && previewItemData.getPanelItem().getVersion() != null && result < previewItemData.getPanelItem().getVersion()) {
                result = previewItemData.getPanelItem().getVersion();
            }
        }
        List<Navigation> navigationList = panelInfo.getNavigationMap().get(panel.getId());
        if (!CollectionUtils.isEmpty(navigationList)) {
            for (Navigation navigation : navigationList) {
                if (navigation.getVersion() != null && result < navigation.getVersion()) {
                    result = navigation.getVersion();
                }
            }
        }
        return result;
    }

    /**
     * 获得某一个panel的最大版本号
     *
     * @param panelInfo
     * @param panel
     * @return
     */
    private Long getMaxPanelVersion(PanelInfo panelInfo, Panel panel) {
        Long result = panel.getVersion();
        return getPanelVersion(panelInfo, result, panel);
    }

}
