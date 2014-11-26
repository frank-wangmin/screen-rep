package com.ysten.local.bss.interfaceUrl.domain;

import com.ysten.area.domain.Area;
import com.ysten.utils.bean.IEnumDisplay;

public class InterfaceUrl {
	private Long id;
	private InterfaceName interfaceName;
	private Area area;
	private String interfaceUrl;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	
	public InterfaceName getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(InterfaceName interfaceName) {
		this.interfaceName = interfaceName;
	}
	public String getInterfaceUrl() {
		return interfaceUrl;
	}
	public void setInterfaceUrl(String interfaceUrl) {
		this.interfaceUrl = interfaceUrl;
	}

	/**
     * 设备状态
     * 
     * @author LI.T
     * @date 2011-4-23
     * @fileName Device.java
     */
    public enum InterfaceName implements IEnumDisplay {
        RECEIVEDEVICE("终端下发"), RECEIVESOFTCODE("终端软件号接收"), RECEIVEAPPSOFTCODE("应用软件号接收"),
        RECEIVESOFTWAREPACKAGE("终端软件包接收"),RECEIVEAPPSOFTWAREPACKAGE("应用软件包接收"),
//        UPGRADEQUERY("升级信息查询"),
        SYNCDEVICE("终端同步"),SYNCUSER("用户同步"),
        GETSCREENID("终端面板包编号获取");
//          ,PANELDISTRIBUTE("面板数据下发");

        private String msg;

        private InterfaceName(String msg) {
            this.msg = msg;
        }

        @Override
        public String getDisplayName() {
            return this.msg;
        }

    }

//    public static void main(String[] args) {
//        for(InterfaceName name: InterfaceName.values()){
//            System.out.println(name.toString().equals("RECEIVEDEVICE"));
//        }
//    }

}
