package com.ysten.local.bss.device.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysten.area.domain.Area;
import com.ysten.local.bss.device.api.domain.request.DefaultBindDeviceRequest;
import com.ysten.local.bss.device.api.domain.request.DefaultBindMultipleDeviceRequest;
import com.ysten.local.bss.device.api.domain.request.DefaultDisableDeviceRequest;
import com.ysten.local.bss.device.api.domain.request.DefaultUpdateRateRequest;
import com.ysten.local.bss.device.api.domain.request.DefaultUpdateServicesStopRequest;
import com.ysten.local.bss.device.api.domain.request.IBindDeviceRequest;
import com.ysten.local.bss.device.api.domain.request.IBindMultipleDeviceRequest;
import com.ysten.local.bss.device.api.domain.request.IDisableDeviceRequest;
import com.ysten.local.bss.device.api.domain.request.IUpdateRateRequest;
import com.ysten.local.bss.device.api.domain.request.IUpdateServicesStopRequest;
import com.ysten.local.bss.device.api.domain.response.DefaultResponse;
import com.ysten.local.bss.device.api.domain.response.IResponse;
import com.ysten.local.bss.device.bean.NumberGenerator;
import com.ysten.local.bss.device.domain.City;
import com.ysten.local.bss.device.domain.Customer;
import com.ysten.local.bss.device.domain.Customer.CustomerType;
import com.ysten.local.bss.device.domain.CustomerDeviceHistory;
import com.ysten.local.bss.device.domain.Device;
import com.ysten.local.bss.device.domain.Device.BindType;
import com.ysten.local.bss.device.domain.DeviceCustomerAccountMap;
import com.ysten.local.bss.device.domain.LockType;
import com.ysten.local.bss.device.repository.ICityRepository;
import com.ysten.local.bss.device.repository.IDeviceCustomerAccountMapRepository;
import com.ysten.local.bss.talk.service.ITalkSystemService;
import com.ysten.local.bss.util.bean.Constant;
import com.ysten.utils.code.MD5;
import com.ysten.utils.date.DateUtil;

/**
 * 
 * Created by Just on 2014-6-16
 * 
 * 根据phone 获取customer --- phone一般为宽带账号
 * 
 */
@Service
public class PDeviceNewServiceImpl extends DefaultDeviceNewServiceImpl {

	@Autowired
	private ITalkSystemService talkSystemService;
	@Autowired
	private IDeviceCustomerAccountMapRepository deviceCustomerAccountMapRepository;
    @Autowired
    private ICityRepository cityRepository;
	
