alter table bss_pp_info add column push_date datetime default null;  -- 锟斤拷锟斤拷时锟斤拷
alter table bss_pp_info add column push_state varchar(32) default null;  -- 锟斤拷锟斤拷状态
alter table bss_pp_info add column call_back_date datetime default null;  -- 锟截碉拷时锟斤拷

alter table bss_product_content add column push_date datetime default null;   -- 锟斤拷锟斤拷时锟斤拷
alter table bss_product_content add column push_state varchar(32) default null;  -- 锟斤拷锟斤拷状态
alter table bss_product_content add column call_back_date datetime default null;  -- 锟截碉拷时锟斤拷
alter table bss_product_content change ppv_code ppv_id bigint(19) default null;  -- 修改ppv_id类型

alter table bss_ppv_pp_relation add column push_date datetime default null;   -- 锟斤拷锟斤拷时锟斤拷
alter table bss_ppv_pp_relation add column push_state varchar(32) default null;  -- 锟斤拷锟斤拷状态
alter table bss_ppv_pp_relation add column call_back_date datetime default null;  -- 锟截碉拷时锟斤拷
alter table bss_ppv_pp_relation add column pp_pk_id bigint(19) default null;   -- 加入pp主键
alter table bss_ppv_pp_relation add column ppv_pk_id bigint(19) default null;   -- 加入ppv主键
alter table bss_ppv_pp_relation add column create_date datetime default null;   -- 加入创建时间


alter table `bss_product_content` change ppv_code ppv_id varchar(32) default null;
/*alter bss_operate_log primarykey to auto increment*/
ALTER TABLE `bss_operate_log`
MODIFY COLUMN `id`  bigint(19) NOT NULL AUTO_INCREMENT FIRST ;
