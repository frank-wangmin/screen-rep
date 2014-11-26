/*修改表bss_product_content结构*/
ALTER TABLE `bss_product_content`
DROP COLUMN `content_type`,
DROP COLUMN `index_type`,
DROP COLUMN `base_price_id`,
DROP COLUMN `sp_id`,
CHANGE COLUMN `cp_id` `cp_code`  bigint(19) NULL DEFAULT NULL AFTER `outter_type`,
ADD COLUMN `publish_date`  datetime NULL AFTER `create_date`,
ADD COLUMN `create_operator_name`  varchar(32) NULL AFTER `publish_date`,
ADD COLUMN `ppv_code`  varchar(32) NULL AFTER `create_operator_name`;
/*修改表bss_product_content字段类型*/
ALTER TABLE `bss_product_content`
MODIFY COLUMN `cp_code`  varchar(32) NULL DEFAULT NULL AFTER `outter_type`;
/*表bss_product_content添加字段*/
ALTER TABLE `bss_product_content`
ADD COLUMN `score`  float(4,1) NULL AFTER `description`,
ADD COLUMN `small_poster_addr`  varchar(1024) NULL AFTER `score`,
ADD COLUMN `poster`  varchar(1024) NULL AFTER `small_poster_addr`,
ADD COLUMN `big_poster_addr`  varchar(1024) NULL AFTER `poster`;

ALTER TABLE `bss_account_detail`
MODIFY COLUMN `id`  bigint(19) NOT NULL FIRST ;

ALTER TABLE `bss_area`
MODIFY COLUMN `id`  bigint(19) NOT NULL AUTO_INCREMENT FIRST ;

ALTER TABLE `bss_account`
MODIFY COLUMN `id`  int(19) NOT NULL AUTO_INCREMENT FIRST ;

ALTER TABLE `bss_authority`
CHANGE COLUMN `ID` `id`  bigint(19) NOT NULL AUTO_INCREMENT COMMENT '主键' FIRST ;

ALTER TABLE `bss_customer`
MODIFY COLUMN `id`  bigint(19) NOT NULL AUTO_INCREMENT FIRST ;

ALTER TABLE `bss_customer_device_history`
MODIFY COLUMN `id`  bigint(19) NOT NULL AUTO_INCREMENT FIRST ;

ALTER TABLE `bss_device`
MODIFY COLUMN `id`  bigint(19) NOT NULL AUTO_INCREMENT FIRST ;

ALTER TABLE `bss_device_customer_account_map`
MODIFY COLUMN `customer_id`  bigint(19) NULL DEFAULT NULL AFTER `id`,
MODIFY COLUMN `device_id`  bigint(19) NULL DEFAULT NULL AFTER `customer_code`,
MODIFY COLUMN `account_id`  bigint(19) NULL DEFAULT NULL AFTER `device_code`;

ALTER TABLE `bss_device_ip`
MODIFY COLUMN `id`  bigint(19) NOT NULL AUTO_INCREMENT FIRST ;

ALTER TABLE `bss_customer_device_history`
MODIFY COLUMN `device_id`  bigint(19) NULL DEFAULT NULL AFTER `id`,
MODIFY COLUMN `old_device_id`  bigint(19) NULL DEFAULT NULL AFTER `device_code`,
MODIFY COLUMN `customer_id`  bigint(19) NULL DEFAULT NULL AFTER `old_device_code`;

ALTER TABLE `bss_device`
MODIFY COLUMN `area_id`  bigint(19) NULL DEFAULT NULL AFTER `bind_type`,
MODIFY COLUMN `device_vendor_id`  bigint(19) NULL DEFAULT NULL AFTER `area_id`,
MODIFY COLUMN `device_type_id`  bigint(19) NULL DEFAULT NULL AFTER `device_vendor_id`,
MODIFY COLUMN `city_id`  bigint(19) NULL DEFAULT NULL AFTER `is_lock`,
MODIFY COLUMN `sp_define_id`  bigint(19) NULL DEFAULT NULL AFTER `city_id`;

ALTER TABLE `bss_device_type`
MODIFY COLUMN `id`  bigint(19) NOT NULL AUTO_INCREMENT FIRST ,
MODIFY COLUMN `device_vendor_id`  bigint(19) NULL DEFAULT NULL AFTER `code`;

ALTER TABLE `bss_device_vendor`
MODIFY COLUMN `id`  bigint(19) NOT NULL AUTO_INCREMENT FIRST ;

ALTER TABLE `bss_discount_policy_define_product`
MODIFY COLUMN `id`  bigint(19) NOT NULL AUTO_INCREMENT FIRST ,
MODIFY COLUMN `group_id`  bigint(19) NULL DEFAULT NULL AFTER `discount_par2`;

ALTER TABLE `bss_interface_log`
MODIFY COLUMN `id`  bigint(19) NOT NULL AUTO_INCREMENT FIRST ;

