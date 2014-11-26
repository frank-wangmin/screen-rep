alter table `bss_customer` modify column login_name varchar(60);
alter table `bss_customer` modify column real_name varchar(60);
alter table `bss_customer` modify column nick_name varchar(60);
alter table `bss_pp_info` add column `ott_product_id`  varchar(32) NULL AFTER `product_id`;
alter table `bss_pp_info` add column `pay_price`  bigint(19) NULL AFTER `price`;