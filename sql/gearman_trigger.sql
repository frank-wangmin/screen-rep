DROP TRIGGER IF exists insert_device_group_map_trigger;
DELIMITER $$
CREATE TRIGGER insert_device_group_map_trigger AFTER INSERT ON bss_device_group_map
  FOR EACH ROW BEGIN
    IF NEW.ysten_id != '' THEN
        SET @ret=gman_do_background('syncToRedis', json_object('ALL' as `type`, 'bss_device_group_map' as `table`, NEW.ysten_id as `ysten_id`)); 	
	END IF;
  END$$
DELIMITER ;

DROP TRIGGER IF exists delete_device_group_map_trigger;
DELIMITER $$
CREATE TRIGGER delete_device_group_map_trigger AFTER DELETE ON bss_device_group_map
  FOR EACH ROW BEGIN
    SET @ret=gman_do_background('syncToRedis', json_object('ALL' as `type`, 'bss_device_group_map' as `table`, OLD.ysten_id as `ysten_id`, OLD.device_group_id as `device_group_id`)); 	
  END$$
DELIMITER ;

DROP TRIGGER IF exists delete_user_group_map_trigger;
DELIMITER $$
CREATE TRIGGER delete_user_group_map_trigger AFTER DELETE ON bss_user_group_map
  FOR EACH ROW BEGIN
    SET @ret=gman_do_background('syncToRedis', json_object('ALL' as `type`, 'bss_user_group_map' as `table`, OLD.code as `customer_code`, OLD.user_group_id as `user_group_id`)); 	
  END$$
DELIMITER ;


-- begin of animation trigger
DROP TRIGGER IF exists update_animation_trigger;
DELIMITER $$
CREATE TRIGGER update_animation_trigger AFTER UPDATE ON bss_boot_animation
  FOR EACH ROW BEGIN
    DECLARE result BOOL;     
    set result = 0;
    IF OLD.name != NEW.name THEN
        set result = 1;
    END IF;
    IF OLD.url != NEW.url THEN
	    set result = 1;
    END IF;
	IF OLD.md5 != NEW.md5 THEN
	    set result = 1;
    END IF;
	IF OLD.state != NEW.state THEN
	    set result = 1;
    END IF;
	IF OLD.is_default != NEW.is_default THEN
	    set result = 1;
    END IF;
	IF result = 1 THEN
        SET @ret=gman_do_background('syncToRedis', json_object('ANIMATION' as `type`, 'bss_boot_animation' as `table`, OLD.id as `boot_animation_id`)); 
	end IF;
END$$
DELIMITER ;

DROP TRIGGER IF exists delete_animation_trigger;
DELIMITER $$
CREATE TRIGGER delete_animation_trigger AFTER DELETE ON bss_boot_animation
  FOR EACH ROW BEGIN
    SET @ret=gman_do_background('syncToRedis', json_object('ANIMATION' as `type`, 'bss_boot_animation' as `table`, OLD.id as `boot_animation_id`)); 	
  END$$
DELIMITER ;

DROP TRIGGER IF exists delete_animation_user_map_trigger;
DELIMITER $$
CREATE TRIGGER delete_animation_user_map_trigger AFTER DELETE ON bss_animation_user_map
  FOR EACH ROW BEGIN
    DECLARE customer_code TEXT;
	DECLARE user_group_id BIGINT;
	IF OLD.code is NULL or OLD.code = '' THEN
	    set customer_code = "";
	ELSE
	    set customer_code = OLD.code;
	END IF;
	if OLD.user_group_id is NULL THEN
	    set user_group_id = -1;
	ELSE
	    set user_group_id = OLD.user_group_id;
	END IF;
    SET @ret=gman_do_background('syncToRedis', json_object('ANIMATION' as `type`, 'bss_animation_user_map' as `table`, OLD.boot_animation_id as `boot_animation_id`, customer_code as `customer_code`, user_group_id as `user_group_id`)); 	
  END$$
DELIMITER ;

