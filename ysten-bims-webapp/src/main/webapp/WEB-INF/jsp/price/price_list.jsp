<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<%@ include file="/include/taglibs.jsp"%>
<%@ include file="/include/operamasks-ui-2.0.jsp"%>
<%@ include file="/include/css.jsp"%>
<%@ include file="/include/ysten.jsp"%>
<script language="javascript">
$(document).ready(function() {
	var isSubmitted = false;//防止重复提交
    $('#grid').omGrid({
    	dataSource : 'price_list.json',
    	width : '100%',
        height : rFrameHeight,
        singleSelect : false,
        limit : limit,
        colModel : [ 
                     {header : '<b>名称</b>', name : 'name', align : 'center', width : 160},
                     {header : '<b>价格(元)</b>', name : 'price', align : 'center', width : 160,renderer:function(value){return division(value,100).toFixed(2);}}, 
                     {header : '<b>价格(分)</b>', name : 'price', align : 'center', width : 160}, 
                     {header : '<b>折扣</b>', name : 'discount', align : 'center', width : 160,renderer:function(value){return value.toFixed(2);}}
		]
    });
    $('#create').omButtonbar({btns : [{label:"新增",icons : {left : opPath+'add.png'}}]});
    $('#update').omButtonbar({btns : [{label:"修改",icons : {left : opPath+'op-edit.png'}}]});
    $('#query').omButtonbar({btns : [{label:"查询",icons : {left : opPath+'search.png'}}]});
    $('#delete').omButtonbar({btns : [{label:"删除",icons : {left : opPath+'remove.png'}}]});
    $("#price").omNumberField({decimalPrecision:2});
    
    //搜索按钮提交事件
    $('#query').bind('click', function(e) {
        var priceName = filterStartEndSpaceTrim($('#priceName').val());
        $('#grid').omGrid("setData", 'price_list.json?priceName='+encodeURIComponent(priceName));
    });
    var dialog = $("#dialog-form").omDialog({
        width: 500,
        autoOpen : false,
        modal : true,
        resizable : false,
        draggable : false,
        buttons : {
            "提交" : function(){
            	if(!isSubmitted){
            		validator.form();
                	if(!checkPrice()){
                		isSubmitted = true;
                    	submitDialog();
                	}
            	}
            	return false;
            },
            "取消" : function() {
                $("#dialog-form").omDialog("close");
            }
        },
        onClose:function(){resetData();isSubmitted = false;}
    });
    
    var validator = $('#myform').validate({
        rules : {
        	name : {required: true}, 
        	price : {required : true},
        }, 
        messages : {
        	name : {required : "名称不能为空！"},
        	price : {required : "价格不能为空！"}
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
    
    var showDialog = function(title,rowData){
        validator.resetForm();
        resetData();
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
            			price:$("#price").val()
                    };
            	$.post('price_add.shtml',submitData,function(result){
                    $('#grid').omGrid('reload');
                    if("success" == result){
                    	$.omMessageTip.show({title: tip, content: "新增定价成功！", type:"success" ,timeout: time});
                    }else{
                    	$.omMessageTip.show({title: tip, content: "新增定价失败！", type:"error" ,timeout: time});
                    }
                    $("#dialog-form").omDialog("close");
                });
            }else{
            	submitData={
                    	id:$("#priceId").val(),
            			name:$("#name").val(),
            			price:$("#price").val()
                    };
            	$.post('price_update.shtml',submitData,function(result){
                    $('#grid').omGrid('reload');
                    if("success" == result){
                    	$.omMessageTip.show({title: tip, content: "修改定价成功！", type:"success" ,timeout: time});
                    }else{
                    	$.omMessageTip.show({title: tip, content: "修改定价失败！", type:"error" ,timeout: time});
                    }
                    $("#dialog-form").omDialog("close"); 
                });
            }
        }
    };
    
    var isAdd = true;
    $('#create').bind('click', function() {
        isAdd = true;
        showDialog('新增定价信息');
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
        showDialog('修改定价信息',selections[0]);
        $.ajax({
           	type:"post",
           	url:"price_to_update.json?id="+selections[0].id,
           	dataType:"json",
          	success:function(msg){
              	$("#priceId").val(msg['id']);
    			$("#name").val(msg['name']);
    			$("#hiddenName").val(msg['name']);
    			$("#price").val(division(msg['price'],100));
          }
          });
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
            content:'批量删除后基本定价数据将无法恢复，你确定要执行该操作吗？',
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
                	$.post('price_atom_delete.shtml',{ids:toDeleteRecordID.toString()},function(result){
                        $('#grid').omGrid('reload');
                        if(result=='success'){
                        	$.omMessageTip.show({title: tip, content: "删除基本定价成功！", timeout: time,type:'success'});
                        }else{
                        	$.omMessageTip.show({title: tip, content: "删除基本定价失败！", timeout: time,type:'error'});
                        }
                        $('#dialog-form').omDialog('close');
                    });
            	}
            }
        });  
    });
    
    $('#name').bind('blur', function() {
    	$("#showResult").html('');
    });
    $('#price').bind('blur', function() {
    	$("#showPriceResult").html('');
    });
    
    function checkPrice(){
    	var flag = false;
    	flag = checkPriceName();
    	var price = $("#price").val();
    	if(price>9999999.99){
    		$("#price").val('');
    		$("#showPriceResult").html('金额过大，请重新输入！');
    		flag = true;
    	}
    	return flag;
    }
    function checkPriceName(){
    	var name = $("#name").val();
    	if(name!=""){
	    	if ( isAdd== true) {
	    		return doCheckPriceNameAjax(name);
	    	}else{
	    		var hiddenName = $("#hiddenName").val();
	    		if(name!=hiddenName){
	    			return doCheckPriceNameAjax(name);
	    		}
	    		$("#showResult").html('');
	    	}
    	}
    }
    
    function doCheckPriceNameAjax(name) {
    	var flag = true;
    	$.ajax({
    		type : "post",
    		url : "price_name_exists.shtml",
    		dataType : "html",
    		data : "name=" + name,
    		async:false,
    		success : function(msg) {
    			if("success" != msg){
    				flag = false;
    			}
    			if(flag==true){
    				$("#showResult").html("该定价名称已存在");
    				$("#name").focus();
    			}else{
    				$("#showResult").html("可用");
    			}
    		}
    	});
    	return flag;
    }
});

