package com.ysten.local.bss.device.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.device.domain.ServiceCollectDeviceGroupMap;

public interface ServiceCollectDeviceGroupMapMapper {

	void saveServiceCollectDeviceGroupMap(ServiceCollectDeviceGroupMap serviceCollectDeviceGroupMap);

	/**
	 * 根据设备分组Id删除服务集设备分组信息
	 * @param id
	 */
	void deleteServiceCollectGroupmapByGroupId(@Param("groupId")Long id);

	/**
	 * add the relationship between serviceCollect and deviceCode
	 * @param serviceCollectDeviceGroupMap
	 */
	void saveServiceCollectDeviceCodeMap(ServiceCollectDeviceGroupMap serviceCollectDeviceGroupMap);
	
	/**
	 * delete the serviceCollectGroupMap bound with deviceCode or deviceGroup
	 * @param serviceCollectId
	 */
	 void deleteServiceCollectDeviceGroupMap(@Param("serviceCollectId")Long serviceCollectId,@Param("isALL")String isALL);

	 
	/**
	 * Get the deviceCodes bound with service collect by Id
	 * @param id
	 * @return list
	 */
	List<ServiceCollectDeviceGroupMap> getDeviceCodesById(long id);

     List<ServiceCollectDeviceGroupMap> finDeviceCodeServiceCollectMap();
	
	List<ServiceCollectDeviceGroupMap> getServiceCollectGroupmapByGroupId(@Param("groupId")Long groupId);
	
	List<ServiceCollectDeviceGroupMap> getServiceCollectGroupmapByYstenId(@Param("ystenId")String ystenId);
	
	void bulkSaveServiceCollectMap(List<ServiceCollectDeviceGroupMap> list);
	
	void deleteServiceCollectMapByGroupIdsAndYstenIds(@Param("groupIds")List<Long> groupIds, @Param("ystenIds")List<String> ystenIds);
	
	/**
	 * delete device collect group map by deviceCode
	 * @param deviceCode
	 */
	public void deleteServiceCollectGroupmapByDeviceCode(String deviceCode);
	
	/**
	 * delete all the serviceCollectDeviceGroupMaps bounded with deviceGroupId by serviceCollectId
	 * @param serviceCollectId
	 */
	public void deleteServiceCollectGroupBoundGIByServiceCollectId(long serviceCollectId);
	
	/**
	 * delete all the serviceCollectDeviceGroupMaps bounded with deviceCode by serviceCollectId
	 * @param serviceCollectId
	 */
	public void deleteServiceCollectGroupBoundDCByServiceCollectId(long serviceCollectId);
	
}