package com.ysten.local.bss.device.bean;

/**
 * 联通营帐系统提供的用户数据
 * 
 * @author Chen Yun
 * 
 */
public class UserDataUpdateRequest {

    /**
     * 流水号
     */
    private String SeqNo;
    /**
     * 更新类型
     */
    private String OrderType;
    /**
     * 用户号码
     */
    private String MDN;
    /**
     * 付费方号码
     */
    private String PayMDN;
    /**
     * SP代码
     */
    private String SPID;
    /**
     * 产品代码
     */
    private String ProductID;
    /**
     * 变更时失效原有产品
     */
    private String OldProductId;
    /**
     * 订购渠道
     */
    private String OrderMethod;
    /**
     * 业务发生时间
     */
    private String SubscriptionTime;
    /**
     * 生效时间
     */
    private String EffectiveDate;
    /**
     * 失效时间
     */
    private String ExpireDate;
    /**
     * 产品附带参数
     */
    private String ProductParams;

    public String getSeqNo() {
        return SeqNo;
    }

    public void setSeqNo(String seqNo) {
        SeqNo = seqNo;
    }

    public String getOrderType() {
        return OrderType;
    }

    public void setOrderType(String orderType) {
        OrderType = orderType;
    }

    public String getMDN() {
        return MDN;
    }

    public void setMDN(String mDN) {
        MDN = mDN;
    }

    public String getPayMDN() {
        return PayMDN;
    }

    public void setPayMDN(String payMDN) {
        PayMDN = payMDN;
    }

    public String getSPID() {
        return SPID;
    }

    public void setSPID(String sPID) {
        SPID = sPID;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getOldProductId() {
        return OldProductId;
    }

    public void setOldProductId(String oldProductId) {
        OldProductId = oldProductId;
    }

    public String getOrderMethod() {
        return OrderMethod;
    }

    public void setOrderMethod(String orderMethod) {
        OrderMethod = orderMethod;
    }

    public String getSubscriptionTime() {
        return SubscriptionTime;
    }

    public void setSubscriptionTime(String subscriptionTime) {
        SubscriptionTime = subscriptionTime;
    }

    public String getEffectiveDate() {
        return EffectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        EffectiveDate = effectiveDate;
    }

    public String getExpireDate() {
        return ExpireDate;
    }

    public void setExpireDate(String expireDate) {
        ExpireDate = expireDate;
    }

    public String getProductParams() {
        return ProductParams;
    }

    public void setProductParams(String productParams) {
        ProductParams = productParams;
    }

}
