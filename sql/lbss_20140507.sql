/*为bss_device表添加生产批次号*/
ALTER TABLE `bss_device`
	ADD COLUMN `product_no` varchar(32) NULL DEFAULT NULL AFTER `sp_define_id`;
/*为bss_device表添加是否需要同步*/
ALTER TABLE `bss_device`
	ADD COLUMN `is_sync` varchar(32) NULL DEFAULT NULL AFTER `product_no`;