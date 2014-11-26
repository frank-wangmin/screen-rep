package com.ysten.local.bss.device.repository.mapper;

import com.ysten.local.bss.device.domain.ServiceInfo;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by frank on 14-5-6.
 */
public interface ServiceInfoMapper {

    /**
     * get serviceInfo list by serviceCollect id
     * @param serviceCollectId
     * @return
     */
    List<ServiceInfo> getServiceInfoByServiceCollectId(@Param("serviceCollectId") Long serviceCollectId);
    
    List<ServiceInfo> findServiceInfoListBySimpleCondition(@Param("serviceName")String serviceName, @Param("serviceType")String serviceType, @Param("serviceCollectId")Long serviceCollectId, @Param("pageNo")Integer pageNo, @Param("pageSize")Integer pageSize);
    
    Integer getServiceInfoCountBySimpleCondition(@Param("serviceName")String serviceName, @Param("serviceType")String serviceType, @Param("serviceCollectId")Long serviceCollectId);

    /**
     * get serviceInfo list by device group id
     * @param deviceGroupId
     * @return
     */
    List<ServiceInfo> getServiceInfoByDeviceGroupId(@Param("deviceGroupId") Long deviceGroupId);
    
    List<ServiceInfo> getServiceInfoByYstenId(@Param("ystenId") String deviceCode);

    /**
     * get serviceInfo list by service collect type
     * @param serviceCollectType
     * @return
     */
    List<ServiceInfo> getServiceInfoByDefaultService(@Param("serviceType")EnumConstantsInterface.ServiceCollectType serviceCollectType);

    List<ServiceInfo> getServiceInfoByDefaultServiceAndName(@Param("serviceType")EnumConstantsInterface.ServiceCollectType serviceCollectType, @Param("name")String name);

    void saveServiceInfo(ServiceInfo serviceInfo);
    
    void updateServiceInfo(ServiceInfo serviceInfo);
    
    ServiceInfo getServiceInfoById(@Param("id")Long id);
    
    ServiceInfo getServiceInfoByName(@Param("name") String name);
    
}
