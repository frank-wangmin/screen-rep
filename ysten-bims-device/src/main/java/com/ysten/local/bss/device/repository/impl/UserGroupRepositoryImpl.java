package com.ysten.local.bss.device.repository.impl;

import java.util.List;

import com.ysten.cache.annotation.MethodFlushAnimation;
import com.ysten.cache.annotation.MethodFlushBackgroundImage;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysten.local.bss.device.domain.Customer;
import com.ysten.local.bss.device.domain.UserGroup;
import com.ysten.local.bss.device.domain.UserGroupMap;
import com.ysten.local.bss.device.domain.UserGroupPpInfoMap;
import com.ysten.local.bss.device.repository.IUserGroupPpInfoMapRepository;
import com.ysten.local.bss.device.repository.IUserGroupRepository;
import com.ysten.local.bss.device.repository.mapper.CustomerMapper;
import com.ysten.local.bss.device.repository.mapper.UserGroupMapper;
import com.ysten.local.bss.device.utils.EnumConstantsInterface.UserGroupType;
import com.ysten.utils.page.Pageable;

@Repository
public class UserGroupRepositoryImpl implements IUserGroupRepository {
//	private static final String BASE_DOMAIN = "ysten:local:bss:device:";
//	private static final String USER_ID = BASE_DOMAIN + "UserGroup:userId:";
	@Autowired
	private UserGroupMapper userGroupMapper;
	@Autowired
	private IUserGroupPpInfoMapRepository userGroupPpInfoMapRepository;
	@Autowired
	CustomerMapper customerMapper;

	@Override
	public Pageable<UserGroup> findUserGroupByNameAndType(String name, String type, Long areaId,Integer pageNo, Integer pageSize) {
		List<UserGroup> page = this.userGroupMapper.findUserGroupByNameAndType(type, name,areaId, pageNo, pageSize);
		if(page!=null && page.size()>0){
			for(UserGroup ug : page){
				List<UserGroupPpInfoMap> userGroupPpInfoMap = this.userGroupPpInfoMapRepository.findMapByUserGroupIdAndProductId(ug.getId(), null);
				StringBuffer upsb = new StringBuffer();
				for(UserGroupPpInfoMap upm : userGroupPpInfoMap){
					upsb.append(upm.getProductId()).append(",");
				}
				if(upsb.length()>0){
					ug.setProductId(upsb.substring(0,upsb.length()-1).toString());
				}
			}
		}
		int total = this.userGroupMapper.findUserGroupCountByNameAndType(type, name,areaId);
		return new Pageable<UserGroup>().instanceByPageNo(page, total, pageNo, pageSize);
	}

	@Override
	public boolean save(UserGroup userGroup) {
		return 1 == this.userGroupMapper.insert(userGroup);
	}

	@Override
	public UserGroup getById(Long id) {
		UserGroup userGroup = this.userGroupMapper.getById(id);
		if(userGroup!=null){
			List<UserGroupPpInfoMap> userGroupPpInfoMap = this.userGroupPpInfoMapRepository.findMapByUserGroupIdAndProductId(userGroup.getId(), null);
			StringBuffer upsb = new StringBuffer();
			for(UserGroupPpInfoMap upm : userGroupPpInfoMap){
				upsb.append(upm.getProductId()).append(",");
			}
			if(upsb.length()>0){
				userGroup.setProductId(upsb.substring(0,upsb.length()-1).toString());
			}
		}
		return userGroup;
	}

	@Override
	public boolean updateByPrimaryKey(UserGroup userGroup) {
		return 1 == this.userGroupMapper.updateByPrimaryKey(userGroup);
	}

	@Override
    @MethodFlushAnimation
    @MethodFlushBackgroundImage
	public boolean delete(List<Long> ids) {
		return ids.size() == this.userGroupMapper.delete(ids);
	}

	@Override
	public List<UserGroup> findUserGroupListByType(UserGroupType type,String dynamicFlag) {
		return this.userGroupMapper.findUserGroupListByType(type,dynamicFlag);
	}

	@Override
	public List<UserGroup> getList() {
		return this.userGroupMapper.getList();
	}

    @Override
    public List<UserGroup> findUserGroupsByUserCode(String code,Long areaId) {
        return this.userGroupMapper.findUserGroupsByUserCode(code,areaId);
    }

    @Override
    public List<UserGroup> findUserGroupByArea(UserGroupType type, String tableName, String character, List<String> districtCode, Long id) {
        return this.userGroupMapper.findUserGroupByArea(type, tableName, character,districtCode, id);
    }


    @Override
    public List<UserGroup> findUserGroupsByUserCodesAndType(String[] codes, String type) {
        return this.userGroupMapper.findUserGroupsByUserCodesAndType(codes, type);
    }

    @Override
    public List<UserGroup> findUserGroupsByProductIdAndArea(String productId, Long areaId) {
        return this.userGroupMapper.findUserGroupsByProductIdAndAreaId(productId, areaId);
    }

    @Override
    public List<Long> findUserGroupById(Long Id, String character, String tableName) {
        return userGroupMapper.findUserGroupById(Id,character,tableName);
    }


    @Override
	public List<Customer> checkInputSql(String sql) {
		return this.customerMapper.checkInputSql(sql);
	}

	@Override
    @MethodFlushAnimation
    @MethodFlushBackgroundImage
	public boolean deleteByPrimaryKey(Long id) {
		return 1 == this.userGroupMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<UserGroup> findUserGroupListByIdsAndType(List<UserGroupMap> userGroupMap, UserGroupType type) {
		return this.userGroupMapper.findUserGroupListByIdsAndType(userGroupMap, type);
	}

	@Override
	public List<UserGroup> findDynamicGroupList(String tableName, String type) {
		return userGroupMapper.findDynamicGroupList(tableName, type);
	}

	@Override
	public UserGroup findUserGroupByNameAndType(String name, String type) {
		return this.userGroupMapper.findUserGroupsByNameAndType(type, name);
	}

}
