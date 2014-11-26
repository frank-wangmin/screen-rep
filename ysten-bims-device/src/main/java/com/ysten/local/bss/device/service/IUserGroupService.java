package com.ysten.local.bss.device.service;

import java.util.List;

import com.ysten.local.bss.device.domain.UserGroup;

/**
 * @author cwang
 * @version 2014-6-23 上午11:58:09
 * 
 */
public interface IUserGroupService {
	/**
	 * 判断用户是否在动态分组中
	 * @param userId
	 * @param type 动态分组的类型
	 * @param tableName 业务与终端分组的映射表
	 * @return
	 */
	List<UserGroup> findDynamicGroupList(String code, String type, String tableName);
}
