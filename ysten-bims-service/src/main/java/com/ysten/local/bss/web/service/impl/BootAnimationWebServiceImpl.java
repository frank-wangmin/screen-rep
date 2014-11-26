package com.ysten.local.bss.web.service.impl;

import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.device.domain.*;
import com.ysten.local.bss.device.repository.*;
import com.ysten.local.bss.util.FilterSpaceUtils;
import com.ysten.local.bss.util.bean.Constant;
import com.ysten.local.bss.web.service.IBootAnimationWebService;
import com.ysten.utils.page.Pageable;
import com.ysten.utils.string.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BootAnimationWebServiceImpl implements IBootAnimationWebService {

    @Autowired
    private IBootAnimationRepository bootAnimationRepository;
    @Autowired
    private IDeviceRepository deviceRepository;
    @Autowired
    private ICustomerRepository customerRepository;

    @Override
    public BootAnimation getBootAnimationById(Long id) {
        return this.bootAnimationRepository.getBootAnimationById(id);
    }

    @Override
    public String saveUserBootAnimationMap(Long animationId, String areaIds,
                                           String userGroupIds, String userIds) {
        String[] _userIds = new String[0];
        List<Long> areaIdList = StringUtil.splitToLong(areaIds);
        StringBuilder jy = new StringBuilder();
        this.bootAnimationRepository.deleteAnimationMapByAnimationIdAndType(animationId, Constant.BUSINESS_USER_MAP_TYPE_GROUP, Constant.BSS_USER_ANIMATION_MAP);
        if (StringUtils.isNotBlank(userGroupIds)) {
            List<Long> groupIds = StringUtil.splitToLong(userGroupIds);
            if (groupIds != null && groupIds.size() > 0) {
                this.bootAnimationRepository.deleteAnimationMapByGroupIds(groupIds, Constant.BSS_USER_ANIMATION_MAP, Constant.BSS_USER_ANIMATION_MAP_GROUP_ID);
                List<AnimationUserMap> maps = new ArrayList<AnimationUserMap>();
                int index = 0;
                for (Long id : groupIds) {
                    AnimationUserMap map = new AnimationUserMap();
                    map.setCreateDate(new Date());
                    map.setType(Constants.USER_NOTICT_MAP_GROUP);
                    map.setUserGroupId(id);
                    map.setBootAnimationId(animationId);
                    map.setCode(null);
                    maps.add(map);
                    index++;
                    if (index % Constants.BULK_NUM == 0) {
                        this.bootAnimationRepository.bulkSaveUserAnimationMap(maps);
                        maps.clear();
                    }
                }
                if (maps.size() > 0) {
                    this.bootAnimationRepository.bulkSaveUserAnimationMap(maps);
                    maps.clear();
                }
            }
        }
        if (StringUtils.isNotBlank(userIds)) {
            _userIds = userIds.split(",");
            List<String> userIdArray = new ArrayList<String>();
            for (int i = 0; i < _userIds.length; i++) {
                if (StringUtils.isEmpty(_userIds[i])) {
                    continue;
                }
                _userIds[i] = FilterSpaceUtils.replaceBlank(_userIds[i]);
                userIdArray.add(_userIds[i]);
            }

            List<Customer> customerList = this.customerRepository.findCustomersByUserCodes(_userIds);
            if (customerList.size() == 0) {
                jy.append("未能找到你所上传的所有用户编号的用户，请确认!");
                return jy.toString();
            }
            if (customerList.size() > 0 && customerList.size() != userIdArray.size()) {
                for (int i = 0; i < userIdArray.size(); i++) {
                    boolean bool = false;
                    for (Customer customer : customerList) {
                        if (customer.getCode().equals(userIdArray.get(i))) {
                            bool = true;
                            break;
                        }
                    }
                    if (bool == false) {
                        jy.append("未能找到用户编号为：" + userIdArray.get(i) + "的用户，请确认!" + "\n");
                    }
                }
            }
            List<String> userIdList = new ArrayList<String>();
            for (Customer customer : customerList) {
                boolean bool = false;
                for (Long areaId : areaIdList) {
                    if (areaId.equals(customer.getArea().getId())) {
                        bool = true;
                        userIdList.add(customer.getCode());
                        break;
                    }
                }
                if (bool == false) {
                    jy.append("请确定用户编号为：" + customer.getCode() + "的所在区域!" + "\n");
                }
            }
            if (userIdList != null && userIdList.size() > 0) {
//                this.bootAnimationRepository.deleteAnimationMapByAnimationIdAndType(animationId, Constant.BUSINESS_USER_MAP_TYPE_USER, Constant.BSS_USER_ANIMATION_MAP);
                List<AnimationUserMap> maps = new ArrayList<AnimationUserMap>();
                this.bootAnimationRepository.deleteAnimationDeviceMapByYstenIds(userIdList, Constant.BSS_USER_ANIMATION_MAP, Constant.BSS_USER_ANIMATION_MAP_USER_ID);
                int index = 0;
                for (String userId : userIdList) {
                    AnimationUserMap map = new AnimationUserMap();
                    map.setCreateDate(new Date());
                    map.setType(Constants.USER_NOTICT_MAP_USER);
                    map.setUserGroupId(null);
                    map.setBootAnimationId(animationId);
                    map.setCode(userId);
                    maps.add(map);
                    index++;
                    if (index % Constants.BULK_NUM == 0) {
                        this.bootAnimationRepository.bulkSaveUserAnimationMap(maps);
                        maps.clear();
                    }
                }
                if (maps.size() > 0) {
                    this.bootAnimationRepository.bulkSaveUserAnimationMap(maps);
                    maps.clear();
                }
            }
        }

        if (StringUtils.isNotBlank(jy.toString())) {
            return jy.toString();
        }

        return null;
    }

    @Override
    public String saveBootAnimationMap(Long animationId, String areaIds, String deviceGroupIds, String ystenIds,
                                       String userGroupIds, String userIds) {
        String[] _ystenIds = new String[0];
        List<Long> areaIdList = StringUtil.splitToLong(areaIds);
        StringBuilder jy = new StringBuilder();

        this.bootAnimationRepository.deleteAnimationMapByAnimationIdAndType(animationId, Constant.BUSINESS_DEVICE_MAP_TYPE_GROUP, Constant.BSS_DEVICE_ANIMATION_MAP);
        if (StringUtils.isNotBlank(deviceGroupIds)) {
            List<Long> groupIds = StringUtil.splitToLong(deviceGroupIds);
            if (groupIds != null && groupIds.size() > 0) {
                this.bootAnimationRepository.deleteAnimationMapByGroupIds(groupIds, Constant.BSS_DEVICE_ANIMATION_MAP, Constant.BSS_DEVICE_ANIMATION_MAP_GROUP_ID);
//                this.bootAnimationRepository.deleteGroupMapByGroupIds(groupIds, Constant.BSS_DEVICE_ANIMATION_MAP, Constant.BSS_DEVICE_ANIMATION_MAP_GROUP_ID);
                List<AnimationDeviceMap> maps = new ArrayList<AnimationDeviceMap>();
                int index = 0;
                for (Long id : groupIds) {
                    AnimationDeviceMap map = new AnimationDeviceMap();
                    map.setCreateDate(new Date());
                    map.setType(Constants.DEVICE_NOTICT_MAP_GROUP);
                    map.setDeviceGroupId(id);
                    map.setBootAnimationId(animationId);
                    map.setYstenId(null);
                    maps.add(map);
                    index++;
                    if (index % Constants.BULK_NUM == 0) {
                        this.bootAnimationRepository.bulkSaveAnimationMap(maps);
                        maps.clear();
                    }
                }
                if (maps.size() > 0) {
                    this.bootAnimationRepository.bulkSaveAnimationMap(maps);
                    maps.clear();
                }
            }
        }
        if (StringUtils.isNotBlank(ystenIds)) {
            _ystenIds = ystenIds.split(",");
            List<String> ystenIdList = new ArrayList<String>();
            for (int i = 0; i < _ystenIds.length; i++) {
                if (StringUtils.isEmpty(_ystenIds[i])) {
                    continue;
                }
                _ystenIds[i] = FilterSpaceUtils.replaceBlank(_ystenIds[i]);
                ystenIdList.add(_ystenIds[i]);
            }

            List<Device> deviceList = this.deviceRepository.findDeviceListByYstenIds(_ystenIds);
            if (deviceList.size() == 0) {
                jy.append("未能找到你所上传的所有易视腾编号的设备，请确认!");
                return jy.toString();
            }
            if (deviceList.size() > 0 && deviceList.size() != ystenIdList.size()) {
                for (int i = 0; i < ystenIdList.size(); i++) {
                    boolean bool = false;
                    for (Device device : deviceList) {
                        if (device.getYstenId().equals(ystenIdList.get(i))) {
                            bool = true;
                            break;
                        }
                    }
                    if (bool == false) {
                        jy.append("未能找到易视腾编号为：" + ystenIdList.get(i) + "的设备，请确认!" + "\n");
                    }
                }
            }
            List<String> ystIds = new ArrayList<String>();
            for (Device device : deviceList) {
                boolean bool = false;
                for (Long areaId : areaIdList) {
                    if (areaId.equals(device.getArea().getId())) {
                        bool = true;
                        ystIds.add(device.getYstenId());
                        break;
                    }
                }
                if (bool == false) {
                    jy.append("请确定易视腾编号为：" + device.getYstenId() + "的所在区域!" + "\n");
                }
            }
            if (ystIds != null && ystIds.size() > 0) {
//                this.bootAnimationRepository.deleteAnimationMapByAnimationIdAndType(animationId, Constant.BUSINESS_DEVICE_MAP_TYPE_DEVICE, Constant.BSS_DEVICE_ANIMATION_MAP);
                List<AnimationDeviceMap> maps = new ArrayList<AnimationDeviceMap>();
                this.bootAnimationRepository.deleteAnimationDeviceMapByYstenIds(ystIds, Constant.BSS_DEVICE_ANIMATION_MAP, Constant.BSS_DEVICE_ANIMATION_MAP_YSTEN_ID);
                int index = 0;
                for (String y : ystIds) {
                    AnimationDeviceMap map = new AnimationDeviceMap();
                    map.setCreateDate(new Date());
                    map.setType(Constants.DEVICE_NOTICT_MAP_DEVICE);
                    map.setDeviceGroupId(null);
                    map.setBootAnimationId(animationId);
                    map.setYstenId(y);
                    maps.add(map);
                    index++;
                    if (index % Constants.BULK_NUM == 0) {
                        this.bootAnimationRepository.bulkSaveAnimationMap(maps);
                        maps.clear();
                    }
                }
                if (maps.size() > 0) {
                    this.bootAnimationRepository.bulkSaveAnimationMap(maps);
                    maps.clear();
                }
            }
        }

        if (StringUtils.isNotBlank(jy.toString())) {
            return jy.toString();
        }

        return null;
    }

    @Override
    public String saveBootAnimation(BootAnimation bootAnimation) {

        // check if the only one default record is existed
        if (bootAnimation.getIsDefault() == 1) {
            if (bootAnimationRepository.getBootAnimationByDefault() != null) {
                return Constants.IS_DEFAULT;
            }
        }
        this.bootAnimationRepository.saveBootAnimation(bootAnimation);
        return null;

    }

    @Override
    public String saveBootAnimation(BootAnimation bootAnimation, String deviceGroupIds, String deviceCodes,
                                    String userGroupIds, String userIds) {
        if (StringUtils.isNotBlank(deviceCodes)) {
            String[] _deviceCodes = deviceCodes.split(",");
            for (String deviceCode : _deviceCodes) {
                Device device = this.deviceRepository.getDeviceByCode(deviceCode);
                if (device == null) {
                    return "未能找到设备code为" + deviceCode + "的设备，请确认!";
                }
            }
        }
        if (StringUtils.isNotBlank(userIds)) {
            String[] _userIds = userIds.split(",");
            for (String userId : _userIds) {
                Customer user = this.customerRepository.getCustomerByUserId(userId);
                if (user == null) {
                    return "未能找到用户外部编码为" + userId + "的用户，请确认!";
                }
            }
        }
        bootAnimation.setCreateDate(new Date());
        this.bootAnimationRepository.saveBootAnimation(bootAnimation);
        if (StringUtils.isNotBlank(deviceGroupIds)) {
            List<Long> groupIds = StringUtil.splitToLong(deviceGroupIds);
            if (groupIds != null && groupIds.size() > 0) {
                for (Long id : groupIds) {
                    this.bootAnimationRepository.deleteAnimationDeviceMapByGroupId(id);
                    this.saveAnimationDeviceMap(bootAnimation, "GROUP", id, null);
                }
            }
        }
        if (StringUtils.isNotBlank(deviceCodes)) {
            String[] deviceCode = deviceCodes.split(",");
            if (deviceCode.length > 0) {
                for (int i = 0; i < deviceCode.length; i++) {
                    this.bootAnimationRepository.deleteAnimationDeviceMapByCode(deviceCode[i]);
                    this.saveAnimationDeviceMap(bootAnimation, "DEVICE", null, deviceCode[i]);
                }
            }
        }
        if (StringUtils.isNotBlank(userGroupIds)) {
            List<Long> groupIds = StringUtil.splitToLong(userGroupIds);
            if (groupIds != null && groupIds.size() > 0) {
                for (Long id : groupIds) {
                    this.bootAnimationRepository.deleteAnimationUserMapByUserGroupId(id);
                    this.saveAnimationUserMap(bootAnimation, "GROUP", id, null);
                }
            }
        }
        if (StringUtils.isNotBlank(userIds)) {
            String[] userId = userIds.split(",");
            if (userId.length > 0) {
                for (int i = 0; i < userId.length; i++) {
                    this.bootAnimationRepository.deleteAnimationUserMapByCode(userId[i]);
                    this.saveAnimationUserMap(bootAnimation, "USER", null, userId[i]);
                }
            }
        }
        return null;
    }

    private boolean saveAnimationDeviceMap(BootAnimation bootAnimation, String type, Long groupId, String deviceCode) {
        AnimationDeviceMap map = new AnimationDeviceMap();
        map.setCreateDate(new Date());
        map.setType(type);
        map.setDeviceGroupId(groupId);
        map.setBootAnimationId(bootAnimation.getId());
        map.setYstenId(deviceCode);
        return this.bootAnimationRepository.saveAnimationDeviceMap(map);
    }

    private boolean saveDeviceMap(Long animationId, String type, Long groupId, String deviceCode) {
        AnimationDeviceMap map = new AnimationDeviceMap();
        map.setCreateDate(new Date());
        map.setType(type);
        map.setDeviceGroupId(groupId);
        map.setBootAnimationId(animationId);
        map.setYstenId(deviceCode);
        return this.bootAnimationRepository.saveAnimationDeviceMap(map);
    }

    @Override
    public String updateBootAnimation(BootAnimation bootAnimation) {
        // check if the only one default record is existed
        if (bootAnimation.getIsDefault() == 1) {
            BootAnimation bootAnimationDefault = bootAnimationRepository.getBootAnimationByDefault();
            if (bootAnimationDefault != null) {
                if (bootAnimationDefault.getId() != bootAnimation.getId()) {
                    return Constants.IS_DEFAULT;
                }
            }
        }
        boolean bool = this.bootAnimationRepository.updateBootAnimation(bootAnimation);
        return bool == true ? null : "修改异常！";
    }

    @Override
    public String updateBootAnimation(BootAnimation bootAnimation, String deviceGroupIds, String deviceCodes,
                                      String userGroupIds, String userIds) {
        if (StringUtils.isNotBlank(deviceCodes)) {
            String[] _deviceCodes = deviceCodes.split(",");
            for (String deviceCode : _deviceCodes) {
                Device device = this.deviceRepository.getDeviceByCode(deviceCode);
                if (device == null) {
                    return "未能找到设备code为" + deviceCode + "的设备，请确认!";
                }
            }
        }
        if (StringUtils.isNotBlank(userIds)) {
            String[] _userIds = userIds.split(",");
            for (String userId : _userIds) {
                Customer user = this.customerRepository.getCustomerByUserId(userId);
                if (user == null) {
                    return "未能找到用户外部编码为" + userId + "的用户，请确认!";
                }
            }
        }
        BootAnimation bootAnimationUpdate = this.getBootAnimationById(bootAnimation.getId());
        bootAnimationUpdate.setName(bootAnimation.getName());
        bootAnimationUpdate.setMd5(bootAnimation.getMd5());
        bootAnimationUpdate.setUrl(bootAnimation.getUrl());
        bootAnimationUpdate.setState(bootAnimation.getState());
        bootAnimationUpdate.setIsDefault(bootAnimation.getIsDefault());
        this.bootAnimationRepository.updateBootAnimation(bootAnimation);
//        this.bootAnimationRepository.deleteAnimationDeviceMapByAnimationIdAndType(bootAnimation.getId(), "GROUP");
        if (StringUtils.isNotBlank(deviceGroupIds)) {
            List<Long> groupIds = StringUtil.splitToLong(deviceGroupIds);
            if (groupIds != null && groupIds.size() > 0) {
                for (Long id : groupIds) {
                    this.bootAnimationRepository.deleteAnimationDeviceMapByGroupId(id);
                    this.saveAnimationDeviceMap(bootAnimation, "GROUP", id, null);
                }
            }
        }
//        this.bootAnimationRepository.deleteAnimationDeviceMapByAnimationIdAndType(bootAnimation.getId(), "DEVICE");
        if (StringUtils.isNotBlank(deviceCodes)) {
            String[] deviceCode = deviceCodes.split(",");
            if (deviceCode.length > 0) {
                for (int i = 0; i < deviceCode.length; i++) {
                    this.bootAnimationRepository.deleteAnimationDeviceMapByCode(deviceCode[i]);
                    this.saveAnimationDeviceMap(bootAnimation, "DEVICE", null, deviceCode[i]);
                }
            }
        }
//        this.bootAnimationRepository.deleteAnimationUserMapByAnimationIdAndType(bootAnimation.getId(), "GROUP");
        if (StringUtils.isNotBlank(userGroupIds)) {
            List<Long> groupIds = StringUtil.splitToLong(userGroupIds);
            if (groupIds != null && groupIds.size() > 0) {
                for (Long id : groupIds) {
                    this.bootAnimationRepository.deleteAnimationUserMapByUserGroupId(id);
                    this.saveAnimationUserMap(bootAnimation, "GROUP", id, null);
                }
            }
        }
//        this.bootAnimationRepository.deleteAnimationUserMapByAnimationIdAndType(bootAnimation.getId(), "USER");
        if (StringUtils.isNotBlank(userIds)) {
            String[] userId = userIds.split(",");
            if (userId.length > 0) {
                for (int i = 0; i < userId.length; i++) {
                    this.bootAnimationRepository.deleteAnimationUserMapByCode(userId[i]);
                    this.saveAnimationUserMap(bootAnimation, "USER", null, userId[i]);
                }
            }
        }
        return null;
    }

    @Override
    public boolean deleteBootAnimationaMap(List<Long> ids, String isUser) {
        if (StringUtils.isNotBlank(isUser)) {
            this.bootAnimationRepository.deleteUserMapByAnimationId(ids);
        } else {
            this.bootAnimationRepository.deleteDeviceMapByAnimationId(ids);
        }
        return true;
    }

    @Override
    public String deleteBootAnimation(List<Long> ids) {
        for (Long bootAnimationId : ids) {
            // this.bootAnimationRepository.deleteUserMapByAnimationId(ids);
            // this.bootAnimationRepository.deleteDeviceMapByAnimationId(ids);
            BootAnimation boot = this.bootAnimationRepository.getBootAnimationById(bootAnimationId);
            List<AnimationDeviceMap> deviceMap = this.bootAnimationRepository
                    .findAnimationDeviceMapByAnimationIdAndType(bootAnimationId, null);
            List<AnimationUserMap> userMap = this.bootAnimationRepository.findAnimationUserMapByAnimationIdAndType(
                    bootAnimationId, null);
            if (deviceMap != null && deviceMap.size() > 0) {
                return "开机动画:" + boot.getName() + "绑定了设备或设备分组，不可删除，请先解绑！";
            }
            if (userMap != null && userMap.size() > 0) {
                return "开机动画:" + boot.getName() + "绑定了用户或用户分组，不可删除，请先解绑！";
            }
            this.bootAnimationRepository.deleteBootAnimationById(bootAnimationId);
        }
        return null;
    }

    @Override
    public Pageable<BootAnimation> findBootAnimationList(String name, Integer pageNo, Integer pageSize) {
        return this.bootAnimationRepository.findBootAnimation(FilterSpaceUtils.filterStartEndSpace(name), pageNo, pageSize);
    }

    @Override
    public List<BootAnimation> findAllBootAnimationList() {
        return this.bootAnimationRepository.findAllBootAnimation();
    }

    @Override
    public List<AnimationDeviceMap> findAnimationDeviceMapByAnimationIdAndType(Long bootAnimationId, String type) {
        return this.bootAnimationRepository.findAnimationDeviceMapByAnimationIdAndType(bootAnimationId, type);
    }

    @Override
    public boolean saveUserBootAnimation(BootAnimation bootAnimation, String userGroupIds, String userIds) {
        boolean bool = this.bootAnimationRepository.saveBootAnimation(bootAnimation);
        if (StringUtils.isNotBlank(userGroupIds)) {
            List<Long> groupIds = StringUtil.splitToLong(userGroupIds);
            if (groupIds != null && groupIds.size() > 0) {
                for (Long id : groupIds) {
                    this.saveAnimationUserMap(bootAnimation, "GROUP", id, null);
                }
            }
        }
        if (StringUtils.isNotBlank(userIds)) {
            String[] userId = userIds.split(",");
            if (userId.length > 0) {
                for (int i = 0; i < userId.length; i++) {
                    this.saveAnimationUserMap(bootAnimation, "USER", null, userId[i]);
                }
            }
        }
        return bool;
    }

    private boolean saveAnimationUserMap(BootAnimation bootAnimation, String type, Long groupId, String userId) {
        AnimationUserMap map = new AnimationUserMap();
        map.setCreateDate(new Date());
        map.setType(type);
        map.setUserGroupId(groupId);
        map.setBootAnimationId(bootAnimation.getId());
        map.setCode(userId);
        return this.bootAnimationRepository.saveAnimationUserMap(map);
    }

    private boolean saveUserMap(Long animationId, String type, Long groupId, String userId) {
        AnimationUserMap map = new AnimationUserMap();
        map.setCreateDate(new Date());
        map.setType(type);
        map.setUserGroupId(groupId);
        map.setBootAnimationId(animationId);
        map.setCode(userId);
        return this.bootAnimationRepository.saveAnimationUserMap(map);
    }

    @Override
    public List<AnimationUserMap> findAnimationUserMapByAnimationIdAndType(Long bootAnimationId, String type) {
        return this.bootAnimationRepository.findAnimationUserMapByAnimationIdAndType(bootAnimationId, type);
    }

    @Override
    public boolean updateUserBootAnimation(BootAnimation bootAnimation, String userGroupIds, String userIds) {
        BootAnimation bootAnimationUpdate = this.getBootAnimationById(bootAnimation.getId());
        bootAnimationUpdate.setName(bootAnimation.getName());
        bootAnimationUpdate.setMd5(bootAnimation.getMd5());
        bootAnimationUpdate.setUrl(bootAnimation.getUrl());
        bootAnimationUpdate.setState(bootAnimation.getState());
        bootAnimationUpdate.setIsDefault(bootAnimation.getIsDefault());
//        this.bootAnimationRepository.deleteAnimationUserMapByAnimationIdAndType(bootAnimation.getId(), "GROUP");
        if (StringUtils.isNotBlank(userGroupIds)) {
            List<Long> groupIds = StringUtil.splitToLong(userGroupIds);
            if (groupIds != null && groupIds.size() > 0) {
                for (Long id : groupIds) {
                    this.saveAnimationUserMap(bootAnimation, "GROUP", id, null);
                }
            }
        }
//        this.bootAnimationRepository.deleteAnimationUserMapByAnimationIdAndType(bootAnimation.getId(), "USER");
        if (StringUtils.isNotBlank(userIds)) {
            String[] userId = userIds.split(",");
            if (userId.length > 0) {
                for (int i = 0; i < userId.length; i++) {
                    this.saveAnimationUserMap(bootAnimation, "USER", null, userId[i]);
                }
            }
        }
        return this.bootAnimationRepository.updateBootAnimation(bootAnimation);
    }

    @Override
    public BootAnimation findBootAnimationByName(String name) {
        return this.bootAnimationRepository.findBootAnimationByName(name);
    }

    @Override
    public BootAnimation findDefaultBootAnimation(int isDefault, String state) {
        return this.bootAnimationRepository.findDefaultBootAnimation(isDefault, state);
    }

    @Override
    public String checkAreaDevices(String areaIds, String[] ystenIds) {
//		    boolean bool = false;
        //int sum = 0;
        StringBuilder sb = new StringBuilder("");
        List<Long> areaIdList = StringUtil.splitToLong(areaIds);
//            String[] ystenIdList = ystenIds.split(",");
        for (int i = 0; i < ystenIds.length; i++) {
            if (StringUtils.isEmpty(ystenIds[i])) {
                continue;
            }
            Device device = this.deviceRepository.getDeviceByYstenId(ystenIds[i]);
            int m = 0;
            boolean bool = false;
            for (Long areaId : areaIdList) {
                if (areaId.equals(device.getArea().getId())) {
                    bool = true;
                    //sum++;
                    continue;
                }
                m++;
                if (m == areaIdList.size() && bool == false) {
                    sb.append("请确定易视腾编号为：" + ystenIds[i] + "的所在区域!" + "\n");
                }
            }
        }
        if (StringUtils.isNotBlank(sb.toString())) {
            return sb.toString();
        }
        return null;
    }
}