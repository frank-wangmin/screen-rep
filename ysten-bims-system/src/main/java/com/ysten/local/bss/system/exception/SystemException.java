package com.ysten.local.bss.system.exception;

import com.ysten.local.bss.system.vo.ResultInfo;

@SuppressWarnings("serial")
public class SystemException extends Exception {
	private ResultInfo errorInfo;

	public SystemException(ResultInfo errorInfo) {
		super(errorInfo.getInfo());
		this.errorInfo = errorInfo;
	}

	public ResultInfo getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(ResultInfo errorInfo) {
		this.errorInfo = errorInfo;
	}

	
}
