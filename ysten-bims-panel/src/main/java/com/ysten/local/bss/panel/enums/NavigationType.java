package com.ysten.local.bss.panel.enums;

/**
 * Created by frank on 14-5-30.
 */
public enum NavigationType {

    HEAD_NAV(1,"上部导航"),ROOT_NAV(2,"下部导航");

    private int value;

    private String name;

    private NavigationType(int value, String name) {
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
