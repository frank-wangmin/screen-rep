package com.ysten.cache.enums;

/**
 * Created by frank on 14-8-14.
 */
public enum RedisFacadeCommonRegular {

    ANIMATION_FREFIX("clear:type:animation:"), BACKGROUND_FREFIX("clear:type:background:"), BOOTSTRAP_FREFIX("clear:type:bootstrap:"), PANEL_FREFIX("clear:type:panel:"), ALL_PANEL_FREFIX("clear:type:allPanel:");

    private String regular;

    private RedisFacadeCommonRegular(String regular) {
        this.regular = regular;
    }

    public String getRegular() {
        return regular;
    }
}
