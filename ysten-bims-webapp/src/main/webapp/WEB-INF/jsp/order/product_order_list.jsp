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
    	dataSource : 'product_order_list.json',
    	width : '100%',
        height : rFrameHeight,
        singleSelect : true,
        limit :limit,
        colModel : [ 
				{header : '<b>用户编号</b>', name : 'customerCode', align : 'center', width : 150}, 
				{header : '<b>手机号码</b>', name : 'phone', align : 'center', width : 80}, 
				{header : '<b>订单编号</b>', name : 'code',align : 'center',width: 150},
				{header : '<b>订单外部编号</b>', name : 'outterOrderCode',align : 'center',width: 150},
				{header : '<b>产品包名称</b>', name : 'productName',align : 'center',width: 100},
				{header : '<b>订单来源</b>', name : 'source',align : 'center',width: 50},
				{header : '<b>单价(分)</b>', name : 'price',align : 'center',width: 60,renderer:function(value){
               	 if(value==null || value==""){
                     return "";
                     }else{
                    	 return value*100;
                         }
                 }},
				{header : '<b>数量</b>', name : 'quantity',align : 'center',width: 60},
				{header : '<b>折扣</b>', name : 'discount',align : 'center',width: 60},
				{header : '<b>总金额(分)</b>', name : 'payPrice',align : 'center',width: 70},
				{header : '<b>订单状态</b>', name : 'state',align : 'center',width: 60},
				{header : '<b>订购时间</b>', name : 'orderDate',align : 'center',width: 120},
				{header : '<b>生效时间</b>', name : 'startDate',align : 'center',width: 120},
				{header : '<b>失效时间</b>', name : 'endDate',align : 'center',width: 120},
				{header : '<b>产品类型</b>', name : 'productType',align : 'center',width: 50},
				{header : '<b>节目集名称</b>', name : 'contentName',align : 'center',width:70}
                     
		]
    });
    $('#query').omButtonbar({btns : [{label:"查询",icons : {left : opPath+'search.png'}}]});
	$('#export').omButtonbar({btns : [{label:"导出",icons : {left : opPath+'op-btn-icon.png'}}]});
    $("#begintime").omCalendar({dateFormat : 'yy-mm-dd',showTime : false});
    $("#endtime").omCalendar({dateFormat : 'yy-mm-dd',showTime : false});
    
    $('#query').bind('click', function(e) {
        var customerCode = filterStartEndSpaceTrim($('#customerCode').val());
        var phone = filterStartEndSpaceTrim($('#phone').val());
        var orderCode = filterStartEndSpaceTrim($('#orderCode').val());
        var outterOrderCode = filterStartEndSpaceTrim($('#outterOrderCode').val());
        var begintime = $('#begintime').val();
        var endtime = $('#endtime').val();
        $('#grid').omGrid("setData", 'product_order_list.json?customerCode='+encodeURIComponent(customerCode)+'&phone='+encodeURIComponent(phone)+'&orderCode='+encodeURIComponent(orderCode)+
        '&outterOrderCode='+outterOrderCode+'&begintime='+begintime+'&endtime='+endtime);
    });
    /********************************************订单导出开始***************************************************/
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
	                var phone = $('#phoneExport').val();
	                var code = $('#codeExport').val();
	                var outterOrderCode = $("#outterOrderCodeExport").val();
	                var startDate = $("#startDateExport").val();
	                var endDate = $("#endDateExport").val();
	                if(startDate==null || startDate=="" || endDate == null || endDate == "" ){
	                	$.omMessageBox.alert({
                            type:'alert',
                            title:'温馨提示',
                            content:'请填写订购时间！',
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
	        		location.href="product_order_export.json?random="+random+"&customerCode="+encodeURIComponent(customerCode)
	        				+"&phone="+encodeURIComponent(phone)
	        				+"&code="+encodeURIComponent(code)
	        				+"&outterOrderCode="+encodeURIComponent(outterOrderCode)
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
		exportDialog.omDialog("option", "title", "用户订单导出");
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
		$('#customerCodeExport').val('');
        $('#phoneExport').val('');
        $('#codeExport').val('');
        $("#outterOrderCodeExport").val('');
        $("#startDateExport").val('');
        $("#endDateExport").val('');
	}
    /********************************************订单导出结束***************************************************/
});
</script>
</head>
<body>
       <table >
            <tr>
           		<c:if test="${sessionScope.export_product_order != null }">
            	<td colspan="13"><div id="export"/></td>
           		</c:if>
           		<td>用户编号：</td>
               <td>
               		<input type="text" name="customerCode" id="customerCode" style="width:100px;height: 20px;border:1px solid #86A3C4;"/>
               </td>
               <td>手机号码：</td>
               <td>
               		<input type="text" name="phone" id="phone" style="width:100px;height: 20px;border:1px solid #86A3C4;"/>
               </td>
               <td>订单编号：</td>
               <td>
               		<input name="orderCode" id="orderCode" style="width:100px;height: 20px;border:1px solid #86A3C4;"/>
               </td>
               <td>订单外部编号：</td>
               <td>
               		<input name="outterOrderCode" id="outterOrderCode" style="width:100px;height: 20px;border:1px solid #86A3C4;"/>
               </td>
               <td>订购时间：</td>
               <td>
               		<input type="text" name="begintime" id="begintime" style="width:100px;"/>
               </td>
               <td>
               		至<input type="text" name="endtime" id="endtime" style="width:100px;"/>
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
					<td>手机号码：</td>
					<td><input type="text" name="phoneExport" id="phoneExport"
						style="width: 120px; height: 20px; border: 1px solid #86A3C4;" /></td>
				</tr>
				<tr>
					<td>订单编号：</td>
					<td><input name="codeExport" id="codeExport"
						style="width: 120px; height: 20px; border: 1px solid #86A3C4;" /></td>
				</tr>
				<tr>
					<td>订单外部编号：</td>
					<td><input name="outterOrderCodeExport" id="outterOrderCodeExport"
						style="width: 120px; height: 20px; border: 1px solid #86A3C4;" /></td>
				</tr>
				<tr>
					<td><span class="color_red">*</span>订购时间：</td>
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
