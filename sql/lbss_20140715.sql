/*增加面板LOGO*/
ALTER TABLE `bss_panel_package_panel_map`
	ADD COLUMN `panel_logo` VARCHAR(256) NULL DEFAULT NULL AFTER `sort_num`