package com.ysten.local.bss.device.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.ysten.cache.annotation.KeyParam;
import com.ysten.cache.annotation.MethodCache;
import com.ysten.local.bss.device.domain.AnimationDeviceMap;
import com.ysten.local.bss.device.domain.AnimationUserMap;
import com.ysten.local.bss.device.domain.BootAnimation;
import com.ysten.local.bss.device.domain.Customer;
import com.ysten.local.bss.device.domain.Device;
import com.ysten.local.bss.device.domain.DeviceGroup;
import com.ysten.local.bss.device.domain.UserGroup;
import com.ysten.local.bss.device.domain.UserGroupMap;
import com.ysten.local.bss.device.exception.CustomerNotFoundException;
import com.ysten.local.bss.device.exception.DeviceNotFoundException;
import com.ysten.local.bss.device.repository.IBootAnimationRepository;
import com.ysten.local.bss.device.repository.IDeviceRepository;
import com.ysten.local.bss.device.repository.IUserGroupMapRepository;
import com.ysten.local.bss.device.repository.IUserGroupRepository;
import com.ysten.local.bss.device.service.IBootAnimationService;
import com.ysten.local.bss.device.service.ICustomerService;
import com.ysten.local.bss.device.service.IDeviceService;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import com.ysten.local.bss.device.utils.SerializeUtil;
import com.ysten.local.bss.util.bean.Constant;
import com.ysten.local.bss.util.bean.RedisUtils;
import com.ysten.local.bss.util.bean.RedisUtils.DeviceInterfaceType;

@Service
public class BootAnimationServiceImpl implements IBootAnimationService {
	private static final int isDefault = 1; // 默认开机动画
	@Autowired
	private IBootAnimationRepository bootAnimationRepository;

	@Autowired
	private IDeviceService deviceService;

	@Autowired
	private ICustomerService customerService;

	@Autowired
	private IUserGroupRepository userGroupRepository;

	@Autowired
	private IUserGroupMapRepository userGroupMapRepository;
	@Autowired
	private IDeviceRepository deviceRepository;

	private static final String BOOT_ANIMATION_KEY = "animation:interface:ystenId:";
	@Autowired
	private RedisTemplate<Serializable, Serializable> redisTemplate;

	@Override
	public AnimationDeviceMap getMapByDeviceCode(String deviceCode) {
		return bootAnimationRepository.findMapByYstenId(deviceCode, "DEVICE");
	}

	@Override
	public AnimationDeviceMap getMapByDeviceGroupId(Long deviceGroupId) {

		return bootAnimationRepository.findMapByDeviceGroupId(deviceGroupId,
				Constant.BUSINESS_DEVICE_MAP_TYPE_GROUP);
	}

