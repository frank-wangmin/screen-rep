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
    	dataSource : 'sys_version_list.json',
    	width : '100%',
        height : rFrameHeight,
        singleSelect : true,
        limit : limit,
        colModel : [ 
                     {header : '<b>版本号</b>', name : 'versionId', align : 'center', width : 190},
                     {header : '<b>版本名称</b>', name : 'name', align : 'center', width : 200},
                     {header : '<b>创建时间</b>', name : 'createDate',align : 'center',width:150},
                     {header : '<b>版本内容</b>', name : 'content',align : 'center',width: 'autoExpand'}
                     
		]
    });

    $('#query').omButtonbar({btns : [{label:"查询",icons : {left : opPath+'search.png'}}]});
    $('#query').bind('click', function(e) {
        var versionId = $('#versionId').val();
        var versionName = $('#versionName').val();
        $('#grid').omGrid("setData", 'sys_version_list.json?versionId='+encodeURIComponent(versionId)+'&versionName='+versionName);
    });
    $('#center-tab').omTabs({height:"33",border:false});
});

</script>
</head>
<body>
<div id="center-tab">
<ul>
	 <li><a href="#tab1">系统版本信息列表:</a></li>
</ul>
</div>
<table >
    <tr>
          <td style="text-align:center;">&nbsp;版本号：</td>
          <td>
          		<input type="text" name="versionId" id="versionId" class="query-title-input"/></td>
          <td style="text-align:center;">&nbsp;版本名称：</td>
          <td>
          		<input name="versionName" id="versionName" class="query-title-input"/></td>
           <td><div id="query"/></td>
  </tr>
</table>
<table id="grid" ></table>
</body>
</html>
