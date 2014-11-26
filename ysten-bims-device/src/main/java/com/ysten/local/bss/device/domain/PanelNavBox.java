package com.ysten.local.bss.device.domain;

import java.io.Serializable;
/**
 * NavBox信息
 * @author 
 *
 */
public class PanelNavBox implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2201971071331161663L;
	private Long id;
	private Long navBoxId;
	private String title;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getNavBoxId() {
		return navBoxId;
	}
	public void setNavBoxId(Long navBoxId) {
		this.navBoxId = navBoxId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
