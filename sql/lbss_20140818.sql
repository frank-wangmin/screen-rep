--增加生成易视腾编号功能
insert into bss_authority (code,name,description,parent_id)values('set_ystenId','生成易视腾编号','生成易视腾编号',31);
insert into bss_role_authority_map(role_id,authority_id) VALUES(1,314 );