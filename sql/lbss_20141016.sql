-- add character appEnterUrl and item_content_type for the table bss_panel_item --
ALTER TABLE `bss_panel_item`
ADD COLUMN `app_enter_url`  varchar(255) NULL AFTER `district_code`,
ADD COLUMN `item_content_type`  varchar(64) NULL AFTER `app_enter_url`;

ALTER TABLE `bss_panel_item`
ADD COLUMN `category_id`  bigint(19) NULL AFTER `item_content_type`;

INSERT INTO bss_authority(code,name,description,parent_id)
VALUES('apk_upgrade_result_log_list','APK升级日志','APK升级日志',9);
INSERT INTO bss_sys_menu(authority_id, name, url, order_sq, parent_id, description)
VALUES( (select id from bss_authority ba where ba.code = 'apk_upgrade_result_log_list'), 'APK升级日志', 'to_apk_upgrade_result_log_list.shtml', 11, 9, 'APK升级日志');
INSERT INTO bss_role_authority_map(role_id,authority_id)VALUES(1, (select id from bss_authority ba where ba.code = 'apk_upgrade_result_log_list'));

INSERT INTO bss_authority(code,name,description,parent_id)
VALUES('apk_upgrade_manager','APK升级维护','APK升级维护',(select ba.id from bss_authority ba where ba.code = 'upgrade_info'));
INSERT INTO bss_sys_menu(authority_id, name, url, order_sq, parent_id, description)
VALUES( (select id from bss_authority ba where ba.code = 'apk_upgrade_manager'), 'APK升级维护', 'apk_software_package_upgrade_page.shtml', 3, (select bm.id from bss_sys_menu bm where bm.name = '升级管理'), 'APK升级维护');
INSERT INTO bss_role_authority_map(role_id,authority_id)VALUES(1, (select id from bss_authority ba where ba.code = 'apk_upgrade_manager'));

INSERT INTO bss_authority(code,name,description,parent_id)
VALUES('apk_software_code_manager','APK软件号维护','APK软件号维护',(select ba.id from bss_authority ba where ba.code = 'apk_upgrade_manager'));
INSERT INTO bss_sys_menu(authority_id, name, url, order_sq, parent_id, description)
VALUES( (select id from bss_authority ba where ba.code = 'apk_software_code_manager'), 'APK软件号维护', 'to_apk_software_code_list.shtml', 1, (select bm.id from bss_sys_menu bm where bm.name = 'APK升级维护'), 'APK软件号维护');
INSERT INTO bss_role_authority_map(role_id,authority_id)VALUES(1, (select id from bss_authority ba where ba.code = 'apk_software_code_manager'));
INSERT INTO bss_authority(code,name,description,parent_id)
VALUES('add_apk_soft_code','新增','新增',(select ba.id from bss_authority ba where ba.code = 'apk_software_code_manager'));
INSERT INTO bss_authority(code,name,description,parent_id)
VALUES('update_apk_soft_code','修改','修改',(select ba.id from bss_authority ba where ba.code = 'apk_software_code_manager'));
INSERT INTO bss_role_authority_map(role_id,authority_id)VALUES(1, (select id from bss_authority ba where ba.code = 'add_apk_soft_code'));
INSERT INTO bss_role_authority_map(role_id,authority_id)VALUES(1, (select id from bss_authority ba where ba.code = 'update_apk_soft_code'));

