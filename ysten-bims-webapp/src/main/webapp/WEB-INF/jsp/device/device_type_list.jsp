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
	$('#center-tab').omTabs({height:"33",border:false});
    $('#grid').omGrid({
    	dataSource : 'device_type_list.json',
    	width : '100%',
        height : rFrameHeight,
        singleSelect : true,
        limit : limit,
        colModel : [ 
                     {header : '<b>设备厂商名称</b>', name : 'deviceVendor', align : 'center', width : 160,renderer:function(value){return value.name;}},
                     {header : '<b>设备型号名称</b>', name : 'name', align : 'center', width : 160}, 
                     {header : '<b>设备型号编号</b>', name : 'code', align : 'center', width : 160},
                     {header : '<b>终端类型</b>', name : 'terminalType', align : 'center', width : 100},
                     {header : '<b>状态</b>', name : 'state',align : 'center',width: 60},
                     {header : '<b>创建时间</b>', name : 'createDate',align : 'center',width:120},
                     {header : '<b>描述</b>', name : 'description',align : 'center',width: 'autoExpand'}
		]
    });

    $('#deviceVendor').omCombo({dataSource : 'device_vendor.json?par=0',editable:false,width:100,listMaxHeight:100,value:''});

    $('#create').omButtonbar({btns : [{label:"新增",icons : {left : opPath+'add.png'}}]});
    $('#update').omButtonbar({btns : [{label:"修改",icons : {left : opPath+'op-edit.png'}}]});
    $('#query').omButtonbar({btns : [{label:"查询",icons : {left : opPath+'search.png'}}]});
    $('#query').bind('click', function(e) {
        var deviceTypeName = $('#deviceTypeName').val();
        var deviceTypeCode = $('#deviceTypeCode').val();
        var deviceVendor = $('#deviceVendor').omCombo('value');
        $('#grid').omGrid("setData", 'device_type_list.json?deviceTypeName='+encodeURIComponent(deviceTypeName)+'&deviceTypeCode='+deviceTypeCode+'&deviceVendor='+deviceVendor);
    });

    var dialog = $("#dialog-form").omDialog({
        width: 500,
        autoOpen : false,
        modal : true,
        resizable : false,
        draggable : false,
        buttons : {
            "提交" : function(){
            	validator.form();
            	if($("#description").val().length>2000){
 					$("#desc").text("描述不能超过2000个字符！");
 					return false;
                 }else($("#desc").text(""))
            	var sr = $("#showResult").html();
            	var sr1 = $("#showResult1").html();
                if((sr=='' || sr =='可用!') && (sr1=='' || sr1 =='可用!') && $("#desc").html()==''){
                	submitDialog();
                }
                return false; 
            },
            "取消" : function() {
            	resetData();
                $("#dialog-form").omDialog("close");
            }
        },onClose:function(){resetData();}
    });
    
    var showDialog = function(title,rowData){
        validator.resetForm();
        rowData = rowData || {};
        dialog.omDialog("option", "title", title);
        dialog.omDialog("open");
    };

    var submitDialog = function(){
        var submitData;
        if (validator.form()) {
            if(isAdd){
            	submitData={
            			deviceVendor1:$("#deviceVendor1").omCombo('value'),
            			deviceTypeName1:$("#deviceTypeName1").val(),
            			deviceTypeCode1:$("#deviceTypeCode1").val(),
            			terminalType:$("#terminalType").omCombo('value'),
            			deviceTypeState:$("#deviceTypeState").omCombo('value'),
            			description:$("#description").val(),
                    };
            	$.post('device_type_add.shtml',submitData,function(result){
                    $('#grid').omGrid('reload');
                    if("success" == result){
                    	$.omMessageTip.show({title: tip, content: "新增设备型号成功！", type:"success" ,timeout: time});
                    }else{
                    	$.omMessageTip.show({title: tip, content: "新增设备型号失败！", type:"error" ,timeout: time});
                    }
                    $("#dialog-form").omDialog("close");
                });
            }else{
            	submitData={
                    	id:$("#deviceTypeId").val(),
            			deviceVendor1:$("#deviceVendor1").omCombo('value'),
            			deviceTypeName1:$("#deviceTypeName1").val(),
            			deviceTypeCode1:$("#deviceTypeCode1").val(),
            			terminalType:$("#terminalType").omCombo('value'),
            			deviceTypeState:$("#deviceTypeState").omCombo('value'),
            			description:$("#description").val(),
                    };
            	$.post('device_type_update.shtml',submitData,function(result){
                    $('#grid').omGrid('reload');
                    if("success" == result){
                    	$.omMessageTip.show({title: tip, content: "修改设备型号成功！", type:"success" ,timeout: time});
                    }else{
                    	$.omMessageTip.show({title: tip, content: "修改设备型号失败！", type:"error" ,timeout: time});
                    }
                    $("#dialog-form").omDialog("close"); 
                });
            }
        }
    };
    
    var isAdd = true;
    $('#create').bind('click', function() {
        isAdd = true;
        validator.resetForm();
        $("#deviceTypeCode1").attr('disabled',false);
        getSelectDate('','','');
        showDialog('新增设备型号信息');
    });

    $('#update').bind('click', function() {
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
           	url:"device_type_to_update.json?id="+selections[0].id,
           	dataType:"json",
          	success:function(msg){
              	$("#deviceTypeId").val(msg['id']);
          		getSelectDate(msg['deviceVendor']['id']+'',msg['terminalType'],msg['state']);
    			$("#deviceTypeName1").val(msg['name']);
    			$("#deviceTypeCode1").val(msg['code']);
    			$("#deviceTypeCode1").attr('disabled',true);
    			$("#description").val(msg['description']);
          }
          });
        
        showDialog('修改设备型号信息',selections[0]);
    });
    
    var validator = $('#myform').validate({
        rules : {
        	deviceTypeName1 : {required: true}, 
        	deviceTypeCode1 : {required : true},
        	deviceVendor1 : {required : true},
        	terminalType : {required : true},
        	deviceTypeState : {required : true},
        }, 
        messages : {
        	deviceTypeName1 : {required : "名称不能为空！"},
        	deviceTypeCode1 : {required : "不能为空且只能为4位数字！"},
        	deviceVendor1 : {required : "请选择设备厂商！"},
        	terminalType : {required : "请选择终端类型！"},
        	deviceTypeState: {required : "请选择设备型号状态！"}
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
});

function getSelectDate(deviceVendorId,terminalType,deviceTypeState){
	$('#deviceVendor1').omCombo({dataSource : 'device_vendor.json',editable:false,width:150,listMaxHeight:100,value:deviceVendorId});
	$('#terminalType').omCombo({dataSource : 'device_terminal_type.json',editable:false,width:150,listMaxHeight:100,value:terminalType});
	$('#deviceTypeState').omCombo({dataSource : [ {text : '可用', value : 'USABLE'},{text : '不可用', value : 'UNUSABLE'}],editable:false,width:150,listMaxHeight:100,value:deviceTypeState});
}

function checkDeviceTypeNameExists(){
	if($.trim($("#deviceTypeName1").val())!=""){
	   var par = new Object();
	   par['deviceTypeName'] = $("#deviceTypeName1").val();
	   par['deviceVendor'] = $("#deviceVendor1").val();
	   par['id'] = $("#deviceTypeId").val();
       $.ajax({
       type:"post",
       url:"device_type_name_exists.shtml?s="+Math.random(),
       dataType:"html",
       data:par,
       success:function(msg){  
           $("#showResult").html(msg);
       }
      });
    }else if($.trim($("#deviceTypeName1").val())=="" && $("#showResult").html() != ""){
    	$("#showResult").html("");
        }
}

function checkDeviceTypeCodeExists(){
	var bool = checkNumberStr();
	if($("#deviceTypeCode1").val()!="" && bool){
       $.ajax({
       type:"post",
       url:"device_type_code_exists.shtml?s="+Math.random(),
       dataType:"html",
       data:"deviceTypeCode="+$("#deviceTypeCode1").val()
       +"&deviceVendor="+$("#deviceVendor1").val(),
       success:function(msg){  
           $("#showResult1").html(msg);
       }
      });
    }else if($.trim($("#deviceTypeName1").val())=="" && $("#showResult").html() != ""){
    	$("#showResult").html("");
    }
 }
function resetData(){
	$("#showResult").html('');
	$("#showResult1").html('');
}

function checkNumberStr() {
	var code = $.trim($("#deviceTypeCode1").val());
    if(code.match(/\D/) == null && code.length==3){
    	$("#showResult1").text("");
    	return true;
    }else{
    	$("#showResult1").text("编号不能为空且只能为3位数字！");
    	return false;
    }
}


</script>
</head>
<body>
<div id="center-tab">
<ul>
	 <li><a href="#tab1">设备型号信息列表:</a></li>
</ul>
</div>
<table >
     <tbody>
        <tr>
        <c:if test="${sessionScope.add_device_type != null }">
            <td><div id="create"/></td>
        </c:if>
        <c:if test="${sessionScope.update_device_type != null }">
            <td><div id="update"/></td>
        </c:if>
            <td style="text-align:center;">&nbsp;设备型号名称：</td>
            <td>
               <input type="text" name="deviceTypeName" id="deviceTypeName" class="query-title-input"/></td>
            <td style="text-align:center;">&nbsp;设备型号编号：</td>
            <td>
               <input name="deviceTypeCode" id="deviceTypeCode" class="query-title-input"/></td>
            <td style="text-align:center;">&nbsp;设备厂商：</td>
           <td>
               <input name="deviceVendor" id="deviceVendor" class="query-title-input" /></td>
           <td>
             <div id="query"/>
           </td>
        </tr>
     </tbody>
</table>
<table id="grid" ></table>
        
<div id="dialog-form">
     <form id="myform">
     <input type="hidden" value="" name="deviceTypeId" id="deviceTypeId"/>
			<table>
				<tr>
	                <td><span class="color_red">*</span>设备厂商：</td>
	                <td><input name="deviceVendor1" id="deviceVendor1"/></td>
	                <td><span></span></td>
	            </tr>
	            <tr>
	                <td><span class="color_red">*</span>设备型号名称：</td>
	                <td><input id="deviceTypeName1" name="deviceTypeName1" style="width:148px;height:20px;border:1px solid #86A3C4;" onblur="checkDeviceTypeNameExists()" maxlength="16"/></td>
	                <td><span></span><div id="showResult" class="color_red" style="display: inline;"/></td>
	            </tr>
	            <tr>
	                <td><span class="color_red">*</span>设备型号编号：</td>
	                <td><input id="deviceTypeCode1" name="deviceTypeCode1" style="width:148px;height:20px;border:1px solid #86A3C4;" onblur="checkDeviceTypeCodeExists();" maxlength="4"/></td>
	                <td><span></span><div id="showResult1" class="color_red" style="display: inline;"/></td>
	            </tr>
	            <tr>
	                <td><span class="color_red">*</span>终端类型：</td>
	                <td><input id="terminalType" name="terminalType"/></td>
	                <td><span></span></td>
	            </tr>
	            <tr>
	                <td><span class="color_red">*</span>设备型号状态：</td>
	                <td><input id="deviceTypeState" name="deviceTypeState"/></td>
	                <td><span></span></td>
	            </tr>
	            <tr>
	                <td>设备型号描述：</td>
	                <td><textarea id="description" name="description" cols="22" rows="4" style="border:1px solid #86A3C4;"></textarea></td>
	                <td><span></span><div id="desc" class="color_red" style="display: inline;"/></td>
	            </tr>
			</table>
		</form>
	</div>					
</body>
</html>
