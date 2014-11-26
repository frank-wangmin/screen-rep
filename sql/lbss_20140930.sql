ALTER TABLE `bss_upgrade_task`
ADD COLUMN `name`  varchar(256) NULL AFTER `id`;

ALTER TABLE `bss_app_upgrade_task`
ADD COLUMN `name`  varchar(256) NULL AFTER `id`;
