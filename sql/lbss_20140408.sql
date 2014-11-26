/*修改product_id为varchar类型，添加user_id字段*/
ALTER TABLE `bss_user_package_info`
	ADD COLUMN `user_id` VARCHAR(32) NULL DEFAULT NULL AFTER `id`,
	CHANGE COLUMN `product_id` `product_id` VARCHAR(32) NULL DEFAULT NULL AFTER `product_name`;

/*修改product_id为varchar类型，添加user_id字段*/
ALTER TABLE `bss_product_order`
	ADD COLUMN `user_id` VARCHAR(32) NULL DEFAULT NULL AFTER `state`,
	CHANGE COLUMN `product_id` `product_id` VARCHAR(32) NULL DEFAULT NULL AFTER `discount_policy_id`;

/*为pp_info表添加product_type字段*/
ALTER TABLE `bss_pp_info`
	ADD COLUMN `product_type` VARCHAR(32) NULL DEFAULT NULL AFTER `product_name`;