package com.ysten.local.bss.device.repository.impl;

import java.util.List;

import com.ysten.local.bss.device.bean.DeviceUpgradeCondition;
import com.ysten.local.bss.device.domain.AppSoftwareCode;
import com.ysten.local.bss.device.repository.IAppSoftwareCodeRepository;
import com.ysten.local.bss.device.repository.mapper.AppSoftwareCodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysten.utils.page.Pageable;

@Repository
public class AppSoftwareCodeRepositoryImpl implements IAppSoftwareCodeRepository {
	
	@Autowired
	private AppSoftwareCodeMapper appSoftwareCodeMapper;
		
	@Override
	public Pageable<AppSoftwareCode> findAppSoftwareCodesByCondition(
			DeviceUpgradeCondition condition) {
		List<AppSoftwareCode> rows = this.appSoftwareCodeMapper.findAppSoftwareCodesByCondition(condition);
		if(condition.getPageNo() == null || condition.getPageSize() == null || rows.isEmpty()){
			return new Pageable<AppSoftwareCode>().instanceByPageNo(rows, rows.size(), condition.getPageNo(), condition.getPageSize());
		}
		int count = this.appSoftwareCodeMapper.countAppSoftwareCodesByCondition(condition);
		return new Pageable<AppSoftwareCode>().instanceByPageNo(rows, count, condition.getPageNo(), condition.getPageSize());
	}
	
	@Override
	public AppSoftwareCode getAppSoftwareCodeBySoftCodeId(Long softCodeId) {		
		return this.appSoftwareCodeMapper.getById(softCodeId);
	}
	
	@Override
	public boolean deleteAppSoftwareCodeByIds(List<Long> ids) {
		return  ids.size() == this.appSoftwareCodeMapper.delete(ids);
	}
	
	@Override
	public boolean saveAppSoftwareCode(AppSoftwareCode appSoftwareCode) {
			return 1 == this.appSoftwareCodeMapper.save(appSoftwareCode);
	}

	@Override
	public boolean updateAppSoftwareCode(AppSoftwareCode appSoftwareCode) {
		return 1 == this.appSoftwareCodeMapper.updateByPrimaryKey(appSoftwareCode);
	}

	@Override
	public List<AppSoftwareCode> findAllAppSoftwareCode() {
		return this.appSoftwareCodeMapper.findAll();
	}
	
	@Override
	public AppSoftwareCode getAppSoftwareCodeByCode(String code){
		return this.appSoftwareCodeMapper.getAppSoftwareCodeByCode(code);
	}

	@Override
	public AppSoftwareCode findSoftwareCodesByName(String name) {
		return this.appSoftwareCodeMapper.findSoftwareCodeByName(name);
	}

	@Override
	public AppSoftwareCode getBySoftwareCode(String code) {
		return this.appSoftwareCodeMapper.getAppSoftwareCodeByCode(code);
	}

	@Override
	public boolean updateDistributeStateById(Long id, String distributeState) {
		return 1 == this.appSoftwareCodeMapper.updateDistributeStateById(id, distributeState);
	}
}
