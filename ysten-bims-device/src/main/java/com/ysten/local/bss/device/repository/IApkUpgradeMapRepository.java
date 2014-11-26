package com.ysten.local.bss.device.repository;

import com.ysten.local.bss.device.domain.ApkUpgradeMap;

import java.util.List;


public interface IApkUpgradeMapRepository {

    ApkUpgradeMap getApkUpgradeMapYstenId(String ystenId, long upgradeId);

    ApkUpgradeMap getApkUpgradeMapByGroupId(long groupId, long upgradeId);
    
    List<ApkUpgradeMap> findMapByUpgradeIdAndType(Long upgradeId,String type);
    
    boolean deleteApkUpgradeMapByType(Long upgradeId,String type);
    
    void bulkSaveApkUpgradeMap(List<ApkUpgradeMap> list);
    
    boolean deleteUpgradeTaskMap(List<Long> ids);
    
    void deleteMapByYstenIdsAndUpgradeId(String[] ystenIds,long upgradeId);

    void deleteApkUpgradeMapByYstenId(String ystenId);

    boolean saveApkUpgradeMap(ApkUpgradeMap deviceUpgradeMap);

    void deleteApkUpgradeMapByGroupId(Long id);

}
