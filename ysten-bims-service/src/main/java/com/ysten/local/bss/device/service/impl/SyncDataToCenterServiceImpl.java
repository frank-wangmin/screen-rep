package com.ysten.local.bss.device.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ysten.local.bss.device.domain.*;
import com.ysten.local.bss.device.repository.*;
import com.ysten.local.bss.util.YstenIdUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ysten.area.domain.Area;
import com.ysten.area.repository.IAreaRepository;
import com.ysten.boss.systemconfig.service.SystemConfigService;
import com.ysten.local.bss.bean.tb.SyncData;
import com.ysten.local.bss.bean.tb.SyncDataByDevice;
import com.ysten.local.bss.device.api.domain.response.DefaultResponse;
import com.ysten.local.bss.device.api.domain.response.IResponse;
import com.ysten.local.bss.device.bean.NumberGenerator;
import com.ysten.local.bss.device.domain.Customer.CustomerType;
import com.ysten.local.bss.device.domain.Device.BindType;
import com.ysten.local.bss.device.domain.Device.SyncType;
import com.ysten.local.bss.device.exception.ParamIsEmptyException;
import com.ysten.local.bss.device.exception.ParamIsInvalidException;
import com.ysten.local.bss.device.service.ISyncDataToCenterService;
import com.ysten.local.bss.util.HttpUtils;
import com.ysten.local.bss.util.HttpUtils.HttpResponseWrapper;
import com.ysten.utils.code.MD5;

@Service
public class SyncDataToCenterServiceImpl implements ISyncDataToCenterService {
    private Logger LOGGER = LoggerFactory.getLogger(SyncDataToCenterServiceImpl.class);
    
	@Autowired
	private IDeviceCustomerAccountMapRepository deviceCustomerAccountMapRepository;
	@Autowired
	private ICustomerCustRepository customerCustRepository;
	@Autowired
	private ICustomerGroupRepository customerGroupRepository;
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

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    /**
     *  根据用户状态定时同步数据到中心
     * @author chenxiang
     * @date 2014-8-15 下午4:59:50 
     */
    @Override
    public boolean syncDataToCenter() throws Exception {
        String syncDataToCenterNum = this.systemConfigService.getSystemConfigByConfigKey("syncDataToCenterNum");
        if (StringUtils.isBlank(syncDataToCenterNum)) {
            throw new Exception("请先维护配置表中的syncDataToCenterNum字段");
        }
        Integer syncToCenterNum = null;
        try {
        	syncToCenterNum = Integer.parseInt(syncDataToCenterNum);
        } catch (Exception e) {
            throw new Exception("请正确配置配置表中的syncDataToCenterNum字段，必须为数字");
        }
        
        // 获取待推送的用户数据
        List<Customer> customers = this.customerRepository.getAllCustomerByIsSync(syncToCenterNum);
        for(Customer customer : customers){
            SyncData syncData = this.getNeedSyncData(customer);
            if (syncData == null) {
                customer.setIsSync(SyncType.SYNCED);
                this.customerRepository.updateCustomer(customer);
                continue;
            }
            String syncDataToCenterUrl = this.systemConfigService.getSystemConfigByConfigKey("syncDataToCenterUrl");
            String jsonData = new GsonBuilder().create().toJson(syncData);
            LOGGER.info("Sync To Center Url: " + syncDataToCenterUrl);
            LOGGER.info("Sync To Center Data: " + jsonData);
            HttpResponseWrapper wrapper = HttpUtils.doPost(syncDataToCenterUrl, jsonData, "UTF-8");
            LOGGER.info("Sync To Center Return :" + wrapper.getResponse());
            if (wrapper.getHttpStatus() == 200) {
                String jsonRsp = wrapper.getResponse();
                DefaultResponse rsp = new GsonBuilder().create().fromJson(jsonRsp, DefaultResponse.class);
                if (StringUtils.equalsIgnoreCase(rsp.getResult(), "true")) {
                    customer.setIsSync(SyncType.SYNCED);
                }else {
                    customer.setIsSync(SyncType.FAILED);
                }
                this.customerRepository.updateCustomer(customer);
            }
        }
        return true;
    }

