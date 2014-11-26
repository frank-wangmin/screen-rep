package com.ysten.local.bss.device.repository.mapper;

import java.util.List;

import com.ysten.local.bss.device.domain.AnimationUserMap;

import org.apache.ibatis.annotations.Param;


public interface AnimationUserMapMapper {
	/**
	 * 根据userId查询AnimationUserMap
	 * @param userId
	 * @return
	 */
	AnimationUserMap findMapByUserCode(@Param("code") String code, @Param("type") String type);
    
    /**
	 * 根据userGroupId查询AnimationUserMap
	 * @param userGroupId
	 * @return
	 */
	AnimationUserMap findMapByUserGroupId(@Param("userGroupId") Long userGroupId, @Param("type") String type);
    /**
     * 删除
     * 
     * @param id
     * @return
     */
    int deleteByAnimationIdAndType(@Param("bootAnimationId")Long bootAnimationId,@Param("type")String type);
    int deleteMapByUserGroupId(@Param("userGroupId") Long userGroupId);
    int deleteMapByUserCode(@Param("code")String code);
    int deleteByAnimationId(@Param("ids")List<Long> ids);
    /**
     * 保存
     * 
     * @param animationUserMap
     * @return
     */
    int save(AnimationUserMap animationUserMap);
    
    /**
     * 根据开机动画id和类型查询
     * 
     * @param id
     * @return
     */
    List<AnimationUserMap> findAnimationUserMapByAnimationIdAndType(@Param("bootAnimationId")Long bootAnimationId,@Param("type")String type);

    void bulkSaveUserAnimationMap(@Param("list")List<AnimationUserMap> list);
}
