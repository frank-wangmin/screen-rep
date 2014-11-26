-- add column isReturnYstenId for the table bss_device --
alter table `bss_device` add column `is_return_ystenId` CHAR DEFAULT NULL  COMMENT '是否返回ystenId: 1--返回, 0--不返回' AFTER `district_code`;

INSERT INTO system_config(configKey,configValue,zhname,depiction) VALUES ('bootstrapUpdateDeviceUrl', 'http://127.0.0.1:8081/yst-bims-facade/stb/bootstrapUpdateDevice.json', 'BOOT接口，更新广电号为空的设备广电号', 'BOOT接口，更新广电号为空的设备广电号');

drop table if exists bss_device_abnormal_log;

/*==============================================================*/
/* Table: bss_device_abnormal_log                            */
/*==============================================================*/
create table bss_device_abnormal_log
(
   id                   bigint(19)  not null auto_increment,
   original_device_id   bigint(19)  default NULL,
   device_code          varchar(32) default NULL,
   ysten_id             varchar(64) default NULL,
   mac                  varchar(32) default NULL,
   original_data_status char(1) default NULL,
   status               char(1) default NULL,
   operation            varchar(32) default NULL,
   create_date          datetime default NULL,
   service_name         varchar(32) DEFAULT NULL,
   message              varchar(1024) DEFAULT NULL,
   primary key (id)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=0
ROW_FORMAT=COMPACT;

alter table bss_device_abnormal_log comment '异常设备记录表';
