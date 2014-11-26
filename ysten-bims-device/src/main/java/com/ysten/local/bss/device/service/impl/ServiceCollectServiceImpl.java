package com.ysten.local.bss.device.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ysten.local.bss.util.FilterSpaceUtils;
import com.ysten.local.bss.util.bean.Constant;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysten.boss.systemconfig.service.SystemConfigService;
import com.ysten.local.bss.device.domain.Device;
import com.ysten.local.bss.device.domain.DeviceGroup;
import com.ysten.local.bss.device.domain.ServiceCollect;
import com.ysten.local.bss.device.domain.ServiceCollectDeviceGroupMap;
import com.ysten.local.bss.device.repository.IDeviceCustomerAccountMapRepository;
import com.ysten.local.bss.device.repository.IDeviceGroupRepository;
import com.ysten.local.bss.device.repository.IDeviceRepository;
import com.ysten.local.bss.device.repository.IServiceCollectDeviceGroupMapRepository;
import com.ysten.local.bss.device.repository.IServiceCollectRepository;
import com.ysten.local.bss.device.service.IServiceCollectService;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import com.ysten.utils.page.Pageable;
import com.ysten.utils.string.StringUtil;


/**
 * Created by frank on 14-5-7.
 */
@Service
public class ServiceCollectServiceImpl implements IServiceCollectService {

    @Autowired
    private IServiceCollectRepository serviceCollectRepository;
    @Autowired
    private IServiceCollectDeviceGroupMapRepository serviceCollectDeviceGroupMapRepository;
    @Autowired
    private IDeviceRepository deviceRepository;
    @Autowired
    private IDeviceGroupRepository deviceGroupRepository;
    @Autowired
    private IDeviceCustomerAccountMapRepository deviceCustomerAccountMapRepository;
    @Autowired
    private SystemConfigService systemConfigService;
    private String ISCENTER = "isCenter";

    @Override
    public ServiceCollect getServiceCollectByDeviceGroupId(Long deviceGroupId) {
        return serviceCollectRepository
                .getServiceCollectByDeviceGroupId(deviceGroupId);
    }

    @Override
    public List<ServiceCollect> getServiceCollectByType(
            EnumConstantsInterface.ServiceCollectType serviceType) {
        return serviceCollectRepository.getServiceCollectByType(serviceType);
    }

    @Override
    public Pageable<ServiceCollect> findServiceCollectList(String serviceType, String id,
                                                           Integer pageNo, Integer pageSize) {
        return serviceCollectRepository.findServiceCollectList(serviceType, id,
                pageNo, pageSize);
    }

    @Override
    public List<ServiceCollect> findAllServiceCollectList() {
        return serviceCollectRepository.findAllServiceCollectList();
    }

    // adding the saveServiceCollectDeviceGroupMap and
    // saveServiceCollectDeviceCodeMap
    @Override
    public String saveServiceCollect(String serviceType, String description,
                                     List<Long> deviceGroupIdList, String deviceCodes) {
            /*
             * if (StringUtils.isNotBlank(deviceCodes)) { String[] _deviceCodes =
		 * deviceCodes.split(","); for (String deviceCode : _deviceCodes) {
		 * Device device = this.deviceRepository .getDeviceByCode(deviceCode);
		 * if (device == null) { return "未能找到设备code为" + deviceCode + "的设备，请确认!";
		 * } } }
		 */
        if (serviceType
                .equals(EnumConstantsInterface.ServiceCollectType.DEFAULT + "")) {
            serviceCollectRepository.setAllSeviceCollectNormal();
        }
        ServiceCollect serviceCollect = new ServiceCollect();
        serviceCollect.setCreateDate(new Date());
        serviceCollect.setUpdateDate(new Date());
        serviceCollect.setServiceType(EnumConstantsInterface.ServiceCollectType
                .valueOf(serviceType));
        serviceCollect.setDescription(description);
        serviceCollectRepository.saveServiceCollect(serviceCollect);
            /*
             * Long serviceCollectId = serviceCollect.getId(); if (deviceGroupIdList
		 * != null && deviceGroupIdList.size() > 0) { for (Long deviceGroupId :
		 * deviceGroupIdList) { ServiceCollectDeviceGroupMap
		 * serviceCollectDeviceGroupMap = new ServiceCollectDeviceGroupMap();
		 * serviceCollectDeviceGroupMap .setServiceCollectId(serviceCollectId);
		 * serviceCollectDeviceGroupMap .setDeviceGroupId(deviceGroupId);
		 * serviceCollectDeviceGroupMapRepository
		 * .saveServiceCollectDeviceGroupMap(serviceCollectDeviceGroupMap); } }
		 * String[] _deviceCodes = StringUtils.split(deviceCodes, ",");
		 * if(_deviceCodes != null && _deviceCodes.length > 0) { for (String
		 * deviceCode : _deviceCodes) { ServiceCollectDeviceGroupMap
		 * serviceCollectDeviceGroupMap = new ServiceCollectDeviceGroupMap();
		 * serviceCollectDeviceGroupMap .setServiceCollectId(serviceCollectId);
		 * serviceCollectDeviceGroupMap.setDeviceCode(deviceCode);
		 * serviceCollectDeviceGroupMapRepository
		 * .deleteServiceCollectGroupmapByDeviceCode(deviceCode);
		 * serviceCollectDeviceGroupMapRepository
		 * .saveServiceCollectDeviceCodeMap(serviceCollectDeviceGroupMap); } }
		 */
        return null;
    }

