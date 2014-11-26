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
	$('#isNew').omCombo({
        dataSource : [ 
				       {text : '', value : ''},
                      {text : '是', value : '1'}, 
                       {text : '否', value : '0'}
                      ]
    });
	$('#isNew1').omCombo({
        dataSource : [ 
				       {text : '', value : ''},
                      {text : '是', value : '1'}, 
                       {text : '否', value : '0'}
                      ]
    });
    $('#grid').omGrid({
    	dataSource : 'text_box_list.json',
    	width : '100%',
        height : rFrameHeight,
        singleSelect : false,
        limit :limit,
        colModel : [ 
				{header : '<b>文本集ID</b>', name : 'textBoxId', align : 'center', width : 120}, 
				{header : '<b>节目编号</b>', name : 'progromId',align : 'center',width: 150},
				{header : '<b>标题</b>', name : 'title',align : 'center',width: 256}	,
				{header : '<b>是否为新节目</b>', name : 'isNew',align : 'center',width: 80, renderer:function(value){
                    if(value==1){
                        return "是";
                        }else if(value==0){
                       	 return "否";
                            }
                    }
                }
						
		]
    });   
     
    $('#create').omButtonbar({btns : [{label:"新增",icons : {left : opPath+'add.png'}}]});
    $('#update').omButtonbar({btns : [{label:"修改",icons : {left : opPath+'op-edit.png'}}]});	
    $('#query').omButtonbar({btns : [{label:"查询",icons : {left : opPath+'search.png'}}]});
    $('#delete').omButtonbar({btns : [{label:"删除",icons : {left : opPath+'remove.png'}}]});
    $('#sync').omButtonbar({btns : [{label:"更新同步",icons : {left : opPath+'op-btn-icon.png'}}]});
    
    $('#query').bind('click', function(e) {
        var title = filterStartEndSpaceTrim($('#title').val());
        var programId = filterStartEndSpaceTrim($('#programId').val());
        var isNew = $("#isNew").omCombo("value");
        $('#grid').omGrid("setData", 'text_box_list.json?title='+encodeURIComponent(title)+"&progromId="+encodeURIComponent(programId)+"&isNew="+encodeURIComponent(isNew));
    });
    
    $("#sync").click(function(){
		$.post('upload_boxs.json?', {}, function(result){
			$('#grid').omGrid('reload');
			if("success" == result){
            	$.omMessageTip.show({title: tip, content: "上传成功！", type:"success" ,timeout: time});
            }else{
            	$.omMessageTip.show({title: tip, content: "上传失败！", type:"error" ,timeout: time});
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
                	$.post('text_delete.json',{ids:toDeleteRecordID.toString()},function(result){
                        $('#grid').omGrid('reload');
                        if(result=='success'){
                        	$.omMessageTip.show({title: tip, content: "删除信息成功！", timeout: time,type:'success'});
                        }else{
                        	$.omMessageTip.show({title: tip, content: "删除信息失败！", timeout: time,type:'error'});
                        }
                        $('#dialog-form').omDialog('close');
                    });
            	}
            }
        });  
    });
    var dialog = $("#dialog-form").omDialog({
        width: 480,
        autoOpen : false,
        modal : true,
        resizable : false,
        draggable : false,
        buttons : {
            "提交" : function(){
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
            			textBoxId:$("#textBoxId").val(),
            			title:$("#title1").val(),
            			isNew:$("#isNew1").val(),
            			progromId:$("#progromId1").val()
                    };
            	$.post('text_add.json',submitData,function(result){
                    $('#grid').omGrid('reload');
                    if("success" == result){
                    	$.omMessageTip.show({title: tip, content: "新增成功！", type:"success" ,timeout: time});
                    }else{
                    	$.omMessageTip.show({title: tip, content: "新增失败！", type:"error" ,timeout: time});
                    }
                    $("#dialog-form").omDialog("close");
                });
            }else{
            	submitData={
            			id:$("#id").val(),
            			textBoxId:$("#textBoxId").val(),
            			title:$("#title1").val(),
            			isNew:$("#isNew1").val(),
            			progromId:$("#progromId1").val()
                    };
            	$.post('text_update.json',submitData,function(result){
                    $('#grid').omGrid('reload');
                    if("success" == result){
                    	$.omMessageTip.show({title: tip, content: "修改成功！", type:"success" ,timeout: time});
                    }else{
                    	$.omMessageTip.show({title: tip, content: "修改失败！", type:"error" ,timeout: time});
                    }
                    $("#dialog-form").omDialog("close"); 
                });
            }
        }
    };
    var isAdd = true;
    $('#create').bind('click', function() {
        isAdd = true;
        showDialog('新增');//显示dialog
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
           	url:"text_to_update.json?id="+selections[0].id,
           	dataType:"json",
          	success:function(msg){
              	$("#id").val(msg['id']);
    			$("#textBoxId").val(msg['textBoxId']),
    			$("#title1").val(msg['title']),
    			$("#isNew1").val(msg['isNew']),
    			$("#progromId1").val(msg['progromId'])
          }
          });
        
        showDialog('修改IP地址库信息',selections[0]);
    });
    var validator = $('#myform').validate({
        rules : {       	   
            	textBoxId:{required: true},
    			title:{required: true},
    			isNew:{required: true},
    			progromId:{required: true} 
        }, 
        messages : {        	
        	textBoxId:{required: "不为空！"},
			title:{required: "不为空！"},
			isNew:{required: "不为空！"},
			progromId:{required: "不为空！"}
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


</script>
</head>
<body>
<div id="center-tab">
<ul>
	 <li><a href="#tab1">首页文本信息列表:</a></li>
</ul>
</div>
       <table >
            <tr>
            	<c:if test="${sessionScope.add_panel_text_recommend != null }"><td><div id="create"/></td></c:if>
	            <c:if test="${sessionScope.update_panel_text_recommend != null }"><td><div id="update"/></td></c:if>
	            <c:if test="${sessionScope.delete_panel_text_recommend != null }"><td><div id="delete"/></td></c:if>
				<c:if test="${sessionScope.sync_panel_text_recommend != null }"><td><div id="sync"/></td></c:if>
               <td>
               		标题：<input type="text" name="title" id="title" style="width:150px;height: 20px;border:1px solid #86A3C4;"/>
               </td>
               <td>
               		节目编号：<input type="text" name="programId" id="programId" style="width:150px;height: 20px;border:1px solid #86A3C4;"/>
               </td>
               <td>
               	           是否为新：<input type="text" name="isNew" id="isNew" style="width:50px"/>
               </td>
                <td><div id="query"/></td>
             </tr>
        </table>
<table id="grid" ></table>
<div id="dialog-form">
     <form id="myform">
     <input type="hidden" value="" name="id" id="id"/>
			<table>						
	            <tr>
	                <td><span class="color_red">*</span>文本集ID：</td>
	                <td><input id="textBoxId" name="textBoxId" style="width:148px;height:20px;border:1px solid #86A3C4;"/></td>
	                <td><span></span></td>
	            </tr>	            
	            <tr>
	                <td><span class="color_red">*</span>标题：</td>
	                <td><input id="title1" name="title1" style="width:148px;height:20px;border:1px solid #86A3C4;"/></td>
	                <td><span></span></td>
	            </tr>	            
	            <tr>
	                <td><span class="color_red">*</span>是否为新节目：</td>
	                <td><input id="isNew1" name="isNew1" style="width:129px"/></td>
	                <td><span></span></td>
	            </tr>	            
	            <tr>
	                <td><span class="color_red">*</span>节目编号：</td>
	                <td><input id="progromId1" name="progromId1" style="width:148px;height:20px;border:1px solid #86A3C4;"/></td>
	                <td><span></span></td>
	            </tr>
			</table>
	</form>
</div>
</body>
</html>