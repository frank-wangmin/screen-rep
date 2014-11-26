package com.ysten.local.bss.web.service.impl;

import java.util.List;

import com.ysten.local.bss.panel.domain.LvomsDistrictCodeUrl;
import com.ysten.local.bss.panel.repository.ILvomsDistrictCodeUrlRepository;
import com.ysten.local.bss.web.service.ILvomsDistrictCodeUrlService;
import com.ysten.utils.page.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LvomsDistrictCodeUrlServiceImpl implements ILvomsDistrictCodeUrlService {
	@Autowired
	private  ILvomsDistrictCodeUrlRepository lvomsDistrictCodeUrlRepository;

	@Override
	public Pageable<LvomsDistrictCodeUrl> findListByConditions(
			String districtCode, Long packageId, String status, int pageNo,
			int pageSize) {
		return this.lvomsDistrictCodeUrlRepository.findListByConditions(districtCode, packageId, status, pageNo, pageSize);
	}

	@Override
	public boolean saveLvomsUrl(LvomsDistrictCodeUrl lvomsDistrictCodeUrl) {
		return this.lvomsDistrictCodeUrlRepository.saveLvomsUrl(lvomsDistrictCodeUrl);
	}

	@Override
	public LvomsDistrictCodeUrl getLvomsDistrictCodeUrlById(Long id) {
		return this.lvomsDistrictCodeUrlRepository.getLvomsDistrictCodeUrlById(id);
	}

	@Override
	public boolean updateLvomsUrl(LvomsDistrictCodeUrl lvomsDistrictCodeUrl) {
		return this.lvomsDistrictCodeUrlRepository.updateLvomsUrl(lvomsDistrictCodeUrl);
	}

	@Override
	public boolean deleteLvomsUrl(List<Long> ids) {
		this.lvomsDistrictCodeUrlRepository.deleteLvomsDistrictCodeUrlListByIds(ids);
		return true;
	}

	@Override
	public LvomsDistrictCodeUrl getLvomsUrlByDistCodeAndPackageId(
			String districtCode, Long packageId) {
		return this.lvomsDistrictCodeUrlRepository.selectByDistrictCode(districtCode, packageId);
	}
  
}
