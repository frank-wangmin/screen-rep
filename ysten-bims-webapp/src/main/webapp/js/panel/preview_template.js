/**
 *
 * Created by frank on 14-11-19.
 */
$(document).ready(function () {

//    BIMS_PANEL.init_preview_box();
    $('#center-tab').omTabs({height: "33", border: false});
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

    $('#preview_template').omButtonbar({btns: [
        {label: "模板配置", icons: {left: opPath + 'op-edit.png'}}
    ]});


    var isAdd = true;
    $('#grid').omGrid({
        dataSource: 'preview_template_list.json',
        width: '100%',
        height: rFrameHeight,
        singleSelect: true,
        limit: limit,
        colModel: [
            {header: '<b>ID</b>', name: 'id', align: 'center', width: 50},
            {header: '<b>模板名称</b>', name: 'name', align: 'center', width: 300},
            {header: '<b>描述</b>', name: 'description', align: 'center',width: 'autoExpand'}
        ]
    });

    $('#query').bind('click', function () {
        var Id = $.trim($('#queryId').val());
        var name = filterStartEndSpaceTrim($('#queryPanelName').val());
        if (Id != '' && !YST_APP.isInteger(Id)) {
            YST_APP.alertMsg("ID必须为正整数类型");
            return false;
        }
        $('#grid').omGrid("setData", 'preview_template_list.json?name=' + encodeURIComponent(name)+'&Id='+encodeURIComponent(Id));
    });

    $('#save').bind('click', function () {
        isAdd = true;
        $("#id").val('');
        $(".form-add-input").each(function () {
            $(this).val("");
        });
        $(".form-border-style").each(function () {
            $(this).val("");
        });
        $('#containsPs').omCombo({dataSource: YST_APP.YES_NO, value: 1, editable: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height});
        showDialog('新增模板信息');
    });

    $('#update').bind('click', function () {
        isAdd = false;
        var selections = $('#grid').omGrid('getSelections', true);
        if (selections.length == 0 || selections.length > 1) {
            YST_APP.alertMsg("修改操作至少且只能选择一行记录！");
            return false;
        }
        if (selections[0].epgTemplateId) {
            YST_APP.alertMsg("不可以编辑外部系统数据！");
            return;
        }
        $.ajax({
            url: "get_previewTemplate.json?id=" + selections[0].id,
            dataType: "json",
            success: function (data) {
                $("#id").val(data['id']);
                $("#name").val(data['name']);
                $('#containsPs').omCombo({dataSource: YST_APP.YES_NO, value: data['containsPs'], editable: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height});
                $("#description").val(data['description']);
            }
        });

        showDialog('修改模板信息', selections[0]);
    });

    var dialog = $("#dialog-form").omDialog({
        width: 550,
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
        var submitData;
        if (validator.form()) {
            if (isAdd) {
                submitData = {
                    name: $("#name").val(),
                    description: $("#description").val(),
                    containsPs: $("#containsPs").omCombo('value')
                };
                $.post('previewTemplate_add.json', submitData, function (result) {
                    $('#grid').omGrid('reload');
                    if ("success" == result) {
                        YST_APP.show_message("新增面板成功！","success");
                    } else {
                        YST_APP.show_message("新增面板失败！","error");
                    }
                    $("#dialog-form").omDialog("close");
                });
            } else {
                submitData = {
                    id: $("#id").val(),
                    name: $("#name").val(),
//                    platformId: $("#platformId").val(),
                    description: $("#description").val(),
                    containsPs: $("#containsPs").omCombo('value')
                };
                $.post('previewTemplate_update.json', submitData, function (result) {
                    $('#grid').omGrid('reload');
                    if ("success" == result) {
                        YST_APP.show_message("修改面板成功！","success");
                    } else {
                        YST_APP.show_message("修改面板失败！","error");
                    }
                    $("#dialog-form").omDialog("close");
                });
            }
        }
    };

    var validator = $('#myform').validate({
        rules: {
            name: {required: true}
        },
        messages: {
            name: {required: "模板名称不能为空！"}
        },
        errorPlacement: YST_APP.Error.errorPlacement,
        showErrors: YST_APP.Error.showErrors
    });

    $('#delete').bind('click', function (e) {
        var selections = $('#grid').omGrid('getSelections', true);
        if (selections.length == 0) {
            YST_APP.alertMsg("删除操作至少选择一行记录！");
            return false;
        }
        for(var i = 0; i < selections.length ; i++){
            if (selections[i].epgTemplateId) {
                YST_APP.alertMsg("不可以删除外部系统数据");
                return;
            }
        }
        $.omMessageBox.confirm({
            title: '确认删除',
            content: '批量删除预览模板信息后将无法恢复，你确定要执行该操作吗？',
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
                    $.post('previewTemplate_delete.json', {ids: toDeleteRecordID.toString()}, function (result) {
                        $('#grid').omGrid('reload');
                        if (result == 'success') {
                            YST_APP.show_message("删除预览模板信息成功！","success");
                        } else {
                            YST_APP.show_message("删除预览模板信息失败！","error");
                        }
                        $('#dialog-form').omDialog('close');
                    });
                }
            }
        });
    });

    /*################################################################ preview panel template ###################################*/
    var panel_template_dialog = BIMS_PANEL.panel_template_dialog();

    $("#preview_template").click(function () {
        var selections = $('#grid').omGrid('getSelections', true);
        if (selections.length == 0 || selections.length > 1) {
            YST_APP.alertMsg("预览操作至少且只能选择一行记录！");
            return;
        };
        if (selections[0].epgTemplateId) {
            YST_APP.alertMsg("不可以编辑外部系统数据！");
            return;
        }
        BIMS_PANEL.destroy_preview_box();
        $("#panel_layout").html("");
        $("#templateId").val(selections[0].id);
        $.ajax({
            url: "get_previewlist_by_templateId.json?templateId=" + selections[0].id,
            dataType: "json",
            type:'GET',
            success: function (data) {
                BIMS_PANEL.set_preview_boxs(data);
            }
        });
        panel_template_dialog.omDialog("option", "title", "模板配置");
        panel_template_dialog.omDialog("open");
    });
    /*################################################################ preview panel template ###################################*/
});
