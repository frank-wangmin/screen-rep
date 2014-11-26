package com.ysten.local.bss.device.repository.impl;

import java.util.List;

import com.ysten.cache.annotation.MethodFlushAnimation;
import com.ysten.cache.annotation.MethodFlushBackgroundImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysten.cache.annotation.KeyParam;
import com.ysten.local.bss.device.domain.UserGroupMap;
import com.ysten.local.bss.device.repository.IUserGroupMapRepository;
import com.ysten.local.bss.device.repository.mapper.UserGroupMapMapper;

/**
 * @author cwang
 * @version 2014-6-26 下午4:08:18
 */
@Repository
public class UserGroupMapRepository implements IUserGroupMapRepository {
    @Autowired
    private UserGroupMapMapper userGroupMapMapper;

    @Override
    public List<UserGroupMap> getMapByGroupId(Long groupId) {
        return this.userGroupMapMapper.getMapByGroupId(groupId);
    }

    @Override
    @MethodFlushAnimation
    @MethodFlushBackgroundImage
    public int saveUserGroupMap(UserGroupMap userGroupMap) {
        return this.userGroupMapMapper.save(userGroupMap);
    }

    @Override
    @MethodFlushAnimation
    @MethodFlushBackgroundImage
    public void deleteMapByGroupId(Long groupId) {
        this.userGroupMapMapper.deleteMapByGroupId(groupId);
    }

    @Override
    @MethodFlushAnimation
    @MethodFlushBackgroundImage
    public void deleteMapByUserCode(String code) {
        this.userGroupMapMapper.deleteMapByUserCode(code);
    }

    @Override
    public UserGroupMap getByUserCodeAndGroupId(Long groupId, String code) {
        return this.userGroupMapMapper.getByUserCodeAndGroupId(groupId, code);
    }

    @Override
    public List<UserGroupMap> findByUserCode(String code) {
        return this.userGroupMapMapper.findByUserCode(code);
    }

    @Override
    @MethodFlushAnimation
    @MethodFlushBackgroundImage
    public void deleteMapByCodeAndGroup(Long groupId, String userId) {
        this.userGroupMapMapper.deleteMapByCodeAndGroup(groupId, userId);
    }

    @Override
    public List<UserGroupMap> findAllUserGroupMapsByUserGroupId(Long userGroupId) {
        return this.userGroupMapMapper.getMapByGroupId(userGroupId);
    }

    @Override
    public void bulkSaveUserGroupMap(List<UserGroupMap> list) {
         this.userGroupMapMapper.bulkSaveUserGroupMap(list);
    }

    @Override
    public int deleteMapByUserCodeAndGroupId(String code, Long groupId) {
        return this.userGroupMapMapper.deleteMapByUserCodeAndGroupId(code, groupId);
    }

    @Override
	@MethodFlushAnimation
    @MethodFlushBackgroundImage
	public void deleteMapByUserCodesAndGroups(String[] codes,
			List<Long> groupIds) {
		this.userGroupMapMapper.deleteMapByUserCodesAndGroups(codes, groupIds);
	}
}
