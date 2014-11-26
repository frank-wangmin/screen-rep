package com.ysten.local.bss.device.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.device.domain.City;

/**
 * 
 * 类名称：CustomerMapper
 * 
 * @version
 */
public interface CityMapper {

    /**
     * 通过编号查询
     * @param code
     * @return
     */
	City queryByCode(String code);
	/**
	 * 通过名称查询
	 * @param name
	 * @return
	 */
	City queryByName(@Param("name")String name);
	/**
	 * 通过id查询
	 * @return
	 */
	City getById(Long id);
	List<City> findCitysByIds(@Param("ids")List<Long> ids);
    /**
     * 通过districtCode查询
     * @return
     */
    City getByDistrictCode(String districtCode);
	/**
	 * 保存地市信息
	 * @param city
	 * @return
	 */
	int save(City city);
	/**
	 * 修改地市信息
	 * @param city
	 * @return
	 */
	int update(City city);
	/**
	 * 删除地市信息
	 * @param ids
	 * @return
	 */
	int delete(@Param("ids")List<Long> ids);
	/**
	 * 分页获取地市信息列表
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	List<City> findCityList(@Param("name") String name,@Param("code") String code,@Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);
	/**
	 * 获取总数
	 * @return
	 */
	int getCount(@Param("name") String name,@Param("code") String code);
	/**
	 * 查询所有地市信息
	 * @return
	 */
	List<City> findAllCity();

    List<City> findAllProvince();

	/**
	 * 根据leaderId查询所有地市信息
	 * @param leaderId
	 * @return
	 */
	List<City> findAllCityByLeaderId(@Param("leaderId")Long leaderId);
    /**
     * 
     * @param userAreaID
     * @return
     */
    List<Long> getLeadIdsByUserAreaId(@Param("code")String userAreaID);

    Long getAreaIdsByDistrictCode(@Param("districtCode")String districtCode);

}