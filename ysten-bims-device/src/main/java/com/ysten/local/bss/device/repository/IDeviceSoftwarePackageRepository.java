package com.ysten.local.bss.device.repository;

import java.util.List;

import com.ysten.local.bss.device.bean.DeviceUpgradeCondition;
import com.ysten.local.bss.device.domain.DeviceSoftwarePackage;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import com.ysten.utils.page.Pageable;

public interface IDeviceSoftwarePackageRepository {
	
	
	/**
	 * 插入或根据主键修改
	 * @param deviceSoftwarePackage
	 * @return
	 * 		true 成功 or false 失败
	 */
	Boolean insertOrUpdate(DeviceSoftwarePackage deviceSoftwarePackage);

	Pageable<DeviceSoftwarePackage> getListByCondition(String versionName,Integer pageNo,Integer pageSize);
	List<DeviceSoftwarePackage> getAll();

	/**
	 * 根据主键删除记录
	 * @param softwareId
	 * @return
	 */
	Boolean deleteByPrimaryKey(Long softwareId);
	
	/**
	 * 根据主键查询记录
	 * @param softwareId
	 * @return
	 */
	DeviceSoftwarePackage selectByPrimaryKey(Long softwareId);
	
	/**
	 * 根据条件查询对象集合
	 * @param condition
	 * @return
	 */
	Pageable<DeviceSoftwarePackage> findEpgDeviceSoftwaresByCondition(DeviceUpgradeCondition condition);
	
	/**
	 * 根据全量ID查询数据
	 * @param fullIds
	 * @return
	 */
	List<DeviceSoftwarePackage> findEpgDeviceSoftwaresByFullIds(List<Long> fullIds);
	
	/**
	 * 
	 * @param softwareIdList
	 * @param softCodeId
	 * @param deviceVersionSeq
	 * @param packageType
	 * @return
	 */
	List<DeviceSoftwarePackage> findbyCondition(List<Long> softwareIdList, Long softCodeId, Integer deviceVersionSeq, EnumConstantsInterface.PackageType packageType);

	boolean deleteByIds(List<Long> ids);
	
	boolean insert(DeviceSoftwarePackage deviceSoftwarePackage);
	
	boolean updateById(DeviceSoftwarePackage DeviceSoftwarePackage);

    public Boolean insertSynchronization(DeviceSoftwarePackage deviceSoftwarePackage);

    public Boolean updateSoftwarePackage(DeviceSoftwarePackage deviceSoftwarePackage);
    
    DeviceSoftwarePackage getSoftwarePackageByName(String versionName);
    
    List<DeviceSoftwarePackage> getSoftwarePackageBySoftCode(Long softwareCodeId);
    
    boolean updateDistributeStateById(Long id,String distributeState);
}
