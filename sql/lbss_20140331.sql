/*设备表的是否锁定字段有int型改为varchar*/
ALTER TABLE `bss_device`
MODIFY COLUMN `is_lock`  varchar(32) NULL DEFAULT NULL AFTER `description`;

/*将所有数据的是否锁定设置为未锁定*/
update bss_device set is_lock='UNLOCKED';

/*交互系统表添加创建时间和描述字段*/
ALTER TABLE `bss_talk_system`
ADD COLUMN `create_date`  timestamp NULL ON UPDATE CURRENT_TIMESTAMP AFTER `name`,
ADD COLUMN `description`  varchar(5000) NULL AFTER `create_date`;

/*交互系统主键设置为自动增长*/
ALTER TABLE `bss_talk_system`
MODIFY COLUMN `id`  bigint(16) NOT NULL AUTO_INCREMENT FIRST ;

/*定价基本表主键设置为自动增长*/
ALTER TABLE `bss_price_atom`
MODIFY COLUMN `ID`  bigint(19) NOT NULL AUTO_INCREMENT FIRST ;

/*修改地市表结构*/
ALTER TABLE `bss_city`
CHANGE COLUMN `city_value` `code`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL FIRST ,
CHANGE COLUMN `city_name` `name`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `code`,
ADD COLUMN `id`  bigint(19) NULL AFTER `name`,
ADD COLUMN `create_date`  timestamp NULL ON UPDATE CURRENT_TIMESTAMP AFTER `id`;

/*修改区域表结构*/
ALTER TABLE `bss_area`
DROP COLUMN `FID`,
DROP COLUMN `Grade`,
DROP COLUMN `Type`,
DROP COLUMN `FDNCode`,
CHANGE COLUMN `ID` `id`  bigint(11) NOT NULL FIRST ,
CHANGE COLUMN `CODE` `code`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `id`,
CHANGE COLUMN `Name` `name`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL AFTER `code`,
CHANGE COLUMN `STATE` `state`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `name`,
CHANGE COLUMN `MEMO` `memo`  varchar(5000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `state`,
CHANGE COLUMN `PARENT_NAME` `parent_name`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `memo`;
ALTER TABLE `bss_area`
MODIFY COLUMN `id`  bigint(11) NOT NULL AUTO_INCREMENT FIRST ;