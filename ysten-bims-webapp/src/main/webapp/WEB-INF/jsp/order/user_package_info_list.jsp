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
    	dataSource : 'user_package_info_list.json',
    	width : '100%',
        height : rFrameHeight,
        singleSelect : true,
        limit :limit,
        colModel : [ 
				{header : '<b>用户编号</b>', name : 'customerCode', align : 'center', width : 150}, 
				{header : '<b>用户外部编号</b>', name : 'userId', align : 'center', width : 100}, 
				{header : '<b>产品包名称</b>', name : 'productName', align : 'center', width : 100}, 
				{header : '<b>产品包类型</b>', name : 'productType',align : 'center',width: 70},
				{header : '<b>产品内容ID</b>', name : 'outterCode',align : 'center',width: 70},
				{header : '<b>产品内容名称</b>', name : 'contentName',align : 'center',width: 120},
				{header : '<b>创建时间</b>', name : 'createDate',align : 'center',width: 120},
				{header : '<b>服务起始时间</b>', name : 'startDate',align : 'center',width: 120},
				{header : '<b>服务结束时间</b>', name : 'endDate',align : 'center',width: 120},
				{header : '<b>数量</b>', name : 'buyNum',align : 'center',width: 40}
		]
    });
    
    $('#query').omButtonbar({btns : [{label:"查询",icons : {left : opPath+'search.png'}}]});
	$('#export').omButtonbar({btns : [{label:"导出",icons : {left : opPath+'op-btn-icon.png'}}]});
    $('#query').bind('click', function(e) {
        var customerCode = filterStartEndSpaceTrim($('#customerCode').val());
        var userId = filterStartEndSpaceTrim($('#userId').val());
        $('#grid').omGrid("setData", 'user_package_info_list.json?customerCode='+encodeURIComponent(customerCode)+'&userId='+(userId));
    });
	
	/********************************************导出开始***************************************************/
    $('#export').bind('click', function(e) {
   		$("#startDateExport").omCalendar({dateFormat : 'yy-mm-dd',showTime : false});
	 	$("#endDateExport").omCalendar({dateFormat : 'yy-mm-dd',showTime : false});
		var exportDialog = $("#export-dialog-form").omDialog({
	        width: 400,
	        autoOpen : false,
	        modal : true,
	        resizable : false,
	        draggable : false,
	        buttons : {
	            "提交" : function(){
	            	var random = Math.random();
	            	var customerCode= $('#customerCodeExport').val();
	            	var productName= $('#productNameExport').val();
	                var contentName = $('#contentNameExport').val();
	                var startDate = $("#startDateExport").val();
	                var endDate = $("#endDateExport").val();
	                if(startDate==null || startDate=="" || endDate == null || endDate == "" ){
	                	$.omMessageBox.alert({
                            type:'alert',
                            title:'温馨提示',
                            content:'请填写创建时间！',
                        });
	                	 return false;
		                }
	                if(startDate > endDate){
	                	$.omMessageBox.alert({
                            type:'alert',
                            title:'温馨提示',
                            content:'结束时间要大于等于起始时间！',
                        });
	                	 return false;
		                }
	                if(startDate!=null&&startDate!=""){
	                	if(!strDateTime(startDate)){
	                		$.omMessageBox.alert({
	                            type:'alert',
	                            title:'温馨提示',
	                            content:'开始时间格式错误',
	                        });
	                	}
	                }
	                if(endDate!=null&&endDate!=""){
	                	if(!strDateTime(endDate)){
	                		$.omMessageBox.alert({
	                            type:'alert',
	                            title:'温馨提示',
	                            content:'结束时间格式错误',
	                        });
	                	}	                	
	                }
	        		location.href="user_package_info_export.json?random="+random+"&customerCode="+customerCode+"&productName="+encodeURIComponent(productName)
	        				+"&contentName="+encodeURIComponent(contentName)
	        				+"&startDate="+encodeURIComponent(startDate)
	        				+"&endDate="+encodeURIComponent(endDate);
	        		$("#export-dialog-form").omDialog("close");
	        		//showDiv();
	            },
	            "取消" : function() {
	                $("#export-dialog-form").omDialog("close");
	            }
	        },onClose:function(){resetData();}
    	});
		exportDialog.omDialog("option", "title", "产品包导出");
		exportDialog.omDialog("open");
	});
	//校验日期格式，如2003-12-05
    function strDateTime(str) 
    { 
	    var r = str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/); 
	    if(r==null)return false; 
	    var d= new Date(r[1], r[3]-1, r[4]); 
	    return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]); 
    } 
	function resetData(){
		$('#productNameExport').val('');
		$('#contentNameExport').val('');
		$("#startDateExport").val('');
		$("#endDateExport").val('');
	}
    /********************************************导出结束***************************************************/
});
</script>
</head>
<body>
       <table >
            <tr>
           		<c:if test="${sessionScope.export_user_package_info != null }">
            	<td colspan="13"><div id="export"/></td>
           		</c:if>
           		<td>用户编号：</td>
               <td>
               		<input type="text" name="customerCode" id="customerCode" style="width:150px;height: 20px;border:1px solid #86A3C4;"/>
               </td>
               <td>用户外部编号：</td>
               <td>
               		<input type="text" name="userId" id="userId" style="width:150px;height: 20px;border:1px solid #86A3C4;"/>
               </td>
                <td><div id="query"/></td>
             </tr>
        </table>
<table id="grid" ></table>

	<div id="export-dialog-form" style="display: none;">
		<form id="export-form">
			<table>
				<tr>
					<td>用户编号：</td>
					<td><input type="text" name="customerCodeExport" id="customerCodeExport"
						style="width: 120px; height: 20px; border: 1px solid #86A3C4;" /></td>
				</tr>
				<tr>
					<td>产品包名称：</td>
					<td><input type="text" name="productNameExport" id="productNameExport"
						style="width: 120px; height: 20px; border: 1px solid #86A3C4;" /></td>
				</tr>
				<tr>
					<td>产品内容名称：</td>
					<td><input type="text" name="contentNameExport" id="contentNameExport"
						style="width: 120px; height: 20px; border: 1px solid #86A3C4;" /></td>
				</tr>
				<tr>
					<td><span class="color_red">*</span>创建时间：</td>
					<td>
					<input type="text" name="startDateExport" id="startDateExport" style="width: 100px;" /></td>
					<td>至<input type="text" name="endDateExport" id="endDateExport" style="width: 100px;"  />
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
