<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title></title>
<%@ include file="/include/taglibs.jsp"%>
<%@ include file="/include/operamasks-ui-2.0.jsp"%>
<%@ include file="/include/css.jsp"%>
<%@ include file="/include/ysten.jsp"%>
<script type="text/javascript">
$(document).ready(function() {
	$('#center-tab').omTabs({height: "33", border: false});
    $('#grid').omGrid({
        dataSource : 'user_group_list.json',
        width : '100%',
        height : rFrameHeight-20,
        singleSelect : false,
        limit :limit,
        colModel : [
            {header : '<b>用户分组名称</b>', name : 'name', align : 'center', width : 120},
            {header : '<b>用户分组类型</b>', name : 'type',align : 'center',width: 70},
            {header: '<b>区域</b>', name: 'areaName', align: 'center', width: 50},
            {header : '<b>是否动态分组</b>', name : 'dynamicFlag',align : 'center',width: 70, renderer:function(value){if(value=='1'){return '是' }else{ return '否'}}},
            {header : '<b>动态分组sql表达式</b>', name : 'sqlExpression',align : 'center',width: 200},
            {header : '<b>关联的产品包编码</b>', name : 'productId',align : 'center',width: 120},
            {header : '<b>创建时间</b>', name : 'createDate',align : 'center',width: 120},
            {header : '<b>更新时间</b>', name : 'updateDate',align : 'center',width: 120},
            {header : '<b>描述</b>', name : 'description',align : 'center',width: 'autoExpand'}

        ]
    });
    $('#save').omButtonbar({btns : [{label:"新增",icons : {left : opPath+'add.png'}}]});
    $('#update').omButtonbar({btns : [{label:"修改",icons : {left : opPath+'op-edit.png'}}]});
    $('#delete').omButtonbar({btns : [{label:"删除",icons : {left : opPath+'remove.png'}}]});
    $('#type').omCombo({dataSource :'user_group_type.json?par=0',emptyText:'全部',value:'',editable:false,width:80,listMaxHeight:140});
    $('#type1').omCombo({dataSource :'user_group_type.json',value:'UPGRADE',editable:false,width:180,listMaxHeight:160});
    $('#dynamicFlag1').omCombo({dataSource : [
        {text:'是',value:'1'},
        {text:'否',value:'0'}
    ],
        value:'0',
        editable:false,
        width:120,
        listMaxHeight:120});
    $('#areaId1').omCombo({dataSource: 'area.json?par=0', value: '0', editable: false, width: 80, listMaxHeight: 140});
    $('#query').omButtonbar({btns : [{label:"查询",icons : {left : opPath+'search.png'}}]});
    $('#bind').omButtonbar({btns : [{label:"绑定用户",icons : {left : opPath+'op-btn-icon.png'}}]});
    $('#unbind').omButtonbar({btns : [{label:"解绑用户",icons : {left : opPath+'remove.png'}}]});
    $('#unbind_business').omButtonbar({btns : [{label:"解绑业务",icons : {left : opPath+'remove.png'}}]});
    $('#queryUser').omButtonbar({btns: [{label: "查询", icons: {left: opPath + 'search.png'}}]});
    $('#delUser').omButtonbar({btns : [{label:"移除",icons : {left : opPath+'remove.png'}}]});
    $('#relate_business').omButtonbar({btns : [{label:"关联业务", icons : {left : opPath+'op-edit.png'}}]});
    $('#customer_list_of_group').omButtonbar({btns: [{label: "关联用户", icons: {left: opPath + 'op-btn-icon.png'}}]});
    var isAdd = true;
/************************************************************************************************/
 
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
        showCustomerDialog("关联的用户");
        $('#gridUserList').omGrid({
            dataSource: 'customer_list_by_groupId.json?userGroupId=' + selections[0].id+'&tableName=bss_user_group_map&character=user_group_id',
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

    $('#delUser').bind('click', function (e) {
        $('#deleteType').omCombo({dataSource : [
            {text:'移除选中用户',value:'0'},
            {text:'移除全部用户',value:'1'}
        ],
            value:'0',
            width:180,
            listMaxHeight:160,
        });
        var dialog_export = $("#del-dialog-form").omDialog({
            width: 300,
            autoOpen : false,
            modal : true,
            resizable : false,
            draggable : false,
            buttons : {
                "提交" : function(){
                    var selections_device = $('#gridUserList').omGrid('getSelections', true);
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
                            toDeleteRecordID  += selections_device[i].code+",";
                        }else{
                            toDeleteRecordID  += selections_device[i].code;
                        }
                    }
                    if ($('#deleteType').omCombo('value') == 0){

                        $.ajax({
                            type: "POST",
                            url: "delete_user_by_userGroupId.json?userCodes="+encodeURIComponent(toDeleteRecordID.toString())+"&userGroupId="+selections[0].id,
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
                            url: "delete_user_by_userGroupId.json?userCodes="+encodeURIComponent(toDeleteRecordID.toString())+"&userGroupId="+selections[0].id,
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
                    }}}});
                    $("#del-dialog-form").omDialog("close");
                },
                "取消" : function() {
                    $("#del-dialog-form").omDialog("close");
                }
            },onClose:function(){
                $('#delUserMyform').validate().resetForm();
                $("#del-dialog-form").omDialog("close");
            }

        });


        dialog_export.omDialog("option", "title", "设备移除");
        dialog_export.omDialog("open");
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
        $('#gridUserList').omGrid("setData", 'customer_list_by_groupId.json?userGroupId=' + selections[0].id + '&userId='+encodeURIComponent(userId)+'&code='+encodeURIComponent(code)+'&phone='+encodeURIComponent(phone)+'&cRegion='+encodeURIComponent(region)+'&tableName=bss_user_group_map&character=user_group_id');
    });
    
