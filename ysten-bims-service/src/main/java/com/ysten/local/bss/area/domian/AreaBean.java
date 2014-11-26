package com.ysten.local.bss.area.domian;

public class AreaBean {
	private Long id;
	private String text;
	private Boolean expanded;
	public AreaBean(Long id, String text){
		this(id, text, new Boolean(false));
	}
	public AreaBean(Long id, String text, Boolean expanded){
		this.id = id;
		this.text = text;
		this.expanded = expanded;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Boolean getExpanded() {
		return expanded;
	}
	public void setExpanded(Boolean expanded) {
		this.expanded = expanded;
	}
}
