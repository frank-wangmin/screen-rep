package com.ysten.local.bss.web.service;


import java.util.List;
import com.ysten.local.bss.device.domain.AppSoftwareCode;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import com.ysten.utils.page.Pageable;

public interface IAppSoftwareCodeService {

    /**
     * 分页查询对象集
     *
     * @param name
     * @param pageNo
     * @param pageSize
     * @return
     */
    Pageable<AppSoftwareCode> findAppSoftwareCodesByCondition(EnumConstantsInterface.Status status, String name, Integer pageNo, Integer pageSize);

    /**
     * 根据主键查询对象
     *
     * @param softCodeId 软件标识
     * @return 设备软件信息
     */
    AppSoftwareCode selectByPrimaryKey(Long softCodeId);
    
    boolean rendSoftCode(List<AppSoftwareCode> softwareCodes,Long area)  throws Exception;

    boolean deleteByIds(List<Long> ids);

    boolean insert(AppSoftwareCode appSoftwareCode);

    boolean update(AppSoftwareCode appSoftwareCode);

    List<AppSoftwareCode> getAll();

    /**
     * insert or update app software code from center
     *
     * @param appSoftwareCode
     * @return
     */
    public boolean insertOrUpdateSynchronization(AppSoftwareCode appSoftwareCode);

    public boolean batchInsertOrUpdateSynchronization(List<AppSoftwareCode> appSoftwareCodeList);

    AppSoftwareCode findSoftwareCodesByName(String name);
	
    AppSoftwareCode findBySoftwareCode(String code);
}
