<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title></title>
<%@ include file="/include/taglibs.jsp" %>
<%@ include file="/include/operamasks-ui-2.0.jsp" %>
<%@ include file="/include/css.jsp" %>
<%@ include file="/include/ysten.jsp" %>
<script type="text/javascript">
$(document).ready(function () {

    var enums = {};

    var init = function(){
        $.getJSON("panel_online_status.json",function(data){
            enums.online_status = data;
        });
        $.getJSON("panel_status.json",function(data){
            enums.panel_status = data;
        });
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

    var isAdd = true;
    $('#grid').omGrid({
        dataSource: 'panel_list.json',
        width: '100%',
        height: rFrameHeight,
        singleSelect: false,
        limit: limit,
        colModel: [
            {header: '<b>面板名称</b>', name: 'panelName', align: 'center', width: 120},
            {header: '<b>面板标题</b>', name: 'panelTitle', align: 'center', width: 120},
            {header: '<b>面板类型</b>', name: 'panelType', align: 'center', width: 150, renderer: panel_type_func},
            {header: '<b>在线状态</b>', name: 'onlineStatus', align: 'center', width: 110, renderer: online_status_func},
            {header: '<b>链接地址</b>', name: 'linkUrl', align: 'center', width: 120},
            {header: '<b>创建时间</b>', name: 'createDate', align: 'center', width: 380},

        ]
    });

    $('#query').bind('click', function () {
        var panelName = $('#queryPanelName').val();
        $('#grid').omGrid("setData", 'panel_list.json?panelName=' + encodeURIComponent(panelName));
    });

    $('#save').bind('click', function () {
        isAdd = true;
        $("#id").val('');
        $(".form-add-input").each(function(){
            $(this).val("");
        });
        $(".form-border-style").each(function(){
            $(this).val("");
        });
        $("#onlineStatueTime").omCalendar();
        $('#onlineStatus').omCombo({dataSource: enums.online_status, value: '0', editable: false, width: 180, listMaxHeight: 160});
        $('#status').omCombo({dataSource: enums.panel_status, value: '0', editable: false, width: 180, listMaxHeight: 160});
        showDialog('新增设备分组信息');
    });

    $('#update').bind('click', function () {
        isAdd = false;
        var selections = $('#grid').omGrid('getSelections', true);
        if (selections.length == 0 || selections.length > 1) {
            YST_APP.alertMsg("修改操作至少且只能选择一行记录！");
            return false;
        }
        $.ajax({
            url: "get_panel_byId.json?id=" + selections[0].id,
            dataType: "json",
            success: function (data) {
                $("#id").val(data['id']);
                $("#panelName").val(data['panelName']);
                $("#panelTitle").val(data['panelTitle']);
                $("#panelStyle").val(data['panelStyle']);
                $("#panelIcon").val(data['panelIcon']);
                $("#linkUrl").val(data['linkUrl']);
                $("#imgUrl").val(data['imgUrl']);
                $("#onlineStatueTime").omCalendar();
                $("#onlineStatueTime").val(new Date(data['onlineStatueTime']).Format("yyyy-MM-dd"));
                $("#epg1Data").val(data['epg1Data']);
                $("#epg1Style").val(data['epg1Style']);
                $("#epg2Data").val(data['epg2Data']);
                $("#epg2Style").val(data['epg2Style']);

                $('#onlineStatus').omCombo({dataSource: enums.online_status, value: data['onlineStatus'], editable: false, width: 180, listMaxHeight: 160});
                $('#status').omCombo({dataSource: enums.panel_status, value: data['status'], editable: false, width: 180, listMaxHeight: 160});
            }
        });

        showDialog('修改面板信息', selections[0]);
    });

    var dialog = $("#dialog-form").omDialog({
        width: 500,
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
        }
    });

    var showDialog = function (title, rowData) {
        rowData = rowData || {};
        dialog.omDialog("option", "title", title);
        dialog.omDialog("open");
    };

    var submitDialog = function () {
        var submitData;
        if (validator.form()) {
            if (isAdd) {
                submitData = {
                    panelName: $("#panelName").val(),
                    panelTitle: $("#panelTitle").val(),
                    panelStyle: $("#panelStyle").val(),
                    panelIcon:$("#panelIcon").val(),
                    linkUrl: $("#linkUrl").val(),
                    imgUrl: $("#imgUrl").val(),
                    onlineStatus: $("#onlineStatus").omCombo('value'),
                    onlineStatueTime: $("#onlineStatueTime").val(),
                    status: $("#status").omCombo('value'),
                    epg1Data: $("#epg1Data").val(),
                    epg1Style: $("#epg1Style").val(),
                    epg2Data: $("#epg2Data").val(),
                    epg2Style: $("#epg2Style").val()
                };
                $.post('panel_add.json', submitData, function (result) {
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
                    panelName: $("#panelName").val(),
                    panelTitle: $("#panelTitle").val(),
                    panelStyle: $("#panelStyle").val(),
                    panelIcon:$("#panelIcon").val(),
                    linkUrl: $("#linkUrl").val(),
                    imgUrl: $("#imgUrl").val(),
                    onlineStatus: $("#onlineStatus").omCombo('value'),
                    onlineStatueTime: $("#onlineStatueTime").val(),
                    status: $("#status").omCombo('value'),
                    epg1Data: $("#epg1Data").val(),
                    epg1Style: $("#epg1Style").val(),
                    epg2Data: $("#epg2Data").val(),
                    epg2Style: $("#epg2Style").val()
                };
                $.post('panel_update.json', submitData, function (result) {
                    $('#grid').omGrid('reload');
                    if ("success" == result) {
                        YST_APP.show_message("修改面板成功！","success");
                    } else {
                        YST_APP.show_message("修改面板失败！","error");
                        $.omMessageTip.show({title: tip, content: "修改面板失败！", type: "error", timeout: time});
                    }
                    $("#dialog-form").omDialog("close");
                });
            }
        }
    };

    var validator = $('#myform').validate({
        rules: {
            panelName: {required: true},
            panelTitle: {required: true},
            panelStyle: {required: true}
        },
        messages: {
            panelName: {required: "面板名称不能为空！"},
            panelTitle: {required: "面板标题不能为空！"},
            panelStyle: {required: "面板式样不能为空！"}
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
        $.omMessageBox.confirm({
            title: '确认删除',
            content: '批量删除面板信息后将无法恢复，你确定要执行该操作吗？',
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
                    $.post('panel_delete.json', {ids: toDeleteRecordID.toString()}, function (result) {
                        $('#grid').omGrid('reload');
                        if (result == 'success') {
                            YST_APP.show_message("删除面板信息成功！","success");
                        } else {
                            YST_APP.show_message("删除面板信息失败！","error");
                        }
                        $('#dialog-form').omDialog('close');
                    });
                }
            }
        });
    });
    $('#center-tab').omTabs({height: "33", border: false});
});
</script>
</head>
<body>
<div id="center-tab">
    <ul>
        <li><a href="#tab1">面板信息列表:</a></li>
    </ul>
