package com.ysten.local.bss.device.domain;

import com.ysten.utils.bean.IEnumDisplay;

/**
 * 是否锁定
 * @fileName LockType.java
 */
public enum LockType implements IEnumDisplay{
    LOCK("锁定"), UNLOCKED("未锁定");
    private String msg;

    private LockType(String msg) {
        this.msg = msg;
    }

    public String getDisplayName() {
        return this.msg;
    }
}