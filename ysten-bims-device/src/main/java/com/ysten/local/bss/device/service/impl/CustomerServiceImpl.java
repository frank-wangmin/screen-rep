package com.ysten.local.bss.device.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.ysten.core.AppErrorCodeException;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysten.area.domain.Area;
import com.ysten.area.repository.IAreaRepository;
import com.ysten.boss.systemconfig.service.SystemConfigService;
import com.ysten.local.bss.device.bean.HdcRequestBean;
import com.ysten.local.bss.device.bean.NumberGenerator;
import com.ysten.local.bss.device.bean.ProductParams;
import com.ysten.local.bss.device.domain.City;
import com.ysten.local.bss.device.domain.Customer;
import com.ysten.local.bss.device.domain.Customer.State;
import com.ysten.local.bss.device.domain.CustomerCust;
import com.ysten.local.bss.device.domain.CustomerDeviceHistory;
import com.ysten.local.bss.device.domain.Device;
import com.ysten.local.bss.device.domain.Device.BindType;
import com.ysten.local.bss.device.domain.Device.SyncType;
import com.ysten.local.bss.device.domain.DeviceCustomerAccountMap;
import com.ysten.local.bss.device.domain.LockType;
import com.ysten.local.bss.device.domain.UserGroup;
import com.ysten.local.bss.device.domain.UserGroupMap;
import com.ysten.local.bss.device.domain.UserGroupPpInfoMap;
import com.ysten.local.bss.device.repository.ICityRepository;
import com.ysten.local.bss.device.repository.ICustomerCustRepository;
import com.ysten.local.bss.device.repository.ICustomerRepository;
import com.ysten.local.bss.device.repository.IDeviceCustomerAccountMapRepository;
import com.ysten.local.bss.device.repository.IDeviceRepository;
import com.ysten.local.bss.device.repository.IUserGroupMapRepository;
import com.ysten.local.bss.device.repository.IUserGroupPpInfoMapRepository;
import com.ysten.local.bss.device.repository.IUserGroupRepository;
import com.ysten.local.bss.device.service.ICustomerService;
import com.ysten.local.bss.device.utils.EnumConstantsInterface.UserGroupType;
import com.ysten.utils.code.MD5;
import com.ysten.utils.date.DateUtil;

/**
 * 
 * 类名称：CustomerServiceImp 类描述： 客户管理
 * 
 * @version
 */
@Service
public class CustomerServiceImpl implements ICustomerService {

	@Autowired
	private ICustomerRepository customerRespository;
	@Autowired
	private ICustomerCustRepository customerCustRepository;
	@Autowired
	private IDeviceRepository deviceRepository;
	@Autowired
	private SystemConfigService systemConfigService;

	@Autowired
	private IUserGroupRepository userGroupRepository;
	@Autowired
	private IUserGroupMapRepository userGroupMapRepository;
	@Autowired
	private IDeviceCustomerAccountMapRepository deviceCustomerAccountMapRepository;
    @Autowired
    private IUserGroupPpInfoMapRepository userGroupPpInfoMapRepository;
    @Autowired
    private ICityRepository cityRepository;
    @Autowired
    protected IAreaRepository areaRepository;

    private static final String CUSTOMER_ORDER_TYPE_SUBSCRIBE = "1";   //用户订购状态--订购
    private static final String CUSTOMER_ORDER_TYPE_UNSUBSCRIBE = "0"; //用户订购状态--退订

	@Override
	public Customer getCustomerByCode(String customerCode) {
		return this.customerRespository.getCustomerByCode(customerCode);
	}

	@Override
	public Customer getCustomerById(long id) {
		return this.customerRespository.getCustomerById(id);
	}

	@Override
	public boolean simpleSyncCustomer(Customer customer) {
		Customer dbCustomer = this.getCustomerByUserId(customer.getUserId());
		if (null == dbCustomer) {
			return this.customerRespository.saveCustomer(customer);
		} else {
			return this.customerRespository.updateByUserId(customer);
		}
	}

