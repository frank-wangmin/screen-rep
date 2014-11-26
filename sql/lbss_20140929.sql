-- 预览模板表中增加字段district_code
ALTER TABLE `bss_preview_template`
ADD COLUMN `district_code`  varchar(6) NULL AFTER `epg_template_id`;

-- 删除用户终端表的旧设备Id,旧设备编号，旧易视腾编号，用户Id
ALTER TABLE `bss_customer_device_history`
DROP COLUMN `old_device_id`,
DROP COLUMN `old_ysten_id`,
DROP COLUMN `old_device_code`,
DROP COLUMN `customer_id`;

-- 新增用户终端表表字段
ALTER TABLE `bss_customer_device_history`
ADD COLUMN `device_activate_date`  datetime NULL AFTER `device_code`,
ADD COLUMN `customer_outer_code`  varchar(6) NULL AFTER `user_id`,
ADD COLUMN `login_name`  varchar(32) NULL AFTER `customer_outer_code`,
ADD COLUMN `phone`  varchar(128) NULL AFTER `login_name`,
ADD COLUMN `customer_activate_date`  datetime NULL AFTER `phone`,
ADD COLUMN `customer_create_date`  datetime NULL AFTER `customer_activate_date`,
ADD COLUMN `city_id` bigint(19) NULL AFTER `customer_create_date`,
ADD COLUMN `area_id` bigint(19) NULL AFTER `city_id`,
ADD COLUMN `description`  varchar(32) NULL AFTER `create_date`;