DROP TRIGGER IF exists delete_animation_device_map_trigger;
DELIMITER $$
CREATE TRIGGER delete_animation_device_map_trigger AFTER DELETE ON bss_animation_device_map
  FOR EACH ROW BEGIN
    DECLARE ysten_id TEXT;
	DECLARE device_group_id BIGINT;
	IF OLD.ysten_id is NULL or OLD.ysten_id = '' THEN
	    set ysten_id = "";
	ELSE
	    set ysten_id = OLD.ysten_id;
	END IF;
	if OLD.device_group_id is NULL THEN
	    set device_group_id = -1;
	ELSE
	    set device_group_id = OLD.device_group_id;
	END IF;
    SET @ret=gman_do_background('syncToRedis', json_object('ANIMATION' as `type`, 'bss_animation_device_map' as `table`, ysten_id as `ysten_id`, device_group_id as `device_group_id`, OLD.boot_animation_id as `boot_animation_id`)); 	
  END$$
DELIMITER ;

DROP TRIGGER IF exists insert_animation_device_map_trigger;
DELIMITER $$
CREATE TRIGGER insert_animation_device_map_trigger AFTER INSERT ON bss_animation_device_map
  FOR EACH ROW BEGIN
    IF NEW.ysten_id != '' THEN
        SET @ret=gman_do_background('syncToRedis', json_object('ANIMATION' as `type`, 'bss_animation_device_map' as `table`, New.ysten_id as `ysten_id`)); 	
	END IF;
  END$$
DELIMITER ;

-- end of animation trigger

-- begin of background trigger
DROP TRIGGER IF exists update_background_image_trigger;
DELIMITER $$
CREATE TRIGGER update_background_image_trigger AFTER UPDATE ON bss_background_image
  FOR EACH ROW BEGIN
    DECLARE result BOOL;     
    set result = 0;
    IF OLD.name != NEW.name THEN
        set result = 1;
    END IF;
    IF OLD.url != NEW.url THEN
	    set result = 1;
    END IF;
	IF OLD.blur_url != NEW.blur_url THEN
	    set result = 1;
    END IF;
	IF OLD.state != NEW.state THEN
	    set result = 1;
    END IF;
	IF OLD.is_default != NEW.is_default THEN
	    set result = 1;
    END IF;
	IF result = 1 THEN
        SET @ret=gman_do_background('syncToRedis', json_object('BACKGROUND' as `type`, 'bss_background_image' as `table`, OLD.id as `background_image_id`)); 
	end IF;
END$$
DELIMITER ;

DROP TRIGGER IF exists delete_background_image_trigger;
DELIMITER $$
CREATE TRIGGER delete_background_image_trigger AFTER DELETE ON bss_background_image
  FOR EACH ROW BEGIN
    SET @ret=gman_do_background('syncToRedis', json_object('BACKGROUND' as `type`, 'bss_background_image' as `table`, OLD.id as `background_image_id`)); 
  END$$
DELIMITER ;

DROP TRIGGER IF exists delete_background_image_user_map_trigger;
DELIMITER $$
CREATE TRIGGER delete_background_image_user_map_trigger AFTER DELETE ON bss_background_image_user_map
  FOR EACH ROW BEGIN
    DECLARE customer_code TEXT;
	DECLARE user_group_id BIGINT;
	IF OLD.code is NULL or OLD.code = '' THEN
	    set customer_code = "";
	ELSE
	    set customer_code = OLD.code;
	END IF;
	if OLD.user_group_id is NULL THEN
	    set user_group_id = -1;
	ELSE
	    set user_group_id = OLD.user_group_id;
	END IF;
    SET @ret=gman_do_background('syncToRedis', json_object('BACKGROUND' as `type`, 'bss_background_image_user_map' as `table`, OLD.background_image_id as `background_image_id`, customer_code as `customer_code`, user_group_id as `user_group_id`)); 
  END$$
DELIMITER ;

DROP TRIGGER IF exists delete_background_image_device_map_trigger;
DELIMITER $$
CREATE TRIGGER delete_background_image_device_map_trigger AFTER DELETE ON bss_background_image_device_map
  FOR EACH ROW BEGIN
    DECLARE ysten_id TEXT;
	DECLARE device_group_id BIGINT;
	IF OLD.ysten_id is NULL or OLD.ysten_id = '' THEN
	    set ysten_id = "";
	ELSE
	    set ysten_id = OLD.ysten_id;
	END IF;
	if OLD.device_group_id is NULL THEN
	    set device_group_id = -1;
	ELSE
	    set device_group_id = OLD.device_group_id;
	END IF;
    SET @ret=gman_do_background('syncToRedis', json_object('BACKGROUND' as `type`, 'bss_background_image_device_map' as `table`, ysten_id as `ysten_id`, device_group_id as `device_group_id`, OLD.background_image_id as `background_image_id`)); 	
  END$$
