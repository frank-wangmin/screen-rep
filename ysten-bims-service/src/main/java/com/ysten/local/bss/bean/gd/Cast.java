package com.ysten.local.bss.bean.gd;

public class Cast {
	private String id = "";
	private String action = "";
	private String code = "";
	private String name = "";
	private String personDisplayName = "";
	private String personSortName = "";
	private String personSearchName = "";
	private String firstName = "";
	private String middleName = "";
	private String lastName = "";
	private String sex = "";
	private String birthday = "";
	private String hometown = "";
	private String education = "";
	private String height = "";
	private String weight = "";
	private String bloodGroup = "";
	private String marriage = "";
	private String favorite = "";
	private String webpage = "";
	private String description = "";
	private String result = "";
	private String errorDescription = "";
	
	public StringBuffer getCast() {
		StringBuffer sb = new StringBuffer();
		sb.append("<Object ElementType=\"Cast\" ID=\""+id+"\" Action=\""+action+"\" Code=\""+code+"\">");
		sb.append("<Property Name=\"Name\">"+name+"</Property>");
		sb.append("<Property Name=\"PersonDisplayName\">"+personDisplayName+"</Property>");
		sb.append("<Property Name=\"PersonSortName\">"+personSortName+"</Property>");
		sb.append("<Property Name=\"PersonSearchName\">"+personSearchName+"</Property>");
		sb.append("<Property Name=\"FirstName\">"+firstName+"</Property>");
		sb.append("<Property Name=\"MiddleName\">"+middleName+"</Property>");
		sb.append("<Property Name=\"LastName\">"+lastName+"</Property>");
		sb.append("<Property Name=\"Sex\">"+sex+"</Property>");
		sb.append("<Property Name=\"Birthday\">"+birthday+"</Property>");
		sb.append("<Property Name=\"Hometown\">"+hometown+"</Property>");
		sb.append("<Property Name=\"Education\">"+education+"</Property>");
		sb.append("<Property Name=\"Height\">"+height+"</Property>");
		sb.append("<Property Name=\"Weight\">"+weight+"</Property>");
		sb.append("<Property Name=\"BloodGroup\">"+bloodGroup+"</Property>");
		sb.append("<Property Name=\"Marriage\">"+marriage+"</Property>");
		sb.append("<Property Name=\"Webpage\">"+webpage+"</Property>");
		sb.append("<Property Name=\"Description\">"+description+"</Property>");
		sb.append("<Property Name=\"Result\">"+result+"</Property>");
		sb.append("<Property Name=\"ErrorDescription\">"+errorDescription+"</Property>");
		sb.append("</Object>");
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPersonDisplayName() {
		return personDisplayName;
	}

	public void setPersonDisplayName(String personDisplayName) {
		this.personDisplayName = personDisplayName;
	}

	public String getPersonSortName() {
		return personSortName;
	}

	public void setPersonSortName(String personSortName) {
		this.personSortName = personSortName;
	}

	public String getPersonSearchName() {
		return personSearchName;
	}

	public void setPersonSearchName(String personSearchName) {
		this.personSearchName = personSearchName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getHometown() {
		return hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public String getMarriage() {
		return marriage;
	}

	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}

	public String getFavorite() {
		return favorite;
	}

	public void setFavorite(String favorite) {
		this.favorite = favorite;
	}

	public String getWebpage() {
		return webpage;
	}

	public void setWebpage(String webpage) {
		this.webpage = webpage;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
