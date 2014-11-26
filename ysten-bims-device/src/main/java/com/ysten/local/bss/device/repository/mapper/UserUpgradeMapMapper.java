package com.ysten.local.bss.device.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.device.domain.UserUpgradeMap;

public interface UserUpgradeMapMapper {
	/**
	 * 根据分组Id删除升级分组信息
	 * @param id
	 */
	int deleteUserUpgradeMapByUserGroupId(@Param("userGroupId")Long userGroupId);

	int save(UserUpgradeMap userUpgradeMap);
	
	int deleteByUpgradeId(@Param("ids")List<Long> ids);
	
	int deleteUserUpgradeMapByUserId(@Param("userId")String userId);
	
	List<UserUpgradeMap> findMapByUpgradeIdAndType(@Param("upgradeId")Long upgradeId,@Param("type")String type);
	
	UserUpgradeMap getUserUpgradeMapByUserGroupId(@Param("userGroupId")Long userGroupId);
	
	UserUpgradeMap getUserUpgradeMapByUserId(@Param("userId")String userId);
}
