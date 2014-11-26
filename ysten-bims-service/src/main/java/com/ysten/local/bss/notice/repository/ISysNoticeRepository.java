package com.ysten.local.bss.notice.repository;

import java.util.List;

import com.ysten.local.bss.device.domain.UserGroup;
import com.ysten.local.bss.notice.domain.DeviceNoticeMap;
import com.ysten.local.bss.notice.domain.SysNotice;
import com.ysten.local.bss.notice.domain.SysNotice.NoticeType;
import com.ysten.local.bss.notice.domain.UserNoticeMap;
import com.ysten.utils.page.Pageable;

public interface ISysNoticeRepository {
	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	boolean deleteSysNotice(List<Long> ids);

	/**
	 * 保存
	 * 
	 * @param sysNotice
	 * @return
	 */
	boolean saveSysNotice(SysNotice sysNotice);

	/**
	 * 通过id查询
	 * 
	 * @param id
	 * @return
	 */
	SysNotice getSysNoticeById(Long id);

	SysNotice findSysNoticeByTitle(String title);

	List<SysNotice> findAllSysNoticeList();

	/**
	 * 更新
	 * 
	 * @param sysNotice
	 * @return
	 */
	boolean updateSysNotice(SysNotice sysNotice);

	/**
	 * 查询消息
	 * 
	 * @param title
	 * @param content
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Pageable<SysNotice> findSysNotice(String title, String content,
			Integer pageNo, Integer pageSize);

	/*   *//**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	/*
	 * boolean deleteDeviceNoticeMapByNoticeId(Long noticeId,String type);
	 */

	void deleteByNoticeIdAndType(Long noticeId, String type, String tableName);

	/**
	 * 保存
	 * 
	 * @param deviceNoticeMap
	 * @return
	 */
	boolean saveDeviceNoticeMap(DeviceNoticeMap deviceNoticeMap);

	void bulkSaveDeviceNoticeMap(List<DeviceNoticeMap> list);

	void bulkSaveUserNoticeMap(List<UserNoticeMap> list);

	boolean saveUserNoticeMap(UserNoticeMap userNoticeMap);

	/**
	 * 根据消息id查询
	 * 
	 * @param id
	 * @return
	 */
	List<DeviceNoticeMap> findDeviceNoticeMapByNoticeIdAndType(Long noticeId,
			String type);

	List<UserNoticeMap> findUserNoticeMapByNoticeIdAndType(Long noticeId,
			String type);

	/**
	 * 根据用户分组ID查询MAP
	 * 
	 * @param userGroupId
	 * @return
	 */
	List<UserNoticeMap> getByUserGroupId(Long userGroupId);

	/**
	 * get notice ids by device code
	 * 
	 * @param deviceCode
	 * @return
	 */
	public List<Long> findNoticeIdsByDeviceCode(String deviceCode);

	List<Long> findNoticeIdsByUserCode(String code);

	/**
	 * get notices which belong to all device
	 * 
	 * @return
	 */
	public List<SysNotice> getBelongToAllNotice();

	List<SysNotice> findSysNoticeByYstenIdOrGroupId(String ystenId, Long groupId);

	List<SysNotice> findSysNoticeByCustomerCodeOrGroupId(String customerCode,
			Long groupId);

	/**
	 * get notices by notice ids
	 * 
	 * @param noticeIds
	 * @return
	 */
	public List<SysNotice> getNoticeListByIds(List<Long> noticeIds);

	/**
	 * get notice ids by device group ids
	 * 
	 * @param deviceGroupIds
	 * @return
	 */
	public List<Long> findNoticeIdsByDeviceGroupIds(List<Long> deviceGroupIds);

	public List<Long> findNoticeIdsByUserGroupIds(List<Long> userGroupIds);

	/**
	 * 根据设备分组ID查询MAP
	 * 
	 * @param groupId
	 * @return
	 */
	List<DeviceNoticeMap> findSysNoticeMapByDeviceGroupId(Long groupId);

	List<DeviceNoticeMap> findSysNoticeMapByYstenId(String ystenId);

	// boolean deleteUserNoticeMapByNoticeId(Long noticeId,String type);

	DeviceNoticeMap findMapByNoticeIdAndYstenId(Long noticeId, String ystenId);

	/**
	 * 根据分组Id删除消息设备分组信息
	 * 
	 * @param id
	 */
	void deleteSysNoticeMapByGroupId(Long id);

	void deleteSysNoticeMapByYstenId(String ystenId);

	void deleteByUserGroupId(Long userGroupId);

	void deleteMapByYstenIdsAndNoticeId(Long noticeId, List<String> ystenIds,
			String tableName, String character);

	List<UserNoticeMap> findSysNoticeMapByUserCode(String code);

	List<UserNoticeMap> findSysNoticeMapByUserGroupId(String groupId);

	void deleteByUserCode(String code);

	/**
	 * get all the UserGroups bound with sysNotice
	 * 
	 * @param noticeId
	 * @param type
	 * @return list
	 */
	List<UserGroup> findUserGroupByNoticeIdAndType(Long noticeId, String type);

	/**
	 * find all the sysNotices bound with device
	 * 
	 * @param groupIds
	 * @param codes
	 * @return list
	 */
	List<SysNotice> findSysNoticeByDevice(List<Long> groupIds, String codes);

	/**
	 * find all the sysNotices bound with user
	 * 
	 * @param groupIds
	 * @param codes
	 * @return list
	 */
	List<SysNotice> findSysNoticeByUser(List<Long> groupIds, String codes);

	/**
	 * find all the sysNotices by type
	 * 
	 * @param type notice type
	 * @return list
	 */
	List<SysNotice> findSysNoticeByType(NoticeType type);
}