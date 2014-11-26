package com.ysten.local.bss.web.service.impl;

import com.ysten.boss.systemconfig.service.SystemConfigService;
import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.device.domain.*;
import com.ysten.local.bss.device.domain.AppUpgradeMap.Type;
import com.ysten.local.bss.device.repository.IAppUpgradeMapRepository;
import com.ysten.local.bss.device.repository.IAppUpgradeTaskRepository;
import com.ysten.local.bss.device.repository.ICustomerRepository;
import com.ysten.local.bss.device.repository.IDeviceRepository;
import com.ysten.local.bss.util.FilterSpaceUtils;
import com.ysten.local.bss.web.service.IAppUpgradeTaskService;
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
public class AppUpgradeTaskServiceImpl implements IAppUpgradeTaskService {

    @Autowired
    private IAppUpgradeTaskRepository appUpgradeTaskRepository;
    @Autowired
    private IAppUpgradeMapRepository appUpgradeMapRepository;
    @Autowired
    private IDeviceRepository deviceRepository;
    @Autowired
    private ICustomerRepository customerRepository;
    @Override
    public Pageable<AppUpgradeTask> getListByCondition(Long softwarePackageId, Long softCodeId, Integer pageNo,
            Integer pageSize) {
        return this.appUpgradeTaskRepository.findAppUpgradeTaskListByCondition(softwarePackageId, softCodeId, pageNo, pageSize);
    }

    @Override
    public String deleteByIds(List<Long> ids) {
        for (Long upgradeId : ids) {
            List<AppUpgradeMap> mapList = this.appUpgradeTaskRepository.findMapByUpgradeId(upgradeId);
            if ( mapList!= null && mapList.size()>0) {
                AppUpgradeTask appUpgradeTask = appUpgradeTaskRepository.getAppUpgradeTaskById(upgradeId);
                return "应用软件信息号:" + appUpgradeTask.getSoftCodeId().getName() + "绑定了设备分组或是设备编号,如果想删除,请先做解绑操作";
            } else if (this.appUpgradeTaskRepository.findUserMapByUpgradeId(upgradeId) != null && this.appUpgradeTaskRepository.findUserMapByUpgradeId(upgradeId).size()>0) {
                AppUpgradeTask appUpgradeTask = appUpgradeTaskRepository.getAppUpgradeTaskById(upgradeId);
                return "应用软件信息号:" + appUpgradeTask.getSoftCodeId().getName() + "绑定了用户分组或是用户外部编号,如果想删除,请先做解绑操作";
            }
        }
        return (this.appUpgradeTaskRepository.deleteAppUpgradeTaskByIds(ids)) ? Constants.SUCCESS : Constants.FAILED;
    }

    @Override
    public AppUpgradeTask getById(Long id) {
        return this.appUpgradeTaskRepository.getAppUpgradeTaskById(id);
    }

    @Override
    public boolean save(AppUpgradeTask upgradeTask) {
        return this.appUpgradeTaskRepository.saveAppUpgradeTask(upgradeTask);
    }

    private boolean saveDeviceMap(AppUpgradeTask upgradeTask, String type, String deviceCodes, Long deviceGroupIds) {
        AppUpgradeMap deviceUpgradeMap = new AppUpgradeMap();
        deviceUpgradeMap.setUpgradeId(upgradeTask.getId());
        deviceUpgradeMap.setCreateDate(new Date());
        deviceUpgradeMap.setDeviceGroupId(deviceGroupIds);
        deviceUpgradeMap.setYstenId(deviceCodes);
        deviceUpgradeMap.setType(Type.valueOf(type));
        return this.appUpgradeMapRepository.save(deviceUpgradeMap);
    }

