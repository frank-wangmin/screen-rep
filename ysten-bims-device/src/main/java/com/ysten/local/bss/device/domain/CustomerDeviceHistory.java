package com.ysten.local.bss.device.domain;

import java.util.Date;

import com.ysten.area.domain.Area;
import com.ysten.local.bss.device.domain.Device.State;

/**
 * 
 * 类名称：CustomerDeviceHistory 类描述： 用户设备关系历史记录 
 * 
 * @author 
 * 
 */
public class CustomerDeviceHistory implements java.io.Serializable {
    
    private static final long serialVersionUID = -7059331243549997464L;

    private Long id;
    private Long deviceId;
    private String deviceCode;
    private String ystenId;
    private String deviceSno;
    private Date deviceActivateDate;
    private Date deviceCreateDate;
    private State deviceState;
    private String customerCode;
    private String userId;
    private String customerOuterCode;
    private String loginName;
    private String phone;
    private Date customerActivateDate;
    private Date customerCreateDate;
    private Area area;
	private City city;
    private Date createDate;
    private String description;

    public CustomerDeviceHistory(){}
   
    public CustomerDeviceHistory(Long id, Long deviceId, String deviceCode,
			String ystenId, String deviceSno, Date deviceActivateDate,
			Date deviceCreateDate, State deviceState, String customerCode,
			String userId, String customerOuterCode, String loginName,
			String phone, Date customerActivateDate, Date customerCreateDate,
			Area area, City city, Date createDate, String description) {
		this.id = id;
		this.deviceId = deviceId;
		this.deviceCode = deviceCode;
		this.ystenId = ystenId;
		this.deviceSno = deviceSno;
		this.deviceActivateDate = deviceActivateDate;
		this.deviceCreateDate = deviceCreateDate;
		this.deviceState = deviceState;
		this.customerCode = customerCode;
		this.userId = userId;
		this.customerOuterCode = customerOuterCode;
		this.loginName = loginName;
		this.phone = phone;
		this.customerActivateDate = customerActivateDate;
		this.customerCreateDate = customerCreateDate;
		this.area = area;
		this.city = city;
		this.createDate = createDate;
		this.description = description;
	}


	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }


    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getYstenId() {
        return ystenId;
    }

    public void setYstenId(String ystenId) {
        this.ystenId = ystenId;
    }

	public Date getDeviceActivateDate() {
		return deviceActivateDate;
	}

	public void setDeviceActivateDate(Date deviceActivateDate) {
		this.deviceActivateDate = deviceActivateDate;
	}

	public String getCustomerOuterCode() {
		return customerOuterCode;
	}

	public void setCustomerOuterCode(String customerOuterCode) {
		this.customerOuterCode = customerOuterCode;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getCustomerActivateDate() {
		return customerActivateDate;
	}

	public void setCustomerActivateDate(Date customerActivateDate) {
		this.customerActivateDate = customerActivateDate;
	}

	public Date getCustomerCreateDate() {
		return customerCreateDate;
	}

	public void setCustomerCreateDate(Date customerCreateDate) {
		this.customerCreateDate = customerCreateDate;
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

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public String getDeviceSno() {
		return deviceSno;
	}

	public void setDeviceSno(String deviceSno) {
		this.deviceSno = deviceSno;
	}

	public Date getDeviceCreateDate() {
		return deviceCreateDate;
	}

	public void setDeviceCreateDate(Date deviceCreateDate) {
		this.deviceCreateDate = deviceCreateDate;
	}

	public State getDeviceState() {
		return deviceState;
	}

	public void setDeviceState(State deviceState) {
		this.deviceState = deviceState;
	}
	
}