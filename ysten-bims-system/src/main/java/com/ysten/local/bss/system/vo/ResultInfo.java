package com.ysten.local.bss.system.vo;

public class ResultInfo {
	/**
	 * 异常代号
	 */
	private Long code;
	/**
	 * 异常信息
	 */
	private String info;

	public ResultInfo(Long code, String info) {
		super();
		this.code = code;
		this.info = info;
	}

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public ResultInfo() {
		super();
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
    /**
     * 重置错误信息
     */
	public ResultInfo modify(ResultInfo errorInfo,String newInfo){
		ResultInfo error = new ResultInfo();
		error.setCode(errorInfo.getCode());
		error.setInfo(newInfo);
		return error;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((info == null) ? 0 : info.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResultInfo other = (ResultInfo) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (info == null) {
			if (other.info != null)
				return false;
		} else if (!info.equals(other.info))
			return false;
		return true;
	}
}
