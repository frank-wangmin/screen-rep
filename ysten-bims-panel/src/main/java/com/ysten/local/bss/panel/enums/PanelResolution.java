package com.ysten.local.bss.panel.enums;

/**
 * 面板分辨率
 * Created by zhangch on 14-11-6.
 */
public enum PanelResolution {

	RESOLUTION_720p("720p", "720p分辨率"), RESOLUTION_1080p("1080p", "1080p分辨率");

    private String value;

    private String name;

    private PanelResolution(String value, String name) {
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