    @Override
    public String updateSerivceCollect(String id, String serviceType,
                                       String description, List<Long> deviceGroupIdList, String deviceCodes) {
        String result = "";
        // check if the input deviceCodes are valid
		/*
		 * if (StringUtils.isNotBlank(deviceCodes)) { String[] _deviceCodes =
		 * deviceCodes.split(","); for (String deviceCode : _deviceCodes) {
		 * Device device = this.deviceRepository .getDeviceByCode(deviceCode);
		 * if (device == null) { result = "未能找到设备code为" + deviceCode +
		 * "的设备，请确认!"; } } }
		 */
        // if(StringUtils.isNotBlank(result)){//if one deviceCode is not existed
        // will be returned
        // return result;
        // }else{//if all the deviceCodes are existed,update the service collect
        // and serviceCollectDeviceGroupMap
        ServiceCollect serviceCollect = serviceCollectRepository
                .getServiceCollectById(Long.parseLong(id));
        if (serviceType
                .equals(EnumConstantsInterface.ServiceCollectType.DEFAULT + "")) {
            serviceCollectRepository.setAllSeviceCollectNormal();
        }

        serviceCollect.setUpdateDate(new Date());
        serviceCollect.setServiceType(EnumConstantsInterface.ServiceCollectType
                .valueOf(serviceType));
        serviceCollect.setDescription(description);
        serviceCollectRepository.updateSerivceCollect(serviceCollect);

		/*
		 * Long serviceCollectId = serviceCollect.getId();//get the
		 * serviceCollect id //delete the existed serviceCollectDeviceGroupMap
		 * bounded with deviceGroupId by serviceCollectId
		 * serviceCollectDeviceGroupMapRepository
		 * .deleteServiceCollectGroupBoundGIByServiceCollectId
		 * (serviceCollectId); //if the deviceGroupIdList is not blank, delete
		 * serviceCollectDeviceGroupMap bounded with selectedDeviceGroupId and
		 * add the new serviceCollectDeviceGroupMap if (deviceGroupIdList !=
		 * null && deviceGroupIdList.size() > 0) { for (Long deviceGroupId :
		 * deviceGroupIdList) { ServiceCollectDeviceGroupMap
		 * serviceCollectDeviceGroupMap = new ServiceCollectDeviceGroupMap();
		 * serviceCollectDeviceGroupMap .setServiceCollectId(serviceCollectId);
		 * serviceCollectDeviceGroupMap .setDeviceGroupId(deviceGroupId);
		 * serviceCollectDeviceGroupMapRepository
		 * .deleteServiceCollectGroupmapByGroupId(deviceGroupId);
		 * serviceCollectDeviceGroupMapRepository
		 * .saveServiceCollectDeviceGroupMap(serviceCollectDeviceGroupMap); } }
		 * //delete the existed serviceCollectDeviceGroupMap bounded with
		 * deviceCode by serviceCollectId
		 * serviceCollectDeviceGroupMapRepository.
		 * deleteServiceCollectGroupBoundDCByServiceCollectId(serviceCollectId);
		 * String[] _deviceCodes = deviceCodes.split(","); //if the _deviceCodes
		 * is not blank, delete serviceCollectDeviceGroupMap bounded with
		 * deviceCode and add the new serviceCollectDeviceGroupMap if
		 * (_deviceCodes != null && _deviceCodes.length > 0) { for (String
		 * deviceCode : _deviceCodes) { ServiceCollectDeviceGroupMap
		 * serviceCollectDeviceGroupMap = new ServiceCollectDeviceGroupMap();
		 * serviceCollectDeviceGroupMap .setServiceCollectId(serviceCollectId);
		 * serviceCollectDeviceGroupMap.setDeviceCode(deviceCode);
		 * serviceCollectDeviceGroupMapRepository
		 * .deleteServiceCollectGroupmapByDeviceCode(deviceCode);
		 * serviceCollectDeviceGroupMapRepository
		 * .saveServiceCollectDeviceCodeMap(serviceCollectDeviceGroupMap); } }
		 */
        // }
        return result;
    }

