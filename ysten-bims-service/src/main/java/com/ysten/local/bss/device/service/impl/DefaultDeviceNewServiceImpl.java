package com.ysten.local.bss.device.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysten.area.domain.Area;
import com.ysten.boss.systemconfig.service.SystemConfigService;
import com.ysten.local.bss.device.api.domain.request.DefaultBindDeviceRequest;
import com.ysten.local.bss.device.api.domain.request.DefaultBindMultipleDeviceRequest;
import com.ysten.local.bss.device.api.domain.request.DefaultDisableDeviceRequest;
import com.ysten.local.bss.device.api.domain.request.DefaultPauseOrRecoverDeviceRequest;
import com.ysten.local.bss.device.api.domain.request.DefaultRebindDeviceRequest;
import com.ysten.local.bss.device.api.domain.request.DefaultUpdateRateRequest;
import com.ysten.local.bss.device.api.domain.request.DefaultUpdateServicesStopRequest;
import com.ysten.local.bss.device.api.domain.request.DefaultVerifyDeviceRequest;
import com.ysten.local.bss.device.api.domain.request.IBindDeviceRequest;
import com.ysten.local.bss.device.api.domain.request.IBindMultipleDeviceRequest;
import com.ysten.local.bss.device.api.domain.request.ICreateUserRequest;
import com.ysten.local.bss.device.api.domain.request.IDisableDeviceRequest;
import com.ysten.local.bss.device.api.domain.request.IPauseOrRecoverDeviceRequest;
import com.ysten.local.bss.device.api.domain.request.IRebindDeviceRequest;
import com.ysten.local.bss.device.api.domain.request.IUpdateRateRequest;
import com.ysten.local.bss.device.api.domain.request.IUpdateServicesStopRequest;
import com.ysten.local.bss.device.api.domain.request.IVerifyDeviceRequest;
import com.ysten.local.bss.device.api.domain.request.MultipleBindDeviceSno;
import com.ysten.local.bss.device.api.domain.response.DefaultResponse;
import com.ysten.local.bss.device.api.domain.response.IResponse;
import com.ysten.local.bss.device.bean.NumberGenerator;
import com.ysten.local.bss.device.domain.City;
import com.ysten.local.bss.device.domain.Customer;
import com.ysten.local.bss.device.domain.Customer.CustomerType;
import com.ysten.local.bss.device.domain.Device;
import com.ysten.local.bss.device.domain.Device.SyncType;
import com.ysten.local.bss.device.domain.DeviceCustomerAccountMap;
import com.ysten.local.bss.device.repository.ICityRepository;
import com.ysten.local.bss.device.repository.IDeviceCustomerAccountMapRepository;
import com.ysten.local.bss.talk.service.ITalkSystemService;
import com.ysten.local.bss.util.DateUtils;
import com.ysten.local.bss.util.bean.Constant;
import com.ysten.utils.code.MD5;
import com.ysten.utils.date.DateUtil;

/**
 * 默认实现的设备服务层类
 * @author XuSemon
 * @since 2014-5-19
 */
@Service
public class DefaultDeviceNewServiceImpl extends DeviceNewServiceImpl {
	@Autowired
	protected SystemConfigService systemConfigService;
	@Autowired
	private IDeviceCustomerAccountMapRepository deviceCustomerAccountMapRepository;
	@Autowired
	private ITalkSystemService talkSystemService;
    @Autowired
    private ICityRepository cityRepository;
	private static final String ONE_CUSTOMER_ONE_DEVICE = "0";
	protected  static final String ONE_CUSTOMER_MULTI_DEVICE = "1";
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	/**************************************************************生成用户操作********************************************************/
	@Override
    public IResponse checkCreateUserParam(ICreateUserRequest request) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IResponse doAfterCreateUser(ICreateUserRequest request, boolean isCreateSuccess) throws Exception {
        if(isCreateSuccess){
            return new DefaultResponse("Create user success!");
        }
        return new DefaultResponse(false, "Create user failed!");
    }

