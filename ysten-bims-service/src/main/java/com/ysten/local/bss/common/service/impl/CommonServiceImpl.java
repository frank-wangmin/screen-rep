package com.ysten.local.bss.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysten.local.bss.common.service.ICommonService;
import com.ysten.local.bss.device.domain.Customer;
import com.ysten.local.bss.device.repository.ICustomerRepository;

@Service
public class CommonServiceImpl implements ICommonService {

    @Autowired
    private ICustomerRepository customerRepository;

    @Override
    public boolean updateCustomerAndUserPackageInfo(Customer customer) {
        return this.customerRepository.updateCustomer(customer);
    }
    
    @Override
    public boolean saveCustomerAndUserPackageInfo(Customer customer) {
        return this.customerRepository.saveCustomer(customer);
    }
    
} 