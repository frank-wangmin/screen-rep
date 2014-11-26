<%@ page language="java" pageEncoding="UTF-8" %>

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
    $('#grid').omGrid({
        dataSource: 'interface_url_list.json',
        width: '100%',
        height: rFrameHeight,
        singleSelect: false,
        limit: limit,
        colModel: [
            {header: '<b>区域</b>', name: 'area', align: 'center', width: 150, renderer: function (value) {
                if (value == null || value == "") {
                    return "";
                } else {
                    return value.name;
                }
            }},
            {header: '<b>接口名称</b>', name: 'interfaceName', align: 'center', width: 400},
            {header: '<b>接口地址</b>', name: 'interfaceUrl', align: 'center', width: 500}
        ]
    });

    $('#create').omButtonbar({btns: [
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

    $('#areaId1').omCombo({dataSource: 'area.json', editable: false, width: 160, listMaxHeight: 160});
    $('#interfaceName1').omCombo({dataSource: "device_interface_name.json", editable: false, width: 160, listMaxHeight: 160});


    $('#query').bind('click', function (e) {
        var areaId = $('#areaId1').val();
        var interfaceName = $('#interfaceName1').val();
        $('#grid').omGrid("setData", 'interface_url_list.json?interfaceName=' + encodeURIComponent(interfaceName) + '&areaId=' + encodeURIComponent(areaId));
    });

    var dialog = $("#dialog-form").omDialog({
        width: 530, autoOpen: false,
        modal: true, resizable: false,
        draggable: false,
        buttons: {
            "提交": function () {
                submitDialog();
            },
            "取消": function () {
                $("#dialog-form").omDialog("close");
            }
        }, onClose: function () {
            $('#myform').validate().resetForm();
//            validator.resetForm();
        }
    });

    var showDialog = function (title, rowData) {
        validator.resetForm();
        rowData = rowData || {};
        dialog.omDialog("option", "title", title);
        dialog.omDialog("open");
    };


    var isAdd = true;
    $('#create').bind('click', function () {
//        validator.resetForm();
        isAdd = true;
        $('#areaId').omCombo({dataSource: 'area.json?par=1', editable: false, width: 220, listMaxHeight: 160,value:'0'});
        $('#interfaceName').omCombo({dataSource: "device_interface_name.json", editable: false, width: 220, listMaxHeight: 160,value:'0'});
        showDialog('新增终端接口地址信息');
    });

    $('#update').bind('click', function () {
        var selections = $('#grid').omGrid('getSelections', true);
        if (selections.length == 0 || selections.length > 1) {
            $.omMessageBox.alert({
                type: 'alert',
                title: '温馨提示',
                content: '修改操作至少且只能选择一行记录！',
            });
            return false;
        }
        isAdd = false;
        $.ajax({
            type: "post",
            url: "interface_url_to_update.json?id=" + selections[0].id,
            dataType: "json",
            success: function (msg) {
                $("#id").val(msg['id']);
                $('#areaId').omCombo({dataSource: 'area.json', editable: false, width: 220, listMaxHeight: 160, value: msg['area']['id']});
                $('#interfaceName').omCombo({dataSource: "device_interface_name.json", editable: false, width: 220, listMaxHeight: 160, value: msg['interfaceName']});
                $("#interfaceUrl").val(msg['interfaceUrl']);
            }
        });
        showDialog('修改终端接口地址信息息', selections[0]);
    });

    $('#delete').click(function () {
        var selections = $('#grid').omGrid('getSelections', true);
        if (selections.length == 0) {
            $.omMessageBox.alert({
                type: 'alert',
                title: '温馨提示',
                content: '删除操作至少选择一行记录！',
            });
            return false;
        }

        $.omMessageBox.confirm({
            title: '确认删除',
            content: '执行删除后数据将不可恢复，你确定要执行该操作吗？',
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
                    $.post('delete_interface_url.json', {ids: toDeleteRecordID.toString()}, function (result) {
                        $('#grid').omGrid('reload');
                        if (result == 'success') {
                            $.omMessageTip.show({title: tip, content: "删除终端接口信息成功！", timeout: time, type: 'success'});
                        } else {
                            $.omMessageTip.show({title: tip, content: result, timeout: time, type: 'error'});
                        }
                        $('#dialog-form').omDialog('close');
                    });
                }
            }
        });
    });

    var submitDialog = function () {
        if (validator.form()) {
            if (isAdd) {
                $.post('interface_url_add.shtml', $("#myform").serialize(), function (result) {
                    if ("success" == result) {
                        $.omMessageTip.show({title: tip, content: "新增终端接口地址信息成功！", type: "success", timeout: time});
                    } else if ("existed" == result) {
                        $.omMessageTip.show({title: tip, content: "该区域和接口名称已经存在！", type: "error", timeout: time});
                    } else {
                        $.omMessageTip.show({title: tip, content: "新增终端接口地址信息失败！", type: "error", timeout: time});
                    }
                    $('#grid').omGrid('reload');
                    $("#dialog-form").omDialog("close");
                });
            } else {
                $.post('interface_url_update.shtml', $("#myform").serialize(), function (result) {
                    if ("success" == result) {
                        $.omMessageTip.show({title: tip, content: "修改终端接口地址信息成功！", type: "success", timeout: time});
                    } else if ("existed" == result) {
                        $.omMessageTip.show({title: tip, content: "该区域和接口名称已经存在！", type: "error", timeout: time});
                    }
                    else {
                        $.omMessageTip.show({title: tip, content: "修改终端接口地址信息失败！" + result, type: "error", timeout: time});
                    }
                    $('#grid').omGrid('reload');
                    $("#dialog-form").omDialog("close");
                });
            }
        }
    };

    var validator = $('#myform').validate({
        rules: {
            interfaceName: {required: true},
            interfaceUrl: {required: true},
            areaId: {required: true},
        },
        messages: {
            interfaceName: {required: "终端接口名称不能为空！"},
            interfaceUrl: {required: "终端接口地址不能为空！"},
            areaId: {required: "区域信息不能为空！"}
        },
        errorPlacement: YST_APP.Error.errorPlacement,
        showErrors: YST_APP.Error.showErrors
    });
    $('#center-tab').omTabs({height: "33", border: false});
});
</script>
</head>
<body>
<div id="center-tab">
    <ul>
        <li><a href="#tab1">终端接口地址信息列表:</a></li>
    </ul>
