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
    	dataSource : 'relation_customer_list.json',
    	width : '100%',
        height : rFrameHeight - 20,
        singleSelect : false,
        limit : limit,
        colModel : [ 
                     {header : '<b>设备编号</b>', name : 'ystenId', align : 'center', width : 230},
                     {header : '<b>设备序列号</b>', name : 'deviceSno', align : 'center', width : 120},
                     {header : '<b>MAC地址</b>', name : 'deviceMac', align : 'center', width : 120},
                     {header : '<b>设备状态</b>', name : 'deviceState', align : 'center', width : 60},
                     {header : '<b>设备厂商</b>', name : 'deviceVendor', align : 'center', width : 60,renderer:function(value){
                        	if(value==null || value==""){
                             	return "";
                            }else{
                                 return value.name;
                            }
                       }},
                     {header : '<b>设备型号</b>', name : 'deviceType', align : 'center', width : 60,renderer:function(value){
                        	if(value==null || value==""){
                             	return "";
                            }else{
                                 return value.name;
                            }
                       }},
                     {header : '<b>设备所属区域</b>', name : 'deviceArea', align : 'center', width : 60,renderer:function(value){
                        	if(value==null || value==""){
                             	return "";
                            }else{
                                 return value.name;
                            }
                       }},
                     {header : '<b>设备所属地市</b>', name : 'deviceCity', align : 'center', width : 60,renderer:function(value){
                        	if(value==null || value==""){
                             	return "";
                            }else{
                                 return value.name;
                            }
                       }},
                     {header : '<b>设备创建时间</b>', name : 'deviceCreateDate', align : 'center', width : 120},
                     {header : '<b>设备激活时间</b>', name : 'deviceActivateDate', align : 'center', width : 120},
                     {header : '<b>设备到期时间</b>', name : 'deviceExpireDate', align : 'center', width : 120},
                     {header : '<b>用户编号</b>', name : 'customerCode', align : 'center', width : 150}, 
                     {header : '<b>用户外部编号</b>', name : 'customerUserId', align : 'center', width : 150},
                     {header : '<b>用户状态</b>', name : 'customerState', align : 'center', width : 60},
                     {header : '<b>手机号</b>', name : 'customerPhone', align : 'center', width : 150},
                     {header : '<b>登录名</b>', name : 'customerLoginName', align : 'center', width : 120},
                     {header : '<b>真实姓名</b>', name : 'customerRealName', align : 'center', width : 120},
                     {header : '<b>昵称</b>', name : 'customerNickName', align : 'center', width : 120},
                     {header : '<b>用户类型</b>', name : 'customerType', align : 'center', width : 60},
                     {header : '<b>用户所属区域</b>', name : 'customerArea', align : 'center', width : 60,renderer:function(value){
                     	if(value==null || value==""){
                          	return "";
                         }else{
                              return value.name;
                         }
                    }},
                  	{header : '<b>用户所属地市</b>', name : 'customerCity', align : 'center', width : 60,renderer:function(value){
                     	if(value==null || value==""){
                          	return "";
                         }else{
                              return value.name;
                         }
                    }},
		]
    });
//    $('#remoteDevice').omButtonbar({btns : [{label:"终端解绑",icons : {left : opPath+'remove.png'}}]});
    $('#query').omButtonbar({btns : [{label:"查询",icons : {left : opPath+'search.png'}}]});
    $('#export').omButtonbar({btns : [{label:"导出",icons : {left : opPath+'op-btn-icon.png'}}]});
//    $('#bulkQuery').omButtonbar({btns : [{label:"批量查询",icons : {left : opPath+'search.png'}}]});
    $('#heightQuery').omButtonbar({btns : [{label:"高级查询",icons : {left : opPath+'search.png'}}]});

/**************************************************高级查询********************************************************/
    function getDeviceType(){
			var deviceVendor2 = $('#deviceVendor2').omCombo('value');
	    	$('#deviceType2').omCombo({dataSource:'dynamicCascade.json?par=0&deviceVendor=' + deviceVendor2,editable:false,width:182,listMaxHeight:150,value:'0'});
	}