    @Override
    public Customer initCustomerByRequest(ICreateUserRequest request) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

	/**************************************************************开户操作********************************************************/
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
		
		startDate=DateUtils.stringToDate(_request.getStartDate());
		endDate=DateUtils.stringToDate(_request.getEndDate());
		
		
		if(startDate == null || endDate == null){
			return new DefaultResponse(false, "binding failed, startDate or endDate format error");
		}
		if (startDate != null && endDate != null && (startDate.getTime() >= endDate.getTime())) {
			return new DefaultResponse(false, "binding failed, startDate should be before endDate.");
		}
		if(StringUtils.isBlank(_request.getRegion())){
			return new DefaultResponse(false, "device bind failed because region is null!");
		}
//		if(StringUtils.isBlank(_request.getCounty())){
//			return new DefaultResponse(false, "device bind failed because county is null!");
//		}
//		if(StringUtils.isBlank(_request.getAddress())){
//			return new DefaultResponse(false, "device bind failed because address is null!");
//		}
//		if(StringUtils.isBlank(_request.getRate())){
//			return new DefaultResponse(false, "device bind failed because rate is null!");
//		}
		Device device = this.deviceRepository.getDeviceBySno(_request.getSno());
		if(device == null){
			return new DefaultResponse(false, "binding failed,device sno does not exists,sno:" + _request.getSno());
		}
		List<DeviceCustomerAccountMap> mapList = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceId(device.getId());
		if(mapList != null && !mapList.isEmpty()){
			return new DefaultResponse(false, "bind failed,device has been binding,deivce sno:" + _request.getSno());
		}
//		Customer customer = this.customerRepository.getCustomerByUserId(_request.getUserId());
//		if(customer != null){
//			List<DeviceCustomerAccountMap> maps = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByCustomerId(customer.getId());
//			if(maps != null && !maps.isEmpty()){
//				return new DefaultResponse(false, "bind failed,customer has been binding!");
//			}
//		}
		return null;
	}

	@Override
	public Customer initCustomerByRequest(IBindDeviceRequest request) throws Exception{
		DefaultBindDeviceRequest _request = (DefaultBindDeviceRequest)request;
		Date startDate=null, endDate=null;
		
		startDate=DateUtils.stringToDate(_request.getStartDate());
		endDate=DateUtils.stringToDate(_request.getEndDate());
		
		
		Customer customer = this.customerRepository.getCustomerByUserId(_request.getUserId());
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
		if(customer.getId() == null){
			customer.setCreateDate(new Date());
		}
		customer.setActivateDate(new Date());
//		customer.setServiceStop(sdf.parse(_request.getEndDate()));//old
		customer.setServiceStop(endDate);
		customer.setStateChangeDate(new Date());
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
		
		//添加是否同步状态，以便铁通数据同步
		customer.setIsSync(SyncType.WAITSYNC);
		
		return customer;
	}

	@Override
	public IResponse doAfterBind(IBindDeviceRequest request, boolean isBindSuccess) throws Exception{
		DefaultBindDeviceRequest _request = (DefaultBindDeviceRequest)request;
		if(isBindSuccess){
			return new DefaultResponse("user and device bind success!device sno:" + _request.getSno());
		}
		return new DefaultResponse(false, "user and device bind failed!device sno:" + _request.getSno());
	}

	
	/**************************************************************销户操作********************************************************/
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
		if(StringUtils.isBlank(_request.getSno()) && StringUtils.equalsIgnoreCase(TYPE_ONLY, _request.getType())){
			return new DefaultResponse(false, "device disable failed because sno is null!");
		}
        if(StringUtils.isBlank(_request.getUserId())){
            return new DefaultResponse(false, "device disable failed because userId is null!");
        }
