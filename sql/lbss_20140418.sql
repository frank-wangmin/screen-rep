/*为权限表添加排序字段order*/
ALTER TABLE `bss_authority`
ADD COLUMN `sort_order`  bigint(19) NULL AFTER `parent_id`;