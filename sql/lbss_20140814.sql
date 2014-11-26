
--add column district_code for the table bss_device --
ALTER TABLE bss_device ADD COLUMN district_code varchar(6) DEFAULT NULL AFTER area_id;

--add column district_code for the table bss_customer --
ALTER TABLE bss_customer ADD COLUMN district_code varchar(6) DEFAULT NULL AFTER area_id;

--delete 软件号， 软件包下发功能，面板下发，同步中心数据 --
delete from bss_authority where code in ('distribute_soft_code','distribute_soft_package','panel_panel_distribute','syns_center_panel_data');
delete from bss_role_authority_map where authority_id in (243,244,297,305);


