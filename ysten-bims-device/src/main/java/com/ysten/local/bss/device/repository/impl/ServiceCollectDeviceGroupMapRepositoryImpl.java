package com.ysten.local.bss.device.repository.impl;

import java.util.List;

import com.ysten.cache.annotation.MethodFlushBootsrap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.ysten.local.bss.device.domain.ServiceCollectDeviceGroupMap;
import com.ysten.local.bss.device.repository.IServiceCollectDeviceGroupMapRepository;
import com.ysten.local.bss.device.repository.mapper.ServiceCollectDeviceGroupMapMapper;

@Repository
public class ServiceCollectDeviceGroupMapRepositoryImpl implements IServiceCollectDeviceGroupMapRepository {

	@Autowired
	private ServiceCollectDeviceGroupMapMapper serviceCollectDeviceGroupMapMapper;
	
	@Override
    @MethodFlushBootsrap
	public void saveServiceCollectDeviceGroupMap(ServiceCollectDeviceGroupMap serviceCollectDeviceGroupMap) {
		serviceCollectDeviceGroupMapMapper.saveServiceCollectDeviceGroupMap(serviceCollectDeviceGroupMap);
	}
	

	
	@Override
    @MethodFlushBootsrap
	public void saveServiceCollectDeviceCodeMap(ServiceCollectDeviceGroupMap serviceCollectDeviceGroupMap) {
		serviceCollectDeviceGroupMapMapper.saveServiceCollectDeviceCodeMap(serviceCollectDeviceGroupMap);
	}
	

	@Override
    @MethodFlushBootsrap
	public void deleteServiceCollectGroupmapByGroupId(Long id) {
		this.serviceCollectDeviceGroupMapMapper.deleteServiceCollectGroupmapByGroupId(id);
	}




	@Override
	public List<ServiceCollectDeviceGroupMap> getDeviceCodesById(long id) {
		return serviceCollectDeviceGroupMapMapper.getDeviceCodesById(id);
	}

    @Override
    public List<ServiceCollectDeviceGroupMap> finDeviceCodeServiceCollectMap() {
        return this.serviceCollectDeviceGroupMapMapper.finDeviceCodeServiceCollectMap();
    }


    @Override
    @MethodFlushBootsrap
	public void deleteServiceCollectGroupmapByDeviceCode(String deviceCode) {
		this.serviceCollectDeviceGroupMapMapper.deleteServiceCollectGroupmapByDeviceCode(deviceCode);
		
	}

	@Override
    @MethodFlushBootsrap
	public void deleteServiceCollectGroupBoundGIByServiceCollectId(
			long serviceCollectId) {
		this.serviceCollectDeviceGroupMapMapper.deleteServiceCollectGroupBoundGIByServiceCollectId(serviceCollectId);
	}

	@Override
    @MethodFlushBootsrap
	public void deleteServiceCollectGroupBoundDCByServiceCollectId(
			long serviceCollectId) {
		this.serviceCollectDeviceGroupMapMapper.deleteServiceCollectGroupBoundDCByServiceCollectId(serviceCollectId);
		
	}

	@Override
    @MethodFlushBootsrap
	public void deleteServiceCollectDeviceGroupMap(Long serviceCollectId,String isALL) {
		this.serviceCollectDeviceGroupMapMapper.deleteServiceCollectDeviceGroupMap(serviceCollectId,isALL);
		
	}

	@Override
	public List<ServiceCollectDeviceGroupMap> getServiceCollectGroupmapByGroupId(Long groupId) {
		return this.serviceCollectDeviceGroupMapMapper.getServiceCollectGroupmapByGroupId(groupId);
	}



    @Override
    public List<ServiceCollectDeviceGroupMap> getServiceCollectGroupmapByYstenId(String ystenId) {
        return this.serviceCollectDeviceGroupMapMapper.getServiceCollectGroupmapByYstenId(ystenId);
    }



	@Override
	public void deleteServiceCollectGroupmapByGroupIdsAndYstenIds(
			List<Long> groupIds, List<String> ystenIds) {
		this.serviceCollectDeviceGroupMapMapper.deleteServiceCollectMapByGroupIdsAndYstenIds(groupIds, ystenIds);
	}



	@Override
	public void bulkSaveServiceCollectMap(
			List<ServiceCollectDeviceGroupMap> list) {
		this.serviceCollectDeviceGroupMapMapper.bulkSaveServiceCollectMap(list);
	}
}