function division(arg1,arg2){   
    var t1=0,t2=0,r1,r2;   
    try{t1=arg1.toString().split(".")[1].length}catch(e){}   
    try{t2=arg2.toString().split(".")[1].length}catch(e){}   
    with(Math){   
        r1=Number(arg1.toString().replace(".",""));   
        r2=Number(arg2.toString().replace(".",""));    
        return (r1/r2)*pow(10,t2-t1);   
    }   
}

function resetData(){
	$("#showResult").html('');
	$("#showPriceResult").html('');
}
</script>
</head>
<body>
	<table>
		<tbody>
			<tr>
				<c:if test="${sessionScope.add_price_atom != null }">
					<td><div id="create"/></td>
				</c:if>
				<c:if test="${sessionScope.update_price_atom != null }">
					<td><div id="update"/></td>
				</c:if>
				<c:if test="${sessionScope.delete_price_atom != null }">
					<td><div id="delete"/></td>
				</c:if>
				<td style="text-align: center;">名称：<input type="text"
					name="priceName" id="priceName"
					style="height: 20px; border: 1px solid #86A3C4;" /></td>
				<td><div id="query"/></td>
			</tr>
		</tbody>
	</table>
	<table id="grid"></table>

	<div id="dialog-form">
		<form id="myform">
			<input type="hidden" value="" name="priceId" id="priceId" />
			<input type="hidden" value="" name="hiddenName" id="hiddenName" />
			<table>
				<tr>
					<td style="text-align: right;"><span class="color_red">*</span>名称：</td>
					<td><input name="name" id="name" style="width:150px"/></td>
					<td><span></span><div id="showResult" style="display: inline;"></div></td>
				</tr>
					<tr>
					<td style="text-align: right;"><span class="color_red">*</span>价格(元)：</td>
					<td><input name="price" id="price"  style="width:150px"/></td>
					<td><span></span><div id="showPriceResult" style="display: inline;"></div></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>