ALTER TABLE `bss_jms_task_record`
MODIFY COLUMN `id`  bigint(19) NOT NULL AUTO_INCREMENT FIRST ;

ALTER TABLE `bss_operator`
CHANGE COLUMN `ID` `id`  bigint(19) NOT NULL AUTO_INCREMENT COMMENT '用户id' FIRST ,
CHANGE COLUMN `EMAIL` `email`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户邮箱地址' AFTER `id`,
CHANGE COLUMN `LOGIN_NAME` `login_name`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登陆的用户名' AFTER `email`,
CHANGE COLUMN `DISPLAY_NAME` `display_name`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名' AFTER `login_name`,
CHANGE COLUMN `STATE` `state`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `display_name`,
CHANGE COLUMN `PASSWORD` `password`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码' AFTER `state`;

ALTER TABLE `bss_operator_role_map`
CHANGE COLUMN `ID` `id`  bigint(19) NOT NULL AUTO_INCREMENT FIRST ,
CHANGE COLUMN `OPERATOR_ID` `operator_id`  bigint(19) NULL DEFAULT NULL COMMENT '用户id' AFTER `id`,
CHANGE COLUMN `ROLE_ID` `role_id`  bigint(19) NULL DEFAULT NULL COMMENT '角色d' AFTER `operator_id`;

ALTER TABLE `bss_ppv_info`
MODIFY COLUMN `id`  bigint(19) NOT NULL AUTO_INCREMENT FIRST ;

ALTER TABLE `bss_product_content`
MODIFY COLUMN `outter_code`  bigint(19) NULL DEFAULT NULL AFTER `name`;

ALTER TABLE `bss_product_item_map`
CHANGE COLUMN `ID` `id`  bigint(19) NOT NULL FIRST ;

ALTER TABLE `bss_program_bitrate_record`
MODIFY COLUMN `id`  bigint(19) NOT NULL AUTO_INCREMENT FIRST ,
MODIFY COLUMN `program_rate_id`  bigint(19) NOT NULL AFTER `id`,
MODIFY COLUMN `program_id`  bigint(19) NULL DEFAULT NULL AFTER `bitrate_id`;

ALTER TABLE `bss_program_record`
MODIFY COLUMN `id`  bigint(19) NOT NULL FIRST ;

ALTER TABLE `bss_program_series_record`
MODIFY COLUMN `id`  bigint(19) NOT NULL FIRST ,
MODIFY COLUMN `program_series_id`  bigint(19) NOT NULL AFTER `id`;

ALTER TABLE `bss_program_series_relation_record`
MODIFY COLUMN `id`  bigint(19) NOT NULL FIRST ,
MODIFY COLUMN `rel_id`  bigint(19) NOT NULL AFTER `id`,
MODIFY COLUMN `program_series_id`  bigint(19) NULL DEFAULT NULL AFTER `rel_id`,
MODIFY COLUMN `program_id`  bigint(19) NULL DEFAULT NULL AFTER `program_series_id`;

ALTER TABLE `bss_role`
MODIFY COLUMN `id`  bigint(19) NOT NULL AUTO_INCREMENT COMMENT '角色id' FIRST ,
CHANGE COLUMN `NAME` `name`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称' AFTER `id`;

ALTER TABLE `bss_role_authority_map`
CHANGE COLUMN `ID` `id`  bigint(19) NOT NULL AUTO_INCREMENT FIRST ,
CHANGE COLUMN `ROLE_ID` `role_id`  bigint(19) NULL DEFAULT NULL COMMENT '角色id' AFTER `id`,
CHANGE COLUMN `AUTHORITY_ID` `authority_id`  bigint(19) NULL DEFAULT NULL COMMENT '权限id' AFTER `role_id`;

ALTER TABLE `bss_screen_manager`
MODIFY COLUMN `id`  bigint(19) NOT NULL AUTO_INCREMENT FIRST ;

ALTER TABLE `bss_settle_ratio`
MODIFY COLUMN `id`  bigint(19) NOT NULL FIRST ;

ALTER TABLE `bss_sys_menu`
MODIFY COLUMN `id`  bigint(19) NOT NULL AUTO_INCREMENT FIRST ,
MODIFY COLUMN `authority_id`  bigint(19) NULL DEFAULT NULL AFTER `id`,
MODIFY COLUMN `parent_id`  bigint(19) NULL DEFAULT NULL AFTER `order_sq`;

ALTER TABLE `bss_talk_system`
MODIFY COLUMN `id`  bigint(19) NOT NULL AUTO_INCREMENT FIRST ;

ALTER TABLE `bss_user_activate_day_sum`
MODIFY COLUMN `id`  bigint(19) NOT NULL AUTO_INCREMENT FIRST ;

ALTER TABLE `bss_user_package_info`
MODIFY COLUMN `id`  bigint(19) NOT NULL AUTO_INCREMENT FIRST ;

ALTER TABLE `system_config`
MODIFY COLUMN `id`  bigint(19) NOT NULL AUTO_INCREMENT COMMENT '主键id' FIRST ;