/**********************************************************************************************/
    $('#query').bind('click', function(e) {

        var type = $('#type').val();
        var name = $('#name2').val();
        var areaId = $('#areaId1').val();
        $('#grid').omGrid("setData", 'user_group_list.json?name='+encodeURIComponent(name)+'&type='+encodeURIComponent(type)+'&areaId='+encodeURIComponent(areaId));
    });
/*********************************************************************************************/    
    $("#selectPpInfo").omButton();
    
    function showTable(title){
        table.omDialog("option", "title", title);
        table.omDialog("open");    	
    }
    
    var table = $("#pp-form").omDialog({
        width: 1000,height:440, autoOpen : false,resizable : false,
        draggable : false,modal : true,
        buttons : {
        	"确定" : function(){
        		var selections=$('#pp-grid').omGrid('getSelections',true);
        		ppCodes = "";
        		if(selections != null && selections.length > 0){
                	for(var i=0;i<selections.length;i++){
                    	if(i != selections.length - 1){
                    		ppCodes += selections[i].productId+",";
                        }else{
                        	ppCodes += selections[i].productId;
                        }
                    }        			
        		}
        		$("#productId").val(ppCodes);
        		$("#pp-form").omDialog("close");
        	},
    		"取消" : function(){
    			$("#pp-form").omDialog("close");
    		}
        }
    });
    $('#selectPpInfo').click(function(){
        $('#pp-grid').omGrid({
        	dataSource : 'get_ppList_infos.json',
        	width : 980, height : "99%",
            singleSelect : true,
            limit : 100,
            colModel : [ 
                         {header : '<b>产品包编号</b>', name : 'productId', align : 'center', width : 120}, 
                         {header : '<b>产品包名称</b>', name : 'productName', align : 'center', width : 120}, 
                         {header : '<b>产品包类型</b>', name : 'productType',align : 'center',width: 80},
                         {header : '<b>创建时间</b>', name : 'createDate',align : 'center',width: 120},
                         {header : '<b>生效时间</b>', name : 'validDate',align : 'center',width: 120},
                         {header : '<b>失效时间</b>', name : 'expireDate',align : 'center',width:120},
                         {header : '<b>来源</b>', name : 'source',align : 'center',width: 80}
    		]
        });    	
        showTable('产品包信息');
    });
    /****************************************************************************************************/
    $('#save').bind('click', function() {
        $('#showResult').html("");
        isAdd = true;
        $("#id").val('');
        $("#name1").val('');
        //$("#type1").attr('disabled',false);
        //$('#type1').omCombo({dataSource :'user_group_type.json',value:'UPGRADE',editable:false,width:180,listMaxHeight:160,disabled:false});
        
        // $('#productType').omCombo({dataSource :'pp_info_type.json?par=',editable:false,width:180,listMaxHeight:160,disabled:false});
        
        $('#dynamicFlag1').omCombo({dataSource : [
            {text:'是',value:'1'},
            {text:'否',value:'0'}
        ],
            value:'0',
            editable:false,
            width:180,
            listMaxHeight:160,
            onValueChange : dynamic_func_change
        });
        $('#type1').omCombo({dataSource :'user_group_type.json',
            value:'UPGRADE',
            editable:false,
            width:180,
            listMaxHeight:160,
            disabled:false,
            onValueChange : ppType_func_change
        });
        $('#areaId').omCombo({dataSource: 'area.json', value: '', editable: false, width: 180, listMaxHeight: 160, disabled: false});

        showDialog('新增用户分组信息');
    });

    $('#update').bind('click', function() {
        $('#showResult').html("");
        var selections=$('#grid').omGrid('getSelections',true);
        if (selections.length == 0 || selections.length > 1) {
            $.omMessageBox.alert({
                type:'alert',
                title:'温馨提示',
                content:'修改操作至少且只能选择一行记录！',
            });
            return false;
        }
        isAdd = false;
        $.ajax({
            type:"post",
            url:"user_group_to_update.json?id="+selections[0].id,
            dataType:"json",
            success:function(msg){
                $("#id").val(msg['id']);
                $("#name1").val(msg['name']);
                $("#sqlExpression1").val(msg['sqlExpression']);
                $("#description1").val(msg['description']);
                $('#productId').val(msg['productId']);
                //$('#productType').omCombo({dataSource :'pp_info_type.json',value:msg['productType'],editable:false,width:180,listMaxHeight:160,disabled:false});
                
               //  if(msg['type'] == 'PRODUCTPACKAGE'){
               //  		$('#productType').omCombo({dataSource :'pp_info_type.json',value:msg['productType'],editable:false,width:180,listMaxHeight:160,disabled:false});
               //       	}else{
               //       		$('#productType').omCombo({dataSource :'pp_info_type.json',editable:false,width:180,listMaxHeight:160,disabled:false});
               //           	}

                    //$('#type1').omCombo({dataSource :'user_group_type.json',value:msg['type'],editable:false,width:180,listMaxHeight:160,disabled:true});
                $('#areaId').omCombo({dataSource: 'area.json', value: selections[0].areaId, editable: false, width: 180, listMaxHeight: 160, disabled: true});
                $('#dynamicFlag1').omCombo({dataSource : [
                    {text:'是',value:'1'},
                    {text:'否',value:'0'}
                ],
                    value:msg['dynamicFlag'],
                    editable:false,
                    width:180,
                    listMaxHeight:160,
                    onValueChange : dynamic_func_change
                });
                $('#type1').omCombo({dataSource :'user_group_type.json',
                    value:msg['type'],
                    editable:false,
                    width:180,
                    listMaxHeight:160,
                    disabled:true,
                    onValueChange : ppType_func_change
                });

            }
        });
        //$("#type1").attr('disabled',true);
        showDialog('修改用户分组信息',selections[0]);
    });

    var dynamic_func_change = function(target, newValue){
        if(newValue == 0){//select '否'
            $("#trSql").hide();
        }else{
            $("#trSql").show();
        }
    }
    
    var ppType_func_change = function(target, newValue){
        if(newValue == "PRODUCTPACKAGE"){//select '是产品包类型'
            $("#openProductType").show();
            //$('#showResult1').html("请选择");
        }else{
            $("#openProductType").hide();
            //$('#showResult1').text("");
        }
    }
    var dialog = $("#dialog-form").omDialog({
        width: 550,
        autoOpen : false,
        modal : true,
        resizable : false,
        draggable : false,
        buttons : {
            "提交" : function(){
                validator.form();
                var t = $("#type1").omCombo('value');
                //var p = $("#productType").omCombo('value');
                var p = $("#productId").val();
                var info = $("#showResult2").html();
                if(t == "PRODUCTPACKAGE" && p==""){
                    return false;
                }
                if(info =='' || info =='可用!'){
                    submitDialog();
                }
            },
            "取消" : function() {
                $("#dialog-form").omDialog("close");
            }
        },onClose:function(){
            $('#myform').validate().resetForm();
        }
    });

    var showDialog = function(title,rowData){
        rowData = rowData || {};
        dialog.omDialog("option", "title", title);
        dialog.omDialog("open");
    };

    var submitDialog = function(){
        var submitData;
        if (validator.form()) {
            if(isAdd){
                submitData={
                    name:$("#name1").val(),
                    type:$("#type1").omCombo('value'),
                    areaId:$("#areaId").omCombo('value'),
                    //productType:$("#productType").omCombo('value'),
                    productId:$("#productId").val(),
                    dynamicFlag:$('#dynamicFlag1').omCombo('value'),
                    sqlExpression:$("#sqlExpression1").val(),
                    description:$("#description1").val()
                };
                $.post('user_group_add.shtml',submitData,function(result){
                    $('#grid').omGrid('reload');
                    /**
                     if("success" == result){
                        		$.omMessageTip.show({title: tip, content: "新增用户分组成功！", type:"success" ,timeout: time});
                        	}else{
                        		$.omMessageTip.show({title: tip, content: "新增用户分组失败！", type:"error" ,timeout: time});
                        	}*/
                    if(result.indexOf('成功') > 0){
                        $.omMessageTip.show({title: tip, content: result, type:"success" ,timeout: time});
                    }else{
                        $.omMessageTip.show({title: tip, content: result, type:"error" ,timeout: time});
                    }
                    $("#dialog-form").omDialog("close");
                });
            }else{
                submitData={
                    id:$("#id").val(),
                    name:$("#name1").val(),
                    areaId:$("#areaId").omCombo('value'),
                    type:$("#type1").omCombo('value'),
                    //productType:$("#productType").omCombo('value'),
                    productId:$("#productId").val(),
                    dynamicFlag:$("#dynamicFlag1").omCombo('value'),
                    sqlExpression:$("#sqlExpression1").val(),
                    description:$("#description1").val()
                };
                $.post('user_group_update.shtml',submitData,function(result){
                    $('#grid').omGrid('reload');
                    if(result.indexOf('成功') > 0){
                        $.omMessageTip.show({title: tip, content: result, type:"success" ,timeout: time});
                    }else{
                        $.omMessageTip.show({title: tip, content: result, type:"error" ,timeout: time});
                    }
                    $("#dialog-form").omDialog("close");
                });
            }
        }
    };

    var validator = $('#myform').validate({
        rules : {
            name1 : {required: true},
            type1 : {required: true},
            areaId :{required: true},
            dynamicFlag1 : {required: true}
        },
        messages : {
            name1 : {required : "用户分组名称不能为空！"},
            type1 : {required : "用户分组类型不能为空！"},
            areaId :{required: "请选择所属区域！"},
            dynamicFlag1 : {required : "是否动态分组不能为空！"}
        },
        errorPlacement : function(error, element) {
            if(error.html()){
                $(element).parents().map(function(){
                    if(this.tagName.toLowerCase()=='td'){
                        var attentionElement = $(this).next().children().eq(0);
                        attentionElement.html(error);
                    }
                });
            }
        },
        showErrors: function(errorMap, errorList) {
            if(errorList && errorList.length > 0){
                $.each(errorList,function(index,obj){
                    var msg = this.message;
                    $(obj.element).parents().map(function(){
                        if(this.tagName.toLowerCase()=='td'){
                            var attentionElement = $(this).next().children().eq(0);
                            attentionElement.show();
                            attentionElement.html(msg);
                        }
                    });
                });
            }else{
                $(this.currentElements).parents().map(function(){
                    if(this.tagName.toLowerCase()=='td'){
                        $(this).next().children().eq(0).hide();
                    }
                });
            }
            this.defaultShowErrors();
        }
    });
    /**************************************************************************************/
    $('#bind_result').hide();
    $('#bind').click(function(){
        var selections=$('#grid').omGrid('getSelections',true);
        if (selections.length == 0 || selections.length > 1) {
            $.omMessageBox.alert({
                type:'alert',
                title:'温馨提示',
                content:'绑定用户操作至少且只能选择一行记录！',
            });
            return false;
        }
        if(selections[0].dynamicFlag==1){
            $.omMessageBox.alert({
                type:'alert',
                title:'温馨提示',
                content:'动态分组不能做绑定用户操作！',
            });
            return false;
        }
        $("#textfield").val("");

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
                        url: 'bind_users.json',
                        secureuri: false,
                        fileElementId: 'fileField',
                        data: {ids: selections[0].id},
                        dataType: 'JSON',
                        success: function (result) {
                            							//$('#message').html(result);
                            							//showInfoDialog('关联用户结果');
                            $('#results').html(result);
                            //alert($('#results').text());
                            var msg = JSON.parse($('#results').text());
                           //alert(msg.info);
                            if(msg.info.indexOf('合法')>0){
                                if(msg.info.indexOf('不合法')>0){
                                	$.omMessageTip.show({title: tip, content: "绑定用户失败!"+ msg.info, timeout: time, type: 'error'});
                                    }else{
                                    	$.omMessageTip.show({title: tip, content: "绑定用户成功!", timeout: time, type: 'success'});
                                        }
                                }else{
                                    //alert("else");
                                    if(msg.userIds != ""){
                                    	$('#message').html(msg.info+"<br/>"+"确定继续绑定操作后，合法的用户将会从旧用户组解绑,并绑定到到指定的新用户组,请确认是否继续该操作?");
                                        $('#users').html(msg.userIds);
                                        showInfoDialog('确认绑定');
                                        }else{
                                        	$('#message').html("部分用户绑定失败!"+"<br/>"+msg.info);
                                        	$('#users').html(msg.userIds);
                                            showInfoDialog('确认绑定');
                                            }
                                	
                                    }
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
        dialog.omDialog("option", "title", "关联用户导入");
        dialog.omDialog("open");
    });
    
    var submitDialog_bind = function () {
    	var selections = $('#grid').omGrid('getSelections', true);
        var submitData;
            if($.trim($("#users").val()) != "") {
               submitData = {
                        userIds: $("#users").val(),
                        id: selections[0].id
                    };
                $.post('add_customer_group_maps.json', submitData, function (result) {
                    $('#grid').omGrid('reload');
                    if (result.indexOf("成功")>0) {
                        $.omMessageTip.show({title: tip, content: "关联用户成功!", type: "success", timeout: time});
                    } else {
                        $.omMessageTip.show({title: tip, content: "关联用户失败!" + result, type: "error", timeout: time});
                    }
                    $("#dialog-form3").omDialog("close");
                });
            } else {
                /**
                if($('#results').text().indexOf("合法")>0){
                	$.omMessageTip.show({title: tip, content: "关联用户成功!", type: "success", timeout: time});
                    }else{
                    	$.omMessageTip.show({title: tip, content: "部分用户关联失败!", type: "alert", timeout: time});
                        }*/
                        $('#grid').omGrid('reload');
            	$("#dialog-form3").omDialog("close");
            }
    };
    
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
     	    	$('#users').html();
     	        $('#myform3').validate().resetForm();
     	    }
        });
        var showInfoDialog = function (title) {
            dialogInfo.omDialog("option", "title", title);
            dialogInfo.omDialog("open");
        };
