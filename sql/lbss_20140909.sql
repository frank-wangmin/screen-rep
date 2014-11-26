--导出终端统计表
insert into bss_authority (code,name,description,parent_id)values('export_device_statistics','终端激活数据导出','终端激活数据导出',37);
insert into bss_role_authority_map(role_id,authority_id) VALUES(1,326 );

--导出用户统计表
insert into bss_authority (code,name,description,parent_id)values('export_user_statistics','用户开户数据导出','用户开户数据导出',38);
insert into bss_role_authority_map(role_id,authority_id) VALUES(1,327 );

--导出用户统计表
insert into bss_authority (code,name,description,parent_id)values('export_user_activate_day_sum_statistics','终端激活定时统计数据导出','终端激活定时统计数据导出',39);
insert into bss_role_authority_map(role_id,authority_id) VALUES(1,328 );