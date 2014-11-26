package com.ysten.local.bss.device.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.device.domain.DeviceGroupMap;

/**
 * DeviceGroupMapper接口
 * 
 * @fileName DeviceGroupMapper.java
 */
public interface DeviceGroupMapMapper {

    /**
     * 按照主键ID获取设备分组信息
     * 
     * @param id
     *            主键ID
     * @return 设备分组信息
     */
    DeviceGroupMap getById(Long id);
    
    List<DeviceGroupMap> getByYstenId(@Param("ystenId")String ystenId);
    
    List<DeviceGroupMap> getDeviceGroupMapByGroupId(@Param("groupId")Long groupId);

	/**
	 * 保存终端分组关系
	 * @param deviceGroupMap
	 * @return
	 */
	int save(DeviceGroupMap deviceGroupMap);
	/**
     * 批量插入设备与设备组关系--DeviceGroupMap
     * @param list
     */
	void BulkSaveDeviceGroupMap(List<DeviceGroupMap> list);

	/**
	 * @param code
	 * @param groupId
	 * @return
	 */
	DeviceGroupMap getByCodeAndGroup(@Param("code")String code, @Param("groupId")Long groupId);

	/**
	 * 根据分组Id删除终端分组信息
	 * @param id
	 */
	void deleteDeviceGroupMapByGroupId(@Param("groupId")Long id);
	
	int deleteDeviceGroupMapByCode(@Param("code")String code);
	
	void deleteDeviceGroupMapByCodeAndGroup(@Param("code")String code, @Param("groupId")Long groupId);
	
	void deleteMapByYstenIdsAndGroups(@Param("ystenIds")String[] ystenIds, @Param("groupIds")List<Long> groupIds);
	
}
