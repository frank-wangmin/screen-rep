package com.ysten.local.bss.panel.repository.mapper;

import java.util.List;

import com.ysten.local.bss.panel.domain.LvomsDistrictCodeUrl;
import org.apache.ibatis.annotations.Param;

public interface LvomsDistrictCodeUrlMapper {
	
	int deleteByPrimaryKey(Long id);
	
	void deleteByIds(@Param("ids")List<Long> ids);

	int insert(LvomsDistrictCodeUrl record);

	int insertSelective(LvomsDistrictCodeUrl record);

	LvomsDistrictCodeUrl selectByPrimaryKey(Long id);

	LvomsDistrictCodeUrl selectByDistrictCode(@Param("districtCode")String districtCode,@Param("packageId")Long packageId);

	int updateByPrimaryKeySelective(LvomsDistrictCodeUrl record);

	int updateByPrimaryKey(LvomsDistrictCodeUrl record);
	
	List<LvomsDistrictCodeUrl> findListByConditions(@Param("distCode")String distCode,@Param("packageId")Long packageId,@Param("status")String status, @Param("index") int index, @Param("size") int size);
	
	int getCountByConditions(@Param("distCode")String distCode,@Param("packageId")Long packageId,@Param("status")String status);
}