INSERT INTO bss_authority(code,name,description,parent_id)
VALUES('apk_software_package_manager','APK软件包维护','APK软件包维护',(select ba.id from bss_authority ba where ba.code = 'apk_upgrade_manager'));
INSERT INTO bss_sys_menu(authority_id, name, url, order_sq, parent_id, description)
VALUES( (select id from bss_authority ba where ba.code = 'apk_software_package_manager'), 'APK软件包维护', 'to_apk_software_package_list.shtml', 2, (select bm.id from bss_sys_menu bm where bm.name = 'APK升级维护'), 'APK软件包维护');
INSERT INTO bss_role_authority_map(role_id,authority_id)VALUES(1, (select id from bss_authority ba where ba.code = 'apk_software_package_manager'));
INSERT INTO bss_authority(code,name,description,parent_id)
VALUES('add_apk_software_package','新增','新增',(select ba.id from bss_authority ba where ba.code = 'apk_software_package_manager'));
INSERT INTO bss_authority(code,name,description,parent_id)
VALUES('update_apk_software_package','修改','修改',(select ba.id from bss_authority ba where ba.code = 'apk_software_package_manager'));
INSERT INTO bss_role_authority_map(role_id,authority_id)VALUES(1, (select id from bss_authority ba where ba.code = 'add_apk_software_package'));
INSERT INTO bss_role_authority_map(role_id,authority_id)VALUES(1, (select id from bss_authority ba where ba.code = 'update_apk_software_package'));

INSERT INTO bss_authority(code,name,description,parent_id)
VALUES('apk_upgrade_task_manager','APK升级任务维护','APK升级任务维护',(select ba.id from bss_authority ba where ba.code = 'apk_upgrade_manager'));
INSERT INTO bss_sys_menu(authority_id, name, url, order_sq, parent_id, description)
VALUES( (select id from bss_authority ba where ba.code = 'apk_upgrade_task_manager'), 'APK升级任务维护', 'to_apk_upgrade_task_list.shtml', 3, (select bm.id from bss_sys_menu bm where bm.name = 'APK升级维护'), 'APK升级任务维护');
INSERT INTO bss_role_authority_map(role_id,authority_id)VALUES(1, (select id from bss_authority ba where ba.code = 'apk_upgrade_task_manager'));
INSERT INTO bss_authority(code,name,description,parent_id)
VALUES('add_apk_upgrade_task','新增','新增',(select ba.id from bss_authority ba where ba.code = 'apk_upgrade_task_manager'));
INSERT INTO bss_authority(code,name,description,parent_id)
VALUES('update_apk_upgrade_task','修改','修改',(select ba.id from bss_authority ba where ba.code = 'apk_upgrade_task_manager'));
INSERT INTO bss_authority(code,name,description,parent_id)
VALUES('delete_apk_upgrade_task','删除','删除',(select ba.id from bss_authority ba where ba.code = 'apk_upgrade_task_manager'));
INSERT INTO bss_authority(code,name,description,parent_id)
VALUES('apk_upgrade_task_bind','绑定设备和分组','绑定设备和分组',(select ba.id from bss_authority ba where ba.code = 'apk_upgrade_task_manager'));
INSERT INTO bss_authority(code,name,description,parent_id)
VALUES('apk_upgrade_task_unbind','解绑设备和分组','解绑设备和分组',(select ba.id from bss_authority ba where ba.code = 'apk_upgrade_task_manager'));
INSERT INTO bss_authority(code,name,description,parent_id)
VALUES('apk_upgrade_task_device_list','关联设备','关联设备',(select ba.id from bss_authority ba where ba.code = 'apk_upgrade_task_manager'));
INSERT INTO bss_role_authority_map(role_id,authority_id)VALUES(1, (select id from bss_authority ba where ba.code = 'add_apk_upgrade_task'));
INSERT INTO bss_role_authority_map(role_id,authority_id)VALUES(1, (select id from bss_authority ba where ba.code = 'update_apk_upgrade_task'));
INSERT INTO bss_role_authority_map(role_id,authority_id)VALUES(1, (select id from bss_authority ba where ba.code = 'delete_apk_upgrade_task'));
INSERT INTO bss_role_authority_map(role_id,authority_id)VALUES(1, (select id from bss_authority ba where ba.code = 'apk_upgrade_task_bind'));
INSERT INTO bss_role_authority_map(role_id,authority_id)VALUES(1, (select id from bss_authority ba where ba.code = 'apk_upgrade_task_unbind'));
INSERT INTO bss_role_authority_map(role_id,authority_id)VALUES(1, (select id from bss_authority ba where ba.code = 'apk_upgrade_task_device_list'));
