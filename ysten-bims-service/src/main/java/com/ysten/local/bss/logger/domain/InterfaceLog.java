package com.ysten.local.bss.logger.domain;

import java.util.Date;

public class InterfaceLog {
    private Long id;
    private String interfaceName;
    private String caller;
    private Date callTime;
    private String input;
    private String output;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getInterfaceName() {
        return interfaceName;
    }
    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }
    public String getCaller() {
        return caller;
    }
    public void setCaller(String caller) {
        this.caller = caller;
    }
    public Date getCallTime() {
        return callTime;
    }
    public void setCallTime(Date callTime) {
        this.callTime = callTime;
    }
    public String getInput() {
        return input;
    }
    public void setInput(String input) {
        this.input = input;
    }
    public String getOutput() {
        return output;
    }
    public void setOutput(String output) {
        this.output = output;
    }
}