$('#heightQuery').bind('click', function(e) {
	$('#customerState2').omCombo({dataSource : 'customer_state.json', editable:false, width:182, listMaxHeight:100,value:'0'});
	$('#cityId2').omCombo({dataSource : 'city.json',editable:false,listMaxHeight:100,width:182});
	$('#deviceState2').omCombo({dataSource : 'device_state.json?par=0',editable:false,width:182,listMaxHeight:100,value:'0'});
	$('#areaId2').omCombo({dataSource : 'area.json',editable:false,width:182,listMaxHeight:100,
		onValueChange : function(target, newValue, oldValue){
            $('#cityId2').omCombo({dataSource : 'city_by_area.json?areaId='+newValue,editable:false,listMaxHeight:100,width:182,value:''});
        }
		});
	$('#deviceVendor2').omCombo({dataSource : 'device_vendor.json?par=0',editable:false,width:182,listMaxHeight:100,value:'0',
		onValueChange : function(target, newValue, oldValue, event){getDeviceType(); }});
    		 $('#bulkSelType').omCombo({dataSource : [
    		                                           {text:'设备编号',value:'1'},
    		                                           {text:'设备序列号',value:'2'}
    		                                       ],
    		                                           value:'0',
    		                                           editable:false,
    		                                           width:180,
    		                                           listMaxHeight:160,
    		                                           onValueChange : function(target, newValue){
    		                                               if(newValue == 0){
    		                                            	   $("#openDeviceCode").show();
    		                                                   $("#openYstenId").hide();
    		                                                   $("#openSno").hide();
    		                                                   $('#ystenId2').val("");
    		                                                   $('#sno2').val("");
    		                                               }
														   if(newValue == 1){
    		                                            	   $("#openDeviceCode").hide();
    		                                                   $("#openYstenId").show();
    		                                                   $("#openSno").hide();
    		                                           	 	   $('#deviceCode2').val("");
    		                                                   $('#sno2').val("");
    		                                               }
														   if(newValue == 2){
    		                                            	   $("#openDeviceCode").hide();
    		                                                   $("#openYstenId").hide();
    		                                                   $("#openSno").show();
    		                                                   $('#ystenId2').val("");
    		                                           	 	   $('#deviceCode2').val("");
    		                                               }
    		                                       }
    		 });
    		 
    		$("#startDateExport").omCalendar();
    	 	$("#endDateExport").omCalendar();
    	 	$("#startDateAvtive").omCalendar();
    	 	$("#endDateAvtive").omCalendar();
        	var dialog1 = $("#dialog-form1").omDialog({
        		width: 750,
            	height: 500,
            	autoOpen : false,
            	modal : true,
            	resizable : false,
            	draggable : false,
            	buttons : {
                	"提交" : function(){
                		var startDate = $("#startDateExport").val();
                	    var endDate = $("#endDateExport").val();
                	    var startDateAvtive = $("#startDateAvtive").val();
                	    var endDateAvtive = $("#endDateAvtive").val();
                		var ystenId = $("#ystenId2").val();
                		var deviceCode = $("#deviceCode2").val();
                        var deviceSno = $("#sno2").val();
                		var deviceVendor = $("#deviceVendor2").omCombo('value');
                		var deviceType = $("#deviceType2").omCombo('value');
                		var deviceArea = $("#areaId2").omCombo('value');
                		var deviceCity = $("#cityId2").omCombo('value');
                		var deviceMac = $("#mac2").val();
                		var deviceState = $("#deviceState2").omCombo('value');
                		var customerUserId = $("#userId2").val();
                		var customerCode = $("#customerCode2").val();
                		var customerState = $("#customerState2").omCombo('value');
                        var customerPhone = $("#phone2").val();
                		var customerNickName = $("#nickName2").val();
                		var customerLoginName = $("#loginName2").val();
	                		                	
	                	if((startDate !=null && endDate == null) || (startDate ==null && endDate != null)){
		                		$.omMessageBox.alert({
	                            		type:'alert',
	                            		title:'温馨提示',
	                            		content:'请将创建时间填写完整！',
	                        		});
		                	 	return false;
		                	}
		                	
	                		if((startDateAvtive !=null && endDateAvtive == null) || (startDateAvtive ==null && endDateAvtive != null)){
		                		$.omMessageBox.alert({
	                            		type:'alert',
	                            		title:'温馨提示',
	                            		content:'请将激活时间填写完整！',
	                        		});
		                	 	return false;
		                	}
	                	if(startDate > endDate || startDateAvtive > endDateAvtive){
	                		$.omMessageBox.alert({
                            		type:'alert',
                            		title:'温馨提示',
                            		content:'结束时间要大于等于起始时间！',
                        		});
	                	 	return false;
		               }
	                	$('#grid').omGrid("setData", 'relation_customer_list.json?bid=1&customerCode='+encodeURIComponent(customerCode)+
								'&ystenId='+encodeURIComponent(ystenId)+
	                    		'&customerUserId='+encodeURIComponent(customerUserId)+
	                    		'&deviceCode='+encodeURIComponent(deviceCode)+
	                    		'&deviceSno='+encodeURIComponent(deviceSno)+
	                    		'&customerPhone='+encodeURIComponent(customerPhone)+
								'&deviceVendorId='+encodeURIComponent(deviceVendor)+
								'&deviceTypeId='+encodeURIComponent(deviceType)+
								'&areaId='+encodeURIComponent(deviceArea)+
								'&cityId='+encodeURIComponent(deviceCity)+
								'&deviceMac='+encodeURIComponent(deviceMac)+
								'&dState='+encodeURIComponent(deviceState)+
								'&cState='+encodeURIComponent(customerState)+
								'&customerNickName='+encodeURIComponent(customerNickName)+
								'&customerLoginName='+encodeURIComponent(customerLoginName)+
								'&startDate='+encodeURIComponent(startDate)+
								'&endDate='+encodeURIComponent(endDate)+
		        				'&startDateAvtive='+encodeURIComponent(startDateAvtive)+
		        				'&endDateAvtive='+encodeURIComponent(endDateAvtive));
	                	$('#ystenValue').val("");
	                	$('#deviceCodeValue').val("");
	                	$('#snoValue').val("");
	                	$('#customerCodeValue').val("");
	                	$('#userIdValue').val("");
	                	$('#phoneValue').val("");	
	                	
	                	$('#startDateValue').val(startDate);
	                	$('#endDateValue').val(endDate);
	    	            $('#startDateAvtiveValue').val(startDateAvtive);
	    	            $('#endDateAvtiveValue').val(endDateAvtive);
	    	            $('#snoHvalue').val(deviceSno);
	    	            $('#deviceCodeHvalue').val(deviceCode);
	    	            $('#ystenIdHvalue').val(ystenId);
	    	            $('#deviceVendorValue').val(deviceVendor);	
	    	            $('#deviceTypeValue').val(deviceType);
	    	            $('#areaHvalue').val(deviceArea);
	    	            $('#cityValue').val(deviceCity);
	    	            $('#macValue').val(deviceMac);
	    	            $('#deviceStateValue').val(deviceState);	
	    	            $('#userIdHvalue').val(customerUserId);
	    	            $('#customerCodeHvalue').val(customerCode);	
	    	            $('#phoneHvalue').val(customerPhone);
	    	            $('#nickNameValue').val(customerNickName);
	    	            $('#loginNameValue').val(customerLoginName);
	    	            $('#customerStateValue').val(customerState);
	    	            
	                	$("#dialog-form1").omDialog("close");
                     	return false; 
                	},
                	"取消" : function() {
                    	$("#dialog-form1").omDialog("close");
                	}
            	},onClose:function(){$('#myform1').validate().resetForm();}
        	});
        	dialog1.omDialog("option", "title", "终端用户关系高级查询");
        	dialog1.omDialog("open");
    	});