	@Override
	@MethodCache(key = BOOT_ANIMATION_KEY + "#{ystenId}")
	public String getBootAnimationByYstenId(@KeyParam("ystenId") String ystenId)
			throws DeviceNotFoundException, CustomerNotFoundException {

		// 1.查询设备是否合法
		Device device = this.deviceService.getDeviceByYstenId(ystenId);
		if (device == null) {
			throw new DeviceNotFoundException("can not find device info.");
		}
		// TODO:改成中心管理、暂时注释掉用户验证;用户、用户组绑定业务的判断。
		Customer customer = customerService.getCustomerByYstenId(ystenId);
		// if (customer == null) {
		// throw new CustomerNotFoundException("can not find customer info ");
		// }

		// 2.用户是否绑定背景
		// 2.1判断用户与背景的map表中是否有记录
		BootAnimation bootAnimation = null;
		if (customer != null) {
			AnimationUserMap animationUserMap = bootAnimationRepository.findMapByUserCode(customer.getCode(), Constant.BUSINESS_USER_MAP_TYPE_USER);
			if (animationUserMap != null) {
				bootAnimation = this.bootAnimationRepository.findBootAnimationById(animationUserMap.getBootAnimationId(), Constant.BUSINESS_STATE_USEABLE);
			}
			if (bootAnimation != null) {
				RedisUtils.saveBootImageAnimationCache(redisTemplate, ystenId, bootAnimation.getId() + "", customer.getCode(), "", "", "", BOOT_ANIMATION_KEY + ystenId,DeviceInterfaceType.Animation);
				return BootAnimation.toXML(bootAnimation);
			} else {
				Object[] objAttr = this.getBootAnimationByUserGroupId(customer.getCode());
				if (objAttr != null) {
					AnimationUserMap animationUserGroupMap = (AnimationUserMap) objAttr[0];
					bootAnimation = (BootAnimation) objAttr[1];
					if (bootAnimation != null) {
						RedisUtils.saveBootImageAnimationCache(redisTemplate, ystenId, bootAnimation.getId() + "", "",animationUserGroupMap.getUserGroupId() + "", "", "", BOOT_ANIMATION_KEY + ystenId,DeviceInterfaceType.Animation);
						return BootAnimation.toXML(bootAnimation);
					}
				}
			}
		}

		if (bootAnimation == null) {
			// 4.设备是否绑定背景
			// 4.1判断设备与背景的map表中是否有记录
			AnimationDeviceMap animationDeviceMap = bootAnimationRepository.findMapByYstenId(ystenId, Constant.BUSINESS_DEVICE_MAP_TYPE_DEVICE);
			if (animationDeviceMap != null) {
				bootAnimation = this.bootAnimationRepository.findBootAnimationById(animationDeviceMap.getBootAnimationId(), Constant.BUSINESS_STATE_USEABLE);
			}
			if (bootAnimation != null) {
				RedisUtils.saveBootImageAnimationCache(redisTemplate, ystenId, bootAnimation.getId() + "", "","", "", "", BOOT_ANIMATION_KEY + ystenId,DeviceInterfaceType.Animation);
				return BootAnimation.toXML(bootAnimation);
			} else {
				// 5.设备分组是否绑定背景
				Object[] objAtt = this.getBootAnimationByDeviceGroup(device);
				if (objAtt != null) {
					AnimationDeviceMap animationDeviceGroupMap = (AnimationDeviceMap) objAtt[0];
					bootAnimation = (BootAnimation) objAtt[1];
					if (bootAnimation != null) {
						RedisUtils.saveBootImageAnimationCache(redisTemplate, ystenId, bootAnimation.getId() + "", "","", animationDeviceGroupMap.getDeviceGroupId() + "", "",BOOT_ANIMATION_KEY + ystenId, DeviceInterfaceType.Animation);
						return BootAnimation.toXML(bootAnimation);
					}
				}
			}
		}
		// 6.查找动画为默认类型的
		bootAnimation = this.getDefaultBootAnimation(isDefault);
		if (bootAnimation != null) {
			RedisUtils.saveBootImageAnimationCache(redisTemplate, ystenId, bootAnimation.getId() + "", "", "", "","",BOOT_ANIMATION_KEY + ystenId, DeviceInterfaceType.Animation);
			return BootAnimation.toXML(bootAnimation);
		}
		if (bootAnimation == null) {
			RedisUtils.saveBootImageAnimationCache(redisTemplate, ystenId, "", "", "", "","",BOOT_ANIMATION_KEY + ystenId, DeviceInterfaceType.Animation);
		}
		return "";
	}

	@Override
	public BootAnimation getBootAnimationByDeviceGroupId(Long deviceGroupId) {
		return bootAnimationRepository.findBootAnimationByDeviceGroupId(
				deviceGroupId, Constant.BUSINESS_DEVICE_MAP_TYPE_GROUP);
	}

	@Override
	public BootAnimation getBootAnimationById(Long id) {
		return bootAnimationRepository.findBootAnimationById(id, "USEABLE");
	}

	@Override
	public BootAnimation getDefaultBootAnimation(int isDefault) {
		return bootAnimationRepository.findDefaultBootAnimation(isDefault, "USEABLE");
	}

