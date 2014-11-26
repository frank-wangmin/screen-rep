package com.ysten.local.bss.bean;

public interface Constants {
	int BULK_NUM = 5000;
	String DEVICE = "DEVICE";
	String GROUP = "GROUP";
    String USER = "USER";
    /*接口名称*/
    String SELECT_PP_INFO = "selectPpList";
    String GET_NOTICE_INFO = "getNoticeList";
    String GET_BOOTSTRAP_PANEL_PACKAGE_INFO = "getBootStarpList";
    String GET_BACKGROUND_IMAGE_INFO = "getBackgroundImageList";
    String GET_BOOT_ANIMATION_INFO = "getBootAnimationList";
    String GET_UPGRADE_INFO = "getUpgradeList";
    String GET_APP_UPGRADE_INFO = "getAppUpgradeList";
    
    String GET_CUSTOMER_PANEL = "getCustomerPanel";
    String GET_STYLE = "getStyle";
    String GET_DATA = "getData";
    String START = "start";
    String LIMIT = "limit";
    String PAGE_NUMBER = "15";
    String SCRIPT = "script";
    String PAGE_SIZE = "pageSize";
    String PAGE_NO_DEFAULT_VALUE = "0";
    String STATE = "state";
    String PAGE_NO = "pageNo";
    String PARAM_ROWS = "rows";
    String IDS = "ids";
    String ID = "id";
    String PAGE = "page";
    String ADD_EN = "ADD";
    String ONLINE = "ONLINE";
    String OFFLINE = "OFFLINE";
    String SUCCESS = "success";
    String EXISTED = "existed";
    String FAIL = "fail";
    String FAILED = "failed";
    String BINDED = "binded";
    String ISDEFAULT = "isDefault";
    String FREE = "free";
    String ERROR = "error";
    String REPEAT = "repeat";
    String LOGIN_SESSION_OPERATOR = "operator";
    String DEVICE_VENDOR = "deviceVendor";
    String YSTRN_ID = "ystenId";
    String DEVICE_CODE = "deviceCode";
    String DESCRIPTION = "description";
    String AREA_ID = "areaId";
    String DEVICE_TYPE_ID = "deviceTypeId";
    String DEVICE_VENDOR_ID = "deviceVendorId";
    String UN_USABLE = "不可用!";
    String IS_DEFAULT = "已经存在默认类型";
    String USABLE = "可用!";
    String PAR = "par";
    String PROGRAM_SERIES = "program_series";
    String PROGRAM = "program";
    String PARAM_NAME = "name";
    String USER_NOTICT_MAP_GROUP ="GROUP";
    String USER_NOTICT_MAP_USER ="USER";
    String DEVICE_NOTICT_MAP_DEVICE ="DEVICE";
    String DEVICE_NOTICT_MAP_GROUP ="GROUP";
    String ISCENTER = "isCenter";

    //系统模块名称
     /*定价管理*/
    String BASE_PRICE_MAINTAIN = "基本定价维护";
    String DISCOUNT_POLICY_MAINTAIN = "优惠策略维护";
    String LOGIN_MODULE = "管理系统登陆";
    String LVMOS_DISTRICTCODE_URL_MAINTAIN = "电视看点各省URL地址维护";

    /*系统管理*/
    String OPERATOR_INFO_MAINTAIN = "操作员信息维护";
    String ROLE_INFO_MAINTAIN = "角色信息维护";
    String AUTHORITY_INFO_MAINTAIN = "权限信息维护";
    String OPERATOR_ROLE_RELEVANCE = "操作员角色关联";
    String ROLE_AUTHORITY_RELEVANCE = "角色与权限关联";
    String SP_INFO_MAINTAIN = "运营商信息管理";
    String AREA_INFO_MAINTAIN = "区域信息管理";
    String SYSTEM_PARAM_CONFIG = "系统参数配置";
    String TALK_SYSTEM_MAINTAIN = "交互系统维护";
    String CITY_INFO_MAINTAIN = "地市信息维护";
    String SCREEN_MANAGER_MAINTAIN = "屏幕管理维护";
    String SYS_NOTICE_INFO_MAINTAIN = "消息信息维护";
    String BOOT_ANIMATION_INFO_MAINTAIN = "开机动画信息维护";
    String BACKGROUD_INFO_MAINTAIN = "背景轮换信息维护";
    String DEVICE_INTERFACE_URL_INFO_MAINTAIN = "终端接口地址信息维护";
    String SERVICE_INFO_MANAGER_MAINTAIN = "服务信息维护";
    String SERVICE_COLLECT_MANAGER_MAINTAIN = "服务集信息维护";

