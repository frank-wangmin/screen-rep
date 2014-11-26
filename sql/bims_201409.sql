-- add column nav_name for the table bss_panel_nav_define --
ALTER TABLE `bss_panel_nav_define`
ADD COLUMN `nav_name`  varchar(256) NULL AFTER `nav_type`;


-- add datas for the table system_config --
insert into system_config(configkey,configvalue,zhname,depiction)
values ('TVsetActionUrl','http://sns.is.ysten.com/CNTV/index.html?action=detail&object=','节目集的播放地址前缀','节目集的播放地址前缀');

insert into system_config(configkey,configvalue,zhname,depiction)
values ('programActionUrl','http://sns.is.ysten.com/CNTV/index.html?action=EPGList&object=','栏目的播放地址前缀','栏目的播放地址前缀');

-- add column area_id for the table bss_user_group
ALTER TABLE `bss_user_group`
ADD COLUMN `area_id`  bigint(19) NULL DEFAULT NULL AFTER `update_date`;

-- update and add the bind and unbind button both user and device

update bss_authority set description='绑定业务---设备，设备分组' where code ='bind_sys_notice';

update bss_authority set description='解绑业务---设备，设备分组' where code ='unbind_sys_notice';

insert into bss_authority(code,name,description,parent_id,sort_order) VALUES('bind_user_sys_notice','绑定业务','绑定业务---用户，用户分组',158,0);

insert into bss_authority(code,name,description,parent_id,sort_order) VALUES('unbind_user_sys_notice','解绑业务','解绑业务---用户，用户分组',158,0);

insert into bss_role_authority_map(role_id,authority_id) values(1,315);

insert into bss_role_authority_map(role_id,authority_id) values(1,316);

-- boot_animation
update bss_authority set description='绑定业务---设备，设备分组' where code ='boot_animation_bind';

update bss_authority set description='解绑业务---设备，设备分组' where code ='boot_animation_unbind';

insert into bss_authority(code,name,description,parent_id,sort_order) VALUES('boot_animation_bind_user','绑定业务','绑定业务---用户，用户分组',178,0);

insert into bss_authority(code,name,description,parent_id,sort_order) VALUES('boot_animation_unbind_user','解绑业务','解绑业务---用户，用户分组',178,0);

insert into bss_role_authority_map(role_id,authority_id) values(1,317);

insert into bss_role_authority_map(role_id,authority_id) values(1,318);

-- background image

update bss_authority set description='绑定业务---设备，设备分组' where code ='background_bind';

update bss_authority set description='解绑业务---设备，设备分组' where code ='background_unbind';

insert into bss_authority(code,name,description,parent_id,sort_order) VALUES('background_bind_user','绑定业务','绑定业务---用户，用户分组',185,0);

insert into bss_authority(code,name,description,parent_id,sort_order) VALUES('background_unbind_user','解绑业务','解绑业务---用户，用户分组',185,0);

insert into bss_role_authority_map(role_id,authority_id) values(1,319);

insert into bss_role_authority_map(role_id,authority_id) values(1,320);



-- Panel_package
update bss_authority set description='绑定业务---设备，设备分组' where code ='bind_panel_package_business';

update bss_authority set description='解绑业务---设备，设备分组' where code ='unbind_panel_package_business';

insert into bss_authority(code,name,description,parent_id,sort_order) VALUES('bind_panel_package_business_user','绑定业务','绑定业务---用户，用户分组',226,0);

insert into bss_authority(code,name,description,parent_id,sort_order) VALUES('unbind_panel_package_business_user','解绑业务','解绑业务---用户，用户分组',226,0);

insert into bss_role_authority_map(role_id,authority_id) values(1,321);

insert into bss_role_authority_map(role_id,authority_id) values(1,322);

-- app_upgrade_task
update bss_authority set description='绑定业务---设备，设备分组' where code ='unbind_upgrade_task';

update bss_authority set description='解绑业务---设备，设备分组' where code ='bind_upgrade_task';

