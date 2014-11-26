package com.ysten.local.bss.device.repository.impl;

import com.ysten.area.domain.Area;
import com.ysten.area.repository.mapper.AreaMapper;
import com.ysten.local.bss.device.domain.City;
import com.ysten.local.bss.device.domain.Device;
import com.ysten.local.bss.device.repository.ICityRepository;
import com.ysten.local.bss.device.repository.mapper.CityMapper;
import com.ysten.utils.page.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hujing on 14-8-13.
 */
@Repository
public class CityRepositoryImpl implements ICityRepository {
    @Autowired
    private CityMapper cityMapper;

    @Override
    public City getCityFromDevice(Device device) {
        return cityMapper.queryByCode(device.getCity().getCode());
    }

    @Override
    public City getCityByCityCode(String cityCode) {
        return cityMapper.queryByCode(cityCode);
    }

    @Override
    public City getCityById(Long id) {
        return this.cityMapper.getById(id);
    }

    @Override
    public City getCityByDistCode(String districtCode) {
        return this.cityMapper.getByDistrictCode(districtCode);
    }

    @Override
    public boolean saveCity(City city) {
        return 1 == this.cityMapper.save(city);
    }

    @Override
    public boolean updateCity(City city) {
        return 1 == this.cityMapper.update(city);
    }

    @Override
    public boolean deleteCity(List<Long> ids) {
        return 1 == this.cityMapper.delete(ids);
    }

    @Override
    public Pageable<City> findCityList(String name, String code, Integer pageNo, Integer pageSize) {
        List<City> cityList = this.cityMapper.findCityList(name, code, pageNo, pageSize);
        int total = this.cityMapper.getCount(name, code);
        return new Pageable<City>().instanceByPageNo(cityList, total, pageNo, pageSize);
    }

    @Override
    public List<City> findAllCity() {
        return this.cityMapper.findAllCity();
    }

    @Override
    public List<City> findAllProvince() {
        return this.cityMapper.findAllProvince();
    }

    @Override
    public List<City> findAllCityByLeaderId(Long leaderId) {
        return this.cityMapper.findAllCityByLeaderId(leaderId);
    }

    @Override
    public City getCityByName(String cityName) {
        return this.cityMapper.queryByName(cityName);
    }

    @Override
    public Long getAreaIdByDistrictCode(String districtCode) {
       return this.cityMapper.getAreaIdsByDistrictCode(districtCode);

    }

}
