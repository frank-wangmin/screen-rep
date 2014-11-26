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
    	dataSource : 'screen_manager_list.json',
    	width : '100%',
        height : rFrameHeight,
        singleSelect : false,
        limit : limit,
        colModel : [ 
                     {header : '<b>名称</b>', name : 'name', align : 'center', width : 150},
                     {header : '<b>区域</b>', name : 'area', align : 'center', width : 80,renderer:function(value){return value.name;}},
                     {header : '<b>地址</b>', name : 'url', align : 'center', width : 300}, 
                     {header : '<b>创建时间</b>', name : 'createDate', align : 'center', width : 120},
                     {header : '<b>描述</b>', name : 'description', align : 'center', width : 350}
		]
    });

    $('#create').omButtonbar({btns : [{label:"新增",icons : {left : opPath+'add.png'}}]});
    $('#update').omButtonbar({btns : [{label:"修改",icons : {left : opPath+'op-edit.png'}}]});
    $('#query').omButtonbar({btns : [{label:"查询",icons : {left : opPath+'search.png'}}]});
    $('#delete').omButtonbar({btns : [{label:"删除",icons : {left : opPath+'remove.png'}}]});
    
    $('#area').omCombo({dataSource : 'area.json',editable:false,width:202,listMaxHeight:160});
    
    $('#query').bind('click', function(e) {
        var name1 = filterStartEndSpaceTrim($('#name1').val());
        $('#grid').omGrid("setData", 'screen_manager_list.json?name='+encodeURIComponent(name1));
    });

    var dialog = $("#dialog-form").omDialog({
        width: 600,
        autoOpen : false,
        modal : true,
        resizable : false,
        draggable : false,
        buttons : {
            "提交" : function(){
            	validator.form();
               	submitDialog();
                return false; 
            },
            "取消" : function() {
                $("#dialog-form").omDialog("close");
            }
        },onClose:function(){}
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
            			url:$("#url").val(),
            			areaId:$("#area").omCombo('value'),
            			description:$("#description").val(),
                    };
            	$.post('screen_manager_add.shtml',submitData,function(result){
                    $('#grid').omGrid('reload');
                    if("success" == result){
                    	$.omMessageTip.show({title: tip, content: "新增屏幕管理信息成功！", type:"success" ,timeout: time});
                    }else{
                    	$.omMessageTip.show({title: tip, content: "新增屏幕管理信息失败！", type:"error" ,timeout: time});
                    }
                    $("#dialog-form").omDialog("close");
                });
            }else{
            	submitData={
                    	id:$("#id").val(),
            			name:$("#name").val(),
            			url:$("#url").val(),
            			areaId:$("#area").omCombo('value'),
            			description:$("#description").val(),
                    };
            	$.post('screen_manager_update.shtml',submitData,function(result){
                    $('#grid').omGrid('reload');
                    if("success" == result){
                    	$.omMessageTip.show({title: tip, content: "修改屏幕管理信息成功！", type:"success" ,timeout: time});
                    }else{
                    	$.omMessageTip.show({title: tip, content: "修改屏幕管理信息失败！", type:"error" ,timeout: time});
                    }
                    $("#dialog-form").omDialog("close"); 
                });
            }
        }
    };
    
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
            content:'批量删除后数据将无法恢复，你确定要执行该操作吗？',
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
                	$.post('screen_manager_delete.json',{ids:toDeleteRecordID.toString()},function(result){
                        $('#grid').omGrid('reload');
                        if(result=='success'){
                        	$.omMessageTip.show({title: tip, content: "删除屏幕管理信息成功！", timeout: time,type:'success'});
                        }else{
                        	$.omMessageTip.show({title: tip, content: "删除屏幕管理信息失败！", timeout: time,type:'error'});
                        }
                        $('#dialog-form').omDialog('close');
                    });
            	}
            }
        });  
    });
    
    var isAdd = true;
    $('#create').bind('click', function() {
        isAdd = true;
        showDialog('新增屏幕管理信息');
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
           	url:"screen_manager_to_update.json?id="+selections[0].id,
           	dataType:"json",
          	success:function(msg){
              	$("#id").val(msg['id']);
    			$("#name").val(msg['name']);
    			$("#url").val(msg['url']);
    			$("#description").val(msg['description']);
    			$('#area').omCombo({dataSource : 'area.json',editable:false,width:202,listMaxHeight:160,value:msg['area']['id']});
          }
          });
        showDialog('修改屏幕管理信息',selections[0]);
    });
    
    var validator = $('#myform').validate({
        rules : {
        	name : {required: true}, 
        	url : {required : true},
        	area : {required : true},
        }, 
        messages : {
        	name : {required : "名称不能为空！"},
        	url : {required : "地址不能为空！"},
        	area : {required : "请选择区域！"}
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
	 <li><a href="#tab1">屏幕管理信息列表:</a></li>
</ul>
</div>
<table >
     <tbody>
        <tr>
        <c:if test="${sessionScope.add_screen_manager != null }">
            <td><div id="create"/></td>
        </c:if>
        <c:if test="${sessionScope.update_screen_manager != null }">
            <td><div id="update"/></td>
        </c:if>
         <c:if test="${sessionScope.delete_screen_manager != null }">
            <td><div id="delete"/></td>
        </c:if>
            <td style="text-align:center;">名称：</td>
            <td><input type="text" name="name1" id="name1" style="height: 20px;border:1px solid #86A3C4;"/></td>
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
	                <td><span class="color_red">*</span>名称：</td>
	                <td><input id="name" name="name" style="width:200px;height:20px;border:1px solid #86A3C4;"/></td>
	                <td><span></span></td>
	            </tr>
	            <tr>
	                <td><span class="color_red">*</span>地址：</td>
	                <td><input id="url" name="url" style="width:200px;height:20px;border:1px solid #86A3C4;"/></td>
	                <td><span></span></td>
	            </tr>
	            <tr>
	                <td><span class="color_red">*</span>区域：</td>
	                <td><input id="area" name="area"/></td>
	                <td><span></span></td>
	            </tr>
	            <tr>
					<td style="text-align: right;">描述：</td>
					<td><textarea id="description" name="description" cols="31" rows="3" style="border: 1px solid #86A3C4;"></textarea></td>
					<td><span></span></td>
				</tr>
			</table>
	</form>
</div>					
</body>
</html>
