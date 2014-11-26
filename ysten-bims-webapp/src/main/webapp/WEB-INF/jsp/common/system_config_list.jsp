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
    	dataSource : 'system_config_list',
    	width : '100%',
        height : rFrameHeight,
        singleSelect : true,
        limit : 10000,
        colModel : [ 
			{header : '<b>配置名称</b>', name : 'zhName',align : 'center',width: 200},
			{header : '<b>配置键值</b>', name : 'configKey', align : 'center', width : 150},
			{header : '<b>配置值</b>', name : 'configValue',align : 'center',width: 400},
			{header : '<b>配置描述</b>', name : 'depiction',align : 'center',width: 'autoExpand'}
		]
    });
	
    $('#update').omButtonbar({btns : [{label:"修改",icons : {left : opPath+'op-edit.png'}}]});
    $('#query').omButtonbar({btns : [{label:"查询",icons : {left : opPath+'search.png'}}]});
    $('#reload').omButtonbar({btns : [{label:"更新缓存",icons : {left : opPath+'op-edit.png'}}]});
	    
    $('#query').bind('click', function(e) {
        var configKey = filterStartEndSpaceTrim($('#key').val().trim());
        var name = filterStartEndSpaceTrim($('#name').val().trim());
        $('#grid').omGrid("setData", 'system_config_query_bykey?configKey=' + configKey+'&name='+name);
    });
    
    $('#update').bind('click', function() {
    	var selections=$('#grid').omGrid('getSelections',true);
    	if (selections.length == 0 || selections.length > 1) {
            $.omMessageBox.alert({
                type:'alert',
                title:tip,
                content:'系统配置信息修改至少且只能选择一行记录！',
            });
            return false;
        }
        $.ajax({
            url: "get_sysconfig_by_key.json?key=" + selections[0].configKey,
            dataType: "json",
            success: function (rowData) {
                if (rowData) {
                    $("input[name='id']",dialog_update).val(rowData.id);
                    $("input[name='configKey']",dialog_update).val(rowData.configKey);
                    $("input[name='configValue']",dialog_update).val(rowData.configValue);
                    $("input[name='zhName']",dialog_update).val(rowData.zhName);
                    $("input[name='depiction']",dialog_update).val(rowData.depiction);
                }
            }
        });
    	showDialog("系统配置信息修改");
    });
    
    $('#reload').bind('click', function() {
    	var selections=$('#grid').omGrid('getSelections',true);
    	if (selections.length == 0 || selections.length > 1) {
            $.omMessageBox.alert({
                type:'alert',
                title:tip,
                content:'更新系统配置信息至少且只能选择一行记录！',
            });
            return false;
        }
    	var submitData={
                configKey:selections[0].configKey,
                configValue:selections[0].configValue
        };
    	$.post('system_config_reload',submitData,function(msg){
        	if("success" == msg){
        		$.omMessageTip.show({title: tip, content: "更新系统参数成功！", type:"success" ,timeout: time});
        	}else{
        		$.omMessageTip.show({title: tip, content: "更新系统参数失败！", type:"success" ,timeout: time});
        	}
            $('#grid').omGrid('reload');
        });
    });
    
    var dialog_update = $("#dialog-form").omDialog({
        width: 550,
        autoOpen : false,
        modal : true,
        resizable : false,
        buttons : {
            "提交" : function(){
            	submitDialog();
                return false;
            },
            "取消" : function() {
                $("#dialog-form").omDialog("close");
            }
        }
    });

    
    var showDialog = function(title){
    	validator.resetForm();
    	dialog_update.omDialog("option", "title", title);
    	dialog_update.omDialog("open");
    }
    
    var submitDialog = function(){
        if (validator.form()) {
            var submitData={
                configKey:$("input[name='configKey']",dialog_update).val(),
                configValue:$("input[name='configValue']",dialog_update).val()
            };
            $.post('system_config_update',submitData,function(msg){
            	if(msg.status == 1){
            		$.omMessageTip.show({title: tip, content: "修改系统参数成功！", type:"success" ,timeout: time});
            	}else{
            		$.omMessageTip.show({title: tip, content: "修改系统参数失败！", type:"success" ,timeout: time});
            	}
                $('#grid').omGrid('reload');
                $("#dialog-form").omDialog("close");
            });
        }
    };

    var validator = $('#sysDeployForm').validate({
        rules : {
        	configValue : {required: true}
        }, 
        messages : {
        	configValue : {required : "值不能为空"}
        }
    });
    $('#center-tab').omTabs({height:"33",border:false});
});
</script>
</head>
<body>
<div id="center-tab">
<ul>
	 <li><a href="#tab1">系统参数配置信息列表:</a></li>
</ul>
</div>
<table >
        <tbody>
            <tr>
            	<c:if test="${sessionScope.update_sysconfig != null }">
	            <td>
	                 <div id="update"/>
	            </td>
	            </c:if>
            	<!-- <c:if test="${sessionScope.update_sysconfig_date != null }">
            	<td>
	                 <div id="reload"/>
	            </td>
	            </c:if> -->
	           <td>配置名称：</td>
               <td><input type="text" name="name" id="name" style="height: 20px;width:180px;border:1px solid #86A3C4;"/></td>
               <td>配置键值：</td>
               <td><input type="text" name="key" id="key" style="height: 20px;width:180px;border:1px solid #86A3C4;"/></td>
               <td><div id="query"/></td>
             </tr>
           </tbody>
        </table>
<table id="grid" ></table>
<div id="dialog-form">
<form id="sysDeployForm">
 <input name="id" style="display: none"/>
 <table>
 	 <tr>
         <td>配置名称：</td>
         <td><input name="zhName" style="width:300px;height:20px;border:1px solid #86A3C4;"/></td>
     </tr>
     <tr>
         <td>配置键值：</td>
         <td><input name="configKey" disabled="disabled" style="width:300px;height:20px;border:1px solid #86A3C4;"/></td>
     </tr>
     <tr>
         <td>配置值：</td>
         <td><input name="configValue" style="width:300px;height:20px;border:1px solid #86A3C4;"/></td>
     </tr>
     <tr>
         <td>配置描述：</td>
         <td><input name="depiction" style="width:300px;height:20px;border:1px solid #86A3C4;"/></td>
     </tr>
 </table>
</form>
</div>
</body>
</html>