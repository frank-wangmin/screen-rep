package com.ysten.local.bss.notice.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ysten.boss.systemconfig.repository.ISystemConfigRepository;
import com.ysten.cache.Cache;
import com.ysten.local.bss.device.domain.Customer;
import com.ysten.local.bss.device.domain.Device;
import com.ysten.local.bss.device.domain.DeviceGroup;
import com.ysten.local.bss.device.domain.UserGroup;
import com.ysten.local.bss.device.domain.UserGroupMap;
import com.ysten.local.bss.device.exception.CustomerNotFoundException;
import com.ysten.local.bss.device.exception.DeviceNotFoundException;
import com.ysten.local.bss.device.repository.IDeviceRepository;
import com.ysten.local.bss.device.repository.IUserGroupMapRepository;
import com.ysten.local.bss.device.repository.IUserGroupRepository;
import com.ysten.local.bss.device.service.ICustomerService;
import com.ysten.local.bss.device.service.IUserGroupService;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import com.ysten.local.bss.notice.domain.DeviceNoticeMap;
import com.ysten.local.bss.notice.domain.SysNotice;
import com.ysten.local.bss.notice.domain.SysNotice.NoticeType;
import com.ysten.local.bss.notice.domain.UserNoticeMap;
import com.ysten.local.bss.notice.repository.ISysNoticeRepository;
import com.ysten.local.bss.notice.service.ISysNoticeService;
import com.ysten.local.bss.util.bean.Constant;

@Service
public class SysNoticeServiceImpl implements ISysNoticeService {
	private static final String EXPIRE_DEVICE_ID_KEY = "ysten:expiring_device:id:key";
	@Autowired
	private ISysNoticeRepository sysNoticeRepository;
	@Autowired
	private IDeviceRepository deviceRepository;
	@Autowired
	private ICustomerService customerService;
	@Autowired
	private IUserGroupMapRepository userGroupMapRepository;
	@Autowired
	private IUserGroupService userGroupService;

	@Autowired
	private IUserGroupRepository userGroupRepository;

	@Autowired
	private ISystemConfigRepository systemConfigRepository;
	
	@Autowired
	private Cache<String, Serializable> cache;

	@Override
	public List<Long> findNoticeIdsByDeviceCode(String deviceCode) {
		return sysNoticeRepository.findNoticeIdsByDeviceCode(deviceCode);
	}

	@Override
	public List<SysNotice> getBelongToAllNotice() {
		return sysNoticeRepository.getBelongToAllNotice();
	}

	@Override
	public List<SysNotice> getNoticeListByIds(List<Long> noticeIds) {
		return sysNoticeRepository.getNoticeListByIds(noticeIds);
	}

	@Override
	public List<Long> findNoticeIdsByDeviceGroupIds(List<Long> deviceGroupIds) {
		return sysNoticeRepository
				.findNoticeIdsByDeviceGroupIds(deviceGroupIds);
	}

	@Override
	public List<Long> findNoticeIdsByUserGroupIds(List<Long> userGroupIds) {
		return this.sysNoticeRepository
				.findNoticeIdsByUserGroupIds(userGroupIds);
	}

	@Override
	public List<Long> findNoticeIdsByUserCode(String code) {
		return this.sysNoticeRepository.findNoticeIdsByUserCode(code);
	}