</div>
<table>
    <tr align="left">
        <c:if test="${sessionScope.add_panel != null }">
            <td>
                <div id="save"></div>
            </td>
        </c:if>
        <c:if test="${sessionScope.update_panel != null }">
            <td>
                <div id="update"></div>
            </td>
        </c:if>
        <c:if test="${sessionScope.delete_panel != null }">
            <td>
                <div id="delete"></div>
            </td>
        </c:if>
        <td>面板名称：</td>
        <td><input name="content2" id="queryPanelName" class="query-title-input"/></td>
        <td>
            <div id="query"></div>
        </td>
    </tr>
</table>
<table id="grid"></table>

<div id="dialog-form" style="display: none;">
    <form id="myform">
        <input type="hidden" value="" name="id" id="id"/>
        <table>
            <tr>
                <td class="form-add-title"><span class="color_red">*</span>面板名称：</td>
                <td><input type="text" name="panelName" id="panelName" class="form-add-input"
                           maxlength="17"/></td>
            </tr>
            <tr>
                <td class="form-add-title"><span class="color_red">*</span>面板标题：</td>
                <td><input name="panelTitle" id="panelTitle" class="form-add-input"/></td>
            </tr>
            <%--<tr>--%>
            <%--<td class="form-add-title"><span class="color_red">*</span>面板类型：</td>--%>
            <%--<td><input type="text" name="panelType" id="panelType"/></td>--%>
            <%--<td style="width: 200px;"><span></span></td>--%>
            <%--</tr>--%>
            <tr>
                <td class="form-add-title"><span class="color_red">*</span>面板式样：</td>
                <td><input type="text" name="panelStyle" id="panelStyle"
                           class="form-add-input"/></td>
            </tr>
            <tr>
                <td class="form-add-title">面板图标：</td>
                <td><input type="text" name="panelIcon" id="panelIcon"
                           class="form-add-input"/></td>
            </tr>

            <tr>
                <td class="form-add-title">链接地址：</td>
                <td><input type="text" name="linkUrl" id="linkUrl"
                           class="form-add-input"/></td>
            </tr>
            <tr>
                <td class="form-add-title">背景图片：</td>
                <td><input type="text" name="imgUrl" id="imgUrl"
                           class="form-add-input"/></td>
            </tr>
            <tr>
                <td class="form-add-title">在线状态：</td>
                <td><input type="text" name="onlineStatus" id="onlineStatus"
                           class="form-border-style"/></td>
            </tr>
            <tr>
                <td class="form-add-title">在线状态时间：</td>
                <td><input name="onlineStatueTime" id="onlineStatueTime" class="form-border-style"/></td>
            </tr>
            <tr>
                <td class="form-add-title">状态：</td>
                <td><input type="text" name="status" id="status"
                           class="form-border-style"/></td>
            </tr>
            <tr>
                <td class="form-add-title">epg1数据：</td>
                <td><textarea id="epg1Data" name="epg1Data" cols="30" rows="4" class="form-border-style"></textarea>
                </td>
            </tr>
            <tr>
                <td class="form-add-title">epg1样式：</td>
                <td><textarea id="epg1Style" name="epg1Style" cols="30" rows="4" class="form-border-style"></textarea>
                </td>
            </tr>
            <tr>
                <td class="form-add-title">epg2数据：</td>
                <td><textarea id="epg2Data" name="epg2Data" cols="30" rows="4" class="form-border-style"></textarea>
                </td>
            </tr>
            <tr>
                <td class="form-add-title">epg2样式：</td>
                <td><textarea id="epg2Style" name="epg2Style" cols="30" rows="4" class="form-border-style"></textarea>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>