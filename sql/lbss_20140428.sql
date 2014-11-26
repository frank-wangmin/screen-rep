/*为pp info表添加海报地址*/
ALTER TABLE `bss_pp_info`
	ADD COLUMN `img_addr` VARCHAR(500) NULL DEFAULT NULL AFTER `call_back_date`;