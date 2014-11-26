/**
 * Created by frank on 14-11-18.
 */
$(document).ready(function () {
    $('#save').omButtonbar({btns: [
        {label: "新增", icons: {left: opPath + 'add.png'}}
    ]});
    $('#update').omButtonbar({btns: [
        {label: "修改", icons: {left: opPath + 'op-edit.png'}}
    ]});

    /*$('#delete').omButtonbar({btns: [
     {label: "删除", icons: {left: opPath + 'remove.png'}}
     ]});*/
    $('#query').omButtonbar({btns: [
        {label: "查询", icons: {left: opPath + 'search.png'}}
    ]});
    $('#bind').omButtonbar({btns: [
        {label: "绑定设备和分组", icons: {left: opPath + 'op-edit.png'}}
    ]});

    $('#unbind').omButtonbar({btns: [
        {label: "解绑设备和分组", icons: {left: opPath + 'remove.png'}}
    ]});

    $('#bindUser').omButtonbar({btns: [
        {label: "绑定用户和分组", icons: {left: opPath + 'op-edit.png'}}
    ]});

    $('#unbindUser').omButtonbar({btns: [
        {label: "解绑用户和分组", icons: {left: opPath + 'remove.png'}}
    ]});

    $('#package_preview').omButtonbar({btns: [
        {label: "面板包预览", icons: {left: opPath + 'op-edit.png'}}
    ]});
    $('#config_panel_sort').omButtonbar({btns: [
        {label: "面板包配置", icons: {left: opPath + 'op-edit.png'}}
    ]});

    $('#createZips').omButtonbar({btns: [
        {label: "上线生成ZIP包", icons: {left: opPath + 'op-edit.png'}}
    ]});

    $('#device_list_of_group').omButtonbar({btns: [
        {label: "关联设备", icons: {left: opPath + 'op-btn-icon.png'}}
    ]});
    $('#customer_list_of_group').omButtonbar({btns: [
        {label: "关联用户", icons: {left: opPath + 'op-btn-icon.png'}}
    ]});
    $('#deviceQuery').omButtonbar({btns: [
        {label: "查询", icons: {left: opPath + 'search.png'}}
    ]});


    $('#queryUser').omButtonbar({btns: [
        {label: "查询", icons: {left: opPath + 'search.png'}}
    ]});
    $('#add720p').omButtonbar({btns: [
        {label: "添加", icons: {left: opPath + 'add.png'}}
    ]});
    $('#del720p').omButtonbar({btns: [
        {label: "移除", icons: {left: opPath + 'remove.png'}}
    ]});
    $('#saveChanges720p').omButtonbar({btns: [
        {label: "保存修改", icons: {left: opPath + 'add.png'}}
    ]});

    $('#add1080p').omButtonbar({btns: [
        {label: "添加", icons: {left: opPath + 'add.png'}}
    ]});
    $('#del1080p').omButtonbar({btns: [
        {label: "移除", icons: {left: opPath + 'remove.png'}}
    ]});
    $('#saveChanges1080p').omButtonbar({btns: [
        {label: "保存修改", icons: {left: opPath + 'add.png'}}
    ]});

    $('#delDevice').omButtonbar({btns: [
        {label: "移除", icons: {left: opPath + 'remove.png'}}
    ]});
    $('#delUser').omButtonbar({btns: [
        {label: "移除", icons: {left: opPath + 'remove.png'}}
    ]});

    $.getJSON("get_online_panel_list.json?dpi=720p", function (data) {
        BIMS_PANEL.enums.panel_name = data;
    });

    $.getJSON("get_online_panel_list.json?dpi=1080p", function (data) {
        BIMS_PANEL.enums.panel_name_1080p = data;
    });

    $.getJSON("root_nav_list_720p.json", function (data) {
        BIMS_PANEL.enums.root_nav_list = data;
    });

    $.getJSON("root_nav_list_1080p.json", function (data) {
        BIMS_PANEL.enums.root_nav_list_1080p = data;
    });

    $.getJSON("head_nav_list_720p.json", function (data) {
        BIMS_PANEL.enums.head_nav_list = data;
    });

    $.getJSON("head_nav_list_1080p.json", function (data) {
        BIMS_PANEL.enums.head_nav_list_1080p = data;
    });

    function show_isLock_display(value) {
        if (value == "true") {
            return "是";
        } else {
            return "否";
        }
    };

    var isLock_display = {
        dataSource: [
            {text: "是", value: "true"},
            {text: "否", value: "false"}
        ],
        editable: false,
        listMaxHeight: 150,
        value: 'false'
    };

    var dialogInfo = $("#dialog-form3").omDialog({
        width: 700,
        height: 320,
        autoOpen: false,
        modal: true, resizable: false,
        draggable: false,
        buttons: {
            "关闭": function () {
                $("#dialog-form3").omDialog("close");
                return false;
            }
        }
    });
    var showInfoDialog = function (title) {
        dialogInfo.omDialog("option", "title", title);
        dialogInfo.omDialog("open");
    };

    $('#createZips').bind('click', function () {
        var selections = $('#grid').omGrid('getSelections', true);
        if (selections.length == 0) {
            YST_APP.alertMsg("生成ZIP包至少选择一行记录！");
            return false;
        }
        $.omMessageBox.confirm({
            title: '创建ZIP包',
            content: '您确定执行该操作吗？',
            onClose: function (v) {
                if (v == true) {
                    var packageIds = YST_APP.convertIds2Str(selections);
                    $.post('createZips.json', {ids: packageIds.toString()}, function (result) {
                        $('#grid').omGrid('reload');
                        if (result == 'success') {
                            YST_APP.show_message("创建ZIP包成功！", 'success');
                        } else {
                            YST_APP.show_message("创建ZIP包失败！", 'error');
                        }
                    });
                }
            }
        });
    });

    $('#unbindUser').bind('click', function (e) {
        var selections = $('#grid').omGrid('getSelections', true);
        if (selections.length == 0) {
            YST_APP.alert("解绑操作至少选择一行记录！");
            return false;
        }
        $.omMessageBox.confirm({
            title: '确认解绑',
            content: '解绑后面板包的绑定关系将删除且无法恢复，你确定要执行该操作吗？',
            onClose: function (v) {
                if (v == true) {
                    var toDeleteRecordID = "";
                    for (var i = 0; i < selections.length; i++) {
                        if (i != selections.length - 1) {
                            toDeleteRecordID += selections[i].id + ",";
                        } else {
                            toDeleteRecordID += selections[i].id;
                        }
                    }
                    $.post('panel_package_map_delete.json', {ids: toDeleteRecordID.toString(), isUser: 'true'}, function (result) {
                        $('#grid').omGrid('reload');
                        if (result == 'success') {
                            YST_APP.show_message("解绑面板包关系成功！", "success");
                        } else {
                            YST_APP.show_message("解绑面板包关系失败！", "error");
                        }
                    });
                }
            }
        });
    });

    $('#unbind').bind('click', function (e) {
        var selections = $('#grid').omGrid('getSelections', true);
        if (selections.length == 0) {
            YST_APP.alert("解绑操作至少选择一行记录！");
            return false;
        }
        $.omMessageBox.confirm({
            title: '确认解绑',
            content: '解绑后面板包的绑定关系将删除且无法恢复，你确定要执行该操作吗？',
            onClose: function (v) {
                if (v == true) {
                    var toDeleteRecordID = "";
                    for (var i = 0; i < selections.length; i++) {
                        if (i != selections.length - 1) {
                            toDeleteRecordID += selections[i].id + ",";
                        } else {
                            toDeleteRecordID += selections[i].id;
                        }
                    }
                    $.post('panel_package_map_delete.json', {ids: toDeleteRecordID.toString()}, function (result) {
                        $('#grid').omGrid('reload');
                        if (result == 'success') {
                            YST_APP.show_message("解绑面板包关系成功！", "success");
                        } else {
                            YST_APP.show_message("解绑面板包关系失败！", "error");
                        }
                    });
                }
            }
        });
    });
    var isAdd = true;
    $('#grid').omGrid({
        dataSource: 'panel_package_list.json',
        width: '100%',
        height: YST_APP.PanelFrameHeight,
        singleSelect: true,
        limit: limit,
        colModel: [
            {header: '<b>ID</b>', name: 'id', align: 'center', width: 50},
            {header: '<b>面板包名称</b>', name: 'packageName', align: 'center', width: 130},
            {header: '<b>是否为默认</b>', name: 'isDefault', align: 'center', width: 60, renderer: YST_APP.YES_NO_FUNC},
            {header: '<b>面板包版本</b>', name: 'version', align: 'center', width: 90},
            {header: '<b>设备分组</b>', name: 'deviceGroupIds', align: 'center', width: 90},
            {header: '<b>用户分组</b>', name: 'userGroupIds', align: 'center', width: 90},
            {header: '<b>720p zip包URL</b>', name: 'zipUrl', align: 'center', width: 180},
            {header: '<b>1080p zip包URL</b>', name: 'zipUrl1080p', align: 'center', width: 180},
            {header: '<b>创建时间</b>', name: 'createTime', align: 'center', width: 'autoExpand', renderer: YST_APP.showDate}
        ],
        rowDetailsProvider: function (rowData, rowIndex) {
            return '第' + (rowIndex + 1) + '行'
                + '</br>ID=' + rowData.id
                + '</br>面板包名称=' + rowData.packageName
                + '</br>是否为默认=' + YST_APP.YES_NO_FUNC(rowData.isDefault)
                + '</br>面板包版本=' + rowData.version
                + '</br>设备分组=' + (rowData.deviceGroupIds ? rowData.deviceGroupIds : "")
                + '</br>用户分组=' + (rowData.userIds ? rowData.userIds : "")
                + '</br>720p zip包URL=' + rowData.zipUrl
                + '</br>1080p zip包URL=' + rowData.zipUrl1080p
                + '</br>创建时间=' + YST_APP.showDate(rowData.createTime)
        }
    });

    $('#query').bind('click', function () {
        var name = $.trim($('#queryPanelName').val());
        var Id = $.trim($('#queryId').val());
        if (Id != '' && !YST_APP.isInteger(Id)) {
            YST_APP.alertMsg("ID必须为正整数类型");
            return false;
        }
        $('#grid').omGrid("setData", 'panel_package_list.json?name=' + name + "&Id=" + Id);
    });

    $('#save').bind('click', function () {
        isAdd = true;
        $("#showResultDefaultBackground1080p").html("");
        $("#showResultDefaultBackground720p").html("");
        $("#id").val('');
        $(".form-add-input").each(function () {
            $(this).val("");
        });
        $(".form-border-style").each(function () {
            $(this).val("");
        });
        $("#maxPageNumber").omCombo({dataSource: YST_APP.MAX_PAGE, value: '0', editable: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height});
        $('#isDefault').omCombo({dataSource: YST_APP.YES_NO, value: '0', editable: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height});
        $('#commonTopNav').omCombo({dataSource: 'heard_nav_list.json?', editable: false, width: BIMS_PANEL.config.select_input_length,listMaxHeight: BIMS_PANEL.config.select_input_height,multi: true, value: ''});
        showDialog('新增面板包信息');
    });

    $('#update').bind('click', function () {
        isAdd = false;
        $("#showResultDefaultBackground1080p").html("");
        $("#showResultDefaultBackground720p").html("");
        var selections = $('#grid').omGrid('getSelections', true);
        if (selections.length == 0 || selections.length > 1) {
            YST_APP.alertMsg("修改操作至少且只能选择一行记录！");
            return false;
        }
        if (selections[0].epgPackageId) {
            YST_APP.alertMsg("不可以编辑外部系统数据！");
            return;
        }
        $.ajax({
            url: "get_panel_package.json?id=" + selections[0].id,
            dataType: "json",
            success: function (data) {
                $("#id").val(data['id']);
                $("#packageName").val(data['packageName']);
                $("#packageDesc").val(data['packageDesc']);
                $("#packageDesc").val(data['packageDesc']);
                $("#defaultBackground1080p").val(data['defaultBackground1080p']);
                $("#defaultBackground720p").val(data['defaultBackground720p']);
                $('#isDefault').omCombo({dataSource: YST_APP.YES_NO, value: data['isDefault'], editable: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height});
                $("#maxPageNumber").omCombo({dataSource: YST_APP.MAX_PAGE, value: data['maxPageNumber'], editable: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height});
                $('#commonTopNav').omCombo({dataSource: 'heard_nav_list.json?', editable: false, width: BIMS_PANEL.config.select_input_length,listMaxHeight: BIMS_PANEL.config.select_input_height, multi: true, value: data['commonTopNav']});
            }
        });

        showDialog('修改面板包信息', selections[0]);
    });

    $('#bindUser').click(function () {
        var selections = $('#grid').omGrid('getSelections', true);
        $('#userGroupIds3').omCombo('setData', []);
        if (selections.length == 0 || selections.length > 1) {
            YST_APP.alertMsg("绑定操作至少且只能选择一行记录！");
            return false;
        }
        if (1 == selections[0].isDefault) {
            $.omMessageBox.alert({
                type: 'alert',
                title: '温馨提示',
                content: '默认的面板包不能做绑定操作！'
            });
            return false;
        }

        $.get("get_areaIds_by_bussId.json", {id: selections[0].id, tableName: "bss_panel_package_user_map", character: "panel_package_id"}, function (result) {
            $('#areaId').omCombo({dataSource: 'area.json', editable: false, width: 184, listMaxHeight: 160, value: result || '', multi: true,
                onValueChange: function (target, newValue, oldValue) {
                    $.get("get_panel_user.json", {id: selections[0].id, type: "GROUP"}, function (result) {
                        $('#userGroupIds3').omCombo({dataSource: 'user_group_list_by_type_area.json?type=PANEL&areaId=' + newValue + '&id=' + selections[0].id + '&tableName=bss_panel_package_user_map&character=panel_package_id',
                            editable: false, width: 184, multi: true, value: result || ''});
                    });
                }
            });
        });

        $.get("get_panel_user.json", {id: selections[0].id, type: "GROUP"}, function (result) {
            $('#userGroupIds3').omCombo({dataSource: 'user_group_list_by_type.json?type=PANEL', editable: false, width: 184, multi: true, value: result || '' });
        });

        var dialog = $("#dialog-user-group").omDialog({
            width: 580,
            height: 200,
            autoOpen: false,
            modal: true,
            resizable: false,
            draggable: false,
            buttons: {
                "提交": function (result) {
                    if (!$('#areaId').omCombo('value')) {
                        $.omMessageBox.alert({
                            type: 'alert',
                            title: '温馨提示',
                            content: '请选择用户所在区域！'
                        });
                        return false;
                    }
                    var submitData = {
                        areaIds: $('#areaId').omCombo('value'),
                        id: selections[0].id,
                        userGroupIds: $("#userGroupIds3").val(),
                    };

                    if (!YST_APP.checkFileText($("#textfield1").val())) {
                        return false;
                    }
                    $.ajaxFileUpload({
                        url: 'bind_user_map.json',
                        secureuri: false,
                        fileElementId: 'userIds3',
                        data: submitData,
                        dataType: 'JSON',
                        success: function (result) {
                            $('#grid').omGrid('reload');
                            $('#message').html(result);
                            showInfoDialog('绑定结果');
                            $("#dialog-user-group").omDialog("close");
                        }
                    });
                    $("#dialog-user-group").omDialog("close");

                    return false;
                },
                "取消": function () {
                    $("#dialog-user-group").omDialog("close");
                }
            }, onClose: function () {
                $("#user-group-form").validate().resetForm();
            }
        });
        dialog.omDialog("option", "title", "绑定分组信息");
        dialog.omDialog("open");

    });

    $('#bind').click(function () {
        var selections = $('#grid').omGrid('getSelections', true);
        $('#deviceGroupIds3').omCombo('setData', []);
        if (selections.length == 0 || selections.length > 1) {
            YST_APP.alertMsg("绑定操作至少且只能选择一行记录！");
            return false;
        }
        if (1 == selections[0].isDefault) {
            $.omMessageBox.alert({
                type: 'alert',
                title: '温馨提示',
                content: '默认的面板包不能做绑定操作！'
            });
            return false;
        }

        $.get("get_districtCodes_by_Id.json?", {id: selections[0].id, tableName: "bss_panel_package_device_map", character: "panel_package_id"}, function (result) {
            $('#districtCode').omCombo({dataSource: 'area.json', editable: false, width: 184, listMaxHeight: 160, value: result || "", multi: true,
                onValueChange: function (target, newValue, oldValue) {
                    $.get("get_panel_device.json", {id: selections[0].id, type: "GROUP"}, function (result) {
                        $('#deviceGroupIds3').omCombo({dataSource: 'device_group_list_by_district.json?type=PANEL&districtCode=' + newValue + '&id=' + selections[0].id + '&tableName=bss_panel_package_device_map&character=panel_package_id', editable: false, width: 184, multi: true, value: result || '' });
                    });
                }
            });
        });
        $.get("get_panel_device.json", {id: selections[0].id, type: "GROUP"}, function (result) {
            $('#deviceGroupIds3').omCombo({dataSource: 'device_group_list_by_type.json?type=PANEL', editable: false, width: 184, multi: true, value: result || '' });
        });

        var dialog = $("#dialog-form1").omDialog({
            width: 580,
            height: 200,
            autoOpen: false,
            modal: true,
            resizable: false,
            draggable: false,
            buttons: {
                "提交": function (result) {
                    if ($('#districtCode').omCombo('value') == "") {
                        $.omMessageBox.alert({
                            type: 'alert',
                            title: '温馨提示',
                            content: '请选择设备所在区域！'
                        });
                        return false;
                    }
                    var submitData = {
                        areaIds: $('#districtCode').omCombo('value'),
                        id: selections[0].id,
                        deviceGroupIds: $("#deviceGroupIds3").omCombo('value'),
//                        deviceCodes: $("#deviceCodes3").val(),
                        userGroupIds: $("#userGroupIds3").omCombo('value'),
                        userIds: $("#userIds3").val()
                    };

                    if (!YST_APP.checkFileText($("#textfield").val())) {
                        return false;
                    }

                    $.ajaxFileUpload({
                        url: 'bind_map.json',
                        secureuri: false,
                        fileElementId: 'deviceCodes3',
                        data: submitData,
                        dataType: 'JSON',
                        success: function (result) {
                            $('#grid').omGrid('reload');
                            $('#message').html(result);
                            showInfoDialog('绑定结果');

                            $("#dialog-form1").omDialog("close");
                        }
                    });
                    $("#dialog-form1").omDialog("close");

                    return false;
                },
                "取消": function () {
                    $("#dialog-form1").omDialog("close");
                }
            }, onClose: function () {
                $("#myform1").validate().resetForm();
            }
        });
        dialog.omDialog("option", "title", "绑定分组信息");
        dialog.omDialog("open");
    });

    var dialog = $("#dialog-form").omDialog({
        width: 600,
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

    var submitDialog = function () {
        //判断是否提交
        var submitData;
        if (validator.form()) {
            /* if(!$("#showResultDefaultBackground720p").html() && !$("#showResultdefaultBackground1080p").html()) {
             }else{
             return false;
             }  */
            if (isAdd) {
                submitData = {
                    packageName: $("#packageName").val(),
                    packageDesc: $("#packageDesc").val(),
                    isDefault: $("#isDefault").val(),
                    maxPageNumber: $("#maxPageNumber").val(),
                    defaultBackground1080p: $("#defaultBackground1080p").val(),
                    defaultBackground720p: $("#defaultBackground720p").val(),
                    commonTopNav: $("#commonTopNav").val()
                };
                submitData = replaceToNullValue(submitData);
                $.ajaxFileUpload({
                    url: 'panel_package_add.json',
                    secureuri: false,
                    fileElementId: ['backgroundImg720p', 'backgroundImg1080p'],
                    data: submitData,
                    dataType: 'JSON',
                    success: function (result) {
                        $('#grid').omGrid('reload');
                        if ("success" == result) {
                            $.omMessageTip.show({title: tip, content: "新增面板包成功！", type: "success", timeout: time});
                        } else if ("failed" == result) {
                            $.omMessageTip.show({title: tip, content: "新增面板包失败！", type: "error", timeout: time});
                        } else {
                            $.omMessageTip.show({title: tip, content: "新增失败，已经存在默认的面板包！", type: "error", timeout: time});
                        }
                        $("#dialog-form").omDialog("close");
                    }
                });

            } else {
                submitData = {
                    id: $("#id").val(),
                    packageName: $("#packageName").val(),
                    packageDesc: $("#packageDesc").val(),
                    isDefault: $("#isDefault").val(),
                    maxPageNumber: $("#maxPageNumber").val(),
                    defaultBackground1080p: $("#defaultBackground1080p").val(),
                    defaultBackground720p: $("#defaultBackground720p").val(),
                    commonTopNav: $("#commonTopNav").val()
                };
                $.ajaxFileUpload({
                    url: 'panel_package_update.json',
                    secureuri: false,
                    fileElementId: ['backgroundImg720p', 'backgroundImg1080p'],
                    data: submitData,
                    dataType: 'JSON',
                    success: function (result) {
                        $('#grid').omGrid('reload');
                        if ("success" == result) {
                            $.omMessageTip.show({title: tip, content: "修改面板包成功！", type: "success", timeout: time});
                        } else if ("failed" == result) {
                            $.omMessageTip.show({title: tip, content: "修改面板包失败！", type: "error", timeout: time});
                        } else {
                            $.omMessageTip.show({title: tip, content: "修改失败，已经存在默认的面板包！", type: "error", timeout: time});
                        }
                        $("#dialog-form").omDialog("close");
                    }
                });

            }
        }
    };

    var validator = $('#myform').validate({
        rules: {
            packageName: {required: true},
            maxPageNumber: {required: true},
            defaultBackground1080p: {required: true, maxlength: 255},
            defaultBackground720p: {required: true, maxlength: 255}
        },
        messages: {
            packageName: {required: "面板包名称不能为空！"},
            maxPageNumber: {required: "最大栏目页数不能为空！"},
            defaultBackground1080p: {required: "1080p默认背景不能为空！", maxlength: "1080p默认背景地址最长255位字符！"},
            defaultBackground720p: {required: "720p默认背景不能为空！", maxlength: "720p默认背景地址最长255位字符！"}
        },
        errorPlacement: YST_APP.Error.errorPlacement,
        showErrors: YST_APP.Error.showErrors
    });

    $('#center-tab').omTabs({height: "33", border: false});

    /*########################################preview package################################*/
    var package_dialog = BIMS_PANEL.preview_package_dialog();
    var panel_sort_dialog = BIMS_PANEL.panel_sort_dialog();
    var epg_panel_sort_dialog = BIMS_PANEL.epg_panel_sort_dialog();

    $("#package_preview").click(function () {
        var selections = $('#grid').omGrid('getSelections', true);
        if (selections.length == 0 || selections.length > 1) {
            YST_APP.alertMsg("预览操作至少且只能选择一行记录！");
            return;
        }
        $("#id").val(selections[0].id);
        BIMS_PANEL.config.package_dpi = "720p";
        $("#panel_content" + BIMS_PANEL.config.package_dpi).empty();
        $("#panel_head_nva_content" + BIMS_PANEL.config.package_dpi).empty();
        $("#panel_root_nav_content" + BIMS_PANEL.config.package_dpi).empty();
        BIMS_PANEL.config.panel_index = 0;
        BIMS_PANEL.destroy_preview_panels();
        $.ajax({
            type: 'GET',
            url: 'panel_package_preview.json?packageId=' + selections[0].id + "&dpi=" + BIMS_PANEL.config.package_dpi,
            contentType: 'application/json',
            success: function (data) {
                BIMS_PANEL.set_preview_panels(data);
            }
        });
        package_dialog.omDialog("option", "title", "面板包预览");
        package_dialog.omDialog("open");
    });

    $("#config_panel_sort").click(function () {
        $('#panel-sort-grid' + BIMS_PANEL.config.package_dpi).omGrid('refresh');
        var selections = $('#grid').omGrid('getSelections', true);
        if (selections.length == 0 || selections.length > 1) {
            YST_APP.alertMsg("面板包配置至少且只能选择一行记录！");
            return;
        }
        $("#id").val(selections[0].id);
        panel_sort_dialog.omDialog("option", "title", "面板包配置");
        panel_sort_dialog.omDialog("open");
        $('#panel-sort-grid' + BIMS_PANEL.config.package_dpi).omGrid({
            dataSource: "panel_list_packageId.json?packageId=" + selections[0].id + "&dpi=" + BIMS_PANEL.config.package_dpi,
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
                    options: {dataSource: BIMS_PANEL.enums.head_nav_list,multi: true}},
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
    });

    var addBindPanelFunc = function (event) {
        if (BIMS_PANEL.config.isDisabled) {
            YST_APP.alertMsg("请先执行完当前的编辑！");
            return;
        }
        $('#panel-sort-grid' + event.data.dpi).omGrid('insertRow', "end", {sort: "1", display: "true", isLock: "true"});
    };

    var del_panel_map = function (event) {
        if (BIMS_PANEL.config.isDisabled) {
            YST_APP.alertMsg("请先执行完当前的编辑！");
            return;
        }
        var selections_panels = $('#panel-sort-grid' + event.data.dpi).omGrid('getSelections', true);
        if (selections_panels.length == 0) {
            YST_APP.alertMsg("移除操作至少选择一行记录！");
            return;
        }
        $('#deleteType').omCombo({dataSource: BIMS_PANEL.config.delete_panel_map,
            value: '0',
            width: 180,
            autoFilter: false,
            listMaxHeight: 160
        });
        var dialog_export = $("#del-dialog-form").omDialog({
            width: 300,
            autoOpen: false,
            modal: true,
            resizable: false,
            draggable: false,
            buttons: {
                "提交": function () {
                    $.omMessageBox.confirm({
                        title: '确认移除',
                        content: '移除面板记录将无法恢复，你确定要执行该操作吗？',
                        onClose: function (v) {
                            if (v == true) {
                                var selections = $('#grid').omGrid('getSelections', true);
                                var toDeleteRecordID = YST_APP.convertIds2Str(selections_panels);
                                if ($('#deleteType').omCombo('value') == 0) {
                                    $.ajax({
                                        type: "POST",
                                        url: "delete_panel_by_panelPackageId.json?panelIds=" + encodeURIComponent(toDeleteRecordID.toString()) + "&panelPackageId=" + selections[0].id,
                                        success: function (result) {
                                            if (result) {
                                                $('#panel-sort-grid' + event.data.dpi).omGrid('reload');
                                                $('#panel-sort-grid' + event.data.dpi).omGrid('saveChanges');
                                                YST_APP.show_message("移除成功！", 'success');
                                            } else {
                                                YST_APP.show_message(result, 'error');
                                            }
                                        }
                                    });
                                }
                                else if ($('#deleteType').omCombo('value') == 1) {
                                    $.ajax({
                                        type: "POST",
                                        url: "delete_panel_by_panelPackageId.json?panelIds=" + encodeURIComponent(toDeleteRecordID.toString()) + "&panelPackageId=" + selections[0].id,
                                        success: function (result) {
                                            if (result) {
                                                $('#panel-sort-grid' + event.data.dpi).omGrid('reload');
                                                $('#panel-sort-grid' + event.data.dpi).omGrid('saveChanges');
                                                YST_APP.show_message("移除成功！", 'success');
                                            } else {
                                                YST_APP.show_message(result, 'error');
                                            }
                                        }
                                    });
                                }
                            }
                        }});
                    $("#del-dialog-form").omDialog("close");
                },
                "取消": function () {
                    $("#del-dialog-form").omDialog("close");
                }
            }, onClose: function () {
                $('#delDeviceMyform').validate().resetForm();
                $("#del-dialog-form").omDialog("close");
            }

        });
        dialog_export.omDialog("option", "title", "移除面板");
        dialog_export.omDialog("open");
    };

    var save_panel_map = function(event){
        if (BIMS_PANEL.config.isDisabled) {
            YST_APP.alertMsg("请先执行完当前的编辑！")
            return;
        }
        var selections = $('#grid').omGrid('getSelections', true);
        var formData = $('#panel-sort-grid' + event.data.dpi).omGrid('getData');
        var formDataStr = JSON.stringify(formData);
        $.ajax({
            type: "POST",
            url: "config_panel_package.json?formData=" + formDataStr + "&packageId=" + selections[0].id + "&dpi=" + event.data.dpi,
            success: function (result) {
                if ("success" == result) {
                    YST_APP.show_message("配置面板包成功！", 'success');
                    $('#panel-sort-grid' + + event.data.dpi).omGrid('reload');
                } else {
                    YST_APP.show_message(result, 'error');
                    $('#panel-sort-grid' + + event.data.dpi).omGrid('reload');
                }
            }
        });
    };

    $("#add720p").bind('click',{dpi:'720p'},addBindPanelFunc);
    $("#add1080p").bind('click',{dpi:'1080p'},addBindPanelFunc);
    $('#del720p').bind('click', {dpi: "720p"}, del_panel_map);
    $('#del1080p').bind('click', {dpi: "1080p"}, del_panel_map);
    $('#saveChanges720p').bind('click',{dpi: "720p"}, save_panel_map);
    $('#saveChanges1080p').bind('click',{dpi: "1080p"}, save_panel_map);
    $(".carousel-control-left").click(BIMS_PANEL.click_preview_package_left);
    $(".carousel-control-right").click(BIMS_PANEL.click_preview_package_right);

    /*########################################preview package################################*/

    /*********************************显示设备列表 start*************************************************************/
    $('#device_list_of_group').bind('click', function (e) {
        var selections = $('#grid').omGrid('getSelections', true);
        if (selections.length == 0 || selections.length > 1) {
            YST_APP.alertMsg("显示操作至少且只能选择一行记录！");
            return false;
        }
        showProgramDialog("关联的设备列表");
        $('#gridDeviceList').omGrid({
            dataSource: 'device_list_by_groupId.json?deviceGroupId=' + selections[0].id + '&tableName=bss_panel_package_device_map&character=panel_package_id',
            width: '100%',
            height: rFrameHeight - 100,
            limit: limit,
            singleSelect: false,
            colModel: [
                {header: '<b>设备编号</b>', name: 'ystenId', align: 'center', width: 155},
                {header: '<b>设备序列号</b>', name: 'sno', align: 'center', width: 120},
                {header: '<b>MAC地址</b>', name: 'mac', align: 'center', width: 120},
                {header: '<b>设备状态</b>', name: 'state', align: 'center', width: 50},
                {header: '<b>绑定状态</b>', name: 'bindType', align: 'center', width: 50},
                {header: '<b>是否锁定</b>', name: 'isLock', align: 'center', width: 50},
                {header: '<b>区域</b>', name: 'area', align: 'center', width: 53, renderer: function (value) {
                    if (value == null || value == "") {
                        return "";
                    } else {
                        return value.name;
                    }
                }},
                {header: '<b>地市</b>', name: 'city', align: 'center', width: 50, renderer: function (value) {
                    if (value == null || value == "") {
                        return "";
                    } else {
                        return value.name;
                    }
                }},
                {header: '<b>软件号</b>', name: 'softCode', align: 'center', width: 90},
                {header: '<b>软件版本号</b>', name: 'versionSeq', align: 'center', width: 70}
            ]
        });

    });


    $('#delDevice').click(function () {
        $('#deleteType').omCombo({dataSource: [
            {text: '移除选中设备', value: '0'},
            {text: '移除全部设备', value: '1'}
        ],
            value: '0',
            width: 180,
            listMaxHeight: 160,
            autoFilter: false
        });
        var dialog_del_device = $("#del-dialog-form").omDialog({
            width: 300,
            autoOpen: false,
            modal: true,
            resizable: false,
            draggable: false,
            buttons: {
                "提交": function () {
                    var selections_device = $('#gridDeviceList').omGrid('getSelections', true);
                    if ($('#deleteType').omCombo('value') == 0) {
                        if (selections_device.length == 0) {
                            YST_APP.alertMsg("移除选中至少选择一行记录！");
                            return false;
                        }
                    }
                    $.omMessageBox.confirm({
                        title: '确认移除',
                        //content:'删除设备分组信息后将同时删除设备分组绑定关系且无法恢复，你确定要执行该操作吗？',
                        content: '移除设备记录将无法恢复，你确定要执行该操作吗？',
                        onClose: function (v) {
                            if (v == true) {
                                var selections = $('#grid').omGrid('getSelections', true);
                                var toDeleteRecordID = YST_APP.convertIds2Str(selections_device);
                                if ($('#deleteType').omCombo('value') == 0) {
                                    $.ajax({
                                        type: "POST",
                                        url: "delete_device_by_bussiness.json?delYstenIds=" + toDeleteRecordID + "&packageId=" + selections[0].id + '&tableName=bss_panel_package_device_map&character=panel_package_id&type=DEVICE&device=ysten_id',
                                        success: function (result) {
                                            if (result) {
                                                $('#gridDeviceList').omGrid('reload');
                                                $('#gridDeviceList').omGrid('saveChanges');
                                                YST_APP.show_message("移除成功！", 'success');
                                            } else {
                                                YST_APP.show_message(result, 'error');
                                            }
                                        }
                                    });
                                }
                                else if ($('#deleteType').omCombo('value') == 1) {
                                    $.ajax({
                                        type: "POST",
                                        url: "delete_device_by_bussiness.json?delYstenIds=" + toDeleteRecordID + "&packageId=" + selections[0].id + '&tableName=bss_panel_package_device_map&character=panel_package_id&type=DEVICE&device=ysten_id',
                                        success: function (result) {
                                            if (result) {
                                                $('#gridDeviceList').omGrid('reload');
                                                $('#gridDeviceList').omGrid('saveChanges');
                                                YST_APP.show_message("移除成功！", 'success');

                                            } else {
                                                YST_APP.show_message(result, 'error');
                                            }
                                        }
                                    });
                                }
                            }
                        }});
                    $("#del-dialog-form").omDialog("close");
                },
                "取消": function () {
                    $("#del-dialog-form").omDialog("close");
                }
            }, onClose: function () {
                $('#delDeviceMyform').validate().resetForm();
                $("#del-dialog-form").omDialog("close");
            }

        });
        dialog_del_device.omDialog("option", "title", "设备移除");
        dialog_del_device.omDialog("open");
    });

    $('#deviceQuery').bind('click', function (e) {
        var selections = $('#grid').omGrid('getSelections', true);
        var deviceCode = $('#deviceCode').val();
        var ystenId = $('#ystenIds').val();
        var mac = $('#macs').val();
        var sno = $('#snos').val();
        $('#gridDeviceList').omGrid("setData", 'device_list_by_groupId.json?deviceGroupId=' + selections[0].id + '&tableName=bss_panel_package_device_map&character=panel_package_id&ystenId=' + encodeURIComponent(ystenId) + '&deviceCode=' + encodeURIComponent(deviceCode) + '&mac=' + encodeURIComponent(mac) + '&sno=' + encodeURIComponent(sno));
    });

    function showProgramDialog(title) {
        var dialogDeviceList = $("#dialog-form-device-list").omDialog({
            width: 950, height: 550, autoOpen: false, resizable: false,
            draggable: false, modal: true
        });
        dialogDeviceList.omDialog("option", "title", title);
        dialogDeviceList.omDialog("open");
    }

    /*********************************显示设备列表 end*************************************************************/

    /*********************************显示用户列表 start*************************************************************/
    $('#customer_list_of_group').bind('click', function (e) {
        $('#region1').omCombo({dataSource: 'city.json', editable: false, listMaxHeight: 150, width: 80});
        var selections = $('#grid').omGrid('getSelections', true);
        if (selections.length == 0 || selections.length > 1) {
            YST_APP.alertMsg("显示操作至少且只能选择一行记录！");
            return false;
        }
        showCustomerDialog("关联的用户列表");
        $('#gridUserList').omGrid({
            dataSource: 'customer_list_by_groupId.json?userGroupId=' + selections[0].id + '&tableName=bss_panel_package_user_map&character=panel_package_id',
            width: '100%',
            height: rFrameHeight - 100,
            limit: limit,
            singleSelect: false,
            colModel: [
                {header: '<b>用户编号</b>', name: 'code', align: 'center', width: 150, editor: {editable: false}},
                {header: '<b>用户外部编号</b>', name: 'userId', align: 'center', width: 120},
                {header: '<b>登录名</b>', name: 'loginName', align: 'center', width: 120},
                {header: '<b>真实姓名</b>', name: 'realName', align: 'center', width: 120},
                {header: '<b>用户类型</b>', name: 'customerType', align: 'center', width: 60},
                {header: '<b>用户状态</b>', name: 'state', align: 'center', width: 60},
                {header: '<b>是否锁定</b>', name: 'isLock', align: 'center', width: 60},
                {header: '<b>所属地市</b>', name: 'region', align: 'center', width: 50, renderer: function (value) {
                    if (value == null || value == "") {
                        return "";
                    } else {
                        return value.name;
                    }
                }
                },
                {header: '<b>创建时间</b>', name: 'createDate', align: 'center', width: 120},
                {header: '<b>服务到期时间</b>', name: 'serviceStop', align: 'center', width: 120},
                {header: '<b>用户电话</b>', name: 'phone', align: 'center', width: 100}
            ]
        });

    });
    $('#delUser').click(function () {
        $('#deleteType').omCombo({dataSource: [
            {text: '移除选中用户', value: '0'},
            {text: '移除全部用户', value: '1'}
        ],
            value: '0',
            width: 180,
            listMaxHeight: 160
        });
        var dialog_del_User = $("#del-dialog-form").omDialog({
            width: 300,
            autoOpen: false,
            modal: true,
            resizable: false,
            draggable: false,
            buttons: {
                "提交": function () {
                    var selections_user = $('#gridUserList').omGrid('getSelections', true);
                    if ($('#deleteType').omCombo('value') == 0) {
                        if (selections_user.length == 0) {
                            YST_APP.alertMsg("移除选中至少选择一行记录！");
                            return false;
                        }
                    }
                    $.omMessageBox.confirm({
                        title: '确认移除',
                        //content:'删除设备分组信息后将同时删除设备分组绑定关系且无法恢复，你确定要执行该操作吗？',
                        content: '移除用户记录将无法恢复，你确定要执行该操作吗？',
                        onClose: function (v) {
                            if (v == true) {
                                var selections = $('#grid').omGrid('getSelections', true);
                                var toDeleteRecordID = YST_APP.convertIds2Str(selections_user);
                                if ($('#deleteType').omCombo('value') == 0) {
                                    $.ajax({
                                        type: "POST",
                                        url: "delete_device_by_bussiness.json?delYstenIds=" + toDeleteRecordID + "&packageId=" + selections[0].id + '&tableName=bss_panel_package_user_map&character=panel_package_id&type=USER&device=code',
                                        success: function (result) {
                                            if (result) {
                                                $('#gridUserList').omGrid('reload');
                                                $('#gridUserList').omGrid('saveChanges');
                                                YST_APP.show_message("移除成功！", 'success');
                                            } else {
                                                YST_APP.show_message(result, 'error');
                                            }
                                        }
                                    });
                                }
                                else if ($('#deleteType').omCombo('value') == 1) {
                                    $.ajax({
                                        type: "POST",
                                        url: "delete_device_by_bussiness.json?delYstenIds=" + toDeleteRecordID + "&packageId=" + selections[0].id + '&tableName=bss_panel_package_user_map&character=panel_package_id&type=USER&device=code',
                                        success: function (result) {
                                            if (result) {
                                                $('#gridUserList').omGrid('reload');
                                                $('#gridUserList').omGrid('saveChanges');
                                                YST_APP.show_message("移除成功！", 'success');
                                            } else {
                                                YST_APP.show_message(result, 'error');
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    });
                    $("#del-dialog-form").omDialog("close");
                },
                "取消": function () {
                    $("#del-dialog-form").omDialog("close");
                }
            }, onClose: function () {
                $('#delDeviceMyform').validate().resetForm();
                $("#del-dialog-form").omDialog("close");
            }

        });
        dialog_del_User.omDialog("option", "title", "用户移除");
        dialog_del_User.omDialog("open");
    });

    function showCustomerDialog(title) {
        var dialogCustomerList = $("#dialog-form-customer-list").omDialog({
            width: 950, height: 550, autoOpen: false, resizable: false,
            draggable: false, modal: true
        });
        dialogCustomerList.omDialog("option", "title", title);
        dialogCustomerList.omDialog("open");
    }

    $('#queryUser').bind('click', function (e) {
        var selections = $('#grid').omGrid('getSelections', true);
        var userId = $('#userId1').val();
        var code = $('#code1').val();
        var phone = $('#phone1').val();
        var region = $('#region1').val();
        $('#gridUserList').omGrid("setData", 'customer_list_by_groupId.json?userGroupId=' + selections[0].id + '&userId=' + encodeURIComponent(userId) + '&code=' + encodeURIComponent(code) + '&phone=' + encodeURIComponent(phone) + '&cRegion=' + encodeURIComponent(region) + '&tableName=bss_panel_package_user_map&character=panel_package_id');
    });
    /*********************************显示用户列表 end*************************************************************/
});