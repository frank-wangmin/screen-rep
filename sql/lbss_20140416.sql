/*为权限表加parent_id字段*/
ALTER TABLE `bss_authority`
ADD COLUMN `parent_id`  bigint(19) NULL AFTER `description`;
/*在产品包表里添加product_type字段*/

ALTER TABLE `bss_pp_info`
 ADD COLUMN `product_type` VARCHAR(32) NULL DEFAULT NULL AFTER `product_name`;
/*操作日志表主键自增*/
ALTER TABLE `bss_operate_log`
MODIFY COLUMN `id`  int(16) NOT NULL AUTO_INCREMENT FIRST ;
/*system_config新增systemTitle*/
insert into system_config (configkey,configvalue,zhname,depiction) value ('systemTitle', '联网电视业务管理子平台(研发)', '系统标题', '系统标题');