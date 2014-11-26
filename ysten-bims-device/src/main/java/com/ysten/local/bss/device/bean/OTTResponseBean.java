package com.ysten.local.bss.device.bean;

import java.util.List;

import net.sf.json.JSONArray;

public class OTTResponseBean {
	
	public static final String KEY_STATUS_RESPONSE = "key_res_ott";
	public static final String KEY_RESULT_CODE = "key_result_code";
	public static final String KEY_RESULT_DESC = "key_result_desc";
	
	public static final String OTT_RETURN_OK = "0";
	
//	public static final String VIRIFIED_CODE_OK = "000";
//	public static final String VIRIFIED_DESC_OK = "Verified ok!";
	public static final String VIRIFIED_ERROR_CODE_PARAMETER = "001";
	public static final String VIRIFIED_ERROR_DESC_PARAMETER = "Invalid Parameters";
	public static final String VIRIFIED_ERROR_CODE_OTT = "002";
	public static final String VIRIFIED_ERROR_DESC_OTT = "Connect With OTT Failed";
	public static final String VIRIFIED_ERROR_CODE_EPG = "003";
	public static final String VIRIFIED_ERROR_DESC_EPG = "Connect With EPG Failed";
	public static final String VIRIFIED_ERROR_CODE_SN = "004";
	public static final String VIRIFIED_ERROR_DESC_SN = "Invalid SN";
	public static final String VIRIFIED_ERROR_CODE_CLIENT = "005";
	public static final String VIRIFIED_ERROR_DESC_CLIENT = "Invalid Client";
	public static final String VIRIFIED_ERROR_CODE_DB = "006";
	public static final String VIRIFIED_ERROR_DESC_DB = "DB Operation Failed";
	public static final String VIRIFIED_ERROR_CODE_SYSTEM = "007";
	public static final String VIRIFIED_ERROR_DESC_SYSTEM = "System Error";
	public static final String VIRIFIED_ERROR_CODE_REDIRECT = "100";
	public static final String VIRIFIED_ERROR_CODE_DATA = "008";
	public static final String VIRIFIED_ERROR_DESC_DATA = "Ivalid Data From OTT";
	
	private String result;
	private String spid;
	private String sn;
	private String stbid;
	private String clientid;
	private String timestamp;
	private JSONArray productlist;
	
	private List<Product> products;

	public String getResult() {
		return result;
	}

	public void setRESULT(String result) {
		this.result = result;
	}

	public String getSpid() {
		return spid;
	}

	public void setSPID(String spid) {
		this.spid = spid;
	}

	public String getSn() {
		return sn;
	}

	public void setSN(String sn) {
		this.sn = sn;
	}

	public String getStbid() {
		return stbid;
	}

	public void setSTBID(String stbid) {
		this.stbid = stbid;
	}

	public String getClientid() {
		return clientid;
	}

	public void setCLIENTID(String clientid) {
		this.clientid = clientid;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTIMESTAMP(String timestamp) {
		this.timestamp = timestamp;
	}

	public JSONArray getProductlist() {
		return productlist;
	}

	public void setPRODUCTLIST(JSONArray productlist) {
		this.productlist = productlist;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	

	

}
