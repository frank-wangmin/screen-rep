package com.ysten.local.bss.device.repository.mapper;

import java.util.List;
import java.util.Map;

import com.ysten.local.bss.device.domain.UserUpgradeMap;
import org.apache.ibatis.annotations.Param;


import com.ysten.local.bss.device.domain.UpgradeTask;

public interface UpgradeTaskMapper {

	UpgradeTask getById(long id);
	
	UpgradeTask findLatestUpgradeTask(Map<String, Object> parameterMap);
	
	List<UpgradeTask> getListByCondition(@Param("softwarePackageId")Long softwarePackageId,@Param("softCodeId")Long softCodeId,
			@Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);
	
	List<UpgradeTask> findUpgradeTaskListByCondition(@Param("softwarePackageName")String softwarePackageName,@Param("softCodeName")String softCodeName,
			@Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);
	
	int getCountByCondition(@Param("softwarePackageId")Long softwarePackageId,
			@Param("softCodeId")Long softCodeId);

    List<UpgradeTask> findAllUpgradeTask();
	
	int getCountBySoftCodeAndPackage(@Param("softwarePackageName")String softwarePackageName,@Param("softCodeName")String softCodeName);
	
	int delete(@Param("ids")List<Long> ids);
	
	int deleteById(@Param("id")Long id);
	
	int updateById(UpgradeTask upgradeTask);
	
	int save(UpgradeTask upgradeTask);
	
	UpgradeTask getByVersionSeq(@Param("versionSeq")int versionSeq);

    List<UserUpgradeMap> getUserMapBySoftCodeIdAndVersionSeq(@Param("softCodeId")Long softCodeId, @Param("versionSeq") int versionSeq);

    List<UpgradeTask> findUpgradeTaskByYstenIdOrGroupId(@Param("ystenId")String ystenId, @Param("groupId")Long groupId);
}
