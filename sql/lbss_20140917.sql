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