	/**
	 * 查询用户分组是否绑定业务
	 * 
	 * @param userId
	 * @return
	 */
	public Object[] getBootAnimationByUserGroupId(String code) {
		// 3.用户分组是否绑定背景
		// 3.1动态分组是否存在
		// TODO:实现优化，暂不考虑动态分组
		// List<UserGroup> groupList =
		// userGroupService.findDynamicGroupList(code,EnumConstantsInterface.UserGroupType.ANIMATION.toString(),Constant.BSS_USER_ANIMATION_MAP);
		List<UserGroup> groupList = new ArrayList<UserGroup>();
		if (CollectionUtils.isEmpty(groupList)) {
			// 3.2动态分组不存在，取设备所在的静态分组
			// 判断用户与用户分组的map表中，是否有记录
			List<UserGroupMap> userGroupMap = this.userGroupMapRepository.findByUserCode(code);
			if (!CollectionUtils.isEmpty(userGroupMap)) {
				groupList = this.userGroupRepository.findUserGroupListByIdsAndType(userGroupMap, EnumConstantsInterface.UserGroupType.ANIMATION);
			}
		}
		// 3.3根据用户组查询与动画的关系
		if (!CollectionUtils.isEmpty(groupList)) {
			AnimationUserMap animationUserMap = bootAnimationRepository.findMapByUserGroupId(groupList.get(0).getId(), Constant.BUSINESS_USER_MAP_TYPE_GROUP);
			if (animationUserMap != null) {
				BootAnimation bootAnimation = this.bootAnimationRepository.findBootAnimationById(animationUserMap.getBootAnimationId(), Constant.BUSINESS_STATE_USEABLE);
				if (bootAnimation != null) {
					Object[] objAttr = new Object[2];
					objAttr[0] = animationUserMap;
					objAttr[1] = bootAnimation;
					return objAttr;
				}
			}
		}
		return null;
	}

	@Override
	public Object[] getBootAnimationByDeviceGroup(Device device) {
		// TODO:内存优化
		List<DeviceGroup> groupList = this.deviceRepository.findDeviceGroupByYstenId(device.getYstenId(), EnumConstantsInterface.DeviceGroupType.ANIMATION);
		// deviceGroupService.findDynamicGroupList(device.getYstenId() + "",
		// EnumConstantsInterface.DeviceGroupType.ANIMATION.toString(),
		// Constant.BSS_DEVICE_ANIMATION_MAP);
		// 5.1动态分组是否存在
		// if (CollectionUtils.isEmpty(groupList)) {
		// // 5.2动态分组不存在，取设备所在的静态分组
		// // TODO:内存优化
		// groupList =
		// this.deviceRepository.findDeviceGroupByYstenId(device.getCode(),
		// EnumConstantsInterface.DeviceGroupType.ANIMATION);
		// }
		// 5.3根据设备组查询与动画的关系
		if (!CollectionUtils.isEmpty(groupList)) {
			AnimationDeviceMap animationDeviceMap = bootAnimationRepository.findMapByDeviceGroupId(groupList.get(0).getId(), Constant.BUSINESS_DEVICE_MAP_TYPE_GROUP);
			if (animationDeviceMap != null) {
				BootAnimation bootAnimation = this.bootAnimationRepository.findBootAnimationById(animationDeviceMap.getBootAnimationId(), Constant.BUSINESS_STATE_USEABLE);
				if (bootAnimation != null) {
					Object[] objAttr = new Object[2];
					objAttr[0] = animationDeviceMap;
					objAttr[1] = bootAnimation;
					return objAttr;
				}
			}
		}
		return null;
	}

	public String getCache(final String type, final String templateId,
			final String ids) {
		try {
			return redisTemplate.execute(new RedisCallback<String>() {

				@Override
				public String doInRedis(RedisConnection connection)
						throws DataAccessException {
					byte[] key = null;
					if (StringUtils.isBlank(ids)) {
						key = redisTemplate.getStringSerializer().serialize(
								type + ":" + templateId);
					} else {
						key = redisTemplate.getStringSerializer().serialize(
								type + ":" + templateId + ":" + ids);
					}
					if (connection.exists(key)) {
						byte[] value = connection.get(key);
						String ss = (String) SerializeUtil.unserialize(value);
						return ss;
					}
					return null;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
