package com.ysten.local.bss.device.repository;

import java.util.Map;

import com.ysten.local.bss.device.domain.CustomerGroup;
import com.ysten.utils.page.Pageable;

public interface ICustomerGroupRepository {

	CustomerGroup getCustomerGroupByGroupId(String groupId);
	
	boolean updateCustomerGroup(CustomerGroup customerGroup);
	
	boolean saveCustomerGroup(CustomerGroup customerGroup);

	/**
	 * 返回用户集团信息
	 * 
	 * @param map
	 * @return
	 */
	Pageable<CustomerGroup> findCustomerGroups(Map<String, String> map);

	/**
	 * 根据groupId删除CustomerGroup
	 * @author chenxiang
	 * @date 2014-8-14 下午4:34:44 
	 * @param groupId
	 */
	void deleteCustomerGroupByGroupId(String groupId);
}
