package com.ysten.local.bss.device.repository.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ysten.local.bss.device.domain.PanelPackageUserMap;

public interface PanelPackageUserMapMapper {


    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bss_panel_package_user_map
     *
     * @mbggenerated Tue May 27 17:17:33 CST 2014
     */
    int deleteByPrimaryKey(Long id);
    
    int deleteMapByUserGroupId(@Param("userGroupId") Long userGroupId);
    
    int deleteMapByUserCode(@Param("code")String code);
    
    int deleteByPanelId(@Param("panelPackageId") Long panelPackageId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bss_panel_package_user_map
     *
     * @mbggenerated Tue May 27 17:17:33 CST 2014
     */
    int insert(PanelPackageUserMap record);

   int bulkSaveUserPanelMap(@Param("list") List<PanelPackageUserMap> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bss_panel_package_user_map
     *
     * @mbggenerated Tue May 27 17:17:33 CST 2014
     */
    int insertSelective(PanelPackageUserMap record);


    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bss_panel_package_user_map
     *
     * @mbggenerated Tue May 27 17:17:33 CST 2014
     */
    PanelPackageUserMap selectByPrimaryKey(Long id);
    
    PanelPackageUserMap getMapByUserGroupId(@Param("userGroupId") Long userGroupId);
    
    PanelPackageUserMap getMapByUserCode(@Param("code")String code);

    List<PanelPackageUserMap> findMapByPanelIdAndType(@Param("panelPackageId")Long panelPackageId,@Param("type")String type);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bss_panel_package_user_map
     *
     * @mbggenerated Tue May 27 17:17:33 CST 2014
     */
    int updateByPrimaryKeySelective(PanelPackageUserMap record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bss_panel_package_user_map
     *
     * @mbggenerated Tue May 27 17:17:33 CST 2014
     */
    int updateByPrimaryKey(PanelPackageUserMap record);
}