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
	$("#mytree").omTree({
	     dataSource : "authority_list.json",
	     simpleDataModel: true,
	     classes:'folder',
	     draggable : false,
	     showIcon:true
	 });
	
    $('#add').omButtonbar({btns : [{label:"新增",icons : {left : opPath+'add.png'}}]});
    $('#update').omButtonbar({btns : [{label:"修改",icons : {left : opPath+'op-edit.png'}}]});	
    
	var type =null;
	var selection = null;
	var pselection = null;
	
    $('#add').bind('click', function() {
   		type = 'add';
   		var bool = checkSelect();
   		if(bool == false) return false;
   		$("#authParentId").val(selection.id);
       	showDialog('新增权限');
   	});
    
    $('#update').bind('click', function() {
		type='update';
		var bool = checkSelect();
		if(bool == false) return false;
		if(selection.pid==0||selection.pid==null){
			$.omMessageBox.alert({
	            	type:'alert',
	            	title:'温馨提示',
	            	content:'不能修改根节点！',
	        	});
	        	return false;
		}
	    showDialog('修改权限信息');
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
                	$.post('delete_authority.shtml',{id:selection.id},function(result){
                		$('#mytree').omTree('refresh');
                        if("success" == result){
                        		$.omMessageTip.show({title: tip, content: "删除权限成功！", type:"success" ,timeout: time});
                        }else{
                        		$.omMessageTip.show({title: tip, content: "删除权限失败！", type:"error" ,timeout: time});
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
		pselection = $('#mytree').omTree('getParent', selection);
	};
	
    var showDialog = function(title){
        validator.resetForm();
        if(type=='update'){
        	//修改时填充信息
    		var authName;
    		$.ajax({
    	       	type:"post",
    	       	url:"to_update_authority.json?id="+selection.id,
    	       	dataType:"json",
    	       	async:false,
    	      	success:function(msg){
    	      		authName = msg['name'];
        			$("#name").val(msg['name']);
        			$("#description").val(msg['description']);
    	      	}
    	      });   
        }
        dialog.omDialog("option", "title", title);
        dialog.omDialog("open");
    };
    
    var dialog = $("#dialog-form").omDialog({
        width: 520, autoOpen : false,
        modal : true, resizable : false,
        draggable : false,
        buttons : {
            "提交" : function(){
            		if(validator.form()){
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
	}
    
    var submitDialog = function(){
        var submitData;
        if (validator.form()) {
            if(type=='add'){
            	submitData={
            			parentId: $("#authParentId").val(),
            			name:$("#name").val(),
            			description:$("#description").val()
                    };
            	$.post('verify_authority_add.shtml',submitData,function(result){
                    $('#mytree').omTree('refresh');
                    if("success" == result){
                    	$.omMessageTip.show({title: tip, content: "新增权限成功！", type:"success" ,timeout: time});
                    }else{
                    	$.omMessageTip.show({title: tip, content: "新增权限失败！", type:"error" ,timeout: time});
                    }
                    $("#dialog-form").omDialog("close");
                });
            }else if(type=='update'){
            	submitData={
            			id: selection.id,
            			name:$("#name").val(),
            			description:$("#description").val()
                    };
            	$.post('verify_authority_update.shtml',submitData,function(result){
            		$('#mytree').omTree('refresh');
                    if("success" == result){
                    	$.omMessageTip.show({title: tip, content: "修改权限成功！", type:"success" ,timeout: time});
                    }else{
                    	$.omMessageTip.show({title: tip, content: "修改权限失败！", type:"error" ,timeout: time});
                    }
                    $("#dialog-form").omDialog("close"); 
                });
            }
        }
    };
    
    var validator = $('#myform').validate({
        rules : {
        	name : {required: true}
        }, 
        messages : {
        	name : {required : "名称不能为空！"}
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
    $("#tree_wrap").css("height", $(document).height() - 33);
});

</script>
</head>
<body>
<div id="center-tab">
<ul>
	 <li><a href="#tab1">权限信息:</a></li>
</ul>

</div>
<!-- <table>
			<tr align="left">
				<td>
				<c:if test="${sessionScope.add_authority != null }">
					<td><div id="add"/></td>
				</c:if>
				<c:if test="${sessionScope.update_authority != null }">
					<td><div id="update"/></td>
				</c:if>
			</tr>
		</table>  -->
    <div id="tree_wrap" style="overflow-y: auto;">
		<table>
			<tr valign="top">
				<td ><ul id="mytree"></ul></td>
			</tr>
		</table>
	</div>
	<div id="dialog-form">
	
		<input type="hidden" value="" name="authId" id="authId" />
		<input type="hidden" value="" name="authParentId" id="authParentId" />
		<form id="myform">
			<table>
				<tr>
					<td style="text-align: right;"><span class="color_red">*</span>名称：</td>
					<td><input id="name" name="name" style="width: 150px; height: 20px; border: 1px solid #86A3C4;"
						maxlength="20" /></td>
					<td><span></span>
					<div id="showResult" style="display: inline;" /></td>
				</tr>
				<tr>
					<td style="text-align: right;">描述：</td>
					<td><textarea id="description" name="description" cols="22"
							rows="3" style="border: 1px solid #86A3C4;width: 150px;"></textarea></td>
					<td><span></span></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>