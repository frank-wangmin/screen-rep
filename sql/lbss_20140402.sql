/*优惠策略组表主键自增*/
ALTER TABLE `bss_discount_policy_group`
MODIFY COLUMN `id`  bigint(19) NOT NULL AUTO_INCREMENT FIRST ;
/*价格规则表主键自增*/
ALTER TABLE `bss_discount_policy_define_product`
MODIFY COLUMN `id`  int(16) NOT NULL AUTO_INCREMENT FIRST ;