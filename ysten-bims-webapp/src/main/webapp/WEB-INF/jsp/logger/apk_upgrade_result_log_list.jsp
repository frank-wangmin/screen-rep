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
    	dataSource : 'apk_upgrade_result_log_list.json',
    	width : '100%',
        height : rFrameHeight,
        singleSelect : false,
        limit : limit,
        colModel : [ 
                     {header : '<b>终端编号</b>', name : 'deviceCode', align : 'center', width : 100},
                     {header : '<b>设备编号</b>', name : 'ystenId', align : 'center', width : 200},
                     {header : '<b>区域</b>', name : 'distCode',align : 'center',width: 50},
                     {header : '<b>软件信息号</b>', name : 'softCode', align : 'center', width: 'autoExpand'},  
                     {header : '<b>apk旧版本号</b>', name : 'deviceVersionSeq', align : 'center', width : 70},
                     {header : '<b>apk新版本号</b>', name : 'versionSeq', align : 'center', width : 70},
                     {header : '<b>创建时间</b>', name : 'createDate',align : 'center',width: 120},
                     {header : '<b>状态</b>', name : 'status',align : 'center',width: 70,renderer:function(value){ 
                      	if(value == "SUCCESS"){
                           	return "成功";
                           }else if(value == "FAILED"){
                           	return "失败";
                           }else if(value == "UNDFEFINED"){
                              	return "未定义";
                           }else if(value==null || value==""){
                                return "";
                           }else return "";
                      }},
                     {header : '<b>持续时间</b>', name : 'duration', align : 'center', width :80},
		],
    	rowDetailsProvider:function(rowData,rowIndex){
        	return '第'+rowIndex+'行'
        		+'</br>软件信息号='+rowData.softCode
        		+'</br>apk旧版本号='+rowData.deviceVersionSeq
        		+', apk新版本号='+rowData.versionSeq
        		+', 区域='+rowData.distCode
        		+', 创建时间='+rowData.createDate
        		+', 状态='+rowData.status
        		+', 描述='+rowData.duration;
    	}
    });
    $('#query').omButtonbar({btns : [{label:"查询",icons : {left : opPath+'search.png'}}]});
    $('#heightQuery').omButtonbar({btns : [{label:"高级查询",icons : {left : opPath+'search.png'}}]});
    $('#export').omButtonbar({btns : [{label:"导出",icons : {left : opPath+'op-btn-icon.png'}}]});
    $('#center-tab').omTabs({height:"33",border:false});
    
    $('#query').bind('click', function(e) {
    	var deviceCode = filterStartEndSpaceTrim($('#deviceCode').val());
    	var ystenId = filterStartEndSpaceTrim($('#ystenId').val());
        $('#grid').omGrid("setData", 'apk_upgrade_result_log_list.json?deviceCode='+encodeURI(deviceCode)+'&ystenId='+encodeURI(ystenId));
        $('#ystenValue').val(ystenId);
       	$('#deviceCodeValue').val(deviceCode);
       	$('#stateValue').val("");
       	$('#softCodevalue').val("");
       	$('#deviceCodeHvalue').val("");
       	$('#ystenIdHvalue').val("");
       	$('#startDateValue').val("");
       	$('#endDateValue').val("");
       	$('#distCodeValue').val("");
       	$('#deviceVersionSeqValue').val("");
       	$('#versionSeqValue').val("");
    });
    
    $('#heightQuery').bind('click', function(e) {
		 $('#bulkSelType').omCombo({dataSource : [
		                                           {text:'设备编号',value:'1'}
		                                       ],
		                                           value:'0',
		                                           editable:false,
		                                           width:180,
		                                           listMaxHeight:160,
		                                           onValueChange : function(target, newValue){
		                                               if(newValue == 0){
		                                            	   $("#openDeviceCode").show();
		                                                   $("#openYstenId").hide();
		                                                   $('#ystenId2').val("");
		                                               }
													   if(newValue == 1){
		                                            	   $("#openDeviceCode").hide();
		                                                   $("#openYstenId").show();
		                                           	 	   $('#deviceCode2').val("");
		                                               }
		                                       }
		 });
		 
		$("#startDateExport").omCalendar();
	 	$("#endDateExport").omCalendar();
	 	$('#state2').omCombo({
	         dataSource : [ 
					       {text : '全部', value : ''},
	                       {text : '成功', value : 'SUCCESS'}, 
	                        {text : '失败', value : 'FAILED'}
	                       ],value:'',width:180
	     });
	 	$('#distCode2').omCombo({dataSource : 'cityList_distCode.json?par=0',editable:false,listMaxHeight:150,width:180,value:''});
  	var dialog1 = $("#dialog-form1").omDialog({
  		width: 800,
      	height: 450,
      	autoOpen : false,
      	modal : true,
      	resizable : false,
      	draggable : false,
      	buttons : {
          	"提交" : function(){
              			var ystenId = filterStartEndSpaceTrim($('#ystenId2').val());
                  	 	var deviceCode = filterStartEndSpaceTrim($('#deviceCode2').val());
                       	var state = $("#state2").omCombo('value');
                       	var softCode = filterStartEndSpaceTrim($('#softCode2').val());
                       	var distCode = $("#distCode2").omCombo('value');
                    	var deviceVersionSeq = filterStartEndSpaceTrim($('#deviceVersionSeq2').val());
                    	var versionSeq = filterStartEndSpaceTrim($('#versionSeq2').val());
                       	var startDate = $("#startDateExport").val();
    	                	var endDate = $("#endDateExport").val();
    	                		                	
    	                	if(startDate !=null && endDate == null){
    		                		$.omMessageBox.alert({
    	                            		type:'alert',
    	                            		title:'温馨提示',
    	                            		content:'请将创建时间填写完整！',
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
    	                	
    	                	var reg =  /^\d+$/;
            	         if(((reg.test(versionSeq) && versionSeq != "") || versionSeq == "") && ((reg.test(deviceVersionSeq) && deviceVersionSeq != "") || deviceVersionSeq == "")){
                            $('#grid').omGrid("setData", 'apk_upgrade_result_list_by_conditions.json?ystenId='+encodeURIComponent(ystenId)+'&deviceCode='+encodeURIComponent(deviceCode)+
                      		 '&state='+state+"&softCode="+encodeURIComponent(softCode)+"&startDate="+encodeURIComponent(startDate)+"&endDate="+encodeURIComponent(endDate)+
                		 		"&distCode="+distCode+
                   		 		"&deviceVersionSeq="+encodeURIComponent(deviceVersionSeq)+
                       		 	"&versionSeq="+encodeURIComponent(versionSeq));
                       	$("#dialog-form1").omDialog("close"); 
                       $('#ystenValue').val("");
                      	$('#deviceCodeValue').val("");
                      	$('#stateValue').val(state);
                      	$('#softCodevalue').val(softCode);
                      	$('#deviceCodeHvalue').val(deviceCode);
                      	$('#ystenIdHvalue').val(ystenId);
                      	$('#startDateValue').val(startDate);
                      	$('#endDateValue').val(endDate);
                      	$('#distCodeValue').val(distCode);
                       	$('#deviceVersionSeqValue').val(deviceVersionSeq);
                       	$('#versionSeqValue').val(versionSeq);
            	         }else{
	                		 $.omMessageBox.alert({
	              	    		type:'alert',
	              	    		title:'温馨提示',
	              	    		content:'请正确填写设备版本旧号和设备新版本号,为正整数！',
	              			});
    	                	}
          	},
          	"取消" : function() {
              	$("#dialog-form1").omDialog("close");
          	}
      	},onClose:function(){$('#myform1').validate().resetForm();}
  	});
  	dialog1.omDialog("option", "title", "APK升级信息高级查询");
  	dialog1.omDialog("open");
	});
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
                        		location.href="apk_upgrade_log_export_byIds.json?ids="+encodeURIComponent(toDeleteRecordID.toString());
                			}
                    		if($('#exportType').omCombo('value') == 1){
                				var random = Math.random();
                				var ystenValue = $('#ystenValue').val();
                            	var deviceCodeValue = $('#deviceCodeValue').val();
                            	var distCodeValue = $('#distCodeValue').val();
                            	var deviceVersionSeqValue = $('#deviceVersionSeqValue').val();
                            	var versionSeqValue = $('#versionSeqValue').val();
                            	var stateValue = $('#stateValue').val();
                            	var softCodevalue = $('#softCodevalue').val();
        	                	var deviceCodeHvalue = $('#deviceCodeHvalue').val();
                            	var ystenIdHvalue = $("#ystenIdHvalue").val();
                            	var startDateValue = $("#startDateValue").val();
        	                	var endDateValue = $("#endDateValue").val();
        	                	location.href="apk_upgrade_log_export_by_query.json?random="+random
                				+"&ystenValue="+encodeURIComponent(ystenValue)
                				+"&deviceCodeValue="+encodeURIComponent(deviceCodeValue)
                				+"&distCodeValue="+encodeURIComponent(distCodeValue)
                				+"&deviceVersionSeqValue="+encodeURIComponent(deviceVersionSeqValue)
                				+"&versionSeqValue="+encodeURIComponent(versionSeqValue)
                				+"&stateValue="+encodeURIComponent(stateValue)
                				+"&softCodevalue="+encodeURIComponent(softCodevalue)
                				+"&deviceCodeHvalue="+encodeURIComponent(deviceCodeHvalue)
                				+"&ystenIdHvalue="+encodeURIComponent(ystenIdHvalue)
                				+"&startDateValue="+encodeURIComponent(startDateValue)
    	        				+"&endDateValue="+encodeURIComponent(endDateValue);
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
    	   	dialog2.omDialog("option", "title", "APK升级结果日志信息导出");
    	   	dialog2.omDialog("open");
    	});
	/**********************************************导出结束*************************************************/
});