    @Override
    public ServiceCollect getServiceCollectById(String id) {
        return serviceCollectRepository.getServiceCollectById(Long
                .parseLong(id));
    }

    // added by joyce to get deviceGroupIds on 2014-6-6
    @Override
    public List<DeviceGroup> getDeviceGroupsById(String id) {
        return deviceGroupRepository.getDeviceGroupsById(Long.parseLong(id));
    }

    // added by joyce to get deviceCodes on 2014-6-6
    @Override
    public List<ServiceCollectDeviceGroupMap> getDeviceCodesById(String id) {
        return serviceCollectDeviceGroupMapRepository.getDeviceCodesById(Long
                .parseLong(id));
    }

    @Override
    public String saveServiceCollectDeviceGroupMap(Long serviceCollectId, String areaIds,
                                                   List<Long> deviceGroupIdList, String deviceCodes, String description) {
        StringBuilder jy = new StringBuilder("");
        String[] _deviceCodes = new String[0];
        List<Long> areaIdList = StringUtil.splitToLong(areaIds);
        serviceCollectDeviceGroupMapRepository.deleteServiceCollectDeviceGroupMap(serviceCollectId,"isAll");
        if (deviceGroupIdList != null && deviceGroupIdList.size() > 0) {
        	serviceCollectDeviceGroupMapRepository.deleteServiceCollectGroupmapByGroupIdsAndYstenIds(deviceGroupIdList, null);
        	int index = 0;
        	List<ServiceCollectDeviceGroupMap> maps = new ArrayList<ServiceCollectDeviceGroupMap>();
            for (Long deviceGroupId : deviceGroupIdList) {
                ServiceCollectDeviceGroupMap serviceCollectDeviceGroupMap = new ServiceCollectDeviceGroupMap();
                serviceCollectDeviceGroupMap.setServiceCollectId(serviceCollectId);
                serviceCollectDeviceGroupMap.setDeviceGroupId(deviceGroupId);
                serviceCollectDeviceGroupMap.setYstenId(null);
                maps.add(serviceCollectDeviceGroupMap);
                index++;
				if (index % Constant.BULK_NUM == 0) {
					this.serviceCollectDeviceGroupMapRepository.bulkSaveServiceCollectMap(maps);
					maps.clear();
				}
            }
            if(maps.size() > 0){
            	this.serviceCollectDeviceGroupMapRepository.bulkSaveServiceCollectMap(maps);
				maps.clear();
            }
        }
        
        if (StringUtils.isNotBlank(deviceCodes)) {
            _deviceCodes = deviceCodes.split(",");
            List<String> ystenIdList = new ArrayList<String>();
            for (int i = 0; i < _deviceCodes.length; i++) {
            	if (StringUtils.isEmpty(_deviceCodes[i])) {
                    continue;
                }
                _deviceCodes[i] = FilterSpaceUtils.replaceBlank(_deviceCodes[i]);
                ystenIdList.add(_deviceCodes[i]);
            }
            List<Device> deviceList = this.deviceRepository.findDeviceListByYstenIds(_deviceCodes);
            if(deviceList.size()==0){
            	jy.append("未能找到你所上传的所有易视腾编号的设备，请确认!");
            	return jy.toString();
            }
            if(deviceList.size()>0 && deviceList.size() != ystenIdList.size()){
            	for (int i = 0; i < ystenIdList.size(); i++) {
                    boolean bool = false;
                    for (Device  device:deviceList) {
                        if (device.getYstenId().equals(ystenIdList.get(i))) {
                            bool = true;
                            break;
                        }
                    }
                    if (bool == false) {
                    	jy.append("未能找到易视腾编号为：" + ystenIdList.get(i) + "的设备，请确认!"+"\n") ;
                    }
                }
            }
            List<String> ystenIds = new ArrayList<String>();
            for (Device  device:deviceList) {
                boolean bool = false;
                for (Long areaId : areaIdList) {
                    if (areaId.equals(device.getArea().getId())) {
                    	bool = true;
                    	ystenIds.add(device.getYstenId());
                        break;
                    }
                }
                if (bool == false) {
               	 jy.append("请确定易视腾编号为：" + device.getYstenId() + "的所在区域!"+"\n");
                }
           	}
            if(ystenIds != null && ystenIds.size()>0){
            	this.serviceCollectDeviceGroupMapRepository.deleteServiceCollectGroupmapByGroupIdsAndYstenIds(null, ystenIds);
            	int index = 0;
            	List<ServiceCollectDeviceGroupMap> maps = new ArrayList<ServiceCollectDeviceGroupMap>();
            	for(String y:ystenIds){
            	  ServiceCollectDeviceGroupMap serviceCollectDeviceGroupMap = new ServiceCollectDeviceGroupMap();
                  serviceCollectDeviceGroupMap.setServiceCollectId(serviceCollectId);
                  serviceCollectDeviceGroupMap.setYstenId(y);
                  maps.add(serviceCollectDeviceGroupMap);
                  index++;
  				if (index % Constant.BULK_NUM == 0) {
  					this.serviceCollectDeviceGroupMapRepository.bulkSaveServiceCollectMap(maps);
  					maps.clear();
  				}
            	}
            	if(maps.size() > 0){
                	this.serviceCollectDeviceGroupMapRepository.bulkSaveServiceCollectMap(maps);
    				maps.clear();
                }
            }
        }

      if (StringUtils.isNotBlank(jy.toString())) {
                return jy.toString();
          }
        return null;
    }