/********************************************************************************************/
        $('#unbind').bind('click', function(e) {
            var selections=$('#grid').omGrid('getSelections',true);
            if (selections.length == 0) {
                $.omMessageBox.alert({
                    type:'alert',
                    title:'温馨提示',
                    content:'解绑操作至少选择一行记录！',
                });
                return false;
            }
            if(selections[0].dynamicFlag==1){
                $.omMessageBox.alert({
                    type:'alert',
                    title:'温馨提示',
                    content:'动态分组不能做解绑用户操作！',
                });
                return false;
            }
            $.omMessageBox.confirm({
                title:'确认解绑',
                content:'解绑后用户与该分组绑定关系将删除且无法恢复，你确定要执行该操作吗？',
                onClose:function(v){
                    if(v==true){
                        var toDeleteRecordID = "";
                        for(var i=0;i<selections.length;i++){
                            if(i != selections.length - 1){
                                toDeleteRecordID  += selections[i].id+",";
                            }else{
                                toDeleteRecordID  += selections[i].id;
                            }
                        }
                        $.post('userGroup_user_map_delete.json',{ids:toDeleteRecordID.toString()},function(result){
                            $('#grid').omGrid('reload');
                            if(result=='success'){
                                $.omMessageTip.show({title: tip, content: "解绑用户成功！", timeout: time,type:'success'});
                            }else{
                                $.omMessageTip.show({title: tip, content: "解绑用户失败！", timeout: time,type:'error'});
                            }
                        });
                    }
                }
            });
        });

        $('#unbind_business').bind('click', function(e) {
            var selections=$('#grid').omGrid('getSelections',true);
            if (selections.length == 0) {
                $.omMessageBox.alert({
                    type:'alert',
                    title:'温馨提示',
                    content:'解绑操作至少选择一行记录！',
                });
                return false;
            }
            $.omMessageBox.confirm({
                title:'确认解绑',
                content:'解绑后该分组与业务的绑定关系将删除且无法恢复，你确定要执行该操作吗？',
                onClose:function(v){
                    if(v==true){
                        var toDeleteRecordID = "";
                        for(var i=0;i<selections.length;i++){
                            if(i != selections.length - 1){
                                toDeleteRecordID  += selections[i].id+",";
                            }else{
                                toDeleteRecordID  += selections[i].id;
                            }
                        }
                        $.post('userGroup_business_map_delete.json',{ids:toDeleteRecordID.toString()},function(result){
                            $('#grid').omGrid('reload');
                            if(result=='success'){
                                $.omMessageTip.show({title: tip, content: "解绑业务成功！", timeout: time,type:'success'});
                            }else{
                                $.omMessageTip.show({title: tip, content: "解绑业务失败！", timeout: time,type:'error'});
                            }
                        });
                    }
                }
            });
        });

    $('#relate_business').bind('click', function (e) {
        var selections=$('#grid').omGrid('getSelections',true);
        if (selections.length == 0 || selections.length > 1) {
            $.omMessageBox.alert({
                type:'alert',
                title:'温馨提示',
                content:'关联业务操作至少且只能选择一行记录！',
            });
            return false;
        }
        if (selections[0].id=="") {
            $.omMessageBox.alert({
                type:'alert',
                title:'温馨提示',
                content:'用户分组编号为空，不可做关联业务操作！',
            });
            return false;
        }
        var type = selections[0].type;
        $.get("get_user_group_relate_business.json",{groupId:selections[0].id},function(result){
            if(result != null){
                $('#notice10').omCombo({dataSource: 'get_sys_notice_list.json', editable: false, width: 182, multi: true, value: result['NOTICE'] || ''});
                $('#background10').omCombo({dataSource : 'get_background_image_list.json?',editable:false,width:182, multi: true, value:result['BACKGROUND'] || ''});
                $('#animation10').omCombo({dataSource : 'get_boot_animation_list.json?',editable:false,width:182,value:result['ANIMATION']});
                $('#panel10').omCombo({dataSource : 'get_panel_package_list.json?',editable:false,width:182,value:result['PANEL']});
                $.each($("#Tb10 tr"), function(i){
                    if(i > 0){
                        this.style.display = 'none';
                    }
                });
                if(type == "消息"){
                    $('#noticeTr').show();
                }
                if(type == "背景"){
                    $("#backgroundTr").show();
                }
                if(type == "开机动画"){
                    $("#animationTr").show();
                }
                if(type == "面板" || type == "产品包"){
                    $("#panelTr").show();
                }
            }else{
                $('#notice10').omCombo({dataSource: 'get_sys_notice_list.json', editable: false, width: 182, multi: true, value:''});
                $('#background10').omCombo({dataSource : 'get_background_image_list.json?',editable:false,width:182,multi:true, value:''});
                $('#animation10').omCombo({dataSource : 'get_boot_animation_list.json?',editable:false,width:182,value:''});
                $('#panel10').omCombo({dataSource : 'get_panel_package_list.json?',editable:false,width:182,value:''});
                $.each($("#Tb10 tr"), function(i){
                    if(i > 0){
                        this.style.display = 'none';
                    }
                });
                if(type == "消息"){
                    $('#noticeTr').show();
                }
                if(type == "背景"){
                    $("#backgroundTr").show();
                }
                if(type == "开机动画"){
                    $("#animationTr").show();
                }
                if(type == "面板" || type == "产品包"){
                    $("#panelTr").show();
                }
            }
        });

        var dialog = $("#dialog-form10").omDialog({
            width: 400,
            height: 160,
            autoOpen : false,
            modal : true,
            resizable : false,
            draggable : false,
            buttons : {
                "提交" : function(result){
                    submitData={
                        animation:$('#animation10').val(),
                        panel:$('#panel10').val(),
                        notice: $("#notice10").omCombo('value'),
                        background:$('#background10').val(),
                        ids:selections[0].id.toString()
                    };

                    $.post('user_group_bind_business.json', submitData, function(result){
                        $('#grid').omGrid('reload');
                        if(result.indexOf('成功') > 0){
                            $.omMessageTip.show({title: tip, content: result, timeout: time,type:'success'});
                        }
                        else if(result.indexOf('失败') > 0){
                            $.omMessageTip.show({title: tip, content: result, timeout: time,type:'error'});
                        }
                        else{
                            $.omMessageTip.show({title: tip, content: result, timeout: time,type:'alert'});
                        }
                        $("#dialog-form10").omDialog("close");
                    });

                    return false;
                },

                "解绑业务" : function(result){
                    if(selections[0].isLock=='锁定'){
                        $.omMessageBox.alert({
                            type:'alert',
                            title:'温馨提示',
                            content:'该条记录被锁定，请解锁后再做操作！',
                        });
                        return false;
                    }
                    $.omMessageBox.confirm({
                        title:'确认解绑',
                        content:'解绑后该分组与业务的绑定关系将删除且无法恢复，你确定要执行该操作吗？',
                        onClose:function(v){
                            if(v==true){
                                var toDeleteRecordID = "";
                                for(var i=0;i<selections.length;i++){
                                    if(i != selections.length - 1){
                                        toDeleteRecordID  += selections[i].id+",";
                                    }else{
                                        toDeleteRecordID  += selections[i].id;
                                    }
                                }
                                $.post('userGroup_business_map_delete',{ids:toDeleteRecordID.toString()},function(result){
                                    $('#grid').omGrid('reload');
                                    if(result=='success'){
                                        $.omMessageTip.show({title: tip, content: "解绑业务成功！", timeout: time,type:'success'});
                                    }else{
                                        $.omMessageTip.show({title: tip, content: "解绑业务失败！", timeout: time,type:'error'});
                                    }
                                    $("#dialog-form10").omDialog("close");
                                });
                            }
                        }
                    });
                },
                "取消" : function() {
                    $("#dialog-form10").omDialog("close");
                }
            },onClose:function(){
                $('#myform10').validate().resetForm();
            }
        });
        dialog.omDialog("option", "title", "关联业务信息");
        dialog.omDialog("open");
    });


        $('#delete').bind('click', function(e) {
            var selections=$('#grid').omGrid('getSelections',true);
            if (selections.length == 0) {
                $.omMessageBox.alert({
                    type:'alert',
                    title:'温馨提示',
                    content:'删除操作至少选择一行记录！',
                });
                return false;
            }
            $.omMessageBox.confirm({
                title:'确认删除',
                //content:'删除用户分组后将同时删除用户分组的绑定关系且无法恢复，你确定要执行该操作吗？',
                content:'删除用户分组信息后记录将无法恢复，你确定要执行该操作吗？',
                onClose:function(v){
                    if(v==true){
                        var toDeleteRecordID = "";
                        for(var i=0;i<selections.length;i++){
                            if(i != selections.length - 1){
                                toDeleteRecordID  += selections[i].id+",";
                            }else{
                                toDeleteRecordID  += selections[i].id;
                            }
                        }
                        $.post('user_group_delete.shtml',{ids:toDeleteRecordID.toString()},function(result){
                            $('#grid').omGrid('reload');
                            /**
                             if(result=='success'){
                            		$.omMessageTip.show({title: tip, content: "删除用户分组信息成功！", timeout: time,type:'success'});
                            	}else{
                            		$.omMessageTip.show({title: tip, content: "删除用户分组信息失败！", timeout: time,type:'error'});
                            	}*/
                            if(result.indexOf('成功') > 0){
                                $.omMessageTip.show({title: tip, content: result, type:"success" ,timeout: time});
                            }else{
                                $.omMessageTip.show({title: tip, content: result, type:"error" ,timeout: time});
                            }
                        });
                    }
                }
            });
        });
    });

    function checkIsValidate(){
        var sql = $('#sqlExpression1').val();
        if(""==sql){
            $('#showResult').html("");
        }else{
            var select = "SELECT";
            var from = "FROM";
            var selIndex = sql.toUpperCase().indexOf(select);
            var froIndex = sql.toUpperCase().indexOf(from);
            var length = sql.length;
            if(selIndex==0 && froIndex>0 && " "==sql.substring(selIndex+select.length,selIndex+select.length+1) && " "==sql.substring(froIndex+from.length,froIndex+from.length+1) && ""!=sql.substring(froIndex+from.length,length-1)){
                $('#showResult').html("");
                $.get('check_Input_Sql_validate.json?sql='+sql,function(result){
                    if(result.indexOf('success')>=0){
                        $('#showResult').html("");
                    }else{
                        $('#showResult').html(result);
                    }
                });
            }else{
                $('#showResult').html("请输入正确的查询语句!");
            }
        }
    }
    function checkNameExists(){
        if($.trim($("#name1").val())!=""){
            var par = new Object();
            par['name'] = $("#name1").val();
            par['type'] = $("#type1").val();
            par['id'] = $("#id").val();
            $.ajax({
                type:"post",
                url:"user_group_name_exists.shtml?s="+Math.random(),
                dataType:"html",
                data:par,
                success:function(msg){
                    $("#showResult2").html(msg);
                }
            });
        }else if($.trim($("#name1").val())=="" && $("#showResult2").html() != ""){
            $("#showResult2").html("");
        }
    }
