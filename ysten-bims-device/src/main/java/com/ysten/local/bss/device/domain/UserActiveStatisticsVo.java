package com.ysten.local.bss.device.domain;

public class UserActiveStatisticsVo {
	private String cityName;
	private Long activeUser;
	private Long activeUsers;
	private Long account;
	private Long accounts;
	private Long cancelUser;
	private Long cancelUsers;
	
    public Long getCancelUser() {
		return cancelUser;
	}
	public void setCancelUser(Long cancelUser) {
		this.cancelUser = cancelUser;
	}
	public Long getCancelUsers() {
		return cancelUsers;
	}
	public void setCancelUsers(Long cancelUsers) {
		this.cancelUsers = cancelUsers;
	}
	public String getCityName() {
        return cityName;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    public Long getActiveUser() {
        return activeUser;
    }
    public void setActiveUser(Long activeUser) {
        this.activeUser = activeUser;
    }
    public Long getActiveUsers() {
        return activeUsers;
    }
    public void setActiveUsers(Long activeUsers) {
        this.activeUsers = activeUsers;
    }
    public Long getAccount() {
        return account;
    }
    public void setAccount(Long account) {
        this.account = account;
    }
    public Long getAccounts() {
        return accounts;
    }
    public void setAccounts(Long accounts) {
        this.accounts = accounts;
    }
}
