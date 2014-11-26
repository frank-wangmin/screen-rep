package com.ysten.local.bss.panel.repository.mapper;

import com.ysten.local.bss.panel.domain.Panel;
import com.ysten.local.bss.panel.vo.PanelQueryCriteria;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by frank on 14-5-9.
 */
public interface PanelMapper {

    /**
     * 根据ID查询panel
     *
     * @param panelId
     * @return
     */
    Panel getPanelById(Long panelId);

    /**
     * 根据ID查询panel
     *
     * @param panelName
     * @return
     */
    List<Panel> getPanelByName(String panelName);

    /**
     * 新增panel
     *
     * @param panel
     * @return
     */
    void insert(Panel panel);

    /**
     * 根据ID删除panel
     *
     * @param panelId
     * @return
     */
    void deleteById(Long panelId);

    /**
     * 根据IDs批量删除panel
     *
     * @param ids
     * @return
     */
    void deleteByIds(@Param("ids") List<Long> ids);

    /**
     * 更新panel
     *
     * @param panel
     * @return
     */
    void updatePanel(Panel panel);

    void batchUpdate(List<Panel> list);


    /**
     * get all panel list
     *
     * @return
     */
    List<Panel> getPanelList(PanelQueryCriteria panelQueryCriteria);

    /**
     * get all panel number
     *
     * @param panelQueryCriteria
     * @return
     */
    Integer getPanelCount(PanelQueryCriteria panelQueryCriteria);

    List<Panel> getPanelListByPackageId(@Param("packageId") Long packageId, @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

    List<Panel> selectPanelListByPackageId(Map<String, Object> map);

    List<Panel> getAllOnlinePanels(@Param("onlineStatus") int onlineStatus,@Param("dpi") String dpi);

    Integer getPanelCountByPackageId(@Param("packageId") Long packageId);

    /**
     * get panel by epgPanelId
     *
     * @param epgPanelId
     * @return panel
     */
    Panel getPanelByEpgPanelId(@Param("epgPanelId") Long epgPanelId);

    List<Panel> findAllEpgList(@Param("districtCode") String districtCode);

    List<Panel> findAllList();

    List<Panel>  findIdAndEpgIdList(@Param("districtCode") String districtCode);

    /**
     * delete all the epg data
     * @return
     */
    Integer deleteByEpgPanelId();

    void batchInsert(List<Panel> list);
}
