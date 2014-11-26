package com.ysten.local.bss.device.repository;

import com.ysten.area.domain.Area;
import com.ysten.local.bss.device.domain.City;
import com.ysten.local.bss.device.domain.Device;
import com.ysten.utils.page.Pageable;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by hujing on 14-8-13.
 */
public interface ICityRepository {
    City getCityFromDevice(Device device);

    /**
     * 根据地市编号查询地市对象
     * @param cityCode
     * @return
     */
    City getCityByCityCode(String cityCode);

    /**
     * 根据行政区划码查询地市对象
     * @param districtCode
     * @return
     */
    City getCityByDistCode(String districtCode);

    /**
     * 通过id查询
     * @return
     */
    City getCityById(Long id);
    /**
     * 保存地市信息
     * @param city
     * @return
     */
    boolean saveCity(City city);
    /**
     * 修改地市信息
     * @param city
     * @return
     */
    boolean updateCity(City city);
    /**
     * 删除地市信息
     * @param ids
     * @return
     */
    boolean deleteCity(@Param("ids")List<Long> ids);
    /**
     * 分页获取地市信息列表
     * @param pageNo
     * @param pageSize
     * @return
     */
    Pageable<City> findCityList(String name,String code,Integer pageNo,Integer pageSize);
    /**
     * 查询所有地市信息
     * @return
     */
    List<City> findAllCity();
    
    List<City> findAllProvince();

    /**
     * 通过city名称获取city全部信息
     *
     * @param cityName
     * @return
     */
    City getCityByName(String cityName);

    Long getAreaIdByDistrictCode(String districtCode);

    List<City> findAllCityByLeaderId(Long leaderId);

}
