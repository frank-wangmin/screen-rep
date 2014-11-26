package com.ysten.local.bss.device.service;

import java.util.List;
import java.util.Map;

import com.ysten.local.bss.device.domain.DeviceSoftwarePackage;
import com.ysten.local.bss.device.exception.DeviceGroupNotFoundException;
import com.ysten.local.bss.device.exception.DeviceNotFoundException;
import com.ysten.local.bss.device.exception.DeviceSoftwareCodeNotFoundException;
import com.ysten.local.bss.device.exception.ParamIsEmptyException;
import com.ysten.local.bss.device.exception.PlatformNotFoundException;
import com.ysten.local.bss.device.exception.UpgradeDeviceInfoNotFoundException;

import javax.management.ServiceNotFoundException;

public interface IUpgradeService {

    DeviceSoftwarePackage getUpgradeSoftwareByCondition(String ystenId, String softwareCode,
            Integer deviceVersionSeq) throws ParamIsEmptyException, DeviceNotFoundException,
            DeviceGroupNotFoundException, PlatformNotFoundException, UpgradeDeviceInfoNotFoundException,
            DeviceSoftwareCodeNotFoundException;
	
    boolean saveUpgradeTaskResultLog(String deviceCode, String ystenId, String softwareCode,
            Integer deviceVersionSeq, Integer versionSeq, String status, Long duration);
}