package com.ysten.local.bss.device.repository.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysten.local.bss.device.domain.DeviceGroup;
import com.ysten.local.bss.device.domain.ServiceCollect;
import com.ysten.local.bss.device.domain.ServiceCollectDeviceGroupMap;
import com.ysten.local.bss.device.repository.IServiceCollectRepository;
import com.ysten.local.bss.device.repository.mapper.DeviceGroupMapper;
import com.ysten.local.bss.device.repository.mapper.ServiceCollectDeviceGroupMapMapper;
import com.ysten.local.bss.device.repository.mapper.ServiceCollectMapper;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import com.ysten.utils.page.Pageable;

/**
 * Created by frank on 14-5-7.
 */
@Repository
public class ServiceCollectRepositoryImpl implements IServiceCollectRepository {

    @Autowired
    private ServiceCollectMapper serviceCollectMapper;
    @Autowired
    private ServiceCollectDeviceGroupMapMapper serviceCollectDeviceGroupMapMapper;
    @Autowired
    private DeviceGroupMapper deviceGroupMapper;
    @Override
    public List<ServiceCollect> getServiceCollectByType(EnumConstantsInterface.ServiceCollectType serviceType) {
        return serviceCollectMapper.getServiceCollectByType(serviceType);
    }

    @Override
	public Pageable<ServiceCollect> findServiceCollectList(String serviceType,String id, Integer pageNo, Integer pageSize) {
		List<ServiceCollect> serviceCollects = this.serviceCollectMapper.findServiceCollectList(serviceType, pageNo, pageSize);
		Integer total = this.serviceCollectMapper.getServiceCollectCount(serviceType);
		if(StringUtils.isNotBlank(serviceType) && StringUtils.isNotBlank(id)){
		    Long serviceCollectId = Long.parseLong(id);
		    ServiceCollect existedDefault = new ServiceCollect();
		    for(ServiceCollect serviceCollect : serviceCollects){
		        if(serviceCollect.getId() == serviceCollectId){
		            existedDefault = serviceCollect;
		        }
		    }
		    serviceCollects.remove(existedDefault);
		    if(existedDefault!=null){
		        total--;
		    }
		}
		for(ServiceCollect serviceCollect : serviceCollects){
		List<ServiceCollectDeviceGroupMap> deviceMap = this.serviceCollectDeviceGroupMapMapper.getDeviceCodesById(serviceCollect.getId());
//		StringBuffer dsb = new StringBuffer();
        StringBuffer dgsb = new StringBuffer();
		if(deviceMap != null && deviceMap.size() != 0){
            for(ServiceCollectDeviceGroupMap map:deviceMap){
               /* if(map.getYstenId() != null && !map.getYstenId().equals("")){
                    dsb.append(map.getYstenId()).append(",");
                }*/
                if(map.getDeviceGroupId() != null && !map.getDeviceGroupId().equals("")){
                    DeviceGroup deviceGroup = this.deviceGroupMapper.getById(map.getDeviceGroupId());
                    dgsb.append(deviceGroup.getName()).append(",");
                }
            }
          }
		/*if(dsb.length()>0){
            serviceCollect.setYstenIds(dsb.substring(0,dsb.length()-1).toString());
        }*/
        if(dgsb.length()>0){
            serviceCollect.setDeviceGroupIds(dgsb.substring(0,dgsb.length()-1).toString());
        }
	  }
		return new Pageable<ServiceCollect>().instanceByPageNo(serviceCollects, total, pageNo, pageSize);
	}

    @Override
    public List<ServiceCollect> findAllServiceCollectList() {
        return serviceCollectMapper.findAllServiceCollectList();
    }

    @Override
    public ServiceCollect getServiceCollectByDeviceGroupId(Long deviceGroupId) {
        return serviceCollectMapper.getServiceCollectByDeviceGroupId(deviceGroupId);
    }

    @Override
    public void saveServiceCollect(ServiceCollect serviceCollect) {
        serviceCollectMapper.saveServiceCollect(serviceCollect);
    }

    @Override
    public void setAllSeviceCollectNormal() {
        serviceCollectMapper.setAllSeviceCollectNormal();
    }

    @Override
    public void updateSerivceCollect(ServiceCollect serviceCollect) {
        serviceCollectMapper.updateSerivceCollect(serviceCollect);
    }

    @Override
    public ServiceCollect getServiceCollectById(Long id) {
        return serviceCollectMapper.getServiceCollectById(id);
    }

    @Override
    public ServiceCollect getServiceCollectByDescription(String description) {
        return this.serviceCollectMapper.getServiceCollectByDescription(description);
    }

    @Override
    public ServiceCollect getServiceCollectByYstenIdOrGroupId(String ystenId, Long groupId) {
        return this.serviceCollectMapper.getServiceCollectByYstenIdOrGroupId(ystenId, groupId);
    }
}
