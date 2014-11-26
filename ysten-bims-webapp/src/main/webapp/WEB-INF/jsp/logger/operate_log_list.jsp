<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<%@ include file="/include/taglibs.jsp"%>
<%@ include file="/include/operamasks-ui-2.0.jsp"%>
<%@ include file="/include/css.jsp"%>
<%@ include file="/include/ysten.jsp"%>
<link href="${cxp}/css/ysten.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
$(document).ready(function() {
	//列表
    $('#grid').omGrid({
    	dataSource : 'operator_logger_list.json',
    	width : '100%',
        height : rFrameHeight,
        singleSelect : true,
        limit : limit,
        colModel : [ 
                     {header : '<b>模块名称</b>', name : 'moduleName', align : 'center', width : 120},
                     {header : '<b>操作人员</b>', name : 'operator', align : 'center', width : 70},  
                     {header : '<b>操作人员IP</b>', name : 'operationIp', align : 'center', width : 90},
                     {header : '<b>操作对象ID</b>', name : 'primaryKeyValue', align : 'center', width : 100},
                     {header : '<b>操作时间</b>', name : 'operationTime',align : 'center',width: 120},
                     {header : '<b>操作类型</b>', name : 'operationType',align : 'center',width: 80},
                     {header : '<b>描述</b>', name : 'description', align : 'left', width :420},
		],
    	rowDetailsProvider:function(rowData,rowIndex){
        	return '第'+rowIndex+'行'
        		+', 模块名称='+rowData.moduleName
        		+', 操作人员='+rowData.operator
        		+', 操作人员IP='+rowData.operationIp
        		+', 操作对象ID='+rowData.primaryKeyValue
        		+', 操作时间='+rowData.operationTime
        		+', 操作类型='+rowData.operationType
        		+'</br>描述='+rowData.description;
    	}
    });
    $('#query').omButtonbar({btns : [{label:"查询",icons : {left : opPath+'search.png'}}]});

    $("#beginDate").omCalendar({showTime : true});
    $("#endDate").omCalendar({showTime : true});

    $('#modelName').omCombo({dataSource : 'model_name_list.json',editable:true,width:135, autoFilter : true, filterStrategy:'anywhere'});
    $('#operator').omCombo({dataSource : 'logger_operator_list.json',editable:false,width:135});
    
    $('#query').bind('click', function(e) {
        var modelName = $('#modelName').val();
        var operator = $('#operator').val();
        var operatorId = filterStartEndSpaceTrim($('#operatorId').val());
        var beginDate = $('#beginDate').val();
        var endDate = $('#endDate').val();
        $('#grid').omGrid("setData", 'operator_logger_list.json?modelName='+encodeURI(modelName)+
                '&operator='+operator+"&operatorId="+operatorId+"&beginDate="+beginDate+"&endDate="+endDate);
    });
    $('#center-tab').omTabs({height:"33",border:false});
});

</script>
</head>
<body>
<div id="center-tab">
<ul>
	 <li><a href="#tab1">系统操作日志信息列表:</a></li>
</ul>
</div>
	<table >
        <tbody>
            <tr>
               	<td style="text-align:center;width: 6%;">模块名称：</td>
        		<td style="width: 7%;"><input type="text" name="modelName" id="modelName"/></td>
        		<td style="text-align:center;width: 7%;">操作人员：</td>
        		<td style="width: 7%;"><input type="text" name="operator" id="operator" style="height: 20px;"/></td>
        		<td style="text-align:center;width: 7%;">操作对象ID：</td>
        		<td style="width: 7%;"><input type="text" name="operatorId" id="operatorId"  style="height: 20px;"/></td>
        		<td style="text-align:center;width: 6%;">开始时间：</td>
        		<td style="width: 7%;"><input type="text" name="beginDate" id="beginDate" /></td>
        		<td style="text-align:center;width: 6%;">结束时间：</td>
        		<td style="width: 7%;"><input type="text" name="endDate" id="endDate" /></td>
                <td><div id="query"/> </td>
             </tr>
           </tbody>
        </table>
	<table id="grid" ></table>
	<div id="dialog-form"><form id="myForm"><table></table></form></div>
</body>
</html>