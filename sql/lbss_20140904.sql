-- delete the column display and is_lock of table panel --
ALTER TABLE `bss_panel`
DROP COLUMN `display`,
DROP COLUMN `is_lock`;

-- add the column display and is_lock of table bss_panel_package_panel_map
ALTER TABLE `bss_panel_package_panel_map`
ADD COLUMN `display`  varchar(32) default 'true' AFTER `panel_logo`,
ADD COLUMN `is_lock`  varchar(32) default 'false' AFTER `display`;

