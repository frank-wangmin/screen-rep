package com.ysten.local.bss.panel.repository;

import java.util.List;

import com.ysten.local.bss.panel.domain.LvomsDistrictCodeUrl;
import com.ysten.utils.page.Pageable;

/**
 * @author cwang
 * @version 2014年10月22日 下午4:55:26
 * 
 */
public interface ILvomsDistrictCodeUrlRepository {
	LvomsDistrictCodeUrl selectByDistrictCode(String districtCode,Long packageId);
	Pageable<LvomsDistrictCodeUrl> findListByConditions(String districtCode,Long packageId,String status,int pageNo, int pageSize);
	boolean saveLvomsUrl(LvomsDistrictCodeUrl lvomsDistrictCodeUrl);
	boolean updateLvomsUrl(LvomsDistrictCodeUrl lvomsDistrictCodeUrl);
	LvomsDistrictCodeUrl getLvomsDistrictCodeUrlById(Long id);
	void deleteLvomsDistrictCodeUrlListByIds(List<Long> ids);
}
