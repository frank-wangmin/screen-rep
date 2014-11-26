package com.ysten.local.bss.device.repository.mapper;

import com.ysten.local.bss.device.domain.ApkUpgradeMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ApkUpgradeMapMapper {
    int deleteByPrimaryKey(Long id);
    int insert(ApkUpgradeMap record);
    ApkUpgradeMap selectByPrimaryKey(Long id);
    int updateByPrimaryKey(ApkUpgradeMap record);

    ApkUpgradeMap getByYstenIdAndUpgradeId(@Param("ystenId")String ystenId, @Param("upgradeId")long upgradeId);

    ApkUpgradeMap getByGroupIdAndUpgradeId(@Param("deviceGroupId") long deviceGroupId, @Param("upgradeId")long upgradeId);

    List<ApkUpgradeMap> findMapByUpgradeIdAndType(@Param("upgradeId")Long upgradeId,@Param("type")String type);
    
    int deleteByUpgradeIdAndType(@Param("upgradeId")Long upgradeId,@Param("type")String type);
    
    void bulkSaveApkUpgradeMap(List<ApkUpgradeMap> list);
    
    void deleteMapByYstenIdsAndUpgradeId(@Param("ystenIds")String[] ystenIds, @Param("upgradeId")long upgradeId);
    
    int deleteByUpgradeId(@Param("ids")List<Long> ids);

    int deleteApkUpgradeMapByYstenId(@Param("ystenId")String ystenId);

    int save(ApkUpgradeMap apkUpgradeMap);

    void deleteUpgradeTaskMapByGroupId(@Param("groupId")Long id);;
}