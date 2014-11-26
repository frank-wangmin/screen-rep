package com.ysten.local.bss.area.domian;

public class AreaChildBean extends AreaBean {
	private Long pid;
	public AreaChildBean(Long id, String text, Long pid){
		super(id, text);
		this.pid = pid;
	}
	public AreaChildBean(Long id, Long pid, String text, Boolean expanded){
		super(id, text, expanded);
		this.pid = pid;
	}
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
}
