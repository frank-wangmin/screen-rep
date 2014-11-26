package com.ysten.local.bss.panel.service;

import java.util.List;
import java.util.Map;

import com.ysten.local.bss.panel.domain.Panel;
import com.ysten.local.bss.panel.domain.PanelPackageMap;
import com.ysten.local.bss.panel.domain.PanelPackageNavigation;
import com.ysten.local.bss.panel.domain.PreviewItemData;
import com.ysten.local.bss.panel.vo.PanelQueryCriteria;
import com.ysten.utils.page.Pageable;

/**
 * Created by frank on 14-5-19.
 */
public interface IPanelService {

	Pageable<Panel> getPanelList(PanelQueryCriteria panelQueryCriteria);

	/**
	 * get all panel list by packageId
	 * 
	 * @return
	 */
	Pageable<PanelPackageNavigation> getPanelListByPackageId(Long packageId,String dpi,Integer pageNo, Integer pageSize);

	/**
	 *
	 * @return
	 */
	List<Panel> getPanelListByPackageId(Map<String, Object> map);

    List<Panel> getAllOnlinePanels(int onlineStatus,String dpi);

	/**
	 * 根据ID查询panel
	 * 
	 * @param panelId
	 * @return
	 */
	Panel getPanelById(Long panelId);

	/**
	 * 新增panel
	 * 
	 * @param panel
	 * @return
	 */
	void insert(Panel panel,Long oprUserId);

	/**
	 * 根据IDs批量删除panel
	 * 
	 * @param ids
	 * @return
	 */
	void deleteByIds(List<Long> ids);

    String onlinePanel(List<Long> ids,Long oprUserId);

    void downLinePanel(List<Long> ids,Long oprUserId);

	/**
	 * 更新panel
	 * 
	 * @param panel
	 * @return
	 */
	void updatePanel(Panel panel,Long oprUserId);

	void insertPanelPackageMap(PanelPackageMap panelPackageMap);

	List<PanelPackageMap> verifyIfExistBinded(Long panelId, Long packageId);

	List<Long> getNavIds(Long panelId, Long packageId);

    List<PreviewItemData> getPreviewItemDataListByPanelId(Long panelId);

    void unBindPanelPackage(Long packageId,List<Long> panelIds);

    void submitPreviewItemDatas(PreviewItemData[] previewItemDatas,Long operUserId);

    boolean pushPanelData();

    List<Panel> getPanelByName(String name);
}
