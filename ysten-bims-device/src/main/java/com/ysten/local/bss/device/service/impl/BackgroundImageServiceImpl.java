package com.ysten.local.bss.device.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.ysten.cache.annotation.KeyParam;
import com.ysten.cache.annotation.MethodCache;
import com.ysten.local.bss.device.domain.BackgroundImage;
import com.ysten.local.bss.device.domain.BackgroundImageDeviceMap;
import com.ysten.local.bss.device.domain.BackgroundImageUserMap;
import com.ysten.local.bss.device.domain.Customer;
import com.ysten.local.bss.device.domain.Device;
import com.ysten.local.bss.device.domain.DeviceGroup;
import com.ysten.local.bss.device.domain.UserGroup;
import com.ysten.local.bss.device.domain.UserGroupMap;
import com.ysten.local.bss.device.exception.CustomerNotFoundException;
import com.ysten.local.bss.device.exception.DeviceNotFoundException;
import com.ysten.local.bss.device.repository.IBackgroundImageRepository;
import com.ysten.local.bss.device.repository.IDeviceRepository;
import com.ysten.local.bss.device.repository.IUserGroupMapRepository;
import com.ysten.local.bss.device.repository.IUserGroupRepository;
import com.ysten.local.bss.device.service.IBackgroundImageService;
import com.ysten.local.bss.device.service.ICustomerService;
import com.ysten.local.bss.device.service.IDeviceGroupService;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import com.ysten.local.bss.util.bean.Constant;
import com.ysten.local.bss.util.bean.RedisUtils;
import com.ysten.local.bss.util.bean.RedisUtils.DeviceInterfaceType;

/**
 * Created by Neil on 2014-05-06.
 */
@Service
public class BackgroundImageServiceImpl implements IBackgroundImageService {
	private static final int isDefault = 1; // 默认
	@Autowired
	private IBackgroundImageRepository backgroundImageRepository;
	@Autowired
	private ICustomerService customerService;
	@Autowired
	private IDeviceRepository deviceRepository;

	@Autowired
	private IDeviceGroupService deviceGroupService;

	@Autowired
	private IUserGroupRepository userGroupRepository;

	@Autowired
	private IUserGroupMapRepository userGroupMapRepository;

	private static final String BACKGROUND_IMAGE_KEY = "backgroundImage:interface:ystenId:";
	
	@Autowired
	private RedisTemplate<Serializable, Serializable> redisTemplate;

	@Override
	public List<BackgroundImageDeviceMap> getMapByDeviceCode(String deviceCode) {
		return this.backgroundImageRepository.findMapByYstenId(deviceCode);
	}

	@Override
	public List<BackgroundImageDeviceMap> getMapByDeviceGroupId(Long deviceGroupId) {
		return this.backgroundImageRepository.findMapByDeviceGroupId(deviceGroupId);
	}

