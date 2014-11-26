
/***************************添加电视看点操作菜单和权限******************************************/

INSERT INTO bss_authority(code,name,description,parent_id)
VALUES('add_lvoms_district_code_url','新增','新增',(select ba.id from bss_authority ba where ba.code = 'system_manager'));
INSERT INTO bss_authority(code,name,description,parent_id)
VALUES('update_lvoms_district_code_url','修改','修改',(select ba.id from bss_authority ba where ba.code = 'system_manager'));
INSERT INTO bss_authority(code,name,description,parent_id)
VALUES('delete_lvoms_district_code_url','删除','删除',(select ba.id from bss_authority ba where ba.code = 'system_manager'));

INSERT INTO bss_role_authority_map(role_id,authority_id)VALUES(1, (select id from bss_authority ba where ba.code = 'add_lvoms_district_code_url'));
INSERT INTO bss_role_authority_map(role_id,authority_id)VALUES(1, (select id from bss_authority ba where ba.code = 'update_lvoms_district_code_url'));
INSERT INTO bss_role_authority_map(role_id,authority_id)VALUES(1, (select id from bss_authority ba where ba.code = 'delete_lvoms_district_code_url'));
