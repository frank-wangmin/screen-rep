package com.ysten.local.bss.device.bean;

public class HdcRequestBean {
	
	public static final int STATUS_CODE_ERROR = 1;
	public static final int STATUS_CODE_OK = 0;
	
	public static  final String KEY_STATUS_CODE = "key_statuscode";
	public static  final String KEY_DESCRIPTION = "key_description";
	public static  final String KEY_XML_ROOT_NAME ="key_rootName";
	public static  final String KEY_REQUEST_URL = "key_requestUrl";
	public static  final String KEY_HDC_REQUEST = "key_hdcRequest";
	public static  final String KEY_AUTHORIZATION = "key_authorization";
	public static  final String KEY_CONTENT = "key_content";
	public static  final String KEY_INTERFACE_NAME = "key_interface_name";
	
	public static final String WRONG_NULL_HEADER = "报头信息错误";
	public static final String WRONG_AUTHORIZATION = "鉴权失败";
	public static final String WRONG_CONTENT = "无效的报文";
	public static final String WRONG_INVLIAD_PHONE = "手机号已注册";
	public static final String WRONG_INVLIAD_DEVICE = "设备无效";
	public static final String WRONG_SYSTEM = "系统错误";
	public static final String WRONG_NODEFINE_CUSTOMER = "手机号未注册";
	public static final String WRONG_NODEFINE_GROUP_CUSTOMER = "集团号未注册";
	public static final String WRONG_INVALID_PRODUCT = "无效的产品编号";
	public static final String OPERATION_SUCCESSFULLY = "成功";
	
	public static final String CREATE_ORDER = "0";
	public static final String CANCEL_ORDER = "1";
	public static final String CHANGE_PASSWORD = "4";
	public static final String UN_DEFINED = "10";
	
	
	public static final String EXPIRE_DATE = "2099-12-31 23:59:59";
	
	//---------------------------------订购关系同步接口
	private String phoneNumber;
	private String userCity;
	private String apppId;
	//同步类型：0-订购，1-取消订购
	private String opType;
	//The following fields contained in extendInfo
	private String password;
	private String area;
	private String createDate;
	
	private ExtendInfo[] extendInfo;
	
	//---------------------------------集团信息
	private String accountId;
	private String account;
	private String linkName;
	private String linkPhoneNum;
//	private String opType;
//	private String userCity;
	private String maxTermina;
	
	//----------------------集团成员信息
	private String userId;
	private String bandWidth;
	private String timstamp;
	//follow fields is not mandatory.
	private String sN;
	private String iDNumber;
	private String phone;
	private String sex;
	private String address;
	private String postalCode;
	private String email;
	private String updateTime;
	private String terminalModel;
	
	//----------------------一户多机
	private String oprSrc;
	private String[] extPrdCode;
	
	//-----普客/集客标识
	private boolean isGroupCustomer = false;
	//-----普客/集客标识
	private boolean isGroupMember = false;
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getUserCity() {
		return userCity;
	}
	public void setUserCity(String userCity) {
		this.userCity = userCity;
	}
	public String getApppId() {
		return apppId;
	}
	public void setApppId(String apppId) {
		this.apppId = apppId;
	}
	public String getOpType() {
		return opType;
	}
	public void setOpType(String opType) {
		this.opType = opType;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public ExtendInfo[] getExtendInfo() {
		return extendInfo;
	}
	public void setExtendInfo(ExtendInfo[] extendInfo) {
		this.extendInfo = extendInfo;
		if(null != this.extendInfo) {
			ExtendInfo ext = null;
			for(int index = 0; index < this.extendInfo.length; index++) {
				ext = this.extendInfo[index];
				if("Password".equals(ext.getInfoCode())) {
					this.password = ext.getInfoValue();
					break;
				}
			}
		}
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	public String getLinkPhoneNum() {
		return linkPhoneNum;
	}
	public void setLinkPhoneNum(String linkPhoneNum) {
		this.linkPhoneNum = linkPhoneNum;
	}
	public String getMaxTermina() {
		return maxTermina;
	}
	public void setMaxTermina(String maxTermina) {
		this.maxTermina = maxTermina;
	}
	public boolean isGroupCustomer() {
		return isGroupCustomer;
	}
	public void setGroupCustomer(boolean isGroupCustomer) {
		this.isGroupCustomer = isGroupCustomer;
	}
	
	public boolean isGroupMember() {
		return isGroupMember;
	}
	public void setGroupMember(boolean isGroupMember) {
		this.isGroupMember = isGroupMember;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getBandWidth() {
		return bandWidth;
	}
	public void setBandWidth(String bandWidth) {
		this.bandWidth = bandWidth;
	}
	public String getTimstamp() {
		return timstamp;
	}
	public void setTimstamp(String timstamp) {
		this.timstamp = timstamp;
	}
	public String getsN() {
		return sN;
	}
	public void setsN(String sN) {
		this.sN = sN;
	}
	public String getiDNumber() {
		return iDNumber;
	}
	public void setiDNumber(String iDNumber) {
		this.iDNumber = iDNumber;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getTerminalModel() {
		return terminalModel;
	}
	public void setTerminalModel(String terminalModel) {
		this.terminalModel = terminalModel;
	}
    public String getOprSrc() {
        return oprSrc;
    }
    public void setOprSrc(String oprSrc) {
        this.oprSrc = oprSrc;
    }
    public String[] getExtPrdCode() {
        return extPrdCode;
    }
    public void setExtPrdCode(String[] extPrdCode) {
        this.extPrdCode = extPrdCode;
    }

}
