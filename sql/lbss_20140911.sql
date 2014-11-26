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