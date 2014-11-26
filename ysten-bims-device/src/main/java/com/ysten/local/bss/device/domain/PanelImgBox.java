package com.ysten.local.bss.device.domain;

import java.io.Serializable;
/**
 * Imgboxs信息
 * @author 
 *
 */
public class PanelImgBox implements Serializable{

	/**
	 * imgBoxId + itemId --> actionUrl  imgUrl  progromId
	 * imgBoxId --> title  imgUrl  progromId
	 */
	private static final long serialVersionUID = -1636985449670879787L;

	private Long id;
	private Long imgBoxId;
	private Long itemId;
	private String title;
	private String imgUrl;
	private String actionUrl;
	private Long progromId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getImgBoxId() {
		return imgBoxId;
	}
	public void setImgBoxId(Long imgBoxId) {
		this.imgBoxId = imgBoxId;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getActionUrl() {
		return actionUrl;
	}
	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}
	public Long getProgromId() {
		return progromId;
	}
	public void setProgromId(Long progromId) {
		this.progromId = progromId;
	}
	
}