/**************************************************************************************************************/
 /*
    $("#remoteDevice").click(function(){
    	var selections=$('#grid').omGrid('getSelections',true);
        if (selections.length == 0) {
        	$.omMessageBox.alert({
                type:'alert',
                title:'温馨提示',
                content:'解绑操作至少选择一行记录！',
            });
            return false;
        }
        $.omMessageBox.confirm({
            title:'确认解绑',
            content:'你确定要解除设备和用户的关系，解除后设备将无法上线，你确定要执行该操作吗？',
            onClose:function(v){
            	if(v==true){
            		var mapIds = "";
                	for(var i=0;i<selections.length;i++){
                    	if(i != selections.length - 1){
                    		mapIds  += selections[i].mapId+",";
                        }else{
                        	mapIds  += selections[i].mapId;
                        }
                    }
                	$.post('remote_customer_device.json',{mapIds:mapIds.toString()},function(result){
                        $('#grid').omGrid('reload');
                        if(result=='success'){
                        	$.omMessageTip.show({title: tip, content: "用户与设备解绑成功！", timeout: time,type:'success'});
                        }else{
                        	if(result != ""){
                        		$.omMessageTip.show({title: tip, content: result , timeout: time,type:'error'});
                        	}else{
                        		$.omMessageTip.show({title: tip, content: "用户与设备解绑失败！" , timeout: time,type:'error'});
                        	}
                        }
                        $('#grid').omGrid('reload')
                        $('#dialog-form').omDialog('close');
                    });
            	}
            }  
        });    	
    });
    */
    $("#query").click(function(){
        var customerCode = $("#customerCode1").val();
        var ystenId = $("#ystenId1").val();
        var customerUserId = $("#customerUserId1").val();
        var deviceCode = $("#deviceCode1").val();
        var deviceSno = $("#deviceSno1").val();
        var customerPhone = $("#customerPhone1").val();
        $('#grid').omGrid("setData", 'relation_customer_list.json?bid=0&customerCode='+encodeURIComponent(customerCode)+'&ystenId='+encodeURIComponent(ystenId)+
        		'&customerUserId='+encodeURIComponent(customerUserId)+
        		'&deviceCode='+encodeURIComponent(deviceCode)+
        		'&deviceSno='+encodeURIComponent(deviceSno)+
        		'&customerPhone='+encodeURIComponent(customerPhone)); 
        $('#ystenValue').val(ystenId);
    	$('#deviceCodeValue').val(deviceCode);
    	$('#snoValue').val(deviceSno);
    	$('#customerCodeValue').val(customerCode);
    	$('#userIdValue').val(customerUserId);
    	$('#phoneValue').val(customerPhone);	
    	
    	$('#startDateValue').val("");
    	$('#endDateValue').val("");
        $('#startDateAvtiveValue').val("");
        $('#endDateAvtiveValue').val("");
        $('#snoHvalue').val("");
        $('#deviceCodeHvalue').val("");
        $('#ystenIdHvalue').val("");
        $('#deviceVendorValue').val("");
        $('#deviceTypeValue').val("");
        $('#areaHvalue').val("");
        $('#cityValue').val("");
        $('#macValue').val("");
        $('#deviceStateValue').val("");
        $('#userIdHvalue').val("");
        $('#customerCodeHvalue').val("");
        $('#phoneHvalue').val("");
        $('#nickNameValue').val("");
        $('#loginNameValue').val("");
        $('#customerStateValue').val("");
    });
    /**
	$('#bulkQuery').bind('click', function(e) {
		var dialog7 = $("#dialog-form7").omDialog({
			width: 700,
        	height: 320,
	    	autoOpen : false,
	    	modal : true,
	    	resizable : false,
	    	draggable : false,
	    	buttons : {
	        	"提交" : function(){
	             	var snos = filterStartEndSpaceTrim($('#snos').val());
	             	submitData={
	             			snos : filterStartEndSpaceTrim($('#snos').val())
	                    };
	             	$.post('validator_sno.json', submitData, function(result){
	                    if(result.indexOf('合法') > 0){
	                       $('#grid').omGrid("setData", 'relation_customer_list_by_snos.json?snos='+encodeURIComponent(snos));
	                    }else{
	                        $.omMessageBox.confirm({
	            	            title:'确认继续批量查询',
	            	            content:result,
	            	            onClose:function(v){
	            	            	if(v==true){
	            	            		$('#grid').omGrid("setData", 'relation_customer_list_by_snos.json?snos='+encodeURIComponent(snos));
	            	            	}
	            	            }  
	            	        });
	                    }
	                    $("#dialog-form7").omDialog("close");
	                });
	                return false;
	        	},
	        	"取消" : function() {
	            	$("#dialog-form7").omDialog("close");
	        	}
	    	},onClose:function(){
	    		$('#myform7').validate().resetForm();
	    		$("#dialog-form7").omDialog("close");
	        	}
		});
		dialog7.omDialog("option", "title", "终端用户关系信息批量查询");
		dialog7.omDialog("open");
	});*/

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
  		 
        		var dialog3 = $("#dialog-form3").omDialog({
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
                                			toDeleteRecordID  += selections[i].deviceId+",";
                                    		}else{
                                    			toDeleteRecordID  += selections[i].deviceId;
                                    		}
                                	}
                            		location.href="relation_device_export_byId.json?ids="+encodeURIComponent(toDeleteRecordID.toString());
                    			}
	                    		if($('#exportType').omCombo('value') == 1){
                    				var random = Math.random();
                    				var ystenValue = $('#ystenValue').val();
                    				var deviceCodeValue = $('#deviceCodeValue').val();
                    		    	var snoValue = $('#snoValue').val();
                    		    	var customerCodeValue = $('#customerCodeValue').val();
                    		    	var userIdValue = $('#userIdValue').val();
                    		    	var phoneValue = $('#phoneValue').val();	
                    		    	
                    		    	var startDateValue = $('#startDateValue').val();
                    		    	var endDateValue = $('#endDateValue').val();
                    		        var startDateAvtiveValue = $('#startDateAvtiveValue').val();
                    		        var endDateAvtiveValue = $('#endDateAvtiveValue').val();
                    		        var snoHvalue = $('#snoHvalue').val();
                    		        var deviceCodeHvalue = $('#deviceCodeHvalue').val();
                    		        var ystenIdHvalue = $('#ystenIdHvalue').val();
                    		        var deviceVendorValue = $('#deviceVendorValue').val();
                    		        var deviceTypeValue = $('#deviceTypeValue').val();
                    		        var areaHvalue = $('#areaHvalue').val();
                    		        var cityValue = $('#cityValue').val();
                    		        var macValue = $('#macValue').val();
                    		        var deviceStateValue = $('#deviceStateValue').val();
                    		        var userIdHvalue = $('#userIdHvalue').val();
                    		        var customerCodeHvalue = $('#customerCodeHvalue').val();
                    		        var phoneHvalue = $('#phoneHvalue').val();
                    		        var nickNameValue = $('#nickNameValue').val();
                    		        var loginNameValue = $('#loginNameValue').val();
                    		        var customerStateValue = $('#customerStateValue').val();
                    		        location.href="relation_device_export_by_conditions.json?random="+random
                    				+"&ystenValue="+encodeURIComponent(ystenValue)
                    				+"&deviceCodeValue="+encodeURIComponent(deviceCodeValue)
                    				+"&snoValue="+encodeURIComponent(snoValue)
									+"&customerCodeValue="+encodeURIComponent(customerCodeValue)
                    				+"&userIdValue="+encodeURIComponent(userIdValue)
                    				+"&phoneValue="+encodeURIComponent(phoneValue)
									+"&snoHvalue="+encodeURIComponent(snoHvalue)
                    				+"&deviceCodeHvalue="+encodeURIComponent(deviceCodeHvalue)
                    				+"&ystenIdHvalue="+encodeURIComponent(ystenIdHvalue)
									+"&deviceVendorValue="+encodeURIComponent(deviceVendorValue)
                    				+"&deviceTypeValue="+encodeURIComponent(deviceTypeValue)
                    				+"&areaHvalue="+encodeURIComponent(areaHvalue)
									+"&cityValue="+encodeURIComponent(cityValue)									
                    				+"&macValue="+encodeURIComponent(macValue)
                    				+"&deviceStateValue="+encodeURIComponent(deviceStateValue)                   				
                    				+"&userIdHvalue="+encodeURIComponent(userIdHvalue)                    				
                    				+"&customerCodeHvalue="+encodeURIComponent(customerCodeHvalue)                   				
                    				+"&phoneHvalue="+encodeURIComponent(phoneHvalue)									
                    				+"&nickNameValue="+encodeURIComponent(nickNameValue)
                    				+"&loginNameValue="+encodeURIComponent(loginNameValue)
                    				+"&customerStateValue="+encodeURIComponent(customerStateValue)
                    				+"&startDateValue="+encodeURIComponent(startDateValue)
        	        				+"&endDateValue="+encodeURIComponent(endDateValue)
        	        				+"&startDateAvtiveValue="+encodeURIComponent(startDateAvtiveValue)
        	        				+"&endDateAvtiveValue="+encodeURIComponent(endDateAvtiveValue);
                        			}
	                    		$("#dialog-form3").omDialog("close");  
                    	},
                    	"取消" : function() {
                        	$("#dialog-form3").omDialog("close");
                    	}
                	},onClose:function(){
                		$('#myform3').validate().resetForm();
                    	$("#dialog-form3").omDialog("close");
                    	}
            	});
        	   	dialog3.omDialog("option", "title", "用户设备信息导出");
        	   	dialog3.omDialog("open");
        	});
