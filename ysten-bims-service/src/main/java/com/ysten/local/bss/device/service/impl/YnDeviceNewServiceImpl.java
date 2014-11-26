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
import com.ysten.local.bss.device.api.domain.request.DefaultRebindDeviceRequest;
import com.ysten.local.bss.device.api.domain.request.DefaultUpdateRateRequest;
import com.ysten.local.bss.device.api.domain.request.DefaultUpdateServicesStopRequest;
import com.ysten.local.bss.device.api.domain.request.IBindDeviceRequest;
import com.ysten.local.bss.device.api.domain.request.IBindMultipleDeviceRequest;
import com.ysten.local.bss.device.api.domain.request.IDisableDeviceRequest;
import com.ysten.local.bss.device.api.domain.request.IRebindDeviceRequest;
import com.ysten.local.bss.device.api.domain.request.IUpdateRateRequest;
import com.ysten.local.bss.device.api.domain.request.IUpdateServicesStopRequest;
import com.ysten.local.bss.device.api.domain.request.MultipleBindDeviceSno;
import com.ysten.local.bss.device.api.domain.response.DefaultResponse;
import com.ysten.local.bss.device.api.domain.response.IResponse;
import com.ysten.local.bss.device.bean.NumberGenerator;
import com.ysten.local.bss.device.domain.City;
import com.ysten.local.bss.device.domain.Customer;
import com.ysten.local.bss.device.domain.Customer.CustomerType;
import com.ysten.local.bss.device.domain.CustomerDeviceHistory;
import com.ysten.local.bss.device.domain.Device;
import com.ysten.local.bss.device.domain.Device.BindType;
import com.ysten.local.bss.device.domain.Device.SyncType;
import com.ysten.local.bss.device.domain.DeviceCustomerAccountMap;
import com.ysten.local.bss.device.domain.LockType;
import com.ysten.local.bss.device.repository.ICityRepository;
import com.ysten.local.bss.device.repository.IDeviceCustomerAccountMapRepository;
import com.ysten.local.bss.device.service.IYnDeviceService;
import com.ysten.local.bss.talk.service.ITalkSystemService;
import com.ysten.local.bss.util.DateUtils;
import com.ysten.local.bss.util.bean.Constant;
import com.ysten.utils.code.MD5;

/**
 * 云南
 * 根据phone 获取customer --- phone一般为宽带账号
 * 
 */
@Service
public class YnDeviceNewServiceImpl extends DefaultDeviceNewServiceImpl implements IYnDeviceService{

	@Autowired
	private ITalkSystemService talkSystemService;
	@Autowired
	private IDeviceCustomerAccountMapRepository deviceCustomerAccountMapRepository;
    @Autowired
    private ICityRepository cityRepository;


    private static final String ONE_CUSTOMER_ONE_DEVICE = "0";
	protected  static final String ONE_CUSTOMER_MULTI_DEVICE = "1";
	
	/**
	 * 普通绑定校验
	 * @author chenxiang
	 * @date 2014-8-7 下午3:24:42 
	 */
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

	
	/**
	 * 初始化 普通用户的Customer
	 * @author chenxiang
	 * @date 2014-8-7 下午3:25:04 
	 */
	@Override
	public Customer initCustomerByRequest(IBindDeviceRequest request) throws Exception{
		DefaultBindDeviceRequest _request = (DefaultBindDeviceRequest)request;
		Date startDate=null, endDate=null;
		
		startDate=DateUtils.stringToDate(_request.getStartDate());
		endDate=DateUtils.stringToDate(_request.getEndDate());
		
		
		Customer customer = this.customerRepository.getCustomerByPhone(_request.getPhone()); //根据phone获取customer
		if(customer == null){
			customer = new Customer();
			customer.setCode(NumberGenerator.getNextCode());
		}else{
			customer.setState(Customer.State.NORMAL);
		}
		customer.setStartDate(startDate);
		customer.setEndDate(endDate);
		customer.setUserId(_request.getPhone());
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
	
	
	/**************************************************************集团业务办理接口********************************************************/
	/**
	 * 集团用户检验
	 * @author chenxiang
	 * @date 2014-8-7 下午3:25:25 
	 */
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
		return null;
	}
	
