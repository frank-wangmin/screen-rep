/**
 * Created by frank on 14-11-18.
 */
$(document).ready(function () {
    $('#center-tab').omTabs({height: "33", border: false});
    var enums = {};
    var is_custom;
    var sortOptions = [
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
    ];

    var epgEditArr = ["isCustom", "display", "isLock", "bigimg", "smallimg"];

    var epg_edit_flag_func = function (name) {
        var flag = false;
        $.each(epgEditArr, function (index, value) {
            if (name == value) {
                flag = true;
                return false;
            }
        });
        return flag;
    }

    var hidden_or_show_style_date = function () {
        if (is_custom == 0) {
            $("#isLockTr").hide();
            $('#displayTr').hide();
        } else {
            $("#isLockTr").show();
            $('#displayTr').show();
        }
    };

    var add_display_isLock = function (submitData) {
        submitData.isLock = $("#isLock").val();
        submitData.display = $("#display").val();
    };

    var is_custom_change = function (target, newValue, oldValue, event) {
        is_custom = newValue;
        hidden_or_show_style_date();
    };

    var init = function () {
        $.getJSON("panel_item_content_type.json", function (data) {
            BIMS_PANEL.enums.panel_item_content_type = data;
        });
        $.getJSON("panel_item_action_type.json", function (data) {
            BIMS_PANEL.enums.panel_item_action_type = data;
        });
        enums.isLock_display = [
            {text: "是", value: "true"},
            {text: "否", value: "false"}
        ];
        is_custom = 1;
    };

    //初始化
    init();

    $('#save').omButtonbar({btns: [
        {label: "新增", icons: {left: opPath + 'add.png'}}
    ]});
    $('#update').omButtonbar({btns: [
        {label: "修改", icons: {left: opPath + 'op-edit.png'}}
    ]});
    $('#delete').omButtonbar({btns: [
        {label: "删除", icons: {left: opPath + 'remove.png'}}
    ]});
    $('#query').omButtonbar({btns: [
        {label: "查询", icons: {left: opPath + 'search.png'}}
    ]});
    $('#queryPanelPackageForm').omButtonbar({btns: [
        {label: "查询", icons: {left: opPath + 'search.png'}}
    ]});
    $('#bindPackage').omButtonbar({btns: [
        {label: "绑定面板包", icons: {left: opPath + 'op-edit.png'}}
    ]});

    $('#previewPanel').omButtonbar({btns: [
        {label: "面板配置预览", icons: {left: opPath + 'op-edit.png'}}
    ]});

    $('#synsPanel').omButtonbar({btns: [
        {label: "同步播控数据", icons: {left: opPath + 'op-edit.png'}}
    ]});

    $('#pushPanel').omButtonbar({btns: [
        {label: "同步省级数据", icons: {left: opPath + 'op-edit.png'}}
    ]});
    /*
     $('#syncCenterPanel').omButtonbar({btns: [
     {label: "同步中心数据", icons: {left: opPath + 'op-edit.png'}}
     ]});

     $('#panelDistribute').omButtonbar({btns: [
     {label: "面板下发", icons: {left: opPath + 'op-edit.png'}}
     ]});*/
    $('#panelOnline').omButtonbar({btns: [
        {label: "上线", icons: {left: opPath + 'op-edit.png'}}
    ]});
    $('#panelDownline').omButtonbar({btns: [
        {label: "下线", icons: {left: opPath + 'op-edit.png'}}
    ]});

    /*   $.getJSON("panel_item_type.json", function (data) {
     BIMS_PANEL.enums.panel_item_type = data;
     });*/

    var validator = $('#myform').validate({
        rules: {
            panelName: {required: true, maxlength: 60},
            panelTitle: {required: true, maxlength: 60},
            panelStyle: {required: true},
            templateId: {required: true},
            bigimg: {required: true},
            smallimg: {required: true},
            panelIcon: {maxlength: 128},
            linkUrl: {maxlength: 128},
            imgUrl: {maxlength: 128}
            /*panelTitle: {maxlength: 60},
             panelName: {maxlength: 60}*/
        },
        messages: {
            panelName: {required: "面板名称不能为空！", maxlength: "面板名称最长60位字符！"},
            panelTitle: {required: "面板标题不能为空！", maxlength: "面板标题最长60位字符！"},
            panelStyle: {required: "面板式样不能为空！"},
            templateId: {required: "面板模板不能为空！"},
            bigimg: {required: "定制页大图不能为空！"},
            smallimg: {required: "定制页小图不能为空！"},
            panelIcon: {maxlength: "面板图标最长128位字符！"},
            linkUrl: {maxlength: "链接地址最长128位字符！"},
            imgUrl: {maxlength: "背景图片地址最长128位字符！"}
            /*panelTitle: {maxlength: "面板标题最长60位字符！"},
             panelName: {maxlength: "面板名称最长60位字符！"}*/

        },
        errorPlacement: YST_APP.Error.errorPlacement,
        showErrors: YST_APP.Error.showErrors
    });


    var isAdd = true;
    $('#grid').omGrid({
        dataSource: 'panel_list.json',
        width: '100%',
        height: window.innerHeight - 90,
        singleSelect: true,
        limit: limit,
        colModel: [
            {header: '<b>ID</b>', name: 'id', align: 'center', width: 50},
            {header: '<b>面板名称</b>', name: 'panelName', align: 'center', width: 130},
            {header: '<b>面板标题</b>', name: 'panelTitle', align: 'center', width: 130},
            {header: '<b>是否自定义</b>', name: 'isCustom', align: 'center', width: 60, renderer: YST_APP.YES_NO_FUNC},
            {header: '<b>分辨率</b>', name: 'resolution', align: 'center', width: 60},
            {header: '<b>创建时间</b>', name: 'createTime', align: 'center', width: 120, renderer: YST_APP.showDate},
            {header: '<b>状态</b>', name: 'onlineStatus', align: 'center', renderer: BIMS_PANEL.show_online_status},
            {header: '<b>链接地址</b>', name: 'linkUrl', align: 'center', width: 'autoExpand'}
        ],
        onRowClick: function (index, rowData, event) {
            parent.frames["panel_item_list"].showInitData("panel_item_list_by_panelId.json?panelId=" + rowData.id);
        },
        onSuccess: function (data) {
            if (data.rows && data.rows.length > 0) {
                if (parent.frames["panel_item_list"].document.readyState == 'complete') {
                    parent.frames["panel_item_list"].showInitData("panel_item_list_by_panelId.json?panelId=" + data.rows[0].id);
                }
            }
        },
        rowDetailsProvider: function (rowData, rowIndex) {
            return '第' + (rowIndex + 1) + '行'
                + '</br>主键ID=' + rowData.id
                + '</br>面板名称=' + rowData.panelName
                + '</br>面板标题=' + rowData.panelTitle
                + '</br>是否自定义=' + YST_APP.YES_NO_FUNC(rowData.isCustom)
                + '</br>分辨率=' + rowData.resolution
                + '</br>创建时间=' + YST_APP.showDate(rowData.createTime)
                + '</br>链接地址=' + rowData.linkUrl
                + '</br>上线状态=' + BIMS_PANEL.show_online_status(rowData.onlineStatus);
        }
    });

    $('#query').bind('click', function () {
        var Id = $.trim($('#queryId').val());
        if (Id != '' && !YST_APP.isInteger(Id)) {
            YST_APP.alertMsg("ID必须为正整数类型");
            return false;
        }
        var name = $.trim($('#queryPanelName').val());
        var title = $.trim($('#queryPanelTitle').val());
        $('#grid').omGrid("setData", 'panel_list.json?name=' + name + "&title=" + title + "&Id=" + Id);
    });

    $('#save').bind('click', function () {
        $("#showResultsmallimg").html("");
        $("#showResultLinkUrl").html("");
        $("#showResultImageUrl").html("");
        $("#showResultbigimg").html("");
        $('#showResultpanelIcon').html("");
        isAdd = true;
        $("#id").val('');
        $('#templateId').val('');
        $("#showResult").html("");
        $(".form-add-input").each(function () {
            $(this).val("");
        });
        $(".form-border-style").each(function () {
            $(this).val("");
        });
        $("#myform > table tr input").each(function () {
            if (!epg_edit_flag_func($(this).attr("id"))) {
                $(this).prop("disabled", false);
            }
        })
        $('#isCustom').omCombo({dataSource: YST_APP.YES_NO, value: '1', editable: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height,
            onValueChange: is_custom_change});
        $('#templateId').omCombo({dataSource: 'get_all_preview_templateList.json', value: '', editable: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height, disabled: false});
        $("#resolution").omCombo({dataSource: YST_APP.RESOLUTION, value: '720p', editable: false,disabled:false,width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height});

        showDialog('新增面板信息');
    });

    $('#update').bind('click', function () {
        $("#showResultsmallimg").html("");
        $("#showResultLinkUrl").html("");
        $("#showResultImageUrl").html("");
        $("#showResultbigimg").html("");
        $('#showResultpanelIcon').html("");
        isAdd = false;
        $("#showResult").html("");
        var selections = $('#grid').omGrid('getSelections', true);
        if (selections.length == 0 || selections.length > 1) {
            YST_APP.alertMsg("修改操作至少且只能选择一行记录！");
            return false;
        }

        if (selections[0].districtCode != districtCode) {
            YST_APP.alertMsg("不可以修改省级数据！");
            return false;
        }

        if (selections[0].epgPanelId) {
            $("#myform > table tr input").each(function () {
                if (!epg_edit_flag_func($(this).attr("id"))) {
                    $(this).prop("disabled", true);
                }
            })
        } else {
            $("#myform > table tr input").each(function () {
                if (!epg_edit_flag_func($(this).attr("id"))) {
                    $(this).prop("disabled", false);
                }
            })
        }
        $.ajax({
            url: "get_panel_byId.json?id=" + selections[0].id,
            dataType: "json",
            success: function (data) {
                $("#id").val(data['id']);
                is_custom = data['isCustom'];
                hidden_or_show_style_date();
                $("#panelName").val(data['panelName']);
                $("#panelTitle").val(data['panelTitle']);
                $("#panelStyle").val(data['panelStyle']);
                $("#panelIcon").val(data['panelIcon']);
                $("#linkUrl").val(data['linkUrl']);
                $("#imgUrl").val(data['imgUrl']);
                $("#bigimg").val(data['bigimg']);
                $("#smallimg").val(data['smallimg']);
                $('#isCustom').omCombo({dataSource: YST_APP.YES_NO, value: is_custom, editable: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height,
                    onValueChange: is_custom_change});
                if (selections[0].epgPanelId) {
                    $('#templateId').omCombo({editable: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height, disabled: true});
                } else {
                    $('#templateId').omCombo({dataSource: 'get_all_preview_templateList.json', value: data['templateId'] || "", editable: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height, disabled: false});
                }
                $("#resolution").omCombo({dataSource: YST_APP.RESOLUTION, value: data['resolution'], editable: false,disabled:true, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height});
            }
        });

        showDialog('修改面板信息', selections[0]);
    });

    var dialog = $("#dialog-form").omDialog({
        width: 650,
        autoOpen: false,
        modal: true,
        resizable: false,
        draggable: false,
        buttons: {
            "提交": function () {
                submitDialog();
            },
            "取消": function () {
                $("#dialog-form").omDialog("close");
            }
        }, onClose: function () {
            validator.resetForm();
        }
    });

    var showDialog = function (title) {
        dialog.omDialog("option", "title", title);
        dialog.omDialog("open");
    };

    var collectSubmitData = function () {
        return {
            panelName: $("#panelName").val(),
            panelTitle: $("#panelTitle").val(),
            panelStyle: $("#panelStyle").val(),
            isCustom: $("#isCustom").val(),
            templateId: $("#templateId").val(),
            panelIcon: $("#panelIcon").val(),
            linkUrl: $("#linkUrl").val(),
            imgUrl: $("#imgUrl").val(),
            bigimg: $("#bigimg").val(),
            smallimg: $("#smallimg").val(),
            resolution: $("#resolution").val()
        }
    };

    var submitDialog = function () {
        var submitData;
//        check_data_style_validator();
        if (validator.form()) {
            //check if the panelName is unique
            var sr = $("#showResult").html();
            if (!$("#showResultbigimg").html() && !$("#showResultsmallimg").html() && !$("#showResultImageUrl").html()
                && !$('#showResultLinkUrl').html() && !$('#showResultpanelIcon').html() && (sr == '' || sr == '可用!')) {

            } else {
                return false;
            }

            submitData = collectSubmitData();
            submitData = replaceToNullValue(submitData);
            if (isAdd) {
                //自定义
                $.ajaxFileUpload({
                    url: 'panel_add.json',
                    secureuri: false,
                    fileElementId: ['fileField', 'fileField1', 'fileField2', 'fileField3'],
                    data: submitData,
                    dataType: 'JSON',
                    success: function (result) {
                        $('#grid').omGrid('reload');
                        if ("success" == result) {
                            YST_APP.show_message("新增面板成功！", 'success');
                        } else {
//                             YST_APP.show_message("新增面板失败！", 'error');
                        }
                        $("#dialog-form").omDialog("close");
                    }
                });

            } else {
                submitData.id = $("#id").val();
                $.ajaxFileUpload({
                    url: 'panel_update.json',
                    secureuri: false,
                    fileElementId: ['fileField', 'fileField1', 'fileField2', 'fileField3'],
                    data: submitData,
                    dataType: 'JSON',
                    success: function (result) {
                        $('#grid').omGrid('reload');
                        if ("success" == result) {
                            YST_APP.show_message("修改面板成功！", 'success');
                        } else {
                            YST_APP.show_message("修改面板失败！", 'error');
                        }
                        $("#dialog-form").omDialog("close");
                    }
                });

            }
        }
    };

    $('#delete').bind('click', function () {
        var selections = $('#grid').omGrid('getSelections', true);
        if (selections.length == 0) {
            YST_APP.alertMsg("删除操作至少选择一行记录！");
            return false;
        }
        if (selections[0].onlineStatus == 99) {
            YST_APP.alertMsg("不可以删除已上线的面板！");
            return false;
        }
        if (selections[0].epgPanelId) {
            YST_APP.alertMsg("不可以编辑外部系统数据！");
            return false;
        }
        $.omMessageBox.confirm({
            title: '确认删除',
            content: '如果该面板已绑定面板包，删除后将自动解绑，您确定执行该操作吗？',
            onClose: function (v) {
                if (v == true) {
                    var toDeleteRecordID = YST_APP.convertIds2Str(selections);
                    $.post('panel_delete.json', {ids: toDeleteRecordID.toString()}, function (result) {
                        $('#grid').omGrid('reload');
                        if (result == 'success') {
                            YST_APP.show_message("删除面板信息成功！", 'success');
                        } else {
                            YST_APP.show_message("删除面板信息失败！", 'error');
                        }
                    });
                }
            }
        });
    });

    $('#panelOnline').bind('click', function () {
        var selections = $('#grid').omGrid('getSelections', true);
        if (selections.length == 0 || selections.length > 1) {
            YST_APP.alertMsg("上线操作至少且只能选择一行记录！");
            return false;
        }
        for (var i = 0; i < selections.length; i++) {
            if (selections[i].epgPanelId) {
                YST_APP.alertMsg("不可以编辑外部系统数据！");
                return;
            }
        }
        $.omMessageBox.confirm({
            title: '确认上线',
            content: '您确定执行上线操作吗？',
            onClose: function (v) {
                if (v == true) {
//                    var toDeleteRecordID = YST_APP.convertIds2Str(selections);
                    $.post('panel_online.json', {ids: selections[0].id}, function (result) {
                        $('#grid').omGrid('reload');
                        if (result == 'success') {
                            YST_APP.show_message("面板上线成功！", 'success');
                        } else {
                            YST_APP.show_message("面板上线失败,面板配置错误！", 'error');
                        }
                    });
                }
            }
        });
    });

    $('#panelDownline').bind('click', function () {
        var selections = $('#grid').omGrid('getSelections', true);
        if (selections.length == 0) {
            YST_APP.alertMsg("下线操作至少要选择一行记录！");
            return false;
        }
        for (var i = 0; i < selections.length; i++) {
            if (selections[i].epgPanelId) {
                YST_APP.alertMsg("不可以编辑外部系统数据！");
                return;
            }
        }
        $.omMessageBox.confirm({
            title: '确认下线',
            content: '您确定执行下线操作吗？',
            onClose: function (v) {
                if (v == true) {
                    var toDeleteRecordID = YST_APP.convertIds2Str(selections);
                    $.post('panel_downline.json', {ids: toDeleteRecordID.toString()}, function (result) {
                        $('#grid').omGrid('reload');
                        if (result == 'success') {
                            YST_APP.show_message("面板下线成功！", 'success');
                        } else {
                            YST_APP.show_message("面板下线失败！", 'error');
                        }
                    });
                }
            }
        });
    });

    //bind package
    var validator_bind = $('#bindPackageForm').validate({
        rules: {
            packageId: {required: true},
            downNavId: {required: true}
        },
        messages: {
            packageId: {required: "面板包不能为空！"},
            downNavId: {required: "下部导航不能为空！"}
        },
        errorPlacement: YST_APP.Error.errorPlacement,
        showErrors: YST_APP.Error.showErrors
    });

    var submitDialog_bind = function () {
        if (validator_bind.form()) {
            var submitData = $('#bindPackageForm').serialize();
            $.post('bindPanelPackage.json', submitData, function (result) {
                $('#grid').omGrid('reload');
                if ("success" == result) {
                    YST_APP.show_message("绑定面板包成功！", 'success');
                } else if ("failed" == result) {
                    YST_APP.show_message("绑定面板包失败！", 'error');
                } else {
                    YST_APP.show_message("该面板已经绑定该面板包，不可重复绑定！", 'error');
                }
                $("#bind-package-form").omDialog("close");
            });
        }
    };

    var dialog_bind = $("#bind-package-form").omDialog({
        width: 550,
        autoOpen: false,
        modal: true,
        resizable: false,
        draggable: false,
        buttons: {
            "提交": function () {
                submitDialog_bind();
            },
            "取消": function () {
                $("#bind-package-form").omDialog("close");
            }
        }, onClose: function () {
            validator_bind.resetForm();
        }
    });

    var showDialog_bind = function (title) {
        dialog_bind.omDialog("option", "title", title);
        dialog_bind.omDialog("open");
    };

    var bind_check = function (selections) {
        var result = false;
        $.each(selections, function (i) {
            if (selections[i] && (selections[i].onlineStatus == -99 || selections[i].onlineStatus == 0)) {
                result = true;
                return false;
            }
        });
        return result;
    };

    $("#bindPackage").click(function () {
        var selections = $('#grid').omGrid('getSelections', true);
        if (selections.length == 0 || selections.length > 1) {
            YST_APP.alertMsg("绑定操作至少且只能选择一行记录！");
            return;
        }
        if (bind_check(selections)) {
            YST_APP.alertMsg("不能绑定初始面板或已下线的面板！");
            return;
        }
        $("#panelId").val(selections[0].id);
        $('#packageId').omCombo({dataSource: 'getAllCustomedTargetList.json', editable: false, forceSelection: true, width: 180, listMaxHeight: BIMS_PANEL.config.select_input_height, value: ""});
        $('#upNavIds').omCombo({dataSource: 'heard_nav_list.json?panelId=' + selections[0].id, editable: false, multi: true, width: 180, listMaxHeight: BIMS_PANEL.config.select_input_height, value: ""});
        $('#downNavId').omCombo({dataSource: 'root_nav_list.json?panelId=' + selections[0].id, editable: true, autoFilter: true, filterStrategy: 'anywhere', width: 180, listMaxHeight: BIMS_PANEL.config.select_input_height, value: ""});
        $('#sortNum').omCombo({dataSource: sortOptions, value: '1', editable: false, width: 180, listMaxHeight: BIMS_PANEL.config.select_input_height});
        $('#isLock').omCombo({dataSource: enums.isLock_display, value: 'false', editable: false, width: 180, listMaxHeight: BIMS_PANEL.config.select_input_height});
        $('#display').omCombo({dataSource: enums.isLock_display, value: 'true', editable: false, width: 180, listMaxHeight: BIMS_PANEL.config.select_input_height});
        $("#isLockTr").show();
        $('#displayTr').show();
        showDialog_bind('绑定面板包信息');

    });

    /*################################################################ preview panel ###################################*/
    $("#previewPanel").click(function () {
        var selections = $('#grid').omGrid('getSelections', true);
        if (selections.length == 0 || selections.length > 1) {
            YST_APP.alertMsg("预览操作至少且只能选择一行记录");
            return;
        }
        var preview_panel_dialog = null;
        if (selections[0].epgPanelId || selections[0].districtCode != districtCode) {
            preview_panel_dialog = BIMS_PANEL.preview_panel_dialog(true);
        } else {
            preview_panel_dialog = BIMS_PANEL.preview_panel_dialog(false);
        }
        $("#panel_layout").empty();
        BIMS_PANEL.destroy_preview_box();
        $("#panelId").val(selections[0].id);
        if (selections[0].resolution == "720p") {
            BIMS_PANEL.config.resolution = [
                {value: "720p", text: "720p"}
            ];
        } else {
            BIMS_PANEL.config.resolution = [
                {value: "1080p", text: "1080p"}
            ];
        }
        $.ajax({
            url: "getPreviewItemDataByPanelId.json?panelId=" + selections[0].id,
            dataType: "json",
            type: 'GET',
            success: function (data) {
                BIMS_PANEL.set_preview_item_datas(data);
            }
        });
        preview_panel_dialog.omDialog("option", "title", "面板配置");
        preview_panel_dialog.omDialog("open");
    });
    /*################################################################ preview panel ###################################*/

    $("#synsPanel").click(function () {
        $("#synsPanel_0").prop("disabled", true);
        $.ajax({
            type: "POST",
            url: "verify_syncEpgPanelDatas.json",
            timeout: 2000000,
            success: function (result) {
                $("#synsPanel_0").prop("disabled", false);
                if ('success' == result) {
                    $('#grid').omGrid('reload');
                    YST_APP.show_message("同步播控数据成功！", 'success');
                } else {
                    YST_APP.show_message("同步播控数据失败！", 'error');
                }
            }
        });
    });

    $("#syncCenterPanel").click(function () {
        $("#syncCenterPanel_0").prop("disabled", true);
        $.ajax({
            type: "POST",
            url: "verify_syncCenterPanelDatas.json",
            timeout: 2000000,
            success: function (result) {
                $("#syncCenterPanel_0").prop("disabled", false);
                if ('success' == result) {
                    $('#grid').omGrid('reload');
                    YST_APP.show_message("同步中心数据成功！", 'success');
                } else {
                    YST_APP.show_message("同步中心数据失败！", 'error');
                }
            }
        });
    });

    $("#pushPanel").click(function () {
        $("#pushPanel_0").prop("disabled", true);
        $.ajax({
            type: "POST",
            url: "verify_push_panel_to_center.json",
            timeout: 2000000,
            success: function (result) {
                $("#pushPanel_0").prop("disabled", false);
                if ('success' == result) {
                    $('#grid').omGrid('reload');
                    YST_APP.show_message("同步省级数据成功！", 'success');
                } else {
                    YST_APP.show_message("同步省级数据失败！", 'error');
                }
            }
        });
    });


    /*############################################################# panel distribute #####################################*/
    $('#panelDistribute').click(function () {
        $('#areaId').omCombo({dataSource: 'area.json?par=0', editable: false, width: 180, listMaxHeight: 160, value: '0'});
        showDialog_distribute('面板数据下发');
    });

    var showDialog_distribute = function (title) {
        dialog_distribute.omDialog("option", "title", title);
        dialog_distribute.omDialog("open");
    }

    var dialog_distribute = $("#panel-distribute-form").omDialog({
        width: 320,
        autoOpen: false,
        modal: true,
        resizable: false,
        draggable: false,
        buttons: {
            "提交": function () {
                submitDialog_distribute();
            },
            "取消": function () {
                $("#panel-distribute-form").omDialog("close");
            }
        }, onClose: function () {
            validator_distribute.resetForm();
        }
    });

    var submitDialog_distribute = function () {
        if (validator_distribute.form()) {
            var submitData = {
                areaId: $("#areaId").val()
            };
            $.post('verify_distribute_panel.json', submitData, function (result) {
                if ("success" == result) {
                    YST_APP.show_message("面版数据下发成功!", 'success');
                } else {
                    YST_APP.show_message("面版数据下发失败!", 'error');
                }
                $("#panel-distribute-form").omDialog("close");
            });
        }
    };


    //distribute panel
    var validator_distribute = $('#panelDistributeForm').validate({
        rules: {
            areaId: {required: true}
        },
        messages: {
            areaId: {required: "请选择区域！"}
        },
        errorPlacement: YST_APP.Error.errorPlacement,
        showErrors: YST_APP.Error.showErrors
    });
});