    /**
     * 获取需要同步的数据
     * @author chenxiang
     * @date 2014-8-13 下午3:10:34 
     * @param customer
     * @return SyncData
     */
    private SyncData getNeedSyncData(Customer customer) {
    	SyncData syncData = null;
        List<DeviceCustomerAccountMap> maps = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByCustomerId(customer.getId());
        
        CustomerCust customerCust= this.customerCustRepository.getCustomerCustByCustId(customer.getCustId());
        
        CustomerGroup customerGroup = null;
        if(customerCust!=null && customerCust.getGroupId()!=null && !"".equals(customerCust.getGroupId())){
            customerGroup = this.customerGroupRepository.getCustomerGroupByGroupId(customerCust.getGroupId());
        }
        
        
        syncData = new SyncData(customer, maps, customerCust,customerGroup);
        if(maps == null || maps.isEmpty()){
        	syncData.setDevices(null);
        }else{
            for (DeviceCustomerAccountMap map : maps) {
                Device device = this.deviceRepository.getDeviceById(map.getDeviceId());
                List<Device> devices = syncData.getDevices();
                if (devices == null) {
                    devices = new ArrayList<Device>();
                    syncData.setDevices(devices);
                }
                devices.add(device);
            }
        }
        return syncData;
    }

	/**
	 * 中心接受需要同步的数据
	 * @author chenxiang
	 * @date 2014-8-13 下午3:21:26 
	 */
	@Override
	public IResponse acceptData(String jsonData) {
		SyncData syncData = new GsonBuilder().create().fromJson(jsonData, SyncData.class);
	        Customer customer = syncData.getCustomer();
	        List<DeviceCustomerAccountMap> maps = syncData.getMaps();
	        List<Device> devices = syncData.getDevices();
	        CustomerCust customerCust = syncData.getCustomerCust();
	        CustomerGroup customerGroup = syncData.getCustomerGroup();
	        this.saveCustomer(customer);
	        this.saveDeviceCustomerMaps(customer, maps, devices);
	        this.saveCustomerCust(customerCust);
	        this.saveCustomerGroup(customerGroup);
	        return new DefaultResponse("Accept Data Success!");
	}


	/**
	 * 保存用户信息
	 * @author chenxiang
	 * @date 2014-8-13 下午3:36:04 
	 * @param customer
	 */
	private void saveCustomer(Customer customer) {
        Customer _customer = this.customerRepository.getCustomerByCode(customer.getCode());
        if (_customer == null) {
            this.customerRepository.saveCustomer(customer);
        } else {
            customer.setId(_customer.getId());
            this.customerRepository.updateCustomer(customer);
        }
    }
    
    
    /**
     * 保存设备及关系表信息
     * @author chenxiang
     * @date 2014-8-13 下午3:36:23 
     * @param customer
     * @param maps
     * @param devices
     */
    private void saveDeviceCustomerMaps(Customer customer, List<DeviceCustomerAccountMap> maps, List<Device> devices) {
        List<DeviceCustomerAccountMap> _maps = this.deviceCustomerAccountMapRepository.findDeviceCustomerAccountMapByCustomerCode(customer.getCode());
        // 首先根据customer信息删除该用户绑定的设备信息
        if(_maps != null && !_maps.isEmpty()){
            for (DeviceCustomerAccountMap _map : _maps) {
                Device device = this.deviceRepository.getDeviceByCode(_map.getDeviceCode());
                if(device!=null){
                	device.setState(Device.State.NONACTIVATED);
                    device.setBindType(BindType.UNBIND);
                    device.setIsLock(LockType.LOCK);
                    device.setIsSync(SyncType.WAITSYNC);
                    this.deviceRepository.updateDevice(device);
                }
                
                deviceCustomerAccountMapRepository.delete(_map);
                
            }
        }
        if(maps == null || maps.isEmpty()){
            return;
        }
        Map<String, Device> devicesMap = this.convertDeviceListToMap(devices);
        // 重新插入设备绑定信息,先插入设备信息，更新map关系中的设备id，最后插入map关系
        for (DeviceCustomerAccountMap map : maps) {
            Device device = devicesMap.get(map.getDeviceCode());
            if (device == null) {
                continue;
            }
            Device _device = this.deviceRepository.getDeviceByCode(device.getCode());
            if (_device == null) {
                this.deviceRepository.saveDevice(device);
            } else {
                device.setId(_device.getId());
                this.deviceRepository.updateDevice(device);
            }
            map.setDeviceId(device.getId());
            map.setCustomerId(customer.getId());
            this.deviceCustomerAccountMapRepository.saveDeviceCustomerAccountMap(map);
        }
    }
   
    
    private Map<String, Device> convertDeviceListToMap(List<Device> devices) {
        if(devices == null || devices.isEmpty()){
            return null;
        }
		Map<String, Device> map = null;
		for(Device device : devices){
			if(map == null){
				map = new HashMap<String, Device>();
			}
			map.put(device.getCode(), device);
		}
		return map;
	}

    
    /**
	 * @author chenxiang
	 * @date 2014-8-13 下午3:37:08 
	 * @param customerCust
	 */
	private void saveCustomerCust(CustomerCust customerCust) {
		if(customerCust!=null){
			this.customerCustRepository.deleteCustomerCustByCustId(customerCust.getCustId());
			this.customerCustRepository.saveCustomerCust(customerCust);
		}
	}
	
    
    /**
     * @author chenxiang
     * @date 2014-8-13 下午3:37:05 
     * @param customerGroup
     */
    private void saveCustomerGroup(CustomerGroup customerGroup) {
    	 if(customerGroup != null){
        	 this.customerGroupRepository.deleteCustomerGroupByGroupId(customerGroup.getGroupId());
        	 this.customerGroupRepository.saveCustomerGroup(customerGroup);
         }
	}