    @Override
    public void deleteServiceCollectDeviceGroupMap(Long serviceCollectId) {
        serviceCollectDeviceGroupMapRepository
                .deleteServiceCollectDeviceGroupMap(serviceCollectId,"");

    }

    @Override
    public ServiceCollect getServiceCollectByDescription(String description) {
        return this.serviceCollectRepository.getServiceCollectByDescription(description);
    }

    @Override
    public String checkAreaDevices(String areaIds, String[] ystenIds) {
//		boolean bool = false;
        //int sum = 0;
        StringBuilder sb = new StringBuilder("");
        List<Long> areaIdList = StringUtil.splitToLong(areaIds);
//        String[] ystenIdList = ystenIds.split(",");
        for (int i = 0; i < ystenIds.length; i++) {
            if (StringUtils.isEmpty(ystenIds[i])) {
                continue;
            }
            Device device = this.deviceRepository.getDeviceByYstenId(ystenIds[i]);
            int m = 0;
            boolean bool = false;
            for (Long areaId : areaIdList) {
                if (areaId.equals(device.getArea().getId())) {
                    bool = true;
                    //sum++;
                    continue;
                }
                m++;
                if (m == areaIdList.size() && bool == false) {
                    sb.append("未能绑定，请确定易视腾编号为：" + ystenIds[i] + "的所在区域!"+"\n");
                }
            }
        }
        if (StringUtils.isNotBlank(sb.toString())) {
            return sb.toString();
        }
        return null;
    }

}
