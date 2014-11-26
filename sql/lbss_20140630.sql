--update the table bss_panel_package and table bss_panel---
ALTER TABLE bss_panel_package ADD COLUMN package_type int(2);
ALTER TABLE bss_panel ADD COLUMN is_custom int(1);


--update the table bss_customer_device_history ---
/* 新易视腾编号 */
alter table bss_customer_device_history add column ysten_id varchar(32);
/* 旧易视腾编号 */
alter table bss_customer_device_history add column old_ysten_id varchar(32);