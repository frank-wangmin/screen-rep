package com.ysten.local.bss.device.domain;

import java.util.Date;

import com.ysten.utils.bean.IEnumDisplay;

/**
 * 账号消费明细
 * 
 * @fileName AccountDetail.java
 */
public class AccountDetail implements java.io.Serializable {

    private static final long serialVersionUID = 1150762949199916930L;

    private Long id;
    private Long currentBalance;
    private Long cost;
    private OperateType operateType;
    private ConsumType consumType;
    private RechargeType rechargeType;
    private String outerCode;
    private Date operateDate;
    private Result result;
    private String operateNo;
    private String businessNo;
    private Long synFlag;
    private String customerId;
    private Account account;
    private String customerCode;

    public AccountDetail(){}
    
    public AccountDetail(Long id, Long currentBalance, Long cost, OperateType operateType, ConsumType consumType,
            RechargeType rechargeType, String outerCode, Date operateDate, Result result, String operateNo,
            String businessNo, Account account, Long synFlag) {
        this.id = id;
        this.currentBalance = currentBalance;
        this.cost = cost;
        this.operateType = operateType;
        this.consumType = consumType;
        this.rechargeType = rechargeType;
        this.outerCode = outerCode;
        this.operateDate = operateDate;
        this.result = result;
        this.operateNo = operateNo;
        this.businessNo = businessNo;
        this.account = account;
        this.synFlag = synFlag;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    

    /**
     * 获取当前余额
     * 
     * @return 当前余额
     */
    public Long getCurrentBalance() {
        return currentBalance;
    }

    /**
     * 设置当前余额
     * 
     * @param currentBalance
     *            当前余额
     */
    public void setCurrentBalance(Long currentBalance) {
        this.currentBalance = currentBalance;
    }

    /**
     * 获取交易金额
     * 
     * @return 交易金额（单位为：分）
     */
    public Long getCost() {
        return cost;
    }

    /**
     * 设置交易金额
     * 
     * @param cost
     *            交易金额（单位为：分）
     */
    public void setCost(Long cost) {
        this.cost = cost;
    }

    /**
     * 获取操作日期
     * 
     * @return 操作日期
     */
    public Date getOperateDate() {
        return operateDate;
    }

    /**
     * 设置操作日期
     * 
     * @param operateDate
     *            操作日期
     */
    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }

    /**
     * 获取操作流水号
     * 
     * @return 操作流水号
     */
    public String getOperateNo() {
        return operateNo;
    }

    /**
     * 设置操作流水号
     * 
     * @param operateNo
     *            操作流水号
     */
    public void setOperateNo(String operateNo) {
        this.operateNo = operateNo == null ? null : operateNo.trim();
    }

    /**
     * 获取业务流水号
     * 
     * @return 业务流水号
     */
    public String getBusinessNo() {
        return businessNo;
    }

    /**
     * 设置业务流水号
     * 
     * @param businessNo
     *            业务流水号
     */
    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo == null ? null : businessNo.trim();
    }

    /**
     * 获取操作类型
     * 
     * @return 操作类型
     */
    public OperateType getOperateType() {
        return operateType;
    }

    /**
     * 设置操作类型
     * 
     * @param operateType
     *            操作类型
     */
    public void setOperateType(OperateType operateType) {
        this.operateType = operateType;
    }

    /**
     * 获取消费类型
     * 
     * @return 消费类型
     */
    public ConsumType getConsumType() {
        return consumType;
    }

    /**
     * 设置消费类型
     * 
     * @param consumType
     *            消费类型
     */
    public void setConsumType(ConsumType consumType) {
        this.consumType = consumType;
    }

    /**
     * 获取充值类型
     * 
     * @return 充值类型
     */
    public RechargeType getRechargeType() {
        return rechargeType;
    }

    /**
     * 设置充值类型
     * 
     * @param rechargeType
     *            充值类型
     */
    public void setRechargeType(RechargeType rechargeType) {
        this.rechargeType = rechargeType;
    }

    /**
     * 获取外部关联编码
     * 
     * @return 外部关联编码
     */
    public String getOuterCode() {
        return outerCode;
    }

    /**
     * 设置外部关联编码
     * 
     * @param outerCode
     *            外部关联编码
     */
    public void setOuterCode(String outerCode) {
        this.outerCode = outerCode;
    }

    /**
     * 获取操作结果
     * 
     * @return 操作结果
     */
    public Result getResult() {
        return result;
    }

    /**
     * 设置操作结果
     * 
     * @param result
     *            操作结果
     */
    public void setResult(Result result) {
        this.result = result;
    }

    /**
     * 获取同步标识位
     * 
     * @return
     */
    public Long getSynFlag() {
        return synFlag;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * 设置同步标识位
     * 
     * @param synFlag
     */
    public void setSynFlag(Long synFlag) {
        this.synFlag = synFlag;
    }

    /**
     * 查询本次余额
     * 
     * @return
     */
    public Long getLastBalance() {
        if (OperateType.CONSUM == this.getOperateType()) {
            return -this.cost + this.currentBalance;
        } else {
            return this.cost + this.currentBalance;
        }
    }

    /**
     * 操作类型
     * 
     * @author LI.T
     * @date 2011-5-9
     * @fileName AccountDetail.java
     */
    public enum OperateType implements IEnumDisplay {
        RECHARGE("充值"), CONSUM("消费");

        private String msg;

        private OperateType(String msg) {
            this.msg = msg;
        }

        @Override
        public String getDisplayName() {
            return this.msg;
        }

    }

    /**
     * 消费类型
     * 
     * @author LI.T
     * @date 2011-5-9
     * @fileName AccountDetail.java
     */
    public enum ConsumType implements IEnumDisplay {
        SINGLE("单片"), ORDER("专题"), BASIC("包月"), APP("应用"), THIRD_APP("第三方");

        private String msg;

        private ConsumType(String msg) {
            this.msg = msg;
        }

        @Override
        public String getDisplayName() {
            return this.msg;
        }
    }

    public enum Result implements IEnumDisplay {
        SUCCESS("成功"), FAIL("失败");
        private String msg;

        private Result(String msg) {
            this.msg = msg;
        }

        @Override
        public String getDisplayName() {
            return this.msg;
        }
    }

}