	@Override
	public List<SysNotice> getNoticeByYstenId(String ystenId)
			throws DeviceNotFoundException, CustomerNotFoundException {
		// 1.查询设备是否合法
		Device device = deviceRepository.getDeviceByYstenId(ystenId);
		if (null == device) {
			throw new DeviceNotFoundException("can not find device info.");
		}
		// TODO:改成中心管理、暂时注释掉用户验证;用户、用户组绑定业务的判断。
		Customer customer = customerService.getCustomerByYstenId(ystenId);
		// if (customer == null) {
		// throw new CustomerNotFoundException("can not find customer info ");
		// }
		List<SysNotice> sysNoticeList = (List<SysNotice>) cache.get(EXPIRE_DEVICE_ID_KEY + ystenId);
		List<SysNotice> list = new ArrayList<SysNotice>();
		if (customer != null) {
			// 2.用户是否绑定背景
			// 2.1判断用户与消息的map表中是否有记录
			List<UserNoticeMap> userNoticeMaps = sysNoticeRepository
					.findSysNoticeMapByUserCode(customer.getCode());
			if (!CollectionUtils.isEmpty(userNoticeMaps)) {
				for (UserNoticeMap userNoticeMap : userNoticeMaps) {
					SysNotice s = sysNoticeRepository
							.getSysNoticeById(userNoticeMap.getNoticeId());
					if (s != null && s.getStatus() == 1
							&& s.getEndDate().after(new Date())
							&& !s.getStartDate().after(new Date())) {
						list.add(s);
					}
				}
			}
			if (!CollectionUtils.isEmpty(list)) {
				if (sysNoticeList != null) {
					list.addAll(sysNoticeList);
				}
				return list;
			}
			// 3.用户分组是否绑定消息
			// TODO:内存优化
			List<UserGroup> userGroupList = userGroupService
					.findDynamicGroupList(customer.getCode(),
							EnumConstantsInterface.UserGroupType.NOTICE
									.toString(), Constant.BSS_USER_NOTICE_MAP);
			// 3.1动态分组是否存在
			if (CollectionUtils.isEmpty(userGroupList)) {
				// 3.2动态分组不存在，取设备所在的静态分组
				// 判断用户与用户分组的map表中，是否有记录
				List<UserGroupMap> userGroupMap = this.userGroupMapRepository
						.findByUserCode(customer.getCode());
				// TODO:内存优化
				if (!CollectionUtils.isEmpty(userGroupMap)) {
					userGroupList = this.userGroupRepository
							.findUserGroupListByIdsAndType(userGroupMap,
									EnumConstantsInterface.UserGroupType.NOTICE);
				}
			}
			// 3.4根据用户组查询与消息的关系
			if (!CollectionUtils.isEmpty(userGroupList)) {
				// 消息是取得所有的集合
				for (int i = 0; i < userGroupList.size(); i++) {
					List<UserNoticeMap> mapList = sysNoticeRepository
							.findSysNoticeMapByUserGroupId(userGroupList.get(i)
									.getId() + "");
					userNoticeMaps.addAll(mapList);
				}
			}
			if (!CollectionUtils.isEmpty(userNoticeMaps)) {
				for (UserNoticeMap userNoticeMap : userNoticeMaps) {
					SysNotice s = sysNoticeRepository
							.getSysNoticeById(userNoticeMap.getNoticeId());
					if (s != null && s.getStatus() == 1
							&& s.getEndDate().after(new Date())
							&& !s.getStartDate().after(new Date())) {
						list.add(s);
					}
				}
			}
			if (!CollectionUtils.isEmpty(list)) {
				if (sysNoticeList != null) {
					list.addAll(sysNoticeList);
				}
				return list;
			}
		}

		// 4.设备是否绑定背景
		// 4.1判断设备与消息的map表中是否有记录
		List<DeviceNoticeMap> deviceNoticeMaps = sysNoticeRepository
				.findSysNoticeMapByYstenId(ystenId);
		if (!CollectionUtils.isEmpty(deviceNoticeMaps)) {
			for (DeviceNoticeMap backgroudUserMap : deviceNoticeMaps) {
				SysNotice s = sysNoticeRepository
						.getSysNoticeById(backgroudUserMap.getNoticeId());
				if (s != null && s.getStatus() == 1
						&& s.getEndDate().after(new Date())
						&& !s.getStartDate().after(new Date())) {
					list.add(s);
				}
			}
		}
		if (!CollectionUtils.isEmpty(list)) {
			if (sysNoticeList != null) {
				list.addAll(sysNoticeList);
			}
			return list;
		}
		// 5.设备分组是否绑定消息
		// TODO:内存优化
		List<DeviceGroup> groupList = this.deviceRepository
				.findDeviceGroupByYstenId(device.getYstenId(),
						EnumConstantsInterface.DeviceGroupType.NOTICE);
		// deviceGroupService.findDynamicGroupList(device.getYstenId(),
		// EnumConstantsInterface.DeviceGroupType.NOTICE.toString(),
		// Constant.BSS_DEVICE_NOTICE_MAP);
		// 5.1动态分组是否存在
		// if (CollectionUtils.isEmpty(groupList)) {
		// // 5.2动态分组不存在，取设备所在的静态分组
		// // TODO:内存优化
		// groupList =
		// this.deviceRepository.findDeviceGroupByYstenId(device.getCode(),
		// EnumConstantsInterface.DeviceGroupType.NOTICE);
		// }
		// 5.3根据设备组查询与消息的关系
		if (!CollectionUtils.isEmpty(groupList)) {
			// 消息是取得所有的集合
			for (int i = 0; i < groupList.size(); i++) {
				List<DeviceNoticeMap> mapList = sysNoticeRepository
						.findSysNoticeMapByDeviceGroupId(groupList.get(i)
								.getId());
				deviceNoticeMaps.addAll(mapList);
			}
		}

		if (!CollectionUtils.isEmpty(deviceNoticeMaps)) {
			for (DeviceNoticeMap backgroudUserMap : deviceNoticeMaps) {
				SysNotice s = sysNoticeRepository
						.getSysNoticeById(backgroudUserMap.getNoticeId());
				if (s != null && s.getStatus() == 1
						&& s.getEndDate().after(new Date())
						&& !s.getStartDate().after(new Date())) {
					list.add(s);
				}
			}
		}
		if (!CollectionUtils.isEmpty(list)) {
			if (sysNoticeList != null) {
				list.addAll(sysNoticeList);
			}
			return list;
		}
		// 6.都没有，取默认分组
		list = this.getBelongToAllNotice();
		if (sysNoticeList != null) {
			list.addAll(sysNoticeList);
		}
		return list;
	}

	public Map<String, ArrayList<SysNotice>> getSysNoticeForExpiringDevices() {
		Date beginDate = new Date();
		int days = systemConfigRepository.getNoticePeriodForExpiringDevice();
		Calendar cal = Calendar.getInstance();
		cal.setTime(beginDate);
		cal.add(Calendar.DAY_OF_MONTH, days);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 59);
		List<Device> list = deviceRepository.findExpiringDevices(beginDate, cal.getTime());
		
		List<SysNotice> noticeList = sysNoticeRepository.findSysNoticeByType(NoticeType.SYSTEM);
		if (noticeList.size() == 0 || list.size() == 0) {
			return null;
		}
		Map<String, ArrayList<SysNotice>> result = new HashMap<String, ArrayList<SysNotice>>();
		for (Device device : list) {
			ArrayList<SysNotice> temp = new ArrayList<SysNotice>();
			for (SysNotice notice : noticeList) {
				if (device.getDistrictCode() != null && StringUtils.isNotBlank(notice.getDistrictCode()) && notice.getDistrictCode().contains(device.getDistrictCode())) {
					temp.add(notice);
				}
			}
			if (temp.size() > 0) {
				result.put(device.getYstenId(), temp);
			}
		}
		return result;
	}
}
