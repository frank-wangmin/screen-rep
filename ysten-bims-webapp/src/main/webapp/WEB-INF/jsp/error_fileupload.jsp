<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<%@ include file="/include/taglibs.jsp"%>
<%@ include file="/include/jquery.jsp"%>
<%@ include file="/include/css.jsp"%>
<%@ include file="/include/operamasks-ui-2.0.jsp"%>
<style type="text/css">
	.file-box{ position:relative;width:340px}
	.txt{ height:22px; border:1px solid #cdcdcd; width:180px;}
	.btn{ background-color:#FFF; border:1px solid #CDCDCD;height:24px; width:70px;}
	.file{ position:absolute; top:0; right:80px; height:24px; filter:alpha(opacity:0);opacity: 0;width:260px } 
</style>
<link href="${cxp}/js/dtree.css" rel="stylesheet" type="text/css" />
<link href="${cxp}/css/ysten.css" rel="stylesheet" type="text/css" />
<link href="${cxp}/css/nav.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${cxp}/js/dtree2.js"></script>
</head>
<body>
<center>
<div>
	<span class="color_red">${error}</span>
	<c:forEach items="${errors}" var="error">
		<c:out value="${error}"></c:out><br/>
	</c:forEach>
</div>
</center>
	
</body>
</html>