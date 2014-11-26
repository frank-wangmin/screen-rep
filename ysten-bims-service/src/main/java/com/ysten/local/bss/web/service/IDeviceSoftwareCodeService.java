package com.ysten.local.bss.web.service;


import java.util.List;

import com.ysten.local.bss.device.domain.DeviceSoftwareCode;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import com.ysten.utils.json.JsonResult;
import com.ysten.utils.page.Pageable;

public interface IDeviceSoftwareCodeService {
	
	/**
	 * 插入或根据主键修改
	 * @param deviceSoftwareCode
	 * @return
	 * 		true 成功 or false 失败
	 */
	JsonResult  insertOrUpdate(DeviceSoftwareCode deviceSoftwareCode);

    /**
     * 数据同步
     * @param deviceSoftwareCode
     * @return
     */
    public boolean insertOrUpdateSynchronization(DeviceSoftwareCode deviceSoftwareCode);

    public boolean batchInsertOrUpdateSynchronization(List<DeviceSoftwareCode> deviceSoftwareCodeList);
	
	/**
	 * 根据主键删除对象
	 * @param ids
	 * @return
	 */
	JsonResult deleteByIds(String ids);

	/**
	 * 根据主键查询对象
	 * @param softCodeId
	 * 				软件标识
	 * @return
	 * 				设备软件信息
	 */
	DeviceSoftwareCode selectByPrimaryKey(Long softCodeId);
	boolean rendSoftCode(List<DeviceSoftwareCode> softwareCodes,Long area)  throws Exception;
	/**
	 * 分页查询对象集
	 * @param name
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Pageable<DeviceSoftwareCode> findEpgSoftwareCodesByCondition(EnumConstantsInterface.Status status, String name, Integer pageNo, Integer pageSize);

	boolean deleteByIds(List<Long> ids);
	
	boolean insert(DeviceSoftwareCode deviceSoftwareCode);
	
	boolean update(DeviceSoftwareCode deviceSoftwareCode);
	
	List<DeviceSoftwareCode> getAll();
	
	DeviceSoftwareCode findSoftwareCodesByName(String name);
	
	DeviceSoftwareCode findBySoftwareCode(String code);
}
