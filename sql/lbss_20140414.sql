/*在产品订单表里添加ott的product id字段，用来存储ott的订单编号，和本地的订单编号不是同一个编号*/
ALTER TABLE `bss_product_order`
ADD COLUMN `ott_product_id` VARCHAR(32) NULL DEFAULT NULL AFTER `product_type`;