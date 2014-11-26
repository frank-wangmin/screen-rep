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
function showInitData(url){
    $('#grid').omGrid({
    	dataSource : url,
    	width : '100%',
        height : rFrameHeight,
        singleSelect : true,
        limit :limit,
        colModel : [ 
                     {header : '<b>服务ID</b>', name : 'id', align : 'center', width : 40},     
                     {header : '<b>服务名称</b>', name : 'serviceType', align : 'center', width : 120},
                     {header : '<b>服务键值</b>', name : 'serviceName', align : 'center', width : 120},
//                     {header : '<b>服务IP</b>', name : 'serviceIp', align : 'center', width : 120},
                     {header : '<b>服务地址</b>', name : 'serviceUrl', align : 'center', width : 250},
                     {header : '<b>创建时间</b>', name : 'createDate', align : 'center', width : 120},
                     {header : '<b>更新时间</b>', name : 'updateDate',align : 'center',width:120},
                     {header : '<b>服务描述</b>', name : 'description', align : 'center', width : 150}
		]
    });
}

$(function(){
	var validator = $('#myform').validate({
       	rules : {
       		serviceNameInForm : {required : true},
       		serviceTypeInForm : {required : true},
       		serviceIpInForm : {isIP : true},
       		serviceUrlInForm : {url : true}
       	}, 
       	messages : {
       		serviceNameInForm : {required : "服务键值不能为空！"},
       		serviceTypeInForm : {required : "服务名称不能为空！"},
       		serviceIpInForm : {isIP : "服务IP地址不正确！"},
       		serviceUrlInForm : {url : "服务地址格式不正确！"}
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

	$('#add').omButtonbar({btns : [{label:"新增",icons : {left : opPath+'add.png'}}]});
	$('#update').omButtonbar({btns : [{label:"修改",icons : {left : opPath+'op-edit.png'}}]});	
	$('#query').omButtonbar({btns : [{label:"查询",icons : {left : opPath+'search.png'}}]});
	$("#query").click(function(){
		var serviceName = filterStartEndSpaceTrim($("#serviceNameIpt").val());
		var serviceType = filterStartEndSpaceTrim($("#serviceTypeIpt").val());
		showInitData("find_service_info_list.json?serviceCollectId="+(parent.serviceCollectIdGlobal)+
													"&serviceName="+encodeURIComponent(serviceName)+
													"&serviceType="+encodeURIComponent(serviceType));
	});
	
	$('#add').bind('click', function(e) {
		validator.resetForm();
        $("#showResult").html("");
   		var dialog = $("#dialog-form").omDialog({
       		width:500, height:200, autoOpen:false, modal:true, resizable:false, draggable:false,
       		buttons : {
				"提交" : function(){
           			submitData={
           				serviceName:$("#serviceNameInForm").val(),
           				serviceType:$("#serviceTypeInForm").val(),
           				serviceIp:$("#serviceIpInForm").val(),
           				serviceUrl:$("#serviceUrlInForm").val(),
           				description:$("#descriptionInForm").val(),
           				serviceCollectId:parent.serviceCollectIdGlobal,
               		};
           			
            		if(validator.form()){
            			var sr = $("#showResult").html();
            			if(sr=='' || sr =='可用!'){
                    		$.post("add_service_info.json", submitData, function(result){
                            	if("success" == result){
                            		$.omMessageTip.show({title: tip, content: "新增服务信息成功", type:"success" ,timeout: time});
                            	}else{
                            		$.omMessageTip.show({title: tip, content: "新增服务信息失败", type:"error" ,timeout: time});
                            	}
                            	$('#grid').omGrid('reload');
                        	});
	            			$("#dialog-form").omDialog("close");
	            			$('#grid').omGrid('reload');
	            			$("#showResult").text("");
                			}
                        return false;
            		}
            	},
	            "取消" : function() {
	            	$("#showResult").text("");
	            	validator.resetForm();
	                $("#dialog-form").omDialog("close");
	            }
       		}
   		});
   		dialog.omDialog("option", "title", "新增服务信息");
       	dialog.omDialog("open");
	});	
	
	$('#update').bind('click', function(e) {
		validator.resetForm();
        $("#showResult").html("");
        var selections=$('#grid').omGrid('getSelections',true);
        if (selections.length == 0 || selections.length > 1) {
        	$.omMessageBox.alert({
                type:'alert',
                title:'温馨提示',
                content:'修改操作至少且只能选择一行记录！',
            });
            return false;
        }
       	$.post("get_service_info_by_id.json", {id:selections[0].id}, function(result){
       		$("#id").val(result['id']);
       		$("#serviceNameInForm").val(result['serviceName']);
       		$("#serviceTypeInForm").val(result['serviceType']);
       		$("#serviceIpInForm").val(result['serviceIp']);
       		$("#serviceUrlInForm").val(result['serviceUrl']);
       		$("#descriptionInForm").val(result['description']);
       	});
		
   		var dialog = $("#dialog-form").omDialog({
       		width:500, height:250, autoOpen:false, modal:true, resizable:false, draggable:false,
       		buttons : {
				"提交" : function(){
           			submitData={
           				//id:selections[0].id,
           				id:$("#id").val(),
           				serviceName:$("#serviceNameInForm").val(),
           				serviceType:$("#serviceTypeInForm").val(),
           				serviceIp:$("#serviceIpInForm").val(),
           				serviceUrl:$("#serviceUrlInForm").val(),
           				description:$("#descriptionInForm").val(),
               		};
            		if(validator.form()){
            			var sr = $("#showResult").html();
            			if(sr=='' || sr =='可用!'){
            				$.post("update_service_info.json", submitData, function(result){
                            	if("success" == result){
                            		$.omMessageTip.show({title: tip, content: "修改服务信息成功", type:"success" ,timeout: time});
                            	}else{
                            		$.omMessageTip.show({title: tip, content: "修改服务新失败", type:"error" ,timeout: time});
                            	}
                            	$('#grid').omGrid('reload');
                        	});
	            			$("#dialog-form").omDialog("close");
	            			$('#grid').omGrid('reload');
	            			$("#showResult").text("");
                			}
                        return false;

            		}
            	},
	            "取消" : function() {
	            	$("#showResult").text("");
	            	validator.resetForm();
	                $("#dialog-form").omDialog("close");
	            }
       		}
   		});
   		dialog.omDialog("option", "title", "修改服务信息");
       	dialog.omDialog("open");
	});	
	$('#center-tab').omTabs({height:"33",border:false});
});
function checkNameExists(){
	if($.trim($("#serviceNameInForm").val())!=""){
	   var par = new Object();
	   par['name'] = $("#serviceNameInForm").val();	   
	   par['id'] = $("#id").val();
        par['serviceCollectId']=parent.serviceCollectIdGlobal;
       $.ajax({
       type:"post",
       url:"service_name_exists.shtml?s="+Math.random(),
       dataType:"html",
       data:par,
       success:function(msg){ 
           //alert(msg); 
           $("#showResult").html(msg);
       }
      });
    }else if($.trim($("#serviceNameInForm").val())=="" && $("#showResult").html() != ""){
    	$("#showResult").html("");
        }
}
</script>
</head>
<body>	
	<div id="center-tab">
		<ul>
			<li><a href="#tab1">服务信息列表:</a></li>
		</ul>
	</div>
	<table>
		<tbody>
			<tr>  
				<c:if test="${sessionScope.add_service_info_list != null }">
					<td><div id="add"/></td>
				</c:if>
				<c:if test="${sessionScope.update_service_info_list != null }">
					<td><div id="update"/></td>
				</c:if>

                
			</tr>
		</tbody>
	</table>
    <table>
        <tr>
        	<td>服务名称：</td>
            <td><input type="text" id="serviceTypeIpt" name="serviceTypeIpt"  class="query-title-input" /></td>
			<td>服务键值：</td>
            <td><input type="text" id="serviceNameIpt" name="serviceNameIpt"  class="query-title-input"/></td>
            <td><div id="query"/></td>
        </tr>
    </table>

	<table id="grid" ></table>
	
	<div id="dialog-form" style="display:none">
		<form id="myform" method="post">
		<input type="hidden" value="" name="id" id="id" />
			<table>
				<tr>
					<td style="width: 100px;text-align: right;"><span class="color_red">*</span>服务名称：</td>
					<td><input id="serviceTypeInForm" name="serviceTypeInForm" class="query-title-input"/></td>
					<td style="width: 150px;"><span></span></td>	
				</tr>
				<tr>
					<td style="width: 100px;text-align: right;"><span class="color_red">*</span>服务键值：</td>
					<td><input id="serviceNameInForm" name="serviceNameInForm"  class="query-title-input" onblur="checkNameExists()"/></td>
					<td style="width: 150px;"><span></span><div id="showResult" class="color_red" style="display: inline;"/></td>			
				</tr>			
				<tr>
					<td style="width: 100px;text-align: right;">服务地址：</td>
					<td><input id="serviceUrlInForm" name="serviceUrlInForm"  class="query-title-input"/></td>
					<td style="width: 200px;"><span></span></td>
				</tr>
				<tr>
					<td style="width: 100px;text-align: right;">服务描述：</td>
					<td><input id="descriptionInForm" name="descriptionInForm"  class="query-title-input"/></td>
				</tr>
			</table>
		</form>
	</div>

</body>
</html>