</script>
</head>
<body>
<div id="center-tab">
    <ul>
        <li><a href="#tab1">用户分组信息列表:</a></li>
    </ul>
</div>
<table>
    <tr align="left">
        <c:if test="${sessionScope.save_user_group != null }">
            <td><div id="save"></div></td>
        </c:if>
        <c:if test="${sessionScope.update_user_group != null }">
            <td><div id="update"></div></td>
        </c:if>
        <c:if test="${sessionScope.delete_user_group != null }">
            <td><div id="delete"></div></td>
        </c:if>
        <c:if test="${sessionScope.bind_user != null }">
            <td><div id="bind"></div></td>
        </c:if>
        <c:if test="${sessionScope.unbind_user != null }">
            <td><div id="unbind"></div></td>
        </c:if>
        <c:if test="${sessionScope.unbind_business_user_group != null }">
            <td><div id="unbind_business"></div></td>
        </c:if>
        <td><div id="relate_business"></div></td>
        <c:if test="${sessionScope.customer_list_of_group != null }">
            <td>
                <div id="customer_list_of_group"></div>
            </td>
        </c:if>
         <td>用户分组名称：</td>
        <td><input id="name2" name="name2" style="width: 110px; height: 20px; border: 1px solid #86A3C4;" /></td>
        <td>用户分组类型：</td>
        <td><input id="type" name="type"/></td>
        <td>区域：</td>
        <td><input id="areaId1" name="areaId1"/></td>
        <td><div id="query"></div></td>
    </tr>

