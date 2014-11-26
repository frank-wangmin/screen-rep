package com.ysten.local.bss.device.repository.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ysten.local.bss.device.domain.UserGroupPpInfoMap;

public interface UserGroupPpInfoMapMapper {
	/**
	 * 根据分组Id删除关系信息
	 * @param userGroupId
	 */
	int deleteMapByUserGroupId(@Param("userGroupId")Long userGroupId);

	int save(UserGroupPpInfoMap userGroupPpInfoMap);
	
//	List<UserGroupPpInfoMap> findMapByUserGroupIdAndProductType(@Param("userGroupId")Long userGroupId,@Param("productType")String productType);
	
	int update(UserGroupPpInfoMap userGroupPpInfoMap);
	
	List<UserGroupPpInfoMap> findMapByUserGroupIdAndProductId(@Param("userGroupId")Long userGroupId,@Param("productId")String productId);
}
