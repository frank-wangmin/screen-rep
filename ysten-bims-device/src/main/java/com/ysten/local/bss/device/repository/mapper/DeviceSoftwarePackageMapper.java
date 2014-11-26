package com.ysten.local.bss.device.repository.mapper;

import java.util.List;
import java.util.Map;

import com.ysten.local.bss.device.bean.DeviceUpgradeCondition;
import com.ysten.local.bss.device.domain.DeviceSoftwarePackage;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import org.apache.ibatis.annotations.Param;


public interface DeviceSoftwarePackageMapper {
	
	DeviceSoftwarePackage findById(long id);

	/**
	 * 插入记录
	 * @param deviceSoftwarePackage
	 * @return
	 */
	int insert(DeviceSoftwarePackage deviceSoftwarePackage);

    /**
     * 中心同步数据
     * @param deviceSoftwarePackage
     * @return
     */
    Integer insertSynchronization(DeviceSoftwarePackage deviceSoftwarePackage);

	/**
	 * 根据主键查询记录
	 * @param softwareId
	 * @return
	 */
	DeviceSoftwarePackage selectByPrimaryKey(Long softwareId);

	/**
	 * 根据主键修改记录
	 * @param DeviceSoftwarePackage
	 * @return
	 */
	int updateByPrimaryKey(DeviceSoftwarePackage DeviceSoftwarePackage);

	/**
	 * 根据主键删除记录
	 * @param softwareId
	 * @return
	 */
	int deleteByPrimaryKey(Long softwareId);
	
	/**
	 * 根据条件查询对象集合
	 * @param condition
	 * @return
	 */
	List<DeviceSoftwarePackage> findDeviceSoftwaresByCondition(DeviceUpgradeCondition condition);

	/**
	 * 根据条件查询对象个数
	 * @param condition
	 * @return
	 */
	int countDeviceSoftwaresByCondition(DeviceUpgradeCondition condition);
	
	/**
	 * 根据全量ID查询数据
	 * @param fullIds
	 * @return
	 */
	List<DeviceSoftwarePackage> findDeviceSoftwaresByFullIds(@Param("fullSoftwareIds") List<Long> fullIds);
	
	List<DeviceSoftwarePackage> getListByCondition(@Param("versionName") String versionName,@Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);
	int delete(@Param("ids")List<Long> ids);
	int  getCountByCondition(@Param("versionName") String versionName);
	/**
	 * 
	 * @param softwareIdList
	 * @param softCodeId
	 * @param deviceVersionSeq
	 * @param packageType
	 * @return
	 */
	List<DeviceSoftwarePackage> findbyCondition(@Param("softwareIdList") List<Long> softwareIdList,
                                            @Param("softCodeId") Long softCodeId,
                                            @Param("deviceVersionSeq") Integer deviceVersionSeq,
                                            @Param("packageType") EnumConstantsInterface.PackageType packageType);

	/**
	 * 根据版本号查找版本记录
	 * @param version
	 * @return
	 */
	DeviceSoftwarePackage findDeviceSoftwarePackageByVersionAndSoftCodeId(@Param("version")String version, @Param("codeId")Long codeId);
	List<DeviceSoftwarePackage> getAll();
	
	
	DeviceSoftwarePackage findLatestSoftwarePackageByDevice(Map<String, Object> paramters);
	
	DeviceSoftwarePackage findLatestSoftwarePackageByUser(Map<String, Object> paramters);
	
	DeviceSoftwarePackage findLatestSoftwarePackageByDeviceGroup(Map<String, Object> paramters);
	
	DeviceSoftwarePackage findLatestSoftwarePackageByUserGroup(Map<String, Object> paramters);

	DeviceSoftwarePackage getSoftwarePackageByName(@Param("versionName") String versionName);
	List<DeviceSoftwarePackage> getSoftwarePackageBySoftCode(@Param("softwareCodeId") Long softwareCodeId);

    List<DeviceSoftwarePackage> getSoftwarePackageListBySoftCodeIdAndVersionSeq(Map<String, Object> paramters);
	
	int updateDistributeStateById(@Param("id")Long id,@Param("distributeState")String distributeState);
}
