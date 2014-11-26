package com.ysten.local.bss.device.domain;

import java.util.Date;

import com.ysten.area.domain.Area;
import com.ysten.local.bss.device.bean.EShAAAStatus.EPrepareOpen;
import com.ysten.local.bss.util.bean.Required;
import com.ysten.utils.bean.IEnumDisplay;

/**
 * 
 * 类描述： 设备信息 修改备注：
 * 
 * @version
 */
public class Device implements java.io.Serializable {

    private static final long serialVersionUID = -8411087552018770296L;
    @Required
    private Long id;
    @Required
    private String code;
    private String ystenId;
    private String districtCode;
    @Required
    private String sno;
    private String mac;
    @Required
    private Area area;
    @Required
    private City city;
    @Required
    private SpDefine spDefine;
    @Required
    private Date createDate;
    private Date activateDate;
    private Date stateChangeDate;
    @Required
    private Date expireDate;
    @Required
    private State state;
    @Required
    private BindType bindType = BindType.BIND;
    @Required
    private DistributeState distributeState = DistributeState.UNDISTRIBUTE;
    @Required
    private DeviceVendor deviceVendor;
    @Required
    private DeviceType deviceType;
    private String ipAddress;
    private LockType isLock;
    private String productNo;
    private Integer versionSeq;
    private String softCode;
    private SyncType isSync;
    private String description;
    private String groups;
    private EPrepareOpen prepareOpen;//上海AAA，预开通标识字段
    private Long customerId;
    private String customerCode;
    private Integer loopTime;//background image deviceLoopTime
    private String isReturnYstenId; //是否返回ystenId

	public Device() {
    }

