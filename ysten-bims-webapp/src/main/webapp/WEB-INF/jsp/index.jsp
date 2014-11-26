<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${systemTitle}</title>
<%@ include file="/include/taglibs.jsp"%>
<%@ include file="/include/jquery.jsp"%>
<%@ include file="/include/css.jsp"%>
<%@ include file="/include/operamasks-ui-2.0.jsp"%>
<link href="${cxp}/css/ysten.css" rel="stylesheet" type="text/css" />
<style>
html, body{ width: 100%; height: 100%; padding: 0; margin: 0;overflow: hidden;}
#north-panel{
	background: url("${cxp}/images/2.png") repeat-x scroll 0 0;
	text-align: left;
}
#north-panel h2{
	font-size: 20px;
	font-weight: bold;
	margin: 8px;
	font-family: 微软雅黑;
}
#north-panel h2 img {
	vertical-align: middle;
}

#search-panel span.om-combo,#search-panel span.om-calendar{
	vertical-align: middle;
}

#grid .op-menu{
	position: absolute;
	display: none;
	background-color: #E6ECF5;
	border: 1px solid #99A8BC;
	padding: 0;
	width: 75px;
}
#grid .op-menu div{
	cursor: pointer;
	padding-left: 20px;
}
#grid .op-menu div:hover{
	background-color: #4E76AD;
	color: #FFFFFF;
}
.om-borderlayout-region-west .om-panel-body{
	padding: 0;
}
.nav-panel {
	padding: 0;
}
.nav-panel div.nav-item{
	line-height: 25px;
	font-size: 13px;
	font-weight: bold;
	padding-left: 40px;
	cursor: pointer;
	list-style-type: none;
}

