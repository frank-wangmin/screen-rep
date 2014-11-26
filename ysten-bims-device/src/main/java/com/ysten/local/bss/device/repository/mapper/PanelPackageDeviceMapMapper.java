package com.ysten.local.bss.device.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.device.domain.PanelPackageDeviceMap;

public interface PanelPackageDeviceMapMapper {


    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bss_panel_package_device_map
     *
     * @mbggenerated Tue May 27 17:27:40 CST 2014
     */
    int deleteByPrimaryKey(Long id);
    
	int deletePanelDeviceMapByGroupId(@Param("deviceGroupId")Long deviceGroupId);
	
	PanelPackageDeviceMap getPanelDeviceMapByGroupId(@Param("deviceGroupId")Long deviceGroupId);

    PanelPackageDeviceMap getPanelDeviceMapByYstenId(@Param("ystenId") String ystenId);

	int deletePanelDeviceMapByCode(@Param("ystenId")String ystenId);
	
//	int deleteByPanelId(@Param("panelPackageId") Long panelPackageId,@Param("type")String isAll);
	
	int updateByYstenId(PanelPackageDeviceMap panelPackageDeviceMap);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bss_panel_package_device_map
     *
     * @mbggenerated Tue May 27 17:27:40 CST 2014
     */
    int insert(PanelPackageDeviceMap record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bss_panel_package_device_map
     *
     * @mbggenerated Tue May 27 17:27:40 CST 2014
     */
    int insertSelective(PanelPackageDeviceMap record);


    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bss_panel_package_device_map
     *
     * @mbggenerated Tue May 27 17:27:40 CST 2014
     */
    PanelPackageDeviceMap selectByPrimaryKey(Long id);

    List<PanelPackageDeviceMap> findMapByPanelIdAndType(@Param("panelPackageId")Long panelPackageId,@Param("type")String type);


    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bss_panel_package_device_map
     *
     * @mbggenerated Tue May 27 17:27:40 CST 2014
     */
    int updateByPrimaryKeySelective(PanelPackageDeviceMap record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bss_panel_package_device_map
     *
     * @mbggenerated Tue May 27 17:27:40 CST 2014
     */
    int updateByPrimaryKey(PanelPackageDeviceMap record);
    
    void deletePanelDeviceMapByGroupIds(@Param("groupIds")List<Long> groupIds,@Param("tableName")String tableName,@Param("character")String character);
    
    void deletePanelDeviceMapByYstenIds(@Param("ystenIds")List<String> ystenIds,@Param("tableName")String tableName,@Param("character")String character );
    
    void bulkSaveDevicePanelMap(List<PanelPackageDeviceMap> list);

    void deletePanelPackageMapByPanelId(@Param("panelPackageId") Long panelPackageId,@Param("type")String type,@Param("tableName")String tableName);
}