DELIMITER ;

DROP TRIGGER IF exists insert_background_image_device_map;
DELIMITER $$
CREATE TRIGGER insert_background_image_device_map AFTER INSERT ON bss_background_image_device_map
  FOR EACH ROW BEGIN
    IF NEW.ysten_id != '' THEN
        SET @ret=gman_do_background('syncToRedis', json_object('BACKGROUND' as `type`, 'bss_background_image_device_map' as `table`, New.ysten_id as `ysten_id`)); 	
	END IF;
  END$$
DELIMITER ;
-- end of background trigger

-- begin of Bootstrap trigger
DROP TRIGGER IF exists update_service_info_trigger;
DELIMITER $$
CREATE TRIGGER update_service_info_trigger AFTER UPDATE ON bss_service_info
  FOR EACH ROW BEGIN
    DECLARE result BOOL;     
    set result = 0;
    IF OLD.service_collect_id != NEW.service_collect_id THEN
        set result = 1;
    END IF;
    IF OLD.service_url != NEW.service_url THEN
	    set result = 1;
    END IF;
	IF result = 1 THEN
        SET @ret=gman_do_background('syncToRedis', json_object('BOOTSTRAP' as `type`, 'bss_service_info' as `table`, OLD.service_collect_id as `service_collect_id`)); 
	end IF;
END$$
DELIMITER ;

DROP TRIGGER IF exists delete_service_info_trigger;
DELIMITER $$
CREATE TRIGGER delete_service_info_trigger AFTER DELETE ON bss_service_info
  FOR EACH ROW BEGIN
    SET @ret=gman_do_background('syncToRedis', json_object('BOOTSTRAP' as `type`, 'bss_service_info' as `table`, OLD.service_collect_id as `service_collect_id`)); 
  END$$
DELIMITER ;

DROP TRIGGER IF exists delete_service_collect_device_group_map_trigger;
DELIMITER $$
CREATE TRIGGER delete_service_collect_device_group_map_trigger AFTER DELETE ON bss_service_collect_device_group_map
  FOR EACH ROW BEGIN
    DECLARE ysten_id TEXT;
	DECLARE device_group_id BIGINT;
	IF OLD.ysten_id is NULL or OLD.ysten_id = '' THEN
	    set ysten_id = "";
	ELSE
	    set ysten_id = OLD.ysten_id;
	END IF;
	if OLD.device_group_id is NULL THEN
	    set device_group_id = -1;
	ELSE
	    set device_group_id = OLD.device_group_id;
	END IF;
    SET @ret=gman_do_background('syncToRedis', json_object('BOOTSTRAP' as `type`, 'bss_service_collect_device_group_map' as `table`, ysten_id as `ysten_id`, device_group_id as `device_group_id`, OLD.service_collect_id as `service_collect_id`)); 
  END$$
DELIMITER ;

DROP TRIGGER IF exists delete_panel_package_device_map_trigger;
DELIMITER $$
CREATE TRIGGER delete_panel_package_device_map_trigger AFTER DELETE ON bss_panel_package_device_map
  FOR EACH ROW BEGIN
    DECLARE ysten_id TEXT;
	DECLARE device_group_id BIGINT;
	IF OLD.ysten_id is NULL or OLD.ysten_id = '' THEN
	    set ysten_id = "";
	ELSE
	    set ysten_id = OLD.ysten_id;
	END IF;
	if OLD.device_group_id is NULL THEN
	    set device_group_id = -1;
	ELSE
	    set device_group_id = OLD.device_group_id;
	END IF;
    SET @ret=gman_do_background('syncToRedis', json_object('BOOTSTRAP' as `type`, 'bss_panel_package_device_map' as `table`, ysten_id as `ysten_id`, device_group_id as `device_group_id`, OLD.panel_package_id as `panel_package_id`)); 
  END$$
DELIMITER ;

