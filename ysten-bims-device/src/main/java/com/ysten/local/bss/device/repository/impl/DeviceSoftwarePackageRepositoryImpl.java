package com.ysten.local.bss.device.repository.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.ysten.local.bss.device.bean.DeviceUpgradeCondition;
import com.ysten.local.bss.device.domain.DeviceSoftwarePackage;
import com.ysten.local.bss.device.repository.IDeviceSoftwarePackageRepository;
import com.ysten.local.bss.device.repository.mapper.DeviceSoftwarePackageMapper;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import com.ysten.utils.page.Pageable;

@Repository
public class DeviceSoftwarePackageRepositoryImpl implements IDeviceSoftwarePackageRepository {

	@Autowired
	private DeviceSoftwarePackageMapper deviceSoftwareMapper;

	@Override
	public Boolean insertOrUpdate(DeviceSoftwarePackage deviceSoftwarePackage) {
		if(null == deviceSoftwarePackage){
			return false;
		}
		if(null == deviceSoftwarePackage.getId()){
			deviceSoftwareMapper.insert(deviceSoftwarePackage);
			return true;
		}
		deviceSoftwareMapper.updateByPrimaryKey(deviceSoftwarePackage);
		return true;
	}

    @Override
    public Boolean insertSynchronization(DeviceSoftwarePackage deviceSoftwarePackage){
        return 1 == deviceSoftwareMapper.insertSynchronization(deviceSoftwarePackage);
    }

    @Override
    public Boolean updateSoftwarePackage(DeviceSoftwarePackage deviceSoftwarePackage){
        return 1 == deviceSoftwareMapper.updateByPrimaryKey(deviceSoftwarePackage);
    }

    @Override
	public Boolean deleteByPrimaryKey(Long softwareId) {

		deviceSoftwareMapper.deleteByPrimaryKey(softwareId);
		 return true;
	}

	@Override
	public DeviceSoftwarePackage selectByPrimaryKey(Long softwareId) {

		return deviceSoftwareMapper.selectByPrimaryKey(softwareId);
	}

	@Override
	public Pageable<DeviceSoftwarePackage> findEpgDeviceSoftwaresByCondition(
			DeviceUpgradeCondition condition) {


		List<DeviceSoftwarePackage> rows = this.deviceSoftwareMapper.findDeviceSoftwaresByCondition(condition);
		if(condition.getPageNo() == null || condition.getPageSize() == null || rows.isEmpty()){
			return new Pageable<DeviceSoftwarePackage>().instanceByPageNo(rows, rows.size(), condition.getPageNo(), condition.getPageSize());
		}
		int count = this.deviceSoftwareMapper.countDeviceSoftwaresByCondition(condition);
		return new Pageable<DeviceSoftwarePackage>().instanceByPageNo(rows, count, condition.getPageNo(), condition.getPageSize());
	}

	@Override
	public List<DeviceSoftwarePackage> findEpgDeviceSoftwaresByFullIds(
			List<Long> fullIds) {
		if(CollectionUtils.isEmpty(fullIds)){
			return Collections.emptyList();
		}
		return deviceSoftwareMapper.findDeviceSoftwaresByFullIds(fullIds);
	}

	@Override
	public List<DeviceSoftwarePackage> findbyCondition(List<Long> softwareIdList,
			Long softCodeId, Integer deviceVersionSeq, EnumConstantsInterface.PackageType packageType) {
		
		return deviceSoftwareMapper.findbyCondition(softwareIdList, softCodeId, deviceVersionSeq, packageType);
	}

	@Override
	public Pageable<DeviceSoftwarePackage> getListByCondition(String versionName,Integer pageNo,Integer pageSize) {
		List<DeviceSoftwarePackage> page=this.deviceSoftwareMapper.getListByCondition(versionName, pageNo, pageSize); 
		 int total = this.deviceSoftwareMapper.getCountByCondition(versionName);
		 return new Pageable<DeviceSoftwarePackage>().instanceByPageNo(page, total, pageNo, pageSize);
	}

	@Override
	public boolean deleteByIds(List<Long> ids) {
		return ids.size() == this.deviceSoftwareMapper.delete(ids);
	}

	@Override
	public boolean insert(DeviceSoftwarePackage deviceSoftwarePackage) {
		return 1 == this.deviceSoftwareMapper.insert(deviceSoftwarePackage);
	}

	@Override
	public boolean updateById(DeviceSoftwarePackage DeviceSoftwarePackage) {
		return 1 == this.deviceSoftwareMapper.updateByPrimaryKey(DeviceSoftwarePackage);
	}

	@Override
	public List<DeviceSoftwarePackage> getAll() {
		return this.deviceSoftwareMapper.getAll();
	}

	@Override
	public DeviceSoftwarePackage getSoftwarePackageByName(String versionName) {
		return this.deviceSoftwareMapper.getSoftwarePackageByName(versionName);
	}

	@Override
	public List<DeviceSoftwarePackage> getSoftwarePackageBySoftCode(Long softwareCodeId) {
		return this.deviceSoftwareMapper.getSoftwarePackageBySoftCode(softwareCodeId);
	}

	@Override
	public boolean updateDistributeStateById(Long id, String distributeState) {
		return 1 == this.deviceSoftwareMapper.updateDistributeStateById(id, distributeState);
	}

}
