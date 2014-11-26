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
    $('#grid').omGrid({
    	dataSource : 'can_bind_customer_list.json',
    	width : '100%',
        height : rFrameHeight,
        singleSelect : false,
        limit : limit,
        colModel : [ 
                     {header : '<b>用户编号</b>', name : 'code', align : 'center', width : 150}, 
                     {header : '<b>用户外部编号</b>', name : 'userId', align : 'center', width : 120}, 
                     {header : '<b>登录名</b>', name : 'loginName', align : 'center', width : 120},
                     {header : '<b>真实姓名</b>', name : 'realName', align : 'center', width : 120},
                     {header : '<b>用户昵称</b>', name : 'nickName', align : 'center', width : 120},
                     {header : '<b>用户类型</b>', name : 'customerType', align : 'center', width : 60},
                     {header : '<b>用户状态</b>', name : 'state', align : 'center', width : 60},
                     {header : '<b>是否锁定</b>', name : 'isLock', align : 'center', width : 60},
                     {header : '<b>客户编号</b>', name : 'code', align : 'center', width : 120},
                     {header : '<b>所属区域</b>', name : 'area',align : 'center',width: 50, renderer:function(value, row, index, options){
						var text = "";
						$.each(options.area, function(i, item){
							if(item.value == value.id){
								text =  item.text;
							}
						})
						return text;
					}}, 
                     {header : '<b>所属地市</b>', name : 'region', align : 'center', width : 50, renderer:function(value, row, index, options){
						var text = value;
						$.each(options.city, function(i, item){
							if(item.value == value.id){
								text =  item.text;
							}
						})
						return text;
					}}, 
                     {header : '<b>创建时间</b>', name : 'createDate', align : 'center', width : 120},
                     {header : '<b>激活时间</b>', name : 'activateDate', align : 'center', width : 120},
                     {header : '<b>服务到期时间</b>', name : 'serviceStop', align : 'center', width : 120},
                     {header : '<b>用户销户时间</b>', name : 'cancelledDate', align : 'center', width : 120},
                     {header : '<b>更新时间</b>', name : 'updateTime', align : 'center', width : 120},
                     {header : '<b>状态变更时间</b>', name : 'stateChangeDate', align : 'center', width : 120},
                     {header : '<b>证件类型</b>', name : 'identityType', align : 'center', width : 60},
                     {header : '<b>证件编号</b>', name : 'identityCode', align : 'center', width : 150},
                     {header : '<b>年龄</b>', name : 'age', align : 'center', width : 50},
                     {header : '<b>性别</b>', name : 'sex', align : 'center', width : 50},
                     {header : '<b>用户来源</b>', name : 'source', align : 'center', width : 100},
                     {header : '<b>用户电话</b>', name : 'phone', align : 'center', width : 120},
                     {header : '<b>宽带速率</b>', name : 'rate', align : 'center', width : 60},
                     {header : '<b>电子邮件</b>', name : 'mail', align : 'center', width : 100},
                     {header : '<b>邮政编码</b>', name : 'zipCode', align : 'center', width : 80},
                     {header : '<b>常用地址</b>', name : 'address', align : 'center', width : 300}
		],
		extraDataSource : [{name:"area", url:"area.json"},{name:"city", url:"city.json"}]
    });
    /**
    var provinceDataSource = null;
	$.get('area.json', function(data){
		provinceDataSource = data;
	});

	 var cityDataSource = null;
		$.get('city.json', function(data){
			cityDataSource = data;
		});
	*/
	$('#query').omButtonbar({btns : [{label:"查询",icons : {left : opPath+'search.png'}}]});
    $('#heightQuery').omButtonbar({btns : [{label:"高级查询",icons : {left : opPath+'search.png'}}]});
    $('#export').omButtonbar({btns : [{label:"导出",icons : {left : opPath+'op-btn-icon.png'}}]});
    $('#bindDevice').omButtonbar({btns : [{label:"终端绑定",icons : {left : opPath+'op-edit.png'}}]});
    
    $("#deviceQuery").omButton();
	$('#customerType2').omCombo({dataSource : 'customer_type.json', editable:false, width:182, listMaxHeight:100});
	$('#region2').omCombo({dataSource : 'city.json',editable:false,listMaxHeight:150,width:182});
	$('#area2').omCombo({dataSource : 'area.json',editable:false,width:182,listMaxHeight:160});	
	$('#customerType3').omCombo({dataSource : 'customer_type.json', editable:false, width:182, listMaxHeight:100});
	$('#region3').omCombo({dataSource : 'city.json',editable:false,listMaxHeight:150,width:182});
	$('#area3').omCombo({dataSource : 'area.json',editable:false,width:182,listMaxHeight:160});	
    
    $('#query').bind('click', function() {
        var userId = $('#userId1').val();
        var code = $('#code1').val();
        var phone = $('#phone1').val();
        $('#grid').omGrid("setData", 'can_bind_customer_list.json?bindType=UNBIND&userId='+encodeURIComponent(userId)+'&code='+encodeURIComponent(code)+'&phone='+encodeURIComponent(phone));
    });        
    
    $('#heightQuery').bind('click', function(e) {
        var highQueryDialog = $("#dialog-high-query-form").omDialog({
            width: 400,
            autoOpen : false,
            modal : true,
            resizable : false,
            draggable : false,
            buttons : {
                "提交" : function(){
                	 var userId = $('#userId2').val();
                	 var code = $('#code2').val();
                	 var region = $('#region2').val();
                	 var customerType = $('#customerType2').val();
                	 var area = $('#area2').val();
                	 var nickName = $('#nickName2').val();
                	 var phone = $('#phone2').val();
                	 var loginName = $('#loginName2').val();
                	 
                	 var paramter = "userId="+encodeURIComponent(userId)+"&";
                	 paramter += "code="+encodeURIComponent(code)+"&";
                	 paramter += "cRegion="+encodeURIComponent(region)+"&";
                	 paramter += "customerType="+encodeURIComponent(customerType)+"&";
                	 paramter += "cArea="+encodeURIComponent(area)+"&";
                	 paramter += "nickName="+encodeURIComponent(nickName)+"&";
                	 paramter += "phone="+encodeURIComponent(phone)+"&";
                	 paramter += "loginName="+encodeURIComponent(loginName);
                     $('#grid').omGrid("setData", 'can_bind_customer_list.json?'+paramter);
                     $("#dialog-high-query-form").omDialog("close"); 
                     return false; 
                },
                "取消" : function() {
                    $("#dialog-high-query-form").omDialog("close");
                }
            },
            onClose:function(){}
        });
        highQueryDialog.omDialog("option", "title", "设备信息高级查询");
        highQueryDialog.omDialog("open");
    });   
    
    $('#deviceQuery').bind('click', function(e) {
    	var selections=$('#grid').omGrid('getSelections',true);
    	if(!selections[0].area){
        	areaId = "";
            }else{
            	areaId = selections[0].area.id;
                }
        var deviceCode = $('#deviceCode1').val();
        var mac = $('#mac1').val();
        var sno = $('#sno1').val();
        var ystenId = $('#ystenId1').val();
        $('#bind-device-grid').omGrid("setData", 'device_list.json?bindType=UNBIND&isLock=UNLOCKED&areaId='+areaId+'&ystenId='+encodeURIComponent(ystenId)+'&deviceCode='+encodeURIComponent(deviceCode)+'&mac='+encodeURIComponent(mac)+'&sno='+encodeURIComponent(sno));
    }); 
    
    $("#bindDevice").click(function(){
        var selections=$('#grid').omGrid('getSelections',true);
        if (selections.length == 0 || selections.length > 1) {
        	$.omMessageBox.alert({
                type:'alert',
                title:'温馨提示',
                content:'终端绑定操作只能选择一行记录！',
            });
            return false;
        }    	
        var singleSelectTemp = false;
        if(selections[0]['customerType']=='个人'){
        	singleSelectTemp = true;
        }
        if(!selections[0].area){
        	areaId = "";
            }else{
            	areaId = selections[0].area.id;
                }
        $('#bind-device-grid').omGrid({
        	dataSource : 'device_list.json?bindType=UNBIND&isLock=UNLOCKED&areaId='+areaId,
        	width : 980, height : '99%',
            singleSelect : singleSelectTemp,
            limit : 10,
            colModel : [ {header : '<b>设备编号</b>', name : 'ystenId', align : 'center', width : 120},
                         {header : '<b>设备序列号</b>', name : 'sno',align : 'center',width: 120},
                         {header : '<b>MAC地址</b>', name : 'mac',align : 'center',width: 120},
                         {header : '<b>设备厂商</b>', name : 'deviceVendor',align : 'center',width: 70,renderer: function(value){
                        	 if(value==null || value==""){
                                 return "";
                                 }else{
                                	 return value.name;
                                     }
                             }
                         },
                         {header : '<b>设备型号</b>', name : 'deviceType',align : 'center',width:70,renderer: function(value){
                        	 if(value==null || value==""){
                                 return "";
                                 }else{
                                	 return value.name;
                                     }
                             }
                         },
                         {header : '<b>设备状态</b>', name : 'state',align : 'center',width: 50},
                         {header : '<b>绑定状态</b>', name : 'bindType',align : 'center',width: 50},
                         {header : '<b>是否锁定</b>', name : 'isLock',align : 'center',width: 50},
                         {header : '<b>所属区域</b>', name : 'area',align : 'center',width: 50,renderer: function(value){
                        	 if(value==null || value==""){
                                 return "";
                                 }else{
                                	 return value.name;
                                     }
                             }
                         },
                         {header : '<b>所属地市</b>', name : 'city',align : 'center',width: 50,renderer: function(value){
                        	 if(value==null || value==""){
                                 return "";
                                 }else{
                                	 return value.name;
                                     }
                             }
                         },
                         {header : '<b>运营商</b>', name : 'spDefine',align : 'center',width: 80,renderer: function(value){
                        	 if(value==null || value==""){
                                 return "";
                                 }else{
                                	 return value.name;
                                     }
                             }
                         },
    		]
        });    	
        showBindDeviceDialog('终端设备信息');    	
    });
    
    $('#export').bind('click', function(e) {
        var dialog = $("#dialog-export-form").omDialog({
            width: 700,
            autoOpen : false,
            modal : true,
            resizable : false,
            draggable : false,
            buttons : {
                "提交" : function(){
                	 var random = Math.random();
                	 var userId = $('#userId3').val();
                	 var code = $('#code3').val();
                	 var region = $('#region3').val();
                	 var customerType = $('#customerType3').val();
                	 var area = $('#area3').val();
                	 var nickName = $('#nickName3').val();
                	 var phone = $('#phone3').val();
                	 var loginName = $('#loginName3').val();
                	 
                	 location.href="bind_customer_list_export.json?random="+random
     				+"&userId="+encodeURIComponent(userId)
     				+"&code="+encodeURIComponent(code)
     				+"&cRegion="+encodeURIComponent(region)
     				+"&customerType="+encodeURIComponent(customerType)
     				+"&cArea="+encodeURIComponent(area)
     				+"&nickName="+encodeURIComponent(nickName)
     				+"&phone="+encodeURIComponent(phone)
     				+"&loginName="+encodeURIComponent(loginName);
                	 
                     $("#dialog-export-form").omDialog("close"); 
                     //showDiv();
                },
                "取消" : function() {
                    $("#dialog-export-form").omDialog("close");
                }
            },
            onClose:function(){}
        });
        dialog.omDialog("option", "title", "用户信息导出");
        dialog.omDialog("open");
    });

    function showBindDeviceDialog(title){
        var bindDeviceDialog = $("#bind-device-form").omDialog({
            width:1000, height:440, autoOpen:false, resizable:false,
            draggable : false,modal : true,
            buttons : {
            	"确定" : function(){
            		var userIdSel = $('#grid').omGrid('getSelections',true);
            		var selections = $('#bind-device-grid').omGrid('getSelections',true);
            		if(userIdSel[0].customerType == '个人'){
                    	if(selections.length != 1){
                        	$.omMessageBox.alert({
                                type:'alert',
                                title:'温馨提示',
                                content:'个人用户只能绑定一个设备！',
                            });
                        	return false;
                    	}
            		}
            		var toDeleteRecordID = "";
                	for(var i=0;i<selections.length;i++){
                    	if(i != selections.length - 1){
                    		toDeleteRecordID  += selections[i].id+",";
                        }else{
                        	toDeleteRecordID  += selections[i].id;
                        }
                    }
               		submitData={
              			customerId : userIdSel[0].id,
              			deviceIds : toDeleteRecordID,
                    };            		
            		$.post('bind_device.json', submitData, function(data){
            			$('#grid').omGrid('reload');
                        if(data=='success'){
                        	$.omMessageTip.show({title: tip, content: "用户与设备绑定成功！", timeout: time,type:'success'});
                        }else{
                        	$.omMessageTip.show({title: tip, content: "用户与设备绑定失败！", timeout: time,type:'error'});
                        }            			
            		});
            		$("#bind-device-form").omDialog("close");
            	},
        		"取消" : function(){
        			$("#bind-device-form").omDialog("close");
        		}
            }
        });    	
    	bindDeviceDialog.omDialog("option", "title", title);
    	bindDeviceDialog.omDialog("open");    	
    }
});
	