	/**
	 *  根据设备状态定时同步数据到中心
	 * @author chenxiang
	 * @date 2014-8-15 下午4:59:57 
	 */
	@Override
	public boolean syncDataToCenterByDevice() throws Exception {
		String syncDataToCenterNum = this.systemConfigService.getSystemConfigByConfigKey("syncDataToCenterNum");
        if (StringUtils.isBlank(syncDataToCenterNum)) {
            throw new Exception("请先维护配置表中的syncDataToCenterNum字段");
        }
        Integer syncToCenterNum = null;
        try {
        	syncToCenterNum = Integer.parseInt(syncDataToCenterNum);
        } catch (Exception e) {
            throw new Exception("请正确配置配置表中的syncDataToCenterNum字段，必须为数字");
        }
        
        // 获取待推送的设备数据
        List<Device> devices = this.deviceRepository.getAllDeviceByIsSync(syncToCenterNum);
        for(Device device : devices){
        	SyncDataByDevice syncDataByDevice = this.getgetNeedSyncDataByDevice(device);
            String syncDataToCenterUrl = this.systemConfigService.getSystemConfigByConfigKey("syncDataToCenterUrl");
            String jsonData = new GsonBuilder().create().toJson(syncDataByDevice);
            
            LOGGER.info("Sync To Center Url: " + syncDataToCenterUrl);
            LOGGER.info("Sync To Center Data: " + jsonData);
            HttpResponseWrapper wrapper = HttpUtils.doPost(syncDataToCenterUrl, jsonData, "UTF-8");
            LOGGER.info("Sync To Center Return :" + wrapper.getResponse());
            if (wrapper.getHttpStatus() == 200) {
                String jsonRsp = wrapper.getResponse();
                DefaultResponse rsp = new GsonBuilder().create().fromJson(jsonRsp, DefaultResponse.class);
                if (StringUtils.equalsIgnoreCase(rsp.getResult(), "true")) {
                	device.setIsSync(SyncType.SYNCED);
                }else {
                	device.setIsSync(SyncType.FAILED);
                }
                this.deviceRepository.updateDevice(device);
            }
        }
        
        return true;
	}

	
	/**
	 * @author chenxiang
	 * @date 2014-8-15 下午5:17:53 
	 * @param device
	 * @return SyncDataByDevice
	 */
	private SyncDataByDevice getgetNeedSyncDataByDevice(Device device){
        String type = null;
		SyncDataByDevice syncDataByDevice=null;
		DeviceCustomerAccountMap map = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceCode(device.getCode());
		Customer customer=null;
		if(map!=null && map.getCustomerId()!=null && !"".equals(map.getCustomerId())){
			customer = this.customerRepository.getCustomerById(map.getCustomerId());
		}
		
		syncDataByDevice=new SyncDataByDevice(type, customer, map, device);
		
		return syncDataByDevice;
	}
	
	
	
