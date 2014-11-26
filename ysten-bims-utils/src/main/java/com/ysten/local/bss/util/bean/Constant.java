package com.ysten.local.bss.util.bean;

/**
 * @author cwang
 * @version 2014-3-21 上午10:29:45
 */
public class Constant {
	public static final int BULK_NUM = 5000;
	public static final String MAP_DEVICE = "DEVICE";
	public static final String MAP_GROUP = "GROUP";
    public static final String SPLIT_LINE = "_";
    
    public static final String LOG_SUCCESS = "成功";
    
    public static final String LOG_FAILED = "失败";
    
    public static final String LOG_UNDFEFINED = "未定义";

    public static final String SUCCESS = "success";
    
    public static final String DEVICE = "device";

    public static final String FAILED = "failed";

    public static final String UTF_ENCODE = "UTF-8";
    
    public static final String TRUE = "true";

    public static final String FALSE = "false";
    
    public static final String SYSTEM_CODE_ILLEGAL = "system code illegal, systemCode: ";

    public static final String UPLOAD_PATH = "upload";
    
    public static final String BUSINESS_STATE_USEABLE = "USEABLE";
    
    public static final String CUSTOMER_SOURCE = "bims";
    
    public static final String CUSTOMER_RATE = "0";

    public static final String REDIS_DEVICE_KEY = "device";
    public static final String REDIS_CUSTOMER_KEY = "customer";
    public static final String REDIS_ACCOUNT_KEY = "account";

    public static final String REDIS_ANIMATION_KEY = "animation";
    public static final String REDIS_ANIMATION_USER_KEY = "animation_user_map";
    public static final String REDIS_ANIMATION_DEVICE_KEY = "animation_device_map";

    public static final String REDIS_DEVICE_CUSTOMER_ACCOUNT_MAP_KEY = "device_customer_account_map";

    public static final String REDIS_SYSTEM_CONFIG_KEY = "system_config_";
    
    /*业务：背景轮换   开机动画*/
    public static final String COMMON_PACKAGE = "ysten:local:bss:device:";
    /*消息*/
    public static final String NOTICE_PACKAGE = "ysten:local:bss:service:";

    /******************* panel *****************************/

    public static final String PANEL_PACKAGE = "ysten:local:bss:panel:";

    public static final String REDIS_NAVIGATION_KEY = "navigation_";
    public static final String REDIS_PANEL_ITEM_KEY = "panel_item_";
    public static final String REDIS_PANEL_KEY = "panel_";
    public static final String REDIS_PANEL_PACKAGE_KEY = "panel_package_";
    public static final String REDIS_PANEL_ITEM_MAP_KEY = "panel_item_map_";
    public static final String REDIS_PANEL_PACKAGE_MAP_KEY = "panel_package_map_";
    public static final String REDIS_PREVIEW_ITEM_DATA_KEY = "preview_item_data_";
    public static final String REDIS_PREVIEW_TEMPLATE_KEY = "preview_template_";
    public static final String REDIS_PREVIEW_ITEM = "preview_item_";
    public static final String DISTRIBUTE_TYPE = "type";
    public static final String DISTRIBUTE_TYPE_PANEL_PACKAGE = "panelPackage";
    public static final String DISTRIBUTE_TYPE_PREVIEW_TEMPLATE = "previewTemplate";
    public static final String DISTRIBUTE_TYPE_PANEL = "panel";
    public static final  String DISTRIBUTE_TYPE_PANEL_ITEM = "panelItem";
    public static final  String DISTRIBUTE_TYPE_NAVIGATION = "navigation";
    public static final String DISTRIBUTE_TYPE_PANEL_ITEM_MAP= "panelItemMap";
    public static final String DISTRIBUTE_TYPE_PANEL_PACKAGE_MAP = "panelPackageMap";
    public static final  String DISTRIBUTE_TYPE_PREVIEW_ITEM = "previewItem";
    public static final  String DISTRIBUTE_TYPE_PREVIEW_ITEM_DATA = "previewItemData";
    public static final  Long AREA_ALL = 0L;
    public static final String BINED_PANEL_ITEM = "related";
    public static final String PANEL_ITEM_TYPE_CHANGE = "typeChange";
    public static final String PANEL_ITEM_CONTENT_TYPE = "list";


    /******************* panel *****************************/


