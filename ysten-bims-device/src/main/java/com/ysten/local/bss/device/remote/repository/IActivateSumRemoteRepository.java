package com.ysten.local.bss.device.remote.repository;


import java.util.List;

import com.ysten.local.bss.device.remote.domain.RemoteUserActivateSum;

/**
 * IAccountRepository interface
 * 
 * @fileName IAccountRespository.java
 */
public interface IActivateSumRemoteRepository {

	/**
	 * 保存统计信息
	 * 
	 * @param account
	 * @return
	 */
	boolean save(RemoteUserActivateSum remoteUserActivateSum);
	
	/**
	 * 保存统计信息集合
	 * 
	 * @param account
	 * @return
	 */
	boolean saveList(List<RemoteUserActivateSum> remoteUserActivateSumList);
}
