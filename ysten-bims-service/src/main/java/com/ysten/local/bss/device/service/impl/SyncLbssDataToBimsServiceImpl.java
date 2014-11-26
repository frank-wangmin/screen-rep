package com.ysten.local.bss.device.service.impl;

import com.ysten.area.domain.Area;
import com.ysten.area.repository.IAreaRepository;
import com.ysten.boss.systemconfig.service.SystemConfigService;
import com.ysten.core.AppErrorCodeException;
import com.ysten.local.bss.bean.tb.SyncDataByDevice;
import com.ysten.local.bss.device.bean.NumberGenerator;
import com.ysten.local.bss.device.domain.*;
import com.ysten.local.bss.device.repository.ICityRepository;
import com.ysten.local.bss.device.repository.IConfigRepository;
import com.ysten.local.bss.device.repository.ICustomerRepository;
import com.ysten.local.bss.device.repository.IDeviceRepository;
import com.ysten.local.bss.device.service.ISyncLbssDataToBimsService;
import com.ysten.local.bss.util.YstenIdUtils;
import com.ysten.message.queue.QueueMessageProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class SyncLbssDataToBimsServiceImpl implements ISyncLbssDataToBimsService {
    private Logger LOGGER = LoggerFactory.getLogger(SyncLbssDataToBimsServiceImpl.class);

    @Autowired
    private QueueMessageProducer syncDataProducer;

    @Autowired
    protected SystemConfigService systemConfigService;
    @Autowired
    protected IDeviceRepository deviceRepository;
    @Autowired
    protected ICustomerRepository customerRepository;
    @Autowired
    protected ICityRepository cityRepository;
    @Autowired
    protected IAreaRepository areaRepository;
    @Autowired
    protected IConfigRepository configRepository;

    @Override
    public void saveSyncData(List<SyncDataByDevice> syncDataList) throws Exception{
        try {
            for (SyncDataByDevice syncData : syncDataList) {
                syncDataProducer.publish(syncData);
            }
        } catch (Exception e) {
            AppErrorCodeException appErrorCodeException = new AppErrorCodeException("syncDataProducer publish data Error", e);
            appErrorCodeException.setErrorCode(905);
            throw appErrorCodeException;
        }
    }

    @Override
    public void saveAcceptDataByDevice(SyncDataByDevice syncData) throws Exception {
        String type = syncData.getType().trim();
        Device device = syncData.getDevice();
        Customer customer = syncData.getCustomer();

        //设置用户状态和设备状态
        Device.State dState = Device.State.ACTIVATED;
        Customer.State cState = Customer.State.NORMAL;
        if (type.equalsIgnoreCase("OPEN")) {
            dState = Device.State.ACTIVATED;
            cState = Customer.State.NORMAL;
        } else if (type.equalsIgnoreCase("CANCEL")) {
            dState = Device.State.NOTUSE;
            cState = Customer.State.CANCEL;
        } else if (type.equalsIgnoreCase("ACTIVATED")) {
            dState = Device.State.ACTIVATED;
            cState = Customer.State.NORMAL;
        } else if (type.equalsIgnoreCase("PAUSED")) {
            dState = Device.State.NONACTIVATED;
            cState = Customer.State.SUSPEND;
        } else if (type.equalsIgnoreCase("RECOVER")) {
            dState = Device.State.ACTIVATED;
            cState = Customer.State.NORMAL;
        }
        Device bimsDevice = null;
        try{
            bimsDevice = this.dealWithDevice(device, dState);
        }catch(Exception e){
            throw new Exception("dealWithDevice error "+e.getMessage(), e);
        }

        Customer bimsCustomer = null;
        try{
            bimsCustomer =this.dealWithCustomer(customer, bimsDevice, cState, type);
        }catch(Exception e){
            throw new Exception("dealWithCustomer error "+e.getMessage(), e);
        }

        try{
            this.dealWithRelationship(bimsCustomer, bimsDevice);
        }catch(Exception e){
            throw new Exception("dealWithRelationship error "+e.getMessage(), e);
        }
    }

    public Device dealWithDevice(Device device, Device.State dState) throws Exception {
        Device _device = null;
        if (null != device) {
            //判断bims中是否存在该设备
            _device = this.deviceRepository.getDeviceByCode(device.getCode().trim());
            if(null == _device){
                throw new Exception("Device not found in bims! device code is: " + device.getCode());
            }

            //更新设备的区域信息
            String disCode = "0000";
            if(null != device.getArea()){
                String deviceAreaName = device.getArea().getName();
                String areaDistCode = device.getArea().getDistCode();
                Long areaId = this.cityRepository.getAreaIdByDistrictCode(areaDistCode);
                Area area = this.areaRepository.getAreaById(areaId);
                if (null != area) {
                    device.setArea(area);
                    disCode = area.getDistCode();
                }
            }
            //更新设备city信息
            if(null != device.getCity()){
                String deviceCityName = device.getCity().getName();
                String cityDistCode = device.getCity().getDistCode();
                City city = this.cityRepository.getCityByDistCode(cityDistCode);
                if (null != city) {
                    device.setCity(city);
                    disCode = city.getDistCode();
                }
                device.setDistrictCode(disCode);
            }
            //更新设备的sp信息
            String spName = device.getSpDefine().getName();
            SpDefine bimsSpDefine = null;
            if(null !=  spName){
                bimsSpDefine = this.configRepository.getSpDefineByName(spName);
            }
            if (null != bimsSpDefine) {
                device.setSpDefine(bimsSpDefine);
            }
            //更新bims中设备
            if(null != device.getSno()){
                _device.setSno(device.getSno().trim());
            }
            if(null != device.getMac()){
                _device.setMac(device.getMac().trim());
            }
            _device.setCreateDate(device.getCreateDate());
            _device.setActivateDate(device.getActivateDate());
            _device.setStateChangeDate(device.getStateChangeDate());
            _device.setExpireDate(device.getExpireDate());
            _device.setBindType(device.getBindType());
            _device.setDistrictCode(device.getDistrictCode());
            if(null != device.getIpAddress()){
                _device.setIpAddress(device.getIpAddress().trim());
            }
            _device.setArea(device.getArea());
            _device.setCity(device.getCity());
            _device.setSpDefine(device.getSpDefine());
//                _device.setState(dState);
            _device.setState(device.getState());
            this.deviceRepository.updateDevice(_device);
        }
        return _device;
    }

    public Customer dealWithCustomer(Customer customer, Device bimsDevice, Customer.State cState, String type) throws Exception {
        if (null != customer) {
            //更新用户区域信息
            if(null != customer.getArea()){
                String customerAreaName = customer.getArea().getName();
                Area area = this.areaRepository.getAreaByNameOrCode(customerAreaName, "");
                if (null != area) {
                    customer.setArea(area);
                }
            }
            //更新用户city信息
            if(null != customer.getRegion()){
                String customerCityName = customer.getRegion().getName();
                City city = this.cityRepository.getCityByName(customerCityName.trim());
                if (null != city) {
                    customer.setRegion(city);
                }
            }

            //只传用户，不传设备
            if (null == bimsDevice) {
                List<Customer> bimsCustomerList =
                        this.customerRepository.getCustomerByUserIdAndOuterCode(customer.getUserId().trim(), customer.getCode().trim());
                //判断bims中是否存在该用户
                if (!CollectionUtils.isEmpty(bimsCustomerList)){
                    for(Customer bimsCustomer: bimsCustomerList){
                        customer.setId(bimsCustomer.getId());
                        customer.setOuterCode(customer.getCode());
                        customer.setCode(bimsCustomer.getCode());
//                            customer.setState(cState);
                        this.customerRepository.updateCustomer(customer);
                    }
                }
                else{
                    customer.setOuterCode(customer.getCode());
                    customer.setCode(NumberGenerator.getNextCode());
//                    customer.setState(cState);
                    this.customerRepository.saveCustomer(customer);
                    long customerId = this.customerRepository.getCustomerByCode(customer.getCode()).getId();
                    customer.setId(customerId);
                }
            }
            //既传设备，也传用户
            else {
                Customer deviceCustomer = null;
                if (null != bimsDevice) {
                    if(null != bimsDevice.getCustomerId()){
                        deviceCustomer = this.customerRepository.getCustomerById(bimsDevice.getCustomerId());
                    }
                }

                //删除bims中不关联设备的用户
                List<Customer> bimsCustomerList =
                        this.customerRepository.getCustomerByUserIdAndOuterCode(customer.getUserId().trim(), customer.getCode().trim());
                if (!CollectionUtils.isEmpty(bimsCustomerList)){
                    for(Customer bimsCustomer: bimsCustomerList){
                        //判断该用户是否关联设备
                        Device dc = this.deviceRepository.getDeviceByCustomerId(bimsCustomer.getId());
                        if(null == dc){
                            this.customerRepository.delete(bimsCustomer.getId());
                        }
                    }
                }

                //更新设备关联用户
                if(null != deviceCustomer){
                    customer.setId(deviceCustomer.getId());
                    customer.setOuterCode(customer.getCode());
                    customer.setCode(deviceCustomer.getCode());
                    //销户时，更新为虚拟用户
                    if(type.equalsIgnoreCase("CANCEL")){
                        //记录历史表
                        this.saveCustomerDeviceHistory(customer, bimsDevice);
                        //userId置为Code
                        customer.setUserId(deviceCustomer.getCode());
                    }
                    this.customerRepository.updateCustomer(customer);
                }else{
                    customer.setOuterCode(customer.getCode());
                    customer.setCode(NumberGenerator.getNextCode());
//                    customer.setState(cState);
                    this.customerRepository.saveCustomer(customer);
                    long customerId = this.customerRepository.getCustomerByCode(customer.getCode()).getId();
                    customer.setId(customerId);
                }
            }
        }else{
            //只传设备，未传用户
            if(null != bimsDevice){
                Customer deviceCustomer = null;
                if (null != bimsDevice) {
                    if(null != bimsDevice.getCustomerId()){
                        deviceCustomer = this.customerRepository.getCustomerById(bimsDevice.getCustomerId());
                    }
                }
                if(null != deviceCustomer){
                    deviceCustomer.setState(cState);
                    //销户时，更新为虚拟用户
                    if(type.equalsIgnoreCase("CANCEL")){
                        //记录历史表
                        this.saveCustomerDeviceHistory(deviceCustomer, bimsDevice);
                        //userId置为Code
                        deviceCustomer.setUserId(deviceCustomer.getCode());
                    }
                    this.customerRepository.updateCustomer(deviceCustomer);
                    customer = deviceCustomer;
                }
            }
        }
        return customer;
    }

    private void saveCustomerDeviceHistory(Customer customer, Device device){
        CustomerDeviceHistory customerDeviceHistory = new CustomerDeviceHistory();

        customerDeviceHistory.setCreateDate(new Date());
        customerDeviceHistory.setUserId(customer.getUserId());
        customerDeviceHistory.setCustomerCode(customer.getCode());
        customerDeviceHistory.setCustomerOuterCode(customer.getOuterCode());
        customerDeviceHistory.setCustomerCreateDate(customer.getCreateDate());
        customerDeviceHistory.setCustomerActivateDate(customer.getActivateDate());
        customerDeviceHistory.setLoginName(customer.getLoginName());
        customerDeviceHistory.setPhone(customer.getPhone());
        customerDeviceHistory.setArea(customer.getArea());
        customerDeviceHistory.setCity(customer.getRegion());
        customerDeviceHistory.setYstenId(device.getYstenId());
        customerDeviceHistory.setDeviceId(device.getId());
        customerDeviceHistory.setDeviceCode(device.getCode());
        customerDeviceHistory.setDeviceCreateDate(device.getCreateDate());
        customerDeviceHistory.setDeviceActivateDate(device.getActivateDate());
        customerDeviceHistory.setDeviceSno(device.getSno());
        customerDeviceHistory.setDeviceState(device.getState());
        customerDeviceHistory.setDescription(device.getDescription());

        this.deviceRepository.saveCustomerDeviceHistory(customerDeviceHistory);
    }

    public void dealWithRelationship(Customer bimsCustomer, Device bimsDevice) throws Exception {
        if (null != bimsDevice && null != bimsCustomer) {
            long customerId = bimsCustomer.getId();
            if (null == bimsDevice.getCustomerId() || customerId != bimsDevice.getCustomerId()) {
                bimsDevice.setCustomerId(customerId);
                this.deviceRepository.updateDevice(bimsDevice);
            }
        }
    }

}
