package com.ysten.local.bss.web.service.impl;

import com.ysten.local.bss.device.domain.City;
import com.ysten.local.bss.device.repository.ICityRepository;
import com.ysten.local.bss.system.vo.Tree;
import com.ysten.local.bss.system.vo.TreeNode;
import com.ysten.local.bss.web.service.ICityWebService;
import com.ysten.utils.page.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CityWebServiceImpl implements ICityWebService {

    @Autowired
    private ICityRepository cityRepository;
    @Override
    public boolean saveCity(City city) {
        return this.cityRepository.saveCity(city);
    }

    @Override
    public boolean updateCity(City city) {
        return this.cityRepository.updateCity(city);
    }

    @Override
    public boolean deleteCity(List<Long> ids) {
        return this.cityRepository.deleteCity(ids);
    }

    @Override
    public Pageable<City> findCityList(String name, String code, Integer pageNo, Integer pageSize) {
        return this.cityRepository.findCityList(name, code, pageNo, pageSize);
    }

    @Override
    public City getCityById(Long id) {
        return this.cityRepository.getCityById(id);
    }

    @Override
    public City getCityByDistCode(String districtCode) {
        return this.cityRepository.getCityByDistCode(districtCode);
    }

    @Override
    public List<City> findAllCity() {
        return this.cityRepository.findAllCity();
    }

    @Override
    public List<City> findAllProvince() {
        return this.cityRepository.findAllProvince();
    }

    @Override
    public List<Tree> getCityTree() {
        List<City> cityList = this.cityRepository.findAllCity();
        if (cityList == null || cityList.isEmpty()){
        	return null;
        }
        List<Tree> treeList = new ArrayList<Tree>();
        for (City city : cityList) {
        	boolean expanded = (city.getLeaderId() == 1) ? false : true;
            TreeNode treeNode = new TreeNode(city.getId(), city.getLeaderId(), city.getName(), expanded);
            treeList.add(treeNode);
        }
        return treeList;
    }

	@Override
	public List<City> findCityListByLeader(Long leaderId) {
		return this.cityRepository.findAllCityByLeaderId(leaderId);
	}

//    public List<Tree> getCityTree() {
//        List<Tree> treeList = new ArrayList<Tree>();
//        List<City> cityList = this.deviceRepository.findAllCity();
//        for (City city : cityList) {
//            boolean expanded = (city.getLeaderId() == 1) ? false : true;
//            TreeNode treeNode = new TreeNode(city.getId(), city.getLeaderId(), city.getName(), expanded);
//            treeList.add(treeNode);
//        }
//        return treeList;
//    }

}
