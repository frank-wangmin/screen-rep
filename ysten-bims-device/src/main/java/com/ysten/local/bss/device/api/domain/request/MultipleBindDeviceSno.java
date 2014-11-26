package com.ysten.local.bss.device.api.domain.request;

public class MultipleBindDeviceSno {
	private String sno;
	public String getSno() {
		return sno;
	}
	public void setSno(String sno) {
		this.sno = sno;
	}
	@Override
	public String toString(){
		return this.sno;
	}
}
