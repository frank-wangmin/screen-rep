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
	$('#center-tab').omTabs({height: "33", border: false});
    $('#grid').omGrid({
        dataSource: 'boot_animation_list.json',
        width: '100%',
        height: rFrameHeight,
        singleSelect: false,
        limit: limit,
        colModel: [
            {header: '<b>名称</b>', name: 'name', align: 'center', width: 150},
            {header: '<b>地址</b>', name: 'url', align: 'center', width: 300},
            {header: '<b>MD5值</b>', name: 'md5', align: 'center', width: 250},
            {header: '<b>设备分组</b>', name: 'deviceGroupIds', align: 'center', width: 100},
//            {header: '<b>设备编号</b>', name: 'deviceCodes', align: 'center', width: 230},
            {header: '<b>用户分组</b>', name: 'userGroupIds', align: 'center', width: 100},
//            {header: '<b>用户编号</b>', name: 'userIds', align: 'center', width: 200},
            {header: '<b>状态</b>', name: 'state', align: 'center', width: 50, renderer: function (value, rowData, rowIndex) {
                return "USEABLE" === value ? "可用" : "不可用";
            }},
            {header: '<b>是否默认</b>', name: 'isDefault', align: 'center', width: 70, renderer: function (value, rowData, rowIndex) {
                return 1 === value ? "是" : "否";
            }},
            {header: '<b>创建时间</b>', name: 'createDate', align: 'center', width: 120},
            {header: '<b>更新时间</b>', name: 'updateDate', align: 'center', width: 120}
        ],
        rowDetailsProvider: function (rowData, rowIndex) {
            var state = "";
            if (rowData.state == 'USEABLE') {
                state = "可用";
            } else {
                state = "不可用";
            }
            var isDefault = "";
            if (rowData.isDefault == 1) {
                isDefault = "是";
            } else {
                isDefault = "否";
            }
            return '第' + rowIndex + '行, 主键ID=' + rowData.id + ", 名称=" + rowData.name + ", 地址=" + rowData.url + ", MD5值=" + rowData.md5 + "</br>创建时间=" + rowData.createDate + ', 更新时间=' + rowData.updateDate
                    + ', 状态=' + state + ', 是否默认=' + isDefault + "</br>设备分组=" + rowData.deviceGroupIds;
        }
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
    $('#device_list_of_group').omButtonbar({btns: [
        {label: "关联设备", icons: {left: opPath + 'op-btn-icon.png'}}
    ]});
    $('#customer_list_of_group').omButtonbar({btns: [{label: "关联用户", icons: {left: opPath + 'op-btn-icon.png'}}]});
    $('#deviceQuery').omButtonbar({btns: [
        {label: "查询", icons: {left: opPath + 'search.png'}}
    ]});
    $('#queryUser').omButtonbar({btns: [{label: "查询", icons: {left: opPath + 'search.png'}}]});
    $('#query').bind('click', function (e) {
        var name = $('#name1').val();
        $('#grid').omGrid("setData", 'boot_animation_list.json?name=' + encodeURIComponent(name));
    });
    $('#delDevice').omButtonbar({btns: [
        {label: "移除", icons: {left: opPath + 'remove.png'}}
    ]});
    $('#delUser').omButtonbar({btns: [
        {label: "移除", icons: {left: opPath + 'remove.png'}}
    ]});

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

    var dialog = $("#dialog-form").omDialog({
        width: 530, autoOpen: false,
        modal: true, resizable: false,
        draggable: false,
        buttons: {
            "提交": function () {
                validator.form();
                checkExists();
                var sr = $("#showResult").html();
                var info = $("#showResult1").html();
                if ((sr == '' || sr == '可用!') && (info == '' || info == '可用!')) {
                    submitDialog();
                }
                return false;
            },
            "取消": function () {
                validator.resetForm();
                $("#showResult").text("");
                $("#showResult1").text("");
                $("#dialog-form").omDialog("close");
            }
        }, onClose: function () {
            $("#showResult").text("");
            $("#showResult1").text("");
        }
    });

    var showDialog = function (title, rowData) {
        validator.resetForm();
        rowData = rowData || {};
        dialog.omDialog("option", "title", title);
        dialog.omDialog("open");
    };

    $('#bindUser').bind('click',function(){
        var selections = $('#grid').omGrid('getSelections', true);
        $('#userGroupIds3').omCombo('setData', []);
        if (selections.length == 0 || selections.length > 1) {
            $.omMessageBox.alert({
                type: 'alert',
                title: '温馨提示',
                content: '绑定操作至少且只能选择一行记录！',
            });
            return false;
        }

        //if the record is default, it can't do the bind operation
        if (1 == selections[0].isDefault) {
            $.omMessageBox.alert({
                type: 'alert',
                title: '温馨提示',
                content: '默认的开机广告不能做绑定操作！',
            });
            return false;
        }
        if (selections[0].state != 'USEABLE') {
            $.omMessageBox.alert({
                type: 'alert',
                title: '温馨提示',
                content: '该开机广告为不可用状态，不能做绑定操作！',
            });
            return false;
        }

        $.get("get_areaIds_by_bussId.json", {id: selections[0].id, tableName: "bss_animation_user_map", character: "boot_animation_id"}, function (result) {
            $('#areaId').omCombo({dataSource: 'area.json', editable: false, width: 184, listMaxHeight: 160, value: result || '', multi: true,
                onValueChange: function (target, newValue, oldValue) {
                    $.get("get_animation_user.json", {id: selections[0].id, type: "GROUP"}, function (result) {
                        $('#userGroupIds3').omCombo({dataSource: 'user_group_list_by_type_area.json?type=ANIMATION&areaId=' + newValue + '&id=' + selections[0].id + '&tableName=bss_animation_user_map&character=boot_animation_id',
                            editable: false, width: 184, multi: true, value: result || ''});
                    });
                }
            });
        });

        $.get("get_animation_user.json", {id: selections[0].id, type: "GROUP"}, function (result) {
            $('#userGroupIds3').omCombo({dataSource: 'user_group_list_by_type.json?type=ANIMATION', editable: false, width: 184, multi: true, value: result || ''});
        });

        var dialog = $("#dialog-user-group").omDialog({
            width: 500, height: 200, autoOpen: false, modal: true, resizable: false, draggable: false,
            buttons: {
                "提交": function () {
                    if (!$('#areaId').omCombo('value')) {
                        $.omMessageBox.alert({
                            type: 'alert',
                            title: '温馨提示',
                            content: '请选择用户所在区域！'
                        });
                        return false;
                    }
                    if (!YST_APP.checkFileText($("#textfield1").val())) {
                        return false;
                    }
                    var submitData = {
                        areaIds: $('#areaId').omCombo('value'),
                        ids: selections[0].id,
                        userGroupIds: $("#userGroupIds3").val(),
                    };


                    $.ajaxFileUpload({
                        url: 'animation_bind_user.json',
                        secureuri: false,
                        fileElementId: 'userIds3',
                        data: submitData,
                        dataType: 'JSON',
                        success: function (result) {
                            $('#grid').omGrid('reload');
                            $('#message').html(result);
                            showInfoDialog('绑定结果');
                        }
                    });
                    $("#dialog-user-group").omDialog("close");
                },
                "取消": function () {
                    $("#dialog-user-group").omDialog("close");
                }
            },
            onClose: function () {
                $('#user-group-form').validate().resetForm();
            }
        });
        dialog.omDialog("option", "title", "绑定用户与用户分组");
        dialog.omDialog("open");
    });



    //updated by joyce on 2014-6-12
    $('#bind').click(function () {
        var selections = $('#grid').omGrid('getSelections', true);
        $('#deviceGroupIds').omCombo('setData', []);
        if (selections.length == 0 || selections.length > 1) {
            $.omMessageBox.alert({
                type: 'alert',
                title: '温馨提示',
                content: '绑定操作至少且只能选择一行记录！',
            });
            return false;
        }

        //if the record is default, it can't do the bind operation
        if (1 == selections[0].isDefault) {
            $.omMessageBox.alert({
                type: 'alert',
                title: '温馨提示',
                content: '默认的开机广告不能做绑定操作！',
            });
            return false;
        }
        if (selections[0].state != 'USEABLE') {
            $.omMessageBox.alert({
                type: 'alert',
                title: '温馨提示',
                content: '该开机广告为不可用状态，不能做绑定操作！',
            });
            return false;
        }

        //set the value of deviceGroup,deviceCodes,userGroup and userId
        $.get("get_districtCodes_by_Id.json", {id: selections[0].id, tableName: "bss_animation_device_map", character: "boot_animation_id"}, function (result) {
            $('#districtCode').omCombo({dataSource: 'area.json', editable: false, width: 184, listMaxHeight: 160, value: result || "", multi: true,
                onValueChange: function (target, newValue, oldValue) {
                    $.get("get_animation_device.json", {id: selections[0].id, type: "GROUP"}, function (result) {
                        $('#deviceGroupIds').omCombo({dataSource: 'device_group_list_by_district.json?type=ANIMATION&districtCode=' + newValue + '&id=' + selections[0].id + '&tableName=bss_animation_device_map&character=boot_animation_id', editable: false, width: 184, multi: true, value: result || ''});
                    });
                }
            });
        });
        $.get("get_animation_device.json", {id: selections[0].id, type: "GROUP"}, function (result) {
            $('#deviceGroupIds').omCombo({dataSource: 'device_group_list_by_type.json?type=ANIMATION', editable: false, width: 184, multi: true, value: result || ''});
        });
        $.get("get_animation_device.json", {id: selections[0].id, type: "DEVICE"}, function (msg) {
            $('#deviceCodes').val(msg);
        });



        var dialog = $("#dialog-bind-form").omDialog({
            width: 530,
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
                            content: '请选择设备所在区域！',
                        });
                        return false;
                    }
                    var submitData = {
                        areaIds: $('#districtCode').omCombo('value'),
                        deviceGroupIds: $("#deviceGroupIds").omCombo('value'),
                        userGroupIds: "",
                        userIds: "",
                        ids: selections[0].id
                    };

                    if (!YST_APP.checkFileText($("#textfield").val())) {
                        return false;
                    }
                    $.ajaxFileUpload({
                        url: 'animation_bind.json',
                        secureuri: false,
                        fileElementId: 'deviceCodes',
                        data: submitData,
                        dataType: 'JSON',
                        success: function (result) {
                            $('#grid').omGrid('reload');
                            $('#message').html(result);
                            showInfoDialog('绑定结果');
                            $("#dialog-bind-form").omDialog("close");
                        }
                    });
                    $("#dialog-group").omDialog("close");
                    return false;
                },
                "取消": function () {
                    $("#dialog-bind-form").omDialog("close");
                }
            }, onClose: function () {
                $('#myform-bind').validate().resetForm();
            }
        });
        dialog.omDialog("option", "title", "绑定开机广告");
        dialog.omDialog("open");
    });

    $('#unbindUser').bind('click', function (e) {
        var selections = $('#grid').omGrid('getSelections', true);
        if (selections.length == 0) {
            $.omMessageBox.alert({
                type: 'alert',
                title: '温馨提示',
                content: '解绑操作至少选择一行记录！',
            });
            return false;
        }

        //if the record is default, it can't do the unbind operation
        if (1 == selections[0].isDefault) {
            $.omMessageBox.alert({
                type: 'alert',
                title: '温馨提示',
                content: '默认的开机广告不能做解绑操作！',
            });
            return false;
        }
        $.omMessageBox.confirm({
            title: '确认解绑',
            content: '解绑后开机广告的绑定关系将删除且无法恢复，你确定要执行该操作吗？',
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
                    $.post('boot_animation_map_delete.json', {ids: toDeleteRecordID.toString(),isUser:"true"}, function (result) {
                        $('#grid').omGrid('reload');
                        if (result == 'success') {
                            $.omMessageTip.show({title: tip, content: "解绑开机广告关系成功！", timeout: time, type: 'success'});
                        } else {
                            $.omMessageTip.show({title: tip, content: "解绑开机广告关系失败！", timeout: time, type: 'error'});
                        }
                    });
                }
            }
        });
    });


    $('#unbind').bind('click', function (e) {
        var selections = $('#grid').omGrid('getSelections', true);
        if (selections.length == 0) {
            $.omMessageBox.alert({
                type: 'alert',
                title: '温馨提示',
                content: '解绑操作至少选择一行记录！',
            });
            return false;
        }

        //if the record is default, it can't do the unbind operation
        if (1 == selections[0].isDefault) {
            $.omMessageBox.alert({
                type: 'alert',
                title: '温馨提示',
                content: '默认的开机广告不能做解绑操作！',
            });
            return false;
        }
        $.omMessageBox.confirm({
            title: '确认解绑',
            content: '解绑后开机广告的绑定关系将删除且无法恢复，你确定要执行该操作吗？',
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
                    $.post('boot_animation_map_delete.json', {ids: toDeleteRecordID.toString()}, function (result) {
                        $('#grid').omGrid('reload');
                        if (result == 'success') {
                            $.omMessageTip.show({title: tip, content: "解绑开机广告关系成功！", timeout: time, type: 'success'});
                        } else {
                            $.omMessageTip.show({title: tip, content: "解绑开机广告关系失败！", timeout: time, type: 'error'});
                        }
                    });
                }
            }
        });
    });
    var isAdd = true;
    $('#create').bind('click', function () {
        validator.resetForm();
        isAdd = true;
        $('#isDefault').omCombo({dataSource: [
            {text: '是', value: '1'},
            {text: '否', value: '0'}
        ], editable: false, width: 182, listMaxHeight: 100, disabled: false});
        $('#state').omCombo({dataSource: [
            {text: '可用', value: 'USEABLE'},
            {text: '不可用', value: 'UNUSEABLE'}
        ], editable: false, width: 182, listMaxHeight: 100});
        //$('#deviceGroupIds').omCombo({dataSource : 'device_group_list_by_type.json?type=ANIMATION',editable:false,width:182,multi : true , value:' '});
        //$('#userGroupIds').omCombo({dataSource : 'user_group_list_by_type.json?type=ANIMATION',editable:false,width:182,multi : true , value:' '});
        showDialog('新增开机广告信息');
    });

    $('#update').bind('click', function () {
        var selections = $('#grid').omGrid('getSelections', true);
        $("#showResult1").html("");
        $("#showResult").html("");
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
            url: "boot_animation_to_update.json?id=" + selections[0].id,
            dataType: "json",
            success: function (msg) {
                $("#id").val(msg['id']);
                $("#name").val(msg['name']);
                $("#url").val(msg['url']);
                $("#md5").val(msg['md5']);
                $('#isDefault').omCombo({dataSource: [
                    {text: '是', value: '1'},
                    {text: '否', value: '0'}
                ], editable: false, width: 182, listMaxHeight: 100, value: msg['isDefault'], disabled: true});
                $('#state').omCombo({dataSource: [
                    {text: '可用', value: 'USEABLE'},
                    {text: '不可用', value: 'UNUSEABLE'}
                ], editable: false, width: 182, listMaxHeight: 100, value: msg['state']});
            }
        });
        showDialog('修改开机广告信息', selections[0]);
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
            dataSource: 'device_list_by_groupId.json?deviceGroupId=' + selections[0].id+'&tableName=bss_animation_device_map&character=boot_animation_id',
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
        $('#gridDeviceList').omGrid("setData", 'device_list_by_groupId.json?deviceGroupId=' + selections[0].id + '&tableName=bss_animation_device_map&character=boot_animation_id&ystenId=' + encodeURIComponent(ystenId) + '&deviceCode=' + encodeURIComponent(deviceCode) + '&mac=' + encodeURIComponent(mac) + '&sno=' + encodeURIComponent(sno));
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
                            url: "delete_device_by_bussiness.json?delYstenIds="+toDeleteRecordID+"&packageId="+selections[0].id+'&tableName=bss_animation_device_map&character=boot_animation_id&type=DEVICE&device=ysten_id',
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
                            url: "delete_device_by_bussiness.json?delYstenIds="+toDeleteRecordID+"&packageId="+selections[0].id+'&tableName=bss_animation_device_map&character=boot_animation_id&type=DEVICE&device=ysten_id',
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
                    }
                            }
                        }
                    });
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

    /*********************************显示用户列表 start*************************************************************/
    $('#customer_list_of_group').bind('click', function (e) {
        $('#region1').omCombo({dataSource : 'city.json',editable:false,listMaxHeight:150});
        var selections = $('#grid').omGrid('getSelections', true);
        if (selections.length == 0 || selections.length > 1) {
            $.omMessageBox.alert({
                type: 'alert',
                title: '温馨提示',
                content: '显示操作至少且只能选择一行记录！',
            });
            return false;
        }
        showCustomerDialog("关联的用户列表");
        $('#gridUserList').omGrid({
            dataSource: 'customer_list_by_groupId.json?userGroupId=' + selections[0].id+'&tableName=bss_animation_user_map&character=boot_animation_id',
            width: '100%',
            height: rFrameHeight - 100,
            limit: limit,
            singleSelect:false,
            colModel: [
                {header : '<b>用户编号</b>', name : 'code', align : 'center', width : 150},
                {header : '<b>用户外部编号</b>', name : 'userId', align : 'center', width : 120},
                {header : '<b>登录名</b>', name : 'loginName', align : 'center', width : 120},
                {header : '<b>真实姓名</b>', name : 'realName', align : 'center', width : 120},
                {header : '<b>用户类型</b>', name : 'customerType', align : 'center', width : 60},
                {header : '<b>用户状态</b>', name : 'state', align : 'center', width : 60},
                {header : '<b>是否锁定</b>', name : 'isLock', align : 'center', width : 60},
                {header : '<b>所属地市</b>', name : 'region', align : 'center', width : 50, renderer:function(value){
                    if(value==null || value==""){
                        return "";
                    }else{
                        return value.name;
                    }
                }
                },
                {header : '<b>创建时间</b>', name : 'createDate', align : 'center', width : 120},
                {header : '<b>服务到期时间</b>', name : 'serviceStop', align : 'center', width : 120},
                {header : '<b>用户电话</b>', name : 'phone', align : 'center', width : 100},
            ]
        });

    });

    $('#delUser').click(function(){
        $('#deleteType').omCombo({dataSource : [
            {text:'移除选中用户',value:'0'},
            {text:'移除全部用户',value:'1'}
        ],
            value:'0',
            width:180,
            listMaxHeight:160,
        });
        var dialog_del_User = $("#del-dialog-form").omDialog({
            width: 300,
            autoOpen : false,
            modal : true,
            resizable : false,
            draggable : false,
            buttons : {
                "提交" : function(){
                    var selections_user = $('#gridUserList').omGrid('getSelections', true);
                    if($('#deleteType').omCombo('value') == 0){
                        if (selections_user.length == 0) {
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
                        content: '移除用户记录将无法恢复，你确定要执行该操作吗？',
                        onClose: function (v) {
                            if (v == true) {
                    var selections = $('#grid').omGrid('getSelections', true);
                    var toDeleteRecordID = "";
                    for(var i=0;i<selections_user.length;i++){
                        if(i != selections_user.length - 1){
                            toDeleteRecordID  += selections_user[i].code+",";
                        }else{
                            toDeleteRecordID  += selections_user[i].code;
                        }
                    }
                    if ($('#deleteType').omCombo('value') == 0){

                        $.ajax({
                            type: "POST",
                            url: "delete_device_by_bussiness.json?delYstenIds="+toDeleteRecordID+"&packageId="+selections[0].id+'&tableName=bss_animation_user_map&character=boot_animation_id&type=USER&device=code',
                            success: function(result){
                                if (result) {
                                    $('#gridUserList').omGrid('reload');
                                    $('#gridUserList').omGrid('saveChanges');
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
                            url: "delete_device_by_bussiness.json?delYstenIds="+toDeleteRecordID+"&packageId="+selections[0].id+'&tableName=bss_animation_user_map&character=boot_animation_id&type=USER&device=code',
                            success: function(result){

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
                            }}
                    });
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


        dialog_del_User.omDialog("option", "title", "用户移除");
        dialog_del_User.omDialog("open");
    });

    function showCustomerDialog(title) {
        var dialogCustomerList = $("#dialog-form-customer-list").omDialog({
            width: 1000, height: 550, autoOpen: false, resizable: false,
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
        $('#gridUserList').omGrid("setData", 'customer_list_by_groupId.json?userGroupId=' + selections[0].id + '&userId='+encodeURIComponent(userId)+'&code='+encodeURIComponent(code)+'&phone='+encodeURIComponent(phone)+'&cRegion='+encodeURIComponent(region)+'&tableName=bss_animation_user_map&character=boot_animation_id');
    });

    /*********************************显示用户列表 end*************************************************************/

    $('#delete').bind('click', function (e) {
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
            content: '批量删除后数据将无法恢复，你确定要执行该操作吗？',
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
                    $.post('boot_animation_delete.json', {ids: toDeleteRecordID.toString()}, function (result) {
                        /**
                         if(result=='success'){
                        	$.omMessageTip.show({title: tip, content: "删除开机广告信息成功！", timeout: time,type:'success'});
                        }else{
                        	$.omMessageTip.show({title: tip, content: "删除开机广告信息失败！", timeout: time,type:'error'});
                        }*/
                        if (result.indexOf('成功') > 0) {
                            $.omMessageTip.show({title: tip, content: result, type: "success", timeout: time});
                        } else {
                            $.omMessageTip.show({title: tip, content: result, type: "error", timeout: time});
                        }
                        $('#grid').omGrid('reload');
                        $('#dialog-form').omDialog('close');
                    });
                }
            }
        });
    });

    var submitDialog = function () {
        var submitData;
        if (validator.form()) {
            if (isAdd) {
                submitData = {
                    name: $("#name").val(),
                    url: $("#url").val(),
                    md5: $("#md5").val(),
                    state: $("#state").omCombo('value'),
                    isDefault: $("#isDefault").omCombo('value'),
                    //deviceGroupIds:$("#deviceGroupIds").omCombo('value'),
                    //deviceCodes:$("#deviceCodes").val(),
                    //userGroupIds:$("#userGroupIds").omCombo('value'),
                    //userIds:$("#userIds").val()
                };
//                $.post('boot_animation_add.shtml', submitData, function (result) {
//                    if (result == "") {
//                        $.omMessageTip.show({title: tip, content: "新增开机广告信息成功！", type: "success", timeout: time});
//                    } else {
//                        $.omMessageTip.show({title: tip, content: "新增开机广告信息失败！" + result, type: "error", timeout: time});
//                    }
//                    /**
//                     if(result.indexOf('成功') > 0){
//            			$.omMessageTip.show({title: tip, content: result, type:"success" ,timeout: time});
//            		}else{
//            			$.omMessageTip.show({title: tip, content: result, type:"error" ,timeout: time});
//            		}*/
//                    $('#grid').omGrid('reload');
//                    $("#dialog-form").omDialog("close");
//                });

                $.ajaxFileUpload({
                    url: 'boot_animation_add.shtml',
                    secureuri: false,
                    fileElementId: ['fileField'],
                    data: submitData,
                    dataType: 'JSON',
                    success: function (result) {
                        $('#grid').omGrid('reload');
                        if (result == "") {
                            $.omMessageTip.show({title: tip, content: "新增开机广告信息成功！", type: "success", timeout: time});
                        } else {
                            $.omMessageTip.show({title: tip, content: "新增开机广告信息失败！" + result, type: "error", timeout: time});
                        }
                        $("#dialog-form").omDialog("close");
                    }
                });
            } else {
                submitData = {
                    id: $("#id").val(),
                    name: $("#name").val(),
                    url: $("#url").val(),
                    md5: $("#md5").val(),
                    state: $("#state").omCombo('value'),
                    isDefault: $("#isDefault").omCombo('value'),
                    /**
                     deviceGroupIds:$("#deviceGroupIds").omCombo('value'),
                     deviceCodes:$("#deviceCodes").val(),
                     userGroupIds:$("#userGroupIds").omCombo('value'),
                     userIds:$("#userIds").val()*/
                };
//                $.post('boot_animation_update.shtml', submitData, function (result) {
//                    /**
//                     if(result==""||result==null){
//                    	$.omMessageTip.show({title: tip, content: "修改开机广告信息成功！", type:"success" ,timeout: time});
//                    }else{
//                    	$.omMessageTip.show({title: tip, content: "修改开机广告信息失败！"+result, type:"error" ,timeout: time});
//                    }
//                     */
//                    if (result.indexOf('成功') > 0) {
//                        $.omMessageTip.show({title: tip, content: result, type: "success", timeout: time});
//                    } else {
//                        $.omMessageTip.show({title: tip, content: result, type: "error", timeout: time});
//                    }
//                    $('#grid').omGrid('reload');
//                    $("#dialog-form").omDialog("close");
//                });

                $.ajaxFileUpload({
                    url: 'boot_animation_update.shtml',
                    secureuri: false,
                    fileElementId: ['fileField'],
                    data: submitData,
                    dataType: 'JSON',
                    success: function (result) {
                        $('#grid').omGrid('reload');
                        if (result.indexOf('成功') > 0) {
                            $.omMessageTip.show({title: tip, content: result, type: "success", timeout: time});
                        } else {
                            $.omMessageTip.show({title: tip, content: result, type: "error", timeout: time});
                        }
                        $("#dialog-form").omDialog("close");
                    }
                });
            }
        }
    };

    var validator = $('#myform').validate({
        rules: {
            name: {required: true},
            url: {required: true, maxlength: 512},
            md5: {required: true, maxlength: 32},
            state: {required: true},
            isDefault: {required: true},

        },
        messages: {
            name: {required: "名称不能为空！"},
            url: {required: "地址不能为空！", maxlength: "地址最大512位！"},
            md5: {required: "MD5不能为空！", maxlength: "MD5值最大32位！"},
            state: {required: "状态不能为空！"},
            isDefault: {required: "是否默认不能为空！"}
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

});
function checkNameExists() {
    if ($.trim($("#name").val()) != "") {
        var par = new Object();
        par['name'] = $("#name").val();
        par['id'] = $("#id").val();
        $.ajax({
            type: "post",
            url: "animation_name_exists.shtml?s=" + Math.random(),
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
function checkExists() {
    if ($.trim($("#isDefault").omCombo('value')) != "") {
        var par = new Object();
        par['isDefault'] = $("#isDefault").omCombo('value');
        par['id'] = $("#id").val();
        $.ajax({
            type: "post",
            url: "default_already_exists.shtml?s=" + Math.random(),
            dataType: "html",
            data: par,
            success: function (msg) {
                //alert(msg);
                $("#showResult1").html(msg);
            }
        });
    } else if ($.trim($("#isDefault").val()) == "" && $("#showResult1").html() != "") {
        $("#showResult1").html("");
    }
}
</script>
</head>
<body>
<div id="center-tab">
    <ul>
        <li><a href="#tab1">开机广告信息列表:</a></li>
    </ul>
</div>
<table>
    <tr>
        <c:if test="${sessionScope.add_boot_animation != null }">
            <td>
                <div id="create"/>
            </td>
        </c:if>
        <c:if test="${sessionScope.update_boot_animation != null }">
            <td>
                <div id="update"/>
            </td>
        </c:if>
        <c:if test="${sessionScope.delete_boot_animation != null }">
            <td>
                <div id="delete"/>
            </td>
        </c:if>
        <c:if test="${sessionScope.boot_animation_bind != null }">
            <td>
                <div id="bind"/>
            </td>
        </c:if>
        <c:if test="${sessionScope.boot_animation_unbind != null }">
            <td>
                <div id="unbind"/>
            </td>
        </c:if>
        <c:if test="${sessionScope.boot_animation_bind_user != null }">
            <td>
                <div id="bindUser"/>
            </td>
        </c:if>
        <c:if test="${sessionScope.boot_animation_unbind_user != null }">
            <td>
                <div id="unbindUser"/>
            </td>
        </c:if>
        <c:if test="${sessionScope.boot_animation_device_list != null }">
            <td>
                <div id="device_list_of_group"/>
            </td>
        </c:if>

        <c:if test="${sessionScope.boot_animation_user_list != null }">
            <td>
                <div id="customer_list_of_group"/>
            </td>
        </c:if>
        <td style="text-align:center;">名称：</td>
        <td><input type="text" name="name1" id="name1" style="height: 20px;width:110px;border:1px solid #86A3C4;"/></td>
        <td>
            <div id="query"/>
        </td>
    </tr>
</table>
<table id="grid"></table>
<div id="dialog-form">
    <form id="myform">
        <input type="hidden" value="" name="id" id="id"/>
        <input type="hidden" value="${picUrl}" name="httpUrl" id="httpUrl"/>
        <table>
            <tr>
                <td style="width: 100px; text-align: right;"><span class="color_red">*</span>名称：</td>
                <td><input id="name" name="name" style="width:180px;height:20px;border:1px solid #86A3C4;"
                           onblur="checkNameExists()"/></td>
                <td><span></span>

                    <div id="showResult" class="color_red" style="display: inline;"/>
                </td>
            </tr>
            <tr>
                <td style="width: 100px; text-align: right;"><span class="color_red">*</span>地址：</td>
                <td><input id="url" name="url" style="width:180px;height:20px;border:1px solid #86A3C4;"/></td>
                <td>
                    <span></span>
                    <input type="file" name="fileField" class="file1" id="fileField" size="28"
                           onchange="document.getElementById('url').value=httpUrl.value+this.value" style="width: 63px;"/></td>
                </td>
            </tr>

            <tr>
                <td style="width: 100px; text-align: right;"><span class="color_red">*</span>MD5值：</td>
                <td><input id="md5" name="md5" style="width:180px;height:20px;border:1px solid #86A3C4;"/></td>
                <td><span></span></td>
            </tr>

            <tr>
                <td style="width: 100px; text-align: right;"><span class="color_red">*</span>状态：</td>
                <td><input id="state" name="state" style="width:162px;"/></td>
                <td><span></span></td>
            </tr>
            <tr>
                <td style="width: 100px; text-align: right;"><span class="color_red">*</span>是否默认：</td>
                <td><input id="isDefault" name="isDefault" style="width:180px;" onblur="checkExists()"/></td>
                <td><span></span>

                    <div id="showResult1" class="color_red" style="display: inline;"/>
                </td>
            </tr>
        </table>
    </form>
</div>
<div id="dialog-bind-form">
    <form id="myform-bind">
        <table>
            <tr>
                <td align="right">设备编号：</td>
                <td><input type='text' name='textfield' id='textfield'
                           class='file_txt'/>
                    <input type='button' class='file_btn' value='浏览...'/>
                    <input type="file" name="deviceCodes" class="file_import" id="deviceCodes"
                           size="40"
                           onchange="document.getElementById('textfield').value=this.value"/>
                </td>
            </tr>
            <tr>
                <td align="right">区域：</td>
                <td><input name="districtCode" id="districtCode" style="width:180px;"/></td>
                <td><span></span></td>
            </tr>
            <tr>
                <td align="right">设备分组：</td>
                <td><input id="deviceGroupIds" name="deviceGroupIds" style="width:180px;"/></td>
                <td><span></span></td>
            </tr>
        </table>
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

<div id="dialog-user-group" style="display:none">
    <form id="user-group-form" method="post">
        <table>
            <tr>
                <td align="right">用户编号：</td>
                <td><input type='text' name='textfield1' id='textfield1'
                           class='file_txt'/>
                    <input type='button' class='file_btn' value='浏览...'/>
                    <input type="file" name="userIds3" class="file_import" id="userIds3"
                           size="40"
                           onchange="document.getElementById('textfield1').value=this.value"/>
                </td>
            </tr>
            <tr>
                <td style="width: 100px; text-align: right;">区域：</td>
                <td><input name="areaId" id="areaId" style="width:205px;"/></td>
                <td ><span></span></td>
            </tr>
            <tr>
                <td align="right">用户分组：</td>
                <td><input id="userGroupIds3" name="deviceGroupIds3" style="width:205px;"/></td>
                <td><span></span></td>
            </tr>
        </table>
    </form>
</div>

<div id="dialog-form-device-list" style="display: none;">
    <form id="device-form">
        <table>
            <tr>
                <td>&nbsp;设备编号：</td>
                <td><input  name="ystenIds" id="ystenIds" class="query-title-input"/></td>
                <td>&nbsp;设备序列号：</td>
                <td><input name="snos" id="snos" class="query-title-input"/></td>
                <td>&nbsp;MAC地址：</td>
                <td><input  name="macs" id="macs" class="query-title-input"/></td>
                <td>
                    <div id="deviceQuery"></div>
                </td>
                <td><div id="delDevice"></div></td>
            </tr>
        </table>
        <table id="gridDeviceList"></table>
    </form>
</div>

<div id="dialog-form-customer-list" style="display: none;">
    <form id="customer-form">
        <table>
            <tr>
                <td>用户外部编号：</td>
                <td>
                    <input  name="userId1" id="userId1" class="query-title-input"/>
                </td>
                <td>&nbsp;用户编号：</td>
                <td>
                    <input  name="code1" id="code1" class="query-title-input"/>
                </td>
                <td>&nbsp;用户电话：</td>
                <td>
                    <input  name="phone1" id="phone1" class="query-title-input"/>
                </td>
                <td>&nbsp;所属地市：</td>
                <td><input id="region1" name="region1"/></td>
                <td><div id="queryUser"/></td>
                <td><div id="delUser"></div></td>
            </tr>
        </table>
        <table id="gridUserList"></table>
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