	@Override
	public IResponse checkBindDeviceParam(IBindDeviceRequest request) throws Exception{
		if(!(request instanceof DefaultBindDeviceRequest)){
			return new DefaultResponse(false, "device bind failed, because param wrong");
		}
		DefaultBindDeviceRequest _request = (DefaultBindDeviceRequest)request;
		boolean isExist = this.talkSystemService.checkTalkSystem(_request.getSystemCode());
		if(!isExist){
			return new DefaultResponse(false, Constant.SYSTEM_CODE_ILLEGAL);
		}
		if(StringUtils.isBlank(_request.getSno())){
			return new DefaultResponse(false, "device bind failed because sno is null!");
		}
		if(StringUtils.isBlank(_request.getUserName())){
			return new DefaultResponse(false, "device bind failed because userName is null!");
		}
		if(StringUtils.isBlank(_request.getPhone())){
			return new DefaultResponse(false, "device bind failed because phone is null!");
		}
		if(StringUtils.isBlank(_request.getStartDate())){
			return new DefaultResponse(false, "device bind failed because startDate is null!");
		}
		if(StringUtils.isBlank(_request.getEndDate())){
			return new DefaultResponse(false, "device bind failed because endDate is null!");
		}
		Date startDate=null, endDate=null;
		if (StringUtils.isNotBlank(_request.getStartDate()) && _request.getStartDate().length() == 8) {
			startDate = DateUtil.convertStringToDate("yyyyMMdd", _request.getStartDate());
		}else if (StringUtils.isNotBlank(_request.getStartDate()) && _request.getStartDate().length() == 14) {
			startDate = DateUtil.convertStringToDate("yyyyMMddHHmmss", _request.getStartDate());
		}
		if (StringUtils.isNotBlank(_request.getEndDate()) && _request.getEndDate().length() == 8) {
			endDate = DateUtil.convertStringToDate("yyyyMMdd", _request.getEndDate());
		}else if (StringUtils.isNotBlank(_request.getEndDate()) && _request.getEndDate().length() == 14) {
			endDate = DateUtil.convertStringToDate("yyyyMMddHHmmss", _request.getEndDate());
		}
		if(startDate == null || endDate == null){
			return new DefaultResponse(false, "binding failed, startDate or endDate format error");
		}
		if (startDate != null && endDate != null && (startDate.getTime() >= endDate.getTime())) {
			return new DefaultResponse(false, "binding failed, startDate should be before endDate.");
		}
		if(StringUtils.isBlank(_request.getRegion())){
			return new DefaultResponse(false, "device bind failed because region is null!");
		}
		if(StringUtils.isBlank(_request.getCounty())){
			return new DefaultResponse(false, "device bind failed because county is null!");
		}
		if(StringUtils.isBlank(_request.getAddress())){
			return new DefaultResponse(false, "device bind failed because address is null!");
		}
		if(StringUtils.isBlank(_request.getRate())){
			return new DefaultResponse(false, "device bind failed because rate is null!");
		}
		
		Device device = this.deviceRepository.getDeviceBySno(_request.getSno());
		if(device == null){
			return new DefaultResponse(false, "binding failed,device sno does not exists,sno:" + _request.getSno());
		}
		List<DeviceCustomerAccountMap> mapList = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceId(device.getId());
		if(mapList != null && !mapList.isEmpty()){
			return new DefaultResponse(false, "bind failed,device has been binding,deivce sno:" + _request.getSno());
		}
		Customer customer = this.customerRepository.getCustomerByPhone(_request.getPhone()); //根据phone获取customer
		if(customer != null){
			List<DeviceCustomerAccountMap> maps = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByCustomerId(customer.getId());
			if(maps != null && !maps.isEmpty()){
				return new DefaultResponse(false, "bind failed,customer has been binding!");
			}
		}
		return null;
	}

	
	@Override
	public IResponse checkDisableDeviceParam(IDisableDeviceRequest request) throws Exception {
		if(!(request instanceof DefaultDisableDeviceRequest)){
			return new DefaultResponse(false, "device disable failed, because param wrong");
		}
		DefaultDisableDeviceRequest _request = (DefaultDisableDeviceRequest)request;
		boolean isExist = this.talkSystemService.checkTalkSystem(_request.getSystemCode());
		if(!isExist){
			return new DefaultResponse(false, Constant.SYSTEM_CODE_ILLEGAL);
		}
		if(StringUtils.isBlank(_request.getSno()) && TYPE_ONLY.equals(_request.getType())){
			return new DefaultResponse(false, "device disable failed because sno is null!");
		}
       /* if(StringUtils.isBlank(_request.getUserId())){
            return new DefaultResponse(false, "device disable failed because userId is null!");
        }*/
		if(StringUtils.isBlank(_request.getPhone())){
			return new DefaultResponse(false, "device disable failed because phone is null!");
		}
        if(StringUtils.isBlank(_request.getType())){
            return new DefaultResponse(false, "device disable failed because type is null!");
        }
        Customer customer = this.customerRepository.getCustomerByPhone(_request.getPhone()); //根据phone 获取customer
		if(customer == null){
			return new DefaultResponse(false, "device disable fail, because customer is not exists!");
		}
        if(TYPE_ONLY.equals(_request.getType())){
            Device device = this.deviceRepository.getDeviceBySno(_request.getSno());
            if(device == null){
                return new DefaultResponse(false, "device disable fail, because sno is not exists!");
            }
            DeviceCustomerAccountMap map = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceCode(device.getCode());
            if(!StringUtils.equalsIgnoreCase(map.getCustomerCode(), customer.getCode())){
            	return new DefaultResponse(false, "device disable fail, because sno is bind to another userId!");
            }
        }
		return null;
	}

