--去掉用户选中导出操作
delete from  bss_role_authority_map where authority_id = ( select id from  bss_authority where code = 'customer_list_export1' );
delete from  bss_authority where code = 'customer_list_export1';
