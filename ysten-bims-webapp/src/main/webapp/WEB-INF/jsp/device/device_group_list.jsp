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
        dataSource: 'device_group_list.json',
        width: '100%',
        height: rFrameHeight,
        singleSelect: false,
        limit: limit,
        colModel: [
            {header: '<b>设备分组名称</b>', name: 'name', align: 'center', width: 120},
            {header: '<b>设备分组类型</b>', name: 'type', align: 'center', width: 120},
            {header: '<b>区域</b>', name: 'areaName', align: 'center', width: 100},
            {header: '<b>创建时间</b>', name: 'createDate', align: 'center', width: 120},
//               {header : '<b>是否动态分组</b>', name : 'dynamicFlag',align : 'center',width: 60, renderer:function(value){if(value=='1'){return '是' }else{ return '否'}}},
//               {header : '<b>动态分组sql表达式</b>', name : 'sqlExpression',align : 'center',width: 330},
            {header: '<b>更新时间</b>', name: 'updateDate', align: 'center', width: 120},
            {header: '<b>描述</b>', name: 'description', align: 'center', width: 'autoExpand'}
        ]
        /* onRowDblClick: function (index, rowData, event) {
         showProgramDialog("所选设备分组下的设备列表");
         $('#gridDeviceList').omGrid({
         dataSource: 'device_list_by_groupId.json?deviceGroupId=' + rowData.id,
         width: '100%',
         height: rFrameHeight - 100,
         limit: limit,
         colModel: [
         {header: '<b>设备编号</b>', name: 'ystenId', align: 'center', width:155},
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
         {header : '<b>地市</b>', name : 'city',align : 'center',width: 50,renderer:function(value){
         if(value==null || value==""){
         return "";
         }else{
         return value.name;
         }
         }},
         {header : '<b>软件号</b>', name : 'softCode',align : 'center',width: 90},
         {header : '<b>软件版本号</b>', name : 'versionSeq',align : 'center',width: 70}
         ]
         });
         }*/
    });

    function showProgramDialog(title) {
        var dialogDeviceList = $("#dialog-form-device-list").omDialog({
            width: 1000, height: 550, autoOpen: false, resizable: false,
            draggable: false, modal: true
        });
        dialogDeviceList.omDialog("option", "title", title);
        dialogDeviceList.omDialog("open");
    }

    $('#deviceQuery').omButtonbar({btns: [
        {label: "查询", icons: {left: opPath + 'search.png'}}
    ]});
    $('#deviceExport').omButtonbar({btns: [
        {label: "导出", icons: {left: opPath + 'op-btn-icon.png'}}
    ]});
    $('#save').omButtonbar({btns: [
        {label: "新增", icons: {left: opPath + 'add.png'}}
    ]});
    $('#update').omButtonbar({btns: [
        {label: "修改", icons: {left: opPath + 'op-edit.png'}}
    ]});
    $('#delete').omButtonbar({btns: [
        {label: "删除", icons: {left: opPath + 'remove.png'}}
    ]});
    $('#type').omCombo({dataSource: 'device_group_type.json?par=0', value: '', editable: false, width: 80, listMaxHeight: 160});
    $('#type1').omCombo({dataSource: 'device_group_type.json', value: 'UPGRADE', editable: false, width: 180, listMaxHeight: 160, disabled: false});
    $('#districtCode').omCombo({dataSource: 'area.json?par=0', value: '0', editable: false, width: 80, listMaxHeight: 160});
    /*$('#dynamicFlag1').omCombo({dataSource : [
     {text:'是',value:'1'},
     {text:'否',value:'0'}
     ],
     value:'0',
     editable:false,
     width:180,
     listMaxHeight:160});*/
    $('#query').omButtonbar({btns: [
        {label: "查询", icons: {left: opPath + 'search.png'}}
    ]});
    $('#bind').omButtonbar({btns: [
        {label: "绑定设备", icons: {left: opPath + 'op-btn-icon.png'}}
    ]});
    $('#unbind').omButtonbar({btns: [
        {label: "解绑设备", icons: {left: opPath + 'remove.png'}}
    ]});
    $('#unbind_business').omButtonbar({btns: [
        {label: "解绑业务", icons: {left: opPath + 'remove.png'}}
    ]});
    $('#device_list_of_group').omButtonbar({btns: [
        {label: "关联设备", icons: {left: opPath + 'op-btn-icon.png'}}
    ]});
    $('#delDevice').omButtonbar({btns: [
        {label: "移除", icons: {left: opPath + 'remove.png'}}
    ]});
    $('#relate_business').omButtonbar({btns: [
        {label: "关联业务", icons: {left: opPath + 'op-edit.png'}}
    ]});
    var isAdd = true;
    /*********************************************************************************/
    $('#bind_result').hide();
    var dialogInfo = $("#dialog-form3").omDialog({
        width: 700,
        height: 320,
        autoOpen: false,
        modal: true, resizable: false,
        draggable: false,
        buttons: {
            "确定": function () {
                $("#dialog-form3").omDialog("close");
                submitDialog_bind();
            },
            "关闭": function () {
                $("#dialog-form3").omDialog("close");
                return false;
            }
        }, onClose: function () {
            $('#ystens').html();
            $('#myform3').validate().resetForm();
        }
    });

    var submitDialog_bind = function () {
        var selections = $('#grid').omGrid('getSelections', true);
        var submitData;
        if ($.trim($("#ystens").val()) != "") {
            submitData = {
                ystenIds: $("#ystens").val(),
                id: selections[0].id
            };
            $.post('add_device_group_maps.json', submitData, function (result) {
                $('#grid').omGrid('reload');
                if (result.indexOf("成功") > 0) {
                    $.omMessageTip.show({title: tip, content: "关联设备成功!", type: "success", timeout: time});
                } else {
                    $.omMessageTip.show({title: tip, content: "关联设备失败!" + result, type: "error", timeout: time});
                }
                $("#dialog-form3").omDialog("close");
            });
        } else {
            /**
             if($('#results').text().indexOf("合法")>0){
                	$.omMessageTip.show({title: tip, content: "关联设备成功!", type: "success", timeout: time});
                    }else{
                    	$.omMessageTip.show({title: tip, content: "部分设备关联失败!", type: "alert", timeout: time});
                        }*/
            $('#grid').omGrid('reload');
            $("#dialog-form3").omDialog("close");
        }
    };

    var showInfoDialog = function (title) {
        dialogInfo.omDialog("option", "title", title);
        dialogInfo.omDialog("open");
    };
    /**********************************************************************************************************/
    $('#query').bind('click', function (e) {
        var type = $('#type').val();
        var name = $('#name2').val();
        var districtCode = $('#districtCode').val();
        $('#grid').omGrid("setData", 'device_group_list.json?name=' + encodeURIComponent(name) + '&type=' + encodeURIComponent(type) + '&districtCode=' + encodeURIComponent(districtCode));
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
        //if the group is dynamic, it can't do the bind and unbind operations
        if (selections[0].dynamicFlag == 1) {
            $.omMessageBox.alert({
                type: 'alert',
                title: '温馨提示',
                content: '动态分组不能做解绑设备操作！',
            });
            return false;
        }
        $.omMessageBox.confirm({
            title: '确认解绑',
            content: '解绑后设备与该分组绑定关系将删除且无法恢复，你确定要执行该操作吗？',
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
                    $.post('deviceGroup_device_map_delete.json', {ids: toDeleteRecordID.toString()}, function (result) {
                        $('#grid').omGrid('reload');
                        if (result == 'success') {
                            $.omMessageTip.show({title: tip, content: "解绑设备成功！", timeout: time, type: 'success'});
                        } else {
                            $.omMessageTip.show({title: tip, content: "解绑设备失败！", timeout: time, type: 'error'});
                        }
                    });
                }
            }
        });
    });

    $('#unbind_business').bind('click', function (e) {
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
            content: '解绑后该分组与业务的绑定关系将删除且无法恢复，你确定要执行该操作吗？',
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
                    $.post('deviceGroup_business_map_delete.json', {ids: toDeleteRecordID.toString()}, function (result) {
                        $('#grid').omGrid('reload');
                        if (result == 'success') {
                            $.omMessageTip.show({title: tip, content: "解绑业务成功！", timeout: time, type: 'success'});
                        } else {
                            $.omMessageTip.show({title: tip, content: "解绑业务失败！", timeout: time, type: 'error'});
                        }
                    });
                }
            }
        });
    });
    $('#bind').click(function () {
        var selections = $('#grid').omGrid('getSelections', true);
        if (selections.length == 0 || selections.length > 1) {
            $.omMessageBox.alert({
                type: 'alert',
                title: '温馨提示',
                content: '绑定设备操作至少且只能选择一行记录！'
            });
            return false;
        }
        //if the group is dynamic, it can't do the bind and unbind operations
        if (selections[0].dynamicFlag == 1) {
            $.omMessageBox.alert({
                type: 'alert',
                title: '温馨提示',
                content: '动态分组不能做绑定设备操作！'
            });
            return false;
        }

        $("#textfield").val("");
        /* $.get('get_deviceCodes_by_deviceGroupId.json', {id: selections[0].id}, function (msg) {
         $('#deviceCodes2').val(msg);
         });*/
        var dialog = $("#dialog-form2").omDialog({
            width: 600,
            autoOpen: false,
            modal: true,
            resizable: false,
            draggable: false,
            buttons: {
                "提交": function () {
                    if ($("#textfield").val().length == 0) {
                        $.omMessageBox.alert({type: 'alert', title: tip, content: '请选择文件后再提交！'});
                        return false;
                    }
                    if (!YST_APP.checkFileText($("#textfield").val())) {
                        return false;
                    }
                    $.ajaxFileUpload({
                        url: 'bind_devices.json',
                        secureuri: false,
                        fileElementId: 'fileField',
                        data: {ids: selections[0].id},
                        dataType: "JSON",
                        success: function (result) {
                            $('#results').html(result);
                            //alert($('#results').text());
                            var msg = JSON.parse($('#results').text());
                            //alert(msg.info);
                            //alert(msg.ystenIds);
                            if (msg.info.indexOf('合法') > 0) {
                                if (msg.info.indexOf('不合法') > 0) {
                                    $.omMessageTip.show({title: tip, content: "绑定设备失败!" + msg.info, timeout: time, type: 'error'});
                                } else {
                                    $.omMessageTip.show({title: tip, content: "绑定设备成功!", timeout: time, type: 'success'});
                                }
                            } else {
                                //alert("else");
                                if (msg.ystenIds != "") {
                                    $('#message').html(msg.info + "<br/>" + "确定继续绑定操作后，合法的设备将会从旧设备组解绑,并绑定到到指定的新设备组,请确认是否继续该操作?");
                                    $('#ystens').html(msg.ystenIds);
                                    showInfoDialog('确认绑定');
                                } else {
                                    $('#message').html("部分设备绑定失败!" + "<br/>" + msg.info);
                                    $('#ystens').html(msg.ystenIds);
                                    showInfoDialog('确认绑定');
                                }

                            }

                            /**
                             if (result.indexOf('成功') > 0) {
                                            $.omMessageTip.show({title: tip, content: result, timeout: time, type: 'success'});
                                        }
                             if (result.indexOf('失败') > 0) {
                                            $.omMessageTip.show({title: tip, content: result, timeout: time, type: 'error'});
                                        }
                             if(result.indexOf('未填写') >= 0){
                                            $.omMessageTip.show({title: tip, content: result, timeout: time, type: 'alert'});
                                        }*/
                        }
                    });
                    $("#dialog-form2").omDialog("close");
                },
                "取消": function () {
                    $("#dialog-form2").omDialog("close");
                }
            }, onClose: function () {
                $('#myform2').validate().resetForm();
            }
        });

        /*var dialog = $("#dialog-form2").omDialog({
         width: 500,
         height: 280,
         autoOpen: false,
         modal: true,
         resizable: false,
         draggable: false,
         buttons: {
         "提交": function (result) {
         submitData = {
         deviceCodes: $('#deviceCodes2').val(),
         ids: selections[0].id
         };
         $.post('bind_devices.json', submitData, function (result) {
         $('#grid').omGrid('reload');
         //alert(result);
         if (result.indexOf('成功') > 0) {
         $.omMessageTip.show({title: tip, content: result, timeout: time, type: 'success'});
         }
         if (result.indexOf('失败') > 0) {
         $.omMessageTip.show({title: tip, content: result, timeout: time, type: 'error'});
         }
         if(result.indexOf('未填写') == 0){
         $.omMessageTip.show({title: tip, content: result, timeout: time, type: 'alert'});
         }
         $("#dialog-form2").omDialog("close");
         });
         return false;
         },
         "取消": function () {
         $("#dialog-form2").omDialog("close");
         }
         }, onClose: function () {
         $('#myform2').validate().resetForm();
         }
         });*/
        dialog.omDialog("option", "title", "关联设备导入");
        dialog.omDialog("open");
    });

    $('#save').bind('click', function () {
        $('#showResult').html("");
        isAdd = true;
        $("#id").val('');
        $("#name1").val('');
        $('#type1').omCombo({dataSource: 'device_group_type.json', value: 'UPGRADE', editable: false, width: 184, listMaxHeight: 160, disabled: false});
        $('#districtCode1').omCombo({dataSource: 'area.json', value: '', editable: false, width: 184, listMaxHeight: 160, disabled: false});
        /*$('#dynamicFlag1').omCombo({dataSource : [
         {text:'是',value:'1'},
         {text:'否',value:'0'}
         ],
         value:'0',
         editable:false,
         width:180,
         listMaxHeight:160,
         onValueChange : dynamic_func_change
         });*/
        showDialog('新增设备分组信息');
    });

    var dynamic_func_change = function (target, newValue) {
        if (newValue == 0) {//select '否'
            $("#trSql").hide();
        } else {
            $("#trSql").show();
        }
    }

    $('#update').bind('click', function () {
        $('#showResult').html("");
        isAdd = false;
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
            url: "device_group_to_update.json?id=" + selections[0].id,
            dataType: "json",
            success: function (msg) {
                $("#id").val(msg['id']);
                $("#name1").val(msg['name']);
                $('#type1').omCombo({dataSource: 'device_group_type.json', value: msg['type'], editable: false, width: 184, listMaxHeight: 160, disabled: true});
                $('#districtCode1').omCombo({dataSource: 'area.json', value: selections[0].areaId, editable: false, width: 184, listMaxHeight: 160, disabled: true});
                /*  	$('#dynamicFlag1').omCombo({dataSource : [
                 {text:'是',value:'1'},
                 {text:'否',value:'0'}
                 ],
                 value:msg['dynamicFlag'],
                 editable:false,
                 width:180,
                 listMaxHeight:160,
                 onValueChange : dynamic_func_change
                 });*/
                $("#sqlExpression1").val(msg['sqlExpression']);
                $("#description1").val(msg['description'])
            }
        });

        /* var ui = $("#trSql");
         if($("#dynamicFlag1").omCombo('value')=='0'){//select '否'
         ui.style.display="none";
         }else{
         ui.style.display="block";
         }*/
        showDialog('修改设备分组信息', selections[0]);
    });

    var dialog = $("#dialog-form").omDialog({
        width: 510,
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
            $('#myform').validate().resetForm();
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
                    name: $("#name1").val(),
                    type: $("#type1").omCombo('value'),
                    areaId: $("#districtCode1").omCombo('value'),
//                    dynamicFlag:$("#dynamicFlag1").omCombo('value'),
                    dynamicFlag: 0,
                    sqlExpression: $("#sqlExpression1").val(),
                    description: $("#description1").val()
                };
                $.post('device_group_add.shtml', submitData, function (result) {
                    $('#grid').omGrid('reload');
                    if ("success" == result) {
                        $.omMessageTip.show({title: tip, content: "新增设备分组成功！", type: "success", timeout: time});
                    } else {
                        $.omMessageTip.show({title: tip, content: "新增设备分组失败,设备分组名称" + $("#name1").val() + "已经存在！", type: "error", timeout: time});
                    }
                    $("#dialog-form").omDialog("close");
                });
            } else {
                submitData = {
                    id: $("#id").val(),
                    name: $("#name1").val(),
                    type: $("#type1").omCombo('value'),
//                   		dynamicFlag:$("#dynamicFlag1").omCombo('value'),
                    dynamicFlag: 0,
                    sqlExpression: $("#sqlExpression1").val(),
                    description: $("#description1").val()
                };
                $.post('device_group_update.shtml', submitData, function (result) {
                    $('#grid').omGrid('reload');
                    if ("success" == result) {
                        $.omMessageTip.show({title: tip, content: "修改设备分组成功！", type: "success", timeout: time});
                    } else {
                        $.omMessageTip.show({title: tip, content: "修改设备分组失败,设备分组名称" + $("#name1").val() + "已经存在！", type: "error", timeout: time});
                    }
                    $("#dialog-form").omDialog("close");
                });
            }
        }
    };

    var validator = $('#myform').validate({
        rules: {
            name1: {required: true},
            type1: {required: true},
            districtCode1: {required: true}
//            	dynamicFlag1 : {required: true}
        },
        messages: {
            name1: {required: "设备分组名称不能为空！"},
            type1: {required: "设备分组类型不能为空！"},
            districtCode1: {required: "请选择所属区域！"}
//            	dynamicFlag1 : {required : "是否动态分组不能为空！"}
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
    /**********************************************show deviceList info of  group******************************************/
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
        showProgramDialog("所选设备分组下的设备列表");
        $('#gridDeviceList').omGrid({
            dataSource: 'device_list_by_groupId.json?deviceGroupId=' + selections[0].id + '&tableName=bss_device_group_map&character=device_group_id',
            width: '100%',
            height: rFrameHeight - 100,
            singleSelect: false,
            limit: limit,
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
        var deviceCode = $('#deviceCodes').val();
        var ystenId = $('#ystenIds').val();
        var mac = $('#macs').val();
        var sno = $('#snos').val();
        var softCode = $('#softCodes').val();
        var versionSeq = $('#versionSeqs').val();
        var reg = /^\d+$/;
        //var reg = /^\+?[1-9][0-9]*$/;
        //alert(reg.test(versionSeq));
        //alert(isNaN(versionSeq));
        if ((reg.test(versionSeq) && versionSeq != "") || versionSeq == "") {
            $('#gridDeviceList').omGrid("setData", 'device_list_by_groupId.json?deviceGroupId=' + selections[0].id + '&tableName=bss_device_group_map&character=device_group_id&ystenId=' + encodeURIComponent(ystenId) + '&deviceCode=' + encodeURIComponent(deviceCode) + '&mac=' + encodeURIComponent(mac) + '&sno=' + encodeURIComponent(sno) + '&softCode=' + encodeURIComponent(softCode) + '&versionSeq=' + encodeURIComponent(versionSeq));
        } else {
            $.omMessageBox.alert({
                type: 'alert',
                title: '温馨提示',
                content: '请正确填写软件版本序号,为正整数！',
            });
        }
    });
    /**********************************************relate business******************************************/
    $('#relate_business').bind('click', function (e) {
        var selections = $('#grid').omGrid('getSelections', true);
        if (selections.length == 0 || selections.length > 1) {
            $.omMessageBox.alert({
                type: 'alert',
                title: '温馨提示',
                content: '关联业务操作至少且只能选择一行记录！',
            });
            return false;
        }
        if (selections[0].id == "") {
            $.omMessageBox.alert({
                type: 'alert',
                title: '温馨提示',
                content: '设备分组编号为空，不可做关联业务操作！',
            });
            return false;
        }
        var type = selections[0].type;
        $.get("get_device_group_relate_business.json", {groupId: selections[0].id}, function (result) {
            if (result != null) {
                $('#notice10').omCombo({dataSource: 'get_sys_notice_list.json', editable: false, width: 182, multi: true, value: result['NOTICE'] || ''});
                $('#upgrade10').omCombo({dataSource: 'get_upgrade_task_list.json', editable: false, width: 182, multi: true, value: result['UPGRADE'] || ''});
                $('#apkUpgrade10').omCombo({dataSource: 'get_apk_upgrade_task_list.json', editable: false, width: 182, multi: true, value: result['APKUPGRADE'] || ''});
                $('#boot10').omCombo({dataSource: 'get_service_collect_list.json?', editable: false, width: 182, value: result['BOOTSTRAP']});
                $('#background10').omCombo({dataSource: 'get_background_image_list.json?', editable: false, width: 182, multi: true, value: result['BACKGROUND' || '']});
                $('#animation10').omCombo({dataSource: 'get_boot_animation_list.json?', editable: false, width: 182, value: result['ANIMATION']});
                $('#panel10').omCombo({dataSource: 'get_panel_package_list.json?', editable: false, width: 182, value: result['PANEL']});
                $.each($("#Tb10 tr"), function (i) {
                    if (i > 0) {
                        this.style.display = 'none';
                    }
                });
                if (type == "消息") {
                    $('#noticeTr').show();
                }
                if (type == "升级") {
                    $('#upgradeTr').show();
                }
                if (type == "APK升级") {
                    $('#apkUpgradeTr').show();
                }
                if (type == "开机引导初始化") {
                    $('#bootTr').show();
                }
                if (type == "背景") {
                    $("#backgroundTr").show();
                }
                if (type == "开机动画") {
                    $("#animationTr").show();
                }
                if (type == "面板") {
                    $("#panelTr").show();
                }
            } else {
                $('#notice10').omCombo({dataSource: 'get_sys_notice_list.json', editable: false, width: 184, multi: true, value: ''});
                $('#upgrade10').omCombo({dataSource: 'get_upgrade_task_list.json', editable: false, width: 182, multi: true, value: ''});
                $('#apkUpgrade10').omCombo({dataSource: 'get_apk_upgrade_task_list.json', editable: false, width: 182, multi: true, value: ''});
                $('#boot10').omCombo({dataSource: 'get_service_collect_list.json?', editable: false, width: 182, value: ''});
                $('#background10').omCombo({dataSource: 'get_background_image_list.json?', editable: false, width: 182, multi: true, value: ''});
                $('#animation10').omCombo({dataSource: 'get_boot_animation_list.json?', editable: false, width: 182, value: ''});
                $('#panel10').omCombo({dataSource: 'get_panel_package_list.json?', editable: false, width: 182, value: ''});
                $.each($("#Tb10 tr"), function (i) {
                    if (i > 0) {
                        this.style.display = 'none';
                    }
                });
                if (type == "消息") {
                    $('#noticeTr').show();
                }
                if (type == "升级") {
                    $('#upgradeTr').show();
                }
                if (type == "APK升级") {
                    $('#apkUpgradeTr').show();
                }
                if (type == "开机引导初始化") {
                    $('#bootTr').show();
                }
                if (type == "背景") {
                    $("#backgroundTr").show();
                }
                if (type == "开机动画") {
                    $("#animationTr").show();
                }
                if (type == "面板") {
                    $("#panelTr").show();
                }
            }
        });

        var dialog = $("#dialog-form10").omDialog({
            width: 400,
            height: 160,
            autoOpen: false,
            modal: true,
            resizable: false,
            draggable: false,
            buttons: {
                "提交": function (result) {
                    submitData = {
                        boot: $('#boot10').val(),
                        animation: $('#animation10').val(),
                        panel: $('#panel10').val(),
                        notice: $("#notice10").omCombo('value'),
                        background: $('#background10').val(),
                        upgrade: $('#upgrade10').omCombo('value'),
                        apkUpgrade: $('#apkUpgrade10').omCombo('value'),
                        ids: selections[0].id.toString()
                    };

                    $.post('device_group_bind_business.json', submitData, function (result) {
                        $('#grid').omGrid('reload');
                        if (result.indexOf('成功') > 0) {
                            $.omMessageTip.show({title: tip, content: result, timeout: time, type: 'success'});
                        }
                        else if (result.indexOf('失败') > 0) {
                            $.omMessageTip.show({title: tip, content: result, timeout: time, type: 'error'});
                        }
                        else {
                            $.omMessageTip.show({title: tip, content: result, timeout: time, type: 'alert'});
                        }
                        $("#dialog-form10").omDialog("close");
                    });

                    return false;
                },

                "解绑业务": function (result) {
                    if (selections[0].isLock == '锁定') {
                        $.omMessageBox.alert({
                            type: 'alert',
                            title: '温馨提示',
                            content: '该条记录被锁定，请解锁后再做操作！',
                        });
                        return false;
                    }
                    $.omMessageBox.confirm({
                        title: '确认解绑',
                        content: '解绑后该分组与业务的绑定关系将删除且无法恢复，你确定要执行该操作吗？',
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
                                $.post('device_group_business_map_delete.json', {ids: toDeleteRecordID.toString()}, function (result) {
                                    $('#grid').omGrid('reload');
                                    if (result == 'success') {
                                        $.omMessageTip.show({title: tip, content: "解绑业务成功！", timeout: time, type: 'success'});
                                    } else {
                                        $.omMessageTip.show({title: tip, content: "解绑业务失败！", timeout: time, type: 'error'});
                                    }
                                    $("#dialog-form10").omDialog("close");
                                });
                            }
                        }
                    });
                },
                "取消": function () {
                    $("#dialog-form10").omDialog("close");
                }
            }, onClose: function () {
                $('#myform10').validate().resetForm();
            }
        });
        dialog.omDialog("option", "title", "关联业务信息");
        dialog.omDialog("open");
    });

    $('#deviceExport').bind('click', function (e) {
        $('#exportType').omCombo({dataSource: [
            {text: '选中导出', value: '0'},
            {text: '全部导出', value: '1'}
        ],
            value: '0',
            width: 180,
            listMaxHeight: 160,
        });
        var dialog_export = $("#dialog-form4").omDialog({
            width: 300,
            autoOpen: false,
            modal: true,
            resizable: false,
            draggable: false,
            buttons: {
                "提交": function () {
                    var selections_device = $('#gridDeviceList').omGrid('getSelections', true);
                    if ($('#exportType').omCombo('value') == 0) {
                        if (selections_device.length == 0) {
                            $.omMessageBox.alert({
                                type: 'alert',
                                title: '温馨提示',
                                content: '选中导出至少选择一行记录！',
                            });
                            return false;
                        }
                    }
                    if (selections_device.length > 0 && $('#exportType').omCombo('value') == 0) {
                        var toDeleteRecordID = "";
                        for (var i = 0; i < selections_device.length; i++) {
                            if (i != selections_device.length - 1) {
                                toDeleteRecordID += selections_device[i].id + ",";
                            } else {
                                toDeleteRecordID += selections_device[i].id;
                            }
                        }
                        location.href = "device_export_byId.json?ids=" + encodeURIComponent(toDeleteRecordID.toString());
                    }
                    if ($('#exportType').omCombo('value') == 1) {
                        var random = Math.random();
                        var selections = $('#grid').omGrid('getSelections', true);
                        var deviceCode = $('#deviceCodes').val();
                        var ystenId = $('#ystenIds').val();
                        var mac = $('#macs').val();
                        var sno = $('#snos').val();
                        var softCode = $('#softCodes').val();
                        var versionSeq = $('#versionSeqs').val();

                        location.href = "export_device_list_by_groupId.json?random=" + random
                                + "&deviceGroupId=" + selections[0].id
                                + '&tableName=bss_device_group_map&character=device_group_id&ystenId=' + encodeURIComponent(ystenId)
                                + '&deviceCode=' + encodeURIComponent(deviceCode)
                                + '&mac=' + encodeURIComponent(mac)
                                + '&sno=' + encodeURIComponent(sno)
                                + '&softCode=' + encodeURIComponent(softCode)
                                + '&versionSeq=' + encodeURIComponent(versionSeq);
                    }
                    $("#dialog-form4").omDialog("close");
                },
                "取消": function () {
                    $("#dialog-form4").omDialog("close");
                }
            }, onClose: function () {
                $('#myform4').validate().resetForm();
                $("#dialog-form4").omDialog("close");
            }
        });
        dialog_export.omDialog("option", "title", "设备信息导出");
        dialog_export.omDialog("open");
    });

    $('#delDevice').bind('click', function (e) {
        $('#deleteType').omCombo({dataSource: [
            {text: '移除选中设备', value: '0'},
            {text: '移除全部设备', value: '1'}
        ],
            value: '0',
            width: 180,
            listMaxHeight: 160,
        });
        var dialog_export = $("#del-dialog-form").omDialog({
            width: 300,
            autoOpen: false,
            modal: true,
            resizable: false,
            draggable: false,
            buttons: {
                "提交": function () {
                    /*  $.omMessageBox.confirm({
                     title: '确认移除',
                     //content:'删除设备分组信息后将同时删除设备分组绑定关系且无法恢复，你确定要执行该操作吗？',
                     content: '移除设备记录将无法恢复，你确定要执行该操作吗？',
                     onClose: function (v) {
                     if (v == true) {*/
                    var selections_device = $('#gridDeviceList').omGrid('getSelections', true);
                    if ($('#deleteType').omCombo('value') == 0) {
                        if (selections_device.length == 0) {
                            $.omMessageBox.alert({
                                type: 'alert',
                                title: '温馨提示',
                                content: '移除选中至少选择一行记录！',
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
                                for (var i = 0; i < selections_device.length; i++) {
                                    if (i != selections_device.length - 1) {
                                        toDeleteRecordID += selections_device[i].ystenId + ",";
                                    } else {
                                        toDeleteRecordID += selections_device[i].ystenId;
                                    }
                                }
                                if ($('#deleteType').omCombo('value') == 0) {

                                    $.ajax({
                                        type: "POST",
                                        url: "delete_device_by_deviceGroupId.json?ystenIds=" + encodeURIComponent(toDeleteRecordID.toString()) + "&deviceGroupId=" + selections[0].id,
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
//                        location.href="delete_device_by_deviceGroupId.json?ystenIds="+encodeURIComponent(toDeleteRecordID.toString())+"&deviceGroupId="+selections[0].id;
                                }
                                else if ($('#deleteType').omCombo('value') == 1) {
//                        var random = Math.random();
                                    $.ajax({
                                        type: "POST",
                                        url: "delete_device_by_deviceGroupId.json?ystenIds=" + encodeURIComponent(toDeleteRecordID.toString()) + "&deviceGroupId=" + selections[0].id,
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


        dialog_export.omDialog("option", "title", "设备移除");
        dialog_export.omDialog("open");
    });
    /*************************************************************************************************************/
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
            //content:'删除设备分组信息后将同时删除设备分组绑定关系且无法恢复，你确定要执行该操作吗？',
            content: '删除设备分组信息后记录将无法恢复，你确定要执行该操作吗？',
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
                    $.post('device_group_delete.shtml', {ids: toDeleteRecordID.toString()}, function (result) {
                        $('#grid').omGrid('reload');
                        /**
                         if(result=='success'){
                            		$.omMessageTip.show({title: tip, content: "删除设备分组信息成功！", timeout: time,type:'success'});
                            	}else{
                            		$.omMessageTip.show({title: tip, content: "删除设备分组信息失败！", timeout: time,type:'error'});
                            	}
                         */
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

});

/****************************************************************************************/
function checkIsValidate() {
    var sql = $('#sqlExpression1').val();
    if ("" == sql) {
        $('#showResult').html("");
    } else {
        var select = "SELECT";
        var from = "FROM";
        var selIndex = sql.toUpperCase().indexOf(select);
        var froIndex = sql.toUpperCase().indexOf(from);
        var length = sql.length;
        if (selIndex == 0 && froIndex > 0 && " " == sql.substring(selIndex + select.length, selIndex + select.length + 1) && " " == sql.substring(froIndex + from.length, froIndex + from.length + 1) && "" != sql.substring(froIndex + from.length, length - 1)) {
            $('#showResult').html("");
            $.get('check_Device_Input_Sql_validate.json?sql=' + sql, function (result) {
                if (result.indexOf('success') >= 0) {
                    $('#showResult').html("");
                } else {
                    $('#showResult').html(result);
                }
            });
        } else {
            $('#showResult').html("请输入正确的查询语句!");
        }
    }
}

function updateSqlComponent(target, newValue) {
    if (newValue == 0) {//select '否'
        $("#trSql").hide();
    } else {
        $("#trSql").show();
    }

}
</script>
</head>
<body>
<div id="center-tab">
    <ul>
        <li><a href="#tab1">设备分组信息列表:</a></li>
    </ul>
</div>
<table>
    <tr align="left">
        <c:if test="${sessionScope.save_device_group != null }">
            <td>
                <div id="save"></div>
            </td>
        </c:if>
        <c:if test="${sessionScope.update_device_group != null }">
            <td>
                <div id="update"></div>
            </td>
        </c:if>
        <c:if test="${sessionScope.delete_device_group != null }">
            <td>
                <div id="delete"></div>
            </td>
        </c:if>
        <c:if test="${sessionScope.bind_device != null }">
            <td>
                <div id="bind"></div>
            </td>
        </c:if>
        <c:if test="${sessionScope.unbind_device != null }">
            <td>
                <div id="unbind"></div>
            </td>
        </c:if>
        <c:if test="${sessionScope.unbind_business != null }">
            <td>
                <div id="unbind_business"></div>
            </td>
        </c:if>
        <td>
            <div id="relate_business"></div>
        </td>
        <c:if test="${sessionScope.device_list_of_group != null }">
            <td>
                <div id="device_list_of_group"></div>
            </td>
        </c:if>
        <td>设备分组名称：</td>
        <td><input id="name2" name="name2" style="width: 100px; height: 20px; border: 1px solid #86A3C4;"/></td>
        <td>设备分组类型：</td>
        <td><input id="type" name="type"/></td>
        <td>区域：</td>
        <td><input id="districtCode" name="districtCode"/></td>
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
                <td style="width: 100px; text-align: right;"><span class="color_red">*</span>区域：</td>
                <td><input name="districtCode1" id="districtCode1"/></td>
                <td style="width: 200px;"><span></span></td>
            </tr>
            <tr>
                <td style="width: 100px; text-align: right;"><span class="color_red">*</span>设备分组名称：</td>
                <td><input type="text" name="name1" id="name1"
                           style="width: 182px; height: 20px; border: 1px solid #86A3C4;"
                           maxlength="17"/></td>
                <td style="width: 200px;"><span></span></td>
            </tr>
            <tr>
                <td style="width: 100px; text-align: right;"><span class="color_red">*</span>设备分组类型：</td>
                <td><input name="type1" id="type1"/></td>
                <td style="width: 200px;"><span></span></td>
            </tr>

            <%--<tr>
                <td style="width: 100px; text-align: right;"><span class="color_red">*</span>是否动态分组：</td>
                <td><input type="text" name="dynamicFlag1" id="dynamicFlag1"/></td>
                <td style="width: 200px;"><span></span></td>
            </tr>--%>
            <tr id="trSql" style="display: none;">
                <td style="width: 100px; text-align: right;">sql表达式：</td>
                <td><textarea id="sqlExpression1" name="sqlExpression1" cols="28" rows="5"
                              style="border:1px solid #86A3C4;" onblur="checkIsValidate()"></textarea>
                    <!--
                    <input type="text" name="sqlExpression1" id="sqlExpression1"
                        style="width: 180px; height: 20px; border: 1px solid #86A3C4;"/>
                     -->
                </td>
                <td style="width: 200px;"><span>请输入查询语句</span>

                    <div id="showResult" class="color_red" style="display: inline;"/>
                </td>
            </tr>
            <tr>
                <td style="width: 100px; text-align: right;">设备分组描述：</td>
                <td><textarea id="description1" name="description1" cols="28" rows="3"
                              style="border:1px solid #86A3C4;"></textarea></td>
                <!--
                <input type="text" name="description1" id="description1"
                    style="width: 180px; height: 20px; border: 1px solid #86A3C4;"/>
                 -->
                <td style="width: 200px;"><span></span></td>
            </tr>
        </table>
    </form>
</div>
<div id="dialog-form2" class="file_box" style="display: none;">
    <form id="myform2" action="bind_devices.json" method="post"
          enctype="multipart/form-data">
        <input type="hidden" value="" name="ids" id="bindedDeviceGroupId"/>
        <table>
            <tr>
                <td style="width: 100px; text-align: right;">设备编号：</td>
                <td><input type='text' name='textfield' id='textfield'
                           class='file_txt'/>
                    <input type='button' class='file_btn' value='浏览...'/>
                    <input type="file" name="fileField" class="file_import" id="fileField"
                           size="40"
                           onchange="document.getElementById('textfield').value=this.value"/>
                </td>
            </tr>
        </table>
    </form>
</div>

<div id="dialog-form-device-list" style="display: none;">
    <form id="device-form">
        <table style="width:auto;">
            <tr>
                <td>&nbsp;设备编号：</td>
                <td><input name="ystenIds" id="ystenIds" class="query-title-input"
                           style="width:150px;height: 20px;border:1px solid #86A3C4;"/></td>
                <td>&nbsp;设备序列号：</td>
                <td><input name="snos" id="snos" class="query-title-input"
                           style="width:150px;height: 20px;border:1px solid #86A3C4;"/></td>
                <td>&nbsp;MAC地址：</td>
                <td><input name="macs" id="macs" class="query-title-input"
                           style="width:150px;height: 20px;border:1px solid #86A3C4;"/></td>
            </tr>
        </table>
        <table>
            <tr>
                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;软件号：</td>
                <td><input name="softCodes" id="softCodes" class="query-title-input"
                           style="width:150px;height: 20px;border:1px solid #86A3C4;"/></td>
                <td>&nbsp;软件版本号：</td>
                <td><input name="versionSeqs" id="versionSeqs" class="query-title-input"
                           style="width:150px;height: 20px;border:1px solid #86A3C4;"/></td>
                <td>
                    <div id="deviceQuery"></div>
                </td>
                <td>
                    <div id="deviceExport"></div>
                </td>
                <td>
                    <div id="delDevice"></div>
                </td>
            </tr>
        </table>
        <table id="gridDeviceList"></table>
    </form>
</div>

<div id="dialog-form3" style="display: none;">
    <form id="myform3">
        <table>
            <tr>
                <td style="width: 100px; text-align: right;"></td>
                <td>
                    <div id="message" style="overflow-y: auto;"></div>
                </td>
            </tr>
            <tr id="bind_result">
                <td colspan="2">
                    <textarea id="ystens" name="ystens"></textarea>
                </td>
            </tr>
        </table>
    </form>
</div>

<div id="dialog-form4" style="display: none;">
    <form id="myform4">
        <table>
            <tr>
                <td>导出方式：</td>
                <td>
                    <input name="exportType" id="exportType"/>
                </td>
            </tr>
        </table>
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
<input type="hidden" id="results" name="results"/>

<div id="dialog-form10">
    <form id="myform10">
        <table id="Tb10">
            <tr id="bootTr" style="display: none;">
                <td align="right">开机引导初始化：</td>
                <td><input name="boot10" id="boot10" style="width:180px;"/></td>
                <td><span></span></td>
            </tr>
            <tr></tr>
            <tr id="noticeTr" style="display: none;">
                <td align="right">消息信息：</td>
                <td><input name="notice10" id="notice10" style="width:180px;"/></td>
                <td><span></span></td>
            </tr>
            <tr></tr>
            <tr id="animationTr" style="display: none;">
                <td align="right">开机动画：</td>
                <td><input name="animation10" id="animation10" style="width:180px;"/></td>
                <td><span></span></td>
            </tr>
            <tr></tr>
            <tr id="backgroundTr" style="display: none;">
                <td align="right">背景轮换：</td>
                <td><input name="background10" id="background10" style="width:180px;"/></td>
                <td><span></span></td>
            </tr>
            <tr></tr>
            <tr id="panelTr" style="display: none;">
                <td align="right">面板包：</td>
                <td><input name="panel10" id="panel10" style="width:180px;"/></td>
                <td><span></span></td>
            </tr>
            <tr></tr>
            <tr id="upgradeTr" style="display: none;">
                <td align="right">终端升级：</td>
                <td><input name="upgrade10" id="upgrade10" style="width:180px;"/></td>
                <td><span></span></td>
            </tr>
            <tr></tr>
            <tr id="apkUpgradeTr" style="display: none;">
                <td align="right">APK升级：</td>
                <td><input name="apkUpgrade10" id="apkUpgrade10" style="width:180px;"/></td>
                <td><span></span></td>
            </tr>
            <!--tr>
                <td align="right">应用升级：</td>
                <td><input name="appUpgrade10" id="appUpgrade10" style="width:180px;"/></td>
                <td><span></span></td>
            </tr-->
        </table>
    </form>
</div>

</body>
</html>