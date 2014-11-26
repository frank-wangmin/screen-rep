package com.ysten.local.bss.web.service.impl;

import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.device.domain.*;
import com.ysten.local.bss.device.repository.ICustomerRepository;
import com.ysten.local.bss.device.repository.IDeviceRepository;
import com.ysten.local.bss.device.repository.IUpgradeTaskRepository;
import com.ysten.local.bss.util.FilterSpaceUtils;
import com.ysten.local.bss.web.service.IUpgradeTaskService;
import com.ysten.utils.page.Pageable;
import com.ysten.utils.string.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UpgradeTaskServiceImpl implements IUpgradeTaskService {

    @Autowired
    private IUpgradeTaskRepository upgradeTaskRepository;
    @Autowired
    private IDeviceRepository deviceRepository;
    @Autowired
    private ICustomerRepository customerRepository;
    @Override
    public Pageable<UpgradeTask> getListByCondition(Long softwarePackageId, Long softCodeId, Integer pageNo,
                                                    Integer pageSize) {
        return this.upgradeTaskRepository.getListByCondition(softwarePackageId, softCodeId, pageNo, pageSize);
    }

    @Override
    public boolean deleteByIds(List<Long> ids) {
        return this.upgradeTaskRepository.deleteByIds(ids);
    }

    @Override
    public String deleteUpgradeTaskByIds(List<Long> ids) {
        for (Long id : ids) {
            UpgradeTask task = this.upgradeTaskRepository.getById(id);
            List<DeviceUpgradeMap> deviceMap = this.upgradeTaskRepository.findMapByUpgradeIdAndType(id, null);
            List<UserUpgradeMap> userMap = this.upgradeTaskRepository.findUserMapByUpgradeIdAndType(id, null);
            if (deviceMap != null && deviceMap.size() > 0) {
                return "软件号:" + task.getSoftCodeId().getName() + ";软件包:" + task.getSoftwarePackageId().getVersionName()
                        + "的升级任务 绑定了设备或设备分组，不可删除，请先解绑！";
            }
            if (userMap != null && userMap.size() > 0) {
                return "软件号:" + task.getSoftCodeId() + ";软件包:" + task.getSoftwarePackageId()
                        + "的升级任务绑定了用户或用户分组，不可删除，请先解绑！";
            }
            this.upgradeTaskRepository.deleteUpgradeTaskById(id);
        }

        return null;
    }

    @Override
    public UpgradeTask getByVersionSeq(int versionSeq) {
        return this.upgradeTaskRepository.getByVersionSeq(versionSeq);
    }

    @Override
    public boolean save(UpgradeTask upgradeTask) {
        return this.upgradeTaskRepository.save(upgradeTask);
    }

    @Override
    public boolean updateById(UpgradeTask upgradeTask) {
        // List<Long> upgradeIds = new ArrayList<Long>();
        // upgradeIds.add(upgradeTask.getId());
        // this.upgradeTaskRepository.deleteDeviceUpgradeMap(upgradeIds);
        // this.upgradeTaskRepository.deleteUserUpgradeMap(upgradeIds);
        return this.upgradeTaskRepository.updateById(upgradeTask);
    }

    @Override
    public UpgradeTask getById(long id) {
        return this.upgradeTaskRepository.getById(id);
    }

    @Override
    public String saveUpgradeTaskMap(Long upgradeTaskId, String areaIds,String deviceGroupIds, String deviceCodes,
                                     String userGroupIds, String userIds) {
    	String result = null;
        String[] _deviceCodes = new String[0];
        List<String> deviceCodeList = new ArrayList<String>();
        List<Long> areaIdList = StringUtil.splitToLong(areaIds);
        StringBuilder jy = new StringBuilder();
        
        this.upgradeTaskRepository.deleteDeviceUpgradeMapByType(upgradeTaskId, Constants.GROUP);
        if (StringUtils.isNotBlank(deviceGroupIds)) {
        	result = this.saveMap(upgradeTaskId, Constants.GROUP, deviceGroupIds, null);
        	if(result != null){
            	jy.append(result+"\n");
            }
        }
        if (StringUtils.isNotBlank(deviceCodes)) {
            _deviceCodes = deviceCodes.split(",");
            List<String> ystenIdList = new ArrayList<String>();
            for (int i = 0; i < _deviceCodes.length; i++) {
            	 if (StringUtils.isEmpty(_deviceCodes[i])) {
                     continue;
                 }
                _deviceCodes[i] = FilterSpaceUtils.replaceBlank(_deviceCodes[i]);
                ystenIdList.add(_deviceCodes[i]);
            }
            List<Device> deviceList = this.deviceRepository.findDeviceListByYstenIds(_deviceCodes);
            if(deviceList.size()==0){
            	jy.append("未能找到你所上传的所有易视腾编号的设备，请确认!");
            	return jy.toString();
            }
            if(deviceList.size()>0 && deviceList.size() != ystenIdList.size()){
            	for (int i = 0; i < ystenIdList.size(); i++) {
                    boolean bool = false;
                    for (Device  device:deviceList) {
                        if (device.getYstenId().equals(ystenIdList.get(i))) {
                            bool = true;
                            break;
                        }
                    }
                    if (bool == false) {
                    	jy.append("未能找到易视腾编号为：" + ystenIdList.get(i) + "的设备，请确认!"+"\n") ;
                    }
                }
            }
            for (Device  device:deviceList) {
                boolean bool = false;
                for (Long areaId : areaIdList) {
                    if (areaId.equals(device.getArea().getId())) {
                    	bool = true;
                    	deviceCodeList.add(device.getYstenId());
                        break;
                    }
                }
                if (bool == false) {
               	 jy.append("请确定易视腾编号为：" + device.getYstenId() + "的所在区域!"+"\n");
                }
           	}
            if (deviceCodeList.size()>0 && deviceCodeList != null){
                result = this.saveMap(upgradeTaskId, Constants.DEVICE, null, StringUtils.join(deviceCodeList, ","));
                if(result != null){
                	jy.append(result+"\n");
                }
            }
        }
        if (StringUtils.isNotBlank(jy.toString())) {
            return jy.toString();
        }
        return result;
    }

    @Override
    public String saveMap(Long upgradeTaskId, String type, String deviceGroupIds, String deviceCodes) {
        List<Long> deviceGroups = new ArrayList<Long>();
        if (StringUtils.isNotBlank(deviceGroupIds) && deviceGroupIds != null) {
            deviceGroups = StringUtil.splitToLong(deviceGroupIds);
            int index = 0;
            List<DeviceUpgradeMap> maps = new ArrayList<DeviceUpgradeMap>();
            for (Long deviceGroup:deviceGroups) {
            	DeviceUpgradeMap deviceUpgradeMap = new DeviceUpgradeMap();
                deviceUpgradeMap.setUpgradeId(upgradeTaskId);
                deviceUpgradeMap.setCreateDate(new Date());
                deviceUpgradeMap.setDeviceGroupId(deviceGroup);
                deviceUpgradeMap.setYstenId(deviceCodes);
                deviceUpgradeMap.setType(type);
                maps.add(deviceUpgradeMap);
                index ++;
                if (index % Constants.BULK_NUM == 0) {
					this.upgradeTaskRepository.bulkSaveDeviceUpgradeMap(maps);
					maps.clear();
				}
            }
            if(maps.size() > 0){
            	this.upgradeTaskRepository.bulkSaveDeviceUpgradeMap(maps);
				maps.clear();
             }
        }
        if (StringUtils.isNotBlank(deviceCodes) && deviceCodes != null) {
            String[] deviceCode = deviceCodes.split(",");
            int index = 0;
            List<DeviceUpgradeMap> maps = new ArrayList<DeviceUpgradeMap>();
            if (deviceCode.length > 0) {
            	this.upgradeTaskRepository.deleteMapByYstenIdsAndUpgradeId(deviceCode, upgradeTaskId);
                for (String y:deviceCode) {
                	DeviceUpgradeMap deviceUpgradeMap = new DeviceUpgradeMap();
                    	deviceUpgradeMap.setUpgradeId(upgradeTaskId);
                        deviceUpgradeMap.setCreateDate(new Date());
                        deviceUpgradeMap.setYstenId(y);
                        deviceUpgradeMap.setDeviceGroupId(null);
                        deviceUpgradeMap.setType(type);
                        maps.add(deviceUpgradeMap);
                        index ++;
                        if (index % Constants.BULK_NUM == 0) {
        					this.upgradeTaskRepository.bulkSaveDeviceUpgradeMap(maps);
        					maps.clear();
        				}
                }
                if(maps.size() > 0){
                	this.upgradeTaskRepository.bulkSaveDeviceUpgradeMap(maps);
    				maps.clear();
                 }
            }
        }
        return null;
    }

    @Override
    public String saveUserMap(Long upgradeTaskId, String type, String userGroupIds, String userIds) {
        UserUpgradeMap userUpgradeMap = new UserUpgradeMap();
        List<Long> userGroups = new ArrayList<Long>();
        // String device = upgradeTask.getDeviceGroupIds();
        if (StringUtils.isNotBlank(userGroupIds) && userGroupIds != null) {
            userGroups = StringUtil.splitToLong(userGroupIds);
            for (int i = 0; i < userGroups.size(); i++) {
                // this.upgradeTaskRepository.deleteUserUpgradeMapByUserGroupId(userGroups.get(i));
                userUpgradeMap.setUpgradeId(upgradeTaskId);
                userUpgradeMap.setCreateDate(new Date());
                userUpgradeMap.setUserGroupId(userGroups.get(i));
                userUpgradeMap.setUserId(userIds);
                userUpgradeMap.setType(type);
                this.upgradeTaskRepository.saveUserUpgradeMap(userUpgradeMap);
            }
        }
        if (StringUtils.isNotBlank(userIds) && userIds != null) {
            String[] userId = userIds.split(",");
            if (userId.length > 0) {
                for (int i = 0; i < userId.length; i++) {
                    // this.upgradeTaskRepository.deleteUserUpgradeMapByUserId(userId[i]);
                    userUpgradeMap.setUpgradeId(upgradeTaskId);
                    userUpgradeMap.setCreateDate(new Date());
                    userUpgradeMap.setUserId(userId[i]);
                    userUpgradeMap.setUserGroupId(null);
                    userUpgradeMap.setType(type);
                    this.upgradeTaskRepository.saveUserUpgradeMap(userUpgradeMap);
                }
            }
        }
        return null;
    }

    @Override
    public String saveMap(UpgradeTask upgradeTask, String type, String deviceGroupIds, String deviceCodes) {
        if (StringUtils.isNotBlank(deviceCodes)) {
            String[] _deviceCodes = deviceCodes.split(",");
            for (String deviceCode : _deviceCodes) {
                Device device = this.deviceRepository.getDeviceByCode(deviceCode);
                if (device == null) {
                    return "未能找到设备code为" + deviceCode + "的设备，请确认!";
                }
            }
        }
        DeviceUpgradeMap deviceUpgradeMap = new DeviceUpgradeMap();
        List<Long> deviceGroups = new ArrayList<Long>();
        // String device = upgradeTask.getDeviceGroupIds();
        if (StringUtils.isNotBlank(deviceGroupIds) && deviceGroupIds != null) {
            deviceGroups = StringUtil.splitToLong(deviceGroupIds);
            for (int i = 0; i < deviceGroups.size(); i++) {
                this.upgradeTaskRepository.deleteUpgradeTaskMapByGroupId(deviceGroups.get(i));
                deviceUpgradeMap.setUpgradeId(upgradeTask.getId());
                deviceUpgradeMap.setCreateDate(new Date());
                deviceUpgradeMap.setDeviceGroupId(deviceGroups.get(i));
                deviceUpgradeMap.setYstenId(deviceCodes);
                deviceUpgradeMap.setType(type);
                this.upgradeTaskRepository.saveDeviceUpgradeMap(deviceUpgradeMap);
            }
        }
        if (StringUtils.isNotBlank(deviceCodes) && deviceCodes != null) {
            String[] deviceCode = deviceCodes.split(",");
            if (deviceCode.length > 0) {
                for (int i = 0; i < deviceCode.length; i++) {
                    this.upgradeTaskRepository.deleteUpgradeTaskMapByCode(deviceCode[i]);
                    deviceUpgradeMap.setUpgradeId(upgradeTask.getId());
                    deviceUpgradeMap.setCreateDate(new Date());
                    deviceUpgradeMap.setYstenId(deviceCode[i]);
                    deviceUpgradeMap.setDeviceGroupId(null);
                    deviceUpgradeMap.setType(type);
                    this.upgradeTaskRepository.saveDeviceUpgradeMap(deviceUpgradeMap);
                }
            }
        }
        return null;
    }

    @Override
    public String saveUserMap(UpgradeTask upgradeTask, String type, String userGroupIds, String userIds) {
        if (StringUtils.isNotBlank(userIds)) {
            String[] _userIds = userIds.split(",");
            for (String userId : _userIds) {
                Customer user = this.customerRepository.getCustomerByUserId(userId);
                if (user == null) {
                    return "未能找到用户外部编码为" + userId + "的用户，请确认!";
                }
            }
        }
        UserUpgradeMap userUpgradeMap = new UserUpgradeMap();
        List<Long> userGroups = new ArrayList<Long>();
        // String device = upgradeTask.getDeviceGroupIds();
        if (StringUtils.isNotBlank(userGroupIds) && userGroupIds != null) {
            userGroups = StringUtil.splitToLong(userGroupIds);
            for (int i = 0; i < userGroups.size(); i++) {
                this.upgradeTaskRepository.deleteUserUpgradeMapByUserGroupId(userGroups.get(i));
                userUpgradeMap.setUpgradeId(upgradeTask.getId());
                userUpgradeMap.setCreateDate(new Date());
                userUpgradeMap.setUserGroupId(userGroups.get(i));
                userUpgradeMap.setUserId(userIds);
                userUpgradeMap.setType(type);
                this.upgradeTaskRepository.saveUserUpgradeMap(userUpgradeMap);
            }
        }
        if (StringUtils.isNotBlank(userIds) && userIds != null) {
            String[] userId = userIds.split(",");
            if (userId.length > 0) {
                for (int i = 0; i < userId.length; i++) {
                    this.upgradeTaskRepository.deleteUserUpgradeMapByUserId(userId[i]);
                    userUpgradeMap.setUpgradeId(upgradeTask.getId());
                    userUpgradeMap.setCreateDate(new Date());
                    userUpgradeMap.setUserId(userId[i]);
                    userUpgradeMap.setUserGroupId(null);
                    userUpgradeMap.setType(type);
                    this.upgradeTaskRepository.saveUserUpgradeMap(userUpgradeMap);
                }
            }
        }
        return null;
    }

    @Override
    public List<DeviceUpgradeMap> findMapByUpgradeIdAndType(Long upgradeId, String type) {
        return this.upgradeTaskRepository.findMapByUpgradeIdAndType(upgradeId, type);
    }

    @Override
    public List<UserUpgradeMap> findUserMapByUpgradeIdAndType(Long upgradeId, String type) {
        return this.upgradeTaskRepository.findUserMapByUpgradeIdAndType(upgradeId, type);
    }

    @Override
    public boolean deleteUpgradeTaskMap(List<Long> ids) {
        this.upgradeTaskRepository.deleteDeviceUpgradeMap(ids);
        this.upgradeTaskRepository.deleteUserUpgradeMap(ids);
        return true;
    }

    @Override
    public List<UpgradeTask> findUpgradeTasksByCondition(
            Long softwarePackageId, Long softCodeId) {
        return this.upgradeTaskRepository.findUpgradeTasksByCondition(softwarePackageId, softCodeId);
    }

    @Override
    public List<UpgradeTask> findAllUpgradeTask() {
        return this.upgradeTaskRepository.findAllUpgradeTask();
    }

    @Override
    public Pageable<UpgradeTask> findUpgradeTaskListByCondition(
            String softwarePackageName, String softCodeName, Integer pageNo,
            Integer pageSize) {
        return this.upgradeTaskRepository.findUpgradeTaskListByCondition(softwarePackageName, softCodeName, pageNo, pageSize);
    }
}
