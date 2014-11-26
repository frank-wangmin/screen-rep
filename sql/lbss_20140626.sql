
alter table bss_device add column ysten_id varchar(32);

alter table bss_device_group_map change device_code  ysten_id varchar(32);

alter table bss_device_customer_account_map add column ysten_id varchar(32);

alter table bss_background_image_device_map change device_code  ysten_id varchar(32);

alter table bss_service_collect_device_group_map change device_code  ysten_id varchar(32);

alter table bss_animation_device_map change device_code  ysten_id varchar(32);

alter table  bss_device_notice_map change device_code  ysten_id varchar(32);
alter table bss_device_upgrade_map change device_code  ysten_id varchar(32);