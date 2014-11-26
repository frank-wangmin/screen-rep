
package com.ysten.local.bss.device.redis;

import com.ysten.local.bss.device.domain.Account;


/**  
 * @author cwang
 * @version 
 * 2014-3-21 上午11:13:20  
 * 
 */
public interface IAccountRedis {
	boolean save(final Account bean);
	Account readByIdOrCode(final String idOrCode );
	boolean delete(final Account bean);
	boolean update(final Account newBean);
}

