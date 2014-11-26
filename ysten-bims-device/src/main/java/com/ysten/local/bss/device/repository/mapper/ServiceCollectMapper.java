package com.ysten.local.bss.device.repository.mapper;

import com.ysten.local.bss.device.domain.ServiceCollect;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by frank on 14-5-6.
 */
public interface ServiceCollectMapper {

    /**
     * get service collect by type(Default,Normal)
     * @param serviceType
     * @return
     */
    List<ServiceCollect> getServiceCollectByType (@Param("serviceType")EnumConstantsInterface.ServiceCollectType serviceType);
    
    List<ServiceCollect> findServiceCollectList(@Param("serviceType")String serviceType, @Param("pageNo")Integer pageNo, @Param("pageSize")Integer pageSize);

    List<ServiceCollect> findAllServiceCollectList();

    Integer getServiceCollectCount(@Param("serviceType")String serviceType);

    /**
     * get service collect by device group id
     * @param deviceGroupId
     * @return
     */
    ServiceCollect getServiceCollectByDeviceGroupId(@Param("deviceGroupId") Long deviceGroupId);
    
    void saveServiceCollect(ServiceCollect serviceCollect);
    
    void setAllSeviceCollectNormal();
    
    void updateSerivceCollect(ServiceCollect serviceCollect);
    
    ServiceCollect getServiceCollectById(@Param("id")Long id);
    
    ServiceCollect getServiceCollectByDescription(@Param("description")String description);

    ServiceCollect getServiceCollectByYstenIdOrGroupId(@Param("ystenId")String ystenId, @Param("groupId")Long groupId);

}
