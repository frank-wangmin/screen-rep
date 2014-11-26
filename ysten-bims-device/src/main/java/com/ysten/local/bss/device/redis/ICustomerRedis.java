package com.ysten.local.bss.device.redis;

import com.ysten.local.bss.device.domain.Customer;

/**
 * @author cwang
 * @version 2014-3-21 上午10:34:50
 * 
 */
public interface ICustomerRedis {
	boolean save(final Customer bean);

	/**
	 * 根据code或者ID查询.
	 * @param codeOrId，根据具体情况，传入code或者ID.
	 * @return
	 */
	Customer getCustomerByCodeOrId(final String codeOrId);

	boolean delete(final Customer newBean);

	boolean update(final Customer newBean);
}
