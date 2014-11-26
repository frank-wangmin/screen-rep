package com.ysten.local.bss.device.repository;

import java.util.List;
import com.ysten.local.bss.device.domain.ServiceCollect;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import com.ysten.utils.page.Pageable;

/**
 * Created by frank on 14-5-7.
 */
public interface IServiceCollectRepository {

    /**
     * get service collect by type(Default,Normal)
     * @param serviceType
     * @return
     */
    public List<ServiceCollect> getServiceCollectByType(EnumConstantsInterface.ServiceCollectType serviceType);
    
    Pageable<ServiceCollect> findServiceCollectList(String serviceType, String id,Integer pageNum, Integer pageSize);

    List<ServiceCollect> findAllServiceCollectList();

    /**
     * get service collect by device group id
     * @param deviceGroupId
     * @return
     */
    public ServiceCollect getServiceCollectByDeviceGroupId(Long deviceGroupId);
    
    void saveServiceCollect(ServiceCollect serviceCollect);
    
    void setAllSeviceCollectNormal();
    
    void updateSerivceCollect(ServiceCollect serviceCollect);    
    
    ServiceCollect getServiceCollectById(Long id);
    
    ServiceCollect getServiceCollectByDescription(String description);

    ServiceCollect getServiceCollectByYstenIdOrGroupId(String ystenId, Long groupId);

}
