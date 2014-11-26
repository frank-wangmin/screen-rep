package com.ysten.local.bss.panel.enums;

/**
 * Created by frank on 14-5-23.
 */
public enum PanelItemActionType {

    //OPEN_LINK(1,"打开链接"),OPEN_APP(2,"打开应用"),INSTALL(3,"安装应用");
	//1:播放视频 2:跳转其他Launcher页面 3:打开指定网页 4:跳转其他应用 5:widget动作
	VIDEO(1,"播放视频"),JUMP_LAUNCHER(2,"跳转其他Launcher页面"),SPECIFIED_WEBPAGE(3,"打开指定网页"),JUMP_APPLICATION(4,"跳转其他应用"),WIDGET_ACTION(5,"widget动作");
    private int value;

    private String name;

    private PanelItemActionType(int value, String name) {
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
