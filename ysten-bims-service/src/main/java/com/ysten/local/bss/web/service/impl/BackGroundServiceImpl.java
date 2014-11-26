package com.ysten.local.bss.web.service.impl;

import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.device.domain.*;
import com.ysten.local.bss.device.repository.*;
import com.ysten.local.bss.util.FilterSpaceUtils;
import com.ysten.local.bss.util.bean.Constant;
import com.ysten.local.bss.web.service.IBackGroundService;
import com.ysten.utils.page.Pageable;
import com.ysten.utils.string.StringUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BackGroundServiceImpl implements IBackGroundService {
    @Autowired
    private IBackgroundImageRepository backgroundImageRepository;
    @Autowired
    private IDeviceRepository deviceRepository;
    @Autowired
    private ICustomerRepository customerRepository;

    @Override
    public Pageable<BackgroundImage> findBackGroundList(String name, Integer pageNo, Integer pageSize) {
        return this.backgroundImageRepository.findBackGroundList(FilterSpaceUtils.filterStartEndSpace(name), pageNo, pageSize);
    }


    @Override
    public String saveUserBackGroundImageMap(Long backgroudImageId, String areaIds,
                                         String userGroupIds, String userIds,
                                         Integer userGroupLoopTime, Integer userLoopTime) {
        String[] _userIds = new String[0];
        BackgroundImage backgroundImage = new BackgroundImage();
        backgroundImage.setId(backgroudImageId);
        backgroundImage.setUserGroupIds(userGroupIds);
        backgroundImage.setUserIds(userIds);
        backgroundImage.setUserGroupLoopTime(userGroupLoopTime);
        backgroundImage.setUserLoopTime(userLoopTime);
        StringBuilder jy = new StringBuilder();
        List<Long> areaIdList = StringUtil.splitToLong(areaIds);
        this.bulkSaveBackGroundImageUGMap(backgroundImage);

        if (StringUtils.isNotBlank(backgroundImage.getUserIds())) {
           /* if (backgroundImage.getDeviceLoopTime() == null || backgroundImage.getDeviceLoopTime() == 0) {
                jy.append("设备循环时间不能为空!" + "\n");
            }*/
            _userIds = backgroundImage.getUserIds().split(",");
            List<String> userIdlList = new ArrayList<String>();
            for (int i = 0; i < _userIds.length; i++) {
                if (StringUtils.isEmpty(_userIds[i])) {
                    continue;
                }
                _userIds[i] = FilterSpaceUtils.replaceBlank(_userIds[i]);
                userIdlList.add(_userIds[i]);
            }
            List<Customer> userList = this.customerRepository.findCustomersByUserCodes(_userIds);
            if (userList.size() == 0) {
                jy.append("未能找到你所上传的所有用户编号的用户，请确认!");
                return jy.toString();
            }
            if (userList.size() > 0 && userList.size() != userIdlList.size()) {
                for (int i = 0; i < userIdlList.size(); i++) {
                    boolean bool = false;
                    for (Customer customer : userList) {
                        if (customer.getCode().equals(userIdlList.get(i))) {
                            bool = true;
                            break;
                        }
                    }
                    if (bool == false) {
                        jy.append("未能找到用户编号为：" + userIdlList.get(i) + "的用户，请确认!" + "\n");
                    }
                }
            }
            String[] userIdArray = new String[userList.size()];
            int n = 0;
            for (Customer customer : userList) {
                boolean bool = false;
                for (Long areaId : areaIdList) {
                    if (areaId.equals(customer.getArea().getId())) {
                        bool = true;
                        userIdArray[n] = customer.getCode();
                        n++;
                        break;
                    }
                }
                if (bool == false) {
                    jy.append("请确定用户编号为：" + customer.getCode() + "的所在区域!" + "\n");
                }
            }
            if (userIdArray != null) {
                backgroundImage.setUserIds(StringUtils.join(userIdArray, ","));
            }

        }
        String res = this.bulkSaveBackGroundImageUserMap(backgroundImage);
        if (StringUtils.isNotBlank(jy.toString())) {
            return jy.toString();
        }
        return res;
    }


    @Override
    public String saveBackGroundImageMap(Long backgroudImageId, String areaIds, String deviceGroupIds, String deviceCodes,
                                         String userGroupIds, String userIds, Integer deviceGroupLoopTime, Integer deviceLoopTime,
                                         Integer userGroupLoopTime, Integer userLoopTime) {
        String[] _deviceCodes = new String[0];
        BackgroundImage backgroundImage = new BackgroundImage();
        backgroundImage.setId(backgroudImageId);
        backgroundImage.setDeviceCodes(deviceCodes);
        backgroundImage.setDeviceGroupIds(deviceGroupIds);
        backgroundImage.setUserGroupIds(userGroupIds);
        backgroundImage.setUserIds(userIds);
        backgroundImage.setDeviceLoopTime(deviceLoopTime);
        backgroundImage.setGroupLoopTime(deviceGroupLoopTime);
        backgroundImage.setUserGroupLoopTime(userGroupLoopTime);
        backgroundImage.setUserLoopTime(userLoopTime);
        StringBuilder jy = new StringBuilder();
        List<Long> areaIdList = StringUtil.splitToLong(areaIds);
       /* if (StringUtils.isNotBlank(backgroundImage.getDeviceGroupIds())) {
            if (backgroundImage.getGroupLoopTime() == null || backgroundImage.getGroupLoopTime() == 0) {
                jy.append("设备分组循环时间不能为空!" + "\n");
            }
        }*/
        this.bulkSaveBackGroundImageDGMap(backgroundImage);
        if (StringUtils.isNotBlank(backgroundImage.getDeviceCodes())) {
           /* if (backgroundImage.getDeviceLoopTime() == null || backgroundImage.getDeviceLoopTime() == 0) {
                jy.append("设备循环时间不能为空!" + "\n");
            }*/
            _deviceCodes = backgroundImage.getDeviceCodes().split(",");
            List<String> ystenIdList = new ArrayList<String>();
            for (int i = 0; i < _deviceCodes.length; i++) {

                if (StringUtils.isEmpty(_deviceCodes[i])) {
                    continue;
                }
                _deviceCodes[i] = FilterSpaceUtils.replaceBlank(_deviceCodes[i]);
                ystenIdList.add(_deviceCodes[i]);
            }
            List<Device> deviceList = this.deviceRepository.findDeviceListByYstenIds(_deviceCodes);
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
            String[] ystIds = new String[deviceList.size()];
            int n = 0;
            for (Device device : deviceList) {
                boolean bool = false;
                for (Long areaId : areaIdList) {
                    if (areaId.equals(device.getArea().getId())) {
                        bool = true;
                        ystIds[n] = device.getYstenId();
                        n++;
                        break;
                    }
                }
                if (bool == false) {
                    jy.append("请确定易视腾编号为：" + device.getYstenId() + "的所在区域!" + "\n");
                }
            }
            if (ystIds != null) {
                backgroundImage.setDeviceCodes(StringUtils.join(ystIds, ","));
            }

        }
        String res = this.bulkSaveBackGroundImageDeviceMap(backgroundImage);
        if (StringUtils.isNotBlank(jy.toString())) {
            return jy.toString();
        }
        return res;
    }

    @Override
    public String saveBackGroundImage(BackgroundImage backgroundImage) {
        // if(StringUtils.isNotBlank(backgroundImage.getUrl()) &&
        // !backgroundImage.getUrl().startsWith("http")){
        // return "高清地址格式不正确，请确认";
        // }
        // if(StringUtils.isNotBlank(backgroundImage.getBlurUrl()) &&
        // !backgroundImage.getBlurUrl().startsWith("http")){
        // return "标清地址格式不正确，请确认";
        // }
        // if(StringUtils.isNotBlank(backgroundImage.getDeviceCodes())){
        // if(backgroundImage.getDeviceLoopTime() == null ||
        // backgroundImage.getDeviceLoopTime() == 0){
        // return "设备循环时间不能为空!";
        // }
        // String[] deviceCodes = backgroundImage.getDeviceCodes().split(",");
        // for(String deviceCode : deviceCodes){
        // Device device = this.deviceRepository.getDeviceByCode(deviceCode);
        // if(device == null){
        // return "未能找到设备code为"+deviceCode+"的设备，请确认!";
        // }
        // }
        // }
        // if(StringUtils.isNotBlank(backgroundImage.getUserIds())){
        // if(backgroundImage.getUserLoopTime() == null ||
        // backgroundImage.getUserLoopTime() == 0){
        // return "用户循环时间不能为空!";
        // }
        // String[] userIds = backgroundImage.getUserIds().split(",");
        // for(String userId : userIds){
        // Customer user = this.customerRepository.getCustomerByUserId(userId);
        // if(user == null){
        // return "未能找到用户外部编码为"+userId+"的用户，请确认!";
        // }
        // }
        // }
        // if(StringUtils.isNotBlank(backgroundImage.getDeviceGroupIds())){
        // if(backgroundImage.getGroupLoopTime() == null ||
        // backgroundImage.getGroupLoopTime() == 0){
        // return "设备分组循环时间不能为空!";
        // }
        // }
        // if(StringUtils.isNotBlank(backgroundImage.getUserGroupIds())){
        // if(backgroundImage.getUserGroupLoopTime() == null ||
        // backgroundImage.getUserGroupLoopTime() == 0){
        // return "用户分组循环时间不能为空!";
        // }
        // }
        boolean bool = this.backgroundImageRepository.saveBackGroundImage(backgroundImage);
        return bool == true ? null : "新增异常,请确认!";
    }

    @Override
    public String updateBackGroundImage(BackgroundImage backgroundImage) {
        if (StringUtils.isNotBlank(backgroundImage.getUrl()) && !backgroundImage.getUrl().startsWith("http")) {
            return "高清地址格式不正确，请确认";
        }
        if (StringUtils.isNotBlank(backgroundImage.getBlurUrl()) && !backgroundImage.getBlurUrl().startsWith("http")) {
            return "标清地址格式不正确，请确认";
        }
        BackgroundImage bgi = this.backgroundImageRepository.getById(backgroundImage.getId());
        boolean bool = true;
        if (bgi != null) {
            bgi.setName(backgroundImage.getName());
            // bgi.setMd5Hd(backgroundImage.getMd5Hd());deleted by joyce on
            // 2014-6-12
            // bgi.setMd5Sd(backgroundImage.getMd5Sd());deleted by joyce on
            // 2014-6-12
            bgi.setUrl(backgroundImage.getUrl());
            bgi.setBlurUrl(backgroundImage.getBlurUrl());
            bgi.setIsDefault(backgroundImage.getIsDefault());
            bgi.setState(backgroundImage.getState());
            bgi.setUpdateDate(backgroundImage.getUpdateDate());
            bool = this.backgroundImageRepository.updateBackGroundImage(bgi);
        }
        // if(StringUtils.isNotBlank(backgroundImage.getDeviceCodes())){
        // String[] deviceCodes = backgroundImage.getDeviceCodes().split(",");
        // for(String deviceCode : deviceCodes){
        // Device device = this.deviceRepository.getDeviceByCode(deviceCode);
        // if(device == null){
        // return "未能找到设备code为"+deviceCode+"的设备，请确认!";
        // }
        // }
        // if(backgroundImage.getDeviceLoopTime() == null ||
        // backgroundImage.getDeviceLoopTime() == 0){
        // return "设备循环时间不能为空!";
        // }
        // }
        // if(StringUtils.isNotBlank(backgroundImage.getUserIds())){
        // String[] userIds = backgroundImage.getUserIds().split(",");
        // for(String userId : userIds){
        // Customer user = this.customerRepository.getCustomerByUserId(userId);
        // if(user == null){
        // return "未能找到用户外部编码为"+userId+"的用户，请确认!";
        // }
        // }
        // if(backgroundImage.getUserLoopTime() == null ||
        // backgroundImage.getUserLoopTime() == 0){
        // return "用户循环时间不能为空!";
        // }
        // }
        // if(StringUtils.isNotBlank(backgroundImage.getDeviceGroupIds())){
        // if(backgroundImage.getGroupLoopTime() == null ||
        // backgroundImage.getGroupLoopTime() == 0){
        // return "设备分组循环时间不能为空!";
        // }
        // }
        // if(StringUtils.isNotBlank(backgroundImage.getUserGroupIds())){
        // if(backgroundImage.getUserGroupLoopTime() == null ||
        // backgroundImage.getUserGroupLoopTime() == 0){
        // return "用户分组循环时间不能为空!";
        // }
        // }
        // this.backgroundImageRepository.deleteBackGroundImageMapByBGId(backgroundImage.getId());
        // this.backgroundImageRepository.deleteUserBackGroundImageMapByBGId(backgroundImage.getId());
        return bool == true ? null : "修改异常,请确认!";
    }

    private String bulkSaveBackGroundImageUGMap(BackgroundImage backgroundImage){
        this.backgroundImageRepository.deleteByBGIdAndType(backgroundImage.getId(), Constant.BUSINESS_USER_MAP_TYPE_GROUP,Constant.BSS_USER_BACKGROUND_IMAGE_MAP);
        if (StringUtils.isNotBlank(backgroundImage.getUserGroupIds())) {
            String[] userGroupIds = backgroundImage.getUserGroupIds().split(",");
            int index = 0;
            List<BackgroundImageUserMap> maps = new ArrayList<BackgroundImageUserMap>();
            for (String userGroupId : userGroupIds) {
                BackgroundImageUserMap map = new BackgroundImageUserMap();
                map.setBackgroundImageId(backgroundImage.getId());
                map.setUserGroupId(Long.parseLong(userGroupId));
                map.setCreateDate(new Date());
                map.setLoopTime(backgroundImage.getUserGroupLoopTime());
                map.setType(Constants.GROUP);
                maps.add(map);
                index++;
                if (index % Constants.BULK_NUM == 0) {
                    this.backgroundImageRepository.bulkSaveUserBackgroundMap(maps);
                    maps.clear();
                }
            }
            if (maps.size() > 0) {
                this.backgroundImageRepository.bulkSaveUserBackgroundMap(maps);
                maps.clear();
            }
        }
        return null;
    }

    private String bulkSaveBackGroundImageUserMap(BackgroundImage backgroundImage) {
        if (StringUtils.isNotBlank(backgroundImage.getUserIds())) {
            String[] userIds = backgroundImage.getUserIds().split(",");
            List<String> userIdList = new ArrayList<String>();
            for (String userId : userIds) {
                if (StringUtils.isNotEmpty(userId)) {
                    userIdList.add(userId);
                }
            }
            this.backgroundImageRepository.deleteBackGroundMapByYstenIdsAndBgId(backgroundImage.getId(), userIdList,Constant.BSS_USER_BACKGROUND_IMAGE_MAP,Constant.BSS_USER_BACKGROUND_IMAGE_MAP_USER_ID);
//            this.backgroundImageRepository.deleteByBGIdAndType(backgroundImage.getId(), Constant.BUSINESS_USER_MAP_TYPE_USER, Constant.BSS_USER_BACKGROUND_IMAGE_MAP);
            int index = 0;
            List<BackgroundImageUserMap> maps = new ArrayList<BackgroundImageUserMap>();
            for (String ystenId : userIds) {
                BackgroundImageUserMap map = new BackgroundImageUserMap();
                map.setBackgroundImageId(backgroundImage.getId());
                map.setCode(ystenId);
                map.setCreateDate(new Date());
                map.setLoopTime(backgroundImage.getUserLoopTime());
                map.setType(Constant.BUSINESS_USER_MAP_TYPE_USER);
                maps.add(map);
                index++;
                if (index % Constants.BULK_NUM == 0) {
                    this.backgroundImageRepository.bulkSaveUserBackgroundMap(maps);
                    maps.clear();
                }
            }
            if (maps.size() > 0) {
                this.backgroundImageRepository.bulkSaveUserBackgroundMap(maps);
                maps.clear();
            }
        }
        return null;
    }


    private String bulkSaveBackGroundImageDGMap(BackgroundImage backgroundImage) {
        this.backgroundImageRepository.deleteByBGIdAndType(backgroundImage.getId(), Constant.BUSINESS_DEVICE_MAP_TYPE_GROUP, Constant.BSS_DEVICE_BACKGROUND_IMAGE_MAP);
        if (StringUtils.isNotBlank(backgroundImage.getDeviceGroupIds())) {
            String[] deviceGroupIds = backgroundImage.getDeviceGroupIds().split(",");
            int index = 0;
            List<BackgroundImageDeviceMap> maps = new ArrayList<BackgroundImageDeviceMap>();
            for (String deviceGroupId : deviceGroupIds) {
                BackgroundImageDeviceMap map = new BackgroundImageDeviceMap();
                map = new BackgroundImageDeviceMap();
                map.setBackgroundImageId(backgroundImage.getId());
                map.setDeviceGroupId(Long.parseLong(deviceGroupId));
                map.setCreateDate(new Date());
                map.setLoopTime(backgroundImage.getGroupLoopTime());
                map.setType(Constants.GROUP);
                maps.add(map);
                index++;
                if (index % Constants.BULK_NUM == 0) {
                    this.backgroundImageRepository.bulkSaveBackgroundMap(maps);
                    maps.clear();
                }
            }
            if (maps.size() > 0) {
                this.backgroundImageRepository.bulkSaveBackgroundMap(maps);
                maps.clear();
            }
        }
        return null;
    }

    private String bulkSaveBackGroundImageDeviceMap(BackgroundImage backgroundImage) {
        if (StringUtils.isNotBlank(backgroundImage.getDeviceCodes())) {
            String[] deviceCodes = backgroundImage.getDeviceCodes().split(",");
            List<String> ystenIds = new ArrayList<String>();
            for (String deviceCode : deviceCodes) {
                if (StringUtils.isNotEmpty(deviceCode)) {
                    ystenIds.add(deviceCode);
                }
            }
//            this.backgroundImageRepository.deleteByBGIdAndType(backgroundImage.getId(), Constant.BUSINESS_DEVICE_MAP_TYPE_DEVICE, Constant.BSS_DEVICE_BACKGROUND_IMAGE_MAP);
            this.backgroundImageRepository.deleteBackGroundMapByYstenIdsAndBgId(backgroundImage.getId(), ystenIds,Constant.BSS_DEVICE_BACKGROUND_IMAGE_MAP,Constant.BSS_DEVICE_BACKGROUND_IMAGE_MAP_YSTEN_ID);
            int index = 0;
            List<BackgroundImageDeviceMap> maps = new ArrayList<BackgroundImageDeviceMap>();
            for (String ystenId : ystenIds) {
                BackgroundImageDeviceMap map = new BackgroundImageDeviceMap();
                map.setBackgroundImageId(backgroundImage.getId());
                map.setYstenId(ystenId);
                map.setCreateDate(new Date());
                map.setLoopTime(backgroundImage.getDeviceLoopTime());
                map.setType(Constants.DEVICE);
                maps.add(map);
                index++;
                if (index % Constants.BULK_NUM == 0) {
                    this.backgroundImageRepository.bulkSaveBackgroundMap(maps);
                    maps.clear();
                }
            }
            if (maps.size() > 0) {
                this.backgroundImageRepository.bulkSaveBackgroundMap(maps);
                maps.clear();
            }
        }
        return null;
    }

 /*   private String saveBackGroundImageMap(BackgroundImage backgroundImage) {

        if (StringUtils.isNotBlank(backgroundImage.getDeviceCodes())) {
            String[] deviceCodes = backgroundImage.getDeviceCodes().split(",");
            List<String> ystenIds = new ArrayList<String>();
            for (String deviceCode : deviceCodes) {
                if (StringUtils.isNotEmpty(deviceCode)) {
                    ystenIds.add(deviceCode);
                }
            }
            this.backgroundImageRepository.deleteBackGroundMapByYstenIdsAndBgId(backgroundImage.getId(), ystenIds);
            int index = 0;
            List<BackgroundImageDeviceMap> maps = new ArrayList<BackgroundImageDeviceMap>();
            for (String ystenId : ystenIds) {
                BackgroundImageDeviceMap map = new BackgroundImageDeviceMap();
                map.setBackgroundImageId(backgroundImage.getId());
                map.setYstenId(ystenId);
                map.setCreateDate(new Date());
                map.setLoopTime(backgroundImage.getDeviceLoopTime());
                map.setType(Constants.DEVICE);
                maps.add(map);
                index++;
                if (index % Constants.BULK_NUM == 0) {
                    this.backgroundImageRepository.bulkSaveBackgroundMap(maps);
                    maps.clear();
                }
            }
            if (maps.size() > 0) {
                this.backgroundImageRepository.bulkSaveBackgroundMap(maps);
                maps.clear();
            }
        }
        this.backgroundImageRepository.deleteByBGIdAndType(backgroundImage.getId(), Constants.GROUP);
        if (StringUtils.isNotBlank(backgroundImage.getDeviceGroupIds())) {
            String[] deviceGroupIds = backgroundImage.getDeviceGroupIds().split(",");
            int index = 0;
            List<BackgroundImageDeviceMap> maps = new ArrayList<BackgroundImageDeviceMap>();
            for (String deviceGroupId : deviceGroupIds) {
                BackgroundImageDeviceMap map = new BackgroundImageDeviceMap();
                map = new BackgroundImageDeviceMap();
                map.setBackgroundImageId(backgroundImage.getId());
                map.setDeviceGroupId(Long.parseLong(deviceGroupId));
                map.setCreateDate(new Date());
                map.setLoopTime(backgroundImage.getGroupLoopTime());
                map.setType(Constants.GROUP);
                maps.add(map);
                index++;
                if (index % Constants.BULK_NUM == 0) {
                    this.backgroundImageRepository.bulkSaveBackgroundMap(maps);
                    maps.clear();
                }
            }
            if (maps.size() > 0) {
                this.backgroundImageRepository.bulkSaveBackgroundMap(maps);
                maps.clear();
            }
        }
        return null;
    }*/

    @Override
    public BackgroundImage getById(Long id) {
        return this.backgroundImageRepository.getById(id);
    }

    @Override
    public boolean deleteBackGroundImage(List<Long> idsList) {
        for (Long id : idsList) {
            boolean bool = this.backgroundImageRepository.deleteById(id);
            if (bool == false) {
                return false;
            }
            this.backgroundImageRepository.deleteBackGroundImageMapByBGId(id);
            this.backgroundImageRepository.deleteUserBackGroundImageMapByBGId(id);
        }
        return true;
    }

    @Override
    public String deleteBackGroundImages(List<Long> idsList) {
        for (Long id : idsList) {
            BackgroundImage backgroud = this.backgroundImageRepository.getById(id);
            List<BackgroundImageDeviceMap> deviceMap = this.backgroundImageRepository.getBGImageDeviceMapByBGId(id);
            List<BackgroundImageUserMap> userMap = this.backgroundImageRepository.getBGUserImageMapByBGId(id);
            if (deviceMap != null && deviceMap.size() > 0) {
                return "背景轮换:" + backgroud.getName() + "绑定了设备或设备分组，不可删除，请先解绑！";
            }
            if (userMap != null && userMap.size() > 0) {
                return "背景轮换:" + backgroud.getName() + "绑定了用户或用户分组，不可删除，请先解绑！";
            }
            this.backgroundImageRepository.deleteById(id);
        }
        return null;
    }

    @Override
    public boolean deleteBackGroundImageMap(List<Long> idsList, String isUser) {
        for (Long id : idsList) {
            if (StringUtils.isNotBlank(isUser)) {
                this.backgroundImageRepository.deleteUserBackGroundImageMapByBGId(id);
            } else {
                this.backgroundImageRepository.deleteBackGroundImageMapByBGId(id);
            }

        }
        // this.backgroundImageRepository.deleteByIds(idsList);
        return true;
    }

    @Override
    public String getBackGroundImageMapByTypeAndId(String type, String id) {
        List<BackgroundImageDeviceMap> maps = this.backgroundImageRepository.getBGImageMapByBGIdAndType(
                Long.parseLong(id), type);
        if (maps == null || maps.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        Integer loopTime = null;
        for (BackgroundImageDeviceMap map : maps) {
            if (StringUtils.equalsIgnoreCase(type, "DEVICE")) {
                sb.append(map.getYstenId()).append(",");
            } else if (StringUtils.equalsIgnoreCase(type, "GROUP")) {
                sb.append(map.getDeviceGroupId()).append(",");
            }
            loopTime = map.getLoopTime();
        }
        if (loopTime == null) {
            loopTime = 0;
        }
        if (StringUtils.equalsIgnoreCase(type, "DEVICE")) {
            return "{\"deviceCodes\":\"" + sb.substring(0, sb.length() - 1) + "\",\"loopTime\":\"" + loopTime + "\"}";
        }
        return "{\"deviceGroupIds\":\"" + sb.substring(0, sb.length() - 1) + "\",\"loopTime\":\"" + loopTime + "\"}";
    }

    @Override
    public List<BackgroundImageUserMap> getBGUserImageMapByBGIdAndType(Long bgId, String type) {
        return this.backgroundImageRepository.getBGUserImageMapByBGIdAndType(bgId, type);
    }

    @Override
    public List<BackgroundImage> findAllBackgroundImage() {
        return this.backgroundImageRepository.findAllBackgroundImage();
    }


    @Override
    public String getUserBackGroundImageMapByTypeAndId(String type, Long id) {
        List<BackgroundImageUserMap> mapList = this.backgroundImageRepository.getBGUserImageMapByBGIdAndType(id, type);
        StringBuilder sb = new StringBuilder();
        Integer loopTime = null;
        if (mapList != null && mapList.size() == 0) {
            return null;
        }
        for (BackgroundImageUserMap map : mapList) {
            if (StringUtils.equalsIgnoreCase(type, "USER")) {
                sb.append(map.getCode()).append(",");
            } else if (StringUtils.equalsIgnoreCase(type, "GROUP")) {
                sb.append(map.getUserGroupId()).append(",");
            }
            loopTime = map.getLoopTime();
        }
        if (loopTime == null) {
            loopTime = 0;
        }
        if (StringUtils.equalsIgnoreCase(type, "USER")) {
            return "{\"userIds\":\"" + sb.substring(0, sb.length() - 1) + "\",\"loopTime\":\"" + loopTime + "\"}";
        }
        return "{\"userGroupIds\":\"" + sb.substring(0, sb.length() - 1) + "\",\"loopTime\":\"" + loopTime + "\"}";
    }

    @Override
    public List<BackgroundImage> findDefaultBackgroundImage(int isDefault) {
        return this.backgroundImageRepository.findDefaultBackgroundImage(isDefault);
    }

}
