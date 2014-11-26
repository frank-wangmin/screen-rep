package com.ysten.local.bss.notice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ysten.local.bss.device.exception.CustomerNotFoundException;
import com.ysten.local.bss.device.exception.DeviceNotFoundException;
import com.ysten.local.bss.notice.domain.SysNotice;

public interface ISysNoticeService {
	// SysNoticeList getSysNoticeList(String groupId);

	/**
	 * get notice ids by device code
	 * 
	 * @param deviceCode
	 * @return
	 */
	public List<Long> findNoticeIdsByDeviceCode(String deviceCode);

	public List<Long> findNoticeIdsByUserCode(String code);

	/**
	 * get notices which belong to all device
	 * 
	 * @return
	 */
	public List<SysNotice> getBelongToAllNotice();

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

	List<SysNotice> getNoticeByYstenId(String ystenId)
			throws DeviceNotFoundException, CustomerNotFoundException;

	Map<String, ArrayList<SysNotice>> getSysNoticeForExpiringDevices();

}