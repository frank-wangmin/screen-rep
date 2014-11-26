package com.ysten.local.bss.panel.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.ysten.cache.annotation.KeyParam;
import com.ysten.cache.annotation.MethodCache;
import com.ysten.local.bss.panel.domain.Navigation;
import com.ysten.local.bss.panel.domain.Panel;
import com.ysten.local.bss.panel.domain.PanelItem;
import com.ysten.local.bss.panel.domain.PanelItem.ActionType;
import com.ysten.local.bss.panel.domain.PanelPackage;
import com.ysten.local.bss.panel.domain.PanelPackageMap;
import com.ysten.local.bss.panel.domain.PreviewItemData;
import com.ysten.local.bss.panel.enums.OnlineStatus;
import com.ysten.local.bss.panel.enums.PanelItemContentType;
import com.ysten.local.bss.panel.repository.ILvomsDistrictCodeUrlRepository;
import com.ysten.local.bss.panel.service.INavigationService;
import com.ysten.local.bss.panel.service.IPanelDataStyleService;
import com.ysten.local.bss.panel.service.IPanelItemService;
import com.ysten.local.bss.panel.service.IPanelPackageMapService;
import com.ysten.local.bss.panel.service.IPanelPackageService;
import com.ysten.local.bss.panel.service.IPanelService;
import com.ysten.local.bss.util.bean.RedisUtils;
import com.ysten.local.bss.util.bean.RedisUtils.DeviceInterfaceType;

/**
 * @author cwang
 * @version 2014-7-10 下午1:03:40
 * 
 */
@Service
public class PanelDataStyleServiceImpl implements IPanelDataStyleService {
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
	private ILvomsDistrictCodeUrlRepository lvomsDistrictCodeUrlRepository;
	@Autowired
	private RedisTemplate<Serializable, Serializable> redisTemplate;

	static String NO_NAVIGATION = "No navigation found.";
	static String NO_TOP_NAVIGATION = "No top navigation found.";
	static String NO_BOTTOM_NAVIGATION = "No bottom navigation found.";
	static String NO_TEMPLATE = "No template found.";
	static String NO_PANEL = "No panel found.";
	static String NO_PANEL_MAP = "No panel map found.";
	static String NO_PREVIEW_ITEM_DATA = "No Preview Item Data found.";
	static String NO_PANEL_ITEM = "No panel item found.";

	private static final String PACKAGE_PANEL_STYLE_INTERFACE = "panel:interface:style:templateId:";

	private static final String PACKAGE_PANEL_DATA_INTERFACE = "panel:interface:data:templateId:";

	private static final String PACKAGE_PANEL_CUSTOMER_INTERFACE = "panel:interface:customer:templateId:";

