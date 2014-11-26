package com.ysten.local.bss.panel.enums;

/**
 * Created by frank on 14-6-11.
 */
public enum YesOrNo {

    NO(0,"否"),YES(1, "是");

    private int value;

    private String name;

    private YesOrNo(int value, String name){
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
