package com.ysten.local.bss.panel.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysten.local.bss.device.domain.City;
import com.ysten.local.bss.device.repository.mapper.CityMapper;
import com.ysten.local.bss.panel.domain.LvomsDistrictCodeUrl;
import com.ysten.local.bss.panel.domain.Navigation;
import com.ysten.local.bss.panel.repository.ILvomsDistrictCodeUrlRepository;
import com.ysten.local.bss.panel.repository.mapper.LvomsDistrictCodeUrlMapper;
import com.ysten.utils.page.Pageable;

/**
 * @author cwang
 * @version 2014年10月22日 下午4:56:12
 * 
 */
@Repository
public class LvomsDistrictCodeUrlRepositoryImpl implements ILvomsDistrictCodeUrlRepository {
	@Autowired
	private LvomsDistrictCodeUrlMapper LvomsDistrictCodeUrlMapper;
	@Autowired
    private CityMapper cityMapper;
	@Override
	public LvomsDistrictCodeUrl selectByDistrictCode(String districtCode,Long packageId) {
		return LvomsDistrictCodeUrlMapper.selectByDistrictCode(districtCode,packageId);
	}

	@Override
	public Pageable<LvomsDistrictCodeUrl> findListByConditions(
			String districtCode, Long packageId, String status,int pageNo, int pageSize) {
		List<LvomsDistrictCodeUrl> datas = this.LvomsDistrictCodeUrlMapper.findListByConditions(districtCode, packageId, status,pageNo,pageSize);
		if(datas.size()>0 && datas != null){
			for(LvomsDistrictCodeUrl url:datas){
				if(url.getDistrictCode() != null && url.getDistrictCode() != ""){
					City city = this.cityMapper.getByDistrictCode(url.getDistrictCode());
					url.setDistrictCode(city != null ? city.getName():url.getDistrictCode());
				}
			}
		}
		int total = this.LvomsDistrictCodeUrlMapper.getCountByConditions(districtCode, packageId, status);
        return new Pageable<LvomsDistrictCodeUrl>().instanceByPageNo(datas, total, pageNo, pageSize);
	}

	@Override
	public boolean saveLvomsUrl(LvomsDistrictCodeUrl lvomsDistrictCodeUrl) {
		return 1 == this.LvomsDistrictCodeUrlMapper.insert(lvomsDistrictCodeUrl);
	}

	@Override
	public LvomsDistrictCodeUrl getLvomsDistrictCodeUrlById(Long id) {
		return this.LvomsDistrictCodeUrlMapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean updateLvomsUrl(LvomsDistrictCodeUrl lvomsDistrictCodeUrl) {
		return 1 == this.LvomsDistrictCodeUrlMapper.updateByPrimaryKey(lvomsDistrictCodeUrl);
	}

	@Override
	public void deleteLvomsDistrictCodeUrlListByIds(List<Long> ids) {
		this.LvomsDistrictCodeUrlMapper.deleteByIds(ids);
	}

}
