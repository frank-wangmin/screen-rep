package com.ysten.local.bss.panel.enums;

/**
 * Created by yaoqy on 14-10-15.
 */
public enum PanelItemType {
    PROGRAM("program", "电视看点节目"), CATEGORY("category", "电视看点栏目"),  CUSTOM("custom", "自定义"),;

    private String value;

    private String name;

    private PanelItemType(String value, String name) {
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
