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
<script>
$(document).ready(function () {
	$('#center-tab').omTabs({height: "33", border: false});
    $('#grid').omGrid({
        dataSource: 'find_apk_upgrade_task_list.json',
        width: '100%',
        height: rFrameHeight,
        singleSelect: false,
        limit: limit,
        colModel: [
            {header: '<b>升级任务名称</b>', name: 'taskName', align: 'center', width: 140},
            {header: '<b>设备软件信息号</b>', name: 'softCodeId', align: 'center', width: 140, renderer: function (value) {
                if (value == null || value == "") {
                    return "";
                } else {
                    return value.name;
                }
            }},
            {header: '<b>设备软件升级包</b>', name: 'softwarePackageId', align: 'center', width: 140, renderer: function (value) {
                if (value == null || value == "") {
                    return "";
                } else {
                    return value.versionName;
                }
            }},
            {header: '<b>设备分组</b>', name: 'deviceGroupIds', align: 'center', width: 100},
//            {header: '<b>设备编号</b>', name: 'deviceCode', align: 'center', width: 150},
            {header: '<b>最大升级终端数</b>', name: 'maxNum', align: 'center', width: 80},
            {header: '<b>间隔时间(分钟)</b>', name: 'timeInterval', align: 'center', width: 80},
            {header: '<b>是否全部升级</b>', name: 'isAll', align: 'center', width: 70, renderer: function (value) {
                if (value == 1) {
                    return "是";
                }
                if (value == 0) {
                    return "否";
                }
                else {
                    return "";
                }
            }},
            {header: '<b>开始时间</b>', name: 'startDate', align: 'center', width: 120},
            {header: '<b>结束时间</b>', name: 'endDate', align: 'center', width: 120},
            {header: '<b>创建时间</b>', name: 'createDate', align: 'center', width: 120},
            {header: '<b>最后操作时间</b>', name: 'lastModifyTime', align: 'center', width: 120},
            {header: '<b>操作者</b>', name: 'operUser', align: 'center', width: 50}
        ]
    });
    $('#create').omButtonbar({btns: [
        {label: "新增", icons: {left: opPath + 'add.png'}}
    ]});
    $('#update').omButtonbar({btns: [
        {label: "修改", icons: {left: opPath + 'op-edit.png'}}
    ]});
    $('#query').omButtonbar({btns: [
        {label: "查询", icons: {left: opPath + 'search.png'}}
    ]});
    $('#delete').omButtonbar({btns: [
        {label: "删除", icons: {left: opPath + 'remove.png'}}
    ]});
    $('#bind').omButtonbar({btns: [{label: "绑定设备和分组", icons: {left: opPath + 'op-edit.png'}}]});
    $('#unbind').omButtonbar({btns: [{label: "解绑设备和分组", icons: {left: opPath + 'remove.png'}}]});
    $('#device_list_of_group').omButtonbar({btns: [{label: "关联设备", icons: {left: opPath + 'op-btn-icon.png'}}]});
    $('#deviceQuery').omButtonbar({btns: [{label: "查询", icons: {left: opPath + 'search.png'}} ]});
    $('#delDevice').omButtonbar({btns: [{label: "移除", icons: {left: opPath + 'remove.png'}}]});
                                      
    /*******************************************************bind start*************************************************************/
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

        if (selections[0].isAll == 1) {
            $.omMessageBox.alert({
                type: 'alert',
                title: '温馨提示',
                content: '该APK升级任务对全部终端升级，无需做绑定操作！',
            });
            return false;
        }

        $.get("get_districtCodes_by_Id.json?", {id: selections[0].id, tableName: "bss_apk_upgrade_map", character: "upgrade_id"}, function (result) {
            $('#districtCode').omCombo({dataSource: 'area.json', editable: false, width: 182, listMaxHeight: 160, value: result, multi: true,
                onValueChange: function (target, newValue, oldValue) {
                    $.get("get_app_upgrade_device.json", {id: selections[0].id, type: "GROUP"}, function (result) {
                        $('#deviceGroupIds').omCombo({dataSource: 'device_group_list_by_district.json?type=APKUPGRADE&districtCode=' + newValue + '&id=' + selections[0].id + '&tableName=bss_apk_upgrade_map&character=upgrade_id', editable: false, width: 182, multi: true, value: result || ''});
                    });
                }
            });
        });

        $.get("get_app_upgrade_device.json", {id: selections[0].id, type: "GROUP"}, function (result) {
            $('#deviceGroupIds').omCombo({dataSource: 'device_group_list_by_type.json?type=APKUPGRADE', editable: false, width: 182, multi: true, value: result || ''});
        });
        var dialog = $("#dialog-bind-form").omDialog({
            width: 600,
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

                    if (!YST_APP.checkFileText($("#textfield").val())) {
                        return false;
                    }
                    var submitData = {
                        areaIds: $('#districtCode').omCombo('value'),
                        deviceGroupIds: $("#deviceGroupIds").omCombo('value'),
                        ids: selections[0].id
                    };

                    $.ajaxFileUpload({
                        url: 'apk_upgradeTask_bind',
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
                    $("#dialog-bind-form").omDialog("close");

                    return false;
                },
                "取消": function () {
                    $("#dialog-bind-form").omDialog("close");
                }
            }, onClose: function () {
                $('#myform-bind').validate().resetForm();
            }
        });
        dialog.omDialog("option", "title", "绑定APK升级任务");
        dialog.omDialog("open");
    });
    /*******************************************************bind end*************************************************************/
    
    /*******************************************************unbind start*********************************************************/
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
        $.omMessageBox.confirm({
            title: '确认解绑',
            content: '解绑后升级任务的绑定关系将删除且无法恢复，你确定要执行该操作吗？',
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
                    $.post('apk_upgrade_task_map_delete.json', {ids: toDeleteRecordID.toString()}, function (result) {
                        $('#grid').omGrid('reload');
                        if (result == 'success') {
                            $.omMessageTip.show({title: tip, content: "解绑升级任务关系成功！", timeout: time, type: 'success'});
                        } else {
                            $.omMessageTip.show({title: tip, content: "解绑升级任务关系失败！", timeout: time, type: 'error'});
                        }
                    });
                }
            }
        });
    });
    /*******************************************************unbind end*********************************************************/
    
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
            dataSource: 'device_list_by_groupId.json?deviceGroupId=' + selections[0].id+'&tableName=bss_apk_upgrade_map&character=upgrade_id',
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
        $('#gridDeviceList').omGrid("setData", 'device_list_by_groupId.json?deviceGroupId=' + selections[0].id + '&tableName=bss_apk_upgrade_map&character=upgrade_id&ystenId=' + encodeURIComponent(ystenId) + '&deviceCode=' + encodeURIComponent(deviceCode) + '&mac=' + encodeURIComponent(mac) + '&sno=' + encodeURIComponent(sno));
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
                            url: "delete_device_by_bussiness.json?delYstenIds="+toDeleteRecordID+"&packageId="+selections[0].id+'&tableName=bss_apk_upgrade_map&character=upgrade_id&type=DEVICE&device=ysten_id',
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
                    else if($('#deleteType').omCombo('value') == 1){
                        $.ajax({
                            type: "POST",
                            url: "delete_device_by_bussiness.json?delYstenIds="+toDeleteRecordID+"&packageId="+selections[0].id+'&tableName=bss_apk_upgrade_map&character=upgrade_id&type=DEVICE&device=ysten_id',
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
    $('#query').bind('click', function (e) {
        var softCodeName = filterStartEndSpaceTrim($('#softCodeName').val());
        var softwarePackageName = filterStartEndSpaceTrim($('#softwarePackageName').val());
        $('#grid').omGrid("setData", 'find_apk_upgrade_task_list.json?softwarePackageName=' + encodeURIComponent(softwarePackageName)
                + "&softCodeName=" + encodeURIComponent(softCodeName));
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
                    $.post('apkUpgrade_task_delete.json', {ids: toDeleteRecordID.toString()}, function (result) {
                        $('#grid').omGrid('reload');
                        if (result.indexOf('成功') > 0) {
                            $.omMessageTip.show({title: tip, content: result, type: "success", timeout: time});
                        } else {
                            $.omMessageTip.show({title: tip, content: result, type: "error", timeout: time});
                        }
                        $('#dialog-form').omDialog('close');
                    });
                }
            }
        });
    });
    var showDialog = function (title, rowData) {
        validator.resetForm();
        rowData = rowData || {};
        dialog.omDialog("option", "title", title);
        dialog.omDialog("open");
    };
    var dialog = $("#dialog-form").omDialog({
        width: 600,
        autoOpen: false,
        modal: true,
        resizable: false,
        draggable: false,
        buttons: {
            "提交": function () {
                validator.form();
                var start = $("#startDate").val();
                var end = $("#endDate").val();
                if (start <= end) {
                    submitDialog();
                }
                else {
                    alert("开始时间不能大于结束时间！");
                }
                ;
                return false;
            },
            "取消": function () {
                $("#dialog-form").omDialog("close");
            }
        }, onClose: function () {
        }
    });
    var start = new Date();
    var today = new Date();
    start.setDate(today.getDate() - 1);
    $("#startDate").omCalendar(
            {dateFormat: 'yy-mm-dd H:i:s',
                showTime: true,
                minDate: start
            });
    $("#endDate").omCalendar(
            {dateFormat: 'yy-mm-dd H:i:s',
                showTime: true,
                minDate: start
            });
    $('#isAll').omCombo({
        dataSource: [
            {text: '是', value: '1'},
            {text: '否', value: '0'}
        ], value: '0'
    });
    $('#create').bind('click', function () {
        isAdd = true;
        $('#softwarePackageId').omCombo({
            dataSource: 'apk_soft_package_by_softCode.json?par=0', value: "", editable: false, width: 181, listMaxHeight: 150});
        $('#softCodeId').omCombo({
            dataSource: 'apk_soft_code.json', value: '', editable: false, width: 181, listMaxHeight: 150, onValueChange: function (target, newValue, oldValue, event) {
                getSoftwarePackage();
            }});
        showDialog('新增APK升级软件任务信息');//显示dialog
    });
    var submitDialog = function () {
        var submitData;
        if (validator.form()) {
            if (isAdd) {
                submitData = {
                    taskName : $("#taskName").val(),
                    softCodeId: $("#softCodeId").val(),
                    softwarePackageId: $("#softwarePackageId").val(),
                    versionSeq: $("#versionSeq").val(),
                    vendorIds: $("#vendorIds").val(),
                    maxNum: $("#maxNum").val(),
                    timeInterval: $("#timeInterval").val(),
                    isAll: $("#isAll").val(),
                    startDate: $("#startDate").val(),
                    endDate: $("#endDate").val()
                };
                $.post('apkUpgrade_task_add.json', submitData, function (result) {
                    $('#grid').omGrid('reload');
                    if ("success" == result) {
                        $.omMessageTip.show({title: tip, content: "添加APK升级任务信息成功！", type: "success", timeout: time});
                    } else {
                        $.omMessageTip.show({title: tip, content: "添加APK升级任务信息失败！", type: "error", timeout: time});
                    }
                    $("#dialog-form").omDialog("close");
                });
            } else {
                submitData = {
                    id: $("#id").val(),
                    taskName : $("#taskName").val(),
                    softCodeId: $("#softCodeId").val(),
                    softwarePackageId: $("#softwarePackageId").val(),
                    versionSeq: $("#versionSeq").val(),
                    vendorIds: $("#vendorIds").val(),
                    maxNum: $("#maxNum").val(),
                    timeInterval: $("#timeInterval").val(),
                    isAll: $("#isAll").val(),
                    startDate: $("#startDate").val(),
                    endDate: $("#endDate").val()
                };
                $.post('apkUpgrade_task_update.json', submitData, function (result) {
                    $('#grid').omGrid('reload');
                    if ("success" == result) {
                        $.omMessageTip.show({title: tip, content: "修改APK升级任务信息成功！", type: "success", timeout: time});
                    } else {
                        $.omMessageTip.show({title: tip, content: "修改APK升级任务信息失败！", type: "error", timeout: time});
                    }
                    $("#dialog-form").omDialog("close");
                });
            }
        }
    };
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
            url: "apkUpgrade_task_to_update.json?id=" + selections[0].id,
            dataType: "json",
            success: function (msg) {
                $("#id").val(msg['id']);
                $("#taskName").val(msg['taskName']);
                $('#softCodeId').omCombo({
                    dataSource: 'apk_soft_code.json', editable: false, width: 180, listMaxHeight: 150, value: msg['softCodeId'].id, onValueChange: function (target, newValue, oldValue, event) {
                        getSoftwarePackageUpdate(msg['softwarePackageId'].id, msg['softCodeId'].id);
                    }});
                $("#versionSeq").val(msg['versionSeq']);
                $("#startDate").val(msg['startDate']);
                $("#endDate").val(msg['endDate']);
                $('#isAll').omCombo({
                    dataSource: [
                        {text: '是', value: '1'},
                        {text: '否', value: '0'}
                    ], value: msg['isAll']
                });
                $("#maxNum").val(msg['maxNum']);
                $("#timeInterval").val(msg['timeInterval'])
            }
        });

        showDialog('修改APK升级软件任务信息', selections[0]);
    });
    var validator = $('#myform').validate({
        rules: {
            taskName : {required: true, maxlength: 11},
            softCodeId: {required: true},
            versionSeq: {required: true, digits: true, maxlength: 9},
            softwarePackageId: {required: true},
            maxNum: {required: true, digits: true, maxlength: 9},
            isAll: {required: true},
            startDate: {required: true},
            endDate: {required: true},
            timeInterval: {required: true, digits: true, maxlength: 9}
        },
        messages: {
            taskName: {required: "升级任务名称不能为空！"},
            softCodeId: {required: "软件信息号不能为空！"},
            versionSeq: {required: "软件版本号不能为空！", digits: "请输入数字！", maxlength: "最大长度为9位字符"},
            softwarePackageId: {required: "设备软件包不能为空！"},
            maxNum: {required: "最大升级终端数不能为空！", digits: "请输入数字！", maxlength: "最大长度为9位字符"},
            isAll: {required: "是否全量升级不能为空！"},
            startDate: {required: "开始时间不能为空！"},
            endDate: {required: "结束时间不能为空！"},
            timeInterval: {required: "间隔时间不能为空！", digits: "请输入数字！", maxlength: "最大长度为9位字符"}
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
})
function getSoftwarePackage() {
    var softwareCode = $('#softCodeId').omCombo('value');
    $('#softwarePackageId').omCombo({dataSource: 'apk_soft_package_by_softCode.json?par=0&softwareCodeId=' + softwareCode, editable: false, width: 180, listMaxHeight: 150, value: ''});
}
function getSoftwarePackageUpdate(softwarePackageId, softCodeId) {
    var softwareCode = $('#softCodeId').omCombo('value');
    if (softwareCode == softCodeId) {
        $('#softwarePackageId').omCombo({dataSource: 'apk_soft_package_by_softCode.json?par=0&softwareCodeId=' + softwareCode, editable: false, width: 180, listMaxHeight: 150, value: softwarePackageId});
    } else {
        $('#softwarePackageId').omCombo({dataSource: 'apk_soft_package_by_softCode.json?par=0&softwareCodeId=' + softwareCode, editable: false, width: 180, listMaxHeight: 150, value: ''});
    }

}
</script>
</head>
<body>
<div id="center-tab">
		<ul>
			<li><a href="#tab1">APK软件包信息列表:</a></li>
		</ul>
	</div>
<table>
    <tr align="left">
        <c:if test="${sessionScope.add_apk_upgrade_task != null }">
            <td>
                <div id="create"></div>
            </td>
        </c:if>
        <c:if test="${sessionScope.update_apk_upgrade_task != null }">
            <td>
                <div id="update"></div>
            </td>
        </c:if>
        <c:if test="${sessionScope.delete_apk_upgrade_task != null }">
            <td>
                <div id="delete"></div>
            </td>
        </c:if>
        <c:if test="${sessionScope.apk_upgrade_task_bind != null }">
            <td>
                <div id="bind"/>
            </td>
        </c:if>
		<c:if test="${sessionScope.apk_upgrade_task_unbind != null }">
            <td>
                <div id="unbind"/>
            </td>
        </c:if>
        <c:if test="${sessionScope.apk_upgrade_task_device_list != null }">
            <td>
                <div id="device_list_of_group"/>
            </td>
        </c:if>
        <td>APK软件信息号：<input type="text" name="softCodeName" id="softCodeName"
                           style="width:150px;height: 20px;border:1px solid #86A3C4;"/></td>


        <td>APK软件升级包：<input type="text" name="softwarePackageName" id="softwarePackageName"
                           style="width:150px;height: 20px;border:1px solid #86A3C4;"/></td>

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
                <td style="text-align: right;"><span class="color_red">*</span>升级任务名称：</td>
                <td><input id="taskName" name="taskName"
                           style="width:180px;height:20px;border:1px solid #86A3C4;"/></td>
                <td><span></span></td>
            </tr>

            <tr>
                <td style="text-align: right;"><span class="color_red">*</span>软件信息号：</td>
                <td><input id="softCodeId" name="softCodeId" onchange="getSoftwarePackage()" style="width:161px"
                           readonly="readonly"/></td>
                <td><span></span></td>
            </tr>
            <tr>
                <td style="text-align: right;"><span class="color_red">*</span>设备软件包：</td>
                <td><input id="softwarePackageId" name="softwarePackageId" style="width:161px" readonly="readonly"/>
                </td>
                <td><span></span></td>
            </tr>

            <tr>
                <td style="text-align: right;"><span class="color_red">*</span>最大升级终端数：</td>
                <td><input id="maxNum" name="maxNum" style="width:180px;height:20px;border:1px solid #86A3C4;"
                           value="0"/></td>
                <td><span></span></td>
            </tr>

            <tr>
                <td style="text-align: right;"><span class="color_red">*</span>间隔时间(分钟)：</td>
                <td><input id="timeInterval" name="timeInterval"
                           style="width:180px;height:20px;border:1px solid #86A3C4;" value="0"/></td>
                <td><span></span></td>
            </tr>

            <tr>
                <td style="text-align: right;"><span class="color_red">*</span>是否全部升级：</td>
                <td><input id="isAll" name="isAll" style="width:161px"/></td>
                <td><span></span></td>
            </tr>

            <tr>
                <td align="right"><span class="color_red">*</span>开始时间：</td>
                <td><input id="startDate" name="startDate" style="width:161px;"/></td>
                <td><span></span></td>
            </tr>

            <tr>
                <td align="right"><span class="color_red">*</span>结束时间：</td>
                <td><input id="endDate" name="endDate" style="width:161px;"/></td>
                <td><span></span></td>
            </tr>

        </table>
    </form>
</div>
<div id="dialog-bind-form">
    <form id="myform-bind">
        <table>
            <tr>
                <td align="right">设备序列号：</td>
                <td><input type='text' name='textfield' id='textfield'
                           class='file_txt'/>
                    <input type='button' class='file_btn' value='浏览...'/>
                    <input type="file" name="deviceCodes" class="file_import" id="deviceCodes"
                           size="40"
                           onchange="document.getElementById('textfield').value=this.value"/>
                </td>
            </tr>
            <tr>
                <td style="width: 100px; text-align: right;">区域：</td>
                <td><input name="districtCode" id="districtCode" style="width:180px;"/></td>
                <td><span></span></td>
            </tr>
            <tr style="height: 3px; text-align: right;">
                <td align="right"></td>
                <td></td>
                <td><span></span></td>
            </tr>
            <tr>
                <td align="right">设备分组：</td>
                <td><input id="deviceGroupIds" name="deviceGroupIds" style="width:180px;"/></td>
                <td><span></span></td>
            </tr>
            <tr style="height: 3px; text-align: right;">
                <td align="right"></td>
                <td></td>
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
                <td> <div id="message" style="overflow-y: auto;"></div> </td>
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
                <td> <div id="deviceQuery"></div> </td>
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