	/**
	 * 接受根据设备状态同步的数据到中心
	 * @author chenxiang
	 * @date 2014-8-15 下午5:30:50 
	 * @param jsonData
	 * @return
	 */
	@Override
	public void saveAcceptDataByDevice(String jsonData) throws Exception {

        List<SyncDataByDevice> syncDataList = gson.fromJson(jsonData, new TypeToken<List<SyncDataByDevice>>(){}.getType());
        if (!CollectionUtils.isEmpty(syncDataList)) {
            for (SyncDataByDevice syncData : syncDataList) {
                String type = syncData.getType().trim();
                Device device = syncData.getDevice();
                Customer customer = syncData.getCustomer();

                Device bimsDevice = this.dealWithDevice(device);
                Customer bimsCustomer = this.dealWithCustomer(customer, bimsDevice, type);
                this.dealWithRelationship(bimsCustomer, bimsDevice, type);
            }
        }
	}

    public Device dealWithDevice(Device device) throws Exception{
        if(null != device){
            //更新设备的区域信息
            String deviceAreaName = device.getArea().getName().trim();
            String deviceCityName = device.getCity().getName().trim();

            String disCode = "0000";

            Area area = this.areaRepository.getAreaByNameOrCode(deviceAreaName, "");
            if(null != area){
                device.setArea(area);
                disCode = area.getDistCode().trim();
            }
            City city =  this.cityRepository.getCityByName(deviceCityName);
            if(null != city){
                device.setCity(city);
                disCode = city.getDistCode().trim();
            }
            device.setDistrictCode(disCode);

            //更新设备的sp信息
            String spName = device.getSpDefine().getName().trim();
            String spId = device.getSpDefine().getId().toString();
            SpDefine bimsSpDefine = this.configRepository.getSpDefineByName(spName);
            if(null != bimsSpDefine){
                device.setSpDefine(bimsSpDefine);
                spId = bimsSpDefine.getId().toString();
            }

            //获取设备类型
            String deviceTypeStr = "0000";
            DeviceType deviceType = device.getDeviceType();
            if(null != device.getDeviceType()){
                deviceTypeStr = device.getDeviceType().getCode().trim();
            }

            //bims不存在该设备，则新增，bims中存在该设备，则更新。
            Device _device = this.deviceRepository.getDeviceByCode(device.getCode());
            if(null != _device){
                device.setId(_device.getId());
                device.setYstenId(_device.getYstenId());
                this.deviceRepository.updateDevice(device);
            }else{
                String ystenId = YstenIdUtils.createYstenId(disCode, spId, deviceTypeStr, device.getMac().replaceAll(":", "").trim());
                device.setYstenId(ystenId);
                this.deviceRepository.saveDevice(device);
                Device savedDevice = this.deviceRepository.getDeviceByCode(device.getCode());
                device.setId(savedDevice.getId());
            }
        }
        return device;
    }

