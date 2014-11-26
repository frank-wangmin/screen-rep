package com.ysten.local.bss.device.repository.mapper;

import java.util.List;
import java.util.Map;

import com.ysten.local.bss.device.domain.CustomerGroup;

public interface CustomerGroupMapper {

	CustomerGroup getById(Long id);
	
	CustomerGroup getByGroupId(String groupId);
	
	int update(CustomerGroup customerGroup);
	
	int save(CustomerGroup customerGroup);

	List<CustomerGroup> findCustomerGroups(Map<String, String> map);

	Integer getTotalCountCustomerGroups(Map<String, String> map);

	/**
	 * 根据groupId删除CustomerGroup
	 * @author chenxiang
	 * @date 2014-8-14 下午4:35:59 
	 * @param groupId
	 */
	void deleteCustomerGroupByGroupId(String groupId);

}