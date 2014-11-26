package com.ysten.local.bss.web.service.impl;

import java.util.List;

import com.ysten.local.bss.device.domain.ApkSoftwareCode;
import com.ysten.local.bss.device.repository.IApkSoftwareCodeRepository;
import com.ysten.local.bss.web.service.IApkSoftwareCodeService;
import com.ysten.utils.page.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApkSoftwareCodeServiceImpl implements IApkSoftwareCodeService {
	@Autowired
	private  IApkSoftwareCodeRepository apkSoftwareCodeRepository;
	@Override
	public Pageable<ApkSoftwareCode> findListByNameAndCode(String code,
			String name, Integer pageNo, Integer pageSize) {
		return this.apkSoftwareCodeRepository.findListByNameAndCode(code, name, pageNo, pageSize);
	}
	@Override
	public boolean saveApkSoftwareCode(ApkSoftwareCode apkSoftwareCode) {
		return this.apkSoftwareCodeRepository.saveApkSoftwareCode(apkSoftwareCode);
	}
	@Override
	public boolean updateApkSoftwareCode(ApkSoftwareCode apkSoftwareCode) {
		return this.apkSoftwareCodeRepository.updateApkSoftwareCode(apkSoftwareCode);
	}
	@Override
	public ApkSoftwareCode selectByPrimaryKey(Long softCodeId) {
		return this.apkSoftwareCodeRepository.selectByPrimaryKey(softCodeId);
	}
	@Override
	public ApkSoftwareCode getSoftwareCodesByCodeAndName(String code,
			String name) {
		return this.apkSoftwareCodeRepository.getSoftwareCodesByCodeAndName(code, name);
	}
	@Override
	public List<ApkSoftwareCode> findAllOfUseble() {
		return this.apkSoftwareCodeRepository.findAllOfUseble();
	}

  
}
