<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<%@ include file="/include/taglibs.jsp"%>
<%@ include file="/include/operamasks-ui-2.0.jsp"%>
<%@ include file="/include/css.jsp"%>
<%@ include file="/include/ysten.jsp"%>
<script language="javascript">
/*加载价格规则信息*/
function showPolicyDefineProduct(policyGroupId) {
	$("#policyDefineProduct")[0].contentWindow.showPolicyDefine(policyGroupId);
}
var dialog;
var validator;
var defineDialog;
var defineValidator;
var isPolicyGroupSubmitted = false;
var isDiscountPolicySubmitted = false;
$(document).ready(function() {
	$("#policyGroup").attr("height",(rFrameHeight+60)+'px');
	$("#policyDefineProduct").attr("height",(rFrameHeight+60)+'px');
		dialog = $("#dialog-form").omDialog({
		    width: 500,
		    autoOpen : false,
		    modal : true,
		    resizable : false,
		    draggable : false,
		    buttons : {
		        "提交" : function(){
		        	if(!isPolicyGroupSubmitted){
			        	validator.form();
			        	if(!checkPolicyGroupName()){
			        		isPolicyGroupSubmitted=true;
			            	submitDialog();
			        	}
		        	}
		            return false; 
		        },
		        "取消" : function() {
		        	resetData();
		            $("#dialog-form").omDialog("close");
		        }
		    },
		    onClose:function(){resetData();isPolicyGroupSubmitted = false;}
	});
	validator = $('#myform').validate({
	    rules : {
	    	policyName : {required: true}
	    }, 
	    messages : {
	    	policyName : {required : "优惠策略组名称不能为空！"},
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
	
	defineDialog = $("#define-dialog-form").omDialog({
		    width: 500,
		    autoOpen : false,
		    modal : true,
		    resizable : false,
		    draggable : false,
		    buttons : {
		        "提交" : function(){
		        	if(!isDiscountPolicySubmitted){
			        	defineValidator.form();
			        	isDiscountPolicySubmitted=true;
			        	submitDefineDialog();
		        	}
		            return false; 
		        },
		        "取消" : function() {
		        	resetData();
		            $("#define-dialog-form").omDialog("close");
		        }
		    },
		    onClose:function(){isDiscountPolicySubmitted=false;}
	});
	defineValidator = $('#define-myform').validate({
	    rules : {
	    	defineCheckType : {required: true},
	    	defineCheckPar1 : {required: true},
	    	defineDiscountType : {required: true},
	    	defineDiscountPar1 : {required: true}
	    }, 
	    messages : {
	    	defineCheckType : {required: "检查类型不能为空！"},
	    	defineCheckPar1 : {required: "检查值不能为空！"},
	    	defineDiscountType : {required: "折扣类型不能为空！"},
	    	defineDiscountPar1 : {required: "折扣值不能为空！"}
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
		
	$('#defineCheckType').omCombo({dataSource : 'define_check_type.json',editable:false,width:150,listMaxHeight:100});
	$('#defineDiscountType').omCombo({dataSource : 'define_discount_type.json',editable:false,width:150,listMaxHeight:100});
	
	$('#defineCheckPar1').omNumberField();
	$('#defineDiscountPar1').omNumberField();

	$('#policyName').bind('blur', function() {
    	$("#policyGroupNameCheckResult").html('');
    });
	
	
});
/**********************优惠策略组开始*****************************/
var isPolicyGroupAdd = false;
var showDialog = function(title,rowData,isAdd){
	isPolicyGroupAdd=isAdd;
    validator.resetForm();
    rowData = rowData || {};
    dialog.omDialog("option", "title", title);
    dialog.omDialog("open");
};

var submitDialog = function(){
    var submitData;
    if (validator.form()) {
        if(isPolicyGroupAdd){
        	submitData={
        			policyName:$("#policyName").val(),
        			description:$("#description").val(),
                };
        	$.post('policy_group_add.shtml',submitData,function(result){
                if("success" == result){
                	$("#policyGroup")[0].contentWindow.reloadFrame();
                	$.omMessageTip.show({title: tip, content: "新增优惠策略组成功！", type:"success" ,timeout: time});
                }else{
                	$.omMessageTip.show({title: tip, content: "新增优惠策略组失败！", type:"error" ,timeout: time});
                }
                $("#dialog-form").omDialog("close");
            });
        }else{
        	submitData={
        			id:$("#discountPolicyGroupId").val(),
        			policyName:$("#policyName").val(),
        			description:$("#description").val(),
                };
        	$.post('policy_group_update.shtml',submitData,function(result){
                if("success" == result){
                	$("#policyGroup")[0].contentWindow.reloadFrame();
                	$.omMessageTip.show({title: tip, content: "修改优惠策略组成功！", type:"success" ,timeout: time});
                }else{
                	$.omMessageTip.show({title: tip, content: "修改优惠策略组失败！", type:"error" ,timeout: time});
                }
                $("#dialog-form").omDialog("close"); 
            });
        }
    }
};

function fillDiscountPolicyGroupData(data){
	$("#discountPolicyGroupId").val(data['id']);
	$("#policyName").val(data['name']);
	$("#hiddenPolicyGroupName").val(data['name'])
	$("#description").val(data['description']);
}

function deleteDiscountPolicyGroupById(selections){
	 if (selections.length == 0) {
     	$.omMessageBox.alert({
             type:'alert',
             title:'温馨提示',
             content:'请选择一行记录！',
         });
         return false;
     }
     $.omMessageBox.confirm({
         title:'确认删除',
         content:'删除后优惠策略组数据将无法恢复，你确定要执行该操作吗？',
         onClose:function(v){
         	if(v==true){
         		var toDeleteRecordID = selections[0].id;
             	$.post('discount_policy_group_delete.shtml',{id:toDeleteRecordID.toString()},function(result){
             		$("#policyGroup")[0].contentWindow.reloadFrame();
                     if(result=='success'){
                     	$.omMessageTip.show({title: tip, content: "删除优惠策略组成功！", timeout: time,type:'success'});
                     }else{
                     	$.omMessageTip.show({title: tip, content: "删除优惠策略组失败！", timeout: time,type:'error'});
                     }
                     $('#dialog-form').omDialog('close');
                 });
         	}
         }
     }); 
}

function checkPolicyGroupName(){
	var name = $("#policyName").val();
	if(name!=""){
    	if ( isPolicyGroupAdd== true) {
    		return doCheckPolicyGroupNameAjax(name);
    	}else{
    		var hiddenName = $("#hiddenPolicyGroupName").val();
    		if(name!=hiddenName){
    			return doCheckPolicyGroupNameAjax(name);
    		}
    		$("#policyGroupNameCheckResult").html('');
    	}
	}
}

function doCheckPolicyGroupNameAjax(name) {
	var flag = true;
	$.ajax({
		type : "post",
		url : "discount_policy_group_name_exists.shtml",
		dataType : "html",
		data : "name=" + name,
		async:false,
		success : function(msg) {
			if("success" != msg){
				flag = false;
			}
			if(flag==true){
				$("#policyGroupNameCheckResult").html("该策略组名称已存在");
				$("#policyName").focus();
			}
		}
	});
	return flag;
}
function resetData(){
	$("#policyGroupNameCheckResult").html("");
}
/******************************优惠策略组结束*******************************/

/******************************价格规则开始*******************************/
var isPolicyDefineAdd;
var showDefineDialog = function(title,rowData,isAdd,policyGroupId){
	isPolicyDefineAdd=isAdd;
	$("#policyId").val(policyGroupId);
    defineValidator.resetForm();
    rowData = rowData || {};
    defineDialog.omDialog("option", "title", title);
    defineDialog.omDialog("open");
};

var submitDefineDialog = function(){
    var submitData;
    if (defineValidator.form()) {
        if(isPolicyDefineAdd){
        	submitData={
        			policyGroupId : $("#policyId").val(),
        			checkType : $("#defineCheckType").val(),
        	    	checkPar1 : $("#defineCheckPar1").val(),
        	    	discountType : $("#defineDiscountType").val(),
        	    	discountPar1 : $("#defineDiscountPar1").val(),
        	    	description : $("#defineDescription").val(),
                };
        	$.post('policy_define_product_add.shtml',submitData,function(result){
                if("success" == result){
                	$("#policyDefineProduct")[0].contentWindow.reloadFrame();
                	$.omMessageTip.show({title: tip, content: "新增价格规则成功！", type:"success" ,timeout: time});
                }else{
                	$.omMessageTip.show({title: tip, content: "新增价格规则失败！", type:"error" ,timeout: time});
                }
                $("#define-dialog-form").omDialog("close");
            });
        }else{
        	submitData={
        			id : $("#policyDefineId").val(),
        			policyGroupId : $("#policyId").val(),
        			checkType : $("#defineCheckType").val(),
        	    	checkPar1 : $("#defineCheckPar1").val(),
        	    	discountType : $("#defineDiscountType").val(),
        	    	discountPar1 : $("#defineDiscountPar1").val(),
        	    	description : $("#defineDescription").val(),
                };
        	$.post('policy_define_product_update.shtml',submitData,function(result){
                if("success" == result){
                	$("#policyDefineProduct")[0].contentWindow.reloadFrame();
                	$.omMessageTip.show({title: tip, content: "修改价格规则成功！", type:"success" ,timeout: time});
                }else{
                	$.omMessageTip.show({title: tip, content: "修改价格规则失败！", type:"error" ,timeout: time});
                }
                $("#define-dialog-form").omDialog("close"); 
            });
        }
    }
};

function fillDiscountPolicyDefineProductData(data){
	$("#policyDefineId").val(data['id'])
	$("#policyId").val(data['groupId']);
	$('#defineCheckType').omCombo({value:data['checkType']});
	$("#defineCheckPar1").val(data['checkPar1']);
	$('#defineDiscountType').omCombo({value:data['discountType']});
	$("#defineDiscountPar1").val(data['discountPar1']);
	$("#defineDescription").val(data['description']);
}

function deleteDiscountPolicyDefineByIds(selections){
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
        content:'批量删除后优惠策略数据将无法恢复，你确定要执行该操作吗？',
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
            	$.post('discount_policy_delete.shtml',{ids:toDeleteRecordID.toString()},function(result){
            		$("#policyDefineProduct")[0].contentWindow.reloadFrame();
                    if(result=='success'){
                    	$.omMessageTip.show({title: tip, content: "删除优惠策略成功！", timeout: time,type:'success'});
                    }else{
                    	$.omMessageTip.show({title: tip, content: "删除优惠策略失败！", timeout: time,type:'error'});
                    }
                    $('#define-dialog-form').omDialog('close');
                });
        	}
        }
    });
}
/******************************价格规则结束*******************************/
</script>
</head>
<body>

	<table style="width: 100%;height:100%;">
		<tr style="height:100%">
			<td style="width: 40%;" >
				<iframe name="policyGroup" src="to_policy_group.shtml" width="100%" id="policyGroup" border=0 frameBorder='no' scrolling="no"></iframe>
			</td>
			<td style="width: 50%;">
				<iframe name="policyDefineProduct" id="policyDefineProduct" src="to_policy_define_product.shtml" width="100%"  border=0 frameBorder='no' scrolling="no"></iframe>
			</td>
		</tr>
	</table>
	
	
	<div id="dialog-form">
		<form id="myform">
		<input type="hidden" value="" name="discountPolicyGroupId" id="discountPolicyGroupId" />
		<input type="hidden" value="" name="hiddenPolicyGroupName" id="hiddenPolicyGroupName" />
			<table>
				<tr>
					<td style="text-align: right;"><span class="color_red" >*</span>策略名称：</td>
					<td><input name="policyName" id="policyName"  style="width: 148px; height: 20px; border: 1px solid #86A3C4;"/></td>
					<td><span></span><div id="policyGroupNameCheckResult" style="display: inline;"></div></td>
				</tr>
				<tr>
					<td style="text-align: right;">描述：</td>
					<td><textarea id="description" name="description" cols="22" rows="4"
							style="border: 1px solid #86A3C4;"></textarea></td>
				</tr>
			</table>
		</form>
	</div>
	
	<div id="define-dialog-form">
		<form id="define-myform">
		<input type="hidden" value="" name="policyId" id="policyId" />
		<input type="hidden" value="" name="policyDefineId" id="policyDefineId" />
			<table>
				<tr>
					<td style="text-align: right;"><span class="color_red">*</span>检查类型：</td>
					<td><input name="defineCheckType" id="defineCheckType"/></td>
					<td><span></span></td>
				</tr>
				<tr>
					<td style="text-align: right;"><span class="color_red">*</span>检查值：</td>
					<td><input id="defineCheckPar1" name="defineCheckPar1"
						style="width:148px;height:20px;border:1px solid #86A3C4;" /></td>
						<td><span></span></td>
				</tr>
				<tr>
					<td style="text-align: right;"><span class="color_red">*</span>折扣类型：</td>
					<td><input id="defineDiscountType" name="defineDiscountType"/></td>
					<td><span></span></td>
				</tr>
				<tr>
					<td style="text-align: right;"><span class="color_red">*</span>折扣值：</td>
					<td><input id="defineDiscountPar1" name="defineDiscountPar1" 
						style="width:148px;height:20px;border:1px solid #86A3C4;" /></td>
						<td><span></span></td>
				</tr>
				<tr>
					<td style="text-align: right;">描述：</td>
					<td><textarea id="defineDescription" name="defineDescription" cols="22" rows="4"
							style="border: 1px solid #86A3C4;"></textarea></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>