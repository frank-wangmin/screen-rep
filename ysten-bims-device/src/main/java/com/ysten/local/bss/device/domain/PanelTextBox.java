package com.ysten.local.bss.device.domain;

import java.io.Serializable;
/**
 * TextBoxs信息
 * @author 
 *
 */
public class PanelTextBox implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2220867697833686924L;

	private Long id;
	private Long textBoxId;
	private String title;
	private int isNew; //是否为新节目 -- 1.是(true) ; 0.否(false)
	private Long progromId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getTextBoxId() {
		return textBoxId;
	}
	public void setTextBoxId(Long textBoxId) {
		this.textBoxId = textBoxId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getIsNew() {
		return isNew;
	}
	public void setIsNew(int isNew) {
		this.isNew = isNew;
	}
	public Long getProgromId() {
		return progromId;
	}
	public void setProgromId(Long progromId) {
		this.progromId = progromId;
	}
	
}
