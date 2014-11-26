package com.ysten.local.bss.device.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.device.domain.UserPackageInfoModified;

public interface UserPackageInfoModifiedMapper {

    UserPackageInfoModified getById(Long id);

    List<UserPackageInfoModified> findByCustomerIdAndPsId(@Param("customerCode") String customerCode,
            @Param("psId") String psId, @Param("productType") String productType);

    List<UserPackageInfoModified> findValidUserPackageInfo(@Param("customerCode") String customerCode,
            @Param("psId") String psId, @Param("productType") String productType);

    List<UserPackageInfoModified> findByCustomerId(String customerCode);

    List<UserPackageInfoModified> findByEnableCustomerId(String customerCode);

    int saveUserPackageInfo(UserPackageInfoModified userPackageInfoModified);

    int upadteUserPackageInfo(UserPackageInfoModified userPackageInfo);

    UserPackageInfoModified getUserPackageInfo(@Param("customerCode") String customerCode, @Param("productId") Long productId,
            @Param("outterCode") String outterCode);

    int findCountByCustomerCode(@Param("customerCode") String customerCode);

}
