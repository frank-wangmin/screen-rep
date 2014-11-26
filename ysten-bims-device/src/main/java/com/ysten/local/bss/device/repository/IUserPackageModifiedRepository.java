package com.ysten.local.bss.device.repository;

import java.util.List;

import com.ysten.local.bss.device.domain.UserPackageInfoModified;

public interface IUserPackageModifiedRepository {

    UserPackageInfoModified getById(Long id);

    List<UserPackageInfoModified> findByCustomerIdAndPsId(String customerCode, String psId, String productType);

    List<UserPackageInfoModified> findValidUserPackageInfo(String customerCode, String psId, String productType);

    List<UserPackageInfoModified> findByCustomerId(String customerCode);

    int saveUserPackageInfo(UserPackageInfoModified userPackageInfo);

    boolean upadteUserPackageInfo(UserPackageInfoModified userPackageInfo);

    UserPackageInfoModified getSingleUserPackageInfo(String customerCode, String outterCode);

    UserPackageInfoModified getBasicUserPackageInfo(String customerCode, Long productId);

    List<UserPackageInfoModified> findByEnableCustomerId(String customerCode);
}
