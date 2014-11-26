<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<title></title>
<%@ include file="/include/taglibs.jsp"%>
<%@ include file="/include/operamasks-ui-2.0.jsp"%>
<%@ include file="/include/css.jsp"%>
<%@ include file="/include/ysten.jsp"%>
<script type="text/javascript">
$(document).ready(function() {
	$('#grid').omGrid({
    	dataSource : 'talk_system_list.json',
    	width : '100%',
        height : rFrameHeight,
        singleSelect : true,
        limit : limit,
        colModel : [ 
			{header : '<b>名称</b>', name : 'name',align : 'center',width: 200},
			{header : '<b>编号</b>', name : 'code', align : 'center', width : 200},
			{header : '<b>创建时间</b>', name : 'createDate',align : 'center',width: 180},
			{header : '<b>描述</b>', name : 'description',align : 'center',width: 450}
		]
    });

	$('#add').omButtonbar({btns : [{label:"新增",icons : {left : opPath+'add.png'}}]});
    $('#update').omButtonbar({btns : [{label:"修改",icons : {left : opPath+'op-edit.png'}}]});

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
                if($("#desc").html()==''){
                	submitDialog();
                }
                return false; 
            },
            "取消" : function() {
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
            			name:$("#name").val(),
            			code:$("#code").val(),
            			description:$("#description").val(),
                    };
            	$.post('talk_system_add.shtml',submitData,function(result){
                    $('#grid').omGrid('reload');
                    if("success" == result){
                    	$.omMessageTip.show({title: tip, content: "新增交互系统信息成功！", type:"success" ,timeout: time});
                    }else{
                    	$.omMessageTip.show({title: tip, content: "新增交互系统信息失败！", type:"error" ,timeout: time});
                    }
                    $("#dialog-form").omDialog("close");
                });
            }else{
            	submitData={
                    	id:$("#id").val(),
            			name:$("#name").val(),
            			code:$("#code").val(),
            			description:$("#description").val(),
                    };
            	$.post('talk_system_update.shtml',submitData,function(result){
                    $('#grid').omGrid('reload');
                    if("success" == result){
                    	$.omMessageTip.show({title: tip, content: "修改交互系统信息成功！", type:"success" ,timeout: time});
                    }else{
                    	$.omMessageTip.show({title: tip, content: "修改交互系统信息失败！", type:"error" ,timeout: time});
                    }
                    $("#dialog-form").omDialog("close"); 
                });
            }
        }
    };
    
    var isAdd = true; 
    $('#add').bind('click', function() {
    	isAdd = true;
        showDialog('新增交互系统信息');
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
           	url:"talk_system_to_update.json?id="+selections[0].id,
           	dataType:"json",
          	success:function(msg){
              	$("#id").val(msg['id']);
    			$("#name").val(msg['name']);
    			$("#code").val(msg['code']);
    			$("#description").val(msg['description']);
          }
          });        
        showDialog('修改交互系统信息',selections[0]);
    });
    
    var validator = $('#myform').validate({
        rules : {
        	name : {required: true}, 
        	code : {required : true},
        }, 
        messages : {
        	name : {required : "交互系统名称不能为空！"},
        	code : {required : "交互系统编号！"}
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
    $('#center-tab').omTabs({height:"33",border:false});
});
</script>
</head>
<body>
<div id="center-tab">
<ul>
	 <li><a href="#tab1">交互系统信息列表:</a></li>
</ul>
</div>
<table >
        <tbody>
            <tr>
            	<c:if test="${sessionScope.add_talk_system != null }">
	            <td style="text-align: center;">
	                 <div id="add"/>
	            </td>
	            </c:if>
            	<c:if test="${sessionScope.update_talk_system != null }">
            	<td style="text-align: center;">
	                 <div id="update"/>
	            </td>
	            </c:if>
             </tr>
           </tbody>
        </table>
<table id="grid" ></table>
<div id="dialog-form">
<form id="myform">
     <input type="hidden" value="" name="id" id="id"/>
			<table>
	            <tr>
	                <td><span class="color_red">*</span>交互系统名称：</td>
	                <td><input id="name" name="name" style="width:145px;height:20px;border:1px solid #86A3C4;" onblur="checkDeviceVendorNameExists();"/></td>
	                <td><span></span></td>
	            </tr>
	            <tr>
	                <td><span class="color_red">*</span>交互系统编号：</td>
	                <td><input id="code" name="code" style="width:145px;height:20px;border:1px solid #86A3C4;" onblur="checkDeviceVendorCodeExists();"/></td>
	                <td><span></span></td>
	            </tr>
	            <tr>
	                <td>交互系统描述：</td>
	                <td><textarea id="description" name="description" cols="22" rows="4" style="border:1px solid #86A3C4;"></textarea></td>
	                <td><span></span><div id="desc" style="display: inline;"/></td>
	            </tr>
			</table>
		</form>
</div>	
</body>
</html>