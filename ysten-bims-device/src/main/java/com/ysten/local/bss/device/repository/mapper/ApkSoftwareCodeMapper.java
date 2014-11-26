package com.ysten.local.bss.device.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.device.domain.ApkSoftwareCode;

public interface ApkSoftwareCodeMapper {

    int deleteByPrimaryKey(Long id);
    int insert(ApkSoftwareCode record);
    ApkSoftwareCode selectByPrimaryKey(Long id);
    int updateByPrimaryKey(ApkSoftwareCode record);
    List<ApkSoftwareCode> findListByNameAndCode(@Param("code")String code,@Param("name")String name,@Param("pageNo")Integer pageNo, @Param("pageSize")Integer pageSize);
    int getCountByNameAndCode(@Param("code")String code,@Param("name")String name);
    ApkSoftwareCode selectByCode(@Param("code")String code);
    ApkSoftwareCode getSoftwareCodesByCodeAndName(@Param("code")String code,@Param("name")String name);
    List<ApkSoftwareCode> findAllOfUseble();
}