package com.ysten.local.bss.device.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.ysten.local.bss.device.api.domain.request.IBindDeviceRequest;
import com.ysten.local.bss.device.api.domain.request.IBindMultipleDeviceRequest;
import com.ysten.local.bss.device.api.domain.request.ICreateUserRequest;
import com.ysten.local.bss.device.api.domain.request.IDisableDeviceRequest;
import com.ysten.local.bss.device.api.domain.request.IPauseOrRecoverDeviceRequest;
import com.ysten.local.bss.device.api.domain.request.IRebindDeviceRequest;
import com.ysten.local.bss.device.api.domain.request.IUpdateRateRequest;
import com.ysten.local.bss.device.api.domain.request.IUpdateServicesStopRequest;
import com.ysten.local.bss.device.api.domain.request.MultipleBindDeviceSno;
import com.ysten.local.bss.device.api.domain.response.IResponse;
import com.ysten.local.bss.device.domain.Customer;
import com.ysten.local.bss.device.domain.CustomerDeviceHistory;
import com.ysten.local.bss.device.domain.Device;
import com.ysten.local.bss.device.domain.Device.BindType;
import com.ysten.local.bss.device.domain.Device.SyncType;
import com.ysten.local.bss.device.domain.DeviceCustomerAccountMap;
import com.ysten.local.bss.device.domain.LockType;
import com.ysten.local.bss.device.repository.ICustomerRepository;
import com.ysten.local.bss.device.repository.IDeviceCustomerAccountMapRepository;
import com.ysten.local.bss.device.repository.IDeviceRepository;
import com.ysten.local.bss.device.service.IDeviceNewService;
import com.ysten.utils.date.DateUtil;

/**
 * 抽象设备服务层类
 * @author XuSemon
 * @since 2014-5-19
 */
public abstract class DeviceNewServiceImpl implements IDeviceNewService {
	@Autowired
	protected IDeviceRepository deviceRepository;
	@Autowired
	protected ICustomerRepository customerRepository;
	@Autowired
	private IDeviceCustomerAccountMapRepository deviceCustomerAccountMapRepository;
	
	protected static final int CONTINUE_FLAG_ZERO = 0;
	protected static final int CONTINUE_FLAG_ONE = 1;
	protected static final int CONTINUE_FLAG_TWO = 2;
	protected static final String END_SECOND = "235959";
	protected static final int OPERATION_TYPE_ORDER = 1;
	protected static final int OPERATION_TYPE_UNSUBSCRIBE = 2;
	protected static final String TYPE_ONLY = "ONLY";
	protected static final String TYPE_ALL = "ALL";
	protected static final String ACTION_RECOVER = "recover";
	protected static final String ACTION_PAUSE = "pause";
    

	
	/**************************************************************生成用户操作********************************************************/
    @Override
    public IResponse createUser(ICreateUserRequest request) throws Exception{
        //首先第一步，检查绑定用户参数
        //如果用户存在且已经绑定需要报警，则在check参数这块报警，这样下面换机操作就不会进行
        IResponse response = this.checkCreateUserParam(request);
        if(response != null){
            return response;
        }
        //绑定用户与设备
        //如果用户存在，且已经绑定，则做换机操作
        this.doCreateUser(request);
        //如果不需要做其他操作，则只需根据绑定结果直接返回
        //如果还需要做其他操作，比如添加产品包信息等，则可以等做了其他操作后，一起返回
        return this.doAfterCreateUser(request, true);
    }
    
    /**
     * 终端绑定参数检查
     */
    public abstract IResponse checkCreateUserParam(ICreateUserRequest request) throws Exception;
    /**
     * 终端绑定后续操作<br/>
     *     例如某些项目需要保存产品包订购信息等
     */
    public abstract IResponse doAfterCreateUser(ICreateUserRequest request, boolean isCreateSuccess) throws Exception;
    /**
     * 初始化用户信息
     */
    public abstract Customer initCustomerByRequest(ICreateUserRequest request) throws Exception;

