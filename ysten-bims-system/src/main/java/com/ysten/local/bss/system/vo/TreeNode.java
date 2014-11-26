package com.ysten.local.bss.system.vo;

public class TreeNode extends Tree {
	/**
	 * 父节点ID
	 */
	private Long pid;

	public TreeNode(Long id, Long pid, String text) {
		super(id, text);
		this.pid = pid;
	}

	public TreeNode(Long id, Long pid, String text, Boolean expanded) {
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