	@Override
	@MethodCache(key = BACKGROUND_IMAGE_KEY + "#{ystenId}")
	public String getBackgroundImageByYstenId(@KeyParam("ystenId") String ystenId) throws DeviceNotFoundException, CustomerNotFoundException {
		// 1.查询设备是否合法
		Device device = this.deviceRepository.getDeviceByYstenId(ystenId);
		if (device == null) {
			throw new DeviceNotFoundException("can not find device info.");
		}
		// TODO:改成中心管理、暂时注释掉用户验证;用户、用户组绑定业务的判断。
		Customer customer = customerService.getCustomerByYstenId(ystenId);
		// if (customer == null) {
		// throw new CustomerNotFoundException("can not find customer info ");
		// }
		List<BackgroundImage> list = new ArrayList<BackgroundImage>();
		StringBuilder businessId = new StringBuilder();
		int loopTime;
		if (customer != null) {
			// 2.用户是否绑定背景
			// 2.1判断用户与背景的map表中是否有记录
			List<BackgroundImageUserMap> imgUserMaps = this.backgroundImageRepository.findMapByUserCode(customer.getCode());
			if (!CollectionUtils.isEmpty(imgUserMaps)) {
				loopTime = imgUserMaps.get(0).getLoopTime();
				for (BackgroundImageUserMap backgroudUserMap : imgUserMaps) {
					BackgroundImage b = this.backgroundImageRepository.getBackgroundImageByIdAndUseable(backgroudUserMap.getBackgroundImageId(), Constant.BUSINESS_STATE_USEABLE);
					if (b != null) {
						b.setLoopTime(loopTime);
						businessId.append(";").append(b.getId());
						list.add(b);
					}
				}
			}
			if (!CollectionUtils.isEmpty(list)) {
				if (businessId.length() > 0) {
					businessId.append(";");
				}
				RedisUtils.saveBootImageAnimationCache(redisTemplate, ystenId, businessId.toString(), customer.getCode(), "","", "",BACKGROUND_IMAGE_KEY + ystenId, DeviceInterfaceType.BackgroundImage);
				return BackgroundImage.toXML(list);
			}
			// 3.用户分组是否绑定背景
			// TODO:动态分组暂时不考虑
			// List<UserGroup> groupList =
			// userGroupService.findDynamicGroupList(customer.getCode(),
			// EnumConstantsInterface.UserGroupType.BACKGROUND.toString(),
			// Constant.BSS_USER_BACKGROUND_IMAGE_MAP);
			List<UserGroup> groupList = new ArrayList<UserGroup>();
			// 3.2动态分组不存在，取设备所在的静态分组
			List<UserGroupMap> userGroupMap = this.userGroupMapRepository.findByUserCode(customer.getCode());
			if (!CollectionUtils.isEmpty(userGroupMap)) {
				groupList = this.userGroupRepository.findUserGroupListByIdsAndType(userGroupMap, EnumConstantsInterface.UserGroupType.BACKGROUND);
			}
			// 3.3根据用户组查询与背景轮换的关系
			if (!CollectionUtils.isEmpty(groupList)) {
				imgUserMaps = this.backgroundImageRepository.findMapByUserGroupId(groupList.get(0).getId());
			}
			if (!CollectionUtils.isEmpty(imgUserMaps)) {
				loopTime = imgUserMaps.get(0).getLoopTime();
				for (BackgroundImageUserMap imgUserMap : imgUserMaps) {
					BackgroundImage b = this.backgroundImageRepository.getBackgroundImageByIdAndUseable(imgUserMap.getBackgroundImageId(), Constant.BUSINESS_STATE_USEABLE);
					if (b != null) {
						b.setLoopTime(loopTime);
						businessId.append(";").append(b.getId());
						list.add(b);
					}
				}
			}
			if (!CollectionUtils.isEmpty(list)) {
				if (businessId.length() > 0) {
					businessId.append(";");
				}
				RedisUtils.saveBootImageAnimationCache(redisTemplate, ystenId, businessId.toString(), "", imgUserMaps.get(0).getUserGroupId() + "","", "",BACKGROUND_IMAGE_KEY + ystenId, DeviceInterfaceType.BackgroundImage);
				return BackgroundImage.toXML(list);
			}
		}

		// 4.设备是否绑定背景
		// 4.1判断设备与背景的map表中是否有记录
		List<BackgroundImageDeviceMap> imgDevMaps = backgroundImageRepository.findMapByYstenId(ystenId);
		if (!CollectionUtils.isEmpty(imgDevMaps)) {
			loopTime = imgDevMaps.get(0).getLoopTime();
			for (BackgroundImageDeviceMap imgDevMap : imgDevMaps) {
				BackgroundImage b = this.backgroundImageRepository.getBackgroundImageByIdAndUseable(imgDevMap.getBackgroundImageId(), Constant.BUSINESS_STATE_USEABLE);
				if (b != null) {
					businessId.append(";").append(b.getId());
					b.setLoopTime(loopTime);
					list.add(b);
				}
			}
		}
		if (!CollectionUtils.isEmpty(list)) {
			if (businessId.length() > 0) {
				businessId.append(";");
			}
			RedisUtils.saveBootImageAnimationCache(redisTemplate, ystenId, businessId.toString(), "", "" , "", "", BACKGROUND_IMAGE_KEY + ystenId,DeviceInterfaceType.BackgroundImage);
			return BackgroundImage.toXML(list);
		}
		// if (CollectionUtils.isEmpty(imgDevMaps)) {
		// 5.设备分组是否绑定背景
		// TODO:内存优化
		List<DeviceGroup> deviceGroupList = this.deviceRepository.findDeviceGroupByYstenId(device.getYstenId(), EnumConstantsInterface.DeviceGroupType.BACKGROUND);
		// deviceGroupService.findDynamicGroupList(device.getYstenId(),
		// EnumConstantsInterface.DeviceGroupType.BACKGROUND.toString(),
		// Constant.BSS_DEVICE_BACKGROUND_IMAGE_MAP);
		// 5.1动态分组是否存在
		// if (CollectionUtils.isEmpty(_groupList)) {
		// // 5.2动态分组不存在，取设备所在的静态分组
		// // TODO:内存优化
		// _groupList =
		// this.deviceRepository.findDeviceGroupByYstenId(device.getYstenId(),
		// EnumConstantsInterface.DeviceGroupType.BACKGROUND);
		// }

		// 5.3根据设备组查询与背景轮换的关系
		if (!CollectionUtils.isEmpty(deviceGroupList)) {
			imgDevMaps = backgroundImageRepository.findMapByDeviceGroupId(deviceGroupList.get(0).getId());
		}
		// }
		if (!CollectionUtils.isEmpty(imgDevMaps)) {
			loopTime = imgDevMaps.get(0).getLoopTime();
			for (BackgroundImageDeviceMap backgroudUserMap : imgDevMaps) {
				BackgroundImage b = this.backgroundImageRepository.getBackgroundImageByIdAndUseable(backgroudUserMap.getBackgroundImageId(), Constant.BUSINESS_STATE_USEABLE);
				if (b != null) {
					b.setLoopTime(loopTime);
					businessId.append(";").append(b.getId());
					list.add(b);
				}
			}
		}
		if (!CollectionUtils.isEmpty(list)) {
			if (businessId.length() > 0) {
				businessId.append(";");
			}
			RedisUtils.saveBootImageAnimationCache(redisTemplate, ystenId, businessId.toString(), "", "" , imgDevMaps.get(0).getDeviceGroupId()+"", "",BACKGROUND_IMAGE_KEY + ystenId, DeviceInterfaceType.BackgroundImage);
			return BackgroundImage.toXML(list);
		}
		List<BackgroundImage> bgs = this.backgroundImageRepository.findDefaultBackgroundImage(isDefault);
		// 加默认类型有效性验证
		if (!CollectionUtils.isEmpty(bgs)) {
			for (BackgroundImage bg : bgs) {
				if (Constant.BUSINESS_STATE_USEABLE.equals(bg.getState())) {
					businessId.append(";").append(bg.getId());
					list.add(bg);
				}
			}
		}
		if (businessId.length() > 0) {
			businessId.append(";");
		}
		RedisUtils.saveBootImageAnimationCache(redisTemplate, ystenId, businessId.toString(), "", "" , "", "",BACKGROUND_IMAGE_KEY + ystenId, DeviceInterfaceType.BackgroundImage);
		return BackgroundImage.toXML(list);
	}

