<%@ page language="java" import="java.util.*,org.apache.commons.lang.StringUtils,java.text.SimpleDateFormat" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>集团用户终端数据统计报表</title>
<%@ include file="/include/taglibs.jsp"%>
<%@ include file="/include/operamasks-ui-2.0.jsp"%>
<%@ include file="/include/css.jsp"%>
<%@ include file="/include/ysten.jsp"%>
<script type="text/javascript">
$(document).ready(function() {	
	var cityDataSource = null;
	$.get('city.json', function(data){
		cityDataSource = data;
	});
	 $('#grid').omGrid({
		    //dataSource : 'group_cust_device_list.json',
	    	width : '100%',
	        height : rFrameHeight,
	        singleSelect : true,
	        limit : limit,
	        colModel : [ 
						{header : '<b>客户集团名称</b>', name : 'groupName', align : 'center', width : 120},
						{header : '<b>客户集团编号</b>', name : 'groupId', align : 'center', width : 120},
						{header : '<b>客户名称</b>', name : 'custName', align : 'center', width : 120},
						{header : '<b>客户编号</b>', name : 'custId', align : 'center', width : 120},
						{header : '<b>客户所属地市</b>', name : 'region', align : 'center', width : 120,renderer:function(value){
							var text = "";
							$.each(cityDataSource, function(index, item){
								if(item.value == value.id){
									text =  item.text;
								}
							})
							return text;
						}},
						{header : '<b>用户外部编号</b>', name : 'userId', align : 'center', width : 120},
						{header : '<b>终端编号</b>', name : 'code', align : 'center', width : 120},
						{header : '<b>终端序列号</b>', name : 'sno', align : 'center', width : 120},
						{header : '<b>终端创建时间</b>', name : 'createDate', align : 'center', width : 120},
						{header : '<b>终端激活时间</b>', name : 'activateDate', align : 'center', width : 120},
						{header : '<b>终端设备状态</b>', name : 'state', align : 'center', width : 120}
			]
	    });
	   $('#query').omButtonbar({btns : [{label:"查询",icons : {left : opPath+'search.png'}}]});
		$('#query').bind('click', function(e) {
	    	var groupIp = filterStartEndSpaceTrim($('#groupIp').val());
	   	    $('#grid').omGrid("setData", 'group_cust_device_list.json?groupIp='+encodeURIComponent(groupIp));
	    });	
		$('#center-tab').omTabs({height:"33",border:false});
});
</script>
 
</head>
<body>
<div id="center-tab">
<ul>
	 <li><a href="#tab1">集团客户的用户终端数据统计报表:</a></li>
</ul>
</div>
       <table >  
       <tr>      	   
	       <td>集团IP：</td>
	       <td>
               <input id="groupIp" name="groupIp" style="width:110px;height: 20px;border:1px solid #86A3C4;"/>
           </td>
           <td></td>              
           <td colspan="13"><div id="query"/></td>
           <td></td>
       </tr>     		          
        </table>

<table id="grid"></table>

</body>
</html>