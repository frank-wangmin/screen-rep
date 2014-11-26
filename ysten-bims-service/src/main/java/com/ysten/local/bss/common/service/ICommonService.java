package com.ysten.local.bss.common.service;

import com.ysten.local.bss.device.domain.Customer;


public interface ICommonService {
    
    /**
     * 更新用户信息及套餐信息(AH)
     * @param customer
     * @param upiList
     * @return
     */
    boolean updateCustomerAndUserPackageInfo(Customer customer);
    /**
     * 更新用户信息及套餐信息(AH)
     * @param customer
     * @param upiList
     * @return
     */
    boolean saveCustomerAndUserPackageInfo(Customer customer);
}