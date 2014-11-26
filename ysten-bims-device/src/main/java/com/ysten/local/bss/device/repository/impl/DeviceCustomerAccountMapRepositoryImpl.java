package com.ysten.local.bss.device.repository.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysten.cache.annotation.KeyParam;
import com.ysten.cache.annotation.MethodCache;
import com.ysten.cache.annotation.MethodFlush;
import com.ysten.local.bss.device.domain.DeviceCustomerAccountMap;
import com.ysten.local.bss.device.repository.IDeviceCustomerAccountMapRepository;
import com.ysten.local.bss.device.repository.mapper.DeviceCustomerAccountMapMapper;
import com.ysten.utils.date.DateUtil;

/**
 * @author cwang
 * @version 2014-4-9 下午4:31:47
 * 
 */
@Repository
public class DeviceCustomerAccountMapRepositoryImpl implements IDeviceCustomerAccountMapRepository {
	private static final String BASE_DOMAIN = "ysten:local:bss:device:";
	private static final String DEVICE_CODE = BASE_DOMAIN + "DeviceCustomerAccountMap:deviceCode:";
	private static final String YSTEN_ID =  BASE_DOMAIN + "DeviceCustomerAccountMap:ystenId:";
	private static final String DEVICE_ID = BASE_DOMAIN + "DeviceCustomerAccountMap:deviceId:";
	private static final String DEVICE_SNO = BASE_DOMAIN + "DeviceCustomerAccountMap:deviceSno:";
	private static final String ID = BASE_DOMAIN + "DeviceCustomerAccountMap:id:";
	@Autowired
	DeviceCustomerAccountMapMapper deviceCustomerAccountMapMapper;

	@Override
	public List<DeviceCustomerAccountMap> findDeviceCustomerAccountMapByCustomerCode(String customerCode) {
		return deviceCustomerAccountMapMapper.getByCustomerCode(customerCode);
	}

    @Override
    @MethodFlush(keys = {DEVICE_CODE + "#{map.deviceCode}", DEVICE_ID + "#{map.deviceId}",ID+"#{map.id}",DEVICE_SNO+"#{map.deviceSno}"
    })
    public boolean delete(@KeyParam("map")DeviceCustomerAccountMap map) {
        return 1 == deviceCustomerAccountMapMapper.deleteDeviceCustomerAccountMap(map.getDeviceId());
    }

    @Override
    @MethodFlush(keys = {DEVICE_CODE + "#{map.deviceCode}", DEVICE_ID + "#{map.deviceId}",ID+"#{map.id}",DEVICE_SNO+"#{map.deviceSno}"
    })
    public boolean deleteDeviceCustomerAccountMap(@KeyParam("map")DeviceCustomerAccountMap map) {
        return 1 == deviceCustomerAccountMapMapper.delete(map.getId());
    }
    
    @Override
    @MethodFlush(keys = {DEVICE_CODE + "#{map.deviceCode}", DEVICE_ID + "#{map.deviceId}",ID+"#{map.id}",DEVICE_SNO+"#{map.deviceSno}"
    })
    public boolean updateDeviceCustomerAccountMap(@KeyParam("map")DeviceCustomerAccountMap map) {
        map.setYstenId(map.getDeviceCode());
        return (1 == this.deviceCustomerAccountMapMapper.update(map));
    }
    
    @Override
    public List<DeviceCustomerAccountMap> getDeviceCustomerAccountMapByDeviceId(Long deviceId) {
    	return deviceCustomerAccountMapMapper.getByDeviceId(deviceId);
    }
    
    @Override
    public boolean saveDeviceCustomerAccountMap(DeviceCustomerAccountMap deviceCustomerAccountMap) {
        int i = this.deviceCustomerAccountMapMapper.save(deviceCustomerAccountMap);
        return (1 == i);
    }

    
    @Override
    public List<DeviceCustomerAccountMap> getAllDeviceCustomerAccountMapCreatedLastDay() {
        Map<String, String> params = new HashMap<String, String>();
        String lastDay = DateUtil.convertDateToString("yyyy-MM-dd", reduceDays(new Date(), 1));
        params.put("start", lastDay + " 00:00:00");
        params.put("end", lastDay + " 23:59:59");
        params.put("source", "OTHERS");
        return this.deviceCustomerAccountMapMapper.getCreatedLastDay(params);
    }
    
    @Override
    public Long getTotalCountByCityAndDeviceType(String cityId, String deviceTypeCode) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("cityId", cityId);
        params.put("deviceTypeCode", deviceTypeCode);
        params.put("source", "OTHERS");
        return deviceCustomerAccountMapMapper.getTotalCountByCityAndDeviceType(params);
    }
    
    public Date reduceDays(Date date, int offset) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - offset);// 让日期加位移量
        return calendar.getTime();
    }
    
    @Override
    @MethodCache(key = ID + "#{id}")
    public DeviceCustomerAccountMap getDeviceCustomerAccountMapById(Long id) {
        return this.deviceCustomerAccountMapMapper.getById(id);
    }
    
    @Override
    @MethodCache(key = DEVICE_CODE + "#{deviceCode}")
    public DeviceCustomerAccountMap getDeviceCustomerAccountMapByDeviceCode(@KeyParam("deviceCode")String deviceCode) {
        return this.deviceCustomerAccountMapMapper.getByDeviceCode(deviceCode);
    }
    
	@Override
	public List<DeviceCustomerAccountMap> getDeviceCustomerAccountMapByCustomerId(Long customerId) {
		return deviceCustomerAccountMapMapper.getByCustomerId(customerId);
	}

	@Override
	public DeviceCustomerAccountMap getFirstReplacmentMapUnderCustomerCust(String custId) {
		return deviceCustomerAccountMapMapper.getFirstReplacmentMapUnderCustomerCust(custId);
	}

	@Override
	@MethodCache(key = YSTEN_ID + "#{ystenId}")
	public DeviceCustomerAccountMap getByYstenId(@KeyParam("ystenId")String ystenId) {
		return deviceCustomerAccountMapMapper.getByYstenId(ystenId);
	}

    @Override
    public DeviceCustomerAccountMap getDeviceCustomerAccountMapByDeviceCodeAndUserId(String deviceCode, String userId) {
        return deviceCustomerAccountMapMapper.getDeviceCustomerAccountMapByDeviceCodeAndUserId(deviceCode, userId);
    }
}
