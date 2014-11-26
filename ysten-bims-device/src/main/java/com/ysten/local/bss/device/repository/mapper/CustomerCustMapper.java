package com.ysten.local.bss.device.repository.mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;
import com.ysten.local.bss.device.domain.CustomerCust;

public interface CustomerCustMapper {

	CustomerCust getById(Long id);
	
	CustomerCust getByCustId(String custId);
	
	CustomerCust getByGroupIP(String groupIp);
	
	//List<CustomerCust> getCustByIp(@Param("groupIp")String groupIp,@Param("index") int index, @Param("size") int size);

	List<CustomerCust> getCustByIp(@Param("groupIp")String groupIp);

	int getCountByIp(@Param("groupIp")String groupIp);

	int update(CustomerCust customerCust);
	
	int save(CustomerCust customerCust);

	/**
	 * 返回客户信息
	 * 
	 * @param map
	 * @return
	 */
	List<CustomerCust> findCustomerCusts(Map<String, String> map);

	/**
	 * 返回客户信息总的记录数
	 * 
	 * @param map
	 * @return
	 */
	Integer getTotalCountCustomerCusts(Map<String, String> map);

	/**
	 * 根据CustId删除所有CustomerCust
	 * @author chenxiang
	 * @date 2014-8-14 下午4:30:37 
	 * @param custId
	 */
	void deleteCustomerCustByCustId(String custId);

}