package com.ysten.local.bss.device.repository;

import java.util.List;
import com.ysten.local.bss.device.domain.ServiceInfo;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import com.ysten.utils.page.Pageable;

/**
 * Created by frank on 14-5-7.
 */
public interface IServiceInfoRepository {

    /**
     * get serviceInfo list by serviceCollect id
     * @param serviceCollectId
     * @return
     */
    public List<ServiceInfo> getServiceInfoByServiceCollectId(Long serviceCollectId);
    
    Pageable<ServiceInfo> findServiceInfoListBySimpleCondition(String serviceName, String serviceType, Long serviceCollectId, Integer pageNo, Integer pageSize);

    /**
     * get serviceInfo list by device group id
     * @param deviceGroupId
     * @return
     */
    public List<ServiceInfo> getServiceInfoByDeviceGroupId(Long deviceGroupId);
    
    List<ServiceInfo> getServiceInfoByYstenId(String ystenId);

    /**
     * get serviceInfo list by service collect type
     * @param serviceCollectType
     * @return
     */
    public List<ServiceInfo> getServiceInfoByDefaultService(EnumConstantsInterface.ServiceCollectType serviceCollectType);

    List<ServiceInfo> getServiceInfoByDefaultServiceAndName(EnumConstantsInterface.ServiceCollectType serviceCollectType, String name);

    void saveServiceInfo(ServiceInfo serviceInfo);
    
    void updateServiceInfo(ServiceInfo serviceInfo);
    
    ServiceInfo getServiceInfoById(Long id);
    
    ServiceInfo getServiceInfoByName(String name);

}
