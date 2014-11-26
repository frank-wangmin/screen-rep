
--delete the column cp_id,sp_id,catg_id--
alter table bss_operator drop column cp_id;

alter table bss_operator drop column sp_id;

alter table bss_operator drop column catg_id;

--删除终端接口地址管理模块
delete from bss_authority where code in ('interface_url_info','add_interface_url','update_interface_url','delete_interface_url' );

delete  from bss_role_authority_map where authority_id in( 188 ,192,193,298);

delete  from bss_sys_menu where url = 'to_interface_url_list.shtml';

--增加JMS重置处理状态功能
insert into bss_authority (code,name,description,parent_id)values('reset_JMS',' 重置','JMS状态重置',15);
insert into bss_role_authority_map(role_id,authority_id) VALUES(1,313 );

--设备分组表字段district_code-->area_id (area_id)
alter table bss_device_group change district_code  area_id  bigint(19);