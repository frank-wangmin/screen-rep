/**
 * Created by frank on 14-11-19.
 */
$(document).ready(function () {
    $('#center-tab').omTabs({height: "33", border: false});
    var panel_item_content_type;

    $.getJSON("panel_item_content_type.json", function (data) {
        BIMS_PANEL.enums.panel_item_content_type = data;
    });
    $.getJSON("panel_item_type.json", function (data) {
        BIMS_PANEL.enums.panel_item_type = data;
    });
    $.getJSON("panel_item_action_type.json", function (data) {
        BIMS_PANEL.enums.panel_item_action_type = data;
    });

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

    var isAdd = true;
    $('#grid').omGrid({
        dataSource: 'panel_item_list.json',
        width: '100%',
        height: rFrameHeight,
        singleSelect: true,
        limit: limit,
        colModel: [
            {header: '<b>ID</b>', name: 'id', align: 'center', width: 60},
            {header: '<b>面板项标题</b>', name: 'title', align: 'center', width: 100},
            {header: '<b>面板项名称</b>', name: 'name', align: 'center', width: 120},
            {header: '<b>父面板项</b>', name: 'parentItemTitle', align: 'center', width: 100},
            {header: '<b>关联面板项</b>', name: 'relatedItemTitle', align: 'center', width: 90},
            {header: '<b>分辨率</b>', name: 'resolution', align: 'center', width: 60},
            {header: '<b>动作类型</b>', name: 'actionType', align: 'center', width: 70, renderer: BIMS_PANEL.renderActionTypeFunc},
            {header: '<b>面板项类型</b>', name: 'contentType', align: 'center', width: 70, renderer: BIMS_PANEL.renderContengTypeFunc},
            {header: '<b>是否自动打开</b>', name: 'autoRun', align: 'center', width: 80, renderer: YST_APP.YES_NO_FUNC},
            {header: '<b>焦点是否进入</b>', name: 'focusRun', align: 'center', width: 80, renderer: YST_APP.YES_NO_FUNC},
            {header: '<b>是否显示标题</b>', name: 'showTitle', align: 'center', width: 80, renderer: YST_APP.YES_NO_FUNC},
            {header: '<b>是否包含子项</b>', name: 'hasSubItem', align: 'center', width: 'autoExpand', renderer: YST_APP.YES_NO_FUNC}
        ]
    });

    /*   $('#query').bind('click', function () {
     var name = filterStartEndSpaceTrim($('#queryPanelName').val());
     $('#grid').omGrid("setData", 'panel_item_list.json?name=' + encodeURIComponent(name));
     });*/

    $('#query').bind('click', function () {
        var name = $.trim($('#queryPanelName').val());
        var title = $.trim($('#queryPanelTitle').val());
        var Id = $.trim($('#queryId').val());
        if (Id != '' && !YST_APP.isInteger(Id)) {
            YST_APP.alertMsg("ID必须为正整数类型");
            return false;
        }
        $('#grid').omGrid("setData", 'panel_item_list.json?name=' + name + "&title=" + title + "&Id=" + Id);
    });

    $('#save').bind('click', function () {
        $("#showResultActionUrl").html("");
        $('#showResultImageDistr').html("");
        $('#showResultVideoUrl').html("");
        $('#showResultParams').html("");
        BIMS_PANEL.config.panel_item_edit_flag = false;
        BIMS_PANEL.config.panel_item_edit_id = null;
        BIMS_PANEL.config.panel_item_parent_id = null;
        BIMS_PANEL.config.panel_item_ref_id = null;
        BIMS_PANEL.config.panel_item_action_type = "1";
        BIMS_PANEL.config.panel_item_content_type = "image";
        isAdd = true;
        $("#id").val('');
        $('#autoRun').omCombo({dataSource: YST_APP.YES_NO, value: '0', editable: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height});
        $('#focusRun').omCombo({dataSource: YST_APP.YES_NO, value: '1', editable: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height});
        $('#showTitle').omCombo({dataSource: YST_APP.YES_NO, value: '0', editable: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height});
        $('#animationRun').omCombo({dataSource: YST_APP.YES_NO, value: '0', editable: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height});
        $('#hasSubItem').omCombo({dataSource: YST_APP.YES_NO, value: '0', editable: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height,
            onValueChange: BIMS_PANEL.has_sub_item_change});
        $('#contentType').omCombo({dataSource: BIMS_PANEL.enums.panel_item_content_type, value: BIMS_PANEL.config.panel_item_content_type, editable: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height,
            onValueChange: BIMS_PANEL.content_type_change});
        $('#actionType').omCombo({dataSource: BIMS_PANEL.enums.panel_item_action_type, value: '1', editable: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height,
            onValueChange: BIMS_PANEL.action_type_change});
        $("#defaultfocus").omCombo({
            dataSource: [
                //left、center或right
                {text: '是', value: 'true'},
                {text: '否', value: 'false'}
            ], value: 'false', editable: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height,
            onValueChange: BIMS_PANEL.action_type_change
        });
        $("#resolution").omCombo({dataSource: YST_APP.RESOLUTION, value: BIMS_PANEL.config.panel_dpi, editable: false, disabled: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height,
            onValueChange: BIMS_PANEL.panel_dpi_change});
        showDialog('新增面板项信息');
    });

    $('#queryCategory').bind('click', function () {
        var name = $.trim($('#cateName').val());
        var Id = $.trim($('#catgId').val());
        if (Id != '' && !YST_APP.isInteger(Id)) {
            YST_APP.alertMsg("ID必须为正整数类型");
            return false;
        }
        $('#gridCategory').omGrid("setData", 'TV_category_list.json?cateName=' + name + "&cateId=" + Id);
    });

    $('#queryProgram').bind('click', function () {
        var name = $.trim($('#programName').val());
        var Id = $.trim($('#programId').val());
        if (Id != '' && !YST_APP.isInteger(Id)) {
            YST_APP.alertMsg("ID必须为正整数类型");
            return false;
        }
        $('#gridProgram').omGrid("setData", 'TV_program_list.json?programName=' + name + "&programId=" + Id);
    });

    $('#update').bind('click', function () {
        $("#showResultActionUrl").html("");
        $('#showResultImageDistr').html("");
        $('#showResultVideoUrl').html("");
        $('#showResultParams').html("");
        isAdd = false;
        var selections = $('#grid').omGrid('getSelections', true);
        if (selections.length == 0 || selections.length > 1) {
            YST_APP.alertMsg("修改操作至少且只能选择一行记录！");
            return false;
        }
        if (selections[0].epgPanelitemId) {
            YST_APP.alertMsg("不可以编辑外部系统数据！");
            return;
        }
        if (selections[0].districtCode != districtCode) {
            YST_APP.alertMsg("不可以编辑省级数据！");
            return;
        }
        BIMS_PANEL.config.panel_item_edit_flag = true;
        BIMS_PANEL.config.panel_item_edit_id = selections[0].id;
        $.ajax({
            url: "get_panel_item.json?id=" + selections[0].id,
            dataType: "json",
            success: function (data) {
                if (data) {
                    BIMS_PANEL.config.panel_item_content_type = data['contentType'];
                    BIMS_PANEL.config.panel_dpi = data['resolution'];
                    BIMS_PANEL.config.panel_item_ref_id = data['refItemId'];
                    BIMS_PANEL.config.panel_item_parent_id = data['panelItemParentId'];
                    BIMS_PANEL.config.panel_item_action_type = data['actionType'];
                    BIMS_PANEL.show_edit_panelitem(data['contentType'], data['refItemId'] || "", data['panelItemParentId'] || "", data['hasSubItem'], data['resolution']);
                    $("#id").val(data['id']);
                    $("#name").val(data['name']);
                    $("#title").val(data['title']);
                    $("#titleComment").val(data['titleComment']);
                    $("#actionType").val(data['actionType']);
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
                    $("#resolution").omCombo({dataSource: YST_APP.RESOLUTION, value: data['resolution'], disabled: true,editable: false, width: BIMS_PANEL.config.select_input_length, listMaxHeight: BIMS_PANEL.config.select_input_height,
                        onValueChange: BIMS_PANEL.panel_dpi_change});
                    $("#params").val(data['params']);
                }
            }
        });

        showDialog('修改面板项信息', selections[0]);
    });

    var dialog = $("#dialog-form").omDialog({
        width: 720,
        autoOpen: false,
        modal: true,
        resizable: true,
        draggable: true,
        buttons: {
            "提交": function () {
                validator.form();
                var sr = $("#showResult").html();
                $('#selectContentId3').show();
                if (!$("#showResultActionUrl").html()
                    && !$('#showResultImageDistr').html() && !$('#showResultParams').html() && !$('#showResultVideoUrl').html() && (sr == '' || sr == '可用!')) {
                    submitDialog();
                } else {
                    return false;
                }
//                $('#grid').omGrid('reload');
            },
            "取消": function () {
                $("#showResult").text("");
                $("#dialog-form").omDialog("close");
            }
        }, onClose: function () {
            $("#showResult").text("");
            validator.resetForm();
        }
    });

    var showDialog = function (title) {
        dialog.omDialog("option", "title", title);
        dialog.omDialog("open");
    };
    var collectSubmitData = function () {
        return {
            id: $("#id").val(),
            name: $("#name").val(),
            title: $("#title").val(),
            titleComment: $("#titleComment").val(),
            actionType: $("#actionType").val(),
            actionUrl: $("#actionUrl").val(),
            imageUrl: $("#imageUrl").val(),
            videoUrl: $("#videoUrl").val(),
            content: $("#panel_item_content").val(),
            hasSubItem: $("#hasSubItem").val(),
            contentType: $("#contentType").val(),
            refItemId: $("#refItemId").val(),
            panelItemParentId: $("#panelItemParentId").val(),
            autoRun: $("#autoRun").val(),
            focusRun: $("#focusRun").val(),
            showTitle: $("#showTitle").val(),
            animationRun: $("#animationRun").val(),
            defaultfocus: $("#defaultfocus").val(),
            params: $("#params").val(),
            resolution: $("#resolution").val()
        }
    };

    var submitDialog = function () {
        var submitData;
        BIMS_PANEL.check_panel_item_add_or_edit_validator();
        if (validator.form()) {
            submitData = collectSubmitData();
//            submitData.serialize();
//            submitData = $("#myform").serialize();
            replaceJsonToNullValue(submitData);
            if (isAdd) {
                $.ajaxFileUpload({
                    url: 'panel_item_add.json',
                    secureuri: false,
                    fileElementId: ['fileField2', 'fileField3'],
                    data: submitData,
                    dataType: 'json',
                    success: function (result) {
                        $('#grid').omGrid('reload');
                        $("#dialog-form").omDialog("close");
                        if ("success" == result) {
                            YST_APP.show_message("新增面板项成功！", 'success');
                        } else if ("failed" == result) {
                            YST_APP.show_message("新增面板项失败！", 'error');
                        } else if ("ioException" == result) {
                            YST_APP.show_message("文件上传异常！", 'error');
                        } else {
                            YST_APP.show_message("新增面板项失败！", 'error');
                        }
                    }
                });

            } else {

                console.log(submitData);
                $.ajaxFileUpload({
                    url: 'panel_item_update.json',
                    secureuri: false,
                    fileElementId: ['fileField2', 'fileField3'],
                    data: submitData,
                    dataType: 'JSON',
                    success: function (result) {
                        $('#grid').omGrid('reload');
                        $("#dialog-form").omDialog("close");
                        if ("success" == result) {
                            YST_APP.show_message("修改面板项成功！", 'success');
                        } else if ("related" == result) {
                            YST_APP.show_message("该父面板项已经被关联，不可改为子面板项！", 'error');
                        } else if ("typeChange" == result) {
                            YST_APP.show_message("该面板项已经被关联，不可修改内容类型！", 'error');
                        } else if ("ioException" == result) {
                            YST_APP.show_message("文件上传异常！", 'error');
                        } else {
                            YST_APP.show_message("修改面板项失败！", 'error');
                        }
                    }
                });
            }
        }
    };

    var validator = $('#myform').validate({
        rules: {
            name: {required: true, maxlength: 256},
            title: {required: true, maxlength: 60},
            titleComment: {maxlength: 1024},
            contentType: {required: true},
//            actionUrl: {required: true, maxlength: 512},
            imageUrl: {required: true, maxlength: 255},
            videoUrl: {required: true, maxlength: 255},
            params: {maxlength: 1024}
        },
        messages: {
            name: {required: "面板项名称不能为空！", maxlength: "面板项名称最长256位字符！"},
            title: {required: "面板项标题不能为空！", maxlength: "标题最长60位字符！"},
            titleComment: {maxlength: "标题说明最长1024位字符！"},
            contentType: {required: "面板项类型不能为空！"},
//            actionUrl: {required: "动作地址不能为空！"},
            imageUrl: {required: "图片地址不能为空！", maxlength: "图片地址最长255位字符！"},
            videoUrl: {required: "视频地址不能为空！", maxlength: "图片地址最长255位字符！"},
            params: {maxlength: "动作参数最长1024位字符！"}
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
        for (var i = 0; i < selections.length; i++) {
            if (selections[i].epgPanelitemId) {
                YST_APP.alertMsg("不可以删除外部系统数据");
                YST_APP.alertMsg("不可以删除外部系统数据");
                return;
            }
        }
        $.omMessageBox.confirm({
            title: '确认删除',
            content: '批量删除面板项信息后将无法恢复，你确定要执行该操作吗？',
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
                    $.post('panel_item_delete.json', {ids: toDeleteRecordID.toString()}, function (result) {
                        $('#grid').omGrid('reload');
                        if (result == 'success') {
                            YST_APP.show_message("删除面板项信息成功！", 'success');
                        } else {
                            YST_APP.show_message("删除面板项信息失败！", 'error');
                        }
                        $('#dialog-form').omDialog('close');
                    });
                }
            }
        });
    });
});


function checkNameExists() {
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
                $("#showResult").html(msg);
            }
        });
    } else if ($.trim($("#name").val()) == "" && $("#showResult").html() != "") {
        $("#showResult").html("");
    }
}
//检查params 参数类型 是否符合：key:value;key:value;
function checkParamsValid(paramsId, showResultId) {
    var params = $(paramsId).val();
    var regexp = new RegExp("^(\\w+\\:\\w+\\;\\n*)*$");
    if (!regexp.test(params)) {
        $(showResultId).html('动作参数格式不正确，请确认![key:value;key:value;key:value;]');
    } else {
        $(showResultId).html('');
    }
}
