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
<style type="text/css">
	.file-box{ position:relative;width:340px}
	.txt{ height:22px; border:1px solid #cdcdcd; width:180px;}
	.btn{ background-color:#FFF; border:1px solid #CDCDCD;height:24px; width:70px;}
	.file{ position:absolute; top:0; right:80px; height:24px; filter:alpha(opacity:0);opacity: 0;width:260px } 
</style>
<script type="text/javascript"><!--
$(function(){
	$('#center-tab').omTabs({height: "33", border: false});
	var method = "";
    $('#grid').omGrid({
    	dataSource : 'customer_list.json',
    	width : '100%',
        height : rFrameHeight-20,
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
                     //{header : '<b>同步状态</b>', name : 'isSync', align : 'center', width : 60},
                     {header : '<b>客户编号</b>', name : 'custId', align : 'center', width : 120},
                     {header : '<b>外部用户编号</b>', name : 'outerCode', align : 'center', width : 150},
                     {header : '<b>所属区域</b>', name : 'area',align : 'center',width: 50, renderer:function(value){
                    	 if(value==null || value==""){
                             return "";
                             }else{
                            	 return value.name;
                                 }
                         }
                     },
                     {header : '<b>所属地市</b>', name : 'region', align : 'center', width : 50, renderer:function(value){
                    	 if(value==null || value==""){
                             return "";
                             }else{
                            	 return value.name;
                                 }
                         }
                     },
                     {header : '<b>用户分组</b>', name : 'groups',align : 'center',width: 200},
                     {header : '<b>创建时间</b>', name : 'createDate', align : 'center', width : 120},
                     {header : '<b>激活时间</b>', name : 'activateDate', align : 'center', width : 120},
                     {header : '<b>服务到期时间</b>', name : 'serviceStop', align : 'center', width : 120},
                     {header : '<b>用户销户时间</b>', name : 'cancelledDate', align : 'center', width : 120},
                     {header : '<b>更新时间</b>', name : 'updateTime', align : 'center', width : 120},
                     {header : '<b>状态变更时间</b>', name : 'stateChangeDate', align : 'center', width : 120},
                     {header : '<b>证件类型</b>', name : 'identityType', align : 'center', width : 60},
                     {header : '<b>证件编号</b>', name : 'identityCode', align : 'center', width : 150},
                     {header : '<b>年龄</b>', name : 'age', align : 'center', width : 40},
                     {header : '<b>性别</b>', name : 'sex', align : 'center', width : 40},
                     {header : '<b>用户来源</b>', name : 'source', align : 'center', width : 80,renderer:function(value, row, index, options){
   						var text = "";
  						$.each(options.source, function(i, item){
  							if(item.value == value){
  								text =  item.text;
  							}
  						});
  						return text;
  					}},
                     {header : '<b>用户电话</b>', name : 'phone', align : 'center', width : 100},
                     {header : '<b>宽带速率</b>', name : 'rate', align : 'center', width : 60},
                     {header : '<b>电子邮件</b>', name : 'mail', align : 'center', width : 100},
                     {header : '<b>邮政编码</b>', name : 'zipCode', align : 'center', width : 80},
                     {header : '<b>常用地址</b>', name : 'address', align : 'center', width : 400}
		],
		extraDataSource : [{name:"source", url:"source.json"}],
		rowDetailsProvider:function(rowData,rowIndex){
			var info = "";
			if(rowData.code!= null && rowData.code!= ""){
				info += ", 用户编号="+rowData.code;
				}
			if(rowData.userId != null && rowData.userId != ""){
				info += ", 用户外部编号="+rowData.userId;
				}
			if(rowData.loginName != null && rowData.loginName != ""){
				info += ", 登录名="+rowData.loginName;
				}
			if(rowData.realName != null && rowData.realName != ""){
				info += ", 真实姓名="+rowData.realName;
				}
			if(rowData.nickName != null && rowData.nickName != ""){
				info += ", 用户昵称="+rowData.nickName;
				}
			if(rowData.customerType != null && rowData.customerType != ""){
				info += ", 用户类型="+rowData.customerType;
				}
			if(rowData.state!= null && rowData.state!= ""){
				info += ", 用户状态="+rowData.state;
				}
			if(rowData.isLock!= null && rowData.isLock!= ""){
				info += "</br>"+"是否锁定="+rowData.isLock;
				}
			if(rowData.custId != null){
				info += ", 客户编号="+rowData.custId;
				}
			if(rowData.area.name != null && rowData.area.name != ""){
				info += ", 所属区域="+rowData.area.name;
				}
			if(rowData.region.name != null && rowData.region.name !="" ){
				info += ", 所属地市="+rowData.region.name;
				}
			if(rowData.createDate != null && rowData.createDate != ""){
				info += ", 创建时间="+rowData.createDate;
				}
			if(rowData.activateDate != null && rowData.activateDate != ""){
					info += ", 激活时间="+rowData.activateDate;
					}
			if(rowData.serviceStop != null && rowData.serviceStop !=""){				
					info += ", 服务到期时间="+rowData.serviceStop;
					}
			if(rowData.cancelledDate != null  && rowData.cancelledDate != ""){				
				info += ", 销户时间="+rowData.cancelledDate;
				}
			if(rowData.updateTime != null && rowData.updateTime != ""){
				info += ", 更新时间="+rowData.updateTime;
				}
			if(rowData.stateChangeDate != null && rowData.stateChangeDate !=""){				
				info += ", 状态变更时间="+rowData.stateChangeDate;
				}
			if(rowData.identityType != null && rowData.identityType !=""){
				info += ", 证件类型="+rowData.identityType;
				}
			
			if(rowData.stateChangeDate != null && rowData.stateChangeDate != ""){				
				info += ", 状态变更时间="+rowData.stateChangeDate;
				}
			if(rowData.identityType != null && rowData.identityType != ""){
				info += ", 证件类型="+rowData.identityType;
				}
			if(rowData.identityCode != null && rowData.identityCode !=""){
				info += ", 证件编号="+rowData.identityCode;
				}
			if(rowData.age != null && rowData.age != ""){
				info += ", 年龄="+rowData.age;
				}
			if(rowData.sex != null && rowData.sex != ""){
				info += ", 性别="+rowData.sex;
				}
			if(rowData.source != null && rowData.source != ""){
				info += ", 用户来源="+rowData.source;
				}
			if(rowData.phone != null && rowData.phone != ""){
				info += ", 电话号码="+rowData.phone;
				}
			if(rowData.rate != null && rowData.rate != ""){
				info += "</br>宽带速率="+rowData.rate;
				}
			if(rowData.mail != null && rowData.mail != ""){
				info +=  ", 电子邮件="+rowData.mail;
				}
			if(rowData.address != null && rowData.address != ""){
				info += ",常用地址="+rowData.address;
				}
			
            return '第'+rowIndex+'行, 主键ID='+rowData.id+info;
        }
    });
    
    $('#add').omButtonbar({btns : [{label:"新增",icons : {left : opPath+'add.png'}}]});
    $('#update').omButtonbar({btns : [{label:"修改",icons : {left : opPath+'op-edit.png'}}]});
    $('#query').omButtonbar({btns : [{label:"查询",icons : {left : opPath+'search.png'}}]});
    $('#heightQuery').omButtonbar({btns : [{label:"高级查询",icons : {left : opPath+'search.png'}}]});
    $('#delete').omButtonbar({btns : [{label:"删除",icons : {left : opPath+'remove.png'}}]});
    $('#import').omButtonbar({btns : [{label:"导入",icons : {left : opPath+'op-btn-icon.png'}}]});
    $('#export').omButtonbar({btns : [{label:"导出",icons : {left : opPath+'op-btn-icon.png'}}]});
    $('#export1').omButtonbar({btns : [{label:"选中导出",icons : {left : opPath+'op-btn-icon.png'}}]});
    $('#lock').omButtonbar({btns : [{label:"锁定",icons : {left : opPath+'op-edit.png'}}]});
    $('#unlock').omButtonbar({btns : [{label:"解锁",icons : {left : opPath+'op-edit.png'}}]});
    $('#temp').omButtonbar({btns : [{label:"下载导入模板",icons : {left : opPath+'icon-help.png'}}]});
    $('#lookpassword').omButtonbar({btns : [{label:"查看密码",icons : {left : opPath+'op-edit.png'}}]});
    $('#resetPwd').omButtonbar({btns : [{label:"密码重置",icons : {left : opPath+'op-edit.png'}}]});
    $('#bindGroup').omButtonbar({btns : [{label:"绑定分组",icons : {left : opPath+'op-edit.png'}}]});
    $('#sync').omButtonbar({btns : [{label:"用户同步",icons : {left : opPath+'op-btn-icon.png'}}]});
    $('#unbindGroup').omButtonbar({btns : [{label:"解绑分组",icons : {left : opPath+'remove.png'}}]});
    $('#unbind_business').omButtonbar({btns : [{label:"解绑业务",icons : {left : opPath+'remove.png'}}]});
    $('#relate_business').omButtonbar({btns : [{label:"关联业务",icons : {left : opPath+'op-edit.png'}}]});
    
    //$('#userGroupId4').omCombo({dataSource : 'get_user_group_list.json',editable:false,width:180,multi : false });
/*	$('#userGroupId4').omCombo({dataSource : 'user_group_list_by_type.json?type=NOTICE',editable:false,width:182,multi : false });
	$('#userGroupId5').omCombo({dataSource : 'user_group_list_by_type.json?type=ANIMATION',editable:false,width:182,multi : false });
	$('#userGroupId6').omCombo({dataSource : 'user_group_list_by_type.json?type=PANEL',editable:false,width:182,multi : false });
	$('#userGroupId7').omCombo({dataSource : 'user_group_list_by_type.json?type=UPGRADE',editable:false,width:182,multi : false });
	$('#userGroupId8').omCombo({dataSource : 'user_group_list_by_type.json?type=APPUPGRADE',editable:false,width:182,multi : false });
	$('#userGroupId9').omCombo({dataSource : 'user_group_list_by_type.json?type=BACKGROUND',editable:false,width:182,multi : false });
	*/
    $("#deviceQuery").omButton();
    $("#selectDevice").omButton();
    
	$("#serviceStop").omCalendar({dateFormat : 'yy-mm-dd H:i:s',showTime : true});
//	$('#region').omCombo({dataSource : 'city.json',editable:false,listMaxHeight:150,width:182});
	$('#source').omCombo({dataSource : 'source.json',editable:false,listMaxHeight:150,width:182});

	
//	$('#customerType2').omCombo({dataSource : 'customer_type.json', editable:false, width:182, listMaxHeight:100});
	$('#customerState2').omCombo({dataSource : 'customer_state.json', editable:false, width:182, listMaxHeight:100});
	$('#region2').omCombo({dataSource : 'city.json',editable:false,listMaxHeight:150,width:182});
	$('#area2').omCombo({dataSource : 'area.json',editable:false,width:182,listMaxHeight:160,
		onValueChange : function(target, newValue, oldValue){
            $('#region2').omCombo({dataSource : 'city_by_area.json?areaId='+newValue,editable:false,listMaxHeight:150,width:182,value:''});
//            $('#region').omCombo({dataSource : 'city_by_area.json?areaId='+newValue,editable:false,listMaxHeight:150,width:180, value:''});
        }
		});
	
	$('#customerType3').omCombo({dataSource : 'customer_type.json', editable:false, width:182, listMaxHeight:100});
	$('#region3').omCombo({dataSource : 'city.json',editable:false,listMaxHeight:150,width:182});
	$('#area3').omCombo({dataSource : 'area.json',editable:false,width:182,listMaxHeight:160});

    $('#areaId1').omCombo({dataSource: 'area.json?par=0', value: '0', editable: false, width: 120, listMaxHeight: 120});

	$('#unbind_business').bind('click', function(e) {
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
            content:'解绑后该用户与业务的绑定关系将删除且无法恢复，你确定要执行该操作吗？',
            onClose:function(v){
            	if(v==true){
            		var toDeleteRecordID = "";
                	for(var i=0;i<selections.length;i++){
                    	if(i != selections.length - 1){
                    		toDeleteRecordID  += selections[i].id+",";
                        }else{
                        	toDeleteRecordID  += selections[i].id;
                        }
                    }
                	$.post('user_business_map_delete.json',{ids:toDeleteRecordID.toString()},function(result){
                        $('#grid').omGrid('reload');
                        if(result=='success'){
                        	$.omMessageTip.show({title: tip, content: "解绑业务成功！", timeout: time,type:'success'});
                        }else{
                        	$.omMessageTip.show({title: tip, content: "解绑业务失败！", timeout: time,type:'error'});
                        }
                    });
            	}
            }
        });  
    });

  	 $('#unbindGroup').bind('click', function(e) {
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
  	            content:'解绑后用户与用户分组绑定关系将删除且无法恢复，你确定要执行该操作吗？',
  	            onClose:function(v){
  	            	if(v==true){
  	            		var toDeleteRecordID = "";
  	                	for(var i=0;i<selections.length;i++){
  	                    	if(i != selections.length - 1){
  	                    		toDeleteRecordID  += selections[i].id+",";
  	                        }else{
  	                        	toDeleteRecordID  += selections[i].id;
  	                        }
  	                    }
  	                	$.post('user_userGroup_map_delete.json',{ids:toDeleteRecordID.toString()},function(result){
  	                        $('#grid').omGrid('reload');
  	                        if(result=='success'){
  	                        	$.omMessageTip.show({title: tip, content: "解绑用户分组成功！", timeout: time,type:'success'});
  	                        }else{
  	                        	$.omMessageTip.show({title: tip, content: "解绑用户分组失败！", timeout: time,type:'error'});
  	                        }
  	                    });
  	            	}
  	            }
  	        });  
  	    });
	$('#bindGroup').click(function(){
	    	var selections=$('#grid').omGrid('getSelections',true);
	        if (selections.length == 0 || selections.length > 1) {
	        	$.omMessageBox.alert({
	                type:'alert',
	                title:'温馨提示',
	                content:'绑定分组操作至少且只能选择一行记录！',
	            });
	            return false;
	        }
	        if(selections[0].isLock=='锁定'){
				$.omMessageBox.alert({
	                type:'alert',
	                title:'温馨提示',
	                content:'该条记录被锁定，请解锁后再做操作！',
	            });
				return false;
	            }
	        $.ajax({
				url : "get_user_groups.json",
				data : "code="+selections[0].code,
				type : "post",
				dataType : "json",
				success : function(result){
					if(!selections[0].area){
	                	areaId = "";
	                    }else{
	                    	areaId = selections[0].area.id;
	                        }
					if(result != null){
	                $('#userGroupId4').omCombo({dataSource : 'user_group_list_by_type.json?type=NOTICE&dynamicFlag=false&areaId='+areaId,editable:false,width:182,multi : false,value:result['NOTICE']||'' });
	                $('#userGroupId5').omCombo({dataSource : 'user_group_list_by_type.json?type=ANIMATION&dynamicFlag=false&areaId='+areaId,editable:false,width:182,multi : false,value:result['ANIMATION']||''});
	                $('#userGroupId6').omCombo({dataSource : 'user_group_list_by_type.json?type=PANEL&dynamicFlag=false&areaId='+areaId,editable:false,width:182,multi : false ,value:result['PANEL']||''});
	                $('#userGroupId7').omCombo({dataSource : 'user_group_list_by_type.json?type=UPGRADE&dynamicFlag=false&areaId='+areaId,editable:false,width:182,multi : false ,value:result['UPGRADE']||''});
	                $('#userGroupId8').omCombo({dataSource : 'user_group_list_by_type.json?type=APPUPGRADE&dynamicFlag=false&areaId='+areaId,editable:false,width:182,multi : false ,value:result['APPUPGRADE']||''});
	                $('#userGroupId9').omCombo({dataSource : 'user_group_list_by_type.json?type=BACKGROUND&dynamicFlag=false&areaId='+areaId,editable:false,width:182,multi : false ,value:result['BACKGROUND']||''});
	                }else{
	                	$('#userGroupId4').omCombo({dataSource : 'user_group_list_by_type.json?type=NOTICE&dynamicFlag=false&areaId='+areaId,editable:false,width:182,multi : false});
		                $('#userGroupId5').omCombo({dataSource : 'user_group_list_by_type.json?type=ANIMATION&dynamicFlag=false&areaId='+areaId,editable:false,width:182,multi : false});
		                $('#userGroupId6').omCombo({dataSource : 'user_group_list_by_type.json?type=PANEL&dynamicFlag=false&areaId='+areaId,editable:false,width:182,multi : false});
		                $('#userGroupId7').omCombo({dataSource : 'user_group_list_by_type.json?type=UPGRADE&dynamicFlag=false&areaId='+areaId,editable:false,width:182,multi : false});
		                $('#userGroupId8').omCombo({dataSource : 'user_group_list_by_type.json?type=APPUPGRADE&dynamicFlag=false&areaId='+areaId,editable:false,width:182,multi : false});
		                $('#userGroupId9').omCombo({dataSource : 'user_group_list_by_type.json?type=BACKGROUND&dynamicFlag=false&areaId='+areaId,editable:false,width:182,multi : false});
		                
		                }
				}
					});
	            var dialog = $("#dialog-form5").omDialog({
	                        width: 400,
	                        height: 260,
	                        autoOpen : false,
	                        modal : true,
	                        resizable : false,
	                        draggable : false,
	                        buttons : {
	                            "提交" : function(result){
	                            	submitData={
	                            			userGroupId4:$("#userGroupId4").val(),
	                            			userGroupId5:$("#userGroupId5").val(),
	                            			userGroupId6:$("#userGroupId6").val(),
	                            			userGroupId7:$("#userGroupId7").val(),
	                            			userGroupId8:$("#userGroupId8").val(),
	                            			userGroupId9:$("#userGroupId9").val(),
	                            			ids:selections[0].id
	                	            };
	                     			$.post('customers_bind_userGroup.json', submitData, function(date){
	                     				$('#grid').omGrid('reload');
	                                    if(date=="success"){
	        	                        	$.omMessageTip.show({title: tip, content: "用户绑定用户分组信息成功！", timeout: time,type:'success'});
	        	                        }else{
	        	                        	$.omMessageTip.show({title: tip, content: "用户绑定用户分组信息失败！"+date, timeout: time,type:'error'});
	        	                        }
	                                    $("#dialog-form5").omDialog("close");
	                                });

	                                 return false;
	                            },
	                            "取消" : function() {
	                                $("#dialog-form5").omDialog("close");
	                            }
	                        },onClose:function(){
                        $('#myform5').validate().resetForm();
                    }
	           		});
	                    dialog.omDialog("option", "title", "绑定用户分组信息");
	                    dialog.omDialog("open");
	    }); 
    
    function showDialog(title){
        dialog.omDialog("option", "title", title);
        dialog.omDialog("open");
    }
    
    var dialog = $("#dialog-form").omDialog({
        width: 1000,height:440, autoOpen : false,
        modal : true, resizable : false,
        draggable : false,
        buttons : {
			"提交" : function(){
            	submitData={
           			userId:$("#userId").val(),
           			cArea:$("#area").val(),
           			customerType:$("#customerType").omCombo('value'),
           			source:$("#source").val(),
           			nickName:$("#nickName").val(),
           			sex:$("#sex").omCombo('value'),
           			phone:$("#phone").val(),
           			identityType:$("#identityType").omCombo('value'),
           			hobby:$("#hobby").val(),
           			cServiceStop:$("#serviceStop").val(),
           			state:$("#state").omCombo('value'),
           			loginName:$("#loginName").val(),
           			cRegion:$("#region").val(),
           			mail:$("#mail").val(),
           			//客户信息，暂时未加
           			realName:$("#realName").val(),
           			cAge:$("#age").val(),
           			profession:$("#profession").val(),
           			identityCode:$("#identityCode").val(),
           			zipCode:$("#zipCode").val(),
           			rate:$("#rate").val(),
           			deviceCode:$("#device").val(),
           			
           			address:$("#address").val(),
                };
                if($("#identityType").val() == 'IDCARD' && $("#identityCode").val().length > 18){
                    alert("身份证号码为18位！");
                    }
            	if(validator.form()){
            		var action = 'add_customer.json';
            		var successMessage = "新增用户信息成功！";
            		var failMessage = "新增用户信息失败！";
            		if(method == "update"){
            			var selections=$('#grid').omGrid('getSelections',true);
            			action = 'update_customer.json?cId='+selections[0].id;
                		successMessage = "修改用户信息成功！";
                		failMessage = "修改用户信息失败！地市与区域不符!";
            		}
                	$.post(action, submitData, function(result){
                        $('#grid').omGrid('reload');
                        validator.resetForm();
                        if("success" == result){
                        	$.omMessageTip.show({title: tip, content: successMessage, type:"success" ,timeout: time});
                        }else{
                        	$.omMessageTip.show({title: tip, content: failMessage+result, type:"error" ,timeout: time});
                        }
                        $("#dialog-form").omDialog("close");
                    });
            	}
            },
            "取消" : function() {
            	validator.resetForm();
                $("#dialog-form").omDialog("close");
            }
        }
    });

    $('#relate_business').click(function(){
        var selections=$('#grid').omGrid('getSelections',true);
        if (selections.length == 0 || selections.length > 1) {
            $.omMessageBox.alert({
                type:'alert',
                title:'温馨提示',
                content:'关联业务操作至少且只能选择一行记录！',
            });
            return false;
        }
        if (selections[0].code=="") {
            $.omMessageBox.alert({
                type:'alert',
                title:'温馨提示',
                content:'用户编号为空，不可做关联业务操作！',
            });
            return false;
        }

        $.get("get_customer_relate_business.json",{customerCode:selections[0].code},function(result){
            if(result != null){
                $('#notice10').omCombo({dataSource: 'get_sys_notice_list.json', editable: false, width: 182, multi: true, value: result['NOTICE'] || ''});
                $('#background10').omCombo({dataSource : 'get_background_image_list.json?',editable:false,width:182,multi: true,value:result['BACKGROUND'] || ''});
                $('#animation10').omCombo({dataSource : 'get_boot_animation_list.json?',editable:false,width:182,value:result['ANIMATION']});
                $('#panel10').omCombo({dataSource : 'get_panel_package_list.json?',editable:false,width:182,value:result['PANEL']});
            }else{
                $('#notice10').omCombo({dataSource: 'get_sys_notice_list.json', editable: false, width: 182, multi: true, value:''});
                $('#background10').omCombo({dataSource : 'get_background_image_list.json?',editable:false,width:182,multi: true,value:''});
                $('#animation10').omCombo({dataSource : 'get_boot_animation_list.json?',editable:false,width:182,value:''});
                $('#panel10').omCombo({dataSource : 'get_panel_package_list.json?',editable:false,width:182,value:''});
            }
        });

        var dialog = $("#dialog-form10").omDialog({
            width: 400,
            height: 200,
            autoOpen : false,
            modal : true,
            resizable : false,
            draggable : false,
            buttons : {
                "提交" : function(result){
                    submitData={
                        animation:$('#animation10').val(),
                        panel:$('#panel10').val(),
                        notice: $("#notice10").omCombo('value'),
                        background:$('#background10').val(),
                        ids:selections[0].id.toString()
                    };

                    $.post('customer_bind_business.json', submitData, function(result){
                        $('#grid').omGrid('reload');
                        if(result.indexOf('成功') > 0){
                            $.omMessageTip.show({title: tip, content: result, timeout: time,type:'success'});
                        }
                        else if(result.indexOf('失败') > 0){
                            $.omMessageTip.show({title: tip, content: result, timeout: time,type:'error'});
                        }
                        else{
                            $.omMessageTip.show({title: tip, content: result, timeout: time,type:'alert'});
                        }
                        $("#dialog-form10").omDialog("close");
                    });

                    return false;
                },

                "解绑全部业务" : function(result){
                    if(selections[0].isLock=='锁定'){
                        $.omMessageBox.alert({
                            type:'alert',
                            title:'温馨提示',
                            content:'该条记录被锁定，请解锁后再做操作！',
                        });
                        return false;
                    }
                    $.omMessageBox.confirm({
                        title:'确认解绑',
                        content:'解绑后该用户与业务的绑定关系将删除且无法恢复，你确定要执行该操作吗？',
                        onClose:function(v){
                            if(v==true){
                                var toDeleteRecordID = "";
                                for(var i=0;i<selections.length;i++){
                                    if(i != selections.length - 1){
                                        toDeleteRecordID  += selections[i].id+",";
                                    }else{
                                        toDeleteRecordID  += selections[i].id;
                                    }
                                }
                                $.post('user_business_map_delete.json',{ids:toDeleteRecordID.toString()},function(result){
                                    $('#grid').omGrid('reload');
                                    if(result=='success'){
                                        $.omMessageTip.show({title: tip, content: "解绑业务成功！", timeout: time,type:'success'});
                                    }else{
                                        $.omMessageTip.show({title: tip, content: "解绑业务失败！", timeout: time,type:'error'});
                                    }
                                    $("#dialog-form10").omDialog("close");
                                });
                            }
                        }
                    });
                },
                "取消" : function() {
                    $("#dialog-form10").omDialog("close");
                }
            },onClose:function(){
                $('#myform10').validate().resetForm();
            }
        });
        dialog.omDialog("option", "title", "关联业务信息");
        dialog.omDialog("open");
    });
    
    $('#add').click(function(){
    	method = "add";
    	validator.resetForm();
    	resetData();
        $('#area').omCombo({dataSource : 'area.json',editable:false,width:182,listMaxHeight:160,
            onValueChange : function(target, newValue, oldValue){
                $('#region').omCombo({dataSource : 'city_by_area.json?areaId='+newValue,editable:false,listMaxHeight:150,width:182,value:''});
//                $('#region').omCombo({dataSource : 'city_by_area.json?areaId='+newValue,editable:false,listMaxHeight:150,width:180, value:''});
            }
        });
        $('#region').omCombo({dataSource : '',editable:false,listMaxHeight:150,width:182});
    	$('#state').omCombo({dataSource : 'customer_state.json',editable:false,width:182,listMaxHeight:150,value:'NORMAL',disabled:true});
    	$('#customerType').omCombo({dataSource : 'customer_type.json', editable:false, width:182, listMaxHeight:100,value:'PERSONAL'});
    	$('#sex').omCombo({dataSource : 'customer_sex.json', editable:false, width:182, listMaxHeight:100,value:'MAN'});
    	$('#identityType').omCombo({dataSource : 'customer_identity_type.json', editable:false, width:182, listMaxHeight:100,value:'IDCARD'});
		$("#userId").attr('disabled',false);
		$("#source").attr('disabled',false);
		$("#selectDevice").attr('disabled',false);
		$("#device").attr('disabled',false);
    	$("#serviceStop").val(getCurrentTime());
    	showDialog('新增用户信息');
    });
    
    function showTable(title){
        table.omDialog("option", "title", title);
        table.omDialog("open");    	
    }
    
    var table = $("#device-form").omDialog({
        width: 1000,height:440, autoOpen : false,resizable : false,
        draggable : false,modal : true,
        buttons : {
        	"确定" : function(){
        		var selections=$('#device-grid').omGrid('getSelections',true);
        		devices = "";
        		if(selections != null && selections.length > 0){
                	for(var i=0;i<selections.length;i++){
                    	if(i != selections.length - 1){
                    		devices += selections[i].code+",";
                        }else{
                        	devices += selections[i].code;
                        }
                    }        			
        		}
        		$("#device").val(devices);
        		$("#device-form").omDialog("close");
        	},
    		"取消" : function(){
    			$("#device-form").omDialog("close");
    		}
        }
    });
    
    $('#selectDevice').click(function(){
    	var customerType = $("#customerType").omCombo('value');
        var singleSelectTemp = false;
        if(customerType=='PERSONAL'){
        	singleSelectTemp = true;
        }
        var areaId = $("#area").val();
        $('#device-grid').omGrid({
        	dataSource : 'device_list.json?bindType=UNBIND&isLock=UNLOCKED&areaId='+areaId,
        	width : 980, height : "99%",
            singleSelect : singleSelectTemp,
            limit : 10,
            colModel : [ 
                         {header : '<b>设备编号</b>', name : 'ystenId', align : 'center', width : 120},
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
        showTable('终端设备信息');
    });
    
    var validator = $('#myform').validate({
        rules : {
        	userId : {required : true},
        	loginName: {required : true},
        	nickName:{maxlength:32},
        	address:{maxlength:2048},
        	profession:{maxlength:128},
        	hobby:{maxlength:512},
        	region: {required : true},     
        	source: {required : true},
        	customerType: {required : true}, 
        	serviceStop: {required : true}, 
        	phone: {required : true,maxlength:11,digits:true}, 
        	device: {required : true}, 
        	area: {required : true}, 
        	mail: {email : true},   
        	age :{digits:true},
        	zipCode :{digits:true,maxlength:6}
        }, 
        messages : {
        	userId : {required : "外部编号不能为空！"},
        	loginName: {required : "登录名称不能为空！"}, 
        	nickName:{maxlength:"用户昵称最长32位字符!"},
        	address:{maxlength:"地址最长可输入2048字符!"},
        	profession:{maxlength:"职业最多可输入128位字符!"},
        	hobby:{maxlength:"爱好最多可输入512位字符!"},
        	region: {required : "地市不能为空！"},     
        	source: {required : "用户来源不能为空！"},
        	customerType: {required : "用户类型不能为空！"}, 
        	serviceStop: {required : "请选择服务到期时间！"}, 
        	phone: {required : "手机号不能为空！",maxlength:"手机号最长为11位数字",digits:"手机号必须为数字"},
        	device: {required : "请选择设备信息！"}, 
        	area: {required : "所属区域不能为空 ！"},
        	mail: {email : '邮件格式不正确'},
        	age: {digits : '年龄只能为数字'},
        	zipCode: {digits : '邮政编码只能为6位数字',maxlength:"邮政编码只能为6位数字"}
        },
        errorPlacement : function(error, element) { 
            if(error.html()){
                $(element).parents().map(function(){
                    if(this.tagName.toLowerCase()=='td'){
                        var attentionElement = $(this).next().children().eq(0);
	                    attentionElement.html(error);
                    }
                });
            }
        },
        showErrors: function(errorMap, errorList) {
            if(errorList && errorList.length > 0){
                $.each(errorList,function(index,obj){
                    var msg = this.message;
                    $(obj.element).parents().map(function(){
                        if(this.tagName.toLowerCase()=='td'){
                            var attentionElement = $(this).next().children().eq(0);
                            attentionElement.show();
    	                    attentionElement.html(msg);
                        }
                    });
                });
            }else{
                $(this.currentElements).parents().map(function(){
                    if(this.tagName.toLowerCase()=='td'){
                        $(this).next().children().eq(0).hide();
                    }
                });
            }
            this.defaultShowErrors();
        }
    });
    
    $("#update").click(function(){
        var selections=$('#grid').omGrid('getSelections',true);
        if (selections.length == 0 || selections.length > 1) {
        	$.omMessageBox.alert({
                type:'alert',
                title:'温馨提示',
                content:'修改操作至少且只能选择一行记录！',
            });
            return false;
        }
        if(selections[0].isLock=='锁定'){
        	$.omMessageBox.alert({
                type:'alert',
                title:'温馨提示',
                content:'该条记录被锁定，请解锁后再做操作！',
            });
			return false;
            }
        $.ajax({
        	type : "post",
        	url : "customer_to_update.json?id="+selections[0].id,
        	dataType : "json",
        	success : function(data){
        		$('#state').omCombo({dataSource : 'customer_state.json',editable:false,width:182,listMaxHeight:150,value:data['state'],disabled:false});
        		$("#userId").val(data['userId']);
        		$("#loginName").val(data['loginName']);
        		$("#region").val(data['region']);
        		$("#source").omCombo({dataSource : 'source.json',editable:false,listMaxHeight:150,width:182,value:data['source']});
        		//$("#device").val(data['deviceCode']);
        		$("#deviceState").val(data['deviceState']);
        		$("#nickName").val(data['nickName']);
        		$("#realName").val(data['realName']);
        		$("#age").val(data['age']);
        		$("#phone").val(data['phone']);
        		$("#profession").val(data['profession']);
        		$("#hobby").val(data['hobby']);
        		$("#identityCode").val(data['identityCode']);
        		$("#zipCode").val(data['zipCode']);
        		$("#rate").val(data['rate']);
        		$("#mail").val(data['mail']);
        		$("#address").val(data['address']);
                var par = data['region']['id'];
        		if(data['area'] == null){
        			$('#area').omCombo({dataSource : 'area.json',editable:false,width:182,listMaxHeight:160,value:''});
        		}else{
        			$('#area').omCombo({dataSource : 'area.json',editable:false,width:182,listMaxHeight:160,value:data['area']['id'],
                    onValueChange : function(target,newValue,oldValue){
                        $('#region').omCombo({dataSource : 'city_by_area.json?areaId='+newValue+'&par='+par,editable:false,listMaxHeight:150,width:182,value:par});
                    }
                    });
        		}
            	$('#customerType').omCombo({dataSource : 'customer_type.json', editable:false, width:182, listMaxHeight:100, value:data['customerType']});
            	$('#sex').omCombo({dataSource : 'customer_sex.json', editable:false, width:182, listMaxHeight:100, value:data['sex']});
            	$('#identityType').omCombo({dataSource : 'customer_identity_type.json', editable:false, width:182, listMaxHeight:100, value:data['identityType']});
        		$("#serviceStop").val(data['serviceStop']);
        		method = "update";
        		$("#userId").attr('disabled',true);
        		$("#source").attr('disabled',true);
        		$("#device").attr('disabled',true);
        		
        		$("#selectDevice").attr('disabled',true);
        		showDialog("修改用户信息");
        	}
        });
    });
    
    $('#delete').click(function(){
    	var selections=$('#grid').omGrid('getSelections',true);
        if (selections.length == 0) {
        	$.omMessageBox.alert({
                type:'alert',
                title:'温馨提示',
                content:'删除操作至少选择一行记录！',
            });
            return false;
        }
        if(selections[0].isLock=='锁定'){
        	$.omMessageBox.alert({
                type:'alert',
                title:'温馨提示',
                content:'该条记录被锁定，请解锁后再做操作！',
            });
			return false;
            }
        $.omMessageBox.confirm({
            title:'确认删除',
            content:'执行删除后数据将不可恢复，你确定要执行该操作吗？',
            onClose:function(v){
            	if(v==true){
            		var toDeleteRecordID = "";
                	for(var i=0;i<selections.length;i++){
                    	if(i != selections.length - 1){
                    		toDeleteRecordID  += selections[i].id+",";
                        }else{
                        	toDeleteRecordID  += selections[i].id;
                        }
                    }
                	$.post('delete_customers.json',{ids:toDeleteRecordID.toString()},function(result){
                        $('#grid').omGrid('reload');
                        if(result=='success'){
                        	$.omMessageTip.show({title: tip, content: "删除用户信息成功！", timeout: time,type:'success'});
                        }else{
                        	$.omMessageTip.show({title: tip, content: result, timeout: time,type:'error'});
                        }
                        $('#grid').omGrid('reload');
                        $('#dialog-form').omDialog('close');
                    });
            	}
            }  
        });
    }); 
    
    $('#deviceQuery').bind('click', function(e) {
    	var areaId = $("#area").val();
        var deviceCode = $('#deviceCode1').val();
        var mac = $('#mac1').val();
        var sno = $('#sno1').val();
        var ystenId = $('#ystenId1').val();
        $('#device-grid').omGrid("setData", 'device_list.json?bindType=UNBIND&isLock=UNLOCKED&areaId='+areaId+'&ystenId='+encodeURIComponent(ystenId)+'&deviceCode='+encodeURIComponent(deviceCode)+'&mac='+encodeURIComponent(mac)+'&sno='+encodeURIComponent(sno));
    });
    
    $("#lock").click(function(){
    	var selections=$('#grid').omGrid('getSelections',true);
        if (selections.length == 0) {
        	$.omMessageBox.alert({
                type:'alert',
                title:'温馨提示',
                content:'锁定操作至少选择一行记录！',
            });
            return false;
        }
        /**
        if(selections[0].isLock=='锁定'){
			alert("该条记录已经锁定！");
			return false;
            }
            */
        $.omMessageBox.confirm({
            title:'确认锁定',
            content:'锁定后将无法对该用户数据进行修改，你确定要执行该操作吗？',
            onClose:function(v){
            	if(v==true){
            		var toDeleteRecordID = "";
                	for(var i=0;i<selections.length;i++){
                    	if(i != selections.length - 1){
                    		toDeleteRecordID  += selections[i].id+",";
                        }else{
                        	toDeleteRecordID  += selections[i].id;
                        }
                    }
                	$.post('lock_customers.json',{ids:toDeleteRecordID.toString()},function(result){
                        $('#grid').omGrid('reload');
                        if(result=='success'){
                        	$.omMessageTip.show({title: tip, content: "锁定用户成功！", timeout: time,type:'success'});
                        }else{
                        	$.omMessageTip.show({title: tip, content: "锁定用户失败！", timeout: time,type:'error'});
                        }
                        $('#dialog-form').omDialog('close');
                    });
            	}
            }  
        });
    });
    
    $("#unlock").click(function(){
    	var selections=$('#grid').omGrid('getSelections',true);
        if (selections.length == 0) {
        	$.omMessageBox.alert({
                type:'alert',
                title:'温馨提示',
                content:'解锁操作至少选择一行记录！',
            });
            return false;
        }
        $.omMessageBox.confirm({
            title:'确认解锁',
            content:'解锁后将可以该批用户数据进行修改，你确定要执行该操作吗？',
            onClose:function(v){
            	if(v==true){
            		var toDeleteRecordID = "";
                	for(var i=0;i<selections.length;i++){
                    	if(i != selections.length - 1){
                    		toDeleteRecordID  += selections[i].id+",";
                        }else{
                        	toDeleteRecordID  += selections[i].id;
                        }
                    }
                	$.post('unlock_customers.json',{ids:toDeleteRecordID.toString()},function(result){
                        $('#grid').omGrid('reload');
                        if(result=='success'){
                        	$.omMessageTip.show({title: tip, content: "解锁用户成功！", timeout: time,type:'success'});
                        }else{
                        	$.omMessageTip.show({title: tip, content: "解锁用户失败！", timeout: time,type:'error'});
                        }
                        $('#dialog-form').omDialog('close');
                    });
            	}
            }  
        });
    });    
    
    $('#query').bind('click', function() {
        var userId = $('#userId1').val();
        var code = $('#code1').val();
        var phone = $('#phone1').val();
        var areaId = $('#areaId1').val();
        $('#userIdValue').val(userId);
    	$('#codeValue').val(code);
    	$('#phoneValue').val(phone);
    	$('#areaIdValue').val(areaId);
    	$('#startDateValue').val("");
    	$('#endDateValue').val("");
    	$('#startDateAvtiveValue').val("");
    	$('#endDateAvtiveValue').val("");
    	$('#userIdHvalue').val("");
    	$('#codeHvalue').val("");
    	$('#customerStateValue').val("");
    	$('#areaIdHvalue').val("");
    	$('#regionValue').val("");
    	$('#nickNameValue').val("");
    	$('#phoneHvalue').val("");
    	$('#loginNameValue').val("");
        $('#grid').omGrid("setData", 'customer_list.json?bid=0&userId='+encodeURIComponent(userId)+'&code='+encodeURIComponent(code)+'&phone='+encodeURIComponent(phone)+'&cArea='+encodeURIComponent(areaId));
    });    

    $('#heightQuery').bind('click', function(e) {
    	$('#bulkSelType').omCombo({dataSource : [
		                                           {text:'用户编号',value:'0'},
		                                           {text:'外部用户编号',value:'1'}
		                                       ],
		                                           value:'0',
		                                           editable:false,
		                                           width:180,
		                                           listMaxHeight:160,
		                                           onValueChange : function(target, newValue){
		                                               if(newValue == 0){
		                                            	   $("#openCode").show();
		                                                   $("#openUserId").hide();
		                                                   $('#userId2').val("");
		                                               }
													   if(newValue == 1){
														   $("#openCode").hide();
		                                                   $("#openUserId").show();
		                                                   $('#code2').val("");
		                                               }
		                                       }
    	});
    	$("#startDateExport").omCalendar();
      	 $("#endDateExport").omCalendar();
      	 $("#startDateAvtive").omCalendar();
      	 $("#endDateAvtive").omCalendar();
        var dialog2 = $("#dialog-form2").omDialog({
            width: 700,
            height: 530,
            autoOpen : false,
            modal : true,
            resizable : false,
            draggable : false,
            buttons : {
                "提交" : function(){
                	 var startDateExport = $("#startDateExport").val();
	                 var endDateExport = $("#endDateExport").val();
	                 var startDateAvtive = $("#startDateAvtive").val();
	                 var endDateAvtive = $("#endDateAvtive").val();
                	 var userId = $('#userId2').val();
                	 var code = $('#code2').val();
                	 var region = $('#region2').val();
                	 var state = $('#customerState2').val();
                	 var area = $('#area2').val();
                	 var nickName = $('#nickName2').val();
                	 var phone = $('#phone2').val();
                	 var loginName = $('#loginName2').val();
                	 if((startDateExport !=null && endDateExport == null) || (startDateExport ==null && endDateExport != null)){
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
             	if(startDateExport > endDateExport || startDateAvtive > endDateAvtive){
             		$.omMessageBox.alert({
                     		type:'alert',
                     		title:'温馨提示',
                     		content:'结束时间要大于等于起始时间！',
                 		});
             	 	return false;
	               }
                	 var paramter = "userId="+encodeURIComponent(userId)+"&";
                	 paramter += "cArea="+encodeURIComponent(area)+"&";
                	 paramter += "cRegion="+encodeURIComponent(region)+"&";
                	 paramter += "code="+encodeURIComponent(code)+"&";
                	 paramter += "state="+encodeURIComponent(state)+"&";
                	 paramter += "nickName="+encodeURIComponent(nickName)+"&";
                	 paramter += "phone="+encodeURIComponent(phone)+"&";
                	 paramter += "loginName="+encodeURIComponent(loginName)+"&";
                	 paramter += "startDateExport="+encodeURIComponent(startDateExport)+"&";
                	 paramter += "endDateExport="+encodeURIComponent(endDateExport)+"&";
                	 paramter += "startDateAvtive="+encodeURIComponent(startDateAvtive)+"&";
                	 paramter += "endDateAvtive="+encodeURIComponent(endDateAvtive);
                    $('#grid').omGrid("setData", 'customer_list.json?bid=1&'+paramter);
                    $("#dialog-form2").omDialog("close"); 
                    $('#userIdValue').val("");
                 	$('#codeValue').val("");
                 	$('#phoneValue').val("");
                 	$('#areaIdValue').val("");
                 	
                 	$('#startDateValue').val(startDateExport);
                 	$('#endDateValue').val(endDateExport);
                 	$('#startDateAvtiveValue').val(startDateAvtive);
                 	$('#endDateAvtiveValue').val(endDateAvtive);
                 	$('#userIdHvalue').val(userId);
                 	$('#codeHvalue').val(code);
                 	$('#customerStateValue').val(state);
                 	$('#areaIdHvalue').val(area);
                 	$('#regionValue').val(region);
                 	$('#nickNameValue').val(nickName);
                 	$('#phoneHvalue').val(phone);
                 	$('#loginNameValue').val(loginName);
                     return false; 
                },
                "取消" : function() {
                    $("#dialog-form2").omDialog("close");
                }
            },onClose:function(){
            	$('#myform2').validate().resetForm();
                }
        });
        dialog2.omDialog("option", "title", "用户信息高级查询");
        dialog2.omDialog("open");
    });    
    
    $("#lookpassword").click(function(){
    	var selections=$('#grid').omGrid('getSelections',true);
        if (selections.length > 1 || selections.length == 0) {
        	$.omMessageBox.alert({
                type:'alert',
                title:'温馨提示',
                content:'查询密码至少且只能选择一条记录！',
            });
            return false;
        }   
        if(selections[0].isLock=='锁定'){
        	$.omMessageBox.alert({
                type:'alert',
                title:'温馨提示',
                content:'该条记录被锁定，请解锁后再做操作！',
            });
			return false;
            } 
        if(selections[0].custId != null && selections[0].custId != ""){
        	$.omMessageBox.alert({
                type:'success',
                title:'查看解密后密码：',
                content:"解密后用户密码为："+selections[0].password+"，请妥善保管！"
            });
            }
        else{
        	$.post('customer_lookpassword.json', {id:selections[0].id}, function(data){
            	$.omMessageBox.alert({
                    type:'success',
                    title:'查看解密后密码：',
                    content:"解密后用户密码为："+data+"，请妥善保管！"
                });
            });
            }
        
    }); 
    
    $("#resetPwd").click(function(){
    	var selections=$('#grid').omGrid('getSelections',true);
        if (selections.length > 1 || selections.length == 0) {
        	$.omMessageBox.alert({
                type:'alert',
                title:'温馨提示',
                content:'重置密码至少且只能选择一条记录！',
            });
            return false;
        }    
        if(selections[0].isLock=='锁定'){
        	$.omMessageBox.alert({
                type:'alert',
                title:'温馨提示',
                content:'该条记录被锁定，请解锁后再做操作！',
            });
			return false;
            }
        $.post('reset_customer_pwd.json', {id:selections[0].id}, function(result){
        	$('#grid').omGrid('reload');
            if(result=='success'){
            	$.omMessageTip.show({title: tip, content: "用户密码重置成功！", timeout: time,type:'success'});
            }else{
            	$.omMessageTip.show({title: tip, content: "用户密码重置失败！", timeout: time,type:'error'});
            }
            $('#dialog-form').omDialog('close');
        });
    }); 

    $('#export1').bind('click', function(e) {
    	var selections=$('#grid').omGrid('getSelections',true);
        if (selections.length == 0) {
        	$.omMessageBox.alert({
                type:'alert',
                title:'温馨提示',
                content:'至少选择一行记录导出！',
            });
            return false;
        }
        $.omMessageBox.confirm({
            title:'确认',
            content:'确定要导出这些数据吗？',
            onClose:function(v){
            	if(v==true){
            		var toDeleteRecordID = "";
                	for(var i=0;i<selections.length;i++){
                    	if(i != selections.length - 1){
                    		toDeleteRecordID  += selections[i].id+",";
                        }else{
                        	toDeleteRecordID  += selections[i].id;
                        }
                    }
                	location.href="customer_export_byId.json?ids="+encodeURIComponent(toDeleteRecordID.toString());               	   
                    $('#dialog-form').omDialog('close');
            	}
            }
        });  
    });

/************************************************导出*******************************************************/
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
	        var dialog = $("#dialog-form3").omDialog({
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
                    		location.href="customer_export_byId.json?ids="+encodeURIComponent(toDeleteRecordID.toString());               	   
            			}
	                	if($('#exportType').omCombo('value') == 1){
	                	 var random = Math.random();
	                	 var uUserId = $('#userIdValue').val();
	                	 var uCode = $('#codeValue').val();	
	                	 var uPhone = $('#phoneValue').val();	
	                	 var uAreaId = $('#areaIdValue').val();
	                	 	                	 
	                	 var userId = $('#userIdHvalue').val();
	                	 var code = $('#codeHvalue').val();
	                	 var region = $('#regionValue').val();
	                	 var state = $('#customerStateValue').val();
	                	 var area = $('#areaIdHvalue').val();
	                	 var nickName = $('#nickNameValue').val();
	                	 var phone = $('#phoneHvalue').val();
	                	 var loginName = $('#loginNameValue').val();
	                	 var startDateExport = $("#startDateValue").val();
	 	                 var endDateExport = $("#endDateValue").val();
	 	                 var startDateAvtive = $("#startDateAvtiveValue").val();
	 	                 var endDateAvtive = $("#endDateAvtiveValue").val();
	 	                
	                	 location.href="customer_list_export_by_conditions.json?random="+random
	                	+"&uUserId="+encodeURIComponent(uUserId)
		     			+"&uAreaId="+encodeURIComponent(uAreaId)
		     			+"&uCode="+encodeURIComponent(uCode)
		     			+"&uPhone="+encodeURIComponent(uPhone)
	     				+"&userId="+encodeURIComponent(userId)
	     				+"&cArea="+encodeURIComponent(area)
	     				+"&cRegion="+encodeURIComponent(region)
	     				+"&code="+encodeURIComponent(code)
	     				+"&state="+encodeURIComponent(state)
	     				+"&nickName="+encodeURIComponent(nickName)
	     				+"&phone="+encodeURIComponent(phone)
	     				+"&loginName="+encodeURIComponent(loginName)
						+"&startDateExport="+encodeURIComponent(startDateExport)
		        		+"&endDateExport="+encodeURIComponent(endDateExport)
		        		+"&startDateAvtive="+encodeURIComponent(startDateAvtive)
		        		+"&endDateAvtive="+encodeURIComponent(endDateAvtive);
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
	        dialog.omDialog("option", "title", "用户信息列表导出");
	        dialog.omDialog("open");
	    });
/**********************************************************************************************************/
/**
    $('#export').bind('click', function(e) {
     $("#startDateExport").omCalendar();
   	 $("#endDateExport").omCalendar();
   	 $("#startDateAvtive").omCalendar();
   	 $("#endDateAvtive").omCalendar();
        var dialog = $("#dialog-form3").omDialog({
            width: 530,
            height:350,
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
                	 var startDateExport = $("#startDateExport").val();
 	                 var endDateExport = $("#endDateExport").val();
 	                 var startDateAvtive = $("#startDateAvtive").val();
 	                 var endDateAvtive = $("#endDateAvtive").val();
 	                if(((startDateExport==null || startDateExport=="") && (endDateExport == null || endDateExport == "" ))&& ((startDateAvtive !=null || startDateAvtive !="") && (endDateAvtive == null || endDateAvtive == ""))){
 	                	$.omMessageBox.alert({
                             type:'alert',
                             title:'温馨提示',
                             content:'创建时间或激活时间至少选填一项！',
                         });
 	                	 return false;
 		                }
 	                if(startDateExport !=null || startDateExport !="" ){
 		                if(endDateExport == null || endDateExport == ""){
 		                	$.omMessageBox.alert({
 	                            type:'alert',
 	                            title:'温馨提示',
 	                            content:'请填写创建的结束时间！',
 	                        });
 		                	 return false;
 			                }
 	                	
 		                }
 	                if((startDateExport ==null || startDateExport =="") && (endDateExport != null || endDateExport != "") ){
 	                	$.omMessageBox.alert({
                             type:'alert',
                             title:'温馨提示',
                             content:'请填写创建的起始时间！',
                         });
 	                	 return false;
 		                }
 	                if(startDateAvtive !=null && startDateAvtive !=""){
 		                if(endDateAvtive == null && endDateAvtive == ""){
 		                	$.omMessageBox.alert({
 	                            type:'alert',
 	                            title:'温馨提示',
 	                            content:'请填写激活的结束时间！',
 	                        });
 		                	 return false;
 			                }
 	                	
 		                }
 	                if((startDateAvtive ==null && startDateAvtive =="") && (endDateAvtive != null && endDateAvtive != "") ){
 	                	$.omMessageBox.alert({
                             type:'alert',
                             title:'温馨提示',
                             content:'请填写激活的起始时间！',
                         });
 	                	 return false;
 		                }
 	                if(startDateExport > endDateExport || startDateAvtive > endDateAvtive){
 	                	$.omMessageBox.alert({
                             type:'alert',
                             title:'温馨提示',
                             content:'结束时间要大于等于起始时间！',
                         });
 	                	 return false;
 		                }
                	 location.href="customer_list_export.json?random="+random
     				+"&userId="+encodeURIComponent(userId)
     				+"&cArea="+encodeURIComponent(area)
     				+"&cRegion="+encodeURIComponent(region)
     				+"&code="+encodeURIComponent(code)
     				+"&customerType="+encodeURIComponent(customerType)
     				+"&nickName="+encodeURIComponent(nickName)
     				+"&phone="+encodeURIComponent(phone)
     				+"&loginName="+encodeURIComponent(loginName)
					+"&startDateExport="+encodeURIComponent(startDateExport)
	        		+"&endDateExport="+encodeURIComponent(endDateExport)
	        		+"&startDateAvtive="+encodeURIComponent(startDateAvtive)
	        		+"&endDateAvtive="+encodeURIComponent(endDateAvtive);
                     $("#dialog-form3").omDialog("close"); 
                     return false; 
                     
                },
                "取消" : function() {
                    $("#dialog-form3").omDialog("close");
                }
            },onClose:function(){}
        });
        dialog.omDialog("option", "title", "用户信息列表导出");
        dialog.omDialog("open");
    });
    */
    $('#sync').bind('click', function(e) {
		$.omMessageBox.confirm({
          	title:'确认同步',
           content:'用户同步将同步所有状态改变的用户，你确定要执行该操作吗？',
           onClose:function(v){
           	if(v==true){
                   	$.post('user_sync.shtml',function(result){
                       	/**
                           if(result=='success'){
                           	$.omMessageTip.show({title: tip, content: "用户同步信息成功！", timeout: time,type:'success'});
                           }else{
                           	$.omMessageTip.show({title: tip, content: "用户同步信息失败，存在未同步成功的数据，请重新同步！", timeout: time,type:'error'});
                           }*/
                           $('#grid').omGrid('reload'); 
                        if(result.indexOf('成功') > 0){
                   			$.omMessageTip.show({title: tip, content: "用户同步信息成功！", type:"success" ,timeout: time});
                   		}else{
                   			$.omMessageTip.show({title: tip, content: result, type:"error" ,timeout: time});
                   		}
                    	$('#grid').omGrid('reload');   
                        $('#dialog-form').omDialog('close');
                     });
               	}
            }
      }); 
	});
	
    $('#import').bind('click', function(e) {
		var dialog = $("#dialog-form4").omDialog({
	        width: 400,
	        autoOpen : false,
	        modal : true,
	        resizable : false,
	        draggable : false,
	        buttons : {
	            "提交" : function(){
	            	if($("#textfield").val().length==0){
	            		$.omMessageBox.alert({type:'alert',title:tip,content:'请选择文件后再提交！',});
	            		return false;
	            	}
	            	 var FileName=new String($("#textfield").val());//文件名
                	 var extension=new String(FileName.substring(FileName.lastIndexOf(".")+1,FileName.length));//文件扩展名
                	if(extension == 'xls' || extension == 'xlsx'){
                    	}else{
                    		alert('导入的文件格式不支持,只可以导入EXCEL文件！');
                            return false;
                            } 
	            	$('#myform4').submit(); 
	            	$("#dialog-form4").omDialog("close");
	            	showDiv();
	            },
	            "取消" : function() {
	                $("#dialog-form4").omDialog("close");
	            }
	        },onClose:function(){}
	    });
		dialog.omDialog("option", "title", "用户信息导入");
		dialog.omDialog("open");
	});

    $('#temp').bind('click', function(e) {
		var random = Math.random();
		location.href="customer_export_template.json?random="+random;
	});
});
	function resetData(){
        $("#showResult").html('');
        $("#area").omCombo('setData', []);
        $("#region").omCombo('setData', []);
	}

	function checkUserIdExists(){
		if($.trim($("#userId").val())!=""){
		   var par = new Object();
		   par['userId'] = $("#userId").val();
		   par['id'] = $("#id").val();
	       $.ajax({
	       type:"post",
	       url:"userId_exists.json?s="+Math.random(),
	       async:false,
	       dataType:"html",
	       data:par,
	       success:function(msg){ 
		       if(msg.indexOf('不可用') >= 0){
		    	   $.omMessageBox.alert({
	                   type:'alert',
	                   title:'温馨提示',
	                   content:"用户外部编号"+msg,
	               });
	               return false;
			       }
	    	    
	       }
	      });
	    }
	    
}	
--></script>
</head>
<body>
<div id="center-tab">
		<ul>
			<li><a href="#tab1">用户信息列表</a></li>
		</ul>
	</div>
	<table>
		<tr align="left">
			<c:if test="${sessionScope.customer_list_add != null }">
				<td><div id="add"/></td>
			</c:if>	
			<c:if test="${sessionScope.customer_list_update != null }">
				<td><div id="update"/></td>
			</c:if>
			<c:if test="${sessionScope.customer_list_delete != null }">
				<td><div id="delete"/></td>
			</c:if>
			<c:if test="${sessionScope.customer_list_lock != null }">
            	<td><div id="lock"/></td>
           	</c:if>
           	<c:if test="${sessionScope.customer_list_unlock != null }">
            	<td><div id="unlock"/></td>
           	</c:if>
			<c:if test="${sessionScope.customer_list_lookpassword != null }">
				<td><div id="lookpassword"/></td>
			</c:if>           	
			<c:if test="${sessionScope.customer_reset_pwd != null }">
				<td><div id="resetPwd"/></td>
			</c:if>	
			<c:if test="${sessionScope.customer_bind_group != null }">
				<td><div id="bindGroup"/></td>
			</c:if>
			<c:if test="${sessionScope.unbind_user_group != null }">
				<td><div id="unbindGroup"></div></td>
			</c:if>
			<c:if test="${sessionScope.unbind_business_user != null }">
				<td><div id="unbind_business"></div></td>
			</c:if>
            <td><div id="relate_business"></div></td>
			<c:if test="${sessionScope.user_sync != null }">
				<td><div id="sync"></div></td>
			</c:if>	
			<c:if test="${sessionScope.customer_list_import != null }">
				<td><div id="import"/></td>
			</c:if>
			<c:if test="${sessionScope.customer_list_export != null }">
				<td><div id="export"/></td>
			</c:if>
			<c:if test="${sessionScope.customer_list_export1 != null }">
				<td><div id="export1"/></td>
			</c:if>
			<td><div id="temp"/></td>
		</tr>
		</table>
		<table>
		<tr>
			<td>用户外部编号：</td>
			<td>
				<input type="text" name="userId1" id="userId1" style="width:110px;height: 20px;border:1px solid #86A3C4;"/>
			</td>
			<td>用户编号：</td>
			<td>
				<input type="text" name="code1" id="code1" style="width:110px;height: 20px;border:1px solid #86A3C4;"/>
			</td>
			<td>手机号码：</td>
			<td>
				<input type="text" name="phone1" id="phone1" style="width:110px;height: 20px;border:1px solid #86A3C4;"/>
			</td>
            <td>区域：</td>
            <td><input id="areaId1" name="areaId1" style="width: 120px;"/></td>
			<td><div id="query"/></td>
			<td><div id="heightQuery"/> </td>
		</tr>
	</table>

	<div id="dialog-form">
		<form id="myform" method="post">
		 <input type="hidden" value="" name="id" id="id"/>
			<table>
				<tr>
					<td style="width: 100px;text-align: right;"><span class="color_red">*</span>外部编号：</td>
					<td><input id="userId" name="userId" style="width:180px;height: 20px;border:1px solid #86A3C4;"onblur="checkUserIdExists();"/></td>
					<td style="width: 180px;"><span></span></td>
					<td style="width: 100px;text-align: right;"><span class="color_red">*</span>登录名称：</td>
					<td><input id="loginName" name="loginName"  style="width:180px;height: 20px;border:1px solid #86A3C4;"/></td>				
					<td style="width: 180px;"><span></span></td>
				</tr>
				<tr>
					<td style="width: 100px;text-align: right;"><span class="color_red">*</span>所属区域：</td>
					<td><input id="area" name="area"/></td>	
					<td style="width: 150px;"><span></span></td>
					<td style="width: 100px;text-align: right;"><span class="color_red">*</span>所属地市：</td>
					<td><input id="region" name="region"/></td>
					<td style="width: 180px;"><span></span></td>
				</tr>	
				<tr>
					<td style="width: 100px;text-align: right;"><span class="color_red">*</span>终端设备：</td>
					<td><input id="device" name="device" style="width:140px;height: 20px;border:1px solid #86A3C4;" readonly="readonly"/><input id="selectDevice" type="button" value="选择"/></td>	
					<td><span></span></td>
					<td style="width: 100px;text-align: right;"><span class="color_red">*</span>用户状态：</td>
					<td><input id="state" name="state"/></td>
					<td><span></span></td>	
				</tr>	
				<tr>
					<td style="width: 100px;text-align: right;"><span class="color_red">*</span>用户类型：</td>
					<td><input id="customerType" name="customerType"/></td>
					<td style="width: 150px;"><span></span></td>
					<td style="width: 100px;text-align: right;">电子邮件：</td>
					<td><input id="mail" name="mail"  style="width:180px;height: 20px;border:1px solid #86A3C4;" maxlength="32"/></td>
					<td style="width: 150px;"><span></span></td>	
				</tr>
				<tr>
					<td style="width: 100px;text-align: right;"><span class="color_red">*</span>用户来源：</td>
					<td><input id="source" name="source"/></td>
					<td style="width: 150px;"><span></span></td>	
					<td style="width: 100px;text-align: right;">客户信息：</td>
					<td><input id="deviceState" name="deviceState" style="width:180px;height: 20px;border:1px solid #86A3C4;" maxlength="32"/></td>
					<td style="width: 150px;"><span></span></td>
				</tr>			
				<tr>
					<td style="width: 100px;text-align: right;">用户昵称：</td>
					<td><input id="nickName" name="nickName"  style="width:180px;height: 20px;border:1px solid #86A3C4;" maxlength="32"/></td>
					<td style="width: 150px;"><span></span></td>
					<td style="width: 100px;text-align: right;">真实姓名：</td>
					<td><input id="realName" name="realName"  style="width:180px;height: 20px;border:1px solid #86A3C4;" maxlength="32"/></td>	
					<td style="width: 150px;"><span></span></td>			
				</tr>
				<tr>
					<td style="width: 100px;text-align: right;">用户性别：</td>
					<td><input id="sex" name="sex"/></td>
					<td style="width: 150px;"><span></span></td>
					<td style="width: 100px;text-align: right;">用户年龄：</td>
					<td><input id="age" name="age"  style="width:180px;height: 20px;border:1px solid #86A3C4;" maxlength="3"/></td>	
					<td style="width: 150px;"><span></span></td>			
				</tr>		
				<tr>
					<td style="width: 100px;text-align: right;"><span class="color_red">*</span>用户电话：</td>
					<td><input id="phone" name="phone"  style="width:180px;height: 20px;border:1px solid #86A3C4;" maxlength="11"/></td>
					<td style="width: 150px;"><span></span></td>
					<td style="width: 100px;text-align: right;">用户职业：</td>
					<td><input id="profession" name="profession"  style="width:180px;height: 20px;border:1px solid #86A3C4;"/></td>
					<td style="width: 150px;"><span></span></td>				
				</tr>
				<tr>
					<td style="width: 100px;text-align: right;">证件类型：</td>
					<td><input id="identityType" name="identityType"/></td>	
					<td style="width: 150px;"><span></span></td>
					<td style="width: 100px;text-align: right;">证件编号：</td>
					<td><input id="identityCode" name="identityCode"  style="width:180px;height: 20px;border:1px solid #86A3C4;" maxlength="18"/></td>
					<td style="width: 150px;"><span></span></td>
				</tr>										
				<tr>
					<td style="width: 100px;text-align: right;">用户爱好：</td>
					<td><input id="hobby" name="hobby"  style="width:180px;height: 20px;border:1px solid #86A3C4;"/></td>
					<td style="width: 150px;"><span></span></td>
					<td style="width: 100px;text-align: right;">邮政编码：</td>
					<td><input id="zipCode" name="zipCode"  style="width:180px;height: 20px;border:1px solid #86A3C4;" maxlength="6"/></td>
					<td style="width: 150px;"><span></span></td>				
				</tr>	
				<tr>
					<td style="width: 100px;text-align: right;"><span class="color_red">*</span>到期时间：</td>
					<td><input id="serviceStop" name="serviceStop" style="width:160px;"/></td>
					<td style="width: 150px;"><span></span></td>
					<td style="width: 100px;text-align: right;">宽带速率：</td>
					<td><input id="rate" name="rate"  style="width:180px;height: 20px;border:1px solid #86A3C4;"/></td>
					<td><span></span></td>				
				</tr>	
				<tr>
					<td style="width: 100px;text-align: right;">常用地址：</td>
					<td colspan="5">
					<textarea id="address" name="address" cols="106" rows="4" style="border:1px solid #86A3C4;" maxlength="2048"></textarea>
				</tr>	
			</table>
		</form>
	</div>
	
	<table id="grid" ></table>
	
	<div id="device-div" style="display: none;">
		<form id="device-form">
			<tr>
				<td>设备编号：</td>
				<td><input type="text" name="ystenId1" id="ystenId1"
				style="width: 130px; height: 20px; border: 1px solid #86A3C4;" /></td>
				<td>设备序列号：</td>
				<td><input name="sno1" id="sno1" style="width:130px;height: 20px;border:1px solid #86A3C4;"/></td>
				<td>MAC地址：</td>
				<td><input type="text" name="mac1" id="mac1" style="width:130px;height: 20px;border:1px solid #86A3C4;"/></td>
				<td><input id="deviceQuery" type="button" value="查询"/>&nbsp; </td>
            </tr>
			<table id="device-grid" ></table>
		</form>
	</div>	
	
	<div id="dialog-form2" style="display:none;">
		<form id="myform2">
			<table>
				<tr>
					<td style="width: 100px;text-align: right;">创建时间：</td>
					<td>
					<input type="text" id="startDateExport" name="startDateExport" style="width:160px;"/>
					至
					<input id="endDateExport" name="endDateExport"  style="width:160px;"/>
					</td>
				</tr>
				<tr>
					<td style="width: 100px;text-align: right;">激活时间：</td>
					<td>
					<input type="text" id="startDateAvtive" name="startDateAvtive" style="width:160px;"/>
					至
					<input id="endDateAvtive" name="endDateAvtive"  style="width:160px;"/>
					</td>
			   </tr>
			</table>
			<table>
				<tr>
		            <td style="width: 100px; text-align: right;">批量查询字段名：</td>
					<td><input name="bulkSelType" id="bulkSelType"/></td>
		            <td><span></span></td>
		            </tr>
				<tr id="openUserId" style="display: none;">
					<td style="width: 100px;text-align: right;">用户外部编号：</td>
					<td><textarea id="userId2" name="userId2" cols="70" rows="15" style="border:1px solid #86A3C4;"></textarea></td>
	               <td><span>请输入用户外部编号，如有多个用户外部编号请用英文逗号分隔</span></td>
				</tr>
				<tr id="openCode" style="display: none;">
					<td style="width: 100px;text-align: right;">用户编号：</td>
					<td><textarea id="code2" name="code2" cols="70" rows="15" style="border:1px solid #86A3C4;"></textarea></td>
	               <td><span>请输入用户编号，如有多个用户编号请用英文逗号分隔</span></td>			
				</tr>
			</table>
				<!-- 
				<tr>
					<td style="width: 100px;text-align: right;">用户类型：</td>
					<td><input id="customerType2" name="customerType2"/></td>
				</tr>
				 -->
			<table>
				<tr>
					<td style="width: 100px;text-align: right;">用户状态：</td>
					<td><input id="customerState2" name="customerState2"/></td>
				</tr>		
				<tr>
					<td style="width: 100px;text-align: right;">所属区域：</td>
					<td><input id="area2" name="area2"/></td>	
				</tr>
				<tr>
					<td style="width: 100px;text-align: right;">所属地市：</td>
					<td><input id="region2" name="region2"/></td>
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
	
	<div id="dialog-form3" style="display:none;">
		<form id="myform3">
			<table>
				<tr>
					<td>导出方式：</td>
					<td>
					  <input name="exportType" id="exportType"/>
					</td>
				</tr>
			<!-- 
			<tr>
					<td style="width: 100px;text-align: right;">创建时间：</td>
					<td>
					<input type="text" id="startDateExport" name="startDateExport" style="width:160px;"/>
					至
					<input id="endDateExport" name="endDateExport"  style="width:160px;"/>
					</td>
				</tr>
				 <tr>
					<td style="width: 100px;text-align: right;">激活时间：</td>
					<td>
					<input type="text" id="startDateAvtive" name="startDateAvtive" style="width:160px;"/>
					至
					<input id="endDateAvtive" name="endDateAvtive"  style="width:160px;"/>
					</td>
			   </tr>
			    
			   <tr>
					<td style="width: 100px;text-align: right;">外部编号：</td>
					<td><input id="userId3" name="userId3" style="width:180px;height: 20px;border:1px solid #86A3C4;"/></td>
				</tr>
				<tr>
					<td style="width: 100px;text-align: right;">用户编号：</td>
					<td><input id="code3" name="code3"  style="width:180px;height: 20px;border:1px solid #86A3C4;"/></td>				
				</tr>
				<tr>
					<td style="width: 100px;text-align: right;">所属区域：</td>
					<td><input id="area3" name="area3" style="width:180px;height: 20px;"/></td>
				</tr>
				<tr>
					<td style="width: 100px;text-align: right;">所属地市：</td>
					<td><input id="region3" name="region3" style="width:180px;"/></td>
				</tr>
				<tr>
					<td style="width: 100px;text-align: right;">用户类型：</td>
					<td><input id="customerType3" name="customerType3"  style="width:180px;"/></td>				
				</tr>
					
				<tr>
					<td style="width: 100px;text-align: right;">用户昵称：</td>
					<td><input id="nickName3" name="nickName3"  style="width:180px;height: 20px;border:1px solid #86A3C4;"/></td>				
				</tr>
				<tr>
					<td style="width: 100px;text-align: right;">用户电话：</td>
					<td><input id="phone3" name="phone3" style="width:180px;height: 20px;border:1px solid #86A3C4;"/></td>
				</tr>
				<tr>
					<td style="width: 100px;text-align: right;">登录名称：</td>
					<td><input id="loginName3" name="loginName3"  style="width:180px;height: 20px;border:1px solid #86A3C4;"/></td>				
				</tr>
				-->
			</table>
		</form>
	</div>	
	<div id="dialog-form4" class="file-box" style="display:none;">
		<form id="myform4" action="customer_import.json" method="post" enctype="multipart/form-data">
			<table>
		         <tr>
		           <td>Excel文件：</td>
		           <td><input type='text' name='textfield' id='textfield' class='txt' />
					<input type='button' class='btn' value='浏览...' />
					<input type="file" name="fileField" class="file" id="fileField" size="28" onchange="document.getElementById('textfield').value=this.value" />
				 </td>
		         </tr>
			</table>
		</form>
	</div>
<div id="dialog-form5">
	<form id="myform5">
	<table>          
           	<tr>
               <td align="right">用户--消息分组：</td>
			   <td><input id="userGroupId4" name="userGroupId4" style="width:180px;"/></td>
               <td><span></span></td>
            </tr>
            <tr></tr>
            <tr>
               <td align="right">用户--动画分组：</td>
			   <td><input id="userGroupId5" name="userGroupId5" style="width:180px;"/></td>
               <td><span></span></td>
               </tr>
                <tr></tr>
                <tr>
			    <td align="right">用户--背景分组：</td>
				<td><input name="userGroupId9" id="userGroupId9" style="width:180px;"/></td>
				<td><span></span></td>
			</tr>
               <tr>
               <td align="right">用户--面板分组：</td>
			   <td><input id="userGroupId6" name="userGroupId6" style="width:180px;"/></td>
               <td><span></span></td>
               </tr>
                <tr></tr>
               <!--tr>
               <td align="right">用户--升级分组：</td>
			   <td><input id="userGroupId7" name="userGroupId7" style="width:180px;"/></td>
               <td><span></span></td>
               </tr>
                <tr></tr-->
               <!--tr>
               <td align="right">用户--应用升级分组：</td>
			   <td><input id="userGroupId8" name="userGroupId8" style="width:180px;"/></td>
               <td><span></span></td>
               </tr-->
	</table>
</form>
</div>


<div id="dialog-form10">
    <form id="myform10">
        <table>
            <!--tr>
                <td align="right">开机引导初始化：</td>
                <td><input name="boot10" id="boot10" style="width:180px;"/></td>
                <td><span></span></td>
            </tr-->
            <tr></tr>
            <tr>
                <td align="right">消息信息：</td>
                <td><input name="notice10" id="notice10" style="width:180px;"/></td>
                <td><span></span></td>
            </tr>
            <tr></tr>
            <tr>
                <td align="right">开机动画：</td>
                <td><input  name="animation10" id="animation10" style="width:180px;"/></td>
                <td><span></span></td>
            </tr>
            <tr></tr>
            <tr>
                <td align="right">背景轮换：</td>
                <td><input name="background10" id="background10" style="width:180px;"/></td>
                <td><span></span></td>
            </tr>
            <tr></tr>
            <tr>
                <td align="right">面板包：</td>
                <td><input  name="panel10" id="panel10" style="width:180px;"/></td>
                <td><span></span></td>
            </tr>
            <tr></tr>
            <!--tr>
                <td align="right">终端升级：</td>
                <td><input name="upgrade10" id="upgrade10" style="width:180px;"/></td>
                <td><span></span></td>
            </tr-->
            <tr></tr>
            <!--tr>
                <td align="right">应用升级：</td>
                <td><input name="appUpgrade10" id="appUpgrade10" style="width:180px;"/></td>
                <td><span></span></td>
            </tr-->
        </table>
    </form>
</div>

	<input type="hidden" id="userIdValue" name="userIdValue"/>
	<input type="hidden" id="codeValue" name="codeValue"/>	
	<input type="hidden" id="phoneValue" name="phoneValue"/>
	<input type="hidden" id="areaIdValue" name="areaIdValue"/>
	
	<input type="hidden" id="startDateValue" name="startDateValue"/>
	<input type="hidden" id="endDateValue" name="endDateValue"/>
	<input type="hidden" id="startDateAvtiveValue" name="startDateAvtiveValue"/>
	<input type="hidden" id="endDateAvtiveValue" name="endDateAvtiveValue"/>
	<input type="hidden" id="userIdHvalue" name="userIdHvalue"/>
	<input type="hidden" id="codeHvalue" name="codeHvalue"/>
	<input type="hidden" id="customerStateValue" name="customerStateValue"/>
	<input type="hidden" id="areaIdHvalue" name="areaIdHvalue"/>
	<input type="hidden" id="regionValue" name="regionValue"/>
	<input type="hidden" id="nickNameValue" name="nickNameValue"/>
	<input type="hidden" id="phoneHvalue" name="phoneHvalue"/>
	<input type="hidden" id="loginNameValue" name="loginNameValue"/>
</body>
<div id="bg"></div>
<div id="show" align="center"></div>
</html>
