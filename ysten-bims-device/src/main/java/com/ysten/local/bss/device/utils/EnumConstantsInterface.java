package com.ysten.local.bss.device.utils;

import com.ysten.utils.bean.IEnumDisplay;

public interface EnumConstantsInterface {
	
	String getDisplayName();
	
	//设备状态
	public enum DeviceStatus implements EnumConstantsInterface,IEnumDisplay{

		ACTIVE("激活的"), INACTIVE("未激活的"),REGISTER("注册");

		private String message;

		private DeviceStatus(String message) {
			this.message = message;
		}

		@Override
		public String getDisplayName() {
			return this.message;
		}

	}

	//设备绑定类型
	public enum BindType implements EnumConstantsInterface,IEnumDisplay{

		BOUNDED("已绑定"), UNBOUNDED("未绑定");

		private String message;

		private BindType(String message) {
			this.message = message;
		}
		@Override
		public String getDisplayName() {
			return this.message;
		}

	}
	
	//通用状态
	public enum Status implements EnumConstantsInterface,IEnumDisplay{

		USABLE("可用"), DISABLED("不可用");

		private String message;

		private Status(String message) {
			this.message = message;
		}
		
		@Override
		public String getDisplayName() {
			return this.message;
		}

	}
	
	//软件包类型
	public enum PackageType implements EnumConstantsInterface,IEnumDisplay{

		INCREMENT("增量"), FULL("全量");

		private String message;

		private PackageType(String message) {
			this.message = message;
		}
		
		@Override
		public String getDisplayName() {
			return this.message;
		}

	}
	
	//软件包状态
	public enum PackageStatus implements EnumConstantsInterface,IEnumDisplay{

		TEST("测试"), RELEASE("发布");

		private String message;

		private PackageStatus(String message) {
			this.message = message;
		}
		
		@Override
		public String getDisplayName() {
			return this.message;
		}

	}
	
	//强制状态
	public enum MandatoryStatus implements EnumConstantsInterface,IEnumDisplay{

		MANDATORY("强制"), NOTMANDATORY("不强制");

		private String message;

		private MandatoryStatus(String message) {
			this.message = message;
		}
		
		@Override
		public String getDisplayName() {
			return this.message;
		}

	}

    //终端分组类型
    public enum DeviceGroupType implements EnumConstantsInterface,IEnumDisplay{
//    	COMMON("通用"), 暂时无此类型的需求
        UPGRADE("升级"), APPUPGRADE("应用升级"), APKUPGRADE("APK升级"), PANEL("面板"),ANIMATION("开机动画"),NOTICE("消息"),BOOTSTRAP("开机引导初始化"),BACKGROUND("背景");

        private String message;

        private DeviceGroupType(String message) {
            this.message = message;
        }

        @Override
        public String getDisplayName() {
            return this.message;
        }

    }
  //用户分组类型
    public enum UserGroupType implements EnumConstantsInterface,IEnumDisplay{
        UPGRADE("升级"), APPUPGRADE("应用升级"),PANEL("面板"),ANIMATION("开机动画"),NOTICE("消息"),BACKGROUND("背景"),PRODUCTPACKAGE("产品包");

        private String message;

        private UserGroupType(String message) {
            this.message = message;
        }

        @Override
        public String getDisplayName() {
            return this.message;
        }

    }

    //服务集类型
    public enum ServiceCollectType implements EnumConstantsInterface,IEnumDisplay{

        DEFAULT("默认"), NORMAL("普通");

        private String message;

        private ServiceCollectType(String message) {
            this.message = message;
        }

        @Override
        public String getDisplayName() {
            return this.message;
        }
    }
    
	//通用状态
	public enum UpgradeResultStatus implements EnumConstantsInterface,IEnumDisplay{

		SUCCESS("0000"), FAILED("9999"), UNDFEFINED("1111");

		private String message;

		private UpgradeResultStatus(String message) {
			this.message = message;
		}
		
		@Override
		public String getDisplayName() {
			return this.message;
		}

	}

    //业务分组类型
    public enum BusinessGroupType implements EnumConstantsInterface,IEnumDisplay{

        USER("USER"), DEVICE("DEVICE");

        private String message;

        private BusinessGroupType(String message) {
            this.message = message;
        }

        @Override
        public String getDisplayName() {
            return this.message;
        }

    }
	
}
