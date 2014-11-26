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
$(function () {
    var validator = $('#myform').validate({
        rules: {
            descriptionInForm: {required: true},
            serviceTypeInForm: {required: true}
        },
        messages: {
            descriptionInForm: {required: "描述不能为空！"},
            serviceTypeInForm: {required: "服务类型不能为空！"}
        },
        errorPlacement: function (error, element) {
            if (error.html()) {
                $(element).parents().map(function () {
                    if (this.tagName.toLowerCase() == 'td') {
                        var attentionElement = $(this).next().children().eq(0);
                        attentionElement.html(error);
                    }
                });
            }
        },
        showErrors: function (errorMap, errorList) {
            if (errorList && errorList.length > 0) {
                $.each(errorList, function (index, obj) {
                    var msg = this.message;
                    $(obj.element).parents().map(function () {
                        if (this.tagName.toLowerCase() == 'td') {
                            var attentionElement = $(this).next().children().eq(0);
                            attentionElement.show();
                            attentionElement.html(msg);
                        }
                    });
                });
            } else {
                $(this.currentElements).parents().map(function () {
                    if (this.tagName.toLowerCase() == 'td') {
                        $(this).next().children().eq(0).hide();
                    }
                });
            }
            this.defaultShowErrors();
        }
    });

    $('#add').omButtonbar({btns: [
        {label: "新增", icons: {left: opPath + 'add.png'}}
    ]});
    $('#update').omButtonbar({btns: [
        {label: "修改", icons: {left: opPath + 'op-edit.png'}}
    ]});
    $('#bindGroup').omButtonbar({btns: [
        {label: "绑定设备和分组", icons: {left: opPath + 'op-edit.png'}}
    ]});
    $('#unbindGroup').omButtonbar({btns: [
        {label: "解绑设备和分组", icons: {left: opPath + 'remove.png'}}
    ]});//added by joyce on 2014-6-9
    $('#query').omButtonbar({btns: [
        {label: "查询", icons: {left: opPath + 'search.png'}}
    ]});
    $('#queryInDeviceGroupForm').omButtonbar({btns: [
        {label: "查询", icons: {left: opPath + 'search.png'}}
    ]});
    $('#serviceTypeIpt').omCombo({dataSource: 'service_collect_type.json', editable: false, listMaxHeight: 150, width: 100, value: ''});
    $('#device_list_of_group').omButtonbar({btns: [
        {label: "关联设备", icons: {left: opPath + 'op-btn-icon.png'}}
    ]});
    $('#deviceQuery').omButtonbar({btns: [
        {label: "查询", icons: {left: opPath + 'search.png'}}
    ]});
    $('#delDevice').omButtonbar({btns: [
        {label: "移除", icons: {left: opPath + 'remove.png'}}
    ]});

    var dialogInfo = $("#dialog-form3").omDialog({
        width: 400,
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

    $('#grid').omGrid({
        dataSource: 'find_service_collect_list.json',
        width: '100%',
        height: rFrameHeight,
        singleSelect: true,
        limit: limit,
        colModel: [
            {header: '<b>服务集合类型</b>', name: 'serviceType', align: 'center', width: 70},
            {header: '<b>服务集合描述</b>', name: 'description', align: 'center', width: 100},
            {header: '<b>设备分组</b>', name: 'deviceGroupIds', align: 'center', width: 100},
//            {header: '<b>设备编号</b>', name: 'ystenIds', align: 'center', width: 230},
            {header: '<b>创建时间</b>', name: 'createDate', align: 'center', width: 120},
            {header: '<b>更新时间</b>', name: 'updateDate', align: 'center', width: 120}
        ],
        onRowClick: function (index, rowData, event) {
            parent.frames["serviceInfoList"].showInitData("find_service_info_list.json?serviceCollectId=" + rowData.id);
            parent.serviceCollectIdGlobal = rowData.id;
        },
        onSuccess: function (data, testStatus, XMLHttpRequest, event) {
            if (typeof(data.rows) != "undefined" && data.rows != null && data.rows.length > 0) {
                parent.serviceCollectIdGlobal = data.rows[0].id;
                var isload = true;
                while (isload == true && parent.frames["serviceInfoList"].document.readyState == 'complete') {
                    parent.frames["serviceInfoList"].showInitData("find_service_info_list.json?serviceCollectId=" + parent.serviceCollectIdGlobal);
                    isload = false;
                }
            }
        }
    });

    $("#query").click(function () {
        $('#grid').omGrid({
            dataSource: 'find_service_collect_list.json?serviceType=' + $("#serviceTypeIpt").val(),
            width: '100%',
            height: rFrameHeight,
            singleSelect: true,
            limit: limit,
            colModel: [
                {header: '<b>服务集合类型</b>', name: 'serviceType', align: 'center', width: 100},
                {header: '<b>服务集合描述</b>', name: 'description', align: 'center', width: 150},
                {header: '<b>设备分组</b>', name: 'deviceGroupIds', align: 'center', width: 120},
                {header: '<b>设备编号</b>', name: 'ystenIds', align: 'center', width: 230},
                {header: '<b>创建时间</b>', name: 'createDate', align: 'center', width: 120},
                {header: '<b>更新时间</b>', name: 'updateDate', align: 'center', width: 120}
            ],
            onRowClick: function (index, rowData, event) {
                parent.frames["serviceInfoList"].showInitData("find_service_info_list.json?serviceCollectId=" + rowData.id);
                parent.serviceCollectIdGlobal = rowData.id;
            },
            onSuccess: function (data, testStatus, XMLHttpRequest, event) {
                if (typeof(data.rows) != "undefined" && data.rows != null && data.rows.length > 0) {
                    parent.serviceCollectIdGlobal = data.rows[0].id;
                    parent.frames["serviceInfoList"].showInitData("find_service_info_list.json?serviceCollectId=" + parent.serviceCollectIdGlobal);
                }
            }
        });
    });

    /*********************************显示设备列表 start*************************************************************/
    $('#device_list_of_group').bind('click', function (e) {
        var selections = $('#grid').omGrid('getSelections', true);
        if (selections.length == 0 || selections.length > 1) {
            $.omMessageBox.alert({
                type: 'alert',
                title: '温馨提示',
                content: '显示操作至少且只能选择一行记录！',
            });
            return false;
        }
        showProgramDialog("关联的设备列表");
        $('#gridDeviceList').omGrid({
            dataSource: 'device_list_by_groupId.json?deviceGroupId=' + selections[0].id+'&tableName=bss_service_collect_device_group_map&character=service_collect_id',
            width: '100%',
            height: rFrameHeight - 100,
            limit: limit,
            singleSelect:false,
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
    $('#deviceQuery').bind('click', function (e) {
        var selections = $('#grid').omGrid('getSelections', true);
        var deviceCode = $('#deviceCode').val();
        var ystenId = $('#ystenIds').val();
        var mac = $('#macs').val();
        var sno = $('#snos').val();
        $('#gridDeviceList').omGrid("setData", 'device_list_by_groupId.json?deviceGroupId=' + selections[0].id + '&tableName=bss_service_collect_device_group_map&character=service_collect_id&ystenId=' + encodeURIComponent(ystenId) + '&deviceCode=' + encodeURIComponent(deviceCode) + '&mac=' + encodeURIComponent(mac) + '&sno=' + encodeURIComponent(sno));
    });


    $('#delDevice').click(function(){
        $('#deleteType').omCombo({dataSource : [
            {text:'移除选中设备',value:'0'},
            {text:'移除全部设备',value:'1'}
        ],
            value:'0',
            width:180,
            listMaxHeight:160,
        });
        var dialog_del_device = $("#del-dialog-form").omDialog({
            width: 300,
            autoOpen : false,
            modal : true,
            resizable : false,
            draggable : false,
            buttons : {
                "提交" : function(){
                    var selections_device = $('#gridDeviceList').omGrid('getSelections', true);
                    if($('#deleteType').omCombo('value') == 0){
                        if (selections_device.length == 0) {
                            $.omMessageBox.alert({
                                type:'alert',
                                title:'温馨提示',
                                content:'移除选中至少选择一行记录！',
                            });
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
                    var toDeleteRecordID = "";
                    for(var i=0;i<selections_device.length;i++){
                        if(i != selections_device.length - 1){
                            toDeleteRecordID  += selections_device[i].ystenId+",";
                        }else{
                            toDeleteRecordID  += selections_device[i].ystenId;
                        }
                    }
                    if ($('#deleteType').omCombo('value') == 0){

                        $.ajax({
                            type: "POST",
                            url: "delete_device_by_bussiness.json?delYstenIds="+toDeleteRecordID+"&packageId="+selections[0].id+'&tableName=bss_service_collect_device_group_map&character=service_collect_id&type=DEVICE&device=ysten_id',
                            success: function(result){
                                if (result) {
                                    $('#gridDeviceList').omGrid('reload');
                                    $('#gridDeviceList').omGrid('saveChanges');
                                    YST_APP.show_message("移除成功！", 'success');
                                } else {
                                    YST_APP.show_message(result, 'error');
                                }
                            }
                        });
//                        location.href="delete_device_by_deviceGroupId.json?ystenIds="+encodeURIComponent(toDeleteRecordID.toString())+"&deviceGroupId="+selections[0].id;
                    }
                    else if($('#deleteType').omCombo('value') == 1){
//                        var random = Math.random();
                        $.ajax({
                            type: "POST",
                            url: "delete_device_by_bussiness.json?delYstenIds="+toDeleteRecordID+"&packageId="+selections[0].id+'&tableName=bss_service_collect_device_group_map&character=service_collect_id&type=DEVICE&device=ysten_id',
                            success: function(result){

                                if (result) {
                                    $('#gridDeviceList').omGrid('reload');
                                    $('#gridDeviceList').omGrid('saveChanges');
                                    YST_APP.show_message("移除成功！", 'success');

                                } else {
                                    YST_APP.show_message(result, 'error');
                                }
                            }
                        });
                    }}}});
                    $("#del-dialog-form").omDialog("close");
                },
                "取消" : function() {
                    $("#del-dialog-form").omDialog("close");
                }
            },onClose:function(){
                $('#delDeviceMyform').validate().resetForm();
                $("#del-dialog-form").omDialog("close");
            }

        });
        dialog_del_device.omDialog("option", "title", "设备移除");
        dialog_del_device.omDialog("open");
    });


    function showProgramDialog(title) {
        var dialogDeviceList = $("#dialog-form-device-list").omDialog({
            width: 1000, height: 550, autoOpen: false, resizable: false,
            draggable: false, modal: true
        });
        dialogDeviceList.omDialog("option", "title", title);
        dialogDeviceList.omDialog("open");
    }

    /*********************************显示设备列表 end*************************************************************/

    $('#add').bind('click', function (e) {
        $('#serviceTypeInForm').omCombo({dataSource: 'service_collect_sub_type.json', editable: false, listMaxHeight: 150, width: 120, value: 'NORMAL', disabled: false});
        //$('#deviceGroupIds3').omCombo({dataSource : 'can_bind_boot_group_device.json',editable:false,width:182,multi : true, value:' ' });//deleted by joyce on 2014-6-9
        var dialog = $("#dialog-form").omDialog({
            width: 350, height: 200, autoOpen: false, modal: true, resizable: false, draggable: false,
            buttons: {
                "提交": function () {
                    submitData = {
                        serviceType: $("#serviceTypeInForm").val(),
                        description: $("#descriptionInForm").val(),
                    };
                    if (validator.form()) {
                        var sr = $("#showResult1").html();
                        var sr1 = $("#showResult2").html();
                        if ((sr == '' || sr == '可用!') && (sr1 == '' || sr1 == '可用!')) {
                            $.post("add_service_collect.json", submitData, function (result) {
                            	$('#grid').omGrid('reload');
                                if (result.indexOf('成功') > 0) {
                                    $.omMessageTip.show({title: tip, content: result, type: "success", timeout: time});
                                } else {
                                    $.omMessageTip.show({title: tip, content: result, type: "error", timeout: time});
                                }
                                //validator.resetForm();
                            });
                            $("#showResult2").text("");
                            $("#dialog-form").omDialog("close");
                            $("#showResult1").text("");
                        }
                        return false;
                    }
                    ;
                },
                "取消": function () {
                    //validator.resetForm();
                    $("#showResult1").text("");
                    $("#showResult2").text("");
                    $("#dialog-form").omDialog("close");
                }
            },
            onClose: function () {
                $("#showResult2").text("");
                validator.resetForm();
                $("#showResult1").text("");
            }
        });
        dialog.omDialog("option", "title", "新增服务集信息");
        dialog.omDialog("open");
    });

    $('#update').bind('click', function (e) {
        validator.resetForm();
        $("#showResult1").html("");
        $("#showResult2").html("");
        var selections = $('#grid').omGrid('getSelections', true);
        if (selections.length == 0 || selections.length > 1) {
            $.omMessageBox.alert({
                type: 'alert',
                title: '温馨提示',
                content: '修改操作至少且只能选择一行记录！',
            });
            return false;
        }

        $.post("get_service_collect_by_id.json", {id: selections[0].id}, function (result) {
//            validator.resetForm();
            $('#serviceTypeInForm').omCombo({dataSource: 'service_collect_sub_type.json', editable: false, listMaxHeight: 150, width: 120, value: result['serviceType'],disabled: true});
            $('#descriptionInForm').val(result['description']);
            $("#id").val(result['id']);
        });
        var dialog = $("#dialog-form").omDialog({
            width: 350, height: 200, autoOpen: false, modal: true, resizable: false, draggable: false,
            buttons: {
                "提交": function () {
                    submitData = {
                        id: $("#id").val(),
                        serviceType: $("#serviceTypeInForm").val(),
                        description: $("#descriptionInForm").val(),
                    };
                    if (validator.form()) {
                        var sr = $("#showResult1").html();
                        var sr1 = $("#showResult2").html();
                        if ((sr == '' || sr == '可用!') && (sr1 == '' || sr1 == '可用!')) {
                            $.post("update_service_collect.json", submitData, function (result) {
                                $('#grid').omGrid('reload');
                                if (result.indexOf('成功') > 0) {
                                    $.omMessageTip.show({title: tip, content: result, type: "success", timeout: time});
                                } else {
                                    $.omMessageTip.show({title: tip, content: result, type: "error", timeout: time});
                                }
                            });
                            $("#showResult1").text("");
                            $("#showResult2").text("");
                            $("#dialog-form").omDialog("close");
                            $('#grid').omGrid('reload');
                        }
                        return false;
                    }
                    ;
                },
                "取消": function () {
                    //validator.resetForm();
                    $("#showResult1").text("");
                    $("#showResult2").text("");
                    $("#dialog-form").omDialog("close");
                }
            },
            onClose: function () {
                $("#showResult1").text("");
                $("#showResult2").text("");
                validator.resetForm();
            }
        });
        dialog.omDialog("option", "title", "修改服务集信息");
        dialog.omDialog("open");
    });

    //unbindGroup begin
    $('#unbindGroup').bind('click', function (e) {
        var selections = $('#grid').omGrid('getSelections', true);
        //check if just select one record begin
        if (selections.length == 0 || selections.length > 1) {
            $.omMessageBox.alert({
                type: 'alert',
                title: '温馨提示',
                content: '修改操作至少且只能选择一行记录！',
            });
            return false;
        }
        //if the record is default,it can't do the bind and unbind operations
        if ('默认' == selections[0].serviceType) {
            $.omMessageBox.alert({
                type: 'alert',
                title: '温馨提示',
                content: '默认的服务信息不能做解绑操作！',
            });
            return false;
        }

        //popup one confirm dialog
        $.omMessageBox.confirm({
            title: '确认解绑',
            content: '解除绑定后数据将无法恢复，你确定要执行该操作吗？',
            onClose: function (v) {
                if (v == true) {
                    console.log(selections[0].id);
                    $.post('delete_service_collect_device_group_map.json', {serviceCollectId: selections[0].id}, function (result) {
                        $('#grid').omGrid('reload');
                        if (result == 'success') {
                            $.omMessageTip.show({title: tip, content: "解除绑定成功！", timeout: time, type: 'success'});
                        } else {
                            $.omMessageTip.show({title: tip, content: "解除绑定成功失败！", timeout: time, type: 'error'});
                        }
                    });
                }
            }
        });
    });
    //unbindGroup end

    $('#bindGroup').bind('click', function (e) {
        var selections = $('#grid').omGrid('getSelections', true);
        $('#deviceGroupIds3').omCombo('setData', []);
        if (selections.length == 0 || selections.length > 1) {
            $.omMessageBox.alert({
                type: 'alert',
                title: '温馨提示',
                content: '修改操作至少且只能选择一行记录！',
            });
            return false;
        }
        //if the record is default,it can't do the bind and unbind operations
        if ('默认' == selections[0].serviceType) {
            $.omMessageBox.alert({
                type: 'alert',
                title: '温馨提示',
                content: '默认的服务信息不能做绑定操作！'
            });
            return false;
        }

        //added by joyce to get deviceGroupIds and deviceCodes on 2014-6-9 begin
        $.get("get_districtCodes_by_Id.json?", {id: selections[0].id, tableName: "bss_service_collect_device_group_map", character: "service_collect_id"}, function (result) {
            $('#districtCode').omCombo({dataSource: 'area.json', editable: false, width: 150, listMaxHeight: 160, value: result || "", multi: true,
                onValueChange: function (target, newValue, oldValue) {
                    $.get("get_device_group_by_id.json", {id: selections[0].id}, function (result) {
                        $('#deviceGroupIds3').omCombo({dataSource: 'device_group_list_by_district.json?type=BOOTSTRAP&districtCode=' + newValue + '&id=' + selections[0].id + '&tableName=bss_service_collect_device_group_map&character=service_collect_id', editable: false, width: 150, multi: true, value: result || ''});
                    });
                }
            });
        });
        $.get("get_device_group_by_id.json", {id: selections[0].id}, function (result) {
            $('#deviceGroupIds3').omCombo({dataSource: 'device_group_list_by_type.json?type=BOOTSTRAP', editable: false, width: 150, multi: true, value: result || ''});
        });
        var dialog = $("#dialog-device-group-form").omDialog({
            width: 350, height: 200, autoOpen: false, modal: true, resizable: false, draggable: false,
            buttons: {
                "提交": function () {
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
                        serviceCollectId: selections[0].id,
                        description: selections[0].description,
                        deviceGroupIds: $("#deviceGroupIds3").val()
                    };

                    if (!YST_APP.checkFileText($("#textfield").val())) {
                        return false;
                    }
                    $.ajaxFileUpload({
                        url: 'add_service_collect_device_group_map.json',
                        secureuri: false,
                        fileElementId: 'deviceCodes3',
                        data: submitData,
                        dataType: 'JSON',
                        success: function (result) {
                            $('#grid').omGrid('reload');
                            $('#message').html(result);
                            showInfoDialog('绑定结果');
                            $("#dialog-device-group-form").omDialog("close");
                        }
                    });
                    $("#dialog-device-group-form").omDialog("close");
                },
                "取消": function () {
                    $("#dialog-device-group-form").omDialog("close");
                }
            },
            onClose: function () {
                $("#deviceGroupForm").resetForm();
            }
        });

        dialog.omDialog("option", "title", "可绑定组信息");
        dialog.omDialog("open");
    });
    $('#center-tab').omTabs({height: "33", border: false});
});
function checkExists() {
    if ($.trim($("#serviceTypeInForm").omCombo('value')) != "") {
        var par = new Object();
        par['isDefault'] = $("#serviceTypeInForm").omCombo('value');
        par['id'] = $("#id").val();
        $.ajax({
            type: "post",
            url: "is_default_already_exists.shtml?s=" + Math.random(),
            dataType: "html",
            data: par,
            success: function (msg) {
                //alert(msg);
                $("#showResult1").html(msg);
            }
        });
    } else if ($.trim($("#serviceTypeInForm").val()) == "" && $("#showResult1").html() != "") {
        $("#showResult1").html("");
    }
}
function checkDescriptionExists() {
    if ($.trim($("#descriptionInForm").val()) != "") {
        var par = new Object();
        par['description'] = $("#descriptionInForm").val();
        par['id'] = $("#id").val();
        $.ajax({
            type: "post",
            url: "service_description_exists.shtml?s=" + Math.random(),
            dataType: "html",
            data: par,
            success: function (msg) {
                //alert(msg);
                $("#showResult2").html(msg);
            }
        });
    } else if ($.trim($("#descriptionInForm").val()) == "" && $("#showResult2").html() != "") {
        $("#showResult2").html("");
    }
}
</script>
</head>

<body>
<div id="center-tab">
    <ul>
        <li><a href="#tab1">服务集信息列表:</a></li>
    </ul>
</div>
<table>
    <tbody>
    <tr>
        <c:if test="${sessionScope.add_service_collect_list != null }">
            <td>
                <div id="add"/>
            </td>
        </c:if>
        <c:if test="${sessionScope.update_service_collect_list != null }">
            <td>
                <div id="update"/>
            </td>
        </c:if>
        <!--  deleted by joyce on 2014-6-6-->
        <c:if test="${sessionScope.bind_group_service_collect_list != null }">
            <td>
                <div id="bindGroup"/>
            </td>
        </c:if>
        <c:if test="${sessionScope.unbind_group_service_collect_list != null }">
            <td>
                <div id="unbindGroup"/>
            </td>
        </c:if>
    </tr>
    </tbody>
</table>
<table>
    <tr>
        <td>服务集合类型：</td>
        <td><input type="text" id="serviceTypeIpt" name="serviceTypeIpt"/></td>
        <td>
            <div id="query"/>
        </td>
        <c:if test="${sessionScope.service_collect_device_list != null }">
            <td>
                <div id="device_list_of_group"/>
            </td>
        </c:if>
    </tr>
</table>

<table id="grid"></table>

<div id="dialog-form" style="display:none">
    <form id="myform" method="post">
        <input type="hidden" value="" name="id" id="id"/>
        <table>
            <tr>
                <td><span class="color_red">*</span>类型：</td>
                <td><input id="serviceTypeInForm" name="serviceTypeInForm" onblur="checkExists()"/></td>
                <td style="width: 150px;"><span></span>

                    <div id="showResult1" class="color_red" style="display: inline;"/>
                </td>
            </tr>
            <tr>
                <td><span class="color_red">*</span>描述：</td>
                <td>
                    <textarea id="descriptionInForm" name="descriptionInForm" cols="17" rows="4"
                              style="border:1px solid #86A3C4;" onblur="checkDescriptionExists()"></textarea></td>
                <td style="width: 150px;"><span></span>

                    <div id="showResult2" class="color_red" style="display: inline;"/>
                </td>
            </tr>
        </table>
    </form>
</div>
<!--  commented by joyce on 2014-6-5-->
<div id="dialog-device-group-form" style="display:none">
    <form id="deviceGroupForm" method="post">
        <table>
            <tr>
                <td align="right">设备编号：</td>
                <td><input type='text' name='textfield' id='textfield' class='file_txt' style='width:75px;'/>
                    <input type='button' class='file_btn' value='浏览...'/>
                    <input type="file" name="deviceCodes3" class="file_import" id="deviceCodes3" size="40" onchange="document.getElementById('textfield').value=this.value"/>
                </td>
            </tr>
            <tr>
                <td style="width: 100px; text-align: right;">区域：</td>
                <td><input name="districtCode" id="districtCode"/></td>
                <td><span></span></td>
            </tr>

            <!-- added by joyce on 2014-6-9 -->
            <tr>
                <td style="width: 100px; text-align: right;">设备分组：</td>
                <td><input id="deviceGroupIds3" name="deviceGroupIds3"/></td>
                <td><span></span></td>
            </tr>
        </table>
        <!--  <table id="device-group-grid"></table> -->
    </form>
</div>
<div id="dialog-form3" style="display: none;">
    <form id="myform3">
        <table>
            <tr>
                <td style="width: 100px; text-align: right;">绑定结果：</td>
                <td>
                    <div id="message" style="overflow-y: auto;"></div>
                </td>
            </tr>
        </table>
    </form>
</div>
<div id="dialog-form-device-list" style="display: none;">
    <form id="device-form">
        <table>
            <tr>
                <td>设备编号：</td>
                <td><input  name="ystenIds" id="ystenIds" class="query-title-input"  style="width: 85px; height: 20px; border: 1px solid #86A3C4;"/></td>
                <td>设备序列号：</td>
                <td><input name="snos" id="snos" class="query-title-input" style="width: 85px; height: 20px; border: 1px solid #86A3C4;"/></td>
                <td>MAC地址：</td>
                <td><input  name="macs" id="macs" class="query-title-input" style="width: 85px; height: 20px; border: 1px solid #86A3C4;"/></td>

                <td clospan='4'><div id="deviceQuery"></div></td>
                <td><div id="delDevice"></div></td>
            </tr>
        </table>
        <table id="gridDeviceList"></table>
    </form>
</div>

<div id="del-dialog-form" style="display: none;">
    <form id="delDeviceMyform">
        <table>
            <tr>
                <td>移除方式：</td>
                <td>
                    <input name="deleteType" id="deleteType"/>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
