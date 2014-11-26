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
    $('#grid').omGrid({
    	dataSource : 'interface_log_list.json',
    	width : '100%',
        height : rFrameHeight,
        singleSelect : false,
        limit : limit,
        colModel : [ 
                     {header : '<b>接口名称</b>', name : 'interfaceName', align : 'center', width : 100,sort:'clientSide'},
                     {header : '<b>访问系统</b>', name : 'caller', align : 'center', width : 70},  
                     {header : '<b>访问时间</b>', name : 'callTime', align : 'center', width : 120,sort:'clientSide'},
                     {header : '<b>输入参数</b>', name : 'input', align : 'center',width:350},
                     {header : '<b>响应结果</b>', name : 'output',align : 'center',width:'autoExpand', renderer : function(row, index, data){
                         return row.replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/"/g, "&quot;");
                     }},
		],
		rowDetailsProvider:function(rowData,rowIndex){
            return '第'+rowIndex+'行, 主键ID='+rowData.id+"</br>接口名称="+rowData.interfaceName+"</br>访问系统="+rowData.caller+"</br>访问时间="+rowData.callTime
                        +'</br>输入参数='+rowData.input+'</br>响应结果='+rowData.output.replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/"/g, "&quot;");
        }
    });
    
    $('#query').omButtonbar({btns : [{label:"查询",icons : {left : opPath+'search.png'}}]});
    $('#export').omButtonbar({btns : [{label:"导出",icons : {left : opPath+'op-btn-icon.png'}}]});
    
    $('#query').bind('click', function(e) {
        var interfaceName = filterStartEndSpaceTrim($('#interfaceName').val());
        var caller = filterStartEndSpaceTrim($('#caller').val());
        var input = filterStartEndSpaceTrim($('#input').val());
        var output = filterStartEndSpaceTrim($('#output').val());
        $('#grid').omGrid("setData", 'interface_log_list?interfaceName='+encodeURI(interfaceName)+
                '&caller='+caller+"&input="+input+"&output="+output);
    });
    /**
    $('#export').bind('click', function(e) {
		var random = Math.random();
		var interfaceName = filterStartEndSpaceTrim($('#interfaceName').val());
        var caller = filterStartEndSpaceTrim($('#caller').val());
        var input = filterStartEndSpaceTrim($('#input').val());
        var output = filterStartEndSpaceTrim($('#output').val());
		location.href="interface_log_export.json?random="+random+'&interfaceName='+encodeURI(interfaceName)+
        '&caller='+caller+"&input="+input+"&output="+output;
		showDiv();
	});*/
    $('#center-tab').omTabs({height:"33",border:false});
    /**********************************************导出开始*************************************************/
	$('#export').bind('click', function(e) {
		var selections=$('#grid').omGrid('getSelections',true);
		$('#exportType').omCombo({dataSource : [
		                                           {text:'选中导出',value:'0'},
		                                           {text:'全部导出',value:'1'}
		                                       ],
		                                           value:'0',
		                                           width:180,
		                                           listMaxHeight:160
		 });
		 
    		var dialog2 = $("#dialog-form2").omDialog({
            	width: 300,
            	autoOpen : false,
            	modal : true,
            	resizable : false,
            	draggable : false,
            	buttons : {
                	"提交" : function(){
                    		if($('#exportType').omCombo('value') == 0){
                             	 if (selections.length == 0) {
                       	        	$.omMessageBox.alert({
                       	                type:'alert',
                       	                title:'温馨提示',
                       	                content:'选中导出至少选择一行记录！',
                       	            });
                       	            return false;
                       	        }
                            }
                    		if (selections.length > 0 && $('#exportType').omCombo('value') == 0){
                				var toDeleteRecordID = "";
                        		for(var i=0;i<selections.length;i++){
                            		if(i != selections.length - 1){
                            			toDeleteRecordID  += selections[i].id+",";
                                		}else{
                                			toDeleteRecordID  += selections[i].id;
                                		}
                            	}
                        		location.href="interface_log_export_by_ids.json?ids="+encodeURIComponent(toDeleteRecordID.toString());
                			}
                    		if($('#exportType').omCombo('value') == 1){
                    			var random = Math.random();
                    			var interfaceName = filterStartEndSpaceTrim($('#interfaceName').val());
                    	        var caller = filterStartEndSpaceTrim($('#caller').val());
                    	        var input = filterStartEndSpaceTrim($('#input').val());
                    	        var output = filterStartEndSpaceTrim($('#output').val());
                    	        location.href="interface_log_export_by_query.json?random="+random+'&interfaceName='+encodeURI(interfaceName)+
                    	        '&caller='+caller+"&input="+input+"&output="+output;
                    			}
                    		$("#dialog-form2").omDialog("close");  
                	},
                	"取消" : function() {
                    	$("#dialog-form2").omDialog("close");
                	}
            	},onClose:function(){
            		$('#myform2').validate().resetForm();
                	$("#dialog-form2").omDialog("close");
                	}
        	});
    	   	dialog2.omDialog("option", "title", "接口日志信息导出");
    	   	dialog2.omDialog("open");
    	});
	/**********************************************导出结束*************************************************/
});
</script>
</head>
<body>

<div id="center-tab">
<ul>
	 <li><a href="#tab1">接口日志信息列表:</a></li>
</ul>
</div>
	<table >
        <tbody>
            <tr>
            	<td><div id="export"/></td>
               	<td style="text-align:center;">接口名称：</td>
        		<td><input type="text" name="interfaceName" id="interfaceName" style="height: 20px;border:1px solid #86A3C4;"/></td>
        		<td style="text-align:center;">访问系统：</td>
        		<td><input type="text" name="caller" id="caller" style="height: 20px;border:1px solid #86A3C4;"/></td>
        		<td style="text-align:center;">输入参数：</td>
        		<td><input type="text" name="input" id="input"  style="height: 20px;border:1px solid #86A3C4;"/></td>
        		<td style="text-align:center;">响应结果：</td>
        		<td><input type="text" name="output" id="output" style="height: 20px;border:1px solid #86A3C4;"/></td>
                <td><div id="query"/> </td>
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
    <div id="dialog-form2" style="display: none;">
		<form id="myform2">
			<table>
				<tr>
					<td>导出方式：</td>
					<td>
					  <input name="exportType" id="exportType"/>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
