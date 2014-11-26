package com.ysten.local.bss.device.repository.impl;

import java.util.List;

import com.ysten.cache.annotation.MethodFlushBootsrap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysten.local.bss.device.domain.ServiceInfo;
import com.ysten.local.bss.device.repository.IServiceInfoRepository;
import com.ysten.local.bss.device.repository.mapper.ServiceInfoMapper;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import com.ysten.utils.page.Pageable;

/**
 * Created by frank on 14-5-7.
 */
@Repository
public class ServiceInfoRepositoryImpl implements IServiceInfoRepository {

    @Autowired
    private ServiceInfoMapper serviceInfoMapper;

    @Override
    public List<ServiceInfo> getServiceInfoByDeviceGroupId(Long deviceGroupId) {
        return serviceInfoMapper.getServiceInfoByDeviceGroupId(deviceGroupId);
    }

    @Override
    public List<ServiceInfo> getServiceInfoByServiceCollectId(Long serviceCollectId) {
        return serviceInfoMapper.getServiceInfoByServiceCollectId(serviceCollectId);
    }

	@Override
	public Pageable<ServiceInfo> findServiceInfoListBySimpleCondition(String serviceName, String serviceType, Long serviceCollectId, Integer pageNo, Integer pageSize) {
		List<ServiceInfo> serviceInfos = serviceInfoMapper.findServiceInfoListBySimpleCondition(serviceName, serviceType, serviceCollectId, pageNo, pageSize);
		Integer total = serviceInfoMapper.getServiceInfoCountBySimpleCondition(serviceName, serviceType, serviceCollectId);
		return new Pageable<ServiceInfo>().instanceByPageNo(serviceInfos, total, pageNo, pageSize);
	}

    @Override
    public List<ServiceInfo> getServiceInfoByDefaultService(EnumConstantsInterface.ServiceCollectType serviceCollectType) {
        return serviceInfoMapper.getServiceInfoByDefaultService(serviceCollectType);
    }

    @Override
    public List<ServiceInfo> getServiceInfoByDefaultServiceAndName(EnumConstantsInterface.ServiceCollectType serviceCollectType, String name) {
        return serviceInfoMapper.getServiceInfoByDefaultServiceAndName(serviceCollectType, name);
    }

	@Override
    @MethodFlushBootsrap
	public void saveServiceInfo(ServiceInfo serviceInfo) {
		serviceInfoMapper.saveServiceInfo(serviceInfo);
	}

	@Override
    @MethodFlushBootsrap
	public void updateServiceInfo(ServiceInfo serviceInfo) {
		serviceInfoMapper.updateServiceInfo(serviceInfo);
	}

	@Override
	public ServiceInfo getServiceInfoById(Long id) {
		return serviceInfoMapper.getServiceInfoById(id);
	}

    @Override
    public List<ServiceInfo> getServiceInfoByYstenId(String ystenId) {
        return this.serviceInfoMapper.getServiceInfoByYstenId(ystenId);
    }

    @Override
    public ServiceInfo getServiceInfoByName(String name) {
        return this.serviceInfoMapper.getServiceInfoByName(name);
    }

}
