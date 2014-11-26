package com.ysten.local.bss.screen.domain;

import java.io.Serializable;
import java.util.Date;

import com.ysten.area.domain.Area;
import com.ysten.utils.date.DateUtil;

public class ScreenManager implements Serializable {

	private static final long serialVersionUID = -8117512869232271016L;

	private Long id;

	private String name;

	private String url;

	private Date createDate;

	private String description;
	
	private Area area;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCreateDate() {
		return DateUtil.convertDateToString(createDate);
	}

	public Date getCreateDateSelf() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
}
