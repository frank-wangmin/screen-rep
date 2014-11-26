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
    	dataSource : 'device_vendor_list.json',
    	width : '100%',
        height : rFrameHeight,
        singleSelect : true,
        limit : limit,
        colModel : [ 
                     {header : '<b>设备厂商名称</b>', name : 'name', align : 'center', width : 190}, 
                     {header : '<b>设备厂商编号</b>', name : 'code', align : 'center', width : 200},
                     {header : '<b>状态</b>', name : 'state',align : 'center',width: 120},
                     {header : '<b>创建时间</b>', name : 'createDate',align : 'center',width:150},
                     {header : '<b>描述</b>', name : 'description',align : 'center',width: 'autoExpand'}
                     
		]
    });

    $('#create').omButtonbar({btns : [{label:"新增",icons : {left : opPath+'add.png'}}]});
    $('#update').omButtonbar({btns : [{label:"修改",icons : {left : opPath+'op-edit.png'}}]});
    $('#query').omButtonbar({btns : [{label:"查询",icons : {left : opPath+'search.png'}}]});
    $('#query').bind('click', function(e) {
        var deviceVendorName = $('#deviceVendorName').val();
        var deviceVendorCode = $('#deviceVendorCode').val();
        $('#grid').omGrid("setData", 'device_vendor_list.json?deviceVendorName='+encodeURIComponent(deviceVendorName)+'&deviceVendorCode='+deviceVendorCode);
    });

    var dialog = $("#dialog-form").omDialog({
        width: 520, autoOpen : false,
        modal : true, resizable : false,
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
                return false; 
            },
            "取消" : function() {
            	resetData();
            	validator.resetForm();
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
            			deviceVendorName1:$("#deviceVendorName1").val(),
            			deviceVendorCode1:$("#deviceVendorCode1").val(),
            			deviceVendorState:$("#deviceVendorState").omCombo('value'),
            			description:$("#description").val(),
                    };
            	$.post('device_vendor_add.shtml',submitData,function(result){
                    $('#grid').omGrid('reload');
                    if("success" == result){
                    	$.omMessageTip.show({title: tip, content: "新增设备厂商信息成功！", type:"success" ,timeout: time});
                    }else{
                    	$.omMessageTip.show({title: tip, content: "新增设备厂商信息失败！", type:"error" ,timeout: time});
                    }
                    $("#dialog-form").omDialog("close");
                });
            }else{
            	submitData={
                    	id:$("#deviceVendorId").val(),
            			deviceVendorName1:$("#deviceVendorName1").val(),
            			deviceVendorCode1:$("#deviceVendorCode1").val(),
            			deviceVendorState:$("#deviceVendorState").omCombo('value'),
            			description:$("#description").val(),
                    };
            	$.post('device_vendor_update.shtml',submitData,function(result){
                    $('#grid').omGrid('reload');
                    if("success" == result){
                    	$.omMessageTip.show({title: tip, content: "修改设备厂商信息成功！", type:"success" ,timeout: time});
                    }else{
                    	$.omMessageTip.show({title: tip, content: "修改设备厂商信息失败！"+result, type:"error" ,timeout: time});
                    }
                    $("#dialog-form").omDialog("close"); 
                });
            }
        }
    };
    
    var isAdd = true; 
    $('#create').bind('click', function() {
    	validator.resetForm();
    	resetData();
    	$("#deviceVendorCode1").attr('disabled',false);
    	isAdd = true;   	
    	$('#deviceVendorState').omCombo({dataSource : [ {text : '可用', value : 'USABLE'},{text : '不可用', value : 'UNUSABLE'}],editable:false,width:150,listMaxHeight:100});
        showDialog('新增设备厂商信息');
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
           	url:"device_vendor_to_update.json?id="+selections[0].id,
           	dataType:"json",
          	success:function(msg){
          		$('#deviceVendorState').omCombo({dataSource : [ {text : '可用', value : 'USABLE'},{text : '不可用', value : 'UNUSABLE'}],editable:false,width:150,listMaxHeight:100,value:msg['state']});
              	$("#deviceVendorId").val(msg['id']);
    			$("#deviceVendorName1").val(msg['name']);
    			$("#deviceVendorCode1").val(msg['code']);
    			$("#deviceVendorCode1").attr('disabled',true);
    			$("#description").val(msg['description']);
          }
          });        
        showDialog('修改设备厂商信息',selections[0]);
    });
    
    var validator = $('#myform').validate({
        rules : {
        	deviceVendorName1 : {required: true}, 
        	deviceVendorCode1 : {required : true},
        	deviceVendorState : {required : true},
        }, 
        messages : {
        	deviceVendorName1 : {required : "名称不能为空！"},
        	deviceVendorCode1 : {required : "编号非且只能为2位数字！"},
        	deviceVendorState: {required : "请选择设备厂商状态！"}
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

function checkDeviceVendorNameExists(){
	if($.trim($("#deviceVendorName1").val())!=""){
	   var par = new Object();
	   par['deviceVendorName'] = $("#deviceVendorName1").val();
	   par['id'] = $("#deviceVendorId").val();
       $.ajax({
       type:"post",
       url:"device_vendor_name_exists.shtml?s="+Math.random(),
       async:false,
       dataType:"html",
       data:par,
       success:function(msg){  
           $("#showResult").html(msg);
       }
      });
    }
}

function checkDeviceVendorCodeExists(){
	var bool = checkNumberStr();
	if($("#deviceVendorCode1").val()!="" && bool){
       $.ajax({
       type:"post",
       url:"device_vendor_code_exists.shtml?s="+Math.random(),
       dataType:"html",
       data:"deviceVendorCode="+$("#deviceVendorCode1").val(),
       success:function(msg){  
           $("#showResult1").html(msg);
       }
      });
    }else if($("#deviceVendorCode1").val()=="" && $("#showResult1").html() != ""){
    	$("#showResult1").html("");
        }
 }

function resetData(){
	$("#showResult").html('');
	$("#showResult1").html('');
}

function checkNumberStr() {
	var code = $.trim($("#deviceVendorCode1").val());
    if(code.match(/\D/) == null && code.length==2){
    	$("#showResult1").text("");
    	return true;
    }else{
    	$("#showResult1").text("设备厂商编号非空且只能为2位数字！");
    	return false;
    }
}

</script>
</head>
<body>
	<div id="center-tab">
		<ul>
			 <li><a href="#tab1">设备厂商信息列表:</a></li>
		</ul>
	</div>
<table >
    <tr>
    	<c:if test="${sessionScope.add_device_vendor != null }">
      	  <td><div id="create"/></td>
      	</c:if>
      	<c:if test="${sessionScope.update_device_vendor != null }">
       	  <td><div id="update"/></td>
       	</c:if>
          <td style="text-align:center;">&nbsp;设备厂商名称：</td>
          <td>
          		<input type="text" name="deviceVendorName" id="deviceVendorName" class="query-title-input"/></td>
          <td style="text-align:center;">&nbsp;设备厂商编号：</td>
          <td>
          		<input name="deviceVendorCode" id="deviceVendorCode" class="query-title-input"/></td>
           <td><div id="query"/></td>
  </tr>
</table>
<table id="grid" ></table>
<div id="dialog-form">
<form id="myform">
     <input type="hidden" value="" name="deviceVendorId" id="deviceVendorId"/>
			<table>
	            <tr>
	                <td><span class="color_red">*</span>设备厂商名称：</td>
	                <td><input id="deviceVendorName1" name="deviceVendorName1" style="width:148px;height:20px;border:1px solid #86A3C4;" onblur="checkDeviceVendorNameExists();" maxlength="16"/></td>
	                <td><span></span><div id="showResult" class="color_red" style="display: inline;"/></td>
	            </tr>
	            <tr>
	                <td><span class="color_red">*</span>设备厂商编号：</td>
	                <td><input id="deviceVendorCode1" name="deviceVendorCode1" style="width:148px;height:20px;border:1px solid #86A3C4;" onblur="checkDeviceVendorCodeExists();" maxlength="2"/></td>
	                <td><span></span><div id="showResult1" class="color_red" style="display: inline;"/></td>
	            </tr>
	            <tr>
	                <td><span class="color_red">*</span>设备厂商状态：</td>
	                <td><input id="deviceVendorState" name="deviceVendorState"/></td>
	                <td><span></span></td>
	            </tr>
	            <tr>
	                <td style="width: 100px;text-align: right;">设备厂商描述：</td>
	                <td><textarea id="description" name="description" cols="22" rows="4" style="border:1px solid #86A3C4;"></textarea></td>
	                <td><span></span><div id="desc" class="color_red" style="display: inline;"/></td>
	            </tr>
			</table>
		</form>
</div>	
</body>
</html>
