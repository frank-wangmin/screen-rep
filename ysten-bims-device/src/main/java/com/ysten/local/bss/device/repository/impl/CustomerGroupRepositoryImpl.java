package com.ysten.local.bss.device.repository.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysten.local.bss.device.domain.CustomerGroup;
import com.ysten.local.bss.device.repository.ICustomerGroupRepository;
import com.ysten.local.bss.device.repository.mapper.CustomerGroupMapper;
import com.ysten.utils.page.Pageable;

@Repository
public class CustomerGroupRepositoryImpl implements ICustomerGroupRepository {
	
    @Autowired
    private CustomerGroupMapper customerGroupMapper;

	@Override
	public CustomerGroup getCustomerGroupByGroupId(String groupId) {
		return this.customerGroupMapper.getByGroupId(groupId);
	}

	@Override
	public boolean updateCustomerGroup(CustomerGroup customerGroup) {
		return 1 == this.customerGroupMapper.update(customerGroup);
	}

	@Override
	public boolean saveCustomerGroup(CustomerGroup customerGroup) {
		return 1 == this.customerGroupMapper.save(customerGroup);
	}

	@Override
	public Pageable<CustomerGroup> findCustomerGroups(Map<String, String> map) {
		List<CustomerGroup> customerGroups = this.customerGroupMapper.findCustomerGroups(map);
		Integer total = this.customerGroupMapper.getTotalCountCustomerGroups(map);
		return new Pageable<CustomerGroup>().instanceByPageNo(customerGroups, total, Integer.parseInt(map.get("pageNo")), Integer.parseInt(map.get("pageSize")));
	}

	/**
	 * 根据groupId删除CustomerGroup
	 * @author chenxiang
	 * @date 2014-8-14 下午4:35:25 
	 */
	@Override
	public void deleteCustomerGroupByGroupId(String groupId) {
		this.customerGroupMapper.deleteCustomerGroupByGroupId(groupId);
	}

}