.nav-panel div.nav-item:hover{
	border: 1px solid #99A8BC;
	background-color: #C4D6EC;
	padding-left: 39px;
	line-height: 23px;
}
.nav-panel div.user:hover{
	background-position: 19px 3px;
}
</style>
<script type="text/javascript">
	setInterval("document.getElementById('webjx').innerHTML=new Date().toLocaleString()+' 星期'+'日一二三四五六'.charAt(new Date().getDay());",1000);
     $(document).ready(function() {
         var element = $('body').omBorderLayout({
        	   panels:[{
        	        id:"north-panel",
        	        region:"north",
        	        header : false,
        	        height : 44
        	    },{
        	        id:"south-panel",
        	        region:"south",
        	        resizable:true,
        	        collapsible:true,
        	        height:20,
        	        header:false
        	    },{
        	        id:"center-panel",
        	     	header:false,
        	        region:"center"
        	    },{
        	        id:"west-panel",
        	        resizable:true,
        	        collapsible:true,
        	        region:"west",
        	        expandToBottom : true,
        	        header:false,
        	        width:175
        	    }],
        	    hideCollapsBtn : true,
        	    spacing : 4
         });
         // 默认关闭下面的面板
         element.omBorderLayout("collapseRegion","south");
         // 导航面板里由5个单独的panel构成
         ${sessionScope.listSysMenu}
         // 初始化中间的tab页签
         $('#center-tab').omTabs({height:"fit",border:false});

         // 导航面板使用自定义滚动条
         $("#west-panel").omScrollbar();
         // 点击页面其他地方的时候隐藏表格里面的菜单
         $("body").click(function(){
			$("#grid").find("div.op-menu:visible").hide();
         });
         
         var dialog = $("#dialog-form").omDialog({
             width: 410,
             height:200,
             autoOpen : false,
             modal : true,
             resizable : false,
             draggable : false,
             buttons : {
                 "提交" : function(){
                 	if($("#showResult").text()!=""){
                         return false;
                     }
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
             dialog.omDialog("option", "title", title);
             dialog.omDialog("open");
         };
         var submitDialog = function(){
             var submitData;
             if (validator.form()) {
                 	submitData={
                 			oldPassword:$("input[name='oldPassword']",dialog).val(),
                 			newPassword1:$("input[name='newPassword1']",dialog).val()
                         };
                 	$.post('update_password.shtml',submitData,function(result){
                     	$("#dialog-form").omDialog("close");
                         if(result=='success'){
                         	$.omMessageTip.show({title: "温馨提示：", content: "修改个人密码成功！", timeout: 2000,type:'success'});
                         }
                         if(result=='oldPassError'){
                         	$.omMessageTip.show({title: "温馨提示：", content: "原密码输入错误！", timeout: 2000,type:'error'});
                         }
                         if(result=='failed'){
                         	$.omMessageTip.show({title: "温馨提示：", content: "修改个人密码失败！", timeout: 2000,type:'error'});
                         }
                          
                     });
             }
         };
         $('#updatePass').bind('click', function() {
             showDialog('修改个人密码');
             $("#showResult").text("");
         });

         
         var validator = $('#myForm').validate({
             rules : {
             	oldPassword : {required: true},
             	newPassword1 : {required: true},
             	newPassword2 : {required: true},
             }, 
             messages : {
             	oldPassword : {required : "   原密码不能为空！"},
             	newPassword1 : {required : "   新密码不能为空！"},
             	newPassword2 : {required : "   确认密码不能为空！"}
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
         $("#accordionId").omAccordion({width:172,height:530});
     });
     
     function onLoad(url){
    	 document.getElementById('rFrame').src = url;
     }
     
</script>
	<div style="position: absolute; z-index: 3;height:10px; left: 10px; top: -10px; color: black;"><img src="${cxp}/images/logo_64.png"/></div>
  	<div id="north-panel"><h2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${systemTitle}</h2></div>
  	<div id="center-panel" style="padding: 1px 1px 1px 1px;">
	<iframe id="rFrame" name="rFrame" width="100%" height="100%" frameborder="No" border="0" marginwidth="0" marginheight="0" scrolling="no" src=""></iframe>
  	</div>
   	<div id="west-panel" class="om-accordion" style="position: relative;">
   		<div id="accordionId"> 
        <ul> 
        	<c:forEach items="${sessionScope.listSysMenuHeader}" var="bean" varStatus="status">
                <li><a href="#accordion-${status.index}">${bean.name}</a>
                </li>
            </c:forEach>
        </ul> 
        <c:forEach items="${sessionScope.listSysMenuHeader}" var="bean" varStatus="status">
	        <div id="accordion-${status.index}">
	            <ul id="navtree${status.index}"></ul>
	        </div>
        </c:forEach>
    </div>
   		
   	</div>
   	<div id="south-panel" style='text-align:center;'>
   	
   	Copyright © 2008-2014 YSTen All Rights Reserved&nbsp;&nbsp;&nbsp;易视腾科技有限公司 版权所有
   	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   	当前用户登录IP：${sessionScope.ip}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;系统时间：<span id="webjx"></span>
   	</div>
   	
   	<span id="webjx1" style="position: absolute; z-index: 1; height: 30px; right: 15px; top: 5px; color: black;">
   	<img src="${cxp}/images/user.png" style="vertical-align: middle;border:0;"/>
    	&nbsp;<u>${sessionScope.operator.displayName}</u>&nbsp;
        <a href="#" style="color: #23466D; line-height: 30px;" id="updatePass"><img src="${cxp}/images/nav_resetPassword.gif" style="vertical-align: middle;border:0;"/>修改密码</a>&nbsp;&nbsp;
        <a href="login_out.shtml" style="color: #23466D; line-height: 30px;"><img src="${cxp}/images/nav_back.gif" style="vertical-align: middle;border:0;"/>退出</a>
    </span>
    
    
    <div id="dialog-form">
        <form id="myForm">
        	<input name="id" id="id" style="display: none"/>
	        <table>
	            <tr>
	                <td style="text-align: right;"><span class="color_red">*</span>原密码：</td>
	                <td><input name="oldPassword" id="oldPassword" type="password" style="height:20px;border:1px solid #86A3C4;"/></td>
	                <td><span style="width: 150px;"></span></td>
	            </tr>
	            <tr>
	                <td style="text-align: right;"><span class="color_red">*</span>新密码：</td>
	                <td><input name="newPassword1" id="newPassword1" type="password" style="height:20px;border:1px solid #86A3C4;" onblur="checkPassword();"/></td>
	                <td><span style="width: 150px;"></span></td>
	            </tr>
	            <tr>
	                <td style="text-align: right;"><span class="color_red">*</span>确认密码：</td>
	                <td><input name="newPassword2" id="newPassword2" type="password" style="height:20px;border:1px solid #86A3C4;" onblur="checkPassword();"/></td>
	                <td><span style="width: 150px;"></span><div id="showResult"></div></td>
	            </tr>
	            <tr>
	                <td colspan="3" height="20px"><span class="color_red">*密码长度不能小于6位</span></td>
	            </tr>
	        </table>
        </form>
    </div>
</html>