<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title></title>
<%@ include file="/include/taglibs.jsp"%>
<%@ include file="/include/operamasks-ui-2.0.jsp"%>
<%@ include file="/include/css.jsp"%>
<%@ include file="/include/ysten.jsp"%>
<script>
$(document).ready(function() {
	$('#center-tab').omTabs({height:"33",border:false});
    $('#grid').omGrid({
    	dataSource : 'find_apk_software_code_list.json',
    	width : '100%',
        height : rFrameHeight,
        singleSelect : false,
        limit : limit,
        colModel:[
      	        {header:'<b>APK软件号名称</b>',name:'name',align:'center',width:140},
      			{header:'<b>APK软件号编码</b>',name:'code',align:'center',width:180},
      			{header:'<b>APK软件号状态</b>',name:'status',align:'center',width:100},
      			{header:'<b>创建时间</b>',name:'createDate',align:'center',width:120},
      			{header:'<b>最后操作时间</b>',name:'lastModifyTime',align:'center',width:120},
      			{header:'<b>操作者</b>',name:'operUser',align:'center',width:60},
      			{header:'<b>描述</b>',name:'description',align:'center',width: 'autoExpand'}
      		]
    }); 
    $('#create').omButtonbar({btns : [{label:"新增",icons : {left : opPath+'add.png'}}]});
    $('#update').omButtonbar({btns : [{label:"修改",icons : {left : opPath+'op-edit.png'}}]});
    $('#query').omButtonbar({btns : [{label:"查询",icons : {left : opPath+'search.png'}}]});
    $('#status').omCombo({
        dataSource : [ 
                      {text : '可用', value : 'USABLE'}, 
                       {text : '不可用', value : 'DISABLED'}
                      ],value:'USABLE'
    });
    
    $('#query').bind('click', function(e) {
        var name = $('#queryName').val();
        var code = $('#queryCode').val();
        $('#grid').omGrid("setData", 'find_apk_software_code_list.json?name='+filterStartEndSpaceTrim(encodeURIComponent(name))+'&code='+filterStartEndSpaceTrim(encodeURIComponent(code)));
    });
    var dialog = $("#dialog-form").omDialog({
        width: 450,
        height: 250,
        autoOpen : false,
        modal : true,
        resizable : false,
        draggable : false,
        buttons : {
            "提交" : function(){
            	var sr = $("#showResult").html();
            	var sr1 = $("#showResult1").html();
                if((sr=='' || sr =='可用!') && (sr1=='' || sr1 =='可用!')){
                	submitDialog();
                }
                return false; 
            },
            "取消" : function() {
                $("#dialog-form").omDialog("close");
                
            }
        },onClose:function(){
        	$("#showResult").text("");
        	$("#showResult1").text("");
            $("#dialog-form").omDialog("close");
            $('#dialog-form').validate().resetForm();
        resetData();}
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
              			name:filterStartEndSpaceTrim($("#name").val()),
              			code:filterStartEndSpaceTrim($("#code").val()),
              			status:filterStartEndSpaceTrim($("#status").val()),
              			description:filterStartEndSpaceTrim($("#description").val())
                    };
            	$.post('apk_soft_code_add.json',submitData,function(result){
                    $('#grid').omGrid('reload');
                    if("success" == result){
                    	$.omMessageTip.show({title: tip, content: "新增APK软件信息号成功！", type:"success" ,timeout: time});
                    }else{
                    	$.omMessageTip.show({title: tip, content: "新增APK软件信息号失败！", type:"error" ,timeout: time});
                    }
                    $("#dialog-form").omDialog("close");
                });
            }else{
            	submitData={
            			id:$("#id").val(),
            			name:$("#name").val(),
              			code:$("#code").val(),
              			status:$("#status").val(),
              			description:$("#description").val()           			
                    };
            	$.post('apk_soft_code_update.json',submitData,function(result){
                    $('#grid').omGrid('reload');
                    if("success" == result){
                    	$.omMessageTip.show({title: tip, content: "修改APK软件信息号成功！", type:"success" ,timeout: time});
                    }else{
                    	$.omMessageTip.show({title: tip, content: "修改APK软件信息号失败！该软件信息号关联了升级任务，不可改为不可用状态，请确认！", type:"error" ,timeout: time});
                    }
                    $("#dialog-form").omDialog("close"); 
                });
            }
        }
    };
    var isAdd = true;
    $('#create').bind('click', function() {
        isAdd = true;
        showDialog('新增APK软件号信息');//显示dialog
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
           	url:"apk_soft_code_to_update.json?id="+selections[0].id,
           	dataType:"json",
          	success:function(msg){
              	$("#id").val(msg['id']);
    			$("#name").val(msg['name']);
    			$("#code").val(msg['code']);
    			$('#status').omCombo({
    		        dataSource : [ 
    		                      {text : '可用', value : 'USABLE'}, 
    		                       {text : '不可用', value : 'DISABLED'}
    		                      ],value:msg['status']
    		    }),
    			$("#description").val(msg['description'])
          }
          });
        
        showDialog('修改APK软件号信息',selections[0]);
    });
    var validator = $('#myform').validate({
        rules : {      	   
            	name:{required: true,maxlength: 128},
            	code:{required: true,maxlength: 64},
            	status:{required: true},
        }, 
        messages : {        	
        	name:{required: "软件号名称不为空！",maxlength: "软件号名称不大于128位！"},
        	code:{required: "编号不为空！",maxlength: "编号不大于64位！"},
			status:{required: "状态不为空！"},
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
})
function checkNameExists(){
	if($.trim($("#name").val())!=""){
	   var par = new Object();
	   par['name'] = filterStartEndSpaceTrim($("#name").val());	   
	   par['id'] = $("#id").val();
       $.ajax({
       type:"post",
       url:"apk_softCode_name_exists.shtml?s="+Math.random(),
       dataType:"html",
       data:par,
       success:function(msg){ 
           //alert(msg); 
           $("#showResult").html(msg);
       }
      });
    }else if($.trim($("#name").val())=="" && $("#showResult").html() != ""){
    	$("#showResult").html("");
        }
}
function resetData(){
	$("#showResult").text("");
	$("#showResult1").text("");
	$("#showResult").html('');
	$("#showResult1").html('');
}
function checkCodeExists(){
	if($.trim($("#code").val())!=""){
	   var par = new Object();
	   par['code'] = filterStartEndSpaceTrim($("#code").val());	   
	   par['id'] = $("#id").val();
       $.ajax({
       type:"post",
       url:"apk_softCode_code_exists.shtml?s="+Math.random(),
       dataType:"html",
       data:par,
       success:function(msg){
    	 //alert(msg);   
           $("#showResult1").html(msg);
       }
      });
    }else if($.trim($("#code").val())=="" && $("#showResult1").html() != ""){
    	$("#showResult1").html("");
        }
}
</script>
</head>
<body>
	<div id="center-tab">
		<ul>
			<li><a href="#tab1">APK软件号信息列表:</a></li>
		</ul>
	</div>
    <table>
        <tr align="left">
                <c:if test="${sessionScope.add_apk_soft_code != null }">          
                <td><div id="create"></div></td>
                </c:if>
                <c:if test="${sessionScope.update_apk_soft_code != null }">
                <td><div id="update"></div></td>
                </c:if>
            <td>APK软件号名称：</td>
            <td>
                <input type="text" name="queryName" id="queryName" style="width:200px;height: 20px;border:1px solid #86A3C4;"/>
            </td>
            <td>APK软件号编码：</td>
            <td>
                <input type="text" name="queryCode" id="queryCode" style="width:200px;height: 20px;border:1px solid #86A3C4;"/>
            </td>
            <td>
             <div id="query"/>
           </td>
         </tr>
    </table>
    <table id="grid" ></table>
    <div id="dialog-form">
     <form id="myform">
     <input type="hidden" value="" name="id" id="id"/>
			<table>
	            <tr>
	                <td><span class="color_red">*</span>APK软件号名称：</td>
	                <td><input id="name" name="name" style="width:148px;height:20px;border:1px solid #86A3C4;" onblur="checkNameExists()"/></td>
	                <td><span></span><div id="showResult" class="color_red" style="display: inline;"/></td>
	            </tr>	            
	            <tr>
	                <td><span class="color_red">*</span>APK软件号编码：</td>
	                <td><input id="code" name="code" style="width:148px;height:20px;border:1px solid #86A3C4;" onblur="checkCodeExists()"/></td>
	                <td><span></span><div id="showResult1" class="color_red" style="display: inline;"/></td>
	            </tr>	            
	            <tr>
	                <td><span class="color_red">*</span>APK软件号状态：</td>
	                <td><input id="status" name="status" style="width:129px"/></td>
	                <td><span></span></td>
	            </tr>	            
	            <tr>
	                <td>APK软件号描述：</td>
	                <td><textarea id="description" name="description" cols="40" rows="5" style="border:1px solid #86A3C4;" maxlength="2048"></textarea></td>
	                <td><span></span></td>
	            </tr>
			</table>
	</form>
</div>
</body>
</html>