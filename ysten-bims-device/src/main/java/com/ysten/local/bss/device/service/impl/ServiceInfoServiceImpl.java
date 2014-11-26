package com.ysten.local.bss.device.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysten.local.bss.device.domain.ServiceInfo;
import com.ysten.local.bss.device.repository.IServiceInfoRepository;
import com.ysten.local.bss.device.service.IServiceInfoService;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import com.ysten.utils.page.Pageable;

/**
 * Created by frank on 14-5-7.
 */
@Service
public class ServiceInfoServiceImpl implements IServiceInfoService {

    @Autowired
    private IServiceInfoRepository serviceInfoRepository;

    @Override
    public List<ServiceInfo> getServiceInfoByServiceCollectId(Long serviceCollectId) {
        return serviceInfoRepository.getServiceInfoByServiceCollectId(serviceCollectId);
    }

	@Override
	public Pageable<ServiceInfo> findServiceInfoListBySimpleCondition(String serviceName, String serviceType, Long serviceCollectId, Integer pageNo, Integer pageSize) {
		return serviceInfoRepository.findServiceInfoListBySimpleCondition(serviceName, serviceType, serviceCollectId, pageNo, pageSize);
	}

    @Override
    public List<ServiceInfo> getServiceInfoByDeviceGroupId(Long deviceGroupId) {
        return serviceInfoRepository.getServiceInfoByDeviceGroupId(deviceGroupId);
    }

    @Override
    public List<ServiceInfo> getServiceInfoByDefaultService(EnumConstantsInterface.ServiceCollectType serviceCollectType) {
        return serviceInfoRepository.getServiceInfoByDefaultService(serviceCollectType);
    }

    @Override
    public List<ServiceInfo> getServiceInfoByDefaultServiceAndName(EnumConstantsInterface.ServiceCollectType serviceCollectType, String name) {
        return serviceInfoRepository.getServiceInfoByDefaultServiceAndName(serviceCollectType, name);
    }

	@Override
	public void saveServiceInfo(ServiceInfo serviceInfo) {
		serviceInfo.setCreateDate(new Date());
		serviceInfo.setUpdateDate(new Date());
		serviceInfoRepository.saveServiceInfo(serviceInfo);
	}

	@Override
	public void updateServiceInfo(ServiceInfo serviceInfo) {
		ServiceInfo si = serviceInfoRepository.getServiceInfoById(serviceInfo.getId());
		serviceInfo.setServiceCollectId(si.getServiceCollectId());
		serviceInfo.setCreateDate(si.getCreateDate());
		serviceInfo.setUpdateDate(new Date());
		serviceInfoRepository.updateServiceInfo(serviceInfo);
	}

	@Override
	public ServiceInfo getServiceInfoById(String id) {
		return serviceInfoRepository.getServiceInfoById(Long.parseLong(id));
	}

    @Override
    public List<ServiceInfo> getServiceInfoByYstenId(String ystenId) {
        return this.serviceInfoRepository.getServiceInfoByYstenId(ystenId);
    }

    @Override
    public ServiceInfo getServiceInfoByName(String name) {
        return this.serviceInfoRepository.getServiceInfoByName(name);
    }

}
