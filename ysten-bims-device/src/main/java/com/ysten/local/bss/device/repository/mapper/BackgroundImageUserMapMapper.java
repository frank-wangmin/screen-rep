package com.ysten.local.bss.device.repository.mapper;

import org.apache.ibatis.annotations.Param;
import com.ysten.local.bss.device.domain.BackgroundImageUserMap;
import java.util.List;

public interface BackgroundImageUserMapMapper {

    /**
     * 根据userId查询用户--背景图片映射
     *
     * @param userId
     * @return
     */
    List<BackgroundImageUserMap> findMapByUserCode(@Param("code") String code);

    /**
     * 根据user  group id查询用户--背景图片映射
     *
     * @param deviceGroupId
     * @return
     */
    List<BackgroundImageUserMap> findMapByUserGroupId(@Param("userGroupId") Long userGroupId);

	/**
	 * 根据背景Id获取所有绑定的userId
	 * @param id
	 * @return
	 */
	List<BackgroundImageUserMap> findBGUserMapByBGId(@Param("id")Long id);

	BackgroundImageUserMap findMapByBGIdAndUserCode(@Param("id")Long id, @Param("code")String userId);

	BackgroundImageUserMap findMapByBGIdAndGroupId(@Param("id")Long id, @Param("userGroupId")Long userGroupId);

	int save(BackgroundImageUserMap map);

    void bulkSaveUserBackgroundMap(List<BackgroundImageUserMap> list);

	List<BackgroundImageUserMap> getBGImageMapByBGIdAndType(@Param("bgId")Long bgId, @Param("type")String type);

	int deleteByBGId(@Param("id")Long id);

	/**
	 * 根据分组Id删除背景分组信息
	 * @param id
	 */
	void deleteBackGroundImageMapByGroupId(@Param("groupId")Long groupId);
	int deleteMapByUserCode(@Param("code")String code);
}
