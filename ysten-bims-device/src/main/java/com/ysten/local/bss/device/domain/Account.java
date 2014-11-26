package com.ysten.local.bss.device.domain;

import java.util.Date;

import com.ysten.utils.bean.IEnumDisplay;

/**
 * 
 * 类名称：Account 类描述： 账号信息 修改备注：
 * 
 * @version
 */
public class Account implements java.io.Serializable {
    private static final long serialVersionUID = -7059331243549997464L;

    private Long id;
    private String code;
    private String payPassword;
    private String customerId;
    private State state = State.NORMAL;
    private Long balance;
    private Date createDate;
    private String customerCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerCode() {
        return this.customerCode;
    }

    public void setCoustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    /**
     * 账号消费
     * 
     * @param charge
     */
    public void consum(Long charge) {
        this.balance = this.balance - charge;
    }

    /**
     * 账号充值
     * 
     * @param charge
     */
    public void recharge(Long charge) {
        this.balance = this.balance + charge;
    }

    public Account(){}
    
    public Account(Long id, String code, String customerId, String payPassword, State state, Long balance,
            Date createDate) {
        this.id = id;
        this.code = code;
        this.customerId = customerId;
        this.payPassword = payPassword;
        this.state = state;
        this.balance = balance;
        this.createDate = createDate;
    }

    public boolean isNormal() {
        return getState() == State.NORMAL;
    }

    /**
     * 设置账号密码
     * 
     * @param password
     *            账号密码
     */
    public void setPayPassword(String password) {
        this.payPassword = password == null ? null : password.trim();
    }

    /**
     * 获取账号状态
     * 
     * @return 账号状态
     */
    public State getState() {
        return state;
    }

    /**
     * 设置账号状态
     * 
     * @param state
     *            账号状态
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * 获取账号余额
     * 
     * @return 账号余额（单位为：分）
     */
    public Long getBalance() {
        return balance;
    }

    /**
     * 设置账号余额
     * 
     * @param balance
     *            账号余额（单位为：分）
     */
    public void setBalance(Long balance) {
        this.balance = balance;
    }

    /**
     * 获取账号创建时间
     * 
     * @return 账号创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置账号创建时间
     * 
     * @param createDate
     *            账号创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public enum State implements IEnumDisplay {
        NORMAL("正常"), UNUSABLE("停用"), CANCEL("销户");

        private String msg;

        private State(String msg) {
            this.msg = msg;
        }

        @Override
        public String getDisplayName() {
            return msg;
        }
    }

    /**
     * 账号类类型
     * 
     * @author LI.T
     * @date 2011-5-9
     * @fileName Account.java
     */
    public enum AccountType implements IEnumDisplay {
        PERSION("个人");

        private String msg;

        private AccountType(String msg) {
            this.msg = msg;
        }

        @Override
        public String getDisplayName() {
            return this.msg;
        }
    }

    /**
     * 信用等级
     * 
     * @author LI.T
     * @date 2011-5-9
     * @fileName Account.java
     */
    public enum CreditRate implements IEnumDisplay {
        NOMAL("普通");

        private String msg;

        private CreditRate(String msg) {
            this.msg = msg;
        }

        @Override
        public String getDisplayName() {
            return this.msg;
        }

    }
}