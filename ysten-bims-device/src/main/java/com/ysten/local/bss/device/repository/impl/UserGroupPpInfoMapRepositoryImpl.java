package com.ysten.local.bss.device.repository.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.ysten.local.bss.device.domain.UserGroupPpInfoMap;
import com.ysten.local.bss.device.repository.IUserGroupPpInfoMapRepository;
import com.ysten.local.bss.device.repository.mapper.UserGroupPpInfoMapMapper;

@Repository
public class UserGroupPpInfoMapRepositoryImpl implements IUserGroupPpInfoMapRepository {
	@Autowired
	private UserGroupPpInfoMapMapper userGroupPpInfoMapMapper;

	@Override
	public boolean deleteMapByUserGroupId(Long userGroupId) {
		return 1 == this.userGroupPpInfoMapMapper.deleteMapByUserGroupId(userGroupId);
	}

	@Override
	public boolean save(UserGroupPpInfoMap userGroupPpInfoMap) {
		return 1 == this.userGroupPpInfoMapMapper.save(userGroupPpInfoMap);
	}

	@Override
	public List<UserGroupPpInfoMap> findMapByUserGroupIdAndProductId(
			Long userGroupId, String productId) {
		return this.userGroupPpInfoMapMapper.findMapByUserGroupIdAndProductId(userGroupId, productId);
	}

	@Override
	public boolean update(UserGroupPpInfoMap userGroupPpInfoMap) {
		return 1 == this.userGroupPpInfoMapMapper.update(userGroupPpInfoMap);
	}

}
