package com.ysten.local.bss.device.domain;

import java.util.Date;

import com.ysten.local.bss.device.domain.Customer.State;
import com.ysten.utils.bean.IEnumDisplay;

public class CustomerCust implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -581459779895474268L;
	
	private Long id;
	private String custId;
	private String custName;
	private CustType custType = CustType.GROUP;
	private City region;
	private String linkName;
	private String linkTel;
	private State state = State.NORMAL;
	private String custManager;
	private String custDeveloper;
	private Date createDate;
	private Date updateDate;
	private String reservelOne;
	private String reservelTwo;
	private String groupIP;
	private String groupId;
	
	private int maxTermina;
	
	
    public enum CustType implements IEnumDisplay {
    	PERSONAL("个人客户"), CORPORATE("企业客户"), GROUP("集团客户");
        private String msg;
        private CustType(String msg) {
            this.msg = msg;
        }
        @Override
        public String getDisplayName() {
            return this.msg;
        }

    }


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getCustId() {
		return custId;
	}


	public void setCustId(String custId) {
		this.custId = custId;
	}


	public String getCustName() {
		return custName;
	}


	public void setCustName(String custName) {
		this.custName = custName;
	}


	public CustType getCustType() {
		return custType;
	}


	public void setCustType(CustType custType) {
		this.custType = custType;
	}

	public City getRegion() {
		return region;
	}


	public void setRegion(City region) {
		this.region = region;
	}


	public String getLinkName() {
		return linkName;
	}


	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}


	public String getLinkTel() {
		return linkTel;
	}


	public void setLinkTel(String linkTel) {
		this.linkTel = linkTel;
	}


	public State getState() {
		return state;
	}


	public void setState(State state) {
		this.state = state;
	}



	public String getCustDeveloper() {
		return custDeveloper;
	}


	public void setCustDeveloper(String custDeveloper) {
		this.custDeveloper = custDeveloper;
	}




	public String getReservelOne() {
		return reservelOne;
	}


	public void setReservelOne(String reservelOne) {
		this.reservelOne = reservelOne;
	}


	public String getReservelTwo() {
		return reservelTwo;
	}


	public void setReservelTwo(String reservelTwo) {
		this.reservelTwo = reservelTwo;
	}

	public String getGroupIP() {
		return groupIP;
	}
	
	public void setGroupIP(String groupIP) {
		this.groupIP = groupIP;
	}


	public String getGroupId() {
		return groupId;
	}


	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getCustManager() {
		return custManager;
	}
	public void setCustManager(String custManager) {
		this.custManager = custManager;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}


	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public int getMaxTermina() {
		return maxTermina;
	}
	public void setMaxTermina(int maxTermina) {
		this.maxTermina = maxTermina;
	}


}
