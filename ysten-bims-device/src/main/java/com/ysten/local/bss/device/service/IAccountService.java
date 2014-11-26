package com.ysten.local.bss.device.service;

import java.util.List;

import com.ysten.local.bss.device.domain.AccountDetail;
import com.ysten.local.bss.device.domain.AccountDetail.ConsumType;
import com.ysten.local.bss.device.domain.AccountDetail.Result;
import com.ysten.local.bss.device.domain.Account;
import com.ysten.local.bss.device.domain.RechargeType;
import com.ysten.local.bss.device.exception.NotSufficientFundsException;

public interface IAccountService {

    /**
     * create
     * 
     * @param account
     * @return boolean
     */
    boolean createAccount(Account account);

    /**
     * update
     * 
     * @param account
     * @return
     */
    boolean updateAccount(Account account);

    /**
     * 根据账户编码查询账户信息
     * 
     * @param accountCode
     * @return Account
     */
    Account getAccountByAccountCode(String accountCode);
    /**
     * 根据用户Id查找账户信息
     * 
     * @param id
     * @return Account
     */
    Account getAccountByCustomerId(Long customerId);

    /**
     * 
     * @param customerCode
     * @return
     */
    Account getAccountByCustomerId(String customerId);

    /**
     * 获取未同步的账户详情
     * @return
     */
    List<AccountDetail> findNotSynAccountDetails();
    /**
     * 设置用户详情为同步状态
     * @param detailId
     *          accountDetailId
     * @return
     */
    boolean setAccountDetailSyn(Long detailId);
    /**
     * 通过id查询账户信息
     * 
     * @param id
     * @return
     */
    Account getAccountById(Long id);
    /**
     * 记录账号的消费信息
     * 1.创建并写入消费详情
     * 2.从账号中扣除消费金额
     * @param account
     *          消费账号
     * @param customer
     *          消费用户
     * @param charge
     *          消费金额
     * @param outCode
     *          生的订单ID
     * @param consumType
     *          消费类型
     * @param result
     *          消费结果
     * @return
     * @throws NotSufficientFundsException 
     *          余额不足
     */
    boolean accountConsume(Account account, Long charge, String outCode, ConsumType consumType, Result result) throws NotSufficientFundsException;
    /**
     * 账号充值
     * @param account
     *          充值信息
     * @param customer
     *          用户信息
     * @param cardMoney
     *          充值金额
     * @param outCode
     *          外部编码
     * @param rechargeType
     *          充值类型
     * @param result
     *          充值结果
     * @return
     */
    boolean accountRecharge(Account account, Long cardMoney, String outCode,RechargeType rechargeType, Result result);

}
