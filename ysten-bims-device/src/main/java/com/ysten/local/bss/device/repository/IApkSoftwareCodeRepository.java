package com.ysten.local.bss.device.repository;

import java.util.List;

import com.ysten.local.bss.device.domain.ApkSoftwareCode;
import com.ysten.utils.page.Pageable;


public interface IApkSoftwareCodeRepository {
	Pageable<ApkSoftwareCode> findListByNameAndCode(String code,String name,Integer pageNo,Integer pageSize);
	
	boolean saveApkSoftwareCode(ApkSoftwareCode apkSoftwareCode);

    boolean updateApkSoftwareCode(ApkSoftwareCode apkSoftwareCode);

    ApkSoftwareCode selectByPrimaryKey(Long softCodeId);

    ApkSoftwareCode getSoftwareCodesByCodeAndName(String code,String name);
    
    ApkSoftwareCode findApkSoftwareCodeByCode(String code);
    
    List<ApkSoftwareCode> findAllOfUseble();

}

