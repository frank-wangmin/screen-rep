package com.ysten.local.bss.web.service;

import java.util.List;
import com.ysten.local.bss.device.domain.AppSoftwarePackage;
import com.ysten.local.bss.device.exception.DeviceGroupNotFoundException;
import com.ysten.local.bss.device.exception.DeviceNotFoundException;
import com.ysten.local.bss.device.exception.PlatformNotFoundException;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import com.ysten.utils.page.Pageable;

public interface IAppSoftwarePackageService {
	 /**
     * 获取应用的升级信息
     * @param deviceCode
     * @param appSoftwareCode
     * @param versionSeq
     * @return
     * @throws PlatformNotFoundException
     * @throws DeviceNotFoundException
	 * @throws DeviceGroupNotFoundException 
     */
    AppSoftwarePackage getAppUpgradeInfo(String deviceCode, String appSoftwareCode, int versionSeq,int sdkVersion) throws PlatformNotFoundException, DeviceNotFoundException, DeviceGroupNotFoundException;
    
	Pageable<AppSoftwarePackage> findAppSoftwaresByCondition(Long softCodeId,EnumConstantsInterface.PackageType packageType,String name, Integer pageNo, Integer pageSize);
	
	Pageable<AppSoftwarePackage> getListByCondition(String versionName,Integer pageNo,Integer pageSize);	

	boolean deleteByIds(List<Long> ids);
	
	boolean insert(AppSoftwarePackage appSoftwarePackage);
	
	boolean updateById(AppSoftwarePackage appSoftwarePackage);
	
	boolean rendSoftwarePackage(List<AppSoftwarePackage> appSoftwarePackage,Long area)  throws Exception;
	
	List<AppSoftwarePackage> getAll();
	
	AppSoftwarePackage getById(Long id);

    /**
     * insert or update app software package from center
     *
     * @param appSoftwarePackage
     * @return
     */
    public boolean insertOrUpdateSynchronization(AppSoftwarePackage appSoftwarePackage);

    public boolean batchInsertOrUpdateSynchronization(List<AppSoftwarePackage> appSoftwarePackageList);
    
    AppSoftwarePackage getAppSoftwarePackageByName(String versionName);
    
    List<AppSoftwarePackage> getSoftwarePackageBySoftCode(Long softwareCodeId);

}
