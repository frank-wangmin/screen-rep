<%--
  Created by IntelliJ IDEA.
  User: frank
  Date: 14-7-8
  Time: 下午3:23
  To change this template use File | Settings | File Templates.
--%>
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

            var render_status = function(row){
                if(row == "ON"){
                    return "开机";
                }
                if(row == "OFF"){
                    return "关机";
                }
                return "";
            };
            $('#grid').omGrid({
                dataSource : 'device_operate_log_list.json',
                width : '100%',
                height : rFrameHeight,
                singleSelect : true,
                limit : limit,
                colModel : [
                    {header : '<b>终端编号</b>', name : 'deviceCode', align : 'center', width : 150,sort:'clientSide'},
                    {header : '<b>IP</b>', name : 'ip', align : 'center', width : 100,sort:'clientSide'},
                    {header : '<b>状态</b>', name : 'status', align : 'center', width : 80,renderer : render_status},
                    {header : '<b>创建日期</b>', name : 'createDate', align : 'center',width:160,renderer: YST_APP.showDate,sort:'clientSide'},
                    {header : '<b>响应结果</b>', name : 'result',align : 'center',width:'autoExpand', renderer : function(row){
                        return row.replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/"/g, "&quot;");
                    }},
                ],
                rowDetailsProvider:function(rowData,rowIndex){
                    return '第'+rowIndex+'行, 主键ID='+rowData.id+"</br>终端编号="+rowData.deviceCode+"</br>IP="+rowData.ip+
                            "</br>状态="+render_status(rowData.status) + "</br>创建日期="+YST_APP.showDate(rowData.createDate)
                            +'</br>响应结果='+rowData.result.replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/"/g, "&quot;");
                }
            });

            $('#query').omButtonbar({btns : [{label:"查询",icons : {left : opPath+'search.png'}}]});
            $('#export').omButtonbar({btns : [{label:"导出",icons : {left : opPath+'op-btn-icon.png'}}]});
            $('#center-tab').omTabs({height:"33",border:false});
            $('#query').bind('click', function() {
                $('#query_0').prop("disabled",true);
                var deviceCode = filterStartEndSpaceTrim($('#deviceCode').val()),ip = filterStartEndSpaceTrim($('#ip').val()),status = $('#status').val(),result = filterStartEndSpaceTrim($("#result").val());
                $('#grid').omGrid("setData", 'device_operate_log_list.json?deviceCode='+encodeURI(deviceCode)+
                        '&ip='+encodeURI(ip)+"&status="+status+"&result="+encodeURI(result));
            });
            $("#status").omCombo({dataSource: YST_APP.ON_OFF, value: "", editable: false, width: 120, listMaxHeight: 120});
            $('#export').bind('click', function() {
                var deviceCode = $('#deviceCode').val(),ip = $('#ip').val(),status = $('#status').val(),result = $("#result").val(),random = Math.random();
                location.href="device_operate_log_export.json?random="+random + "&deviceCode=" + encodeURI(deviceCode) +
                        '&ip='+encodeURI(ip)+"&status="+status+"&result="+encodeURI(result);
            });
        });

    </script>
</head>
<body>
<div id="center-tab">
    <ul>
        <li><a href="#tab1">终端操作日志信息列表:</a></li>
    </ul>
</div>
<table >
    <tbody>
    <tr>
        <td style="text-align:center;">终端编号：</td>
        <td><input type="text" name="deviceCode" id="deviceCode" class="query-title-input"/></td>
        <td style="text-align:center;">IP：</td>
        <td><input type="text" name="ip" id="ip" class="query-title-input"/></td>
        <td style="text-align:center;">状态：</td>
        <td><input type="text" name="status" id="status"  class="query-title-input"/></td>
        <td style="text-align:center;">响应结果：</td>
        <td><input type="text" name="result" id="result" class="query-title-input"/></td>
        <td><div id="query"/> </td>
        <td><div id="export"/></td>
    </tr>
    </tbody>
</table>
<table id="grid" ></table>
<div id="dialog-form">
    <form id="myForm">
        <table>
        </table>
    </form>
</div>
</body>
</html>

