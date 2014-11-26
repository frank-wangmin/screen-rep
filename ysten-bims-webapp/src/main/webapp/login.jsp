<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<title>${systemTitle}</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<%@ include file="/include/taglibs.jsp"%>
<script type="text/javascript" src="${cxp}/js/jquery-1.6.3.min.js"></script>
<style>
* {
	margin: 0;
	padding: 0;
}

body {
	background: #d1f1fc url(images/admin/body_bg.jpg) no-repeat
		right 0;
	font: tahoma, Arial, Helvetica, sans-serif;
	text-align: center;
	overflow: auto;
}

img {
	border: 0
}

h1 {
	font-size: 200%;
	margin: 0
}

h2 {
	font-size: 130%;
	margin: 1em 0 .5em
}

h3 {
	font-size: 100%;
	margin: 1em 0 .5em
}

h4 {
	font-size: 90%;
	margin: 1em 0
}

p {
	margin: 0 0 1em 0
}

#login {
	position: absolute;
	top: 50%;
	left: 50%;
	width: 500px;
	height: 270px;
	margin: -135px 0 0 -250px;
	background: url(images/admin/login_area.jpg) no-repeat 0 0;
	text-align: left;
	z-index: 9001;
}

#shadow {
	position: absolute;
	top: 50%;
	left: 50%;
	width: 500px;
	height: 270px;
	margin: -132px 0 0 -247px;
	z-index: 9000;
	filter: progid:DXImageTransform.Microsoft.alpha(opacity=50) progid:DXImageTransform.Microsoft.Blur(pixelradius=2);
	-moz-opacity: 0.5;
	opacity: .50;
	background-color: #777;
}

#about {
	position: absolute;
	bottom: 10px;
	left: 10px;
	cursor: pointer;
	font: 11px "Trebuchet MS";
	color: #5ab3f4;
}

#login h1 {
	margin-bottom: 10px;
	padding: 0px 0 0 10px;
	font: 22px/50px "微软雅黑", tahoma;
	text-indent: 60px;
	text-align: left;
}

#login form {
	height: 200px;
	padding-left: 170px;
	background: url(images/admin/users.gif) no-repeat 20px 20px;
}

.error_message {
	color: #d00;
	font: 12px/2 "微软雅黑", tahoma;
}

#login form p {
	clear: both;
	margin: 5px 0;
}

#login form p img {
	vertical-align: middle;
}

#login form p label {
	width: 50px;
	display: inline-block;
	font: 12px/2 "微软雅黑", tahoma;
	color: #246bb3;
}

#login_ctr {
	text-indent: 58px;
}

#login form p .logintxt {
	font: 12px/16px tahoma;
	height: 20px;
	border: 1px solid #ccc;
}

#login form p input.logintxt {
	padding: 0 2px;
}

#login img {
	cursor: hand;
}

.w1 {
	width: 230px;
}

.w2 {
	width: 230px;
}

#copyright {
	width: 100%;
	position: absolute;
	left: 0;
	bottom: 0;
	border-top: 1px solid #87c8f7;
	font: 12px/2 "Trebuchet MS", arial;
	border-collapse: collapse;
}

#copyright td {
	padding-left: 10px;
	text-align: left;
}

#copyright th {
	width: 120px;
}

#copyright th a {
	font: normal 12px "Trebuchet MS", arial;
	padding-left: 20px;
	color: #000;
	text-decoration: none;
}
.STYLE1 {
	COLOR: #000000; FONT-SIZE: 13px;font-weight:bold
}
</style>

<script language=javascript>
 if (window != top){
	 top.location.href = location.href; 
 }
var bSubmit = false;
function fnSubmit(){
	loginIn();
	var userName = document.getElementById("userName").value;
	var pwd = document.getElementById("pwd").value;
	var divResult = document.getElementById("showResult").innerHTML;
	if(userName.length==0){
		$("#showResult").css("color", "red");
		$("#showResult").html("登录失败:用户名不能为空！");
		return false;
	}
	if(pwd.length==0){
		$("#showResult").css("color", "red");
		$("#showResult").html("登录失败:密码不能为空！");
		return false;
	}
	if(divResult!="温馨提示: 恭喜您!用户名密码正确！"){
		return false;
	}
}
function EnterSumbit(){
	if( event.keyCode == 13 ){
		loginIn();
	}
}

function loginIn() {
	if ($("#userName").val() != "" && $("#pwd").val() != "") {
		$.ajax({
			type : "post",
			url : "check_login.html",
			dataType : "json",
			data : "userName=" + encodeURIComponent($("#userName").val()) + "&pwd=" + encodeURIComponent($("#pwd").val()),
			success : function(result) {
				$("#showResult").html(result.message);
				$("#showResult").css("color", "green");
				if (result.status=="1") {
					$("#showResult").html("正在登录, 请稍等...");
					$("#img1").attr('disabled', true);
					$('#myform').submit();
				}
			}
		});
	}
}

function onLoad() {
	$.ajax({
	   	type:"post",
	   	url:"get_system_title",
	  	success:function(msg){
	  	$("#appTitle").html(msg);
	  }
	  });
	document.getElementById("userName").focus();
}
</script>
<body onload="onLoad();" onkeypress="EnterSumbit()"
	style="background:#d1f1fc url(images/admin/body_bg.jpg) no-repeat right 0;">
	<div id="login">
		<h1><div id='appTitle'>${systemTitle}</div></h1>
		<form id="myform" name="myform" method="post" action="login_in.html">
			<div id='loginbody'>
				<p class="error_message" id="error_message">&nbsp;</p>
				<p>
					<label for='userId'>用户</label> 
					<input type="text" name="userName" id="userName"   class="logintxt w1" value="admin" style="height:27px;"/>
				</p>
				<p>
					<label for='pass'>密码</label> <input type="password" name="pwd"	id="pwd" class="logintxt w1" value="admin"  style="height:27px;"/>
				</p>
				<p id="login_ctr">
					<img id="img1" src="images/admin/login_submit.png" onclick="return fnSubmit();">
				</p>
			</div>
			<div id="showResult" class="STYLE1" style="position:absolute; width:250px; height:24px; z-index:1; left:220px;; top:190px;"></div>
		</form>
	</div>
	
</body>
</html>