//        if(StringUtils.isBlank(_request.getType())){
//            return new DefaultResponse(false, "device disable failed because type is null!");
//        }
        Customer customer = this.customerRepository.getCustomerByUserId(_request.getUserId());
		if(customer == null){
			return new DefaultResponse(false, "device disable fail, because customer is not exists!");
		}
        if(StringUtils.equalsIgnoreCase(TYPE_ONLY, _request.getType())){
            Device device = this.deviceRepository.getDeviceBySno(_request.getSno());
            if(device == null){
                return new DefaultResponse(false, "device disable fail, because sno is not exists!");
            }
            DeviceCustomerAccountMap map = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceCode(device.getCode());
            if(map == null){
            	return new DefaultResponse(false, "device disable fail, because sno is not bind to any user!");
            }
            if(!StringUtils.equalsIgnoreCase(map.getCustomerCode(), customer.getCode())){
            	return new DefaultResponse(false, "device disable fail, because sno is bind to another userId!");
            }
        }
		return null;
	}

	@Override
	public IResponse doAfterDisable(IDisableDeviceRequest request, boolean isDisableSuccess) throws Exception {
		if(isDisableSuccess){
			return new DefaultResponse("device disable success!device sno:" + request.getSno());
		}
		return new DefaultResponse(false, "device disable failed!device sno:" + request.getSno());
	}

	
	/**************************************************************换机操作********************************************************/
	@Override
	public IResponse checkRebindDeviceParam(IRebindDeviceRequest request) throws Exception {
		if(!(request instanceof DefaultRebindDeviceRequest)){
			return new DefaultResponse(false, "device rebind failed, because param wrong");
		}
		DefaultRebindDeviceRequest _request = (DefaultRebindDeviceRequest)request;
		boolean isExist = this.talkSystemService.checkTalkSystem(_request.getSystemCode());
		if(!isExist){
			return new DefaultResponse(false, Constant.SYSTEM_CODE_ILLEGAL);
		}
//		if(StringUtils.isBlank(_request.getPhone())){
//			return new DefaultResponse(false, "device rebind failed because phone is null!");
//		}
		if(StringUtils.isBlank(_request.getOldSno())){
			return new DefaultResponse(false, "device rebind failed because old sno is null!");
		}
		if(StringUtils.isBlank(_request.getNewSno())){
			return new DefaultResponse(false, "device rebind failed because new sno is null!");
		}
//		Customer customer = this.customerRepository.getCustomerByPhone(_request.getPhone());
//		if(customer == null){
//			return new DefaultResponse(false, "device rebind failed because phone is not exist!");
//		}
		Device oldDevice = this.deviceRepository.getDeviceBySno(_request.getOldSno());
		if(oldDevice == null){
			return new DefaultResponse(false, "device rebind failed because old sno is not exist!");
		}
		Customer _customer = this.customerRepository.getCustomerByDeviceId(oldDevice.getId());
		if(_customer == null){
			return new DefaultResponse(false, "user rebind device failed: there is no relationship between user and old device!");
		}
//		if(!customer.getId().equals(_customer.getId())){
//			return new DefaultResponse(false, "device rebind failed because old sno and phone can not be matched!");
//		}
		Device newDevice = this.deviceRepository.getDeviceBySno(_request.getNewSno());
		if(newDevice == null){
			return new DefaultResponse(false, "device rebind failed because new sno is not exist!");
		}
		if(!newDevice.getState().equals(Device.State.NONACTIVATED)){
			return new DefaultResponse(false, "device rebind failed because newSno device state must be non activated!");
		}
		List<DeviceCustomerAccountMap> maps = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceId(newDevice.getId());
		if(maps != null && !maps.isEmpty()){
			return new DefaultResponse(false, "device rebind failed, newSno device is bind customer!");
		}
		return null;
	}

	@Override
	public IResponse doAfterRebind(IRebindDeviceRequest request, boolean isRebindSuccess) throws Exception {
		if(isRebindSuccess){
			return new DefaultResponse("user rebind device success,oldSno:" + request.getOldSno() + ",newSno:" + request.getNewSno());
		}
		return new DefaultResponse(false, "user rebind device failed,oldSno:" + request.getOldSno() + ",newSno:" + request.getNewSno());
	}

	
	/************************************************************** 终端编号合法性查询接口********************************************************/
	@Override
	public IResponse verifyDevice(IVerifyDeviceRequest request) throws Exception {
		if(!(request instanceof DefaultVerifyDeviceRequest)){
			return new DefaultResponse(false, "device verify failed, because param wrong");
		}
		DefaultVerifyDeviceRequest _request = (DefaultVerifyDeviceRequest)request;
		boolean isExist = this.talkSystemService.checkTalkSystem(_request.getSystemCode());
		if(!isExist){
			return new DefaultResponse(false, Constant.SYSTEM_CODE_ILLEGAL);
		}
		if(StringUtils.isBlank(_request.getSno())){
			return new DefaultResponse(false, "device verify failed because sno is null!");
		}
		Device device = this.deviceRepository.getDeviceBySno(_request.getSno());
		if(device == null){
			return new DefaultResponse(false, "device verify failed, because sno is not exists!");
		}
		Customer customer = this.customerRepository.getCustomerByDeviceId(device.getId());
		if(customer != null){
			return new DefaultResponse(false, "verify device failed," + customer.getPhone() + " is use this sno " + DateUtil.convertDateToString("yyyy-MM-dd HH:mm:ss", customer.getCreateDate()));
		}
		return new DefaultResponse("verify device success! device sno:" + _request.getSno());
	}

	
	/**************************************************************终端到期时间修改操作********************************************************/
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
		if(StringUtils.isBlank(_request.getSno()) && StringUtils.equalsIgnoreCase(TYPE_ONLY, _request.getType())){
			return new DefaultResponse(false, "update service stop failed, because sno is null!");
		}
        // phone 不做强制检验