	@Override
	protected boolean doDisableDeviceAndCustomer(IDisableDeviceRequest request) {
		DefaultUpdateRateRequest _request = (DefaultUpdateRateRequest)request;
		Customer customer = this.customerRepository.getCustomerByPhone(_request.getPhone()); //根据phone 获取customer
		
        List<DeviceCustomerAccountMap> maps = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByCustomerId(customer.getId());
        boolean isAllDisable = true;
        if (CollectionUtils.isNotEmpty(maps)) {
            for (DeviceCustomerAccountMap map : maps) {
                // 如果type=only，那么只能更新指定的device，如果type=all，那么更新所有的device
            	//只要有一个sno没有销户，那么用户的状态就不能更新为销户
                if(TYPE_ONLY.equals(request.getType()) && !map.getDeviceSno().equals(map.getDeviceSno())){
                	isAllDisable = false;
                    continue;
                }
                Device device = this.deviceRepository.getDeviceByCodeSnoAndMac(map.getDeviceSno());
                // 终端停用
                device.setState(Device.State.NONACTIVATED);
                device.setBindType(BindType.UNBIND);
                device.setIsLock(LockType.LOCK);
                device.setStateChangeDate(new Date());
                this.deviceRepository.updateDevice(device);

                List<DeviceCustomerAccountMap>  list=deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceId(device.getId());
            	for(int i=0;i<list.size();i++){
            		DeviceCustomerAccountMap dcaMap=list.get(i);
            		deviceCustomerAccountMapRepository.delete(dcaMap);
            	}
                //新增用户历史记录
                CustomerDeviceHistory history = this.createCustomerDeviceHistory(null, device, customer);
                this.deviceRepository.saveCustomerDeviceHistory(history);
            }
        }
        
        // 销户
        if(isAllDisable){
	 		customer.setState(Customer.State.CANCEL);
	 		customer.setCancelledDate(new Date());
	 		this.customerRepository.updateCustomer(customer);
        }
		return true;
	}

	@Override
	public IResponse checkUpdateServicesStopParam(IUpdateServicesStopRequest request) throws Exception {
		if(!(request instanceof DefaultUpdateServicesStopRequest)){
			return new DefaultResponse(false, "update services stop failed, because param wrong");
		}
		DefaultUpdateServicesStopRequest _request = (DefaultUpdateServicesStopRequest)request;
		boolean isExist = this.talkSystemService.checkTalkSystem(_request.getSystemCode());
		if(!isExist){
			return new DefaultResponse(false, Constant.SYSTEM_CODE_ILLEGAL);
		}
        // 只有当type=only时，才检验sno
		if(StringUtils.isBlank(_request.getSno()) && TYPE_ONLY.equals(_request.getType())){
			return new DefaultResponse(false, "update service stop failed, because sno is null!");
		}
        // phone 不做强制检验
		if(StringUtils.isBlank(_request.getPhone())){
			return new DefaultResponse(false, "update service stop failed, because phone is null!");
		}
		if(StringUtils.isBlank(_request.getServiceStop())){
			return new DefaultResponse(false, "update service stop failed, because serviceStop is null!");
		}
       /* if(StringUtils.isBlank(_request.getUserId())){
			return new DefaultResponse(false, "update service stop failed, because userId is null!");
        }*/
        if(StringUtils.isBlank(_request.getType())){
            return new DefaultResponse(false, "update service stop failed, because type is null!");
        }
        

		Customer customer = this.customerRepository.getCustomerByPhone(_request.getPhone()); //根据phone 获取customer
		
        if(customer == null){
        	return new DefaultResponse(false, "update service stop failed, because userId is not exists!");
        }
        if(TYPE_ONLY.equals(_request.getType())){
            Device device = this.deviceRepository.getDeviceBySno(_request.getSno());
            if(device == null){
                return new DefaultResponse(false, "update service stop failed, because sno is not exists!");
            }
            DeviceCustomerAccountMap map = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceCode(device.getCode());
            if(map == null){
                return new DefaultResponse(false, "update deivce service stop failed, because sno "+ _request.getSno() +" not bind customer");
            }
            if(!StringUtils.equals(customer.getCode(), map.getCustomerCode())){
				return new DefaultResponse(false, "update deivce service stop failed,, because userId "+_request.getUserId()+" bind another device!");
			}
        }
		Date eDate = null;
		if (_request.getServiceStop().length() == 8) {
			eDate = DateUtil.convertStringToDate("yyyyMMdd", _request.getServiceStop());
		}else if (_request.getServiceStop().length() == 14) {
			eDate = DateUtil.convertStringToDate("yyyyMMddHHmmss", _request.getServiceStop());
		}else {
			return new DefaultResponse(false, "update deivce service stop failed, because servicesStop format error");
		}
		Date sDate = customer.getStartDate();
		if(eDate.before(sDate)){
			return new DefaultResponse(false, "update deivce service stop failed, because service stop time is before service start time");
		}
		return null;
	}
	
