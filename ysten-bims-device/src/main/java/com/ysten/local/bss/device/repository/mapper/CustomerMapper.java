package com.ysten.local.bss.device.repository.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.device.domain.Customer;
import com.ysten.local.bss.device.domain.CustomerRelationDeviceVo;
import com.ysten.local.bss.device.domain.UserActiveStatistics;

/**
 * 
 * 类名称：CustomerMapper
 * 
 * @version
 */
public interface CustomerMapper {

    /**
     * 按主键获取用户信息
     * 
     * @param id
     *            主键ID
     * @return 用户信息
     */
    Customer getById(Long id);

    /**
     * 根据用户编码查询该用户
     * 
     * @param customerCode
     *            用户Code
     * @return 用户信息
     */
    Customer getByCode(String customerCode);

    /**
     * 根据用户ID查询该用户
     * 
     * @param userId
     *            用户Id
     * @return 用户信息
     */
    Customer getByUserId(String userId);

    /**
     * 保存用户信息
     * 
     * @param customer
     *            待保存的用户信息
     * @return 影响的行数
     */
    int save(Customer customer);

    /**
     * 更新用户信息
     * 
     * @param customer
     *            用户信息
     * @return 影响的行数
     */
    int update(Customer customer);
    
    int updateByUserId(Customer customer);
    
    /**
     * 
     * @param userIds
     * @return
     */
    int updateCustomerAsProcessed(List<String> userIds);

    int updateSyncById(@Param("id")Long id,@Param("isSync")String isSync);
    /**
     * 根据用户登录名查询该用户
     * 
     * @param loginName
     *            登录名
     * @return
     */
    Customer getByLoginName(@Param("loginName") String loginName);

    /**
     * 根据手机号码查询用户
     * 
     * @param phone
     * @return
     */
    Customer getByPhone(@Param("phone") String phone);
    
    /**
     * 根据custId取当前最小的未绑定任何设备的账户
     * @param custId
     * @return
     * @author HanksXu
     * 2014-2-10
     */
    Customer getMinCustomerByCustId(@Param("custId") String custId);
    
    /**
     * 
     * @return
     */
	public Customer getCustomerByCustIdAndDeviceCode(Map<String, String> params);

    public List<Customer> getCustomerByUserIdAndOuterCode(@Param("userId")String userId, @Param("outerCode")String outerCode);

    public List<Customer> getCustomerByUserIdAndAreaId(@Param("userId")String userId,@Param("areaId")Long areaId );

    public void delCustomerByUserIdAndOuterCode(@Param("userId")String userId, @Param("outerCode")String outerCode, @Param("id")Long id);

	/**
	 * 
	 * @param custId
	 * @return
	 */
	public Customer getLatestCreateCustomerByCustId(String custId);
    
    /**
     * 
     * @return
     */
    List<Customer> getNotProcessedCustomers();
        
    /**
     * 根据用户序列号更新用户信息（暂停SUSPEND/复机NORMAL）
     * @param userId
     * @param state（暂停SUSPEND/复机NORMAL）
     * @return
     */   
    int pauseAndRecoverDevice(@Param("userId")String userId,@Param("state")String state);
    
    /**
     * 查询前一天新增的用户
     * @return
     */
    long getAllCustomerCreatedLastDay(Map<String, String> map);
    
    /**
     * 查询对就城市所有的用户
     * @return
     */
    long getAllCustomer(Map<String, String> map);

    long getLastInsertId();
    
    /**
     * 根据客户编号查询所有子帐户信息
     * @param custId
     * @return
     */
    List<Customer> getAllCustomerByCustId(String custId);
    
    /**
     * 查询 前一天退订的用户
     * @return
     */
    long getAllCustomerCancelLastDay(Map<String, String> map);

    List<Customer> findCustomersByCondition(Map<String, Object> map);
    
    List<Customer> findCustomersOfGroupByCondition(Map<String, Object> map);

    List<Customer> findCustomersByBackImageId(Map<String, Object> map);
    
    int getCountByCondition(Map<String, Object> map);
    
    int getUserCountOfGroupByCondition(Map<String, Object> map);
    
    List<Customer> getCustomersByCustId(@Param("custId") String custId);
    
    List<Customer> getBySource(@Param("source") String source);
    
    Long getCountByState(@Param("start")Date  start,@Param("end")Date  end,@Param("province") int province,@Param("source") String source,@Param("state") String state);

    Long getCountByRegionAndState(@Param("start")Date  start,@Param("end")Date  end,@Param("city") long city,@Param("province") int province,@Param("source") String source,@Param("state") String state);
    
    void delete(@Param("customerId")Long customerId);
    
    List<Customer> findCanBindCustomersByCondition(Map<String, Object> map);
    
    List<Customer> findCustomersToExport(Map<String, Object> map);
    
    List<Customer> findExportCustomers(Map<String, Object> map);
    
    int getCountByCanBindCustomerCondition(Map<String, Object> customer);
    
    List<Customer> getCustomerByIds(@Param("ids")List<Long> ids);
    
    List<Customer> getAllCustomerByIsSync(@Param("num")Integer num);
    
    int getCountByIsSync();

	/**
	 * 铁通获取昨天的开户销户数据
	 * @param yesterday
	 * @return
	 */
	List<Customer> getOpenAndCancelCustomer(@Param("createStartDate")Date createStartDate, @Param("createEndDate")Date createEndDate,
			@Param("cancelStartDate")Date cancelStartDate, @Param("cancelEndDate")Date cancelEndDate);
	
	List<UserActiveStatistics> getAccountUserReport(@Param("activeDate")String activeDate, @Param("province")Long province);
	
	List<UserActiveStatistics> getActiveUserReport(@Param("activeDate")String activeDate,@Param("statisticsType") String statisticsType, @Param("province")Long province);
	
	List<UserActiveStatistics> getCancelUserReport(@Param("cancelledDate")String cancelledDate, @Param("statisticsType")String statisticsType, @Param("province")Long province);
	
	List<Customer> checkInputSql(@Param("sql")String sql);

    Customer getcustomerByNickName(@Param("nickName")String userId);

    List<Customer> findCustomersByUserCodes(@Param("codes")String[] codes);

    List<Long> findAreaByBusiness(@Param("id")Long id, @Param("character")String character,@Param("tableName")String tableName);
}