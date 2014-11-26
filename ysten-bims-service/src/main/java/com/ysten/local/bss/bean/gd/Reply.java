package com.ysten.local.bss.bean.gd;

public class Reply {
	private String result = "";
	private String description = "";
	
	public StringBuffer getReply() {
		StringBuffer sb = new StringBuffer();
		sb.append("<Object ElementType=\"Reply\">");
		sb.append("<Property Name=\"Result\">"+result+"</Property>");
		sb.append("<Property Name=\"Description\">"+description+"</Property>");
		sb.append("</Object>");
		return sb;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