    @Override
    public String saveMap(AppUpgradeTask upgradeTask, String type, String deviceGroupIds, String deviceCodes) {
        if (StringUtils.isNotBlank(deviceCodes)) {
            String[] _deviceCodes = deviceCodes.split(",");
            for (String deviceCode : _deviceCodes) {
                Device device = this.deviceRepository.getDeviceByCode(deviceCode);
                if (device == null) {
                    return "未能找到设备code为" + deviceCode + "的设备，请确认!";
                }
            }
        }
        AppUpgradeMap deviceUpgradeMap = new AppUpgradeMap();
        List<Long> deviceGroups = new ArrayList<Long>();
        // String device = upgradeTask.getDeviceGroupIds();
        if (StringUtils.isNotBlank(deviceGroupIds) && deviceGroupIds != null) {
            deviceGroups = StringUtil.splitToLong(deviceGroupIds);
            for (int i = 0; i < deviceGroups.size(); i++) {
                this.appUpgradeMapRepository.deleteAppUpgradeMapByGroupId(deviceGroups.get(i));
                deviceUpgradeMap.setUpgradeId(upgradeTask.getId());
                deviceUpgradeMap.setCreateDate(new Date());
                deviceUpgradeMap.setDeviceGroupId(deviceGroups.get(i));
                deviceUpgradeMap.setYstenId(deviceCodes);
                deviceUpgradeMap.setType(Type.valueOf(type));
                this.appUpgradeMapRepository.save(deviceUpgradeMap);
            }
        }
        if (StringUtils.isNotBlank(deviceCodes) && deviceCodes != null) {
            String[] deviceCode = deviceCodes.split(",");
            if (deviceCode.length > 0) {
                for (int i = 0; i < deviceCode.length; i++) {
                    this.appUpgradeMapRepository.deleteAppUpgradeMapByCode(deviceCode[i]);
                    deviceUpgradeMap.setUpgradeId(upgradeTask.getId());
                    deviceUpgradeMap.setCreateDate(new Date());
                    deviceUpgradeMap.setDeviceGroupId(null);
                    deviceUpgradeMap.setYstenId(deviceCode[i]);
                    deviceUpgradeMap.setType(Type.valueOf(type));
                    this.appUpgradeMapRepository.save(deviceUpgradeMap);
                }
            }
        }
        return null;
    }

    // @Override
    // public UpgradeTask getByVersionSeq(int versionSeq) {
    // return this.upgradeTaskRepository.getByVersionSeq(versionSeq);
    // }
    @Override
    public boolean updateById(AppUpgradeTask upgradeTask) {
        // List<Long> upgradeIds = new ArrayList<Long>();
        // upgradeIds.add(upgradeTask.getId());
        // this.appUpgradeMapRepository.deleteByUpgradeId(upgradeIds);
        return this.appUpgradeTaskRepository.updateAppUpgradeTask(upgradeTask);
    }

    private boolean saveUserAppMap(AppUpgradeTask upgradeTask, String type, String userId, Long userGroupIds) {
        UserAppUpgradeMap userUpgradeMap = new UserAppUpgradeMap();
        userUpgradeMap.setUpgradeId(upgradeTask.getId());
        userUpgradeMap.setCreateDate(new Date());
        userUpgradeMap.setUserGroupId(userGroupIds);
        userUpgradeMap.setUserId(userId);
        userUpgradeMap.setType(UserAppUpgradeMap.Type.valueOf(type));
        return this.appUpgradeTaskRepository.saveUserAppMap(userUpgradeMap);
    }