DROP TRIGGER IF exists delete_panel_package_user_map_trigger;
DELIMITER $$
CREATE TRIGGER delete_panel_package_user_map_trigger AFTER DELETE ON bss_panel_package_user_map
  FOR EACH ROW BEGIN
    DECLARE customer_code TEXT;
	DECLARE user_group_id BIGINT;
	IF OLD.code is NULL or OLD.code = '' THEN
	    set customer_code = "";
	ELSE
	    set customer_code = OLD.code;
	END IF;
	if OLD.user_group_id is NULL THEN
	    set user_group_id = -1;
	ELSE
	    set user_group_id = OLD.user_group_id;
	END IF;
    SET @ret=gman_do_background('syncToRedis', json_object('BOOTSTRAP' as `type`, 'bss_panel_package_user_map' as `table`, OLD.panel_package_id as `panel_package_id`, customer_code as `customer_code`, user_group_id as `user_group_id`)); 
  END$$
DELIMITER ;

DROP TRIGGER IF exists insert_service_collect_device_group_map_trigger;
DELIMITER $$
CREATE TRIGGER insert_service_collect_device_group_map_trigger AFTER INSERT ON bss_service_collect_device_group_map
  FOR EACH ROW BEGIN
    IF NEW.ysten_id != '' THEN
        SET @ret=gman_do_background('syncToRedis', json_object('BOOTSTRAP' as `type`, 'bss_service_collect_device_group_map' as `table`, New.ysten_id as `ysten_id`)); 	
	END IF;
  END$$
DELIMITER ;

DROP TRIGGER IF exists insert_panel_package_device_map_trigger;
DELIMITER $$
CREATE TRIGGER insert_panel_package_device_map_trigger AFTER INSERT ON bss_panel_package_device_map
  FOR EACH ROW BEGIN
    IF NEW.ysten_id != '' THEN
        SET @ret=gman_do_background('syncToRedis', json_object('BOOTSTRAP' as `type`, 'bss_panel_package_device_map' as `table`, New.ysten_id as `ysten_id`)); 	
	END IF;
  END$$
DELIMITER ;
-- end of Bootstrap trigger 

-- begin of panel trigger
DROP TRIGGER IF exists update_panel_package_trigger;
DELIMITER $$
CREATE TRIGGER update_panel_package_trigger AFTER UPDATE ON bss_panel_package
  FOR EACH ROW BEGIN
    DECLARE result BOOL;     
    set result = 0;
    IF OLD.is_default != NEW.is_default THEN
        set result = 1;
    END IF;
    IF OLD.package_type != NEW.package_type THEN
	    set result = 1;
    END IF;
	IF OLD.online_status != NEW.online_status THEN
	    set result = 1;
    END IF;
	
	IF result = 1 THEN
        SET @ret=gman_do_background('syncToRedis', json_object('PANEL_ALL' as `type`, 'bss_panel_package' as `table`, OLD.id as `panel_package_id`)); 
	end IF;
  END$$
DELIMITER ;

DROP TRIGGER IF exists delete_panel_package_trigger;
DELIMITER $$
CREATE TRIGGER delete_panel_package_trigger AFTER DELETE ON bss_panel_package
  FOR EACH ROW BEGIN
    SET @ret=gman_do_background('syncToRedis', json_object('PANEL_ALL' as `type`, 'bss_panel_package' as `table`, OLD.id as `panel_package_id`)); 
  END$$
DELIMITER ;

DROP TRIGGER IF exists update_panel_trigger;
DELIMITER $$
CREATE TRIGGER update_panel_trigger AFTER UPDATE ON bss_panel
  FOR EACH ROW BEGIN
    DECLARE result BOOL;     
    set result = 0;
    IF OLD.panel_name != NEW.panel_name THEN
        set result = 1;
    END IF;
    IF OLD.panel_title != NEW.panel_title THEN
	    set result = 1;
    END IF;
	IF OLD.panel_icon != NEW.panel_icon THEN
	    set result = 1;
    END IF;
	IF OLD.link_url != NEW.link_url THEN
        set result = 1;
    END IF;
    IF OLD.img_url != NEW.img_url THEN
	    set result = 1;
    END IF;
	IF OLD.ref_panel_id != NEW.ref_panel_id THEN
	    set result = 1;
    END IF;
	IF OLD.online_status != NEW.online_status THEN
        set result = 1;
    END IF;
    IF OLD.status != NEW.status THEN
	    set result = 1;
    END IF;
	IF OLD.big_img != NEW.big_img THEN
	    set result = 1;
    END IF;
	IF OLD.small_img != NEW.small_img THEN
	    set result = 1;
    END IF;
	IF OLD.is_custom != NEW.is_custom THEN
	    set result = 1;
    END IF;
	IF result = 1 THEN
        SET @ret=gman_do_background('syncToRedis', json_object('PANEL_ALL' as `type`, 'bss_panel' as `table`, OLD.id as `panel_id`)); 
	end IF;
  END$$