	@Override
	public boolean batchSimpleSyncCustomer(List<Customer> customers) {
		if (!org.springframework.util.CollectionUtils.isEmpty(customers)) {
			boolean result;
			for (Customer customer : customers) {
				result = this.simpleSyncCustomer(customer);
				if (!result)
					return result;
			}
		}
		return true;
	}

	@Override
	public boolean syncCustomer(JSONObject json) throws ParseException {
		String userId = json.getString("userId");
		Customer customer = this.getCustomerByUserId(userId);
		if (null == customer) {
			return createNewCustomer(json);
		} else {
			return updateExistedCustomer(customer, json);
		}
	}

	/**
	 * create a new customer which is not existed in database.
	 * 
	 * @param json
	 * @return
	 * @throws ParseException
	 */
	private boolean createNewCustomer(JSONObject json) throws ParseException {
		Customer customer = new Customer();
		customer.setState(State.NORMAL);
		customer.setCreateDate(new Date());
		customer.setStartDate(new Date());
		customer.setSource(this.systemConfigService.getSystemConfigByConfigKey("source"));
		customer.setCode(NumberGenerator.getNextCode());
		Area area = new Area();
		String areaId = this.systemConfigService.getSystemConfigByConfigKey("areaId");
		area.setId(Long.valueOf(areaId));
		customer.setArea(area);
		copyPropertyFromJson(json, customer);
		if (!this.createCustomer(customer)) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param json
	 * @param customer
	 * @throws ParseException
	 * 
	 */
	private void copyPropertyFromJson(JSONObject json, Customer customer) throws ParseException {
		customer.setUserId(json.getString("userId"));
		if (StringUtils.isNotBlank(json.getString("password"))) {
			customer.setPassword(json.getString("password"));
		}
		if (StringUtils.isNotBlank(json.getString("loginName"))) {
			customer.setLoginName(json.getString("loginName"));
		}
		else {
			customer.setLoginName(json.getString("userId"));
		}
		if (StringUtils.isNotBlank(json.getString("realName"))) {
			customer.setRealName(json.getString("realName"));
		} else {
			customer.setRealName(json.getString("userId"));
		}
		if (StringUtils.isNotBlank(json.getString("customerType"))) {
			customer.setCustomerType(Customer.CustomerType.valueOf(json.getString("customerType")));
		}
		if (StringUtils.isNotBlank(json.getString("sex"))) {
			customer.setSex(Customer.Sex.valueOf(json.getString("sex")));
		}
		if (StringUtils.isNotBlank(json.getString("phone"))) {
			customer.setPhone(json.getString("phone"));
		}
		if (StringUtils.isNotBlank(json.getString("profession"))) {
			customer.setProfession(json.getString("profession"));
		}
		if (StringUtils.isNotBlank(json.getString("hobby"))) {
			customer.setHobby(json.getString("hobby"));
		}
		if (StringUtils.isNotBlank(json.getString("identityType"))) {
			customer.setIdentityType(Customer.IdentityType.valueOf(json.getString("identityType")));
		}
		if (StringUtils.isNotBlank(json.getString("identityCode"))) {
			customer.setIdentityCode(json.getString("identityCode"));
		}
		if (StringUtils.isNotBlank(json.getString("zipCode"))) {
			customer.setZipCode(json.getString("zipCode"));
		}
		if (StringUtils.isNotBlank(json.getString("address"))) {
			customer.setAddress(json.getString("address"));
		}
		if (StringUtils.isNotBlank(json.getString("province"))) {
			customer.setProvince(json.getString("province"));
		}
		if (StringUtils.isNotBlank(json.getString("region"))) {
			City city = this.cityRepository.getCityByCityCode(json.getString("region"));
			if (null != city) {
				customer.setRegion(city);
			} else {
				customer.setRegion(null);
			}
		}
		if (StringUtils.isNotBlank(json.getString("age"))) {
			customer.setAge(Integer.parseInt(json.getString("age")));
		}
		if (StringUtils.isNotBlank(json.getString("mail"))) {
			customer.setMail(json.getString("mail"));
		}
		if (StringUtils.isNotBlank(json.getString("expireDate"))) {
			customer.setEndDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(json.getString("expireDate")));
		}
	}


