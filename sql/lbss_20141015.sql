INSERT INTO bss_service_info(service_name, service_type, service_url, service_collect_id, create_date)
VALUES ('BIMS_APK_UPDATE', 'APK升级地址', 'http://58.214.17.67:8081/yst-bims-facade/', '1', current_time() );

INSERT INTO bss_service_info(service_name, service_type, service_url, service_collect_id, create_date)
VALUES ('BIMS_APK_UPDATE_RESULT', 'APK升级结果反馈地址', 'http://58.214.17.67:8081/yst-bims-facade/', '1', current_time() );

drop table if exists bss_apk_upgrade_task;

/*==============================================================*/
/* Table: bss_apk_upgrade_task                                  */
/*==============================================================*/
create table bss_apk_upgrade_task
(
   id                   bigint(19) not null auto_increment comment '升级ID',
   name                 varchar(256) character set utf8 comment '升级任务名称',
   max_num              int(11) comment '终端当前版本号',
   soft_code_id         bigint(19) default NULL comment '软件号id',
   software_package_id  bigint(19) not null comment '软件版本id',
   create_date          datetime default NULL comment '创建时间',
   vendor_ids           varchar(1000) comment '供应商id',
   version_seq          int(11) comment '最大升级数',
   time_interval        int(11) comment '时间间隔',
   is_all               int(12) comment '是否全部终端都提示（当设备、设备组都没有关联设备时，显示）',
   last_modify_time     datetime,
   oper_user            varchar(50),
   start_date           datetime,
   end_date             datetime,
   primary key (id)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=0
ROW_FORMAT=COMPACT;

alter table bss_apk_upgrade_task comment 'APK升级任务';


drop table if exists bss_apk_software_package;

/*==============================================================*/
/* Table: bss_apk_software_package                              */
/*==============================================================*/
create table bss_apk_software_package
(
   id                   bigint(19) not null auto_increment comment '软件包ID',
   soft_code_id         bigint(19) default NULL comment '软件号',
   version_seq          int(11) default NULL comment '软件版本序号。设备升级后对应的设备软件包版本序列号，为整型',
   version_name         varchar(32) character set utf8 comment '软件版本名称',
   package_type         varchar(32) character set utf8 comment '软件包类型TEST("测试"), RELEASE("发布");',
   package_status       varchar(32) character set utf8 comment '软件包状态',
   package_location     varchar(255) character set utf8 comment '软件包绝对路径',
   is_mandatory         varchar(32) character set utf8 comment '是否强制升级MANDATORY("强制"), NOTMANDATORY("不强制");',
   md5                  varchar(32) character set utf8,
   create_date          datetime default NULL comment '创建时间',
   device_version_seq   int(11) default 0 comment '终端的apk软件当前版本号',
   full_software_id     bigint(19) default NULL comment '当为增量升级选择对应的全量包软件ID',
   last_modify_time     datetime,
   oper_user            varchar(50),
   primary key (id)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=0
ROW_FORMAT=COMPACT;

alter table bss_apk_software_package comment 'APK升级软件包信息（apk路径）';


drop table if exists bss_apk_software_code;

/*==============================================================*/
/* Table: bss_apk_software_code                                 */
/*==============================================================*/
create table bss_apk_software_code
(
   id                   bigint(19) not null auto_increment comment '软件号',
   name                 varchar(32) character set utf8 comment '名称',
   code                 varchar(64) character set utf8 comment ' 编码',
   status               varchar(16) character set utf8 comment '状态',
   create_date          datetime default NULL comment '创建时间',
   description          varchar(2048) character set utf8 comment '描述',
   oper_user            varchar(50),
   last_modify_time     datetime,
   primary key (id)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=0
ROW_FORMAT=COMPACT;

alter table bss_apk_software_code comment 'APK设备软件号';

/*==============================================================*/
/* Table: bss_apk_upgrade_map                                   */
/*==============================================================*/

drop table if exists bss_apk_upgrade_map;
create table bss_apk_upgrade_map
(
   id                   bigint(19) not null auto_increment,
   ysten_id             varchar(32),
   upgrade_id           bigint(19),
   device_group_id      bigint(19),
   type                 varchar(32),
   create_date          datetime,
   primary key (id)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=0
ROW_FORMAT=COMPACT;

alter table bss_apk_upgrade_map comment '设备-apk升级关联表';

drop table if exists bss_apk_upgrade_result_log;

/*==============================================================*/
/* Table: bss_apk_upgrade_result_log                            */
/*==============================================================*/
create table bss_apk_upgrade_result_log
(
   id                   bigint(19) not null auto_increment,
   device_code          varchar(36) character set utf8,
   ysten_id             varchar(32),
   soft_code            varchar(64) character set utf8 comment '软件编码',
   device_version_seq   int(11) default NULL comment '终端的apk软件当前版本号',
   version_seq          int(11) default NULL comment '终端升级前apk软件版本序号',
   status               varchar(16) character set utf8,
   duration             bigint(16) default NULL comment '持续时间',
   create_date          datetime default NULL,
   primary key (id)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=0
ROW_FORMAT=COMPACT;

alter table bss_apk_upgrade_result_log comment '设备apk更新记录';
