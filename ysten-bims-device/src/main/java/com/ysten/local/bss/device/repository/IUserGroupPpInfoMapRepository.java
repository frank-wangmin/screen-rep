package com.ysten.local.bss.device.repository;

import java.util.List;
import com.ysten.local.bss.device.domain.UserGroupPpInfoMap;

/**
 */
public interface IUserGroupPpInfoMapRepository {
	/**
	 * 根据分组Id删除关系信息
	 * @param userGroupId
	 */
	boolean deleteMapByUserGroupId(Long userGroupId);

	boolean save(UserGroupPpInfoMap userGroupPpInfoMap);
	
	List<UserGroupPpInfoMap> findMapByUserGroupIdAndProductId(Long userGroupId,String productId);
	
	boolean update(UserGroupPpInfoMap userGroupPpInfoMap);
	
}
