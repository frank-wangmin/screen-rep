package com.ysten.local.bss.device.repository.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ysten.local.bss.device.domain.UserAppUpgradeMap;


public interface UserAppUpgradeMapMapper {

	void deleteUserUpgradeMapByUserGroupId(@Param("userGroupId")Long userGroupId);

	int save(UserAppUpgradeMap userAppUpgradeMap);
	
	int deleteByUpgradeId(@Param("ids")List<Long> ids);
	
	int deleteAppUpgradeMapByUserId(@Param("userId")String userId);
	
	List<UserAppUpgradeMap> getUserUpgradeMapByUserId(@Param("userId")String userId);
	
	List<UserAppUpgradeMap> findMapByUpgradeIdAndType(@Param("upgradeId")Long upgradeId,@Param("type")String type);

	List<UserAppUpgradeMap> getUserUpgradeMapByUserGroupId(@Param("userGroupId")Long userGroupId);

    int deleteUserAppUpgradeMapByUpgradeId(@Param("upgradeId")Long upgradeId);

    List<UserAppUpgradeMap> findUserMapByUpgradeId(@Param("upgradeId")Long upgradeId);

}
