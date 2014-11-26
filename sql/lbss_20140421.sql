/*为角色表添加创建时间和描述字段*/
ALTER TABLE `bss_role`
ADD COLUMN `description`  varchar(5000) NULL AFTER `name`,
ADD COLUMN `create_date`  timestamp NULL ON UPDATE CURRENT_TIMESTAMP AFTER `description`;