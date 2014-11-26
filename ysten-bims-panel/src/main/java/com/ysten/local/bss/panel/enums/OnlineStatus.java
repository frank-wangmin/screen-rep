package com.ysten.local.bss.panel.enums;

/**
 * 在线状态
 * Created by frank on 14-5-12.
 */
public enum OnlineStatus {

    INIT(0, "初始"), ONLINE(99, "上线"), DOWNLINE(-99, "下线");

    private int value;

    private String name;

    private OnlineStatus(int value, String name) {
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
