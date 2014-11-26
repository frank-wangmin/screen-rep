package com.ysten.local.bss.panel.enums;

/**
 * 面板类型
 * Created by frank on 14-5-12.
 */
public enum PanelType {

    INTERNAL(1, "内部数据"), REAL_TIME(3, "实时生成"),CUSTOM(4,"自定义");

    private int value;

    private String name;

    private PanelType(int value, String name) {
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
