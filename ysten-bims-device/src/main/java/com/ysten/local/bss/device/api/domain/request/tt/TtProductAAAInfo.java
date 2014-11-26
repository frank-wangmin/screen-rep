package com.ysten.local.bss.device.api.domain.request.tt;

/**
 * 同步到AAA的产品包信息domain类
 * @author XuSemon
 * @since 2014-5-29
 */
public class TtProductAAAInfo {
	/**
	 * 虚拟用户标识（宽带账号或者为铁通虚拟号码）
	 */
	private String userId;
	/**
	 * 机顶盒ID
	 */
	private String stbId;
	/**
	 * 广电号，即许可ID 
	 */
	private String tvId;
	/**
	 * 产品代码(铁通侧产品代码，需要和移动侧产品代码做映射关系。)
	 */
	private String productId;
	/**
	 * 操作类型0：退订 1：订购 2：变更（同产品的变更）
	 */
	private String operateType;
	/**
	 * 网络电视类型(01：IPTV、02：互联网电视)
	 */
	private String itvType;
	/**
	 * 用户类型（01：移动受理用户、02：铁通受理用户）
	 */
	private String userType;
	/**
	 * 用户装机地址
	 */
	private String address;
	/**
	 * 联系电话
	 */
	private String contactPhone;
	/**
	 * 业务登记时间
	 */
	private String registerTime;
	/**
	 * 业务起始时间
	 */
	private String serviceBeginTime;
	/**
	 * 业务结束时间
	 */
	private String serviceEndTime;
	/**
	 * 内容的唯一标识
	 */
	private String contentId;
	/**
	 * 内容名称 
	 */
	private String contentName;
	/**
	 * 用户点播时候的节目内容在展示栏目上的路径，每个栏目使用Code来标识
	 */
	private String visitPath;
	/**
	 * 备注信息
	 */
	private String remark;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getStbId() {
		return stbId;
	}
	public void setStbId(String stbId) {
		this.stbId = stbId;
	}
	public String getTvId() {
		return tvId;
	}
	public void setTvId(String tvId) {
		this.tvId = tvId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	public String getItvType() {
		return itvType;
	}
	public void setItvType(String itvType) {
		this.itvType = itvType;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}
	public String getServiceBeginTime() {
		return serviceBeginTime;
	}
	public void setServiceBeginTime(String serviceBeginTime) {
		this.serviceBeginTime = serviceBeginTime;
	}
	public String getServiceEndTime() {
		return serviceEndTime;
	}
	public void setServiceEndTime(String serviceEndTime) {
		this.serviceEndTime = serviceEndTime;
	}
	public String getContentId() {
		return contentId;
	}
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}
	public String getContentName() {
		return contentName;
	}
	public void setContentName(String contentName) {
		this.contentName = contentName;
	}
	public String getVisitPath() {
		return visitPath;
	}
	public void setVisitPath(String visitPath) {
		this.visitPath = visitPath;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
			.append("<message module=\"AAA\" version=\"1.1\">\n")
			.append("<header action=\"REQUEST\" command=\"SERVICENOTICE\"/>\n")
			.append("<body>\n")
			.append("<serviceNotice")
			.append(" userID=").append(userId)
			.append(" stbid=").append(stbId)
			.append(" tvid=").append(tvId)
			.append(" productID=").append(productId)
			.append(" operateType=").append(operateType)
			.append(" itvType=").append(itvType)
			.append(" userType=").append(userType)
			.append(" address=").append(address)
			.append(" contactPhone=").append(contactPhone)
			.append(" registerTime=").append(registerTime)
			.append(" seviceBeginTime=").append(serviceBeginTime)
			.append(" seviceEndTime=").append(serviceEndTime)
			.append(" ContentID=").append(contentId)
			.append(" ContentName=").append(contentName)
			.append(" VisitPath=").append(visitPath)
			.append(" remark=").append(remark)
			.append("/>\n")
			.append("</body>\n")
			.append("</message>");
		return sb.toString();
	}
}