	@Override
	public List<BackgroundImage> getBackgroundImageByDeviceGroupId(Long deviceGroupId) {
		return this.backgroundImageRepository.findBackgroundImageByDeviceGroupId(deviceGroupId);
	}

	@Override
	public BackgroundImage getBackgroundImageById(Long id) {
		return this.backgroundImageRepository.getById(id);
	}

	@Override
	public List<BackgroundImage> getDefaultBackgroundImage(int isDefault) {
		return this.backgroundImageRepository.findDefaultBackgroundImage(isDefault);
	}

	@Override
	public List<BackgroundImage> getBackgroundImageByDeviceGroup(Device device, int isDefault) {
		List<BackgroundImage> list = null;
		DeviceGroup deviceGroup = null;

		if (device != null) {
			// updated by joyce on 2014-6-27 begin
			// List<DeviceGroup> groupList =
			// this.deviceRepository.findDeviceGroupByYstenId(device.getCode(),EnumConstantsInterface.DeviceGroupType.BACKGROUND);
			List<DeviceGroup> groupList = new ArrayList<DeviceGroup>();
			List<DeviceGroup> dynamicGroupList = deviceGroupService.findDynamicGroupList(device.getCode(), EnumConstantsInterface.DeviceGroupType.BACKGROUND.toString(), Constant.BSS_DEVICE_BACKGROUND_IMAGE_MAP);
			if (dynamicGroupList != null && dynamicGroupList.size() > 0) {
				groupList.addAll(dynamicGroupList);
			} else {
				groupList = this.deviceRepository.findDeviceGroupByYstenId(device.getCode(), EnumConstantsInterface.DeviceGroupType.BACKGROUND);
			}
			// updated by joyce on 2014-6-27 end
			if (!CollectionUtils.isEmpty(groupList)) {
				deviceGroup = groupList.get(0);
			}
		}
		if (deviceGroup != null && deviceGroup.getId() != null) {
			list = this.backgroundImageRepository.findBackgroundImageByDeviceGroupIdAndState(deviceGroup.getId(), Constant.BUSINESS_STATE_USEABLE);
		}

		if (CollectionUtils.isEmpty(list)) {
			list = this.backgroundImageRepository.findDefaultBackgroundImage(isDefault);
		}

		return list;
	}
}