    /**
     * 日志管理
     */
    String OPERATE_LOG_INFO_MAINTAIN = "操作日志管理";
    String INTERFACE_LOG_INFO_MAINTAIN = "接口日志管理";
    String DEVICE_UPGRADE_RESULT_LOG_MAINTAIN = "终端升级日志管理";
    String APK_UPGRADE_RESULT_LOG_MAINTAIN = "APK升级日志管理";
    /**
     * 统计管理
     */
    String DEVICE_ACTIVE_INFO_STATISTICS_MAINTAIN = "终端激活数据统计管理";
    String USER_ACTIVE_INFO_STATISTICS_MAINTAIN = "用户激活数据统计管理";
    String USER_ACTIVATE_DAY_SUM  = "终端用户定时统计管理";
    /**
     * 终端管理
     */
    String DEVICE_INFO_MAINTAIN = "设备信息维护";
    String DEVICE_IP_INFO_MAINTAIN = "IP地址库信息维护";
    String DEVICE_TYPE_INFO_MAINTAIN = "设备型号信息维护";
    String DEVICE_VENDOR_INFO_MAINTAIN = "设备厂商信息维护";
    String DEVICE_GROUP_INFO_MAINTAIN = "设备厂商信息维护";

    /**
     * 面板管理
     */
    String PANEL_INFO_MAINTAIN = "面板信息维护";
    String PREVIEW_TEMPLATE_MAINTAIN = "预览模板信息维护";
    String PANEL_PACKAGE_MAINTAIN = "面板包信息维护";
    String PANEL_ITEM_MAINTAIN = "面板项项信息维护";
    String PANEL_NAV_MAINTAIN = "导航信息维护";
    String PANEL_ONLINE = "面板上线";
    String PANEL_CONFIG = "面板配置预览";
    String TEMPLATE_CONFIG="模板配置";
    /**
     * 升级信息管理
     */
    String DEVICE_SOFT_CODE_MAINTAIN = "设备软件号信息维护";
    String DEVICE_SOFT_WARE_PACKAGE_MAINTAIN = "设备软件升级包信息维护";
    String UPGRADE_TASK_MAINTAIN = "升级任务信息维护";
    String APP_SOFT_CODE_MAINTAIN = "应用软件号信息维护";
    String APP_SOFT_WARE_PACKAGE_MAINTAIN = "应用软件包信息维护";
    String APP_UPGRADE_TASK_MAINTAIN = "应用升级任务信息维护";
    String APK_SOFT_CODE_MAINTAIN = "apk软件号信息维护";
    String APK_SOFT_WARE_PACKAGE_MAINTAIN = "apk软件包信息维护";
    String APK_UPGRADE_TASK_MAINTAIN = "APK升级任务信息维护";

    /**
     * 用户管理
     */
    String CUSTOMER_INFO_MAINTAIN = "用户信息维护";
    String RELATION_CUSTOMER_DEVICE_MAINTAIN = "终端用户关系维护";
    String CUSTOMER_DEVICE_HISTORY_INFO_MAINTAIN = "用户终端历史信息维护";
    String BIND_CUSTOMER_DEVICE_MAINTAIN = "用户终端维护绑定";
    String USER_GROUP_INFO_MAINTAIN = "用户分组信息维护";

