package com.ysten.local.bss.web.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.device.domain.City;
import com.ysten.local.bss.system.vo.Tree;
import com.ysten.utils.page.Pageable;


public interface ICityWebService {
    /**
     * 通过id查询
     * @return
     */
    City getCityById(Long id);
    /**
     * 通过districtCode查询
     * @return
     */
    City getCityByDistCode(String districtCode);
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
    List<City>findAllCity();
    
    List<City>findCityListByLeader(Long leaderId);

    List<City> findAllProvince();
    /**
     * 查询所有地市信息，用以树形列表显示
     * @return
     */
	List<Tree> getCityTree();
}
