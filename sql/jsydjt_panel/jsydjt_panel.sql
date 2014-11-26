-- --------------------------------------------------------
-- 主机:                           192.168.1.2
-- 服务器版本:                        5.6.4-m7-log - Source distribution
-- 服务器操作系统:                      Linux
-- HeidiSQL 版本:                  8.0.0.4482
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出 bims_panel_jsyd_jt 的数据库结构
CREATE DATABASE IF NOT EXISTS `bims_panel_jsyd_jt` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;
USE `bims_panel_jsyd_jt`;


-- 导出  表 bims_panel_jsyd_jt.bss_account 结构
CREATE TABLE IF NOT EXISTS `bss_account` (
  `id` int(19) NOT NULL AUTO_INCREMENT,
  `code` varchar(32) DEFAULT NULL,
  `state` varchar(32) DEFAULT NULL,
  `customer_id` varchar(32) DEFAULT NULL,
  `customer_code` varchar(32) DEFAULT NULL,
  `pay_password` varchar(32) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `balance` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_account 的数据：~3 rows (大约)
DELETE FROM `bss_account`;
/*!40000 ALTER TABLE `bss_account` DISABLE KEYS */;
INSERT INTO `bss_account` (`id`, `code`, `state`, `customer_id`, `customer_code`, `pay_password`, `create_date`, `balance`) VALUES
	(1, '111', '333', '5', '6', NULL, NULL, NULL),
	(2, '3', '3', '4', '7', NULL, NULL, NULL),
	(3, '4', '5', '6', '7', NULL, NULL, NULL);
/*!40000 ALTER TABLE `bss_account` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_account_detail 结构
CREATE TABLE IF NOT EXISTS `bss_account_detail` (
  `id` bigint(20) NOT NULL,
  `operate_no` varchar(256) DEFAULT NULL,
  `business_no` varchar(256) DEFAULT NULL,
  `customer_code` varchar(32) DEFAULT NULL,
  `account_code` varchar(32) DEFAULT NULL,
  `operate_type` varchar(32) DEFAULT NULL,
  `current_balance` int(10) DEFAULT NULL,
  `cost` int(10) DEFAULT NULL,
  `account_id` bigint(19) DEFAULT NULL,
  `customer_id` bigint(19) DEFAULT NULL,
  `consum_type` varchar(32) DEFAULT NULL,
  `recharge_type` varchar(32) DEFAULT NULL,
  `outter_code` varchar(32) DEFAULT NULL,
  `result` varchar(32) DEFAULT NULL,
  `syn_flag` bigint(19) DEFAULT NULL,
  `operate_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_account_detail 的数据：~0 rows (大约)
DELETE FROM `bss_account_detail`;
/*!40000 ALTER TABLE `bss_account_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_account_detail` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_animation_device_map 结构
CREATE TABLE IF NOT EXISTS `bss_animation_device_map` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT,
  `boot_animation_id` bigint(16) DEFAULT NULL,
  `ysten_id` varchar(32) DEFAULT NULL,
  `device_group_id` bigint(16) DEFAULT NULL,
  `type` enum('GROUP','DEVICE') NOT NULL,
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_animation_device_map 的数据：~0 rows (大约)
DELETE FROM `bss_animation_device_map`;
/*!40000 ALTER TABLE `bss_animation_device_map` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_animation_device_map` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_animation_user_map 结构
CREATE TABLE IF NOT EXISTS `bss_animation_user_map` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `boot_animation_id` bigint(19) DEFAULT NULL,
  `code` varchar(32) DEFAULT NULL,
  `user_group_id` bigint(19) DEFAULT NULL,
  `type` varchar(32) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `boot_animation_id` (`boot_animation_id`),
  KEY `user_group_id` (`user_group_id`),
  CONSTRAINT `bss_animation_user_map_ibfk_1` FOREIGN KEY (`user_group_id`) REFERENCES `bss_user_group` (`id`),
  CONSTRAINT `bss_animation_user_map_ibfk_2` FOREIGN KEY (`boot_animation_id`) REFERENCES `bss_boot_animation` (`id`),
  CONSTRAINT `bss_animation_user_map_ibfk_3` FOREIGN KEY (`user_group_id`) REFERENCES `bss_user_group` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_animation_user_map 的数据：~0 rows (大约)
DELETE FROM `bss_animation_user_map`;
/*!40000 ALTER TABLE `bss_animation_user_map` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_animation_user_map` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_apk_software_code 结构
CREATE TABLE IF NOT EXISTS `bss_apk_software_code` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '软件号',
  `name` varchar(32) DEFAULT NULL COMMENT '名称',
  `code` varchar(64) DEFAULT NULL COMMENT ' 编码',
  `status` varchar(16) DEFAULT NULL COMMENT '状态',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `description` varchar(2048) DEFAULT NULL COMMENT '描述',
  `oper_user` varchar(50) DEFAULT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='APK设备软件号';

-- 正在导出表  bims_panel_jsyd_jt.bss_apk_software_code 的数据：~5 rows (大约)
DELETE FROM `bss_apk_software_code`;
/*!40000 ALTER TABLE `bss_apk_software_code` DISABLE KEYS */;
INSERT INTO `bss_apk_software_code` (`id`, `name`, `code`, `status`, `create_date`, `description`, `oper_user`, `last_modify_time`) VALUES
	(1, '设备升级-0714', 'W0714', 'USABLE', '2014-08-20 14:15:03', 'W0714', 'admin', NULL),
	(2, 'xj-software-0826', '0826', 'USABLE', '2014-08-26 10:26:55', '', 'admin', NULL),
	(3, '设备升级-0826', '4.0.1.301', 'USABLE', '2014-08-26 15:10:08', '', 'admin', NULL),
	(4, '易视腾E5终端', 'is_E520140304165715601', 'USABLE', '2014-09-01 10:58:45', '', 'admin', NULL),
	(5, '4.7升级-BJ', '00000021201305021525593', 'USABLE', '2014-09-22 17:17:30', '', 'admin', '2014-09-22 18:08:27');
/*!40000 ALTER TABLE `bss_apk_software_code` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_apk_software_package 结构
CREATE TABLE IF NOT EXISTS `bss_apk_software_package` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '软件包ID',
  `soft_code_id` bigint(19) DEFAULT NULL COMMENT '软件号',
  `version_seq` int(11) DEFAULT NULL COMMENT '软件版本序号。\r\n            设备升级后对应的设备软件包版本序列号，为整型',
  `version_name` varchar(32) DEFAULT NULL COMMENT '软件版本名称',
  `package_type` varchar(32) DEFAULT NULL COMMENT '软件包类型TEST("测试"), RELEASE("发布");',
  `package_location` varchar(255) DEFAULT NULL COMMENT '软件包绝对路径',
  `is_mandatory` varchar(32) DEFAULT NULL COMMENT '是否强制升级MANDATORY("强制"), NOTMANDATORY("不强制");',
  `md5` varchar(32) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `device_version_seq` int(11) DEFAULT '0' COMMENT '终端当前版本号',
  `full_software_id` bigint(19) DEFAULT NULL COMMENT '当为增量升级选择对应的全量包软件ID',
  `last_modify_time` datetime DEFAULT NULL,
  `oper_user` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='APK升级软件包信息（apk路径）';

-- 正在导出表  bims_panel_jsyd_jt.bss_apk_software_package 的数据：~7 rows (大约)
DELETE FROM `bss_apk_software_package`;
/*!40000 ALTER TABLE `bss_apk_software_package` DISABLE KEYS */;
INSERT INTO `bss_apk_software_package` (`id`, `soft_code_id`, `version_seq`, `version_name`, `package_type`, `package_location`, `is_mandatory`, `md5`, `create_date`, `device_version_seq`, `full_software_id`, `last_modify_time`, `oper_user`) VALUES
	(1, 2, 1, 'xj-version-0826', 'FULL', '12', 'NOTMANDATORY', '123', '2014-08-26 10:27:49', NULL, NULL, NULL, 'admin'),
	(2, 3, 5, '设备升级-0826', 'FULL', 'http://www.baidu.com', 'NOTMANDATORY', '222334455', '2014-08-26 15:11:05', NULL, NULL, '2014-08-26 15:11:13', 'admin'),
	(3, 1, 2, '版本-0714', 'FULL', '2', 'NOTMANDATORY', '2', '2014-08-28 11:07:44', 1, NULL, NULL, 'admin'),
	(4, 4, 28, 'V4.6', 'FULL', '543511111', 'MANDATORY', '3432432', '2014-09-01 10:59:45', NULL, NULL, NULL, 'admin'),
	(5, 4, 28, 'V4.6', 'INCREMENT', '4444444', 'MANDATORY', '5466666', '2014-09-01 11:00:30', 12, 4, NULL, 'admin'),
	(6, 5, 27, '4.7升级联调-BJ', 'FULL', 'http://58.214.17.67:8081/attachments/update.zip', 'NOTMANDATORY', '055D736BB7EBD2772F18778FDC7046F0', '2014-09-22 17:19:23', NULL, NULL, '2014-09-22 18:08:43', 'admin'),
	(7, 5, 45, '测试包', 'INCREMENT', '56565', 'NOTMANDATORY', '56565', '2014-10-16 09:50:25', 5, 6, NULL, 'admin');
/*!40000 ALTER TABLE `bss_apk_software_package` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_apk_upgrade_map 结构
CREATE TABLE IF NOT EXISTS `bss_apk_upgrade_map` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `ysten_id` varchar(32) DEFAULT NULL,
  `upgrade_id` bigint(19) DEFAULT NULL,
  `device_group_id` bigint(19) DEFAULT NULL,
  `type` varchar(32) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备-apk升级关联表';

-- 正在导出表  bims_panel_jsyd_jt.bss_apk_upgrade_map 的数据：~0 rows (大约)
DELETE FROM `bss_apk_upgrade_map`;
/*!40000 ALTER TABLE `bss_apk_upgrade_map` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_apk_upgrade_map` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_apk_upgrade_result_log 结构
CREATE TABLE IF NOT EXISTS `bss_apk_upgrade_result_log` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT,
  `device_code` varchar(36) DEFAULT NULL,
  `ysten_id` varchar(32) DEFAULT NULL,
  `soft_code` varchar(64) DEFAULT NULL COMMENT '软件编码',
  `device_version_seq` int(11) DEFAULT NULL COMMENT '终端当前版本号',
  `version_seq` int(11) DEFAULT NULL COMMENT '终端升级前软件版本序号',
  `status` varchar(16) DEFAULT NULL,
  `duration` bigint(16) DEFAULT NULL COMMENT '持续时间',
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备apk更新记录';

-- 正在导出表  bims_panel_jsyd_jt.bss_apk_upgrade_result_log 的数据：~0 rows (大约)
DELETE FROM `bss_apk_upgrade_result_log`;
/*!40000 ALTER TABLE `bss_apk_upgrade_result_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_apk_upgrade_result_log` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_apk_upgrade_task 结构
CREATE TABLE IF NOT EXISTS `bss_apk_upgrade_task` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '升级ID',
  `name` varchar(256) DEFAULT NULL,
  `soft_code_id` bigint(19) DEFAULT NULL COMMENT '软件号id',
  `software_package_id` bigint(19) NOT NULL COMMENT '软件版本id',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `vendor_ids` varchar(1000) DEFAULT NULL COMMENT '供应商id',
  `version_seq` int(11) DEFAULT NULL COMMENT '终端当前版本号',
  `max_num` int(11) DEFAULT NULL COMMENT '最大升级数',
  `time_interval` int(11) DEFAULT NULL COMMENT '时间间隔',
  `is_all` int(12) DEFAULT NULL COMMENT '是否全部终端都提示（当设备、设备组都没有关联设备时，显示）',
  `last_modify_time` datetime DEFAULT NULL,
  `oper_user` varchar(50) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='APK升级任务';

-- 正在导出表  bims_panel_jsyd_jt.bss_apk_upgrade_task 的数据：~5 rows (大约)
DELETE FROM `bss_apk_upgrade_task`;
/*!40000 ALTER TABLE `bss_apk_upgrade_task` DISABLE KEYS */;
INSERT INTO `bss_apk_upgrade_task` (`id`, `name`, `soft_code_id`, `software_package_id`, `create_date`, `vendor_ids`, `version_seq`, `max_num`, `time_interval`, `is_all`, `last_modify_time`, `oper_user`, `start_date`, `end_date`) VALUES
	(10, '任务1', 2, 1, '2014-08-26 10:28:16', NULL, NULL, 20, 2, 0, '2014-08-26 10:28:16', 'admin', '2014-08-26 10:28:21', '2015-09-30 10:28:21'),
	(11, '任务2', 3, 2, '2014-08-26 15:12:02', NULL, NULL, 20, 1, 1, '2014-08-26 15:20:30', 'admin', '2014-08-26 15:00:39', '2015-08-28 15:11:51'),
	(12, '任务3', 1, 3, '2014-08-28 11:08:08', NULL, NULL, 30, 2, 0, '2014-08-28 11:08:08', 'admin', '2014-08-28 11:08:27', '2015-01-29 11:08:27'),
	(13, '任务4', 4, 4, '2014-09-01 11:23:15', NULL, NULL, 2, 2, 0, '2014-09-01 11:23:15', 'admin', '2014-09-01 11:23:31', '2015-09-27 11:23:31'),
	(14, '任务5', 5, 6, '2014-09-22 17:19:53', NULL, NULL, 20, 1, 1, '2014-09-23 11:44:41', 'admin', '2014-09-22 00:21:09', '2015-09-30 17:21:09');
/*!40000 ALTER TABLE `bss_apk_upgrade_task` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_app_software_code 结构
CREATE TABLE IF NOT EXISTS `bss_app_software_code` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `code` varchar(64) DEFAULT NULL,
  `status` varchar(16) DEFAULT NULL COMMENT ' USABLE("可用"), UNUSABLE("不可用");',
  `create_date` datetime DEFAULT NULL,
  `description` varchar(5000) DEFAULT NULL,
  `oper_user` varchar(50) DEFAULT NULL,
  `distribute_state` varchar(32) DEFAULT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='应用升级软件号';

-- 正在导出表  bims_panel_jsyd_jt.bss_app_software_code 的数据：~0 rows (大约)
DELETE FROM `bss_app_software_code`;
/*!40000 ALTER TABLE `bss_app_software_code` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_app_software_code` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_app_software_package 结构
CREATE TABLE IF NOT EXISTS `bss_app_software_package` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT,
  `soft_code_id` bigint(16) DEFAULT NULL,
  `version_seq` int(11) DEFAULT NULL,
  `version_name` varchar(32) DEFAULT NULL,
  `package_type` varchar(32) DEFAULT NULL,
  `package_location` varchar(255) DEFAULT NULL,
  `is_mandatory` varchar(32) DEFAULT NULL,
  `md5` varchar(32) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `app_version_seq` int(11) DEFAULT NULL,
  `sdk_version` int(12) DEFAULT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  `oper_user` varchar(50) DEFAULT NULL,
  `package_status` varchar(32) DEFAULT NULL,
  `distribute_state` varchar(32) DEFAULT NULL,
  `full_software_id` bigint(12) DEFAULT NULL COMMENT '全量包软件ID',
  PRIMARY KEY (`id`),
  KEY `soft_code_id` (`soft_code_id`),
  CONSTRAINT `bss_app_software_package_ibfk_1` FOREIGN KEY (`soft_code_id`) REFERENCES `bss_app_software_code` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='应用升级软件包信息';

-- 正在导出表  bims_panel_jsyd_jt.bss_app_software_package 的数据：~0 rows (大约)
DELETE FROM `bss_app_software_package`;
/*!40000 ALTER TABLE `bss_app_software_package` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_app_software_package` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_app_upgrade_map 结构
CREATE TABLE IF NOT EXISTS `bss_app_upgrade_map` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT,
  `ysten_id` varchar(32) DEFAULT NULL,
  `upgrade_id` bigint(16) DEFAULT NULL,
  `device_group_id` bigint(16) DEFAULT NULL,
  `type` varchar(32) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_app_upgrade_map 的数据：~0 rows (大约)
DELETE FROM `bss_app_upgrade_map`;
/*!40000 ALTER TABLE `bss_app_upgrade_map` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_app_upgrade_map` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_app_upgrade_task 结构
CREATE TABLE IF NOT EXISTS `bss_app_upgrade_task` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) DEFAULT NULL,
  `soft_code_id` bigint(16) DEFAULT NULL,
  `software_package_id` bigint(16) DEFAULT NULL,
  `version_seq` int(11) DEFAULT NULL,
  `vendor_ids` varchar(1000) DEFAULT NULL COMMENT '供应商id',
  `create_date` datetime DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `time_interval` bigint(16) DEFAULT NULL,
  `max_num` int(11) DEFAULT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  `oper_user` varchar(50) DEFAULT NULL,
  `is_all` int(12) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `software_package_id` (`software_package_id`),
  KEY `soft_code_id` (`soft_code_id`),
  CONSTRAINT `bss_app_upgrade_task_ibfk_1` FOREIGN KEY (`software_package_id`) REFERENCES `bss_app_software_package` (`id`),
  CONSTRAINT `bss_app_upgrade_task_ibfk_2` FOREIGN KEY (`soft_code_id`) REFERENCES `bss_app_software_code` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='应用升级任务';

-- 正在导出表  bims_panel_jsyd_jt.bss_app_upgrade_task 的数据：~0 rows (大约)
DELETE FROM `bss_app_upgrade_task`;
/*!40000 ALTER TABLE `bss_app_upgrade_task` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_app_upgrade_task` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_area 结构
CREATE TABLE IF NOT EXISTS `bss_area` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `code` varchar(32) DEFAULT NULL,
  `name` varchar(32) NOT NULL,
  `dist_code` varchar(6) DEFAULT NULL,
  `state` varchar(30) DEFAULT NULL,
  `memo` varchar(5000) DEFAULT NULL,
  `parent_name` varchar(128) DEFAULT NULL,
  `parent_id` bigint(19) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_area 的数据：~36 rows (大约)
DELETE FROM `bss_area`;
/*!40000 ALTER TABLE `bss_area` DISABLE KEYS */;
INSERT INTO `bss_area` (`id`, `code`, `name`, `dist_code`, `state`, `memo`, `parent_name`, `parent_id`) VALUES
	(1, 'zhongguo', '中国', '100000', 'USABLE', '中国', '地球', -1),
	(2, 'shanghai', '上海市', '310000', 'USABLE', '上海市', '中国', 1),
	(3, 'tianjin', '天津市', '120000', 'USABLE', '天津市', '中国', 1),
	(4, 'chongqing', '重庆市', '500000', 'USABLE', '重庆市', '中国', 1),
	(5, 'heibei', '河北省', '130000', 'USABLE', '河北省', '中国', 1),
	(6, 'shanxi', '陕西省', '610000', 'USABLE', '陕西省', '中国', 1),
	(7, 'shanxi', '山西省', '140000', 'USABLE', '山西省', '中国', 1),
	(8, 'beijing', '北京市', '110000', 'USABLE', '北京市', '中国', 1),
	(9, 'liaoning', '辽宁省', '210000', 'USABLE', '辽宁省', '中国', 1),
	(10, 'jilin', '吉林省', '220000', 'USABLE', '吉林省', '中国', 1),
	(11, 'heilongjiang', '黑龙江省', '230000', 'USABLE', '黑龙江省', '中国', 1),
	(12, 'jiangsu', '江苏省', '320000', 'USABLE', '江苏省', '中国', 1),
	(13, 'zhejiang', '浙江省', '330000', 'USABLE', '浙江省', '中国', 1),
	(14, 'jiangxi', '江西省', '360000', 'USABLE', '江西省', '中国', 1),
	(15, 'shangdong', '山东省', '370000', 'USABLE', '山东省', '中国', 1),
	(16, 'heinan', '河南省', '410000', 'USABLE', '河南省', '中国', 1),
	(17, 'hunan', '湖南省', '430000', 'USABLE', '湖南省', '中国', 1),
	(18, 'hubei', '湖北省', '420000', 'USABLE', '湖北省', '中国', 1),
	(19, 'guangdong', '广东省', '440000', 'USABLE', '广东省', '中国', 1),
	(20, 'guangxi', '广西壮族自治区', '450000', 'USABLE', '广西壮族自治区', '中国', 1),
	(21, 'hainan', '海南省', '460000', 'USABLE', '海南省', '中国', 1),
	(22, 'sichuan', '四川省', '510000', 'USABLE', '四川省', '中国', 1),
	(23, 'guizhou', '贵州省', '520000', 'USABLE', '贵州省', '中国', 1),
	(24, 'yunnan', '云南省', '530000', 'USABLE', '云南省', '中国', 1),
	(25, 'xizang', '西藏自治区', '540000', 'USABLE', '西藏自治区', '中国', 1),
	(26, 'gansu', '甘肃省', '620000', 'USABLE', '甘肃省', '中国', 1),
	(27, 'qinghai', '青海省', '630000', 'USABLE', '青海省', '中国', 1),
	(28, 'ningxia', '宁夏回族自治区', '640000', 'USABLE', '宁夏回族自治区', '中国', 1),
	(29, 'xinjiang', '新疆维吾尔自治区', '650000', 'USABLE', '新疆维吾尔自治区', '中国', 1),
	(30, 'anhui', '安徽省', '340000', 'USABLE', '安徽省', '中国', 1),
	(31, 'neimenggu', '内蒙古自治区', '150000', 'USABLE', '内蒙古自治区', '中国', 1),
	(32, 'fujian', '福建省', '350000', 'USABLE', '福建省', '中国', 1),
	(33, 'taiwan', '台湾省', '710000', 'USABLE', '台湾省', '中国', 1),
	(34, 'xianggang', '香港特别行政区', '810000', 'USABLE', '香港特别行政区', '中国', 1),
	(35, 'aomen', '澳门特别行政区', '820000', 'USABLE', '澳门特别行政区', '中国', 1),
	(9999, 'weizhi', '未知区域', '999999', 'USABLE', '未知区域', '中国', 1);
/*!40000 ALTER TABLE `bss_area` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_authority 结构
CREATE TABLE IF NOT EXISTS `bss_authority` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(5000) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL COMMENT '权限显示的名称',
  `description` varchar(5000) DEFAULT NULL,
  `parent_id` bigint(19) DEFAULT NULL,
  `sort_order` bigint(19) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=341 DEFAULT CHARSET=utf8 COMMENT='特权，记录操作信息';

-- 正在导出表  bims_panel_jsyd_jt.bss_authority 的数据：~273 rows (大约)
DELETE FROM `bss_authority`;
/*!40000 ALTER TABLE `bss_authority` DISABLE KEYS */;
INSERT INTO `bss_authority` (`id`, `code`, `name`, `description`, `parent_id`, `sort_order`) VALUES
	(1, 'root', '业务集成管理平台', '业务集成管理平台', 0, 0),
	(5, 'device_manager', '终端管理', '终端管理', 1, 13),
	(6, 'customer_manager', '用户管理', '用户管理', 1, 11),
	(7, 'statistics_manager', '统计管理', '统计管理', 1, 5),
	(8, 'system_manager', '系统管理', '系统管理', 1, 1),
	(9, 'logger_manager', '日志管理', '日志管理', 1, 4),
	(19, 'customer_manager', '用户信息维护', '用户信息维护', 6, 0),
	(20, 'customer_device_manager', '用户终端绑定', '用户终端绑定', 6, 0),
	(21, 'device_customer_rel', '终端用户关系', '终端用户关系', 6, 0),
	(22, 'device_customer_history', '终端用户历史', '终端用户历史', 6, 0),
	(23, 'operator_manager', '操作员管理', '操作员管理', 8, 0),
	(24, 'role_manager', '角色管理', '角色管理', 8, 0),
	(25, 'authority_manager', '权限信息', '权限信息', 8, 0),
	(28, 'interface_log', '接口日志查询', '接口日志查询', 9, 0),
	(29, 'customer_group', '用户集团维护', '用户集团维护', 6, 0),
	(30, 'customer_cust', '客户信息维护', '客户信息维护', 6, 0),
	(31, 'device_manager', '设备信息管理', '设备信息管理', 5, 0),
	(32, 'device_ip_manager', 'IP地址信息库', 'IP地址信息库', 5, 0),
	(34, 'area_manager', '区域信息管理', '区域信息管理', 164, 0),
	(35, 'device_vendor_manager', '设备厂商管理', '设备厂商管理', 5, 0),
	(36, 'device_type_manager', '设备型号管理', '设备型号管理', 5, 0),
	(37, 'device_activate_statistics', '终端激活数据统计', '终端激活数据统计', 7, 0),
	(38, 'customer_register_statistics', '用户开户数据统计', '用户开户数据统计', 7, 0),
	(39, 'device_customer_timed_statistics', '终端用户数据定时统计', '终端用户数据定时统计', 7, 0),
	(44, 'system_conifg', '系统参数配置', '系统参数配置', 187, 0),
	(45, 'update_sysconfig', '修改', '修改系统配置', 44, 0),
	(46, 'add_area', '新增', '新增区域信息', 34, 0),
	(47, 'update_area', '修改', '修改区域信息', 34, 0),
	(48, 'update_device', '修改', '修改设备信息', 31, 0),
	(49, 'add_device_type', '新增', '新增设备类型', 36, 0),
	(50, 'update_device_type', '修改', '修改设备类型', 36, 0),
	(51, 'add_device_vendor', '新增', '新增设备厂商', 35, 0),
	(52, 'update_device_vendor', '修改', '修改设备厂商', 35, 0),
	(53, 'add_device_ip', '新增', '新增IP地址段', 32, 0),
	(54, 'update_device_ip', '修改', '修改ip地址段', 32, 0),
	(55, 'delete_device_ip', '删除', '删除ip地址段', 32, 0),
	(56, 'device_ip_search ', 'ip地址段是否存在', 'ip地址段是否存在', 32, 0),
	(57, 'sp_define_manager', '运营商信息管理', '运营商信息管理', 187, 0),
	(58, 'add_sp_define', '新增', '新增运营商信息', 57, 0),
	(59, 'update_sp_define', '修改', '修改运营商信息', 57, 0),
	(60, 'update_sysconfig_date', '更新', '更新系统参数', 57, 0),
	(61, 'operate_manager', '操作日志查询', '操作日志查询', 9, 0),
	(62, 'add_role', '新增', '新增角色信息', 24, 0),
	(63, 'update_role', '修改', '修改角色信息', 24, 0),
	(64, 'add_authority', '新增', '新增权限信息', 25, 0),
	(65, 'update_authority', '修改', '修改权限信息', 25, 0),
	(71, 'city_manager', '地市信息维护', '地市信息维护', 164, 0),
	(72, 'add_city', '新增', '新增地市信息', 71, 0),
	(73, 'update_city', '修改', '修改地市信息', 71, 0),
	(74, 'delete_city', '删除', '删除地市信息', 71, 0),
	(75, 'lock_device', '锁定设备', '锁定设备', 31, 0),
	(76, 'unlock_device', '解锁设备', '解锁设备', 31, 0),
	(77, 'import_device', '导入', '导入设备信息', 31, 0),
	(78, 'export_device', '导出', '导出设备信息', 31, 0),
	(102, 'customer_list_add', '新增', '用户管理-用户信息维护-新增', 19, 0),
	(103, 'customer_list_update', '修改', '用户管理-用户信息维护-修改', 19, 0),
	(104, 'customer_list_delete', '删除', '用户管理-用户信息维护-删除', 19, 0),
	(105, 'customer_list_lock', '锁定', '用户管理-用户信息维护-锁定', 19, 0),
	(106, 'customer_list_unlock', '解锁', '用户管理-用户信息维护-解锁', 19, 0),
	(107, 'customer_list_lookpassword', '查看密码', '用户管理-用户信息维护-查看密码', 19, 0),
	(108, 'customer_list_import', '导入', '用户管理-用户信息维护-导入', 19, 0),
	(109, 'customer_list_export', '导出', '用户管理-用户信息维护-导出', 19, 0),
	(110, 'customer_bind_device', '终端绑定', '用户管理-用户终端绑定-终端绑定', 20, 0),
	(111, 'customer_bind_export', '导出', '用户管理-用户终端绑定-导出', 20, 0),
	(113, 'customer_relation_export', '导出', '用户管理-终端用户关系-导出', 21, 0),
	(138, 'assgin_role_authority', '角色权限分配', '角色权限分配', 24, 0),
	(141, 'add_operator', '新增操作员', '新增操作员', 23, 0),
	(142, 'update_operator', '修改操作员', '修改操作员', 23, 0),
	(146, 'customer_reset_pwd', '重置密码', '用户管理-用户信息维护-重置密码', 19, 0),
	(147, 'device_import_template', '下载导入模板', '下载导入模板', 31, 0),
	(148, 'upgrade_manager', '终端升级维护', '终端升级维护', 165, 0),
	(149, 'device_software_code_manager', '设备软件号维护', '设备软件号维护', 148, 0),
	(150, 'device_software_package_manager', '设备软件包维护', '设备软件包维护', 148, 0),
	(157, 'device_group_info', '设备分组维护', '设备分组维护', 5, 0),
	(158, 'sys_notice_list', '消息信息维护', '消息信息维护', 5, 0),
	(161, 'add_sys_notice', '新增', '新增消息信息', 158, 0),
	(162, 'update_sys_notice', '修改', '修改消息信息', 158, 0),
	(163, 'delete_sys_notice', '删除', '删除消息信息', 158, 0),
	(164, 'area_info', '区域维护', '区域维护', 1, 6),
	(165, 'upgrade_info', '升级管理', '升级管理', 1, 12),
	(166, 'app_upgrade_info', '应用升级维护', '应用升级维护', 165, 2),
	(167, 'app_software_info', '应用软件号维护', '应用软件号维护', 166, 0),
	(169, 'add_soft_code', '新增', '新增软件号', 149, 0),
	(170, 'update_soft_code', '修改', '修改软件号', 149, 0),
	(171, 'delete_soft_code', '删除', '删除软件号', 149, 0),
	(172, 'add_soft_package', '新增', '新增软件号包', 150, 0),
	(173, 'update_soft_package', '修改', '修改软件号包', 150, 0),
	(174, 'delete_soft_package', '删除', '删除软件号包', 150, 0),
	(175, 'save_device_group', '新增', '新增设备分组', 157, 0),
	(176, 'update_device_group', '修改', '修改设备分组', 157, 0),
	(177, 'delete_device_group', '删除', '删除设备分组', 157, 0),
	(178, 'boot_animation_info', '开机动画维护', '开机动画维护', 5, 0),
	(179, 'add_boot_animation', '新增', '新增开机动画', 178, 0),
	(180, 'update_boot_animation', '修改', '修改开机动画', 178, 0),
	(181, 'delete_boot_animation', '删除', '删除开机动画', 178, 0),
	(184, 'service_collect', '服务集信息管理', '服务集信息管理', 187, 0),
	(185, 'device_background_image_info', '背景轮换维护', '背景轮换维护', 5, 0),
	(186, 'device_bind_group', '终端分组绑定', '终端分组绑定', 31, 0),
	(187, 'config_info', '配置管理', '配置管理', 1, 2),
	(189, 'add_background', '新增', '新增背景', 185, 0),
	(190, 'update_background', '修改', '修改背景', 185, 0),
	(191, 'delete_background', '删除', '删除背景', 185, 0),
	(194, 'add_service_collect_list', '新增', '新增服务集信息', 184, 0),
	(195, 'update_service_collect_list', '修改', '修改服务集信息', 184, 0),
	(196, 'bind_group_service_collect_list', '绑定', '服务集绑定设备组', 184, 0),
	(197, 'add_service_info_list', '新增', '新增服务信息', 279, 0),
	(198, 'update_service_info_list', '修改', '修改服务信息', 279, 0),
	(199, 'app_software_package_info', '应用软件包维护', '应用软件包维护', 166, 0),
	(200, 'app_upgrade_task_info', '应用升级任务维护', '应用升级任务维护', 166, 0),
	(202, 'device_upgrade_result_log_info', '终端升级日志', '终端升级日志', 9, 0),
	(203, 'panel_manager', '面板管理', '面板管理', 1, 10),
	(204, 'panel_info', '面板信息维护', '面板信息维护', 203, 0),
	(205, 'add_panel', '新增', '新增', 204, 0),
	(206, 'update_panel', '修改', '修改', 204, 0),
	(208, 'user_group_manager', '用户分组维护', '用户分组维护', 6, 0),
	(213, 'preview_template_info', '预览面板信息维护', '预览面板信息维护', 203, 4),
	(214, 'add_preview_template', '新增', '新增预览面板信息', 213, 0),
	(215, 'update_preview_template', '修改', '修改预览面板信息', 213, 0),
	(216, 'delete_preview_template', '删除', '删除预览面板信息', 213, 0),
	(217, 'customer_bind_group', '用户绑定用户分组', '用户绑定用户分组', 19, 0),
	(219, 'add_upgrade_task', '新增', '新增升级任务', 220, 0),
	(220, 'upgrade_task_manager', '设备升级任务维护', '设备升级任务维护', 148, 0),
	(221, 'update_upgrade_task', '修改', '修改升级任务', 220, 0),
	(222, 'delete_upgrade_task', '删除', '删除升级任务', 220, 0),
	(223, 'add_app_soft_code', '新增', '新增应用升级软件号', 167, 0),
	(224, 'update_app_soft_code', '修改', '修改应用升级软件号', 167, 0),
	(225, 'delete_app_soft_code', '删除', '删除应用升级软件号', 167, 0),
	(226, 'panel_package_info', '面板包信息维护', '面板包信息维护', 203, 3),
	(227, 'add_panel_package', '新增', '新增面板包', 226, 1),
	(228, 'update_panel_package', '修改', '修改面板包', 226, 2),
	(229, 'delete_panel_package', '删除', '删除面板包', 226, 3),
	(230, 'bind_panel_package', '绑定面板包', '绑定面板包', 204, 4),
	(231, 'panel_item_info', '面板项信息维护', '面板项信息维护', 203, 3),
	(232, 'add_panel_item', '新增', '新增面板项', 231, 1),
	(233, 'update_panel_item', '修改', '修改面板项', 231, 2),
	(234, 'delete_panel_item', '删除', '删除面板项', 231, 3),
	(235, 'nav_define_info', '导航信息维护', '导航信息维护', 203, 5),
	(236, 'add_panel_nav', '新增', '新增导航信息维护', 235, 1),
	(237, 'update_panel_nav', '修改', '修改导航信息维护', 235, 2),
	(238, 'delete_panel_nav', '删除', '删除导航信息维护', 235, 3),
	(239, 'bind_panel_package_business', '绑定', '绑定业务---设备，设备分组', 226, 4),
	(240, 'panel_template_config', '面板模板配置', '面板模板配置', 213, 0),
	(241, 'panel_config', '面板配置', '面板配置', 204, 0),
	(242, 'syns_epg_panel_data', '同步EPG面板相关数据', '同步EPG面板相关数据', 204, 0),
	(245, 'bind_device', '关联设备', '关联设备', 157, 0),
	(246, 'unbind_group_service_collect_list', '解绑业务', '服务集解绑设备组', 184, 0),
	(247, 'unbind_device', '解绑设备', '解绑设备---设备分组', 157, 0),
	(248, 'unbind_business', '解绑业务', '解绑业务---设备分组', 157, 0),
	(249, 'unbind_device_group', '解绑设备分组', '解绑设备分组---设备', 31, 0),
	(250, 'bind_sys_notice', '绑定业务', '绑定业务---设备，设备分组', 158, 0),
	(251, 'unbind_sys_notice', '解绑业务', '解绑业务---设备，设备分组', 158, 0),
	(252, 'unbind_user_group', '解绑用户分组', '解绑用户分组---用户', 19, 0),
	(253, 'save_user_group', '新增', '新增---用户分组', 208, 0),
	(254, 'update_user_group', '修改', '修改---用户分组', 208, 0),
	(255, 'delete_user_group', '删除', '删除---用户分组', 208, 0),
	(256, 'bind_user', '关联用户', '关联用户---用户分组', 208, 0),
	(257, 'unbind_user', '解绑用户', '解绑用户---用户分组', 208, 0),
	(258, 'unbind_business_user_group', '解绑业务', '解绑业务---用户分组', 208, 0),
	(259, 'unbind_business_user', '解绑业务', '解绑业务---用户', 19, 0),
	(260, 'boot_animation_bind', '绑定', '绑定业务---设备，设备分组', 178, 0),
	(261, 'boot_animation_unbind', '解绑', '解绑业务---设备，设备分组', 178, 0),
	(262, 'background_bind', '绑定', '绑定业务---设备，设备分组', 185, 0),
	(263, 'background_unbind', '解绑', '解绑业务---设备，设备分组', 185, 0),
	(264, 'upgrade_task_bind', '绑定', '绑定---终端升级', 220, 0),
	(265, 'upgrade_task_unbind', '解绑', '解绑---终端升级', 220, 0),
	(266, 'unbind_upgrade_task', '解绑', '绑定业务---设备，设备分组', 200, 0),
	(267, 'bind_upgrade_task', '绑定', '解绑业务---设备，设备分组', 200, 0),
	(272, 'add_app_soft_package', '新增', '新增--应用软件包维护', 199, 0),
	(273, 'update_app_soft_package', '修改', '修改--应用软件包维护', 199, 0),
	(274, 'delete_app_soft_package', '删除', '删除--应用软件包维护', 199, 0),
	(276, 'add_app_upgrade_task', '新增', '新增--应用升级任务', 200, 0),
	(277, 'update_app_upgrade_task', '修改', '修改--应用升级任务', 200, 0),
	(278, 'delete_app_upgrade_task', '删除', '删除--应用升级任务', 200, 0),
	(279, 'service_info', '服务信息管理', '服务信息管理', 187, 0),
	(280, 'unbind_panel_package_business', '解绑', '解绑业务---设备，设备分组', 226, 0),
	(295, 'device_active_statistics_chart', '终端激活统计图表', '终端激活统计图表', 7, 0),
	(296, 'user_activate_statistics_chart', '用户开户统计图表', '用户开户统计图表', 7, 0),
	(300, 'delete_device', '删除', '删除设备信息', 31, 0),
	(301, 'unbind_business_device', '解绑业务', '解绑业务', 31, 0),
	(302, 'config_online_panel', '上线', '上线', 204, 0),
	(303, 'config_downline_panel', '下线', '下线', 204, 0),
	(304, 'panel_package_preview', '面板包预览', '面板包预览', 226, 0),
	(307, 'config_panel_sort', '面板排序调整', '面板排序调整', 226, 0),
	(308, 'device_list_of_group', '显示设备列表', '显示设备列表', 157, NULL),
	(309, 'set_ystenId', '生成易视腾编号', '生成易视腾编号', 31, NULL),
	(310, 'bind_user_sys_notice', '绑定业务', '绑定业务---用户，用户分组', 158, 0),
	(311, 'unbind_user_sys_notice', '解绑业务', '解绑业务---用户，用户分组', 158, 0),
	(312, 'boot_animation_bind_user', '绑定业务', '绑定业务---用户，用户分组', 178, 0),
	(314, 'boot_animation_unbind_user', '解绑业务', '解绑业务---用户，用户分组', 178, 0),
	(315, 'background_bind_user', '绑定业务', '绑定业务---用户，用户分组', 185, 0),
	(316, 'background_unbind_user', '解绑业务', '解绑业务---用户，用户分组', 185, 0),
	(317, 'bind_user_sys_notice', '绑定业务', '绑定业务---用户，用户分组', 158, 0),
	(318, 'bind_panel_package_business_user', '绑定业务', '绑定业务---用户，用户分组', 226, 0),
	(319, 'unbind_panel_package_business_user', '解绑业务', '解绑业务---用户，用户分组', 226, 0),
	(320, 'unbind_user_upgrade_task', '绑定业务', '绑定业务---用户，用户分组', 200, 0),
	(321, 'bind_user_upgrade_task', '解绑业务', '解绑业务---用户，用户分组', 200, 0),
	(322, 'customer_list_of_group', '显示用户列表', '显示用户列表', 208, 0),
	(323, 'export_device_statistics', '终端激活数据导出', '终端激活数据导出', 37, NULL),
	(324, 'export_user_statistics', '用户开户数据导出', '用户开户数据导出', 38, NULL),
	(325, 'export_user_activate_day_sum_statistics', '终端激活定时统计数据导出', '终端激活定时统计数据导出', 39, NULL),
	(326, 'boot_animation_device_list', '显示设备列表', '显示设备列表', 178, NULL),
	(327, 'boot_animation_user_list', '显示用户列表', '显示用户列表', 178, NULL),
	(328, 'sys_notice_user_list', '显示用户列表', '显示用户列表', 158, NULL),
	(329, 'sys_notice_device_list', '显示设备列表', '显示设备列表', 158, NULL),
	(330, 'upgrade_task_device_list', '显示设备列表', '显示设备列表', 220, NULL),
	(331, 'upgrade_task_user_list', '显示用户列表', '显示用户列表', 220, NULL),
	(332, 'panel_package_user_list', '显示用户列表', '显示用户列表', 226, NULL),
	(333, 'panel_package_device_list', '显示设备列表', '显示设备列表', 226, NULL),
	(334, 'service_collect_device_list', '显示设备列表', '显示设备列表', 184, NULL),
	(335, 'background_device_list', '显示设备列表', '显示设备列表', 185, NULL),
	(336, 'background_user_list', '显示用户列表', '显示用户列表', 185, NULL),
	(337, 'push_province_panel_data', '向中心同步省级数据', '向中心同步省级数据', 204, NULL),
	(338, 'sys_version_list', '系统版本管理', '系统版本管理', 8, NULL),
	(339, 'apk_upgrade_manager', 'apk升级维护', 'apk升级维护', 165, NULL),
	(340, 'apk_software_code_manager', 'apk软件号维护', 'apk软件号维护', 339, 1);
/*!40000 ALTER TABLE `bss_authority` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_background_image 结构
CREATE TABLE IF NOT EXISTS `bss_background_image` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL COMMENT '高清图片',
  `blur_url` varchar(500) DEFAULT NULL COMMENT '模糊图片',
  `create_date` datetime NOT NULL,
  `update_date` datetime DEFAULT NULL,
  `is_default` int(2) NOT NULL DEFAULT '0',
  `state` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COMMENT='背景';

-- 正在导出表  bims_panel_jsyd_jt.bss_background_image 的数据：~7 rows (大约)
DELETE FROM `bss_background_image`;
/*!40000 ALTER TABLE `bss_background_image` DISABLE KEYS */;
INSERT INTO `bss_background_image` (`id`, `name`, `url`, `blur_url`, `create_date`, `update_date`, `is_default`, `state`) VALUES
	(2, '无锡测试', 'http://192.168.1.1:8080/yst-bims333/login_in.html', 'http://192.168.1.1:8080/yst-bims/login_in.html', '2014-08-26 14:41:37', '2014-08-26 14:41:37', 0, 'USEABLE'),
	(3, 'xj-bg-01', 'http://background-user group.gq.com', 'http://background-user group.bq.com', '2014-08-26 16:33:23', '2014-09-15 14:44:04', 0, 'USEABLE'),
	(4, 'xj-bg-02', 'http://bg-usercode/gq.com', 'http://bg-usercode/bq.com', '2014-08-26 16:33:38', '2014-09-15 14:47:22', 0, 'USEABLE'),
	(5, '默认背景01', 'http://defaullt-background-01.gq66.com', 'http://defaullt-background-01.bq.com', '2014-09-12 11:14:58', '2014-10-16 10:59:43', 1, 'USEABLE'),
	(6, 'root', 'http://localhost:8080/pic/big2.png', 'http://localhost:8080/pic/big11.png', '2014-10-15 20:45:17', '2014-10-16 10:36:46', 0, 'UNUSEABLE'),
	(7, 'aa', 'http://localhost:8080/pic/dafegnmian1.png', 'http://localhost:8080/pic/dafegnmian3.png', '2014-10-16 10:37:36', '2014-10-16 10:37:36', 0, 'USEABLE'),
	(8, 'aa', 'http://localhost:8080/pic/dafegnmian3.png', 'http://localhost:8080/pic/dafegnmian3.png', '2014-10-16 11:23:16', '2014-10-16 11:23:39', 0, 'USEABLE'),
	(9, 'root', 'http://58.214.17.75:8081/pic/big1.png', 'http://58.214.17.75:8081/pic/big6.png', '2014-10-17 19:50:15', '2014-10-17 19:50:15', 0, 'USEABLE'),
	(10, 'root', 'http://58.214.17.75:8081/pic/big1.png', 'http://58.214.17.75:8081/pic/big6.png', '2014-10-17 19:50:29', '2014-10-17 19:50:29', 0, 'USEABLE'),
	(11, 'root', 'http://58.214.17.75:8081/pic/big1.png', 'http://58.214.17.75:8081/pic/big6.png', '2014-10-17 19:50:30', '2014-10-17 19:50:30', 0, 'USEABLE'),
	(12, 'root', 'http://58.214.17.75:8081/pic/big1.png', 'http://58.214.17.75:8081/pic/big6.png', '2014-10-17 19:50:30', '2014-10-17 19:50:30', 0, 'USEABLE'),
	(13, 'admin', 'http://58.214.17.75:8081/pic/big10.png', 'http://58.214.17.75:8081/pic/big10.png', '2014-10-17 19:51:37', '2014-10-17 19:51:37', 0, 'USEABLE'),
	(14, 'admin', 'http://58.214.17.75:8081/pic/big5.png', 'http://58.214.17.75:8081/pic/big10.png', '2014-10-17 19:52:02', '2014-10-17 19:52:02', 0, 'USEABLE'),
	(15, 'admin', 'http://58.214.17.75:8081/pic/big5.png', 'http://58.214.17.75:8081/pic/big10.png', '2014-10-17 19:52:03', '2014-10-17 19:52:03', 0, 'USEABLE'),
	(16, 'admin', 'http://58.214.17.75:8081/pic/big5.png', 'http://58.214.17.75:8081/pic/big10.png', '2014-10-17 19:52:04', '2014-10-17 19:52:04', 0, 'USEABLE'),
	(17, 'admin', 'http://58.214.17.75:8081/pic/big5.png', 'http://58.214.17.75:8081/pic/big10.png', '2014-10-17 19:52:04', '2014-10-17 19:52:04', 0, 'USEABLE'),
	(18, 'admin', 'http://58.214.17.75:8081/pic/big5.png', 'http://58.214.17.75:8081/pic/big10.png', '2014-10-17 19:52:05', '2014-10-17 19:52:05', 0, 'USEABLE'),
	(19, 'admin', 'http://58.214.17.75:8081/pic/big5.png', 'http://58.214.17.75:8081/pic/big10.png', '2014-10-17 19:52:06', '2014-10-17 19:52:06', 0, 'USEABLE'),
	(20, 'admin', 'http://58.214.17.75:8081/pic/big5.png', 'http://58.214.17.75:8081/pic/big10.png', '2014-10-17 19:52:06', '2014-10-17 19:52:06', 0, 'USEABLE'),
	(21, 'admin', 'http://58.214.17.75:8081/pic/big5.png', 'http://58.214.17.75:8081/pic/big10.png', '2014-10-17 19:52:07', '2014-10-17 19:52:07', 0, 'USEABLE'),
	(22, 'admin', 'http://58.214.17.75:8081/pic/big5.png', 'http://58.214.17.75:8081/pic/big10.png', '2014-10-17 19:52:07', '2014-10-17 19:52:07', 0, 'USEABLE'),
	(23, 'admin', 'http://58.214.17.75:8081/pic/big5.png', 'http://58.214.17.75:8081/pic/big10.png', '2014-10-17 19:52:07', '2014-10-17 19:52:07', 0, 'USEABLE'),
	(24, 'admin', 'http://58.214.17.75:8081/pic/big5.png', 'http://58.214.17.75:8081/pic/big10.png', '2014-10-17 19:52:53', '2014-10-17 19:52:54', 0, 'USEABLE'),
	(25, 'admin', 'http://58.214.17.75:8081/pic/big5.png', 'http://58.214.17.75:8081/pic/big10.png', '2014-10-17 19:53:11', '2014-10-17 19:53:11', 0, 'USEABLE'),
	(26, 'admin', 'http://58.214.17.75:8081/pic/big5.png', 'http://58.214.17.75:8081/pic/big10.png', '2014-10-17 19:53:12', '2014-10-17 19:53:12', 0, 'USEABLE');
/*!40000 ALTER TABLE `bss_background_image` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_background_image_device_map 结构
CREATE TABLE IF NOT EXISTS `bss_background_image_device_map` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT,
  `background_image_id` bigint(16) NOT NULL COMMENT '背景图片id',
  `ysten_id` varchar(32) DEFAULT NULL,
  `device_group_id` bigint(16) DEFAULT NULL COMMENT '设备分组id',
  `create_date` datetime NOT NULL,
  `type` enum('DEVICE','GROUP') DEFAULT NULL,
  `loop_time` bigint(16) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='背景图片--设备关系';

-- 正在导出表  bims_panel_jsyd_jt.bss_background_image_device_map 的数据：~0 rows (大约)
DELETE FROM `bss_background_image_device_map`;
/*!40000 ALTER TABLE `bss_background_image_device_map` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_background_image_device_map` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_background_image_user_map 结构
CREATE TABLE IF NOT EXISTS `bss_background_image_user_map` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `background_image_id` bigint(19) NOT NULL COMMENT '背景图片id',
  `code` varchar(32) DEFAULT NULL,
  `user_group_id` bigint(19) DEFAULT NULL COMMENT '设备分组id',
  `create_date` datetime NOT NULL,
  `type` varchar(32) DEFAULT NULL COMMENT 'GROUP;DEVICE',
  `loop_time` bigint(16) DEFAULT NULL COMMENT '图片切换间隔，单位毫秒。',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='背景图片--用户-用户组关系';

-- 正在导出表  bims_panel_jsyd_jt.bss_background_image_user_map 的数据：~0 rows (大约)
DELETE FROM `bss_background_image_user_map`;
/*!40000 ALTER TABLE `bss_background_image_user_map` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_background_image_user_map` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_boot_animation 结构
CREATE TABLE IF NOT EXISTS `bss_boot_animation` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `url` varchar(512) DEFAULT NULL,
  `md5` varchar(32) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `state` varchar(32) DEFAULT NULL,
  `is_default` int(12) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='开机动画';

-- 正在导出表  bims_panel_jsyd_jt.bss_boot_animation 的数据：~7 rows (大约)
DELETE FROM `bss_boot_animation`;
/*!40000 ALTER TABLE `bss_boot_animation` DISABLE KEYS */;
INSERT INTO `bss_boot_animation` (`id`, `name`, `url`, `md5`, `create_date`, `state`, `is_default`, `update_date`) VALUES
	(1, '开机动画测试', 'http://58.214.17.67:9144/attachments/xjanimation.zip', '22C754C250DB934EC19F237FB5AF965D', '2014-08-21 14:49:51', 'USEABLE', 0, '2014-10-15 16:59:35'),
	(2, 'xj-animation-01', 'http://usercode.zip', '11', '2014-08-26 16:29:31', 'USEABLE', 0, '2014-09-15 16:48:42'),
	(3, 'xj-animation-02', 'http://usergroup.zip', '22', '2014-08-26 16:29:49', 'USEABLE', 0, '2014-09-15 16:41:13'),
	(5, '4.7动画', 'http://58.214.17.67:8081/attachments/bootanimation1.zip', '8F34202D3711E3C71FD89F416CB082D3', '2014-09-22 17:56:32', 'USEABLE', 0, '2014-09-24 10:10:49'),
	(6, '开机引导', 'http://localhost:8080/pic/dafegnmian3.png', '33', '2014-10-15 20:10:48', 'USEABLE', 0, '2014-10-16 16:01:09'),
	(7, '33f', 'http://localhost:8080/ysten-bims-webapp/upload/big1.png', '33f', '2014-10-15 20:11:28', 'USEABLE', 0, '2014-10-15 20:21:39'),
	(8, 'xx', 'http://localhost:8080/pic/dafegnmian3.png', 'xx', '2014-10-15 20:13:45', 'USEABLE', 0, '2014-10-16 11:23:57'),
	(9, 'dd', 'http://58.214.17.75:8081/pic/big5.png', 'dd', '2014-10-17 20:09:22', 'USEABLE', 0, '2014-10-17 20:09:22');
/*!40000 ALTER TABLE `bss_boot_animation` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_city 结构
CREATE TABLE IF NOT EXISTS `bss_city` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `district_code` varchar(6) DEFAULT NULL,
  `code` varchar(32) NOT NULL,
  `name` varchar(32) DEFAULT NULL,
  `create_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `leader_id` bigint(19) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=379 DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_city 的数据：~327 rows (大约)
DELETE FROM `bss_city`;
/*!40000 ALTER TABLE `bss_city` DISABLE KEYS */;
INSERT INTO `bss_city` (`id`, `district_code`, `code`, `name`, `create_date`, `leader_id`) VALUES
	(5, '130000', '130000', '河北省', NULL, 1),
	(6, '610000', '610000', '陕西省', NULL, 1),
	(12, '320000', '320000', '江苏省', NULL, 1),
	(13, '330000', '330000', '浙江省', NULL, 1),
	(14, '360000', '360000', '江西省', NULL, 1),
	(15, '370000', '370000', '山东省', NULL, 1),
	(22, '510000', '510000', '四川省', NULL, 1),
	(24, '530000', '530000', '云南省', NULL, 1),
	(29, '650000', '650000', '新疆维吾尔自治区', NULL, 1),
	(30, '340000', '340000', '安徽省', NULL, 1),
	(101, '110000', '110000', '北京市', NULL, 8),
	(102, '120000', '120000', '天津市', NULL, 3),
	(103, '310000', '310000', '上海市', NULL, 2),
	(104, '500000', '500000', '重庆市', NULL, 4),
	(105, '130100', '130100', '石家庄市', NULL, 5),
	(106, '130200', '130200', '唐山市', NULL, 5),
	(107, '130281', '130281', '遵化市', NULL, 5),
	(108, '130283', '130283', '迁安市', NULL, 5),
	(109, '130300', '130300', '秦皇岛市', NULL, 5),
	(110, '130400', '130400', '邯郸市', NULL, 5),
	(111, '130481', '130481', '武安市', NULL, 5),
	(112, '130500', '130500', '邢台市', NULL, 5),
	(113, '130581', '130581', '南宫市', NULL, 5),
	(114, '130582', '130582', '沙河市', NULL, 5),
	(115, '130600', '130600', '保定市', NULL, 5),
	(116, '130681', '130681', '涿州市', NULL, 5),
	(117, '130682', '130682', '定州市', NULL, 5),
	(118, '130683', '130683', '安国市', NULL, 5),
	(119, '130684', '130684', '高碑店市', NULL, 5),
	(120, '130700', '130700', '张家口市', NULL, 5),
	(121, '130800', '130800', '承德市', NULL, 5),
	(122, '130900', '130900', '沧州市', NULL, 5),
	(123, '130981', '130981', '泊头市', NULL, 5),
	(124, '130982', '130982', '任丘市', NULL, 5),
	(125, '130983', '130983', '黄骅市', NULL, 5),
	(126, '130984', '130984', '河间市', NULL, 5),
	(127, '131000', '131000', '廊坊市', NULL, 5),
	(128, '131081', '131081', '霸州市', NULL, 5),
	(129, '131082', '131082', '三河市', NULL, 5),
	(130, '131100', '131100', '衡水市', NULL, 5),
	(131, '131181', '131181', '冀州市', NULL, 5),
	(132, '131182', '131182', '深州市', NULL, 5),
	(133, '320100', '14', '南京市', NULL, 12),
	(134, '320200', '19', '无锡市', NULL, 12),
	(135, '320281', 'jiangyin', '江阴市', NULL, 12),
	(136, '320282', 'yixing', '宜兴市', NULL, 12),
	(137, '320300', '16', '徐州市', NULL, 12),
	(138, '320400', '17', '常州市', NULL, 12),
	(139, '320500', '11', '苏州市', NULL, 12),
	(140, '320581', '320581', '常熟市', NULL, 12),
	(141, '320582', '808', '张家港市', NULL, 12),
	(142, '320583', '320583', '昆山市', NULL, 12),
	(143, '320584', '320584', '吴江市', NULL, 12),
	(144, '320585', '320585', '太仓市', NULL, 12),
	(145, '320600', '20', '南通市', NULL, 12),
	(146, '320700', '15', '连云港市', NULL, 12),
	(147, '320800', '12', '淮安市', NULL, 12),
	(148, '320900', '22', '盐城市', NULL, 12),
	(149, '321000', '23', '扬州市', NULL, 12),
	(150, '321100', '18', '镇江市', NULL, 12),
	(151, '321200', '21', '泰州市', NULL, 12),
	(152, '321300', '13', '宿迁市', NULL, 12),
	(153, '330100', '330100', '杭州市', NULL, 13),
	(154, '330182', '330182', '建德市', NULL, 13),
	(155, '330183', '330183', '富阳市', NULL, 13),
	(156, '330185', '330185', '临安市', NULL, 13),
	(157, '330200', '330200', '宁波市', NULL, 13),
	(158, '330281', '330281', '余姚市', NULL, 13),
	(159, '330282', '330282', '慈溪市', NULL, 13),
	(160, '330283', '330283', '奉化市', NULL, 13),
	(161, '330300', '330300', '温州市', NULL, 13),
	(162, '330381', '330381', '瑞安市', NULL, 13),
	(163, '330382', '330382', '乐清市', NULL, 13),
	(164, '330400', '330400', '嘉兴市', NULL, 13),
	(165, '330481', '330481', '海宁市', NULL, 13),
	(166, '330482', '330482', '平湖市', NULL, 13),
	(167, '330483', '330483', '桐乡市', NULL, 13),
	(168, '330500', '330500', '湖州市', NULL, 13),
	(169, '330600', '330600', '绍兴市', NULL, 13),
	(170, '330681', '330681', '诸暨市', NULL, 13),
	(171, '330682', '330682', '上虞市', NULL, 13),
	(172, '330683', '330683', '嵊州市', NULL, 13),
	(173, '330700', '330700', '金华市', NULL, 13),
	(174, '330781', '330781', '兰溪市', NULL, 13),
	(175, '330782', '330782', '义乌市', NULL, 13),
	(176, '330783', '330783', '东阳市', NULL, 13),
	(177, '330784', '330784', '永康市', NULL, 13),
	(178, '330800', '330800', '衢州市', NULL, 13),
	(179, '330881', '330881', '江山市', NULL, 13),
	(180, '330900', '330900', '舟山市', NULL, 13),
	(181, '331000', '331000', '台州市', NULL, 13),
	(182, '331081', '331081', '温岭市', NULL, 13),
	(183, '331082', '331082', '临海市', NULL, 13),
	(184, '331100', '331100', '丽水市', NULL, 13),
	(185, '331181', '331181', '龙泉市', NULL, 13),
	(186, '340100', '340100', '合肥市', NULL, 30),
	(187, '340200', '340200', '芜湖市', NULL, 30),
	(188, '340300', '340300', '蚌埠市', NULL, 30),
	(189, '340400', '340400', '淮南市', NULL, 30),
	(190, '340500', '340500', '马鞍山市', NULL, 30),
	(191, '340600', '340600', '淮北市', NULL, 30),
	(192, '340700', '340700', '铜陵市', NULL, 30),
	(193, '340800', '340800', '安庆市', NULL, 30),
	(194, '340881', '340881', '桐城市', NULL, 30),
	(195, '341000', '341000', '黄山市', NULL, 30),
	(196, '341100', '341100', '滁州市', NULL, 30),
	(197, '341181', '341181', '天长市', NULL, 30),
	(198, '341182', '341182', '明光市', NULL, 30),
	(199, '341200', '341200', '阜阳市', NULL, 30),
	(200, '341282', '341282', '界首市', NULL, 30),
	(201, '341300', '341300', '宿州市', NULL, 30),
	(202, '341400', '341400', '巢湖市', NULL, 30),
	(203, '341500', '341500', '六安市', NULL, 30),
	(204, '341600', '341600', '亳州市', NULL, 30),
	(205, '341700', '341700', '池州市', NULL, 30),
	(206, '341800', '341800', '宣城市', NULL, 30),
	(207, '341881', '341881', '宁国市', NULL, 30),
	(208, '360100', '360100', '南昌市', NULL, 14),
	(209, '360200', '360200', '景德镇市', NULL, 14),
	(210, '360281', '360281', '乐平市', NULL, 14),
	(211, '360300', '360300', '萍乡市', NULL, 14),
	(212, '360400', '360400', '九江市', NULL, 14),
	(213, '360481', '360481', '瑞昌市', NULL, 14),
	(214, '360500', '360500', '新余市', NULL, 14),
	(215, '360600', '360600', '鹰潭市', NULL, 14),
	(216, '360681', '360681', '贵溪市', NULL, 14),
	(217, '360700', '360700', '赣州市', NULL, 14),
	(218, '360781', '360781', '瑞金市', NULL, 14),
	(219, '360782', '360782', '南康市', NULL, 14),
	(220, '360800', '360800', '吉安市', NULL, 14),
	(221, '360900', '360900', '宜春市', NULL, 14),
	(222, '360902', '360902', '袁州市', NULL, 14),
	(223, '361000', '361000', '抚州市', NULL, 14),
	(224, '361100', '361100', '上饶市', NULL, 14),
	(225, '361181', '361181', '德兴市', NULL, 14),
	(226, '510100', '510100', '成都市', NULL, 22),
	(227, '510182', '510182', '彭州市', NULL, 22),
	(228, '510183', '510183', '邛崃市', NULL, 22),
	(229, '510184', '510184', '崇州市', NULL, 22),
	(230, '510300', '510300', '自贡市', NULL, 22),
	(231, '510400', '510400', '攀枝花市', NULL, 22),
	(232, '510500', '510500', '泸州市', NULL, 22),
	(233, '510600', '510600', '德阳市', NULL, 22),
	(234, '510683', '510683', '绵竹市', NULL, 22),
	(235, '510700', '510700', '绵阳市', NULL, 22),
	(236, '510781', '510781', '江油市', NULL, 22),
	(237, '510800', '510800', '广元市', NULL, 22),
	(238, '510900', '510900', '遂宁市', NULL, 22),
	(239, '511000', '511000', '内江市', NULL, 22),
	(240, '511100', '511100', '乐山市', NULL, 22),
	(241, '511181', '511181', '峨眉山市', NULL, 22),
	(242, '511300', '511300', '南充市', NULL, 22),
	(243, '511381', '511381', '阆中市', NULL, 22),
	(244, '511400', '511400', '眉山市', NULL, 22),
	(245, '511500', '511500', '宜宾市', NULL, 22),
	(246, '511600', '511600', '广安市', NULL, 22),
	(247, '511681', '511681', '华蓥市', NULL, 22),
	(248, '511700', '511700', '达州市', NULL, 22),
	(249, '511781', '511781', '万源市', NULL, 22),
	(250, '511800', '511800', '雅安市', NULL, 22),
	(251, '511900', '511900', '巴中市', NULL, 22),
	(252, '512000', '512000', '资阳市', NULL, 22),
	(253, '512081', '512081', '简阳市', NULL, 22),
	(254, '513200', '513200', '阿坝藏族羌族自治州', NULL, 22),
	(255, '513300', '513300', '甘孜藏族自治州', NULL, 22),
	(256, '513400', '513400', '凉山彝族自治州', NULL, 22),
	(257, '513401', '513401', '西昌市', NULL, 22),
	(258, '530100', '530100', '昆明市', NULL, 24),
	(259, '530181', '530181', '安宁市', NULL, 24),
	(260, '530300', '530300', '曲靖市', NULL, 24),
	(261, '530381', '530381', '宣威市', NULL, 24),
	(262, '530400', '530400', '玉溪市', NULL, 24),
	(263, '530500', '530500', '保山市', NULL, 24),
	(264, '530600', '530600', '昭通市', NULL, 24),
	(265, '530700', '530700', '丽江市', NULL, 24),
	(266, '530800', '530800', '思茅市', NULL, 24),
	(267, '530900', '530900', '临沧市', NULL, 24),
	(268, '532300', '532300', '楚雄彝族自治州', NULL, 24),
	(269, '532301', '532301', '楚雄市', NULL, 24),
	(270, '532500', '532500', '红河哈尼族彝族自治州', NULL, 24),
	(271, '532501', '532501', '个旧市', NULL, 24),
	(272, '532502', '532502', '开远市', NULL, 24),
	(273, '532600', '532600', '文山壮族苗族自治州', NULL, 24),
	(274, '532800', '532800', '西双版纳傣族自治州', NULL, 24),
	(275, '532801', '532801', '景洪市', NULL, 24),
	(276, '532900', '532900', '大理白族自治州', NULL, 24),
	(277, '532901', '532901', '大理市', NULL, 24),
	(278, '533100', '533100', '德宏傣族景颇族自治州', NULL, 24),
	(279, '533102', '533102', '瑞丽市', NULL, 24),
	(280, '533103', '533103', '潞西市', NULL, 24),
	(281, '533300', '533300', '怒江傈僳族自治州', NULL, 24),
	(282, '533400', '533400', '迪庆藏族自治州', NULL, 24),
	(283, '610100', '610100', '西安市', NULL, 6),
	(284, '610200', '610200', '铜川市', NULL, 6),
	(285, '610300', '610300', '宝鸡市', NULL, 6),
	(286, '610400', '610400', '咸阳市', NULL, 6),
	(287, '610481', '610481', '兴平市', NULL, 6),
	(288, '610500', '610500', '渭南市', NULL, 6),
	(289, '610581', '610581', '韩城市', NULL, 6),
	(290, '610582', '610582', '华阴市', NULL, 6),
	(291, '610600', '610600', '延安市', NULL, 6),
	(292, '610700', '610700', '汉中市', NULL, 6),
	(293, '610800', '610800', '榆林市', NULL, 6),
	(294, '610900', '610900', '安康市', NULL, 6),
	(295, '611000', '611000', '商洛市', NULL, 6),
	(296, '650100', '650100', '乌鲁木齐市', NULL, 29),
	(297, '650200', '650200', '克拉玛依市', NULL, 29),
	(298, '652100', '652100', '吐鲁番地区', NULL, 29),
	(299, '652101', '652101', '吐鲁番市', NULL, 29),
	(300, '652122', '652122', '鄯善市', NULL, 29),
	(301, '652200', '652200', '哈密地区', NULL, 29),
	(302, '652201', '652201', '哈密市', NULL, 29),
	(303, '652300', '652300', '昌吉回族自治州', NULL, 29),
	(304, '652301', '652301', '昌吉市', NULL, 29),
	(305, '652302', '652302', '阜康市', NULL, 29),
	(306, '652303', '652303', '米泉市', NULL, 29),
	(307, '652700', '652700', '博尔塔拉蒙古自治州', NULL, 29),
	(308, '652701', '652701', '博乐市', NULL, 29),
	(309, '652800', '652800', '巴音郭楞蒙古自治州', NULL, 29),
	(310, '652801', '652801', '库尔勒市', NULL, 29),
	(311, '652900', '652900', '阿克苏地区', NULL, 29),
	(312, '652901', '652901', '阿克苏市', NULL, 29),
	(313, '653000', '653000', '克孜勒苏柯尔克孜自治州', NULL, 29),
	(314, '653001', '653001', '阿图什市', NULL, 29),
	(315, '653100', '653100', '喀什地区', NULL, 29),
	(316, '653101', '653101', '喀什市', NULL, 29),
	(317, '653200', '653200', '和田地区', NULL, 29),
	(318, '653201', '653201', '和田市', NULL, 29),
	(319, '654000', '654000', '伊犁哈萨克自治州', NULL, 29),
	(320, '654002', '654002', '伊宁市', NULL, 29),
	(321, '654003', '654003', '奎屯市', NULL, 29),
	(322, '654200', '654200', '塔城地区', NULL, 29),
	(323, '654201', '654201', '塔城市', NULL, 29),
	(324, '654202', '654202', '乌苏市', NULL, 29),
	(325, '654300', '654300', '阿勒泰地区', NULL, 29),
	(326, '654301', '654301', '阿勒泰市', NULL, 29),
	(327, '659001', '659001', '石河子市', NULL, 29),
	(328, '659002', '659002', '阿拉尔市', NULL, 29),
	(329, '659003', '659003', '图木舒克市', NULL, 29),
	(330, '659004', '659004', '五家渠市', NULL, 29),
	(331, '370100', '370100', '济南市', NULL, 15),
	(332, '370181', '370181', '章丘市', NULL, 15),
	(333, '370200', '370200', '青岛市', NULL, 15),
	(334, '370281', '370281', '胶州市', NULL, 15),
	(335, '370282', '370282', '即墨市', NULL, 15),
	(336, '370283', '370283', '平度市', NULL, 15),
	(337, '370284', '370284', '胶南市', NULL, 15),
	(338, '370285', '370285', '莱西市', NULL, 15),
	(339, '370300', '370300', '淄博市', NULL, 15),
	(340, '370400', '370400', '枣庄市', NULL, 15),
	(341, '370481', '370481', '滕州市', NULL, 15),
	(342, '370500', '370500', '东营市', NULL, 15),
	(343, '370600', '370600', '烟台市', NULL, 15),
	(344, '370681', '370681', '龙口市', NULL, 15),
	(345, '370682', '370682', '莱阳市', NULL, 15),
	(346, '370683', '370683', '莱州市', NULL, 15),
	(347, '370684', '370684', '蓬莱市', NULL, 15),
	(348, '370685', '370685', '招远市', NULL, 15),
	(349, '370686', '370686', '栖霞市', NULL, 15),
	(350, '370687', '370687', '海阳市', NULL, 15),
	(351, '370700', '370700', '潍坊市', NULL, 15),
	(352, '370781', '370781', '青州市', NULL, 15),
	(353, '370782', '370782', '诸城市', NULL, 15),
	(354, '370783', '370783', '寿光市', NULL, 15),
	(355, '370784', '370784', '安丘市', NULL, 15),
	(356, '370785', '370785', '高密市', NULL, 15),
	(357, '370786', '370786', '昌邑市', NULL, 15),
	(358, '370800', '370800', '济宁市', NULL, 15),
	(359, '370881', '370881', '曲阜市', NULL, 15),
	(360, '370882', '370882', '兖州市', NULL, 15),
	(361, '370883', '370883', '邹城市', NULL, 15),
	(362, '370900', '370900', '泰安市', NULL, 15),
	(363, '370982', '370982', '新泰市', NULL, 15),
	(364, '370983', '370983', '肥城市', NULL, 15),
	(365, '371000', '371000', '威海市', NULL, 15),
	(366, '371081', '371081', '文登市', NULL, 15),
	(367, '371082', '371082', '荣成市', NULL, 15),
	(368, '371083', '371083', '乳山市', NULL, 15),
	(369, '371100', '371100', '日照市', NULL, 15),
	(370, '371200', '371200', '莱芜市', NULL, 15),
	(371, '371300', '371300', '临沂市', NULL, 15),
	(372, '371400', '371400', '德州市', NULL, 15),
	(373, '371481', '371481', '乐陵市', NULL, 15),
	(374, '371482', '371482', '禹城市', NULL, 15),
	(375, '371500', '371500', '聊城市', NULL, 15),
	(376, '371581', '371581', '临清市', NULL, 15),
	(377, '371600', '371600', '滨州市', NULL, 15),
	(378, '371700', '371700', '菏泽市', NULL, 15);
/*!40000 ALTER TABLE `bss_city` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_comsum_order 结构
CREATE TABLE IF NOT EXISTS `bss_comsum_order` (
  `id` bigint(19) NOT NULL,
  `order_id` bigint(19) DEFAULT NULL,
  `order_code` varchar(32) DEFAULT NULL,
  `consum_date` datetime DEFAULT NULL COMMENT '消费记录创建时间',
  `charge` decimal(10,0) DEFAULT NULL COMMENT '订单金额',
  `customer_code` varchar(32) DEFAULT NULL COMMENT '用户编号',
  `product_id` bigint(19) DEFAULT NULL,
  `product_name` varchar(32) DEFAULT NULL COMMENT '产品包名称',
  `product_type` varchar(32) DEFAULT NULL COMMENT '产品包类型，单片包还是基本包',
  `content_id` bigint(19) DEFAULT NULL,
  `content_name` varchar(32) DEFAULT NULL COMMENT '产品内容名称，只有产品类型为“单片”的时候才有效',
  `pay_type` varchar(32) DEFAULT NULL,
  `order_source` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_comsum_order 的数据：~0 rows (大约)
DELETE FROM `bss_comsum_order`;
/*!40000 ALTER TABLE `bss_comsum_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_comsum_order` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_customer 结构
CREATE TABLE IF NOT EXISTS `bss_customer` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `code` varchar(32) DEFAULT NULL,
  `outer_code` varchar(32) DEFAULT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  `login_name` varchar(60) DEFAULT NULL,
  `real_name` varchar(60) DEFAULT NULL,
  `nick_name` varchar(60) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `customer_type` varchar(32) DEFAULT NULL,
  `state` varchar(32) DEFAULT NULL,
  `sex` varchar(32) DEFAULT NULL,
  `phone` varchar(128) DEFAULT NULL,
  `profession` varchar(128) DEFAULT NULL,
  `hobby` varchar(512) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `identity_type` varchar(32) DEFAULT NULL,
  `identity_code` varchar(50) DEFAULT NULL,
  `state_change_date` datetime DEFAULT NULL,
  `zip_code` varchar(10) DEFAULT NULL,
  `address` varchar(2048) DEFAULT NULL,
  `area_id` int(10) DEFAULT NULL,
  `district_code` varchar(6) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `region` int(19) DEFAULT NULL,
  `county` varchar(32) DEFAULT NULL,
  `rate` varchar(32) DEFAULT NULL,
  `mail` varchar(32) DEFAULT NULL,
  `source` varchar(50) DEFAULT NULL,
  `device_code` varchar(30) DEFAULT NULL,
  `verification_code` varchar(32) DEFAULT NULL,
  `province` varchar(10) DEFAULT NULL,
  `age` int(3) DEFAULT NULL,
  `activate_date` datetime DEFAULT NULL,
  `service_stop` datetime DEFAULT NULL,
  `replacement` varchar(10) DEFAULT NULL COMMENT '是否换机标示',
  `update_time` datetime DEFAULT NULL COMMENT '用户记录修改时间',
  `terminal_model` varchar(10) DEFAULT '' COMMENT '终端型号，记录用户终端的具体型号',
  `suspended_date` datetime DEFAULT NULL COMMENT '用户停机时间',
  `cancelled_date` datetime DEFAULT NULL COMMENT '用户拆机时间',
  `is_lock` varchar(20) DEFAULT NULL,
  `is_sync` varchar(32) DEFAULT NULL,
  `cust_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_customer 的数据：~0 rows (大约)
DELETE FROM `bss_customer`;
/*!40000 ALTER TABLE `bss_customer` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_customer` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_customer_cust 结构
CREATE TABLE IF NOT EXISTS `bss_customer_cust` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `cust_id` varchar(32) DEFAULT NULL,
  `cust_name` varchar(32) DEFAULT NULL,
  `cust_type` varchar(20) DEFAULT NULL,
  `region` int(19) DEFAULT NULL,
  `link_name` varchar(32) DEFAULT NULL,
  `link_tel` varchar(20) DEFAULT NULL,
  `state` varchar(32) DEFAULT NULL,
  `cust_manager` varchar(32) DEFAULT NULL,
  `cust_developer` varchar(20) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `reservel_one` varchar(20) DEFAULT NULL,
  `reservel_two` varchar(20) DEFAULT NULL,
  `group_ip` varchar(128) DEFAULT NULL,
  `group_id` varchar(30) DEFAULT NULL,
  `max_termina` smallint(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

-- 正在导出表  bims_panel_jsyd_jt.bss_customer_cust 的数据：~0 rows (大约)
DELETE FROM `bss_customer_cust`;
/*!40000 ALTER TABLE `bss_customer_cust` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_customer_cust` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_customer_device_history 结构
CREATE TABLE IF NOT EXISTS `bss_customer_device_history` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `device_id` bigint(19) DEFAULT NULL,
  `device_code` varchar(32) DEFAULT NULL,
  `device_activate_date` datetime DEFAULT NULL,
  `customer_code` varchar(32) DEFAULT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  `customer_outer_code` varchar(6) DEFAULT NULL,
  `login_name` varchar(32) DEFAULT NULL,
  `phone` varchar(128) DEFAULT NULL,
  `customer_activate_date` datetime DEFAULT NULL,
  `customer_create_date` datetime DEFAULT NULL,
  `city_id` bigint(19) DEFAULT NULL,
  `area_id` bigint(19) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `description` varchar(32) DEFAULT NULL,
  `ysten_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_customer_device_history 的数据：~0 rows (大约)
DELETE FROM `bss_customer_device_history`;
/*!40000 ALTER TABLE `bss_customer_device_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_customer_device_history` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_customer_group 结构
CREATE TABLE IF NOT EXISTS `bss_customer_group` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `group_id` varchar(30) DEFAULT NULL,
  `group_name` varchar(128) DEFAULT NULL,
  `link_name` varchar(32) DEFAULT NULL,
  `link_tel` varchar(32) DEFAULT NULL,
  `state` varchar(32) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `additional_info` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

-- 正在导出表  bims_panel_jsyd_jt.bss_customer_group 的数据：~0 rows (大约)
DELETE FROM `bss_customer_group`;
/*!40000 ALTER TABLE `bss_customer_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_customer_group` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_device 结构
CREATE TABLE IF NOT EXISTS `bss_device` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `code` varchar(32) DEFAULT NULL,
  `sno` varchar(32) DEFAULT NULL,
  `mac` varchar(32) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `activate_date` datetime DEFAULT NULL,
  `state_change_date` datetime DEFAULT NULL,
  `expire_date` datetime DEFAULT NULL,
  `state` varchar(32) DEFAULT NULL,
  `bind_type` varchar(32) DEFAULT NULL,
  `distribute_state` varchar(32) DEFAULT NULL,
  `area_id` bigint(19) DEFAULT NULL,
  `district_code` varchar(6) DEFAULT NULL,
  `device_vendor_id` bigint(19) DEFAULT NULL,
  `device_type_id` bigint(19) DEFAULT NULL,
  `ip_address` varchar(32) DEFAULT NULL,
  `description` varchar(5000) DEFAULT NULL,
  `is_lock` varchar(32) DEFAULT NULL,
  `city_id` bigint(19) DEFAULT NULL,
  `sp_define_id` bigint(19) DEFAULT NULL,
  `product_no` varchar(32) DEFAULT NULL,
  `is_sync` varchar(32) DEFAULT NULL,
  `soft_code` varchar(64) DEFAULT NULL,
  `version_seq` int(11) DEFAULT NULL,
  `prepare_open` varchar(15) DEFAULT NULL,
  `ysten_id` varchar(64) DEFAULT NULL,
  `customer_id` bigint(19) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_CODE` (`code`) USING BTREE,
  UNIQUE KEY `ysten_id` (`ysten_id`),
  UNIQUE KEY `idx_mac` (`mac`)
) ENGINE=InnoDB AUTO_INCREMENT=10000016 DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_device 的数据：~0 rows (大约)
DELETE FROM `bss_device`;
/*!40000 ALTER TABLE `bss_device` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_device` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_device_customer_account_map 结构
CREATE TABLE IF NOT EXISTS `bss_device_customer_account_map` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `customer_id` bigint(19) DEFAULT NULL,
  `customer_code` varchar(32) DEFAULT NULL,
  `device_id` bigint(19) DEFAULT NULL,
  `device_code` varchar(32) DEFAULT NULL,
  `account_id` bigint(19) DEFAULT NULL,
  `account_code` varchar(32) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `replacement` varchar(30) DEFAULT NULL,
  `device_sno` varchar(32) DEFAULT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  `ysten_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_device_customer_account_map 的数据：~0 rows (大约)
DELETE FROM `bss_device_customer_account_map`;
/*!40000 ALTER TABLE `bss_device_customer_account_map` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_device_customer_account_map` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_device_group 结构
CREATE TABLE IF NOT EXISTS `bss_device_group` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  `area_id` bigint(19) DEFAULT NULL,
  `p_device_group_id` bigint(19) DEFAULT NULL,
  `description` varchar(2048) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `type` varchar(128) DEFAULT NULL COMMENT '分组的类型\\r\\n            按业务功能分为消息、升级、Panel、背景、开机动画、区域、通用分组',
  `dynamic_flag` int(1) DEFAULT NULL COMMENT '是否是动态分组标识',
  `sql_expression` varchar(2048) DEFAULT NULL COMMENT 'sql表达式',
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=272 DEFAULT CHARSET=utf8 COMMENT='终端分组';

-- 正在导出表  bims_panel_jsyd_jt.bss_device_group 的数据：~219 rows (大约)
DELETE FROM `bss_device_group`;
/*!40000 ALTER TABLE `bss_device_group` DISABLE KEYS */;
INSERT INTO `bss_device_group` (`id`, `name`, `area_id`, `p_device_group_id`, `description`, `create_date`, `type`, `dynamic_flag`, `sql_expression`, `update_date`) VALUES
	(1, 'YSTen-betates', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(2, '华录一体机', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(3, 'YSTen-betates_new', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(4, '华录BDP2040', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(5, '华录BDP2046', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(6, '华录BDP2040_2', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(7, '华录BDP2046_2', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(8, '同方MTK8803_test', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(9, 'isboxTCC8803_01', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(10, '九州STi7162_01', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(11, 'isboxTCC8803_02', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(12, '九州STi7162_02', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(13, 'isboxTCC8803_03', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(14, '华录BDP2040_new01', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(15, 'isboxTCC8803_04', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(16, '海信6i80_开发', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(17, '华录BDP2046_new01', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(18, '华录BDP2039_01', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(19, '华录BDP2050_01', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(20, '华录N6_01-01', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(21, '华录N6_01-02', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(22, '华录N8_01-01', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(23, '华录N8_01-02', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(24, 'isboxTCC8803_05', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(25, 'isboxTCC8803_06', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(26, 'isboxTCC8803_07', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(27, 'isboxE1_01', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(28, 'isboxTCC8803_08', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(29, '海信6i80_测试', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(30, 'isboxTCC8803_09', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(31, '海信6i80_厂测', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(32, 'isboxTCC8803_10', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(33, '海信6i80', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(34, 'isboxTCC8803_11', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(35, '统一测试号', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(36, '海信K580', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(37, '网动版测试', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(38, '江西_E1', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(39, 'isbox_sp', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(40, '国家电网', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(41, '海信XT770', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(42, '海信K660', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(43, '雄联_网动版1', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(44, 'TCL_V7500_01', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(45, 'TCL_6500_03', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(46, '海信XT880_01', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(47, '天敏TM5_01', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(48, '易视宝E2_01', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(49, 'isboxTCC8803_12', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(50, 'guojiakaifangdaxue_isboxTCC8803', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(51, '海思K3V2_0924', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(52, 'TCL_6500_02', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(53, '易视宝IS-D1_01', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(54, '海信XT780_01', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(55, '海信XT900_01', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(56, '碧维视BeTVU5_01', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(57, '冠捷_47PFL7730_01', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(58, '冠捷_55PFL7730_01', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(59, '冠捷_47PFL8730_01', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(60, '冠捷_55PFL8730_01', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(61, '吉林联通', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(62, '康佳-001', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(63, '康佳-002', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(64, '创维-E600Y', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(65, '创维-E530S', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(66, '清华同方-灵悦3', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(67, 'TCL_F3200_01', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(68, '中国电力企业联合会', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(69, '乐视-C1', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(70, '海南联通', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(71, '易视腾E2S_01', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(72, '华录N5_014', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(73, '陕西', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(74, '江西_E2', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(75, '华录L1', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(76, '天敏I', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(77, '小米MDZ-05-AA', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(78, 'TCLF3500A', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(79, 'TCL7600', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(80, 'TCL8500', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(81, '碧维视BeTV-U7', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(82, '华录N9', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(83, '海信K600D', NULL, NULL, '海信元春机绑定65536个MAC', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(84, '海信PX2800', NULL, NULL, '海信PX2800  2000个正式MAC地址报备', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(85, '小米测试', NULL, NULL, '186个小米测试盒子绑定', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(86, '华录N8_01_02网动版', NULL, NULL, '2025个华录N8_01_02网动版', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(87, '易视腾-E3', NULL, NULL, '新申请的20000个E3的广电号', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(88, '康佳9200', NULL, NULL, ' 康佳申请50W个ID，型号为9200， 芯片：MST6A901  \nCNTVID： 010105003000001~010105003500000\n 伪SN地址段：WKDCN 0000100201 92000 --- WKDCN 0000600200 92000请对康佳这50万个9200系列单独  分组   和以前的  8200系列区分开来', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(89, '乐视X60', NULL, NULL, '乐视X60电视机新增终端规则--储磊', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(90, '乐视S40', NULL, NULL, '新建乐视S40电视机终端规则--储磊', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(91, '雷柏RATV', NULL, NULL, '雷柏申请10000个ID，型号为RATV', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(92, 'TCL_E5690A', NULL, NULL, 'TCL_E5690A\nMS901平台\n正式号报备5w（本批共50w）', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(93, '易视腾-EL', NULL, NULL, '', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(94, '重庆', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(95, '四川', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(96, '华录炫动改网动', NULL, NULL, '请将之前评估过的共15460个ID，在后台进行炫动版到网动版的全部切换', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(97, '一帆德G1W-Z', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(98, '一帆德G1W-Z-bims', NULL, NULL, '2013/7/9 300个mac报备', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(99, '海信PX3000', NULL, NULL, '海信PX3000绑定1000个MAC-储磊20130717', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(100, '中航院小网', NULL, NULL, '中航院小网正式mac报备20350个', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(101, '冠捷PFL5040', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(102, '金通文化G6', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(103, '冠捷_realtek', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(104, '冠捷PFL5241', NULL, NULL, '型号：5241     网动版  芯片：Realtek2694A', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(105, '冠捷PFL9340', NULL, NULL, '型号：9340  飘动python版 芯片：Panasonic SLD3', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(106, '海信K680', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(107, '海信XT910', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(108, '佳视互动-A18', NULL, NULL, '佳视互动-A18  AML-8726-M3', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(109, '康佳-9500', NULL, NULL, '型号：9500   芯片：MST818', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(110, '康佳_9500', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(111, '小米L47M1-AA', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(112, '云联YLB-A2', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(113, '华为M310', NULL, NULL, '华为M310', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(114, '易世腾IS-E4', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(115, '中航院_H200', NULL, NULL, '望海楼小网OTT正式MAC地址报备2500个　', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(116, '新疆', NULL, NULL, '新疆', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(117, '安徽', NULL, NULL, '安徽', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(118, '广东广电_华为', NULL, NULL, '广东广电  华为驻地1000个', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(119, '快播R810', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(120, '快播R810S', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(121, '广东', NULL, NULL, '广东', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(122, '广西', NULL, NULL, '广西', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(123, '快播R820', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(124, '长虹C2000', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(125, '27test测试分组', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(126, '睿盈达美万嘉', NULL, NULL, '睿盈达美万嘉\r\n42	睿盈达	M9	　	　	2000 	010142001000001	010142001002000	正式	李静	2013.10.28/XX/XX								\r\n42	睿盈达	M9	　	　	4000 	010142001002001	010142001006000	正式	张影	2013.12.23/XX/XX\r\n', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(127, '海信PX2600', NULL, NULL, 'PX2600 芯片：Amologic M3 数量：2000个python', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(128, '吉林分组', NULL, NULL, '吉林分组', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(129, '宁夏分组', NULL, NULL, '宁夏分组', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(130, '云南分组', NULL, NULL, '云南分组', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(131, '江苏分组', NULL, NULL, '江苏分组', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(132, '海信vidaa', NULL, NULL, '海信vidaa 50000\r\n\r\n     Panel ID：29      EPG ID：4\r\n     MAC：C816BD000000～C816BD00C34F\r\n     ID：010104009616237～010104009666236', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(133, '海信PX2700', NULL, NULL, '海信PX2700（vidaa box）', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(134, '康佳K60U', NULL, NULL, '\r\n          康佳 K60U (KKTV)     芯片：MST818A   数量：20000个\r\n \r\n         CNTV ID：010104005000001~010104005020000  \r\n \r\n         SN串号段：WKDCN 0000110002 K60U0 ----- WKDCN 0000130003 K60U0      \r\n \r\n          EPG模版ID：35 面板模版ID：194', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(135, 'uplink艾普林克', NULL, NULL, 'uplink 艾普林克', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(136, '湖北分组', NULL, NULL, '湖北分组', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(137, '海信PX2600-1', NULL, NULL, '增加到 010104052018001~010104052026001    8000个终端', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(138, '海信PX2700（VIDAA 模式）', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(139, 'PFL5341', NULL, NULL, 'TPV（冠捷）型号：PFL5341 芯片：MSD6A608 数量：10000个 Python版 易视腾平台', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(140, '江西_E4', NULL, NULL, '江西E4终端分组', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(141, '陕西_E4', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(142, '四川_E4', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(143, '新疆_E4', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(144, '安徽_E4', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(145, '江苏_E4', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(146, '湖北_E4', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(147, '重庆_E4', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(148, '宁夏_E4', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(149, '云南_E4', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(150, '吉林_E4', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(151, '海信XT910_01', NULL, NULL, '型号：XT910 Panel 190 EPG 4\r\n   MAC地址段:C8-16-BD-13-0D-40~C8-16-BD-13-34-4F\r\n  ID号段：010104011100002~010104011110001\r\n', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(152, '广西_E4', NULL, NULL, '广西_E4', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(153, '优酷Q盒子', NULL, NULL, '优酷专用', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(154, '河北分组', NULL, NULL, '河北分组', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(155, '河北_E4', NULL, NULL, '河北_E4', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(156, '浙江_E4', NULL, NULL, '浙江_E4', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(157, '云南移动分组', NULL, NULL, '云南移动分组', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(158, '云南移动_E4', NULL, NULL, '云南移动_E4', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(159, '四川烽火分组', NULL, NULL, '四川烽火分组', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(160, '四川中兴分组', NULL, NULL, '四川中兴分组', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(161, '四川华为分组', NULL, NULL, '四川华为分组', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(162, '四川-中兴第三方分组', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(163, 'EPG3.0测试', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(164, '海南移动-九联', NULL, NULL, '基地模式-海南移动  九联 17个盒子  绑定模板244，设备分组04\r\n\r\n              串号段：014444000015374~014444000015390\r\n', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(165, '安徽Cobra测试', NULL, NULL, '安徽Cobra测试', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(166, '消防台项目', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(167, '大连有线', NULL, NULL, '大连有线分组\r\n绑定面板模版ID：14       EPG：4\r\n20140319创建', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(168, '湖南_E4', NULL, NULL, '湖南移动E4分组，麻烦将湖南E4分组的默认模板调整为26100072', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(169, '重庆铁通E4', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(170, '湖北E4新分组', NULL, NULL, '自4月16日起，新到出货数据需绑定到此新分组', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(171, '三星电视', NULL, NULL, '现发出三星提出的互联网电视ID申请函扫描件及MAC地址（附件）：\r\n    (1) 型号：UHU9800    数量：200,000个 易视腾平台\r\n    MAC地址段：90F1AA1053B0——90F1AA1360EF\r\n\r\n\r\n20140716-ID：010210001599993~010210001799992', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(172, '辽宁新媒体', NULL, NULL, '辽宁新媒体', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(173, '湖北移动-九联', NULL, NULL, '基地模式服务集合 湖北移动-九联， Panel:244, Epg:72', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(174, '长虹MTK5327', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(175, '江西零散终端', NULL, NULL, '因业务需要需要解除升级等需要踢出原有设备分组的，为保证正常业务正常继续记性又便于统计这些零散终端的分组', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(176, '礼品终端', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(177, '湖南移动-九联', NULL, NULL, '湖南移动有九联等厂家的设备需要进行测试，申请新建一个湖南九联设备分组，绑定基地模式服务集合，默认模板26100072', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(178, '海信XT780_1', NULL, NULL, '  (3)型号XT780 芯片：MSD6A901    数量：100000个  飘动版       panel:190     epg:4\r\n     MAC地址段为：C816BD5DAF30到C816BD5F35CF\r\n     ID:010104007383249~010104007483248', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(179, '江苏软终端设备分组', NULL, NULL, '江苏软终端设备分组', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(180, '监播监控终端分组', NULL, NULL, '各地监播监控终端', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(181, '河北零散终端分组', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(182, '海尔', NULL, NULL, '20个测试：447BC4FF0001~447BC4FF0014\r\n50个测试：44-7b-c4-ff-00-15~44-7b-c4-ff-00-47\r\n1000个正式：44-7b-c4-ff-00-48 ------ 44-7b-c4-ff-04-2f', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(183, '安徽软终端设备分组', NULL, NULL, '安徽中兴、华为软终端设备分组', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(184, '广东软终端设备分组', NULL, NULL, '广东软终端移动分组', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(185, '四川软终端设备分组', NULL, NULL, '四川软终端设备分组', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(186, 'tpv818', NULL, NULL, '00246754D72A-------------00246754EAB1\r\n00246754FE9E--------------002467551225', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(187, 'tpv 0729', NULL, NULL, 'ID：010109011000001~010109011030000，共30000个', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(188, '大连广电', NULL, NULL, '010160001001821 ~010160001002201', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(189, '浙江暂存', NULL, NULL, NULL, '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(190, '西藏移动-九联', NULL, NULL, '西藏移动九联 管理模板24400072', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(191, '重庆测试设备分组', NULL, NULL, '用于测试', '2014-08-22 22:35:38', 'PANEL', 0, NULL, NULL),
	(256, '中心测试用', 9999, NULL, '中心测试用', '2014-08-22 22:40:52', 'PANEL', 0, '', NULL),
	(260, 'teste', 10, NULL, '', '2014-08-26 15:17:13', 'PANEL', 0, '', NULL),
	(261, '消息-WXY', 14, NULL, '', '2014-08-27 15:43:20', 'NOTICE', 0, '', NULL),
	(262, 'test', 14, NULL, 'test', '2014-08-28 09:57:13', 'BACKGROUND', 0, '', NULL),
	(263, 'upgrade_BJ', 8, NULL, '', '2014-08-29 09:38:02', 'UPGRADE', 0, '', NULL),
	(264, 'upgrade_JS', 12, NULL, '', '2014-08-29 11:07:31', 'UPGRADE', 0, '', NULL),
	(265, '升级-江西', 14, NULL, '', '2014-09-05 10:39:02', 'UPGRADE', 0, '', NULL),
	(266, '升级-江西2', 14, NULL, '', '2014-09-05 10:39:15', 'UPGRADE', 0, '', NULL),
	(267, '江西省-面板', 14, NULL, '', '2014-09-09 11:02:57', 'PANEL', 0, '', NULL),
	(268, '324', 2, NULL, '234', '2014-09-17 14:14:01', 'UPGRADE', 0, '', NULL),
	(269, 'test2', 14, NULL, 'qianyina', '2014-09-19 11:36:33', 'BACKGROUND', 0, '', NULL),
	(270, 'issacTest', 12, NULL, '11', '2014-10-10 16:06:01', 'UPGRADE', 0, '', '2014-10-11 17:20:27'),
	(271, 'hello', 9999, NULL, '', '2014-10-16 15:04:57', 'APPUPGRADE', 0, '', NULL);
/*!40000 ALTER TABLE `bss_device_group` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_device_group_map 结构
CREATE TABLE IF NOT EXISTS `bss_device_group_map` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `ysten_id` varchar(32) DEFAULT NULL,
  `device_group_id` bigint(16) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ysten_id_idx` (`ysten_id`) USING BTREE,
  KEY `device_group_id_idx` (`device_group_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='终端--分组关联表';

-- 正在导出表  bims_panel_jsyd_jt.bss_device_group_map 的数据：~0 rows (大约)
DELETE FROM `bss_device_group_map`;
/*!40000 ALTER TABLE `bss_device_group_map` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_device_group_map` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_device_history 结构
CREATE TABLE IF NOT EXISTS `bss_device_history` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `code` varchar(32) DEFAULT NULL,
  `sno` varchar(32) DEFAULT NULL,
  `mac` varchar(32) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `activate_date` datetime DEFAULT NULL,
  `state_change_date` datetime DEFAULT NULL,
  `expire_date` datetime DEFAULT NULL,
  `state` varchar(32) DEFAULT NULL,
  `bind_type` varchar(32) DEFAULT NULL,
  `distribute_state` varchar(32) DEFAULT NULL,
  `area_id` bigint(19) DEFAULT NULL,
  `device_vendor_id` bigint(19) DEFAULT NULL,
  `device_type_id` bigint(19) DEFAULT NULL,
  `ip_address` varchar(32) DEFAULT NULL,
  `description` varchar(5000) DEFAULT NULL,
  `is_lock` varchar(32) DEFAULT NULL,
  `city_id` bigint(19) DEFAULT NULL,
  `sp_define_id` bigint(19) DEFAULT NULL,
  `product_no` varchar(32) DEFAULT NULL,
  `is_sync` varchar(32) DEFAULT NULL,
  `soft_code` varchar(64) DEFAULT NULL,
  `version_seq` int(11) DEFAULT NULL,
  `ysten_id` varchar(32) DEFAULT NULL,
  `prepare_open` varchar(15) DEFAULT NULL,
  `history_create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_device_history 的数据：~0 rows (大约)
DELETE FROM `bss_device_history`;
/*!40000 ALTER TABLE `bss_device_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_device_history` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_device_ip 结构
CREATE TABLE IF NOT EXISTS `bss_device_ip` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `ip_seg` varchar(16) DEFAULT NULL,
  `mask_length` bigint(12) DEFAULT NULL,
  `start_ip_value` decimal(13,1) DEFAULT NULL,
  `end_ip_value` decimal(13,1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_device_ip 的数据：~0 rows (大约)
DELETE FROM `bss_device_ip`;
/*!40000 ALTER TABLE `bss_device_ip` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_device_ip` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_device_notice_map 结构
CREATE TABLE IF NOT EXISTS `bss_device_notice_map` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `ysten_id` varchar(32) DEFAULT NULL,
  `notice_id` bigint(19) DEFAULT NULL COMMENT '根据type类型的不同关联不同的任务ID或panel的分组ID',
  `device_group_id` bigint(19) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `type` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='终端-消息关联表';

-- 正在导出表  bims_panel_jsyd_jt.bss_device_notice_map 的数据：~0 rows (大约)
DELETE FROM `bss_device_notice_map`;
/*!40000 ALTER TABLE `bss_device_notice_map` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_device_notice_map` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_device_operate_log 结构
CREATE TABLE IF NOT EXISTS `bss_device_operate_log` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `device_code` varchar(32) DEFAULT NULL,
  `ip` varchar(20) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `result` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_device_operate_log 的数据：0 rows
DELETE FROM `bss_device_operate_log`;
/*!40000 ALTER TABLE `bss_device_operate_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_device_operate_log` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_device_software_code 结构
CREATE TABLE IF NOT EXISTS `bss_device_software_code` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT COMMENT '软件号',
  `name` varchar(32) DEFAULT NULL COMMENT '名称',
  `code` varchar(64) DEFAULT NULL COMMENT ' 编码',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `status` varchar(16) DEFAULT NULL COMMENT '状态',
  `description` varchar(2048) DEFAULT NULL COMMENT '描述',
  `last_modify_time` datetime DEFAULT NULL,
  `oper_user` varchar(50) DEFAULT NULL,
  `distribute_state` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='设备软件号';

-- 正在导出表  bims_panel_jsyd_jt.bss_device_software_code 的数据：~5 rows (大约)
DELETE FROM `bss_device_software_code`;
/*!40000 ALTER TABLE `bss_device_software_code` DISABLE KEYS */;
INSERT INTO `bss_device_software_code` (`id`, `name`, `code`, `create_date`, `status`, `description`, `last_modify_time`, `oper_user`, `distribute_state`) VALUES
	(1, '设备升级-0714', 'W0714', '2014-08-20 14:15:03', 'USABLE', 'W0714', NULL, 'admin', 'UNDISTRIBUTE'),
	(2, 'xj-software-0826', '0826', '2014-08-26 10:26:55', 'USABLE', '', NULL, 'admin', 'UNDISTRIBUTE'),
	(3, '设备升级-0826', '4.0.1.301', '2014-08-26 15:10:08', 'USABLE', '', NULL, 'admin', 'UNDISTRIBUTE'),
	(4, '易视腾E5终端', 'is_E520140304165715601', '2014-09-01 10:58:45', 'USABLE', '', NULL, 'admin', 'UNDISTRIBUTE'),
	(5, '4.7升级-BJ', '00000021201305021525593', '2014-09-22 17:17:30', 'USABLE', '', '2014-09-22 18:08:27', 'admin', 'UNDISTRIBUTE');
/*!40000 ALTER TABLE `bss_device_software_code` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_device_software_package 结构
CREATE TABLE IF NOT EXISTS `bss_device_software_package` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT COMMENT '软件包ID',
  `version_seq` int(11) DEFAULT NULL COMMENT '软件版本序号',
  `version_name` varchar(32) DEFAULT NULL COMMENT '软件版本名称',
  `soft_code_id` bigint(19) DEFAULT NULL COMMENT '软件号',
  `PACKAGE_STATUS` varchar(32) DEFAULT NULL,
  `package_type` varchar(32) DEFAULT NULL COMMENT '软件包类型TEST("测试"), RELEASE("发布");',
  `package_location` varchar(255) DEFAULT NULL COMMENT '软件包绝对路径',
  `is_mandatory` varchar(32) DEFAULT NULL COMMENT '是否强制升级MANDATORY("强制"), NOTMANDATORY("不强制");',
  `md5` varchar(32) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `device_version_seq` int(11) DEFAULT '0' COMMENT '终端当前版本号',
  `full_software_id` bigint(12) DEFAULT NULL COMMENT '全量包软件ID',
  `last_modify_time` datetime DEFAULT NULL,
  `distribute_state` varchar(32) DEFAULT NULL,
  `oper_user` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `soft_code_id` (`soft_code_id`),
  CONSTRAINT `bss_device_software_package_ibfk_1` FOREIGN KEY (`soft_code_id`) REFERENCES `bss_device_software_code` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='终端升级软件包信息（apk路径）';

-- 正在导出表  bims_panel_jsyd_jt.bss_device_software_package 的数据：~7 rows (大约)
DELETE FROM `bss_device_software_package`;
/*!40000 ALTER TABLE `bss_device_software_package` DISABLE KEYS */;
INSERT INTO `bss_device_software_package` (`id`, `version_seq`, `version_name`, `soft_code_id`, `PACKAGE_STATUS`, `package_type`, `package_location`, `is_mandatory`, `md5`, `create_date`, `device_version_seq`, `full_software_id`, `last_modify_time`, `distribute_state`, `oper_user`) VALUES
	(1, 1, 'xj-version-0826', 2, 'TEST', 'FULL', '12', 'NOTMANDATORY', '123', '2014-08-26 10:27:49', NULL, NULL, NULL, 'UNDISTRIBUTE', 'admin'),
	(2, 5, '设备升级-0826', 3, 'TEST', 'FULL', 'http://www.baidu.com', 'NOTMANDATORY', '222334455', '2014-08-26 15:11:05', NULL, NULL, '2014-08-26 15:11:13', 'UNDISTRIBUTE', 'admin'),
	(3, 2, '版本-0714', 1, 'TEST', 'FULL', '2', 'NOTMANDATORY', '2', '2014-08-28 11:07:44', 1, NULL, NULL, 'UNDISTRIBUTE', 'admin'),
	(4, 28, 'V4.6', 4, 'RELEASE', 'FULL', '543511111', 'MANDATORY', '3432432', '2014-09-01 10:59:45', NULL, NULL, NULL, 'UNDISTRIBUTE', 'admin'),
	(5, 28, 'V4.6', 4, 'RELEASE', 'INCREMENT', '4444444', 'MANDATORY', '5466666', '2014-09-01 11:00:30', 12, 4, NULL, 'UNDISTRIBUTE', 'admin'),
	(6, 27, '4.7升级联调-BJ', 5, 'TEST', 'FULL', 'http://58.214.17.67:8081/attachments/update.zip', 'NOTMANDATORY', '055D736BB7EBD2772F18778FDC7046F0', '2014-09-22 17:19:23', NULL, NULL, '2014-09-22 18:08:43', 'UNDISTRIBUTE', 'admin'),
	(7, 45, '测试包', 5, 'TEST', 'INCREMENT', '56565', 'NOTMANDATORY', '56565', '2014-10-16 09:50:25', 5, 6, NULL, 'UNDISTRIBUTE', 'admin');
/*!40000 ALTER TABLE `bss_device_software_package` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_device_type 结构
CREATE TABLE IF NOT EXISTS `bss_device_type` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `code` varchar(32) DEFAULT NULL,
  `device_vendor_id` bigint(19) DEFAULT NULL,
  `state` varchar(32) DEFAULT NULL,
  `terminal_type` varchar(64) DEFAULT NULL,
  `description` varchar(5000) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=176 DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_device_type 的数据：~8 rows (大约)
DELETE FROM `bss_device_type`;
/*!40000 ALTER TABLE `bss_device_type` DISABLE KEYS */;
INSERT INTO `bss_device_type` (`id`, `name`, `code`, `device_vendor_id`, `state`, `terminal_type`, `description`, `create_date`) VALUES
	(1, '未知型号', '0001', 70, 'USABLE', 'TV', '未知型号', '2014-08-21 17:09:44'),
	(2, 'ysten', '0201', 70, 'USABLE', 'TV', 'ysten', '2014-08-20 13:02:27'),
	(103, '易视宝-E1', '0001', 70, 'USABLE', 'TV', '易视宝-E1', '2014-08-21 17:09:49'),
	(104, '易视宝-E2S', '0002', 70, 'USABLE', 'TV', '易视宝-E2S', '2014-08-21 17:09:52'),
	(170, '易视宝-E4', '0004', 70, 'USABLE', 'TV', '易视宝-E4', '2014-08-21 17:09:54'),
	(171, '易视宝-E5', '0006', 70, 'USABLE', 'TV', '易视宝-E5', '2014-08-21 17:09:56'),
	(172, '易视宝-E4S', '0005', 70, 'USABLE', 'TV', '易视宝-E4S', '2014-08-25 10:31:32'),
	(175, '易视宝-E3', '0003', 70, 'USABLE', 'TV', '易世宝-E3', '2014-08-21 17:09:58');
/*!40000 ALTER TABLE `bss_device_type` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_device_upgrade_map 结构
CREATE TABLE IF NOT EXISTS `bss_device_upgrade_map` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT,
  `ysten_id` varchar(32) DEFAULT NULL,
  `upgrade_id` bigint(16) DEFAULT NULL,
  `device_group_id` bigint(16) DEFAULT NULL,
  `type` varchar(32) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备-升级关联表';

-- 正在导出表  bims_panel_jsyd_jt.bss_device_upgrade_map 的数据：~0 rows (大约)
DELETE FROM `bss_device_upgrade_map`;
/*!40000 ALTER TABLE `bss_device_upgrade_map` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_device_upgrade_map` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_device_upgrade_result_log 结构
CREATE TABLE IF NOT EXISTS `bss_device_upgrade_result_log` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `device_code` varchar(36) DEFAULT NULL,
  `soft_code` varchar(64) DEFAULT NULL,
  `device_version_seq` int(11) DEFAULT NULL,
  `version_seq` int(11) DEFAULT NULL,
  `status` enum('SUCCESS','FAILED','UNDFEFINED') DEFAULT NULL,
  `duration` bigint(16) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `ysten_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_device_upgrade_result_log 的数据：~0 rows (大约)
DELETE FROM `bss_device_upgrade_result_log`;
/*!40000 ALTER TABLE `bss_device_upgrade_result_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_device_upgrade_result_log` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_device_vendor 结构
CREATE TABLE IF NOT EXISTS `bss_device_vendor` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `code` varchar(32) DEFAULT NULL,
  `state` varchar(32) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `description` varchar(5000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_device_vendor 的数据：~1 rows (大约)
DELETE FROM `bss_device_vendor`;
/*!40000 ALTER TABLE `bss_device_vendor` DISABLE KEYS */;
INSERT INTO `bss_device_vendor` (`id`, `name`, `code`, `state`, `create_date`, `description`) VALUES
	(70, '易视腾', '01', 'USABLE', '2013-07-14 00:21:32', '易视腾');
/*!40000 ALTER TABLE `bss_device_vendor` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_discount_policy_define_product 结构
CREATE TABLE IF NOT EXISTS `bss_discount_policy_define_product` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  `check_type` varchar(30) DEFAULT NULL,
  `check_par1` varchar(30) DEFAULT NULL,
  `discount_type` varchar(30) DEFAULT NULL,
  `discount_par1` varchar(30) DEFAULT NULL,
  `inner_icon` varchar(256) DEFAULT NULL,
  `outer_icon` varchar(256) DEFAULT NULL,
  `description` varchar(30) DEFAULT NULL,
  `check_par2` varchar(30) DEFAULT NULL,
  `discount_par2` varchar(30) DEFAULT NULL,
  `group_id` bigint(19) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_discount_policy_define_product 的数据：~0 rows (大约)
DELETE FROM `bss_discount_policy_define_product`;
/*!40000 ALTER TABLE `bss_discount_policy_define_product` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_discount_policy_define_product` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_discount_policy_group 结构
CREATE TABLE IF NOT EXISTS `bss_discount_policy_group` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  `description` varchar(5000) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `operate_type` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_discount_policy_group 的数据：~0 rows (大约)
DELETE FROM `bss_discount_policy_group`;
/*!40000 ALTER TABLE `bss_discount_policy_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_discount_policy_group` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_interface_log 结构
CREATE TABLE IF NOT EXISTS `bss_interface_log` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `interface_name` varchar(32) DEFAULT NULL,
  `caller` varchar(100) DEFAULT NULL,
  `call_time` datetime DEFAULT NULL,
  `input` text,
  `output` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_interface_log 的数据：~0 rows (大约)
DELETE FROM `bss_interface_log`;
/*!40000 ALTER TABLE `bss_interface_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_interface_log` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_interface_url 结构
CREATE TABLE IF NOT EXISTS `bss_interface_url` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT,
  `interface_name` varchar(32) DEFAULT NULL,
  `area_id` bigint(16) DEFAULT NULL,
  `interface_url` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='省接口Url表';

-- 正在导出表  bims_panel_jsyd_jt.bss_interface_url 的数据：~0 rows (大约)
DELETE FROM `bss_interface_url`;
/*!40000 ALTER TABLE `bss_interface_url` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_interface_url` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_jms_task_record 结构
CREATE TABLE IF NOT EXISTS `bss_jms_task_record` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `outter_id` bigint(20) DEFAULT NULL,
  `type` varchar(32) DEFAULT NULL,
  `create_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `action` varchar(32) DEFAULT NULL,
  `is_handle` bigint(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_jms_task_record 的数据：~0 rows (大约)
DELETE FROM `bss_jms_task_record`;
/*!40000 ALTER TABLE `bss_jms_task_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_jms_task_record` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_media_url 结构
CREATE TABLE IF NOT EXISTS `bss_media_url` (
  `id` bigint(19) NOT NULL,
  `program_id` bigint(19) DEFAULT NULL,
  `media_id` bigint(19) DEFAULT NULL,
  `play_url` varchar(1800) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `source` varchar(32) DEFAULT NULL,
  `is_sync` varchar(10) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `push_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_media_url 的数据：~0 rows (大约)
DELETE FROM `bss_media_url`;
/*!40000 ALTER TABLE `bss_media_url` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_media_url` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_operate_log 结构
CREATE TABLE IF NOT EXISTS `bss_operate_log` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `module_name` varchar(30) DEFAULT NULL,
  `operation_type` varchar(32) DEFAULT NULL,
  `primary_key_value` text,
  `description` text,
  `operator` varchar(50) DEFAULT NULL,
  `operation_ip` varchar(32) DEFAULT NULL,
  `operation_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_operate_log 的数据：~0 rows (大约)
DELETE FROM `bss_operate_log`;
/*!40000 ALTER TABLE `bss_operate_log` DISABLE KEYS */;
INSERT INTO `bss_operate_log` (`id`, `module_name`, `operation_type`, `primary_key_value`, `description`, `operator`, `operation_ip`, `operation_time`) VALUES
	(1, '预览模板信息维护', '新增', '1', '新增预览模板成功!', 'admin', '192.168.2.73', '2014-10-17 15:30:53'),
	(2, '预览模板信息维护', '模板配置', '', '新增模块成功', 'admin', '192.168.2.73', '2014-10-17 15:33:24'),
	(3, '管理系统登陆', '系统登陆', '1', '用户 admin 在2014-10-17 15:40:38登陆系统成功', 'admin', '192.168.2.73', '2014-10-17 15:40:40'),
	(4, '面板信息维护', '新增', '1', '新增面板成功!', 'admin', '192.168.2.73', '2014-10-17 15:42:39'),
	(5, '管理系统登陆', '系统登陆', '1', '用户 admin 在2014-10-17 16:43:53登陆系统成功', 'admin', '58.214.17.75', '2014-10-17 16:43:53'),
	(6, '管理系统登陆', '系统登陆', '1', '用户 admin 在2014-10-17 16:46:49登陆系统成功', 'admin', '58.214.17.75', '2014-10-17 16:46:49'),
	(7, '系统参数配置', '修改', '28', '系统参数更新成功.configKey：picFiles,configValue:/usr/local/tomcat-jsydjt/webapps/', 'admin', '58.214.17.75', '2014-10-17 16:48:08'),
	(8, '系统参数配置', '修改', '29', '系统参数更新成功.configKey：videoFiles,configValue:/usr/local/tomcat-jsydjt/webapps/', 'admin', '58.214.17.75', '2014-10-17 16:48:15'),
	(9, '系统参数配置', '修改', '26', '系统参数更新成功.configKey：videoUrl,configValue:http://58.214.17.75:8081/video/', 'admin', '58.214.17.75', '2014-10-17 16:48:36'),
	(10, '系统参数配置', '修改', '27', '系统参数更新成功.configKey：picUrl,configValue:http://58.214.17.75:8081/pic/', 'admin', '58.214.17.75', '2014-10-17 16:48:48'),
	(11, '管理系统登陆', '系统登陆', '1', '用户 admin 在2014-10-17 16:50:06登陆系统成功', 'admin', '58.214.17.75', '2014-10-17 16:50:06'),
	(12, '面板信息维护', '修改', '1', '更新面板成功!', 'admin', '58.214.17.75', '2014-10-17 16:50:33'),
	(13, '系统参数配置', '修改', '28', '系统参数更新成功.configKey：picFiles,configValue:/usr/local/tomcat-jsydjt/webapps/pic/', 'admin', '58.214.17.75', '2014-10-17 16:51:51'),
	(14, '系统参数配置', '修改', '29', '系统参数更新成功.configKey：videoFiles,configValue:/usr/local/tomcat-jsydjt/webapps/video/', 'admin', '58.214.17.75', '2014-10-17 16:52:04'),
	(15, '管理系统登陆', '系统登陆', '1', '用户 admin 在2014-10-17 16:52:13登陆系统成功', 'admin', '58.214.17.75', '2014-10-17 16:52:13'),
	(16, '面板信息维护', '修改', '1', '更新面板成功!', 'admin', '58.214.17.75', '2014-10-17 16:52:36'),
	(17, '面板信息维护', '修改', '1', '更新面板成功!', 'admin', '58.214.17.75', '2014-10-17 16:53:41'),
	(18, '面板项项信息维护', '新增', 'null', '新增面板项失败,org.springframework.jdbc.BadSqlGrammarException: \n### Error updating database.  Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'item_content_type,app_enter_url\n       )\n     values (\n              null,\n     \' at line 6\n### The error may involve com.ysten.local.bss.panel.repository.mapper.PanelItemMapper.save-Inline\n### The error occurred while setting parameters\n### Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'item_content_type,app_enter_url\n       )\n     values (\n              null,\n     \' at line 6\n; bad SQL grammar []; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'item_content_type,app_enter_url\n       )\n     values (\n              null,\n     \' at line 6\n	at org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator.doTranslate(SQLErrorCodeSQLExceptionTranslator.java:237)\n	at org.springframework.jdbc.support.AbstractFallbackSQLExceptionTranslator.translate(AbstractFallbackSQLExceptionTranslator.java:72)\n	at org.mybatis.spring.MyBatisExceptionTranslator.translateExceptionIfPossible(MyBatisExceptionTranslator.java:71)\n	at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:365)\n	at com.sun.proxy.$Proxy8.insert(Unknown Source)\n	at org.mybatis.spring.SqlSessionTemplate.insert(SqlSessionTemplate.java:237)\n	at org.apache.ibatis.binding.MapperMethod.execute(MapperMethod.java:79)\n	at org.apache.ibatis.binding.MapperProxy.invoke(MapperProxy.java:40)\n	at com.sun.proxy.$Proxy87.save(Unknown Source)\n	at com.ysten.local.bss.panel.repository.impl.PanelItemRepositoryImpl.savePanelItem(PanelItemRepositoryImpl.java:37)\n	at com.ysten.local.bss.panel.repository.impl.PanelItemRepositoryImpl$$FastClassByCGLIB$$ac02f3a6.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:698)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:150)\n	at com.ysten.cache.spring.interceptor.CacheInterceptor.invoke(CacheInterceptor.java:86)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:172)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:631)\n	at com.ysten.local.bss.panel.repository.impl.PanelItemRepositoryImpl$$EnhancerByCGLIB$$92e7f9d8.savePanelItem(<generated>)\n	at com.ysten.local.bss.panel.service.impl.PanelItemServiceImpl.savePanelItemData(PanelItemServiceImpl.java:96)\n	at com.ysten.local.bss.panel.service.impl.PanelItemServiceImpl$$FastClassByCGLIB$$c0139502.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:698)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:150)\n	at org.springframework.transaction.interceptor.TransactionInterceptor$1.proceedWithInvocation(TransactionInterceptor.java:96)\n	at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:260)\n	at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:94)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:172)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:631)\n	at com.ysten.local.bss.panel.service.impl.PanelItemServiceImpl$$EnhancerByCGLIB$$fb40a8d7.savePanelItemData(<generated>)\n	at com.ysten.local.bss.panel.web.controller.PanelItemController.addPanelItem(PanelItemController.java:107)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:39)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)\n	at java.lang.reflect.Method.invoke(Method.java:597)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invoke(InvocableHandlerMethod.java:219)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:132)\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:104)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandleMethod(RequestMappingHandlerAdapter.java:745)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:686)\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:80)\n	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:925)\n	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:856)\n	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:936)\n	at org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:838)\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:646)\n	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:812)\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:727)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:303)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:208)\n	at com.ysten.utils.filter.AccessFilter.doFilter(AccessFilter.java:55)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:241)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:208)\n	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:88)\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:241)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:208)\n	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:220)\n	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:122)\n	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:501)\n	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:171)\n	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:102)\n	at org.apache.catalina.valves.AccessLogValve.invoke(AccessLogValve.java:950)\n	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:116)\n	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:408)\n	at org.apache.coyote.http11.AbstractHttp11Processor.process(AbstractHttp11Processor.java:1040)\n	at org.apache.coyote.AbstractProtocol$AbstractConnectionHandler.process(AbstractProtocol.java:607)\n	at org.apache.tomcat.util.net.JIoEndpoint$SocketProcessor.run(JIoEndpoint.java:314)\n	at java.util.concurrent.ThreadPoolExecutor$Worker.runTask(ThreadPoolExecutor.java:895)\n	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:918)\n	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)\n	at java.lang.Thread.run(Thread.java:662)\nCaused by: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'item_content_type,app_enter_url\n       )\n     values (\n              null,\n     \' at line 6\n	at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)\n	at sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:39)\n	at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:27)\n	at java.lang.reflect.Constructor.newInstance(Constructor.java:513)\n	at com.mysql.jdbc.Util.handleNewInstance(Util.java:407)\n	at com.mysql.jdbc.Util.getInstance(Util.java:382)\n	at com.mysql.jdbc.SQLError.createSQLException(SQLError.java:1052)\n	at com.mysql.jdbc.MysqlIO.checkErrorPacket(MysqlIO.java:3603)\n	at com.mysql.jdbc.MysqlIO.checkErrorPacket(MysqlIO.java:3535)\n	at com.mysql.jdbc.MysqlIO.sendCommand(MysqlIO.java:1989)\n	at com.mysql.jdbc.MysqlIO.sqlQueryDirect(MysqlIO.java:2150)\n	at com.mysql.jdbc.ConnectionImpl.execSQL(ConnectionImpl.java:2626)\n	at com.mysql.jdbc.PreparedStatement.executeInternal(PreparedStatement.java:2119)\n	at com.mysql.jdbc.PreparedStatement.execute(PreparedStatement.java:1362)\n	at org.apache.tomcat.dbcp.dbcp.DelegatingPreparedStatement.execute(DelegatingPreparedStatement.java:172)\n	at org.apache.tomcat.dbcp.dbcp.DelegatingPreparedStatement.execute(DelegatingPreparedStatement.java:172)\n	at org.apache.ibatis.executor.statement.PreparedStatementHandler.update(PreparedStatementHandler.java:42)\n	at org.apache.ibatis.executor.statement.RoutingStatementHandler.update(RoutingStatementHandler.java:66)\n	at org.apache.ibatis.executor.SimpleExecutor.doUpdate(SimpleExecutor.java:45)\n	at org.apache.ibatis.executor.BaseExecutor.update(BaseExecutor.java:108)\n	at org.apache.ibatis.executor.CachingExecutor.update(CachingExecutor.java:67)\n	at org.apache.ibatis.session.defaults.DefaultSqlSession.update(DefaultSqlSession.java:145)\n	at org.apache.ibatis.session.defaults.DefaultSqlSession.insert(DefaultSqlSession.java:134)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:39)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)\n	at java.lang.reflect.Method.invoke(Method.java:597)\n	at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:355)\n	... 67 more', 'admin', '58.214.17.75', '2014-10-17 16:55:07'),
	(19, '面板项项信息维护', '新增', 'null', '新增面板项失败,org.springframework.jdbc.BadSqlGrammarException: \n### Error updating database.  Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'item_content_type,app_enter_url\n       )\n     values (\n              null,\n     \' at line 6\n### The error may involve com.ysten.local.bss.panel.repository.mapper.PanelItemMapper.save-Inline\n### The error occurred while setting parameters\n### Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'item_content_type,app_enter_url\n       )\n     values (\n              null,\n     \' at line 6\n; bad SQL grammar []; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'item_content_type,app_enter_url\n       )\n     values (\n              null,\n     \' at line 6\n	at org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator.doTranslate(SQLErrorCodeSQLExceptionTranslator.java:237)\n	at org.springframework.jdbc.support.AbstractFallbackSQLExceptionTranslator.translate(AbstractFallbackSQLExceptionTranslator.java:72)\n	at org.mybatis.spring.MyBatisExceptionTranslator.translateExceptionIfPossible(MyBatisExceptionTranslator.java:71)\n	at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:365)\n	at com.sun.proxy.$Proxy8.insert(Unknown Source)\n	at org.mybatis.spring.SqlSessionTemplate.insert(SqlSessionTemplate.java:237)\n	at org.apache.ibatis.binding.MapperMethod.execute(MapperMethod.java:79)\n	at org.apache.ibatis.binding.MapperProxy.invoke(MapperProxy.java:40)\n	at com.sun.proxy.$Proxy87.save(Unknown Source)\n	at com.ysten.local.bss.panel.repository.impl.PanelItemRepositoryImpl.savePanelItem(PanelItemRepositoryImpl.java:37)\n	at com.ysten.local.bss.panel.repository.impl.PanelItemRepositoryImpl$$FastClassByCGLIB$$ac02f3a6.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:698)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:150)\n	at com.ysten.cache.spring.interceptor.CacheInterceptor.invoke(CacheInterceptor.java:86)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:172)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:631)\n	at com.ysten.local.bss.panel.repository.impl.PanelItemRepositoryImpl$$EnhancerByCGLIB$$92e7f9d8.savePanelItem(<generated>)\n	at com.ysten.local.bss.panel.service.impl.PanelItemServiceImpl.savePanelItemData(PanelItemServiceImpl.java:96)\n	at com.ysten.local.bss.panel.service.impl.PanelItemServiceImpl$$FastClassByCGLIB$$c0139502.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:698)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:150)\n	at org.springframework.transaction.interceptor.TransactionInterceptor$1.proceedWithInvocation(TransactionInterceptor.java:96)\n	at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:260)\n	at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:94)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:172)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:631)\n	at com.ysten.local.bss.panel.service.impl.PanelItemServiceImpl$$EnhancerByCGLIB$$fb40a8d7.savePanelItemData(<generated>)\n	at com.ysten.local.bss.panel.web.controller.PanelItemController.addPanelItem(PanelItemController.java:107)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:39)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)\n	at java.lang.reflect.Method.invoke(Method.java:597)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invoke(InvocableHandlerMethod.java:219)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:132)\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:104)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandleMethod(RequestMappingHandlerAdapter.java:745)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:686)\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:80)\n	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:925)\n	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:856)\n	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:936)\n	at org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:838)\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:646)\n	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:812)\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:727)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:303)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:208)\n	at com.ysten.utils.filter.AccessFilter.doFilter(AccessFilter.java:55)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:241)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:208)\n	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:88)\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:241)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:208)\n	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:220)\n	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:122)\n	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:501)\n	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:171)\n	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:102)\n	at org.apache.catalina.valves.AccessLogValve.invoke(AccessLogValve.java:950)\n	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:116)\n	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:408)\n	at org.apache.coyote.http11.AbstractHttp11Processor.process(AbstractHttp11Processor.java:1040)\n	at org.apache.coyote.AbstractProtocol$AbstractConnectionHandler.process(AbstractProtocol.java:607)\n	at org.apache.tomcat.util.net.JIoEndpoint$SocketProcessor.run(JIoEndpoint.java:316)\n	at java.util.concurrent.ThreadPoolExecutor$Worker.runTask(ThreadPoolExecutor.java:895)\n	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:918)\n	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)\n	at java.lang.Thread.run(Thread.java:662)\nCaused by: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'item_content_type,app_enter_url\n       )\n     values (\n              null,\n     \' at line 6\n	at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)\n	at sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:39)\n	at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:27)\n	at java.lang.reflect.Constructor.newInstance(Constructor.java:513)\n	at com.mysql.jdbc.Util.handleNewInstance(Util.java:407)\n	at com.mysql.jdbc.Util.getInstance(Util.java:382)\n	at com.mysql.jdbc.SQLError.createSQLException(SQLError.java:1052)\n	at com.mysql.jdbc.MysqlIO.checkErrorPacket(MysqlIO.java:3603)\n	at com.mysql.jdbc.MysqlIO.checkErrorPacket(MysqlIO.java:3535)\n	at com.mysql.jdbc.MysqlIO.sendCommand(MysqlIO.java:1989)\n	at com.mysql.jdbc.MysqlIO.sqlQueryDirect(MysqlIO.java:2150)\n	at com.mysql.jdbc.ConnectionImpl.execSQL(ConnectionImpl.java:2626)\n	at com.mysql.jdbc.PreparedStatement.executeInternal(PreparedStatement.java:2119)\n	at com.mysql.jdbc.PreparedStatement.execute(PreparedStatement.java:1362)\n	at org.apache.tomcat.dbcp.dbcp.DelegatingPreparedStatement.execute(DelegatingPreparedStatement.java:172)\n	at org.apache.tomcat.dbcp.dbcp.DelegatingPreparedStatement.execute(DelegatingPreparedStatement.java:172)\n	at org.apache.ibatis.executor.statement.PreparedStatementHandler.update(PreparedStatementHandler.java:42)\n	at org.apache.ibatis.executor.statement.RoutingStatementHandler.update(RoutingStatementHandler.java:66)\n	at org.apache.ibatis.executor.SimpleExecutor.doUpdate(SimpleExecutor.java:45)\n	at org.apache.ibatis.executor.BaseExecutor.update(BaseExecutor.java:108)\n	at org.apache.ibatis.executor.CachingExecutor.update(CachingExecutor.java:67)\n	at org.apache.ibatis.session.defaults.DefaultSqlSession.update(DefaultSqlSession.java:145)\n	at org.apache.ibatis.session.defaults.DefaultSqlSession.insert(DefaultSqlSession.java:134)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:39)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)\n	at java.lang.reflect.Method.invoke(Method.java:597)\n	at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:355)\n	... 67 more', 'admin', '58.214.17.75', '2014-10-17 16:56:21'),
	(20, '面板项项信息维护', '新增', 'null', '新增面板项失败,org.springframework.jdbc.BadSqlGrammarException: \n### Error updating database.  Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'item_content_type,app_enter_url\n       )\n     values (\n              null,\n     \' at line 6\n### The error may involve com.ysten.local.bss.panel.repository.mapper.PanelItemMapper.save-Inline\n### The error occurred while setting parameters\n### Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'item_content_type,app_enter_url\n       )\n     values (\n              null,\n     \' at line 6\n; bad SQL grammar []; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'item_content_type,app_enter_url\n       )\n     values (\n              null,\n     \' at line 6\n	at org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator.doTranslate(SQLErrorCodeSQLExceptionTranslator.java:237)\n	at org.springframework.jdbc.support.AbstractFallbackSQLExceptionTranslator.translate(AbstractFallbackSQLExceptionTranslator.java:72)\n	at org.mybatis.spring.MyBatisExceptionTranslator.translateExceptionIfPossible(MyBatisExceptionTranslator.java:71)\n	at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:365)\n	at com.sun.proxy.$Proxy8.insert(Unknown Source)\n	at org.mybatis.spring.SqlSessionTemplate.insert(SqlSessionTemplate.java:237)\n	at org.apache.ibatis.binding.MapperMethod.execute(MapperMethod.java:79)\n	at org.apache.ibatis.binding.MapperProxy.invoke(MapperProxy.java:40)\n	at com.sun.proxy.$Proxy87.save(Unknown Source)\n	at com.ysten.local.bss.panel.repository.impl.PanelItemRepositoryImpl.savePanelItem(PanelItemRepositoryImpl.java:37)\n	at com.ysten.local.bss.panel.repository.impl.PanelItemRepositoryImpl$$FastClassByCGLIB$$ac02f3a6.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:698)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:150)\n	at com.ysten.cache.spring.interceptor.CacheInterceptor.invoke(CacheInterceptor.java:86)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:172)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:631)\n	at com.ysten.local.bss.panel.repository.impl.PanelItemRepositoryImpl$$EnhancerByCGLIB$$92e7f9d8.savePanelItem(<generated>)\n	at com.ysten.local.bss.panel.service.impl.PanelItemServiceImpl.savePanelItemData(PanelItemServiceImpl.java:96)\n	at com.ysten.local.bss.panel.service.impl.PanelItemServiceImpl$$FastClassByCGLIB$$c0139502.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:698)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:150)\n	at org.springframework.transaction.interceptor.TransactionInterceptor$1.proceedWithInvocation(TransactionInterceptor.java:96)\n	at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:260)\n	at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:94)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:172)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:631)\n	at com.ysten.local.bss.panel.service.impl.PanelItemServiceImpl$$EnhancerByCGLIB$$fb40a8d7.savePanelItemData(<generated>)\n	at com.ysten.local.bss.panel.web.controller.PanelItemController.addPanelItem(PanelItemController.java:107)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:39)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)\n	at java.lang.reflect.Method.invoke(Method.java:597)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invoke(InvocableHandlerMethod.java:219)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:132)\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:104)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandleMethod(RequestMappingHandlerAdapter.java:745)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:686)\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:80)\n	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:925)\n	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:856)\n	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:936)\n	at org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:838)\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:646)\n	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:812)\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:727)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:303)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:208)\n	at com.ysten.utils.filter.AccessFilter.doFilter(AccessFilter.java:55)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:241)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:208)\n	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:88)\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:241)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:208)\n	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:220)\n	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:122)\n	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:501)\n	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:171)\n	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:102)\n	at org.apache.catalina.valves.AccessLogValve.invoke(AccessLogValve.java:950)\n	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:116)\n	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:408)\n	at org.apache.coyote.http11.AbstractHttp11Processor.process(AbstractHttp11Processor.java:1040)\n	at org.apache.coyote.AbstractProtocol$AbstractConnectionHandler.process(AbstractProtocol.java:607)\n	at org.apache.tomcat.util.net.JIoEndpoint$SocketProcessor.run(JIoEndpoint.java:316)\n	at java.util.concurrent.ThreadPoolExecutor$Worker.runTask(ThreadPoolExecutor.java:895)\n	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:918)\n	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)\n	at java.lang.Thread.run(Thread.java:662)\nCaused by: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'item_content_type,app_enter_url\n       )\n     values (\n              null,\n     \' at line 6\n	at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)\n	at sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:39)\n	at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:27)\n	at java.lang.reflect.Constructor.newInstance(Constructor.java:513)\n	at com.mysql.jdbc.Util.handleNewInstance(Util.java:407)\n	at com.mysql.jdbc.Util.getInstance(Util.java:382)\n	at com.mysql.jdbc.SQLError.createSQLException(SQLError.java:1052)\n	at com.mysql.jdbc.MysqlIO.checkErrorPacket(MysqlIO.java:3603)\n	at com.mysql.jdbc.MysqlIO.checkErrorPacket(MysqlIO.java:3535)\n	at com.mysql.jdbc.MysqlIO.sendCommand(MysqlIO.java:1989)\n	at com.mysql.jdbc.MysqlIO.sqlQueryDirect(MysqlIO.java:2150)\n	at com.mysql.jdbc.ConnectionImpl.execSQL(ConnectionImpl.java:2626)\n	at com.mysql.jdbc.PreparedStatement.executeInternal(PreparedStatement.java:2119)\n	at com.mysql.jdbc.PreparedStatement.execute(PreparedStatement.java:1362)\n	at org.apache.tomcat.dbcp.dbcp.DelegatingPreparedStatement.execute(DelegatingPreparedStatement.java:172)\n	at org.apache.tomcat.dbcp.dbcp.DelegatingPreparedStatement.execute(DelegatingPreparedStatement.java:172)\n	at org.apache.ibatis.executor.statement.PreparedStatementHandler.update(PreparedStatementHandler.java:42)\n	at org.apache.ibatis.executor.statement.RoutingStatementHandler.update(RoutingStatementHandler.java:66)\n	at org.apache.ibatis.executor.SimpleExecutor.doUpdate(SimpleExecutor.java:45)\n	at org.apache.ibatis.executor.BaseExecutor.update(BaseExecutor.java:108)\n	at org.apache.ibatis.executor.CachingExecutor.update(CachingExecutor.java:67)\n	at org.apache.ibatis.session.defaults.DefaultSqlSession.update(DefaultSqlSession.java:145)\n	at org.apache.ibatis.session.defaults.DefaultSqlSession.insert(DefaultSqlSession.java:134)\n	at sun.reflect.GeneratedMethodAccessor161.invoke(Unknown Source)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)\n	at java.lang.reflect.Method.invoke(Method.java:597)\n	at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:355)\n	... 67 more', 'admin', '58.214.17.75', '2014-10-17 16:57:11'),
	(21, '管理系统登陆', '系统登陆', '1', '用户 admin 在2014-10-17 17:04:20登陆系统成功', 'admin', '58.214.17.75', '2014-10-17 17:04:20'),
	(22, '管理系统登陆', '系统登陆', '1', '用户 admin 在2014-10-17 17:04:33登陆系统成功', 'admin', '58.214.17.75', '2014-10-17 17:04:33'),
	(23, '角色与权限关联', '新增', '1', '角色权限分配成功.角色ID：1,权限ID：1,5,31,300,301,48,77,78,147,186,249,309,35,51,52,36,49,50,157,175,176,177,245,247,248,308,158,311,317,161,162,163,250,251,328,329,178,260,261,312,314,179,180,181,326,327,185,262,263,315,316,189,190,191,335,336,165,166,167,223,224,225,199,272,273,274,200,266,267,276,277,278,320,321,148,149,169,170,171,150,172,173,174,220,264,265,219,221,222,330,331,339,340,6,19,259,102,103,104,105,106,107,108,109,146,217,252,21,113,22,208,256,257,258,322,253,254,255,203,235,238,237,236,213,214,215,216,240,226,239,229,228,227,280,304,307,318,319,332,333,231,234,233,232,204,230,302,303,205,206,241,242,337,9,28,61,202,8,23,141,142,24,62,63,138,25,64,65,338', 'admin', '58.214.17.75', '2014-10-17 17:05:32'),
	(24, '管理系统登陆', '系统登陆', '1', '用户 admin 在2014-10-17 17:05:37登陆系统成功', 'admin', '58.214.17.75', '2014-10-17 17:05:38'),
	(25, '角色与权限关联', '新增', '1', '角色权限分配成功.角色ID：1,权限ID：1,5,31,300,301,48,77,78,147,186,249,309,35,51,52,36,49,50,157,175,176,177,245,247,248,308,158,311,317,161,162,163,250,251,328,329,178,260,261,312,314,179,180,181,326,327,185,262,263,315,316,189,190,191,335,336,165,166,167,223,224,225,199,272,273,274,200,266,267,276,277,278,320,321,148,149,169,170,171,150,172,173,174,220,264,265,219,221,222,330,331,339,340,6,19,259,102,103,104,105,106,107,108,109,146,217,252,21,113,22,208,256,257,258,322,253,254,255,203,235,238,237,236,213,214,215,216,240,226,239,229,228,227,280,304,307,318,319,332,333,231,234,233,232,204,230,302,303,205,206,241,242,337,9,28,61,202,8,23,141,142,24,62,63,138,25,64,65,338', 'admin', '58.214.17.75', '2014-10-17 17:05:40'),
	(26, '角色与权限关联', '新增', '1', '角色权限分配成功.角色ID：1,权限ID：1,5,31,300,301,48,77,78,147,186,249,309,35,51,52,36,49,50,157,175,176,177,245,247,248,308,158,311,317,161,162,163,250,251,328,329,178,260,261,312,314,179,180,181,326,327,185,262,263,315,316,189,190,191,335,336,165,166,167,223,224,225,199,272,273,274,200,266,267,276,277,278,320,321,148,149,169,170,171,150,172,173,174,220,264,265,219,221,222,330,331,339,340,6,19,259,102,103,104,105,106,107,108,109,146,217,252,21,113,22,208,256,257,258,322,253,254,255,203,235,238,237,236,213,214,215,216,240,226,239,229,228,227,280,304,307,318,319,332,333,231,234,233,232,204,230,302,303,205,206,241,242,337,9,28,61,202,8,23,141,142,24,62,63,138,25,64,65,338', 'admin', '58.214.17.75', '2014-10-17 17:05:42'),
	(27, '角色与权限关联', '新增', '1', '角色权限分配成功.角色ID：1,权限ID：1,5,31,300,301,48,77,78,147,186,249,309,35,51,52,36,49,50,157,175,176,177,245,247,248,308,158,311,317,161,162,163,250,251,328,329,178,260,261,312,314,179,180,181,326,327,185,262,263,315,316,189,190,191,335,336,165,166,167,223,224,225,199,272,273,274,200,266,267,276,277,278,320,321,148,149,169,170,171,150,172,173,174,220,264,265,219,221,222,330,331,339,340,6,19,259,102,103,104,105,106,107,108,109,146,217,252,21,113,22,208,256,257,258,322,253,254,255,203,235,238,237,236,213,214,215,216,240,226,239,229,228,227,280,304,307,318,319,332,333,231,234,233,232,204,230,302,303,205,206,241,242,337,9,28,61,202,8,23,141,142,24,62,63,138,25,64,65,338', 'admin', '58.214.17.75', '2014-10-17 17:05:42'),
	(28, '角色与权限关联', '新增', '1', '角色权限分配成功.角色ID：1,权限ID：1,5,31,300,301,48,77,78,147,186,249,309,35,51,52,36,49,50,157,175,176,177,245,247,248,308,158,311,317,161,162,163,250,251,328,329,178,260,261,312,314,179,180,181,326,327,185,262,263,315,316,189,190,191,335,336,165,166,167,223,224,225,199,272,273,274,200,266,267,276,277,278,320,321,148,149,169,170,171,150,172,173,174,220,264,265,219,221,222,330,331,339,340,6,19,259,102,103,104,105,106,107,108,109,146,217,252,21,113,22,208,256,257,258,322,253,254,255,203,235,238,237,236,213,214,215,216,240,226,239,229,228,227,280,304,307,318,319,332,333,231,234,233,232,204,230,302,303,205,206,241,242,337,9,28,61,202,8,23,141,142,24,62,63,138,25,64,65,338', 'admin', '58.214.17.75', '2014-10-17 17:05:43'),
	(29, '角色与权限关联', '新增', '1', '角色权限分配成功.角色ID：1,权限ID：1,5,31,300,301,48,77,78,147,186,249,309,35,51,52,36,49,50,157,175,176,177,245,247,248,308,158,311,317,161,162,163,250,251,328,329,178,260,261,312,314,179,180,181,326,327,185,262,263,315,316,189,190,191,335,336,165,166,167,223,224,225,199,272,273,274,200,266,267,276,277,278,320,321,148,149,169,170,171,150,172,173,174,220,264,265,219,221,222,330,331,339,340,6,19,259,102,103,104,105,106,107,108,109,146,217,252,21,113,22,208,256,257,258,322,253,254,255,203,235,238,237,236,213,214,215,216,240,226,239,229,228,227,280,304,307,318,319,332,333,231,234,233,232,204,230,302,303,205,206,241,242,337,9,28,61,202,8,23,141,142,24,62,63,138,25,64,65,338', 'admin', '58.214.17.75', '2014-10-17 17:05:43'),
	(30, '角色与权限关联', '新增', '1', '角色权限分配成功.角色ID：1,权限ID：1,5,31,300,301,48,77,78,147,186,249,309,35,51,52,36,49,50,157,175,176,177,245,247,248,308,158,311,317,161,162,163,250,251,328,329,178,260,261,312,314,179,180,181,326,327,185,262,263,315,316,189,190,191,335,336,165,166,167,223,224,225,199,272,273,274,200,266,267,276,277,278,320,321,148,149,169,170,171,150,172,173,174,220,264,265,219,221,222,330,331,339,340,6,19,259,102,103,104,105,106,107,108,109,146,217,252,21,113,22,208,256,257,258,322,253,254,255,203,235,238,237,236,213,214,215,216,240,226,239,229,228,227,280,304,307,318,319,332,333,231,234,233,232,204,230,302,303,205,206,241,242,337,9,28,61,202,8,23,141,142,24,62,63,138,25,64,65,338', 'admin', '58.214.17.75', '2014-10-17 17:05:44'),
	(31, '面板项项信息维护', '新增', 'null', '新增面板项失败,org.springframework.jdbc.BadSqlGrammarException: \n### Error updating database.  Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'item_content_type,app_enter_url\n       )\n     values (\n              null,\n     \' at line 6\n### The error may involve com.ysten.local.bss.panel.repository.mapper.PanelItemMapper.save-Inline\n### The error occurred while setting parameters\n### Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'item_content_type,app_enter_url\n       )\n     values (\n              null,\n     \' at line 6\n; bad SQL grammar []; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'item_content_type,app_enter_url\n       )\n     values (\n              null,\n     \' at line 6\n	at org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator.doTranslate(SQLErrorCodeSQLExceptionTranslator.java:237)\n	at org.springframework.jdbc.support.AbstractFallbackSQLExceptionTranslator.translate(AbstractFallbackSQLExceptionTranslator.java:72)\n	at org.mybatis.spring.MyBatisExceptionTranslator.translateExceptionIfPossible(MyBatisExceptionTranslator.java:71)\n	at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:365)\n	at com.sun.proxy.$Proxy8.insert(Unknown Source)\n	at org.mybatis.spring.SqlSessionTemplate.insert(SqlSessionTemplate.java:237)\n	at org.apache.ibatis.binding.MapperMethod.execute(MapperMethod.java:79)\n	at org.apache.ibatis.binding.MapperProxy.invoke(MapperProxy.java:40)\n	at com.sun.proxy.$Proxy87.save(Unknown Source)\n	at com.ysten.local.bss.panel.repository.impl.PanelItemRepositoryImpl.savePanelItem(PanelItemRepositoryImpl.java:37)\n	at com.ysten.local.bss.panel.repository.impl.PanelItemRepositoryImpl$$FastClassByCGLIB$$ac02f3a6.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:698)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:150)\n	at com.ysten.cache.spring.interceptor.CacheInterceptor.invoke(CacheInterceptor.java:86)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:172)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:631)\n	at com.ysten.local.bss.panel.repository.impl.PanelItemRepositoryImpl$$EnhancerByCGLIB$$92e7f9d8.savePanelItem(<generated>)\n	at com.ysten.local.bss.panel.service.impl.PanelItemServiceImpl.savePanelItemData(PanelItemServiceImpl.java:96)\n	at com.ysten.local.bss.panel.service.impl.PanelItemServiceImpl$$FastClassByCGLIB$$c0139502.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:698)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:150)\n	at org.springframework.transaction.interceptor.TransactionInterceptor$1.proceedWithInvocation(TransactionInterceptor.java:96)\n	at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:260)\n	at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:94)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:172)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:631)\n	at com.ysten.local.bss.panel.service.impl.PanelItemServiceImpl$$EnhancerByCGLIB$$fb40a8d7.savePanelItemData(<generated>)\n	at com.ysten.local.bss.panel.web.controller.PanelItemController.addPanelItem(PanelItemController.java:107)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:39)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)\n	at java.lang.reflect.Method.invoke(Method.java:597)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invoke(InvocableHandlerMethod.java:219)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:132)\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:104)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandleMethod(RequestMappingHandlerAdapter.java:745)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:686)\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:80)\n	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:925)\n	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:856)\n	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:936)\n	at org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:838)\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:646)\n	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:812)\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:727)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:303)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:208)\n	at com.ysten.utils.filter.AccessFilter.doFilter(AccessFilter.java:55)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:241)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:208)\n	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:88)\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:241)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:208)\n	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:220)\n	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:122)\n	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:501)\n	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:171)\n	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:102)\n	at org.apache.catalina.valves.AccessLogValve.invoke(AccessLogValve.java:950)\n	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:116)\n	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:408)\n	at org.apache.coyote.http11.AbstractHttp11Processor.process(AbstractHttp11Processor.java:1040)\n	at org.apache.coyote.AbstractProtocol$AbstractConnectionHandler.process(AbstractProtocol.java:607)\n	at org.apache.tomcat.util.net.JIoEndpoint$SocketProcessor.run(JIoEndpoint.java:316)\n	at java.util.concurrent.ThreadPoolExecutor$Worker.runTask(ThreadPoolExecutor.java:895)\n	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:918)\n	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)\n	at java.lang.Thread.run(Thread.java:662)\nCaused by: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'item_content_type,app_enter_url\n       )\n     values (\n              null,\n     \' at line 6\n	at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)\n	at sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:39)\n	at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:27)\n	at java.lang.reflect.Constructor.newInstance(Constructor.java:513)\n	at com.mysql.jdbc.Util.handleNewInstance(Util.java:407)\n	at com.mysql.jdbc.Util.getInstance(Util.java:382)\n	at com.mysql.jdbc.SQLError.createSQLException(SQLError.java:1052)\n	at com.mysql.jdbc.MysqlIO.checkErrorPacket(MysqlIO.java:3603)\n	at com.mysql.jdbc.MysqlIO.checkErrorPacket(MysqlIO.java:3535)\n	at com.mysql.jdbc.MysqlIO.sendCommand(MysqlIO.java:1989)\n	at com.mysql.jdbc.MysqlIO.sqlQueryDirect(MysqlIO.java:2150)\n	at com.mysql.jdbc.ConnectionImpl.execSQL(ConnectionImpl.java:2626)\n	at com.mysql.jdbc.PreparedStatement.executeInternal(PreparedStatement.java:2119)\n	at com.mysql.jdbc.PreparedStatement.execute(PreparedStatement.java:1362)\n	at org.apache.tomcat.dbcp.dbcp.DelegatingPreparedStatement.execute(DelegatingPreparedStatement.java:172)\n	at org.apache.tomcat.dbcp.dbcp.DelegatingPreparedStatement.execute(DelegatingPreparedStatement.java:172)\n	at org.apache.ibatis.executor.statement.PreparedStatementHandler.update(PreparedStatementHandler.java:42)\n	at org.apache.ibatis.executor.statement.RoutingStatementHandler.update(RoutingStatementHandler.java:66)\n	at org.apache.ibatis.executor.SimpleExecutor.doUpdate(SimpleExecutor.java:45)\n	at org.apache.ibatis.executor.BaseExecutor.update(BaseExecutor.java:108)\n	at org.apache.ibatis.executor.CachingExecutor.update(CachingExecutor.java:67)\n	at org.apache.ibatis.session.defaults.DefaultSqlSession.update(DefaultSqlSession.java:145)\n	at org.apache.ibatis.session.defaults.DefaultSqlSession.insert(DefaultSqlSession.java:134)\n	at sun.reflect.GeneratedMethodAccessor161.invoke(Unknown Source)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)\n	at java.lang.reflect.Method.invoke(Method.java:597)\n	at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:355)\n	... 67 more', 'admin', '58.214.17.75', '2014-10-17 17:06:18'),
	(32, '面板项项信息维护', '新增', 'null', '新增面板项失败,org.springframework.jdbc.BadSqlGrammarException: \n### Error updating database.  Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'item_content_type,app_enter_url\n       )\n     values (\n              null,\n     \' at line 6\n### The error may involve com.ysten.local.bss.panel.repository.mapper.PanelItemMapper.save-Inline\n### The error occurred while setting parameters\n### Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'item_content_type,app_enter_url\n       )\n     values (\n              null,\n     \' at line 6\n; bad SQL grammar []; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'item_content_type,app_enter_url\n       )\n     values (\n              null,\n     \' at line 6\n	at org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator.doTranslate(SQLErrorCodeSQLExceptionTranslator.java:237)\n	at org.springframework.jdbc.support.AbstractFallbackSQLExceptionTranslator.translate(AbstractFallbackSQLExceptionTranslator.java:72)\n	at org.mybatis.spring.MyBatisExceptionTranslator.translateExceptionIfPossible(MyBatisExceptionTranslator.java:71)\n	at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:365)\n	at com.sun.proxy.$Proxy8.insert(Unknown Source)\n	at org.mybatis.spring.SqlSessionTemplate.insert(SqlSessionTemplate.java:237)\n	at org.apache.ibatis.binding.MapperMethod.execute(MapperMethod.java:79)\n	at org.apache.ibatis.binding.MapperProxy.invoke(MapperProxy.java:40)\n	at com.sun.proxy.$Proxy87.save(Unknown Source)\n	at com.ysten.local.bss.panel.repository.impl.PanelItemRepositoryImpl.savePanelItem(PanelItemRepositoryImpl.java:37)\n	at com.ysten.local.bss.panel.repository.impl.PanelItemRepositoryImpl$$FastClassByCGLIB$$ac02f3a6.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:698)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:150)\n	at com.ysten.cache.spring.interceptor.CacheInterceptor.invoke(CacheInterceptor.java:86)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:172)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:631)\n	at com.ysten.local.bss.panel.repository.impl.PanelItemRepositoryImpl$$EnhancerByCGLIB$$92e7f9d8.savePanelItem(<generated>)\n	at com.ysten.local.bss.panel.service.impl.PanelItemServiceImpl.savePanelItemData(PanelItemServiceImpl.java:96)\n	at com.ysten.local.bss.panel.service.impl.PanelItemServiceImpl$$FastClassByCGLIB$$c0139502.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:698)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:150)\n	at org.springframework.transaction.interceptor.TransactionInterceptor$1.proceedWithInvocation(TransactionInterceptor.java:96)\n	at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:260)\n	at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:94)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:172)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:631)\n	at com.ysten.local.bss.panel.service.impl.PanelItemServiceImpl$$EnhancerByCGLIB$$fb40a8d7.savePanelItemData(<generated>)\n	at com.ysten.local.bss.panel.web.controller.PanelItemController.addPanelItem(PanelItemController.java:107)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:39)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)\n	at java.lang.reflect.Method.invoke(Method.java:597)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invoke(InvocableHandlerMethod.java:219)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:132)\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:104)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandleMethod(RequestMappingHandlerAdapter.java:745)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:686)\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:80)\n	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:925)\n	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:856)\n	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:936)\n	at org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:838)\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:646)\n	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:812)\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:727)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:303)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:208)\n	at com.ysten.utils.filter.AccessFilter.doFilter(AccessFilter.java:55)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:241)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:208)\n	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:88)\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:241)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:208)\n	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:220)\n	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:122)\n	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:501)\n	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:171)\n	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:102)\n	at org.apache.catalina.valves.AccessLogValve.invoke(AccessLogValve.java:950)\n	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:116)\n	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:408)\n	at org.apache.coyote.http11.AbstractHttp11Processor.process(AbstractHttp11Processor.java:1040)\n	at org.apache.coyote.AbstractProtocol$AbstractConnectionHandler.process(AbstractProtocol.java:607)\n	at org.apache.tomcat.util.net.JIoEndpoint$SocketProcessor.run(JIoEndpoint.java:316)\n	at java.util.concurrent.ThreadPoolExecutor$Worker.runTask(ThreadPoolExecutor.java:895)\n	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:918)\n	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)\n	at java.lang.Thread.run(Thread.java:662)\nCaused by: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'item_content_type,app_enter_url\n       )\n     values (\n              null,\n     \' at line 6\n	at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)\n	at sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:39)\n	at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:27)\n	at java.lang.reflect.Constructor.newInstance(Constructor.java:513)\n	at com.mysql.jdbc.Util.handleNewInstance(Util.java:407)\n	at com.mysql.jdbc.Util.getInstance(Util.java:382)\n	at com.mysql.jdbc.SQLError.createSQLException(SQLError.java:1052)\n	at com.mysql.jdbc.MysqlIO.checkErrorPacket(MysqlIO.java:3603)\n	at com.mysql.jdbc.MysqlIO.checkErrorPacket(MysqlIO.java:3535)\n	at com.mysql.jdbc.MysqlIO.sendCommand(MysqlIO.java:1989)\n	at com.mysql.jdbc.MysqlIO.sqlQueryDirect(MysqlIO.java:2150)\n	at com.mysql.jdbc.ConnectionImpl.execSQL(ConnectionImpl.java:2626)\n	at com.mysql.jdbc.PreparedStatement.executeInternal(PreparedStatement.java:2119)\n	at com.mysql.jdbc.PreparedStatement.execute(PreparedStatement.java:1362)\n	at org.apache.tomcat.dbcp.dbcp.DelegatingPreparedStatement.execute(DelegatingPreparedStatement.java:172)\n	at org.apache.tomcat.dbcp.dbcp.DelegatingPreparedStatement.execute(DelegatingPreparedStatement.java:172)\n	at org.apache.ibatis.executor.statement.PreparedStatementHandler.update(PreparedStatementHandler.java:42)\n	at org.apache.ibatis.executor.statement.RoutingStatementHandler.update(RoutingStatementHandler.java:66)\n	at org.apache.ibatis.executor.SimpleExecutor.doUpdate(SimpleExecutor.java:45)\n	at org.apache.ibatis.executor.BaseExecutor.update(BaseExecutor.java:108)\n	at org.apache.ibatis.executor.CachingExecutor.update(CachingExecutor.java:67)\n	at org.apache.ibatis.session.defaults.DefaultSqlSession.update(DefaultSqlSession.java:145)\n	at org.apache.ibatis.session.defaults.DefaultSqlSession.insert(DefaultSqlSession.java:134)\n	at sun.reflect.GeneratedMethodAccessor161.invoke(Unknown Source)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)\n	at java.lang.reflect.Method.invoke(Method.java:597)\n	at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:355)\n	... 67 more', 'admin', '58.214.17.75', '2014-10-17 17:06:41'),
	(33, '管理系统登陆', '系统登陆', '1', '用户 admin 在2014-10-17 17:07:16登陆系统成功', 'admin', '58.214.17.75', '2014-10-17 17:07:16'),
	(34, '面板项项信息维护', '新增', 'null', '新增面板项失败,org.springframework.jdbc.BadSqlGrammarException: \n### Error updating database.  Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'item_content_type,app_enter_url\n       )\n     values (\n              null,\n     \' at line 6\n### The error may involve com.ysten.local.bss.panel.repository.mapper.PanelItemMapper.save-Inline\n### The error occurred while setting parameters\n### Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'item_content_type,app_enter_url\n       )\n     values (\n              null,\n     \' at line 6\n; bad SQL grammar []; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'item_content_type,app_enter_url\n       )\n     values (\n              null,\n     \' at line 6\n	at org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator.doTranslate(SQLErrorCodeSQLExceptionTranslator.java:237)\n	at org.springframework.jdbc.support.AbstractFallbackSQLExceptionTranslator.translate(AbstractFallbackSQLExceptionTranslator.java:72)\n	at org.mybatis.spring.MyBatisExceptionTranslator.translateExceptionIfPossible(MyBatisExceptionTranslator.java:71)\n	at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:365)\n	at com.sun.proxy.$Proxy8.insert(Unknown Source)\n	at org.mybatis.spring.SqlSessionTemplate.insert(SqlSessionTemplate.java:237)\n	at org.apache.ibatis.binding.MapperMethod.execute(MapperMethod.java:79)\n	at org.apache.ibatis.binding.MapperProxy.invoke(MapperProxy.java:40)\n	at com.sun.proxy.$Proxy87.save(Unknown Source)\n	at com.ysten.local.bss.panel.repository.impl.PanelItemRepositoryImpl.savePanelItem(PanelItemRepositoryImpl.java:37)\n	at com.ysten.local.bss.panel.repository.impl.PanelItemRepositoryImpl$$FastClassByCGLIB$$ac02f3a6.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:698)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:150)\n	at com.ysten.cache.spring.interceptor.CacheInterceptor.invoke(CacheInterceptor.java:86)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:172)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:631)\n	at com.ysten.local.bss.panel.repository.impl.PanelItemRepositoryImpl$$EnhancerByCGLIB$$92e7f9d8.savePanelItem(<generated>)\n	at com.ysten.local.bss.panel.service.impl.PanelItemServiceImpl.savePanelItemData(PanelItemServiceImpl.java:96)\n	at com.ysten.local.bss.panel.service.impl.PanelItemServiceImpl$$FastClassByCGLIB$$c0139502.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:698)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:150)\n	at org.springframework.transaction.interceptor.TransactionInterceptor$1.proceedWithInvocation(TransactionInterceptor.java:96)\n	at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:260)\n	at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:94)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:172)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:631)\n	at com.ysten.local.bss.panel.service.impl.PanelItemServiceImpl$$EnhancerByCGLIB$$fb40a8d7.savePanelItemData(<generated>)\n	at com.ysten.local.bss.panel.web.controller.PanelItemController.addPanelItem(PanelItemController.java:107)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:39)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)\n	at java.lang.reflect.Method.invoke(Method.java:597)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invoke(InvocableHandlerMethod.java:219)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:132)\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:104)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandleMethod(RequestMappingHandlerAdapter.java:745)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:686)\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:80)\n	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:925)\n	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:856)\n	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:936)\n	at org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:838)\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:646)\n	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:812)\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:727)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:303)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:208)\n	at com.ysten.utils.filter.AccessFilter.doFilter(AccessFilter.java:55)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:241)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:208)\n	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:88)\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:241)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:208)\n	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:220)\n	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:122)\n	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:501)\n	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:171)\n	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:102)\n	at org.apache.catalina.valves.AccessLogValve.invoke(AccessLogValve.java:950)\n	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:116)\n	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:408)\n	at org.apache.coyote.http11.AbstractHttp11Processor.process(AbstractHttp11Processor.java:1040)\n	at org.apache.coyote.AbstractProtocol$AbstractConnectionHandler.process(AbstractProtocol.java:607)\n	at org.apache.tomcat.util.net.JIoEndpoint$SocketProcessor.run(JIoEndpoint.java:316)\n	at java.util.concurrent.ThreadPoolExecutor$Worker.runTask(ThreadPoolExecutor.java:895)\n	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:918)\n	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)\n	at java.lang.Thread.run(Thread.java:662)\nCaused by: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'item_content_type,app_enter_url\n       )\n     values (\n              null,\n     \' at line 6\n	at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)\n	at sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:39)\n	at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:27)\n	at java.lang.reflect.Constructor.newInstance(Constructor.java:513)\n	at com.mysql.jdbc.Util.handleNewInstance(Util.java:407)\n	at com.mysql.jdbc.Util.getInstance(Util.java:382)\n	at com.mysql.jdbc.SQLError.createSQLException(SQLError.java:1052)\n	at com.mysql.jdbc.MysqlIO.checkErrorPacket(MysqlIO.java:3603)\n	at com.mysql.jdbc.MysqlIO.checkErrorPacket(MysqlIO.java:3535)\n	at com.mysql.jdbc.MysqlIO.sendCommand(MysqlIO.java:1989)\n	at com.mysql.jdbc.MysqlIO.sqlQueryDirect(MysqlIO.java:2150)\n	at com.mysql.jdbc.ConnectionImpl.execSQL(ConnectionImpl.java:2626)\n	at com.mysql.jdbc.PreparedStatement.executeInternal(PreparedStatement.java:2119)\n	at com.mysql.jdbc.PreparedStatement.execute(PreparedStatement.java:1362)\n	at org.apache.tomcat.dbcp.dbcp.DelegatingPreparedStatement.execute(DelegatingPreparedStatement.java:172)\n	at org.apache.tomcat.dbcp.dbcp.DelegatingPreparedStatement.execute(DelegatingPreparedStatement.java:172)\n	at org.apache.ibatis.executor.statement.PreparedStatementHandler.update(PreparedStatementHandler.java:42)\n	at org.apache.ibatis.executor.statement.RoutingStatementHandler.update(RoutingStatementHandler.java:66)\n	at org.apache.ibatis.executor.SimpleExecutor.doUpdate(SimpleExecutor.java:45)\n	at org.apache.ibatis.executor.BaseExecutor.update(BaseExecutor.java:108)\n	at org.apache.ibatis.executor.CachingExecutor.update(CachingExecutor.java:67)\n	at org.apache.ibatis.session.defaults.DefaultSqlSession.update(DefaultSqlSession.java:145)\n	at org.apache.ibatis.session.defaults.DefaultSqlSession.insert(DefaultSqlSession.java:134)\n	at sun.reflect.GeneratedMethodAccessor161.invoke(Unknown Source)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)\n	at java.lang.reflect.Method.invoke(Method.java:597)\n	at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:355)\n	... 67 more', 'admin', '58.214.17.75', '2014-10-17 17:08:12'),
	(35, '面板项项信息维护', '新增', 'null', '新增面板项失败,org.springframework.jdbc.BadSqlGrammarException: \n### Error updating database.  Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'item_content_type,app_enter_url\n       )\n     values (\n              null,\n     \' at line 6\n### The error may involve com.ysten.local.bss.panel.repository.mapper.PanelItemMapper.save-Inline\n### The error occurred while setting parameters\n### Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'item_content_type,app_enter_url\n       )\n     values (\n              null,\n     \' at line 6\n; bad SQL grammar []; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'item_content_type,app_enter_url\n       )\n     values (\n              null,\n     \' at line 6\n	at org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator.doTranslate(SQLErrorCodeSQLExceptionTranslator.java:237)\n	at org.springframework.jdbc.support.AbstractFallbackSQLExceptionTranslator.translate(AbstractFallbackSQLExceptionTranslator.java:72)\n	at org.mybatis.spring.MyBatisExceptionTranslator.translateExceptionIfPossible(MyBatisExceptionTranslator.java:71)\n	at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:365)\n	at com.sun.proxy.$Proxy8.insert(Unknown Source)\n	at org.mybatis.spring.SqlSessionTemplate.insert(SqlSessionTemplate.java:237)\n	at org.apache.ibatis.binding.MapperMethod.execute(MapperMethod.java:79)\n	at org.apache.ibatis.binding.MapperProxy.invoke(MapperProxy.java:40)\n	at com.sun.proxy.$Proxy87.save(Unknown Source)\n	at com.ysten.local.bss.panel.repository.impl.PanelItemRepositoryImpl.savePanelItem(PanelItemRepositoryImpl.java:37)\n	at com.ysten.local.bss.panel.repository.impl.PanelItemRepositoryImpl$$FastClassByCGLIB$$ac02f3a6.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:698)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:150)\n	at com.ysten.cache.spring.interceptor.CacheInterceptor.invoke(CacheInterceptor.java:86)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:172)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:631)\n	at com.ysten.local.bss.panel.repository.impl.PanelItemRepositoryImpl$$EnhancerByCGLIB$$92e7f9d8.savePanelItem(<generated>)\n	at com.ysten.local.bss.panel.service.impl.PanelItemServiceImpl.savePanelItemData(PanelItemServiceImpl.java:96)\n	at com.ysten.local.bss.panel.service.impl.PanelItemServiceImpl$$FastClassByCGLIB$$c0139502.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:698)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:150)\n	at org.springframework.transaction.interceptor.TransactionInterceptor$1.proceedWithInvocation(TransactionInterceptor.java:96)\n	at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:260)\n	at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:94)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:172)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:631)\n	at com.ysten.local.bss.panel.service.impl.PanelItemServiceImpl$$EnhancerByCGLIB$$fb40a8d7.savePanelItemData(<generated>)\n	at com.ysten.local.bss.panel.web.controller.PanelItemController.addPanelItem(PanelItemController.java:107)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:39)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)\n	at java.lang.reflect.Method.invoke(Method.java:597)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invoke(InvocableHandlerMethod.java:219)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:132)\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:104)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandleMethod(RequestMappingHandlerAdapter.java:745)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:686)\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:80)\n	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:925)\n	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:856)\n	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:936)\n	at org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:838)\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:646)\n	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:812)\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:727)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:303)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:208)\n	at com.ysten.utils.filter.AccessFilter.doFilter(AccessFilter.java:55)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:241)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:208)\n	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:88)\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:241)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:208)\n	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:220)\n	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:122)\n	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:501)\n	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:171)\n	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:102)\n	at org.apache.catalina.valves.AccessLogValve.invoke(AccessLogValve.java:950)\n	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:116)\n	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:408)\n	at org.apache.coyote.http11.AbstractHttp11Processor.process(AbstractHttp11Processor.java:1040)\n	at org.apache.coyote.AbstractProtocol$AbstractConnectionHandler.process(AbstractProtocol.java:607)\n	at org.apache.tomcat.util.net.JIoEndpoint$SocketProcessor.run(JIoEndpoint.java:316)\n	at java.util.concurrent.ThreadPoolExecutor$Worker.runTask(ThreadPoolExecutor.java:895)\n	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:918)\n	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)\n	at java.lang.Thread.run(Thread.java:662)\nCaused by: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'item_content_type,app_enter_url\n       )\n     values (\n              null,\n     \' at line 6\n	at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)\n	at sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:39)\n	at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:27)\n	at java.lang.reflect.Constructor.newInstance(Constructor.java:513)\n	at com.mysql.jdbc.Util.handleNewInstance(Util.java:407)\n	at com.mysql.jdbc.Util.getInstance(Util.java:382)\n	at com.mysql.jdbc.SQLError.createSQLException(SQLError.java:1052)\n	at com.mysql.jdbc.MysqlIO.checkErrorPacket(MysqlIO.java:3603)\n	at com.mysql.jdbc.MysqlIO.checkErrorPacket(MysqlIO.java:3535)\n	at com.mysql.jdbc.MysqlIO.sendCommand(MysqlIO.java:1989)\n	at com.mysql.jdbc.MysqlIO.sqlQueryDirect(MysqlIO.java:2150)\n	at com.mysql.jdbc.ConnectionImpl.execSQL(ConnectionImpl.java:2626)\n	at com.mysql.jdbc.PreparedStatement.executeInternal(PreparedStatement.java:2119)\n	at com.mysql.jdbc.PreparedStatement.execute(PreparedStatement.java:1362)\n	at org.apache.tomcat.dbcp.dbcp.DelegatingPreparedStatement.execute(DelegatingPreparedStatement.java:172)\n	at org.apache.tomcat.dbcp.dbcp.DelegatingPreparedStatement.execute(DelegatingPreparedStatement.java:172)\n	at org.apache.ibatis.executor.statement.PreparedStatementHandler.update(PreparedStatementHandler.java:42)\n	at org.apache.ibatis.executor.statement.RoutingStatementHandler.update(RoutingStatementHandler.java:66)\n	at org.apache.ibatis.executor.SimpleExecutor.doUpdate(SimpleExecutor.java:45)\n	at org.apache.ibatis.executor.BaseExecutor.update(BaseExecutor.java:108)\n	at org.apache.ibatis.executor.CachingExecutor.update(CachingExecutor.java:67)\n	at org.apache.ibatis.session.defaults.DefaultSqlSession.update(DefaultSqlSession.java:145)\n	at org.apache.ibatis.session.defaults.DefaultSqlSession.insert(DefaultSqlSession.java:134)\n	at sun.reflect.GeneratedMethodAccessor161.invoke(Unknown Source)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)\n	at java.lang.reflect.Method.invoke(Method.java:597)\n	at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:355)\n	... 67 more', 'admin', '58.214.17.75', '2014-10-17 17:09:03'),
	(36, '面板项项信息维护', '新增', 'null', '新增面板项失败,org.springframework.jdbc.BadSqlGrammarException: \n### Error updating database.  Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'item_content_type,app_enter_url\n       )\n     values (\n              null,\n     \' at line 6\n### The error may involve com.ysten.local.bss.panel.repository.mapper.PanelItemMapper.save-Inline\n### The error occurred while setting parameters\n### Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'item_content_type,app_enter_url\n       )\n     values (\n              null,\n     \' at line 6\n; bad SQL grammar []; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'item_content_type,app_enter_url\n       )\n     values (\n              null,\n     \' at line 6\n	at org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator.doTranslate(SQLErrorCodeSQLExceptionTranslator.java:237)\n	at org.springframework.jdbc.support.AbstractFallbackSQLExceptionTranslator.translate(AbstractFallbackSQLExceptionTranslator.java:72)\n	at org.mybatis.spring.MyBatisExceptionTranslator.translateExceptionIfPossible(MyBatisExceptionTranslator.java:71)\n	at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:365)\n	at com.sun.proxy.$Proxy8.insert(Unknown Source)\n	at org.mybatis.spring.SqlSessionTemplate.insert(SqlSessionTemplate.java:237)\n	at org.apache.ibatis.binding.MapperMethod.execute(MapperMethod.java:79)\n	at org.apache.ibatis.binding.MapperProxy.invoke(MapperProxy.java:40)\n	at com.sun.proxy.$Proxy87.save(Unknown Source)\n	at com.ysten.local.bss.panel.repository.impl.PanelItemRepositoryImpl.savePanelItem(PanelItemRepositoryImpl.java:37)\n	at com.ysten.local.bss.panel.repository.impl.PanelItemRepositoryImpl$$FastClassByCGLIB$$ac02f3a6.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:698)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:150)\n	at com.ysten.cache.spring.interceptor.CacheInterceptor.invoke(CacheInterceptor.java:86)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:172)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:631)\n	at com.ysten.local.bss.panel.repository.impl.PanelItemRepositoryImpl$$EnhancerByCGLIB$$92e7f9d8.savePanelItem(<generated>)\n	at com.ysten.local.bss.panel.service.impl.PanelItemServiceImpl.savePanelItemData(PanelItemServiceImpl.java:96)\n	at com.ysten.local.bss.panel.service.impl.PanelItemServiceImpl$$FastClassByCGLIB$$c0139502.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:698)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:150)\n	at org.springframework.transaction.interceptor.TransactionInterceptor$1.proceedWithInvocation(TransactionInterceptor.java:96)\n	at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:260)\n	at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:94)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:172)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:631)\n	at com.ysten.local.bss.panel.service.impl.PanelItemServiceImpl$$EnhancerByCGLIB$$fb40a8d7.savePanelItemData(<generated>)\n	at com.ysten.local.bss.panel.web.controller.PanelItemController.addPanelItem(PanelItemController.java:107)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:39)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)\n	at java.lang.reflect.Method.invoke(Method.java:597)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invoke(InvocableHandlerMethod.java:219)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:132)\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:104)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandleMethod(RequestMappingHandlerAdapter.java:745)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:686)\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:80)\n	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:925)\n	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:856)\n	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:936)\n	at org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:838)\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:646)\n	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:812)\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:727)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:303)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:208)\n	at com.ysten.utils.filter.AccessFilter.doFilter(AccessFilter.java:55)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:241)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:208)\n	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:88)\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:241)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:208)\n	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:220)\n	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:122)\n	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:501)\n	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:171)\n	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:102)\n	at org.apache.catalina.valves.AccessLogValve.invoke(AccessLogValve.java:950)\n	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:116)\n	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:408)\n	at org.apache.coyote.http11.AbstractHttp11Processor.process(AbstractHttp11Processor.java:1040)\n	at org.apache.coyote.AbstractProtocol$AbstractConnectionHandler.process(AbstractProtocol.java:607)\n	at org.apache.tomcat.util.net.JIoEndpoint$SocketProcessor.run(JIoEndpoint.java:316)\n	at java.util.concurrent.ThreadPoolExecutor$Worker.runTask(ThreadPoolExecutor.java:895)\n	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:918)\n	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)\n	at java.lang.Thread.run(Thread.java:662)\nCaused by: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'item_content_type,app_enter_url\n       )\n     values (\n              null,\n     \' at line 6\n	at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)\n	at sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:39)\n	at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:27)\n	at java.lang.reflect.Constructor.newInstance(Constructor.java:513)\n	at com.mysql.jdbc.Util.handleNewInstance(Util.java:407)\n	at com.mysql.jdbc.Util.getInstance(Util.java:382)\n	at com.mysql.jdbc.SQLError.createSQLException(SQLError.java:1052)\n	at com.mysql.jdbc.MysqlIO.checkErrorPacket(MysqlIO.java:3603)\n	at com.mysql.jdbc.MysqlIO.checkErrorPacket(MysqlIO.java:3535)\n	at com.mysql.jdbc.MysqlIO.sendCommand(MysqlIO.java:1989)\n	at com.mysql.jdbc.MysqlIO.sqlQueryDirect(MysqlIO.java:2150)\n	at com.mysql.jdbc.ConnectionImpl.execSQL(ConnectionImpl.java:2626)\n	at com.mysql.jdbc.PreparedStatement.executeInternal(PreparedStatement.java:2119)\n	at com.mysql.jdbc.PreparedStatement.execute(PreparedStatement.java:1362)\n	at org.apache.tomcat.dbcp.dbcp.DelegatingPreparedStatement.execute(DelegatingPreparedStatement.java:172)\n	at org.apache.tomcat.dbcp.dbcp.DelegatingPreparedStatement.execute(DelegatingPreparedStatement.java:172)\n	at org.apache.ibatis.executor.statement.PreparedStatementHandler.update(PreparedStatementHandler.java:42)\n	at org.apache.ibatis.executor.statement.RoutingStatementHandler.update(RoutingStatementHandler.java:66)\n	at org.apache.ibatis.executor.SimpleExecutor.doUpdate(SimpleExecutor.java:45)\n	at org.apache.ibatis.executor.BaseExecutor.update(BaseExecutor.java:108)\n	at org.apache.ibatis.executor.CachingExecutor.update(CachingExecutor.java:67)\n	at org.apache.ibatis.session.defaults.DefaultSqlSession.update(DefaultSqlSession.java:145)\n	at org.apache.ibatis.session.defaults.DefaultSqlSession.insert(DefaultSqlSession.java:134)\n	at sun.reflect.GeneratedMethodAccessor161.invoke(Unknown Source)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)\n	at java.lang.reflect.Method.invoke(Method.java:597)\n	at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:355)\n	... 67 more', 'admin', '58.214.17.75', '2014-10-17 17:10:21'),
	(37, '面板项项信息维护', '新增', 'null', '新增面板项失败,org.springframework.jdbc.BadSqlGrammarException: \n### Error updating database.  Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'item_content_type,app_enter_url\n       )\n     values (\n              null,\n     \' at line 6\n### The error may involve com.ysten.local.bss.panel.repository.mapper.PanelItemMapper.save-Inline\n### The error occurred while setting parameters\n### Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'item_content_type,app_enter_url\n       )\n     values (\n              null,\n     \' at line 6\n; bad SQL grammar []; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'item_content_type,app_enter_url\n       )\n     values (\n              null,\n     \' at line 6\n	at org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator.doTranslate(SQLErrorCodeSQLExceptionTranslator.java:237)\n	at org.springframework.jdbc.support.AbstractFallbackSQLExceptionTranslator.translate(AbstractFallbackSQLExceptionTranslator.java:72)\n	at org.mybatis.spring.MyBatisExceptionTranslator.translateExceptionIfPossible(MyBatisExceptionTranslator.java:71)\n	at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:365)\n	at com.sun.proxy.$Proxy8.insert(Unknown Source)\n	at org.mybatis.spring.SqlSessionTemplate.insert(SqlSessionTemplate.java:237)\n	at org.apache.ibatis.binding.MapperMethod.execute(MapperMethod.java:79)\n	at org.apache.ibatis.binding.MapperProxy.invoke(MapperProxy.java:40)\n	at com.sun.proxy.$Proxy87.save(Unknown Source)\n	at com.ysten.local.bss.panel.repository.impl.PanelItemRepositoryImpl.savePanelItem(PanelItemRepositoryImpl.java:37)\n	at com.ysten.local.bss.panel.repository.impl.PanelItemRepositoryImpl$$FastClassByCGLIB$$ac02f3a6.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:698)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:150)\n	at com.ysten.cache.spring.interceptor.CacheInterceptor.invoke(CacheInterceptor.java:86)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:172)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:631)\n	at com.ysten.local.bss.panel.repository.impl.PanelItemRepositoryImpl$$EnhancerByCGLIB$$92e7f9d8.savePanelItem(<generated>)\n	at com.ysten.local.bss.panel.service.impl.PanelItemServiceImpl.savePanelItemData(PanelItemServiceImpl.java:96)\n	at com.ysten.local.bss.panel.service.impl.PanelItemServiceImpl$$FastClassByCGLIB$$c0139502.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:698)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:150)\n	at org.springframework.transaction.interceptor.TransactionInterceptor$1.proceedWithInvocation(TransactionInterceptor.java:96)\n	at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:260)\n	at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:94)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:172)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:631)\n	at com.ysten.local.bss.panel.service.impl.PanelItemServiceImpl$$EnhancerByCGLIB$$fb40a8d7.savePanelItemData(<generated>)\n	at com.ysten.local.bss.panel.web.controller.PanelItemController.addPanelItem(PanelItemController.java:107)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:39)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)\n	at java.lang.reflect.Method.invoke(Method.java:597)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invoke(InvocableHandlerMethod.java:219)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:132)\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:104)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandleMethod(RequestMappingHandlerAdapter.java:745)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:686)\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:80)\n	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:925)\n	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:856)\n	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:936)\n	at org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:838)\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:646)\n	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:812)\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:727)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:303)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:208)\n	at com.ysten.utils.filter.AccessFilter.doFilter(AccessFilter.java:55)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:241)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:208)\n	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:88)\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:241)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:208)\n	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:220)\n	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:122)\n	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:501)\n	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:171)\n	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:102)\n	at org.apache.catalina.valves.AccessLogValve.invoke(AccessLogValve.java:950)\n	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:116)\n	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:408)\n	at org.apache.coyote.http11.AbstractHttp11Processor.process(AbstractHttp11Processor.java:1040)\n	at org.apache.coyote.AbstractProtocol$AbstractConnectionHandler.process(AbstractProtocol.java:607)\n	at org.apache.tomcat.util.net.JIoEndpoint$SocketProcessor.run(JIoEndpoint.java:316)\n	at java.util.concurrent.ThreadPoolExecutor$Worker.runTask(ThreadPoolExecutor.java:895)\n	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:918)\n	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)\n	at java.lang.Thread.run(Thread.java:662)\nCaused by: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'item_content_type,app_enter_url\n       )\n     values (\n              null,\n     \' at line 6\n	at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)\n	at sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:39)\n	at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:27)\n	at java.lang.reflect.Constructor.newInstance(Constructor.java:513)\n	at com.mysql.jdbc.Util.handleNewInstance(Util.java:407)\n	at com.mysql.jdbc.Util.getInstance(Util.java:382)\n	at com.mysql.jdbc.SQLError.createSQLException(SQLError.java:1052)\n	at com.mysql.jdbc.MysqlIO.checkErrorPacket(MysqlIO.java:3603)\n	at com.mysql.jdbc.MysqlIO.checkErrorPacket(MysqlIO.java:3535)\n	at com.mysql.jdbc.MysqlIO.sendCommand(MysqlIO.java:1989)\n	at com.mysql.jdbc.MysqlIO.sqlQueryDirect(MysqlIO.java:2150)\n	at com.mysql.jdbc.ConnectionImpl.execSQL(ConnectionImpl.java:2626)\n	at com.mysql.jdbc.PreparedStatement.executeInternal(PreparedStatement.java:2119)\n	at com.mysql.jdbc.PreparedStatement.execute(PreparedStatement.java:1362)\n	at org.apache.tomcat.dbcp.dbcp.DelegatingPreparedStatement.execute(DelegatingPreparedStatement.java:172)\n	at org.apache.tomcat.dbcp.dbcp.DelegatingPreparedStatement.execute(DelegatingPreparedStatement.java:172)\n	at org.apache.ibatis.executor.statement.PreparedStatementHandler.update(PreparedStatementHandler.java:42)\n	at org.apache.ibatis.executor.statement.RoutingStatementHandler.update(RoutingStatementHandler.java:66)\n	at org.apache.ibatis.executor.SimpleExecutor.doUpdate(SimpleExecutor.java:45)\n	at org.apache.ibatis.executor.BaseExecutor.update(BaseExecutor.java:108)\n	at org.apache.ibatis.executor.CachingExecutor.update(CachingExecutor.java:67)\n	at org.apache.ibatis.session.defaults.DefaultSqlSession.update(DefaultSqlSession.java:145)\n	at org.apache.ibatis.session.defaults.DefaultSqlSession.insert(DefaultSqlSession.java:134)\n	at sun.reflect.GeneratedMethodAccessor161.invoke(Unknown Source)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)\n	at java.lang.reflect.Method.invoke(Method.java:597)\n	at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:355)\n	... 67 more', 'admin', '58.214.17.75', '2014-10-17 17:14:40'),
	(38, '面板项项信息维护', '新增', 'null', '新增面板项失败,org.springframework.jdbc.BadSqlGrammarException: \n### Error updating database.  Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'item_content_type,app_enter_url\n       )\n     values (\n              null,\n     \' at line 6\n### The error may involve com.ysten.local.bss.panel.repository.mapper.PanelItemMapper.save-Inline\n### The error occurred while setting parameters\n### Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'item_content_type,app_enter_url\n       )\n     values (\n              null,\n     \' at line 6\n; bad SQL grammar []; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'item_content_type,app_enter_url\n       )\n     values (\n              null,\n     \' at line 6\n	at org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator.doTranslate(SQLErrorCodeSQLExceptionTranslator.java:237)\n	at org.springframework.jdbc.support.AbstractFallbackSQLExceptionTranslator.translate(AbstractFallbackSQLExceptionTranslator.java:72)\n	at org.mybatis.spring.MyBatisExceptionTranslator.translateExceptionIfPossible(MyBatisExceptionTranslator.java:71)\n	at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:365)\n	at com.sun.proxy.$Proxy8.insert(Unknown Source)\n	at org.mybatis.spring.SqlSessionTemplate.insert(SqlSessionTemplate.java:237)\n	at org.apache.ibatis.binding.MapperMethod.execute(MapperMethod.java:79)\n	at org.apache.ibatis.binding.MapperProxy.invoke(MapperProxy.java:40)\n	at com.sun.proxy.$Proxy87.save(Unknown Source)\n	at com.ysten.local.bss.panel.repository.impl.PanelItemRepositoryImpl.savePanelItem(PanelItemRepositoryImpl.java:37)\n	at com.ysten.local.bss.panel.repository.impl.PanelItemRepositoryImpl$$FastClassByCGLIB$$ac02f3a6.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:698)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:150)\n	at com.ysten.cache.spring.interceptor.CacheInterceptor.invoke(CacheInterceptor.java:86)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:172)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:631)\n	at com.ysten.local.bss.panel.repository.impl.PanelItemRepositoryImpl$$EnhancerByCGLIB$$92e7f9d8.savePanelItem(<generated>)\n	at com.ysten.local.bss.panel.service.impl.PanelItemServiceImpl.savePanelItemData(PanelItemServiceImpl.java:96)\n	at com.ysten.local.bss.panel.service.impl.PanelItemServiceImpl$$FastClassByCGLIB$$c0139502.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:698)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:150)\n	at org.springframework.transaction.interceptor.TransactionInterceptor$1.proceedWithInvocation(TransactionInterceptor.java:96)\n	at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:260)\n	at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:94)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:172)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:631)\n	at com.ysten.local.bss.panel.service.impl.PanelItemServiceImpl$$EnhancerByCGLIB$$fb40a8d7.savePanelItemData(<generated>)\n	at com.ysten.local.bss.panel.web.controller.PanelItemController.addPanelItem(PanelItemController.java:107)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:39)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)\n	at java.lang.reflect.Method.invoke(Method.java:597)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invoke(InvocableHandlerMethod.java:219)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:132)\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:104)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandleMethod(RequestMappingHandlerAdapter.java:745)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:686)\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:80)\n	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:925)\n	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:856)\n	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:936)\n	at org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:838)\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:646)\n	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:812)\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:727)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:303)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:208)\n	at com.ysten.utils.filter.AccessFilter.doFilter(AccessFilter.java:55)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:241)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:208)\n	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:88)\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:241)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:208)\n	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:220)\n	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:122)\n	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:501)\n	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:171)\n	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:102)\n	at org.apache.catalina.valves.AccessLogValve.invoke(AccessLogValve.java:950)\n	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:116)\n	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:408)\n	at org.apache.coyote.http11.AbstractHttp11Processor.process(AbstractHttp11Processor.java:1040)\n	at org.apache.coyote.AbstractProtocol$AbstractConnectionHandler.process(AbstractProtocol.java:607)\n	at org.apache.tomcat.util.net.JIoEndpoint$SocketProcessor.run(JIoEndpoint.java:316)\n	at java.util.concurrent.ThreadPoolExecutor$Worker.runTask(ThreadPoolExecutor.java:895)\n	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:918)\n	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)\n	at java.lang.Thread.run(Thread.java:662)\nCaused by: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'item_content_type,app_enter_url\n       )\n     values (\n              null,\n     \' at line 6\n	at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)\n	at sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:39)\n	at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:27)\n	at java.lang.reflect.Constructor.newInstance(Constructor.java:513)\n	at com.mysql.jdbc.Util.handleNewInstance(Util.java:407)\n	at com.mysql.jdbc.Util.getInstance(Util.java:382)\n	at com.mysql.jdbc.SQLError.createSQLException(SQLError.java:1052)\n	at com.mysql.jdbc.MysqlIO.checkErrorPacket(MysqlIO.java:3603)\n	at com.mysql.jdbc.MysqlIO.checkErrorPacket(MysqlIO.java:3535)\n	at com.mysql.jdbc.MysqlIO.sendCommand(MysqlIO.java:1989)\n	at com.mysql.jdbc.MysqlIO.sqlQueryDirect(MysqlIO.java:2150)\n	at com.mysql.jdbc.ConnectionImpl.execSQL(ConnectionImpl.java:2626)\n	at com.mysql.jdbc.PreparedStatement.executeInternal(PreparedStatement.java:2119)\n	at com.mysql.jdbc.PreparedStatement.execute(PreparedStatement.java:1362)\n	at org.apache.tomcat.dbcp.dbcp.DelegatingPreparedStatement.execute(DelegatingPreparedStatement.java:172)\n	at org.apache.tomcat.dbcp.dbcp.DelegatingPreparedStatement.execute(DelegatingPreparedStatement.java:172)\n	at org.apache.ibatis.executor.statement.PreparedStatementHandler.update(PreparedStatementHandler.java:42)\n	at org.apache.ibatis.executor.statement.RoutingStatementHandler.update(RoutingStatementHandler.java:66)\n	at org.apache.ibatis.executor.SimpleExecutor.doUpdate(SimpleExecutor.java:45)\n	at org.apache.ibatis.executor.BaseExecutor.update(BaseExecutor.java:108)\n	at org.apache.ibatis.executor.CachingExecutor.update(CachingExecutor.java:67)\n	at org.apache.ibatis.session.defaults.DefaultSqlSession.update(DefaultSqlSession.java:145)\n	at org.apache.ibatis.session.defaults.DefaultSqlSession.insert(DefaultSqlSession.java:134)\n	at sun.reflect.GeneratedMethodAccessor161.invoke(Unknown Source)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)\n	at java.lang.reflect.Method.invoke(Method.java:597)\n	at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:355)\n	... 67 more', 'admin', '58.214.17.75', '2014-10-17 17:15:24'),
	(39, '管理系统登陆', '系统登陆', '1', '用户 admin 在2014-10-17 17:22:01登陆系统成功', 'admin', '192.168.2.73', '2014-10-17 17:22:03'),
	(40, '面板项项信息维护', '新增', '1', '新增面板项成功!', 'admin', '192.168.2.73', '2014-10-17 17:23:15'),
	(41, '面板项项信息维护', '新增', '2', '新增面板项成功!', 'admin', '192.168.2.73', '2014-10-17 17:23:19'),
	(42, '管理系统登陆', '系统登陆', '1', '用户 admin 在2014-10-17 17:26:48登陆系统成功', 'admin', '192.168.2.73', '2014-10-17 17:26:50'),
	(43, '面板项项信息维护', '修改', '2', '更新面板项成功！', 'admin', '192.168.2.73', '2014-10-17 17:27:42'),
	(44, '面板项项信息维护', '修改', '1', '更新面板项成功！', 'admin', '192.168.2.73', '2014-10-17 17:27:59'),
	(45, '面板信息维护', '面板配置预览', '', '面板配置成功', 'admin', '192.168.2.73', '2014-10-17 17:29:28'),
	(46, '面板信息维护', '面板配置预览', '', '面板配置成功', 'admin', '192.168.2.73', '2014-10-17 17:29:32'),
	(47, '面板信息维护', '面板配置预览', '', '面板配置成功', 'admin', '192.168.2.73', '2014-10-17 17:29:37'),
	(48, '面板信息维护', '面板配置预览', '', '面板配置成功', 'admin', '192.168.2.73', '2014-10-17 17:29:38'),
	(49, '面板信息维护', '面板配置预览', '', '面板配置成功', 'admin', '192.168.2.73', '2014-10-17 17:29:38'),
	(50, '面板信息维护', '面板配置预览', '', '面板配置成功', 'admin', '192.168.2.73', '2014-10-17 17:29:38'),
	(51, '面板信息维护', '面板配置预览', '', '面板配置成功', 'admin', '192.168.2.73', '2014-10-17 17:29:38'),
	(52, '面板包信息维护', '新增', '1', '新增面板包成功!', 'admin', '192.168.2.73', '2014-10-17 17:30:06'),
	(53, '面板信息维护', '面板上线', '1', '上线成功', 'admin', '192.168.2.73', '2014-10-17 17:33:29'),
	(54, '导航信息维护', '新增', '1', '新增导航信息成功！新增导航 标题：dd;导航类型：1;动作类型:OpenUrl动作地址:dd;序号:1', 'admin', '192.168.2.73', '2014-10-17 17:35:31'),
	(55, '导航信息维护', '新增', '2', '新增导航信息成功！新增导航 标题：aa;导航类型：2;动作类型:OpenUrl动作地址:aa;序号:2', 'admin', '192.168.2.73', '2014-10-17 17:35:45'),
	(56, '面板包信息维护', '配置面板包', '1', '面板包配置失败，org.springframework.dao.DeadlockLoserDataAccessException: \r\n### Error updating database.  Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLTransactionRollbackException: Deadlock found when trying to get lock; try restarting transaction\r\n### The error may involve com.ysten.local.bss.panel.repository.mapper.PanelPackageMapMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLTransactionRollbackException: Deadlock found when trying to get lock; try restarting transaction\n; SQL []; Deadlock found when trying to get lock; try restarting transaction; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLTransactionRollbackException: Deadlock found when trying to get lock; try restarting transaction\r\n	at org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator.doTranslate(SQLErrorCodeSQLExceptionTranslator.java:269)\r\n	at org.springframework.jdbc.support.AbstractFallbackSQLExceptionTranslator.translate(AbstractFallbackSQLExceptionTranslator.java:72)\r\n	at org.mybatis.spring.MyBatisExceptionTranslator.translateExceptionIfPossible(MyBatisExceptionTranslator.java:71)\r\n	at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:365)\r\n	at com.sun.proxy.$Proxy7.insert(Unknown Source)\r\n	at org.mybatis.spring.SqlSessionTemplate.insert(SqlSessionTemplate.java:237)\r\n	at org.apache.ibatis.binding.MapperMethod.execute(MapperMethod.java:79)\r\n	at org.apache.ibatis.binding.MapperProxy.invoke(MapperProxy.java:40)\r\n	at com.sun.proxy.$Proxy25.insert(Unknown Source)\r\n	at com.ysten.local.bss.panel.repository.impl.PanelPackageMapRepository.savePanelPackageMap(PanelPackageMapRepository.java:34)\r\n	at com.ysten.local.bss.panel.repository.impl.PanelPackageMapRepository$$FastClassByCGLIB$$d9c35e09.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:698)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:150)\r\n	at com.ysten.cache.spring.interceptor.CacheInterceptor.invoke(CacheInterceptor.java:84)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:172)\r\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:631)\r\n	at com.ysten.local.bss.panel.repository.impl.PanelPackageMapRepository$$EnhancerByCGLIB$$adc24351.savePanelPackageMap(<generated>)\r\n	at com.ysten.local.bss.panel.service.impl.PanelPackageMapService.updatePanelPackageConfig(PanelPackageMapService.java:117)\r\n	at com.ysten.local.bss.panel.service.impl.PanelPackageMapService$$FastClassByCGLIB$$903b8a3f.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:698)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:150)\r\n	at org.springframework.transaction.interceptor.TransactionInterceptor$1.proceedWithInvocation(TransactionInterceptor.java:96)\r\n	at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:260)\r\n	at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:94)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:172)\r\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:631)\r\n	at com.ysten.local.bss.panel.service.impl.PanelPackageMapService$$EnhancerByCGLIB$$bda59774.updatePanelPackageConfig(<generated>)\r\n	at com.ysten.local.bss.panel.web.controller.PanelPackageController.configPanelPackage(PanelPackageController.java:255)\r\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n	at sun.reflect.NativeMethodAccessorImpl.invoke(Unknown Source)\r\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source)\r\n	at java.lang.reflect.Method.invoke(Unknown Source)\r\n	at org.springframework.web.method.support.InvocableHandlerMethod.invoke(InvocableHandlerMethod.java:219)\r\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:132)\r\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:104)\r\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandleMethod(RequestMappingHandlerAdapter.java:745)\r\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:686)\r\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:80)\r\n	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:925)\r\n	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:856)\r\n	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:936)\r\n	at org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:838)\r\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:727)\r\n	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:812)\r\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:820)\r\n	at org.mortbay.jetty.servlet.ServletHolder.handle(ServletHolder.java:511)\r\n	at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1221)\r\n	at com.ysten.utils.filter.AccessFilter.doFilter(AccessFilter.java:55)\r\n	at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1212)\r\n	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:88)\r\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\r\n	at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1212)\r\n	at org.mortbay.jetty.servlet.ServletHandler.handle(ServletHandler.java:399)\r\n	at org.mortbay.jetty.security.SecurityHandler.handle(SecurityHandler.java:216)\r\n	at org.mortbay.jetty.servlet.SessionHandler.handle(SessionHandler.java:182)\r\n	at org.mortbay.jetty.handler.ContextHandler.handle(ContextHandler.java:766)\r\n	at org.mortbay.jetty.webapp.WebAppContext.handle(WebAppContext.java:450)\r\n	at org.mortbay.jetty.handler.HandlerWrapper.handle(HandlerWrapper.java:152)\r\n	at org.mortbay.jetty.Server.handle(Server.java:326)\r\n	at org.mortbay.jetty.HttpConnection.handleRequest(HttpConnection.java:542)\r\n	at org.mortbay.jetty.HttpConnection$RequestHandler.headerComplete(HttpConnection.java:928)\r\n	at org.mortbay.jetty.HttpParser.parseNext(HttpParser.java:549)\r\n	at org.mortbay.jetty.HttpParser.parseAvailable(HttpParser.java:212)\r\n	at org.mortbay.jetty.HttpConnection.handle(HttpConnection.java:404)\r\n	at org.mortbay.io.nio.SelectChannelEndPoint.run(SelectChannelEndPoint.java:410)\r\n	at org.mortbay.thread.QueuedThreadPool$PoolThread.run(QueuedThreadPool.java:582)\r\nCaused by: com.mysql.jdbc.exceptions.jdbc4.MySQLTransactionRollbackException: Deadlock found when trying to get lock; try restarting transaction\r\n	at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)\r\n	at sun.reflect.NativeConstructorAccessorImpl.newInstance(Unknown Source)\r\n	at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(Unknown Source)\r\n	at java.lang.reflect.Constructor.newInstance(Unknown Source)\r\n	at com.mysql.jdbc.Util.handleNewInstance(Util.java:407)\r\n	at com.mysql.jdbc.Util.getInstance(Util.java:382)\r\n	at com.mysql.jdbc.SQLError.createSQLException(SQLError.java:1064)\r\n	at com.mysql.jdbc.MysqlIO.checkErrorPacket(MysqlIO.java:3603)\r\n	at com.mysql.jdbc.MysqlIO.checkErrorPacket(MysqlIO.java:3535)\r\n	at com.mysql.jdbc.MysqlIO.sendCommand(MysqlIO.java:1989)\r\n	at com.mysql.jdbc.MysqlIO.sqlQueryDirect(MysqlIO.java:2150)\r\n	at com.mysql.jdbc.ConnectionImpl.execSQL(ConnectionImpl.java:2626)\r\n	at com.mysql.jdbc.PreparedStatement.executeInternal(PreparedStatement.java:2119)\r\n	at com.mysql.jdbc.PreparedStatement.execute(PreparedStatement.java:1362)\r\n	at org.apache.commons.dbcp.DelegatingPreparedStatement.execute(DelegatingPreparedStatement.java:172)\r\n	at org.apache.commons.dbcp.DelegatingPreparedStatement.execute(DelegatingPreparedStatement.java:172)\r\n	at org.apache.ibatis.executor.statement.PreparedStatementHandler.update(PreparedStatementHandler.java:42)\r\n	at org.apache.ibatis.executor.statement.RoutingStatementHandler.update(RoutingStatementHandler.java:66)\r\n	at org.apache.ibatis.executor.SimpleExecutor.doUpdate(SimpleExecutor.java:45)\r\n	at org.apache.ibatis.executor.BaseExecutor.update(BaseExecutor.java:108)\r\n	at org.apache.ibatis.executor.CachingExecutor.update(CachingExecutor.java:67)\r\n	at org.apache.ibatis.session.defaults.DefaultSqlSession.update(DefaultSqlSession.java:145)\r\n	at org.apache.ibatis.session.defaults.DefaultSqlSession.insert(DefaultSqlSession.java:134)\r\n	at sun.reflect.GeneratedMethodAccessor174.invoke(Unknown Source)\r\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source)\r\n	at java.lang.reflect.Method.invoke(Unknown Source)\r\n	at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:355)\r\n	... 64 more', 'admin', '192.168.2.73', '2014-10-17 17:37:53'),
	(57, '面板包信息维护', '配置面板包', '1', '面板包配置失败，org.springframework.dao.DeadlockLoserDataAccessException: \r\n### Error updating database.  Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLTransactionRollbackException: Deadlock found when trying to get lock; try restarting transaction\r\n### The error may involve com.ysten.local.bss.panel.repository.mapper.PanelPackageMapMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLTransactionRollbackException: Deadlock found when trying to get lock; try restarting transaction\n; SQL []; Deadlock found when trying to get lock; try restarting transaction; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLTransactionRollbackException: Deadlock found when trying to get lock; try restarting transaction\r\n	at org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator.doTranslate(SQLErrorCodeSQLExceptionTranslator.java:269)\r\n	at org.springframework.jdbc.support.AbstractFallbackSQLExceptionTranslator.translate(AbstractFallbackSQLExceptionTranslator.java:72)\r\n	at org.mybatis.spring.MyBatisExceptionTranslator.translateExceptionIfPossible(MyBatisExceptionTranslator.java:71)\r\n	at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:365)\r\n	at com.sun.proxy.$Proxy7.insert(Unknown Source)\r\n	at org.mybatis.spring.SqlSessionTemplate.insert(SqlSessionTemplate.java:237)\r\n	at org.apache.ibatis.binding.MapperMethod.execute(MapperMethod.java:79)\r\n	at org.apache.ibatis.binding.MapperProxy.invoke(MapperProxy.java:40)\r\n	at com.sun.proxy.$Proxy25.insert(Unknown Source)\r\n	at com.ysten.local.bss.panel.repository.impl.PanelPackageMapRepository.savePanelPackageMap(PanelPackageMapRepository.java:34)\r\n	at com.ysten.local.bss.panel.repository.impl.PanelPackageMapRepository$$FastClassByCGLIB$$d9c35e09.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:698)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:150)\r\n	at com.ysten.cache.spring.interceptor.CacheInterceptor.invoke(CacheInterceptor.java:84)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:172)\r\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:631)\r\n	at com.ysten.local.bss.panel.repository.impl.PanelPackageMapRepository$$EnhancerByCGLIB$$adc24351.savePanelPackageMap(<generated>)\r\n	at com.ysten.local.bss.panel.service.impl.PanelPackageMapService.updatePanelPackageConfig(PanelPackageMapService.java:117)\r\n	at com.ysten.local.bss.panel.service.impl.PanelPackageMapService$$FastClassByCGLIB$$903b8a3f.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:698)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:150)\r\n	at org.springframework.transaction.interceptor.TransactionInterceptor$1.proceedWithInvocation(TransactionInterceptor.java:96)\r\n	at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:260)\r\n	at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:94)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:172)\r\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:631)\r\n	at com.ysten.local.bss.panel.service.impl.PanelPackageMapService$$EnhancerByCGLIB$$bda59774.updatePanelPackageConfig(<generated>)\r\n	at com.ysten.local.bss.panel.web.controller.PanelPackageController.configPanelPackage(PanelPackageController.java:255)\r\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n	at sun.reflect.NativeMethodAccessorImpl.invoke(Unknown Source)\r\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source)\r\n	at java.lang.reflect.Method.invoke(Unknown Source)\r\n	at org.springframework.web.method.support.InvocableHandlerMethod.invoke(InvocableHandlerMethod.java:219)\r\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:132)\r\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:104)\r\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandleMethod(RequestMappingHandlerAdapter.java:745)\r\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:686)\r\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:80)\r\n	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:925)\r\n	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:856)\r\n	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:936)\r\n	at org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:838)\r\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:727)\r\n	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:812)\r\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:820)\r\n	at org.mortbay.jetty.servlet.ServletHolder.handle(ServletHolder.java:511)\r\n	at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1221)\r\n	at com.ysten.utils.filter.AccessFilter.doFilter(AccessFilter.java:55)\r\n	at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1212)\r\n	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:88)\r\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\r\n	at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1212)\r\n	at org.mortbay.jetty.servlet.ServletHandler.handle(ServletHandler.java:399)\r\n	at org.mortbay.jetty.security.SecurityHandler.handle(SecurityHandler.java:216)\r\n	at org.mortbay.jetty.servlet.SessionHandler.handle(SessionHandler.java:182)\r\n	at org.mortbay.jetty.handler.ContextHandler.handle(ContextHandler.java:766)\r\n	at org.mortbay.jetty.webapp.WebAppContext.handle(WebAppContext.java:450)\r\n	at org.mortbay.jetty.handler.HandlerWrapper.handle(HandlerWrapper.java:152)\r\n	at org.mortbay.jetty.Server.handle(Server.java:326)\r\n	at org.mortbay.jetty.HttpConnection.handleRequest(HttpConnection.java:542)\r\n	at org.mortbay.jetty.HttpConnection$RequestHandler.headerComplete(HttpConnection.java:928)\r\n	at org.mortbay.jetty.HttpParser.parseNext(HttpParser.java:549)\r\n	at org.mortbay.jetty.HttpParser.parseAvailable(HttpParser.java:212)\r\n	at org.mortbay.jetty.HttpConnection.handle(HttpConnection.java:404)\r\n	at org.mortbay.io.nio.SelectChannelEndPoint.run(SelectChannelEndPoint.java:410)\r\n	at org.mortbay.thread.QueuedThreadPool$PoolThread.run(QueuedThreadPool.java:582)\r\nCaused by: com.mysql.jdbc.exceptions.jdbc4.MySQLTransactionRollbackException: Deadlock found when trying to get lock; try restarting transaction\r\n	at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)\r\n	at sun.reflect.NativeConstructorAccessorImpl.newInstance(Unknown Source)\r\n	at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(Unknown Source)\r\n	at java.lang.reflect.Constructor.newInstance(Unknown Source)\r\n	at com.mysql.jdbc.Util.handleNewInstance(Util.java:407)\r\n	at com.mysql.jdbc.Util.getInstance(Util.java:382)\r\n	at com.mysql.jdbc.SQLError.createSQLException(SQLError.java:1064)\r\n	at com.mysql.jdbc.MysqlIO.checkErrorPacket(MysqlIO.java:3603)\r\n	at com.mysql.jdbc.MysqlIO.checkErrorPacket(MysqlIO.java:3535)\r\n	at com.mysql.jdbc.MysqlIO.sendCommand(MysqlIO.java:1989)\r\n	at com.mysql.jdbc.MysqlIO.sqlQueryDirect(MysqlIO.java:2150)\r\n	at com.mysql.jdbc.ConnectionImpl.execSQL(ConnectionImpl.java:2626)\r\n	at com.mysql.jdbc.PreparedStatement.executeInternal(PreparedStatement.java:2119)\r\n	at com.mysql.jdbc.PreparedStatement.execute(PreparedStatement.java:1362)\r\n	at org.apache.commons.dbcp.DelegatingPreparedStatement.execute(DelegatingPreparedStatement.java:172)\r\n	at org.apache.commons.dbcp.DelegatingPreparedStatement.execute(DelegatingPreparedStatement.java:172)\r\n	at org.apache.ibatis.executor.statement.PreparedStatementHandler.update(PreparedStatementHandler.java:42)\r\n	at org.apache.ibatis.executor.statement.RoutingStatementHandler.update(RoutingStatementHandler.java:66)\r\n	at org.apache.ibatis.executor.SimpleExecutor.doUpdate(SimpleExecutor.java:45)\r\n	at org.apache.ibatis.executor.BaseExecutor.update(BaseExecutor.java:108)\r\n	at org.apache.ibatis.executor.CachingExecutor.update(CachingExecutor.java:67)\r\n	at org.apache.ibatis.session.defaults.DefaultSqlSession.update(DefaultSqlSession.java:145)\r\n	at org.apache.ibatis.session.defaults.DefaultSqlSession.insert(DefaultSqlSession.java:134)\r\n	at sun.reflect.GeneratedMethodAccessor174.invoke(Unknown Source)\r\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source)\r\n	at java.lang.reflect.Method.invoke(Unknown Source)\r\n	at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:355)\r\n	... 64 more', 'admin', '192.168.2.73', '2014-10-17 17:37:54'),
	(58, '面板包信息维护', '配置面板包', '1', '面板包配置失败，org.springframework.dao.DeadlockLoserDataAccessException: \r\n### Error updating database.  Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLTransactionRollbackException: Deadlock found when trying to get lock; try restarting transaction\r\n### The error may involve com.ysten.local.bss.panel.repository.mapper.PanelPackageMapMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLTransactionRollbackException: Deadlock found when trying to get lock; try restarting transaction\n; SQL []; Deadlock found when trying to get lock; try restarting transaction; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLTransactionRollbackException: Deadlock found when trying to get lock; try restarting transaction\r\n	at org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator.doTranslate(SQLErrorCodeSQLExceptionTranslator.java:269)\r\n	at org.springframework.jdbc.support.AbstractFallbackSQLExceptionTranslator.translate(AbstractFallbackSQLExceptionTranslator.java:72)\r\n	at org.mybatis.spring.MyBatisExceptionTranslator.translateExceptionIfPossible(MyBatisExceptionTranslator.java:71)\r\n	at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:365)\r\n	at com.sun.proxy.$Proxy7.insert(Unknown Source)\r\n	at org.mybatis.spring.SqlSessionTemplate.insert(SqlSessionTemplate.java:237)\r\n	at org.apache.ibatis.binding.MapperMethod.execute(MapperMethod.java:79)\r\n	at org.apache.ibatis.binding.MapperProxy.invoke(MapperProxy.java:40)\r\n	at com.sun.proxy.$Proxy25.insert(Unknown Source)\r\n	at com.ysten.local.bss.panel.repository.impl.PanelPackageMapRepository.savePanelPackageMap(PanelPackageMapRepository.java:34)\r\n	at com.ysten.local.bss.panel.repository.impl.PanelPackageMapRepository$$FastClassByCGLIB$$d9c35e09.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:698)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:150)\r\n	at com.ysten.cache.spring.interceptor.CacheInterceptor.invoke(CacheInterceptor.java:84)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:172)\r\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:631)\r\n	at com.ysten.local.bss.panel.repository.impl.PanelPackageMapRepository$$EnhancerByCGLIB$$adc24351.savePanelPackageMap(<generated>)\r\n	at com.ysten.local.bss.panel.service.impl.PanelPackageMapService.updatePanelPackageConfig(PanelPackageMapService.java:117)\r\n	at com.ysten.local.bss.panel.service.impl.PanelPackageMapService$$FastClassByCGLIB$$903b8a3f.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:698)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:150)\r\n	at org.springframework.transaction.interceptor.TransactionInterceptor$1.proceedWithInvocation(TransactionInterceptor.java:96)\r\n	at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:260)\r\n	at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:94)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:172)\r\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:631)\r\n	at com.ysten.local.bss.panel.service.impl.PanelPackageMapService$$EnhancerByCGLIB$$bda59774.updatePanelPackageConfig(<generated>)\r\n	at com.ysten.local.bss.panel.web.controller.PanelPackageController.configPanelPackage(PanelPackageController.java:255)\r\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n	at sun.reflect.NativeMethodAccessorImpl.invoke(Unknown Source)\r\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source)\r\n	at java.lang.reflect.Method.invoke(Unknown Source)\r\n	at org.springframework.web.method.support.InvocableHandlerMethod.invoke(InvocableHandlerMethod.java:219)\r\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:132)\r\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:104)\r\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandleMethod(RequestMappingHandlerAdapter.java:745)\r\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:686)\r\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:80)\r\n	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:925)\r\n	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:856)\r\n	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:936)\r\n	at org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:838)\r\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:727)\r\n	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:812)\r\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:820)\r\n	at org.mortbay.jetty.servlet.ServletHolder.handle(ServletHolder.java:511)\r\n	at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1221)\r\n	at com.ysten.utils.filter.AccessFilter.doFilter(AccessFilter.java:55)\r\n	at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1212)\r\n	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:88)\r\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\r\n	at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1212)\r\n	at org.mortbay.jetty.servlet.ServletHandler.handle(ServletHandler.java:399)\r\n	at org.mortbay.jetty.security.SecurityHandler.handle(SecurityHandler.java:216)\r\n	at org.mortbay.jetty.servlet.SessionHandler.handle(SessionHandler.java:182)\r\n	at org.mortbay.jetty.handler.ContextHandler.handle(ContextHandler.java:766)\r\n	at org.mortbay.jetty.webapp.WebAppContext.handle(WebAppContext.java:450)\r\n	at org.mortbay.jetty.handler.HandlerWrapper.handle(HandlerWrapper.java:152)\r\n	at org.mortbay.jetty.Server.handle(Server.java:326)\r\n	at org.mortbay.jetty.HttpConnection.handleRequest(HttpConnection.java:542)\r\n	at org.mortbay.jetty.HttpConnection$RequestHandler.headerComplete(HttpConnection.java:928)\r\n	at org.mortbay.jetty.HttpParser.parseNext(HttpParser.java:549)\r\n	at org.mortbay.jetty.HttpParser.parseAvailable(HttpParser.java:212)\r\n	at org.mortbay.jetty.HttpConnection.handle(HttpConnection.java:404)\r\n	at org.mortbay.io.nio.SelectChannelEndPoint.run(SelectChannelEndPoint.java:410)\r\n	at org.mortbay.thread.QueuedThreadPool$PoolThread.run(QueuedThreadPool.java:582)\r\nCaused by: com.mysql.jdbc.exceptions.jdbc4.MySQLTransactionRollbackException: Deadlock found when trying to get lock; try restarting transaction\r\n	at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)\r\n	at sun.reflect.NativeConstructorAccessorImpl.newInstance(Unknown Source)\r\n	at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(Unknown Source)\r\n	at java.lang.reflect.Constructor.newInstance(Unknown Source)\r\n	at com.mysql.jdbc.Util.handleNewInstance(Util.java:407)\r\n	at com.mysql.jdbc.Util.getInstance(Util.java:382)\r\n	at com.mysql.jdbc.SQLError.createSQLException(SQLError.java:1064)\r\n	at com.mysql.jdbc.MysqlIO.checkErrorPacket(MysqlIO.java:3603)\r\n	at com.mysql.jdbc.MysqlIO.checkErrorPacket(MysqlIO.java:3535)\r\n	at com.mysql.jdbc.MysqlIO.sendCommand(MysqlIO.java:1989)\r\n	at com.mysql.jdbc.MysqlIO.sqlQueryDirect(MysqlIO.java:2150)\r\n	at com.mysql.jdbc.ConnectionImpl.execSQL(ConnectionImpl.java:2626)\r\n	at com.mysql.jdbc.PreparedStatement.executeInternal(PreparedStatement.java:2119)\r\n	at com.mysql.jdbc.PreparedStatement.execute(PreparedStatement.java:1362)\r\n	at org.apache.commons.dbcp.DelegatingPreparedStatement.execute(DelegatingPreparedStatement.java:172)\r\n	at org.apache.commons.dbcp.DelegatingPreparedStatement.execute(DelegatingPreparedStatement.java:172)\r\n	at org.apache.ibatis.executor.statement.PreparedStatementHandler.update(PreparedStatementHandler.java:42)\r\n	at org.apache.ibatis.executor.statement.RoutingStatementHandler.update(RoutingStatementHandler.java:66)\r\n	at org.apache.ibatis.executor.SimpleExecutor.doUpdate(SimpleExecutor.java:45)\r\n	at org.apache.ibatis.executor.BaseExecutor.update(BaseExecutor.java:108)\r\n	at org.apache.ibatis.executor.CachingExecutor.update(CachingExecutor.java:67)\r\n	at org.apache.ibatis.session.defaults.DefaultSqlSession.update(DefaultSqlSession.java:145)\r\n	at org.apache.ibatis.session.defaults.DefaultSqlSession.insert(DefaultSqlSession.java:134)\r\n	at sun.reflect.GeneratedMethodAccessor174.invoke(Unknown Source)\r\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source)\r\n	at java.lang.reflect.Method.invoke(Unknown Source)\r\n	at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:355)\r\n	... 64 more', 'admin', '192.168.2.73', '2014-10-17 17:37:54'),
	(59, '面板包信息维护', '配置面板包', '1', '面板包配置失败，org.springframework.dao.DeadlockLoserDataAccessException: \r\n### Error updating database.  Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLTransactionRollbackException: Deadlock found when trying to get lock; try restarting transaction\r\n### The error may involve com.ysten.local.bss.panel.repository.mapper.PanelPackageMapMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLTransactionRollbackException: Deadlock found when trying to get lock; try restarting transaction\n; SQL []; Deadlock found when trying to get lock; try restarting transaction; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLTransactionRollbackException: Deadlock found when trying to get lock; try restarting transaction\r\n	at org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator.doTranslate(SQLErrorCodeSQLExceptionTranslator.java:269)\r\n	at org.springframework.jdbc.support.AbstractFallbackSQLExceptionTranslator.translate(AbstractFallbackSQLExceptionTranslator.java:72)\r\n	at org.mybatis.spring.MyBatisExceptionTranslator.translateExceptionIfPossible(MyBatisExceptionTranslator.java:71)\r\n	at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:365)\r\n	at com.sun.proxy.$Proxy7.insert(Unknown Source)\r\n	at org.mybatis.spring.SqlSessionTemplate.insert(SqlSessionTemplate.java:237)\r\n	at org.apache.ibatis.binding.MapperMethod.execute(MapperMethod.java:79)\r\n	at org.apache.ibatis.binding.MapperProxy.invoke(MapperProxy.java:40)\r\n	at com.sun.proxy.$Proxy25.insert(Unknown Source)\r\n	at com.ysten.local.bss.panel.repository.impl.PanelPackageMapRepository.savePanelPackageMap(PanelPackageMapRepository.java:34)\r\n	at com.ysten.local.bss.panel.repository.impl.PanelPackageMapRepository$$FastClassByCGLIB$$d9c35e09.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:698)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:150)\r\n	at com.ysten.cache.spring.interceptor.CacheInterceptor.invoke(CacheInterceptor.java:84)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:172)\r\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:631)\r\n	at com.ysten.local.bss.panel.repository.impl.PanelPackageMapRepository$$EnhancerByCGLIB$$adc24351.savePanelPackageMap(<generated>)\r\n	at com.ysten.local.bss.panel.service.impl.PanelPackageMapService.updatePanelPackageConfig(PanelPackageMapService.java:117)\r\n	at com.ysten.local.bss.panel.service.impl.PanelPackageMapService$$FastClassByCGLIB$$903b8a3f.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:698)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:150)\r\n	at org.springframework.transaction.interceptor.TransactionInterceptor$1.proceedWithInvocation(TransactionInterceptor.java:96)\r\n	at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:260)\r\n	at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:94)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:172)\r\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:631)\r\n	at com.ysten.local.bss.panel.service.impl.PanelPackageMapService$$EnhancerByCGLIB$$bda59774.updatePanelPackageConfig(<generated>)\r\n	at com.ysten.local.bss.panel.web.controller.PanelPackageController.configPanelPackage(PanelPackageController.java:255)\r\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n	at sun.reflect.NativeMethodAccessorImpl.invoke(Unknown Source)\r\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source)\r\n	at java.lang.reflect.Method.invoke(Unknown Source)\r\n	at org.springframework.web.method.support.InvocableHandlerMethod.invoke(InvocableHandlerMethod.java:219)\r\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:132)\r\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:104)\r\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandleMethod(RequestMappingHandlerAdapter.java:745)\r\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:686)\r\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:80)\r\n	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:925)\r\n	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:856)\r\n	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:936)\r\n	at org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:838)\r\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:727)\r\n	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:812)\r\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:820)\r\n	at org.mortbay.jetty.servlet.ServletHolder.handle(ServletHolder.java:511)\r\n	at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1221)\r\n	at com.ysten.utils.filter.AccessFilter.doFilter(AccessFilter.java:55)\r\n	at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1212)\r\n	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:88)\r\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\r\n	at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1212)\r\n	at org.mortbay.jetty.servlet.ServletHandler.handle(ServletHandler.java:399)\r\n	at org.mortbay.jetty.security.SecurityHandler.handle(SecurityHandler.java:216)\r\n	at org.mortbay.jetty.servlet.SessionHandler.handle(SessionHandler.java:182)\r\n	at org.mortbay.jetty.handler.ContextHandler.handle(ContextHandler.java:766)\r\n	at org.mortbay.jetty.webapp.WebAppContext.handle(WebAppContext.java:450)\r\n	at org.mortbay.jetty.handler.HandlerWrapper.handle(HandlerWrapper.java:152)\r\n	at org.mortbay.jetty.Server.handle(Server.java:326)\r\n	at org.mortbay.jetty.HttpConnection.handleRequest(HttpConnection.java:542)\r\n	at org.mortbay.jetty.HttpConnection$RequestHandler.headerComplete(HttpConnection.java:928)\r\n	at org.mortbay.jetty.HttpParser.parseNext(HttpParser.java:549)\r\n	at org.mortbay.jetty.HttpParser.parseAvailable(HttpParser.java:212)\r\n	at org.mortbay.jetty.HttpConnection.handle(HttpConnection.java:404)\r\n	at org.mortbay.io.nio.SelectChannelEndPoint.run(SelectChannelEndPoint.java:410)\r\n	at org.mortbay.thread.QueuedThreadPool$PoolThread.run(QueuedThreadPool.java:582)\r\nCaused by: com.mysql.jdbc.exceptions.jdbc4.MySQLTransactionRollbackException: Deadlock found when trying to get lock; try restarting transaction\r\n	at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)\r\n	at sun.reflect.NativeConstructorAccessorImpl.newInstance(Unknown Source)\r\n	at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(Unknown Source)\r\n	at java.lang.reflect.Constructor.newInstance(Unknown Source)\r\n	at com.mysql.jdbc.Util.handleNewInstance(Util.java:407)\r\n	at com.mysql.jdbc.Util.getInstance(Util.java:382)\r\n	at com.mysql.jdbc.SQLError.createSQLException(SQLError.java:1064)\r\n	at com.mysql.jdbc.MysqlIO.checkErrorPacket(MysqlIO.java:3603)\r\n	at com.mysql.jdbc.MysqlIO.checkErrorPacket(MysqlIO.java:3535)\r\n	at com.mysql.jdbc.MysqlIO.sendCommand(MysqlIO.java:1989)\r\n	at com.mysql.jdbc.MysqlIO.sqlQueryDirect(MysqlIO.java:2150)\r\n	at com.mysql.jdbc.ConnectionImpl.execSQL(ConnectionImpl.java:2626)\r\n	at com.mysql.jdbc.PreparedStatement.executeInternal(PreparedStatement.java:2119)\r\n	at com.mysql.jdbc.PreparedStatement.execute(PreparedStatement.java:1362)\r\n	at org.apache.commons.dbcp.DelegatingPreparedStatement.execute(DelegatingPreparedStatement.java:172)\r\n	at org.apache.commons.dbcp.DelegatingPreparedStatement.execute(DelegatingPreparedStatement.java:172)\r\n	at org.apache.ibatis.executor.statement.PreparedStatementHandler.update(PreparedStatementHandler.java:42)\r\n	at org.apache.ibatis.executor.statement.RoutingStatementHandler.update(RoutingStatementHandler.java:66)\r\n	at org.apache.ibatis.executor.SimpleExecutor.doUpdate(SimpleExecutor.java:45)\r\n	at org.apache.ibatis.executor.BaseExecutor.update(BaseExecutor.java:108)\r\n	at org.apache.ibatis.executor.CachingExecutor.update(CachingExecutor.java:67)\r\n	at org.apache.ibatis.session.defaults.DefaultSqlSession.update(DefaultSqlSession.java:145)\r\n	at org.apache.ibatis.session.defaults.DefaultSqlSession.insert(DefaultSqlSession.java:134)\r\n	at sun.reflect.GeneratedMethodAccessor174.invoke(Unknown Source)\r\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source)\r\n	at java.lang.reflect.Method.invoke(Unknown Source)\r\n	at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:355)\r\n	... 64 more', 'admin', '192.168.2.73', '2014-10-17 17:37:54'),
	(60, '面板包信息维护', '配置面板包', '1', '面板包配置成功', 'admin', '192.168.2.73', '2014-10-17 17:37:54'),
	(61, '管理系统登陆', '系统登陆', '1', '用户 admin 在2014-10-17 19:34:50登陆系统成功', 'admin', '0:0:0:0:0:0:0:1', '2014-10-17 19:34:51'),
	(62, '管理系统登陆', '系统登陆', '1', '用户 admin 在2014-10-17 19:45:51登陆系统成功', 'admin', '192.168.2.73', '2014-10-17 19:45:52'),
	(63, '管理系统登陆', '系统登陆', '1', '用户 admin 在2014-10-17 19:49:38登陆系统成功', 'admin', '0:0:0:0:0:0:0:1', '2014-10-17 19:49:39'),
	(64, '背景轮换信息维护', '新增', '9', '添加背景轮换信息成功!;背景轮换:{"id":9,"name":"root","url":"http://58.214.17.75:8081/pic/big1.png","blurUrl":"http://58.214.17.75:8081/pic/big6.png","createDate":"2014-10-17 19:50:15","isDefault":0,"state":"USEABLE","loopTime":null,"deviceLoopTime":null,"groupLoopTime":null,"userLoopTime":null,"userGroupLoopTime":null,"deviceGroupIds":null,"deviceCodes":null,"userGroupIds":null,"userIds":null,"updateDate":"2014-10-17 19:50:15","type":null}', 'admin', '0:0:0:0:0:0:0:1', '2014-10-17 19:50:15'),
	(65, '背景轮换信息维护', '新增', '10', '添加背景轮换信息成功!;背景轮换:{"id":10,"name":"root","url":"http://58.214.17.75:8081/pic/big1.png","blurUrl":"http://58.214.17.75:8081/pic/big6.png","createDate":"2014-10-17 19:50:29","isDefault":0,"state":"USEABLE","loopTime":null,"deviceLoopTime":null,"groupLoopTime":null,"userLoopTime":null,"userGroupLoopTime":null,"deviceGroupIds":null,"deviceCodes":null,"userGroupIds":null,"userIds":null,"updateDate":"2014-10-17 19:50:29","type":null}', 'admin', '0:0:0:0:0:0:0:1', '2014-10-17 19:50:29'),
	(66, '背景轮换信息维护', '新增', '11', '添加背景轮换信息成功!;背景轮换:{"id":11,"name":"root","url":"http://58.214.17.75:8081/pic/big1.png","blurUrl":"http://58.214.17.75:8081/pic/big6.png","createDate":"2014-10-17 19:50:30","isDefault":0,"state":"USEABLE","loopTime":null,"deviceLoopTime":null,"groupLoopTime":null,"userLoopTime":null,"userGroupLoopTime":null,"deviceGroupIds":null,"deviceCodes":null,"userGroupIds":null,"userIds":null,"updateDate":"2014-10-17 19:50:30","type":null}', 'admin', '0:0:0:0:0:0:0:1', '2014-10-17 19:50:30'),
	(67, '背景轮换信息维护', '新增', '12', '添加背景轮换信息成功!;背景轮换:{"id":12,"name":"root","url":"http://58.214.17.75:8081/pic/big1.png","blurUrl":"http://58.214.17.75:8081/pic/big6.png","createDate":"2014-10-17 19:50:30","isDefault":0,"state":"USEABLE","loopTime":null,"deviceLoopTime":null,"groupLoopTime":null,"userLoopTime":null,"userGroupLoopTime":null,"deviceGroupIds":null,"deviceCodes":null,"userGroupIds":null,"userIds":null,"updateDate":"2014-10-17 19:50:30","type":null}', 'admin', '0:0:0:0:0:0:0:1', '2014-10-17 19:50:30'),
	(68, '背景轮换信息维护', '新增', '13', '添加背景轮换信息成功!;背景轮换:{"id":13,"name":"admin","url":"http://58.214.17.75:8081/pic/big10.png","blurUrl":"http://58.214.17.75:8081/pic/big10.png","createDate":"2014-10-17 19:51:37","isDefault":0,"state":"USEABLE","loopTime":null,"deviceLoopTime":null,"groupLoopTime":null,"userLoopTime":null,"userGroupLoopTime":null,"deviceGroupIds":null,"deviceCodes":null,"userGroupIds":null,"userIds":null,"updateDate":"2014-10-17 19:51:37","type":null}', 'admin', '0:0:0:0:0:0:0:1', '2014-10-17 19:51:37'),
	(69, '背景轮换信息维护', '新增', '14', '添加背景轮换信息成功!;背景轮换:{"id":14,"name":"admin","url":"http://58.214.17.75:8081/pic/big5.png","blurUrl":"http://58.214.17.75:8081/pic/big10.png","createDate":"2014-10-17 19:52:02","isDefault":0,"state":"USEABLE","loopTime":null,"deviceLoopTime":null,"groupLoopTime":null,"userLoopTime":null,"userGroupLoopTime":null,"deviceGroupIds":null,"deviceCodes":null,"userGroupIds":null,"userIds":null,"updateDate":"2014-10-17 19:52:02","type":null}', 'admin', '0:0:0:0:0:0:0:1', '2014-10-17 19:52:02'),
	(70, '背景轮换信息维护', '新增', '15', '添加背景轮换信息成功!;背景轮换:{"id":15,"name":"admin","url":"http://58.214.17.75:8081/pic/big5.png","blurUrl":"http://58.214.17.75:8081/pic/big10.png","createDate":"2014-10-17 19:52:03","isDefault":0,"state":"USEABLE","loopTime":null,"deviceLoopTime":null,"groupLoopTime":null,"userLoopTime":null,"userGroupLoopTime":null,"deviceGroupIds":null,"deviceCodes":null,"userGroupIds":null,"userIds":null,"updateDate":"2014-10-17 19:52:03","type":null}', 'admin', '0:0:0:0:0:0:0:1', '2014-10-17 19:52:03'),
	(71, '背景轮换信息维护', '新增', '16', '添加背景轮换信息成功!;背景轮换:{"id":16,"name":"admin","url":"http://58.214.17.75:8081/pic/big5.png","blurUrl":"http://58.214.17.75:8081/pic/big10.png","createDate":"2014-10-17 19:52:04","isDefault":0,"state":"USEABLE","loopTime":null,"deviceLoopTime":null,"groupLoopTime":null,"userLoopTime":null,"userGroupLoopTime":null,"deviceGroupIds":null,"deviceCodes":null,"userGroupIds":null,"userIds":null,"updateDate":"2014-10-17 19:52:04","type":null}', 'admin', '0:0:0:0:0:0:0:1', '2014-10-17 19:52:04'),
	(72, '背景轮换信息维护', '新增', '17', '添加背景轮换信息成功!;背景轮换:{"id":17,"name":"admin","url":"http://58.214.17.75:8081/pic/big5.png","blurUrl":"http://58.214.17.75:8081/pic/big10.png","createDate":"2014-10-17 19:52:04","isDefault":0,"state":"USEABLE","loopTime":null,"deviceLoopTime":null,"groupLoopTime":null,"userLoopTime":null,"userGroupLoopTime":null,"deviceGroupIds":null,"deviceCodes":null,"userGroupIds":null,"userIds":null,"updateDate":"2014-10-17 19:52:04","type":null}', 'admin', '0:0:0:0:0:0:0:1', '2014-10-17 19:52:04'),
	(73, '背景轮换信息维护', '新增', '18', '添加背景轮换信息成功!;背景轮换:{"id":18,"name":"admin","url":"http://58.214.17.75:8081/pic/big5.png","blurUrl":"http://58.214.17.75:8081/pic/big10.png","createDate":"2014-10-17 19:52:05","isDefault":0,"state":"USEABLE","loopTime":null,"deviceLoopTime":null,"groupLoopTime":null,"userLoopTime":null,"userGroupLoopTime":null,"deviceGroupIds":null,"deviceCodes":null,"userGroupIds":null,"userIds":null,"updateDate":"2014-10-17 19:52:05","type":null}', 'admin', '0:0:0:0:0:0:0:1', '2014-10-17 19:52:05'),
	(74, '背景轮换信息维护', '新增', '19', '添加背景轮换信息成功!;背景轮换:{"id":19,"name":"admin","url":"http://58.214.17.75:8081/pic/big5.png","blurUrl":"http://58.214.17.75:8081/pic/big10.png","createDate":"2014-10-17 19:52:06","isDefault":0,"state":"USEABLE","loopTime":null,"deviceLoopTime":null,"groupLoopTime":null,"userLoopTime":null,"userGroupLoopTime":null,"deviceGroupIds":null,"deviceCodes":null,"userGroupIds":null,"userIds":null,"updateDate":"2014-10-17 19:52:06","type":null}', 'admin', '0:0:0:0:0:0:0:1', '2014-10-17 19:52:06'),
	(75, '背景轮换信息维护', '新增', '20', '添加背景轮换信息成功!;背景轮换:{"id":20,"name":"admin","url":"http://58.214.17.75:8081/pic/big5.png","blurUrl":"http://58.214.17.75:8081/pic/big10.png","createDate":"2014-10-17 19:52:06","isDefault":0,"state":"USEABLE","loopTime":null,"deviceLoopTime":null,"groupLoopTime":null,"userLoopTime":null,"userGroupLoopTime":null,"deviceGroupIds":null,"deviceCodes":null,"userGroupIds":null,"userIds":null,"updateDate":"2014-10-17 19:52:06","type":null}', 'admin', '0:0:0:0:0:0:0:1', '2014-10-17 19:52:06'),
	(76, '背景轮换信息维护', '新增', '21', '添加背景轮换信息成功!;背景轮换:{"id":21,"name":"admin","url":"http://58.214.17.75:8081/pic/big5.png","blurUrl":"http://58.214.17.75:8081/pic/big10.png","createDate":"2014-10-17 19:52:07","isDefault":0,"state":"USEABLE","loopTime":null,"deviceLoopTime":null,"groupLoopTime":null,"userLoopTime":null,"userGroupLoopTime":null,"deviceGroupIds":null,"deviceCodes":null,"userGroupIds":null,"userIds":null,"updateDate":"2014-10-17 19:52:07","type":null}', 'admin', '0:0:0:0:0:0:0:1', '2014-10-17 19:52:07'),
	(77, '背景轮换信息维护', '新增', '22', '添加背景轮换信息成功!;背景轮换:{"id":22,"name":"admin","url":"http://58.214.17.75:8081/pic/big5.png","blurUrl":"http://58.214.17.75:8081/pic/big10.png","createDate":"2014-10-17 19:52:07","isDefault":0,"state":"USEABLE","loopTime":null,"deviceLoopTime":null,"groupLoopTime":null,"userLoopTime":null,"userGroupLoopTime":null,"deviceGroupIds":null,"deviceCodes":null,"userGroupIds":null,"userIds":null,"updateDate":"2014-10-17 19:52:07","type":null}', 'admin', '0:0:0:0:0:0:0:1', '2014-10-17 19:52:07'),
	(78, '背景轮换信息维护', '新增', '23', '添加背景轮换信息成功!;背景轮换:{"id":23,"name":"admin","url":"http://58.214.17.75:8081/pic/big5.png","blurUrl":"http://58.214.17.75:8081/pic/big10.png","createDate":"2014-10-17 19:52:07","isDefault":0,"state":"USEABLE","loopTime":null,"deviceLoopTime":null,"groupLoopTime":null,"userLoopTime":null,"userGroupLoopTime":null,"deviceGroupIds":null,"deviceCodes":null,"userGroupIds":null,"userIds":null,"updateDate":"2014-10-17 19:52:07","type":null}', 'admin', '0:0:0:0:0:0:0:1', '2014-10-17 19:52:07'),
	(79, '背景轮换信息维护', '新增', '24', '添加背景轮换信息成功!;背景轮换:{"id":24,"name":"admin","url":"http://58.214.17.75:8081/pic/big5.png","blurUrl":"http://58.214.17.75:8081/pic/big10.png","createDate":"2014-10-17 19:52:53","isDefault":0,"state":"USEABLE","loopTime":null,"deviceLoopTime":null,"groupLoopTime":null,"userLoopTime":null,"userGroupLoopTime":null,"deviceGroupIds":null,"deviceCodes":null,"userGroupIds":null,"userIds":null,"updateDate":"2014-10-17 19:52:54","type":null}', 'admin', '0:0:0:0:0:0:0:1', '2014-10-17 19:53:06'),
	(80, '背景轮换信息维护', '新增', '25', '添加背景轮换信息成功!;背景轮换:{"id":25,"name":"admin","url":"http://58.214.17.75:8081/pic/big5.png","blurUrl":"http://58.214.17.75:8081/pic/big10.png","createDate":"2014-10-17 19:53:11","isDefault":0,"state":"USEABLE","loopTime":null,"deviceLoopTime":null,"groupLoopTime":null,"userLoopTime":null,"userGroupLoopTime":null,"deviceGroupIds":null,"deviceCodes":null,"userGroupIds":null,"userIds":null,"updateDate":"2014-10-17 19:53:11","type":null}', 'admin', '0:0:0:0:0:0:0:1', '2014-10-17 19:53:12'),
	(81, '背景轮换信息维护', '新增', '26', '添加背景轮换信息成功!;背景轮换:{"id":26,"name":"admin","url":"http://58.214.17.75:8081/pic/big5.png","blurUrl":"http://58.214.17.75:8081/pic/big10.png","createDate":"2014-10-17 19:53:12","isDefault":0,"state":"USEABLE","loopTime":null,"deviceLoopTime":null,"groupLoopTime":null,"userLoopTime":null,"userGroupLoopTime":null,"deviceGroupIds":null,"deviceCodes":null,"userGroupIds":null,"userIds":null,"updateDate":"2014-10-17 19:53:12","type":null}', 'admin', '0:0:0:0:0:0:0:1', '2014-10-17 19:53:12'),
	(82, '管理系统登陆', '系统登陆', '1', '用户 admin 在2014-10-17 20:04:35登陆系统成功', 'admin', '0:0:0:0:0:0:0:1', '2014-10-17 20:04:35'),
	(83, '管理系统登陆', '系统登陆', '1', '用户 admin 在2014-10-17 20:07:09登陆系统成功', 'admin', '0:0:0:0:0:0:0:1', '2014-10-17 20:07:09'),
	(84, '管理系统登陆', '系统登陆', '1', '用户 admin 在2014-10-17 20:08:59登陆系统成功', 'admin', '0:0:0:0:0:0:0:1', '2014-10-17 20:09:00'),
	(85, '开机动画信息维护', '新增', '', '新增开机动画信息异常;null', 'admin', '0:0:0:0:0:0:0:1', '2014-10-17 20:09:22'),
	(86, '管理系统登陆', '系统登陆', '1', '用户 admin 在2014-10-17 20:10:39登陆系统成功', 'admin', '0:0:0:0:0:0:0:1', '2014-10-17 20:10:39'),
	(87, '管理系统登陆', '系统登陆', '1', '用户 admin 在2014-10-17 20:29:18登陆系统成功', 'admin', '192.168.2.73', '2014-10-17 20:29:19'),
	(88, '管理系统登陆', '系统登陆', '1', '用户 admin 在2014-10-17 20:34:16登陆系统成功', 'admin', '192.168.2.73', '2014-10-17 20:34:19'),
	(89, '面板信息维护', '新增', '2', '新增面板成功!', 'admin', '192.168.2.73', '2014-10-17 20:35:36'),
	(90, '面板项项信息维护', '新增', '3', '新增面板项成功!', 'admin', '192.168.2.73', '2014-10-17 20:37:35'),
	(91, '面板项项信息维护', '新增', '4', '新增面板项成功!', 'admin', '192.168.2.73', '2014-10-17 20:38:53'),
	(92, '面板项项信息维护', '新增', '5', '新增面板项成功!', 'admin', '192.168.2.73', '2014-10-17 20:38:54'),
	(93, '面板项项信息维护', '新增', '6', '新增面板项成功!', 'admin', '192.168.2.73', '2014-10-17 20:38:55'),
	(94, '面板项项信息维护', '新增', '7', '新增面板项成功!', 'admin', '192.168.2.73', '2014-10-17 20:38:56'),
	(95, '管理系统登陆', '系统登陆', '1', '用户 admin 在2014-10-17 21:02:50登陆系统成功', 'admin', '192.168.2.73', '2014-10-17 21:02:53'),
	(96, '导航信息维护', '修改', '2', '修改导航信息成功！修改后导航 标题：aa;导航类型：2;动作类型:OpenUrl动作地址:aa;序号:2', 'admin', '192.168.2.73', '2014-10-17 21:03:58'),
	(97, '导航信息维护', '修改', '2', '修改导航信息成功！修改后导航 标题：aa;导航类型：2;动作类型:OpenUrl动作地址:aa;序号:2', 'admin', '192.168.2.73', '2014-10-17 21:04:19'),
	(98, '面板包信息维护', '面板包配置-移除面板', '1', '移除面板成功!', 'admin', '192.168.2.73', '2014-10-17 21:05:04'),
	(99, '管理系统登陆', '系统登陆', '1', '用户 admin 在2014-10-17 21:06:31登陆系统成功', 'admin', '0:0:0:0:0:0:0:1', '2014-10-17 21:06:33');
/*!40000 ALTER TABLE `bss_operate_log` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_operator 结构
CREATE TABLE IF NOT EXISTS `bss_operator` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `email` varchar(32) DEFAULT NULL COMMENT '用户邮箱地址',
  `login_name` varchar(32) DEFAULT NULL COMMENT '登陆的用户名',
  `display_name` varchar(32) DEFAULT NULL COMMENT '用户名',
  `state` varchar(32) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL COMMENT '密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='系统用户表，记录用户的相关信息';

-- 正在导出表  bims_panel_jsyd_jt.bss_operator 的数据：~2 rows (大约)
DELETE FROM `bss_operator`;
/*!40000 ALTER TABLE `bss_operator` DISABLE KEYS */;
INSERT INTO `bss_operator` (`id`, `email`, `login_name`, `display_name`, `state`, `password`) VALUES
	(1, 'admin@ysten.com', 'admin', '系统管理员', 'NORMAL', '21232f297a57a5a743894a0e4a801fc3'),
	(2, 'guoyi@ysten.com', 'guoyi', 'guoyi', 'NORMAL', 'e10adc3949ba59abbe56e057f20f883e');
/*!40000 ALTER TABLE `bss_operator` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_operator_role_map 结构
CREATE TABLE IF NOT EXISTS `bss_operator_role_map` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `operator_id` bigint(19) DEFAULT NULL COMMENT '用户id',
  `role_id` bigint(19) DEFAULT NULL COMMENT '角色d',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='用户角色表，定义用户所拥有的角色';

-- 正在导出表  bims_panel_jsyd_jt.bss_operator_role_map 的数据：~2 rows (大约)
DELETE FROM `bss_operator_role_map`;
/*!40000 ALTER TABLE `bss_operator_role_map` DISABLE KEYS */;
INSERT INTO `bss_operator_role_map` (`id`, `operator_id`, `role_id`) VALUES
	(3, 2, 1),
	(4, 1, 1);
/*!40000 ALTER TABLE `bss_operator_role_map` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_panel 结构
CREATE TABLE IF NOT EXISTS `bss_panel` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `panel_id` bigint(19) DEFAULT NULL,
  `panel_mark` varchar(32) DEFAULT NULL,
  `template_id` bigint(19) DEFAULT NULL,
  `panel_name` varchar(60) DEFAULT NULL,
  `panel_title` varchar(60) DEFAULT NULL,
  `panel_style` varchar(60) DEFAULT NULL,
  `panel_icon` varchar(128) DEFAULT NULL,
  `link_url` varchar(128) DEFAULT NULL,
  `img_url` varchar(128) DEFAULT NULL,
  `online_status` int(2) DEFAULT NULL,
  `online_status_time` datetime DEFAULT NULL,
  `status` int(2) DEFAULT NULL,
  `opr_userid` bigint(19) DEFAULT NULL,
  `epg_1_data` text,
  `epg_1_style` text,
  `epg_2_data` text,
  `epg_2_style` text,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `epg_panel_id` bigint(19) DEFAULT NULL,
  `epg_template_id` bigint(19) DEFAULT NULL,
  `big_img` varchar(256) DEFAULT NULL,
  `small_img` varchar(256) DEFAULT NULL,
  `is_custom` int(2) DEFAULT NULL,
  `ref_panel_id` bigint(19) DEFAULT NULL,
  `district_code` varchar(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_panel 的数据：~0 rows (大约)
DELETE FROM `bss_panel`;
/*!40000 ALTER TABLE `bss_panel` DISABLE KEYS */;
INSERT INTO `bss_panel` (`id`, `panel_id`, `panel_mark`, `template_id`, `panel_name`, `panel_title`, `panel_style`, `panel_icon`, `link_url`, `img_url`, `online_status`, `online_status_time`, `status`, `opr_userid`, `epg_1_data`, `epg_1_style`, `epg_2_data`, `epg_2_style`, `create_time`, `update_time`, `epg_panel_id`, `epg_template_id`, `big_img`, `small_img`, `is_custom`, `ref_panel_id`, `district_code`) VALUES
	(1, NULL, NULL, 1, '4box_首页', '首页', '无', 'http://58.214.17.75:8081/pic/small9.jpg', '', 'http://58.214.17.75:8081/pic/big10.png', 99, NULL, NULL, 1, NULL, NULL, NULL, NULL, '2014-10-17 15:42:38', '2014-10-17 16:53:41', NULL, NULL, 'http://58.214.17.75:8081/pic/dafegnmian5.png', 'http://58.214.17.75:8081/pic/dafegnmian6.png', 1, NULL, '320000'),
	(2, NULL, NULL, 1, 'sfdd', 'df', 'dfd', 'http://127.0.0.1:8080/pic/big5.png', '', 'http://127.0.0.1:8080/pic/dafegnmian.png', 0, NULL, NULL, 1, NULL, NULL, NULL, NULL, '2014-10-17 20:35:32', '2014-10-17 20:35:33', NULL, NULL, 'http://127.0.0.1:8080/pic/big10.png', 'http://127.0.0.1:8080/pic/big9.png', 1, NULL, '320000');
/*!40000 ALTER TABLE `bss_panel` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_panel_img_box 结构
CREATE TABLE IF NOT EXISTS `bss_panel_img_box` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `img_box_id` bigint(19) NOT NULL,
  `item_id` bigint(19) DEFAULT NULL,
  `title` varchar(256) DEFAULT NULL,
  `img_url` varchar(1000) NOT NULL,
  `action_url` varchar(1000) DEFAULT NULL,
  `progrom_id` bigint(19) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_panel_img_box 的数据：~0 rows (大约)
DELETE FROM `bss_panel_img_box`;
/*!40000 ALTER TABLE `bss_panel_img_box` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_panel_img_box` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_panel_item 结构
CREATE TABLE IF NOT EXISTS `bss_panel_item` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `panel_item_id` bigint(19) DEFAULT NULL,
  `name` varchar(256) DEFAULT NULL,
  `title` varchar(60) DEFAULT NULL,
  `title_comment` varchar(1024) DEFAULT NULL,
  `action_type` int(3) DEFAULT NULL,
  `action_url` varchar(512) DEFAULT NULL,
  `image_url` varchar(512) DEFAULT NULL,
  `image_disturl` varchar(255) DEFAULT NULL,
  `video_url` varchar(255) DEFAULT NULL,
  `content_id` bigint(19) DEFAULT NULL,
  `content` varchar(1024) DEFAULT NULL,
  `content_type` varchar(64) DEFAULT NULL,
  `ref_item_id` bigint(19) DEFAULT NULL,
  `panelitem_parentid` bigint(19) DEFAULT NULL,
  `auto_run` int(1) DEFAULT NULL,
  `focus_run` int(1) DEFAULT NULL,
  `show_title` int(1) DEFAULT NULL,
  `animation_run` int(1) DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  `opr_userid` bigint(19) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `epg_panelitem_id` bigint(19) DEFAULT NULL,
  `epg_content_id` bigint(19) DEFAULT NULL,
  `epg_ref_item_id` bigint(19) DEFAULT NULL,
  `epg_panelitem_parentid` bigint(19) DEFAULT NULL,
  `has_sub_item` int(1) DEFAULT NULL,
  `auto_play` int(1) DEFAULT NULL,
  `install_url` varchar(255) DEFAULT NULL,
  `online_status` int(1) DEFAULT '99',
  `district_code` varchar(6) DEFAULT NULL,
  `app_enter_url` varchar(255) DEFAULT NULL,
  `item_content_type` varchar(64) DEFAULT NULL,
  `category_id` bigint(19) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_panel_item 的数据：~0 rows (大约)
DELETE FROM `bss_panel_item`;
/*!40000 ALTER TABLE `bss_panel_item` DISABLE KEYS */;
INSERT INTO `bss_panel_item` (`id`, `panel_item_id`, `name`, `title`, `title_comment`, `action_type`, `action_url`, `image_url`, `image_disturl`, `video_url`, `content_id`, `content`, `content_type`, `ref_item_id`, `panelitem_parentid`, `auto_run`, `focus_run`, `show_title`, `animation_run`, `status`, `opr_userid`, `create_time`, `update_time`, `epg_panelitem_id`, `epg_content_id`, `epg_ref_item_id`, `epg_panelitem_parentid`, `has_sub_item`, `auto_play`, `install_url`, `online_status`, `district_code`, `app_enter_url`, `item_content_type`, `category_id`) VALUES
	(1, NULL, 'box-2', 'box-2', '', 1, '', 'http://58.214.17.75:8081/pic/big1.png', NULL, '', NULL, '', 'icon', NULL, NULL, 0, 1, 0, 0, NULL, 1, '2014-10-17 17:22:34', '2014-10-17 17:27:56', NULL, NULL, NULL, NULL, 0, 0, '', 99, '320000', NULL, NULL, NULL),
	(2, NULL, 'box-1', 'box-1', '', 1, '', 'http://58.214.17.75:8081/pic/small1.jpg', NULL, '', NULL, '', 'icon', NULL, NULL, 0, 1, 0, 0, NULL, 1, '2014-10-17 17:22:37', '2014-10-17 17:27:39', NULL, NULL, NULL, NULL, 0, 0, '', 99, '320000', NULL, NULL, NULL),
	(3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, '2014-10-17 20:37:34', '2014-10-17 20:37:34', NULL, NULL, NULL, NULL, NULL, 0, NULL, 99, '320000', NULL, NULL, NULL),
	(4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, '2014-10-17 20:38:52', '2014-10-17 20:38:52', NULL, NULL, NULL, NULL, NULL, 0, NULL, 99, '320000', NULL, NULL, NULL),
	(5, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, '2014-10-17 20:38:53', '2014-10-17 20:38:53', NULL, NULL, NULL, NULL, NULL, 0, NULL, 99, '320000', NULL, NULL, NULL),
	(6, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, '2014-10-17 20:38:54', '2014-10-17 20:38:54', NULL, NULL, NULL, NULL, NULL, 0, NULL, 99, '320000', NULL, NULL, NULL),
	(7, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, '2014-10-17 20:38:55', '2014-10-17 20:38:55', NULL, NULL, NULL, NULL, NULL, 0, NULL, 99, '320000', NULL, NULL, NULL);
/*!40000 ALTER TABLE `bss_panel_item` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_panel_nav_box 结构
CREATE TABLE IF NOT EXISTS `bss_panel_nav_box` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `nav_box_id` bigint(19) NOT NULL,
  `title` varchar(256) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_panel_nav_box 的数据：~0 rows (大约)
DELETE FROM `bss_panel_nav_box`;
/*!40000 ALTER TABLE `bss_panel_nav_box` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_panel_nav_box` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_panel_nav_define 结构
CREATE TABLE IF NOT EXISTS `bss_panel_nav_define` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `title` varchar(256) DEFAULT NULL,
  `nav_type` int(11) DEFAULT NULL,
  `nav_name` varchar(256) DEFAULT NULL,
  `title_comment` varchar(1024) DEFAULT NULL,
  `action_type` varchar(20) DEFAULT NULL,
  `action_url` varchar(256) DEFAULT NULL,
  `image_url` varchar(256) DEFAULT NULL,
  `image_disturl` varchar(256) DEFAULT NULL,
  `show_title` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `opr_userid` bigint(20) DEFAULT NULL,
  `sort_num` int(11) DEFAULT NULL,
  `epg_nav_id` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `online_status` int(1) DEFAULT '99',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_panel_nav_define 的数据：~0 rows (大约)
DELETE FROM `bss_panel_nav_define`;
/*!40000 ALTER TABLE `bss_panel_nav_define` DISABLE KEYS */;
INSERT INTO `bss_panel_nav_define` (`id`, `title`, `nav_type`, `nav_name`, `title_comment`, `action_type`, `action_url`, `image_url`, `image_disturl`, `show_title`, `status`, `opr_userid`, `sort_num`, `epg_nav_id`, `create_time`, `online_status`, `update_time`) VALUES
	(1, 'dd', 1, 'dd', '', 'OpenUrl', 'dd', 'dd', 'dd', 1, 0, 1, 1, NULL, '2014-10-17 17:35:31', 99, '2014-10-17 17:35:31'),
	(2, 'aa', 2, 'aa', 'aa', 'OpenUrl', 'aa', 'http://127.0.0.1:8080/pic/big1.png', 'http://127.0.0.1:8080/pic/big10.png', 1, 0, 1, 2, NULL, '2014-10-17 17:35:45', 99, '2014-10-17 21:04:17');
/*!40000 ALTER TABLE `bss_panel_nav_define` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_panel_package 结构
CREATE TABLE IF NOT EXISTS `bss_panel_package` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `epg_package_id` bigint(19) DEFAULT NULL,
  `package_name` varchar(60) DEFAULT NULL,
  `package_desc` varchar(1028) DEFAULT NULL,
  `is_default` int(1) DEFAULT NULL,
  `opr_userid` bigint(19) DEFAULT NULL,
  `platform_id` bigint(19) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `nav_datas` text,
  `epg_style1` text,
  `epg_style2` text,
  `online_status` int(1) DEFAULT '99',
  `package_type` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_panel_package 的数据：~0 rows (大约)
DELETE FROM `bss_panel_package`;
/*!40000 ALTER TABLE `bss_panel_package` DISABLE KEYS */;
INSERT INTO `bss_panel_package` (`id`, `epg_package_id`, `package_name`, `package_desc`, `is_default`, `opr_userid`, `platform_id`, `create_time`, `update_time`, `nav_datas`, `epg_style1`, `epg_style2`, `online_status`, `package_type`) VALUES
	(1, NULL, 'package1', 'package1', 1, 1, NULL, '2014-10-17 17:30:06', NULL, NULL, NULL, NULL, 99, 3);
/*!40000 ALTER TABLE `bss_panel_package` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_panel_package_device_map 结构
CREATE TABLE IF NOT EXISTS `bss_panel_package_device_map` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `panel_package_id` bigint(19) DEFAULT NULL COMMENT '背景图片id',
  `ysten_id` varchar(32) DEFAULT NULL,
  `device_group_id` bigint(19) DEFAULT NULL COMMENT '设备分组id',
  `create_date` datetime NOT NULL,
  `type` varchar(32) DEFAULT NULL COMMENT 'GROUP;DEVICE',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_panel_package_device_map 的数据：~0 rows (大约)
DELETE FROM `bss_panel_package_device_map`;
/*!40000 ALTER TABLE `bss_panel_package_device_map` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_panel_package_device_map` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_panel_package_panel_map 结构
CREATE TABLE IF NOT EXISTS `bss_panel_package_panel_map` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `panel_id` bigint(19) DEFAULT NULL,
  `package_id` bigint(19) DEFAULT NULL,
  `nav_id` varchar(256) DEFAULT NULL,
  `sort_num` int(11) DEFAULT NULL,
  `epg_rel_id` bigint(19) DEFAULT NULL,
  `epg_panel_id` bigint(19) DEFAULT NULL,
  `epg_package_id` bigint(19) DEFAULT NULL,
  `epg_nav_id` varchar(256) DEFAULT NULL,
  `panel_logo` varchar(256) DEFAULT NULL,
  `display` varchar(32) DEFAULT 'true',
  `is_lock` varchar(32) DEFAULT 'false',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_panel_package_panel_map 的数据：~0 rows (大约)
DELETE FROM `bss_panel_package_panel_map`;
/*!40000 ALTER TABLE `bss_panel_package_panel_map` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_panel_package_panel_map` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_panel_package_user_map 结构
CREATE TABLE IF NOT EXISTS `bss_panel_package_user_map` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `panel_package_id` bigint(19) NOT NULL COMMENT '背景图片id',
  `code` varchar(32) DEFAULT NULL,
  `user_group_id` bigint(19) DEFAULT NULL COMMENT '设备分组id',
  `create_date` datetime NOT NULL,
  `type` varchar(32) DEFAULT NULL COMMENT 'GROUP;DEVICE',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_panel_package_user_map 的数据：~0 rows (大约)
DELETE FROM `bss_panel_package_user_map`;
/*!40000 ALTER TABLE `bss_panel_package_user_map` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_panel_package_user_map` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_panel_panel_item_map 结构
CREATE TABLE IF NOT EXISTS `bss_panel_panel_item_map` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `panel_id` bigint(19) DEFAULT NULL,
  `panel_item_id` bigint(19) DEFAULT NULL,
  `sort_num` int(11) DEFAULT NULL,
  `rel_type` int(3) DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  `opr_userid` bigint(19) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `epg_panel_id` bigint(19) DEFAULT NULL,
  `epg_panelItem_id` bigint(19) DEFAULT NULL,
  `epg_rel_id` int(19) DEFAULT NULL,
  `district_code` varchar(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_panel_panel_item_map 的数据：~0 rows (大约)
DELETE FROM `bss_panel_panel_item_map`;
/*!40000 ALTER TABLE `bss_panel_panel_item_map` DISABLE KEYS */;
INSERT INTO `bss_panel_panel_item_map` (`id`, `panel_id`, `panel_item_id`, `sort_num`, `rel_type`, `status`, `opr_userid`, `create_date`, `update_date`, `epg_panel_id`, `epg_panelItem_id`, `epg_rel_id`, `district_code`) VALUES
	(5, 1, 2, NULL, NULL, NULL, NULL, '2014-10-17 17:29:29', '2014-10-17 17:29:29', NULL, NULL, NULL, '320000'),
	(6, 1, 2, NULL, NULL, NULL, NULL, '2014-10-17 17:29:30', '2014-10-17 17:29:30', NULL, NULL, NULL, '320000'),
	(7, 1, 2, NULL, NULL, NULL, NULL, '2014-10-17 17:29:30', '2014-10-17 17:29:30', NULL, NULL, NULL, '320000'),
	(8, 1, 1, NULL, NULL, NULL, NULL, '2014-10-17 17:29:30', '2014-10-17 17:29:30', NULL, NULL, NULL, '320000'),
	(9, 1, 2, NULL, NULL, NULL, NULL, '2014-10-17 17:29:30', '2014-10-17 17:29:30', NULL, NULL, NULL, '320000');
/*!40000 ALTER TABLE `bss_panel_panel_item_map` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_panel_text_box 结构
CREATE TABLE IF NOT EXISTS `bss_panel_text_box` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `text_box_id` bigint(19) NOT NULL,
  `title` varchar(256) NOT NULL,
  `is_new` tinyint(2) NOT NULL,
  `progrom_id` bigint(19) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_panel_text_box 的数据：~0 rows (大约)
DELETE FROM `bss_panel_text_box`;
/*!40000 ALTER TABLE `bss_panel_text_box` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_panel_text_box` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_ppv_info 结构
CREATE TABLE IF NOT EXISTS `bss_ppv_info` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `ppv_id` bigint(19) DEFAULT NULL,
  `ppv_name` varchar(32) DEFAULT NULL,
  `alias` varchar(32) DEFAULT NULL,
  `price` bigint(19) DEFAULT NULL,
  `currency` varchar(32) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `valid_date` datetime DEFAULT NULL,
  `expire_date` datetime DEFAULT NULL,
  `source` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_ppv_info 的数据：~0 rows (大约)
DELETE FROM `bss_ppv_info`;
/*!40000 ALTER TABLE `bss_ppv_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_ppv_info` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_ppv_pp_relation 结构
CREATE TABLE IF NOT EXISTS `bss_ppv_pp_relation` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `ppv_id` bigint(19) DEFAULT NULL,
  `product_id` varchar(32) DEFAULT NULL,
  `push_date` datetime DEFAULT NULL,
  `push_state` varchar(32) DEFAULT NULL,
  `call_back_date` datetime DEFAULT NULL,
  `pp_pk_id` bigint(19) DEFAULT NULL,
  `ppv_pk_id` bigint(19) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_ppv_pp_relation 的数据：~0 rows (大约)
DELETE FROM `bss_ppv_pp_relation`;
/*!40000 ALTER TABLE `bss_ppv_pp_relation` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_ppv_pp_relation` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_ppv_pp_relation_history 结构
CREATE TABLE IF NOT EXISTS `bss_ppv_pp_relation_history` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `action` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `old_id` bigint(19) NOT NULL,
  `old_ppv_id` bigint(19) DEFAULT NULL,
  `old_product_id` varchar(32) DEFAULT NULL,
  `old_push_date` datetime DEFAULT NULL,
  `old_push_state` varchar(32) DEFAULT NULL,
  `old_call_back_date` datetime DEFAULT NULL,
  `old_pp_pk_id` bigint(19) DEFAULT NULL,
  `old_ppv_pk_id` bigint(19) DEFAULT NULL,
  `old_create_date` datetime DEFAULT NULL,
  `new_id` bigint(19) DEFAULT NULL,
  `new_ppv_id` bigint(19) DEFAULT NULL,
  `new_product_id` varchar(32) DEFAULT NULL,
  `new_push_date` datetime DEFAULT NULL,
  `new_push_state` varchar(32) DEFAULT NULL,
  `new_call_back_date` datetime DEFAULT NULL,
  `new_pp_pk_id` bigint(19) DEFAULT NULL,
  `new_ppv_pk_id` bigint(19) DEFAULT NULL,
  `new_create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_ppv_pp_relation_history 的数据：~0 rows (大约)
DELETE FROM `bss_ppv_pp_relation_history`;
/*!40000 ALTER TABLE `bss_ppv_pp_relation_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_ppv_pp_relation_history` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_pp_info 结构
CREATE TABLE IF NOT EXISTS `bss_pp_info` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `product_id` varchar(32) DEFAULT NULL,
  `service_id` varchar(32) DEFAULT NULL,
  `ott_product_id` varchar(32) DEFAULT NULL,
  `product_name` varchar(100) DEFAULT NULL,
  `product_type` varchar(32) DEFAULT NULL,
  `description` varchar(5000) DEFAULT NULL,
  `price` bigint(19) DEFAULT NULL,
  `pay_price` bigint(19) DEFAULT NULL,
  `currency` varchar(32) DEFAULT NULL,
  `order_cycle_num` bigint(19) DEFAULT NULL,
  `order_cycle_unit` bigint(12) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `valid_date` datetime DEFAULT NULL,
  `expire_date` datetime DEFAULT NULL,
  `source` varchar(100) DEFAULT NULL,
  `push_date` datetime DEFAULT NULL,
  `push_state` varchar(32) DEFAULT NULL,
  `call_back_date` datetime DEFAULT NULL,
  `img_addr` varchar(500) DEFAULT NULL,
  `packageId` bigint(19) DEFAULT NULL COMMENT '产品包id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_pp_info 的数据：~0 rows (大约)
DELETE FROM `bss_pp_info`;
/*!40000 ALTER TABLE `bss_pp_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_pp_info` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_preview_item 结构
CREATE TABLE IF NOT EXISTS `bss_preview_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `template_id` bigint(20) DEFAULT NULL,
  `left` int(11) DEFAULT NULL,
  `top` int(11) DEFAULT NULL,
  `width` int(11) DEFAULT NULL,
  `height` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `epg_ioid` bigint(20) DEFAULT NULL,
  `epg_template_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_preview_item 的数据：~0 rows (大约)
DELETE FROM `bss_preview_item`;
/*!40000 ALTER TABLE `bss_preview_item` DISABLE KEYS */;
INSERT INTO `bss_preview_item` (`id`, `template_id`, `left`, `top`, `width`, `height`, `type`, `sort`, `epg_ioid`, `epg_template_id`) VALUES
	(1, 1, 0, 0, 2, 1, NULL, 1, NULL, NULL),
	(2, 1, 0, 1, 2, 2, NULL, 2, NULL, NULL),
	(3, 1, 2, 0, 3, 1, NULL, 3, NULL, NULL),
	(4, 1, 2, 1, 3, 2, NULL, 4, NULL, NULL);
/*!40000 ALTER TABLE `bss_preview_item` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_preview_item_data 结构
CREATE TABLE IF NOT EXISTS `bss_preview_item_data` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `template_id` bigint(19) DEFAULT NULL,
  `left` int(2) DEFAULT NULL,
  `top` int(2) DEFAULT NULL,
  `width` int(2) DEFAULT NULL,
  `height` int(2) DEFAULT NULL,
  `type` int(1) DEFAULT NULL COMMENT '1.image\\r\\n            2.icon\\r\\n            3.',
  `sort` int(11) DEFAULT NULL,
  `content_type` int(11) DEFAULT NULL COMMENT '1.专题\r\n            2.推荐',
  `content_id` bigint(19) DEFAULT NULL,
  `content_item_id` bigint(19) DEFAULT NULL,
  `epg_ioid` bigint(19) DEFAULT NULL,
  `epg_template_id` bigint(19) DEFAULT NULL,
  `epg_content_id` bigint(19) DEFAULT NULL,
  `epg_content_item_id` bigint(19) DEFAULT NULL,
  `district_code` varchar(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_preview_item_data 的数据：~0 rows (大约)
DELETE FROM `bss_preview_item_data`;
/*!40000 ALTER TABLE `bss_preview_item_data` DISABLE KEYS */;
INSERT INTO `bss_preview_item_data` (`id`, `template_id`, `left`, `top`, `width`, `height`, `type`, `sort`, `content_type`, `content_id`, `content_item_id`, `epg_ioid`, `epg_template_id`, `epg_content_id`, `epg_content_item_id`, `district_code`) VALUES
	(1, 1, 0, 0, 2, 1, NULL, 1, NULL, 1, 2, NULL, NULL, NULL, NULL, '320000'),
	(2, 1, 0, 1, 2, 2, NULL, 2, NULL, 1, 2, NULL, NULL, NULL, NULL, '320000'),
	(3, 1, 2, 0, 3, 1, NULL, 3, NULL, 1, 1, NULL, NULL, NULL, NULL, '320000'),
	(4, 1, 2, 1, 3, 2, NULL, 4, NULL, 1, 1, NULL, NULL, NULL, NULL, '320000'),
	(5, 1, 0, 0, 2, 1, NULL, 1, NULL, 2, NULL, NULL, NULL, NULL, NULL, '320000'),
	(6, 1, 0, 1, 2, 2, NULL, 2, NULL, 2, NULL, NULL, NULL, NULL, NULL, '320000'),
	(7, 1, 2, 0, 3, 1, NULL, 3, NULL, 2, NULL, NULL, NULL, NULL, NULL, '320000'),
	(8, 1, 2, 1, 3, 2, NULL, 4, NULL, 2, NULL, NULL, NULL, NULL, NULL, '320000');
/*!40000 ALTER TABLE `bss_preview_item_data` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_preview_template 结构
CREATE TABLE IF NOT EXISTS `bss_preview_template` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `template_id` bigint(19) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `type` int(1) DEFAULT NULL,
  `platform_id` bigint(19) DEFAULT NULL,
  `contain_ps` int(1) DEFAULT NULL,
  `epg_template_id` bigint(19) DEFAULT NULL,
  `district_code` varchar(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_preview_template 的数据：~0 rows (大约)
DELETE FROM `bss_preview_template`;
/*!40000 ALTER TABLE `bss_preview_template` DISABLE KEYS */;
INSERT INTO `bss_preview_template` (`id`, `template_id`, `name`, `description`, `type`, `platform_id`, `contain_ps`, `epg_template_id`, `district_code`) VALUES
	(1, NULL, '4box', '', NULL, NULL, 0, NULL, '320000');
/*!40000 ALTER TABLE `bss_preview_template` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_price_atom 结构
CREATE TABLE IF NOT EXISTS `bss_price_atom` (
  `ID` bigint(19) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `price` int(10) DEFAULT NULL,
  `discount` decimal(4,2) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_price_atom 的数据：~0 rows (大约)
DELETE FROM `bss_price_atom`;
/*!40000 ALTER TABLE `bss_price_atom` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_price_atom` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_product_content 结构
CREATE TABLE IF NOT EXISTS `bss_product_content` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  `outter_code` bigint(19) DEFAULT NULL,
  `ott_outter_code` varchar(32) DEFAULT NULL,
  `outter_type` varchar(30) DEFAULT NULL,
  `cp_code` varchar(32) DEFAULT NULL,
  `state` varchar(32) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `publish_date` datetime DEFAULT NULL,
  `create_operator_name` varchar(32) DEFAULT NULL,
  `ppv_id` bigint(19) DEFAULT NULL,
  `create_operator_id` int(10) DEFAULT NULL,
  `description` varchar(5000) DEFAULT NULL,
  `score` float(4,1) DEFAULT NULL,
  `small_poster_addr` varchar(1024) DEFAULT NULL,
  `poster` varchar(1024) DEFAULT NULL,
  `big_poster_addr` varchar(1024) DEFAULT NULL,
  `push_date` datetime DEFAULT NULL,
  `push_state` varchar(32) DEFAULT NULL,
  `call_back_date` datetime DEFAULT NULL,
  `relation_push_date` datetime DEFAULT NULL,
  `relation_push_state` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_product_content 的数据：~0 rows (大约)
DELETE FROM `bss_product_content`;
/*!40000 ALTER TABLE `bss_product_content` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_product_content` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_product_content_history 结构
CREATE TABLE IF NOT EXISTS `bss_product_content_history` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `create_date` datetime DEFAULT NULL,
  `action` varchar(50) DEFAULT NULL,
  `pc_id` bigint(19) DEFAULT NULL,
  `pc_name` varchar(64) DEFAULT NULL,
  `pc_outter_code` bigint(19) DEFAULT NULL,
  `pc_ott_outter_code` varchar(32) DEFAULT NULL,
  `pc_outter_type` varchar(30) DEFAULT NULL,
  `pc_cp_code` varchar(32) DEFAULT NULL,
  `pc_state` varchar(32) DEFAULT NULL,
  `pc_create_date` datetime DEFAULT NULL,
  `pc_publish_date` datetime DEFAULT NULL,
  `pc_create_operator_name` varchar(32) DEFAULT NULL,
  `pc_ppv_id` bigint(19) DEFAULT NULL,
  `pc_create_operator_id` int(10) DEFAULT NULL,
  `pc_description` varchar(5000) DEFAULT NULL,
  `pc_score` float(4,1) DEFAULT NULL,
  `pc_small_poster_addr` varchar(1024) DEFAULT NULL,
  `pc_poster` varchar(1024) DEFAULT NULL,
  `pc_big_poster_addr` varchar(1024) DEFAULT NULL,
  `pc_push_date` datetime DEFAULT NULL,
  `pc_push_state` varchar(32) DEFAULT NULL,
  `pc_call_back_date` datetime DEFAULT NULL,
  `pc_relation_push_date` datetime DEFAULT NULL,
  `pc_relation_push_state` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_product_content_history 的数据：~0 rows (大约)
DELETE FROM `bss_product_content_history`;
/*!40000 ALTER TABLE `bss_product_content_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_product_content_history` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_product_order 结构
CREATE TABLE IF NOT EXISTS `bss_product_order` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `code` varchar(32) DEFAULT NULL,
  `state` varchar(32) DEFAULT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  `customer_code` varchar(32) DEFAULT NULL,
  `discount_policy_id` bigint(19) DEFAULT NULL,
  `product_id` varchar(32) DEFAULT NULL,
  `product_name` varchar(32) DEFAULT NULL,
  `product_type` varchar(32) DEFAULT NULL,
  `ott_product_id` varchar(32) DEFAULT NULL,
  `content_id` bigint(19) DEFAULT NULL,
  `content_name` varchar(32) DEFAULT NULL,
  `bill_cycle_type` varchar(32) DEFAULT NULL,
  `source` varchar(32) DEFAULT NULL,
  `billcode` varchar(32) DEFAULT NULL,
  `price` int(10) DEFAULT NULL,
  `discount` int(11) DEFAULT NULL,
  `quantity` bigint(11) DEFAULT NULL,
  `order_date` datetime DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `outter_order_code` varchar(32) DEFAULT NULL,
  `outter_code` varchar(32) DEFAULT NULL,
  `pay_price` int(10) DEFAULT NULL,
  `device_code` varchar(32) DEFAULT NULL,
  `phone` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_product_order 的数据：~0 rows (大约)
DELETE FROM `bss_product_order`;
/*!40000 ALTER TABLE `bss_product_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_product_order` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_program_bitrate_record 结构
CREATE TABLE IF NOT EXISTS `bss_program_bitrate_record` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `program_rate_id` bigint(19) NOT NULL,
  `bitrate_id` bigint(11) DEFAULT NULL,
  `program_id` bigint(19) DEFAULT NULL,
  `src_file_name` varchar(50) DEFAULT NULL,
  `src_file_path` varchar(256) DEFAULT NULL,
  `covert_path` varchar(256) DEFAULT NULL,
  `covert_file_name` varchar(50) DEFAULT NULL,
  `drm_path` varchar(256) DEFAULT NULL,
  `drm_file_name` varchar(50) DEFAULT NULL,
  `md5` varchar(48) DEFAULT NULL,
  `convert_status` bigint(12) DEFAULT NULL,
  `drm_status` bigint(12) DEFAULT NULL,
  `need_drm` bigint(12) DEFAULT NULL,
  `uuid` varchar(32) DEFAULT NULL,
  `bitrate_status` bigint(12) DEFAULT NULL,
  `file_type` varchar(32) DEFAULT NULL COMMENT '文件的格式，例如ts,mp4',
  `file_size` bigint(12) DEFAULT NULL,
  `cp_code` varchar(30) DEFAULT NULL,
  `play_url` varchar(1000) DEFAULT NULL,
  `down_url` varchar(256) DEFAULT NULL,
  `data_provider` varchar(32) DEFAULT NULL COMMENT '数据提供商，第三方CP接口接入时存CPCODE，小米素材导入时存储小米标识，自有数据存储为CMS拼上PROGRAM_RATE_ID',
  `out_source_id` varchar(36) DEFAULT NULL COMMENT '外部数据标识，用来标识CP接口中的节目。第三方接口接入时会带唯一标识。自有数据存储CMS_',
  `definition_code` varchar(36) NOT NULL,
  `content_id` bigint(16) DEFAULT NULL,
  `record_date` timestamp NULL DEFAULT NULL,
  `log_status` varchar(36) DEFAULT NULL,
  `push_date` timestamp NULL DEFAULT NULL COMMENT '推送时间',
  `last_handle_date` timestamp NULL DEFAULT NULL COMMENT '最后处理时间',
  `push_status` varchar(36) DEFAULT NULL COMMENT '推送状态',
  `error_times` bigint(11) DEFAULT NULL,
  `task_id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_program_bitrate_record 的数据：~0 rows (大约)
DELETE FROM `bss_program_bitrate_record`;
/*!40000 ALTER TABLE `bss_program_bitrate_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_program_bitrate_record` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_program_record 结构
CREATE TABLE IF NOT EXISTS `bss_program_record` (
  `id` bigint(19) NOT NULL,
  `program_id` bigint(19) NOT NULL,
  `program_name` varchar(256) DEFAULT NULL,
  `english_name` varchar(256) DEFAULT NULL,
  `alias` varchar(256) DEFAULT NULL,
  `director` varchar(64) DEFAULT NULL,
  `leading_role` varchar(258) DEFAULT NULL,
  `years` varchar(10) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `plan_time` datetime DEFAULT NULL,
  `plan_user` varchar(32) DEFAULT NULL,
  `product_id` bigint(19) DEFAULT NULL,
  `update_user` varchar(32) DEFAULT NULL,
  `program_desc` text,
  `material_type_id` int(9) DEFAULT NULL,
  `material_class` varchar(64) DEFAULT NULL,
  `definition_code` varchar(64) DEFAULT NULL,
  `zone` varchar(168) DEFAULT NULL,
  `language_id` bigint(12) DEFAULT NULL,
  `set_number` int(10) DEFAULT NULL,
  `bitrate_id` bigint(12) DEFAULT NULL,
  `trial_dura` int(10) DEFAULT NULL,
  `off_line_time` datetime DEFAULT NULL,
  `program_status_id` varchar(64) DEFAULT NULL,
  `resolution_id` bigint(12) DEFAULT NULL,
  `program_length` int(10) DEFAULT NULL,
  `premiere_channel` varchar(256) DEFAULT NULL,
  `premiere_date` varchar(32) DEFAULT NULL,
  `sound_channel` varchar(32) DEFAULT NULL,
  `status_date` datetime DEFAULT NULL,
  `last_modify_date` datetime DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `poster` varchar(256) DEFAULT NULL,
  `sort_num` int(10) DEFAULT NULL,
  `encrypted_type` varchar(32) DEFAULT NULL,
  `addr_type` varchar(32) DEFAULT NULL,
  `priority` int(10) DEFAULT NULL,
  `org_url` varchar(256) DEFAULT NULL,
  `is3d` int(12) DEFAULT NULL,
  `out_source_id` varchar(36) DEFAULT NULL,
  `ppv_code` bigint(19) DEFAULT NULL,
  `cp_code` varchar(30) DEFAULT NULL,
  `start_time` int(10) DEFAULT NULL,
  `end_time` int(10) DEFAULT NULL,
  `special_request` varchar(2000) DEFAULT NULL,
  `off_reason` varchar(2000) DEFAULT NULL,
  `re_on_reason` varchar(2000) DEFAULT NULL,
  `del_reason` varchar(2000) DEFAULT NULL,
  `is_re_convert` int(12) DEFAULT NULL,
  `is_re_drm` int(12) DEFAULT NULL,
  `is_re_cdn` int(12) DEFAULT NULL,
  `back_reason` varchar(2000) DEFAULT NULL,
  `publish_platform_ids` varchar(30) DEFAULT NULL,
  `tag` varchar(256) DEFAULT NULL,
  `record_date` timestamp NULL DEFAULT NULL,
  `push_date` timestamp NULL DEFAULT NULL,
  `last_handle_date` timestamp NULL DEFAULT NULL,
  `push_status` varchar(36) DEFAULT NULL,
  `log_status` varchar(36) DEFAULT NULL,
  `error_times` bigint(11) DEFAULT NULL,
  `catalog_status` varchar(100) DEFAULT NULL,
  `reason` text,
  `is_return_url` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_program_record 的数据：~0 rows (大约)
DELETE FROM `bss_program_record`;
/*!40000 ALTER TABLE `bss_program_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_program_record` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_program_serial_price_change 结构
CREATE TABLE IF NOT EXISTS `bss_program_serial_price_change` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `product_id` bigint(20) NOT NULL,
  `outter_code` bigint(20) NOT NULL,
  `old_price` decimal(10,0) DEFAULT NULL,
  `new_price` decimal(10,0) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_program_serial_price_change 的数据：~0 rows (大约)
DELETE FROM `bss_program_serial_price_change`;
/*!40000 ALTER TABLE `bss_program_serial_price_change` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_program_serial_price_change` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_program_series_record 结构
CREATE TABLE IF NOT EXISTS `bss_program_series_record` (
  `id` bigint(19) NOT NULL,
  `program_series_id` bigint(19) NOT NULL,
  `program_series_name` varchar(256) DEFAULT NULL,
  `program_pinyin` varchar(256) DEFAULT NULL,
  `program_series_alias` varchar(256) DEFAULT NULL,
  `program_series_en_name` varchar(256) DEFAULT NULL,
  `poster` varchar(1024) DEFAULT NULL,
  `small_poster_addr` varchar(1024) DEFAULT NULL,
  `big_poster_addr` varchar(1024) DEFAULT NULL,
  `program_series_desc` text,
  `program_type` bigint(12) DEFAULT NULL,
  `program_class` varchar(64) DEFAULT NULL,
  `program_count` int(11) DEFAULT NULL,
  `program_total_count` int(11) DEFAULT NULL,
  `program_content_type` varchar(30) DEFAULT NULL,
  `tag` varchar(256) DEFAULT NULL,
  `definition_code` varchar(64) DEFAULT NULL,
  `director` varchar(256) DEFAULT NULL,
  `script_writer` varchar(256) DEFAULT NULL,
  `leading_role` varchar(258) DEFAULT NULL,
  `leading_role_pinyin` varchar(1024) DEFAULT NULL,
  `years` varchar(10) DEFAULT NULL,
  `language_id` int(11) DEFAULT NULL,
  `premiere_date` datetime DEFAULT NULL,
  `publish_date` datetime DEFAULT NULL,
  `premiere_channel` varchar(256) DEFAULT NULL,
  `sync_live_channel` varchar(256) DEFAULT NULL,
  `sort_type` varchar(30) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `status_time` datetime DEFAULT NULL,
  `last_modify_date` datetime DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `is3d` int(12) DEFAULT NULL,
  `time_length` int(11) DEFAULT NULL,
  `is_customer` int(12) DEFAULT NULL,
  `star_rating` bigint(12) DEFAULT NULL,
  `mask_description` varchar(512) DEFAULT NULL,
  `out_source_id` varchar(36) DEFAULT NULL,
  `zone` varchar(64) DEFAULT NULL,
  `cp_code` varchar(30) DEFAULT NULL,
  `copyright_start_date` date DEFAULT NULL,
  `copyright_end_date` date DEFAULT NULL,
  `copyright_id` bigint(19) DEFAULT NULL,
  `cr_class_id` bigint(19) DEFAULT NULL,
  `ppv_code` bigint(19) DEFAULT NULL,
  `data_provider` varchar(32) DEFAULT NULL,
  `off_reason` varchar(256) DEFAULT NULL,
  `re_on_reason` varchar(256) DEFAULT NULL,
  `del_reason` varchar(256) DEFAULT NULL,
  `program_type_id` int(9) DEFAULT NULL,
  `back_reason` varchar(256) DEFAULT NULL,
  `publish_platform_ids` varchar(256) DEFAULT NULL,
  `competition` varchar(64) DEFAULT NULL,
  `target_audience` varchar(64) DEFAULT NULL,
  `upload_poster` varchar(256) DEFAULT NULL,
  `record_date` timestamp NULL DEFAULT NULL,
  `push_date` timestamp NULL DEFAULT NULL,
  `last_handle_date` timestamp NULL DEFAULT NULL,
  `push_status` varchar(36) DEFAULT NULL,
  `log_status` varchar(36) DEFAULT NULL,
  `error_times` bigint(11) DEFAULT NULL,
  `catalog_status` varchar(50) DEFAULT NULL,
  `reason` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_program_series_record 的数据：~0 rows (大约)
DELETE FROM `bss_program_series_record`;
/*!40000 ALTER TABLE `bss_program_series_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_program_series_record` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_program_series_relation_record 结构
CREATE TABLE IF NOT EXISTS `bss_program_series_relation_record` (
  `id` bigint(19) NOT NULL,
  `rel_id` bigint(19) NOT NULL,
  `program_series_id` bigint(19) DEFAULT NULL,
  `program_id` bigint(19) DEFAULT NULL,
  `rel_time` datetime DEFAULT NULL,
  `rel_user` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(50) DEFAULT NULL,
  `taxis` int(4) DEFAULT NULL,
  `status` int(12) DEFAULT NULL,
  `last_modify_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_program_series_relation_record 的数据：0 rows
DELETE FROM `bss_program_series_relation_record`;
/*!40000 ALTER TABLE `bss_program_series_relation_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_program_series_relation_record` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_program_series_status 结构
CREATE TABLE IF NOT EXISTS `bss_program_series_status` (
  `status_value` varchar(32) NOT NULL,
  `status_name` varchar(32) DEFAULT NULL,
  `sort_num` int(10) DEFAULT NULL,
  PRIMARY KEY (`status_value`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_program_series_status 的数据：~0 rows (大约)
DELETE FROM `bss_program_series_status`;
/*!40000 ALTER TABLE `bss_program_series_status` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_program_series_status` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_role 结构
CREATE TABLE IF NOT EXISTS `bss_role` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `name` varchar(32) DEFAULT NULL COMMENT '角色名称',
  `description` varchar(5000) DEFAULT NULL,
  `create_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='角色表，记录系统中的角色信息';

-- 正在导出表  bims_panel_jsyd_jt.bss_role 的数据：~1 rows (大约)
DELETE FROM `bss_role`;
/*!40000 ALTER TABLE `bss_role` DISABLE KEYS */;
INSERT INTO `bss_role` (`id`, `name`, `description`, `create_date`) VALUES
	(1, '系统管理员', '系统管理员', '2014-04-21 13:35:54');
/*!40000 ALTER TABLE `bss_role` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_role_authority_map 结构
CREATE TABLE IF NOT EXISTS `bss_role_authority_map` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(19) DEFAULT NULL COMMENT '角色id',
  `authority_id` bigint(19) DEFAULT NULL COMMENT '权限id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1286978 DEFAULT CHARSET=utf8 COMMENT='记录角色所拥有的权限';

-- 正在导出表  bims_panel_jsyd_jt.bss_role_authority_map 的数据：~743 rows (大约)
DELETE FROM `bss_role_authority_map`;
/*!40000 ALTER TABLE `bss_role_authority_map` DISABLE KEYS */;
INSERT INTO `bss_role_authority_map` (`id`, `role_id`, `authority_id`) VALUES
	(1286190, 1, 272),
	(1286191, 1, 261),
	(1286192, 1, 178),
	(1286193, 1, 180),
	(1286194, 1, 1),
	(1286195, 1, 262),
	(1286196, 1, 260),
	(1286197, 1, 312),
	(1286198, 1, 273),
	(1286199, 1, 181),
	(1286200, 1, 5),
	(1286201, 1, 263),
	(1286202, 1, 314),
	(1286203, 1, 274),
	(1286204, 1, 261),
	(1286205, 1, 31),
	(1286206, 1, 326),
	(1286207, 1, 315),
	(1286208, 1, 312),
	(1286209, 1, 200),
	(1286210, 1, 179),
	(1286211, 1, 327),
	(1286212, 1, 316),
	(1286213, 1, 300),
	(1286214, 1, 180),
	(1286215, 1, 314),
	(1286216, 1, 266),
	(1286217, 1, 189),
	(1286218, 1, 185),
	(1286219, 1, 301),
	(1286220, 1, 179),
	(1286221, 1, 267),
	(1286222, 1, 181),
	(1286223, 1, 262),
	(1286224, 1, 190),
	(1286225, 1, 48),
	(1286226, 1, 276),
	(1286227, 1, 180),
	(1286228, 1, 326),
	(1286229, 1, 263),
	(1286230, 1, 191),
	(1286231, 1, 77),
	(1286232, 1, 277),
	(1286233, 1, 181),
	(1286234, 1, 327),
	(1286235, 1, 78),
	(1286236, 1, 315),
	(1286237, 1, 335),
	(1286238, 1, 278),
	(1286239, 1, 185),
	(1286240, 1, 326),
	(1286241, 1, 316),
	(1286242, 1, 147),
	(1286243, 1, 336),
	(1286244, 1, 327),
	(1286245, 1, 320),
	(1286246, 1, 262),
	(1286247, 1, 186),
	(1286248, 1, 189),
	(1286249, 1, 165),
	(1286250, 1, 321),
	(1286251, 1, 185),
	(1286252, 1, 263),
	(1286253, 1, 166),
	(1286254, 1, 190),
	(1286255, 1, 249),
	(1286256, 1, 148),
	(1286257, 1, 262),
	(1286258, 1, 315),
	(1286259, 1, 309),
	(1286260, 1, 167),
	(1286261, 1, 191),
	(1286262, 1, 263),
	(1286263, 1, 149),
	(1286264, 1, 316),
	(1286265, 1, 35),
	(1286266, 1, 335),
	(1286267, 1, 223),
	(1286268, 1, 315),
	(1286269, 1, 169),
	(1286270, 1, 189),
	(1286271, 1, 51),
	(1286272, 1, 336),
	(1286273, 1, 224),
	(1286274, 1, 316),
	(1286275, 1, 170),
	(1286276, 1, 190),
	(1286277, 1, 52),
	(1286278, 1, 165),
	(1286279, 1, 225),
	(1286280, 1, 189),
	(1286281, 1, 171),
	(1286282, 1, 191),
	(1286283, 1, 36),
	(1286284, 1, 199),
	(1286285, 1, 166),
	(1286286, 1, 190),
	(1286287, 1, 150),
	(1286288, 1, 335),
	(1286289, 1, 49),
	(1286290, 1, 272),
	(1286291, 1, 167),
	(1286292, 1, 172),
	(1286293, 1, 191),
	(1286294, 1, 336),
	(1286295, 1, 50),
	(1286296, 1, 223),
	(1286297, 1, 273),
	(1286298, 1, 335),
	(1286299, 1, 165),
	(1286300, 1, 173),
	(1286301, 1, 224),
	(1286302, 1, 274),
	(1286303, 1, 157),
	(1286304, 1, 336),
	(1286305, 1, 174),
	(1286306, 1, 166),
	(1286307, 1, 200),
	(1286308, 1, 175),
	(1286309, 1, 225),
	(1286310, 1, 165),
	(1286311, 1, 167),
	(1286312, 1, 220),
	(1286313, 1, 176),
	(1286314, 1, 199),
	(1286315, 1, 266),
	(1286316, 1, 166),
	(1286317, 1, 223),
	(1286318, 1, 264),
	(1286319, 1, 177),
	(1286320, 1, 272),
	(1286321, 1, 267),
	(1286322, 1, 167),
	(1286323, 1, 224),
	(1286324, 1, 265),
	(1286325, 1, 245),
	(1286326, 1, 273),
	(1286327, 1, 276),
	(1286328, 1, 223),
	(1286329, 1, 225),
	(1286330, 1, 219),
	(1286331, 1, 247),
	(1286332, 1, 274),
	(1286333, 1, 277),
	(1286334, 1, 224),
	(1286335, 1, 221),
	(1286336, 1, 199),
	(1286337, 1, 248),
	(1286338, 1, 200),
	(1286339, 1, 278),
	(1286340, 1, 225),
	(1286341, 1, 222),
	(1286342, 1, 272),
	(1286343, 1, 320),
	(1286344, 1, 308),
	(1286345, 1, 266),
	(1286346, 1, 199),
	(1286347, 1, 330),
	(1286348, 1, 273),
	(1286349, 1, 158),
	(1286350, 1, 321),
	(1286351, 1, 267),
	(1286352, 1, 272),
	(1286353, 1, 331),
	(1286354, 1, 274),
	(1286355, 1, 148),
	(1286356, 1, 311),
	(1286357, 1, 276),
	(1286358, 1, 273),
	(1286359, 1, 200),
	(1286360, 1, 339),
	(1286361, 1, 149),
	(1286362, 1, 317),
	(1286363, 1, 277),
	(1286364, 1, 274),
	(1286365, 1, 266),
	(1286366, 1, 340),
	(1286367, 1, 161),
	(1286368, 1, 278),
	(1286369, 1, 169),
	(1286370, 1, 6),
	(1286371, 1, 200),
	(1286372, 1, 267),
	(1286373, 1, 170),
	(1286374, 1, 320),
	(1286375, 1, 162),
	(1286376, 1, 266),
	(1286377, 1, 19),
	(1286378, 1, 276),
	(1286379, 1, 171),
	(1286380, 1, 163),
	(1286381, 1, 321),
	(1286382, 1, 277),
	(1286383, 1, 259),
	(1286384, 1, 267),
	(1286385, 1, 250),
	(1286386, 1, 150),
	(1286387, 1, 148),
	(1286388, 1, 276),
	(1286389, 1, 102),
	(1286390, 1, 278),
	(1286391, 1, 251),
	(1286392, 1, 172),
	(1286393, 1, 149),
	(1286394, 1, 103),
	(1286395, 1, 277),
	(1286396, 1, 320),
	(1286397, 1, 169),
	(1286398, 1, 173),
	(1286399, 1, 328),
	(1286400, 1, 278),
	(1286401, 1, 104),
	(1286402, 1, 321),
	(1286403, 1, 329),
	(1286404, 1, 174),
	(1286405, 1, 170),
	(1286406, 1, 320),
	(1286407, 1, 148),
	(1286408, 1, 105),
	(1286409, 1, 178),
	(1286410, 1, 220),
	(1286411, 1, 171),
	(1286412, 1, 149),
	(1286413, 1, 321),
	(1286414, 1, 106),
	(1286415, 1, 260),
	(1286416, 1, 264),
	(1286417, 1, 150),
	(1286418, 1, 148),
	(1286419, 1, 107),
	(1286420, 1, 169),
	(1286421, 1, 265),
	(1286422, 1, 261),
	(1286423, 1, 172),
	(1286424, 1, 170),
	(1286425, 1, 149),
	(1286426, 1, 108),
	(1286427, 1, 219),
	(1286428, 1, 312),
	(1286429, 1, 173),
	(1286430, 1, 171),
	(1286431, 1, 169),
	(1286432, 1, 109),
	(1286433, 1, 314),
	(1286434, 1, 174),
	(1286435, 1, 221),
	(1286436, 1, 170),
	(1286437, 1, 150),
	(1286438, 1, 146),
	(1286439, 1, 220),
	(1286440, 1, 179),
	(1286441, 1, 222),
	(1286442, 1, 172),
	(1286443, 1, 171),
	(1286444, 1, 217),
	(1286445, 1, 330),
	(1286446, 1, 264),
	(1286447, 1, 180),
	(1286448, 1, 150),
	(1286449, 1, 173),
	(1286450, 1, 252),
	(1286451, 1, 181),
	(1286452, 1, 331),
	(1286453, 1, 265),
	(1286454, 1, 21),
	(1286455, 1, 172),
	(1286456, 1, 174),
	(1286457, 1, 339),
	(1286458, 1, 326),
	(1286459, 1, 219),
	(1286460, 1, 173),
	(1286461, 1, 113),
	(1286462, 1, 220),
	(1286463, 1, 327),
	(1286464, 1, 340),
	(1286465, 1, 221),
	(1286466, 1, 264),
	(1286467, 1, 174),
	(1286468, 1, 22),
	(1286469, 1, 6),
	(1286470, 1, 185),
	(1286471, 1, 222),
	(1286472, 1, 220),
	(1286473, 1, 265),
	(1286474, 1, 208),
	(1286475, 1, 19),
	(1286476, 1, 262),
	(1286477, 1, 330),
	(1286478, 1, 264),
	(1286479, 1, 256),
	(1286480, 1, 219),
	(1286481, 1, 263),
	(1286482, 1, 259),
	(1286483, 1, 331),
	(1286484, 1, 265),
	(1286485, 1, 257),
	(1286486, 1, 221),
	(1286487, 1, 339),
	(1286488, 1, 102),
	(1286489, 1, 315),
	(1286490, 1, 258),
	(1286491, 1, 219),
	(1286492, 1, 222),
	(1286493, 1, 103),
	(1286494, 1, 340),
	(1286495, 1, 316),
	(1286496, 1, 221),
	(1286497, 1, 322),
	(1286498, 1, 330),
	(1286499, 1, 104),
	(1286500, 1, 189),
	(1286501, 1, 6),
	(1286502, 1, 253),
	(1286503, 1, 222),
	(1286504, 1, 331),
	(1286505, 1, 19),
	(1286506, 1, 190),
	(1286507, 1, 105),
	(1286508, 1, 330),
	(1286509, 1, 254),
	(1286510, 1, 339),
	(1286511, 1, 191),
	(1286512, 1, 259),
	(1286513, 1, 106),
	(1286514, 1, 331),
	(1286515, 1, 255),
	(1286516, 1, 340),
	(1286517, 1, 335),
	(1286518, 1, 107),
	(1286519, 1, 102),
	(1286520, 1, 203),
	(1286521, 1, 339),
	(1286522, 1, 6),
	(1286523, 1, 103),
	(1286524, 1, 108),
	(1286525, 1, 336),
	(1286526, 1, 235),
	(1286527, 1, 340),
	(1286528, 1, 19),
	(1286529, 1, 165),
	(1286530, 1, 109),
	(1286531, 1, 104),
	(1286532, 1, 238),
	(1286533, 1, 6),
	(1286534, 1, 259),
	(1286535, 1, 166),
	(1286536, 1, 146),
	(1286537, 1, 105),
	(1286538, 1, 237),
	(1286539, 1, 102),
	(1286540, 1, 19),
	(1286541, 1, 167),
	(1286542, 1, 217),
	(1286543, 1, 106),
	(1286544, 1, 236),
	(1286545, 1, 259),
	(1286546, 1, 103),
	(1286547, 1, 252),
	(1286548, 1, 107),
	(1286549, 1, 223),
	(1286550, 1, 213),
	(1286551, 1, 102),
	(1286552, 1, 104),
	(1286553, 1, 21),
	(1286554, 1, 224),
	(1286555, 1, 108),
	(1286556, 1, 214),
	(1286557, 1, 105),
	(1286558, 1, 103),
	(1286559, 1, 113),
	(1286560, 1, 225),
	(1286561, 1, 109),
	(1286562, 1, 106),
	(1286563, 1, 104),
	(1286564, 1, 215),
	(1286565, 1, 22),
	(1286566, 1, 199),
	(1286567, 1, 146),
	(1286568, 1, 216),
	(1286569, 1, 105),
	(1286570, 1, 107),
	(1286571, 1, 208),
	(1286572, 1, 272),
	(1286573, 1, 217),
	(1286574, 1, 106),
	(1286575, 1, 240),
	(1286576, 1, 108),
	(1286577, 1, 256),
	(1286578, 1, 273),
	(1286579, 1, 252),
	(1286580, 1, 107),
	(1286581, 1, 226),
	(1286582, 1, 109),
	(1286583, 1, 274),
	(1286584, 1, 257),
	(1286585, 1, 21),
	(1286586, 1, 239),
	(1286587, 1, 108),
	(1286588, 1, 146),
	(1286589, 1, 258),
	(1286590, 1, 200),
	(1286591, 1, 113),
	(1286592, 1, 229),
	(1286593, 1, 109),
	(1286594, 1, 217),
	(1286595, 1, 266),
	(1286596, 1, 322),
	(1286597, 1, 22),
	(1286598, 1, 146),
	(1286599, 1, 228),
	(1286600, 1, 252),
	(1286601, 1, 253),
	(1286602, 1, 267),
	(1286603, 1, 208),
	(1286604, 1, 227),
	(1286605, 1, 217),
	(1286606, 1, 21),
	(1286607, 1, 276),
	(1286608, 1, 254),
	(1286609, 1, 256),
	(1286610, 1, 252),
	(1286611, 1, 280),
	(1286612, 1, 113),
	(1286613, 1, 277),
	(1286614, 1, 255),
	(1286615, 1, 257),
	(1286616, 1, 304),
	(1286617, 1, 21),
	(1286618, 1, 22),
	(1286619, 1, 278),
	(1286620, 1, 203),
	(1286621, 1, 258),
	(1286622, 1, 113),
	(1286623, 1, 307),
	(1286624, 1, 208),
	(1286625, 1, 320),
	(1286626, 1, 235),
	(1286627, 1, 322),
	(1286628, 1, 318),
	(1286629, 1, 22),
	(1286630, 1, 256),
	(1286631, 1, 321),
	(1286632, 1, 238),
	(1286633, 1, 253),
	(1286634, 1, 208),
	(1286635, 1, 257),
	(1286636, 1, 319),
	(1286637, 1, 148),
	(1286638, 1, 237),
	(1286639, 1, 254),
	(1286640, 1, 258),
	(1286641, 1, 332),
	(1286642, 1, 256),
	(1286643, 1, 236),
	(1286644, 1, 149),
	(1286645, 1, 255),
	(1286646, 1, 257),
	(1286647, 1, 322),
	(1286648, 1, 333),
	(1286649, 1, 213),
	(1286650, 1, 169),
	(1286651, 1, 203),
	(1286652, 1, 231),
	(1286653, 1, 258),
	(1286654, 1, 253),
	(1286655, 1, 214),
	(1286656, 1, 170),
	(1286657, 1, 235),
	(1286658, 1, 322),
	(1286659, 1, 234),
	(1286660, 1, 254),
	(1286661, 1, 215),
	(1286662, 1, 171),
	(1286663, 1, 238),
	(1286664, 1, 253),
	(1286665, 1, 233),
	(1286666, 1, 255),
	(1286667, 1, 237),
	(1286668, 1, 150),
	(1286669, 1, 216),
	(1286670, 1, 232),
	(1286671, 1, 254),
	(1286672, 1, 203),
	(1286673, 1, 172),
	(1286674, 1, 236),
	(1286675, 1, 240),
	(1286676, 1, 204),
	(1286677, 1, 235),
	(1286678, 1, 255),
	(1286679, 1, 226),
	(1286680, 1, 213),
	(1286681, 1, 173),
	(1286682, 1, 203),
	(1286683, 1, 238),
	(1286684, 1, 230),
	(1286685, 1, 214),
	(1286686, 1, 174),
	(1286687, 1, 239),
	(1286688, 1, 302),
	(1286689, 1, 237),
	(1286690, 1, 235),
	(1286691, 1, 215),
	(1286692, 1, 229),
	(1286693, 1, 220),
	(1286694, 1, 303),
	(1286695, 1, 236),
	(1286696, 1, 238),
	(1286697, 1, 264),
	(1286698, 1, 228),
	(1286699, 1, 216),
	(1286700, 1, 205),
	(1286701, 1, 213),
	(1286702, 1, 237),
	(1286703, 1, 227),
	(1286704, 1, 265),
	(1286705, 1, 240),
	(1286706, 1, 206),
	(1286707, 1, 236),
	(1286708, 1, 214),
	(1286709, 1, 280),
	(1286710, 1, 219),
	(1286711, 1, 226),
	(1286712, 1, 213),
	(1286713, 1, 215),
	(1286714, 1, 241),
	(1286715, 1, 239),
	(1286716, 1, 304),
	(1286717, 1, 221),
	(1286718, 1, 216),
	(1286719, 1, 214),
	(1286720, 1, 242),
	(1286721, 1, 307),
	(1286722, 1, 229),
	(1286723, 1, 222),
	(1286724, 1, 215),
	(1286725, 1, 337),
	(1286726, 1, 240),
	(1286727, 1, 318),
	(1286728, 1, 330),
	(1286729, 1, 228),
	(1286730, 1, 216),
	(1286731, 1, 226),
	(1286732, 1, 9),
	(1286733, 1, 319),
	(1286734, 1, 331),
	(1286735, 1, 227),
	(1286736, 1, 240),
	(1286737, 1, 239),
	(1286738, 1, 28),
	(1286739, 1, 280),
	(1286740, 1, 332),
	(1286741, 1, 339),
	(1286742, 1, 226),
	(1286743, 1, 229),
	(1286744, 1, 61),
	(1286745, 1, 333),
	(1286746, 1, 340),
	(1286747, 1, 304),
	(1286748, 1, 239),
	(1286749, 1, 228),
	(1286750, 1, 202),
	(1286751, 1, 231),
	(1286752, 1, 6),
	(1286753, 1, 307),
	(1286754, 1, 227),
	(1286755, 1, 229),
	(1286756, 1, 8),
	(1286757, 1, 19),
	(1286758, 1, 234),
	(1286759, 1, 318),
	(1286760, 1, 280),
	(1286761, 1, 228),
	(1286762, 1, 23),
	(1286763, 1, 233),
	(1286764, 1, 319),
	(1286765, 1, 259),
	(1286766, 1, 227),
	(1286767, 1, 304),
	(1286768, 1, 141),
	(1286769, 1, 232),
	(1286770, 1, 332),
	(1286771, 1, 102),
	(1286772, 1, 280),
	(1286773, 1, 142),
	(1286774, 1, 307),
	(1286775, 1, 103),
	(1286776, 1, 333),
	(1286777, 1, 204),
	(1286778, 1, 318),
	(1286779, 1, 304),
	(1286780, 1, 24),
	(1286781, 1, 231),
	(1286782, 1, 104),
	(1286783, 1, 230),
	(1286784, 1, 62),
	(1286785, 1, 307),
	(1286786, 1, 319),
	(1286787, 1, 105),
	(1286788, 1, 234),
	(1286789, 1, 302),
	(1286790, 1, 63),
	(1286791, 1, 332),
	(1286792, 1, 318),
	(1286793, 1, 106),
	(1286794, 1, 233),
	(1286795, 1, 303),
	(1286796, 1, 138),
	(1286797, 1, 319),
	(1286798, 1, 333),
	(1286799, 1, 232),
	(1286800, 1, 107),
	(1286801, 1, 205),
	(1286802, 1, 231),
	(1286803, 1, 25),
	(1286804, 1, 332),
	(1286805, 1, 108),
	(1286806, 1, 204),
	(1286807, 1, 206),
	(1286808, 1, 333),
	(1286809, 1, 64),
	(1286810, 1, 234),
	(1286811, 1, 230),
	(1286812, 1, 109),
	(1286813, 1, 241),
	(1286814, 1, 65),
	(1286815, 1, 231),
	(1286816, 1, 233),
	(1286817, 1, 302),
	(1286818, 1, 146),
	(1286819, 1, 242),
	(1286820, 1, 232),
	(1286821, 1, 234),
	(1286822, 1, 338),
	(1286823, 1, 303),
	(1286824, 1, 217),
	(1286825, 1, 337),
	(1286826, 1, 233),
	(1286827, 1, 204),
	(1286828, 1, 252),
	(1286829, 1, 205),
	(1286830, 1, 9),
	(1286831, 1, 232),
	(1286832, 1, 230),
	(1286833, 1, 21),
	(1286834, 1, 206),
	(1286835, 1, 28),
	(1286836, 1, 204),
	(1286837, 1, 302),
	(1286838, 1, 241),
	(1286839, 1, 113),
	(1286840, 1, 61),
	(1286841, 1, 230),
	(1286842, 1, 303),
	(1286843, 1, 22),
	(1286844, 1, 202),
	(1286845, 1, 242),
	(1286846, 1, 302),
	(1286847, 1, 205),
	(1286848, 1, 337),
	(1286849, 1, 8),
	(1286850, 1, 208),
	(1286851, 1, 206),
	(1286852, 1, 303),
	(1286853, 1, 256),
	(1286854, 1, 9),
	(1286855, 1, 23),
	(1286856, 1, 205),
	(1286857, 1, 241),
	(1286858, 1, 141),
	(1286859, 1, 257),
	(1286860, 1, 28),
	(1286861, 1, 206),
	(1286862, 1, 242),
	(1286863, 1, 142),
	(1286864, 1, 258),
	(1286865, 1, 61),
	(1286866, 1, 241),
	(1286867, 1, 337),
	(1286868, 1, 24),
	(1286869, 1, 202),
	(1286870, 1, 322),
	(1286871, 1, 242),
	(1286872, 1, 9),
	(1286873, 1, 8),
	(1286874, 1, 62),
	(1286875, 1, 253),
	(1286876, 1, 28),
	(1286877, 1, 337),
	(1286878, 1, 254),
	(1286879, 1, 23),
	(1286880, 1, 63),
	(1286881, 1, 9),
	(1286882, 1, 61),
	(1286883, 1, 138),
	(1286884, 1, 255),
	(1286885, 1, 141),
	(1286886, 1, 28),
	(1286887, 1, 202),
	(1286888, 1, 25),
	(1286889, 1, 203),
	(1286890, 1, 142),
	(1286891, 1, 8),
	(1286892, 1, 61),
	(1286893, 1, 24),
	(1286894, 1, 235),
	(1286895, 1, 64),
	(1286896, 1, 202),
	(1286897, 1, 23),
	(1286898, 1, 65),
	(1286899, 1, 238),
	(1286900, 1, 62),
	(1286901, 1, 141),
	(1286902, 1, 8),
	(1286903, 1, 63),
	(1286904, 1, 338),
	(1286905, 1, 237),
	(1286906, 1, 23),
	(1286907, 1, 142),
	(1286908, 1, 236),
	(1286909, 1, 138),
	(1286910, 1, 24),
	(1286911, 1, 141),
	(1286912, 1, 213),
	(1286913, 1, 25),
	(1286914, 1, 142),
	(1286915, 1, 62),
	(1286916, 1, 64),
	(1286917, 1, 214),
	(1286918, 1, 63),
	(1286919, 1, 24),
	(1286920, 1, 215),
	(1286921, 1, 65),
	(1286922, 1, 138),
	(1286923, 1, 62),
	(1286924, 1, 216),
	(1286925, 1, 338),
	(1286926, 1, 63),
	(1286927, 1, 25),
	(1286928, 1, 240),
	(1286929, 1, 64),
	(1286930, 1, 138),
	(1286931, 1, 226),
	(1286932, 1, 65),
	(1286933, 1, 25),
	(1286934, 1, 239),
	(1286935, 1, 338),
	(1286936, 1, 64),
	(1286937, 1, 229),
	(1286938, 1, 65),
	(1286939, 1, 228),
	(1286940, 1, 338),
	(1286941, 1, 227),
	(1286942, 1, 280),
	(1286943, 1, 304),
	(1286944, 1, 307),
	(1286945, 1, 318),
	(1286946, 1, 319),
	(1286947, 1, 332),
	(1286948, 1, 333),
	(1286949, 1, 231),
	(1286950, 1, 234),
	(1286951, 1, 233),
	(1286952, 1, 232),
	(1286953, 1, 204),
	(1286954, 1, 230),
	(1286955, 1, 302),
	(1286956, 1, 303),
	(1286957, 1, 205),
	(1286958, 1, 206),
	(1286959, 1, 241),
	(1286960, 1, 242),
	(1286961, 1, 337),
	(1286962, 1, 9),
	(1286963, 1, 28),
	(1286964, 1, 61),
	(1286965, 1, 202),
	(1286966, 1, 8),
	(1286967, 1, 23),
	(1286968, 1, 141),
	(1286969, 1, 142),
	(1286970, 1, 24),
	(1286971, 1, 62),
	(1286972, 1, 63),
	(1286973, 1, 138),
	(1286974, 1, 25),
	(1286975, 1, 64),
	(1286976, 1, 65),
	(1286977, 1, 338);
/*!40000 ALTER TABLE `bss_role_authority_map` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_screen_manager 结构
CREATE TABLE IF NOT EXISTS `bss_screen_manager` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `url` varchar(128) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `description` varchar(5000) DEFAULT NULL,
  `area_id` bigint(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='屏幕管理';

-- 正在导出表  bims_panel_jsyd_jt.bss_screen_manager 的数据：~0 rows (大约)
DELETE FROM `bss_screen_manager`;
/*!40000 ALTER TABLE `bss_screen_manager` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_screen_manager` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_service_collect 结构
CREATE TABLE IF NOT EXISTS `bss_service_collect` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT,
  `create_date` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `service_type` varchar(50) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_service_collect 的数据：~9 rows (大约)
DELETE FROM `bss_service_collect`;
/*!40000 ALTER TABLE `bss_service_collect` DISABLE KEYS */;
INSERT INTO `bss_service_collect` (`id`, `create_date`, `description`, `service_type`, `update_date`) VALUES
	(1, '2014-08-20 12:37:22', '测试4322545', 'DEFAULT', '2014-09-18 17:04:08'),
	(2, '2014-08-23 14:09:39', '无锡测试431', 'NORMAL', '2014-09-18 17:03:56'),
	(3, '2014-08-28 10:57:49', 'bootstrap-0828', 'NORMAL', '2014-08-28 10:57:49'),
	(4, '2014-08-28 18:19:03', 'bootstrap-xj234235', 'NORMAL', '2014-09-18 16:57:13'),
	(5, '2014-09-18 16:46:23', '111111wefwqt', 'NORMAL', '2014-09-18 17:04:17'),
	(6, '2014-09-18 16:47:57', '2222', 'NORMAL', '2014-09-18 17:23:36'),
	(7, '2014-09-18 17:23:41', '11111', 'NORMAL', '2014-09-18 17:23:48'),
	(8, '2014-09-18 17:29:25', 'joyce test', 'NORMAL', '2014-09-18 17:29:25'),
	(9, '2014-09-18 17:33:56', '222222222222', 'NORMAL', '2014-09-18 17:34:13');
/*!40000 ALTER TABLE `bss_service_collect` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_service_collect_device_group_map 结构
CREATE TABLE IF NOT EXISTS `bss_service_collect_device_group_map` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `service_collect_id` bigint(16) DEFAULT NULL,
  `device_group_id` bigint(16) DEFAULT NULL,
  `ysten_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='服务集和终端分组关联表';

-- 正在导出表  bims_panel_jsyd_jt.bss_service_collect_device_group_map 的数据：~0 rows (大约)
DELETE FROM `bss_service_collect_device_group_map`;
/*!40000 ALTER TABLE `bss_service_collect_device_group_map` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_service_collect_device_group_map` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_service_info 结构
CREATE TABLE IF NOT EXISTS `bss_service_info` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT,
  `service_name` varchar(64) DEFAULT NULL,
  `service_type` varchar(32) DEFAULT NULL,
  `service_url` varchar(256) DEFAULT NULL,
  `service_ip` varchar(256) DEFAULT NULL,
  `service_collect_id` bigint(19) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_service_info 的数据：~11 rows (大约)
DELETE FROM `bss_service_info`;
/*!40000 ALTER TABLE `bss_service_info` DISABLE KEYS */;
INSERT INTO `bss_service_info` (`id`, `service_name`, `service_type`, `service_url`, `service_ip`, `service_collect_id`, `create_date`, `description`, `update_date`) VALUES
	(1, 'BIMS_DEFAULT_TMS', '默认TMS地址', 'http://tms.is.ysten.com:8080/yst-tms', NULL, 1, '2014-08-20 12:41:42', '436234', '2014-09-18 17:02:05'),
	(2, 'BIMS_FACADE', '运营商支付接口地址', 'http://223.82.250.26:8081/yst-lbss-api/queryPrice', '', 1, '2014-08-20 12:42:52', '', '2014-10-14 11:26:57'),
	(3, 'BIMS_DEVICE_UPDATE', '终端升级地址', 'http://58.214.17.67:8081/yst-bims-facade/', NULL, 1, '2014-08-20 12:43:50', '', '2014-09-18 16:12:28'),
	(4, 'BIMS_DEVICE_UPDATE_RESULT', '终端升级结果反馈地址', 'http://58.214.17.67:8081/yst-bims-facade/', NULL, 1, '2014-08-20 12:44:29', '', '2014-09-18 16:12:20'),
	(5, 'BIMS_ANIMATION', '开机动画地址', 'http://58.214.17.67:8081/yst-bims-facade/', NULL, 1, '2014-08-20 12:45:33', '', '2014-09-18 16:12:16'),
	(6, 'PANEL', 'PANEL接口地址', 'http://58.214.17.67:8081/yst-bims-panel-api/', NULL, 1, '2014-08-20 12:45:52', '', '2014-09-18 16:12:50'),
	(7, 'BIMS_BACKGROUND', '背景轮换地址', 'http://58.214.17.67:8081/yst-bims-facade/', NULL, 1, '2014-08-20 12:46:04', '', '2014-09-18 16:12:11'),
	(8, 'NOTICE', '系统消息地址', 'http://58.214.17.67:8081/yst-bims-facade/', NULL, 1, '2014-08-20 12:46:21', '', '2014-09-18 16:12:07'),
	(9, 'BIMS_TMS', '播控平台TMS系统地址', 'http://tms.is.ysten.com:8080/yst-tms', NULL, 1, '2014-08-20 12:46:37', '', '2014-09-18 16:12:01'),
	(10, 'BIMS_APK_UPDATE', 'APK升级地址', 'http://58.214.17.67:8081/yst-bims-facade/', NULL, 1, '2014-10-17 10:14:15', NULL, NULL),
	(11, 'BIMS_APK_UPDATE_RESULT', 'APK升级结果反馈地址', 'http://58.214.17.67:8081/yst-bims-facade/', NULL, 1, '2014-10-17 10:15:56', NULL, NULL);
/*!40000 ALTER TABLE `bss_service_info` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_settle_ratio 结构
CREATE TABLE IF NOT EXISTS `bss_settle_ratio` (
  `id` bigint(19) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `description` varchar(5000) DEFAULT NULL,
  `first_name` varchar(30) DEFAULT NULL,
  `first_value` bigint(12) DEFAULT NULL,
  `second_name` varchar(30) DEFAULT NULL,
  `second_value` bigint(12) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_settle_ratio 的数据：~0 rows (大约)
DELETE FROM `bss_settle_ratio`;
/*!40000 ALTER TABLE `bss_settle_ratio` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_settle_ratio` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_sp_define 结构
CREATE TABLE IF NOT EXISTS `bss_sp_define` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `code` varchar(32) DEFAULT NULL,
  `name` varchar(60) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `state` varchar(32) DEFAULT NULL,
  `create_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_sp_define 的数据：~13 rows (大约)
DELETE FROM `bss_sp_define`;
/*!40000 ALTER TABLE `bss_sp_define` DISABLE KEYS */;
INSERT INTO `bss_sp_define` (`id`, `code`, `name`, `description`, `state`, `create_date`) VALUES
	(1, '00', '未知运营商', '未知运营商', 'USABLE', '2014-08-21 18:14:47'),
	(2, '01', '江西移动', '江西移动', 'USABLE', '2014-08-21 18:14:19'),
	(3, '01', '安徽移动', '安徽移动', 'USABLE', '2014-08-21 18:14:22'),
	(4, '01', '四川移动', '四川移动', 'USABLE', '2014-08-21 18:14:23'),
	(5, '01', '重庆移动', '重庆移动', 'USABLE', '2014-08-21 18:14:24'),
	(6, '01', '陕西移动', '陕西移动', 'USABLE', '2014-08-21 18:14:25'),
	(7, '04', '重庆铁通', '重庆铁通', 'USABLE', '2014-08-21 18:14:45'),
	(8, '01', '云南移动', '云南移动', 'USABLE', '2014-08-21 18:14:26'),
	(9, '01', '广东移动', '广东移动', 'USABLE', '2014-08-21 18:14:27'),
	(10, '04', '河北铁通', '河北铁通', 'USABLE', '2014-08-21 18:14:42'),
	(11, '04', '新疆铁通', '新疆铁通', 'USABLE', '2014-08-21 18:14:41'),
	(12, '01', '浙江移动', '浙江移动', 'USABLE', '2014-08-21 18:14:27'),
	(13, '01', '江苏移动', '江苏移动', 'USABLE', '2014-08-21 18:14:37');
/*!40000 ALTER TABLE `bss_sp_define` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_sync_data_log 结构
CREATE TABLE IF NOT EXISTS `bss_sync_data_log` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `data_id` bigint(19) DEFAULT NULL,
  `device_code` varchar(32) DEFAULT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  `customer_code` varchar(32) DEFAULT NULL,
  `back_url` varchar(256) DEFAULT NULL,
  `flag` char(1) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `service_name` varchar(32) DEFAULT NULL,
  `message` varchar(1024) DEFAULT NULL,
  `cause` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_sync_data_log 的数据：~0 rows (大约)
DELETE FROM `bss_sync_data_log`;
/*!40000 ALTER TABLE `bss_sync_data_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_sync_data_log` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_sys_menu 结构
CREATE TABLE IF NOT EXISTS `bss_sys_menu` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `authority_id` bigint(19) DEFAULT NULL,
  `name` varchar(32) DEFAULT NULL,
  `url` varchar(128) DEFAULT NULL,
  `order_sq` bigint(20) DEFAULT NULL COMMENT '导航排序',
  `parent_id` bigint(19) DEFAULT NULL,
  `description` varchar(5000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_sys_menu 的数据：~56 rows (大约)
DELETE FROM `bss_sys_menu`;
/*!40000 ALTER TABLE `bss_sys_menu` DISABLE KEYS */;
INSERT INTO `bss_sys_menu` (`id`, `authority_id`, `name`, `url`, `order_sq`, `parent_id`, `description`) VALUES
	(5, 5, '终端管理', '#', 1, 0, '终端管理'),
	(6, 6, '用户管理', '#', 2, 0, '用户管理'),
	(7, 7, '统计分析', '#', 7, 0, '统计分析管理'),
	(8, 8, '系统管理', '#', 8, 0, '系统管理'),
	(9, 9, '日志管理', '#', 7, 0, '日志管理'),
	(19, 19, '用户信息维护', 'to_customer_list.shtml', 1, 6, '用户信息维护'),
	(20, 20, '用户终端绑定', 'to_device_bind_customer_list.shtml', 2, 6, '用户终端绑定'),
	(21, 21, '终端用户关系', 'to_device_customer_map_list.shtml', 3, 6, '终端用户关系'),
	(22, 22, '终端用户历史', 'to_device_customer_history_list.shtml', 4, 6, '终端用户历史'),
	(23, 23, '操作员信息维护', 'to_operator_list.shtml', 1, 8, '操作员管理'),
	(24, 24, '角色信息维护', 'to_role_list.shtml', 2, 8, '角色管理'),
	(25, 25, '权限信息维护', 'to_authority_list.shtml', 3, 8, '权限信息'),
	(28, 28, '接口日志查询', 'to_interface_log_list.shtml', 8, 9, '接口日志查询'),
	(29, 29, '用户集团维护', 'sp_to_add.shtml', 5, 6, '用户集团维护'),
	(30, 30, '客户信息维护', 'message_list.shtml', 6, 6, '客户信息维护'),
	(31, 31, '设备信息维护', 'to_device_list.shtml', 2, 5, '设备信息维护'),
	(32, 32, 'IP地址信息库', 'to_device_ip_list.shtml', 7, 5, 'IP地址信息库'),
	(34, 34, '区域信息管理', 'area_list.shtml', 6, 59, '区域信息管理'),
	(35, 35, '设备厂商维护', 'to_device_vendor_list.shtml', 4, 5, '设备厂商维护'),
	(36, 36, '设备型号维护', 'to_device_type_list.shtml', 5, 5, '设备型号维护'),
	(37, 37, '终端激活数据统计', 'to_device_active_statistics_list.shtml', 1, 7, '终端激活数据统计'),
	(38, 38, '用户开户数据统计', 'to_user_activate_statistics_list.shtml', 2, 7, '用户开户数据统计'),
	(39, 39, '终端用户数据定时统计', 'to_user_activate_sum_list.shtml', 3, 7, '终端用户数据定时统计'),
	(44, 44, '系统参数配置', 'to_system_config.shtml', 7, 68, '系统参数配置'),
	(45, 57, '运营商信息管理', 'to_sp_define_list.shtml', 5, 68, '运营商信息管理'),
	(46, 61, '操作日志查询', 'to_operate_log_list.shtml', 8, 9, '操作日志查询'),
	(48, 71, '地市信息维护', 'to_city_list.shtml', 10, 59, '地市信息维护'),
	(54, 148, '终端升级维护', 'device_software_package_page.shtml', 1, 60, '终端升级维护'),
	(55, 149, '设备软件号维护', 'device_software_code_page.shtml', 1, 54, '设备软件号维护'),
	(56, 150, '设备软件包维护', 'device_software_package_page.shtml', 2, 54, '设备软件包维护'),
	(57, 157, '设备分组维护', 'to_device_group_list.shtml', 7, 5, '设备分组维护'),
	(58, 158, '消息信息维护', 'to_sys_notice_list.shtml', 10, 5, '消息信息维护'),
	(59, 164, '区域管理', '#', 6, 0, '区域管理'),
	(60, 165, '升级管理', '#', 1, 0, '升级管理'),
	(61, 166, '应用升级维护', 'app_software_package_page.shtml', 2, 60, '应用升级维护'),
	(62, 167, '应用软件号维护', 'app_software_code_page.shtml', 1, 61, '应用软件号维护'),
	(64, 178, '开机动画维护', 'to_boot_animation_list.shtml', 11, 5, '开机动画维护'),
	(65, 220, '升级任务维护', 'upgrade_task_page.shtml', 3, 54, '升级任务维护'),
	(66, 184, '服务信息管理', 'to_service_collect_main.shtml', 12, 68, '服务信息管理'),
	(67, 185, '背景轮换维护', 'to_background_image_list.shtml', 12, 5, '背景轮换维护'),
	(68, 187, '配置管理', '#', 7, 0, '配置管理'),
	(70, 199, '应用软件包维护', 'app_software_package_page.shtml', 2, 61, '应用软件包维护'),
	(71, 200, '应用升级任务维护', 'app_upgrade_task_page.shtml', 3, 61, '应用升级任务维护'),
	(72, 202, '终端升级日志', 'to_device_upgrade_result_log_list.shtml', 10, 9, '终端升级日志'),
	(73, 203, '面板管理', '#', 2, 0, '面板管理'),
	(74, 204, '面板信息维护', 'to_panel_layout.shtml', 2, 73, '面板信息维护'),
	(75, 208, '用户分组维护', 'to_user_group_list.shtml', 7, 6, '用户分组维护'),
	(80, 213, '预览面板信息维护', 'to_preview_template_list.shtml', 4, 73, '预览面板信息维护'),
	(81, 226, '面板包信息维护', 'to_panel_package_layout.shtml', 1, 73, '面板包信息维护'),
	(82, 231, '面板项信息维护', 'to_panel_item_list.shtml', 4, 73, '面板项信息维护'),
	(83, 235, '导航信息维护', 'to_nav_define_list.shtml', 5, 73, '导航信息维护'),
	(84, 295, '终端激活统计图表', 'to_device_active_statistics_chart.shtml', 5, 7, '终端激活统计图表'),
	(85, 296, '用户开户统计图表', 'to_user_activate_statistics_chart.shtml', 6, 7, '用户开户统计图表'),
	(86, 338, '版本信息维护', 'to_sys_version_list.shtml', 4, 8, '版本信息维护'),
	(87, 339, 'APK升级维护', 'apk_software_package_upgrade_page.shtml', 3, 60, 'APK升级维护'),
	(88, 340, 'APK软件号维护', 'to_apk_software_code_list.shtml', 3, 87, 'APK软件号维护');
/*!40000 ALTER TABLE `bss_sys_menu` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_sys_notice 结构
CREATE TABLE IF NOT EXISTS `bss_sys_notice` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `title` varchar(32) DEFAULT NULL,
  `content` varchar(5000) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `is_default` int(12) DEFAULT NULL,
  `operate_user` varchar(50) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='消息';

-- 正在导出表  bims_panel_jsyd_jt.bss_sys_notice 的数据：~8 rows (大约)
DELETE FROM `bss_sys_notice`;
/*!40000 ALTER TABLE `bss_sys_notice` DISABLE KEYS */;
INSERT INTO `bss_sys_notice` (`id`, `title`, `content`, `start_date`, `end_date`, `is_default`, `operate_user`, `create_date`, `status`, `update_date`) VALUES
	(1, '中心测试消息', 'BIMS江西中心平台正式发布2', '2014-08-21 10:52:55', '2015-05-13 10:52:55', 0, 'admin', '2014-08-21 10:57:28', 1, '2014-10-15 11:13:56'),
	(4, 'xj-notice-01', '绑定用户分组 消息测试\n绑定用户分组 消息测试', '2014-08-26 15:08:17', '2014-08-27 15:08:17', 0, 'admin', '2014-08-26 15:08:51', 1, '2014-09-16 09:46:44'),
	(5, 'xj-notice-02', '绑定用户编号 消息', '2014-08-26 16:32:08', '2014-09-11 16:32:08', 0, 'admin', '2014-08-26 16:32:00', 1, '2014-09-16 09:46:38'),
	(6, '默认消息01', '默认消息01\n凡墻都是門3\n', '2014-09-12 11:03:58', '2015-10-12 11:03:58', 1, 'admin', '2014-09-12 11:06:41', 1, '2014-10-15 13:34:04'),
	(7, '默认消息02', '默认消息02 看不见的不等于不存在', '2014-09-12 15:20:57', '2014-09-30 15:20:57', 1, 'admin', '2014-09-12 15:20:53', 1, '2014-09-12 15:20:53'),
	(8, 'xj-notice-03', '绑定设备编号 测试\n绑定设备编号 测试', '2014-09-16 09:43:42', '2015-09-24 09:43:42', 0, 'admin', '2014-09-16 09:43:05', 0, '2014-09-16 09:46:32'),
	(9, 'xj-notice-04', '绑定设备分组 消息分组测试', '2014-09-16 09:43:42', '2015-09-16 09:43:42', 0, 'admin', '2014-09-16 09:45:46', 1, '2014-09-16 09:45:46'),
	(10, 'sos', '123', '2014-10-11 16:15:45', '2014-11-01 16:15:45', 0, 'admin', '2014-10-11 16:16:24', 1, '2014-10-15 12:51:46');
/*!40000 ALTER TABLE `bss_sys_notice` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_sys_version 结构
CREATE TABLE IF NOT EXISTS `bss_sys_version` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `version_id` varchar(32) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `content` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_sys_version 的数据：~1 rows (大约)
DELETE FROM `bss_sys_version`;
/*!40000 ALTER TABLE `bss_sys_version` DISABLE KEYS */;
INSERT INTO `bss_sys_version` (`id`, `version_id`, `name`, `create_date`, `content`) VALUES
	(1, 'V4.6', '4.6版本', '2014-10-10 13:34:55', '版本内容');
/*!40000 ALTER TABLE `bss_sys_version` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_talk_system 结构
CREATE TABLE IF NOT EXISTS `bss_talk_system` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `code` varchar(32) DEFAULT NULL,
  `name` varchar(32) DEFAULT NULL,
  `create_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `description` varchar(5000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_talk_system 的数据：~0 rows (大约)
DELETE FROM `bss_talk_system`;
/*!40000 ALTER TABLE `bss_talk_system` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_talk_system` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_upgrade_task 结构
CREATE TABLE IF NOT EXISTS `bss_upgrade_task` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT COMMENT '升级ID',
  `name` varchar(256) DEFAULT NULL,
  `soft_code_id` bigint(19) DEFAULT NULL COMMENT '软件号',
  `software_package_id` bigint(16) NOT NULL COMMENT '软件包',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `vendor_ids` varchar(1000) DEFAULT NULL,
  `version_seq` int(11) DEFAULT NULL,
  `max_num` int(11) DEFAULT NULL,
  `time_interval` int(11) DEFAULT NULL,
  `is_all` int(12) DEFAULT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  `oper_user` varchar(50) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `soft_code_id` (`soft_code_id`),
  KEY `software_package_id` (`software_package_id`),
  CONSTRAINT `bss_upgrade_task_ibfk_1` FOREIGN KEY (`soft_code_id`) REFERENCES `bss_device_software_code` (`id`),
  CONSTRAINT `bss_upgrade_task_ibfk_2` FOREIGN KEY (`software_package_id`) REFERENCES `bss_device_software_package` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='升级任务';

-- 正在导出表  bims_panel_jsyd_jt.bss_upgrade_task 的数据：~5 rows (大约)
DELETE FROM `bss_upgrade_task`;
/*!40000 ALTER TABLE `bss_upgrade_task` DISABLE KEYS */;
INSERT INTO `bss_upgrade_task` (`id`, `name`, `soft_code_id`, `software_package_id`, `create_date`, `vendor_ids`, `version_seq`, `max_num`, `time_interval`, `is_all`, `last_modify_time`, `oper_user`, `start_date`, `end_date`) VALUES
	(1, '任务1', 2, 1, '2014-08-26 10:28:16', NULL, NULL, 20, 2, 0, '2014-08-26 10:28:16', 'admin', '2014-08-26 10:28:21', '2015-09-30 10:28:21'),
	(2, '任务2', 3, 2, '2014-08-26 15:12:02', NULL, NULL, 20, 1, 1, '2014-08-26 15:20:30', 'admin', '2014-08-26 15:00:39', '2014-08-28 15:11:51'),
	(4, '任务4', 1, 3, '2014-08-28 11:08:08', NULL, NULL, 30, 2, 0, '2014-08-28 11:08:08', 'admin', '2014-08-28 11:08:27', '2015-01-29 11:08:27'),
	(6, '任务6', 4, 4, '2014-09-01 11:23:15', NULL, NULL, 2, 2, 0, '2014-09-01 11:23:15', 'admin', '2014-09-01 11:23:31', '2014-09-27 11:23:31'),
	(7, '任务7', 5, 6, '2014-09-22 17:19:53', NULL, NULL, 20, 1, 1, '2014-09-23 11:44:41', 'admin', '2014-09-22 00:21:09', '2014-09-30 17:21:09');
/*!40000 ALTER TABLE `bss_upgrade_task` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_user_activate_day_sum 结构
CREATE TABLE IF NOT EXISTS `bss_user_activate_day_sum` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `date` varchar(10) DEFAULT NULL COMMENT '日期',
  `province_id` varchar(8) DEFAULT NULL COMMENT '省份',
  `city_id` varchar(8) DEFAULT NULL COMMENT '地市',
  `telecom_id` varchar(8) DEFAULT NULL COMMENT '运营商',
  `vendor_id` varchar(8) DEFAULT NULL COMMENT '厂商',
  `terminal_id` varchar(8) DEFAULT NULL COMMENT '终端类型',
  `activate_all` int(11) DEFAULT NULL COMMENT '总激活终端数',
  `activate_day` int(11) DEFAULT NULL COMMENT '当日激活终端数',
  `sync` varchar(50) DEFAULT NULL COMMENT '是否与远程数据库同步：0：未同步，1：已同步',
  `user_all` int(11) DEFAULT NULL COMMENT '总开户数',
  `user_day` int(11) DEFAULT NULL COMMENT '新增开户数',
  `stb_return_day` int(11) DEFAULT NULL COMMENT '退订用户数',
  `stb_receive_day` int(11) DEFAULT NULL COMMENT '到货终端数',
  `sync_date` datetime DEFAULT NULL COMMENT '同步时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日激活数';

-- 正在导出表  bims_panel_jsyd_jt.bss_user_activate_day_sum 的数据：~0 rows (大约)
DELETE FROM `bss_user_activate_day_sum`;
/*!40000 ALTER TABLE `bss_user_activate_day_sum` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_user_activate_day_sum` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_user_app_upgrade_map 结构
CREATE TABLE IF NOT EXISTS `bss_user_app_upgrade_map` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(32) DEFAULT NULL,
  `upgrade_id` bigint(19) DEFAULT NULL,
  `user_group_id` bigint(19) DEFAULT NULL,
  `type` varchar(32) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户-用户组-应用升级关联表';

-- 正在导出表  bims_panel_jsyd_jt.bss_user_app_upgrade_map 的数据：~0 rows (大约)
DELETE FROM `bss_user_app_upgrade_map`;
/*!40000 ALTER TABLE `bss_user_app_upgrade_map` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_user_app_upgrade_map` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_user_group 结构
CREATE TABLE IF NOT EXISTS `bss_user_group` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `type` varchar(32) DEFAULT NULL COMMENT '业务分组类型，升级用户组、PANEL用户组、开机动画用户组、通知用户组等',
  `dynamic_flag` int(11) DEFAULT NULL COMMENT '静态分组：0，动态分组：1',
  `sql_expression` varchar(256) DEFAULT NULL COMMENT 'sql表达式（用于动态分组）',
  `description` varchar(256) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `area_id` bigint(19) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COMMENT='用户组';

-- 正在导出表  bims_panel_jsyd_jt.bss_user_group 的数据：~24 rows (大约)
DELETE FROM `bss_user_group`;
/*!40000 ALTER TABLE `bss_user_group` DISABLE KEYS */;
INSERT INTO `bss_user_group` (`id`, `name`, `type`, `dynamic_flag`, `sql_expression`, `description`, `create_date`, `update_date`, `area_id`) VALUES
	(1, 'notice-ug-SH', 'NOTICE', 0, '', '', '2014-09-03 15:45:59', '2014-09-03 15:45:59', 2),
	(2, 'animation-ug-JS', 'ANIMATION', 0, '', '', '2014-09-03 15:49:46', '2014-09-03 15:49:46', 12),
	(3, 'notice01-ug-JS', 'NOTICE', 0, '', '', '2014-09-03 15:55:08', '2014-09-03 15:55:08', 12),
	(4, 'notice02-ug-JS', 'NOTICE', 0, '', '', '2014-09-03 15:55:38', '2014-09-03 15:55:38', 12),
	(5, 'banckground-ug-SH', 'BACKGROUND', 0, '', '', '2014-09-03 16:38:01', '2014-09-03 16:38:01', 2),
	(6, 'backimage-ug-SH', 'BACKGROUND', 0, '', '', '2014-09-04 10:00:58', '2014-09-04 10:00:58', 2),
	(7, 'background-ug-JS', 'BACKGROUND', 0, '', '', '2014-09-04 10:01:18', '2014-09-04 10:01:18', 12),
	(8, '面板-天津', 'PANEL', 0, '', '', '2014-09-04 16:32:48', '2014-09-04 16:32:48', 3),
	(9, '面板-江西', 'PANEL', 0, '', '面板-江西', '2014-09-04 16:58:12', '2014-09-04 16:58:12', 14),
	(10, '消息-江西', 'NOTICE', 0, '', '', '2014-09-04 17:08:06', '2014-09-04 17:08:06', 14),
	(11, '江西-开机动画', 'ANIMATION', 0, '', '江西-开机动画', '2014-09-04 17:12:30', '2014-09-04 17:12:30', 14),
	(12, '江西-升级', 'UPGRADE', 0, '', '', '2014-09-04 17:15:15', '2014-09-04 17:15:15', 14),
	(13, 'animation-ug-SH', 'ANIMATION', 0, '', '', '2014-09-05 09:42:29', '2014-09-05 09:42:29', 2),
	(14, '江西省-WWW', 'ANIMATION', 0, '', '', '2014-09-09 10:52:21', '2014-09-09 10:52:21', 14),
	(15, 'ddddasdfasdf', 'APPUPGRADE', 0, '', 'ddd', '2014-09-11 14:37:56', '2014-09-11 14:38:00', 4),
	(17, 'JiangXi-animation', 'ANIMATION', 0, '', '', '2014-09-12 10:51:16', '2014-09-16 09:18:02', 14),
	(18, 'JiangXi-notice1', 'NOTICE', 0, '', '', '2014-09-12 10:51:35', '2014-09-12 10:51:35', 14),
	(19, 'JiangXi-notice2', 'NOTICE', 0, '', '', '2014-09-12 10:51:54', '2014-09-12 10:51:54', 14),
	(20, 'Jiangxi-bg1', 'BACKGROUND', 0, '', '', '2014-09-12 10:52:51', '2014-09-12 10:52:51', 14),
	(21, 'JiangXi-bg2', 'BACKGROUND', 0, '', '', '2014-09-12 10:53:08', '2014-09-18 15:55:17', 14),
	(22, 'WWW', 'PRODUCTPACKAGE', 0, '', '', '2014-09-19 11:21:16', '2014-09-19 11:21:16', 14),
	(24, 'WWW1', 'PRODUCTPACKAGE', 0, '', '', '2014-09-22 10:14:09', '2014-09-22 10:14:09', 12),
	(25, 'sostest', 'PRODUCTPACKAGE', 0, '', 'wuxi ceshi ', '2014-10-15 16:57:05', '2014-10-15 16:57:05', 12),
	(26, 'soso', 'PRODUCTPACKAGE', 0, '', '', '2014-10-16 10:44:07', '2014-10-16 10:44:07', 12);
/*!40000 ALTER TABLE `bss_user_group` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_user_group_map 结构
CREATE TABLE IF NOT EXISTS `bss_user_group_map` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `code` varchar(32) DEFAULT NULL,
  `user_group_id` bigint(19) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8 COMMENT='用户组和用户关联表';

-- 正在导出表  bims_panel_jsyd_jt.bss_user_group_map 的数据：~12 rows (大约)
DELETE FROM `bss_user_group_map`;
/*!40000 ALTER TABLE `bss_user_group_map` DISABLE KEYS */;
INSERT INTO `bss_user_group_map` (`id`, `code`, `user_group_id`, `create_date`) VALUES
	(47, '20140811135456001000', 17, '2014-09-15 14:32:01'),
	(48, '20140811135456001000', 18, '2014-09-15 14:32:24'),
	(54, '20140811135456001000', 19, '2014-09-15 15:42:04'),
	(55, '20140811135456001000', 11, '2014-09-15 15:42:37'),
	(59, '20140811135456001000', 20, '2014-09-16 16:46:28'),
	(61, '400204734995', 20, '2014-09-17 14:40:47'),
	(62, '400206853473', 22, '2014-09-22 09:52:20'),
	(63, '400206853473', 24, '2014-09-22 10:15:44'),
	(64, '20141014170006001004', 25, '2014-10-16 10:25:22'),
	(65, '20140924101407001000', 25, '2014-10-16 10:27:17'),
	(68, '20141014170006001004', 26, '2014-10-16 10:46:15'),
	(69, '20140924101407001000', 26, '2014-10-16 11:02:15');
/*!40000 ALTER TABLE `bss_user_group_map` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_user_group_pp_info_map 结构
CREATE TABLE IF NOT EXISTS `bss_user_group_pp_info_map` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `user_group_id` bigint(19) DEFAULT NULL,
  `product_id` varchar(32) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_user_group_pp_info_map 的数据：~4 rows (大约)
DELETE FROM `bss_user_group_pp_info_map`;
/*!40000 ALTER TABLE `bss_user_group_pp_info_map` DISABLE KEYS */;
INSERT INTO `bss_user_group_pp_info_map` (`id`, `user_group_id`, `product_id`, `create_date`, `update_date`) VALUES
	(1, 22, '100004362', '2014-09-19 11:21:16', '2014-09-19 11:21:16'),
	(3, 24, '100004362', '2014-09-22 10:14:09', '2014-09-22 10:14:09'),
	(4, 25, '20140409001', '2014-10-15 16:57:05', '2014-10-15 16:57:05'),
	(5, 26, '100004364', '2014-10-16 10:44:07', '2014-10-16 10:44:07');
/*!40000 ALTER TABLE `bss_user_group_pp_info_map` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_user_notice_map 结构
CREATE TABLE IF NOT EXISTS `bss_user_notice_map` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `code` varchar(32) DEFAULT NULL,
  `notice_id` bigint(19) DEFAULT NULL COMMENT '根据type类型的不同关联不同的任务ID或panel的分组ID',
  `user_group_id` bigint(19) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `type` varchar(32) DEFAULT NULL COMMENT '是用户映射还是分组映射',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户-消息关联表';

-- 正在导出表  bims_panel_jsyd_jt.bss_user_notice_map 的数据：~0 rows (大约)
DELETE FROM `bss_user_notice_map`;
/*!40000 ALTER TABLE `bss_user_notice_map` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_user_notice_map` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_user_package_info 结构
CREATE TABLE IF NOT EXISTS `bss_user_package_info` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(32) DEFAULT NULL,
  `customer_code` varchar(32) DEFAULT NULL,
  `product_id` varchar(32) DEFAULT NULL,
  `product_name` varchar(64) DEFAULT NULL,
  `product_type` varchar(32) DEFAULT NULL,
  `outter_code` varchar(32) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `content_name` varchar(32) DEFAULT NULL,
  `buy_num` int(20) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.bss_user_package_info 的数据：~0 rows (大约)
DELETE FROM `bss_user_package_info`;
/*!40000 ALTER TABLE `bss_user_package_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_user_package_info` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.bss_user_upgrade_map 结构
CREATE TABLE IF NOT EXISTS `bss_user_upgrade_map` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(32) DEFAULT NULL,
  `upgrade_id` bigint(19) DEFAULT NULL,
  `user_group_id` bigint(19) DEFAULT NULL,
  `type` varchar(32) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备-升级关联表';

-- 正在导出表  bims_panel_jsyd_jt.bss_user_upgrade_map 的数据：~0 rows (大约)
DELETE FROM `bss_user_upgrade_map`;
/*!40000 ALTER TABLE `bss_user_upgrade_map` DISABLE KEYS */;
/*!40000 ALTER TABLE `bss_user_upgrade_map` ENABLE KEYS */;


-- 导出  表 bims_panel_jsyd_jt.system_config 结构
CREATE TABLE IF NOT EXISTS `system_config` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `configkey` varchar(64) DEFAULT NULL COMMENT '键',
  `configvalue` varchar(1024) DEFAULT NULL COMMENT '值',
  `zhname` varchar(2048) DEFAULT NULL COMMENT '名称',
  `depiction` varchar(5000) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_index` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

-- 正在导出表  bims_panel_jsyd_jt.system_config 的数据：~26 rows (大约)
DELETE FROM `system_config`;
/*!40000 ALTER TABLE `system_config` DISABLE KEYS */;
INSERT INTO `system_config` (`id`, `configkey`, `configvalue`, `zhname`, `depiction`) VALUES
	(1, 'systemTitle', '业务集成管理平台【BIMS】(V2.1.9.30）', '系统标题', '【管理系统】系统标题'),
	(3, 'importFileSize', '10000', '导入最大数量', '【管理系统】导入最大数量'),
	(4, 'sysPanelPackageUrl', 'http://125.39.95.57:8089/rtms-api/api/getPanelPackage.json', '同步面板包', '从播控同步面板包数据'),
	(5, 'sysPanelUrl', 'http://125.39.95.57:8089/rtms-api/api/getPanel.json', '同步面板', '从播控同步面板数据'),
	(6, 'sysPanelItemUrl', 'http://125.39.95.57:8089/rtms-api/api/getPanelItem.json', '同步面板项', '从播控同步面板项数据'),
	(7, 'sysNavUrl', 'http://125.39.95.57:8089/rtms-api/api/getNavDefine.json', '同步导航', '从播控同步导航数据'),
	(8, 'sysPanelItemMapUrl', 'http://125.39.95.57:8089/rtms-api/api/getPanelItemRel.json', '同步面板与面板项的关系', '从播控同步面板与面板项关系数据'),
	(9, 'sysPanelPackageMapUrl', 'http://125.39.95.57:8089/rtms-api/api/getPanelPackageRel.json', '同步面板、导航、面板包三者的关系', '从播控同步面板、导航、面板包三者的关系数据'),
	(10, 'sysPreviewItemUrl', 'http://125.39.95.57:8089/rtms-api/api/getPreviewItem.json', '同步预览模板', '从播控同步预览模板数据'),
	(11, 'sysPreviewTemplateUrl', 'http://125.39.95.57:8089/rtms-api/api/getPreviewTemplate.json', '同步预览模块表', '从播控同步预览模块表数据'),
	(12, 'sysPreviewItemDataUrl', 'http://125.39.95.57:8089/rtms-api/api/getPreviewItemData.json', '同步预览模块表-实例表', '从播控同步预览模块表-实例表数据'),
	(13, 'isCenter', 'false', '是否是中心系统', '是否是中心系统'),
	(14, 'bootstrapencrypt', 'false', '终端引导加密传输开关', '终端BOOT接口是否开启加密传。true：打开，false：关闭'),
	(15, 'bootstrapSaveDeviceUrl', 'http://223.82.250.117:9144/yst-bims-facade/stb/bootstrapSaveDevice.json', 'BOOT接口，MAC地址不存在写入设备数据接口地址', 'BOOT接口，MAC地址不存在写入设备数据接口地址'),
	(19, 'TVsetActionUrl', 'http://sns.is.ysten.com/CNTV/index.html?action=detail&object=', '节目集的播放地址前缀', '节目集的播放地址前缀'),
	(20, 'programActionUrl', 'http://sns.is.ysten.com/CNTV/index.html?action=EPGList&object=', '栏目的播放地址前缀', '栏目的播放地址前缀'),
	(21, 'updateResultUrl', 'http://58.214.17.67:8081/yst-bims-facade/', '升级结果反馈接口地址', '升级结果反馈接口地址'),
	(22, 'selectPpList', 'http://192.168.1.2:9145/yst-lbss-api/stb/getAllProducts', '产品包查询接口URL', '产品包查询接口URL'),
	(23, 'updateResultUrl', 'http://192.168.1.1:8081/yst-bims-facade/', '升级结果反馈接口地址', '升级结果反馈接口地址'),
	(24, 'syncProvincePanelData', 'http://localhost:8080/center/receivePanelDatas.json', '省级同步数据到中心', '省级同步数据到中心'),
	(25, 'deployDistrictCode', '320000', '部署区域行政区划码', '系统部署所在区域的行政区划码'),
	(26, 'videoUrl', 'http://127.0.0.1:8080/video/', '视频访问地址', '上传的视频存放地址'),
	(27, 'picUrl', 'http://127.0.0.1:8080/pic/', '图片访问地址', '上传的图片存放地地址'),
	(28, 'picFiles', 'D:\\apache-tomcat-7.0.54\\webapps\\pic\\', '图片上传地址', '图片上传地址'),
	(29, 'videoFiles', 'D:\\apache-tomcat-7.0.54\\webapps\\video\\', '视频上传地址', '视频上传地址'),
	(30, 'notice_expire_days', '15', '通知设备即将过期的通知天数', '通知设备即将过期的通知天数');
/*!40000 ALTER TABLE `system_config` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
