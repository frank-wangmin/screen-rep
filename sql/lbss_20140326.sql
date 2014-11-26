/*system_config表主键自动增长*/
ALTER TABLE `system_config`
MODIFY COLUMN `ID`  bigint(16) NOT NULL AUTO_INCREMENT FIRST ;

/*system_config新增内容注入的id和code的拼接类型*/
insert into system_config (configkey,configvalue,zhname,depiction) value ('codeType', 'number', 'id或者code拼接方式', 'id或者code拼接方式，如果为32为纯数字则configvalue必须为number，否则可以自定义其他值');