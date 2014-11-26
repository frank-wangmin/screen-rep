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
<script type="text/javascript">
$(document).ready(function() {
	$('#center-tab').omTabs({height: "33", border: false});
    $('#grid').omGrid({
    	dataSource : 'get_app_software_package_list.json',
    	width : '100%',
    	height : rFrameHeight,
        singleSelect : false,
        limit : limit,
        colModel : [                                                    
			{header:'<b>软件版本序号</b>',name:'versionSeq',align:'center',width:80},
			{header:'<b>软件版本名称</b>',name:'versionName',align:'center',width:140},
			{header:'<b>应用前版本序号</b>',name:'appVersionSeq',align:'center',width:100},
			{header:'<b>SDK版本序号</b>',name:'sdkVersion',align:'center',width:100},
			{header:'<b>软件包类型</b>',name:'packageType',align:'center',width:80},
			 {header:'<b>软件号名称</b>',name:'softCodeId',align : 'center', width :130,renderer:function(value){
            	 if(value==null || value==""){
                     return "";
                     }else{
                    	 return value.name;
                         }
                 }
            },
            
	        {header:'<b>软件号编码</b>',name:'softCodeId',align:'center',width:130,renderer:function(value){
	        	 if(value==null || value==""){
                     return "";
                     }else{
                    	 return value.code;
                         }
                 }
            },   
			{header:'<b>软件包绝对路径</b>',name:'packageLocation',align:'center',width:200},
			{header:'<b>软件包状态</b>',name:'packageStatus',align:'center',width:70},
			{header:'<b>分发状态</b>',name:'distributeState',align:'center',width:70},
			{header:'<b>是否强制升级</b>',name:'mandatoryStatus',align:'center',width:70},
			{header:'<b>MD5</b>',name:'md5',align:'center',width:250},
			{header:'<b>全量包ID</b>',name:'fullSoftwareId',align:'center',width:60, renderer:function(value, row, index, options){
				var text = value;
				$.each(options.fullPackage, function(i, item){
					if(item.value == value){
						text =  item.text;
					}
				})
				return text;
			}},
			{header:'<b>创建时间</b>',name:'createDate',align:'center',width:120},
			{header:'<b>最后操作时间</b>',name:'lastModifyTime',align:'center',width:120},
  			{header:'<b>操作者</b>',name:'operUser',align:'center',width:80}
		],extraDataSource : [{name:"fullPackage", url:"app_soft_package.json"}]
    });
    $('#packageStatus').omCombo({
        dataSource : [ 
                      {text : '测试', value : 'TEST'}, 
                       {text : '发布', value : 'RELEASE'}
                      ],value:'TEST'
    });
    $('#packageType').omCombo({
        dataSource : [ 
                      {text : '增量', value : 'INCREMENT'}, 
                       {text : '全量', value : 'FULL'}
                      ],value:'INCREMENT'
    });
    $('#mandatoryStatus').omCombo({
        dataSource : [ 
                      {text : '强制', value : 'MANDATORY'}, 
                       {text : '不强制', value : 'NOTMANDATORY'}
                      ],value:'MANDATORY'
    });
    $('#create').omButtonbar({btns : [{label:"新增",icons : {left : opPath+'add.png'}}]});
    $('#update').omButtonbar({btns : [{label:"修改",icons : {left : opPath+'op-edit.png'}}]});
    $('#query').omButtonbar({btns : [{label:"查询",icons : {left : opPath+'search.png'}}]});
    $('#delete').omButtonbar({btns : [{label:"删除",icons : {left : opPath+'remove.png'}}]});
    $('#distribute').omButtonbar({btns : [{label:"软件升级包下发",icons : {left : opPath+'op-btn-icon.png'}}]});
    $("#selectSoftCode").omButton();
    $("#selectFullSoftCode").omButton();
    $("#softCodeQuery").omButton();
    $("#softPackageQuery").omButton();
    $('#softCodeId').omCombo({
    	dataSource : 'app_soft_code.json',editable:false,width:181,listMaxHeight:150,value:''});
	
    $('#distribute').bind('click', function(e) {
    	$('#areaId1').omCombo({dataSource : 'area.json?par=0',editable:false,width:180,listMaxHeight:160,value:'0'});
    	var selections=$('#grid').omGrid('getSelections',true);
        if (selections.length == 0) {
        	$.omMessageBox.alert({
                type:'alert',
                title:'温馨提示',
                content:'至少选择一行记录！',
            });
            return false;
        }
        var toDeleteRecordID = "";
    	for(var i=0;i<selections.length;i++){
    		if(selections[i].packageType == "增量"){
     	       $.ajax({
     	       type:"post",
     	       url:"check_appsoftPackage_isDistribute.json?id="+selections[i].fullSoftwareId,
     	       dataType:"text",
     	       success:function(msg){ 
     	    	   $.omMessageBox.alert({
     	                type:'alert',
     	                title:'温馨提示',
     	                content:msg,
     	            });
     	       }
     	      });
     	       return false;
         	}
        	if(i != selections.length - 1){
        		toDeleteRecordID  += selections[i].id+",";
            }else{
            	toDeleteRecordID  += selections[i].id;
            }
        }
        $.omMessageBox.confirm({
            title:'确认下发软件包',
            content:'批量操作后数据将下发给省级，你确定要执行该操作吗？',
            onClose:function(v){
            	if(v==true){
                	var dialog = $("#dialog-form1").omDialog({
                        width: 400,
                        height: 220,
                        autoOpen : false,
                        modal : true,
                        resizable : false,
                        draggable : false,
                        buttons : {
                            "提交" : function(result){ 
                            	submitData={
                            			areaId:$("#areaId1").val(),	                            			
                            			ids:toDeleteRecordID.toString()            		
                	            };         
                            	$.post('app_soft_package_rend.json',submitData,function(result){
                                    $('#grid').omGrid('reload');
                                    if(result=='success'){
                                    	$.omMessageTip.show({title: tip, content: "下发应用软件包信息成功！", timeout: time,type:'success'});
                                    }else{
                                    	$.omMessageTip.show({title: tip, content: "下发应用软件包信息失败！", timeout: time,type:'error'});
                                    }
                                    $("#dialog-form1").omDialog("close");
                                });
                                 $('#grid').omGrid('reload');
                                 return false; 
                            },
                            "取消" : function() {
                                $("#dialog-form1").omDialog("close");
                            }
                        },onClose:function(){}
           		});
         	 	 dialog.omDialog("option", "title", "下发区域选择");
                 dialog.omDialog("open");
                    /**
                	$.post('app_soft_package_rend.json',{ids:toDeleteRecordID.toString()},function(result){
                        $('#grid').omGrid('reload');
                        if(result=='success'){
                        	$.omMessageTip.show({title: tip, content: "下发信息成功！", timeout: time,type:'success'});
                        }else{
                        	$.omMessageTip.show({title: tip, content: "下发信息失败！", timeout: time,type:'error'});
                        }
                        $('#dialog-form').omDialog('close');
                    });*/
            	}
            }
        });  
    });
    
    var table = $("#soft-code-form").omDialog({
        width: 1000,height:440, autoOpen : false,resizable : false,
        draggable : false,modal : true,
        buttons : {
        	"确定" : function(){
        		var selections=$('#soft-code-grid').omGrid('getSelections',true);
        		softCodes = "";
        		softCode = "";
        		if(selections != null && selections.length > 0){
                	for(var i=0;i<selections.length;i++){
                    	if(i != selections.length - 1){
                    		softCode += selections[i].name+",";
                    		softCodes += selections[i].id+",";
                        }else{
                        	softCode += selections[i].name;
                        	softCodes += selections[i].id;
                        }
                    }        			
        		}
        		$("#softCodeId").val(softCodes);
        		$("#softCode").val(softCode);
        		$("#soft-code-form").omDialog("close");
        	},
    		"取消" : function(){
    			$("#soft-code-form").omDialog("close");
    		}
        }
    });
    
    function showTable(title){
        table.omDialog("option", "title", title);
        table.omDialog("open");    	
    }

    var table1 = $("#soft-package-form").omDialog({
        width: 1000,height:440, autoOpen : false,resizable : false,
        draggable : false,modal : true,
        buttons : {
        	"确定" : function(){
        		var selections=$('#soft-package-grid').omGrid('getSelections',true);
        		softPackages = "";
        		softPackage = "";
        		if(selections != null && selections.length > 0){
                	for(var i=0;i<selections.length;i++){
                    	if(i != selections.length - 1){
                    		softPackages += selections[i].id+",";
                    		softPackage += selections[i].versionName+",";
                        }else{
                        	softPackages += selections[i].id;
                        	softPackage += selections[i].versionName;
                        }
                    }        			
        		}
        		$("#fullSoftwareId").val(softPackages);
        		$("#fullSoftware").val(softPackage);
        		$("#soft-package-form").omDialog("close");
        	},
    		"取消" : function(){
    			$("#soft-package-form").omDialog("close");
    		}
        }
    });
    
    function showTable1(title){
        table1.omDialog("option", "title", title);
        table1.omDialog("open");    	
    }
    
    $('#softCodeQuery').bind('click', function(e) {
        var name = $('#name').val();
        $('#soft-code-grid').omGrid("setData", 'find_app_software_code_list.json?status=USABLE&name='+encodeURIComponent(name));
    });
    
    $('#softPackageQuery').bind('click', function(e) {
    	var softCodeIds = $("#softCodeId").val(); 
        var versionName = $('#versionName2').val();
        $('#soft-package-grid').omGrid("setData", 'find_app_software_package_list.json?softCodeId='+encodeURIComponent(softCodeIds)+'&packageType=FULL&name='+encodeURIComponent(versionName));
    });
    
    $('#selectSoftCode').click(function(){       
        $('#soft-code-grid').omGrid({
        	dataSource : 'find_app_software_code_list.json?status=USABLE',
        	width : 980, height : "99%",
            singleSelect : true,
            limit : 10,
            colModel:[
            	        {header:'软件号名称',name:'name',align:'center',width:200},
            			{header:'编码',name:'code',align:'center',width:200},
            			{header:'状态',name:'status',align:'center',width:200},
            			{header:'创建时间',name:'createDate',align:'center',width:200},
            			{header:'描述',name:'description',align:'center',width:250}
            		]
		});    	
        showTable('设备软件号信息');
    });
    $('#selectFullSoftCode').click(function(){  
    	var softCodeIds = $("#softCodeId").val();       
        $('#soft-package-grid').omGrid({
        	dataSource : 'find_app_software_package_list.json?softCodeId='+encodeURIComponent(softCodeIds)+'&packageType=FULL',
        	width : 980, height : "99%",
            singleSelect : true,
            limit : 10,
            colModel : [ 
                        {header:'软件号',name:'softCodeId',align:'center',align : 'center', width :130,renderer:function(value){
                            if(value.name !=null && value.name != ""){
                            	return value.name;
                                }
                            else{
                                return value;
                                }
                            }
                        },
                        
            	        {header:'软件号编码',name:'softCodeId',align:'center',width:130,renderer:function(value){
                            if(value.code !=null && value.code != ""){
                            	return value.code;
                                }
                            else{
                                return value;
                                }
                            }
                        },
    			{header:'软件版本序号',name:'versionSeq',align:'center',width:120},
    			{header:'软件版本名称',name:'versionName',align:'center',width:200},
    			{header:'应用前版本序号',name:'appVersionSeq',align:'center',width:120},
    			{header:'SDK版本序号',name:'sdkVersion',align:'center',width:120},
    			{header:'全量包ID',name:'fullSoftwareId',align:'center',width:120, renderer:function(value, row, index, options){
    				var text = value;
    				$.each(options.fullPackage, function(i, item){
    					if(item.value == value){
    						text =  item.text;
    					}
    				})
    				return text;
    			}},
    			{header:'软件包类型',name:'packageType',align:'center',width:200},
    			{header:'软件包绝对路径',name:'packageLocation',align:'center',width:250},
    			{header:'软件包状态',name:'packageStatus',align:'center',width:120},
    			{header:'是否强制升级',name:'mandatoryStatus',align:'center',width:120},
    			{header:'md5',name:'md5',align:'center',width:250},
    			{header:'创建时间',name:'createDate',align:'center',width:250}
    		],extraDataSource : [{name:"fullPackage", url:"app_soft_package.json"}]
		});    	
        showTable1('设备软件包信息');
    });
    $('#query').bind('click', function(e) {
        var versionName = $('#versionName').val();
        $('#grid').omGrid("setData", 'get_app_software_package_list.json?versionName='+encodeURIComponent(versionName));
    });
    $('#delete').bind('click', function(e) {
    	var selections=$('#grid').omGrid('getSelections',true);
        if (selections.length == 0) {
        	$.omMessageBox.alert({
                type:'alert',
                title:'温馨提示',
                content:'删除操作至少选择一行记录！',
            });
            return false;
        }
        $.omMessageBox.confirm({
            title:'确认删除',
            content:'批量删除后数据将无法恢复，你确定要执行该操作吗？',
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
                	$.post('app_soft_package_delete.json',{ids:toDeleteRecordID.toString()},function(result){
                        $('#grid').omGrid('reload');
                        if(result=='success'){
                        	$.omMessageTip.show({title: tip, content: "删除应用软件包信息成功！", timeout: time,type:'success'});
                        }else{
                        	$.omMessageTip.show({title: tip, content: "删除应用软件包信息失败！", timeout: time,type:'error'});
                        }
                        $('#dialog-form').omDialog('close');
                    });
            	}
            }
        });  
    });
    var dialog = $("#dialog-form").omDialog({
        width: 700,
        height:500,
        autoOpen : false,
        modal : true,
        resizable : false,
        draggable : false,
        buttons : {
            "提交" : function(){
                var packageType = $("#packageType").val();
                var fullSoftwareId = $("#fullSoftwareId").val();
                var sr = $("#showResult").html();
            	if(packageType == "INCREMENT" && (fullSoftwareId == ""||fullSoftwareId == null)){
            		$("#showResult1").text("请选择全量的软件包！");
            		return false; 
                }
            	if(sr=='' || sr =='可用!'){
                	submitDialog();
                }
                return false; 
            },
            "取消" : function() {
            	$("#showResult").text("");
            	$("#showResult1").text("");
                $("#dialog-form").omDialog("close");
            }
        },onClose:function(){$("#showResult").text("");$("#showResult1").text("");}
    }); 
   
    var showDialog = function(title,rowData){
        validator.resetForm();
        rowData = rowData || {};
        dialog.omDialog("option", "title", title);
        dialog.omDialog("open");
    };
    var submitDialog = function(){
        var submitData;
        if (validator.form()) {
            if(isAdd){
            	submitData={
            		packageType:$("#packageType").val(),
            		softCodeId:$("#softCodeId").val(),
            		versionSeq:$("#versionSeq").val(),
            		versionName:$("#versionName1").val(),
            		packageStatus:$("#packageStatus").val(),
            		mandatoryStatus:$("#mandatoryStatus").val(),
            		appVersionSeq:$("#appVersionSeq").val(),
            		sdkVersion:$("#sdkVersion").val(),
            		md5:$("#md5").val(),
            		packageLocation:$("#packageLocation").val(),
            		fullSoftwareId:$("#fullSoftwareId").val()
                    };               
            	$.post('app_soft_package_add.json',submitData,function(result){
                    $('#grid').omGrid('reload');
                    if("success" == result){
                    	$.omMessageTip.show({title: tip, content: "新增应用软件包信息成功！", type:"success" ,timeout: time});
                    }else{
                    	$.omMessageTip.show({title: tip, content: "新增应用软件包信息失败！", type:"error" ,timeout: time});
                    }
                    $("#dialog-form").omDialog("close");
                });
            }else{
            	submitData={           		   
            			id:$("#id").val(),
            			packageType:$("#packageType").val(),
                		softCodeId:$("#softCodeId").val(),
                		versionSeq:$("#versionSeq").val(),
                		versionName:$("#versionName1").val(),
                		packageStatus:$("#packageStatus").val(),
                		mandatoryStatus:$("#mandatoryStatus").val(),
                		appVersionSeq:$("#appVersionSeq").val(),
                		sdkVersion:$("#sdkVersion").val(),
                		md5:$("#md5").val(),
                		packageLocation:$("#packageLocation").val(),
                		fullSoftwareId:$("#fullSoftwareId").val()         			
                    };
            	$.post('app_soft_package_update.json',submitData,function(result){
                    $('#grid').omGrid('reload');
                    if("success" == result){
                    	$.omMessageTip.show({title: tip, content: "修改应用软件包信息成功！", type:"success" ,timeout: time});
                    }else{
                    	$.omMessageTip.show({title: tip, content: "修改应用软件包信息失败！", type:"error" ,timeout: time});
                    }
                    $("#dialog-form").omDialog("close"); 
                });
            }
        }
    };
    var isAdd = true;
    $('#create').bind('click', function() {
        isAdd = true;
        showDialog('新增升级软件包信息');//显示dialog
    });
    $('#update').bind('click', function() {
        var selections=$('#grid').omGrid('getSelections',true);
        if (selections.length == 0 || selections.length > 1) {
        	$.omMessageBox.alert({
                type:'alert',
                title:'温馨提示',
                content:'修改操作至少且只能选择一行记录！',
            });
            return false;
        }
        isAdd = false;
        $.ajax({
           	type:"post",
           	url:"app_soft_package_to_update.json?id="+selections[0].id,
           	dataType:"json",
          	success:function(msg){
              	$("#id").val(msg['id']);
    			//$("#packageType").val(msg['packageType']),
    			$('#packageType').omCombo({
			        dataSource : [ 
			                      {text : '增量', value : 'INCREMENT'}, 
			                       {text : '全量', value : 'FULL'}
			                      ],value:msg['packageType']
			    });   			 
    			//$("#softCodeId").val(msg['softCodeId'].id);
    			//$("#softCode").val(msg['softCodeId'].id);
    			$('#softCodeId').omCombo({
    		    	dataSource : 'app_soft_code.json',editable:false,width:180,listMaxHeight:150,value:msg['softCodeId'].id});
    			$("#versionSeq").val(msg['versionSeq']);
    			$("#versionName1").val(msg['versionName']);
    			//$("#packageStatus").val(msg['packageStatus']);
    			$('#packageStatus').omCombo({
			        dataSource : [ 
			                      {text : '测试', value : 'TEST'}, 
			                       {text : '发布', value : 'RELEASE'}
			                      ],value:msg['packageStatus']
			    });		  
    			//$("#mandatoryStatus").val(msg['mandatoryStatus']),    			  
			    $('#mandatoryStatus').omCombo({
			        dataSource : [ 
			                      {text : '强制', value : 'MANDATORY'}, 
			                       {text : '不强制', value : 'NOTMANDATORY'}
			                      ],value:msg['mandatoryStatus']
			    });
    			$("#appVersionSeq").val(msg['appVersionSeq']);
    			$("#sdkVersion").val(msg['sdkVersion']);
    			$("#md5").val(msg['md5']);
    			$("#packageLocation").val(msg['packageLocation']);
    			$("#fullSoftwareId").val(msg['fullSoftwareId']);
    			//$("#fullSoftware").val(msg['fullSoftwareId'])    			
          }
          });  
        $.ajax({
           	type:"post",
           	url:"app_soft_package_to_update.json?id="+selections[0].fullSoftwareId,
           	dataType:"json",
          	success:function(msg){
          		$('#fullSoftware').val(msg['versionName']);
          	}
          });     
        showDialog('修改升级软件包信息',selections[0]);
    });
    var validator = $('#myform').validate({
        rules : {    	   
            	packageType:{required: true},
            	softCodeId:{required: true},
            	versionName1:{required: true,maxlength:32},
            	versionSeq:{required: true,digits:true,maxlength:9},
            	sdkVersion:{required: true, digits:true,maxlength:9},            	
            	packageStatus:{required: true},
            	mandatoryStatus:{required: true},
            	deviceVersionSeq:{required: true,digits:true,maxlength:9},
            	md5:{required: true,maxlength:32},
            	packageLocation:{required: true,maxlength:255}
        }, 
        messages : {
        	packageType:{required: "软件包类型不能为空！"},
        	softCodeId:{required: "软件号不能为空！"},
        	versionName1:{required: "软件版本名称不能为空！",maxlength:"最大长度为32位字符"},
        	versionSeq:{required: "软件版本序号不能为空！",digits:"请输入数字！",maxlength:"最大长度为9位字符"},
        	sdkVersion:{required: "SDK版本号不为空！",digits:"请输入数字！",maxlength:"最大长度为9位字符"},
        	packageStatus:{required: "软件包状态不能为空！"},
        	mandatoryStatus:{required: "是否强制升级不能为空！"},
        	appVersionSeq:{required: "当前版本号不能为空！",digits:"请输入数字！",maxlength:"最大长度为9位字符"},
        	md5:{required: "MD5值不能为空！",maxlength:"最大长度为32位字符"},
        	packageLocation:{required: "软件包绝对路径不能为空！",maxlength:"最大长度为255位字符"}
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
})
function checkNameExists(){
	if($.trim($("#versionName1").val())!=""){
	   var par = new Object();
	   par['versionName'] = $("#versionName1").val();	   
	   par['id'] = $("#id").val();
       $.ajax({
       type:"post",
       url:"app_software_package_name_exists.shtml?s="+Math.random(),
       dataType:"html",
       data:par,
       success:function(msg){ 
           //alert(msg); 
           $("#showResult").html(msg);
       }
      });
    }else if($.trim($("#name1").val())=="" && $("#showResult").html() != ""){
    	$("#showResult").html("");
        }
}
function resetData(){
	$("#showResult").html('');
}
</script>
</head>
<body>
<div id="center-tab">
<ul>
	 <li><a href="#tab1">应用软件包信息列表:</a></li>
</ul>
</div>
<table >
     <tbody>
        <tr>
               <c:if test="${sessionScope.add_app_soft_package != null }">          
                <td><div id="create"></div></td>
                </c:if>
                <c:if test="${sessionScope.update_app_soft_package != null }">
                <td><div id="update"></div></td>
                </c:if>
                <c:if test="${sessionScope.delete_app_soft_package_hide != null }">
                <td><div id="delete"></div></td>
                </c:if>
                <c:if test="${sessionScope.distribute_app_soft_package != null }">
                <td><div id="distribute"></div></td>
				</c:if>
            <td style="text-align:center;">软件版本名称：</td>
            <td>
               <input type="text" name="versionName" id="versionName" style="height: 20px;border:1px solid #86A3C4;"/></td>            
           <td>
             <div id="query"/>
           </td>
        </tr>
     </tbody>
</table>
<table id="grid" ></table>
<div id="soft-code-div" style="display: none;">
		<form id="soft-code-form">
		<table>
			<tr>
				<td>软件号名称：</td>
            <td>
                <input type="text" name="name" id="name" style="width:200px;height: 20px;border:1px solid #86A3C4;"/>
            </td>
            <td>
            <input id="softCodeQuery" type="button" value="查询"/>
           </td>
           </tr>
           </table>
			<table id="soft-code-grid" ></table>
		</form>
	</div>	 
	<div id="soft-package-div" style="display: none;">
		<form id="soft-package-form">
		<table>
			<tr>
				<td>软件版本名称：</td>
            <td>
                <input type="text" name="versionName2" id="versionName2" style="width:200px;height: 20px;border:1px solid #86A3C4;"/>
            </td>
            <td>
            <input id="softPackageQuery" type="button" value="查询"/>
           </td>
           </tr>
           </table>
			<table id="soft-package-grid" ></table>
		</form>
	</div>	       
<div id="dialog-form">
     <form id="myform">
     <input type="hidden" value="" name="id" id="id"/>
			<table>								 			   
			<tr>			
				<td style="width: 180px;text-align: right;"><span class="color_red">*</span>软件包类型：</td>
				<td><input id="packageType" name="packageType" style="width:161px"/></td>
	            <td><span></span></td>
			</tr>
			<tr>
				<td style="width: 100px;text-align: right;"><span class="color_red">*</span>软件信息号：</td>
				<td><input id="softCodeId" name="softCodeId" style="width:161px" readonly="readonly"/>
				</td>	
			    <td><span></span></td>
				
			</tr>			
			<tr>
				<td style="width: 100px;text-align: right;"><span class="color_red">*</span>软件版本序号： </td>
				<td><input id="versionSeq" name="versionSeq" style="width:180px;height:20px;border:1px solid #86A3C4;"/></td>
				<td><span></span></td>
			</tr>			
			<tr>
				<td style="width: 100px;text-align: right;"><span class="color_red">*</span>软件版本名称：</td>
				<td><input id="versionName1" name="versionName1" style="width:180px;height:20px;border:1px solid #86A3C4;"onblur="checkNameExists()"/></td>
	                <td><span></span><div id="showResult" class="color_red" style="display: inline;"/></td>
			</tr>			
			<tr>
				<td style="width: 100px;text-align: right;"><span class="color_red">*</span>软件包状态：</td>
				<td><input id="packageStatus" name="packageStatus" style="width:161px"/></td>
	            <td><span></span></td>
			</tr>
			<tr>
				<td style="width: 100px;text-align: right;"><span class="color_red">*</span>是否强制升级：</td>
			    <td><input id="mandatoryStatus" name="mandatoryStatus" style="width:161px"/></td>
	            <td><span></span></td>
			</tr>
			<tr>
				<td style="width: 100px;text-align: right;"><span class="color_red">*</span>当前应用版本号：</td>
				<td><input id="appVersionSeq" name="appVersionSeq" style="width:180px;height:20px;border:1px solid #86A3C4;"/></td>
				<td><span></span></td>	
			</tr>
			<tr>
				<td style="width: 100px;text-align: right;"><span class="color_red">*</span>SDK版本号：</td>
				<td><input id="sdkVersion" name="sdkVersion" style="width:180px;height:20px;border:1px solid #86A3C4;"/></td>
				<td><span></span></td>	
			</tr>
			<!-- updated by joyce on 2014-6-12-->
			<tr>
				<td style="width: 100px;text-align: right;"><span class="color_red">*</span>MD5：</td>
				<td><input id="md5" name="md5" style="width:180px;height:20px;border:1px solid #86A3C4;" maxlength="32"/></td>
				<td><span></span></td>	
			</tr>
			<tr>
				<td style="width: 100px;text-align: right;"><span class="color_red">*</span>软件包绝对路径：</td>				
				<td colspan="2">
					<textarea id="packageLocation" name="packageLocation" cols="50" rows="3" style="border:1px solid #86A3C4;" maxlength="255"></textarea>			
			    </td><td><span></span></td>
			</tr>	
			<tr>
				<td style="width: 100px;text-align: right;">全量软件包：</td>
				<td><input id="fullSoftware" name="fullSoftware" style="width:180px;height: 20px;border:1px solid #86A3C4;" readonly="readonly"/>
				<input id="selectFullSoftCode" type="button" value="选择"/><input id="fullSoftwareId" name="fullSoftwareId" hidden="ture"></input></td>	
			    <td><span></span><div id="showResult1" class="color_red" style="display: inline;"/></td>				
			</tr>		
			</table>
		</form>
	</div>	
	<div id="dialog-form1" style="display: none;">
		<form id="myform1">
			<table>
				<tr>
					<td style="width: 100px; text-align: right;"><span class="color_red">*</span>区域：</td>
					<td><input name="areaId1" id="areaId1"/></td>
					<td style="width: 200px;"><span></span></td>
				</tr>				
			</table>
		</form>
	</div>				
</body>
</html>
