package com.ysten.local.bss.panel.enums;

/**
 * Created by frank on 14-5-23.
 */
public enum PanelItemContentType {

//    ICON("icon", "图标"), IMAGE("image", "图片"), VIDEO("video", "视频"), CUSTOM("custom", "自定义"), LIVE("live", "直播"), LIVEURL("liveurl", "直播URL"),ANIMATEICON("animateicon", "动画"),REF("ref", "引用"),;
    IMAGE("image", "图片"),VIDEO("video", "视频"),WIDGET("widget", "Android Widget"),CUSTOM("custom", "自定义");

    private String value;

    private String name;

    private PanelItemContentType(String value, String name) {
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
