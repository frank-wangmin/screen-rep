--用户表增加外部用户编号字段
alter table bss_customer add column outer_code  varchar(32)  DEFAULT NULL  AFTER  code