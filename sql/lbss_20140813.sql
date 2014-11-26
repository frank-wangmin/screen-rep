--add program and series of onLine and offLine
insert into bss_authority (code,name,description,parent_id)values('onLine_program',' 上线','节目上线',11);

insert into bss_authority (code,name,description,parent_id)values('offLine_program',' 下线','节目下线',11);

insert into bss_authority (code,name,description,parent_id)values('onLine_series',' 上线','节目集上线',10);

insert into bss_authority (code,name,description,parent_id)values('offLine_series',' 下线','节目集下线',10);

--权限
insert into bss_role_authority_map(role_id,authority_id) VALUES(1,309);

insert into bss_role_authority_map(role_id,authority_id) VALUES(1,310);

insert into bss_role_authority_map(role_id,authority_id) VALUES(1,311);

insert into bss_role_authority_map(role_id,authority_id) VALUES(1,312);


--add column ref_panel_id
ALTER TABLE `bss_panel`
ADD COLUMN `ref_panel_id`  bigint(19) NULL AFTER `is_custom`;

--delete device manager :pickup sync distribute distributeByIds
delete  from bss_authority where code in ('device_pickup', 'device_sync' ,'device_distribute1' ,'device_distribute' );
delete from bss_role_authority_map where authority_id in (160,182,207,159);

--清理垃圾数据：用户组产品包类型的 | 绑定产品包类型的用户关系表记录  | 绑定产品包类型用户组的面板包关系表记录[先删除外键表]
delete  from  bss_user_group_map where user_group_id in (select id from bss_user_group where type = 'PRODUCTPACKAGE');
delete  from  bss_panel_package_user_map  where user_group_id in (select id from bss_user_group where type = 'PRODUCTPACKAGE');
delete  from  bss_user_group where type = 'PRODUCTPACKAGE' ;


