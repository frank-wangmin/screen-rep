package com.ysten.local.bss.panel.domain;

import java.io.Serializable;

import com.ysten.utils.bean.IEnumDisplay;

public class LvomsDistrictCodeUrl implements Serializable {
	private static final long serialVersionUID = -3881526001872658575L;

	private Long id;
	private String districtCode;
	private Long packageId;
	private String url;
	private String status;
	private String packageName;

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getPackageId() {
		return packageId;
	}

	public void setPackageId(Long packageId) {
		this.packageId = packageId;
	}
	
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public enum Status implements IEnumDisplay {
        ONLINE("已上线"), OFFLINE("已下线");
        
        private String msg;

        private Status(String msg) {
            this.msg = msg;
        }

        @Override
        public String getDisplayName() {
            return this.msg;
        }

    }
}