    @Override
    public String saveUserMap(AppUpgradeTask upgradeTask, String type, String userGroupIds, String userId) {
        if (StringUtils.isNotBlank(userId)) {
            String[] _userIds = userId.split(",");
            for (String _userId : _userIds) {
                Customer user = this.customerRepository.getCustomerByUserId(_userId);
                if (user == null) {
                    return "未能找到用户外部编码为" + _userId + "的用户，请确认!";
                }
            }
        }
        UserAppUpgradeMap userUpgradeMap = new UserAppUpgradeMap();
        List<Long> userGroups = new ArrayList<Long>();
        if (StringUtils.isNotBlank(userGroupIds) && userGroupIds != null) {
            userGroups = StringUtil.splitToLong(userGroupIds);
            for (int i = 0; i < userGroups.size(); i++) {
                this.appUpgradeTaskRepository.deleteUserUpgradeMapByUserGroupId(userGroups.get(i));
                userUpgradeMap.setUpgradeId(upgradeTask.getId());
                userUpgradeMap.setCreateDate(new Date());
                userUpgradeMap.setUserGroupId(userGroups.get(i));
                userUpgradeMap.setUserId(userId);
                userUpgradeMap.setType(UserAppUpgradeMap.Type.valueOf(type));
                this.appUpgradeTaskRepository.saveUserAppMap(userUpgradeMap);
            }
        }
        if (StringUtils.isNotBlank(userId) && userId != null) {
            String[] userIds = userId.split(",");
            if (userIds.length > 0) {
                for (int i = 0; i < userIds.length; i++) {
                    this.appUpgradeTaskRepository.deleteAppUpgradeMapByUserId(userIds[i]);
                    userUpgradeMap.setUpgradeId(upgradeTask.getId());
                    userUpgradeMap.setCreateDate(new Date());
                    userUpgradeMap.setUserGroupId(null);
                    userUpgradeMap.setUserId(userIds[i]);
                    userUpgradeMap.setType(UserAppUpgradeMap.Type.valueOf(type));
                    this.appUpgradeTaskRepository.saveUserAppMap(userUpgradeMap);
                }
            }
        }
        return null;
    }

    @Override
    public List<AppUpgradeMap> findMapByUpgradeIdAndType(Long upgradeId, String type) {
        return this.appUpgradeMapRepository.findMapByUpgradeIdAndType(upgradeId, type);
    }

    @Override
    public List<UserAppUpgradeMap> findUserMapByUpgradeIdAndType(Long upgradeId, String type) {
        return this.appUpgradeTaskRepository.findMapByUpgradeIdAndType(upgradeId, type);
    }

    @Override
    public String deleteDeviceAndUserMap(AppUpgradeTask upgradeTask) {
        if (this.appUpgradeMapRepository.deleteAppUpgradeMapByUpgradeId(upgradeTask.getId())
                && this.appUpgradeMapRepository.deleteUserAppUpgradeMapByUpgradeId(upgradeTask.getId())) {
            return Constants.SUCCESS;
        }
        return null;
    }

    @Override
    public String saveDeviceAndUserMap(AppUpgradeTask upgradeTask,  String areaIds,String deviceGroupIds, String deviceCodes,
            String userGroupIds, String userIds) {
        StringBuilder jy = new StringBuilder("");
        String[] _deviceCodes = new String[0];
        List<Long> areaIdList = StringUtil.splitToLong(areaIds);
        // check if the deviceCodes existed
        AppUpgradeTask task = this.appUpgradeTaskRepository.getAppUpgradeTaskById(upgradeTask.getId());
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
                        boolean b= true;
                        AppUpgradeMap map = this.appUpgradeMapRepository.getByYstenIdAndUpgradeId(device.getYstenId(), upgradeTask.getId());
                    	if(map==null){
                    		b= this.saveDeviceMap(task, Constants.DEVICE, device.getYstenId(), null);
                    	}
                        if (b == false) {
                        jy.append( "易视腾编号为：" + device.getYstenId() +"的设备绑定应用升级信息异常!");
                        }
                        break;
                    }
                }
                if (bool == false) {
               	 jy.append("请确定易视腾编号为：" + device.getYstenId() + "的所在区域!"+"\n");
                }
           	}
        }
       
        if (StringUtils.isNotBlank(deviceGroupIds)) {
                List<Long> groupIds = StringUtil.splitToLong(deviceGroupIds);
                if (groupIds != null && groupIds.size() > 0) {
                	boolean b= true;
                    for (Long id : groupIds) {
                        b=this.saveDeviceMap(task, Constants.GROUP, null, id);
                        if (b == false) {
                            jy.append( "设备分组绑定应用升级信息异常!");
                            }
                    }
                }
        }
        if (StringUtils.isNotBlank(jy.toString())) {
            return jy.toString();
        }
        return null;
    }

	@Override
	public Pageable<AppUpgradeTask> findAppUpgradeTaskListByCondition(
			String softwarePackageName, String softCodeName, Integer pageNo,
			Integer pageSize) {
		return this.appUpgradeTaskRepository.findAppUpgradeTaskListByCondition(softwarePackageName, softCodeName, pageNo, pageSize);
	}
}
