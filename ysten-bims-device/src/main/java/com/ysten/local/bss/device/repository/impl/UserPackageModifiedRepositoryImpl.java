package com.ysten.local.bss.device.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysten.local.bss.device.domain.UserPackageInfoModified;
import com.ysten.local.bss.device.repository.IUserPackageModifiedRepository;
import com.ysten.local.bss.device.repository.mapper.UserPackageInfoModifiedMapper;


@Repository
public class UserPackageModifiedRepositoryImpl implements IUserPackageModifiedRepository {

    @Autowired
    UserPackageInfoModifiedMapper userPackageInfoModifiedMapper;
    
    @Override
    public UserPackageInfoModified getById(Long id) {
        return userPackageInfoModifiedMapper.getById(id);
    }

    @Override
    public List<UserPackageInfoModified> findByCustomerIdAndPsId(String customerCode, String psId, String productType) {
        return userPackageInfoModifiedMapper.findByCustomerIdAndPsId(customerCode, psId, productType);
    }

    @Override
    public List<UserPackageInfoModified> findValidUserPackageInfo(String customerCode, String psId, String productType) {
        return userPackageInfoModifiedMapper.findValidUserPackageInfo(customerCode, psId, productType);
    }

    @Override
    public List<UserPackageInfoModified> findByCustomerId(String customerCode) {
        return this.userPackageInfoModifiedMapper.findByCustomerId(customerCode);
    }

    @Override
    public int saveUserPackageInfo(UserPackageInfoModified userPackageInfo) {
        return this.userPackageInfoModifiedMapper.saveUserPackageInfo(userPackageInfo);
    }

    @Override
    public boolean upadteUserPackageInfo(UserPackageInfoModified userPackageInfo) {
        return this.userPackageInfoModifiedMapper.upadteUserPackageInfo(userPackageInfo) == 1;
    }

    @Override
    public UserPackageInfoModified getSingleUserPackageInfo(String customerCode, String outterCode) {
        return this.userPackageInfoModifiedMapper.getUserPackageInfo(customerCode, null, outterCode);
    }

    @Override
    public UserPackageInfoModified getBasicUserPackageInfo(String customerCode, Long productId) {
        return this.userPackageInfoModifiedMapper.getUserPackageInfo(customerCode, productId, null);
    }

    @Override
    public List<UserPackageInfoModified> findByEnableCustomerId(String customerCode) {
        return this.userPackageInfoModifiedMapper.findByEnableCustomerId(customerCode);
    }

}
