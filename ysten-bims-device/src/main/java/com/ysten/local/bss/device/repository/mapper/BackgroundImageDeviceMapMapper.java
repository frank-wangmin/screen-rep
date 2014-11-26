package com.ysten.local.bss.device.repository.mapper;

import com.ysten.local.bss.device.domain.BackgroundImageDeviceMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BackgroundImageDeviceMapMapper {

    /**
     * 根据device code查询设备--背景图片映射
     *
     * @param deviceCode
     * @return
     */
    List<BackgroundImageDeviceMap> findMapByYstenId(@Param("ystenId") String deviceCode);

    /**
     * 根据device group id查询设备--背景图片映射
     *
     * @param deviceGroupId
     * @return
     */
    List<BackgroundImageDeviceMap> findMapByDeviceGroupId(@Param("deviceGroupId") Long deviceGroupId);

	/**
	 * 根据背景Id获取所有绑定的终端code
	 * @param id
	 * @return
	 */
	List<BackgroundImageDeviceMap> findBGDeviceMapByBGId(@Param("id")Long id);

	BackgroundImageDeviceMap findMapByBGIdAndCode(@Param("id")Long id, @Param("ystenId")String ystenId);

	BackgroundImageDeviceMap findMapByBGIdAndGroupId(@Param("id")Long id, @Param("deviceGroupId")Long deviceGroupId);

	int save(BackgroundImageDeviceMap map);
	
	void bulkSaveBackgroundMap(List<BackgroundImageDeviceMap> list);
	
	void deleteBackGroundMapByYstenIdsAndBgId(@Param("bgId")Long bgId,@Param("ystenIds")List<String> ystenIds,@Param("tableName")String tableName,@Param("character")String character);

	List<BackgroundImageDeviceMap> getBGImageMapByBGIdAndType(@Param("bgId")Long bgId, @Param("type")String type);

	int deleteByBGId(@Param("id")Long id);

	/**
	 * 根据设备分组Id删除背景终端分组信息
	 * @param id
	 */
	void deleteBackGroundImageMapByGroupId(@Param("groupId")Long id);
	int deleteBackGroundImageMapByCode(@Param("ystenId")String ystenId);
	
	void deleteByBGIdAndType(@Param("groupId")Long groupId,@Param("type")String type,@Param("tableName")String tableName);
}
