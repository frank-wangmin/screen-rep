package com.ysten.local.bss.device.repository.mapper;

import java.util.List;

import com.ysten.local.bss.device.domain.AnimationDeviceMap;

import org.apache.ibatis.annotations.Param;


public interface AnimationDeviceMapMapper {
	/**
	 * 根据deviceCode查询AnimationDeviceMap
	 * @param type
	 * @return
	 */
    AnimationDeviceMap findMapByYstenId(@Param("ystenId") String ystenId, @Param("type") String type);
    
    /**
	 * 根据deviceGroupId查询AnimationDeviceMap
	 * @param deviceGroupId
	 * @return
	 */
    AnimationDeviceMap findMapByDeviceGroupId(@Param("deviceGroupId") Long deviceGroupId, @Param("type") String type);
    /**
     * 删除
     * 
     * @param type
     * @return
     */
    int deleteByAnimationIdAndType(@Param("bootAnimationId")Long bootAnimationId,@Param("type")String type,@Param("tableName")String tableName);

    /**
     * 保存
     * 
     * @param animationDeviceMap
     * @return
     */
    int save(AnimationDeviceMap animationDeviceMap);
    
    /**
     * 根据开机动画id和类型查询
     * 
     * @param type
     * @param bootAnimationId
     * @return
     */
    List<AnimationDeviceMap> findAnimationDeviceMapByAnimationIdAndType(@Param("bootAnimationId")Long bootAnimationId,@Param("type")String type);

	/**
	 * 根据分组Id删除开机动画设备分组信息
	 * @param id
	 */
	void deleteAnimationDeviceMapByGroupId(@Param("groupId")Long id);
	
	int deleteAnimationDeviceMapByCode(@Param("ystenId")String ystenId);
	
	int deleteByAnimationId(@Param("ids")List<Long> ids);
	
//	void deleteAnimationDeviceMapByGroupIds(@Param("ids")List<Long> ids);

    void deleteAnimationMapByGroupIds(@Param("ids")List<Long> groupIds,@Param("tableName")String tableName,@Param("character")String character);
	
	void deleteAnimationDeviceMapByYstenIds(@Param("ystenIds")List<String> ystenIds,@Param("tableName")String tableName,@Param("character") String character);
	
	void bulkSaveAnimationMap(List<AnimationDeviceMap> list);
}
