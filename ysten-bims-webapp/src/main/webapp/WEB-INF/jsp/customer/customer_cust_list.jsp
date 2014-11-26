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
	var cityDataSource = null;
	$.get('city.json', function(data){
		cityDataSource = data;
	});
    $('#grid').omGrid({
    	dataSource : 'customer_cust_list.json',
    	width : '100%',
        height : rFrameHeight,
        singleSelect : true,
        limit : limit,
        colModel : [ 
                     {header : '<b>客户编号</b>', name : 'custId', align : 'center', width : 80}, 
                     {header : '<b>客户名称</b>', name : 'custName', align : 'center', width : 180}, 
                     {header : '<b>客户类型</b>', name : 'custType', align : 'center', width : 80},
                     {header : '<b>所属地市</b>', name : 'region', align : 'center', width : 60,renderer:function(value){
						var text = "";
						$.each(cityDataSource, function(index, item){
							if(item.value == value.id){
								text =  item.text;
							}
						})
						return text;
					}},
                     {header : '<b>联系人</b>', name : 'linkName', align : 'center', width : 60},
                     {header : '<b>联系电话</b>', name : 'linkTel', align : 'center', width : 80},
                     {header : '<b>状态</b>', name : 'state', align : 'center', width : 60},
                     {header : '<b>客户管理员</b>', name : 'custManager', align : 'center', width : 60},
                     {header : '<b>客户发展人</b>', name : 'custDeveloper', align : 'center', width : 60},
                     {header : '<b>创建时间</b>', name : 'createDate', align : 'center', width : 130},
                     {header : '<b>更新时间</b>', name : 'updateDate', align : 'center', width : 130},
                     {header : '<b>集团编号</b>', name : 'groupId', align : 'center', width : 80},
                     {header : '<b>集团IP</b>', name : 'groupIP', align : 'center', width : 200},
                     {header : '<b>备注</b>', name : '', align : 'center', width : 100}
		]
    });
    
    $('#query').omButtonbar({btns : [{label:"查询",icons : {left : opPath+'search.png'}}]});
    
    $('#query').bind('click', function() {
    		var custId = $("#custId").val();
    		var custName = $("#custName").val();
    		var linkTel = $("#linkTel").val();
    		var groupIp = $("#groupIp").val();
    		var groupId = $("#groupId").val();
    		$('#grid').omGrid("setData", 'customer_cust_list.json?custId='+encodeURIComponent(custId)
    								+'&custName='+encodeURIComponent(custName)
    								+'&linkTel='+encodeURIComponent(linkTel)
    								+'&groupIp='+encodeURIComponent(groupIp)
    								+'&groupId='+encodeURIComponent(groupId));        
	});
});
	
</script>
</head>
<body>
	<table >
		<tr>
			<td>
				客户编号: <input type="text" name="custId" id="custId" style="width:150px;height: 20px;border:1px solid #86A3C4;"/>
				客户名称: <input type="text" name="custName" id="custName" style="width:150px;height: 20px;border:1px solid #86A3C4;"/>
				联系电话: <input type="text" name="linkTel" id="linkTel" style="width:150px;height: 20px;border:1px solid #86A3C4;"/>
				集团IP: <input type="text" name="groupIp" id="groupIp" style="width:150px;height: 20px;border:1px solid #86A3C4;"/>
				集团编号: <input type="text" name="groupId" id="groupId" style="width:150px;height: 20px;border:1px solid #86A3C4;"/>
			</td>
			<td>
				<div id="query"/>
			</td>
		</tr>
	</table>
	
	<table id="grid" ></table>

</body>
</html>
