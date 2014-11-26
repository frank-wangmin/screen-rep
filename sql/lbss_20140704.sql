alter table bss_device add UNIQUE (ysten_id);
alter table bss_customer add UNIQUE (code);

--添加冗余的outter code，此ott outter code是根据规则生成的32位或者其它长度的字符串
ALTER TABLE `bss_product_content`
	ADD COLUMN `ott_outter_code` VARCHAR(32) NULL DEFAULT NULL AFTER `outter_code`;