</script>
</head>
<body>
<div id="center-tab">
<ul>
	 <li><a href="#tab1">APK升级日志信息列表:</a></li>
</ul>
</div>
	<table >
        <tbody>
            <tr>
        		<td>&nbsp;设备编号：</td>
        		<td><input type="text" name="ystenId" id="ystenId" style="width:150px;height: 20px;border:1px solid #86A3C4;"/></td>
                <td><div id="query"/> </td>
                <td><div id="heightQuery"></div></td>
                <c:if test="${sessionScope.export_apk_upgrade_result_log != null }">
				<td><div id="export"></div></td>
				</c:if>
             </tr>
           </tbody>
        </table>
	<table id="grid" ></table>
	<div id="dialog-form"><form id="myForm"><table></table></form></div>
	<div id="dialog-form1" style="display: none;">
		<form id="myform1">
		 <table>
		   		<tr>
					<td style="width: 100px; text-align: right;">创建时间：</td>
					<td><input type="text" name="startDateExport"
						id="startDateExport" style="width: 160px;" /></td>
					<td>至<input type="text" name="endDateExport"
							id="endDateExport" style="width: 160px;"/>
					</td>
				</tr>
		   </table>
			<table>
				<tr>
	               <td style="width: 100px; text-align: right;">批量查询字段名：</td>
				   <td><input name="bulkSelType" id="bulkSelType"/></td>
	               <td></td>
	            </tr>
				<tr id="openYstenId" style="display: none;">
	               <td style="width: 100px; text-align: right;">设备编号：</td>
				   <td><textarea id="ystenId2" name="ystenId2" cols="70" rows="15" style="border:1px solid #86A3C4;"></textarea></td>
	               <td><span>请输入易视腾编号，如有多个请用英文逗号分隔</span></td>
               </tr>
		   </table>
		   <table>
				<tr>
					<td style="width: 100px; text-align: right;">软件信息号：</td>
					<td><input type="text" name="softCode2" id="softCode2"
						style="width: 180px; height: 20px; border: 1px solid #86A3C4;" /></td>
										
					<td style="width: 100px; text-align: right;">所属区域：</td>
					<td><input name="distCode2" id="distCode2"/></td>
				</tr>
				<tr>
					<td style="width: 100px; text-align: right;">设备旧版本号：</td>
					<td><input type="text" name="deviceVersionSeq2" id="deviceVersionSeq2"
						style="width: 180px; height: 20px; border: 1px solid #86A3C4;" /></td>
						
					<td style="width: 70px; text-align: right;">状态：</td>
					<td style="text-align: left;"><input name="state2"
						id="state2" /></td>
				</tr>
				<tr>
					<td style="width: 70px; text-align: right;">设备新版本号：</td>
					<td style="text-align: left;"><input type="text" name="versionSeq2" id="versionSeq2"
						style="width: 180px; height: 20px; border: 1px solid #86A3C4;" /></td>
										
					<td></td>
					<td></td>
				</tr>
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
	<input type="hidden" id="ystenValue" name="ystenValue"/>
	<input type="hidden" id="deviceCodeValue" name="deviceCodeValue"/>
	<input type="hidden" id="distCodeValue" name="distCodeValue"/>
	<input type="hidden" id="deviceVersionSeqValue" name="deviceVersionSeqValue"/>
	<input type="hidden" id="versionSeqValue" name="versionSeqValue"/>
	<input type="hidden" id="stateValue" name="stateValue"/>
	<input type="hidden" id="softCodevalue" name="softCodevalue"/>
	<input type="hidden" id="deviceCodeHvalue" name="deviceCodeHvalue"/>
	<input type="hidden" id="ystenIdHvalue" name="ystenIdHvalue"/>
	<input type="hidden" id="startDateValue" name="startDateValue"/>
	<input type="hidden" id="endDateValue" name="endDateValue"/>
</body>
</html>