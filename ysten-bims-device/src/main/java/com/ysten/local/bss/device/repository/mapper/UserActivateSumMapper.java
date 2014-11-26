package com.ysten.local.bss.device.repository.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.device.domain.UserActivateSum;
import com.ysten.local.bss.device.domain.UserActivateSum.SyncType;

/**
 * 
 * 类名称：CustomerMapper
 * 
 * @version
 */
public interface UserActivateSumMapper {
	/**
	 * 保存统计信息
	 * 
	 * @param remoteUserActivateSum
	 *            待保存的统计信息
	 * @return 影响的行数
	 */
	int save(UserActivateSum userActivateSum);
	
	/**
	 * 保存统计信息集合
	 * @param remoteUserActivateSumList  待保存的统计信息集合
	 * @return
	 */
	int saveList(List<UserActivateSum> userActivateSumList);
	
	/**
	 * 根据同步状态查询
	 * @param sync
	 * @return
	 */
	List<UserActivateSum> getBySync(String sync);
	List<UserActivateSum> getList(@Param("date")String date,@Param("province")String province,@Param("sync")SyncType sync, @Param("index") int index, @Param("size") int size);
	List<UserActivateSum> getAll(@Param("index") Integer index, @Param("size") Integer size);
	int getCount(@Param("date")String date,@Param("province")String province,@Param("sync")SyncType sync, @Param("index") int index, @Param("size") int size);
	int getCounts();
	/**
	 * 批量更新本地数据的同步状态
	 * @param userActivateSumList
	 * @return
	 */
	int updateSyncStateList(Map<String, Object> map);
	
	/**
	 * 更新本地数据的同步状态
	 * @param userActivateSum
	 * @return
	 */
	int updateSyncState(UserActivateSum userActivateSum);

}