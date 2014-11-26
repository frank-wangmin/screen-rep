package com.ysten.local.bss.device.service;

import com.ysten.local.bss.device.domain.ApkSoftwarePackage;
import com.ysten.local.bss.device.exception.*;

public interface IApkUpgradeService {

    ApkSoftwarePackage getApkUpgradeSoftwareByCondition(String ystenId, String softwareCode,Integer deviceVersionSeq)
            throws ParamIsEmptyException, DeviceNotFoundException,
            DeviceGroupNotFoundException, PlatformNotFoundException, UpgradeDeviceInfoNotFoundException,
            Exception;
	
    boolean saveApkUpgradeTaskResultLog(String deviceCode, String ystenId, String softwareCode,
                                     Integer deviceVersionSeq, Integer versionSeq, String status, Long duration);
}