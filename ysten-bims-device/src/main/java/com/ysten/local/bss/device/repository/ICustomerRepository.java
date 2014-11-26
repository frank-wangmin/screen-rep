package com.ysten.local.bss.device.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.device.domain.Customer;
import com.ysten.local.bss.device.domain.CustomerDeviceHistory;
import com.ysten.local.bss.device.domain.CustomerDeviceHistoryVo;
import com.ysten.local.bss.device.domain.CustomerRelationDeviceVo;
import com.ysten.local.bss.device.domain.DeviceCustomerAccountMap;
import com.ysten.local.bss.device.domain.UserActiveStatistics;
import com.ysten.utils.page.Pageable;

/**
 * ICustomerRepository interface
 * 
 * @fileName ICustomerRepository.java
 */
public interface ICustomerRepository {

    /**
     * get customer by code
     * 
     * @param customerCode
     * @return
     */
    Customer getCustomerByCode(String customerCode);

    /**
     * get customer by code
     * 
     * @param
     * @return
     */
    Customer getCustomerByUserId(String userId);

    List<Customer> getCustomerByUserIdAndOuterCode(String userId, String OuterCode);

    List<Customer> getCustomerByUserIdAndAreaId(String userId, Long areaId);

    void delCustomerByUserIdAndOuterCode(String userId, String OuterCode, Long id);

    /**
     * get customer by loginName
     * 
     * @param loginName
     * @return
     */
    Customer getCustomerByLoginName(String loginName);
    
    /**
     * 
     * @param custId
     * @param deviceCode
     * @return
     */
    Customer getCustomerByCustIdAndDeviceCode(String custId, String deviceCode);
    
    /**
     * 
     * @param custId
     * @return
     */
    Customer getLatestCreateCustomerByCustId(String custId);
    
    /**
     * 
     * @return
     */
    List<Customer> getNotProcessedCustomers();

    /**
     * update customer
     * 
     * @param customer
     * @return
     */
    boolean updateCustomer(Customer customer);
    
    boolean updateByUserId(Customer customer);
    
    boolean updateCustomerAsProcessed(List<String> userIds);
    
    boolean updateSyncById(Long id, String isSync);

    /**
     * save a new customer
     * 
     * @param customer
     * @return
     */
    boolean saveCustomer(Customer customer);

    // /**
    // * save product
    // * @param userPackageInfo
    // * @return
    // * @author HanksXu
    // */
    // boolean saveUserPackageInfo(UserPackageInfo userPackageInfo);

    /**
     * get customer by id
     * 
     * @param id
     * @return
     */
    Customer getCustomerById(Long id);
    
    Customer getMinCustomerByCustId(String custId);

    /**
     * 通过手机号码查询用户
     * 
     * @param phone
     * @return
     */
    Customer getCustomerByPhone(String phone);

    /**
     * 根据设备id获取用户id（DeviceCustomerAccountMap）
     * 
     * @param deviceId
     * @return
     */
    Customer getCustomerByDeviceId(Long deviceId);
    
    /**
     * 根据用户序列号更新用户信息（暂停SUSPEND/复机NORMAL）
     * @param userId
     * @param state（暂停SUSPEND/复机NORMAL）
     * @return
     */   
    boolean pauseAndRecoverDevice(String userId,String state);
    
    /**
     * 查询前一天新增的用户
     * @return
     */
    public long getAllCustomerCreatedLastDay(String cityId);
    
    /**
     * 查询对应城市所有的用户
     * @return
     */
    public long getAllCustomer(String cityId);

    Long getLastInsertId();
    
    /**
     * 
     * @param custId
     * @return
     */
    public List<Customer> getAllCustomerByCustId(String custId);
    
    /**
     * 查询 前一天退订的用户
     * @return
     */
    long getAllCustomerCancelLastDay(String cityId);
    
    Pageable<Customer> findCustomersByCondition(Map<String, Object> map);
    
    Pageable<Customer> findCustomersOfGroupByCondition(String tableName,String character,Customer customer, Integer pageNo, Integer pageSize);