</table>
<table id="grid"></table>

<div id="dialog-form" style="display: none;">
    <form id="myform">
        <input type="hidden" value="" name="id" id="id"/>
        <table>
            <tr>
                <td style="width: 100px; text-align: right;"><span class="color_red">*</span>区域：</td>
                <td><input name="areaId" id="areaId"/></td>
                <td style="width: 200px;"><span></span></td>
            </tr>
            <tr>
                <td style="width: 100px; text-align: right;"><span class="color_red">*</span>用户分组类型：</td>
                <td><input name="type1" id="type1"/></td>
                <td style="width: 200px;"><span></span></td>
            </tr>
            <tr id="openProductType" style="display: none;">
                <td style="width: 100px; text-align: right;"><span class="color_red">*</span>产品包编码：</td>
                <td style="width: 230px;"><input  type="text" name="productId" id="productId" style="width: 180px; height: 20px; border: 1px solid #86A3C4;" readonly="readonly"/><input id="selectPpInfo" type="button" value="选择"/></td>
                <td style="width: 200px;"><span>请填写产品包编码</span>
                <!-- <div id="showResult1" class="color_red" style="display: inline;"/> --></td>
            </tr>
            <tr>
                <td style="width: 100px; text-align: right;"><span class="color_red">*</span>用户分组名称：</td>
                <td><input type="text" name="name1" id="name1"
                           style="width: 178px; height: 20px; border: 1px solid #86A3C4;"
                           maxlength="17"  onblur="checkNameExists()"/></td>
                <td style="width: 200px;"><span></span><div id="showResult2" class="color_red" style="display: inline;"/></td>
            </tr>
            <tr>
                <td style="width: 100px; text-align: right;"><span class="color_red">*</span>是否动态分组：</td>
                <td><input type="text" name="dynamicFlag1" id="dynamicFlag1"/></td>
                <td style="width: 200px;"><span></span></td>
            </tr>
            <tr id="trSql" style="display: none;">
                <td style="width: 100px; text-align: right;">sql表达式：</td>
                <td><textarea id="sqlExpression1" name="sqlExpression1" cols="28" rows="5" style="border:1px solid #86A3C4;" onblur="checkIsValidate();"></textarea></td>
                <td style="width: 200px;"><span>请输入查询语句</span><div id="showResult" class="color_red" style="display: inline;"/></td>
            </tr>
            <tr>
                <td style="width: 100px; text-align: right;">描述：</td>
                <td>
                    <textarea id="description1" name="description1" cols="27" rows="4" style="border:1px solid #86A3C4;"></textarea></td>
                <td style="width: 200px;"><span></span></td>
            </tr>
        </table>
    </form>
