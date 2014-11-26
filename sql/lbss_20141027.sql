alter table lvoms_district_code_url add column `status` varchar(32)  AFTER `url`;


/*********************************************************************/

INSERT INTO bss_authority(code,name,description,parent_id)
VALUES('lvoms_district_code_url_manager','电视看点栏目运营商各省推荐URL信息','电视看点栏目运营商各省推荐URL信息',(select ba.id from bss_authority ba where ba.code = 'system_manager'));

INSERT INTO bss_sys_menu(authority_id, name, url, order_sq, parent_id, description)
VALUES( (select id from bss_authority ba where ba.code = 'lvoms_district_code_url_manager'), '电视看点栏目运营商各省推荐URL信息', 'to_lvoms_district_code_url_list.shtml', 2, (select bm.id from bss_sys_menu bm where bm.name = '系统管理'), '电视看点栏目运营商各省推荐URL信息');

INSERT INTO bss_role_authority_map(role_id,authority_id)VALUES(1, (select id from bss_authority ba where ba.code = 'lvoms_district_code_url_manager'));

ALTER TABLE `bss_panel_package_panel_map`
ADD COLUMN `opr_userid`  bigint(19) NULL DEFAULT NULL AFTER `is_lock`,
ADD COLUMN `create_time`  datetime NULL DEFAULT NULL AFTER `opr_userid`,
ADD COLUMN `update_time`  datetime NULL DEFAULT NULL AFTER `create_time`;

