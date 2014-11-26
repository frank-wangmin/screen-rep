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
        dataSource: 'background_list.json',
        width: '100%',
        height: rFrameHeight,
        singleSelect: false,
        limit: limit,
        colModel: [
            {header: '<b>名称</b>', name: 'name', align: 'center', width: 120},
            {header: '<b>高清地址</b>', name: 'url', align: 'center', width: 200},
//                     {header : '<b>高清MD5值</b>', name : 'md5Hd',align : 'center',width: 300}deleted by joyce on 2014-6-12
            {header: '<b>标清地址</b>', name: 'blurUrl', align: 'center', width: 200},
//                     {header : '<b>标清MD5值</b>', name : 'md5Sd',align : 'center',width: 300},
            {header: '<b>设备分组</b>', name: 'deviceGroupIds', align: 'center', width: 80},
            {header: '<b>设备分组循环毫秒</b>', name: 'groupLoopTime', align: 'center', width: 90},
//            {header: '<b>设备编号</b>', name: 'deviceCodes', align: 'center', width: 230},
//            {header: '<b>设备循环毫秒</b>', name: 'deviceLoopTime', align: 'center', width: 80},
            {header: '<b>用户分组</b>', name: 'userGroupIds', align: 'center', width: 80},
            {header: '<b>用户分组循环毫秒</b>', name: 'userGroupLoopTime', align: 'center', width: 90},
//            {header: '<b>用户编号</b>', name: 'userIds', align: 'center', width: 200},
//            {header: '<b>用户循环毫秒</b>', name: 'userLoopTime', align: 'center', width: 80},
            {header: '<b>是否默认</b>', name: 'isDefault', align: 'center', width: 50, renderer: function (value, rowData, rowIndex) {
                return 1 === value ? "是" : "否";
            }},
            {header: '<b>状态</b>', name: 'state', align: 'center', width: 70, renderer: function (value, rowData, rowIndex) {
                return "USEABLE" === value ? "可用" : "不可用";
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
            return '第' + rowIndex + '行, 高清地址=' + rowData.url + ", 标清地址=" + rowData.blurUrl + ", 创建时间=" + rowData.createDate + ', 更新时间=' + rowData.updateDate
                    + ', 状态=' + state + ', 是否默认=' + isDefault;
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
    //$('#districtCode').omCombo({dataSource: 'area.json', editable: false, width: 252, listMaxHeight: 160,multi : true});

    $('#query').bind('click', function (e) {
        var name = $('#name1').val();
        $('#grid').omGrid("setData", 'background_list.json?name=' + encodeURIComponent(name));
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
        width: 600, autoOpen: false,
        modal: true, resizable: false,
        draggable: false,
        buttons: {
            "提交": function () {
                validator.form();
                //var info = $("#showResult").html();
                //if(info =='' || info =='可用!'){
                checkFormatIsValid();
                checkBlurFormatIsValid();
                var url = $("#showResultUrl1").html();
                var blur = $("#showResultBlurUrl1").html();
                if (url == "" && blur == "") {
                    submitDialog();
                }
                //}
                return false;
            },
            "取消": function () {
                validator.resetForm();
                $("#showResult").text("");
                $("#dialog-form").omDialog("close");
            }
        }, onClose: function () {
            $("#showResult").text("");
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
        validator.resetForm();
        $("#showResultUrl1").html("");
        $("#showResultBlurUrl1").html("");
        isAdd = true;
        $('#state').omCombo({dataSource: [
            {text: '可用', value: 'USEABLE'},
            {text: '不可用', value: 'UNUSEABLE'}
        ], editable: false, width: 252, listMaxHeight: 100});
        $('#isDefault1').omCombo({dataSource: [
            {text: '是', value: '1'},
            {text: '否', value: '0'}
        ], editable: false, width: 252, listMaxHeight: 100, disabled: false});
        //$('#deviceGroupIds1').omCombo({dataSource : 'device_group_list_by_type.json?type=BACKGROUND',editable:false,width:252,multi : true, value:' ' });
        //$('#userGroupIds').omCombo({dataSource : 'user_group_list_by_type.json?type=BACKGROUND',editable:false,width:252,multi : true , value:' '});
        showDialog('新增背景信息');
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
        if (selections[0].state != 'USEABLE') {
            $.omMessageBox.alert({
                type: 'alert',
                title: '温馨提示',
                content: '该背景轮换为不可用状态，不能做绑定操作！',
            });
            return false;
        }
        showProgramDialog("关联的设备列表");
        $('#gridDeviceList').omGrid({
            dataSource: 'device_list_by_groupId.json?deviceGroupId=' + selections[0].id+'&tableName=bss_background_image_device_map&character=background_image_id',
            width: '100%',
            height: rFrameHeight - 100,
            limit: limit,
            singleSelect:false,
            colModel: [
                {header: '<b>设备编号</b>', name: 'ystenId', align: 'center', width: 155},
                {header: '<b>设备循环毫秒</b>', name: 'loopTime', align: 'center', width: 50},

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
        $('#gridDeviceList').omGrid("setData", 'device_list_by_groupId.json?deviceGroupId=' + selections[0].id + '&tableName=bss_background_image_device_map&character=background_image_id&ystenId=' + encodeURIComponent(ystenId) + '&deviceCode=' + encodeURIComponent(deviceCode) + '&mac=' + encodeURIComponent(mac) + '&sno=' + encodeURIComponent(sno));
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
                            url: "delete_device_by_bussiness.json?delYstenIds="+toDeleteRecordID+"&packageId="+selections[0].id+'&tableName=bss_background_image_device_map&character=background_image_id&type=DEVICE&device=ysten_id',
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
                            url: "delete_device_by_bussiness.json?delYstenIds="+toDeleteRecordID+"&packageId="+selections[0].id+'&tableName=bss_background_image_device_map&character=background_image_id&type=DEVICE&device=ysten_id',
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
        if (selections[0].state != 'USEABLE') {
            $.omMessageBox.alert({
                type: 'alert',
                title: '温馨提示',
                content: '该背景轮换为不可用状态，不能做绑定操作！',
            });
            return false;
        }
        showCustomerDialog("关联的用户列表");
        $('#gridUserList').omGrid({
            dataSource: 'customer_list_by_groupId.json?userGroupId=' + selections[0].id+'&tableName=bss_background_image_user_map&character=background_image_id',
            width: '100%',
            height: rFrameHeight - 100,
            limit: limit,
            singleSelect:false,
            colModel: [
                {header : '<b>用户编号</b>', name : 'code', align : 'center', width : 150},
                {header: '<b>用户循环毫秒</b>', name: 'loopTime', align: 'center', width: 80},
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
                            url:"delete_device_by_bussiness.json?delYstenIds="+toDeleteRecordID+"&packageId="+selections[0].id+'&tableName=bss_background_image_user_map&character=background_image_id&type=USER&device=code',
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
                            url: "delete_device_by_bussiness.json?delYstenIds="+toDeleteRecordID+"&packageId="+selections[0].id+'&tableName=bss_background_image_user_map&character=background_image_id&type=USER&device=code',
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
        $('#gridUserList').omGrid("setData", 'customer_list_by_groupId.json?userGroupId=' + selections[0].id + '&userId='+encodeURIComponent(userId)+'&code='+encodeURIComponent(code)+'&phone='+encodeURIComponent(phone)+'&cRegion='+encodeURIComponent(region)+'&tableName=bss_background_image_user_map&character=background_image_id');
    });

    /*********************************显示用户列表 end*************************************************************/

    $('#update').bind('click', function () {
        $("#showResultUrl1").html("");
        $("#showResultBlurUrl1").html("");
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
            url: "background_image_to_update.json?id=" + selections[0].id,
            dataType: "json",
            success: function (msg) {
                $("#id").val(msg['id']);
                $("#name").val(msg['name']);
                $("#url1").val(msg['url']);
                $("#blurUrl1").val(msg['blurUrl']);
//    			$("#md5Hd").val(msg['md5Hd']);deleted by joyce on 2014-6-12
//            	$("#md5Sd").val(msg['md5Sd']);
                $('#state').omCombo({dataSource: [
                    {text: '可用', value: 'USEABLE'},
                    {text: '不可用', value: 'UNUSEABLE'}
                ], editable: false, width: 252, listMaxHeight: 100, value: msg['state']});
                $('#isDefault1').omCombo({dataSource: [
                    {text: '是', value: '1'},
                    {text: '否', value: '0'}
                ], editable: false, width: 252, listMaxHeight: 100, value: msg['isDefault'], disabled: true});
            }
        });
        $.ajax({
            type: "post",
            url: "get_background_image_map.json?type=DEVICE&id=" + selections[0].id,
            dataType: "json",
            success: function (msg) {
                if (msg == null) {

                } else {
//                    $('#deviceCodes1').val(msg['deviceCodes']);
                    $('#deviceLoopTime1').val(msg['loopTime']);
                }
            }
        });
        $.ajax({
            type: "post",
            url: "get_background_image_map.json?type=GROUP&id=" + selections[0].id,
            dataType: "json",
            success: function (msg) {

                if (msg == null) {
                    $('#deviceGroupIds1').omCombo({dataSource: 'device_group_list_by_type.json?type=BACKGROUND', editable: false, width: 252, multi: true});
                } else {
                    $('#groupLoopTime1').val(msg['loopTime']);
                    $('#deviceGroupIds1').omCombo({dataSource: 'device_group_list_by_type.json?type=BACKGROUND', editable: false, width: 252, multi: true, value: msg['deviceGroupIds']});
                }
            }
        });

        $.ajax({
            type: "post",
            url: "get_user_background_image_map.json?type=GROUP&id=" + selections[0].id,
            dataType: "json",
            success: function (msg) {
                if (msg == null) {
                    $('#userGroupIds').omCombo({dataSource: 'user_group_list_by_type.json?type=BACKGROUND', editable: false, width: 252, multi: true });
                } else {
                    $('#userGroupLoopTime1').val(msg['loopTime']);
                    $('#userGroupIds').omCombo({dataSource: 'user_group_list_by_type.json?type=BACKGROUND', editable: false, width: 252, multi: true, value: msg['userGroupIds']});
                }
            }
        });
        $.ajax({
            type: "post",
            url: "get_user_background_image_map.json?type=USER&id=" + selections[0].id,
            dataType: "json",
            success: function (msg) {
                if (msg == null) {

                } else {
                    $('#userIds').val(msg['userIds']);
                    $('#userLoopTime1').val(msg['loopTime']);
                }
            }
        });
        showDialog('修改背景信息', selections[0]);
    });

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
                    $.post('background_image_delete.json', {ids: toDeleteRecordID.toString()}, function (result) {
                        /**
                         if(result=='success'){
                        	$.omMessageTip.show({title: tip, content: "删除背景信息成功！", timeout: time,type:'success'});
                        }else{
                        	$.omMessageTip.show({title: tip, content: "删除背景信息失败！", timeout: time,type:'error'});
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
                    url: $("#url1").val(),
                    blurUrl: $("#blurUrl1").val(),
//            			md5Hd:$("#md5Hd").val(),deleted by joyce on 2014-6-12
//                    	md5Sd:$("#md5Sd").val(),
                    isDefault: $("#isDefault1").omCombo('value'),
                    state: $("#state").omCombo('value')
                    /**
                     deviceGroupIds:$("#deviceGroupIds1").omCombo('value'),
                     deviceCodes:$("#deviceCodes1").val(),
                     groupLoopTime:$("#groupLoopTime1").val(),
                     deviceLoopTime:$("#deviceLoopTime1").val(),
                     userGroupIds:$("#userGroupIds").omCombo('value'),
                     userIds:$("#userIds").val(),
                     userGroupLoopTime:$("#userGroupLoopTime1").val(),
                     userLoopTime:$("#userLoopTime1").val()*/
                };
//                $.post('background_image_add.shtml', submitData, function (result) {
//                    if (result.indexOf('成功') > 0) {
//                        $.omMessageTip.show({title: tip, content: result, type: "success", timeout: time});
//                    } else {
//                        $.omMessageTip.show({title: tip, content: result, type: "error", timeout: time});
//                    }
//                    $('#grid').omGrid('reload');
//                    $("#dialog-form").omDialog("close");
//                });
                $.ajaxFileUpload({
                    url: 'background_image_add.shtml',
                    secureuri: false,
                    fileElementId: ['fileField1','fileField2'],
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
            } else {
                submitData = {
                    id: $("#id").val(),
                    name: $("#name").val(),
                    url: $("#url1").val(),
                    /*	deleted by joyce on 2014-6-12
                     md5Hd:$("#md5Hd").val(),
                     md5Sd:$("#md5Sd").val(),*/
                    blurUrl: $("#blurUrl1").val(),
                    isDefault: $("#isDefault1").omCombo('value'),
                    state: $("#state").omCombo('value')
                    /**
                     deviceGroupIds:$("#deviceGroupIds1").omCombo('value'),
                     deviceCodes:$("#deviceCodes1").val(),
                     groupLoopTime:$("#groupLoopTime1").val(),
                     deviceLoopTime:$("#deviceLoopTime1").val(),
                     userGroupIds:$("#userGroupIds").omCombo('value'),
                     userIds:$("#userIds").val(),
                     userGroupLoopTime:$("#userGroupLoopTime1").val(),
                     userLoopTime:$("#userLoopTime1").val()*/
                };
//                $.post('background_image_update.shtml', submitData, function (result) {
//                    if (result.indexOf('成功') > 0) {
//                        $.omMessageTip.show({title: tip, content: result, type: "success", timeout: time});
//                    } else {
//                        $.omMessageTip.show({title: tip, content: result, type: "error", timeout: time});
//                    }
//                    $('#grid').omGrid('reload');
//                    $("#dialog-form").omDialog("close");
//                });
                $.ajaxFileUpload({
                    url: 'background_image_update.shtml',
                    secureuri: false,
                    fileElementId: ['fileField1','fileField2'],
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

    $('#bindUser').bind('click', function () {
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
        if (selections[0].state != 'USEABLE') {
            $.omMessageBox.alert({
                type: 'alert',
                title: '温馨提示',
                content: '该背景轮换为不可用状态，不能做绑定操作！',
            });
            return false;
        }
        //if the record is default, it can't do the bind operation
        if (1 == selections[0].isDefault) {
            $.omMessageBox.alert({
                type: 'alert',
                title: '温馨提示',
                content: '默认的背景轮换不能做绑定操作！',
            });
            return false;
        }

        $.get("get_areaIds_by_bussId.json", {id: selections[0].id, tableName: "bss_background_image_user_map", character: "background_image_id"}, function (result) {
            $('#areaId').omCombo({dataSource: 'area.json', editable: false, width: 252, listMaxHeight: 160, value: result || '', multi: true,
                onValueChange: function (target, newValue, oldValue) {
                    $.ajax({
                        type: "post",
                        url: "get_user_background_image_map.json?type=GROUP&id=" + selections[0].id,
                        dataType: "json",
                        success: function (msg) {
                            if (msg == null) {
                                $('#userGroupIds3').omCombo({dataSource: 'user_group_list_by_type_area.json?type=BACKGROUND&areaId=' + newValue + '&id=' + selections[0].id + '&tableName=bss_background_image_user_map&character=background_image_id', editable: false, width: 252, multi: true, value: '' });
                            } else {
                                $('#userGroupLoopTime1').val(msg['loopTime']);
                                $('#userGroupIds3').omCombo({dataSource: 'user_group_list_by_type_area.json?type=BACKGROUND&areaId=' + newValue + '&id=' + selections[0].id + '&tableName=bss_background_image_user_map&character=background_image_id', editable: false, width: 252, multi: true, value: msg['userGroupIds']});
                            }
                        }
                    });
                }
            });
        });

        $.ajax({
            type: "post",
            url: "get_user_background_image_map.json?type=GROUP&id=" + selections[0].id,
            dataType: "json",
            success: function (msg) {
                if (msg == null) {
                    $('#userGroupIds3').omCombo({dataSource: 'user_group_list_by_type.json?type=BACKGROUND', editable: false, width: 252, multi: true, value: '' });
                } else {
                    $('#userGroupLoopTime1').val(msg['loopTime']);
                    $('#userGroupIds3').omCombo({dataSource: 'user_group_list_by_type.json?type=BACKGROUND', editable: false, width: 252, multi: true, value: msg['userGroupIds']});
                }
            }
        });
        $.ajax({
            type: "post",
            url: "get_user_background_image_map.json?type=USER&id=" + selections[0].id,
            dataType: "json",
            success: function (msg) {
                if (msg == null) {

                } else {
                    $('#userLoopTime1').val(msg['loopTime']);
                }
            }
        });
        var dialog = $("#dialog-user-group").omDialog({
            width: 540,
            height: 250,
            autoOpen: false,
            modal: true,
            resizable: false,
            draggable: false,
            buttons: {
                "提交": function (result) {
                    if ($('#areaId').omCombo('value') == "") {
                        $.omMessageBox.alert({
                            type: 'alert',
                            title: '温馨提示',
                            content: '请选择用户所在区域！',
                        });
                        return false;
                    }
                    var submitData = {
                        areaIds: $('#areaId').omCombo('value'),
                        userGroupIds: $("#userGroupIds3").omCombo('value'),
                        userGroupLoopTime: $("#userGroupLoopTime1").val(),
                        userLoopTime: $("#userLoopTime1").val(),
                        ids: selections[0].id
                    };
                    if ($("#userGroupIds3").omCombo('value') != null && $("#userGroupIds3").omCombo('value') != "" && $("#userGroupLoopTime1").val() == "") {
                        $("#info3").html("请填写循环时间!");
                        return false;
                    }
                    if ($("#userIds3").val() != null && $("#userIds3").val() != "" && $("#userLoopTime1").val() == "") {
                        $("#info4").html("请填写循环时间!");
                        return false;
                    }

                    if (validator_bind_user.form()) {

                        if (!YST_APP.checkFileText($("#textfield1").val())) {
                            return false;
                        }
                        $.ajaxFileUpload({
                            url: 'backgroud_bind_user.json',
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
                    }

                },
                "取消": function () {
                    $("#dialog-user-group").omDialog("close");
                }
            }, onClose: function () {
                $("#info3").html("");
                $("#info4").html("");
                validator_bind_user.resetForm();
//                $('#user-group-form').validate().resetForm();
            }
        });
        dialog.omDialog("option", "title", "绑定背景轮换");
        dialog.omDialog("open");

    });


    $('#bind').click(function () {
        var selections = $('#grid').omGrid('getSelections', true);
        if (selections.length == 0 || selections.length > 1) {
            $.omMessageBox.alert({
                type: 'alert',
                title: '温馨提示',
                content: '绑定操作至少且只能选择一行记录！',
            });
            return false;
        }
        if (selections[0].state != 'USEABLE') {
            $.omMessageBox.alert({
                type: 'alert',
                title: '温馨提示',
                content: '该背景轮换为不可用状态，不能做绑定操作！',
            });
            return false;
        }
        //if the record is default, it can't do the bind operation
        if (1 == selections[0].isDefault) {
            $.omMessageBox.alert({
                type: 'alert',
                title: '温馨提示',
                content: '默认的背景轮换不能做绑定操作！',
            });
            return false;
        }

        $.ajax({
            type: "post",
            url: "get_background_image_map.json?type=DEVICE&id=" + selections[0].id,
            dataType: "json",
            success: function (msg) {
                if (msg == null) {

                } else {
                    $('#deviceLoopTime1').val(msg['loopTime']);
                }
            }
        });
        $('#deviceGroupIds1').val('').omCombo('setData', []);

        $.get("get_districtCodes_by_Id.json", {id: selections[0].id, tableName: "bss_background_image_device_map", character: "background_image_id"}, function (result) {
            $('#districtCode').omCombo({dataSource: 'area.json', editable: false, width: 252, listMaxHeight: 160, value: result || '', multi: true,
                onValueChange: function (target, newValue, oldValue) {
                    $.ajax({
                        type: "post",
                        url: "get_background_image_map.json?type=GROUP&id=" + selections[0].id,
                        dataType: "json",
                        success: function (msg) {
                            if (msg == null) {
                                $('#deviceGroupIds1').omCombo({dataSource: 'device_group_list_by_district.json?type=BACKGROUND&districtCode=' + newValue + '&id=' + selections[0].id + '&tableName=bss_background_image_device_map&character=background_image_id',
                                    editable: false, width: 252, multi: true, value: ''});
                            } else {
                                $('#groupLoopTime1').val(msg['loopTime']);
                                $('#deviceGroupIds1').omCombo({dataSource: 'device_group_list_by_district.json?type=BACKGROUND&districtCode=' + newValue + '&id=' + selections[0].id + '&tableName=bss_background_image_device_map&character=background_image_id',
                                    editable: false, width: 252, multi: true, value: msg['deviceGroupIds']});
                            }
                        }
                    });
                }
            });
        });

        $.ajax({
            type: "post",
            url: "get_background_image_map.json?type=GROUP&id=" + selections[0].id,
            dataType: "json",
            success: function (msg) {
                if (msg == null) {
                    $('#deviceGroupIds1').omCombo({dataSource: 'device_group_list_by_type.json?type=BACKGROUND', editable: false, width: 252, multi: true, value: ''});
                } else {
                    $('#groupLoopTime1').val(msg['loopTime']);
                    $('#deviceGroupIds1').omCombo({dataSource: 'device_group_list_by_type.json?type=BACKGROUND', editable: false, width: 252, multi: true, value: msg['deviceGroupIds']});
                }
            }
        });

        var dialog = $("#dialog-bind-form").omDialog({
            width: 530,
            height: 250,
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
                        deviceGroupIds: $("#deviceGroupIds1").omCombo('value'),
//                        deviceCodes: $("#deviceCodes1").val(),
                        groupLoopTime: $("#groupLoopTime1").val(),
                        deviceLoopTime: $("#deviceLoopTime1").val(),
                        /*userGroupIds: $("#userGroupIds").omCombo('value'),
                         userIds: $("#userIds").val(),
                         userGroupLoopTime: $("#userGroupLoopTime1").val(),
                         userLoopTime: $("#userLoopTime1").val(),*/
                        ids: selections[0].id
                    };
                    if ($("#deviceGroupIds1").omCombo('value') != null && $("#deviceGroupIds1").omCombo('value') != "" && $("#groupLoopTime1").val() == "") {
                        $("#info1").html("请填写循环时间!");
                        return false;
                    }
                    if ($("#deviceCodes1").val() != null && $("#deviceCodes1").val() != "" && $("#deviceLoopTime1").val() == "") {
                        $("#info2").html("请填写循环时间!");
                        return false;
                    }

                    if (validator_bind.form()) {

                        if (!YST_APP.checkFileText($("#textfield").val())) {
                            return false;
                        }
                        $.ajaxFileUpload({
                            url: 'backgroud_bind.json',
                            secureuri: false,
                            fileElementId: 'deviceCodes1',
                            data: submitData,
                            dataType: 'JSON',
                            success: function (result) {
                                $('#grid').omGrid('reload');
                                $('#message').html(result);
                                showInfoDialog('绑定结果');

                                $("#dialog-bind-form").omDialog("close");
                            }
                        });
                        $("#dialog-bind-form").omDialog("close");


                        return false;
                    }

                },
                "取消": function () {
                    $("#dialog-bind-form").omDialog("close");
                }
            }, onClose: function () {
                $("#info1").html("");
                $("#info2").html("");
//                $("#info3").html("");
//                $("#info4").html("");
                $('#myform-bind').validate().resetForm();
            }
        });
        dialog.omDialog("option", "title", "绑定背景轮换");
        dialog.omDialog("open");

    });

    $('#unbindUser').bind('click', function () {
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
                content: '默认的背景轮换不能做解绑操作！',
            });
            return false;
        }
        $.omMessageBox.confirm({
            title: '确认解绑',
            content: '解绑后背景轮换的绑定关系将删除且无法恢复，你确定要执行该操作吗？',
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
                    $.post('backgroud_map_delete.json', {ids: toDeleteRecordID.toString(), isUser: "true"}, function (result) {
                        $('#grid').omGrid('reload');
                        if (result == 'success') {
                            $.omMessageTip.show({title: tip, content: "解绑背景轮换关系成功！", timeout: time, type: 'success'});
                        } else {
                            $.omMessageTip.show({title: tip, content: "解绑背景轮换关系失败！", timeout: time, type: 'error'});
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
                content: '默认的背景轮换不能做解绑操作！',
            });
            return false;
        }
        $.omMessageBox.confirm({
            title: '确认解绑',
            content: '解绑后背景轮换的绑定关系将删除且无法恢复，你确定要执行该操作吗？',
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
                    $.post('backgroud_map_delete.json', {ids: toDeleteRecordID.toString()}, function (result) {
                        $('#grid').omGrid('reload');
                        if (result == 'success') {
                            $.omMessageTip.show({title: tip, content: "解绑背景轮换关系成功！", timeout: time, type: 'success'});
                        } else {
                            $.omMessageTip.show({title: tip, content: "解绑背景轮换关系失败！", timeout: time, type: 'error'});
                        }
                    });
                }
            }
        });
    });
    var validator = $('#myform').validate({
        rules: {
            isDefault: {required: true},
            name: {required: true},
            state: {required: true},
            blurUrl1: {required: true, maxlength: 500},
            url1: {required: true, maxlength: 500}
        },
        messages: {
            isDefault: {required: "是否默认不能为空！"},
            name: {required: "名称不能为空！"},
            state: {required: "状态不能为空！"},
            blurUrl1: {required: "标清地址不能为空", maxlength: "标清地址最大500位！"},
            url1: {required: "高清地址不能为空", maxlength: "高清地址最大500位！"}
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
    var validator_bind = $('#myform-bind').validate({
        rules: {
            groupLoopTime1: {digits: true },
            deviceLoopTime1: {digits: true }
        },
        messages: {
            groupLoopTime1: {digits: "请填写数字类型!" },
            deviceLoopTime1: {digits: "请填写数字类型!" }
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

    var validator_bind_user = $('#user-group-form').validate({
        rules: {
            userGroupLoopTime1: {digits: true },
            userLoopTime1: {digits: true }
        },
        messages: {
            userGroupLoopTime1: {digits: "请填写数字类型!" },
            userLoopTime1: {digits: "请填写数字类型!" }
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
function checkExists() {
    if ($.trim($("#isDefault1").omCombo('value')) != "") {
        var par = new Object();
        par['isDefault'] = $("#isDefault1").omCombo('value');
        par['id'] = $("#id").val();
        $.ajax({
            type: "post",
            url: "backgroud_default_already_exists.shtml?s=" + Math.random(),
            dataType: "html",
            data: par,
            success: function (msg) {
                $("#showResult").html(msg);
            }
        });
    } else if ($.trim($("#isDefault1").val()) == "" && $("#showResult").html() != "") {
        $("#showResult").html("");
    }
}

function checkFormatIsValid() {
//    alert($("#url1").val());
    if ($.trim($("#url1").val() != null) && $.trim($("#url1").val()).indexOf('http') == 0) {
        $("#showResultUrl1").html("");
    } else {
        $("#showResultUrl1").html('高清地址格式不正确，请确认');

    }
}

function checkBlurFormatIsValid() {
    if ($.trim($("#blurUrl1").val() != null) && $.trim($("#blurUrl1").val()).indexOf('http') == 0) {
        $("#showResultBlurUrl1").html("");
    } else {
        $("#showResultBlurUrl1").html('标清地址格式不正确，请确认');
    }
}

function fileField1Change(){
    var dd=$('#httpUrl').val()+$('#fileField1').val();
    $('#url1').val(dd);
    checkFormatIsValid();
}
function fileField2Change(){
    var dd=$('#httpUrl').val()+$('#fileField2').val();
    $('#blurUrl1').val(dd);
    checkBlurFormatIsValid();
}
</script>
</head>
<body>
<div id="center-tab">
    <ul>
        <li><a href="#tab1">背景轮换列表:</a></li>
    </ul>
</div>
<table>
    <tr>
        <c:if test="${sessionScope.add_background != null }">
            <td>
                <div id="create"/>
            </td>
        </c:if>
        <c:if test="${sessionScope.update_background != null }">
            <td>
                <div id="update"/>
            </td>
        </c:if>
        <c:if test="${sessionScope.delete_background != null }">
            <td>
                <div id="delete"/>
            </td>
        </c:if>
        <c:if test="${sessionScope.background_bind != null }">
            <td>
                <div id="bind"/>
            </td>
        </c:if>
        <c:if test="${sessionScope.background_unbind != null }">
            <td>
                <div id="unbind"/>
            </td>
        </c:if>
        <c:if test="${sessionScope.background_bind_user != null }">
            <td>
                <div id="bindUser"/>
            </td>
        </c:if>
        <c:if test="${sessionScope.background_unbind_user != null }">
            <td>
                <div id="unbindUser"/>
            </td>
        </c:if>
        <c:if test="${sessionScope.background_device_list != null }">
            <td>
                <div id="device_list_of_group"/>
            </td>
        </c:if>

        <c:if test="${sessionScope.background_user_list != null }">
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
                <td><input id="name" name="name" style="width:250px;height:20px;border:1px solid #86A3C4;"/></td>
                <td><span></span></td>
            </tr>
            <tr>
                <td style="width: 100px; text-align: right;"><span class="color_red">*</span>高清地址：</td>
                <td><input type="text" name="url1" id="url1"
                           style="width: 250px; height: 20px; border: 1px solid #86A3C4;"
                           onblur="checkFormatIsValid()"/></td>
                <td><span></span>
                    <input type="file" name="fileField1" class="file1" id="fileField1" size="28"
                           onchange="fileField1Change()" style="width: 63px;"/>
                    <div id="showResultUrl1" class="color_red" style="display: inline;"/>
                </td>
            </tr>
            <tr>
                <td style="width: 100px; text-align: right;"><span class="color_red">*</span>标清地址：</td>
                <td><input id="blurUrl1" name="blurUrl1" style="width:250px;height:20px;border:1px solid #86A3C4;"
                           onblur="checkBlurFormatIsValid()"/></td>
                <td><span></span>
                    <input type="file" name="fileField2" class="file2" id="fileField2" size="28"
                           onchange="fileField2Change()" style="width: 63px;"/>
                    <div id="showResultBlurUrl1" class="color_red" style="display: inline;"/>
                </td>
            </tr>
            <tr>
                <td style="width: 100px; text-align: right;"><span class="color_red">*</span>是否默认：</td>
                <td><input id="isDefault1" name="isDefault1" style="width:250px;"/></td>
                <!-- onblur="checkExists() -->
                <td><span></span><!--  div id="showResult" class="color_red" style="display: inline;"/>--></td>
            </tr>
            <tr>
                <td style="width: 100px; text-align: right;"><span class="color_red">*</span>状态：</td>
                <td><input id="state" name="state" style="width:250px;"/></td>
                <td><span></span></td>
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
                    <input type="file" name="deviceCodes1" class="file_import" id="deviceCodes1"
                           size="40"
                           onchange="document.getElementById('textfield').value=this.value"/>
                </td>
            </tr>
            <tr>
                <td style="width: 100px; text-align: right;">区域：</td>
                <td><input name="districtCode" id="districtCode" style="width:180px;"/></td>
                <td><span></span></td>
            </tr>
            <tr>
                <td style="width: 100px; text-align: right;">设备分组：</td>
                <td><input id="deviceGroupIds1" name="deviceGroupIds1" style="width:250px;"/></td>
                <td><span></span></td>
            </tr>
            <tr>
                <td style="width: 110px; text-align: right;">设备分组循环时间：</td>
                <td><input id="groupLoopTime1" name="groupLoopTime1"
                           style="width:250px;height:20px;border:1px solid #86A3C4;"/></td>
                <td><span>毫秒</span>

                    <div id="info1" class="color_red" style="display: inline;"/>
                </td>
            </tr>
            <tr>
                <td style="width: 100px; text-align: right;">设备循环时间：</td>
                <td><input id="deviceLoopTime1" name="deviceLoopTime1"
                           style="width:250px;height:20px;border:1px solid #86A3C4;"/></td>
                <td><span>毫秒</span>

                    <div id="info2" class="color_red" style="display: inline;"/>
                </td>
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
                <td><span></span></td>
            </tr>
            <tr>
                <td align="right">用户分组：</td>
                <td><input id="userGroupIds3" name="userGroupIds3" style="width:205px;"/></td>
                <td><span></span></td>
            </tr>
            <tr>
                <td style="width: 110px; text-align: right;">用户分组循环时间：</td>
                <td><input id="userGroupLoopTime1" name="userGroupLoopTime1"
                           style="width:250px;height:20px;border:1px solid #86A3C4;"/></td>
                <td><span>毫秒</span>

                    <div id="info3" class="color_red" style="display: inline;"/>
                </td>
            </tr>
            <tr>
                <td align="right">用户循环时间：</td>
                <td><input id="userLoopTime1" name="userLoopTime1"
                           style="width:250px;height:20px;border:1px solid #86A3C4;"/></td>
                <td><span>毫秒</span>

                    <div id="info4" class="color_red" style="display: inline;"/>
                </td>
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
