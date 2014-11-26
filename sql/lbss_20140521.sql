/*为bss_device_group表添加更新时间字段*/
ALTER TABLE `bss_device_group` ADD COLUMN `update_date` datetime NULL DEFAULT NULL;