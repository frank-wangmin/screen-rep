package com.ysten.local.bss.web.service.impl;

import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.device.domain.Customer;
import com.ysten.local.bss.device.domain.Device;
import com.ysten.local.bss.device.repository.ICustomerRepository;
import com.ysten.local.bss.device.repository.IDeviceRepository;
import com.ysten.local.bss.notice.domain.DeviceNoticeMap;
import com.ysten.local.bss.notice.domain.UserNoticeMap;
import com.ysten.local.bss.notice.domain.SysNotice;
import com.ysten.local.bss.notice.repository.ISysNoticeRepository;
import com.ysten.local.bss.system.domain.Operator;
import com.ysten.local.bss.util.FilterSpaceUtils;
import com.ysten.local.bss.util.bean.Constant;
import com.ysten.local.bss.utils.ControllerUtil;
import com.ysten.local.bss.web.service.ISysNoticeWebService;
import com.ysten.utils.page.Pageable;
import com.ysten.utils.string.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SysNoticeWebServiceImpl implements ISysNoticeWebService {

    @Autowired
    private ISysNoticeRepository sysNoticeRepository;
    @Autowired
    private IDeviceRepository deviceRepository;
    @Autowired
    private ICustomerRepository customerRepository;

    @Override
    public SysNotice getSysNoticeById(Long id) {
        return this.sysNoticeRepository.getSysNoticeById(id);
    }

    @Override
    public String saveSysNotice(SysNotice sysNotice, String startDate, String endDate, String deviceGroupIds,
                                String deviceCodes, String userGroupIds, String userIds, HttpServletRequest request) throws ParseException {
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
        sysNotice.setCreateDate(new Date());
        sysNotice.setUpdateDate(new Date());
        Operator op = ControllerUtil.getLoginOperator(request);
        sysNotice.setOperateUser(op.getLoginName());
        if(sysNotice.getType().equals("CUSTOM")){
        	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sysNotice.setStartDate(format.parse(startDate));
            sysNotice.setEndDate(format.parse(endDate));
            sysNotice.setDistrictCode(null);
        }
        if(sysNotice.getType().equals("SYSTEM")){
        	sysNotice.setIsDefault(null);
        }
        this.sysNoticeRepository.saveSysNotice(sysNotice);
        // if(sysNotice.getIsAll()==1){
        if (StringUtils.isNotBlank(deviceGroupIds)) {
            List<Long> groupIds = StringUtil.splitToLong(deviceGroupIds);
            if (groupIds != null && groupIds.size() > 0) {
                for (Long id : groupIds) {
                    this.saveDeviceNoticeMap(sysNotice, "GROUP", id, null);
                }
            }
        }
        if (StringUtils.isNotBlank(deviceCodes)) {
            String[] deviceCode = deviceCodes.split(",");
            if (deviceCode.length > 0) {
                for (int i = 0; i < deviceCode.length; i++) {
                    this.saveDeviceNoticeMap(sysNotice, "DEVICE", null, deviceCode[i]);
                }
            }
        }
        if (StringUtils.isNotBlank(userGroupIds)) {
            List<Long> groupIds = StringUtil.splitToLong(userGroupIds);
            if (groupIds != null && groupIds.size() > 0) {
                for (Long id : groupIds) {
                    this.saveUserNoticeMap(sysNotice, "GROUP", id, null);
                }
            }
        }
        if (StringUtils.isNotBlank(userIds)) {
            String[] userId = userIds.split(",");
            if (userId.length > 0) {
                for (int i = 0; i < userId.length; i++) {
                    this.saveUserNoticeMap(sysNotice, "USER", null, userId[i]);
                }
            }
        }
        // }
        return null;
    }

    private boolean saveDeviceNoticeMap(SysNotice sysNotice, String type, Long deviceGroupId, String deviceCode) {
        DeviceNoticeMap deviceNoticeMap = new DeviceNoticeMap();
        deviceNoticeMap.setCreateDate(new Date());
        deviceNoticeMap.setType(type);
        deviceNoticeMap.setDeviceGroupId(deviceGroupId);
        deviceNoticeMap.setYstenId(deviceCode);
        deviceNoticeMap.setNoticeId(sysNotice.getId());
        return sysNoticeRepository.saveDeviceNoticeMap(deviceNoticeMap);
    }

    private boolean saveUserNoticeMap(SysNotice sysNotice, String type, Long userGroupId, String userId) {
        UserNoticeMap userNoticeMap = new UserNoticeMap();
        userNoticeMap.setCreateDate(new Date());
        userNoticeMap.setType(type);
        userNoticeMap.setUserGroupId(userGroupId);
        userNoticeMap.setCode(userId);
        userNoticeMap.setNoticeId(sysNotice.getId());
        return sysNoticeRepository.saveUserNoticeMap(userNoticeMap);
    }

    @Override
    public String updateSysNotice(SysNotice sysNotice, String startDate, String endDate, String deviceGroupIds,
                                  String deviceCodes, String userGroupIds, String userIds) throws ParseException {
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
        SysNotice sysNoticeUpdate = this.sysNoticeRepository.getSysNoticeById(sysNotice.getId());
        if(sysNotice.getType().equals("CUSTOM")){
        	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	sysNoticeUpdate.setStartDate(format.parse(startDate));
        	sysNoticeUpdate.setEndDate(format.parse(endDate));
        	sysNoticeUpdate.setIsDefault(sysNotice.getIsDefault());
        	sysNoticeUpdate.setDistrictCode(null);
        }
        if(sysNotice.getType().equals("SYSTEM")){
        	sysNoticeUpdate.setIsDefault(null);
        	sysNoticeUpdate.setDistrictCode(sysNotice.getDistrictCode());
        }
        sysNoticeUpdate.setTitle(sysNotice.getTitle());
        sysNoticeUpdate.setContent(sysNotice.getContent());
//        sysNoticeUpdate.setIsAll(sysNotice.getIsAll());
        sysNoticeUpdate.setStatus(sysNotice.getStatus());
        sysNoticeUpdate.setUpdateDate(new Date());
        this.sysNoticeRepository.updateSysNotice(sysNoticeUpdate);
        /*
         * deleted by joyce on 2014-6-16
         * this.sysNoticeRepository.deleteDeviceNoticeMapByNoticeId
         * (sysNotice.getId(), "GROUP");
         * this.sysNoticeRepository.deleteDeviceNoticeMapByNoticeId
         * (sysNotice.getId(), "DEVICE");
         * this.sysNoticeRepository.deleteUserNoticeMapByNoticeId
         * (sysNotice.getId(), "GROUP");
         * this.sysNoticeRepository.deleteUserNoticeMapByNoticeId
         * (sysNotice.getId(), "USER");
         */
        // if(sysNotice.getIsAll()==0){
        if (StringUtils.isNotBlank(deviceGroupIds)) {
            List<Long> groupIds = StringUtil.splitToLong(deviceGroupIds);
            if (groupIds != null && groupIds.size() > 0) {
                for (Long id : groupIds) {
                    this.saveDeviceNoticeMap(sysNotice, "GROUP", id, null);
                }
            }
        }
        if (StringUtils.isNotBlank(deviceCodes)) {
            String[] deviceCode = deviceCodes.split(",");
            if (deviceCode.length > 0) {
                for (int i = 0; i < deviceCode.length; i++) {
                    this.saveDeviceNoticeMap(sysNotice, "DEVICE", null, deviceCode[i]);
                }
            }
        }
        if (StringUtils.isNotBlank(userGroupIds)) {
            List<Long> groupIds = StringUtil.splitToLong(userGroupIds);
            if (groupIds != null && groupIds.size() > 0) {
                for (Long id : groupIds) {
                    this.saveUserNoticeMap(sysNotice, "GROUP", id, null);
                }
            }
        }
        if (StringUtils.isNotBlank(userIds)) {
            String[] userId = userIds.split(",");
            if (userId.length > 0) {
                for (int i = 0; i < userId.length; i++) {
                    this.saveUserNoticeMap(sysNotice, "USER", null, userId[i]);
                }
            }
        }
        // }

        return null;
    }

    @Override
    public String deleteSysNoticeRelationship(Long id, String user) {
        if (StringUtils.isNotBlank(user)) {
            this.sysNoticeRepository.deleteByNoticeIdAndType(id, "", Constant.BSS_USER_NOTICE_MAP);
            return Constants.SUCCESS;
        } else {
            this.sysNoticeRepository.deleteByNoticeIdAndType(id, "", Constant.BSS_DEVICE_NOTICE_MAP);
            return Constants.SUCCESS;
        }
    }

    @Override
    public boolean deleteSysNotice(List<Long> ids) {
        return this.sysNoticeRepository.deleteSysNotice(ids);
    }

    @Override
    public Pageable<SysNotice> findSysNoticeList(String title, String content, Integer pageNo, Integer pageSize) {
        return this.sysNoticeRepository.findSysNotice(FilterSpaceUtils.filterStartEndSpace(title), FilterSpaceUtils.filterStartEndSpace(content), pageNo, pageSize);
    }

    @Override
    public List<DeviceNoticeMap> findDeviceNoticeMapByNoticeIdAndType(Long noticeId, String type) {
        return this.sysNoticeRepository.findDeviceNoticeMapByNoticeIdAndType(noticeId, type);
    }

    @Override
    public boolean saveUserSysNotice(SysNotice sysNotice, String startDate, String endDate, String userGroupIds,
                                     String userIds, HttpServletRequest request) throws ParseException {
        boolean bool = false;
        sysNotice.setCreateDate(new Date());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Operator op = ControllerUtil.getLoginOperator(request);
        sysNotice.setOperateUser(op.getLoginName());
        sysNotice.setStartDate(format.parse(startDate));
        sysNotice.setEndDate(format.parse(endDate));
        bool = this.sysNoticeRepository.saveSysNotice(sysNotice);
        // if(sysNotice.getIsAll()==1){
        if (StringUtils.isNotBlank(userGroupIds)) {
            List<Long> groupIds = StringUtil.splitToLong(userGroupIds);
            if (groupIds != null && groupIds.size() > 0) {
                for (Long id : groupIds) {
                    bool = this.saveUserNoticeMap(sysNotice, "GROUP", id, null);
                }
            }
        }
        if (StringUtils.isNotBlank(userIds)) {
            String[] userId = userIds.split(",");
            if (userId.length > 0) {
                for (int i = 0; i < userId.length; i++) {
                    bool = this.saveUserNoticeMap(sysNotice, "USER", null, userId[i]);
                }
            }
        }
        // }
        return bool;
    }

    @Override
    public boolean updateUserSysNotice(SysNotice sysNotice, String startDate, String endDate, String userGroupIds,
                                       String userIds) throws ParseException {
        boolean bool = false;
        SysNotice sysNoticeUpdate = this.sysNoticeRepository.getSysNoticeById(sysNotice.getId());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sysNoticeUpdate.setStartDate(format.parse(startDate));
        sysNoticeUpdate.setEndDate(format.parse(endDate));
        sysNoticeUpdate.setTitle(sysNotice.getTitle());
        sysNoticeUpdate.setContent(sysNotice.getContent());
        // sysNoticeUpdate.setIsAll(sysNotice.getIsAll());
        sysNoticeUpdate.setStatus(sysNotice.getStatus());
        sysNoticeUpdate.setUpdateDate(new Date());
/*        this.sysNoticeRepository.deleteUserNoticeMapByNoticeId(sysNotice.getId(), "GROUP");
        this.sysNoticeRepository.deleteUserNoticeMapByNoticeId(sysNotice.getId(), "USER");*/
        // if(sysNotice.getIsAll()==1){
        if (StringUtils.isNotBlank(userGroupIds)) {
            List<Long> groupIds = StringUtil.splitToLong(userGroupIds);
            if (groupIds != null && groupIds.size() > 0) {
                for (Long id : groupIds) {
                    bool = this.saveUserNoticeMap(sysNotice, "GROUP", id, null);
                }
            }
        }
        if (StringUtils.isNotBlank(userIds)) {
            String[] userId = userIds.split(",");
            if (userId.length > 0) {
                for (int i = 0; i < userId.length; i++) {
                    bool = this.saveUserNoticeMap(sysNotice, "USER", null, userId[i]);
                }
            }
        }
        // }
        bool = this.sysNoticeRepository.updateSysNotice(sysNoticeUpdate);
        return bool;
    }

    @Override
    public List<UserNoticeMap> findUserNoticeMapByNoticeIdAndType(Long noticeId, String type) {
        return this.sysNoticeRepository.findUserNoticeMapByNoticeIdAndType(noticeId, type);
    }

    @Override
    public List<SysNotice> findAllSysNoticeList() {
        return this.sysNoticeRepository.findAllSysNoticeList();
    }

    @Override
    public SysNotice findSysNoticeByTitle(String title) {
        return this.sysNoticeRepository.findSysNoticeByTitle(title);
    }


    @Override
    public String updateSysNoticeDeviceRelationship(SysNotice sysNotice, String areaIds, String deviceGroupIds, String ystenIds) throws ParseException {
        StringBuilder jy = new StringBuilder("");
        String[] _ystenIds = new String[0];
        List<Long> areaIdList = StringUtil.splitToLong(areaIds);
        this.sysNoticeRepository.deleteByNoticeIdAndType(sysNotice.getId(), Constant.BUSINESS_DEVICE_MAP_TYPE_GROUP, Constant.BSS_DEVICE_NOTICE_MAP);
        if (StringUtils.isNotBlank(deviceGroupIds)) {
            int index = 0;
            List<DeviceNoticeMap> maps = new ArrayList<DeviceNoticeMap>();
            List<Long> groupIds = StringUtil.splitToLong(deviceGroupIds);
            if (groupIds != null && groupIds.size() > 0) {
                for (Long id : groupIds) {
                    DeviceNoticeMap deviceNoticeMap = new DeviceNoticeMap();
                    deviceNoticeMap.setCreateDate(new Date());
                    deviceNoticeMap.setType(Constants.GROUP);
                    deviceNoticeMap.setDeviceGroupId(id);
                    deviceNoticeMap.setYstenId(null);
                    deviceNoticeMap.setNoticeId(sysNotice.getId());
                    maps.add(deviceNoticeMap);
                    index++;
                    if (index % Constants.BULK_NUM == 0) {
                        this.sysNoticeRepository.bulkSaveDeviceNoticeMap(maps);
                        maps.clear();
                    }
                }
                if (maps.size() > 0) {
                    this.sysNoticeRepository.bulkSaveDeviceNoticeMap(maps);
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
            List<String> ystList = new ArrayList<String>();
            for (Device device : deviceList) {
                boolean bool = false;
                for (Long areaId : areaIdList) {
                    if (areaId.equals(device.getArea().getId())) {
                        bool = true;
                        ystList.add(device.getYstenId());
                        break;
                    }
                }
                if (bool == false) {
                    jy.append("请确定易视腾编号为：" + device.getYstenId() + "的所在区域!" + "\n");
                }
            }
            if (ystList != null && ystList.size() > 0) {
                int index = 0;
//                this.sysNoticeRepository.deleteByNoticeIdAndType(sysNotice.getId(), Constant.BUSINESS_DEVICE_MAP_TYPE_DEVICE, Constant.BSS_DEVICE_NOTICE_MAP);
                this.sysNoticeRepository.deleteMapByYstenIdsAndNoticeId(sysNotice.getId(), ystList,Constant.BSS_DEVICE_NOTICE_MAP,Constant.BSS_DEVICE_NOTICE_MAP_YSTEN_ID);
                List<DeviceNoticeMap> maps = new ArrayList<DeviceNoticeMap>();
                for (String y : ystList) {
                    DeviceNoticeMap deviceNoticeMap = new DeviceNoticeMap();
                    deviceNoticeMap.setCreateDate(new Date());
                    deviceNoticeMap.setType(Constants.DEVICE);
                    deviceNoticeMap.setDeviceGroupId(null);
                    deviceNoticeMap.setYstenId(y);
                    deviceNoticeMap.setNoticeId(sysNotice.getId());
                    maps.add(deviceNoticeMap);
                    index++;
                    if (index % Constants.BULK_NUM == 0) {
                        this.sysNoticeRepository.bulkSaveDeviceNoticeMap(maps);
                        maps.clear();
                    }
                }
                if (maps.size() > 0) {
                    this.sysNoticeRepository.bulkSaveDeviceNoticeMap(maps);
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
    public String updateSysNoticeUserRelationship(SysNotice sysNotice, String areaIds, String userGroupIds, String userIds) throws ParseException {
        StringBuilder jy = new StringBuilder("");
        String[] _userIds = new String[0];
        List<Long> areaIdList = StringUtil.splitToLong(areaIds);
        this.sysNoticeRepository.deleteByNoticeIdAndType(sysNotice.getId(), Constant.BUSINESS_USER_MAP_TYPE_GROUP, Constant.BSS_USER_NOTICE_MAP);
//        this.sysNoticeRepository.deleteUserNoticeMapByNoticeId(sysNotice.getId(), Constant.BUSINESS_USER_MAP_TYPE_GROUP);
        if (StringUtils.isNotBlank(userGroupIds)) {
            int index = 0;
            List<UserNoticeMap> maps = new ArrayList<UserNoticeMap>();
            List<Long> groupIds = StringUtil.splitToLong(userGroupIds);
            if (groupIds != null && groupIds.size() > 0) {
                for (Long id : groupIds) {
                    UserNoticeMap userNoticeMapp = new UserNoticeMap();
                    userNoticeMapp.setCreateDate(new Date());
                    userNoticeMapp.setType(Constants.GROUP);
                    userNoticeMapp.setUserGroupId(id);
                    userNoticeMapp.setCode(null);
                    userNoticeMapp.setNoticeId(sysNotice.getId());
                    maps.add(userNoticeMapp);
                    index++;
                    if (index % Constants.BULK_NUM == 0) {
                        this.sysNoticeRepository.bulkSaveUserNoticeMap(maps);
                        maps.clear();
                    }
                }
                if (maps.size() > 0) {
                    this.sysNoticeRepository.bulkSaveUserNoticeMap(maps);
                    maps.clear();
                }
            }
        }
        if (StringUtils.isNotBlank(userIds)) {
            List<String> userIdArray = new ArrayList<String>();
            _userIds = userIds.split(",");
            for (int i = 0; i < _userIds.length; i++) {
                if (StringUtils.isEmpty(_userIds[i])) {
                    continue;
                }
                _userIds[i] = FilterSpaceUtils.replaceBlank(_userIds[i]);
                userIdArray.add(FilterSpaceUtils.replaceBlank(_userIds[i]));
            }
            List<Customer> customerList = this.customerRepository.findCustomersByUserCodes(_userIds);
            if (customerList.size() == 0) {
                jy.append("未能找到你所上传的所有用户编码的用户，请确认!");
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
                        jy.append("未能找到用户编码为：" + userIdArray.get(i) + "的用户，请确认!" + "\n");
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
                int index = 0;
                this.sysNoticeRepository.deleteMapByYstenIdsAndNoticeId(sysNotice.getId(), userIdList,Constant.BSS_USER_NOTICE_MAP,Constant.BSS_USER_NOTICE_MAP_USER_ID);
//                this.sysNoticeRepository.deleteByNoticeIdAndType(sysNotice.getId(), Constant.BUSINESS_USER_MAP_TYPE_USER, Constant.BSS_USER_NOTICE_MAP);
                List<UserNoticeMap> maps = new ArrayList<UserNoticeMap>();
                for (String y : userIdList) {
                    UserNoticeMap userNoticeMap = new UserNoticeMap();
                    userNoticeMap.setCreateDate(new Date());
                    userNoticeMap.setType(Constant.BUSINESS_USER_MAP_TYPE_USER);
                    userNoticeMap.setUserGroupId(null);
                    userNoticeMap.setCode(y);
                    userNoticeMap.setNoticeId(sysNotice.getId());
                    maps.add(userNoticeMap);
                    index++;
                    if (index % Constants.BULK_NUM == 0) {
                        this.sysNoticeRepository.bulkSaveUserNoticeMap(maps);
                        maps.clear();
                    }
                }
                if (maps.size() > 0) {
                    this.sysNoticeRepository.bulkSaveUserNoticeMap(maps);
                    maps.clear();
                }
            }
        }

        if (StringUtils.isNotBlank(jy.toString())) {
            return jy.toString();
        }
        return null;
    }
}