    private void doCreateUser(ICreateUserRequest request) throws Exception{
        Customer customer = this.initCustomerByRequest(request);
        if(customer.getId() != null){
            this.customerRepository.updateCustomer(customer);
            
            List<DeviceCustomerAccountMap> maps = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByCustomerId(customer.getId());
            if(CollectionUtils.isNotEmpty(maps)){
                for(DeviceCustomerAccountMap map : maps){
                    if(!StringUtils.equalsIgnoreCase(customer.getUserId(), map.getUserId())){
                        map.setUserId(customer.getUserId());
                        map.setCustomerCode(customer.getCode());
                        this.deviceCustomerAccountMapRepository.updateDeviceCustomerAccountMap(map);
                    }

                    Device device = this.deviceRepository.getDeviceById(map.getDeviceId());
                    device.setExpireDate(customer.getServiceStop());
                    device.setState(Device.State.ACTIVATED);
                    device.setBindType(BindType.BIND);
                    device.setIsLock(LockType.UNLOCKED);
                    device.setIsSync(SyncType.WAITSYNC);
                    this.deviceRepository.updateDevice(device);
                }
            }
        } else {
            this.customerRepository.saveCustomer(customer);
        }
    }
	/**************************************************************开户操作********************************************************/
	@Override
	public IResponse bindDevice(IBindDeviceRequest request) throws Exception{
		//首先第一步，检查绑定用户参数
		//如果用户存在且已经绑定需要报警，则在check参数这块报警，这样下面换机操作就不会进行
		IResponse response = this.checkBindDeviceParam(request);
		if(response != null){
			return response;
		}
		//绑定用户与设备
		//如果用户存在，且已经绑定，则做换机操作
		boolean isBindSuccess = this.doBindDeviceToCustomer(request);
		//如果不需要做其他操作，则只需根据绑定结果直接返回
		//如果还需要做其他操作，比如添加产品包信息等，则可以等做了其他操作后，一起返回
		return this.doAfterBind(request, isBindSuccess);
	}
	/**
	 * 终端绑定参数检查
	 */
	public abstract IResponse checkBindDeviceParam(IBindDeviceRequest request) throws Exception;
	/**
	 * 终端绑定后续操作<br/>
	 *     例如某些项目需要保存产品包订购信息等
	 */
	public abstract IResponse doAfterBind(IBindDeviceRequest request, boolean isBindSuccess) throws Exception;
	/**
	 * 初始化用户信息
	 */
	public abstract Customer initCustomerByRequest(IBindDeviceRequest request) throws Exception;
	private boolean doBindDeviceToCustomer(IBindDeviceRequest request) throws Exception{
		String _device = request.getDevice();
		Device device = this.deviceRepository.getDeviceByCodeSnoAndMac(_device);
		Customer customer = this.initCustomerByRequest(request);
		//如果用户还未创建，则创建用户及绑定关系
		if(customer.getId() == null){
			return this.saveCustomerAndDeviceMap(customer, device);
		//如果用户已经创建，但未绑定设备，则创建绑定关系
		//如果用户已经创建，且已经绑定设备，则做换机操作
		}else {
			List<DeviceCustomerAccountMap> maps = this.customerRepository.getByCustomerId(customer.getId());
			if(maps == null || maps.isEmpty()){
				return this.saveCustomerAndDeviceMap(customer, device);
			}else {
				return this.changeCustomerDeviceMap(customer, device);
			}
		}
	}
	private boolean saveCustomerAndDeviceMap(Customer customer, Device device) throws Exception{
		//首先保存用户信息
		if(customer.getId() == null){
			this.customerRepository.saveCustomer(customer);
		}else {
			this.customerRepository.updateCustomer(customer);
		}

		//其次，保存设备信息
		device.setState(Device.State.ACTIVATED);
		device.setBindType(BindType.BIND);
		device.setIsLock(LockType.UNLOCKED);
		device.setActivateDate(new Date());
		device.setStateChangeDate(new Date());
		device.setExpireDate(customer.getServiceStop());
		this.deviceRepository.updateDevice(device);

		//最后，保存设备用户关系信息
		DeviceCustomerAccountMap map = new DeviceCustomerAccountMap();
		map.setCustomerId(customer.getId());
		map.setCustomerCode(customer.getCode());
		map.setDeviceId(device.getId());
		map.setDeviceCode(device.getCode());
		map.setDeviceSno(device.getSno());
		map.setUserId(customer.getUserId());//新增
		this.deviceCustomerAccountMapRepository.saveDeviceCustomerAccountMap(map);
		return true;
	}
	private boolean changeCustomerDeviceMap(Customer customer, Device device) throws Exception{
		//重新绑定，都是先删后增，保证redis数据正确性
		
		List<DeviceCustomerAccountMap>  list=deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceId(device.getId());
    	for(int i=0;i<list.size();i++){
    		DeviceCustomerAccountMap map=list.get(i);
    		deviceCustomerAccountMapRepository.delete(map);
    	}
		return this.saveCustomerAndDeviceMap(customer, device);
	}


