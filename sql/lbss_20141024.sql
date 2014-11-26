-- add column package_id for the table lvoms_district_code_url --
alter table `lvoms_district_code_url` add column `package_id` bigint(16)  AFTER `district_code`;

-- 增加操作者Id
ALTER TABLE `bss_preview_item_data`
ADD COLUMN `opr_userid`  bigint(19) NULL DEFAULT NULL AFTER `district_code`,
ADD COLUMN `create_time`  datetime NULL DEFAULT NULL AFTER `opr_userid`,
ADD COLUMN `update_time`  datetime NULL DEFAULT NULL AFTER `create_time`;

ALTER TABLE `bss_preview_template`
ADD COLUMN `opr_userid`  bigint(19) NULL DEFAULT NULL AFTER `district_code`,
ADD COLUMN `create_time`  datetime NULL DEFAULT NULL AFTER `opr_userid`,
ADD COLUMN `update_time`  datetime NULL DEFAULT NULL AFTER `create_time`;


