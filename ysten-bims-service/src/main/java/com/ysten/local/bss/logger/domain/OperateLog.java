package com.ysten.local.bss.logger.domain;

import java.util.Date;

public class OperateLog {
    /**
     * 记录标识
     */
    private Integer id;
    /**
     * 操作类型
     */
    private String operationType;
    /**
     * 主键(节目集/栏目)
     */
    private String primaryKeyValue;
    /**
     * 模块名称
     */
    private String moduleName;
    /**
     * 操作时间
     */
    private Date operationTime;
    /**
     * 操作员
     */
    private String operator;
    /**
     * 操作IP
     */
    private String operationIp;
    /**
     * 描述信息
     */
    private String description;

    public OperateLog() {
        super();
    }

    public OperateLog(String operationType, String primaryKeyValue, String moduleName, Date operationTime,
            String operator, String operationIp, String description) {
        super();
        this.operationType = operationType;
        this.primaryKeyValue = primaryKeyValue;
        this.moduleName = moduleName;
        this.operationTime = operationTime;
        this.operator = operator;
        this.operationIp = operationIp;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrimaryKeyValue() {
        return primaryKeyValue;
    }

    public void setPrimaryKeyValue(String primaryKeyValue) {
        this.primaryKeyValue = primaryKeyValue == null ? null : primaryKeyValue.trim();
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName == null ? null : moduleName.trim();
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getOperationIp() {
        return operationIp;
    }

    public void setOperationIp(String operationIp) {
        this.operationIp = operationIp == null ? null : operationIp.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

}
