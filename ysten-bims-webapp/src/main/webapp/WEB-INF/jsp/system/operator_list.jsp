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

function checkLoginNameExists(){
     $.ajax({
     type:"post",
     url:"check_login_name_exists.shtml",
     async:false,
     dataType:"html",
     data:"loginName="+$("#loginName").val(),
     success:function(msg)
     {   
    	 if(msg=='已存在！'){
    		 $("#showResult").empty();
        $("#showResult").append(msg);
        $("#showResult").css("color","red");
    	 }
    	 if(msg=='可用！'){
    		 $("#showResult").empty();
    	 $("#showResult").append(msg);
    	 $("#showResult").css("color","green");
    	 }
     }
   });
}


function selectedData(operatorState,spId,role){
    $('#operatorState').omCombo({dataSource : 'operator_state.json',editable:false,width:141,value:operatorState});
    $('#spId').omCombo({dataSource : 'sp_list.json',editable:false,width:141,value:spId});
    $('#role').omCombo({dataSource : 'system_role_list.json',editable:false,width:144,multi : true  ,value:role});
}

$(document).ready(function() {
	    $('#grid').omGrid({
	    	dataSource : 'operator_list.json',
	    	width : '100%',
	        height : rFrameHeight,
	        singleSelect : true,
	        limit : limit,
	        colModel : [ 
	                     {header : '<b>用户名称</b>', name : 'displayName', align : 'center', width : 300},
	                     {header : '<b>登录名称</b>', name : 'loginName', align : 'center', width : 300},
	                     {header : '<b>邮箱</b>', name : 'email', align : 'center', width : 300},
						 {header : '<b>状态</b>', name : 'stateName', align : 'center', width : 140}
	                     
			]
	    });
	    $('#create').omButtonbar({btns : [{label:"新增",icons : {left : opPath+'add.png'}}]});
	    $('#update').omButtonbar({btns : [{label:"修改",icons : {left : opPath+'op-edit.png'}}]});
	    $('#query').omButtonbar({btns : [{label:"查询",icons : {left : opPath+'search.png'}}]});
	    $('#center-tab').omTabs({height:"33",border:false});
	    $('#query').bind('click', function(e) {
		    var operatorName = filterStartEndSpaceTrim($("#operatorName1").val());
		    var loginName = filterStartEndSpaceTrim($("#loginName1").val());
	        $('#grid').omGrid("setData", 'operator_list.json?operatorName='+encodeURIComponent(operatorName)+"&loginName="+loginName);
	    });

	    var dialog = $("#dialog-form").omDialog({
	        width: '50%',
	       	height:200,
	        autoOpen : false,
	        modal : true,
	        resizable : false,
	        draggable : false,
	        buttons : {
	            "提交" : function(){
	                submitDialog();
	                return false; //阻止form的默认提交动作
	            },
	            "取消" : function() {
	                $("#dialog-form").omDialog("close");
	            }
	        }
	    });
	    var showDialog = function(title,rowData){
	    	$("#showResult").text('');
	    	$('#myForm').resetForm();
	        rowData = rowData || {};
	        if(isAdd){
	        	selectedData('NORMAL','','');
	        }else{
	        $.ajax({
	           	type:"post",
	           	url:"operator_to_update.json?id="+rowData.id,
	           	dataType:"json",
	           	async:false,
	          	success:function(msg){
	          		selectedData(msg['state'],msg['spId'],'');
   					$("#userName").val(msg['displayName']);
   					$("#loginName").val(msg['loginName']);
   					$("#email").val(msg['email']);
	          }
	          });
	        //填充角色信息
	        $.ajax({
	           	type:"post",
	           	url:"get_operator_roleIds.json?id="+rowData.id,
	           	dataType:"text",
	           	async:false,
	          	success:function(msg){
	          		if(msg!=''&&msg!=null&&msg!='null'){
	          		 	$('#role').omCombo({value:msg});
	          		}
	          	}
	          });
	        }
	        $("input[name='id']",dialog).val(rowData.id);
	        dialog.omDialog("option", "title", title);
	        dialog.omDialog("open");
	    };
	    var submitDialog = function(){
	        var submitData;
	            if(isAdd){
	            	if (doSubmit('add')) {
	            	submitData={
	            			userName:$("input[name='userName']",dialog).val(),
	            			loginName:$("input[name='loginName']",dialog).val(),
	            			email:$("input[name='email']",dialog).val(),
	            			operatorState:$('#operatorState').omCombo('value'),
	            			pwd1:$("input[name='pwd1']",dialog).val(),
	            			pwd2:$("input[name='pwd2']",dialog).val(),
	            			roles:$("#role").omCombo('value')
	                    };
	            	$.post('operator_add.shtml',submitData,function(result){
	                    $('#grid').omGrid('reload');
	                    if(result=='success'){
                        	$.omMessageTip.show({title: tip, content: "新增系统操作员信息成功！", timeout: time,type:'success'});
                        }else{
                        	$.omMessageTip.show({title: tip, content: "新增系统操作员信息失败！", timeout: time,type:'error'});
                        }
	                    $("#dialog-form").omDialog("close");
	                });
	            	}
	            }else{
	            	if(doSubmit("update")){
	            	submitData={
	            			userName:$("input[name='userName']",dialog).val(),
	            			loginName:$("input[name='loginName']",dialog).val(),
	            			email:$("input[name='email']",dialog).val(),
	            			operatorState:$('#operatorState').omCombo('value'),
	            			pwd1:$("input[name='pwd1']",dialog).val(),
	            			pwd2:$("input[name='pwd2']",dialog).val(),
	                        id:$("input[name='id']",dialog).val(),
	                        roles:$("#role").omCombo('value')
	                    };
	            	$.post('operator_update.shtml',submitData,function(result){
	                    $('#grid').omGrid('reload');
	                    if(result=='success'){
                        	$.omMessageTip.show({title: tip, content: "修改系统操作员信息成功！", timeout: time,type:'success'});
                        }else{
                        	$.omMessageTip.show({title: tip, content: "修改系统操作员信息失败！", timeout: time,type:'error'});
                        }
	                    $("#dialog-form").omDialog("close");
	                });
	            }}
	    };
	    var isAdd = true; 
	    $('#create').bind('click', function() {
	    	$('#loginName').attr('disabled',false);
	        isAdd = true;
	        $.ajax({
	 		   type:"post",
	           url:"operator_cp.json?id=",
	 		   async:true,
	 		   dataType:"json",
	 		   success:function(result){
	 			   initCpSelector(result,[]);
	 			}
	 		});
	        $.ajax({
		 		   type:"post",
		            url:"operator_catg.json?id=",
		 		   async:true,
		 		   dataType:"json",
		 		   success:function(result){
		 			   initCatgSelector(result,[]);
		 			}
		 		});
	        showDialog('新增操作员信息');
	    });
	    $('#update').bind('click', function() {
	    	$('#loginName').attr('disabled',true);
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
	        $.ajax({
		 		   type:"post",
		           url:"operator_cp.json?id="+selections[0].id,
		 		   async:true,
		 		   dataType:"json",
		 		   success:function(result){
		 			   initCpSelector(result[0],result[1]);
		 			}
		 		});
	        $.ajax({
		 		   type:"post",
		           url:"operator_catg.json?id="+selections[0].id,
		 		   async:true,
		 		   dataType:"json",
		 		   success:function(result){
		 			   initCatgSelector(result[0],result[1]);
		 			}
		 		});
	        showDialog('修改操作员信息',selections[0]);
	    });

});