</div>
<div id="pp-div" style="display: none;">
		<form id="pp-form">
			<table id="pp-grid" ></table>
		</form>
	</div>	
<div id="dialog-form2" class="file_box" style="display: none;">
    <form id="myform2" action="bind_users.json" method="post"
          enctype="multipart/form-data">
        <input type="hidden" value="" name="ids" id="bindedUserGroupId"/>
        <table>
            <tr>
                <td style="width: 100px; text-align: right;">用户编号：</td>
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
 <input type="hidden" id="results" name="results"/>
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
                	<textarea id="users" name="users"></textarea>
                </td>
            </tr>
        </table>
    </form>
</div>

<div id="dialog-form10">
    <form id="myform10">
        <table id="Tb10">
            <tr></tr>
            <tr id="noticeTr" style="display: none;">
                <td align="right">消息信息：</td>
                <td><input name="notice10" id="notice10" style="width:180px;"/></td>
                <td><span></span></td>
            </tr>
            <tr></tr>
            <tr id="animationTr" style="display: none;">
                <td align="right">开机动画：</td>
                <td><input  name="animation10" id="animation10" style="width:180px;"/></td>
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
                <td><input  name="panel10" id="panel10" style="width:180px;"/></td>
                <td><span></span></td>
            </tr>
            <tr></tr>
        </table>
    </form>
</div>

<div id="dialog-form-customer-list" style="display: none;">
    <form id="customer-form">
        <table>
            <tr>
            <td>&nbsp;用户编号：</td>
			<td>
				<input  name="code1" id="code1" class="query-title-input"/>
			</td>
			<td>用户外部编号：</td>
			<td>
				<input  name="userId1" id="userId1" class="query-title-input"/>
			</td>
			<td>&nbsp;用户电话：</td>
			<td>
				<input  name="phone1" id="phone1" class="query-title-input"/>
			</td>
            <td>&nbsp;所属地市：</td>
            <td><input id="region1" name="region1"/></td>
			<td><div id="queryUser"/></td>
                <td>
                    <div id="delUser"></div>
                </td>
		</tr>
        </table>
        <table id="gridUserList"></table>
    </form>
</div>
<div id="del-dialog-form" style="display: none;">
    <form id="delUserMyform">
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