package com.ysten.local.bss.device.service;

import com.ysten.local.bss.device.domain.DeviceGroup;
import com.ysten.local.bss.device.domain.ServiceCollect;
import com.ysten.local.bss.device.domain.ServiceCollectDeviceGroupMap;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import com.ysten.utils.page.Pageable;

import java.util.List;

/**
 * Created by frank on 14-5-7.
 */
public interface IServiceCollectService {

    /**
     * get service collect by type(Default,Normal)
     *
     * @param serviceType
     * @return
     */
    public List<ServiceCollect> getServiceCollectByType(
            EnumConstantsInterface.ServiceCollectType serviceType);

    Pageable<ServiceCollect> findServiceCollectList(String serviceType, String id,
                                                    Integer pageNo, Integer pageSize);

    List<ServiceCollect> findAllServiceCollectList();

    /**
     * get service collect by device group id
     *
     * @param deviceGroupId
     * @return
     */
    public ServiceCollect getServiceCollectByDeviceGroupId(Long deviceGroupId);

    /**
     * check if the serviceCollect can be added successfully
     *
     * @param serviceType
     * @param description
     * @param deviceGroupIdList
     * @param deviceCodes
     * @return str
     */
    public String saveServiceCollect(String serviceType, String description,
                                     List<Long> deviceGroupIdList, String deviceCodes);

    /**
     * update service collect and service collect group map
     *
     * @param id
     * @param serviceType
     * @param description
     * @param deviceGroupIdList
     * @param deviceCodes
     * @return str
     */
    String updateSerivceCollect(String id, String serviceType, String description, List<Long> deviceGroupIdList, String deviceCodes);

    ServiceCollect getServiceCollectById(String id);

    ServiceCollect getServiceCollectByDescription(String description);

    /**
     * add serviceCollectGroupMap
     *
     * @param serviceCollectId
     * @param deviceGroupIdList
     * @param deviceCodes
     * @param description
     */
    String saveServiceCollectDeviceGroupMap(Long serviceCollectId, String areaIds,
                                            List<Long> deviceGroupIdList, String deviceCodes, String description);
    
    String checkAreaDevices(String areaIds,String[] ystenIds);

    /**
     * delete the serviceCollectGroupMap bound with deviceCode or deviceGroup
     *
     * @param serviceCollectId
     */
    void deleteServiceCollectDeviceGroupMap(Long serviceCollectId);

    /**
     * get deviceGroupIds added by joyce on 2014-6-6
     *
     * @param id
     * @return list
     */
    public List<DeviceGroup> getDeviceGroupsById(String id);

    /**
     * get deviceGroupIds added by joyce on 2014-6-6
     *
     * @param id
     * @return list
     */
    public List<ServiceCollectDeviceGroupMap> getDeviceCodesById(String id);

}