    public Customer dealWithCustomer(Customer customer, Device bimsDevice, String type) throws Exception{
        Customer.State cState = Customer.State.NORMAL;
        if(type.equalsIgnoreCase("OPEN")){
            cState =  Customer.State.NONACTIVATED;
        }else if(type.equalsIgnoreCase("CANCEL")){
            cState = Customer.State.CANCEL;
        }else if(type.equalsIgnoreCase("ACTIVATED")){
            cState = Customer.State.NORMAL;
        }else if(type.equalsIgnoreCase("PAUSED")){
            cState = Customer.State.SUSPEND;
        }else if(type.equalsIgnoreCase("RECOVER")){
            cState = Customer.State.NORMAL;
        }

        if(null != customer){
            String customerAreaName = customer.getArea().getName().trim();
            String customerCityName = customer.getRegion().getName().trim();
            City city =  this.cityRepository.getCityByName(customerCityName);
            if(null != city){
                customer.setRegion(city);
            }
            Area area = this.areaRepository.getAreaByNameOrCode(customerAreaName, "");
            if(null != area){
                customer.setArea(area);
            }

            List<Customer> bimsCustomer = this.customerRepository.getCustomerByUserIdAndOuterCode(customer.getUserId().trim(), customer.getCode().trim());
            //判断bims中是否存在该用户
            if(null == bimsCustomer){
                //判断设备是否绑定其他用户
                Customer deviceCustomer = null;
                if(null != bimsDevice){
                    deviceCustomer = this.customerRepository.getCustomerById(bimsDevice.getCustomerId());
                }
                if(null ==deviceCustomer){
                    //保存用户
                    customer.setState(cState);
                    customer.setCode(NumberGenerator.getNextCode());
                    this.customerRepository.saveCustomer(customer);
                    long customerId = this.customerRepository.getCustomerByCode(customer.getCode()).getId();
                    customer.setId(customerId);
                }else{
                    customer.setId(deviceCustomer.getId());
                    customer.setCode(deviceCustomer.getCode());
                    customer.setState(cState);
                    this.customerRepository.updateCustomer(customer);
                }
            }else{
//                customer.setId(bimsCustomer.getId());
//                customer.setCode(bimsCustomer.getCode());
//                customer.setState(cState);
//                if(type.equalsIgnoreCase("CANCEL")){
//                    customer.setUserId(bimsCustomer.getCode());
//                }
                this.customerRepository.updateCustomer(customer);
            }
        }
        return customer;
    }

    public void dealWithRelationship(Customer bimsCustomer, Device bimsDevice, String type){
        if(null != bimsDevice && null != bimsCustomer){
            //开户，一户多机
            long customerId = bimsCustomer.getId();
            if(null == bimsDevice.getCustomerId() || customerId != bimsDevice.getCustomerId()){
                bimsDevice.setCustomerId(customerId);
                this.deviceRepository.updateDevice(bimsDevice);
            }
        }
    }

    public void dealWithCustomerBak(Customer customer, Device bimsDevice, String type) throws Exception{
        Customer.State cState = Customer.State.NORMAL;
        if(type.equalsIgnoreCase("OPEN")){
            cState =  Customer.State.NONACTIVATED;
        }else if(type.equalsIgnoreCase("CANCEL")){
            cState = Customer.State.CANCEL;
        }else if(type.equalsIgnoreCase("ACTIVATED")){
            cState = Customer.State.NORMAL;
        }else if(type.equalsIgnoreCase("PAUSED")){
            cState = Customer.State.SUSPEND;
        }else if(type.equalsIgnoreCase("RECOVER")){
            cState = Customer.State.NORMAL;
        }

        //获得BIMS中设备对应的用户
//        DeviceCustomerAccountMap _map = null;
        Customer bimsCustomer = null;

        if(null != bimsDevice){
//            _map = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceCode(device.getCode());
            bimsCustomer = this.customerRepository.getCustomerById(bimsDevice.getCustomerId());
        }

        //LBSS传入用户不为空
        if(null != customer){
            String customerAreaName = customer.getArea().getName().trim();
            String customerCityName = customer.getRegion().getName().trim();
            City city =  this.cityRepository.getCityByName(customerCityName);
            if(null != city){
                customer.setRegion(city);
            }
            Area area = this.areaRepository.getAreaByNameOrCode(customerAreaName, "");
            if(null != area){
                customer.setArea(area);
            }

            if(null == bimsCustomer){
                //保存用户
                customer.setState(cState);
                customer.setCode(NumberGenerator.getNextCode());
                this.customerRepository.saveCustomer(customer);
            } else{
                customer.setId(bimsCustomer.getId());
                customer.setCode(bimsCustomer.getCode());
                customer.setState(cState);
                this.customerRepository.updateCustomer(customer);
            }

            long customerId = this.customerRepository.getCustomerByCode(customer.getCode()).getId();
            //保存设备用户关系信息
            if(null != bimsDevice){
                bimsDevice.setCustomerId(customerId);
                this.deviceRepository.updateDevice(bimsDevice);
            }
            //删除冗余用户数据
            this.customerRepository.delCustomerByUserIdAndOuterCode(customer.getUserId().trim(), customer.getCode().trim(), customer.getId());

        }
        //LBSS传入用户为空
        else{
            if(null != bimsCustomer){
                bimsCustomer.setState(cState); //销户
//                bimsCustomer.setUserId(bimsCustomer.getCode());
                this.customerRepository.updateCustomer(bimsCustomer);

            }
        }
    }

