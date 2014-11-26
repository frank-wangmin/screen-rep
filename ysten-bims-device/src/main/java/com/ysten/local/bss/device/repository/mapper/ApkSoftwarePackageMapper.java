package com.ysten.local.bss.device.repository.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ysten.local.bss.device.domain.ApkSoftwarePackage;

public interface ApkSoftwarePackageMapper {
    int deleteByPrimaryKey(Long id);
    int insert(ApkSoftwarePackage record);
    ApkSoftwarePackage selectByPrimaryKey(Long id);
    ApkSoftwarePackage getApkSoftwarePackageByName(@Param("versionName")String versionName);
    List<ApkSoftwarePackage> findSoftwarePackageBySoftCode(@Param("softwareCodeId")Long softwareCodeId);
    int updateByPrimaryKey(ApkSoftwarePackage record);
    int getCountByBySoftCodeNameAndVersionName(@Param("versionName")String versionName,@Param("softCodeName")String softCodeName,@Param("packageType")String packageType,@Param("softCodeId")Long softCodeId);
    List<ApkSoftwarePackage> findListBySoftCodeNameAndVersionName(@Param("versionName")String versionName,@Param("softCodeName")String softCodeName,@Param("packageType")String packageType,@Param("softCodeId")Long softCodeId,@Param("pageNo")Integer pageNo, @Param("pageSize")Integer pageSize);
    List<ApkSoftwarePackage> getSoftwarePackageListBySoftCodeIdAndVersionSeq(@Param("softwareCodeId")Long softwareCodeId, @Param("deviceVersionSeq")Integer deviceVersionSeq);
}