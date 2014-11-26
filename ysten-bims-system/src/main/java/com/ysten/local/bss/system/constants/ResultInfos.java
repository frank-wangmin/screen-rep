package com.ysten.local.bss.system.constants;

import com.ysten.local.bss.system.vo.ResultInfo;

public class ResultInfos {
	/*错误代码*/
	private static final Long OK_CODE = 0L;
	private static final Long SAVE_ERROR_CODE =  100000L; 
	
	
	/*错误信息*/
	public static final ResultInfo SAVE_ERROR = new ResultInfo(SAVE_ERROR_CODE,"保存发生异常，操作失败");
	public static final ResultInfo OK = new ResultInfo(OK_CODE,"成功");
}
