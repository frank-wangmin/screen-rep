package com.ysten.local.bss.device.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysten.local.bss.device.bean.DeviceUpgradeCondition;
import com.ysten.local.bss.device.domain.DeviceSoftwareCode;
import com.ysten.local.bss.device.repository.IDeviceSoftwareCodeRepository;
import com.ysten.local.bss.device.repository.mapper.DeviceSoftwareCodeMapper;
import com.ysten.utils.page.Pageable;

@Repository
public class DeviceSoftwareCodeRepositoryImpl implements IDeviceSoftwareCodeRepository {
	@Autowired
	private DeviceSoftwareCodeMapper deviceSoftwareCodeMapper;

	@Override
	public Boolean insertOrUpdate(DeviceSoftwareCode deviceSoftwareCode) {
		if(null == deviceSoftwareCode){
			return false;
		}
		if(null == deviceSoftwareCode.getId()){
			deviceSoftwareCodeMapper.insert(deviceSoftwareCode);
			return true;
		}
		deviceSoftwareCodeMapper.updateByPrimaryKey(deviceSoftwareCode);
		return true;
	}

	@Override
	public Boolean deleteByPrimaryKey(Long softCodeId) {
		deviceSoftwareCodeMapper.deleteByPrimaryKey(softCodeId);
		return true;
	}

	@Override
	public DeviceSoftwareCode selectByPrimaryKey(Long softCodeId) {
		
		return deviceSoftwareCodeMapper.selectByPrimaryKey(softCodeId);
	}

	@Override
	public Pageable<DeviceSoftwareCode> findEpgSoftwareCodesByCondition(
			DeviceUpgradeCondition condition) {
		List<DeviceSoftwareCode> rows = this.deviceSoftwareCodeMapper.findEpgSoftwareCodesByCondition(condition);
		if(condition.getPageNo() == null || condition.getPageSize() == null || rows.isEmpty()){
			return new Pageable<DeviceSoftwareCode>().instanceByPageNo(rows, rows.size(), condition.getPageNo(), condition.getPageSize());
		}
		int count = this.deviceSoftwareCodeMapper.countEpgSoftwareCodesByCondition(condition);
		return new Pageable<DeviceSoftwareCode>().instanceByPageNo(rows, count, condition.getPageNo(), condition.getPageSize());
	}

	@Override
	public DeviceSoftwareCode selectByCode(String code) {
		
		return deviceSoftwareCodeMapper.selectByCode(code);
	}

	@Override
	public boolean deleteByIds(List<Long> ids) {
		return  ids.size() == this.deviceSoftwareCodeMapper.delete(ids);
	}

	@Override
	public boolean insert(DeviceSoftwareCode deviceSoftwareCode) {
		return 1 == this.deviceSoftwareCodeMapper.insert(deviceSoftwareCode);
	}

	@Override
	public boolean update(DeviceSoftwareCode deviceSoftwareCode) {
		return 1 == this.deviceSoftwareCodeMapper.updateByPrimaryKey(deviceSoftwareCode);
	}

	@Override
	public List<DeviceSoftwareCode> getAll() {
		return this.deviceSoftwareCodeMapper.getAll();
	}

    @Override
    public Boolean insertSynchronization(DeviceSoftwareCode deviceSoftwareCode) {
        return 1 == deviceSoftwareCodeMapper.insertSynchronization(deviceSoftwareCode);
    }

	@Override
	public DeviceSoftwareCode findSoftwareCodesByName(String name) {
		return this.deviceSoftwareCodeMapper.findEpgSoftwareCodesByName(name);
	}

	@Override
	public boolean updateDistributeStateById(Long id, String distributeState) {
		return 1 == this.deviceSoftwareCodeMapper.updateDistributeStateById(id, distributeState);
	}
}
