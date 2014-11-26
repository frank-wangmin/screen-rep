/**
 * Created by frank on 14-5-26.
 */

var BIMS_PANEL = (function () {

    var PANEL = {};

    var max_width = 5, max_height = 3, preview_boxs = [], preview_item_datas = [], preview_panels = [],
        preview_root_navs = [], item_children_list = [], auto_play_items = {}, clearInterval_arr = [];

    PANEL.template = {};

    PANEL.config = {
        isAdd: true,
        backgroud_img: 'images/nodata.jpg',
        ysten_logo: 'images/logo_64.png',
        layout_height: 0,
        layout_width: 0,
        height_unit: 0,
        width_unit: 0,
        panel_item_content_type: 'image',
        panel_dpi: '720p',
        package_dpi: '720p',
        isDisabled: false,
        panel_item_type: 'custom',
        panel_item_action_type: '1',
        panel_index: 0,
        panel_item_all: [],
        head_navs: [],
        select_exsit_flag: 0,
        panel_item_edit_flag: false,
        panel_item_ref_id: null,
        panel_item_parent_id: null,
        panel_item_edit_id: null,
        select_input_length: 202,
        select_input_height: 160,
        auto_display_millis: 2000,
        msg_success: "success",
        msg_error: "error",
        datasource_query: [
            {value: '', text: '全部'},
            {value: 'custom', text: 'BIMS(中心)'},
            {value: 'outter', text: '播控系统'}
        ],
        delete_panel_map : [
            {text: '移除选中面板', value: '0'},
            {text: '移除全部面板', value: '1'}
        ],
        isLock_display: {
            dataSource: [
                {text: "是", value: "true"},
                {text: "否", value: "false"}
            ],
            editable: false,
            listMaxHeight: 150,
            value: 'false'
        },
        sort_options: {
            dataSource: [
                {text: "1", value: "1"},
                {text: "2", value: "2"},
                {text: "3", value: "3"},
                {text: "4", value: "4"},
                {text: "5", value: "5"},
                {text: "6", value: "6"},
                {text: "7", value: "7"},
                {text: "8", value: "8"},
                {text: "9", value: "9"},
                {text: "10", value: "10"},
                {text: "11", value: "11"},
                {text: "12", value: "12"},
                {text: "13", value: "13"},
                {text: "14", value: "14"},
                {text: "15", value: "15"}
            ],
            editable: false,
            listMaxHeight: 150
        }
    };
    PANEL.enums = {
        panel_item_content_type: [],
        panel_item_type: [],
        panel_item_action_type: [],
        panel_name: [],
        panel_name_1080p: [],
        root_nav_list: [],
        root_nav_list_1080p: [],
        head_nav_list: [],
        head_nav_list_1080p: [],
        resolution: []
    }

    PANEL.check_array = function (data) {
        return data && Object.prototype.toString.call(data) === "[object Array]";
    };

    PANEL.set_width = function (width) {
        max_width = width;
    };
    PANEL.get_width = function () {
        return max_width;
    };
    PANEL.set_height = function (height) {
        max_height = height;
    };
    PANEL.get_height = function () {
        return max_height;
    };

    PANEL.get_preview_boxs = function () {
        return preview_boxs;
    };

    PANEL.get_preview_panels = function () {
        return preview_panels;
    };

    PANEL.add_preview_box = function (obj) {
        obj.imageUrl = this.config.backgroud_img;
        preview_boxs.push(obj);
    };

    PANEL.destroy_preview_box = function () {
        preview_boxs = [];
    };

    PANEL.show_message = function (msg, type) {
        $.omMessageTip.show({title: tip, content: msg, type: type, timeout: time});
    };

    PANEL.set_preview_panels = function (data) {
        preview_panels = data || [];
        for (var i = 0; i < preview_panels.length; i++) {
            var rootNav = preview_panels[i].rootNavigation;
            if (rootNav && rootNav.title) {
                preview_root_navs.push(rootNav.title);
            }
        }
        if (preview_panels.length > 0) {
            this.display_preview_package(this.config.panel_index);
            this.display_panel_root_nvas(this.config.panel_index);
        }
    };

    PANEL.show_data_source = function (val) {
        if (val) {
            return "播控系统";
        } else {
            return "BIMS(中心)";
        }

    };

    PANEL.show_online_status = function (val) {
        if (val == 99) {
            return "上线";
        } else if (val == -99) {
            return "下线";
        } else {
            return "初始";
        }
    };

    PANEL.renderActionTypeFunc = function (value) {
        //1:播放视频 2:跳转其他Launcher页面 3:打开指定网页 4:跳转其他应用 5:widget动作
        if (value == 1) {
            return "播放视频";
        }
        if (value == 2) {
            return "跳转其他Launcher页面";
        }
        if (value == 3) {
            return "打开指定网页";
        }
        if (value == 4) {
            return "跳转其他应用";
        }
        if (value == 5) {
            return "widget动作";
        }
        return "";
    };

    PANEL.renderContengTypeFunc = function (value) {
        if (value == "image") {
            return "图片";
        }
        if (value == "video") {
            return "视频";
        }
        if (value == "widget") {
            return "Android Widget";
        }
        if (value == "custom") {
            return "自定义";
        }
        return "";
    };

    PANEL.click_preview_package_left = function () {
        var panels = BIMS_PANEL.get_preview_panels();
        if (panels && BIMS_PANEL.check_array(panels) && panels.length > 0) {
            if (BIMS_PANEL.config.panel_index == 0) {
                BIMS_PANEL.config.panel_index = panels.length - 1;
            } else {
                BIMS_PANEL.config.panel_index = BIMS_PANEL.config.panel_index - 1;
            }
            BIMS_PANEL.display_preview_package(BIMS_PANEL.config.panel_index);
            BIMS_PANEL.display_panel_root_nvas(BIMS_PANEL.config.panel_index);
        }
    };

    PANEL.click_preview_package_right = function () {
        var panels = BIMS_PANEL.get_preview_panels();
        if (panels && BIMS_PANEL.check_array(panels) && panels.length > 0) {
            if (BIMS_PANEL.config.panel_index == panels.length - 1) {
                BIMS_PANEL.config.panel_index = 0;
            } else {
                BIMS_PANEL.config.panel_index = BIMS_PANEL.config.panel_index + 1;
            }
            BIMS_PANEL.display_preview_package(BIMS_PANEL.config.panel_index);
            BIMS_PANEL.display_panel_root_nvas(BIMS_PANEL.config.panel_index);
        }
    };

    PANEL.destroy_preview_panels = function () {
        preview_panels = [];
        preview_root_navs = [];
    };

    PANEL.set_preview_boxs = function (data) {
        if (this.check_array(data)) {
            for (var i = 0; i < data.length; i++) {
                data[i].imageUrl = this.config.backgroud_img;
            }
        }
        preview_boxs = data || [];
        this.display_preview_template();
    };

    PANEL.set_preview_item_datas = function (data) {
        auto_play_items = {};
        if (data) {
            $.each(data, function (index, value) {
                if (!value['panelItem']) {
                    value.imageUrl = BIMS_PANEL.config.backgroud_img;
                } else {
                    if (value.panelItem.hasSubItem && !value.panelItem.refItemId) {
                        auto_play_items["auto_play_" + value.panelItem.id] = value.panelItem.childrenList;
                    }
                }
            });
        }
        preview_item_datas = data || [];
        this.display_preview_panel();
    };

    PANEL.config_layout_num = function ($layout) {
        this.config.layout_height = this.config.layout_height || $layout.height();
        this.config.layout_width = this.config.layout_width || $layout.width();
        this.config.height_unit = this.config.height_unit || Math.floor(this.config.layout_height / max_height);
        this.config.width_unit = this.config.width_unit || Math.floor(this.config.layout_width / max_width);
    };

    PANEL.init_display_related_item = function (child_panel_item_init_id) {
        if (child_panel_item_init_id) {
            $("#" + child_panel_item_init_id).trigger("mouseenter");
        }
    };

    PANEL.init_auto_play_item = function (id_arr, clearInterval_arr_param) {
        if ($.isArray(clearInterval_arr_param) && clearInterval_arr_param.length > 0) {
            $.each(clearInterval_arr_param, function (i, value) {
                window.clearInterval(value);
            })
            clearInterval_arr = [];
        }
        if ($.isArray(id_arr) && id_arr.length > 0) {
            $.each(id_arr, function (i, id) {
                $("#" + id).trigger("auto_display");
            });
        }
    };

    PANEL.display_preview_panel = function () {
        var $panel_layout = $("#panel_layout"), items = preview_item_datas, child_panel_item_init_id, auto_play_arr = [];
        $panel_layout.empty();
        this.config_layout_num($panel_layout);
        var style = "", box = "", left = "", top = "", width = "", height = "";
        for (var i = 0; i < items.length; i++) {
            var obj = items[i];
            var image_url,
                left = "left:" + (obj.left * this.config.width_unit + 12) + "px;",
                top = "top:" + (obj.top * this.config.height_unit + 8) + "px;",
                width = "width:" + obj.width * this.config.width_unit + "px;",
                height = "height:" + obj.height * this.config.height_unit + "px;";
            style = left + top + width + height;
            if (obj.panelItem) {

                //有关联的父面板项
                if (obj.panelItem.hasSubItem == 1 && obj.panelItem.refItemId) {
                    image_url = "<img style=" + width + height + " src='" + obj.panelItem.imageUrl + "' />";
                }
                //无关联的父面板项
                else if (obj.panelItem.hasSubItem == 1 && !obj.panelItem.refItemId) {
                    image_url = "<img id='auto_play_" + obj.panelItem.id + "' style=" + width + height + " src='" + obj.panelItem.imageUrl + "' />";
                    auto_play_arr.push("auto_play_" + obj.panelItem.id);
                }
                //关联面板项
                else if (obj.panelItem.contentType != 'ref' && obj.panelItem.refItemId) {
                    child_panel_item_init_id = "childrenPanelItem_" + obj.panelItem.refItemId + "_0";
                    image_url = "<img id='related_item_" + obj.panelItem.refItemId + "' style='" + width + height + "' class='parent_panel_item' src='" + obj.panelItem.imageUrl + "' />";
                }
                //其他
                else {
                    image_url = "<img style=" + width + height + " src='" + obj.panelItem.imageUrl + "' />";
                }
            } else {
                image_url = "<img style=" + width + height + " src='" + obj.imageUrl + "' />";
            }
            if (obj.panelItem && obj.panelItem.hasSubItem == 1 && obj.panelItem.refItemId && obj.panelItem.childrenList) {
                item_children_list = obj.panelItem.childrenList;
                var children_size = item_children_list.length, chilren_height = Math.floor((obj.height * this.config.height_unit - 24 - (children_size * 2)) / children_size),
                    children_width = obj.width * this.config.width_unit - 20, children_str = "",
                    children_style = "height:" + chilren_height + "px;" + "line-height:" + chilren_height + "px;" + "width:" + children_width + "px;";
                for (var j = 0; j < children_size; j++) {
                    children_str += '<li id="childrenPanelItem_' + item_children_list[j].panelItemParentId + '_' + j + '" class="children_box" style=' + children_style + '><a>' + item_children_list[j].title + '</a></li>';
                }

                box += "<div class='panel_box' style=" + style + ">" + (obj["epgIoid"] ? "" : "<a onclick='BIMS_PANEL.editPanelItem(" + i + ")' href='#' class='box_edit'>编辑</a>") +
                    "<div class='parent_panel_item' style='" + width + height + "'>" + image_url + "<ul class='parent_panel_ul'>" + children_str +
                    "</ul></div></div>";
            } else {
                box += "<div class='panel_box' style=" + style + ">" + (obj["epgIoid"] ? "" : "<a onclick='BIMS_PANEL.editPanelItem(" + i + ")' href='#' class='box_edit'>编辑</a>") + image_url + "</div>";
            }
        }
        $panel_layout.html(box);
        this.init_display_related_item(child_panel_item_init_id);
        this.init_auto_play_item(auto_play_arr, clearInterval_arr);
    };

    PANEL.display_preview_template = function () {
        var $panel_layout = $("#panel_layout"), items = preview_boxs;
        $panel_layout.empty();
        this.config_layout_num($panel_layout);
        var style = "", box = "", left = "", top = "", width = "", height = "";
        for (var i = 0; i < items.length; i++) {
            var obj = items[i];
            var image_url = "";
            left = "left:" + (obj.left * this.config.width_unit + 14) + "px;";
            top = "top:" + (obj.top * this.config.height_unit + 8) + "px;";
            width = "width:" + obj.width * this.config.width_unit + "px;";
            height = "height:" + obj.height * this.config.height_unit + "px;";
            style = left + top + width + height;
            if (obj.imageUrl) {
                image_url = "<img style=" + width + height + " src='" + obj.imageUrl + "' />";
            }
            box += "<div class='panel_box' style=" + style +
                "><a onclick='BIMS_PANEL.editBox(" + i + ")' href='#' class='box_edit'>编辑</a>" +
                "<a onclick='BIMS_PANEL.deleteBox(" + i + ")' href='#' class='box_edit box_delete'>删除</a>" +
                image_url + "</div>";
        }
        $panel_layout.html(box);
    };

    PANEL.display_preview_package = function (panel_index) {
        var $panel_content = $("#panel_content" + this.config.package_dpi),$panel_package_div = $("#preview_" + this.config.package_dpi),child_panel_item_init_id, auto_play_arr = [];
        this.config_layout_num($panel_content);
        var preview_panel = preview_panels[panel_index];
        this.config.ysten_logo = preview_panel["logo"];
        if(preview_panel['defaultBackImag' + this.config.package_dpi]){
            $panel_package_div.css({"background":"url(" + preview_panel['defaultBackImag' + this.config.package_dpi] + ") no-repeat top","background-size":"100% 100%"});
        }
        var style = "", box = "", left = "", top = "", width = "", height = "", head_nav = "", root_nav;
        this.config.head_navs = preview_panel.headNavList || [], root_navs = preview_panel.rootNavigation || {}, preview_items_data = preview_panel.previewItemDataList || [];
        if ($.isArray(preview_items_data) && preview_items_data.length > 0) {
            auto_play_items = {};
            $.each(preview_items_data, function (index, value) {
                if (!value['panelItem']) {
                    value.imageUrl = BIMS_PANEL.config.backgroud_img;
                } else {
                    if (value.panelItem.hasSubItem && !value.panelItem.refItemId) {
                        auto_play_items["auto_play_" + value.panelItem.id] = value.panelItem.childrenList;
                    }
                }
            });
        }
        if(this.config.ysten_logo){
            head_nav += '<img class="panel_head_logo" src="' + this.config.ysten_logo + '" alt=""/>';
        }
        for (var j = 0; j < this.config.head_navs.length; j++) {
            head_nav += '<img class="panel_head_nva_img" src="' + this.config.head_navs[j].imageUrl + '" id="headNav_' + j + '_' + this.config.head_navs[j].showTitle + '" alt="' + this.config.head_navs[j].title + '"/>';
        }
        for (var k = 0; k < preview_items_data.length; k++) {
            var obj = preview_items_data[k];
            var image_url,
                left = "left:" + (obj.left * this.config.width_unit + 14) + "px;";
            top = "top:" + (obj.top * this.config.height_unit + 8) + "px;";
            width = "width:" + obj.width * this.config.width_unit + "px;";
            height = "height:" + obj.height * this.config.height_unit + "px;";
            style = left + top + width + height;
            if (obj.panelItem) {

                //父面板项
                if (obj.panelItem.hasSubItem == 1 && obj.panelItem.refItemId) {
                    image_url = "<img style=" + width + height + " src='" + obj.panelItem.imageUrl + "' />";


                }
                //无关联的父面板项
                else if (obj.panelItem.hasSubItem == 1 && !obj.panelItem.refItemId) {
                    image_url = "<img id='auto_play_" + obj.panelItem.id + "' style=" + width + height + " src='" + obj.panelItem.imageUrl + "' />";
                    auto_play_arr.push("auto_play_" + obj.panelItem.id);
                }
                //关联面板项
                else if (obj.panelItem.contentType != 'ref' && obj.panelItem.refItemId) {
                    child_panel_item_init_id = "childrenPanelItem_" + obj.panelItem.refItemId + "_0";
                    image_url = "<img id='related_item_" + obj.panelItem.refItemId + "' style=" + width + height + " src='" + obj.panelItem.imageUrl + "' />";
                }
                //其他
                else {
                    image_url = "<img style=" + width + height + " src='" + obj.panelItem.imageUrl + "' />";
                }
            } else {
                image_url = "<img style=" + width + height + " src='" + obj.imageUrl + "' />";
            }
            if (obj.panelItem && obj.panelItem.hasSubItem == 1 && obj.panelItem.refItemId && obj.panelItem.childrenList) {
                item_children_list = obj.panelItem.childrenList;
                var children_size = item_children_list.length, chilren_height = Math.floor((obj.height * this.config.height_unit - 8 - (children_size * 2)) / children_size),
                    children_width = obj.width * this.config.width_unit - 20, children_str = "",
                    children_style = "height:" + chilren_height + "px;" + "line-height:" + chilren_height + "px;" + "width:" + children_width + "px;";
                for (var j = 0; j < children_size; j++) {
                    children_str += '<li id="childrenPanelItem_' + item_children_list[j].panelItemParentId + '_' + j + '" class="children_box" style=' + children_style + '><a>' + item_children_list[j].title + '</a></li>';
                }

                box += "<div class='panel_box' style=" + style + ">" +
                    "<div class='parent_panel_item' style='" + width + height + "'>" +
                    image_url + "<ul class='parent_package_ul'>" + children_str +
                    "</ul></div></div>";
            } else {
                box += "<div class='panel_box' style=" + style + ">" + image_url + "</div>";
            }
        }
        $("#panel_head_nva_content" + this.config.package_dpi).html(head_nav);
        $panel_content.html(box);
        this.init_display_related_item(child_panel_item_init_id);
        this.init_auto_play_item(auto_play_arr, clearInterval_arr);
    };

    PANEL.display_panel_root_nvas = function (panel_index) {
        var root_nav = "";
        for (var i = 0; i < preview_root_navs.length; i++) {
            if (i == panel_index) {
                root_nav += '<a href="#" class="panel_root_nva panel_root_nva_active">' + preview_root_navs[i] + '</a>';
            } else {
                root_nav += '<a href="#" onclick="BIMS_PANEL.display_panel_by_rootnav(' + i + ')" class="panel_root_nva">' + preview_root_navs[i] + '</a>';
            }
        }
        $("#panel_root_nav_content" + this.config.package_dpi).html(root_nav);
    };

    PANEL.display_panel_by_rootnav = function (panel_index) {
        this.config.panel_index = panel_index;
        this.display_preview_package(this.config.panel_index);
        this.display_panel_root_nvas(this.config.panel_index);
    };

    PANEL.generate_top = function () {
        var top_arr = [];
        for (var i = 0; i < max_height; i++) {
            top_arr.push({"text": i, "value": i});
        }
        return top_arr;
    };

    PANEL.generate_left = function () {
        var left_arr = [];
        for (var i = 0; i < max_width; i++) {
            left_arr.push({"text": i, "value": i});
        }
        return left_arr;
    };

    PANEL.generate_height = function () {
        var height_arr = [];
        for (var i = 1; i <= max_height; i++) {
            height_arr.push({"text": i, "value": i});
        }
        return height_arr;
    };

    PANEL.generate_height_dynamical = function (target, newValue) {
        var height_arr = [];
        for (var i = 1; i <= (max_height - newValue); i++) {
            height_arr.push({"text": i, "value": i});
        }
        $('#height').omCombo('setData', height_arr);
        $('#height').omCombo('value', 1);
    };

    PANEL.generate_width = function () {
        var width_arr = [];
        for (var i = 1; i <= max_width; i++) {
            width_arr.push({"text": i, "value": i});
        }
        return width_arr;
    };

    PANEL.generate_width_dynamical = function (target, newValue) {
        var width_arr = [];
        for (var i = 1; i <= (max_width - newValue); i++) {
            width_arr.push({"text": i, "value": i});
        }
        $('#width').omCombo('setData', width_arr);
        $('#width').omCombo('value', 1);
    };

    PANEL.submitBox = function () {
        var box = {templateId: $("#templateId").val(), top: $("#top").val(), left: $("#boxLeft").val(),
            width: $("#width").val(), height: $("#height").val(), sort: $("#sort").val()};
        if (this.config.isAdd) {
            this.add_preview_box(box);
        } else {
            var origin_box = preview_boxs[$("#templateBoxIndex").val()];
            origin_box.top = box.top;
            origin_box.left = box.left;
            origin_box.width = box.width;
            origin_box.height = box.height;
            origin_box.sort = box.sort;
        }

        this.display_preview_template();
        $("#box-form").omDialog("close");
    };

    PANEL.submitTemplate = function () {
        $.ajax({
            type: 'POST',
            url: 'add_batch_previewItem.json',
            contentType: 'application/json',
            data: JSON.stringify(preview_boxs),
            success: function (result) {
                if ("success" == result) {
                    BIMS_PANEL.show_message("新增模块成功！", BIMS_PANEL.config.msg_success);
                } else {
                    BIMS_PANEL.show_message("新增模块失败！", BIMS_PANEL.config.msg_error);
                }
                $("#panel_layout").omDialog("close");
            }
        });

    };

    PANEL.panel_template_dialog = function () {
        return $("#panel_layout").omDialog({
            width: 800,
            height: 500,
            autoOpen: false,
            modal: true,
            resizable: false,
            draggable: false,
            buttons: {
                "添加模块": function () {
                    BIMS_PANEL.config.isAdd = true;
                    $("#top").omCombo({dataSource: BIMS_PANEL.generate_top(), value: '0', editable: false, width: 120, listMaxHeight: 120,
                        onValueChange: BIMS_PANEL.generate_height_dynamical});
                    $("#height").omCombo({dataSource: BIMS_PANEL.generate_height(), value: '1', editable: false, width: 120, listMaxHeight: 120});
                    $("#boxLeft").omCombo({dataSource: BIMS_PANEL.generate_left(), value: '0', editable: false, width: 120, listMaxHeight: 120,
                        onValueChange: BIMS_PANEL.generate_width_dynamical});
                    $("#width").omCombo({dataSource: BIMS_PANEL.generate_width(), value: '1', editable: false, width: 120, listMaxHeight: 120});
                    $("#sort").val("");
                    BIMS_PANEL.box_dialog().omDialog("option", "title", "模板块信息");
                    BIMS_PANEL.box_dialog().omDialog("open");
                },
                "提交": function () {
                    BIMS_PANEL.submitTemplate();
                },
                "取消": function () {
                    $("#panel_layout").omDialog("close");
                }
            },
            onClose: function () {
            }
        });
    };

    PANEL.preview_panel_dialog = function (epg_flag) {
        var params = {
            width: 889,
            height: 530,
            autoOpen: false,
            modal: true,
            resizable: true,
            draggable: true,
            buttons: {
                "提交": function () {
                    BIMS_PANEL.submitPreviewPanel();
                },
                "取消": function () {
                    $("#panel_layout").omDialog("close");
                }
            },
            onClose: function () {
                if ($.isArray(clearInterval_arr) && clearInterval_arr.length > 0) {
                    $.each(clearInterval_arr, function (i, value) {
                        window.clearInterval(value);
                    })
                    clearInterval_arr = [];
                }
            }
        };
        if (epg_flag) {
            params["buttons"] = {};
        }
        return $("#panel_layout").omDialog(params);
    };

    PANEL.preview_package_dialog = function () {
        return $("#panel_layout").omDialog({
            width: 889,
            height: 560,
            autoOpen: false,
            modal: true,
            resizable: true,
            draggable: true,
            onOpen: function () {
                $('#package-tab').omTabs({
                    switchMode: 'click',
                    width: 889,
                    height: 570,
                    onActivate: function (n) {
                        //预览1080p
                        if (n == 1) {
                            BIMS_PANEL.config.package_dpi = "1080p";
                            $("#panel_content" + BIMS_PANEL.config.package_dpi).empty();
                            $("#panel_head_nva_content" + BIMS_PANEL.config.package_dpi).empty();
                            $("#panel_root_nav_content" + BIMS_PANEL.config.package_dpi).empty();
                            BIMS_PANEL.config.panel_index = 0;
                            BIMS_PANEL.destroy_preview_panels();
                            $.ajax({
                                type: 'GET',
                                url: 'panel_package_preview.json?packageId=' + $("#id").val() + "&dpi=" + BIMS_PANEL.config.package_dpi,
                                contentType: 'application/json',
                                success: function (data) {
                                    console.log(data);
                                    BIMS_PANEL.set_preview_panels(data);
                                }
                            });
                        }
                        //预览720p
                        if (n == 0) {
                            BIMS_PANEL.config.package_dpi = "720p";
                            $("#panel_content" + BIMS_PANEL.config.package_dpi).empty();
                            $("#panel_head_nva_content" + BIMS_PANEL.config.package_dpi).empty();
                            $("#panel_root_nav_content" + BIMS_PANEL.config.package_dpi).empty();
                            BIMS_PANEL.config.panel_index = 0;
                            BIMS_PANEL.destroy_preview_panels();
                            $.ajax({
                                type: 'GET',
                                url: 'panel_package_preview.json?packageId=' + $("#id").val() + "&dpi=" + BIMS_PANEL.config.package_dpi,
                                contentType: 'application/json',
                                success: function (data) {
                                    console.log(data);
                                    BIMS_PANEL.set_preview_panels(data);
                                }
                            });
                        }
                    }});
            },
            onClose: function () {
                if ($.isArray(clearInterval_arr) && clearInterval_arr.length > 0) {
                    $.each(clearInterval_arr, function (i, value) {
                        window.clearInterval(value);
                    })
                    clearInterval_arr = [];
                }
            }
        });
    };

    PANEL.panel_sort_dialog = function () {
        return $("#panel-sort").omDialog({
            width: 900,
            height: 580,
            autoOpen: false,
            modal: true,
            resizable: true,
            draggable: true,
            onOpen: function () {
                $('#config-package-tab').omTabs({
                    switchMode: 'click',
                    width: 900,
                    height: 530,
                    onActivate: function (n) {
                        //预览1080p
                        if (n == 1) {
                            BIMS_PANEL.config.isDisabled = false;
                            $("#panel-sort-grid" + BIMS_PANEL.config.package_dpi).omGrid("cancelEdit");
                            BIMS_PANEL.config.package_dpi = "1080p";
                            $('#panel-sort-grid' + BIMS_PANEL.config.package_dpi).omGrid('refresh');
                            $('#panel-sort-grid' + BIMS_PANEL.config.package_dpi).omGrid({
                                dataSource: "panel_list_packageId.json?packageId=" + $("#id").val() + "&dpi=" + BIMS_PANEL.config.package_dpi,
                                width: '100%',
                                height: 450,
                                singleSelect: false,
                                limit: limit,
                                colModel: [
                                    {header: '<b>面板名称</b>', name: 'id', align: 'center', width: 220, editor: {editable: true,
                                        type: "omCombo",
                                        name: "id",
                                        rules: ["required", true, "面板名称是必填的"],
                                        options: {dataSource: BIMS_PANEL.enums.panel_name_1080p}},
                                        renderer: function (value, rowData, rowIndex) {
                                            var name = "";
                                            $.each(BIMS_PANEL.enums.panel_name_1080p, function (i, nav_root) {
                                                if (nav_root['value'] == value) {
                                                    name = nav_root['text'];
                                                    return false;
                                                }
                                            });
                                            return name;
                                        }
                                    },
                                    {header: '<b>下部导航名称</b>', name: 'rootNavId', align: 'center', width: 120, editor: {editable: true,
                                        type: "omCombo",
                                        name: "rootNavId",
                                        rules: ["required", true, "下部导航名称是必填的"],
                                        options: {dataSource: BIMS_PANEL.enums.root_nav_list_1080p}},
                                        renderer: function (value, rowData, rowIndex) {
                                            var name = "";
                                            $.each(BIMS_PANEL.enums.root_nav_list_1080p, function (i, nav_root) {
                                                if (nav_root['value'] == value) {
                                                    name = nav_root['text'];
                                                    return false;
                                                }
                                            });
                                            return name;
                                        }
                                    },
                                    {header: '<b>上部导航名称</b>', name: 'headNavIds', align: 'center', width: 120, editor: {editable: true,
                                        type: "omCombo",
                                        name: "headNavIds",
                                        options: {dataSource: BIMS_PANEL.enums.head_nav_list_1080p}},
                                        renderer: function (value) {
                                            if (value) {
                                                var name = "";
                                                $.each(BIMS_PANEL.enums.head_nav_list_1080p, function (i, nav_root) {
                                                    if (nav_root['value'] == value) {
                                                        name = nav_root['text'];
                                                        return false;
                                                    }
                                                });
                                                return name;
                                            } else {
                                                return "";
                                            }
                                        }
                                    },
                                    {header: '<b>排序</b>', name: 'sort', align: 'center', width: 30, editor: {
                                        type: "omCombo",
                                        options: BIMS_PANEL.config.sort_options,
                                        editable: true,
                                        name: "sort"}},
                                    {header: '<b>是否显示</b>', name: 'display', align: 'center', width: 50, editor: {
                                        type: "omCombo",
                                        options: BIMS_PANEL.config.isLock_display,
                                        editable: true,
                                        name: "display"},
                                        renderer: BIMS_PANEL.show_isLock_display
                                    },
                                    {header: '<b>是否锁定</b>', name: 'isLock', align: 'center', width: 50, editor: {
                                        type: "omCombo",
                                        options: BIMS_PANEL.config.isLock_display,
                                        editable: true,
                                        name: "isLock"},
                                        renderer: BIMS_PANEL.show_isLock_display
                                    },
                                    {header: '<b>面板LOGO2</b>', name: 'panelLogo', align: 'center', width: 100, editor: {editable: true}}
                                ],
                                onBeforeEdit: function () {
                                    BIMS_PANEL.config.isDisabled = true;
                                },
                                onAfterEdit: function (rowIndex, rowData) {
                                    $('#panel-sort-grid' + BIMS_PANEL.config.package_dpi).omGrid('saveChanges');//用于及时更新标题值
                                    BIMS_PANEL.config.isDisabled = false;
                                },

                                onCancelEdit: function () {
                                    $("#panel-sort-grid" + BIMS_PANEL.config.package_dpi).omGrid("cancelEdit");
                                    BIMS_PANEL.config.isDisabled = false;
                                }
                            });
                        }
                        //预览720p
                        if (n == 0) {
                            BIMS_PANEL.config.isDisabled = false;
                            $("#panel-sort-grid" + BIMS_PANEL.config.package_dpi).omGrid("cancelEdit");
                            BIMS_PANEL.config.package_dpi = "720p";
                            $('#panel-sort-grid' + BIMS_PANEL.config.package_dpi).omGrid('refresh');
                            $('#panel-sort-grid' + BIMS_PANEL.config.package_dpi).omGrid({
                                dataSource: "panel_list_packageId.json?packageId=" + $("#id").val() + "&dpi=" + BIMS_PANEL.config.package_dpi,
                                width: '100%',
                                height: 450,
                                singleSelect: false,
                                limit: limit,
                                colModel: [
                                    {header: '<b>面板名称</b>', name: 'id', align: 'center', width: 220, editor: {editable: true,
                                        type: "omCombo",
                                        name: "id",
                                        rules: ["required", true, "面板名称是必填的"],
                                        options: {dataSource: BIMS_PANEL.enums.panel_name}},
                                        renderer: function (value, rowData, rowIndex) {
                                            var name = "";
                                            $.each(BIMS_PANEL.enums.panel_name, function (i, nav_root) {
                                                if (nav_root['value'] == value) {
                                                    name = nav_root['text'];
                                                    return false;
                                                }
                                            });
                                            return name;
                                        }
                                    },
                                    {header: '<b>下部导航名称</b>', name: 'rootNavId', align: 'center', width: 120, editor: {editable: true,
                                        type: "omCombo",
                                        name: "rootNavId",
                                        rules: ["required", true, "下部导航名称是必填的"],
                                        options: {dataSource: BIMS_PANEL.enums.root_nav_list}},
                                        renderer: function (value, rowData, rowIndex) {
                                            var name = "";
                                            $.each(BIMS_PANEL.enums.root_nav_list, function (i, nav_root) {
                                                if (nav_root['value'] == value) {
                                                    name = nav_root['text'];
                                                    return false;
                                                }
                                            });
                                            return name;
                                        }
                                    },
                                    {header: '<b>上部导航名称</b>', name: 'headNavIds', align: 'center', width: 120, editor: {editable: true,
                                        type: "omCombo",
                                        name: "headNavIds",
                                        options: {dataSource: BIMS_PANEL.enums.head_nav_list}},
                                        renderer: function (value) {
                                            if (value) {
                                                var name = "";
                                                $.each(BIMS_PANEL.enums.head_nav_list, function (i, nav_root) {
                                                    if (nav_root['value'] == value) {
                                                        name = nav_root['text'];
                                                        return false;
                                                    }
                                                });
                                                return name;
                                            } else {
                                                return "";
                                            }
                                        }
                                    },
                                    {header: '<b>排序</b>', name: 'sort', align: 'center', width: 30, editor: {
                                        type: "omCombo",
                                        options: BIMS_PANEL.config.sort_options,
                                        editable: true,
                                        name: "sort"}},
                                    {header: '<b>是否显示</b>', name: 'display', align: 'center', width: 50, editor: {
                                        type: "omCombo",
                                        options: BIMS_PANEL.config.isLock_display,
                                        editable: true,
                                        name: "display"},
                                        renderer: BIMS_PANEL.show_isLock_display
                                    },
                                    {header: '<b>是否锁定</b>', name: 'isLock', align: 'center', width: 50, editor: {
                                        type: "omCombo",
                                        options: BIMS_PANEL.config.isLock_display,
                                        editable: true,
                                        name: "isLock"},
                                        renderer: BIMS_PANEL.show_isLock_display
                                    },
                                    {header: '<b>面板LOGO2</b>', name: 'panelLogo', align: 'center', width: 100, editor: {editable: true}}
                                ],
                                onBeforeEdit: function () {
                                    BIMS_PANEL.config.isDisabled = true;
                                },
                                onAfterEdit: function (rowIndex, rowData) {
                                    $('#panel-sort-grid' + BIMS_PANEL.config.package_dpi).omGrid('saveChanges');//用于及时更新标题值
                                    BIMS_PANEL.config.isDisabled = false;
                                },
                                onCancelEdit: function () {
                                    $("#panel-sort-grid" + BIMS_PANEL.config.package_dpi).omGrid("cancelEdit");
                                    BIMS_PANEL.config.isDisabled = false;
                                }
                            });
                        }
                    }});
            }
        });
    };

    PANEL.show_isLock_display = function show_isLock_display(value) {
        if (value == "true") {
            return "是";
        } else {
            return "否";
        }
    };

    PANEL.epg_panel_sort_dialog = function () {
        return $("#epg_panel-review").omDialog({
            width: 900,
            height: 550,
            autoOpen: false,
            modal: true,
            resizable: false,
            draggable: false
        });
    };

    PANEL.box_dialog = function () {
        return $("#box-form").omDialog({
            width: 500,
            autoOpen: false,
            modal: true,
            resizable: false,
            draggable: false,
            buttons: {
                "提交": function () {
                    BIMS_PANEL.submitBox();
                },
                "取消": function () {
                    $("#box-form").omDialog("close");
                }
            }, onClose: function () {
            }
        });
    };

    PANEL.collect_panel_item_data = function () {
        /*  submitData = $("#panelItemForm").serialize();
         submitData = replaceToNullValue(submitData);
         return submitData;*/
        return {
            name: $("#name").val(),
            title: $("#title").val(),
            titleComment: $("#titleComment").val(),
            actionType: $("#actionType").val(),
            actionUrl: $("#actionUrl").val(),
            imageUrl: $("#imageUrl").val(),
            videoUrl: $("#videoUrl").val(),
            content: $("#panel_item_content").val(),
            contentType: $("#contentType").val(),
            refItemId: $("#refItemId").val(),
            panelItemParentId: $("#panelItemParentId").val(),
            autoRun: $("#autoRun").val(),
            focusRun: $("#focusRun").val(),
            showTitle: $("#showTitle").val(),
            hasSubItem: $("#hasSubItem").val(),
            animationRun: $("#animationRun").val(),
            defaultfocus: $("#defaultfocus").val(),
            params: $("#params").val(),
            resolution: $("#resolution_panelItem").val()
        };
    };

    PANEL.validator_bind_panelitem = function () {
        return $('#panelItemForm').validate({
            rules: {
                name: {required: true, maxlength: 256},
                title: {required: true, maxlength: 60},
                titleComment: {maxlength: 1024},
                contentType: {required: true},
//                actionUrl: {required: true, maxlength: 512},
                imageUrl: {required: true, maxlength: 255},
                videoUrl: {required: true, maxlength: 255}
            },
            messages: {
                name: {required: "面板项名称不能为空！", maxlength: "面板项名称最长256位字符！"},
                title: {required: "面板项标题不能为空！", maxlength: "标题最长60位字符！"},
                titleComment: {maxlength: "标题说明最长1024位字符！"},
                contentType: {required: "面板项类型不能为空！"},
//                actionUrl: {required: "动作地址不能为空！"},
                imageUrl: {required: "图片地址不能为空！", maxlength: "图片地址最长255位字符！"},
                videoUrl: {required: "视频地址不能为空！", maxlength: "图片地址最长255位字符！"}
            },
            errorPlacement: YST_APP.Error.errorPlacement,
            showErrors: YST_APP.Error.showErrors

        });
    };

    PANEL.check_bind_panelitem_validator = function () {
        if (BIMS_PANEL.config.select_exsit_flag) {
            $("#existPanelItemId").rules("add", "required");
            $("#name").rules("remove", "required");
            $("#title").rules("remove", "required");
            $("#contentType").rules("remove", "required");
//            $("#actionUrl").rules("remove", "required");
            $("#imageUrl").rules("remove", "required");
        } else {
            $("#existPanelItemId").rules("remove", "required");
            $("#name").rules("add", "required");
            $("#title").rules("add", "required");
            $("#contentType").rules("add", "required");
//            $("#actionUrl").rules("add", "required");
            $("#imageUrl").rules("add", "required");
        }
    };

    PANEL.check_panel_item_add_or_edit_validator = function () {
        if ($('#contentType').val() != 'video') {
            $("#videoUrl").rules("remove", "required");
        }
    }

    PANEL.call_back_func_submit_panelitem = function () {
        $("#panel-item-form").omDialog("close");
        this.display_preview_panel();
    };

    PANEL.check_if_exist_parent_item = function () {
        var exist_flag = false;
        $.each(preview_item_datas, function (index, item_data) {
            if (item_data.panelItem && item_data.panelItem.hasSubItem == 1 && item_data.panelItem.refItemId) {
                exist_flag = true;
                return false;
            }
        });
        return exist_flag;
    };

    PANEL.submitPanelItem = function () {
        var validator_panelItem = BIMS_PANEL.validator_bind_panelitem();
        BIMS_PANEL.check_bind_panelitem_validator();
        BIMS_PANEL.check_panel_item_add_or_edit_validator();
        if (validator_panelItem.form()) {
            var panelItemId = $("#panelItemId").val();
            if (BIMS_PANEL.config.select_exsit_flag) {
                var selectedPanelItemId = $("#existPanelItemId").val();
                if (panelItemId && panelItemId == selectedPanelItemId) {
                    $("#panel-item-form").omDialog("close");
                } else {
                    $.get("get_panel_item_detail.json?id=" + selectedPanelItemId, function (panelItem) {
                        if (panelItem.hasSubItem == 1 && BIMS_PANEL.check_if_exist_parent_item()) {
                            $.omMessageTip.show({title: tip, content: "已经存在父面板项！", type: "error", timeout: time});
                            return;
                        }
                        if (panelItem.hasSubItem == 1 && !panelItem.refItemId) {
                            auto_play_items["auto_play_" + panelItem.id] = panelItem.childrenList;
                        }
                        if (panelItem) {
                            var preview_item_data = preview_item_datas[$("#previewItemDataIndex").val()];
                            preview_item_data.contentItemId = selectedPanelItemId;
                            preview_item_data.panelItem = panelItem;
                        }
                        BIMS_PANEL.call_back_func_submit_panelitem();
                    });
                }
            } else {
                var submitData = this.collect_panel_item_data();
                replaceJsonToNullValue(submitData);
                console.log(submitData);
                if (submitData.hasSubItem == 1 && BIMS_PANEL.check_if_exist_parent_item()) {
                    $.omMessageTip.show({title: tip, content: "已经存在父面板项！", type: "error", timeout: time});
                    return;
                }

                //编辑现有的面板项
                if (panelItemId) {
//                    submitData.panelItemId = panelItemId;
                    submitData.id = panelItemId;
                    $.ajaxFileUpload({
                        url: 'update_binded_panel_item.json',
                        secureuri: false,
                        fileElementId: ['fileField2', 'fileField3'],
                        data: submitData,
                        dataType: 'JSON',
                        success: function (panelItem) {
                            var jsonObject = JSON.parse(panelItem);
//                    $.post('update_binded_panel_item.json', submitData, function (panelItem) {
//                        if (panelItem && panelItem.id) {
                            if (jsonObject && jsonObject['id']) {
                                var preview_item_data = preview_item_datas[$("#previewItemDataIndex").val()];
                                preview_item_data.panelItem = jsonObject;
                                if (jsonObject.hasSubItem == 1 || jsonObject.contentType == 'ref') {
                                    $.get("get_panel_item_detail.json?id=" + jsonObject['id'], function (detailPanelItem) {
                                        if (detailPanelItem) {
                                            preview_item_data.panelItem = detailPanelItem;
                                        }
                                        BIMS_PANEL.call_back_func_submit_panelitem();
                                    });
                                } else {
                                    BIMS_PANEL.call_back_func_submit_panelitem();
                                }
                            } else {
                                if ("success" == panelItem) {
                                    BIMS_PANEL.show_message("修改面板项成功！", BIMS_PANEL.config.msg_success);
                                } else if ("related" == result) {
                                    YST_APP.show_message("该父面板项已经被关联，不可改为子面板项！", 'error');
                                } else if ("typeChange" == result) {
                                    YST_APP.show_message("该父面板项已经被关联，不可修改内容类型！", 'error');
                                } else {
                                    YST_APP.show_message("修改面板项失败！", 'error');
                                }
                                BIMS_PANEL.call_back_func_submit_panelitem();
                            }
                        }
                    });
                } else {

                    //新绑定面板项
                    submitData.panelId = $("#panelId").val();
                    $.ajaxFileUpload({
                        url: 'bind_panel_item.json',
                        secureuri: false,
                        fileElementId: ['fileField2', 'fileField3'],
                        data: submitData,
                        dataType: 'JSON',
                        success: function (panelItem) {
                            var jsonObject = JSON.parse(panelItem);
                            if (jsonObject && jsonObject['id']) {
                                var preview_item_data = preview_item_datas[$("#previewItemDataIndex").val()];
                                preview_item_data.contentItemId = jsonObject['id'];
                                preview_item_data.panelItem = jsonObject;
                                if (jsonObject.hasSubItem == 1 || jsonObject.contentType == 'ref') {
                                    $.get("get_panel_item_detail.json?id=" + jsonObject['id'], function (detailPanelItem) {
                                        if (detailPanelItem) {
                                            preview_item_data.panelItem = detailPanelItem;
                                            BIMS_PANEL.call_back_func_submit_panelitem();
                                        }
                                    });
                                } else {
                                    BIMS_PANEL.call_back_func_submit_panelitem();
                                }
                            } else {
                                if ("success" == panelItem) {
                                    $.omMessageTip.show({title: tip, content: "配置面板项成功！", type: "success", timeout: time});
                                } else if ("failed" == panelItem) {
                                    $.omMessageTip.show({title: tip, content: "配置面板项失败！", type: "error", timeout: time});
                                } else {
                                    $.omMessageTip.show({title: tip, content: "不可重复关联面板项！", type: "error", timeout: time});
                                }
                                BIMS_PANEL.call_back_func_submit_panelitem();
                            }
                        }
                    });
                }
            }
        }
    };

    PANEL.submitPreviewPanel = function () {
        $.each(preview_item_datas, function (index, obj) {
            if (obj.panelItem) {
                obj.panelItem.createTime = null;
                obj.panelItem.updateTime = null;
            }
        });
        $.ajax({
            type: 'POST',
            url: 'batch_update_preview_item_data.json',
            contentType: 'application/json',
            data: JSON.stringify(preview_item_datas),
            success: function (result) {
                if ("success" == result) {
                    $.omMessageTip.show({title: tip, content: "面板配置成功！", type: "success", timeout: time});
                } else {
                    $.omMessageTip.show({title: tip, content: "面板配置失败！", type: "error", timeout: time});
                }
                $("#panel_layout").omDialog("close");
            }
        });

    };

    PANEL.panel_item_dialog = function () {
        return $("#panel-item-form").omDialog({
            width: 600,
            autoOpen: false,
            modal: true,
            resizable: true,
            draggable: true,
            buttons: {
                "提交": function () {
                    var sr = $("#showResult1").html();
                    if (!$("#showResultActionUrl2").html() && !$('#showResultImageDistr2').html() && !$('#showResultVideoUrl2').html() && (sr == '' || sr == '可用!')) {
                        BIMS_PANEL.submitPanelItem();
                    } else {
                        YST_APP.show_message("URL格式不正确！", 'error');
                        return false;
                    }
                },
                "取消": function () {
                    $("#panel-item-form").omDialog("close");
                }
            }, onClose: function () {
                BIMS_PANEL.validator_bind_panelitem().resetForm();
            }
        });
    };

    PANEL.editBox = function (index) {
        this.config.isAdd = false;
        $("#templateBoxIndex").val(index);
        var box = preview_boxs[index];
        $("#top").omCombo({dataSource: this.generate_top(), value: box.top, editable: false, width: 120, listMaxHeight: 120});
        $("#height").omCombo({dataSource: this.generate_height(), value: box.height, editable: false, width: 120, listMaxHeight: 120});
        $("#boxLeft").omCombo({dataSource: this.generate_left(), value: box.left, editable: false, width: 120, listMaxHeight: 120});
        $("#width").omCombo({dataSource: this.generate_width(), value: box.width, editable: false, width: 120, listMaxHeight: 120});
        $("#sort").val(box.sort);
        this.box_dialog().omDialog("option", "title", "模板块信息");
        this.box_dialog().omDialog("open");
    };

    PANEL.deleteBox = function (index) {
        preview_boxs.splice(index, 1);
        this.display_preview_template();
    };

    PANEL.show_or_hide_panelitem = function (contentType, dpi) {
        contentType = contentType ? contentType : BIMS_PANEL.config.panel_item_content_type;
        if (this.config.panel_item_content_type == "video") {
            $('#videoUrlTr').show();
        } else {
            $('#videoUrlTr').hide();
        }
        if ($("#hasSubItem").val() != 1) {
            $.get("get_parent_item_list.json?contentType=" + contentType + "&dpi=" + dpi, function (data) {
                $("#panelItemParentIdTr").show();
                $('#panelItemParentId').omCombo({dataSource: data, value: BIMS_PANEL.config.panel_item_parent_id || "", editable: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height,
                    onValueChange: BIMS_PANEL.panel_item_parent_change});
            });
            $.get("get_related_item_list.json?contentType=" + contentType + "&dpi=" + dpi, function (data) {
                $('#refItemId').omCombo({dataSource: data, value: BIMS_PANEL.config.panel_item_ref_id || "", editable: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height,
                    onValueChange: BIMS_PANEL.panel_item_related_change});
            });
        }
    };

    PANEL.show_edit_panelitem = function (contentType, refPanelItemId, parentItemId, hasSubItem, resolution) {
        $('#hasSubItem').omCombo({dataSource: YST_APP.YES_NO, value: hasSubItem, editable: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height,
            onValueChange: BIMS_PANEL.has_sub_item_change});

        //不可以包含子面板项的面板项，显示关联列表和父面板项
        if ($("#hasSubItem").val() != 1) {
            $.get("get_parent_item_list_update.json?contentType=" + contentType + "&refPanelItemId=" + parentItemId + "&dpi=" + resolution, function (data) {
                $("#panelItemParentIdTr").show();
                $('#panelItemParentId').omCombo({dataSource: data, value: parentItemId, editable: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height,
                    onValueChange: BIMS_PANEL.panel_item_parent_change});
            });
            $.get("get_related_item_list_update.json?contentType=" + contentType + "&refPanelItemId=" + refPanelItemId + "&dpi=" + resolution, function (data) {
                $('#refItemId').omCombo({dataSource: data, value: refPanelItemId, editable: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height,
                    onValueChange: BIMS_PANEL.panel_item_related_change});
            });
        }
    };

    PANEL.panel_item_related_change = function (target, newValue) {
        if (newValue) {
            $("#panelItemParentIdTr").hide();
            $("#panelItemParentId").val("");
        } else {
            $("#panelItemParentIdTr").show();
        }
    };

    PANEL.panel_item_parent_change = function (target, newValue) {
        if (newValue) {
            $("#refItemIdTr").hide();
            $("#refItemId").val("");
        } else {
            $("#refItemIdTr").show();
        }
    };

    PANEL.content_type_change = function (target, newValue) {
        if (newValue) {
            BIMS_PANEL.config.panel_item_content_type = newValue;
        }
        BIMS_PANEL.show_or_hide_panelitem(BIMS_PANEL.config.panel_item_content_type, BIMS_PANEL.config.panel_dpi);
    };

    PANEL.action_type_change = function (target, newValue) {
        BIMS_PANEL.config.panel_item_action_type = newValue;
    };

    PANEL.panel_dpi_change = function (target, newValue) {
        if (newValue) {
            BIMS_PANEL.config.panel_dpi = newValue;
        }
        BIMS_PANEL.show_or_hide_panelitem(BIMS_PANEL.config.panel_item_content_type, BIMS_PANEL.config.panel_dpi);
    };

    PANEL.change_panel_item_content_type = function (hasSubItemValue) {
        if (this.check_array(this.enums.panel_item_content_type) && this.enums.panel_item_content_type.length > 0) {
            var lastType = this.enums.panel_item_content_type[this.enums.panel_item_content_type.length - 1];
            if (hasSubItemValue == 1) {
                if (lastType.value == 'ref') {
                    this.enums.panel_item_content_type.pop();
                }
                $('#contentType').omCombo({dataSource: this.enums.panel_item_content_type, value: this.config.panel_item_content_type != 'ref' ? this.config.panel_item_content_type : "", editable: false, width: 180, listMaxHeight: BIMS_PANEL.config.select_input_height,
                    onValueChange: this.content_type_change});
            } else {
                $('#contentType').omCombo({dataSource: this.enums.panel_item_content_type, value: this.config.panel_item_content_type, editable: false, width: 180, listMaxHeight: BIMS_PANEL.config.select_input_height,
                    onValueChange: this.content_type_change});
            }

        }
    };

    PANEL.has_sub_item_change = function (target, newValue) {
        BIMS_PANEL.change_panel_item_content_type(newValue);
        if (newValue == 1) {
            BIMS_PANEL.config.panel_item_ref_id = null;
            BIMS_PANEL.config.panel_item_parent_id = null;
            $("#refItemIdTr").hide();
            $("#refItemId").val("");
            $("#panelItemParentIdTr").hide();
            $("#panelItemParentId").val("");
        } else {

            $.get("get_parent_item_list.json?contentType=" + $("#contentType").val() + "&dpi=" + $("#resolution").val(), function (data) {
                $("#panelItemParentIdTr").show();
                $('#panelItemParentId').omCombo({dataSource: data, value: BIMS_PANEL.config.panel_item_parent_id || "", editable: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height,
                    onValueChange: BIMS_PANEL.panel_item_parent_change});
            });
            $.get("get_related_item_list.json?contentType=" + $("#contentType").val(), function (data) {
                $("#refItemIdTr").show();
                $('#refItemId').omCombo({dataSource: data, value: BIMS_PANEL.config.panel_item_ref_id || "", editable: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height,
                    onValueChange: BIMS_PANEL.panel_item_related_change});
            });
        }
    }

    PANEL.select_exist_panel_item_change = function () {
        var panelItemId = $("#panelItemId").val();
        if ($(this).val() == 1) {
            BIMS_PANEL.config.select_exsit_flag = 1;
            $("#selectExistTr ~ tr").hide();
            $("contentTypeTr").hide();
            $("#existPanelItemIdTr").show();

            $.get("find_dpi_panel_item_list.json?panelId=" + $("#panelId").val(), function (data) {
                $('#existPanelItemId').omCombo({dataSource: data, value: panelItemId || '', editable: true, autoFilter: true, filterStrategy: 'anywhere', forceSelection: true, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height});
            });
        } else {
            BIMS_PANEL.config.select_exsit_flag = 0;
            $("#selectExistTr ~ tr").show();
            $("#existPanelItemIdTr").hide();

            //判断面板项类型
            if ($('#contentType').val() == "video") {
                $('#videoUrlTr').show();
            } else {
                $('#videoUrlTr').hide();
            }

            /* $("contentTypeTr").show();
             $('#contentType').omCombo({dataSource: BIMS_PANEL.enums.panel_item_content_type, value: BIMS_PANEL.config.panel_item_content_type, editable: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height,
             onValueChange: BIMS_PANEL.content_type_change});*/
        }
    };

    PANEL.editPanelItem = function (index) {
        $("#showResult1").html("");
        $("#showResultAppEnterUrl2").html("");
        $("#showResultActionUrl2").html("");
        $('#showResultImageDistr2').html("");
        $('#showResultVideoUrl2').html("");
        $("#previewItemDataIndex").val(index);
        var previewItemData = preview_item_datas[index];
        $("#panelItemId").val(previewItemData.contentItemId);
        $("#selectExist").omCombo({dataSource: YST_APP.YES_NO, value: 0, editable: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height,
            onValueChange: BIMS_PANEL.select_exist_panel_item_change});
        if (previewItemData.contentItemId) {
            this.config.panel_item_edit_flag = true;
            this.config.panel_item_edit_id = previewItemData.contentItemId;
            $.ajax({
                url: "get_panel_item.json?id=" + previewItemData.contentItemId,
                dataType: "json",
                success: function (data) {
                    if (data && data.id) {
                        BIMS_PANEL.config.panel_item_content_type = data['contentType'];
                        BIMS_PANEL.config.panel_item_ref_id = data['refItemId'];
                        BIMS_PANEL.config.panel_item_parent_id = data['panelItemParentId'];
                        BIMS_PANEL.config.panel_item_action_type = data['actionType'];
                        BIMS_PANEL.config.panel_dpi = data['resolution'];
                        BIMS_PANEL.show_edit_panelitem(data['contentType'], data['refItemId'] || "", data['panelItemParentId'] || "", data['hasSubItem'], data['resolution']);
                        $("#panelItemId").val(data['id']);
                        $("#name").val(data['name']);
                        $("#title").val(data['title']);
                        $("#titleComment").val(data['titleComment']);
                        $("#actionUrl").val(data['actionUrl']);
                        $("#imageUrl").val(data['imageUrl']);
                        $("#videoUrl").val(data['videoUrl']);
                        $("#panel_item_content").val(data['content']);
                        $("#contentType").val(data['contentType']);
                        $('#autoRun').omCombo({dataSource: YST_APP.YES_NO, value: data['autoRun'], editable: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height});
                        $('#focusRun').omCombo({dataSource: YST_APP.YES_NO, value: data['focusRun'], editable: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height});
                        $('#showTitle').omCombo({dataSource: YST_APP.YES_NO, value: data['showTitle'], editable: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height});
                        $('#animationRun').omCombo({dataSource: YST_APP.YES_NO, value: data['animationRun'], editable: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height});
                        $('#contentType').omCombo({dataSource: BIMS_PANEL.enums.panel_item_content_type, value: data['contentType'], editable: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height,
                            onValueChange: BIMS_PANEL.content_type_change});
                        $('#actionType').omCombo({dataSource: BIMS_PANEL.enums.panel_item_action_type, value: data['actionType'], editable: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height,
                            onValueChange: BIMS_PANEL.action_type_change});
                        $("#defaultfocus").omCombo({
                            dataSource: [
                                //left、center或right
                                {text: '是', value: 'true'},
                                {text: '否', value: 'false'}
                            ], value: data['defaultfocus'], editable: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height});
                        $("#resolution_panelItem").omCombo({dataSource: BIMS_PANEL.config.resolution, value: data['resolution'], editable: false,disabled:true, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height,
                            onValueChange: BIMS_PANEL.panel_dpi_change});
                        $("#params").val(data['params']);
                    } else {
                        this.config.panel_item_content_type = "image";
                        this.config.panel_item_action_type = "1";
                        this.config.panel_item_edit_flag = false;
                        this.config.panel_item_edit_id = null;
                        this.config.panel_item_parent_id = null;
                        this.config.panel_item_ref_id = null;
                        this.config.panel_item_parent_id = null;
                        this.config.panel_dpi = BIMS_PANEL.config.resolution[0]["value"];
                        $('#autoRun').omCombo({dataSource: YST_APP.YES_NO, value: '0', editable: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height});
                        $('#focusRun').omCombo({dataSource: YST_APP.YES_NO, value: '1', editable: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height});
                        $('#showTitle').omCombo({dataSource: YST_APP.YES_NO, value: '0', editable: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height});
                        $('#animationRun').omCombo({dataSource: YST_APP.YES_NO, value: '0', editable: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height});
                        $('#hasSubItem').omCombo({dataSource: YST_APP.YES_NO, value: '0', editable: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height,
                            onValueChange: this.has_sub_item_change});
                        $('#contentType').omCombo({dataSource: BIMS_PANEL.enums.panel_item_content_type, value: BIMS_PANEL.config.panel_item_content_type, editable: false, width: 180, listMaxHeight: BIMS_PANEL.config.select_input_height,
                            onValueChange: this.content_type_change});
                        $('#actionType').omCombo({dataSource: BIMS_PANEL.enums.panel_item_action_type, value: '1', editable: false, width: 180, listMaxHeight: BIMS_PANEL.config.select_input_height,
                            onValueChange: BIMS_PANEL.action_type_change});
                        $("#defaultfocus").omCombo({
                            dataSource: [
                                //left、center或right
                                {text: '是', value: 'true'},
                                {text: '否', value: 'false'}
                            ], value: 'false', editable: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height});
                        $("#resolution_panelItem").omCombo({dataSource: BIMS_PANEL.config.resolution, value: BIMS_PANEL.config.resolution[0]["value"], editable: false,disabled: false,width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height,
                            onValueChange: BIMS_PANEL.panel_dpi_change});
                    }
                }
            });
        } else {
            this.config.panel_item_content_type = "image";
            this.config.panel_item_action_type = "1";
            this.config.panel_item_edit_flag = false;
            this.config.panel_item_edit_id = null;
            this.config.panel_item_ref_id = null;
            this.config.panel_item_parent_id = null;
            this.config.panel_dpi = BIMS_PANEL.config.resolution[0]["value"];
            $('#autoRun').omCombo({dataSource: YST_APP.YES_NO, value: '0', editable: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height});
            $('#focusRun').omCombo({dataSource: YST_APP.YES_NO, value: '1', editable: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height});
            $('#showTitle').omCombo({dataSource: YST_APP.YES_NO, value: '0', editable: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height});
            $('#animationRun').omCombo({dataSource: YST_APP.YES_NO, value: '0', editable: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height});
            $('#hasSubItem').omCombo({dataSource: YST_APP.YES_NO, value: '0', editable: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height,
                onValueChange: this.has_sub_item_change});
            $('#contentType').omCombo({dataSource: this.enums.panel_item_content_type, value: BIMS_PANEL.config.panel_item_content_type, editable: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height,
                onValueChange: this.content_type_change});
            $('#actionType').omCombo({dataSource: this.enums.panel_item_action_type, value: '1', editable: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height,
                onValueChange: BIMS_PANEL.action_type_change});
            $("#defaultfocus").omCombo({
                dataSource: [
                    //left、center或right
                    {text: '是', value: 'true'},
                    {text: '否', value: 'false'}
                ], value: 'false', editable: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height});
            $("#resolution_panelItem").omCombo({dataSource: BIMS_PANEL.config.resolution, value: BIMS_PANEL.config.resolution[0]["value"], editable: false,disabled: false,width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height,
                onValueChange: BIMS_PANEL.panel_dpi_change});
        }
        this.panel_item_dialog().omDialog("option", "title", "模板块信息");
        this.panel_item_dialog().omDialog("open");
    };

    PANEL.auto_play_panel_items = function (auto_play_id) {
        var child_auto_items = auto_play_items[auto_play_id];
        var init_index = 0;
        if ($.isArray(child_auto_items) && child_auto_items.length > 0) {
            var interval_id = window.setInterval(function () {
                var items_length = child_auto_items.length;
                var child_item = child_auto_items[init_index];
                $("#" + auto_play_id).attr("src", child_item.imageUrl);
                init_index++;
                if (init_index == items_length) {
                    init_index = 0;
                }
            }, this.config.auto_display_millis);
            clearInterval_arr.push(interval_id);
        }
    };

    (function () {
        $("#panel_layout .panel_head_nva_img").live('mouseenter', function () {
            var arr_param = $(this).attr("id").split("_");
            var index = arr_param[1], showTitle = arr_param[2];
            if (showTitle == "1") {
                $(this).replaceWith('<a href="#" id="headNavTitle_' + index + '_' + showTitle +
                    '"  class="panel_head_nva panel_root_nva_active">' + BIMS_PANEL.config.head_navs[index].title + '</a>');
            }
        });
        $("#panel_layout .panel_head_nva").live('mouseleave', function () {
            var arr_param = $(this).attr("id").split("_");
            var index = arr_param[1], showTitle = arr_param[2];
            if (showTitle == "1") {
                $(this).replaceWith('<img class="panel_head_nva_img" src="' + BIMS_PANEL.config.head_navs[index].imageUrl +
                    '" id="headNav_' + index + '_' + showTitle + '" alt=""/>');
            }
        });

        $("#panel_layout li[id^='childrenPanelItem_']").live('mouseenter', function () {
            $(this).siblings().removeClass("li_active");
            $(this).addClass("li_active");
            var children_id = $(this).attr("id"), id_arr = children_id.split("_");
            if (id_arr.length == 3) {
                var parent_id = id_arr[1], index = id_arr[2];
                $("#related_item_" + parent_id).attr("src", item_children_list[index]["imageUrl"]);
            }
        });

        $("#panel_layout img[id^='auto_play_']").live("auto_display", function () {
            var auto_play_id = $(this).attr("id");
            BIMS_PANEL.auto_play_panel_items(auto_play_id);
        });
    }());

    return PANEL;

}());
