--增加终端升级导出功能
INSERT INTO bss_authority(code,name,description,parent_id)
VALUES('export_device_upgrade_result_log','导出','导出',(select ba.id from bss_authority ba where ba.code = 'device_upgrade_result_log_info'));
INSERT INTO bss_role_authority_map(role_id,authority_id)VALUES(1, (select id from bss_authority ba where ba.code = 'export_device_upgrade_result_log'));

--增加APK升级导出功能
INSERT INTO bss_authority(code,name,description,parent_id)
VALUES('export_apk_upgrade_result_log','导出','导出',(select ba.id from bss_authority ba where ba.code = 'apk_upgrade_result_log_list'));
INSERT INTO bss_role_authority_map(role_id,authority_id)VALUES(1, (select id from bss_authority ba where ba.code = 'export_apk_upgrade_result_log'));

--设备升级日志和apk升级日志表 增加地市字段
alter table bss_apk_upgrade_result_log add column district_code  varchar(32)  DEFAULT NULL  AFTER  ysten_id;
alter table bss_device_upgrade_result_log add column district_code  varchar(32)  DEFAULT NULL  AFTER  ysten_id;
