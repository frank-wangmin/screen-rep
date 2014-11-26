package com.ysten.local.bss.device.domain;

import com.ysten.utils.bean.IEnumDisplay;

/**
 * 终端类型
 * @fileName TerminalType.java
 */
public enum TerminalType implements IEnumDisplay{
	TV("TV客户端"),PC("PC客户端");
		
	private String msg;
	private TerminalType(String msg){
		this.msg = msg;
	}
	@Override
	public String getDisplayName() {
		return this.msg;
	}
	
}