</script>
</head>
<body>
	<table >
		<tr>
			<c:if test="${sessionScope.customer_bind_device != null }">
				<td><div id="bindDevice"/></td>
			</c:if>
			<c:if test="${sessionScope.customer_bind_export != null }">
				<td><div id="export"/></td>
			</c:if>
			<td>用户外部编号：</td>
			<td>
				<input type="text" name="userId1" id="userId1" style="width:130px;height: 20px;border:1px solid #86A3C4;"/>
			</td>
			<td>用户编号：</td>
			<td>
				<input type="text" name="code1" id="code1" style="width:130px;height: 20px;border:1px solid #86A3C4;"/>
			</td>
			<td>手机号码：</td>
			<td>
				<input type="text" name="phone1" id="phone1" style="width:130px;height: 20px;border:1px solid #86A3C4;"/>
			</td>
			<td><div id="query"/></td>
			<td><div id="heightQuery"/></td>			
		</tr>
	</table>
	<table id="grid" ></table>
	
	<div id="device-div" style="display: none;">
		<form id="bind-device-form">
			<tr>
				<td>设备编号：</td>
				<td><input type="text" name="ystenId1" id="ystenId1"
				style="width: 130px; height: 20px; border: 1px solid #86A3C4;" /></td>
				<td>设备序列号：</td>
				<td><input name="sno1" id="sno1" style="width:130px;height: 20px;border:1px solid #86A3C4;"/></td>
				<td>MAC地址：</td>
				<td><input type="text" name="mac1" id="mac1" style="width:130px;height: 20px;border:1px solid #86A3C4;"/></td>
				<td><input id="deviceQuery" type="button" value="查询"/>&nbsp;</td>
            </tr>
			<table id="bind-device-grid" ></table>
		</form>
	</div>		
	
	<div id="dialog-high-query-form" style="display:none;">
		<form id="high-query-form">
			<table>
				<tr>
					<td style="width: 100px;text-align: right;">外部编号：</td>
					<td><input id="userId2" name="userId2" style="width:180px;height: 20px;border:1px solid #86A3C4;"/></td>
				</tr>
				<tr>
					<td style="width: 100px;text-align: right;">用户编号：</td>
					<td><input id="code2" name="code2"  style="width:180px;height: 20px;border:1px solid #86A3C4;"/></td>					
				</tr>
				<tr>
					<td style="width: 100px;text-align: right;">所属地市：</td>
					<td><input id="region2" name="region2"/></td>
				</tr>
				<tr>
					<td style="width: 100px;text-align: right;">用户类型：</td>
					<td><input id="customerType2" name="customerType2"/></td>
				</tr>		
				<tr>
					<td style="width: 100px;text-align: right;">所属区域：</td>
					<td><input id="area2" name="area2"/></td>	
				</tr>
				<tr>
					<td style="width: 100px;text-align: right;">用户昵称：</td>
					<td><input id="nickName2" name="nickName2"  style="width:180px;height: 20px;border:1px solid #86A3C4;"/></td>
				</tr>
				<tr>
					<td style="width: 100px;text-align: right;">用户电话：</td>
					<td><input id="phone2" name="phone2"  style="width:180px;height: 20px;border:1px solid #86A3C4;"/></td>					
				</tr>
				<tr>
					<td style="width: 100px;text-align: right;">登录名称：</td>
					<td><input id="loginName2" name="loginName2"  style="width:180px;height: 20px;border:1px solid #86A3C4;"/></td>		
				</tr>				
			</table>
		</form>
	</div>
	<div id="dialog-export-form" style="display:none;">
		<form id="export-form">
			<table>
				<tr>
					<td style="width: 100px;text-align: right;">外部编号：</td>
					<td><input id="userId3" name="userId3" style="width:180px;height: 20px;border:1px solid #86A3C4;"/></td>
					<td style="width: 100px;text-align: right;">用户编号：</td>
					<td><input id="code3" name="code3"  style="width:180px;height: 20px;border:1px solid #86A3C4;"/></td>					
				</tr>
				<tr>
					<td style="width: 100px;text-align: right;">所属地市：</td>
					<td><input id="region3" name="region3"/></td>
					<td style="width: 100px;text-align: right;">用户类型：</td>
					<td><input id="customerType3" name="customerType3"/></td>
				</tr>		
				<tr>
					<td style="width: 100px;text-align: right;">所属区域：</td>
					<td><input id="area3" name="area3"/></td>	
					<td style="width: 100px;text-align: right;">用户昵称：</td>
					<td><input id="nickName3" name="nickName3"  style="width:180px;height: 20px;border:1px solid #86A3C4;"/></td>
				</tr>
				<tr>
					<td style="width: 100px;text-align: right;">用户电话：</td>
					<td><input id="phone3" name="phone3"  style="width:180px;height: 20px;border:1px solid #86A3C4;"/></td>					
					<td style="width: 100px;text-align: right;">登录名称：</td>
					<td><input id="loginName3" name="loginName3"  style="width:180px;height: 20px;border:1px solid #86A3C4;"/></td>		
				</tr>				
			</table>
		</form>
	</div>
</body>
</html>
