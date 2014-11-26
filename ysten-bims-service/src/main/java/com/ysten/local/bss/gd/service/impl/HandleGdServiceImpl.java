package com.ysten.local.bss.gd.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysten.area.domain.Area;
import com.ysten.area.repository.IAreaRepository;
import com.ysten.boss.systemconfig.service.SystemConfigService;
import com.ysten.local.bss.device.api.domain.response.DefaultResponse;
import com.ysten.local.bss.device.bean.NumberGenerator;
import com.ysten.local.bss.device.domain.Customer;
import com.ysten.local.bss.device.domain.Customer.CustomerType;
import com.ysten.local.bss.device.domain.Device;
import com.ysten.local.bss.device.domain.DeviceCustomerAccountMap;
import com.ysten.local.bss.device.domain.LockType;
import com.ysten.local.bss.device.repository.ICustomerRepository;
import com.ysten.local.bss.device.repository.IDeviceCustomerAccountMapRepository;
import com.ysten.local.bss.device.repository.IDeviceRepository;
import com.ysten.local.bss.gd.service.IGdRecordService;
import com.ysten.local.bss.gd.service.IHandleGdService;
import com.ysten.local.bss.util.DateUtils;

@Service
public class HandleGdServiceImpl implements IHandleGdService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HandleGdServiceImpl.class);

    private static final String ACTIVE_STATUS = "1";

    @Autowired
    private SystemConfigService systemConfigService;
    @Autowired
    private IGdRecordService recordService;
    @Autowired
    private IDeviceRepository deviceRepository;
    @Autowired
    private ICustomerRepository customerRepository;
    @Autowired
    private IDeviceCustomerAccountMapRepository deviceCustomerAccountMapRepository;
    @Autowired
    private IAreaRepository areaRepository;


    @Override
    public DefaultResponse bindOrRebindDevice(String userId, String deviceId, String password) throws Exception{
        Device device = this.deviceRepository.getDeviceByCode(deviceId);
        if(device == null){
            return new DefaultResponse(false, "Device Not Exists");
        }
        if(!Device.State.NONACTIVATED.equals(device.getState())){
            return new DefaultResponse(false, "Device Can Not Use");
        }
        Customer customer = this.customerRepository.getCustomerByUserId(userId);
        if(customer != null){
            return new DefaultResponse(false, "UserId Exists");
        }
        customer = this.saveCustomer(userId, password);
        this.updateDevice(device);
        this.saveDeviceCustomerMap(customer, device);
        return new DefaultResponse("SUCCESS");
    }

    private Customer saveCustomer(String userId, String password) {
        Customer customer = new Customer();
        customer.setCode(NumberGenerator.getNextCode());
        customer.setUserId(userId);
        customer.setPassword(password);
        customer.setState(Customer.State.NORMAL);
        customer.setCustomerType(CustomerType.PERSONAL);
        String areaId = this.systemConfigService.getSystemConfigByConfigKey("areaId");
        Area area = this.areaRepository.getAreaById(Long.parseLong(areaId));
        customer.setArea(area);
        customer.setStartDate(new Date());
        customer.setEndDate(DateUtils.getDate("20991231235959","yyyyMMddHHmmss"));
        customer.setCreateDate(new Date());
        customer.setServiceStop(DateUtils.getDate("20991231235959","yyyyMMddHHmmss"));
        customer.setCustomerType(CustomerType.PERSONAL);
        this.customerRepository.saveCustomer(customer);
        return customer;
    }

    private void updateDevice(Device device) {
        device.setState(Device.State.ACTIVATED);
        device.setBindType(Device.BindType.BIND);
        device.setIsLock(LockType.UNLOCKED);
        device.setStateChangeDate(new Date());
        device.setActivateDate(new Date());
        this.deviceRepository.updateDevice(device);
    }

    private void saveDeviceCustomerMap(Customer customer, Device device) {
        DeviceCustomerAccountMap map = new DeviceCustomerAccountMap();
        map.setCustomerId(customer.getId());
        map.setCustomerCode(customer.getCode());
        map.setUserId(customer.getUserId());
        map.setDeviceId(device.getId());
        map.setDeviceCode(device.getCode());
        map.setDeviceSno(device.getSno());
        map.setReplacement(0);
        this.deviceCustomerAccountMapRepository.saveDeviceCustomerAccountMap(map);
    }

}
