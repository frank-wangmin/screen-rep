/*设备表修改地市关联字段*/
ALTER TABLE `bss_device`
CHANGE COLUMN `city` `city_id`  int(10) NULL DEFAULT NULL AFTER `is_lock`;
/*修改设备表运营商字段名称*/
ALTER TABLE `bss_device`
CHANGE COLUMN `sp_code` `sp_define_id`  int(10) NULL DEFAULT NULL AFTER `city_id`;
ALTER TABLE `bss_screen_manager`
MODIFY COLUMN `id`  bigint(16) NOT NULL AUTO_INCREMENT FIRST ;
/*修改用户定时统计表*/
ALTER TABLE `bss_user_activate_day_sum`
	CHANGE COLUMN `sync` VARCHAR(50) NULL DEFAULT `NOSYNC`;