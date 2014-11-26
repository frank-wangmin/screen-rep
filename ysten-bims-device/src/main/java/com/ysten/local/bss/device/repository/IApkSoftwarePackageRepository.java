package com.ysten.local.bss.device.repository;

import com.ysten.local.bss.device.domain.ApkSoftwarePackage;
import com.ysten.utils.page.Pageable;

import java.util.List;


public interface IApkSoftwarePackageRepository {

    List<ApkSoftwarePackage> getSoftwarePackageListBySoftCodeIdAndVersionSeq(long softwareCodeId, Integer deviceVersionSeq);

    Pageable<ApkSoftwarePackage> findListByNameAndCode(String versionName,String softCodeName,String packageType,Long softCodeId,Integer pageNo,Integer pageSize);
    
    boolean insert(ApkSoftwarePackage apkSoftwarePackage);
    
    ApkSoftwarePackage selectByPrimaryKey(Long softwareId);
    
    boolean updateById(ApkSoftwarePackage apkSoftwarePackage);
    
    List<ApkSoftwarePackage> findSoftwarePackageBySoftCode(Long softwareCodeId);
    
    ApkSoftwarePackage getApkSoftwarePackByName(String versionName);
}

