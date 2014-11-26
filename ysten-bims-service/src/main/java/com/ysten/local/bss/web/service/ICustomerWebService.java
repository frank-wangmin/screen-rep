package com.ysten.local.bss.web.service;

import com.ysten.local.bss.device.domain.*;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import com.ysten.utils.page.Pageable;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface ICustomerWebService {
	
	Pageable<Customer> findCustomersByCondition(Map<String, Object> map);
	
	Pageable<ProductPackageInfo> findPpList()throws Exception;
	
	Pageable<Customer> findCustomersOfGroupByCondition(String tableName,String character,Customer customer, Integer pageNo, Integer pageSize);
	
	void save(Customer customer);
	
	Customer getCustomerById(Long id);
	
	String update(Customer customer);
	
	Pageable<UserGroup> findUserGroupByNameAndType(String name,String type, Long areaId,Integer pageNo, Integer pageSize);
	
	UserGroup findUserGroupByNameAndType(String name,String type);

	/**
	 * 返回终端客户历史记录
	 * 
	 * @param map
	 * @return
	 */
//	Pageable<CustomerDeviceHistoryVo> findCustomerDeviceHistorys(Map<String, String> map);

	/**
	 * 返回用户集团信息
	 * 
	 * @param map
	 * @return
	 */
	Pageable<CustomerGroup> findCustomerGroups(Map<String, String> map);

	/**
	 * 返回客户信息
	 * 
	 * @param map
	 * @return
	 */
	Pageable<CustomerCust> findCustomerCusts(Map<String, String> map);
	
//	String remoteCustomerDevice(List<Long> customerDeviceMapIds);
	
	String bindDevice(String customerId, List<Long> deviceIds) throws ParseException;
	
	String addCustomer(Customer customer) throws Exception;
	
	String delete(List<Long> customerIds);
	
	Pageable<Customer> findCanBindCustomerList(Customer customer, int pageNo, int pageSize);
	
	Pageable<CustomerRelationDeviceVo> findCustomerRelationByCondition(Map<String, Object> map);
	
	Pageable<CustomerRelationDeviceVo> findCustomerRelationByDeviceSno(String snos, int pageNo, int pageSize);
	
	Pageable<Device> findCustomerCanBindDeviceList(Device device, int pageNo, int pageSize);
	
	String getCustomerPassword(String id) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException;

	/**
	 * 获取终端用户历史记录
	 * @param map
	 * @return
	 */
	Pageable<CustomerDeviceHistory> findCustomerDeviceHistoryByConditions(Map<String, Object> map);
	
	List<CustomerDeviceHistory> findExportHistoryByConditions(Map<String, Object> map);
	
	List<CustomerDeviceHistory> findCustomerDeviceHistoryByIds(List<Long> ids);
	/**
	 * 返回所有用户信息
	 * 
	 * @param customer
	 * @return
	 */
	List<Customer> findAllCustomersByCondition(Customer customer);

	/**
	 * 获取所有可绑定客户列表
	 * @param customer
	 * @return
	 */
	List<Customer> findAllCanBindCustomerList(Customer customer);

	/**
	 * 返回所有终端用户关系
	 * @return
	 */
	List<CustomerRelationDeviceVo> findAllCustomerRelation(CustomerRelationDeviceVo vo);

	/**
	 * 导入所有用户信息
	 * @param targetFile
	 * @return
	 */
	Map<String, Object> importCustomerFile(File targetFile) throws Exception;
	
    List<Customer> getCustomerByIds(List<Long> ids);
    
    List<Customer> findCustomersToExport(Map<String, Object> map);
    
    List<Customer> findExportCustomers(Map<String, Object> map);
    
    Customer getByUserId(String userId);
    
    String saveUserGroup(UserGroup userGroup);
    
    String checkPpVail(UserGroup userGroup);
    
    UserGroup getUserGroupById(Long id);
    
    String updateUserGroupById(UserGroup userGroup);
    
    boolean deleteUserGroupByIds(List<Long> ids);
    
    boolean deleteUserGroupBusiness(List<Long> idsList);
    
    boolean deleteUserBusiness(List<Long> idsList);
    
    boolean deleteUserGroupMap(List<Long> ids);
    
    boolean deleteUserGroupMapByUserId(List<Long> ids);
    
    String deleteUserGroupByCondition(List<Long> idsList);
    
    String bindUserGroupMap(String id, String userIds);
    
    String addUserGroupMap(String id, String userIds);
    
    List<UserGroup> findUserGroupListByType(EnumConstantsInterface.UserGroupType type,String dynamicFlag);
    
    List<UserGroup> getUserGroupList();
    
    int saveUserGroupMap(UserGroupMap userGroupMap);
    
    String bindUserGroup(List<Long> idsList,String userGroupId);
    
    boolean syncCustomer() throws Exception; 
    
    List<Customer> findAllCustomerByIsSync(Integer num);
    
    int getCustomerCountByIsSync();

    /**
     *  check the input SQL
     * @param sql
     * @return
     */
   String checkInputSql(String sql);

    /**
     * find all the relationship with userGroup and userId by userGroupId
     * @param userGroupId
     * @return list
     */
    public List<UserGroupMap> findAllUserGroupMapsByUserGroupId(String userGroupId);

    /**
     * Get all the userGroups by userId and type
     * @param userId
     * @return list
     */
    public Map<String, Object> findUserGroupsByUserCode(String userId);

    /**
     * find device group by districtCode,type and bootId
     *
     * @param id
     * @param districtCode
     * @param tableName
     * @param character
     * @param type
     * @return
     */
    List<UserGroup> findUserGroupByArea(EnumConstantsInterface.UserGroupType type, String tableName,String character,String districtCode, String id);

    List<Long> findUserGroupById(String id,String character,String tableName);

    List<Long> findAreaByBusiness(String id, String character,String tableName);
    
    List<CustomerRelationDeviceVo> findRelationCustomerByDeviceIds(List<Long> deviceIds);
    
    List<CustomerRelationDeviceVo> exportRelationListByConditions(Map<String, Object> map);

    boolean deleteUserByGroupId(Long userGroupId,List<String> idsList);

    Map<String, Object> findCustomerRelateBusinessByCodeOrGroupId(String code, Long groupId);

    String saveCustomerBusinessBind(String ids, String animationId, String panelId, String noticeIds, String backgroundIds);

    String saveGroupBusinessBind(String ids, String animationId, String panelId, String noticeIds, String backgroundIds);

}
