package com.ysten.local.bss.device.repository.mapper;

import java.util.List;

import com.ysten.local.bss.device.domain.AccountDetail;

/**
 * AccountMapper接口
 * 
 * @fileName AccountDetailMapper.java
 */
public interface AccountDetailMapper {
    String BEGIN_TIME = "begintime";
    String END_TIME = "endtime";
    String OPERATE_TYPE = "operateType";
    String CODE = "code";
    String CURRENT_PAGE = "currentPage";
    String ACCOUNT_ID = "accountId";
    String CUSTOMER_CODE = "customerCode";
    String PAGE_SIZE = "pageSize";
    String STARTROW_NO = "startRowNo";

    /**
     * 通过id查询账户信息
     * 
     * @param id
     * @return
     */
    AccountDetail getById(Long id);

    /**
     * 修改
     * 
     * @param accountDetail
     * @return
     */
    int update(AccountDetail accountDetail);

    /**
     * 新增
     * 
     * @param accountDetail
     * @return
     */
    int save(AccountDetail accountDetail);

    /**
     * 获取没有同步的账号详情信息
     * 
     * @return
     */
    List<AccountDetail> findNotSynAccountDetails();
}