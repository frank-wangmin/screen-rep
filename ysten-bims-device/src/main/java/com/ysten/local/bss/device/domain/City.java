package com.ysten.local.bss.device.domain;

import java.io.Serializable;
import java.util.Date;

public class City implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 4636425019398810211L;

	private Long id;

    private String code;

    private String name;

    private Date createDate;
    
    private Long leaderId;
    
    private String distCode;

    public String getDistCode() {
        return distCode;
    }

    public void setDistCode(String distCode) {
        this.distCode = distCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    @Override
    public String toString(){
    	return this.name;
    }

    public Long getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(Long leaderId) {
		this.leaderId = leaderId;
	}

	public static City createCityByCode(String cityCode){
        City city = new City();
        city.setCode(cityCode);
        return city;
    }
}