insert into bss_authority(code,name,description,parent_id,sort_order) VALUES('unbind_user_upgrade_task','绑定业务','绑定业务---用户，用户分组',200,0);

insert into bss_authority(code,name,description,parent_id,sort_order) VALUES('bind_user_upgrade_task','解绑业务','解绑业务---用户，用户分组',200,0);

insert into bss_role_authority_map(role_id,authority_id) values(1,323);

insert into bss_role_authority_map(role_id,authority_id) values(1,324);

--- 用户分组查看所包含的用户列表
insert into bss_authority(code,name,description,parent_id,sort_order) VALUES('customer_list_of_group','显示用户列表','显示用户列表',208,0);

insert into bss_role_authority_map(role_id,authority_id) values(1,325);

-- delete the column display and is_lock of table panel --
ALTER TABLE `bss_panel`
DROP COLUMN `display`,
DROP COLUMN `is_lock`;

-- add the column display and is_lock of table bss_panel_package_panel_map
ALTER TABLE `bss_panel_package_panel_map`
ADD COLUMN `display`  varchar(32) default 'true' AFTER `panel_logo`,
ADD COLUMN `is_lock`  varchar(32) default 'false' AFTER `display`;

--导出终端统计表
insert into bss_authority (code,name,description,parent_id)values('export_device_statistics','终端激活数据导出','终端激活数据导出',37);
insert into bss_role_authority_map(role_id,authority_id) VALUES(1,326 );

--导出用户统计表
insert into bss_authority (code,name,description,parent_id)values('export_user_statistics','用户开户数据导出','用户开户数据导出',38);
insert into bss_role_authority_map(role_id,authority_id) VALUES(1,327 );

--导出用户统计表
insert into bss_authority (code,name,description,parent_id)values('export_user_activate_day_sum_statistics','终端激活定时统计数据导出','终端激活定时统计数据导出',39);
insert into bss_role_authority_map(role_id,authority_id) VALUES(1,328 );

--终端表加用户表主键字段
alter table bss_device add column customer_id  bigint(19)   DEFAULT NULL  AFTER  ysten_id

--bss_animation_user_map表字段变更
alter table bss_animation_user_map change user_id  code varchar(32)

--bss_user_group_map
alter table bss_user_group_map change user_id  code varchar(32)

--bss_background_image_user_map
alter table bss_background_image_user_map change user_id  code varchar(32)

--bss_panel_package_user_map
alter table bss_panel_package_user_map change user_id  code varchar(32)

--bss_user_notice_map
alter table bss_user_notice_map change user_id  code varchar(32)

--用户表增加外部用户编号字段
alter table bss_customer add column outer_code  varchar(32)  DEFAULT NULL  AFTER  code

INSERT into bss_authority(code,name,description,parent_id) VALUES('boot_animation_device_list','显示设备列表','显示设备列表',178);
INSERT into bss_authority(code,name,description,parent_id) VALUES('boot_animation_user_list','显示用户列表','显示用户列表',178);
INSERT into bss_authority(code,name,description,parent_id) VALUES('sys_notice_user_list','显示用户列表','显示用户列表',158);
INSERT into bss_authority(code,name,description,parent_id) VALUES('sys_notice_device_list','显示设备列表','显示设备列表',158);
INSERT into bss_authority(code,name,description,parent_id) VALUES('upgrade_task_device_list','显示设备列表','显示设备列表',220);
INSERT into bss_authority(code,name,description,parent_id) VALUES('upgrade_task_user_list','显示用户列表','显示用户列表',220);
INSERT into bss_authority(code,name,description,parent_id) VALUES('panel_package_user_list','显示用户列表','显示用户列表',226);
INSERT into bss_authority(code,name,description,parent_id) VALUES('panel_package_device_list','显示设备列表','显示设备列表',226);
INSERT into bss_authority(code,name,description,parent_id) VALUES('service_collect_device_list','显示设备列表','显示设备列表',184);
INSERT into bss_authority(code,name,description,parent_id) VALUES('background_device_list','显示设备列表','显示设备列表',185);
INSERT into bss_authority(code,name,description,parent_id) VALUES('background_user_list','显示用户列表','显示用户列表',185);