    /**
     * 内容注入管理
     */
    String JMSRASK_INFO_MAINTAIN = "JMS消息维护";
    String PROGRAM_INFO_MAINTAIN = "节目信息维护";
    String PROGRAM_SERIES_INFO_MAINTAIN = "节目集信息维护";
    /**
     * 产品管理
     */
    String PPV_INFO_MAINTAIN = "PPV信息维护";
    String PP_INFO_MAINTAIN = "PP信息维护";
    // 操作类型
    String SYNC_PRODUCT_CONTENT = "同步产品内容";
    String ADD = "新增";
    String MODIFY = "修改";
    String DELETE = "删除";
    String CREATE_ZIP = "创建Zip包";
    String LOCK = "加锁";
    String UNLOCK = "解锁";
    String IMPORT = "导入";
    String RESET_PWD = "密码重置";
    String UNBIND = "终端用户解绑";
    String BIND = "绑定用户终端";
    String RESET_ERROR_TIMES = "重置失败次数";
    String RESET_PUSH_STATUS = "重置推送状态";
    String UPDATE_PUSH_STATUS = "修改推送状态";
    String SYNC_PPV = "同步PPV";
    String PUSH_C2_RELATION_PRODUCT_PACKAGE = "推送C2产品包关系";
    String PUSH_C2_PRODUCT_PACKAGE = "推送C2产品包";
    String DELETE_C2_PRODUCT_PACKAGE = "删除推送C2产品包";
    String PUSH_SH_RELATION_PRODUCT_PACKAGE = "推送SH产品包关系";
    String PUSH_SH_PRODUCT_PACKAGE = "推送SH产品包";
    String DELETE_SH_PRODUCT_PACKAGE = "删除推送C2产品包";
    String UPDATE_SYSTEM_PARAM_CACHE = "更新缓存";
    String GET_ASIAL_PP = "同步亚信PP信息";
    String CUSTOMER_SYNC = "用户同步";
    String CUSTOMER_BIND_USERGROUP = "用户绑定用户组";
    String DISTRIBUTE_SOFT_CODE = "下发软件信息号";
    String DISTRIBUTE_DEVICE = "下发终端";
    String DISTRIBUTE_SOFT_WARE_PACKAGE = "下发软件信息号";
    String LOGIN = "系统登陆";
    String XML_HEAD = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
    String XML_BODY = "<boxs>";
    String XML_ROOT = "</boxs>";
    String SYNS_EPG_PANEL_DATA="同步epg面板数据";
    String DISTRIBUTE_PANEL = "同步省级数据";
    Long AREA_ALL = 0L;
    String PLEASE_SELECT = "请选择";
    String ALL_AREA = "所有区域";
    String CHINA = "全国";
    String DIST_CODE_CHINA = "100000";
    String EPG_DISTRICT_CODE="888888";
    String EPG_NAME="播控系统";
    
    int ZERO = 0;
    int ONE = 1;
    int TWO = 2;
    int THREE = 3;
    int FOUR = 4;
    int FIVE = 5;
    int SIX = 6;
    int SEVEN = 7;
    int EIGHT = 8;
    int NINE = 9;
    int TEN = 10;
    int ELEVEN = 11;
    int TWELEV = 12;
    int THIRTEEN = 13;
    int FOURTEEN = 14;
    int FIFTEEN = 15;
    int SIXTEEN = 16;
    int SEVENTEEN = 17;
    int EIGHTEEN = 18;
    int NINETEEN = 19;
    int TWENTY = 20;
    int TWENTY_ONE = 21;
    int TWENTY_TWO = 22;
    int TWENTY_THREE = 23;
    int TWENTY_FOUR = 24;
    int TWENTY_FIVE = 25;
    int TWENTY_SIX = 26;
    int TWENTY_SEVEN = 27;
    int TWENTY_EIGHT = 28;
    int TWENTY_NINE = 29;
    int THIRTY = 30;
    int THIRTY_ONE = 31;
    int THIRTY_TWO = 32;
    int THIRTY_THREE = 33;
    int THIRTY_FOUR = 34;
    int THIRTY_FIVE = 35;
    int THIRTY_SIX = 36;
    int THIRTY_SEVEN = 37;
    int THIRTY_EIGHT = 38;
    int THIRTY_NINE = 39;
    int FORTY = 40;
    int FORTY_ONE = 41;
    int FORTY_TWO = 42;
    int FORTY_THREE = 43;
    int FORTY_FOUR = 44;
}