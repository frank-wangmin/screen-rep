/*日激活统计表*/
CREATE TABLE `bss_user_activate_day_sum` (
	`id` BIGINT(16) NOT NULL AUTO_INCREMENT,
	`date` VARCHAR(10) NULL DEFAULT NULL COMMENT '日期',
	`province_id` VARCHAR(8) NULL DEFAULT NULL COMMENT '省份',
	`city_id` VARCHAR(8) NULL DEFAULT NULL COMMENT '地市',
	`telecom_id` VARCHAR(8) NULL DEFAULT NULL COMMENT '运营商',
	`vendor_id` VARCHAR(8) NULL DEFAULT NULL COMMENT '厂商',
	`terminal_id` VARCHAR(8) NULL DEFAULT NULL COMMENT '终端类型',
	`activate_all` INT(11) NULL DEFAULT NULL COMMENT '总激活终端数',
	`activate_day` INT(11) NULL DEFAULT NULL COMMENT '当日激活终端数',
	`sync` INT(2) NULL DEFAULT NULL COMMENT '是否与远程数据库同步：0：未同步，1：已同步',
	`user_all` INT(11) NULL DEFAULT NULL COMMENT '总开户数',
	`user_day` INT(11) NULL DEFAULT NULL COMMENT '新增开户数',
	`stb_return_day` INT(11) NULL DEFAULT NULL COMMENT '退订用户数',
	`stb_receive_day` INT(11) NULL DEFAULT NULL COMMENT '到货终端数',
	`sync_date` DATETIME NULL DEFAULT NULL COMMENT '同步时间',
	PRIMARY KEY (`id`)
)
COMMENT='日激活数'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=1;

/*日激活统计表-远程表*/
CREATE TABLE `user_activate_day_sum` (
	`date` VARCHAR(10) NULL DEFAULT NULL COMMENT '日期',
	`province_id` VARCHAR(8) NULL DEFAULT NULL COMMENT '省份',
	`city_id` VARCHAR(8) NULL DEFAULT NULL COMMENT '地市',
	`telecom_id` VARCHAR(8) NULL DEFAULT NULL COMMENT '运营商',
	`vendor_id` VARCHAR(8) NULL DEFAULT NULL COMMENT '厂商',
	`terminal_id` VARCHAR(8) NULL DEFAULT NULL COMMENT '终端类型',
	`activate_all` INT(11) NULL DEFAULT NULL COMMENT '总激活终端数',
	`activate_day` INT(11) NULL DEFAULT NULL COMMENT '当日激活终端数',
	`user_all` INT(11) NULL DEFAULT NULL COMMENT '总开户数',
	`user_day` INT(11) NULL DEFAULT NULL COMMENT '新增开户数',
	`stb_return_day` INT(11) NULL DEFAULT NULL COMMENT '退订用户数',
	`stb_receive_day` INT(11) NULL DEFAULT NULL COMMENT '到货终端数'
)
COMMENT='日激活数'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

/*屏幕管理表*/
CREATE TABLE `bss_screen_manager` (
	`ID` BIGINT(16) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(32) NULL DEFAULT NULL COMMENT '标题',
	`url` VARCHAR(128) NULL DEFAULT NULL COMMENT 'URL',
	`create_date` DATETIME NULL DEFAULT NULL COMMENT '创建时间',
	`description` VARCHAR(5000) NULL DEFAULT NULL COMMENT '描述',
	`area_id` BIGINT(11) NULL DEFAULT NULL COMMENT '地市ID',
	PRIMARY KEY (`ID`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=1;

alter table bss_program_record add column is_sync_cdn varchar(10);      -- 添加同步CDN系统状态字段
alter table bss_program_record add column sync_cdn_status varchar(10);     --添加同步CDN系统结果状态字段

/*媒体表添加字段*/
alter table bss_program_bitrate_record add column `push_date` timestamp NULL DEFAULT NULL COMMENT '推送时间';
alter table bss_program_bitrate_record add column `last_handle_date` timestamp NULL DEFAULT NULL COMMENT '最后处理时间';
alter table bss_program_bitrate_record add column `push_status` varchar(36) DEFAULT NULL COMMENT '推送状态';
alter table bss_program_bitrate_record add column `error_times` int(11) DEFAULT NULL COMMENT '推送失败次数';