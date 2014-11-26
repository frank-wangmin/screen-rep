--add column online_status for the table bss_panel_package--
ALTER TABLE `bss_panel_package`
ADD COLUMN `online_status` int(1) NULL DEFAULT 99 AFTER `epg_style2`;

--add column online_status for the table bss_panel_nav_define--
ALTER TABLE `bss_panel_nav_define`
ADD COLUMN `online_status`  int(1) NULL DEFAULT 99 AFTER `create_time`;

--add column online_status for the table bss_panel_item--
ALTER TABLE `bss_panel_item`
ADD COLUMN `online_status`  int(1) NULL DEFAULT 99 AFTER `install_url`;

alter table bss_panel alter column display set default 'true';

alter table bss_panel alter column is_lock set default 'true';