//		if(StringUtils.isBlank(_request.getPhone())){
//			return new DefaultResponse(false, "update service stop failed, because phone is null!");
//		}
		if(StringUtils.isBlank(_request.getServiceStop())){
			return new DefaultResponse(false, "update service stop failed, because serviceStop is null!");
		}
        if(StringUtils.isBlank(_request.getUserId())){
			return new DefaultResponse(false, "update service stop failed, because userId is null!");
        }
//        if(StringUtils.isBlank(_request.getType())){
//            return new DefaultResponse(false, "update service stop failed, because type is null!");
//        }
        Customer customer = this.customerRepository.getCustomerByUserId(_request.getUserId());
        if(customer == null){
        	return new DefaultResponse(false, "update service stop failed, because userId is not exists!");
        }
        if(StringUtils.equalsIgnoreCase(TYPE_ONLY, _request.getType())){
            Device device = this.deviceRepository.getDeviceBySno(_request.getSno());
            if(device == null){
                return new DefaultResponse(false, "update service stop failed, because sno is not exists!");
            }
            DeviceCustomerAccountMap map = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceCode(device.getCode());
            if(map == null){
                return new DefaultResponse(false, "update deivce service stop failed, because sno "+ _request.getSno() +" not bind customer");
            }
            if(!StringUtils.equalsIgnoreCase(customer.getCode(), map.getCustomerCode())){
				return new DefaultResponse(false, "update deivce service stop failed,, because userId "+_request.getUserId()+" bind another device!");
			}
        }
		Date eDate = null;
		eDate=DateUtils.stringToDate(_request.getServiceStop());
		if(eDate==null){
			return new DefaultResponse(false, "update deivce service stop failed, because servicesStop format error");
		}
		
		Date sDate = customer.getStartDate();
		if(eDate.before(sDate)){
			return new DefaultResponse(false, "update deivce service stop failed, because service stop time is before service start time");
		}
		return null;
	}

	@Override
	public IResponse doAfterUpdateServicesStop(IUpdateServicesStopRequest request, boolean isUpdateServicesStopSuccess) throws Exception {
		if(isUpdateServicesStopSuccess){
			return new DefaultResponse("update deivce services stop success!");
		}
		return new DefaultResponse(false, "update deivce services stop failed!");
	}

	
	/**************************************************************集团业务办理接口********************************************************/
	@Override
	public IResponse checkBindMultipleDeviceParam(IBindMultipleDeviceRequest request) throws Exception {
		if(!(request instanceof DefaultBindMultipleDeviceRequest)){
			return new DefaultResponse(false, "bind multiple device failed, because param wrong");
		}
		DefaultBindMultipleDeviceRequest _request = (DefaultBindMultipleDeviceRequest)request;
		boolean isExist = this.talkSystemService.checkTalkSystem(_request.getSystemCode());
		if(!isExist){
			return new DefaultResponse(false, Constant.SYSTEM_CODE_ILLEGAL);
		}
		if(_request.getSnoList() == null || _request.getSnoList().isEmpty()){
			return new DefaultResponse(false, "bind multiple device failed, because snoList is null!");
		}
		if(StringUtils.isBlank(_request.getUserName())){
			return new DefaultResponse(false, "bind multiple device failed, because userName is null!");
		}
		if(StringUtils.isBlank(_request.getPhone())){
			return new DefaultResponse(false, "bind multiple device failed, because phone is null!");
		}
		if(StringUtils.isBlank(_request.getStartDate())){
			return new DefaultResponse(false, "bind multiple device failed, because startDate is null!");
		}
		if(StringUtils.isBlank(_request.getEndDate())){
			return new DefaultResponse(false, "bind multiple device failed, because endDate is null!");
		}
		if(StringUtils.isBlank(_request.getRegion())){
			return new DefaultResponse(false, "bind multiple device failed, because region is null!");
		}
		if(StringUtils.isBlank(_request.getCounty())){
			return new DefaultResponse(false, "bind multiple device failed, because county is null!");
		}
		if(StringUtils.isBlank(_request.getAddress())){
			return new DefaultResponse(false, "bind multiple device failed, because address is null!");
		}
		if(StringUtils.isBlank(_request.getRate())){
			return new DefaultResponse(false, "bind multiple device failed, because rate is null!");
		}
		if(StringUtils.isBlank(_request.getUserId())){
			return new DefaultResponse(false, "bind multiple device failed, because userId is null!");
		}
		if(StringUtils.isBlank(_request.getIsGroup())){
			return new DefaultResponse(false, "bind multiple device failed, because isGroup is null!");
		}
		for(MultipleBindDeviceSno sno : _request.getSnoList()){
			Device device = this.deviceRepository.getDeviceBySno(sno.getSno());
			if(device == null){
				return new DefaultResponse(false, "binding multiple device failed,device sno does not exists,sno:" + sno);
			}
			List<DeviceCustomerAccountMap> mapList = deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceId(device.getId());
			if (mapList != null && mapList.size() > 0) {
				return new DefaultResponse(false, "bind multiple device failed, device has been binding, deivce sno:" + sno);
			}
		}
		if (_request.getSnoList().size() > 1) {
			if (StringUtils.equalsIgnoreCase(ONE_CUSTOMER_ONE_DEVICE, _request.getIsGroup())) {
				return new DefaultResponse(false, "bind multiple device failed, because isGroup is 0, a user can only bind a sno!");
			}
		}
		Date startDate=null, endDate=null;
		
		startDate=DateUtils.stringToDate(_request.getStartDate());
		endDate=DateUtils.stringToDate(_request.getEndDate());
		
		
		if(startDate == null || endDate == null){
			return new DefaultResponse(false, "binding multiple device failed, startDate or endDate format error");
		}
		if (startDate != null && endDate != null && (startDate.getTime() >= endDate.getTime())) {
			return new DefaultResponse(false, "binding multiple device failed, startDate should be before endDate.");
		}
		if(!StringUtils.equalsIgnoreCase(ONE_CUSTOMER_ONE_DEVICE, _request.getIsGroup())
				&& !StringUtils.equalsIgnoreCase(ONE_CUSTOMER_MULTI_DEVICE, _request.getIsGroup())){
			return new DefaultResponse(false, "bind multiple device failed, isGroup is illegal.");
		}
//		Customer customer = this.customerRepository.getCustomerByPhone(_request.getPhone());
//		Customer _customer = this.customerRepository.getCustomerByUserId(_request.getUserId());
//		if((customer == null && _customer != null) || (customer != null && _customer == null) 
//				|| (customer != null && _customer != null && !customer.getId().equals(_customer.getId()))){
//			return new DefaultResponse(false, "bind multiple device failed,useId and phone number do not match");
//		}
		return null;
	}

	@Override
	public IResponse doAfterBindMultipleDevice(IBindMultipleDeviceRequest request, boolean isBindMultipleDeviceSuccess) throws Exception {
		if(isBindMultipleDeviceSuccess){
			return new DefaultResponse("bind multiple device success!");
		}
		return new DefaultResponse(false, "bind multiple device failed!");
	}

	@Override
	public Customer initMultipleCustomerByRequest(IBindMultipleDeviceRequest request) throws Exception {
		DefaultBindMultipleDeviceRequest _request = (DefaultBindMultipleDeviceRequest)request;
		Date startDate=null, endDate=null;
		
		startDate=DateUtils.stringToDate(_request.getStartDate());
		endDate=DateUtils.stringToDate(_request.getEndDate());
		
		
		Customer customer = this.customerRepository.getCustomerByUserId(_request.getUserId());
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
//		customer.setServiceStop(sdf.parse(_request.getEndDate()));//old
		customer.setServiceStop(endDate);
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
		
		//添加是否同步状态，以便铁通数据同步
		customer.setIsSync(SyncType.WAITSYNC);
		return customer;
	}

	/**************************************************************集团业务办理接口********************************************************/
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
		if(StringUtils.isBlank(_request.getUserId())){
			return new DefaultResponse(false, "Update rate failed, because userId is null!");
		}
		if(StringUtils.isBlank(_request.getPhone())){
			return new DefaultResponse(false, "Update rate failed, because phone is null!");
		}
		if(StringUtils.isBlank(_request.getRate())){
			return new DefaultResponse(false, "Update rate failed, because rate is null!");
		}
		if(StringUtils.isBlank(_request.getSno())){
			return new DefaultResponse(false, "Update rate failed, because sno is null!");
		}
		Customer customer = this.customerRepository.getCustomerByUserId(_request.getUserId());
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

	@Override
	public IResponse doAfterUpdateRate(IUpdateRateRequest request, boolean isUpdateRateSuccess) throws Exception {
		if(isUpdateRateSuccess){
			return new DefaultResponse("Update rate success!");
		}
		return new DefaultResponse(false, "Update rate failed!");
	}

	/**************************************************************终端暂停启用接口********************************************************/
	@Override
	public IResponse checkPauseOrRecoverDeviceParam(IPauseOrRecoverDeviceRequest request) throws Exception {
		if(!(request instanceof DefaultPauseOrRecoverDeviceRequest)){
			return new DefaultResponse(false, "Pause or recover failed, because param wrong");
		}
		DefaultPauseOrRecoverDeviceRequest _request = (DefaultPauseOrRecoverDeviceRequest)request;
		boolean isExist = this.talkSystemService.checkTalkSystem(_request.getSystemCode());
		if(!isExist){
			return new DefaultResponse(false, Constant.SYSTEM_CODE_ILLEGAL);
		}
		if(StringUtils.isBlank(_request.getSno())){
			return new DefaultResponse(false, "Pause or recover failed, because sno is null!");
		}
		if(StringUtils.isBlank(_request.getAction())){
			return new DefaultResponse(false, "Pause or recover failed, because action is null!");
		}
		Device device = this.deviceRepository.getDeviceBySno(_request.getSno());
		if(device == null){
			return new DefaultResponse(false, "Pause or recover failed, because sno is not exist!");
		}
		return null;
	}

	@Override
	public IResponse doAfterPauseOrRecoverDevice(IPauseOrRecoverDeviceRequest request, boolean isPauseOrRecoverDeviceSuccess) throws Exception {
		if(isPauseOrRecoverDeviceSuccess){
			return new DefaultResponse("Pause or recover success!");
		}
		return new DefaultResponse(false, "Pause or recover failed!");
	}

}
