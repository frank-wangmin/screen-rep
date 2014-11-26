<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<%@ include file="/include/taglibs.jsp"%>
<%@ include file="/include/operamasks-ui-2.0.jsp"%>
<%@ include file="/include/css.jsp"%>
<%@ include file="/include/ysten.jsp"%>
<script type="text/javascript">
$(document).ready(function(){
    $("#mytree").omTree({
        dataSource : "area_list.json",
        simpleDataModel: true,
        classes:'folder',
        draggable : true
    });
    
    $("#add").omButton();
    $("#delete").omButton();
    $("#update").omButton();
    
    var selection = null;
    var pselection = null;
    var type = null;
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
            },
            "取消" : function() {
            	resetData();
                $("#dialog-form").omDialog("close");
            }
        },onClose:function(){resetData();}
    });
    
    function resetData(){
    		$("#showResult").html('');
    		$("#showResult1").html('');
    }
    
    var showDialog = function(title){
        validator.resetForm();
        dialog.omDialog("option", "title", title);
        dialog.omDialog("open");
    };
    
    var submitDialog = function(){
        var submitData;
        if (validator.form()) {
            if(type=='add'){
            	submitData={
            			parentId: $("#areaParentId").val(),
            			parentName: $("#areaParentName").val(),
            			code:$("#areaCode").val(),
            			name:$("#areaName").val(),
            			state:$("#areaState").omCombo('value'),
            			description:$("#description").val(),
                    };
            	$.post('area_add.shtml',submitData,function(result){
                    $('#mytree').omTree('refresh');
                    if("success" == result){
                    	$.omMessageTip.show({title: tip, content: "新增区域信息成功！", type:"success" ,timeout: time});
                    }else{
                    	$.omMessageTip.show({title: tip, content: "新增区域信息失败！", type:"error" ,timeout: time});
                    }
                    $("#dialog-form").omDialog("close");
                });
            }else if(type=='update'){
            	submitData={
            			id: $("#areaId").val(),
            			parentId: $("#areaParentId").val(),
            			parentName: $("#areaParentName").val(),
            			code:$("#areaCode").val(),
            			name:$("#areaName").val(),
            			state:$("#areaState").omCombo('value'),
            			description:$("#description").val(),
                    };
            	$.post('area_update.shtml',submitData,function(result){
            		$('#mytree').omTree('refresh');
                    if("success" == result){
                    	$.omMessageTip.show({title: tip, content: "修改区域信息成功！", type:"success" ,timeout: time});
                    }else{
                    	$.omMessageTip.show({title: tip, content: "修改区域信息失败！", type:"error" ,timeout: time});
                    }
                    $("#dialog-form").omDialog("close"); 
                });
            }else if(type=='delete'){
            	submitData={
            			id: $("#areaId").val(),
            			parentId: $("#areaParentId").val(),
            			parentName: $("#areaParentName").val(),
            			code:$("#areaCode").val(),
            			name:$("#areaName").val(),
            			state:$("#areaState").omCombo('value'),
            			description:$("#description").val(),
                    };
            	$.post('area_update.shtml',submitData,function(result){
            		$('#mytree').omTree('refresh');
                    if("success" == result){
                    	$.omMessageTip.show({title: tip, content: "修改区域信息成功！", type:"success" ,timeout: time});
                    }else{
                    	$.omMessageTip.show({title: tip, content: "修改区域信息失败！", type:"error" ,timeout: time});
                    }
                    $("#dialog-form").omDialog("close"); 
                });
            }
        }
    };
    
    $('#add').bind('click', function() {
    		type = 'add';
    		var bool = checkSelect();
    		if(bool == false) return false;
    		$("#areaParentId").val(selection.id);
    		$("#areaParentName").val(selection.text);
    	   	$('#areaState').omCombo({dataSource : [ {text : '可用', value : 'USABLE'},{text : '不可用', value : 'UNUSABLE'}],editable:false,width:152,listMaxHeight:100,value : 'USABLE'});
        	showDialog('新增区域信息');
    });
    
    $('#update').bind('click', function() {
    		type='update';
		var bool = checkSelect();
		if(bool == false) return false;
		$("#areaId").val(selection.id);
		$("#areaParentId").val(pselection.id);
		$("#areaParentName").val(pselection.text);
		if(selection.id==1){
			$.omMessageBox.alert({
	            	type:'alert',
	            	title:'温馨提示',
	            	content:'不能修改根节点！',
	        	});
	        	return false;
		}
		$.ajax({
           	type:"post",
           	url:"area_id.json?id="+selection.id,
           	dataType:"json",
          	success:function(msg){
          		$('#areaState').omCombo({dataSource : [ {text : '可用', value : 'USABLE'},{text : '不可用', value : 'UNUSABLE'}],editable:false,width:152,listMaxHeight:100,value:msg['state']});
              		$("#areaCode").val(msg['code']);
              		$("#areaName").val(msg['name']);
          	}
          });        
        	showDialog('修改区域信息');
	});
    
    $('#delete').bind('click', function() {
		type='delete';
		var bool = checkSelect();
		if(bool == false) return false;
		$.omMessageBox.confirm({
            title:'确认删除',
            content:'确认删除当前节点及下属的全部节点？',
            onClose:function(v){
            	if(v==true){
                	$.post('area_delete.shtml',{id:selection.id},function(result){
                		$('#mytree').omTree('refresh');
                        if("success" == result){
                        		$.omMessageTip.show({title: tip, content: "删除区域信息成功！", type:"success" ,timeout: time});
                        }else{
                        		$.omMessageTip.show({title: tip, content: "删除区域信息失败！", type:"error" ,timeout: time});
                        }
                    });
            	}
            }
        });
	});
    
    var checkSelect= function(){
    		selection=$('#mytree').omTree('getSelected');
        	if (selection == null || selection.length == 0) {
        		$.omMessageBox.alert({
                	type:'alert',
                	title:'温馨提示',
                	content:'至少得选择一个节点！',
            	});
            	return false;
        	}
        	if(selection.id != 1){
    			pselection = $('#mytree').omTree('getParent', selection);
    		}
    };
    
    var validator = $('#myform').validate({
        rules : {
        	areaCode : {required: true}, 
        	areaName : {required : true},
        	areaState : {required : true},
        }, 
        messages : {
        	areaCode : {required : "编码不能为空！"},
        	areaName : {required : "名称不能为空！"},
        	areaState: {required : "请选择状态！"}
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
    $("#tree_wrap").css("height", $(document).height() - 33);
});



</script>
</head>
<body>
<div id="center-tab">
<ul>
	 <li><a href="#tab1">区域信息:</a></li>
</ul>
</div>
	<div id="tree_wrap" style="overflow-y: auto;">
		<table>
			<tr>
				<td>
					<input id="add" type="button" value="新增" />
				 	<input id="update" type="button" value="修改" />
				 </td>
			</tr>
			<tr>
				<td width="160"><div id="mytree"></div></td>
			</tr>
		</table>
	</div>
	<div id="dialog-form">
		<form id="myform">
			<input type="hidden" value="" name="areaId" id="areaId" />
			<input type="hidden" value="" name="areaParentId" id="areaParentId" />
			<input type="hidden" value="" name="areaParentName" id="areaParentName" />
			<table>
				<tr>
					<td style="text-align: right;"><span class="color_red">*</span>区域编码：</td>
					<td><input id="areaCode" name="areaCode" style="width: 150px; height: 20px; border: 1px solid #86A3C4;"
						maxlength="16" /></td>
					<td><span></span>
					<div id="showResult" style="display: inline;" /></td>
				</tr>
				<tr>
					<td style="text-align: right;"><span class="color_red">*</span>区域名称：</td>
					<td><input id="areaName" name="areaName" style="width: 150px; height: 20px; border: 1px solid #86A3C4;"
						maxlength="20" /></td>
					<td><span></span>
					<div id="showResult1" style="display: inline;" /></td>
				</tr>
				<tr>
					<td style="text-align: right;"><span class="color_red">*</span>区域状态：</td>
					<td><input id="areaState" name="deviceVendorState" /></td>
					<td><span></span></td>
				</tr>
				<tr>
					<td style="text-align: right;">区域描述：</td>
					<td><textarea id="description" name="description" cols="23"
							rows="3" style="border: 1px solid #86A3C4;"></textarea></td>
					<td><span></span>
					<div id="desc" style="display: inline;" /></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>