	/**
	 * 返回终端客户历史记录
	 * 
	 * @param map
	 * @return
	 */
//	Pageable<CustomerDeviceHistoryVo> findCustomerDeviceHistorys(Map<String, String> map);
    
    List<Customer> getCustomersByCustId(String custId);
    
    List<Customer> getBySource(String source);
    
    Long getCountByRegionAndState(Date  start,Date  end,long city,int province,String source,String state);

    Long getCountByState(Date  start,Date  end,int province,String source,String state);
    void delete(Long customerId);
    
    Pageable<Customer> findCanBindCustomerList(Customer customer, int pageNo, int pageSize);
    
    Pageable<CustomerRelationDeviceVo> findCustomerRelationByCondition(Map<String, Object> map);
    
    Pageable<CustomerRelationDeviceVo> findCustomerRelationByDeviceSno(String snos,int pageNo, int pageSize);

    Long getCountByCreateDateAndUidDid(Date start, Date end,List<Long> customerIds, List<Long> deviceIds);
	Long getCountByUidDid(Date end,List<Long> customerIds,List<Long> deviceIds);
	Long getCountByCityAndDeviceType(Date start, Date end, int province,String source, String state, int deviceType, long cityId);
	Long getCountByRegion(Date start, Date end, long city, int province,String source);
	Long getCountByAera(Date  start,Date  end,int province,String source);
	List<DeviceCustomerAccountMap> getByCustomerId(Long customerId);
//	/**
//	 * 返回所有终端客户历史记录
//	 * @param map
//	 * @return
//	 */
//	List<CustomerDeviceHistoryVo> findCustomerDeviceHistoryByState(Map<String, Object> map);
	/**
	 * 返回所有客户信息
	 * @param customer
	 * @return
	 */
	List<Customer> findAllCustomersByCondition(Customer customer);
	/**
	 * 返回所有可以绑定客户列表
	 * @param customer
	 * @return
	 */
	List<Customer> findAllCanBindCustomerList(Customer customer);

	/**
	 * 返回所有终端用户关系
	 * @return
	 */
	List<CustomerRelationDeviceVo> findAllCustomerRelation(CustomerRelationDeviceVo vo);
	
    List<Customer> getCustomerByIds(List<Long> ids);
    
    List<Customer> findCustomersToExport(Map<String, Object> map);
    
    List<Customer> findExportCustomers(Map<String, Object> map);
    
    List<Customer> getAllCustomerByIsSync(Integer num);
    
    int getCustomerCountByIsSync();

	/**
	 * 获取昨天的开户销户数据
	 * @param yesterday 格式：yyyy-MM-dd
	 * @return
	 */
	List<Customer> getOpenAndCancelCustomer(String yesterday) throws Exception;

	/**
	 * 根据用户id获取历史记录
	 * @param
	 * @return
	 */
//	List<CustomerDeviceHistory> findCustomerDeviceHistorysByCustomerIdAndDate(Long customerId, Date beginDate, Date endDate);

    List<UserActiveStatistics> getAccountUserReport(String activeDate, Long province);
    
	List<UserActiveStatistics> getActiveUserReport(String activeDate, String statisticsType, Long province);
	
	List<UserActiveStatistics> getCancelUserReport(String cancelledDate, String statisticsType, Long province);

    /**
     * 浙江特殊处理，通过nickname获取Customer信息
     * nickName存放的是32位唯一码
     * @param userId
     * @return
     */
    Customer getcustomerByNickName(String userId);

    List<Customer> findCustomersByUserCodes(String[] codes);

    List<Long> findAreaByBusiness(Long Id, String character,String tableName);
    
    List<CustomerRelationDeviceVo> findRelationCustomerByDeviceIds(List<Long> deviceIds);
    
    List<CustomerRelationDeviceVo> exportRelationListByConditions(Map<String, Object> map);
    
    Pageable<CustomerDeviceHistory> findCustomerDeviceHistoryByConditions(Map<String, Object> map);
    
    List<CustomerDeviceHistory> findExportHistoryByConditions(Map<String, Object> map);
    
    List<CustomerDeviceHistory> findCustomerDeviceHistoryByIds(List<Long> ids);
}