DELIMITER ;

DROP TRIGGER IF exists delete_panel_trigger;
DELIMITER $$
CREATE TRIGGER delete_panel_trigger AFTER DELETE ON bss_panel
  FOR EACH ROW BEGIN
    SET @ret=gman_do_background('syncToRedis', json_object('PANEL_ALL' as `type`, 'bss_panel' as `table`, OLD.id as `panel_id`)); 
  END$$
DELIMITER ;


DROP TRIGGER IF exists update_panel_item_trigger;
DELIMITER $$
CREATE TRIGGER update_panel_item_trigger AFTER UPDATE ON bss_panel_item
  FOR EACH ROW BEGIN
    DECLARE result BOOL;     
    set result = 0;
    IF OLD.name != NEW.name THEN
        set result = 1;
    END IF;
    IF OLD.title != NEW.title THEN
	    set result = 1;
    END IF;
	IF OLD.title_comment != NEW.title_comment THEN
	    set result = 1;
    END IF;
	IF OLD.action_type != NEW.action_type THEN
        set result = 1;
    END IF;
    IF OLD.action_url != NEW.action_url THEN
	    set result = 1;
    END IF;
	IF OLD.image_url != NEW.image_url THEN
	    set result = 1;
    END IF;
	IF OLD.image_disturl != NEW.image_disturl THEN
        set result = 1;
    END IF;
    IF OLD.video_url != NEW.video_url THEN
	    set result = 1;
    END IF;
	IF OLD.content_type != NEW.content_type THEN
	    set result = 1;
    END IF;
	IF OLD.ref_item_id != NEW.ref_item_id THEN
	    set result = 1;
    END IF;
	IF OLD.panelitem_parentid != NEW.panelitem_parentid THEN
	    set result = 1;
    END IF;
	
	IF OLD.auto_run != NEW.auto_run THEN
	    set result = 1;
    END IF;
	IF OLD.focus_run != NEW.focus_run THEN
	    set result = 1;
    END IF;
	IF OLD.show_title != NEW.show_title THEN
	    set result = 1;
    END IF;
	IF OLD.animation_run != NEW.animation_run THEN
	    set result = 1;
    END IF;
	IF OLD.status != NEW.status THEN
	    set result = 1;
    END IF;
	IF OLD.has_sub_item != NEW.has_sub_item THEN
	    set result = 1;
    END IF;
	IF OLD.epg_ref_item_id != NEW.epg_ref_item_id THEN
	    set result = 1;
    END IF;
	IF OLD.epg_panelitem_parentid != NEW.epg_panelitem_parentid THEN
	    set result = 1;
    END IF;
	IF OLD.auto_play != NEW.auto_play THEN
	    set result = 1;
    END IF;
	IF OLD.install_url != NEW.install_url THEN
	    set result = 1;
    END IF;
	IF OLD.online_status != NEW.online_status THEN
	    set result = 1;
    END IF;

	IF result = 1 THEN
        SET @ret=gman_do_background('syncToRedis', json_object('PANEL_DATA' as `type`, 'bss_panel_item' as `table`, OLD.id as `panel_item_id`)); 
	end IF;
  END$$
DELIMITER ;

DROP TRIGGER IF exists delete_panel_item_trigger;
DELIMITER $$
CREATE TRIGGER delete_panel_item_trigger AFTER DELETE ON bss_panel_item
  FOR EACH ROW BEGIN
    SET @ret=gman_do_background('syncToRedis', json_object('PANEL_DATA' as `type`, 'bss_panel_item' as `table`, OLD.id as `panel_item_id`)); 
  END$$
DELIMITER ;

