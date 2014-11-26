package com.ysten.local.bss.device.domain;

import com.ysten.utils.bean.IEnumDisplay;

/**
	 * 充值类型
	 * @fileName AccountDetail.java
	 */
	public enum RechargeType implements IEnumDisplay{
        YEEPAY("易宝"),YSTEN("易视滕");
        
        private String msg;
        private RechargeType(String msg){
            this.msg = msg;
        }
        @Override
        public String getDisplayName() {
            return this.msg;
        }
	}