package com.ysten.local.bss.device.repository.mapper;

import java.util.List;

import com.ysten.local.bss.device.domain.UserAppUpgradeMap;
import org.apache.ibatis.annotations.Param;
import com.ysten.local.bss.device.domain.AppUpgradeMap;


public interface AppUpgradeMapMapper {
	
	int deleteByUpgradeId(@Param("ids")List<Long> ids);
	
	int save(AppUpgradeMap appUpgradeMap);
	
	int updateByUpgradeId(AppUpgradeMap appUpgradeMap);
	
	List<AppUpgradeMap> findByGroupIdAndType(@Param("groupId")Long groupId,@Param("type")String type);
	
	List<AppUpgradeMap>  findMapByUpgradeIdAndType(@Param("upgradeId")Long upgradeId,@Param("type")String type);

	/**
	 * 根据分组Id删除应用升级设备分组信息
	 * @param id
	 */
	void deleteAppUpgradeMapByGroupId(@Param("groupId")Long id);
	
    int deleteAppUpgradeMapByCode(@Param("ystenId")String ystenId);
    
    int deleteAppUpgradeMapByUpgradeIdAndType(@Param("upgradeId")Long upgradeId,@Param("type")String type);

    int deleteAppUpgradeMapByUpgradeId(@Param("upgradeId")Long upgradeId);

    /**
     * find AppUpgradeMap according the upgradeId
     * @param upgradeId
     * @return list
     */
    List<AppUpgradeMap>  findMapByUpgradeId(@Param("upgradeId")Long upgradeId);
    /**
     * find AppUpgradeMap according the upgradeId
     * @param upgradeId
     * @return list
     */
    List<UserAppUpgradeMap> findUserMapByUpgradeId(@Param("upgradeId")Long upgradeId);
    
    List<AppUpgradeMap> findAppUpgradeMapByYstenId(@Param("ystenId")String ystenId);
    
    AppUpgradeMap getByYstenIdAndUpgradeId(@Param("ystenId")String ystenId, @Param("upgradeId")long upgradeId);
}
