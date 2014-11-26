/*为bss_device表添加软件号*/
ALTER TABLE `bss_device`
	ADD COLUMN `soft_code` varchar(64) NULL DEFAULT NULL AFTER `sp_define_id`;