	protected boolean doUpdateCustomerServicesStop(IUpdateServicesStopRequest request) throws Exception{
		String serviceStop = request.getServiceStop();
		
		DefaultUpdateRateRequest _request = (DefaultUpdateRateRequest)request;
		Customer customer = this.customerRepository.getCustomerByPhone(_request.getPhone()); //根据phone 获取customer
		
		if(serviceStop.length() == 8){
			customer.setServiceStop(DateUtil.convertStringToDate("yyyyMMdd", serviceStop));
		}else if (serviceStop.length() == 10) {
			customer.setServiceStop(DateUtil.convertStringToDate("yyyy-MM-dd", serviceStop));
		}else if (serviceStop.length() == 14) {
			customer.setServiceStop(DateUtil.convertStringToDate("yyyyMMddHHmmss", serviceStop));
		}else if (serviceStop.length() == 19) {
			customer.setServiceStop(DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss", serviceStop));
		}
		this.customerRepository.updateCustomer(customer);

        List<DeviceCustomerAccountMap> maps = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByCustomerId(customer.getId());
        if (CollectionUtils.isNotEmpty(maps)) {
            for (DeviceCustomerAccountMap map : maps) {
                // 如果type=only，那么只能更新指定的device，如果type=all，那么更新所有的device
                if(TYPE_ONLY.equals(request.getType()) && !map.getDeviceSno().equals(request.getSno())){
                    continue;
                }
                Device device = this.deviceRepository.getDeviceByCodeSnoAndMac(map.getDeviceCode());
                Date eDate = DateUtil.convertStringToDate("yyyyMMddHHmmss", serviceStop);
                device.setExpireDate(eDate);
                this.deviceRepository.updateDevice(device);
            }
        }
        return true;
	}

	@Override
	public Customer initMultipleCustomerByRequest(IBindMultipleDeviceRequest request) throws Exception {
		DefaultBindMultipleDeviceRequest _request = (DefaultBindMultipleDeviceRequest)request;
		Date startDate=null, endDate=null;
		if (StringUtils.isNotBlank(_request.getStartDate()) && _request.getStartDate().length() == 8) {
			startDate = DateUtil.convertStringToDate("yyyyMMdd", _request.getStartDate());
		}else if (StringUtils.isNotBlank(_request.getStartDate()) && _request.getStartDate().length() == 14) {
			startDate = DateUtil.convertStringToDate("yyyyMMddHHmmss", _request.getStartDate());
		}
		if (StringUtils.isNotBlank(_request.getEndDate()) && _request.getEndDate().length() == 8) {
			endDate = DateUtil.convertStringToDate("yyyyMMdd", _request.getEndDate());
		}else if (StringUtils.isNotBlank(_request.getEndDate()) && _request.getEndDate().length() == 14) {
			endDate = DateUtil.convertStringToDate("yyyyMMddHHmmss", _request.getEndDate());
		}
		Customer customer = this.customerRepository.getCustomerByPhone(_request.getPhone()); //根据phone 获取customer
		if(customer == null){
			customer = new Customer();
			customer.setCode(NumberGenerator.getNextCode());
		}else{
			customer.setState(Customer.State.NORMAL);
		}
		customer.setStartDate(startDate);
		customer.setEndDate(endDate);
		customer.setUserId(_request.getUserId());
		customer.setRealName(_request.getUserName());
		customer.setLoginName(customer.getRealName());
		customer.setNickName(customer.getRealName());
		customer.setPhone(_request.getPhone());
		City city = this.cityRepository.getCityByCityCode(_request.getRegion());
		customer.setRegion(city);
		customer.setCounty(_request.getCounty());
		customer.setAddress(_request.getAddress());
		customer.setRate(_request.getRate());
		customer.setCreateDate(new Date());
		customer.setActivateDate(new Date());
		customer.setServiceStop(sdf.parse(_request.getEndDate()));
		customer.setPassword(MD5.encrypt("666666"));
		String areaId = this.systemConfigService.getSystemConfigByConfigKey("areaId");
		customer.setSource(_request.getSystemCode());
		Area area = new Area();
		area.setId(Long.parseLong(areaId));
		customer.setArea(area);
		if(StringUtils.isBlank(_request.getIsGroup())){
			customer.setCustomerType(CustomerType.PERSONAL);
		}else {
			if(StringUtils.equalsIgnoreCase(ONE_CUSTOMER_MULTI_DEVICE, _request.getIsGroup())){
				customer.setCustomerType(CustomerType.GROUP);
			}else {
				customer.setCustomerType(CustomerType.PERSONAL);
			}
		}
		return customer;
	}

	@Override
	public IResponse checkUpdateRateParam(IUpdateRateRequest request) throws Exception {
		if(!(request instanceof DefaultUpdateRateRequest)){
			return new DefaultResponse(false, "Update rate failed, because param wrong");
		}
		DefaultUpdateRateRequest _request = (DefaultUpdateRateRequest)request;
		boolean isExist = this.talkSystemService.checkTalkSystem(_request.getSystemCode());
		if(!isExist){
			return new DefaultResponse(false, Constant.SYSTEM_CODE_ILLEGAL);
		}
		/*if(StringUtils.isBlank(_request.getUserId())){
			return new DefaultResponse(false, "Update rate failed, because userId is null!");
		}*/
		if(StringUtils.isBlank(_request.getPhone())){
			return new DefaultResponse(false, "Update rate failed, because phone is null!");
		}
		if(StringUtils.isBlank(_request.getRate())){
			return new DefaultResponse(false, "Update rate failed, because rate is null!");
		}
		if(StringUtils.isBlank(_request.getSno())){
			return new DefaultResponse(false, "Update rate failed, because sno is null!");
		}
		Customer customer = this.customerRepository.getCustomerByPhone(_request.getPhone()); //根据phone 获取customer
		if(customer == null){
			return new DefaultResponse(false, "Update rate failed, because userId is not exists!");
		}
		if(StringUtils.isNotBlank(_request.getType()) && StringUtils.equalsIgnoreCase(_request.getType(), TYPE_ONLY)){
			Device device = this.deviceRepository.getDeviceBySno(_request.getSno());
			if(device == null){
				return new DefaultResponse(false, "Update rate failed, because sno is not exists!");
			}
			DeviceCustomerAccountMap map = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceCode(device.getCode());
			if(map == null){
				return new DefaultResponse(false, "Update rate failed, because device bind customer is not exists!");
			}
			if(!StringUtils.equals(customer.getCode(), map.getCustomerCode())){
				return new DefaultResponse(false, "Update rate failed, because userId "+_request.getUserId()+" bind another device!");
			}
		}
		return null;
	}
	
	/**
	 * 终端速率变更修改后续操作
	 */
	public boolean doUpdateRate(IUpdateRateRequest request) throws Exception{
		DefaultUpdateRateRequest _request = (DefaultUpdateRateRequest)request;
		Customer customer = this.customerRepository.getCustomerByPhone(_request.getPhone()); //根据phone 获取customer
		customer.setRate(request.getRate());
		this.customerRepository.updateCustomer(customer);
		return true;
	}


}
