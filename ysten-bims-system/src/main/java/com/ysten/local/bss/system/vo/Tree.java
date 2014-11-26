package com.ysten.local.bss.system.vo;

public class Tree {
	/**
	 * 节点ID
	 */
	private Long id;
	/**
	 * 节点名称
	 */
	private String text;
	/**
	 * 是否展开。"true"代表展开，"false"代表折叠
	 */
	private Boolean expanded;

	public Tree() {
		super();
	}

	public Tree(Long id, String text) {
		this(id, text, false);
	}

	public Tree(Long id, String text, Boolean expanded) {
		super();
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
