package com.ysten.local.bss.device.domain;

import com.ysten.area.domain.Area;
import com.ysten.local.bss.device.domain.Device.SyncType;
import com.ysten.local.bss.util.bean.Required;
import com.ysten.utils.bean.IEnumDisplay;

import java.util.Date;

/**
 * 用户信息
 * 
 * @author xuyesheng
 * 
 */
public class Customer implements java.io.Serializable {

    private static final long serialVersionUID = -7830051843104833941L;

    private Long id;
    @Required
    private String code; // CNTVUserId
    // add new fields for anhui ott

    private String outerCode; //子平台用户code
    @Required
    private String userId; // UserId
    @Required
    private String loginName;
    private String password; // Password
    private String realName;
    private String nickName;
    @Required
    private CustomerType customerType = CustomerType.PERSONAL;
    @Required
    private State state;
    @Required
    private LockType isLock = LockType.UNLOCKED;
    private String custId;
    @Required
    private Area area;
    @Required
    private City region;
    private String districtCode;
    public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	@Required
    private Date createDate;// DateCreated
    private Date activateDate;// DateActivated
    @Required
    private Date serviceStop;
    private Date cancelledDate;
    private Date updateTime;
    private Date stateChangeDate;
    @Required
    private IdentityType identityType = IdentityType.IDCARD;
    private String identityCode; // IDNumber
    private Integer age;
    @Required
    private Sex sex = Sex.MAN; // Sex
    @Required
    private String source;
    @Required
    private String phone; // Phone
    private String rate; // BandWidth
    private String mail; // email
    private String zipCode; // PostalCode
    private String address; // Address
    private String productList;   
    private String county; // Areacode
    private String terminalModel;
    private Date suspendedDate;
    private String profession;
    private String hobby;
    private String deviceCode;
    private String outterDeviceCode;
    private String province;
    private String verificationCode;
    private Date startDate;
    private Date endDate;
    private SyncType isSync;
    private String groups;
    private Integer loopTime;//background image userLoopTime
    
    public String getGroups() {
		return groups;
	}

	public void setGroups(String groups) {
		this.groups = groups;
	}

	public SyncType getIsSync() {
		return isSync;
	}

	public void setIsSync(SyncType isSync) {
		this.isSync = isSync;
	}

	public LockType getIsLock() {
		return isLock;
	}

