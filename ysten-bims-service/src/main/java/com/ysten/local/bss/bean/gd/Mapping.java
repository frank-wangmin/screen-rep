package com.ysten.local.bss.bean.gd;

public class Mapping {
	//接口中的唯一标识
	private String id = "";
	//操作类型  注册－REGIST 更新－UPDATE 删除－DELETE
	private String action = "";
	private String parentType = "";
	private String elementType = "";
	private String parentID = "";
	private String elementID = "";
	private String parentCode = "";
	private String elementCode = "";
	private String type = "";
	private String sequence = "";
	private String validStart = "";
	private String validEnd = "";
	private String result = "";
	private String errorDescription = "";

	public StringBuffer getMapping() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("<Mapping ParentType=\""+parentType+"\" ID=\""+id+"\" Action=\""+action+"\" ElementType=\""+elementType+"\" ParentID=\""+parentID+"\" ElementID=\""+elementID+"\" ParentCode=\""+parentCode+"\" ElementCode=\""+elementCode+"\">");
		sb.append("<Property Name=\"Type\">"+type+"</Property>");
		sb.append("<Property Name=\"Sequence\">"+sequence+"</Property>");
//		sb.append("<Property Name=\"ValidStart\">"+validStart+"</Property>");
//		sb.append("<Property Name=\"ValidEnd\">"+validEnd+"</Property>");
//		sb.append("<Property Name=\"Result\">"+result+"</Property>");
//		sb.append("<Property Name=\"ErrorDescription\">"+errorDescription+"</Property>");
		
		sb.append("</Mapping>");
		
		return sb;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getParentType() {
		return parentType;
	}

	public void setParentType(String parentType) {
		this.parentType = parentType;
	}

	public String getElementType() {
		return elementType;
	}

	public void setElementType(String elementType) {
		this.elementType = elementType;
	}

	public String getParentID() {
		return parentID;
	}

	public void setParentID(String parentID) {
		this.parentID = parentID;
	}

	public String getElementID() {
		return elementID;
	}

	public void setElementID(String elementID) {
		this.elementID = elementID;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getElementCode() {
		return elementCode;
	}

	public void setElementCode(String elementCode) {
		this.elementCode = elementCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getValidStart() {
		return validStart;
	}

	public void setValidStart(String validStart) {
		this.validStart = validStart;
	}

	public String getValidEnd() {
		return validEnd;
	}

	public void setValidEnd(String validEnd) {
		this.validEnd = validEnd;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	
}
