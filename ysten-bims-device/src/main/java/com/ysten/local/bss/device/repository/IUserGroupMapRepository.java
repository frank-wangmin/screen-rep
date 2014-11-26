package com.ysten.local.bss.device.repository;

import java.util.List;

import com.ysten.local.bss.device.domain.UserGroupMap;

/**
 */
public interface IUserGroupMapRepository {

	List<UserGroupMap> getMapByGroupId(Long groupId);

	int saveUserGroupMap(UserGroupMap userGroupMap);

	void deleteMapByGroupId(Long groupId);

	void deleteMapByUserCode(String code);

	UserGroupMap getByUserCodeAndGroupId(Long groupId, String code);

	List<UserGroupMap> findByUserCode(String code);

	void deleteMapByCodeAndGroup(Long groupId, String userId);
	
	void deleteMapByUserCodesAndGroups(String[] codes, List<Long> groupIds);

	List<UserGroupMap> findAllUserGroupMapsByUserGroupId(Long userGroupId);

    void bulkSaveUserGroupMap(List<UserGroupMap> maps);

    int deleteMapByUserCodeAndGroupId(String code, Long groupId);
}
