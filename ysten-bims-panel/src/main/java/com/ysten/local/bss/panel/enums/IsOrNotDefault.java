package com.ysten.local.bss.panel.enums;

/**
 * Created by frank on 14-6-3.
 */
public enum IsOrNotDefault {

    NO_DEFAULT(0,"否"),DEFULT(1, "是");

    private int value;

    private String name;

    private IsOrNotDefault(int value, String name){
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
