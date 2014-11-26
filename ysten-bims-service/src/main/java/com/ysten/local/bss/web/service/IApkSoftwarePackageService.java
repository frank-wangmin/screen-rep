package com.ysten.local.bss.web.service;

import java.util.List;

import com.ysten.local.bss.device.domain.ApkSoftwarePackage;
import com.ysten.utils.page.Pageable;

public interface IApkSoftwarePackageService {
	Pageable<ApkSoftwarePackage> findListByNameAndCode(String versionName,String softCodeName,String packageType,Long softCodeId,Integer pageNo,Integer pageSize);
	boolean insert(ApkSoftwarePackage apkSoftwarePackage);
	ApkSoftwarePackage selectByPrimaryKey(Long softwareId);
	ApkSoftwarePackage getApkSoftwarePackByName(String versionName);
	boolean updateById(ApkSoftwarePackage apkSoftwarePackage);
	List<ApkSoftwarePackage> findSoftwarePackageBySoftCode(Long softwareCodeId);
}
