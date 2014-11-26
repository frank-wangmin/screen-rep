--去掉设备选中导出操作
delete from  bss_role_authority_map where authority_id = ( select id from  bss_authority where code = 'export_device1' );
delete from  bss_authority where code = 'export_device1';
