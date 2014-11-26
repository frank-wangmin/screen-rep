/*为bss_device表添加分配状态*/
ALTER TABLE `bss_device`
	ADD COLUMN `distribute_state` VARCHAR(32) NULL DEFAULT NULL AFTER `bind_type`;