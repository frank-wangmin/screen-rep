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
        dataSource: 'sys_notice_list.json',
        width: '100%',
        height: rFrameHeight,
        singleSelect: false,
        limit: limit,
        colModel: [
            {header: '<b>标题</b>', name: 'title', align: 'center', width: 120},
            {header: '<b>内容</b>', name: 'content', align: 'center', width: 120},
            {header: '<b>类型</b>', name: 'type', align: 'center', width: 60, renderer: function (value) {
                if (value == "SYSTEM") {
                    return "系统";
                }
                if (value == "CUSTOM") {
                    return "自定义";
                }
                else {
                    return "";
                }
            }},
            {header: '<b>创建时间</b>', name: 'createDate', align: 'center', width: 120},
            {header: '<b>开始时间</b>', name: 'startDate', align: 'center', width: 120},
            {header: '<b>结束时间</b>', name: 'endDate', align: 'center', width: 120},
            {header: '<b>更新时间</b>', name: 'updateDate', align: 'center', width: 120},
            {header: '<b>设备分组</b>', name: 'deviceGroupIds', align: 'center', width: 80},
//            {header: '<b>设备编号</b>', name: 'deviceCodes', align: 'center', width: 230},
            {header: '<b>用户分组</b>', name: 'userGroupIds', align: 'center', width: 80},
//            {header: '<b>用户编号</b>', name: 'userIds', align: 'center', width: 200},
            {header: '<b>是否默认</b>', name: 'isDefault', align: 'center', width: 65, renderer: function (value, rowData, rowIndex) {
                return 1 === value ? "是" : "否";
            }},
            {header: '<b>区域</b>', name: 'districtCode', align: 'center', width: 200},
            {header: '<b>状态</b>', name: 'status', align: 'center', width: 50, renderer: function (value, rowData, rowIndex) {
                return 1 === value ? "可用" : "不可用";
            }},
            {header: '<b>操作人</b>', name: 'operateUser', align: 'center', width: 50}

        ],
        rowDetailsProvider: function (rowData, rowIndex) {
            var isDefault = "";
            if (rowData.isDefault == 1) {
                isDefault = "是";
            } else {
                isDefault = "否";
            }
            var type = "";
            if (rowData.type == "SYSTEM") {
            	type = "系统";
            }else {
            	type = "自定义";
            }
            return '第' + rowIndex + '行, 主键ID=' + rowData.id + ", 标题=" + rowData.title + "，内容=" + rowData.content + "，类型=" + type + ", 操作人=" + rowData.operateUser + "</br>创建时间=" + rowData.createDate
                    + ', 开始时间=' + rowData.startDate + ', 结束时间=' + rowData.endDate + ', 更新时间=' + rowData.updateDate+ ', 区域=' + rowData.districtCode
                    + '</br>是否默认=' + isDefault + '</br>设备分组=' + rowData.deviceGroupIds + '</br>设备=' + rowData.deviceCodes;
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
    //added by joyce on 2014-6-9
    $('#bindGroup').omButtonbar({btns: [
        {label: "绑定设备和分组", icons: {left: opPath + 'op-edit.png'}}
    ]});
    $('#unbindGroup').omButtonbar({btns: [
        {label: "解绑设备和分组", icons: {left: opPath + 'remove.png'}}
    ]});
    $('#bindUserGroup').omButtonbar({btns: [
        {label: "绑定用户和分组", icons: {left: opPath + 'op-edit.png'}}
    ]});
    $('#unbindUserGroup').omButtonbar({btns: [
        {label: "解绑用户和分组", icons: {left: opPath + 'remove.png'}}
    ]});
    //added by joyce on 2014-6-9
    $('#query').omButtonbar({btns: [
        {label: "查询", icons: {left: opPath + 'search.png'}}
    ]});
    $('#device_list_of_group').omButtonbar({btns: [
        {label: "关联设备", icons: {left: opPath + 'op-btn-icon.png'}}
    ]});
    $('#customer_list_of_group').omButtonbar({btns: [{label: "关联用户", icons: {left: opPath + 'op-btn-icon.png'}}]});
    $('#deviceQuery').omButtonbar({btns: [
        {label: "查询", icons: {left: opPath + 'search.png'}}
    ]});
    $('#queryUser').omButtonbar({btns: [{label: "查询", icons: {left: opPath + 'search.png'}}]});
    $('#delDevice').omButtonbar({btns: [
        {label: "移除", icons: {left: opPath + 'remove.png'}}
    ]});
    $('#delUser').omButtonbar({btns: [
        {label: "移除", icons: {left: opPath + 'remove.png'}}
    ]});
    $('#query').bind('click', function (e) {
        var title = $('#title2').val();
        var content = $('#content2').val();
        $('#grid').omGrid("setData", 'sys_notice_list.json?name=' + encodeURIComponent(title) + '&content=' + content);
    });
    var start = new Date();
    var today = new Date();
    start.setDate(today.getDate() - 1);
    $("#startDate3").omCalendar(
            {dateFormat: 'yy-mm-dd H:i:s',
                showTime: true,
                minDate: start
            });
    $("#endDate3").omCalendar(
            {dateFormat: 'yy-mm-dd H:i:s',
                showTime: true,
                minDate: start
            });
    var dialog = $("#dialog-form").omDialog({
        width: 500, 
        height: 350,
        autoOpen: false,
        modal: true, 
        resizable: false,
        draggable: false,
        buttons: {
            "提交": function () {
                validator.form();
                var start = $("#startDate3").val();
                var end = $("#endDate3").val();
                var sr = $("#showResult").html();
                
                if (start > end) {
                    $.omMessageBox.alert({
                        type: 'alert',
                        title: '温馨提示',
                        content: '开始时间要小于结束时间！！',
                    });
                    return false;
                }else if ($("#type3").omCombo('value')=='CUSTOM' && start <= end && (sr == '' || sr == '可用!')) {
                    submitDialog();
                }else if ($("#type3").omCombo('value')=='SYSTEM' && (sr == '' || sr == '可用!')) {
                    submitDialog();
                }
                return false;
            },
            "取消": function () {
                $("#showResult").text("");
                $("#dialog-form").omDialog("close");
            }
        }, onClose: function () {
            $("#showResult").text("");
            $('#myform').validate().resetForm();
        }
    });

    var showDialog = function (title, rowData) {
        rowData = rowData || {};
        dialog.omDialog("option", "title", title);
        dialog.omDialog("open");
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

    var isAdd = true;
    $('#create').bind('click', function () {
    	 $('#type3').omCombo({
    	        dataSource : [ 
    	                      {text : '系统消息', value : 'SYSTEM'}, 
    	                       {text : '自定义', value : 'CUSTOM'}
    	                      ],value:'CUSTOM',disabled: false,width:186, onValueChange : function(target, newValue){
    	                          if(newValue == 'SYSTEM'){
    		                       	   $("#openSystem").show();
    		                           $("#openCustom1").hide();
    		                           $("#openCustom2").hide();
    		                           $("#openCustom3").hide();
    		                           $("#startDate3").rules("remove", "required");
    		                       	   $("#endDate3").rules("remove", "required");
    		                       	   $("#isAll3").rules("remove", "required");
    		                       	   $("#districtCode3").rules("add", "required");
    	                          }
    							   if(newValue == 'CUSTOM'){
    								   $("#openSystem").hide();
    		                           $("#openCustom1").show();
    		                           $("#openCustom2").show();
    		                           $("#openCustom3").show();
    		                           $("#startDate3").rules("add", "required");
    		                       	   $("#endDate3").rules("add", "required");
    		                       	   $("#isAll3").rules("add", "required");
    		                       	   $("#districtCode3").rules("remove", "required");
    	                          }
    	                  }
    	    });
        isAdd = true;
        $('#districtCode3').omCombo({dataSource : 'area_distCode.json?par=0',editable:false,multi: true,width:186,listMaxHeight:160});
        $('#isAll3').omCombo({dataSource: [
            {text: '是', value: '1'},
            {text: '否', value: '0'}
        ], editable: false, width: 186, listMaxHeight: 100, disabled: false});
        $('#status3').omCombo({dataSource: [
            {text: '可用', value: '1'},
            {text: '不可用', value: '0'}
        ], editable: false, width: 186, listMaxHeight: 100});
//        $('#deviceGroupIds3').omCombo({dataSource : 'device_group_list_by_type.json?type=NOTICE',editable:false,width:184,multi : true, value:' ' });
//        $('#userGroupIds3').omCombo({dataSource : 'user_group_list_by_type.json?type=NOTICE',editable:false,width:184,multi : true, value:' ' });
        showDialog('新增消息信息');
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
            url: "sys_notice_to_update.json?id=" + selections[0].id,
            dataType: "json",
            success: function (msg) {
                $("#id").val(msg['id']);
                $("#title3").val(msg['title']);
                $("#content3").val(msg['content']);
                $("#isAll3").val(msg['isDefault']);
                $('#status3').omCombo({dataSource: [
                    {text: '可用', value: '1'},
                    {text: '不可用', value: '0'}
                ], editable: false, width: 186, listMaxHeight: 100, value: msg['status']});
                $('#type3').omCombo({
         	        dataSource : [ 
         	                      {text : '系统消息', value : 'SYSTEM'}, 
         	                       {text : '自定义', value : 'CUSTOM'}
         	                      ],value:msg['type'], disabled: true,width:186, onValueChange : function(target, newValue){
         	                          if(newValue == 'SYSTEM'){
         		                       	   $("#openSystem").show();
         		                           $("#openCustom1").hide();
         		                           $("#openCustom2").hide();
         		                           $("#openCustom3").hide();
         		                           $('#districtCode3').omCombo({dataSource : 'area_distCode.json?par=0',editable:false,multi: true,width:186,listMaxHeight:160,value: msg['districtCode'],});
         		                           $("#startDate3").rules("remove", "required");
         		                       	   $("#endDate3").rules("remove", "required");
         		                       	   $("#isAll3").rules("remove", "required");
         		                       	   $("#districtCode3").rules("add", "required");
         	                          }
         							   if(newValue == 'CUSTOM'){
         								   $("#openSystem").hide();
         		                           $("#openCustom1").show();
         		                           $("#openCustom2").show();
         		                           $("#openCustom3").show();
         		                          $("#startDate3").val(msg['startDate']);
         		                          $("#endDate3").val(msg['endDate']);
         		                          $('#isAll3').omCombo({dataSource: [
         		                             {text: '是', value: '1'},
         		                             {text: '否', value: '0'}
         		                         ], editable: false, width: 186, listMaxHeight: 100, value: msg['isDefault'], disabled: true});
         		                           $("#startDate3").rules("add", "required");
         		                       	   $("#endDate3").rules("add", "required");
         		                       	   $("#isAll3").rules("add", "required");
         		                       	   $("#districtCode3").rules("remove", "required");
         	                          }
         	                  }
         	    });
            }
        });
        $.ajax({
            type: "post",
            url: "get_notice_ids.json?type=GROUP&id=" + selections[0].id,
            dataType: "text",
            success: function (msg) {
                $('#deviceGroupIds3').omCombo({dataSource: 'device_group_list_by_type.json?type=NOTICE', editable: false, width: 184, multi: true, value: msg});
            }
        });
        $.ajax({
            type: "post",
            url: "get_user_notice_ids.json?type=GROUP&id=" + selections[0].id,
            dataType: "text",
            success: function (msg) {
                $('#userGroupIds3').omCombo({dataSource: 'user_group_list_by_type.json?type=NOTICE', editable: false, width: 184, multi: true, value: msg});
            }
        });
        $.ajax({
            type: "post",
            url: "get_user_notice_ids.json?type=USER&id=" + selections[0].id,
            dataType: "text",
            success: function (msg) {
                $('#userIds3').val(msg);
            }
        });
        showDialog('修改消息信息', selections[0]);
    });



    $('#unbindUserGroup').bind('click', function () {
        var selections = $('#grid').omGrid('getSelections', true);
        //check if just select one record begin
        if (selections.length == 0 || selections.length > 1) {
            $.omMessageBox.alert({
                type: 'alert',
                title: '温馨提示',
                content: '解绑操作至少且只能选择一行记录！',
            });
            return false;
        }

        //if the record is default, it can't do the unbind operation
        if (1 == selections[0].isDefault) {
            $.omMessageBox.alert({
                type: 'alert',
                title: '温馨提示',
                content: '默认的消息信息不能做解绑操作！',
            });
            return false;
        }
        if ("SYSTEM" == selections[0].type) {
            $.omMessageBox.alert({
                type: 'alert',
                title: '温馨提示',
                content: '系统类型消息不能做解绑操作！',
            });
            return false;
        }

        //popup one confirm dialog
        $.omMessageBox.confirm({
            title: '确认删除',
            content: '解除绑定后数据将无法恢复，你确定要执行该操作吗？',
            onClose: function (v) {
                if (v == true) {
                    console.log(selections[0].id);
                    $.post('delete_sys_notic_relationship.json', {id: selections[0].id,user:"true"}, function (result) {
                        if (result.indexOf('成功')) {
                            $.omMessageTip.show({title: tip, content: result, timeout: time, type: 'success'});
                        } else {
                            $.omMessageTip.show({title: tip, content: result, timeout: time, type: 'error'});
                        }
                        $('#grid').omGrid('reload');
                    });
                }
            }
        });


    });


    //unbindGroup begin
    $('#unbindGroup').bind('click', function () {
        var selections = $('#grid').omGrid('getSelections', true);
        //check if just select one record begin
        if (selections.length == 0 || selections.length > 1) {
            $.omMessageBox.alert({
                type: 'alert',
                title: '温馨提示',
                content: '解绑操作至少且只能选择一行记录！',
            });
            return false;
        }

        //if the record is default, it can't do the unbind operation
        if (1 == selections[0].isDefault) {
            $.omMessageBox.alert({
                type: 'alert',
                title: '温馨提示',
                content: '默认的消息信息不能做解绑操作！',
            });
            return false;
        }
        if ("SYSTEM" == selections[0].type) {
            $.omMessageBox.alert({
                type: 'alert',
                title: '温馨提示',
                content: '系统类型消息不能做解绑操作！',
            });
            return false;
        }

        //popup one confirm dialog
        $.omMessageBox.confirm({
            title: '确认删除',
            content: '解除绑定后数据将无法恢复，你确定要执行该操作吗？',
            onClose: function (v) {
                if (v == true) {
                    console.log(selections[0].id);
                    $.post('delete_sys_notic_relationship.json', {id: selections[0].id}, function (result) {
                        if (result.indexOf('成功')) {
                            $.omMessageTip.show({title: tip, content: result, timeout: time, type: 'success'});
                        } else {
                            $.omMessageTip.show({title: tip, content: result, timeout: time, type: 'error'});
                        }
                        $('#grid').omGrid('reload');
                    });
                }
            }
        });


    });

    $('#bindUserGroup').bind('click',function(){
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
        if (selections[0].status != 1) {
            $.omMessageBox.alert({
                type: 'alert',
                title: '温馨提示',
                content: '该消息为不可用状态，不能做绑定操作！',
            });
            return false;
        }
        if ("SYSTEM" == selections[0].type) {
            $.omMessageBox.alert({
                type: 'alert',
                title: '温馨提示',
                content: '系统类型消息不能做绑定操作！',
            });
            return false;
        }
    if (1 == selections[0].isDefault) {
        $.omMessageBox.alert({
            type: 'alert',
            title: '温馨提示',
            content: '默认的消息信息不能做绑定操作！',
        });
        return false;
    }
        $.get("get_areaIds_by_bussId.json", {id: selections[0].id, tableName: "bss_user_notice_map", character: "notice_id"}, function (result) {
            $('#areaId').omCombo({dataSource: 'area.json', editable: false, width: 184, listMaxHeight: 160, value: result || '', multi: true,
                onValueChange: function (target, newValue, oldValue) {
                    $.get("get_user_notice_ids.json", {id: selections[0].id, type: "GROUP"}, function (result) {
                        $('#userGroupIds3').omCombo({dataSource: 'user_group_list_by_type_area.json?type=NOTICE&areaId=' + newValue + '&id=' + selections[0].id + '&tableName=bss_user_notice_map&character=notice_id',
                            editable: false, width: 184, multi: true, value: result || ''});
                    });
                }
            });
        });

        $.get("get_user_notice_ids.json", {id: selections[0].id, type: "GROUP"}, function (result) {
            $('#userGroupIds3').omCombo({dataSource: 'user_group_list_by_type.json?type=NOTICE', editable: false, width: 184, multi: true, value: result || ''});
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
                 id: selections[0].id,
                 userGroupIds: $("#userGroupIds3").val(),
//                 userIds: $("#userIds3").val(),
                 };


                $.ajaxFileUpload({
                    url: 'sys_notice_bind_user_relationship.json',
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
    //unbindGroup end
    //updated by joyce on 2014-6-12
    $('#bindGroup').bind('click', function () {
        var selections = $('#grid').omGrid('getSelections', true);
        $('#deviceGroupIds3').omCombo('setData', []);
        if (selections.length == 0 || selections.length > 1) {
            $.omMessageBox.alert({
                type: 'alert',
                title: '温馨提示',
                content: '绑定操作至少且只能选择一行记录！',
            });
            return false;
        }
        if (selections[0].status != 1) {
            $.omMessageBox.alert({
                type: 'alert',
                title: '温馨提示',
                content: '该消息为不可用状态，不能做绑定操作！',
            });
            return false;
        }
        //if the record is default, it can't do the bind operation
        if (1 == selections[0].isDefault) {
            $.omMessageBox.alert({
                type: 'alert',
                title: '温馨提示',
                content: '默认的消息信息不能做绑定操作！',
            });
            return false;
        }

        if ("SYSTEM" == selections[0].type) {
            $.omMessageBox.alert({
                type: 'alert',
                title: '温馨提示',
                content: '系统类型消息不能做绑定操作！',
            });
            return false;
        }

        $.ajax({
            type: "post",
            url: "sys_notice_to_update.json?id=" + selections[0].id,
            dataType: "json",
            success: function (msg) {
                $("#id").val(msg['id']);
            }
        });
        $.get("get_districtCodes_by_Id.json", {id: selections[0].id, tableName: "bss_device_notice_map", character: "notice_id"}, function (result) {
            $('#districtCode').omCombo({dataSource: 'area.json', editable: false, width: 184, listMaxHeight: 160, value: result || '', multi: true,
                onValueChange: function (target, newValue, oldValue) {
                    $.get("get_notice_ids.json", {id: selections[0].id, type: "GROUP"}, function (result) {
                        $('#deviceGroupIds3').omCombo({dataSource: 'device_group_list_by_district.json?type=NOTICE&districtCode=' + newValue + '&id=' + selections[0].id + '&tableName=bss_device_notice_map&character=notice_id',
                            editable: false, width: 184, multi: true, value: result || ''});
                    });
                }
            });
        });
        $.get("get_notice_ids.json", {id: selections[0].id, type: "GROUP"}, function (result) {
            $('#deviceGroupIds3').omCombo({dataSource: 'device_group_list_by_type.json?type=NOTICE', editable: false, width: 184, multi: true, value: result || ''});
        });


        var dialog = $("#dialog-group").omDialog({
            width: 500, height: 200, autoOpen: false, modal: true, resizable: false, draggable: false,
            buttons: {
                "提交": function () {
                    if (!$('#districtCode').omCombo('value')) {
                        $.omMessageBox.alert({
                            type: 'alert',
                            title: '温馨提示',
                            content: '请选择设备所在区域！'
                        });
                        return false;
                    }
                    if (!YST_APP.checkFileText($("#textfield").val())) {
                        return false;
                    }
                    var submitData = {
                        areaIds: $('#districtCode').omCombo('value'),
                        id: selections[0].id,
                        deviceGroupIds: $("#deviceGroupIds3").val(),
                    };

                    $.ajaxFileUpload({
                        url: 'sys_notice_bind_device_relationship.json',
                        secureuri: false,
                        fileElementId: 'deviceCodes3',
                        data: submitData,
                        dataType: 'JSON',
                        success: function (result) {
                            $('#grid').omGrid('reload');
                            $('#message').html(result);
                            showInfoDialog('绑定结果');
                        }
                    });
                    $("#dialog-group").omDialog("close");
                },
                "取消": function () {
                    $("#dialog-group").omDialog("close");
                }
            },
            onClose: function () {
                $('#dialog-group-form').validate().resetForm();
            }
        });
        dialog.omDialog("option", "title", "绑定消息信息");
        dialog.omDialog("open");
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

        for (var i = 0; i < selections.length; i++) {
            if (selections[i].deviceGroupIds || selections[i].deviceCodes || selections[i].userGroupIds || selections[i].userIds) {
                $.omMessageBox.alert({
                    type: 'alert',
                    title: '温馨提示',
                    content: '存在绑定关系,不能删除,如果想删除,请先做解绑操作!',
                });
                return false;
            }
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
                    $.post('sys_notice_delete.json', {ids: toDeleteRecordID.toString()}, function (result) {
                        $('#grid').omGrid('reload');
                        if (result == 'success') {
                            $.omMessageTip.show({title: tip, content: "删除消息信息成功！", timeout: time, type: 'success'});
                        } else {
                            $.omMessageTip.show({title: tip, content: "删除消息信息失败！", timeout: time, type: 'error'});
                        }
                        $('#dialog-form').omDialog('close');
                    });
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
            dataSource: 'device_list_by_groupId.json?deviceGroupId=' + selections[0].id+'&tableName=bss_device_notice_map&character=notice_id',
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
        $('#gridDeviceList').omGrid("setData", 'device_list_by_groupId.json?deviceGroupId=' + selections[0].id + '&tableName=bss_device_notice_map&character=notice_id&ystenId=' + encodeURIComponent(ystenId) + '&deviceCode=' + encodeURIComponent(deviceCode) + '&mac=' + encodeURIComponent(mac) + '&sno=' + encodeURIComponent(sno));
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
                            url: "delete_device_by_bussiness.json?delYstenIds="+toDeleteRecordID+"&packageId="+selections[0].id+'&tableName=bss_device_notice_map&character=notice_id&type=DEVICE&device=ysten_id',
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
                            url: "delete_device_by_bussiness.json?delYstenIds="+toDeleteRecordID+"&packageId="+selections[0].id+'&tableName=bss_device_notice_map&character=notice_id&type=DEVICE&device=ysten_id',
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
            dataSource: 'customer_list_by_groupId.json?userGroupId=' + selections[0].id+'&tableName=bss_user_notice_map&character=notice_id',
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
                            url: "delete_device_by_bussiness.json?delYstenIds="+toDeleteRecordID+"&packageId="+selections[0].id+'&tableName=bss_user_notice_map&character=notice_id&type=USER&device=code',
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
                            url: "delete_device_by_bussiness.json?delYstenIds="+toDeleteRecordID+"&packageId="+selections[0].id+'&tableName=bss_user_notice_map&character=notice_id&type=USER&device=code',
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
        $('#gridUserList').omGrid("setData", 'customer_list_by_groupId.json?userGroupId=' + selections[0].id + '&userId='+encodeURIComponent(userId)+'&code='+encodeURIComponent(code)+'&phone='+encodeURIComponent(phone)+'&cRegion='+encodeURIComponent(region)+'&tableName=bss_user_notice_map&character=notice_id');
    });

    /*********************************显示用户列表 end*************************************************************/

    var submitDialog = function () {
        var submitData;
        if ( validator.form()) {
            if (isAdd) {
                submitData = {
                    title: $("#title3").val(),
                    content: $("#content3").val(),
                    type:$("#type3").omCombo('value'),
                    districtCode:$("#districtCode3").omCombo('value'),
                    startDate3: $("#startDate3").val(),
                    endDate3: $("#endDate3").val(),
                    isDefault: $("#isAll3").omCombo('value'),
                    status: $("#status3").omCombo('value'),
                };
                $.post('sys_notice_add.shtml', submitData, function (result) {

                    if (result.indexOf('成功') > 0) {
                        $.omMessageTip.show({title: tip, content: result, type: "success", timeout: time});
                    } else {
                        $.omMessageTip.show({title: tip, content: result, type: "error", timeout: time});
                    }
                    $('#grid').omGrid('reload');
                    $("#dialog-form").omDialog("close");
                });
            } else {
               
                if($("#type3").omCombo('value') == 'SYSTEM'){
                	 submitData = {
                             id: $("#id").val(),
                             title: $("#title3").val(),
                             content: $("#content3").val(),
                             type:$("#type3").omCombo('value'),
                             districtCode:$("#districtCode3").omCombo('value'),
                             status: $("#status3").omCombo('value'),
                         };
                }else{
                	 submitData = {
                             id: $("#id").val(),
                             title: $("#title3").val(),
                             content: $("#content3").val(),
                             type:$("#type3").omCombo('value'),
                             //districtCode:$("#districtCode3").omCombo('value'),
                             startDate3: $("#startDate3").val(),
                             endDate3: $("#endDate3").val(),
                             isDefault: $("#isAll3").omCombo('value'),
                             status: $("#status3").omCombo('value'),

                         };
                    }
                $.post('sys_notice_update.shtml', submitData, function (result) {

                    if (result.indexOf('成功') > 0) {
                        $.omMessageTip.show({title: tip, content: result, type: "success", timeout: time});
                    } else {
                        $.omMessageTip.show({title: tip, content: result, type: "error", timeout: time});
                    }
                    $('#grid').omGrid('reload');
                    $("#dialog-form").omDialog("close");
                });
            }
        }
    };

    var validator = $('#myform').validate({
        rules: {
            title3: {required: true},
            content3: {required: true},
            type3: {required: true},
            startDate3: {required: true},
            endDate3: {required: true},
            isAll3: {required: true},
            status3: {required: true},
			districtCode3: {required: true}
        },
        messages: {
            title3: {required: "标题不能为空！"},
            content3: {required: "内容不能为空！"},
            type3: {required: "类型不能为空！"},
            startDate3: {required: "开始时间不能为空！"},
            endDate3: {required: "结束时间不能为空！"},
            isAll3: {required: "请选择是否全部终端！"},
            status3: {required: "请选择是状态！"},
			districtCode3: {required: "所属区域不能为空！"}
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
    if ($.trim($("#title3").val()) != "") {
        var par = new Object();
        par['title'] = $("#title3").val();
        par['id'] = $("#id").val();
        $.ajax({
            type: "post",
            url: "notice_title_exists.shtml?s=" + Math.random(),
            dataType: "html",
            data: par,
            success: function (msg) {
                //alert(msg);
                $("#showResult").html(msg);
            }
        });
    } else if ($.trim($("#title3").val()) == "" && $("#showResult").html() != "") {
        $("#showResult").html("");
    }
}
</script>
</head>
<body>
<div id="center-tab">
    <ul>
        <li><a href="#tab1">消息信息列表:</a></li>
    </ul>
</div>
<table>
    <tr>
        <c:if test="${sessionScope.add_sys_notice != null }">
            <td>
                <div id="create"/>
            </td>
        </c:if>
        <c:if test="${sessionScope.update_sys_notice != null }">
            <td>
                <div id="update"/>
            </td>
        </c:if>
        <c:if test="${sessionScope.delete_sys_notice != null }">
            <td>
                <div id="delete"/>
            </td>
        </c:if>
        <!-- added by joyce on 2014-6-9 begin -->
        <c:if test="${sessionScope.bind_sys_notice != null }">
            <td>
                <div id="bindGroup"/>
            </td>
        </c:if>
        <c:if test="${sessionScope.unbind_sys_notice != null }">
            <td>
                <div id="unbindGroup"/>
            </td>
        </c:if>
        <c:if test="${sessionScope.bind_user_sys_notice != null }">
            <td>
                <div id="bindUserGroup"/>
            </td>
        </c:if>
        <c:if test="${sessionScope.unbind_user_sys_notice != null }">
            <td>
                <div id="unbindUserGroup"/>
            </td>
        </c:if>
        <c:if test="${sessionScope.sys_notice_device_list != null }">
            <td>
                <div id="device_list_of_group"/>
            </td>
        </c:if>

        <c:if test="${sessionScope.sys_notice_user_list != null }">
            <td>
                <div id="customer_list_of_group"/>
            </td>
        </c:if>
        <!-- added by joyce on 2014-6-9 end -->
        </td>
    </tr>
</table>
<table>
    <tr align="left">
        <td style="text-align:center;">标题：</td>
        <td><input type="text" name="title2" id="title2" style="height: 20px;border:1px solid #86A3C4;"/></td>
        <td style="text-align:center;">&nbsp;内容：</td>
        <td><input name="content2" id="content2" style="height: 20px;border:1px solid #86A3C4;"/></td>
        <td>
            <div id="query"/>
    </tr>
    </table>
<table id="grid"></table>
<div id="dialog-form">
    <form id="myform">
        <input type="hidden" value="" name="id" id="id"/>
        <table>
            <tr>
                <td align="right"><span class="color_red">*</span>标题：</td>
                <td><input id="title3" name="title3" style="width:182px;height:20px;border:1px solid #86A3C4;"
                           onblur="checkNameExists()"/></td>
                <td><span></span>

                    <div id="showResult" class="color_red" style="display: inline;"/>
                </td>
            </tr>
            <tr>
                <td align="right"><span class="color_red">*</span>消息内容：</td>
                <td><textarea id="content3" name="content3" cols="28" rows="8"
                              style="border:1px solid #86A3C4;"></textarea></td>
                <td><span></span></td>
            </tr>
            <tr>
                <td align="right"><span class="color_red">*</span>消息状态：</td>
                <td><input id="status3" name="status3"/></td>
                <td><span></span></td>
            </tr>
            <tr>
            	<td align="right"><span class="color_red">*</span>类型：</td>
                <td><input id="type3" name="type3"/></td>
                <td><span></span></td>
            </tr>
            	<tr id="openSystem" style="display: none;">
	            	<td align="right"><span class="color_red">*</span>所属区域：</td>
	                <td><input id="districtCode3" name="districtCode3"/></td>
	                <td><span></span></td>
            	</tr>
	        	<tr id="openCustom1" style="display: none;">
	                <td align="right"><span class="color_red">*</span>开始时间：</td>
	                <td><input id="startDate3" name="startDate3" style="width:164px;"/></td>
	                <td><span></span></td>
	            </tr>
	            <tr id="openCustom2" style="display: none;">
	                <td align="right"><span class="color_red">*</span>结束时间：</td>
	                <td><input id="endDate3" name="endDate3" style="width:164px;"/></td>
	                <td><span></span></td>
	            </tr>
	            <tr id="openCustom3" style="display: none;">
	                <td align="right"><span class="color_red">*</span>是否默认：</td>
	                <td><input id="isAll3" name="isAll3"/></td>
	                <td><span></span></td>
	            </tr>
        </table>
    </form>
</div>
<div id="dialog-group" style="display:none">
    <form id="dialog-group-form" method="post">
        <table>
            <tr>
                <td align="right">设备编号：</td>
                <td><input type='text' name='textfield' id='textfield'
                           class='file_txt'/>
                    <input type='button' class='file_btn' value='浏览...'/>
                    <input type="file" name="deviceCodes3" class="file_import" id="deviceCodes3"
                           size="40"
                           onchange="document.getElementById('textfield').value=this.value"/>
                </td>
            </tr>
            <tr>
                <td style="width: 100px; text-align: right;">区域：</td>
                <td><input name="districtCode" id="districtCode" style="width:205px;"/></td>
                <td><span></span></td>
            </tr>
            <tr>
                <td align="right">设备分组：</td>
                <td><input id="deviceGroupIds3" name="deviceGroupIds3" style="width:205px;"/></td>
                <td><span></span></td>
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
