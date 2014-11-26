package com.ysten.local.bss.device.repository;

import java.util.List;

import com.ysten.local.bss.device.domain.Customer;
import com.ysten.local.bss.device.domain.DeviceGroup;
import com.ysten.local.bss.device.domain.UserGroup;
import com.ysten.local.bss.device.domain.UserGroupMap;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import com.ysten.utils.page.Pageable;
import org.apache.ibatis.annotations.Param;

/**
 */
public interface IUserGroupRepository {

	Pageable<UserGroup> findUserGroupByNameAndType(String name, String type, Long areaId,Integer pageNo, Integer pageSize);
	
	UserGroup findUserGroupByNameAndType(String name,String type);

	boolean save(UserGroup userGroup);

	UserGroup getById(Long id);

	boolean updateByPrimaryKey(UserGroup userGroup);

	boolean delete(List<Long> ids);

	boolean deleteByPrimaryKey(Long id);

	List<UserGroup> findUserGroupListByType(EnumConstantsInterface.UserGroupType type,String dynamicFlag);

	List<UserGroup> findUserGroupListByIdsAndType(List<UserGroupMap> userGroupMap, EnumConstantsInterface.UserGroupType type);

	List<UserGroup> getList();

	List<Customer> checkInputSql(String sql);

	/**
	 * 查询已绑定业务的动态分组
	 * 
	 * @param tableName
	 *            业务与终端分组的映射表
	 * @param type
	 *            分组的类型
	 * @return
	 */
	List<UserGroup> findDynamicGroupList(String tableName, String type);


    /**
     * Get all the userGroups by userId and type
     * @param userId
     * @return list
     */
    public List<UserGroup> findUserGroupsByUserCode(String code,Long areaId);

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
    List<UserGroup> findUserGroupByArea(EnumConstantsInterface.UserGroupType type, String tableName,String character,List<String> districtCode, Long id);

    List<Long> findUserGroupById(Long Id, String character,String tableName);

    List<UserGroup> findUserGroupsByUserCodesAndType(String[] codes,String type);

    List<UserGroup> findUserGroupsByProductIdAndArea(String productId, Long areaId);
}