    public Device(Long id, String code, Date createDate, Date activateDate, Date stateChangeDate, State state,
            BindType bindType, String description, DeviceVendor deviceVendor, DeviceType deviceType,SpDefine spDefine,
            Area area, String mac, String sno, Date expireDate, String ipAddress,LockType isLock,City city,
            String productNo, String softCode) {
        this.id = id;
        this.code = code;
        this.createDate = createDate;
        this.activateDate = activateDate;
        this.stateChangeDate = stateChangeDate;
        this.state = state;
        this.bindType = bindType;
        this.description = description;
        this.deviceVendor = deviceVendor;
        this.deviceType = deviceType;
        this.sno = sno;
        this.expireDate = expireDate;
        this.area = area;
        this.mac = mac;
        this.ipAddress = ipAddress;
        this.isLock = isLock;
        this.city = city;
        this.spDefine = spDefine;
        this.productNo = productNo;
        this.softCode = softCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    /**
     * 设备类型信息
     * 
     * @return
     */
    public DeviceType getDeviceType() {
        return this.deviceType;
    }

    /**
     * 设置设备类型信息
     * 
     * @param deviceType
     */
    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    /**
     * 获取设备所在的区域
     * 
     * @return
     */
    public Area getArea() {
        return this.area;
    }

    /**
     * 设置设备所在的区域
     * 
     * @param area
     *            区域信息
     */
    public void setArea(Area area) {
        this.area = area;
    }

    /**
     * 获取设备的生产厂商
     * 
     * @return
     */
    public DeviceVendor getDeviceVendor() {
        return this.deviceVendor;
    }

    /**
     * 设置设备的生产厂商
     * 
     * @param deviceFirm
     */
    public void setDeviceVendor(DeviceVendor deviceVendor) {
        this.deviceVendor = deviceVendor;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    /**
     * 获取设备编号
     * 
     * @return
     */
    public String getCode() {
        return this.code;
    }

    /**
     * 获取设备创建时间
     * 
     * @return
     */
    public Date getCreateDate() {
        return this.createDate;
    }

    /**
     * 设置设备创建时间
     * 
     * @param createDate
     *            设备创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取设备的激活时间
     * 
     * @return
     */
    public Date getActivateDate() {
        return this.activateDate;
    }

    /**
     * 设置设备的激活时间
     * 
     * @param activateDate
     *            激活时间
     */
    public void setActivateDate(Date activateDate) {
        this.activateDate = activateDate;
    }

    /**
     * 获取设备状态修改时间
     * 
     * @return
     */
    public Date getStateChangeDate() {
        return this.stateChangeDate;
    }

    /**
     * 获取设备状态修改时间
     * 
     * @param stateChangeDate
     *            设备状态修改时间
     */
    public void setStateChangeDate(Date stateChangeDate) {
        this.stateChangeDate = stateChangeDate;
    }

    /**
     * 获取设备的描述信息
     * 
     * @return
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * 设置设备的描述信息
     * 
     * @param description
     *            设备的描述信息
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 设置设备编码
     * 
     * @param code
     *            设备编码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取设备状态
     * 
     * @return 设备状态
     */
    public State getState() {
        return state;
    }
	public String getYstenId() {
		return ystenId;
	}

	public void setYstenId(String ystenId) {
		this.ystenId = ystenId;
	}
    /**
     * 设置设备状态
     * 
     * @param state
     *            设备状态
     */
    public void setState(State state) {
        this.state = state;
    }

    public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
     * 获取绑定类型
     * 
     * @return 绑定类型
     */
    public BindType getBindType() {
        return bindType;
    }

    /**
     * 设置绑定类型
     * 
     * @param bindType
     *            绑定类型
     */
    public void setBindType(BindType bindType) {
        this.bindType = bindType;
    }
    
    
    public DistributeState getDistributeState() {
		return distributeState;
	}

	public void setDistributeState(DistributeState distributeState) {
		this.distributeState = distributeState;
	}

	public SpDefine getSpDefine() {
		return spDefine;
	}

	public void setSpDefine(SpDefine spDefine) {
		this.spDefine = spDefine;
	}

	public LockType getIsLock() {
        return isLock;
    }

    public void setIsLock(LockType isLock) {
        this.isLock = isLock;
    }
    
	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}


	public String getSoftCode() {
        return softCode;
    }

    public void setSoftCode(String softCode) {
        this.softCode = softCode;
    }

    public SyncType getIsSync() {
		return isSync;
	}

	public void setIsSync(SyncType isSync) {
		this.isSync = isSync;
	}

	public Integer getVersionSeq() {
		return versionSeq;
	}

	public void setVersionSeq(Integer versionSeq) {
		this.versionSeq = versionSeq;
	}

	public String getGroups() {
		return groups;
	}

	public void setGroups(String groups) {
		this.groups = groups;
	}

	public EPrepareOpen getPrepareOpen() {
        return prepareOpen;
    }

    public void setPrepareOpen(EPrepareOpen prepareOpen) {
        this.prepareOpen = prepareOpen;
    }

    public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

    public String getIsReturnYstenId() { return isReturnYstenId; }

    public void setIsReturnYstenId(String isReturnYstenId) { this.isReturnYstenId = isReturnYstenId; }

    /**
     * 设备状态
     * 
     * @author LI.T
     * @date 2011-4-23
     * @fileName Device.java
     */
    public enum State implements IEnumDisplay {
        NONACTIVATED("未激活"), ACTIVATED("激活"), NOTUSE("停用"),PAUSED("暂停"),RETURN("退回"),RECYCLE("回收"),ALLOCATED("调拨");

        private String msg;

        private State(String msg) {
            this.msg = msg;
        }

        @Override
        public String getDisplayName() {
            return this.msg;
        }

    }

    /**
     * 设备绑定类型
     * 
     * @author LI.T
     * @date 2011-4-23
     * @fileName Device.java
     */
    public enum BindType implements IEnumDisplay {
        BIND("绑定"), UNBIND("未绑定");
        private String msg;

        private BindType(String msg) {
            this.msg = msg;
        }

        public String getDisplayName() {
            return this.msg;
        }
    }
    
    /**
     * 设备分发类型
     * 
     * @author LI.T
     * @date 2011-4-23
     * @fileName Device.java
     */
    public enum DistributeState implements IEnumDisplay {
        UNDISTRIBUTE("未分发"), DISTRIBUTE("已分发"), PICKUP("已领用");
        private String msg;

        private DistributeState(String msg) {
            this.msg = msg;
        }

        public String getDisplayName() {
            return this.msg;
        }
    }

    public Integer getLoopTime() {
        return loopTime;
    }

    public void setLoopTime(Integer loopTime) {
        this.loopTime = loopTime;
    }

    /**
     * 设备分发类型
     * 
     * @author LI.T
     * @date 2011-4-23
     * @fileName Device.java
     */
    public enum SyncType implements IEnumDisplay {
        WAITSYNC("等待同步"), SYNCING("同步中"), SYNCED("已同步"), FAILED("同步失败");
        private String msg;

        private SyncType(String msg) {
            this.msg = msg;
        }

        public String getDisplayName() {
            return this.msg;
        }
    }

}