function doSubmit(par){
	var userName= $.trim($("#userName").val());
	var loginName= $.trim($("#loginName").val());
	var email= $.trim($("#email").val());
	var pwd1= $.trim($("#pwd1").val());
	var pwd2= $.trim($("#pwd2").val());
	var pwdExp = RegExp(/[(\ )(\~)(\!)(\@)(\#)(\$)(\%)(\^)(\&)(\*)(\()(\))(\-)(\_)(\+)(\=)(\[)(\])(\{)(\})(\|)(\\)(\;)(\:)(\')(\")(\,)(\.)(\/)(\<)(\>)(\?)(\)]+/);
	var re = /^([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/; 
	var objExp=new RegExp(re); 
	var showResult= $("#showResult").text();
	var spId = $.trim($("#spId").val());
	var n = $("#selFrom")[0];
	var v = '';
	var t = '';
	if(userName.length==0){
		$.omMessageBox.alert({type:'alert',title:tip,content:'用户名称不能为空！',});
		return false;
	}
	if(loginName.length==0){
		$.omMessageBox.alert({type:'alert',title:tip,content:'登录名称不能为空！',});
		return false;
	}
	if(email.length!=0){
		if(!objExp.test(email)==true){ 
			$.omMessageBox.alert({type:'alert',title:tip,content:'邮箱格式不正确！',});
			return false; 
		}
	}
	if(par=='add'){
		if(pwd1.length==0){
			$.omMessageBox.alert({type:'alert',title:tip,content:'登录密码不能为空！',});
			return false;
		}
		if(pwd2.length==0){
			$.omMessageBox.alert({type:'alert',title:tip,content:'确认密码不能为空！',});
			return false;
		}
		if(pwd1.length<6){
			$.omMessageBox.alert({type:'alert',title:tip,content:'登录密码长度应大于6位！',});
			return false;
		}
		if(pwd2.length<6){
			$.omMessageBox.alert({type:'alert',title:tip,content:'确认密码长度应大于6位！',});
			return false;
		}
		if(pwd1.length>32){
			$.omMessageBox.alert({type:'alert',title:tip,content:'登录密码长度应不大于32位！',});
			return false;
		}
		if(pwd2.length>32){
			$.omMessageBox.alert({type:'alert',title:tip,content:'确认密码长度应不大于32位！',});
			return false;
		}
		//if(pwdExp.test(pwd1)){
		//	$.omMessageBox.alert({type:'alert',title:tip,content:'登录密码包含特殊字符！',});
		//	return false;
		//}
		//if(pwdExp.test(pwd2)){
		//	$.omMessageBox.alert({type:'alert',title:tip,content:'确认密码包含特殊字符！',});
		//	return false;
		//}
		
	}
	if(pwd1.length!=0||pwd2.length!=0){
		if(pwd1.length<6){
			$.omMessageBox.alert({type:'alert',title:tip,content:'登录密码长度应大于6位！',});
			return false;
		}
		if(pwd2.length<6){
			$.omMessageBox.alert({type:'alert',title:tip,content:'确认密码长度应大于6位！',});
			return false;
		}
		if(pwd1.length>32){
			$.omMessageBox.alert({type:'alert',title:tip,content:'登录密码长度应不大于32位！',});
			return false;
		}
		if(pwd2.length>32){
			$.omMessageBox.alert({type:'alert',title:tip,content:'确认密码长度应不大于32位！',});
			return false;
		}
		//if(pwdExp.test(pwd1)){
		//	$.omMessageBox.alert({type:'alert',title:tip,content:'登录密码包含特殊字符！',});
		//	return false;
		//}
		//if(pwdExp.test(pwd2)){
		//	$.omMessageBox.alert({type:'alert',title:tip,content:'确认密码包含特殊字符！',});
		//	return false;
		//}
	}
	if(pwd1!=pwd2){
		$.omMessageBox.alert({type:'alert',title:tip,content:'登录密码必须与确认密码一致！',});
		return false;
	}
	if(showResult=="已存在！")
	{
		$.omMessageBox.alert({type:'alert',title:tip,content:'登录名已经存在！',});
		return false;
	}
	else{
		return true;
	}
}
</script>
</head>
<body>
<div id="center-tab">
<ul>
	 <li><a href="#tab1">操作员信息列表:</a></li>
</ul>
</div>
 <table >
     <tbody>
           <tr>
           <c:if test="${sessionScope.add_operator != null }">
        	<td style="text-align: left;">
	            <div id="create"/>
	        </td>
	        </c:if>
	        <c:if test="${sessionScope.update_operator != null }">
	        <td style="text-align: left;">
        		<div id="update"/>
        	</td>
        	 </c:if>
            <td style="text-align:center;">用户名称：</td>
            <td><input type="text" name="operatorName1" id="operatorName1" style="height: 20px;border:1px solid #86A3C4;"/></td>
           	<td style="text-align:center;">登录名称：</td>
            <td><input name="loginName1" id="loginName1" style="height: 20px;border:1px solid #86A3C4;"/></td>
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
	                <td style="text-align: right;">用户名称：</td>
	                <td><input name="userName" id="userName" style="height:20px;border:1px solid #86A3C4;" maxlength="16"/></td>
	                <td style="text-align: right;">登录名称：</td>
	                <td><input name="loginName" id="loginName" style="height:20px;border:1px solid #86A3C4;" onblur="checkLoginNameExists();" maxlength="16"/></td>
	                <td><div id="showResult"></div></td>
	            </tr>
	            <tr>
	                <td style="text-align: right;">用户邮箱：</td>
	                <td><input name="email" id="email" style="height:20px;border:1px solid #86A3C4;" maxlength="30"/></td>
	                 <td style="text-align: right;">状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态：</td>
	                <td><input id="operatorState" name="operatorState" /></td>
	                <td></td>
	            </tr>
	            <tr>
	                <td style="text-align: right;">登录密码：</td>
	                <td><input id="pwd1" name="pwd1" type="password" maxlength="32" style="height:20px;border:1px solid #86A3C4;"/></td>
	               	<td style="text-align: right;">确认密码：</td>
	                <td><input id="pwd2" name="pwd2" type="password" maxlength="32" style="height:20px;border:1px solid #86A3C4;"/></td>
	                <td><span class="errorImg"></span><span class="errorMsg"></span></td>
	            </tr>
	            <tr>
	                <td style="text-align: right;">角&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;色：</td>
	                <td><input id="role" name="role" /></td>
	                <td></td>
	            </tr>
	        </table>
	        
        </form>
    </div>
</body>
</html>