package com.ysten.local.bss.device.repository.impl;

import com.ysten.local.bss.device.domain.ApkSoftwarePackage;
import com.ysten.local.bss.device.domain.AppSoftwarePackage;
import com.ysten.local.bss.device.repository.IApkSoftwarePackageRepository;
import com.ysten.local.bss.device.repository.mapper.ApkSoftwarePackageMapper;
import com.ysten.utils.page.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ApkSoftwarePackageRepositoryImpl implements IApkSoftwarePackageRepository {
    @Autowired
    private ApkSoftwarePackageMapper apkSoftwarePackageMapper;

    @Override
    public List<ApkSoftwarePackage> getSoftwarePackageListBySoftCodeIdAndVersionSeq(long softwareCodeId, Integer deviceVersionSeq) {
        return this.apkSoftwarePackageMapper.getSoftwarePackageListBySoftCodeIdAndVersionSeq(softwareCodeId, deviceVersionSeq);
    }

	@Override
	public Pageable<ApkSoftwarePackage> findListByNameAndCode(
			String versionName, String softCodeName,String packageType,Long softCodeId, Integer pageNo,
			Integer pageSize) {
		List<ApkSoftwarePackage> page = this.apkSoftwarePackageMapper.findListBySoftCodeNameAndVersionName(versionName, softCodeName, packageType, softCodeId, pageNo, pageSize);
		int total = this.apkSoftwarePackageMapper.getCountByBySoftCodeNameAndVersionName(versionName, softCodeName, packageType, softCodeId);
		return new Pageable<ApkSoftwarePackage>().instanceByPageNo(page, total, pageNo, pageSize);
	}

	@Override
	public boolean insert(ApkSoftwarePackage apkSoftwarePackage) {
		return 1 == this.apkSoftwarePackageMapper.insert(apkSoftwarePackage);
	}

	@Override
	public ApkSoftwarePackage selectByPrimaryKey(Long softwareId) {
		return this.apkSoftwarePackageMapper.selectByPrimaryKey(softwareId);
	}

	@Override
	public boolean updateById(ApkSoftwarePackage apkSoftwarePackage) {
		return 1 == this.apkSoftwarePackageMapper.updateByPrimaryKey(apkSoftwarePackage);
	}

	@Override
	public List<ApkSoftwarePackage> findSoftwarePackageBySoftCode(
			Long softwareCodeId) {
		return this.apkSoftwarePackageMapper.findSoftwarePackageBySoftCode(softwareCodeId);
	}

	@Override
	public ApkSoftwarePackage getApkSoftwarePackByName(String versionName) {
		return this.apkSoftwarePackageMapper.getApkSoftwarePackageByName(versionName);
	}
}
