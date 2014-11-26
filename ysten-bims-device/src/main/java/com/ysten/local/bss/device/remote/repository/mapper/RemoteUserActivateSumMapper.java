package com.ysten.local.bss.device.remote.repository.mapper;

import java.util.List;

import com.ysten.local.bss.device.remote.domain.RemoteUserActivateSum;

/**
 * 
 * 类名称：RemoteUserActivateSumMapper
 * 
 * @version
 */
public interface RemoteUserActivateSumMapper {

	/**
	 * 保存统计信息
	 * 
	 * @param remoteUserActivateSum
	 *            待保存的统计信息
	 * @return 影响的行数
	 */
	int save(RemoteUserActivateSum remoteUserActivateSum);
	
	/**
	 * 保存统计信息集合
	 * @param remoteUserActivateSumList  待保存的统计信息集合
	 * @return
	 */
	int saveList(List<RemoteUserActivateSum> remoteUserActivateSumList);

}