/*******************************************************************************************************************/  
/*
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
                	var customerCode = $("#customerCode2").val();
                    var customerUserId = $("#customerUserId2").val();
                    var deviceCode = $("#deviceCode2").val();
                    var deviceSno = $("#deviceSno2").val();               	 
                	location.href="relation_customer_list_export.json?random="+random
     				+'&customerCode='+encodeURIComponent(customerCode)
        			+'&customerUserId='+encodeURIComponent(customerUserId)
        			+'&deviceCode='+encodeURIComponent(deviceCode)
        			+'&deviceSno='+encodeURIComponent(deviceSno); 
                     $("#dialog-form").omDialog("close"); 
                     //showDiv();
                     return false; 
                },
                "取消" : function() {
                    $("#dialog-form").omDialog("close");
                }
            },onClose:function(){}
        });
        dialog.omDialog("option", "title", "终端用户关系信息列表导出");
        dialog.omDialog("open");
    });
 */
});

</script>
</head>
<body>
<div id="center-tab">
    <ul>
        <li><a href="#tab1">用户终端关系信息列表:</a></li>
    </ul>
</div>
<!--
	<table>
			<tr>
			
			<c:if test="${sessionScope.customer_relation_remote_device != null }">
				<td><div id="remoteDevice"/></td>
			</c:if>
			  
		</tr>
	</table>-->
	<table >
		<tr>
			<td style="width: 60px; text-align: left;">用户编号：</td>
			<td><input type="text" name="customerCode1" id="customerCode1" style="width:120px;height: 20px;border:1px solid #86A3C4;"/></td>
			<td style="width: 85px; text-align: left;">用户外部编号：</td>
			<td><input type="text" name="customerUserId1" id="customerUserId1" style="width:120px;height: 20px;border:1px solid #86A3C4;"/></td>
			<td style="width: 50px; text-align: left;">手机号：</td>
			<td><input type="text" name="customerPhone1" id="customerPhone1" style="width:120px;height: 20px;border:1px solid #86A3C4;"/></td>	
			<td style="width: 75px; text-align: left;">设备编号：</td>
			<td><input type="text" name="ystenId1" id="ystenId1" style="width:120px;height: 20px;border:1px solid #86A3C4;"/></td>
			<td>设备序列号：</td>
			<td><input type="text" name="deviceSno1" id="deviceSno1" style="width:120px;height: 20px;border:1px solid #86A3C4;"/></td>
			<td><div id="query"/></td>			
		</tr>
	</table>
	<table>
		<tr>
			<td>
				<!-- <div id="bulkQuery"></div> -->
				<div id="heightQuery"></div>
			</td>
			<td>
				<c:if test="${sessionScope.customer_relation_export != null }">
					<td><div id="export"/></td>
				</c:if>	
			</td>
			</tr>
	</table>
	<table id="grid" ></table>
	<div id="dialog-form" style="display: none;">
		<form id="myform">
		<table>
			<tr>
			<td>用户编号：</td>
			<td><input type="text" name="customerCode2" id="customerCode2" style="width:130px;height: 20px;border:1px solid #86A3C4;"/></td>
			</tr>
			<tr>
			<td>用户外部编号：</td>
			<td><input type="text" name="customerUserId2" id="customerUserId2" style="width:130px;height: 20px;border:1px solid #86A3C4;"/></td>
			</tr>
			<tr>
			<td>设备序列号：</td>
			<td><input type="text" name="deviceSno2" id="deviceSno2" style="width:130px;height: 20px;border:1px solid #86A3C4;"/></td>			
			</tr>
		</table>
			
		</form>
	</div>	
	<div id="dialog-form1" style="display: none;">
		<form id="myform1">
		 <table>
		   <tr>
					<td style="width: 100px; text-align: right;">设备创建时间：</td>
					<td><input type="text" name="startDateExport"
						id="startDateExport" style="width: 160px;" /></td>
					<td>至<input type="text" name="endDateExport"
						id="endDateExport" style="width: 160px;" />
					</td>
				</tr>
				<tr>
					<td style="width: 100px; text-align: right;">设备激活时间：</td>
					<td><input type="text" name="startDateAvtive"
						id="startDateAvtive" style="width: 160px;" /></td>
					<td>至<input type="text" name="endDateAvtive"
						id="endDateAvtive" style="width: 160px;" />
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
				<tr id="openSno" style="display: none;">
	               <td style="width: 100px; text-align: right;">设备序列号：</td>
				   <td><textarea id="sno2" name="sno2" cols="70" rows="15" style="border:1px solid #86A3C4;"></textarea></td>
	               <td><span>请输入设备序列号，如有多个请用英文逗号分隔</span></td>
               </tr>
		   </table>
		   <table>
				<tr>
					<td style="width: 100px; text-align: right;">设备厂商：</td>
					<td style="text-align: left;"><input name="deviceVendor2"
						id="deviceVendor2" onchange="getDeviceType();" /></td>
					<td style="width: 100px; text-align: right;">设备型号：</td>
					<td style="text-align: left;"><input name="deviceType2"
						id="deviceType2" /></td>
				</tr>
				<tr>
					<td style="width: 100px; text-align: right;">所属区域：</td>
					<td style="text-align: left;"><input name="areaId2"
						id="areaId2" /></td>
					<td style="width: 100px;text-align: right;">所属地市：</td>
					<td><input id="cityId2" name="cityId2"/></td>
				</tr>
				<tr>	
					<td style="width: 100px; text-align: right;">MAC地址：</td>
					<td><input type="text" name="mac2" id="mac2"
						style="width:180px;height: 20px;border:1px solid #86A3C4;" /></td>
						<td style="width: 100px; text-align: right;">设备状态：</td>
					<td style="text-align: left;"><input name="deviceState2"
						id="deviceState2" /></td>
				</tr>
				<tr>		
					<td style="width: 100px;text-align: right;">外部编号：</td>
					<td><input id="userId2" name="userId2" style="width:180px;height: 20px;border:1px solid #86A3C4;"/></td>
					<td style="width: 100px;text-align: right;">用户编号：</td>
					<td><input id="customerCode2" name="customerCode2"  style="width:180px;height: 20px;border:1px solid #86A3C4;"/></td>	
				</tr>
				<tr>	
					<td style="width: 100px;text-align: right;">用户状态：</td>
					<td><input id="customerState2" name="customerState2"/></td>
					<td style="width: 100px;text-align: right;">用户电话：</td>
					<td><input id="phone2" name="phone2"  style="width:180px;height: 20px;border:1px solid #86A3C4;"/></td>	
				</tr>
				<tr>	
					<td style="width: 100px;text-align: right;">用户昵称：</td>
					<td><input id="nickName2" name="nickName2"  style="width:180px;height: 20px;border:1px solid #86A3C4;"/></td>
					<td style="width: 100px;text-align: right;">登录名称：</td>
					<td><input id="loginName2" name="loginName2"  style="width:180px;height: 20px;border:1px solid #86A3C4;"/></td>		
				</tr>				
			</table>
		</form>
	</div>
	<div id="dialog-form3" style="display: none;">
		<form id="myform3">
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
	<div id="dialog-form7" style="display: none;">
		<form id="myform7">
			<table>
			   <tr>
               <td style="width: 100px; text-align: right;">设备序列号：</td>
			   <td><textarea id="snos" name="snos" cols="70" rows="15" style="border:1px solid #86A3C4;"></textarea></td>
               <td><span>请输入设备序列号，如有多个请用英文逗号分隔</span></td>
               </tr>				 
			</table>
		</form>
	</div>
	<input type="hidden" id="ystenValue" name="ystenValue"/>
	<input type="hidden" id="deviceCodeValue" name="deviceCodeValue"/>	
	<input type="hidden" id="snoValue" name="snoValue"/>
	<input type="hidden" id="customerCodeValue" name="customerCodeValue"/>
	<input type="hidden" id="userIdValue" name="userIdValue"/>
	<input type="hidden" id="phoneValue" name="phoneValue"/>
	
	<input type="hidden" id="startDateValue" name="startDateValue"/>
	<input type="hidden" id="endDateValue" name="endDateValue"/>
	<input type="hidden" id="startDateAvtiveValue" name="startDateAvtiveValue"/>
	<input type="hidden" id="endDateAvtiveValue" name="endDateAvtiveValue"/>
	<input type="hidden" id="snoHvalue" name="snoHvalue"/>
	<input type="hidden" id="deviceCodeHvalue" name="deviceCodeHvalue"/>
	<input type="hidden" id="ystenIdHvalue" name="ystenIdHvalue"/>
	<input type="hidden" id="deviceVendorValue" name="deviceVendorValue"/>	
	<input type="hidden" id="deviceTypeValue" name="deviceTypeValue"/>
	<input type="hidden" id="areaHvalue" name="areaHvalue"/>
	<input type="hidden" id="cityValue" name="cityValue"/>
	<input type="hidden" id="macValue" name="macValue"/>
	<input type="hidden" id="deviceStateValue" name="deviceStateValue"/>	
	<input type="hidden" id="userIdHvalue" name="userIdHvalue"/>
	<input type="hidden" id="customerCodeHvalue" name="customerCodeHvalue"/>	
	<input type="hidden" id="phoneHvalue" name="phoneHvalue"/>
	<input type="hidden" id="nickNameValue" name="nickNameValue"/>
	<input type="hidden" id="loginNameValue" name="loginNameValue"/>
	<input type="hidden" id="customerStateValue" name="customerStateValue"/>
</body>
</html>
