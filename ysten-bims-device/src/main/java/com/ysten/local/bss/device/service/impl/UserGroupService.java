package com.ysten.local.bss.device.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysten.local.bss.device.domain.Customer;
import com.ysten.local.bss.device.domain.UserGroup;
import com.ysten.local.bss.device.repository.IUserGroupRepository;
import com.ysten.local.bss.device.service.IUserGroupService;

/**
 * @author cwang
 * @version 2014-6-23 上午11:58:24
 * 
 */
@Service
public class UserGroupService implements IUserGroupService {
	@Autowired
	private IUserGroupRepository userGroupRepository;

	@Override
	public List<UserGroup> findDynamicGroupList(String code, String type, String tableName) {

		// 1.查询动态分组.
		List<UserGroup> list = userGroupRepository.findDynamicGroupList(tableName, type);
		List<UserGroup> listResult = new ArrayList<UserGroup>();

		// 2.判断用户是否在动态分组中.
		for (int i = 0; i < list.size(); i++) {
			UserGroup userGroup = list.get(i);
			String sql = userGroup.getSqlExpression();
			sql.replaceAll(";", "");
			sql = sql + " and code='" + code+"'";
			List<Customer> customerList = userGroupRepository.checkInputSql(sql);
			if(!CollectionUtils.isEmpty(customerList)){
				listResult.add(userGroup);
			}
		}
		return listResult;
	}

}