	/**
	 * 初始化 集团用户的Customer
	 * @author chenxiang
	 * @date 2014-8-7 下午3:34:51 
	 */
	@Override
	public Customer initMultipleCustomerByRequest(IBindMultipleDeviceRequest request) throws Exception {
		DefaultBindMultipleDeviceRequest _request = (DefaultBindMultipleDeviceRequest)request;
		Date startDate=null, endDate=null;
		
		startDate=DateUtils.stringToDate(_request.getStartDate());
		endDate=DateUtils.stringToDate(_request.getEndDate());
		
		
		Customer customer = this.customerRepository.getCustomerByPhone(_request.getPhone());
		if(customer == null){
			customer = new Customer();
			customer.setCode(NumberGenerator.getNextCode());
		}else{
			customer.setState(Customer.State.NORMAL);
		}
		customer.setStartDate(startDate);
		customer.setEndDate(endDate);
		customer.setUserId(_request.getPhone());
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
	
	
	/**************************************************************换机操作********************************************************/
	/**
	 * 换机验证
	 * @author chenxiang
	 * @date 2014-8-7 下午3:35:10 
	 */
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
		if(StringUtils.isBlank(_request.getPhone())){
			return new DefaultResponse(false, "device rebind failed because phone is null!");
		}
		if(StringUtils.isBlank(_request.getOldSno())){
			return new DefaultResponse(false, "device rebind failed because old sno is null!");
		}
		if(StringUtils.isBlank(_request.getNewSno())){
			return new DefaultResponse(false, "device rebind failed because new sno is null!");
		}
		Customer customer = this.customerRepository.getCustomerByPhone(_request.getPhone());
		if(customer == null){
			return new DefaultResponse(false, "device rebind failed because phone is not exist!");
		}
		Device oldDevice = this.deviceRepository.getDeviceBySno(_request.getOldSno());
		if(oldDevice == null){
			return new DefaultResponse(false, "device rebind failed because old sno is not exist!");
		}
		Customer _customer = this.customerRepository.getCustomerByDeviceId(oldDevice.getId());
		if(_customer == null){
			return new DefaultResponse(false, "user rebind device failed: there is no relationship between user and old device!");
		}
		
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
	
	
	
	/**
	 * 终端时间到期变更检验
	 * @author chenxiang
	 * @date 2014-8-7 下午3:43:09 
	 */
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
        // phone
		if(StringUtils.isBlank(_request.getPhone())){
			return new DefaultResponse(false, "update service stop failed, because phone is null!");
		}
		if(StringUtils.isBlank(_request.getServiceStop())){
			return new DefaultResponse(false, "update service stop failed, because serviceStop is null!");
		}

		Customer customer = this.customerRepository.getCustomerByPhone(_request.getPhone()); //根据phone 获取customer
		
        if(customer == null){
        	return new DefaultResponse(false, "update service stop failed, because phone is not exists!");
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
	

	/**
	 * 终端到期时间变更
	 * @author chenxiang
	 * @date 2014-8-7 下午3:56:46 
	 */
	protected boolean doUpdateCustomerServicesStop(IUpdateServicesStopRequest request) throws Exception{
		String serviceStop = request.getServiceStop();
		
		DefaultUpdateRateRequest _request = (DefaultUpdateRateRequest)request;
		Customer customer = this.customerRepository.getCustomerByPhone(_request.getPhone()); //根据phone 获取customer
		
		Date serviceStopDate=DateUtils.stringToDate(serviceStop);
		customer.setServiceStop(serviceStopDate);
		
		this.customerRepository.updateCustomer(customer);

        List<DeviceCustomerAccountMap> maps = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByCustomerId(customer.getId());
        if (CollectionUtils.isNotEmpty(maps)) {
            for (DeviceCustomerAccountMap map : maps) {
                // 如果type=only，那么只能更新指定的device，如果type=all，那么更新所有的device
                if(TYPE_ONLY.equals(request.getType()) && !map.getDeviceSno().equals(request.getSno())){
                    continue;
                }
                Device device = this.deviceRepository.getDeviceByCodeSnoAndMac(map.getDeviceCode());
                Date eDate = DateUtils.stringToDate(serviceStop);
                device.setExpireDate(eDate);
                this.deviceRepository.updateDevice(device);
            }
        }
        return true;
	}
	
	
	
	
	
	/**
	 * 销户验证
	 * @author chenxiang
	 * @date 2014-8-7 下午4:22:28 
	 */
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
		
		if(StringUtils.isBlank(_request.getSno())){
			return new DefaultResponse(false, "device disable failed, because sno is null!");
		}
        
        Customer customer = this.customerRepository.getCustomerByPhone(_request.getPhone()); //根据phone 获取customer
		if(customer == null){
			return new DefaultResponse(false, "device disable fail, because customer is not exists!");
		}
           
		 Device device = this.deviceRepository.getDeviceBySno(_request.getSno());
         if(device == null){
             return new DefaultResponse(false, "device disable fail, because sno is not exists!");
         }
         DeviceCustomerAccountMap map = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceCode(device.getCode());
         if(map == null){
 			return new DefaultResponse(false, "device disable fail, because device bind customer is not exists!");
 		}
         if(!StringUtils.equalsIgnoreCase(map.getCustomerCode(), customer.getCode())){
         	return new DefaultResponse(false, "device disable fail, because sno is bind to another userId!");
         }
		return null;
	}

	/**
	 * 销户操作
	 * @author chenxiang
	 * @date 2014-8-7 下午4:27:53 
	 */
	@Override
	protected boolean doDisableDeviceAndCustomer(IDisableDeviceRequest request) {
		DefaultUpdateRateRequest _request = (DefaultUpdateRateRequest)request;
		
		Device device = this.deviceRepository.getDeviceBySno(_request.getSno());
		DeviceCustomerAccountMap map = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceCode(device.getCode());
		
		//获取用户信息
        Customer customer = this.customerRepository.getCustomerById(map.getCustomerId());
        
		// 终端停用
        device.setState(Device.State.NONACTIVATED);
        device.setBindType(BindType.UNBIND);
        device.setIsLock(LockType.LOCK);
        device.setStateChangeDate(new Date());
        this.deviceRepository.updateDevice(device);
        
        //删除关系表
        deviceCustomerAccountMapRepository.delete(map);
        
	    //新增用户历史记录
	    CustomerDeviceHistory history = this.createCustomerDeviceHistory(null, device, customer);
	    this.deviceRepository.saveCustomerDeviceHistory(history);
        
		
        List<DeviceCustomerAccountMap> maps = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByCustomerId(customer.getId());
        boolean isAllDisable = true;
        if (CollectionUtils.isNotEmpty(maps)) {
            for (DeviceCustomerAccountMap map0 : maps) {
                if(!map0.getDeviceSno().equals(map.getDeviceSno())){
                	isAllDisable = false;
                    continue;
                }
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


	/**
	 * 宽带速率变更校验
	 * @author chenxiang
	 * @date 2014-8-7 下午4:07:29 
	 */
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
			return new DefaultResponse(false, "Update rate failed, because customer is not exists!");
		}
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
