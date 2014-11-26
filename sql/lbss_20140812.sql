--add column packageId for the table bss_pp_info--
alter table bss_pp_info add column `packageId` bigint(19) DEFAULT NULL COMMENT '产品包id';


--add the data for the two tables with adding the button on the page of device_group_list --
insert into bss_authority (code,name,description,parent_id)values('device_list_of_group','显示设备列表','显示设备列表',157);

insert into bss_role_authority_map(role_id,authority_id) VALUES(1,308);

--add column district_code for the table bss_device_group --
ALTER TABLE bss_device_group ADD COLUMN district_code varchar(6) DEFAULT NULL AFTER name;