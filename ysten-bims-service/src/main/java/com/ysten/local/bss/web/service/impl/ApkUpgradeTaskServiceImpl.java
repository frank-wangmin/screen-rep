package com.ysten.local.bss.web.service.impl;

import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.device.domain.ApkUpgradeMap;
import com.ysten.local.bss.device.domain.ApkUpgradeTask;
import com.ysten.local.bss.device.domain.Device;
import com.ysten.local.bss.device.repository.IApkUpgradeMapRepository;
import com.ysten.local.bss.device.repository.IApkUpgradeTaskRepository;
import com.ysten.local.bss.device.repository.IDeviceRepository;
import com.ysten.local.bss.util.FilterSpaceUtils;
import com.ysten.local.bss.web.service.IApkUpgradeTaskService;
import com.ysten.utils.page.Pageable;
import com.ysten.utils.string.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class ApkUpgradeTaskServiceImpl implements IApkUpgradeTaskService {
    @Autowired
    private IApkUpgradeTaskRepository apkUpgradeTaskRepository;
    @Autowired
    private IApkUpgradeMapRepository apkUpgradeMapRepository;
    @Autowired
    private IDeviceRepository deviceRepository;

    @Override
    public Pageable<ApkUpgradeTask> findApkUpgradeTaskListBySoftCodeAndPackage(
            String softwarePackageName, String softCodeName, Integer pageNo,
            Integer pageSize) {
        return this.apkUpgradeTaskRepository.findApkUpgradeTaskListBySoftCodeAndPackage(softwarePackageName, softCodeName, pageNo, pageSize);
    }

    @Override
    public List<ApkUpgradeTask> findAllUpgradeTask() {
        return this.apkUpgradeTaskRepository.findAllUpgradeTask();
    }

    @Override
    public ApkUpgradeTask getById(Long id) {
        return this.apkUpgradeTaskRepository.selectByPrimaryKey(id);
    }

    @Override
    public boolean save(ApkUpgradeTask apkUpgradeTask) {
        return this.apkUpgradeTaskRepository.save(apkUpgradeTask);
    }

    @Override
    public boolean updateById(ApkUpgradeTask apkUpgradeTask) {
        return this.apkUpgradeTaskRepository.updateById(apkUpgradeTask);
    }

    @Override
    public String deleteUpgradeTaskByIds(List<Long> ids) {
        for (Long id : ids) {
            ApkUpgradeTask task = this.getById(id);
            List<ApkUpgradeMap> deviceMap = this.apkUpgradeMapRepository.findMapByUpgradeIdAndType(id, null);
            if (deviceMap != null && deviceMap.size() > 0) {
                return "APK升级任务名:" + task.getTaskName() + ";软件号:" + task.getSoftCodeId().getName() + ";软件包:" + task.getSoftwarePackageId().getVersionName()
                        + "的升级任务 绑定了设备或设备分组，不可删除，请先解绑！";
            }
            this.apkUpgradeTaskRepository.deleteById(id);
        }
        return null;
    }

    @Override
    public List<ApkUpgradeMap> findMapByUpgradeIdAndType(Long upgradeId,
                                                         String type) {
        return this.apkUpgradeMapRepository.findMapByUpgradeIdAndType(upgradeId, type);
    }

    @Override
    public String saveUpgradeTaskMap(Long upgradeTaskId, String areaIds,
                                     String deviceGroupIds, String deviceCodes, String userGroupIds,
                                     String userIds) {
        String result = null;
        String[] _deviceCodes;
//        List<String> deviceCodeList = new ArrayList<String>();
//        List<Long> areaIdList = StringUtil.splitToLong(areaIds);
        StringBuilder jy = new StringBuilder();

        this.apkUpgradeMapRepository.deleteApkUpgradeMapByType(upgradeTaskId, Constants.GROUP);
        if (StringUtils.isNotBlank(deviceGroupIds)) {
            result = this.saveMap(upgradeTaskId, Constants.GROUP, deviceGroupIds, null);
            if (result != null) {
                jy.append(result + "\n");
            }
        }
        if (StringUtils.isNotBlank(deviceCodes)) {
            _deviceCodes = deviceCodes.split(",");
            List<String> ystenIdList = new ArrayList<String>();
            for (String deviceCode : _deviceCodes) {
                if (StringUtils.isEmpty(deviceCode)) {
                    continue;
                }
                deviceCode = FilterSpaceUtils.replaceBlank(deviceCode);
                ystenIdList.add(deviceCode);
            }
            /*List<Device> deviceList = this.deviceRepository.findDeviceListByYstenIds(_deviceCodes);
            if (deviceList.size() == 0) {
                jy.append("未能找到你所上传的所有设备序列号的设备，请确认!");
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
                        jy.append("未能找到设备序列号为：" + ystenIdList.get(i) + "的设备，请确认!" + "\n");
                    }
                }
            }
            for (Device device : deviceList) {
                boolean bool = false;
                for (Long areaId : areaIdList) {
                    if (areaId.equals(device.getArea().getId())) {
                        bool = true;
                        deviceCodeList.add(device.getYstenId());
                        break;
                    }
                }
                if (bool == false) {
                    jy.append("请确定设备序列号为：" + device.getYstenId() + "的所在区域!" + "\n");
                }
            }*/
            if (!CollectionUtils.isEmpty(ystenIdList)) {
                result = this.saveMap(upgradeTaskId, Constants.DEVICE, null, StringUtils.join(ystenIdList, ","));
                if (result != null) {
                    jy.append(result + "\n");
                }
            }
        }
        if (StringUtils.isNotBlank(jy.toString())) {
            return jy.toString();
        }
        return result;
    }

    public String saveMap(Long upgradeTaskId, String type, String deviceGroupIds, String deviceCodes) {
        List<Long> deviceGroups = new ArrayList<Long>();
        if (StringUtils.isNotBlank(deviceGroupIds) && deviceGroupIds != null) {
            deviceGroups = StringUtil.splitToLong(deviceGroupIds);
            int index = 0;
            List<ApkUpgradeMap> maps = new ArrayList<ApkUpgradeMap>();
            for (Long deviceGroup : deviceGroups) {
                ApkUpgradeMap deviceUpgradeMap = new ApkUpgradeMap();
                deviceUpgradeMap.setUpgradeId(upgradeTaskId);
                deviceUpgradeMap.setCreateDate(new Date());
                deviceUpgradeMap.setDeviceGroupId(deviceGroup);
                deviceUpgradeMap.setYstenId(deviceCodes);
                deviceUpgradeMap.setType(type);
                maps.add(deviceUpgradeMap);
                index++;
                if (index % Constants.BULK_NUM == 0) {
                    this.apkUpgradeMapRepository.bulkSaveApkUpgradeMap(maps);
                    maps.clear();
                }
            }
            if (maps.size() > 0) {
                this.apkUpgradeMapRepository.bulkSaveApkUpgradeMap(maps);
                maps.clear();
            }
        }
        if (StringUtils.isNotBlank(deviceCodes) && deviceCodes != null) {
            String[] deviceCode = deviceCodes.split(",");
            int index = 0;
            List<ApkUpgradeMap> maps = new ArrayList<ApkUpgradeMap>();
            if (deviceCode.length > 0) {
                this.apkUpgradeMapRepository.deleteMapByYstenIdsAndUpgradeId(deviceCode, upgradeTaskId);
                for (String y : deviceCode) {
                    ApkUpgradeMap deviceUpgradeMap = new ApkUpgradeMap();
                    deviceUpgradeMap.setUpgradeId(upgradeTaskId);
                    deviceUpgradeMap.setCreateDate(new Date());
                    deviceUpgradeMap.setYstenId(y);
                    deviceUpgradeMap.setDeviceGroupId(null);
                    deviceUpgradeMap.setType(type);
                    maps.add(deviceUpgradeMap);
                    index++;
                    if (index % Constants.BULK_NUM == 0) {
                        this.apkUpgradeMapRepository.bulkSaveApkUpgradeMap(maps);
                        maps.clear();
                    }
                }
                if (maps.size() > 0) {
                    this.apkUpgradeMapRepository.bulkSaveApkUpgradeMap(maps);
                    maps.clear();
                }
            }
        }
        return null;
    }

    @Override
    public boolean deleteUpgradeTaskMap(List<Long> ids) {
        return this.apkUpgradeMapRepository.deleteUpgradeTaskMap(ids);
    }
}