    // DeviceCustomerAccountMap
    public static final String DEVICE_CUSTOMER_ACCOUNT_MAP = "DCAMap";
    public static final String PARAM_IDS = "ids";
    public static final int DEVICE_ACTIVE_STATUS = 2;
    /**
     * 定义设备升级时间在Redis缓存中的区域ID
     */
    public static String UPGRADE_DATE_REDIS_KEY = "upgrade-date-heartbeat";
    /**
     * 定义设备升级最大升级数量在Redis缓存中的区域ID
     */
    public static String UPGRADE_MAX_NUM_REDIS_KEY = "upgrade-max-num-heartbeat";
    
    /*******************************映射的表名***********************************/
    /**
     * 映射表 bss_service_collect_device_group_map
     */
    public static String BSS_DEVICE_SERVICE_COLLECT_MAP="bss_service_collect_device_group_map";

    /**
     * 映射表 bss_device_notice_map
     */
    public static String BSS_DEVICE_NOTICE_MAP="bss_device_notice_map";
    public static String BSS_DEVICE_NOTICE_MAP_YSTEN_ID="ysten_id";
    /**
     * 映射表
     * bss_user_notice_map
     */
    public static String BSS_USER_NOTICE_MAP="bss_user_notice_map";
    public static String BSS_USER_NOTICE_MAP_USER_ID="code";

    /**
     * 映射表
     * bss_animation_user_map
     */
    public static String BSS_USER_ANIMATION_MAP="bss_animation_user_map";

    public static String BSS_USER_ANIMATION_MAP_GROUP_ID="user_group_id";
    public static String BSS_USER_ANIMATION_MAP_USER_ID="code";
    
    /**
     * 映射表
     * bss_animation_device_map
     */
    public static String BSS_DEVICE_ANIMATION_MAP="bss_animation_device_map";
    public static String BSS_DEVICE_ANIMATION_MAP_GROUP_ID="device_group_id";
    public static String BSS_DEVICE_ANIMATION_MAP_YSTEN_ID="ysten_id";

    /**
     * 映射表
     * bss_background_image_device_map
     */
    public static String BSS_DEVICE_BACKGROUND_IMAGE_MAP="bss_background_image_device_map";
    public static String BSS_DEVICE_BACKGROUND_IMAGE_MAP_YSTEN_ID="ysten_id";

    /**
     * 映射表
     * bss_background_image_user_map
     */
    public static String BSS_USER_BACKGROUND_IMAGE_MAP="bss_background_image_user_map";
    public static String BSS_USER_BACKGROUND_IMAGE_MAP_USER_ID="code";

    /**
     * 映射表
     * bss_background_image_user_map
     */
    public static String BSS_USER_UPGRADE_IMAGE_MAP="bss_user_upgrade_map";

    /**
     * 映射表
     * bss_background_image_device_map
     */
    public static String BSS_DEVICE_UPGRADE_IMAGE_MAP="bss_device_upgrade_map";
    
    /**
     * 映射表
     * bss_panel_package_user_map
     */
    public static String BSS_USER_PANEL_PACKAGE_MAP="bss_panel_package_user_map";
    public static String BSS_USER_PANEL_PACKAGE_MAP_GROUP_ID="user_group_id";
    public static String BSS_USER_PANEL_PACKAGE_MAP_USER_ID="code";

    /**
     * 映射表
     * bss_panel_package_device_map
     */

    public static String BSS_DEVICE_PANEL_PACKAGE_MAP="bss_panel_package_device_map";
    public static String BSS_DEVICE_PANEL_PACKAGE_MAP_GROUP_ID="device_group_id";
    public static String BSS_DEVICE_PANEL_PACKAGE_MAP_YSTEN_ID="ysten_id";


    
    public static String BUSINESS_DEVICE_MAP_TYPE_GROUP="GROUP";
    public static String BUSINESS_DEVICE_MAP_TYPE_DEVICE="DEVICE";
    
    public static String BUSINESS_USER_MAP_TYPE_GROUP="GROUP";
    public static String BUSINESS_USER_MAP_TYPE_USER="USER";
    public static Long PANEL_ITEM_TVSET_TYPE =2L;

    public static Long PANEL_ITEM_FIRST_CLASS_PROGRAM_TYPE =1L;

    public static Long PANEL_ITEM_SECOND_CLASS_PROGRAM_TYPE =5L;

    public static String EPG_DISTRICT_CODE="888888";


}
