package com.ysten.local.bss.panel.enums;

/**
 * 面板状态
 * Created by frank on 14-5-12.
 */
public enum PanelStatus {

    INVALID(0, "无效"), VALID(1, "有效");

    private int value;

    private String name;

    private PanelStatus(int value,String name){
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }
}
