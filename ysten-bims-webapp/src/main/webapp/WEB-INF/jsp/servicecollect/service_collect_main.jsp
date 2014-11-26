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
<link type="text/css" rel="stylesheet"
	href="http://layout.jquery-dev.net/lib/css/layout-default-latest.css" />
<link type="text/css" rel="stylesheet"
	href="${cxp}/css/resize-div.css" />
<script type="text/javascript"
	src="http://layout.jquery-dev.net/lib/js/jquery-latest.js"></script>
<script type="text/javascript"
	src="http://layout.jquery-dev.net/lib/js/jquery-ui-latest.js"></script>
<script type="text/javascript"
	src="http://layout.jquery-dev.net/lib/js/jquery.layout-latest.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('#container').layout();
	});
</script>

<script type="text/javascript">
	var serviceCollectIdGlobal = "";

	$(function(){
		$("#service-collect-list").attr("height",(rFrameHeight+60)+'px');
		$("#service-info-list").attr("height",(rFrameHeight+60)+'px');
	});
</script>
</head>
<body>
  <div id="container">
		<div class="pane ui-layout-center" style="overflow: hidden">
      	<iframe id="service-collect-list" name="serviceCollectList" src="to_service_collect_list.shtml" width="100%"  border=0 frameBorder='no' scrolling="no"></iframe>
     </div>
		<div class="pane ui-layout-east" style="overflow: hidden">
      	<iframe id="service-info-list" name="serviceInfoList" src="to_service_info_list.shtml" width="100%"  border=0 frameBorder='no' scrolling="no"></iframe>
       </div>
	</div>	
</body>
</html>
