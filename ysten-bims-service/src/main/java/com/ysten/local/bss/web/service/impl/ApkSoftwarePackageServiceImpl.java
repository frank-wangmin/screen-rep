package com.ysten.local.bss.web.service.impl;

import java.util.List;

import com.ysten.local.bss.device.domain.ApkSoftwarePackage;
import com.ysten.local.bss.device.repository.IApkSoftwarePackageRepository;
import com.ysten.local.bss.web.service.IApkSoftwarePackageService;
import com.ysten.utils.page.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ApkSoftwarePackageServiceImpl implements IApkSoftwarePackageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApkSoftwarePackageServiceImpl.class);
    @Autowired
    private IApkSoftwarePackageRepository apkSoftwarePackageRepository;
	@Override
	public Pageable<ApkSoftwarePackage> findListByNameAndCode(
			String versionName, String softCodeName,String packageType,Long softCodeId, Integer pageNo,
			Integer pageSize) {
		return this.apkSoftwarePackageRepository.findListByNameAndCode(versionName, softCodeName, packageType,softCodeId,pageNo, pageSize);
	}
	@Override
	public boolean insert(ApkSoftwarePackage apkSoftwarePackage) {
		return this.apkSoftwarePackageRepository.insert(apkSoftwarePackage);
	}
	@Override
	public ApkSoftwarePackage selectByPrimaryKey(Long softwareId) {
		return this.apkSoftwarePackageRepository.selectByPrimaryKey(softwareId);
	}
	@Override
	public boolean updateById(ApkSoftwarePackage apkSoftwarePackage) {
		return this.apkSoftwarePackageRepository.updateById(apkSoftwarePackage);
	}
	@Override
	public List<ApkSoftwarePackage> findSoftwarePackageBySoftCode(
			Long softwareCodeId) {
		return this.apkSoftwarePackageRepository.findSoftwarePackageBySoftCode(softwareCodeId);
	}
	@Override
	public ApkSoftwarePackage getApkSoftwarePackByName(String versionName) {
		return this.apkSoftwarePackageRepository.getApkSoftwarePackByName(versionName);
	}

}
