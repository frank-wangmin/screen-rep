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
    $('#grid').omGrid({
    	dataSource : 'sp_define_list.json',
    	width : '100%',
        height : rFrameHeight,
        singleSelect : true,
        limit : limit,
        colModel : [ 
                     {header : '<b>运营商名称</b>', name : 'name', align : 'center', width : 200},
                     {header : '<b>运营商编号</b>', name : 'code', align : 'center', width : 200}, 
                     {header : '<b>运营商状态</b>', name : 'state', align : 'center', width : 160},
                     {header : '<b>创建时间</b>', name : 'createDate', align : 'center', width : 120},
                     {header : '<b>描述</b>', name : 'description',align : 'center',width: 'autoExpand'}
		]
    });
    $('#center-tab').omTabs({height:"33",border:false});
    $('#create').omButtonbar({btns : [{label:"新增",icons : {left : opPath+'add.png'}}]});
    $('#update').omButtonbar({btns : [{label:"修改",icons : {left : opPath+'op-edit.png'}}]});
    $('#query').omButtonbar({btns : [{label:"查询",icons : {left : opPath+'search.png'}}]});
    
    $('#query').bind('click', function(e) {
        var name1 = filterStartEndSpaceTrim($('#name1').val());
        var code1 = filterStartEndSpaceTrim($('#code1').val());
        $('#grid').omGrid("setData", 'sp_define_list.json?name='+encodeURIComponent(name1)+'&code='+code1);
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
            			name:$("#name").val(),
            			code:$("#code").val(),
            			state:$("#state").omCombo('value'),
            			description:$("#description").val(),
                    };
            	$.post('sp_define_add.shtml',submitData,function(result){
                    $('#grid').omGrid('reload');
                    if("success" == result){
                    	$.omMessageTip.show({title: tip, content: "新增运营商信息成功！", type:"success" ,timeout: time});
                    }else{
                    	$.omMessageTip.show({title: tip, content: "新增运营商信息失败！", type:"error" ,timeout: time});
                    }
                    $("#dialog-form").omDialog("close");
                });
            }else{
            	submitData={
                    	id:$("#id").val(),
            			name:$("#name").val(),
            			code:$("#code").val(),
            			state:$("#state").omCombo('value'),
            			description:$("#description").val(),
                    };
            	$.post('sp_define_update.shtml',submitData,function(result){
                    $('#grid').omGrid('reload');
                    if("success" == result){
                    	$.omMessageTip.show({title: tip, content: "修改运营商信息成功！", type:"success" ,timeout: time});
                    }else{
                    	$.omMessageTip.show({title: tip, content: "修改运营商信息失败！", type:"error" ,timeout: time});
                    }
                    $("#dialog-form").omDialog("close"); 
                });
            }
        }
    };
    
    var isAdd = true;
    $('#create').bind('click', function() {
        isAdd = true;
        getSelectDate('USABLE');
        showDialog('新增运营商信息');
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
           	url:"sp_define_to_update.json?id="+selections[0].id,
           	dataType:"json",
          	success:function(msg){
              	$("#id").val(msg['id']);
          		getSelectDate(msg['state']);
    			$("#name").val(msg['name']);
    			$("#code").val(msg['code']);
    			$("#description").val(msg['description']);
          }
          });
        
        showDialog('修改运营商信息',selections[0]);
    });
    
    var validator = $('#myform').validate({
        rules : {
        	name : {required: true}, 
        	code : {required : true},
        	state : {required : true},
        }, 
        messages : {
        	name : {required : "运营商名称不能为空！"},
        	code : {required : "运营商编号不能为空！"},
        	state : {required : "请选择状态！"}
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

function getSelectDate(state){
	$('#state').omCombo({dataSource : 'sp_state_list.json',editable:false,width:150,listMaxHeight:100,value:state});
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
		 <li><a href="#tab1">运营商信息列表:</a></li>
	</ul>
</div>
<table >
     <tbody>
        <tr>
        <c:if test="${sessionScope.add_sp_define != null }">
            <td><div id="create"/></td>
        </c:if>
        <c:if test="${sessionScope.update_sp_define != null }">
            <td><div id="update"/></td>
        </c:if>
            <td style="text-align:center;">运营商名称：</td>
            <td><input type="text" name="name1" id="name1" style="height: 20px;border:1px solid #86A3C4;"/></td>
            <td style="text-align:center;">运营商编号：</td>
            <td><input name="code1" id="code1" style="height: 20px;border:1px solid #86A3C4;"/></td>
            <td><div id="query"/></td>
        </tr>
     </tbody>
</table>
<table id="grid" ></table>
        
<div id="dialog-form">
     <form id="myform">
     <input type="hidden" value="" name="id" id="id"/>
			<table>
	            <tr>
	                <td><span class="color_red">*</span>运营商名称：</td>
	                <td><input id="name" name="name" style="width:148px;height:20px;border:1px solid #86A3C4;" onblur="checkSpNameExists()" maxlength="16"/></td>
	                <td><span></span><div id="showResult" style="display: inline;"/></td>
	            </tr>
	            <tr>
	                <td><span class="color_red">*</span>运营商编号：</td>
	                <td><input id="code" name="code" style="width:148px;height:20px;border:1px solid #86A3C4;" onblur="checkSpCodeExists();" maxlength="3"/></td>
	                <td><span></span><div id="showResult1" style="display: inline;"/></td>
	            </tr>
	            <tr>
	                <td><span class="color_red">*</span>运营商状态：</td>
	                <td><input id="state" name="state"/></td>
	                <td><span></span></td>
	            </tr>
	            <tr>
	                <td>运营商描述：</td>
	                <td><textarea id="description" name="description" cols="22" rows="4" style="border:1px solid #86A3C4;"></textarea></td>
	                <td><span></span><div id="desc" style="display: inline;"/></td>
	            </tr>
			</table>
	</form>
</div>					
</body>
</html>