function checkNameExists() {
    if ($.trim($("#panelName").val()) != "") {
        var par = new Object();
        par['panelName'] = $("#panelName").val();
        par['id'] = $("#id").val();
        $.ajax({
            type: "post",
            url: "panel_name_exists.shtml?s=" + Math.random(),
            dataType: "html",
            data: par,
            success: function (msg) {
                $("#showResult").html(msg);
            }
        });
    } else if ($.trim($("#panelName").val()) == "" && $("#showResult").html() != "") {
        $("#showResult").html("");
    }
}

function checkPanelItemNameExists() {
    if ($.trim($("#name").val()) != "") {
        var par = new Object();
        par['name'] = $("#name").val();
        par['id'] = $("#id").val();
        $.ajax({
            type: "post",
            url: "panel_item_name_exists.shtml?s=" + Math.random(),
            dataType: "html",
            data: par,
            success: function (msg) {
                //alert(msg);
                $("#showResult1").html(msg);
            }
        });
    } else if ($.trim($("#name").val()) == "" && $("#showResult1").html() != "") {
        $("#showResult1").html("");
    }
}

//检查params 参数类型 是否符合：key:value;key:value;
function checkParamsValid(paramsId, showResultId) {
    var params = $(paramsId).val();
    var regexp = new RegExp("^(\\w+\\:\\w+\\;\\n?)*$");
    if (!regexp.test(params)) {
        $("#paramsTitle").html('');
        $(showResultId).html('动作参数格式不正确，请确认![key:value;key:value;key:value;]');
    } else {
        $(showResultId).html('');
    }
}