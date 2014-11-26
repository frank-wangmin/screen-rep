package com.ysten.local.bss.device.service;

import java.util.List;

import com.ysten.local.bss.device.domain.DeviceGroup;
import com.ysten.utils.json.JsonResult;


public interface IDeviceGroupService {
	
	
	
	JsonResult insertOrUpdate(DeviceGroup epgDeviceGroup);
	
	JsonResult deleteDeviceGroupByIds(String ids);
	
	/**
	 * 根据父ID查找单层子节点数据集合，默认查找所有数据
	 * @param pDeviceGroupId
	 * 						父ID
	 * @return
	 */
	List<DeviceGroup>  findDeviceGroupTree(Long pDeviceGroupId);
	
	DeviceGroup  getDeviceGroup(Long deviceGroupId);
	
	/**
	 * 判断设备是否在动态分组中。
	 * @param deviceCode
	 * @param type 动态分组的类型
	 * @param tableName 业务与终端分组的映射表
	 * @return
	 */
	List<DeviceGroup> findDynamicGroupList(String deviceCode,String type,String tableName);
}