	@MethodCache(key = PACKAGE_PANEL_CUSTOMER_INTERFACE + "#{templateId}")
	public String getCustomerPanel(@KeyParam("templateId") String templateId) throws Exception {
		// 1.获取面板包
		PanelPackage pp = panelPackageService.getPanelPackageById(Long.parseLong(templateId));
		if (pp == null) {
			return NO_TEMPLATE;
		}
		StringBuilder panelIds = new StringBuilder();
		StringBuilder navIdsAll = new StringBuilder();
		// 2.面板包下的面板
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("packageId", pp.getId());
		map.put("onlineStatus", OnlineStatus.ONLINE.getValue());
		List<Panel> panelList = panelService.getPanelListByPackageId(map);
		if (CollectionUtils.isEmpty(panelList)) {
			return NO_PANEL;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		sb.append("<panels version=\"2.0\">\n");
		for (int i = 0; i < panelList.size(); i++) {
			Panel panel = panelList.get(i);
			panelIds.append(";").append(panel.getId());
			sb.append("<panel id=\"" + panel.getId() + "\" width=\"5\" height=\"3\" title=\"" + panel.getPanelTitle() + "\"");
			PanelPackageMap panelPackageMap = panelPackageMapService.getMapByPackageIdAndPanelId(Long.parseLong(templateId), panel.getId());
			// if (panel.getIsCustom() == 1) {
			sb.append(" defaultpanel=\"" + panelPackageMap.getDisplay() + "\"");
			sb.append(" lock=\"" + panelPackageMap.getIsLock() + "\"");
			/*
			 * } else { sb.append(" defaultpanel=\"true" + "\"");
			 * sb.append(" lock=\"true\""); }
			 */
			sb.append(">\n");
			sb.append("<bigimg><![CDATA[" + panel.getBigimg() + "]]></bigimg>\n");
			sb.append("<smallimg><![CDATA[" + panel.getSmallimg() + "]]></smallimg>\n");
			// 4.产品包关联的导航
			List<Long> navIds = panelService.getNavIds(panel.getId(), pp.getId());
			if (navIds == null) {
				return NO_NAVIGATION;
			}
			// 4.1过滤topNav
			for (int j = 0; j < navIds.size(); j++) {
				Navigation nav = navigationService.getNavigationById(navIds.get(j));
				if (nav == null) {
					return NO_TOP_NAVIGATION;
				}
				navIdsAll.append(";").append(nav.getId());
				if (nav.getNavType() == 1) {
					sb.append("<nav id=\"" + nav.getId() + "\"/>\n");
				}
			}
			// 4.2过滤bottomNav
			for (int j = 0; j < navIds.size(); j++) {
				Navigation nav = navigationService.getNavigationById(navIds.get(j));
				if (nav == null) {
					return NO_BOTTOM_NAVIGATION;
				}
				navIdsAll.append(";").append(nav.getId());
				if (nav.getNavType() == 2) {
					sb.append("<bottomnav id=\"nav_" + nav.getId() + "\"/>\n");
				}
			}
			sb.append("</panel>\n");
		}
		sb.append("</panels>");
		String s = sb.toString();
		if (panelIds.length() > 0) {
			panelIds.append(";");
		}
		if (navIdsAll.length() > 0) {
			navIdsAll.append(";");
		}
		RedisUtils.savePanelCache(redisTemplate, templateId, panelIds.toString(), navIdsAll.toString(), "", PACKAGE_PANEL_CUSTOMER_INTERFACE + templateId, DeviceInterfaceType.PanelCustom);
		return s;
	}

	@MethodCache(key = PACKAGE_PANEL_STYLE_INTERFACE + "#{templateId}" + ":panelIds:#{panelIds}")
	public String getPanelStyle(@KeyParam("templateId") String templateId, @KeyParam("panelIds") String panelIds) throws Exception {
		// 1.获取面板包
		PanelPackage pp = panelPackageService.getPanelPackageById(Long.parseLong(templateId));
		StringBuilder panelIdsAll = new StringBuilder();
		StringBuilder navIdsAll = new StringBuilder();
		StringBuilder panelItemIdsAll = new StringBuilder();
		if (pp == null) {
			return NO_TEMPLATE;
		}
		// 2.面板包下的面板
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("packageId", pp.getId());
		map.put("onlineStatus", OnlineStatus.ONLINE.getValue());
		map.put("display", "true");
		List<Panel> panelList = panelService.getPanelListByPackageId(map);
		if (CollectionUtils.isEmpty(panelList)) {
			return NO_PANEL;
		}
		Map<String, Object> ppMap = new HashMap<String, Object>();
		ppMap.put("packageId", pp.getId());
		ppMap.put("panelList", panelList);
		List<PanelPackageMap> ppMapList = panelPackageMapService.getMapByPanelPanelPackage(ppMap);

		if (CollectionUtils.isEmpty(ppMapList)) {
			return NO_PANEL_MAP;
		}

		if (!StringUtils.isEmpty(panelIds)) {
			map.remove("display");
			panelList = panelService.getPanelListByPackageId(map);
			String[] customIds = panelIds.split(",");
			panelList = panelFilter(customIds, panelList);
			ppMap.clear();
			ppMap.put("packageId", pp.getId());
			ppMap.put("panelList", panelList);
			ppMapList = panelPackageMapService.getMapByPanelPanelPackage(ppMap);
			ppMapList = panelPackageMapFilter(customIds, ppMapList);
		}

		// 面板关联的导航
		// 过滤包下重复的导航id
		String[] navArray = navIdsFilter(ppMapList);
		if (navArray == null) {
			return NO_NAVIGATION;
		}
		Map<String, String> topMap = topMap(navArray[0].split(","));

		Map<String, String> bottomMap = bottomMap(navArray[1].split(","));

		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		sb.append("<panels version=\"2.0\">\n");
		for (int i = 0; i < panelList.size(); i++) {
			Panel panel = panelList.get(i);
			panelIdsAll.append(";").append(panel.getId());
			PanelPackageMap panelPackageMap = panelPackageMapService.getMapByPackageIdAndPanelId(Long.parseLong(templateId), panel.getId());
			int aa = i + 1;
			sb.append("<panel id=\"" + aa + "\" width=\"5\" height=\"3\" title=\"" + panel.getPanelTitle() + "\" logo=\"" + panelPackageMap.getPanelLogo() + "\">\n");
			// 3.面板下的面板项
			List<PreviewItemData> pidList = panelService.getPreviewItemDataListByPanelId(panel.getId());
			if (CollectionUtils.isEmpty(pidList)) {
				return NO_PANEL_ITEM;
			}
			// 4.面板关联的导航
			List<Long> navIds = panelService.getNavIds(panel.getId(), pp.getId());
			if (navIds == null) {
				return NO_NAVIGATION;
			}
			// 4.1生成topNav
			for (int j = 0; j < navIds.size(); j++) {
				Navigation nav = navigationService.getNavigationById(navIds.get(j));
				if (nav == null) {
					return NO_TOP_NAVIGATION;
				}
				navIdsAll.append(";").append(nav.getId());
				if (nav.getNavType() == 1) {
					sb.append("<nav id=\"" + topMap.get(navIds.get(j) + "") + "\"/>\n");
				}
			}
			// 4.2生成box
			for (int j = 0; j < pidList.size(); j++) {
				PreviewItemData pid = pidList.get(j);
				if (pid == null) {
					return NO_PREVIEW_ITEM_DATA;
				}
				panelItemIdsAll.append(";").append(pid.getContentItemId());
				int a = i + 1;
				int b = j + 1;
				sb.append("<box id=\"" + a + "_" + b + "\"");
				int left = pid.getLeft() + 1;
				sb.append(" left=\"" + left + "\"");
				int top = pid.getTop() + 1;
				sb.append(" top=\"" + top + "\"");
				int with = pid.getWidth();
				sb.append(" width=\"" + with + "\"");
				int height = pid.getHeight();
				sb.append(" height=\"" + height + "\"");
				// 有且必须有第一页的第一个box的defaultfocus为true
				if (a == 1 && b == 1) {
					sb.append(" defaultfocus=\"true\"/>\n");
				} else {
					sb.append("/>\n");
				}
			}

			// 4.3生成bottomNav
			for (int j = 0; j < navIds.size(); j++) {
				Navigation nav = navigationService.getNavigationById(navIds.get(j));
				if (nav == null) {
					return NO_BOTTOM_NAVIGATION;
				}
				navIdsAll.append(";").append(nav.getId());
				if (nav.getNavType() == 2) {
					sb.append("<bottomnav id=\"nav_" + bottomMap.get(navIds.get(j) + "") + "\"/>\n");
				}
			}
			sb.append("</panel>\n");
		}
		sb.append("</panels>");
		String s = sb.toString();
		if (panelIdsAll.length() > 0) {
			panelIdsAll.append(";");
		}
		if (navIdsAll.length() > 0) {
			navIdsAll.append(";");
		}
		if (panelItemIdsAll.length() > 0) {
			panelItemIdsAll.append(";");
		}

		RedisUtils.savePanelCache(redisTemplate, templateId, panelIdsAll.toString(), navIdsAll.toString(), panelItemIdsAll.toString(), PACKAGE_PANEL_STYLE_INTERFACE + templateId + ":panelIds:" + panelIds, DeviceInterfaceType.PanelStyle);
		return s;
	}

	@MethodCache(key = PACKAGE_PANEL_DATA_INTERFACE + "#{templateId}" + ":panelIds:#{panelIds}")
	public String getPanelData(@KeyParam("templateId") String templateId, @KeyParam("panelIds") String panelIds) throws Exception {
		// 1.获取面板包
		PanelPackage pp = panelPackageService.getPanelPackageById(Long.parseLong(templateId));

		if (pp == null) {
			return NO_TEMPLATE;
		}
		// 2.面板包下的面板
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("packageId", pp.getId());
		map.put("onlineStatus", OnlineStatus.ONLINE.getValue());
		map.put("display", "true");
		List<Panel> panelList = panelService.getPanelListByPackageId(map);

		if (CollectionUtils.isEmpty(panelList)) {
			return NO_PANEL;
		}
		Map<String, Object> ppMap = new HashMap<String, Object>();
		ppMap.put("packageId", pp.getId());
		ppMap.put("panelList", panelList);
		List<PanelPackageMap> ppMapList = panelPackageMapService.getMapByPanelPanelPackage(ppMap);
		if (CollectionUtils.isEmpty(ppMapList)) {
			return NO_PANEL_MAP;
		}
		if (!StringUtils.isEmpty(panelIds)) {
			map.remove("display");
			panelList = panelService.getPanelListByPackageId(map);
			String[] customIds = panelIds.split(",");
			panelList = panelFilter(customIds, panelList);
			ppMap.clear();
			ppMap.put("packageId", pp.getId());
			ppMap.put("panelList", panelList);
			ppMapList = panelPackageMapService.getMapByPanelPanelPackage(ppMap);
			ppMapList = panelPackageMapFilter(customIds, ppMapList);
		}

		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		sb.append("<boxs>\n");
		String[] result = genBoxforData(panelList, pp);
		String boxStr = (String) result[0];
		if (StringUtils.isEmpty(boxStr)) {
			return "No box data found.";
		} else {
			sb.append(boxStr);
		}

		// 4.面板关联的导航
		// 过滤包下重复的导航id
		String[] navArray = navIdsFilter(ppMapList);
		if (navArray == null) {
			return NO_NAVIGATION;
		}

		// 4.1生成nav
		String navStr = genNavForData(navArray[0].split(","));
		if (StringUtils.isNotEmpty(navStr)) {
			sb.append(navStr);
		}
		// 4.2生成bottomNav
		String bottomNavStr = genBottomNavForData(navArray[1].split(","));
		if (StringUtils.isEmpty(bottomNavStr)) {
			return NO_BOTTOM_NAVIGATION;
		} else {
			sb.append(bottomNavStr);
		}
		sb.append("</boxs>");
		String s = sb.toString();
		// @MethodCache(key = PACKAGE_PANEL_DATA_INTERFACE + "#{templateId}" +
		// ":panelIds:#{panelIds}")
		RedisUtils.savePanelCache(redisTemplate, templateId, result[1], navArray[2], result[2], PACKAGE_PANEL_DATA_INTERFACE + templateId + ":panelIds:" + panelIds, DeviceInterfaceType.PanelData);
		return s;
	}

	private List<Panel> panelFilter(String[] customIds, List<Panel> tempList) {
		List<Panel> panelList = new ArrayList<Panel>();
		if (customIds != null && !CollectionUtils.isEmpty(tempList)) {
			for (int j = 0; j < customIds.length; j++) {
				for (int i = 0; i < tempList.size(); i++) {
					Panel panel = tempList.get(i);
					if (panel.getId() == Long.parseLong(customIds[j])) {
						panelList.add(panel);
						break;
					}
				}
			}
		}
		return panelList;
	}

	/**
	 * 生成上部导航
	 * 
	 * @param navTopIds
	 * @return
	 */
	private String genNavForData(String[] navTopIds) {
		StringBuilder sb = new StringBuilder();
		int navChar = (int) 'a';
		int navCharCount = 0;
		for (int j = 0; j < navTopIds.length; j++) {
			Navigation nav = navigationService.getNavigationById(Long.parseLong(navTopIds[j]));
			if (nav == null) {
				return null;
			}
			char a = (char) (navCharCount + navChar);
			navCharCount++;
			sb.append("<nav id=\"" + a + "\">\n");
			if (nav.getImageUrl() != null) {
				sb.append("<imgurl><![CDATA[" + nav.getImageUrl() + "]]></imgurl>\n");
			} else {
				sb.append("<imgurl><![CDATA[\"\"]]></imgurl>\n");
			}
			sb.append("<focusimgurl><![CDATA[" + "]]></focusimgurl>\n");
			if (nav.getTitle() != null) {
				sb.append("<focustext><![CDATA[" + nav.getTitle() + "]]></focustext>\n");
			} else {
				sb.append("<focustext><![CDATA[\"\"]]></focustext>\n");
			}
			sb.append("<title><![CDATA[" + "]]></title>\n");
			if (nav.getActionType() != null) {
				sb.append("<action><![CDATA[" + nav.getActionType() + "]]></action>\n");
			} else {
				sb.append("<action><![CDATA[\"\"]]></action>\n");
			}
			if (StringUtils.isNotBlank(nav.getActionUrl())) {
				sb.append("<actionurl><![CDATA[" + nav.getActionUrl() + "]]></actionurl>\n");
			}
			sb.append("</nav>\n");
		}
		return sb.toString();
	}

	private String[] genBoxforData(List<Panel> panelList, PanelPackage pp) {
		String[] result = new String[3];
		StringBuilder sb = new StringBuilder();
		StringBuilder panelIdsAll = new StringBuilder();
		StringBuilder panelItemIdsAll = new StringBuilder();
		Map<String, String> listMap = new HashMap<String, String>();
		Map<String, String> typeMap = new HashMap<String, String>();
		for (int i = 0; i < panelList.size(); i++) {
			Panel panel = panelList.get(i);
			panelIdsAll.append(";").append(panel.getId());
			// 3.生成box
			// 面板下的面板项
			List<PreviewItemData> pidList = panelService.getPreviewItemDataListByPanelId(panel.getId());
			if (CollectionUtils.isEmpty(pidList)) {
				return null;
			}
			for (int j = 0; j < pidList.size(); j++) {
				PreviewItemData pid = pidList.get(j);
				if (pid.getPanelItem() == null) {
					continue;
				}
				PanelItem pi = pid.getPanelItem();
				panelItemIdsAll.append(";").append(pi.getId());
				int a = i + 1;
				int b = j + 1;
				sb.append("<box id=\"" + a + "_" + b + "\"");
				if (pi.getHasSubItem() != null && pi.getHasSubItem() == 1 && pi.getRefItemId() != null) {
					sb.append(" type=\"list\"");
					sb.append(" link=listLink" + pi.getId());
					typeMap.put("typeLink" + pi.getId(), a + "_" + b);
				} else if (pi.getHasSubItem() != null && pi.getHasSubItem() == 0 && pi.getRefItemId() != null
						&& pi.getContentType().equals(PanelItemContentType.IMAGE.getValue())) {
					sb.append(" type=\"image\"");
					sb.append(" link=typeLink" + pi.getRefItemId());
					listMap.put("listLink" + pi.getRefItemId(), a + "_" + b);
				} else if (pi.getHasSubItem() != null && pi.getHasSubItem() == 0 && pi.getRefItemId() != null
						&& pi.getContentType().equals(PanelItemContentType.VIDEO.getValue())) {
					sb.append(" type=\"video\"");
					sb.append(" link=typeLink" + pi.getRefItemId());
					listMap.put("listLink" + pi.getRefItemId(), a + "_" + b);
				} else {
					sb.append(" type=\"" + pi.getContentType() + "\"");
				}
				if ((pi.getAutoRun() != null && pi.getAutoRun() == 0 && pi.getContentType().equals(PanelItemContentType.VIDEO.getValue()))
						|| (pi.getHasSubItem() != null && pi.getHasSubItem() == 1 && pi.getRefItemId() != null)) {
					sb.append(" auto=\"true\"");
				}
				if (pi.getFocusRun() != null && pi.getFocusRun() == 1) {
					sb.append(" canfocus=\"true\"");
				} else {
					sb.append(" canfocus=\"false\"");
				}
				if (!pi.getContentType().equals(PanelItemContentType.VIDEO.getValue())) {
					if (pi.getShowTitle() != null && pi.getShowTitle() == 1) {
						sb.append(" showtitle=\"true\">\n");
					} else {
						sb.append(" showtitle=\"false\">\n");
					}
				} else {
					sb.append(">\n");
				}

				// 3.1面板项下的子面板项
				List<PanelItem> subPiList = panelItemService.findSublItemListByPanelItemParentId(pi.getId());
				if (!subPiList.isEmpty()) {
					for (int k = 0; k < subPiList.size(); k++) {
						PanelItem subPi = subPiList.get(k);
						panelItemIdsAll.append(";").append(subPi.getId());
						sb.append("<item>\n");
						if (StringUtils.isNotBlank(subPi.getTitle())) {
							sb.append("<title><![CDATA[" + subPi.getTitle() + "]]></title>\n");
						}
						if (subPi.getImageUrl() != null) {
							sb.append("<imgurl><![CDATA[" + subPi.getImageUrl() + "]]></imgurl>\n");
						} else {
							sb.append("<imgurl><![CDATA[\"\"]]></imgurl>\n");
						}
						if (StringUtils.isNotBlank(subPi.getVideoUrl())) {
							sb.append("<videourl><![CDATA[" + subPi.getVideoUrl() + "]]></videourl>\n");
						}
						if (subPi.getActionType() != null && subPi.getActionType() == 1) {
							sb.append("<action><![CDATA[" + ActionType.OpenUrl + "]]></action>\n");
						} else if (subPi.getActionType() != null && subPi.getActionType() == 2) {
							sb.append("<action><![CDATA[" + ActionType.OpenApp + "]]></action>\n");
						} else {
							sb.append("<action><![CDATA[" + ActionType.Install + "]]></action>\n");
							sb.append("<Installurl><![CDATA[" + subPi.getInstallUrl() + "]]></Installurl>\n");
						}
						if (StringUtils.isNotBlank(subPi.getActionUrl())) {
							genActionUrlForBox(pp, sb, subPi);
						}
						sb.append("</item>\n");
					}

				} else if (pi.getHasSubItem() != null && pi.getHasSubItem() == 0 && pi.getRefItemId() == null
						&& pi.getContentType().equals(PanelItemContentType.VIDEO.getValue())) {
					sb.append("<item>\n");
					if (pi.getImageUrl() != null) {
						sb.append("<imgurl><![CDATA[" + pi.getImageUrl() + "]]></imgurl>\n");
					} else {
						sb.append("<imgurl><![CDATA[\"\"]]></imgurl>\n");
					}
					if (StringUtils.isNotBlank(pi.getVideoUrl())) {
						sb.append("<videourl><![CDATA[" + pi.getVideoUrl() + "]]></videourl>\n");
					}
					if (pi.getActionType() != null && pi.getActionType() == 1) {
						sb.append("<action><![CDATA[" + ActionType.OpenUrl + "]]></action>\n");
					} else if (pi.getActionType() != null && pi.getActionType() == 2) {
						sb.append("<action><![CDATA[" + ActionType.OpenApp + "]]></action>\n");
					} else {
						sb.append("<action><![CDATA[" + ActionType.Install + "]]></action>\n");
						sb.append("<Installurl><![CDATA[" + pi.getInstallUrl() + "]]></Installurl>\n");
					}
					if (StringUtils.isNotBlank(pi.getActionUrl())) {
						genActionUrlForBox(pp, sb, pi);
					}
					sb.append("</item>\n");
				} else if (pi.getHasSubItem() != null && pi.getHasSubItem() == 0 && pi.getRefItemId() != null) {
					List<PanelItem> refPiList = panelItemService.findSublItemListByPanelItemParentId(pi.getRefItemId());
					for (int k = 0; k < refPiList.size(); k++) {
						PanelItem subPi = refPiList.get(k);
						panelItemIdsAll.append(";").append(subPi.getId());
						sb.append("<item>\n");
						if (subPi.getImageUrl() != null) {
							sb.append("<imgurl><![CDATA[" + subPi.getImageUrl() + "]]></imgurl>\n");
						} else {
							sb.append("<imgurl><![CDATA[\"\"]]></imgurl>\n");
						}
						if (StringUtils.isNotBlank(subPi.getVideoUrl())) {
							sb.append("<videourl><![CDATA[" + subPi.getVideoUrl() + "]]></videourl>\n");
						}
						if (subPi.getActionType() != null && subPi.getActionType() == 1) {
							sb.append("<action><![CDATA[" + ActionType.OpenUrl + "]]></action>\n");
						} else if (subPi.getActionType() != null && subPi.getActionType() == 2) {
							sb.append("<action><![CDATA[" + ActionType.OpenApp + "]]></action>\n");
						} else {
							sb.append("<action><![CDATA[" + ActionType.Install + "]]></action>\n");
							sb.append("<Installurl><![CDATA[" + subPi.getInstallUrl() + "]]></Installurl>\n");
						}
						if (StringUtils.isNotBlank(subPi.getActionUrl())) {
							genActionUrlForBox(pp, sb, subPi);
						}
						sb.append("</item>\n");
					}
				} else if (pi.getContentType().equals(PanelItemContentType.CUSTOM.getValue())) {
					// 自定义item
					sb.append("<content>\n");
					sb.append("<varstr><![CDATA[" + pi.getContent() + "]]></varstr>\n");
					sb.append("</content>\n");
					if (StringUtils.isNotBlank(pi.getTitle())) {
						sb.append("<title><![CDATA[" + pi.getTitle() + "]]></title>\n");
					}
					if (pi.getActionType() != null && pi.getActionType() == 1) {
						sb.append("<action><![CDATA[" + ActionType.OpenUrl + "]]></action>\n");
					} else if (pi.getActionType() != null && pi.getActionType() == 2) {
						sb.append("<action><![CDATA[" + ActionType.OpenApp + "]]></action>\n");
					} else {
						sb.append("<action><![CDATA[" + ActionType.Install + "]]></action>\n");
						sb.append("<Installurl><![CDATA[" + pi.getInstallUrl() + "]]></Installurl>\n");
					}
					if (StringUtils.isNotBlank(pi.getActionUrl())) {
						genActionUrlForBox(pp, sb, pi);
					}
				} else {

					if (StringUtils.isNotBlank(pi.getTitle())) {
						sb.append("<title><![CDATA[" + pi.getTitle() + "]]></title>\n");
					}
					if (pi.getTitleComment() != null) {
						sb.append("<titlecomment><![CDATA[" + pi.getTitleComment() + "]]></titlecomment>\n");
					} else {
						sb.append("<titlecomment><![CDATA[\"\"]]></titlecomment>\n");
					}
					if (pi.getImageUrl() != null) {
						sb.append("<imgurl><![CDATA[" + pi.getImageUrl() + "]]></imgurl>\n");
					} else {
						sb.append("<imgurl><![CDATA[\"\"]]></imgurl>\n");
					}
					if (StringUtils.isNotBlank(pi.getVideoUrl())) {
						sb.append("<videourl><![CDATA[" + pi.getVideoUrl() + "]]></videourl>\n");
					}
					if (pi.getActionType() != null && pi.getActionType() == 1) {
						sb.append("<action><![CDATA[" + ActionType.OpenUrl + "]]></action>\n");
					} else if (pi.getActionType() != null && pi.getActionType() == 2) {
						sb.append("<action><![CDATA[" + ActionType.OpenApp + "]]></action>\n");
					} else {
						sb.append("<action><![CDATA[" + ActionType.Install + "]]></action>\n");
						sb.append("<Installurl><![CDATA[" + pi.getInstallUrl() + "]]></Installurl>\n");
					}
					if (StringUtils.isNotBlank(pi.getActionUrl())) {
						genActionUrlForBox(pp, sb, pi);
					}
					if (pi.getRefItemId() != null) {
						sb.append("<leftTitleIcon><![CDATA[" + pi.getRefItemId() + "]]></leftTitleIcon>\n");
					}
				}
				sb.append("</box>\n");
			}
		}
		String reslutStr = sb.toString();
		result[0] = reslutStr;
		if (panelIdsAll.length() > 0) {
			panelIdsAll.append(';');
		}
		if (panelItemIdsAll.length() > 0) {
			panelItemIdsAll.append(';');
		}
		result[1] = panelIdsAll.toString();
		result[2] = panelItemIdsAll.toString();
		reslutStr = transformBoxLink(listMap, typeMap, reslutStr);
		return result;
	}

	private void genActionUrlForBox(PanelPackage pp, StringBuilder sb, PanelItem subPi) {
		if (StringUtils.isNotBlank(pp.getDistrictCode())) {
			sb.append("<actionurl><![CDATA[" + genDistrictActionUrl(pp, subPi.getActionUrl()) + "]]></actionurl>\n");
		} else {
			sb.append("<actionurl><![CDATA[" + subPi.getActionUrl() + "]]></actionurl>\n");
		}
	}

	private String genDistrictActionUrl(PanelPackage pp, String actionUrl) {
		String[] actionUrlSplit = actionUrl.split("&", 0);
		String tailDidtrictActionUrl = actionUrl.substring(actionUrlSplit[0].length());
		StringBuilder districtActionUrl = new StringBuilder();
		districtActionUrl.append(lvomsDistrictCodeUrlRepository.selectByDistrictCode(pp.getDistrictCode(), pp.getId()).getUrl());
		districtActionUrl.append(tailDidtrictActionUrl);
		return districtActionUrl.toString();
	}

	/**
	 * 转换box之间的link
	 * 
	 * @param listMap
	 * @param typeMap
	 * @param reslutStr
	 * @return
	 */
	private String transformBoxLink(Map<String, String> listMap, Map<String, String> typeMap, String reslutStr) {
		if (listMap.size() != 0 && typeMap.size() != 0) {
			Set<String> listKey = listMap.keySet();
			for (Iterator<String> it = listKey.iterator(); it.hasNext();) {
				String s = (String) it.next();
				String sa = "link=" + s;
				reslutStr = reslutStr.replace(sa, "link=" + "\"" + listMap.get(s) + "\"");
			}

			Set<String> typeKey = typeMap.keySet();
			for (Iterator<String> it = typeKey.iterator(); it.hasNext();) {
				String s = (String) it.next();
				String sa = "link=" + s;
				reslutStr = reslutStr.replace(sa, "link=" + "\"" + typeMap.get(s) + "\"");
			}
		}
		return reslutStr;
	}

	private List<PanelPackageMap> panelPackageMapFilter(String[] customIds, List<PanelPackageMap> tempMapList) {
		List<PanelPackageMap> mapList = new ArrayList<PanelPackageMap>();
		if (customIds != null && !CollectionUtils.isEmpty(tempMapList)) {
			for (int i = 0; i < tempMapList.size(); i++) {
				PanelPackageMap map = tempMapList.get(i);
				for (int j = 0; j < customIds.length; j++) {
					if (map.getPanelId() == Long.parseLong(customIds[j])) {
						mapList.add(map);
						break;
					}
				}
			}
		}
		return mapList;
	}

	/**
	 * 过滤上、下导航
	 * 
	 * @param mapList
	 * @return
	 */
	private String[] navIdsFilter(List<PanelPackageMap> mapList) {
		String[] navArray = new String[3];
		StringBuilder navIdsAll = new StringBuilder();
		StringBuilder navTopSb = new StringBuilder();
		StringBuilder navBottomSb = new StringBuilder();
		for (int i = 0; i < mapList.size(); i++) {
			PanelPackageMap map = mapList.get(i);
			String ids = map.getNavId();
			String idsArr[] = ids.split(",");
			Long idsLong[] = new Long[idsArr.length];
			for (int j = 0; j < idsArr.length; j++) {
				idsLong[j] = Long.parseLong(idsArr[j]);
				Navigation nav = navigationService.getNavigationById(idsLong[j]);
				if (nav == null) {
					return null;
				}
				navIdsAll.append(";").append(nav.getId());
				if (nav.getNavType() == 1) {
					navTopSb.append(nav.getId()).append(",");
				} else {
					navBottomSb.append(nav.getId()).append(",");
				}
			}
		}
		String navTop = removeDuplicate(navTopSb.toString());
		String navBottom = removeDuplicate(navBottomSb.toString());
		navArray[0] = navTop;
		navArray[1] = navBottom;
		navArray[2] = navIdsAll.toString();
		return navArray;
	}

	private Map<String, String> bottomMap(String[] navBottomIds) {
		int bottomNavChar = (int) 'a';
		int bottomNavCharCount = 0;
		Map<String, String> bottomMap = new HashMap<String, String>();
		// 生成 bottomnav map
		for (int j = 0; j < navBottomIds.length; j++) {
			char a = (char) (bottomNavCharCount + bottomNavChar);
			bottomNavCharCount++;
			bottomMap.put(navBottomIds[j], a + "");
		}
		return bottomMap;
	}

	private Map<String, String> topMap(String[] navTopIds) {
		int navChar = (int) 'a';
		int navCharCount = 0;
		Map<String, String> topMap = new HashMap<String, String>();
		// 生成 nav map
		for (int j = 0; j < navTopIds.length; j++) {
			char a = (char) (navCharCount + navChar);
			topMap.put(navTopIds[j], a + "");
			navCharCount++;
		}
		return topMap;
	}

	private String genBottomNavForData(String[] navBottomIds) {
		int bottomNavChar = (int) 'a';
		int bottomNavCharCount = 0;
		StringBuilder sb = new StringBuilder();
		for (int j = 0; j < navBottomIds.length; j++) {
			Navigation nav = navigationService.getNavigationById(Long.parseLong(navBottomIds[j]));
			if (nav == null) {
				return null;
			}
			char a = (char) (bottomNavCharCount + bottomNavChar);
			bottomNavCharCount++;
			sb.append("<bottomnav id=\"nav_" + a + "\">\n");
			if (nav.getTitle() != null) {
				sb.append("<title><![CDATA[" + nav.getTitle() + "]]></title>\n");
			} else {
				sb.append("<title><![CDATA[]]></title>\n");
			}
			if (nav.getImageUrl() != null) {
				sb.append("<imgurl><![CDATA[" + nav.getImageUrl() + "]]></imgurl>\n");
			} else {
				sb.append("<imgurl><![CDATA[\"\"]]></imgurl>\n");
			}
			sb.append("<focusimgurl><![CDATA[" + "]]></focusimgurl>\n");
			if (nav.getActionType() != null) {
				sb.append("<action><![CDATA[" + nav.getActionType() + "]]></action>\n");
			} else {
				sb.append("<action><![CDATA[\"\"]]></action>\n");
			}
			if (StringUtils.isNotBlank(nav.getActionUrl())) {
				sb.append("<actionurl><![CDATA[" + nav.getActionUrl() + "]]></actionurl>\n");
			}
			sb.append("</bottomnav>\n");
		}
		return sb.toString();
	}

	private String removeDuplicate(String str) {
		List<String> data = new ArrayList<String>();
		String[] s = str.split(",");
		for (int i = 0; i < s.length; i++) {

			if (!data.contains(s[i])) {
				data.add(s[i]);
			}
		}

		StringBuilder sb = new StringBuilder();
		for (String s1 : data) {
			sb.append(s1).append(",");
		}
		return sb.toString();
	}
}
