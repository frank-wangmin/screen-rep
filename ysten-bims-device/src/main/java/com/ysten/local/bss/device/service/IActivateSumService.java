package com.ysten.local.bss.device.service;


import java.util.List;

import com.ysten.local.bss.device.domain.UserActivateSum;
import com.ysten.local.bss.device.remote.domain.RemoteUserActivateSum;

public interface IActivateSumService {

	/**
	 * 保存激活设备统计信息-远程数据库
	 * @param remoteUserActivateSum
	 * @return
	 */
	boolean saveActivateSumRemote(RemoteUserActivateSum remoteUserActivateSum);
	
	/**
	 * 保存统计作息集合-远程数据库
	 * @param remoteUserActivateSum
	 * @return
	 */
	boolean saveActivateSumRemoteList(List<RemoteUserActivateSum> remoteUserActivateSumList);
	
	/**
	 * 保存激活设备统计信息-本地数据库
	 * @param remoteUserActivateSum
	 * @return
	 */
	boolean saveActivateSum(UserActivateSum userActivateSum);
	
	/**
	 * 保存统计作息集合-本地数据库
	 * @param remoteUserActivateSumList
	 * @return
	 */
	boolean saveActivateSumList(List<UserActivateSum> userActivateSumList);
	
	/**
	 * 批量更新本地数据的同步状态
	 * @param userActivateSumList
	 * @return
	 */
	boolean updateSyncState(List<UserActivateSum> userActivateSumList);
	
	/**
	 * 更新本地数据的同步状态
	 * @param userActivateSum
	 * @return
	 */
	boolean updateSyncState(UserActivateSum userActivateSum);
	
	/**
	 * 查询没有同步过的统计信息
	 * @return
	 */
	List<UserActivateSum> getNotSync();
	
	/**
	 * 同步数据到远程数据库
	 * @param notSyncList
	 * @param remoteUserActivateSumList
	 */
	void syncData(List<UserActivateSum> notSyncList, List<RemoteUserActivateSum> remoteUserActivateSumList);
}
