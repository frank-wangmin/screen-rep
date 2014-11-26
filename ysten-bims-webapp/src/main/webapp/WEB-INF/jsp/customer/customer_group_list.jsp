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
$(function(){
    $('#grid').omGrid({
    	dataSource : 'customer_group_list.json',
    	width : '100%',
        height : rFrameHeight,
        singleSelect : true,
        limit : limit,
        colModel : [ 
                     {header : '<b>集团编号</b>', name : 'groupId', align : 'center', width : 120}, 
                     {header : '<b>集团名称</b>', name : 'groupName', align : 'center', width : 150}, 
                     {header : '<b>联系人</b>', name : 'linkName', align : 'center', width : 100},
                     {header : '<b>联系电话</b>', name : 'linkTel', align : 'center', width : 100},
                     {header : '<b>状态</b>', name : 'state', align : 'center', width : 60, renderer:function(value){if(value=='NORMAL'){return '正常' }else{ return '异常'}}},
                     {header : '<b>创建时间</b>', name : 'createDate', align : 'center', width : 130},
                     {header : '<b>更新时间</b>', name : 'updateDate', align : 'center', width : 130},
                     {header : '<b>附加信息</b>', name : 'additionalInfo', align : 'center', width : 130}
		]
    });
    
    $('#query').omButtonbar({btns : [{label:"查询",icons : {left : opPath+'search.png'}}]});
    
    $('#query').bind('click', function() {
    		var groupId = $("#groupId").val();
    		var groupName = $("#groupName").val();
    		var linkName = $("#linkName").val();
    		var linkTel = $("#linkTel").val();
    		$('#grid').omGrid("setData", 'customer_group_list.json?groupId='+encodeURIComponent(groupId)+'&groupName='+encodeURIComponent(groupName)+'&linkName='+encodeURIComponent(linkName)+'&linkTel='+encodeURIComponent(linkTel));        
	});
});
	
</script>
</head>
<body>
	<table >
		<tr>
			<td>
				集团编号: <input type="text" name="groupId" id="groupId" style="width:150px;height: 20px;border:1px solid #86A3C4;"/>
				集团名称: <input type="text" name="groupName" id="groupName" style="width:150px;height: 20px;border:1px solid #86A3C4;"/>
				联系人: <input type="text" name="linkName" id="linkName" style="width:150px;height: 20px;border:1px solid #86A3C4;"/>
				联系电话: <input type="text" name="linkTel" id="linkTel" style="width:150px;height: 20px;border:1px solid #86A3C4;"/>
			</td>
			<td>
				<div id="query"/>
			</td>
		</tr>
	</table>
	
	<table id="grid" ></table>

</body>
</html>
