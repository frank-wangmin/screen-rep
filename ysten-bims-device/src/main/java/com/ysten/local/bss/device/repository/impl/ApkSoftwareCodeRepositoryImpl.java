package com.ysten.local.bss.device.repository.impl;

import java.util.List;

import com.ysten.local.bss.device.domain.ApkSoftwareCode;
import com.ysten.local.bss.device.repository.IApkSoftwareCodeRepository;
import com.ysten.local.bss.device.repository.mapper.ApkSoftwareCodeMapper;
import com.ysten.utils.page.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ApkSoftwareCodeRepositoryImpl implements IApkSoftwareCodeRepository {
	@Autowired
	private ApkSoftwareCodeMapper apkSoftwareCodeMapper;

	@Override
	public Pageable<ApkSoftwareCode> findListByNameAndCode(String code,
			String name, Integer pageNo, Integer pageSize) {
		List<ApkSoftwareCode> datas = this.apkSoftwareCodeMapper.findListByNameAndCode(code, name, pageNo, pageSize);
		int total = this.apkSoftwareCodeMapper.getCountByNameAndCode(code, name);
		return new Pageable<ApkSoftwareCode>().instanceByPageNo(datas, total, pageNo, pageSize);
	}

	@Override
	public boolean saveApkSoftwareCode(ApkSoftwareCode apkSoftwareCode) {
		return 1 == this.apkSoftwareCodeMapper.insert(apkSoftwareCode);
	}

	@Override
	public boolean updateApkSoftwareCode(ApkSoftwareCode apkSoftwareCode) {
		return 1 == this.apkSoftwareCodeMapper.updateByPrimaryKey(apkSoftwareCode);
	}

	@Override
	public ApkSoftwareCode selectByPrimaryKey(Long softCodeId) {
		return this.apkSoftwareCodeMapper.selectByPrimaryKey(softCodeId);
	}

	@Override
	public ApkSoftwareCode getSoftwareCodesByCodeAndName(String code,
			String name) {
		return this.apkSoftwareCodeMapper.getSoftwareCodesByCodeAndName(code, name);
	}

    @Override
    public ApkSoftwareCode findApkSoftwareCodeByCode(String code) {
        return this.apkSoftwareCodeMapper.selectByCode(code);
    }

	@Override
	public List<ApkSoftwareCode> findAllOfUseble() {
		return this.apkSoftwareCodeMapper.findAllOfUseble();
	}
}