	/**************************************************************销户操作********************************************************/
	@Override
	public IResponse disableDevice(IDisableDeviceRequest request) throws Exception {
		IResponse response = this.checkDisableDeviceParam(request);
		if(response != null){
			return response;
		}
		boolean isDisableSuccess = this.doDisableDeviceAndCustomer(request);
		return this.doAfterDisable(request, isDisableSuccess);
	}
	/**
	 * 销户操作参数检查
	 */
	public abstract IResponse checkDisableDeviceParam(IDisableDeviceRequest request) throws Exception;
	/**
	 * 销户后续操作
	 */
	public abstract IResponse doAfterDisable(IDisableDeviceRequest request, boolean isDisableSuccess) throws Exception;
	protected boolean doDisableDeviceAndCustomer(IDisableDeviceRequest request) {
		Customer customer = this.customerRepository.getCustomerByUserId(request.getUserId());
		
		//添加是否同步状态，以便铁通数据同步
		customer.setIsSync(SyncType.WAITSYNC);

        List<DeviceCustomerAccountMap> maps = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByCustomerId(customer.getId());
        boolean isAllDisable = true;
        if (CollectionUtils.isNotEmpty(maps)) {
            for (DeviceCustomerAccountMap map : maps) {
                // 如果type=only，那么只能更新指定的device，如果type=all，那么更新所有的device
            	//只要有一个sno没有销户，那么用户的状态就不能更新为销户
                if(StringUtils.equalsIgnoreCase(TYPE_ONLY, request.getType()) && !StringUtils.equalsIgnoreCase(map.getDeviceSno(), request.getSno())){
                	isAllDisable = false;
                    continue;
                }
                Device device = this.deviceRepository.getDeviceByCodeSnoAndMac(map.getDeviceSno());
                // 终端停用
                device.setState(Device.State.NONACTIVATED);
                device.setBindType(BindType.UNBIND);
                device.setIsLock(LockType.UNLOCKED);
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
	 		customer.setStateChangeDate(new Date());
	 		customer.setCancelledDate(new Date());
        }
        this.customerRepository.updateCustomer(customer);
        
		return true;
	}
	
	protected CustomerDeviceHistory createCustomerDeviceHistory(Device newDevice, Device oldDevice, Customer customer) {
		CustomerDeviceHistory history = new CustomerDeviceHistory();
		history.setCreateDate(new Date());
		if (newDevice != null) {
			history.setDeviceCode(newDevice.getCode());
			history.setDeviceId(newDevice.getId());
		}
//		if (customer != null) {
//			history.setCustomerCode(customer.getCode());
//			history.setCustomerId(customer.getId());
//		}
//		if (oldDevice != null) {
//			history.setOldDeviceCode(oldDevice.getCode());
//			history.setOldDeviceId(oldDevice.getId());
//		}
		return history;
	}


	/**************************************************************换机操作********************************************************/
	@Override
	public IResponse rebindDevice(IRebindDeviceRequest request) throws Exception {
		IResponse response = this.checkRebindDeviceParam(request);
		if(response != null){
			return response;
		}
		boolean isRebindSuccess = this.doRebindDeviceAndCustomer(request);
		return this.doAfterRebind(request, isRebindSuccess);
	}
	/**
	 * 换机操作参数检查
	 */
	public abstract IResponse checkRebindDeviceParam(IRebindDeviceRequest request) throws Exception;
	/**
	 * 换机后续操作
	 */
	public abstract IResponse doAfterRebind(IRebindDeviceRequest request, boolean isRebindSuccess) throws Exception;
	public boolean doRebindDeviceAndCustomer(IRebindDeviceRequest request) throws Exception{
		Device oldDevice = this.deviceRepository.getDeviceBySno(request.getOldSno());
		Device newDevice = this.deviceRepository.getDeviceBySno(request.getNewSno());
//		Customer customer = this.customerRepository.getCustomerByPhone(request.getPhone());
		
		List<DeviceCustomerAccountMap> mapList = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceId(oldDevice.getId());
		if (mapList != null && mapList.size() > 0) {
			
			Customer customer = this.customerRepository.getCustomerByDeviceId(oldDevice.getId());
			
			List<DeviceCustomerAccountMap>  list=deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByDeviceId(oldDevice.getId());
	    	for(int i=0;i<list.size();i++){
	    		DeviceCustomerAccountMap map=list.get(i);
	    		deviceCustomerAccountMapRepository.delete(map);
	    	}
			
			oldDevice.setStateChangeDate(new Date());
			oldDevice.setBindType(BindType.UNBIND);
			oldDevice.setState(Device.State.NONACTIVATED);
			oldDevice.setIsLock(LockType.UNLOCKED);
			this.deviceRepository.updateDevice(oldDevice);
			
			//添加是否同步状态，以便铁通数据同步
            customer.setIsSync(SyncType.WAITSYNC);
            this.saveCustomerAndDeviceMap(customer, newDevice);


			CustomerDeviceHistory history = this.createCustomerDeviceHistory(newDevice, oldDevice, customer);
			this.deviceRepository.saveCustomerDeviceHistory(history);
		}
		return true;
	}

	/**************************************************************终端到期时间修改操作********************************************************/
	@Override
	public IResponse updateServicesStop(IUpdateServicesStopRequest request) throws Exception {
		IResponse response = this.checkUpdateServicesStopParam(request);
		if(response != null){
			return response;
		}
		boolean isUpdateServicesStopSuccess = this.doUpdateCustomerServicesStop(request);
		return this.doAfterUpdateServicesStop(request, isUpdateServicesStopSuccess);
	}
	/**
	 * 终端到期时间修改参数检查
	 */
	public abstract IResponse checkUpdateServicesStopParam(IUpdateServicesStopRequest request) throws Exception;
	/**
	 * 终端到期时间修改后续操作
	 */
	public abstract IResponse doAfterUpdateServicesStop(IUpdateServicesStopRequest request, boolean isUpdateServicesStopSuccess) throws Exception;
	protected boolean doUpdateCustomerServicesStop(IUpdateServicesStopRequest request) throws Exception{
		String serviceStop = request.getServiceStop();

		Customer customer = this.customerRepository.getCustomerByUserId(request.getUserId());
		
		//添加是否同步状态，以便铁通数据同步
		customer.setIsSync(SyncType.WAITSYNC);
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
                if(StringUtils.equalsIgnoreCase(TYPE_ONLY, request.getType()) && !StringUtils.equalsIgnoreCase(map.getDeviceSno(), request.getSno())){
                    continue;
                }
                Device device = this.deviceRepository.getDeviceByCodeSnoAndMac(map.getDeviceCode());
                device.setExpireDate(customer.getServiceStop());
                this.deviceRepository.updateDevice(device);
            }
        }
        return true;
	}

	/**************************************************************集团业务办理接口********************************************************/
	@Override
	public IResponse bindMultipleDevice(IBindMultipleDeviceRequest request) throws Exception {
		IResponse response = this.checkBindMultipleDeviceParam(request);
		if(response != null){
			return response;
		}
		boolean isBindMultipleDeviceSuccess = this.doBindMultipleDevice(request);
		return this.doAfterBindMultipleDevice(request, isBindMultipleDeviceSuccess);
	}
	/**
	 * 集团业务办理参数检查
	 */
	public abstract IResponse checkBindMultipleDeviceParam(IBindMultipleDeviceRequest request) throws Exception;
	/**
	 * 集团业务办理后续操作
	 */
	public abstract IResponse doAfterBindMultipleDevice(IBindMultipleDeviceRequest request, boolean isBindMultipleDeviceSuccess) throws Exception;
	/**
	 * 初始化用户信息
	 */
	public abstract Customer initMultipleCustomerByRequest(IBindMultipleDeviceRequest request) throws Exception;
	private boolean doBindMultipleDevice(IBindMultipleDeviceRequest request) throws Exception{
		List<MultipleBindDeviceSno> snos = request.getSnos();
		Customer customer = this.initMultipleCustomerByRequest(request);
		if(customer.getId() == null){
			return this.saveCustomerAndDeviceMap(customer, snos);
		}else {
//			List<DeviceCustomerAccountMap> maps = this.customerRepository.getByCustomerId(customer.getId());
//			if(maps == null || maps.isEmpty()){
			return this.saveCustomerAndDeviceMap(customer, snos);
//			}else {
//				this.changeCustomerDeviceMap(customer, snos);
//			}
		}
//		return true;
	}
	private boolean saveCustomerAndDeviceMap(Customer customer, List<MultipleBindDeviceSno> snos) throws Exception{
		for(MultipleBindDeviceSno sno : snos){
			Device device = this.deviceRepository.getDeviceBySno(sno.getSno());
			this.saveCustomerAndDeviceMap(customer, device);
		}
		return true;
	}