DROP TRIGGER IF exists update_panel_nav_define_trigger;
DELIMITER $$
CREATE TRIGGER update_panel_nav_define_trigger AFTER UPDATE ON bss_panel_nav_define
  FOR EACH ROW BEGIN
    DECLARE result BOOL;     
    set result = 0;
    IF OLD.nav_type != NEW.nav_type THEN
        set result = 1;
    END IF;
    IF OLD.title != NEW.title THEN
	    set result = 1;
    END IF;
	IF OLD.title_comment != NEW.title_comment THEN
	    set result = 1;
    END IF;
	IF OLD.action_type != NEW.action_type THEN
        set result = 1;
    END IF;
    IF OLD.action_url != NEW.action_url THEN
	    set result = 1;
    END IF;
	IF OLD.image_url != NEW.image_url THEN
	    set result = 1;
    END IF;
	IF OLD.image_disturl != NEW.image_disturl THEN
        set result = 1;
    END IF;
    IF OLD.show_title != NEW.show_title THEN
	    set result = 1;
    END IF;
	IF OLD.online_status != NEW.online_status THEN
	    set result = 1;
    END IF;
	IF OLD.nav_name != NEW.nav_name THEN
	    set result = 1;
    END IF;
	
	IF result = 1 THEN
        SET @ret=gman_do_background('syncToRedis', json_object('PANEL_DATA' as `type`, 'bss_panel_nav_define' as `table`, OLD.id as `nav_id`)); 
	end IF;
  END$$
DELIMITER ;

DROP TRIGGER IF exists delete_panel_nav_define_trigger;
DELIMITER $$
CREATE TRIGGER delete_panel_nav_define_trigger AFTER DELETE ON bss_panel_nav_define
  FOR EACH ROW BEGIN
    SET @ret=gman_do_background('syncToRedis', json_object('PANEL_ALL' as `type`, 'bss_panel_nav_define' as `table`, OLD.id as `nav_id`)); 
  END$$
DELIMITER ;

DROP TRIGGER IF exists delete_panel_panel_item_map_trigger;
DELIMITER $$
CREATE TRIGGER delete_panel_panel_item_map_trigger AFTER DELETE ON bss_panel_panel_item_map
  FOR EACH ROW BEGIN
    SET @ret=gman_do_background('syncToRedis', json_object('PANEL_DATA' as `type`, 'bss_panel_panel_item_map' as `table`, OLD.panel_id as `panel_id`, OLD.panel_item_id as `panel_item_id`)); 
  END$$
DELIMITER ;

DROP TRIGGER IF exists delete_panel_package_panel_map_trigger;
DELIMITER $$
CREATE TRIGGER delete_panel_package_panel_map_trigger AFTER DELETE ON bss_panel_package_panel_map
  FOR EACH ROW BEGIN
    SET @ret=gman_do_background('syncToRedis', json_object('PANEL_ALL' as `type`, 'bss_panel_package_panel_map' as `table`, OLD.panel_id as `panel_id`, OLD.package_id as `panel_package_id`)); 
  END$$
DELIMITER ;

DROP TRIGGER IF exists delete_preview_item_data_trigger;
DELIMITER $$
CREATE TRIGGER delete_preview_item_data_trigger AFTER DELETE ON bss_preview_item_data
  FOR EACH ROW BEGIN
    SET @ret=gman_do_background('syncToRedis', json_object('PANEL_STYLE_DATA' as `type`, 'bss_preview_item_data' as `table`, OLD.content_id as `panel_id`, OLD.content_item_id as `panel_item_id`)); 
  END$$
DELIMITER ;

DROP TRIGGER IF exists update_preview_item_data_trigger;
DELIMITER $$
CREATE TRIGGER update_preview_item_data_trigger AFTER UPDATE ON bss_preview_item_data
  FOR EACH ROW BEGIN
    DECLARE result BOOL;     
    set result = 0;
    IF OLD.left != NEW.left THEN
        set result = 1;
    END IF;
    IF OLD.top != NEW.top THEN
	    set result = 1;
    END IF;
	IF OLD.width != NEW.width THEN
	    set result = 1;
    END IF;
	IF OLD.height != NEW.height THEN
        set result = 1;
    END IF;
    IF OLD.sort != NEW.sort THEN
	    set result = 1;
    END IF;
	
	IF result = 1 THEN
        SET @ret=gman_do_background('syncToRedis', json_object('PANEL_STYLE_DATA' as `type`, 'bss_preview_item_data' as `table`, OLD.content_id as `panel_id`, OLD.content_item_id as `panel_item_id`)); 
	end IF;
  END$$
DELIMITER ;

-- end of panel trigger