</div>
<table>
    <tr>
        <c:if test="${sessionScope.add_interface_url != null }">
            <td>
                <div id="create"/>
            </td>
        </c:if>
        <c:if test="${sessionScope.update_interface_url != null }">
            <td>
                <div id="update"/>
            </td>
        </c:if>
        <c:if test="${sessionScope.delete_interface_url != null }">
            <td>
                <div id="delete"/>
            </td>
        </c:if>
        <td style="text-align:center;">区域：</td>
        <td><input id="areaId1" name="areaId1" style="width:180px;"/></td>
        <td style="text-align:center;">接口名称：</td>
        <td><input id="interfaceName1" name="interfaceName1" style="width:180px;"/></td>
        <td>
            <div id="query"/>
        </td>
    </tr>
</table>
<table id="grid"></table>
<div id="dialog-form">
    <form id="myform">
        <input type="hidden" value="" name="id" id="id"/>
        <table>
            <tr>
                <td align="right"><span class="color_red">*</span>区域：</td>
                <td><input id="areaId" name="areaId" style="width:180px;"/></td>
                <td><span></span></td>
            </tr>
            <tr>
                <td align="right"><span class="color_red">*</span>接口名称：</td>
                <td><input id="interfaceName" name="interfaceName" style="width:180px;"/></td>
                <td><span></span></td>
            </tr>
            <tr>
                <td align="right"><span class="color_red">*</span>接口地址：</td>
                <td><input id="interfaceUrl" name="interfaceUrl"
                           style="width:220px;height:20px;border:1px solid #86A3C4;"/></td>
                <td><span></span></td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
