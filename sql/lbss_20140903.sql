
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

