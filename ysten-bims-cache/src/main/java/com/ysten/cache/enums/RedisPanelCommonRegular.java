package com.ysten.cache.enums;

/**
 * Created by frank on 14-8-14.
 */
public enum RedisPanelCommonRegular {

    PANEL_FREFIX("clear:type:panel:"), ALL_PANEL_FREFIX("clear:type:allPanel:");

    private String regular;

    private RedisPanelCommonRegular(String regular) {
        this.regular = regular;
    }

    public String getRegular() {
        return regular;
    }
}