	@Override
	public boolean checkRequired(JSONObject json) {
		if (StringUtils.isBlank(json.getString("systemCode")) || StringUtils.isBlank(json.getString("userId")) ||
				StringUtils.isBlank(json.getString("password")) || StringUtils.isBlank(json.getString("customerType")) ||
				StringUtils.isBlank(json.getString("region")) || StringUtils.isBlank(json.getString("productlist"))) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param customer
	 * @param json
	 * @return
	 * @throws ParseException
	 */
	private boolean updateExistedCustomer(Customer customer, JSONObject json) throws ParseException {
		copyPropertyFromJson(json, customer);
		return this.updateCustomer(customer);
	}

	@Override
	public boolean saveCustomer(Customer customer) {
		if (!this.customerRespository.saveCustomer(customer)) {
			return false;
		}
		// 此处的用户的deviceCode必须为终端序列号(5M码才对)，否则会有问题
		String deviceCode = customer.getDeviceCode();
		if (StringUtils.isNotBlank(deviceCode)) {
			Device device = this.deviceRepository.getDeviceBySno(deviceCode);
			if (null != device && Device.State.NONACTIVATED.equals(device.getState())) {
				if (this.deviceBindCustomer(customer, device)) {
					return true;
				}
			}
			customer.setDeviceCode(null);
			return this.customerRespository.updateCustomer(customer);
		}
		return true;
	}

	@Override
	public boolean createCustomer(Customer customer) {
		if (!this.customerRespository.saveCustomer(customer)) {
			return false;
		}
		String deviceCode = customer.getDeviceCode();
		if (!StringUtils.isEmpty(deviceCode)) {
			Device device = this.deviceRepository.getDeviceBySno(deviceCode);
			if (null != device && Device.State.NONACTIVATED == device.getState() && Device.BindType.UNBIND == device.getBindType()) {
				SimpleDateFormat dateFormatTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					device.setExpireDate(dateFormatTime.parse(HdcRequestBean.EXPIRE_DATE));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if (!deviceBindCustomer(customer, device)) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public boolean updateCustomer(Customer customer) {
		List<DeviceCustomerAccountMap> maps = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByCustomerId(customer.getId());
		if (customer.getState() == Customer.State.CANCEL) {
			boolean resCode = cancelCustomer(customer, maps);
			return resCode;
		}
		if (null != maps && maps.size() > 0) {
			DeviceCustomerAccountMap map = maps.get(0);
			if (!customer.getUserId().equals(map.getUserId())) {
				map.setUserId(customer.getUserId());
				this.deviceCustomerAccountMapRepository.updateDeviceCustomerAccountMap(map);
			}
		}
		return this.customerRespository.updateCustomer(customer);
	}

	@Override
	public boolean updateCustomerAsProcessed(List<String> userIds) {
		return this.customerRespository.updateCustomerAsProcessed(userIds);
	}

	@Override
	public boolean updateCustomerCode(Customer customer, DeviceCustomerAccountMap map) {
		if (this.customerRespository.updateCustomer(customer)) {
			return this.deviceCustomerAccountMapRepository.updateDeviceCustomerAccountMap(map);
		}
		return false;
	}

	@Override
	public Customer getCustomerByUserId(String userId) {
		return this.customerRespository.getCustomerByUserId(userId);
	}

	@Override
	public Customer getCustomerByPhone(String phone) {
		return this.customerRespository.getCustomerByPhone(phone);
	}

	@Override
	public Customer getMinEnableCustomerByCustId(String custId) {
		return this.customerRespository.getMinCustomerByCustId(custId);
	}

	@Override
	public List<Customer> getNotProcessedCustomers() {
		return this.customerRespository.getNotProcessedCustomers();
	}

	@Override
	public boolean cancelCustomer(Customer customer, List<DeviceCustomerAccountMap> maps) {
		Date curDate = new Date();
		if (null != maps && maps.size() > 0) {
			Device oldDevice = this.deviceRepository.getDeviceById(maps.get(0).getDeviceId());
			// set the old device to NONACTIVATED[NOTUSE].
			oldDevice.setState(Device.State.NONACTIVATED);
			oldDevice.setBindType(Device.BindType.UNBIND);
			oldDevice.setIsLock(LockType.LOCK);
			oldDevice.setStateChangeDate(curDate);
			oldDevice.setIsSync(SyncType.WAITSYNC);
			if (!deviceRepository.updateDevice(oldDevice)) {
				oldDevice = null;
				return false;
			}
			// delete relation.

			boolean flag = false;
			List<DeviceCustomerAccountMap> list = deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceId(oldDevice.getId());
			for (int i = 0; i < list.size(); i++) {
				DeviceCustomerAccountMap map = list.get(i);
				flag = deviceCustomerAccountMapRepository.delete(map);
			}
			if (flag == false) {
				oldDevice = null;
				return false;
			}
			// insert record.
			CustomerDeviceHistory history = new CustomerDeviceHistory();
//			history.setCustomerId(customer.getId());
			history.setCustomerCode(customer.getCode());
			history.setDeviceId(oldDevice.getId());
			history.setDeviceCode(oldDevice.getCode());
//			history.setOldDeviceCode(oldDevice.getCode());
//			history.setOldDeviceId(oldDevice.getId());
			history.setCreateDate(curDate);
			if (!this.deviceRepository.saveCustomerDeviceHistory(history)) {
				oldDevice = null;
				history = null;
				return false;
			}
		}
		customer.setDeviceCode(null);
		customer.setCancelledDate(curDate);
		customer.setStateChangeDate(curDate);
		return this.customerRespository.updateCustomer(customer);
	}

	@Override
	public boolean changeDeviceBind(Customer customer, Device device) {
		List<DeviceCustomerAccountMap> mapList = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByCustomerId(customer.getId());
		if (null == mapList || mapList.size() <= 0) {
			return this.deviceBindCustomer(customer, device);
		}
		return this.changeDeviceBind(customer, device, mapList.get(0));
	}

	@Override
	public boolean changeDeviceBind(Customer customer, Device device, DeviceCustomerAccountMap map) {
		if (-1l == map.getDeviceId()) {
			return deviceBindCustomer(customer, device);
		}
		Device oldDevice = this.deviceRepository.getDeviceById(map.getDeviceId());
		if (oldDevice == null) {
			return false;
		}
		Date curDate = new Date();
		// 老设备的state变更为NONACTIVATED
		oldDevice.setState(Device.State.NONACTIVATED);
		oldDevice.setBindType(Device.BindType.UNBIND);
		oldDevice.setIsLock(LockType.LOCK);
		oldDevice.setStateChangeDate(curDate);
		oldDevice.setIsSync(SyncType.WAITSYNC);
		if (!deviceRepository.updateDevice(oldDevice)) {
			return false;
		}
		// 激活新设备
		device.setState(Device.State.ACTIVATED);
		device.setBindType(Device.BindType.BIND);
		device.setIsLock(LockType.UNLOCKED);
		device.setStateChangeDate(curDate);
		device.setActivateDate(curDate);
		if (!deviceRepository.updateDevice(device)) {
			return false;
		}
		CustomerDeviceHistory history = new CustomerDeviceHistory();
//		history.setCustomerId(customer.getId());
		history.setCustomerCode(customer.getCode());
		history.setDeviceId(device.getId());
		history.setDeviceCode(device.getCode());
//		history.setOldDeviceCode(oldDevice.getCode());
//		history.setOldDeviceId(oldDevice.getId());
		history.setCreateDate(curDate);
		if (!this.deviceRepository.saveCustomerDeviceHistory(history)) {
			return false;
		}
		customer.setDeviceCode(device.getCode());
		if (!this.customerRespository.updateCustomer(customer)) {
			return false;
		}
		map.setDeviceId(device.getId());
		map.setDeviceCode(device.getCode());
		map.setDeviceSno(device.getSno());
		map.setReplacement(0);
		map.setCreateDate(curDate);
		return this.deviceCustomerAccountMapRepository.updateDeviceCustomerAccountMap(map);
	}

	@Override
	public boolean deviceBindCustomer(Customer customer, Device device) {
		DeviceCustomerAccountMap bindMap = new DeviceCustomerAccountMap();
		Date date = new Date();
		bindMap.setCustomerCode(customer.getCode());
		bindMap.setCustomerId(customer.getId());
		bindMap.setUserId(customer.getUserId());
		bindMap.setDeviceId(device.getId());
		bindMap.setDeviceCode(device.getCode());
		bindMap.setDeviceSno(device.getSno());
		bindMap.setAccountId(0L);
		bindMap.setCreateDate(date);
		if(StringUtils.isNotBlank(customer.getZipCode())) {
		    bindMap.setAccountCode(customer.getZipCode());
		} else {
		    bindMap.setAccountCode("");
		}
		if (!this.deviceCustomerAccountMapRepository.saveDeviceCustomerAccountMap(bindMap)) {
			return false;
		}
		// 激活用户
		customer.setState(State.NORMAL);
		customer.setDeviceCode(device.getCode());
		customer.setUpdateTime(date);
		customer.setActivateDate(date);
		if (!customerRespository.updateCustomer(customer)) {
			return false;
		}
		// 激活新设备
		device.setBindType(Device.BindType.BIND);
		device.setState(Device.State.ACTIVATED);
		device.setIsLock(LockType.UNLOCKED);
		device.setStateChangeDate(date);
		device.setIsSync(SyncType.WAITSYNC);
		device.setActivateDate(date);
		return deviceRepository.updateDevice(device);
	}

	@Override
	public boolean deviceUnbindCustomer(Customer customer, Device device) {
		boolean flag = false;
		List<DeviceCustomerAccountMap> list = deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceId(device.getId());
		for (int i = 0; i < list.size(); i++) {
			DeviceCustomerAccountMap map = list.get(i);
			flag = deviceCustomerAccountMapRepository.delete(map);
		}
		if (flag == false) {
			return false;
		}
		customer.setActivateDate(null);
		customer.setDeviceCode(null);
		if (!this.customerRespository.updateCustomer(customer)) {
			return false;
		}
		device.setState(Device.State.NONACTIVATED);
		device.setBindType(Device.BindType.UNBIND);
		device.setActivateDate(null);
		return this.deviceRepository.updateDevice(device);
	}

	@Override
	public String[] bindDevice(ProductParams productParams, String effectiveeDate, String expireDate) throws ParseException {
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String[] result = new String[2];
		Device device = deviceRepository.getDeviceBySno(productParams.getSNO());
		if (device != null && BindType.UNBIND.equals(device.getBindType())) {
			String source = systemConfigService.getSystemConfigByConfigKey("source");
			String areaId = systemConfigService.getSystemConfigByConfigKey("areaId");
			Area area = new Area();
			area.setId(Long.parseLong(areaId));

			if (expireDate != null) {
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
				effectiveeDate = sdf2.format(sdf1.parse(effectiveeDate));
				expireDate = sdf2.format(sdf1.parse(expireDate));
			} else {
				effectiveeDate = sdf2.format(new Date());
				expireDate = "2099-12-31 23:59:59";
			}
			device.setBindType(BindType.BIND);
			device.setState(Device.State.ACTIVATED);
			device.setActivateDate(sdf2.parse(effectiveeDate));
			device.setExpireDate(sdf2.parse(expireDate));
			device.setArea(area);
			deviceRepository.updateDevice(device);

			Customer customer = new Customer();
			customer.setLoginName(productParams.getPayPhoneNO());
			customer.setNickName(productParams.getPayPhoneNO());
			customer.setRealName(productParams.getUserName());
			customer.setUserId(productParams.getSheetNO());
			customer.setPhone(productParams.getPayPhoneNO());
			// customer.setRegion(areaId);//重庆58
			customer.setProvince(areaId);
			customer.setArea(area);
			customer.setSource(source);
			customer.setCounty(productParams.getCountyName());
			customer.setAddress(productParams.getFixAddress());
			customer.setCreateDate(new Date());
			customer.setPassword(MD5.encrypt(productParams.getLogPass()));
			customer.setCode(NumberGenerator.getNextCode());
			customer.setActivateDate(new Date());
			customer.setServiceStop(sdf2.parse(expireDate));
			customer.setDeviceCode(device.getCode());
			customerRespository.saveCustomer(customer);

			DeviceCustomerAccountMap dcaMap = new DeviceCustomerAccountMap();
			dcaMap.setCustomerId(customer.getId());
			dcaMap.setCustomerCode(customer.getCode());
			dcaMap.setDeviceId(device.getId());
			dcaMap.setDeviceCode(device.getCode());
			dcaMap.setDeviceSno(device.getSno());
			dcaMap.setCreateDate(new Date());
			dcaMap.setReplacement(0);
			dcaMap.setUserId(customer.getUserId());
			this.deviceCustomerAccountMapRepository.saveDeviceCustomerAccountMap(dcaMap);

			result[0] = "0";
			result[1] = "处理成功";
		} else {
			result[0] = "18";
			result[1] = "该产品未生效";
		}
		return result;
	}

	@Override
	public String[] revokeDevice(String sheetNO) {
		String[] result = new String[2];
		Customer customer = customerRespository.getCustomerByUserId(sheetNO);
		if (customer != null) {
			List<DeviceCustomerAccountMap> dcaMapLists = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByCustomerId(customer.getId());
			if (dcaMapLists != null && dcaMapLists.size() > 0) {
				for (DeviceCustomerAccountMap map : dcaMapLists) {
					Device device = deviceRepository.getDeviceById(map.getDeviceId());
					if (device == null) {
						result[0] = "18";
						result[1] = "该产品未生效";
					} else {
						customer.setState(Customer.State.CANCEL);
						customerRespository.updateCustomer(customer);
				     	List<DeviceCustomerAccountMap>  list=deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceId(device.getId());
				    	for(int i=0;i<list.size();i++){
				    		DeviceCustomerAccountMap dcaMap=list.get(i);
				    		deviceCustomerAccountMapRepository.delete(dcaMap);
				    	}
						device.setState(Device.State.NONACTIVATED);
						device.setIsSync(SyncType.WAITSYNC);
						device.setBindType(BindType.UNBIND);
						device.setStateChangeDate(new Date());
						this.deviceRepository.updateDevice(device);
						result[0] = "0";
						result[1] = "处理成功";
					}
				}
			} else {
				result[0] = "18";
				result[1] = "该产品未生效";
			}
		} else {
			result[0] = "4";
			result[1] = "用户账户不存在";
		}
		return result;
	}

	@Override
	public String[] pauseDevice(String sheetNO) {
		String state = State.SUSPEND.toString();
		boolean result = this.customerRespository.pauseAndRecoverDevice(sheetNO, state);
		String[] results = new String[2];
		if (result) {
			results[0] = "0";
			results[1] = "处理成功";
		} else {
			results[0] = "4";
			results[1] = "用户帐号不存在";
		}
		return results;
	}

	@Override
	public String[] recoverDevice(String sheetNO) {
		String state = State.NORMAL.toString();
		boolean result = this.customerRespository.pauseAndRecoverDevice(sheetNO, state);
		String[] results = new String[2];
		if (result) {
			results[0] = "0";
			results[1] = "处理成功";
		} else {
			results[0] = "4";
			results[1] = "用户帐号不存在";
		}
		return results;
	}

	@Override
	public String[] rebindDevice(String sheetNO, String newsno) {
		String[] result = new String[2];
		Device newDevice = deviceRepository.getDeviceBySno(newsno);
		if (newDevice != null && BindType.UNBIND.equals(newDevice.getBindType())) {
			Customer customer = customerRespository.getCustomerByUserId(sheetNO);
			List<DeviceCustomerAccountMap> dcaMaps = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByCustomerId(customer.getId());
			if (dcaMaps != null && dcaMaps.size() > 0) {
				for (DeviceCustomerAccountMap dcaMap : dcaMaps) {
					Device device = this.deviceRepository.getDeviceByCode(dcaMap.getDeviceCode());
					if (device != null) {
						if (device.getSno() != null && device.getSno().equals(newsno)) {
							result[0] = "17";
							result[1] = "Mac地址已被使用";
							break;
						}
						device.setSno(newsno);
						this.deviceRepository.updateDevice(device);
						CustomerDeviceHistory history = new CustomerDeviceHistory();
//						history.setCustomerId(customer.getId());
						history.setCustomerCode(customer.getCode());
						history.setDeviceId(device.getId());
						history.setDeviceCode(device.getCode());
//						history.setOldDeviceCode(dcaMap.getDeviceCode());
//						history.setOldDeviceId(dcaMap.getDeviceId());
						history.setCreateDate(new Date());
						deviceRepository.saveCustomerDeviceHistory(history);
						result[0] = "0";
						result[1] = "处理成功";
					} else {
						result[0] = "18";
						result[1] = "该产品未生效";
						break;
					}
				}
			} else {
				result[0] = "18";
				result[1] = "该产品未生效";
			}
		} else {
			result[0] = "18";
			result[1] = "该产品未生效";
		}
		return result;
	}

	@Override
	public String[] updateServiceStop(String userId, String servicestop) throws ParseException {
		Customer customer = this.customerRespository.getCustomerByUserId(userId);
		if (null == customer) {
			return new String[] { "false", "customer:" + userId + " is not existed." };
		}
		if (StringUtils.isNotBlank(servicestop) && servicestop.length() == 8) {
			servicestop = servicestop + "235959";
		}
		Date eDate = DateUtil.convertStringToDate("yyyyMMddHHmmss", servicestop);
		Date sDate = customer.getStartDate();
		if (eDate.getTime() <= sDate.getTime()) {
			return new String[] { "false", "update deivce services stop date failed, because service stop time is before service start time." };
		}
		customer.setServiceStop(eDate);
		this.customerRespository.updateCustomer(customer);
		List<DeviceCustomerAccountMap> mapList = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByCustomerId(customer.getId());
		if (!CollectionUtils.isEmpty(mapList)) {
			Device device = this.deviceRepository.getDeviceById(mapList.get(0).getDeviceId());
			if (null != device) {
				device.setExpireDate(eDate);
				this.deviceRepository.updateDevice(device);
			}
		}
		return new String[] { "true", "update customer services stop date success!" };
	}

	@Override
	public long getAllCustomerCreatedLastDay(String cityId) {
		return this.customerRespository.getAllCustomerCreatedLastDay(cityId);
	}

	@Override
	public long getAllCustomer(String cityId) {
		return this.customerRespository.getAllCustomer(cityId);
	}

	@Override
	public long getAllCustomerCancelLastDay(String cityId) {
		return this.customerRespository.getAllCustomerCancelLastDay(cityId);
	}

	@Override
	public List<UserGroup> findUserGroupListByUserIdAndType(String userCode, UserGroupType type) {
		List<UserGroupMap> userGroupMap = this.userGroupMapRepository.findByUserCode(userCode);
		if (userGroupMap != null && userGroupMap.size() > 0) {
			return this.userGroupRepository.findUserGroupListByIdsAndType(userGroupMap, type);
		}
		return null;
	}

	@Override
	public Customer getcustomerByNickName(String userId) {
		return this.customerRespository.getcustomerByNickName(userId);
	}
	
	@Override
    public Customer getCustomerByYstenId(String ystenId) {
//        DeviceCustomerAccountMap dcm = this.deviceCustomerAccountMapRepository.getByYstenId(ystenId);
//        if (dcm != null) {
//        	return this.customerRespository.getCustomerByUserId(dcm.getUserId());
//        }
//        return null;
		Device device = this.deviceRepository.getDeviceByYstenId(ystenId);
		if(device != null && device.getCustomerId() != null){
			return this.customerRespository.getCustomerById(device.getCustomerId());
		}
		return null;
    }

    @Override
    public Customer getCustomerBySno(String sno) {
//        DeviceCustomerAccountMap dcm = this.deviceCustomerAccountMapRepository.getByYstenId(ystenId);
//        if (dcm != null) {
//        	return this.customerRespository.getCustomerByUserId(dcm.getUserId());
//        }
//        return null;
        Device device = this.deviceRepository.getDeviceBySno(sno);
        if(device != null && device.getCustomerId() != null){
            return this.customerRespository.getCustomerById(device.getCustomerId());
        }
        return null;
    }

    @Override
    public void bindUserGroupByProductId(String userId, String productId, String districtCode, String orderType) throws Exception {

        Long areaId = this.cityRepository.getAreaIdByDistrictCode(districtCode.trim().substring(0,2).concat("0000"));

        if(null == areaId){
            AppErrorCodeException appErrorCodeException = new AppErrorCodeException("can not find area id in bims! districtCode is " + districtCode);
            appErrorCodeException.setErrorCode(906);
            throw appErrorCodeException;
        }

        List<UserGroup>  userGroupList =this.userGroupRepository.findUserGroupsByProductIdAndArea(productId.trim(), areaId);
        if(CollectionUtils.isEmpty(userGroupList)){
            AppErrorCodeException appErrorCodeException =
                    new AppErrorCodeException("can not find matched usergroup! productId is " + productId + " districtCode is "+ districtCode + " Area Id is" + areaId);
            appErrorCodeException.setErrorCode(907);
            throw appErrorCodeException;
        }

        //查找对应区域的用户
        List<Customer> customerList = this.customerRespository.getCustomerByUserIdAndAreaId(userId, areaId);
        if(CollectionUtils.isEmpty(customerList)){
            AppErrorCodeException appErrorCodeException =
                    new AppErrorCodeException("Customer not found! userId is: " + userId + " areaId is: " + areaId);
            appErrorCodeException.setErrorCode(908);
            throw appErrorCodeException;
        }

        Long userGroupId = userGroupList.get(0).getId();
        this.saveCustomerGroup(userGroupId, customerList, orderType);
    }

    public void saveCustomerGroup(Long userGroupId, List<Customer> customerList, String orderType) throws Exception{
        for(Customer customer : customerList){
            String userCode = customer.getCode();
            UserGroupMap userGroupMap = this.userGroupMapRepository.getByUserCodeAndGroupId(userGroupId, userCode.trim());
            //用户订购，绑定用户分组
            if(orderType.trim().equals(CUSTOMER_ORDER_TYPE_SUBSCRIBE)){
                if(null != userGroupMap){
                    continue; //一户多机情况，存在多个userId相同的用户记录
//                    AppErrorCodeException appErrorCodeException =
//                            new AppErrorCodeException("user have binded to userGroup! userId is " + userId + " userGroupId is "+ userGroupId);
//                    appErrorCodeException.setErrorCode(909);
//                    throw appErrorCodeException;
                }else{
                    userGroupMap = new UserGroupMap();
                    userGroupMap.setCode(userCode);
                    userGroupMap.setUserGroupId(userGroupId);
                    userGroupMap.setCreateDate(new Date());
                    this.userGroupMapRepository.saveUserGroupMap(userGroupMap);
                }
            }
            //用户退订，解绑用户分组
            else if(orderType.trim().equals(CUSTOMER_ORDER_TYPE_UNSUBSCRIBE)){
                if(null == userGroupMap){
                    continue; //一户多机情况，存在多个userId相同的用户记录
//                    AppErrorCodeException appErrorCodeException =
//                            new AppErrorCodeException("user have not binded to userGroup! userId is " + userId + " userGroupId is "+ userGroupId);
//                    appErrorCodeException.setErrorCode(910);
//                    throw appErrorCodeException;
                }else{
                    this.userGroupMapRepository.deleteMapByUserCodeAndGroupId(userCode, userGroupId);
                }
            }
        }
    }

    @Override
    public CustomerCust getCustomerCustByCustId(String custId) {
        return this.customerCustRepository.getCustomerCustByCustId(custId);
    }

    @Override
    public boolean saveCustomerCust(CustomerCust customerCust) {
        return this.customerCustRepository.saveCustomerCust(customerCust);
    }

    @Override
    public boolean updateCustomerCust(CustomerCust customerCust) {
        return this.customerCustRepository.updateCustomerCust(customerCust);
    }
}
