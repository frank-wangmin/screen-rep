package com.ysten.local.bss.enums;

/**
 * Created by frank on 14-7-8.
 */
public enum OnOff {

    ON("ON", "开机"), OFF("OFF", "关机");

    private String value;

    private String name;

    private OnOff(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }
}
