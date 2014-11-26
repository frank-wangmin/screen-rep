package com.ysten.local.bss.web.service;

import java.util.List;

import com.ysten.local.bss.panel.domain.LvomsDistrictCodeUrl;
import com.ysten.utils.page.Pageable;

public interface ILvomsDistrictCodeUrlService {
	
	Pageable<LvomsDistrictCodeUrl> findListByConditions(String districtCode,Long packageId,String status,int pageNo, int pageSize);

	boolean saveLvomsUrl(LvomsDistrictCodeUrl lvomsDistrictCodeUrl);
	
	LvomsDistrictCodeUrl getLvomsDistrictCodeUrlById(Long id);
	
	boolean updateLvomsUrl(LvomsDistrictCodeUrl lvomsDistrictCodeUrl);
	
	boolean deleteLvomsUrl(List<Long> ids);
	
	LvomsDistrictCodeUrl getLvomsUrlByDistCodeAndPackageId(String districtCode,Long packageId);
}
