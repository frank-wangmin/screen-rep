package com.ysten.local.bss.device.repository;


import java.util.Date;
import java.util.List;

import com.ysten.local.bss.device.domain.UserActivateSum;
import com.ysten.local.bss.device.domain.UserActivateSum.SyncType;
import com.ysten.utils.page.Pageable;

/**
 * IAccountRepository interface
 * 
 * @fileName IAccountRespository.java
 */
public interface IActivateSumRepository {

	/**
	 * 保存统计信息
	 * 
	 * @param account
	 * @return
	 */
	boolean saveUserActivateSum(UserActivateSum userActivateSum);
	
	/**
	 * 保存统计信息集合
	 * 
	 * @param account
	 * @return
	 */
	boolean saveUserActivateSumList(List<UserActivateSum> userActivateSumList);

	/**
	 * 根据sync状态查询
	 * @param sync
	 * @return
	 */
	List<UserActivateSum> findUserActivateSumBySync(String sync);
	
	/**
	 * 批量更新本地数据的同步状态
	 * @param syncState 0:未同步，1：已同步
	 * @param syncDate 同步的时间
	 * @param userActivateSumList 需要同步的数据集合
	 * @return
	 */
	boolean updateUserActivateSumSyncState(int syncState, Date syncDate, List<UserActivateSum> userActivateSumList);
	
	/**
	 * 更新本地数据的同步状态
	 * @param userActivateSum
	 * @return
	 */
	boolean updateUserActivateSumSyncState(UserActivateSum userActivateSum);
	/**
	 * 
	 * @param date
	 * @param province
	 * @param sync
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Pageable<UserActivateSum> getUserActivateSumList(String date,String province,SyncType sync,int pageNo, int pageSize);
	/**
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Pageable<UserActivateSum> getAll(int pageNo, int pageSize);

	/**
	 * 获取所有终端用户统计数据
	 */
	List<UserActivateSum> findAllUserActiveSum();
}
