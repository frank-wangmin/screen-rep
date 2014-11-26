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
	    	dataSource : 'role_list.json',
	    	width : '100%',
	        height : rFrameHeight,
	        singleSelect : true,
	        limit : limit,
	        colModel : [ 
	                     {header : '<b>角色名称</b>', name : 'name', align : 'center', width : 300},
	                     {header : '<b>创建时间</b>', name : 'createDate', align : 'center', width : 300},
	                     {header : '<b>描述</b>', name : 'description', align : 'center', width : 470}
			]
	    });
	    
	    $('#create').omButtonbar({btns : [{label:"新增",icons : {left : opPath+'add.png'}}]});
	    $('#update').omButtonbar({btns : [{label:"修改",icons : {left : opPath+'op-edit.png'}}]});
	    $('#query').omButtonbar({btns : [{label:"查询",icons : {left : opPath+'search.png'}}]});
	    $('#assign').omButtonbar({btns : [{label:"权限分配",icons : {left : opPath+'op-edit.png'}}]});

	    var dialog = $("#dialog-form").omDialog({
	        width: 410,
	        height:220,
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
	        }
	    });
	    var showDialog = function(title,rowData){
	        validator.resetForm();
	        rowData = rowData || {};
	        $("input[name='id']",dialog).val(rowData.id);
	        $("input[name='roleName']",dialog).val(rowData.name);
	        $("#description",dialog).val(rowData.description);
	        dialog.omDialog("option", "title", title);
	        dialog.omDialog("open");
	    };
	    var submitDialog = function(){
	        var submitData;
	        if (validator.form()) {
	            if(isAdd){
	            	submitData={
	            			roleName:$("input[name='roleName']",dialog).val(),
	            			description:$("#description").val()
	                    };
	            	$.post('role_add.shtml',submitData,function(result){
	                    $('#grid').omGrid('reload');
	                    if(result=='success'){
                        	$.omMessageTip.show({title: tip, content: "新增角色信息成功！", timeout: time,type:'success'});
                        }else{
                        	$.omMessageTip.show({title: tip, content: "新增角色信息失败！", timeout: time,type:'error'});
                        }
	                    $("#dialog-form").omDialog("close"); 
	                });
	            }else{
	            	submitData={
	            			roleName:$("input[name='roleName']",dialog).val(),
	            			description:$("#description").val(),
	            			roleId:$("input[name='id']",dialog).val()
	                    };
	            	$.post('role_update.shtml',submitData,function(result){
	                    $('#grid').omGrid('reload');
	                    if(result=='success'){
                        	$.omMessageTip.show({title: tip, content: "修改角色信息成功！", timeout: time,type:'success'});
                        }else{
                        	$.omMessageTip.show({title: tip, content: "修改角色信息失败！", timeout: time,type:'error'});
                        }
	                    $("#dialog-form").omDialog("close"); 
	                });
	            }
	        }
	    };
	    var isAdd = true;
	    $('#create').bind('click', function() {
	        isAdd = true;
	        showDialog('新增角色信息');
	    });
	    $('#update').bind('click', function() {
	        var selections=$('#grid').omGrid('getSelections',true);
	        if (selections.length == 0 || selections.length > 1) {
	        	$.omMessageBox.alert({
	                type:'alert',
	                title:tip,
	                content:'修改操作至少且只能选择一行记录！',
	            });
	            return false;
	        }
	        isAdd = false;
	        showDialog('修改角色信息',selections[0]);
	    });
	    //搜索按钮提交事件
	    $('#query').bind('click', function(e) {
	        var roleName = filterStartEndSpaceTrim($('#roleNameQuery').val());
	        $('#grid').omGrid("setData", 'role_list.json?roleName='+encodeURIComponent(roleName));
	    });
	    var validator = $('#myForm').validate({
	        rules : {
	        	roleName : {required: true},
	        }, 
	        messages : {
	        	roleName : {required : "   角色名称不能为空！"}
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
	    /*****************************************权限分配开始*************************************************/
	    var cRoleSeleted; 
	    $("#myTree").omTree({
	   	     dataSource : "authority_list.json",
	   	     simpleDataModel: true,
	   	     classes:'folder',
	   	     draggable : false,
	   	     showIcon:true,
	   	  	 showCheckbox:true
	   	 	});
	    $('#assign').bind('click', function() {
	        var selections=$('#grid').omGrid('getSelections',true);
	        cRoleSeleted = selections[0];
	        if (selections.length == 0 || selections.length > 1) {
	        	$.omMessageBox.alert({
	                type:'alert',
	                title:tip,
	                content:'修改操作至少且只能选择一行记录！',
	            });
	            return false;
	        }
	        isAdd = false;
	        showAssignDialog('角色权限分配',selections[0]);
	    });
	    var showAssignDialog = function(title,rowData){
	        rowData = rowData || {};
	        assignDialog.omDialog("option", "title", title);
	        assignDialog.omDialog("open");
	        setData(rowData);
	    };
	    var assignDialog = $("#assign-form").omDialog({
	        width: 400,
	        height:400,
	        autoOpen : false,
	        modal : true,
	        resizable : false,
	        draggable : false,
	        buttons : {
	            "提交" : function(){
	                submitAssignDialog();
	                return false; 
	            },
	            "取消" : function() {
	                $("#assign-form").omDialog("close");
	            }
	        },onClose:function(){
	        	$("#myTree").omTree({
		   	   	     dataSource : "authority_list.json",
		   	   	     simpleDataModel: true,
		   	   	     classes:'folder',
		   	   	     draggable : false,
		   	   	     showIcon:true,
		   	   	  	 showCheckbox:true
		   	   	 	});
                $('#assignForm').validate().resetForm();
            }
	    });
	    var submitAssignDialog = function(){
	        var submitDatas = $('#myTree').omTree('getChecked',true);;
	        var toSaveAuthID = '';
			for(var i=0;i<submitDatas.length;i++){
            	if(i != submitDatas.length - 1){
            		toSaveAuthID  += submitDatas[i].id+",";
                }else{
                	toSaveAuthID  += submitDatas[i].id;
                }
            }
           	submitData={
           			authIds:toSaveAuthID,
           			roleId:cRoleSeleted.id
                   };
           	$.post('role_authority_assign.shtml',submitData,function(result){
                   $('#grid').omGrid('reload');
                   if(result=='success'){
                      	$.omMessageTip.show({title: tip, content: "角色权限分配成功！", timeout: time,type:'success'});
                      }else{
                      	$.omMessageTip.show({title: tip, content: result, timeout: time,type:'error'});
                      }
                   $("#assign-form").omDialog("close"); 
               });
	    };
	    
	    function setData(rowData){
	    	 var authIds="";
		        $.ajax({
			 		   type:"post",
			           url:"selected_authority_for_role.json?id="+rowData.id,
			 		   async:false,
			 		   dataType:"text",
			 		   success:function(result){
			 			   authIds = result;
			 			}
			 		});
		        if(authIds!=null&&authIds!=""){
			        var authIdsArray = authIds.split(",");
			        for(var authId in authIdsArray){
			        	var target = $('#myTree').omTree("findNode", "id", authIdsArray[authId],"",true);
			        	$('#myTree').omTree('check',target);
			        	//$('#myTree').omTree('expand',target);
			        }
		        }
	    }
	    /*****************************************权限分配结束*************************************************/
	    $('#center-tab').omTabs({height:"33",border:false});
	});
</script>
</head>
<body>
<div id="center-tab">
<ul>
	 <li><a href="#tab1">角色信息列表:</a></li>
</ul>
</div>
<table >
     <tbody>
           <tr>
           <c:if test="${sessionScope.add_role != null }">
			<td><div id="create"/></td>
			</c:if>
			<c:if test="${sessionScope.update_role != null }">
			<td><div id="update"/></td>
			</c:if>
			<c:if test="${sessionScope.assgin_role_authority != null }">
			<td><div id="assign"/></td> 
			</c:if>
            <td style="text-align:center;">角色名称：</td>
            <td>
            	<input type="text" name="roleNameQuery" id="roleNameQuery" style="height: 20px; border: 1px solid #86A3C4;" />
            </td>
            <td><div id="query"/> </td>
          </tr>
      </tbody>
</table>
<table id="grid" ></table>
<div id="dialog-form">
        <form id="myForm">
        	<input name="id" id="id" style="display: none"/>
	        <table>
	            <tr>
	                <td style="text-align: right;"><span class="color_red">*</span>角色名称：</td>
	                <td><input name="roleName" id="roleName" style="height:20px;border:1px solid #86A3C4;" maxlength="32"/></td>
	                <td><span style="width: 150px;"></span></td>
	            </tr>
	            <tr>
					<td style="text-align: right;">描述：</td>
					<td><textarea id="description" name="description" rows="4"
							style="border: 1px solid #86A3C4;width: 142px;"></textarea></td>
				</tr>
	        </table>
	        
        </form>
    </div>
    
  <div id="assign-form">
        <form id="assignForm">
       		 <table >
       		 <tr valign="top">
				<td ><ul id="myTree"></ul></td>
			</tr>
       		 </table>
        </form>
    </div>
</body>
</html>