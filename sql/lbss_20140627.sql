/*添加是否预开通字段NOT_OPEN：未开通，PREPARE_OPEN预开通，FORMAL_OPEN正式开通*/
ALTER TABLE `bss_device` ADD COLUMN `prepare_open` varchar(15) DEFAULT 'NOT_OPEN';