INSERT into bss_role_authority_map(role_id,authority_id) VALUES(1,326);
INSERT into bss_role_authority_map(role_id,authority_id) VALUES(1,327);
INSERT into bss_role_authority_map(role_id,authority_id) VALUES(1,328);
INSERT into bss_role_authority_map(role_id,authority_id) VALUES(1,329);
INSERT into bss_role_authority_map(role_id,authority_id) VALUES(1,330);
INSERT into bss_role_authority_map(role_id,authority_id) VALUES(1,331);
INSERT into bss_role_authority_map(role_id,authority_id) VALUES(1,332);
INSERT into bss_role_authority_map(role_id,authority_id) VALUES(1,333);
INSERT into bss_role_authority_map(role_id,authority_id) VALUES(1,334);
INSERT into bss_role_authority_map(role_id,authority_id) VALUES(1,335);
INSERT into bss_role_authority_map(role_id,authority_id) VALUES(1,336);


-- add index for the column device_group_id--
ALTER TABLE `bss_device_group_map`
ADD INDEX `device_group_id_idx` (`device_group_id`) USING BTREE ;

-- add index for the column boot_animation_id--
ALTER TABLE `bss_animation_device_map`
ADD INDEX `boot_animation_id_idx` (`boot_animation_id`) USING BTREE ;


-- add index for the column background_image_id--
ALTER TABLE `bss_background_image_user_map`
ADD INDEX `background_image_id_idx` (`background_image_id`) USING BTREE ;

ALTER TABLE `bss_background_image_device_map`
ADD INDEX `background_image_id_idx` (`background_image_id`) USING BTREE ;

-- add index for the column notice_id--
ALTER TABLE `bss_user_notice_map`
ADD INDEX `notice_id_idx` (`notice_id`) USING BTREE ;

ALTER TABLE `bss_device_notice_map`
ADD INDEX `notice_id_idx` (`notice_id`) USING BTREE ;


-- add index for the column upgrade_id--
ALTER TABLE `bss_device_upgrade_map`
ADD INDEX `upgrade_id_idx` (`upgrade_id`) USING BTREE ;


-- add index for the column panel_package_id--
ALTER TABLE `bss_panel_package_device_map`
ADD INDEX `panel_package_id_idx` (`panel_package_id`) USING BTREE ;

ALTER TABLE `bss_panel_package_user_map`
ADD INDEX `panel_package_id_idx` (`panel_package_id`) USING BTREE ;

-- add index for the column service_collect_id--
ALTER TABLE `bss_service_collect_device_group_map`
ADD INDEX `service_collect_id_idx` (`service_collect_id`) USING BTREE ;

-- add datas for the table system_config --
insert into system_config(configkey,configvalue,zhname,depiction)
values ('updateResultUrl','http://192.168.1.1:8080/yst-bims-facade/','升级结果反馈接口地址','升级结果反馈接口地址');

--配置产品包查询接口URL【configvalue：根据部署环境自行填写】
insert into system_config(configkey,configvalue,zhname,depiction)
values ('selectPpList','http://192.168.2.251:8080/yst-lbss-api/stb/getAllProducts','产品包查询接口URL','产品包查询接口URL');

--bss_customer_device_history 增加 user_id 字段
alter table bss_customer_device_history add column user_id  varchar(32)  DEFAULT NULL  AFTER  customer_code;

-- 同步失败数据记录表
CREATE TABLE `bss_sync_data_log` (
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

--去掉设备选中导出操作
delete from  bss_role_authority_map where authority_id = ( select id from  bss_authority where code = 'export_device1' );
delete from  bss_authority where code = 'export_device1';










