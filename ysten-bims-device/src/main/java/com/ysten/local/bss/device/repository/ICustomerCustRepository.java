package com.ysten.local.bss.device.repository;

import java.util.Map;
import java.util.List;
import com.ysten.local.bss.device.domain.CustomerCust;
import com.ysten.utils.page.Pageable;

public interface ICustomerCustRepository {

	CustomerCust getCustomerCustByCustId(String custId);
	
	CustomerCust getCustomerCustByIp(String ip);
	
	List<CustomerCust> getCustomerCustListByIp(String ip);

	boolean updateCustomerCust(CustomerCust customerCust);
	
	boolean saveCustomerCust(CustomerCust customerCust);
	
	int getCountByIp(String ip);


	/**
	 * 返回客户信息
	 * 
	 * @param map
	 * @return
	 */
	Pageable<CustomerCust> findCustomerCusts(Map<String, String> map);

	/**
	 * 根据CustId删除所有CustomerCust
	 * @author chenxiang
	 * @date 2014-8-14 下午4:27:43 
	 * @param custId
	 */
	void deleteCustomerCustByCustId(String custId);
}
