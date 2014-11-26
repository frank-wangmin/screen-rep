package com.ysten.local.bss.device.domain;

import java.util.Date;

import com.ysten.area.domain.Area;
import com.ysten.utils.bean.IEnumDisplay;


/**
  *    
  * 类名称：DeviceRule   
  * 类描述： 终端规则信息
  * 修改备注：   
  * @version
 */
public class DeviceRule implements java.io.Serializable {

	private static final long serialVersionUID = -4478794334852120040L;
	
	private Long id;
    private Integer startNo;
    private Integer endNo;
    private Long createOperatorId;
    private Long updateOperatorId;
    private String name;
	private State state = State.USABLE;
	private Date createDate;
	private Date updateDate;
	private Area area;
	private String operateOrgan;
	private Long softwareCodeId;
	private String softwareCode;
	private String description;
	private EpgCode epgCode;
	private DeviceType deviceType;
    private DeviceVendor deviceVendor;
	private String batchCode;

	public DeviceRule() {
	}

	
	public DeviceRule(Long id, Integer startNo, Integer endNo,Long createOperatorId, Long updateOperatorId, 
	        String name, State state,
	        Date createDate, Date updateDate,Area area,String operateOrgan,String batchCode,String softwareCode,
	        String description,Long softwareCodeId,DeviceType deviceType,DeviceVendor deviceVendor,EpgCode epgCode) {
		this.id = id;
		this.startNo = startNo;
		this.endNo = endNo;
		this.createOperatorId = createOperatorId;
		this.updateOperatorId = updateOperatorId;
		this.name = name;
		this.state = state;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.area = area;
		this.operateOrgan = operateOrgan;
		this.batchCode = batchCode;
		this.softwareCode = softwareCode;
		this.description = description;
		this.softwareCodeId = softwareCodeId;
		this.deviceType = deviceType;
		this.deviceVendor = deviceVendor;
		this.epgCode = epgCode;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
	 * 获取规则所在的区域
	 */
	public Area getArea() {
		return this.area;
	}

	/**
	 * 设置规则所在的区域
	 * @param area
	 * 		区域
	 */
	public void setArea(Area area) {
		this.area = area;
	}

	/**
	 * 获取规则起始号
	 * @return
	 */
	public Integer getStartNo() {
		return this.startNo;
	}

	/**
	 * 设置规则的起始号
	 * @param startNo
	 * 			规则的起始号
	 */
	public void setStartNo(Integer startNo) {
		this.startNo = startNo;
	}

	/**
	 * 获取规则的终止号
	 * @return
	 * 		规则的终止号
	 */
	public Integer getEndNo() {
		return this.endNo;
	}

	/**
	 * 设置规则的终止号
	 * @param endNo
	 * 			规则的终止号
	 */
	public void setEndNo(Integer endNo) {
		this.endNo = endNo;
	}
	
	/**
	 * 获取规则的状态
	 * @return
	 */
	public State getState() {
		return this.state;
	}
	
	/**
	 * 设置规则的状态
	 * @param state
	 * 			规则状态
	 */
	public void setState(State state) {
		this.state = state;
	}
	
	/**
	 * 获取规则创建的时间
	 * @return
	 */
	public Date getCreateDate() {
		return this.createDate;
	}

	/**
	 * 设置规则创建的时间
	 * @param createDate
	 * 			规则创建时间
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 获取规则更新的时间
	 * @return
	 */
	public Date getUpdateDate() {
		return this.updateDate;
	}

	/**
	 * 设置规则最后更新的时间
	 * @param updateDate
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * 获取创建操作员ID
	 * @return
	 * 		创建操作员ID
	 */
	public Long getCreateOperatorId() {
		return createOperatorId;
	}

	/**
	 * 设置创建操作员ID
	 * @param createOperatorId
	 * 		创建操作员ID
	 */
	public void setCreateOperatorId(Long createOperatorId) {
		this.createOperatorId = createOperatorId;
	}

	/**
	 * 获得更改操作员ID
	 * @return
	 * 			更改操作员ID
	 */
	public Long getUpdateOperatorId() {
		return updateOperatorId;
	}

	/**
	 * 设置更改操作员ID
	 * @param updateOperatorId
	 * 			更改操作员ID
	 */
	public void setUpdateOperatorId(Long updateOperatorId) {
		this.updateOperatorId = updateOperatorId;
	}

	public Long getSoftwareCodeId() {
        return softwareCodeId;
    }

    public void setSoftwareCodeId(Long softwareCodeId) {
        this.softwareCodeId = softwareCodeId;
    }

    public String getSoftwareCode() {
        return softwareCode;
    }

    public void setSoftwareCode(String softwareCode) {
        this.softwareCode = softwareCode;
    }

    public EpgCode getEpgCode() {
        return epgCode;
    }

    public void setEpgCode(EpgCode epgCode) {
        this.epgCode = epgCode;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public DeviceVendor getDeviceVendor() {
        return deviceVendor;
    }

    public void setDeviceVendor(DeviceVendor deviceVendor) {
        this.deviceVendor = deviceVendor;
    }

	/**
	 * 获取规则名称
	 * @return
	 * 			规则名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置规则名称
	 * @param name
	 * 			规则名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 描述
	 * @return
	 */
	public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOperateOrgan() {
        return operateOrgan;
    }

    public void setOperateOrgan(String operateOrgan) {
        this.operateOrgan = operateOrgan;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    /**
	 * 设备规则状态
	 * @author LI.T
	 * @date 2011-4-23
	 * @fileName DeviceRule.java
	 */
	public enum State implements IEnumDisplay{
		USABLE("可用"),UNUSABLE("不可用");

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
     * EPG版本
     * @author 
     * @date 2012-8-21
     * @fileName Platform.java
     */
    public enum EpgCode implements IEnumDisplay{
        FOUR("易视腾2.0炫动版"),FIVE("易视腾2.5 网动版"),SIX("易视腾3.0 飘动版");
        private String msg;
        private EpgCode(String msg) {
            this.msg = msg;
        }
        @Override
        public String getDisplayName() {
            return this.msg;
        }
        
    }
}