	/**
	 * 保存用户 和 关系表
	 * @author chenxiang
	 * @date 2014-8-15 下午7:15:31 
	 * @param customer
	 * @param map
	 * @param device
	 */
	private void saveCustomerAndDeviceCustomerMap(Customer customer,
			DeviceCustomerAccountMap map, Device device) {

		DeviceCustomerAccountMap _map = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceCode(device.getCode());

		if(_map!=null){//开户 ；更新设备到期时间 ;销户
			Customer _customer = this.customerRepository.getCustomerByCode(_map.getCustomerCode());
			if(customer!=null){//开户 ；更新设备到期时间
                String customerAreaName = customer.getArea().getName().trim();
                String customerCityName = customer.getRegion().getName().trim();
                City city =  this.cityRepository.getCityByName(customerCityName);
                if(null != city){
                    customer.setRegion(city);
                }
                Area area = this.areaRepository.getAreaByNameOrCode(customerAreaName, "");
                if(null != area){
                    customer.setArea(area);
                }

				customer.setId(_customer.getId());
				customer.setCode(_customer.getCode());
				_map.setCustomerId(customer.getId());
				_map.setUserId(customer.getUserId());
				_map.setDeviceId(device.getId());
			}else{//销户  更新用户成虚拟用户
				_customer.setUserId(device.getCode());
				_customer.setPhone(device.getCode());
				customer=_customer;
				_map.setUserId(device.getCode());
				_map.setDeviceId(device.getId());
			}

			this.customerRepository.updateCustomer(customer);
            this.deviceCustomerAccountMapRepository.updateDeviceCustomerAccountMap(_map);
		}else {//默认初始设备  ;开户 ；更新设备到期时间 ;销户
			Customer initCustomer = initCustomer(device,customer);
			//创建虚拟用户
			this.customerRepository.saveCustomer(initCustomer);
			
			//保存设备用户关系信息
			DeviceCustomerAccountMap newMap = new DeviceCustomerAccountMap();
			newMap.setCustomerId(initCustomer.getId());
			newMap.setCustomerCode(initCustomer.getCode());
			newMap.setDeviceId(device.getId());
			newMap.setDeviceCode(device.getCode());
			newMap.setDeviceSno(device.getSno());
			newMap.setUserId(initCustomer.getUserId());
			this.deviceCustomerAccountMapRepository.saveDeviceCustomerAccountMap(newMap);
		}
	}
	
	
	/**
	 * 虚拟用户
	 * @author chenxiang
	 * @date 2014-8-15 下午8:07:52 
	 * @param device
	 * @return
	 */
	public Customer initCustomer(Device device,Customer customer0){
		Customer customer=new Customer();
		if(customer0==null){
			customer.setCode(NumberGenerator.getNextCode());
			customer.setState(Customer.State.NORMAL);
			customer.setStartDate(new Date());
			customer.setEndDate(new Date());
			customer.setUserId(device.getCode());//device.getCode()
			customer.setPhone(device.getCode());//device.getCode()
			customer.setRealName("");
			customer.setLoginName("");
			customer.setNickName("");
			City city = new City();
			city.setId(Long.parseLong("0"));
			customer.setRegion(city);
			customer.setCounty("");
			customer.setRate("");
			customer.setCreateDate(new Date());
			customer.setActivateDate(new Date());
			customer.setServiceStop(new Date());
			customer.setStateChangeDate(new Date());
			customer.setPassword(MD5.encrypt("666666"));
			customer.setSource("");
			Area area = new Area();
			area.setId(Long.parseLong("0"));
			customer.setArea(area);
			customer.setCustomerType(CustomerType.PERSONAL);
			customer.setIsSync(SyncType.WAITSYNC);
		}else {
			customer0.setCode(NumberGenerator.getNextCode());
			customer=customer0;
		}
		
		return customer;
	}
	
}
