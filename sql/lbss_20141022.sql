-- 增加 电视看点地址来源 --
ALTER TABLE `bss_panel_package`
ADD COLUMN `district_code`  varchar(6) NULL AFTER `package_type`;
--消息增加类型字段：系统到期提醒、自定义消息--
ALTER TABLE `bss_sys_notice`
ADD COLUMN `type`  varchar(32) NULL AFTER `update_date`;
--消息增加区域字段：多个区域使用逗号分隔
ALTER TABLE `bss_sys_notice`
ADD COLUMN `district_code`  varchar(600) NULL AFTER `type`;

--栏目运营各省URL，字典表--
create table lvoms_district_code_url
(
   id                   bigint(16) not null auto_increment,
   district_code        varchar(6),
   url                  varchar(250),
   primary key (id)
);

alter table lvoms_district_code_url comment '栏目运营各省URL，字典表。';