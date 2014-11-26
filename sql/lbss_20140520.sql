/*为bss_program_series_record表添加原因字段*/
ALTER TABLE `bss_program_series_record` ADD COLUMN `reason` text NULL DEFAULT NULL;
/*修改bss_program_record表reason字段为text类型*/
ALTER TABLE `bss_program_record` MODIFY COLUMN `reason`  text NULL DEFAULT NULL;
/*为bss_media_url表添加节目Id字段*/
ALTER TABLE `bss_media_url` ADD COLUMN `program_id` bigint(19) NULL DEFAULT NULL AFTER `id`;
/*为bss_device表添加版本号字段*/
ALTER TABLE `bss_device` ADD COLUMN `version_seq` int(11) NULL DEFAULT NULL AFTER `soft_code`;