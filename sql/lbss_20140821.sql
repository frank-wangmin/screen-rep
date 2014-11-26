--bss_pp_info表增加package_type 产品包字段
alter table bss_pp_info add column `package_type` VARCHAR(32) NULL DEFAULT NULL  AFTER `product_type`;
--区域表字段变更
alter table bss_area change district_code  dist_code varchar(6)
