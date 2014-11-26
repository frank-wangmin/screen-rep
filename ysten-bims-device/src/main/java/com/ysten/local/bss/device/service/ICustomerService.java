package com.ysten.local.bss.device.service;

import java.text.ParseException;
import java.util.List;

import net.sf.json.JSONObject;

import com.ysten.local.bss.device.bean.ProductParams;
import com.ysten.local.bss.device.domain.Customer;
import com.ysten.local.bss.device.domain.CustomerCust;
import com.ysten.local.bss.device.domain.Device;
import com.ysten.local.bss.device.domain.DeviceCustomerAccountMap;
import com.ysten.local.bss.device.domain.UserGroup;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;

/**
 * 
 */
public interface ICustomerService {
    
    List<UserGroup> findUserGroupListByUserIdAndType(String userId,EnumConstantsInterface.UserGroupType type);
	/**
	 * get customer by code
	 * 
	 * @param customerCode
	 * @return
	 */
	Customer getCustomerByCode(String customerCode);

	Customer getCustomerById(long id);

    /**
     * 简单同步用户
     * @param customer
     * @return
     */
    public boolean simpleSyncCustomer(Customer customer);

    public boolean batchSimpleSyncCustomer(List<Customer> customers);

	/**
	 * 同步用户数据
	 * 
	 * @param systemCode
	 * @param customer
	 * @return
	 */
	boolean syncCustomer(JSONObject json) throws ParseException;

	/**
	 * 验证字段必填是否填了
	 * 
	 * @param json
	 * @return
	 */
	boolean checkRequired(JSONObject json);

	/**
	 * save new customer
	 * 
	 * @param customer
	 * @return
	 * @author HanksXu
	 */
	boolean saveCustomer(Customer customer);

	/**
	 * 
	 * @param customer
	 * @return
	 * @author HanksXu
	 * @date 2013-10-21
	 */
	boolean createCustomer(Customer customer);

	// /**
	// *
	// * @param custoemr
	// * @param prodcutList
	// * @return
	// * @author HanksXu
	// */
	// boolean registerCustomer(Customer customer, List<UserPackageInfo>
	// upiList);
	//
	// /**
	// *
	// * @param custoemr
	// * @param upiList
	// * @return
	// * @author HanksXu
	// */
	// boolean updateCustomer(Customer customer, List<UserPackageInfo> upiList);

	// /**
	// * save product
	// *
	// * @param userPackageInfo
	// * @return
	// * @author HanksXu
	// */
	// boolean saveUserPackageInfo(UserPackageInfo userPackageInfo);
	// /**
	// *
	// * @param userPackageInfoList
	// * @return
	// * @author HanksXu
	// */
	// boolean saveUserPackageInfo(List<UserPackageInfo> userPackageInfoList);

	/**
	 * 更新 customer
	 * 
	 * @param customer
	 * @return
	 * @author
	 */
	boolean updateCustomer(Customer customer);

	boolean updateCustomerAsProcessed(List<String> userIds);

	/**
	 * JsDeviceController: when changing cntvId, set the replacement to YES.
	 * 
	 * @param customer
	 * @return
	 * @author HanksXu
	 * @date 2013-10-16
	 */
	boolean updateCustomerCode(Customer customer, DeviceCustomerAccountMap map);

	/**
	 * 更新 customer
	 * 
	 * @param userId
	 * @return
	 * @author
	 */
	Customer getCustomerByUserId(String userId);

	/**
	 * 
	 * @param custId
	 * @return
	 * @author HanksXu 2014-2-10
	 */
	Customer getMinEnableCustomerByCustId(String custId);

	/**
	 * 
	 * @param phone
	 * @return
	 * @author HanksXu
	 * @date 2013-10-14
	 */
	Customer getCustomerByPhone(String phone);

	/**
	 * 
	 * @return
	 */
	List<Customer> getNotProcessedCustomers();

	/**
	 * 
	 * @param c
	 * @param maps
	 * @return
	 * @author HanksXu
	 * @date 2013-10-14
	 */
	boolean cancelCustomer(Customer c, List<DeviceCustomerAccountMap> maps);

	/**
	 * 更换设备处理
	 * 
	 * @param customer
	 * @param device
	 * @param map
	 * @return
	 */
	boolean changeDeviceBind(Customer customer, Device device, DeviceCustomerAccountMap map);
	
	/**
	 * 
	 * @param customer
	 * @param device
	 * @return
	 */
	boolean changeDeviceBind(Customer customer, Device device);

	/**
	 * 更换设备处理
	 * 
	 * @param customer
	 * @param device
	 * @return
	 */
	boolean deviceBindCustomer(Customer customer, Device device);

	/**
	 * 
	 * @param customer
	 * @param device
	 * @return
	 * @author HanksXu
	 * @date 2013-10-16
	 */
	boolean deviceUnbindCustomer(Customer customer, Device device);

	/**
	 * 开户
	 * 
	 * @param productParams
	 * @param effectiveDate
	 * @param expireDate
	 * @return
	 */
	public String[] bindDevice(ProductParams productParams, String effectiveDate, String expireDate) throws ParseException;

	/**
	 * 销户
	 * 
	 * @param sheetNO
	 *            业务帐号
	 * @return
	 */
	public String[] revokeDevice(String sheetNO);

	/**
	 * 暂停
	 * 
	 * @param sheetNO
	 *            业务帐号
	 * @return
	 */
	public String[] pauseDevice(String sheetNO);

	/**
	 * 复机
	 * 
	 * @param sheetNO
	 *            业务帐号
	 * @return
	 */
	public String[] recoverDevice(String sheetNO);

	/**
	 * 换机
	 * 
	 * @param sheetNO
	 *            业务帐号
	 * @param newsno
	 * @return
	 */
	public String[] rebindDevice(String sheetNO, String newsno);

	/**
	 * 查询对应城市前一天新增的用户
	 * 
	 * @return
	 */
	public long getAllCustomerCreatedLastDay(String cityId);

	/**
	 * 查询对应城市所有的用户
	 * 
	 * @return
	 */
	public long getAllCustomer(String cityId);

	/**
	 * 查询对应城市前一天退订的用户
	 * 
	 * @return
	 */
	public long getAllCustomerCancelLastDay(String cityId);
	
	/**
	 * 用户到期时间修改
	 * @param userId
	 * @param servicestop
	 * @return
	 * @throws ParseException
	 */
	String[] updateServiceStop(String userId, String servicestop) throws ParseException;
    /**
     * 浙江特殊处理，通过nickname获取Customer信息
     * nickName存放的是32位唯一码
     * @param userId
     * @return
     */
    Customer getcustomerByNickName(String userId);
    
    CustomerCust getCustomerCustByCustId(String custId);
    
    boolean saveCustomerCust(CustomerCust customerCust);
    
    boolean updateCustomerCust(CustomerCust customerCust);
    
    public Customer getCustomerByYstenId(String ystenId);

    public Customer getCustomerBySno(String sno);

    void bindUserGroupByProductId(String userId, String productId, String areaName, String orderType) throws Exception;

//    boolean unBindUserGroupByProductId(String userId, String productId, String areaName, String orderType) throws Exception;
}