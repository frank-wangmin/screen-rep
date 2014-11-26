package com.ysten.local.bss.device.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysten.local.bss.device.domain.Customer;
import com.ysten.local.bss.device.domain.CustomerDeviceHistory;
import com.ysten.local.bss.device.domain.Device;
import com.ysten.local.bss.device.domain.DeviceCustomerAccountMap;
import com.ysten.local.bss.device.domain.LockType;
import com.ysten.local.bss.device.domain.Device.SyncType;
import com.ysten.local.bss.device.repository.ICustomerRepository;
import com.ysten.local.bss.device.repository.IDeviceCustomerAccountMapRepository;
import com.ysten.local.bss.device.repository.IDeviceRepository;
import com.ysten.local.bss.device.service.IDeviceCustomerAccountMapService;

@Service
public class DeviceCustomerAccountMapServiceImpl implements IDeviceCustomerAccountMapService {
	@Autowired
	IDeviceCustomerAccountMapRepository deviceCustomerAccountMapRepository;
	 @Autowired
	private ICustomerRepository customerRepository;
	 @Autowired
	 private IDeviceRepository deviceRepository;
	 
	@Override
	public List<DeviceCustomerAccountMap> findDeviceCustomerAccountMapByCustomerId(Long customerId) {
		return this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByCustomerId(customerId);
	}

	@Override
	public List<DeviceCustomerAccountMap> findDeviceCustomerAccountMapByCustomerCode(String customerCode) {
		return this.deviceCustomerAccountMapRepository.findDeviceCustomerAccountMapByCustomerCode(customerCode);
	}

	@Override
	public List<DeviceCustomerAccountMap> findDeviceCustomerAccountMapByDeviceId(Long deviceId) {
		return this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceId(deviceId);
	}

	@Override
	public DeviceCustomerAccountMap getFirstReplacmentMapUnderCustomerCust(String custId) {
		return this.deviceCustomerAccountMapRepository.getFirstReplacmentMapUnderCustomerCust(custId);
	}

	@Override
	public boolean updateDeviceCustomerAccountMap(DeviceCustomerAccountMap deviceCustomerMap) {
		return this.deviceCustomerAccountMapRepository.updateDeviceCustomerAccountMap(deviceCustomerMap);
	}

	@Override
	public boolean saveDeviceCustomerAccountMap(DeviceCustomerAccountMap deviceCustomerMap) {
		return this.deviceCustomerAccountMapRepository.saveDeviceCustomerAccountMap(deviceCustomerMap);
	}

	@Override
	public List<DeviceCustomerAccountMap> findDeviceCustomerAccountMapCreatedLastDay() {
		return this.deviceCustomerAccountMapRepository.getAllDeviceCustomerAccountMapCreatedLastDay();
	}

	@Override
	public Long getTotalCountByCityAndDeviceType(String cityId, String deviceTypeCode) {
		return this.deviceCustomerAccountMapRepository.getTotalCountByCityAndDeviceType(cityId, deviceTypeCode);
	}

	@Override
	public List<DeviceCustomerAccountMap> findDeviceCustomerAccountMapBySnoCustomerCode(String sno, String customerCode) {
		List<DeviceCustomerAccountMap> list = deviceCustomerAccountMapRepository.findDeviceCustomerAccountMapByCustomerCode(customerCode);
		List<DeviceCustomerAccountMap> filterList = new ArrayList<DeviceCustomerAccountMap>();
		if (!list.isEmpty()) {
			for (DeviceCustomerAccountMap dca : list) {
				String deviceSno = dca.getDeviceSno();
				if (StringUtils.isNotBlank(deviceSno)) {
					if (sno.equals(dca.getDeviceSno())) {
						filterList.add(dca);
					}
				}
			}
		}
		return filterList;
	}

	@Override
	public DeviceCustomerAccountMap getDeviceCustomerAccountMapByDeviceCode(String deviceCode) {
		return deviceCustomerAccountMapRepository.getByYstenId(deviceCode);
	}
	
	@Override
	public boolean deleteDeviceCustomerAccountMap(Long deviceId) {
		boolean flag=false;
    	List<DeviceCustomerAccountMap>  list=deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceId(deviceId);
    	for(int i=0;i<list.size();i++){
    		DeviceCustomerAccountMap map=list.get(i);
    		flag=deviceCustomerAccountMapRepository.delete(map);
    	}
     return flag;   
	}
	
	@Override
    public boolean deleteDeviceCustomerAccountMap(DeviceCustomerAccountMap map) {
        Date curDate = new Date();
        Device oldDevice = this.deviceRepository.getDeviceById(map.getDeviceId());
        // set the old device to NONACTIVATED[NOTUSE].
        oldDevice.setState(Device.State.NONACTIVATED);
        oldDevice.setBindType(Device.BindType.UNBIND);
        oldDevice.setIsLock(LockType.LOCK);
        oldDevice.setStateChangeDate(curDate);
        oldDevice.setIsSync(SyncType.WAITSYNC);
        if (!deviceRepository.updateDevice(oldDevice)) {
            return false;
        }
        // delete relation.
        deviceCustomerAccountMapRepository.delete(map);
        // insert record.
        CustomerDeviceHistory history = new CustomerDeviceHistory();
//        history.setCustomerId(map.getCustomerId());
        history.setCustomerCode(map.getCustomerCode());
        history.setDeviceId(oldDevice.getId());
        history.setDeviceCode(oldDevice.getCode());
//        history.setOldDeviceCode(oldDevice.getCode());
//        history.setOldDeviceId(oldDevice.getId());
        history.setCreateDate(curDate);
        if (!this.deviceRepository.saveCustomerDeviceHistory(history)) {
            return false;
        }
        return true;
    }

	@Override
	public Customer getCustomerByYstenId(String ystenId) {
		 DeviceCustomerAccountMap dcm = this.deviceCustomerAccountMapRepository.getByYstenId(ystenId);
         if(dcm != null){
             Customer customer = this.customerRepository.getCustomerByUserId(dcm.getUserId());
             if (null == customer || (null != customer.getServiceStop() && new Date().after(customer.getServiceStop()))){
                 return null;
             }
             return customer;
         }
         return null;
	}
	
}
