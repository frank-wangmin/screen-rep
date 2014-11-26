package com.ysten.local.bss.device.repository;

import java.util.List;

import com.ysten.local.bss.device.domain.ServiceCollectDeviceGroupMap;


public interface IServiceCollectDeviceGroupMapRepository {
	
	void saveServiceCollectDeviceGroupMap(ServiceCollectDeviceGroupMap serviceCollectDeviceGroupMap);

	/**
	 * 根据分组Id删除服务集分组信息
	 * @param id
	 */
	public void deleteServiceCollectGroupmapByGroupId(Long id);
	
	void deleteServiceCollectGroupmapByGroupIdsAndYstenIds(List<Long> groupIds,List<String> ystenIds);
	
	void bulkSaveServiceCollectMap(List<ServiceCollectDeviceGroupMap> list);
	
	/**
	 *  delete the service collect group map by deviceCode
	 * @param deviceCode
	 */
	public void deleteServiceCollectGroupmapByDeviceCode(String deviceCode);
	
	/**
	 * delete the serviceCollectGroupMap bound with deviceCode or deviceGroup
	 * @param serviceCollectId
	 */
	 void deleteServiceCollectDeviceGroupMap(Long serviceCollectId,String isALl);
	
	/**
	 * add the relationship between serviceCollect and deviceCode
	 * @param serviceCollectDeviceGroupMap
	 */
	public void saveServiceCollectDeviceCodeMap(ServiceCollectDeviceGroupMap serviceCollectDeviceGroupMap);
	
	/**
	 * get deviceCodes bound with service collect by id
	 * @param id
	 * @return list
	 */
	public List<ServiceCollectDeviceGroupMap> getDeviceCodesById(long id);

    public List<ServiceCollectDeviceGroupMap> finDeviceCodeServiceCollectMap();
	
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

	List<ServiceCollectDeviceGroupMap> getServiceCollectGroupmapByGroupId(Long groupId);
	
	List<ServiceCollectDeviceGroupMap> getServiceCollectGroupmapByYstenId(String ystenId);
}
