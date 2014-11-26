package com.ysten.local.bss.device.repository.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysten.local.bss.device.domain.CustomerCust;
import com.ysten.local.bss.device.repository.ICustomerCustRepository;
import com.ysten.local.bss.device.repository.mapper.CustomerCustMapper;
import com.ysten.utils.page.Pageable;

@Repository
public class CustomerCustRepositoryImpl implements ICustomerCustRepository {
	
    @Autowired
    private CustomerCustMapper customerCustMapper;

	@Override
	public CustomerCust getCustomerCustByCustId(String custId) {
		return this.customerCustMapper.getByCustId(custId);
	}

	@Override
	public boolean updateCustomerCust(CustomerCust customerCust) {
		return 1 == this.customerCustMapper.update(customerCust);
	}

	@Override
	public boolean saveCustomerCust(CustomerCust customerCust) {
		return 1 == this.customerCustMapper.save(customerCust);
	}

	@Override
	public CustomerCust getCustomerCustByIp(String ip) {
		String groupIp = "%" + ip + "%";
		return this.customerCustMapper.getByGroupIP(groupIp);
	}
	@Override
	public List<CustomerCust> getCustomerCustListByIp(String ip) {
		String groupIp = "%" + ip + "%";
		return this.customerCustMapper.getCustByIp(groupIp);
	}
	@Override
	public int getCountByIp(String ip){
		String groupIp = "%" + ip + "%";
		return this.customerCustMapper.getCountByIp(groupIp);
	}

	@Override
	public Pageable<CustomerCust> findCustomerCusts(Map<String, String> map) {
		List<CustomerCust> customerCusts = this.customerCustMapper.findCustomerCusts(map);
		Integer total = this.customerCustMapper.getTotalCountCustomerCusts(map);
		return new Pageable<CustomerCust>().instanceByPageNo(customerCusts, total, Integer.parseInt(map.get("pageNo")), Integer.parseInt(map.get("pageSize")));
	}

	/**
	 * 根据CustId删除所有CustomerCust
	 * @author chenxiang
	 * @date 2014-8-14 下午4:28:20 
	 */
	@Override
	public void deleteCustomerCustByCustId(String custId) {
		this.customerCustMapper.deleteCustomerCustByCustId(custId);
	}
	

}