//	private boolean changeCustomerDeviceMap(Customer customer, List<MultipleBindDeviceSno> snos)  throws Exception{
//		//重新绑定，都是先删后增，保证redis数据正确性
//		for(MultipleBindDeviceSno sno : snos){
//			Device device = this.deviceRepository.getDeviceBySno(sno.getSno());
//			this.changeCustomerDeviceMap(customer, device);
//		}
//		return true;
//	}

	
	/**************************************************************终端速率变更接口********************************************************/
	@Override
	public IResponse updateRate(IUpdateRateRequest request) throws Exception {
		IResponse response = this.checkUpdateRateParam(request);
		if(response != null){
			return response;
		}
		boolean isUpdateRateSuccess = this.doUpdateRate(request);
		return this.doAfterUpdateRate(request, isUpdateRateSuccess);
	}
	/**
	 * 终端速率变更修改参数检查
	 */
	public abstract IResponse checkUpdateRateParam(IUpdateRateRequest request) throws Exception;
	/**
	 * 终端速率变更修改后续操作
	 */
	public abstract IResponse doAfterUpdateRate(IUpdateRateRequest request, boolean isUpdateRateSuccess) throws Exception;
	protected boolean doUpdateRate(IUpdateRateRequest request) throws Exception{
		String userId = request.getUserId();
		Customer customer = this.customerRepository.getCustomerByUserId(userId);
		customer.setRate(request.getRate());
		this.customerRepository.updateCustomer(customer);
		return true;
	}
	
	/**************************************************************终端暂停启用接口********************************************************/
	@Override
	public IResponse pauseOrRecoverDevice(IPauseOrRecoverDeviceRequest request) throws Exception {
		IResponse response = this.checkPauseOrRecoverDeviceParam(request);
		if(response != null){
			return response;
		}
		boolean isPauseOrRecoverDeviceSuccess = this.doPauseOrRecoverDevice(request);
		return this.doAfterPauseOrRecoverDevice(request, isPauseOrRecoverDeviceSuccess);
	}
	/**
	 * 终端暂停启用修改参数检查
	 */
	public abstract IResponse checkPauseOrRecoverDeviceParam(IPauseOrRecoverDeviceRequest request) throws Exception;
	/**
	 * 终端暂停启用修改后续操作
	 */
	public abstract IResponse doAfterPauseOrRecoverDevice(IPauseOrRecoverDeviceRequest request, boolean isPauseOrRecoverDeviceSuccess) throws Exception;
	private boolean doPauseOrRecoverDevice(IPauseOrRecoverDeviceRequest request) throws Exception{
	    Customer customer = this.customerRepository.getCustomerByUserId(request.getUserId());
        
        //添加是否同步状态，以便铁通数据同步
        customer.setIsSync(SyncType.WAITSYNC);

        boolean isAllPause = true;
        List<DeviceCustomerAccountMap> maps = this.deviceCustomerAccountMapRepository.getDeviceCustomerAccountMapByCustomerId(customer.getId());
        if (CollectionUtils.isNotEmpty(maps)) {
            for (DeviceCustomerAccountMap map : maps) {
                // 如果type=only，那么只能更新指定的device，如果type=all，那么更新所有的device
                if(StringUtils.equalsIgnoreCase(TYPE_ONLY, request.getType()) && !StringUtils.equalsIgnoreCase(map.getDeviceSno(), request.getSno())){
                	isAllPause=false;
                    continue;
                }
                Device device = this.deviceRepository.getDeviceByCodeSnoAndMac(map.getDeviceCode());
                if(StringUtils.equalsIgnoreCase(request.getAction(), ACTION_PAUSE)){
                    device.setState(Device.State.NOTUSE);
                }else if (StringUtils.equalsIgnoreCase(request.getAction(), ACTION_RECOVER)) {
                    if(device.getBindType().equals(BindType.BIND)){
                        device.setState(Device.State.ACTIVATED);
                    }else if (device.getBindType().equals(BindType.UNBIND)) {
                        device.setState(Device.State.NONACTIVATED);
                    }
                }
                device.setStateChangeDate(new Date());
                this.deviceRepository.updateDevice(device);
            }
        }
        
        
        if(StringUtils.equalsIgnoreCase(request.getAction(), ACTION_PAUSE) && isAllPause
        		&& !customer.getState().equals(Customer.State.UNUSABLE)){//用户下的所有设备暂定，并且用户的状态 不是暂定状态时，更新用户状态
            customer.setState(Customer.State.UNUSABLE);
            customer.setStateChangeDate(new Date());
        }else if (StringUtils.equalsIgnoreCase(request.getAction(), ACTION_RECOVER)  
        		&& !customer.getState().equals(Customer.State.NORMAL)) {//有一个设备恢复，并且用户的状态 不是正常状态时，更新用户状态
            customer.setState(Customer.State.NORMAL);
            customer.setStateChangeDate(new Date());
        }
        
        this.customerRepository.updateCustomer(customer);
        
        return true;
	}
}
