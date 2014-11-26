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
	$('#center-tab').omTabs({height: "33", border: false});
    $('#grid').omGrid({
    	dataSource : 'customer_device_history_list.json',
    	width : '100%',
        height : rFrameHeight,
        singleSelect : false,
        limit : limit,
        colModel : [ 
                     {header : '<b>用户编号</b>', name : 'customerCode', align : 'center', width : 150}, 
                     {header : '<b>用户外部编号</b>', name : 'userId', align : 'center', width : 100}, 
                     {header : '<b>用户登录名</b>', name : 'loginName', align : 'center', width : 100},
                     {header : '<b>用户电话</b>', name : 'phone', align : 'center', width : 100},
                     {header : '<b>外部用户编号</b>', name : 'customerOuterCode', align : 'center', width : 150}, 
                     {header : '<b>用户创建时间</b>', name : 'customerCreateDate', align : 'center', width : 130},
                     {header : '<b>用户激活时间</b>', name : 'customerActivateDate', align : 'center', width : 130},
                     {header : '<b>设备编号</b>', name : 'ystenId', align : 'center', width : 120},
                     {header : '<b>设备序列号</b>', name : 'deviceSno', align : 'center', width : 130},
                     {header : '<b>设备创建时间</b>', name : 'deviceCreateDate', align : 'center', width : 130},
                     {header : '<b>设备激活时间</b>', name : 'deviceActivateDate', align : 'center', width : 130},
                     {header : '<b>设备状态</b>', name : 'deviceState', align : 'center', width : 60},
                     {header : '<b>所属区域</b>', name : 'area',align : 'center',width: 50, renderer:function(value){
                    	 if(value==null || value==""){
                             return "";
                             }else{
                            	 return value.name;
                                 }
                         }
                     },
                     {header : '<b>所属地市</b>', name : 'city', align : 'center', width : 50, renderer:function(value){
                    	 if(value==null || value==""){
                             return "";
                             }else{
                            	 return value.name;
                                 }
                         }
                     },
                     {header : '<b>创建时间</b>', name : 'createDate', align : 'center', width : 130},
                     {header : '<b>描述</b>', name : 'description', align : 'center', width : 130}
		]
    });
    
    $('#query').omButtonbar({btns : [{label:"查询",icons : {left : opPath+'search.png'}}]});
    $('#heightQuery').omButtonbar({btns : [{label:"高级查询",icons : {left : opPath+'search.png'}}]});
    $('#export').omButtonbar({btns : [{label:"导出",icons : {left : opPath+'op-btn-icon.png'}}]});
    
    $('#query').bind('click', function() {
	    	var ystenId = $("#ystenId").val();
			var deviceCode = $("#deviceCode").val();
    		var userId = $("#userId").val();
    		var customerCode = $("#customerCode").val();
    		
    		$('#ystenValue').val(ystenId);
        	$('#deviceCodeValue').val(deviceCode);
        	$('#customerCodeValue').val(customerCode);
        	$('#userIdValue').val(userId);
        	$('#deviceStartDateValue').val("");
        	$('#deviceEndDateValue').val("");
        	$('#areaValue').val("");
        	$('#cityValue').val("");
        	$('#ystenHvalue').val("");
        	$('#deviceCodeHvalue').val("");
        	$('#customerCodeHvalue').val("");
        	$('#userIdHvalue').val("");
        	$('#uOuterCodeValue').val("");
        	$('#loginNameValue').val("");
        	$('#phoneValue').val("");
        	
    		$('#grid').omGrid("setData", 'customer_device_history_list.json?bid=0&ystenId='+encodeURIComponent(ystenId)+'&deviceCode='+encodeURIComponent(deviceCode)+'&userId='+encodeURIComponent(userId)+'&customerCode='+encodeURIComponent(customerCode));        
	});
    $('#heightQuery').bind('click', function(e) {
    	$('#bulkSelType').omCombo({dataSource : [
		                                           {text:'设备编号',value:'1'},
		                                           {text:'用户外部编号',value:'2'},
		                                           {text:'用户编号',value:'3'},
		                                           {text:'外部用户编号',value:'4'}
		                                       ],
		                                           value:'0',
		                                           editable:false,
		                                           width:180,
		                                           listMaxHeight:160,
		                                           onValueChange : function(target, newValue){
		                                               if(newValue == 0){
		                                            	   $("#openDeviceCode").show();
		                                                   $("#openYstenId").hide();
		                                                   $("#openUserId").hide();
		                                                   $("#openCustomerCode").hide();
		                                                   $("#openCustomerOuterCode").hide();
		                                                   $('#ystenId2').val("");
		                                                   $('#userId2').val("");
		                                                   $('#customerCode2').val("");
		                                                   $('#customerOuterCode2').val("");
		                                               }
													   if(newValue == 1){
		                                            	   $("#openDeviceCode").hide();
		                                                   $("#openYstenId").show();
		                                                   $("#openUserId").hide();
		                                                   $("#openCustomerCode").hide();
		                                                   $("#openCustomerOuterCode").hide();
		                                           	 	   $('#deviceCode2').val("");
		                                           	 	   $('#userId2').val("");
		                                                   $('#customerCode2').val("");
		                                                   $('#customerOuterCode2').val("");
		                                               }
													   if(newValue == 2){
														   $("#openDeviceCode").hide();
		                                                   $("#openYstenId").hide();
		                                                   $("#openUserId").show();
		                                                   $("#openCustomerCode").hide();
		                                                   $("#openCustomerOuterCode").hide();
		                                           	 	   $('#deviceCode2').val("");
		                                           	 	   $('#ystenId2').val("");
		                                                   $('#customerCode2').val("");
		                                                   $('#customerOuterCode2').val("");
		                                               }
													   if(newValue == 3){
														   $("#openDeviceCode").hide();
		                                                   $("#openYstenId").hide();
		                                                   $("#openUserId").hide();
		                                                   $("#openCustomerCode").show();
		                                                   $("#openCustomerOuterCode").hide();
		                                           	 	   $('#deviceCode2').val("");
		                                           	 	   $('#ystenId2').val("");
		                                                   $('#userId2').val("");
		                                                   $('#customerOuterCode2').val("");
		                                               }
													   if(newValue == 4){
														   $("#openDeviceCode").hide();
		                                                   $("#openYstenId").hide();
		                                                   $("#openUserId").hide();
		                                                   $("#openCustomerCode").hide();
		                                                   $("#openCustomerOuterCode").show();
		                                           	 	   $('#deviceCode2').val("");
		                                           	 	   $('#ystenId2').val("");
		                                                   $('#customerCode2').val("");
		                                                   $('#userId2').val("");
		                                               }
		                                       }
		 });
    	$("#deviceStartDate").omCalendar();
     	$("#deviceEndDate").omCalendar();
     	$('#city2').omCombo({dataSource : 'city.json',editable:false,listMaxHeight:150,width:182});
    	$('#area2').omCombo({dataSource : 'area.json',editable:false,width:182,listMaxHeight:160,
    		onValueChange : function(target, newValue, oldValue){
                $('#city2').omCombo({dataSource : 'city_by_area.json?areaId='+newValue,editable:false,listMaxHeight:150,width:182,value:''});
            }
    		});
	   	var dialog1 = $("#dialog-form1").omDialog({
	   		width: 750,
	       	height: 420,
	       	autoOpen : false,
	       	modal : true,
	       	resizable : false,
	       	draggable : false,
	       	buttons : {
	           	"提交" : function(){
	           		var startDate = $("#deviceStartDate").val();
                	var endDate = $("#deviceEndDate").val();
                	var cityId = $("#city2").omCombo('value');
                	var areaId = $("#area2").omCombo('value');
                	var deviceCode = $('#deviceCode2').val();
                	var ystenId = $('#ystenId2').val();
                	var userId = $('#userId2').val();
                	var customerCode = $('#customerCode2').val();
                	var customerOuterCode = $('#customerOuterCode2').val();
                	var loginName = $('#loginName2').val();
                	var phone = $('#phone2').val();
                	if((startDate !=null && endDate == null) || (startDate ==null && endDate != null)){
                		$.omMessageBox.alert({
                        		type:'alert',
                        		title:'温馨提示',
                        		content:'请将设备激活时间填写完整！',
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
                	$('#grid').omGrid("setData", 'customer_device_history_list.json?bid=1&ystenId='+encodeURIComponent(ystenId)
                        	+'&deviceCode='+encodeURIComponent(deviceCode)
                        	+'&userId='+encodeURIComponent(userId)
                        	+'&customerCode='+encodeURIComponent(customerCode)
                        	+'&startDate='+encodeURIComponent(startDate)
                        	+'&endDate='+encodeURIComponent(endDate)
                        	+'&cityId='+encodeURIComponent(cityId)
                        	+'&areaId='+encodeURIComponent(areaId)
                        	+'&customerOuterCode='+encodeURIComponent(customerOuterCode)
                        	+'&loginName='+encodeURIComponent(loginName)
                        	+'&phone='+encodeURIComponent(phone)
                        	);        
                	$('#ystenValue').val("");
                	$('#deviceCodeValue').val("");
                	$('#customerCodeValue').val("");
                	$('#userIdValue').val("");
                	$('#deviceStartDateValue').val(startDate);
                	$('#deviceEndDateValue').val(endDate);
                	$('#areaValue').val(areaId);
                	$('#cityValue').val(cityId);
                	$('#ystenHvalue').val(ystenId);
                	$('#deviceCodeHvalue').val(deviceCode);
                	$('#customerCodeHvalue').val(customerCode);
                	$('#userIdHvalue').val(userId);
                	$('#uOuterCodeValue').val(customerOuterCode);
                	$('#loginNameValue').val(loginName);
                	$('#phoneValue').val(phone);
	               		$("#dialog-form1").omDialog("close");
	                	return false; 
	           	},
	           	"取消" : function() {
	               	$("#dialog-form1").omDialog("close");
	           	}
	       	},onClose:function(){$('#myform1').validate().resetForm();}
	   	});
	   	dialog1.omDialog("option", "title", "设备信息高级查询");
	   	dialog1.omDialog("open");
	});
    /**********************************************导出*************************************************/
	$('#export').bind('click', function(e) {
		var selections=$('#grid').omGrid('getSelections',true);
		$('#exportType').omCombo({dataSource : [
		                                           {text:'选中导出',value:'0'},
		                                           {text:'全部导出',value:'1'}
		                                       ],
		                                           value:'0',
		                                           width:180,
		                                           listMaxHeight:160,
		 });
		 
    		var dialog_export = $("#dialog-form").omDialog({
            	width: 300,
            	autoOpen : false,
            	modal : true,
            	resizable : false,
            	draggable : false,
            	buttons : {
                	"提交" : function(){
                    	//var info = "你确定要导出查询出的全部数据吗？";
                    		if($('#exportType').omCombo('value') == 0){
                             	 if (selections.length == 0) {
                       	        	$.omMessageBox.alert({
                       	                type:'alert',
                       	                title:'温馨提示',
                       	                content:'选中导出至少选择一行记录！',
                       	            });
                       	            return false;
                       	        }
	                       	        //info = "你确定导出选中的这些数据吗？";
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
                        		location.href="device_customer_history_export_byId.json?ids="+encodeURIComponent(toDeleteRecordID.toString());
                			}
                    		if($('#exportType').omCombo('value') == 1){
                				var random = Math.random();
                				var ystenValue = $('#ystenValue').val();
                            	var deviceCodeValue = $('#deviceCodeValue').val();
                            	var customerCodeValue = $('#customerCodeValue').val();
                            	var userIdValue = $('#userIdValue').val();
                            	var deviceStartDateValue = $('#deviceStartDateValue').val();
                            	var deviceEndDateValue = $('#deviceEndDateValue').val();
                            	var areaValue = $("#areaValue").val();
                            	var cityValue = $("#cityValue").val();
        	                	var ystenHvalue = $("#ystenHvalue").val();
        	                	var deviceCodeHvalue = $("#deviceCodeHvalue").val();
        	                	var customerCodeHvalue = $("#customerCodeHvalue").val();
        	                	var userIdHvalue = $("#userIdHvalue").val();
        	                	var uOuterCodeValue = $("#uOuterCodeValue").val();
        	                	var loginNameValue = $("#loginNameValue").val();
        	                	var phoneValue = $("#phoneValue").val();
        	                	
        	                	location.href="device_customer_history_export_by_query.json?random="+random
                				+"&ystenValue="+encodeURIComponent(ystenValue)
                				+"&deviceCodeValue="+encodeURIComponent(deviceCodeValue)
                				+"&customerCodeValue="+encodeURIComponent(customerCodeValue)
                				+"&userIdValue="+encodeURIComponent(userIdValue)
                				
                				+"&deviceStartDateValue="+encodeURIComponent(deviceStartDateValue)
                				+"&deviceEndDateValue="+encodeURIComponent(deviceEndDateValue)
                				+"&areaValue="+encodeURIComponent(areaValue)
                				+"&cityValue="+encodeURIComponent(cityValue)
                				+"&ystenHvalue="+encodeURIComponent(ystenHvalue)
                				+"&deviceCodeHvalue="+encodeURIComponent(deviceCodeHvalue)
                				+"&customerCodeHvalue="+encodeURIComponent(customerCodeHvalue)
                				+"&userIdHvalue="+encodeURIComponent(userIdHvalue)
                				+"&uOuterCodeValue="+encodeURIComponent(uOuterCodeValue)
                				+"&loginNameValue="+encodeURIComponent(loginNameValue)
                				+"&phoneValue="+encodeURIComponent(phoneValue)
                    			}
                    		$("#dialog-form").omDialog("close");  
                	},
                	"取消" : function() {
                    	$("#dialog-form").omDialog("close");
                	}
            	},onClose:function(){
            		$('#myform').validate().resetForm();
                	$("#dialog-form").omDialog("close");
                	}
        	});
    		dialog_export.omDialog("option", "title", "设备信息导出");
    		dialog_export.omDialog("open");
    	});
/**************************************************************************************/
/**
    $('#export').bind('click', function(e) {
        var dialog = $("#dialog-form").omDialog({
            width: 400,
            autoOpen : false,
            modal : true,
            resizable : false,
            draggable : false,
            buttons : {
                "提交" : function(){
                	var random = Math.random();
                	var newDeviceCode = $('#newDeviceCode1').val();
                	var oldDeviceCode = $('#oldDeviceCode1').val();
                	var customerCode = $('#customerCode1').val();
        		location.href="customer_device_history_export.json?random="+random
        				+"&newDeviceCode="+encodeURIComponent(newDeviceCode)
        				+"&oldDeviceCode="+encodeURIComponent(oldDeviceCode)
        				+"&customerCode="+encodeURIComponent(customerCode);
                     $("#dialog-form").omDialog("close"); 
                     //showDiv();
                     return false; 
                },
                "取消" : function() {
                    $("#dialog-form").omDialog("close");
                }
            },onClose:function(){}
        });
        dialog.omDialog("option", "title", "终端用户历史信息列表导出");
        dialog.omDialog("open");
    });
    */
});
	
</script>
</head>
<body>
<div id="center-tab">
    <ul>
        <li><a href="#tab1">用户终端关系历史信息列表:</a></li>
    </ul>
</div>
<table >
	<tr>
		<td>
			设备编号: <input type="text" name="ystenId" id="ystenId" style="width:150px;height: 20px;border:1px solid #86A3C4;"/>
			设备编号: <input type="text" name="deviceCode" id="deviceCode" style="width:150px;height: 20px;border:1px solid #86A3C4;"/>
			用户编号: <input type="text" name="customerCode" id="customerCode" style="width:150px;height: 20px;border:1px solid #86A3C4;"/>
			用户外部编号: <input type="text" name="userId" id="userId" style="width:150px;height: 20px;border:1px solid #86A3C4;"/>
		</td>
		<td>
			<div id="query"/>
		</td>
		<td><div id="heightQuery"/></td>
		<td>
			<div id="export"/>
		</td>
	</tr>
</table>
<table id="grid" ></table>
<div id="dialog-form" style="display:none;">
<form id="myform">
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
<div id="dialog-form1" style="display: none;">
		<form id="myform1">
		 <table>
				<tr>
					<td style="width: 100px;text-align: right;">设备激活时间：</td>
					<td>
					<input type="text" id="deviceStartDate" name="deviceStartDate" style="width:160px;"/>
					至
					<input id="deviceEndDate" name="deviceEndDate"  style="width:160px;"/>
					</td>
			   </tr>
		  </table>
		  <table>
				<tr>
	               <td style="width: 100px; text-align: right;">批量查询字段名：</td>
				   <td><input name="bulkSelType" id="bulkSelType"/></td>
	               <td><span></span></td>
	            </tr>
				<tr id="openYstenId" style="display: none;">
	               <td style="width: 100px; text-align: right;">设备编号：</td>
				   <td><textarea id="ystenId2" name="ystenId2" cols="70" rows="15" style="border:1px solid #86A3C4;"></textarea></td>
	               <td><span>请输入易视腾编号，如有多个请用英文逗号分隔</span></td>
               </tr>
				<tr id="openUserId" style="display: none;">
	               <td style="width: 100px; text-align: right;">用户外部编号：</td>
				   <td><textarea id="userId2" name="userId2" cols="70" rows="15" style="border:1px solid #86A3C4;"></textarea></td>
	               <td><span>请输入用户外部编号，如有多个用户外部编号请用英文逗号分隔</span></td>
               </tr>
               <tr id="openCustomerCode" style="display: none;">
	               <td style="width: 100px; text-align: right;">用户编号：</td>
				   <td><textarea id="customerCode2" name="customerCode2" cols="70" rows="15" style="border:1px solid #86A3C4;"></textarea></td>
	               <td><span>请输入用户编号，如有多个用户编号请用英文逗号分隔</span></td>
               </tr>
               <tr id="openCustomerOuterCode" style="display: none;">
	               <td style="width: 100px; text-align: right;">外部用户编号：</td>
				   <td><textarea id="customerOuterCode2" name="customerOuterCode2" cols="70" rows="15" style="border:1px solid #86A3C4;"></textarea></td>
	               <td><span>请输入外部用户编号，如有多个外部用户编号请用英文逗号分隔</span></td>
               </tr>
           </table>
		  <table>
			   <tr>
					<td style="width: 100px;text-align: right;">所属区域：</td>
					<td><input id="area2" name="area2"/></td>	
					<td style="width: 65px;text-align: left;">所属地市：</td>
					<td><input id="city2" name="city2"/></td>
				</tr>
			<!--
				<tr>
					<td style="width: 100px;text-align: right;">设备编号：</td>
					<td><input id="ystenId2" name="ystenId2"  style="width:180px;height: 20px;border:1px solid #86A3C4;"/></td>					
				</tr>
				<tr>
					<td style="width: 100px;text-align: right;">用户外部编号：</td>
					<td><input id="userId2" name="userId2" style="width:180px;height: 20px;border:1px solid #86A3C4;"/></td>
				</tr>
				<tr>
					<td style="width: 100px;text-align: right;">用户编号：</td>
					<td><input id="customerCode2" name="customerCode2"  style="width:180px;height: 20px;border:1px solid #86A3C4;"/></td>					
				</tr>
				<tr>
					<td style="width: 100px;text-align: right;">外部用户编号：</td>
					<td><input id="customerOuterCode2" name="customerOuterCode2"  style="width:180px;height: 20px;border:1px solid #86A3C4;"/></td>					
				</tr>
				  -->
				<tr>
					<td style="width: 100px;text-align: right;">登录名：</td>
					<td><input id="loginName2" name="loginName2"  style="width:180px;height: 20px;border:1px solid #86A3C4;"/></td>
					<td style="width: 65px;text-align: left;">用户电话：</td>
					<td><input id="phone2" name="phone2"  style="width:180px;height: 20px;border:1px solid #86A3C4;"/></td>					
				</tr>
			</table>
		</form>
	</div>
	
	<input type="hidden" id="ystenValue" name="ystenValue"/>
	<input type="hidden" id="deviceCodeValue" name="deviceCodeValue"/>	
	<input type="hidden" id="customerCodeValue" name="customerCodeValue"/>
	<input type="hidden" id="userIdValue" name="userIdValue"/>
	
	<input type="hidden" id="deviceStartDateValue" name="deviceStartDateValue"/>
	<input type="hidden" id="deviceEndDateValue" name="deviceEndDateValue"/>
	<input type="hidden" id="areaValue" name="areaValue"/>	
	<input type="hidden" id="cityValue" name="cityValue"/>
	<input type="hidden" id="ystenHvalue" name="ystenHvalue"/>
	<input type="hidden" id="deviceCodeHvalue" name="deviceCodeHvalue"/>	
	<input type="hidden" id="customerCodeHvalue" name="customerCodeHvalue"/>
	<input type="hidden" id="userIdHvalue" name="userIdHvalue"/>
	<input type="hidden" id="uOuterCodeValue" name="uOuterCodeValue"/>
	<input type="hidden" id="loginNameValue" name="loginNameValue"/>
	<input type="hidden" id="phoneValue" name="phoneValue"/>
</body>
</html>
