package com.ysten.local.bss.web.service;

import java.util.List;

import com.ysten.local.bss.device.domain.DeviceSoftwarePackage;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import com.ysten.utils.json.JsonResult;
import com.ysten.utils.page.Pageable;

public interface IDeviceSoftwarePackageService {
	
	/**
	 * 插入或根据主键修改
	 * @param DeviceSoftwarePackage
	 * @return
	 * 		true 成功 or false 失败
	 */
	JsonResult  insertOrUpdate(DeviceSoftwarePackage DeviceSoftwarePackage);

    /**
     * 中心同步数据
     * @param deviceSoftwarePackage
     * @return
     */
    public boolean insertOrUpdateSynchronization(DeviceSoftwarePackage deviceSoftwarePackage);

    public boolean batchInsertOrUpdateSynchronization(List<DeviceSoftwarePackage> deviceSoftwarePackageList);
	
	/**
	 * 根据主键删除对象
	 * @param ids
	 * @return
	 */
	JsonResult deleteByIds(String ids);

	/**
	 * 根据主键查询对象
	 * @param softwareId
	 * 				设备软件包标识
	 * @return
	 * 				设备软件信息
	 */
	DeviceSoftwarePackage selectByPrimaryKey(Long softwareId);
	
	boolean rendSoftwarePackage(List<DeviceSoftwarePackage> deviceSoftwarePackage,Long area)  throws Exception;

	/**
	 * 分页查询对象集
	 * @param name
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Pageable<DeviceSoftwarePackage> findEpgDeviceSoftwaresByCondition(Long softCodeId, EnumConstantsInterface.PackageType packageType, String name, Integer pageNo, Integer pageSize);

	Pageable<DeviceSoftwarePackage> getListByCondition(String versionName,Integer pageNo,Integer pageSize);

	boolean deleteByIds(List<Long> ids);
	
	boolean insert(DeviceSoftwarePackage deviceSoftwarePackage);
	
	boolean updateById(DeviceSoftwarePackage DeviceSoftwarePackage);
	
	List<DeviceSoftwarePackage> getAll();
	
	DeviceSoftwarePackage getSoftwarePackageByName(String versionName);
	
    List<DeviceSoftwarePackage> getSoftwarePackageBySoftCode(Long softwareCodeId);

}
