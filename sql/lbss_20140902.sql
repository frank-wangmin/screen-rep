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





