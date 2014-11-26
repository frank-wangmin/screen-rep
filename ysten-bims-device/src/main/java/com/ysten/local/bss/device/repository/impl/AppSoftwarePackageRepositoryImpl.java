package com.ysten.local.bss.device.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysten.local.bss.device.bean.DeviceUpgradeCondition;
import com.ysten.local.bss.device.domain.AppSoftwarePackage;
import com.ysten.local.bss.device.repository.IAppSoftwarePackageRepository;
import com.ysten.local.bss.device.repository.mapper.AppSoftwarePackageMapper;
import com.ysten.utils.page.Pageable;

@Repository
public class AppSoftwarePackageRepositoryImpl implements IAppSoftwarePackageRepository {
	
	@Autowired
	private AppSoftwarePackageMapper appSoftwarePackageMapper;
	@Override
	public Pageable<AppSoftwarePackage> findAppSoftwarePackageListByCondition(String versionName,Integer pageNo, Integer pageSize) {
		List<AppSoftwarePackage> page = this.appSoftwarePackageMapper.getListByCondition(versionName, pageNo, pageSize);
		int total = this.appSoftwarePackageMapper.getCountByCondition(versionName);
		return new Pageable<AppSoftwarePackage>().instanceByPageNo(page, total, pageNo, pageSize);
	}
	
	@Override
	public AppSoftwarePackage getAppSoftwarePackageById(Long id){
		return this.appSoftwarePackageMapper.getById(id);
	}

	@Override
	public Pageable<AppSoftwarePackage> findAppSoftwaresByCondition(DeviceUpgradeCondition condition) {
		List<AppSoftwarePackage> page = this.appSoftwarePackageMapper.findAppSoftwaresByCondition(condition);
		int total = this.appSoftwarePackageMapper.countAppSoftwaresByCondition(condition);
		return new Pageable<AppSoftwarePackage>().instanceByPageNo(page, total, condition.getPageNo(), condition.getPageSize());
	}

	@Override
	public boolean deleteAppSoftwarePackageByIds(List<Long> ids) {
		return ids.size() == this.appSoftwarePackageMapper.delete(ids);
	}

	@Override
	public boolean saveAppSoftwarePackage(AppSoftwarePackage appSoftwarePackage) {
		return 1 == this.appSoftwarePackageMapper.save(appSoftwarePackage);
	}

	@Override
	public boolean updateAppSoftwarePackage(AppSoftwarePackage appSoftwarePackage) {
		return 1 == this.appSoftwarePackageMapper.update(appSoftwarePackage);
	}

	@Override
	public List<AppSoftwarePackage> findAllAppSoftwarePackage() {
		return this.appSoftwarePackageMapper.findAll();
	}

	@Override
	public AppSoftwarePackage getAppSoftwarePackageByName(String versionName) {
		return this.appSoftwarePackageMapper.getAppSoftwarePackageByName(versionName);
	}

	@Override
	public List<AppSoftwarePackage> findSoftwarePackageBySoftCode(Long softwareCodeId) {
		return this.appSoftwarePackageMapper.getSoftwarePackageBySoftCode(softwareCodeId);
	}

	@Override
	public boolean updateDistributeStateById(Long id, String distributeState) {
		return 1 == this.appSoftwarePackageMapper.updateDistributeStateById(id, distributeState);
	}
}