	public void setIsLock(LockType isLock) {
		this.isLock = isLock;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getOutterDeviceCode() {
        return outterDeviceCode;
    }

    public void setOutterDeviceCode(String outterDeviceCode) {
        this.outterDeviceCode = outterDeviceCode;
    }

    public Date getActivateDate() {
        return activateDate;
    }

    public void setActivateDate(Date activateDate) {
        this.activateDate = activateDate;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Customer() {
        super();
    }

    public boolean isNormal() {
        return getState() == State.NORMAL;
    }

    /**
     * 获取用户编码
     * 
     * @return 用户编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置用户编码
     * 
     * @param code
     *            用户编码
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getOuterCode() {
        return outerCode;
    }

    public void setOuterCode(String outerCode) {
        this.outerCode = outerCode;
    }

    /**
     * 获取用户登录名称
     * 
     * @return 用户登录名
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * 设置用户登录名
     * 
     * @param loginName
     *            用户登录名
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    /**
     * 获取用户的真实姓名
     * 
     * @return 用户真实姓名
     */
    public String getRealName() {
        return realName;
    }

    /**
     * 设置用户的真实姓名
     * 
     * @param realName
     *            用户的真实姓名
     */
    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    /**
     * 获取用户的昵称
     * 
     * @return 用户昵称
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * 设置用户昵称
     * 
     * @param nickName
     *            用户昵称
     */
    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    /**
     * 获取用户密码
     * 
     * @return 用户密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置用户密码
     * 
     * @param password
     *            用户密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 获取用户类型
     * 
     * @return 用户类型
     */
    public CustomerType getCustomerType() {
        return customerType;
    }

    /**
     * 设置用户类型
     * 
     * @param isGroup
     *            用户类型信息
     */
    public boolean setCustomerType(String isGroup) {
        if("1".equals(isGroup)){
        	this.customerType =  CustomerType.GROUP;
        }else if("0".equals(isGroup)){
        	this.customerType = CustomerType.PERSONAL;
        }else{
        	return false;
        }
        return true;
    }

    public void setCustomerType(CustomerType customerType){
    	this.customerType= customerType;
    }
    
    /**
     * 获取用户状态
     * 
     * @return 用户状态
     */
    public State getState() {
        return state;
    }

    /**
     * 设置用户状态
     * 
     * @param state
     *            用户状态
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * 获取用户性别
     * 
     * @return 用户性别
     */
    public Sex getSex() {
        return sex;
    }

    /**
     * 设置用户性别
     * 
     * @param sex
     *            用户性别
     */
    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Integer getLoopTime() {
        return loopTime;
    }

    public void setLoopTime(Integer loopTime) {
        this.loopTime = loopTime;
    }

    /**
     * 获取用户电话
     * 
     * @return 用户电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置用户电话
     * 
     * @param phone
     *            用户电话
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * 获取用户职业
     * 
     * @return 用户职业
     */
    public String getProfession() {
        return profession;
    }

    /**
     * 设置用户职业
     * 
     * @param profession
     *            用户职业
     */
    public void setProfession(String profession) {
        this.profession = profession == null ? null : profession.trim();
    }

    /**
     * 获取用户爱好
     * 
     * @return 用户爱好
     */
    public String getHobby() {
        return hobby;
    }

    /**
     * 设置用户爱好
     * 
     * @param hobby
     *            用户爱好
     */
    public void setHobby(String hobby) {
        this.hobby = hobby == null ? null : hobby.trim();
    }

    /**
     * 获取用户创建时间
     * 
     * @return 用户创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置用户创建时间
     * 
     * @param createDate
     *            用户创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取用户证件类型
     * 
     * @return 用户证件类型
     */
    public IdentityType getIdentityType() {
        return identityType;
    }

    /**
     * 设置用户证件类型
     * 
     * @param identityType
     *            用户证件类型
     */
    public void setIdentityType(IdentityType identityType) {
        this.identityType = identityType;
    }

    /**
     * 获取用户证件号
     * 
     * @return 用户证件号
     */
    public String getIdentityCode() {
        return identityCode;
    }

    /**
     * 设置用户的证件号
     * 
     * @param identityCode
     *            用户证件号
     */
    public void setIdentityCode(String identityCode) {
        this.identityCode = identityCode == null ? null : identityCode.trim();
    }

    /**
     * 获取状态更改时间
     * 
     * @return 状态更改时间
     */
    public Date getStateChangeDate() {
        return stateChangeDate;
    }

    /**
     * 设置状态更改时间
     * 
     * @param stateChangeDate
     *            状态更改时间
     */
    public void setStateChangeDate(Date stateChangeDate) {
        this.stateChangeDate = stateChangeDate;
    }

    /**
     * 获取邮编
     * 
     * @return 邮编
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * 设置邮编
     * 
     * @param zipCode
     *            邮编
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode == null ? null : zipCode.trim();
    }

    /**
     * 获取用户地址
     * 
     * @return 用户地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置用户地址
     * 
     * @param address
     *            用户地址
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * 获取区域信息
     * 
     * @return 区域信息
     */
    public Area getArea() {
        return area;
    }

    /**
     * 设置区域信息
     * 
     * @param area
     *            区域信息
     */
    public void setArea(Area area) {
        this.area = area;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public City getRegion() {
		return region;
	}

	public void setRegion(City region) {
		this.region = region;
	}

	public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public Date getServiceStop() {
		return serviceStop;
	}

	public void setServiceStop(Date serviceStop) {
		this.serviceStop = serviceStop;
	}

	/**
     * 客户类型
     * 
     * @author LI.T
     * @date 2011-4-23
     * @fileName Customer.java
     */
    public enum CustomerType implements IEnumDisplay {
        PERSONAL("个人"), GROUP("团体");

        private String msg;

        private CustomerType(String msg) {
            this.msg = msg;
        }

        @Override
        public String getDisplayName() {
            return this.msg;
        }
    }
    
    /**
     * 客户状态
     * 
     * @author LI.T
     * @date 2011-4-23
     * @fileName Customer.java
     */
    public enum State implements IEnumDisplay {
        NORMAL("正常"), NONACTIVATED("未激活"),UNUSABLE("停用"), CANCEL("销户"), SUSPEND("暂停");

        private String msg;

        private State(String msg) {
            this.msg = msg;
        }

        @Override
        public String getDisplayName() {
            return this.msg;
        }

        public static State getStateByInt(String type){
            State rst = null;
            switch (Integer.parseInt(type.trim())) {
                case 0:
                    rst = SUSPEND;
                    break;
                case 1:
                    rst = NORMAL;
                    break;
                case 2:
                    rst = UNUSABLE;
                    break;
                case 3:
                    rst = UNUSABLE;
                    break;
                case 4:
                    rst = CANCEL;
                    break;
                default:
                    break;
            }
            return rst;
        }

    }

    /**
     * 证件类型
     * 
     * @author LI.T
     * @date 2011-4-23
     * @fileName Customer.java
     */
    public enum IdentityType implements IEnumDisplay {
        IDCARD("身份证"), OFFICERCARD("士官证"), STUDENTCART("学生证"), DRIVERCART("驾驶证"), PASSPORT("护照"), OTHER("其他");
        private String msg;

        private IdentityType(String msg) {
            this.msg = msg;
        }

        @Override
        public String getDisplayName() {
            return this.msg;
        }

    }

    /**
     * 性别
     * 
     * @author LI.T
     * @date 2011-4-23
     * @fileName Customer.java
     */
    public enum Sex implements IEnumDisplay {
        MAN("男"), WOMAN("女");
        private String msg;

        private Sex(String msg) {
            this.msg = msg;
        }

        @Override
        public String getDisplayName() {
            return this.msg;
        }

        public static Sex getSexByInt(String type){
            if("0".equals(type)){
                return WOMAN;
            } else {
                return MAN;
            }
        }
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getTerminalModel() {
        return terminalModel;
    }

    public void setTerminalModel(String terminalModel) {
        this.terminalModel = terminalModel;
    }

    public Date getSuspendedDate() {
        return suspendedDate;
    }

    public void setSuspendedDate(Date suspendedDate) {
        this.suspendedDate = suspendedDate;
    }

    public Date getCancelledDate() {
        return cancelledDate;
    }

    public void setCancelledDate(Date cancelledDate) {
        this.cancelledDate = cancelledDate;
    }

    public String getProductList() {
        return productList;
    }

    public void setProductList(String productList) {
        this.productList = productList;
    }

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("code:").append(this.getCode()).append(",")
        .append("userId:").append(this.getUserId()).append(",")
                .append("createDate:").append(this.getCreateDate()).append(",")
                .append("activateDate:").append(this.getActivateDate()).append(",")
                .append("state:").append(this.getState()).append(",")
                .append("deviceCode:").append(this.getDeviceCode()).append(",")
                .append("region:").append(this.getRegion()).append(",")
                .append("loginName:").append(this.getLoginName())
                .append("phone:").append(this.getPhone()).append(",")
                .append("address:").append(this.getAddress()).append(",")
                .append("zipCode:").append(this.getZipCode()).append(",")
                .append("mail:").append(this.getMail()).append(",")
                .append("identityCode:").append(this.getIdentityCode()).append(",")
                .append("age:").append(this.getAge())
                .append("sex:").append(this.getSex());
        return sb.toString();
    }
}