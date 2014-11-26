/*更改bss_interface_log日志表字段类型--varchar改为text*/
alter table bss_interface_log modify column input text;

alter table bss_interface_log modify column output text;


