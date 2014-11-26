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
	$('#center-tab').omTabs({height:"33",border:false});
    $('#grid').omGrid({
    	dataSource : 'device_ip_list.json',
    	width : '100%',
        height : rFrameHeight,
        singleSelect : false,
        limit : limit,
        colModel : [ 
                     {header : '<b>IP段</b>', name : 'ipSeg', align : 'center', width : 120},
                     {header : '<b>掩码长度</b>', name : 'maskLength', align : 'center', width : 120}, 
                     {header : '<b>起始IP值</b>', name : 'startIpValue',align : 'center',width: 200},
                     {header : '<b>终止IP值</b>', name : 'endIpValue',align : 'center',width: 200}
		]
    });

    $('#create').omButtonbar({btns : [{label:"新增",icons : {left : opPath+'add.png'}}]});
    $('#update').omButtonbar({btns : [{label:"修改",icons : {left : opPath+'op-edit.png'}}]});
    $('#query').omButtonbar({btns : [{label:"查询",icons : {left : opPath+'search.png'}}]});
    $('#delete').omButtonbar({btns : [{label:"删除",icons : {left : opPath+'remove.png'}}]});
    $('#search').omButtonbar({btns : [{label:"IP地址是否存在",icons : {left : opPath+'search.png'}}]});
    
    $('#query').bind('click', function(e) {
        var ip = $('#ip').val();
        $('#grid').omGrid("setData", 'device_ip_list.json?ipSeq='+encodeURIComponent(ip));
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
            content:'批量删除后IP地址库数据将无法恢复，你确定要执行该操作吗？',
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
                	$.post('device_ip_delete.shtml',{ids:toDeleteRecordID.toString()},function(result){
                        $('#grid').omGrid('reload');
                        if(result=='success'){
                        	$.omMessageTip.show({title: tip, content: "删除IP地址库成功！", timeout: time,type:'success'});
                        }else{
                        	$.omMessageTip.show({title: tip, content: "删除IP地址库失败！", timeout: time,type:'error'});
                        }
                        $('#dialog-form').omDialog('close');
                    });
            	}
            }
        });  
    });

    var dialog = $("#dialog-form").omDialog({
        width: 480,
        autoOpen : false,
        modal : true,
        resizable : false,
        draggable : false,
        buttons : {
            "提交" : function(){
            	validator.form();
            	//doSubmit1();
        var ipSeg = $("#ipSeg").val();
    	var ipLength = ipSeg.length;
    	var Letters = "1234567890."; 
    	var scount=0;
    	
        for (i=0; i<ipLength; i++) { 
         	  var CheckChar = ipSeg.charAt(i); 
         	  if (Letters.indexOf(CheckChar) == -1) 
         	  { 
			   //alert ("IP地址格式不对!!，只能输入数字和“.”,格式为XXX.XXX.XXX.XXX 例如：192.168.0.1");      
			   $.omMessageBox.alert({
                   type:'alert',
                   title:'温馨提示',
                   content:'IP地址格式不对!!，只能输入数字和“.”,格式为XXX.XXX.XXX.XXX 例如：192.168.0.1',
               });	     	   
         	   return false; 
         	  		} 
         		} ;
          for (var i = 0;i<ipLength;i++) 
          		 (ipSeg.substr(i,1)==".")?scount++:scount; 
      	    if(scount!=3) 
      	    {
          	   // alert ("IP地址格式不对!!，只能输入数字和“.”,格式为XXX.XXX.XXX.XXX 例如：192.168.0.1"); 
          	  $.omMessageBox.alert({
                  type:'alert',
                  title:'温馨提示',
                  content:'IP地址格式不对!!，只能输入数字和“.”,格式为XXX.XXX.XXX.XXX 例如：192.168.0.1',
              });
      	     return false; 
      	    } ; 
      	    var first = ipSeg.indexOf("."); 
     	    var last = ipSeg.lastIndexOf("."); 
     	    var str1 = ipSeg.substring(0,first); 
     	    var subip = ipSeg.substring(0,last); 
     	    var sublength = subip.length; 
     	    var second = subip.lastIndexOf("."); 
     	    var str2 = subip.substring(first+1,second); 
     	    var str3 = subip.substring(second+1,sublength); 
     	    var str4 = ipSeg.substring(last+1,ipLength); 
     	    if (str1=="" || str2=="" || str3== "" || str4 == "") 
     	    {
         	    //alert("数字不能为空！格式为XXX.XXX.XXX.XXX 例如：192.168.0.1");
         	    $.omMessageBox.alert({
                   type:'alert',
                   title:'温馨提示',
                   content:'数字不能为空！格式为XXX.XXX.XXX.XXX 例如：192.168.0.1',
               });
     	    $("#ipSeg").val()=""; 
     	 	$("#ipSeg").focus();  
     	    return false; 
     	    } 
     	    if (str1< 0 || str1 >255) 
     	    {
         	    //alert ("数字范围为0~255！"); 
         	   $.omMessageBox.alert({
                   type:'alert',
                   title:'温馨提示',
                   content:'数字范围为0~255！',
               });
     	    $("#ipSeg").val()=""; 
     	 	$("#ipSeg").focus(); 
     	    return false; 
     	    } 
     	    else if (str2< 0 || str2 >255) 
     	    {
         	    //alert ("数字范围为0~255！"); 
         	   $.omMessageBox.alert({
                   type:'alert',
                   title:'温馨提示',
                   content:'数字范围为0~255！',
               });
     	    $("#ipSeg").val()=""; 
     	 	$("#ipSeg").focus();
     	    return false; 
     	    } 
     	    else if (str3< 0 || str3 >255) 
     	    { 
         	    //alert ("数字范围为0~255！"); 
         	   $.omMessageBox.alert({
                   type:'alert',
                   title:'温馨提示',
                   content:'数字范围为0~255！',
               });
     	    $("#ipSeg").val()=""; 
     	 	$("#ipSeg").focus(); 
     	    return false; 
     	    } 
     	    else if (str4< 0 || str4 >255) 
     	    {
         	    //alert ("数字范围为0~255！"); 
         	   $.omMessageBox.alert({
                   type:'alert',
                   title:'温馨提示',
                   content:'数字范围为0~255！',
               });
     	    $("#ipSeg").val()=""; 
     	 	$("#ipSeg").focus();
     	    return false; 
     	    };
                submitDialog();
                return false; 
            },
            "取消" : function() {
                $("#dialog-form").omDialog("close");
            }
        },onClose:function(){}
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
            			ipSeg:$("#ipSeg").val(),
            			maskLength:$("#maskLength").val(),
                    };
            	$.post('device_ip_add.shtml',submitData,function(result){
                    $('#grid').omGrid('reload');
                    if("success" == result){
                    	$.omMessageTip.show({title: tip, content: "新增IP地址库成功！", type:"success" ,timeout: time});
                    }else{
                    	$.omMessageTip.show({title: tip, content: "新增IP地址库失败！", type:"error" ,timeout: time});
                    }
                    $("#dialog-form").omDialog("close");
                });
            }else{
            	submitData={
                    	id:$("#id").val(),
            			ipSeg:$("#ipSeg").val(),
            			maskLength:$("#maskLength").val(),
                    };
            	$.post('device_ip_update.shtml',submitData,function(result){
                    $('#grid').omGrid('reload');
                    if("success" == result){
                    	$.omMessageTip.show({title: tip, content: "修改IP地址库成功！", type:"success" ,timeout: time});
                    }else{
                    	$.omMessageTip.show({title: tip, content: "修改IP地址库失败！", type:"error" ,timeout: time});
                    }
                    $("#dialog-form").omDialog("close"); 
                });
            }
        }
    };
    
    var isAdd = true;
    $('#create').bind('click', function() {
        isAdd = true;
        showDialog('新增IP地址库信息');
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
           	url:"device_ip_to_update.json?id="+selections[0].id,
           	dataType:"json",
          	success:function(msg){
              	$("#id").val(msg['id']);
    			$("#ipSeg").val(msg['ipSeg']);
    			$("#maskLength").val(msg['maskLength']);
          }
          });
        
        showDialog('修改IP地址库信息',selections[0]);
    });
    
    $('#search').bind('click', function() {
        var dialog1 = $("#dialog-form1").omDialog({
            width: 480,
            autoOpen : false,
            modal : true,
            resizable : false,
            draggable : false,
            buttons : {
                "提交" : function(){
                	doSubmit();
                    return false; 
                },
                "取消" : function() {
                    $("#dialog-form1").omDialog("close");
                }
            },onClose:function(){}
        });
        dialog1.omDialog("option", "title", "IP地址在数据库中是否存在");
        dialog1.omDialog("open");
    });
    function doSubmit(){
    	var ipSeg1 = $("#ipSeg1").val();
    	var ipLength = ipSeg1.length;
    	var Letters = "1234567890."; 
    	var scount=0;
    for (i=0; i<ipLength; i++) { 
   	  var CheckChar = ipSeg1.charAt(i); 
   	  if (Letters.indexOf(CheckChar) == -1) 
   	  { 
   	  // alert ("IP地址格式不对!!，只能输入数字和“.”,格式为XXX.XXX.XXX.XXX 例如：192.168.0.1"); 
	   	$.omMessageBox.alert({
	        type:'alert',
	        title:'温馨提示',
	        content:'IP地址格式不对!!，只能输入数字和“.”,格式为XXX.XXX.XXX.XXX 例如：192.168.0.1',
	    });
   	   $("#ipSeg1").val()=""; 
   	   $("#ipSeg1").focus(); 
   	   return false; 
   	  		} 
   		} ;
    for (var i = 0;i<ipLength;i++) 
    		 (ipSeg1.substr(i,1)==".")?scount++:scount; 
	    if(scount!=3) 
	    { 
	     //alert ("IP地址格式不对!，只能输入数字和“.”,格式为XXX.XXX.XXX.XXX 例如：192.168.0.1"); 
	     $.omMessageBox.alert({
		        type:'alert',
		        title:'温馨提示',
		        content:'IP地址格式不对!!，只能输入数字和“.”,格式为XXX.XXX.XXX.XXX 例如：192.168.0.1',
		    });
	     $("#ipSeg1").val()=""; 
	 	 $("#ipSeg1").focus(); 
	     return false; 
	    } ;
	    var first = ipSeg1.indexOf("."); 
	    var last = ipSeg1.lastIndexOf("."); 
	    var str1 = ipSeg1.substring(0,first); 
	    var subip = ipSeg1.substring(0,last); 
	    var sublength = subip.length; 
	    var second = subip.lastIndexOf("."); 
	    var str2 = subip.substring(first+1,second); 
	    var str3 = subip.substring(second+1,sublength); 
	    var str4 = ipSeg1.substring(last+1,ipLength); 

	    if (str1=="" || str2=="" ||str3== "" ||str4 == "") 
	    {
		    //alert("数字不能为空！格式为XXX.XXX.XXX.XXX 例如：192.168.0.1"); 
		    $.omMessageBox.alert({
		        type:'alert',
		        title:'温馨提示',
		        content:'数字不能为空！格式为XXX.XXX.XXX.XXX 例如：192.168.0.1',
		    });
	    $("#ipSeg1").val()=""; 
	 	$("#ipSeg1").focus();  
	    return false; 
	    } 

	    if (str1< 0 || str1 >255) 
	    {
		    //alert ("数字范围为0~255！");
	    $.omMessageBox.alert({
	        type:'alert',
	        title:'温馨提示',
	        content:'数字范围为0~255！',
	    }); 
	    $("#ipSeg1").val()=""; 
	 	$("#ipSeg1").focus(); 
	    return false; 
	    } 
	    else if (str2< 0 || str2 >255) 
	    {
		    //alert ("数字范围为0~255！");
		    $.omMessageBox.alert({
		        type:'alert',
		        title:'温馨提示',
		        content:'数字范围为0~255！',
		    }); 
	    $("#ipSeg1").val()=""; 
	 	$("#ipSeg1").focus(); 
	    return false; 
	    } 
	    else if (str3< 0 || str3 >255) 
	    {
		    //alert ("数字范围为0~255！");
		    $.omMessageBox.alert({
		        type:'alert',
		        title:'温馨提示',
		        content:'数字范围为0~255！',
		    }); 
	    $("#ipSeg1").val()=""; 
	 	$("#ipSeg1").focus(); 
	    return false; 
	    } 
	    else if (str4< 0 || str4 >255) 
	    {
		    //alert ("数字范围为0~255！");
		    $.omMessageBox.alert({
		        type:'alert',
		        title:'温馨提示',
		        content:'数字范围为0~255！',
		    }); 
	    $("#ipSeg1").val()=""; 
	 	$("#ipSeg1").focus(); 
	    return false; 
	    } 
    	if(ipLength==0){
    		$.omMessageBox.alert({type:'alert',title:tip,content:'IP地址段不能为空！',});
    	}else{
        	$.post('device_ip_isexists.shtml?ipSeg='+ipSeg1,null,function(result){
                if("success" == result){
                	$.omMessageTip.show({title: tip, content: "IP地址在数据库中不存在！", type:"success" ,timeout: time});
                }else{
                	$.omMessageTip.show({title: tip, content: "IP地址在数据库中已经存在！", type:"error" ,timeout: time});
                }
                $("#dialog-form1").omDialog("close");
            });
    	}
    }
    
    var validator = $('#myform').validate({
        rules : {
        	ipSeg : {required: true,
        		     //isValidIp:true,
        		     //isValidIpLength：true
            	}, 
        	maskLength : {required: true,
        				  digits:true,
        				  max:30
            	}, 
        }, 
        messages : {
        	ipSeg : {required : "IP地址段不能为空！"     		     
            	},
        	maskLength : {required : "掩码长度不能为空！",
        		          digits:"请输入数字！",
        		          max:"最大值为30！"
            		}
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
 
    $.validator.addMethod("isValidIp", function(value) {
        var ipSeg = value;
    	var ipLength = ipSeg.length;
    	var Letters = "1234567890."; 
    	var scount=0;
    	var bool = true;
        for (i=0; i<ipLength; i++) { 
         	  var CheckChar = ipSeg.charAt(i); 
         	  if (Letters.indexOf(CheckChar) == -1) 
         	  { 
             	  //alert ("IP地址格式不对!!，只能输入数字和“.”,格式为XXX.XXX.XXX.XXX 例如：192.168.0.1");      
         	 $.omMessageBox.alert({
   		        type:'alert',
   		        title:'温馨提示',
   		        content:'IP地址格式不对!!，只能输入数字和“.”,格式为XXX.XXX.XXX.XXX 例如：192.168.0.1',
   		    });
        	   bool = false;	     	   
         	   return false; 
         	  		} 
         		} ;
          for (var i = 0;i<ipLength;i++) 
          		 (ipSeg.substr(i,1)==".")?scount++:scount; 
      	    if(scount!=3) 
      	    {
          	    //alert ("IP地址格式不对!!，只能输入数字和“.”,格式为XXX.XXX.XXX.XXX 例如：192.168.0.1"); 
          	  $.omMessageBox.alert({
  		        type:'alert',
  		        title:'温馨提示',
  		        content:'IP地址格式不对!!，只能输入数字和“.”,格式为XXX.XXX.XXX.XXX 例如：192.168.0.1',
  		    });
          	 bool = false;
      	     return false; 
      	    } ; 
      	  var first = ipSeg.indexOf("."); 
     	    var last = ipSeg.lastIndexOf("."); 
     	    var str1 = ipSeg.substring(0,first); 
     	    var subip = ipSeg.substring(0,last); 
     	    var sublength = subip.length; 
     	    var second = subip.lastIndexOf("."); 
     	    var str2 = subip.substring(first+1,second); 
     	    var str3 = subip.substring(second+1,sublength); 
     	    var str4 = ipSeg.substring(last+1,ipLength); 
     	    if (str1=="" || str2=="" ||str3== "" ||str4 == "") 
     	    {
         	    //alert("数字不能为空！格式为XXX.XXX.XXX.XXX 例如：192.168.0.1");
		    $.omMessageBox.alert({
		        type:'alert',
		        title:'温馨提示',
		        content:'数字不能为空！格式为XXX.XXX.XXX.XXX 例如：192.168.0.1',
		    }); 
     	    $("#ipSeg").val()=""; 
     	 	$("#ipSeg").focus(); 
     	 	bool = false; 
     	    return false; 
     	    } 
     	    if (str1< 0 || str1 >255) 
     	    {
    		    //alert ("数字范围为0~255！");
     		    $.omMessageBox.alert({
     		        type:'alert',
     		        title:'温馨提示',
     		        content:'数字范围为0~255！',
     		    }); 
     	    $("#ipSeg").val()=""; 
     	 	$("#ipSeg").focus(); 
     	 	bool = false;
     	    return false; 
     	    } 
     	    else if (str2< 0 || str2 >255) 
     	    {
    		    //alert ("数字范围为0~255！");
     		    $.omMessageBox.alert({
     		        type:'alert',
     		        title:'温馨提示',
     		        content:'数字范围为0~255！',
     		    }); 
     	    $("#ipSeg").val()=""; 
     	 	$("#ipSeg").focus();
     	 	bool = false; 
     	    return false; 
     	    } 
     	    else if (str3< 0 || str3 >255) 
     	    { 
    		    //alert ("数字范围为0~255！");
     		    $.omMessageBox.alert({
     		        type:'alert',
     		        title:'温馨提示',
     		        content:'数字范围为0~255！',
     		    }); 
     	    $("#ipSeg").val()=""; 
     	 	$("#ipSeg").focus(); 
     	 	bool = false;
     	    return false; 
     	    } 
     	    else if (str4< 0 || str4 >255) 
     	    {
    		    //alert ("数字范围为0~255！");
     		    $.omMessageBox.alert({
     		        type:'alert',
     		        title:'温馨提示',
     		        content:'数字范围为0~255！',
     		    });  
     	    $("#ipSeg").val()=""; 
     	 	$("#ipSeg").focus();
     	 	bool = false; 
     	    return false; 
     	    };   
        return bool;
    }, 'IP地址格式不对!');
    /**
    function doSubmit1(){
    	var ipSeg = $("#ipSeg").val();
    	var ipLength = ipSeg.length;
    	var Letters = "1234567890."; 
    	var scount=0;
        for (i=0; i<ipLength; i++) { 
         	  var CheckChar = ipSeg.charAt(i); 
         	  if (Letters.indexOf(CheckChar) == -1) 
         	  { alert ("IP地址格式不对!!，只能输入数字和“.”,格式为XXX.XXX.XXX.XXX 例如：192.168.0.1");      	     	   
         	   return false; 
         	  		} 
         		} ;
          for (var i = 0;i<ipLength;i++) 
          		 (ipSeg.substr(i,1)==".")?scount++:scount; 
      	    if(scount!=3) 
      	    {alert ("IP地址格式不对!!，只能输入数字和“.”,格式为XXX.XXX.XXX.XXX 例如：192.168.0.1"); 
      	     return false; 
      	    } ; 
      	  var first = ipSeg.indexOf("."); 
     	    var last = ipSeg.lastIndexOf("."); 
     	    var str1 = ipSeg.substring(0,first); 
     	    var subip = ipSeg.substring(0,last); 
     	    var sublength = subip.length; 
     	    var second = subip.lastIndexOf("."); 
     	    var str2 = subip.substring(first+1,second); 
     	    var str3 = subip.substring(second+1,sublength); 
     	    var str4 = ipSeg.substring(last+1,ipLength); 
     	    if (str1=="" || str2=="" ||str3== "" ||str4 == "") 
     	    {alert("数字不能为空！格式为XXX.XXX.XXX.XXX 例如：192.168.0.1");
     	    $("#ipSeg").val()=""; 
     	 	$("#ipSeg").focus(); 
     	    return false; 
     	    } 
     	    if (str1< 0 || str1 >255) 
     	    {alert ("数字范围为0~255！"); 
     	    $("#ipSeg").val()=""; 
     	 	$("#ipSeg").focus(); 
     	    return false; 
     	    } 
     	    else if (str2< 0 || str2 >255) 
     	    {alert ("数字范围为0~255！"); 
     	    $("#ipSeg").val()=""; 
     	 	$("#ipSeg").focus();
     	    return false; 
     	    } 
     	    else if (str3< 0 || str3 >255) 
     	    { alert ("数字范围为0~255！"); 
     	    $("#ipSeg").val()=""; 
     	 	$("#ipSeg").focus(); 
     	    return false; 
     	    } 
     	    else if (str4< 0 || str4 >255) 
     	    {alert ("数字范围为0~255！"); 
     	    $("#ipSeg").val()=""; 
     	 	$("#ipSeg").focus(); 
     	    return false; 
     	    }
    }*/
});
</script>
</head>
<body>
<div id="center-tab">
<ul>
	 <li><a href="#tab1">IP地址库列表:</a></li>
</ul>
</div>
<table >
     <tbody>
        <tr>
        <c:if test="${sessionScope.add_device_ip != null }">
            <td><div id="create"/></td>
        </c:if>
        <c:if test="${sessionScope.update_device_ip != null }">
            <td><div id="update"/></td>
        </c:if>
        <c:if test="${sessionScope.delete_device_ip != null }">
            <td><div id="delete"/></td>
        </c:if>
        <c:if test="${sessionScope.device_ip_search != null }">
            <td><div id="search"/></td>
        </c:if>
            <td style="text-align:center;">IP地址段：</td>
            <td>
               <input type="text" name="ip" id="ip" style="height: 20px;border:1px solid #86A3C4;"/>
           </td>
           <td>
             <div id="query"/>
           </td>
        </tr>
     </tbody>
</table>
<table id="grid" ></table>
        
<div id="dialog-form">
     <form id="myform">
     <input type="hidden" value="" name="id" id="id"/>
			<table>
	            <tr>
	                <td><span class="color_red">*</span>IP地址段：</td>
	                <td><input id="ipSeg" name="ipSeg" style="width:148px;height:20px;border:1px solid #86A3C4;"/></td>
	                <td><span></span></td>
	            </tr>
	            <tr>
	                <td><span class="color_red">*</span>掩码长度：</td>
	                <td><input id="maskLength" name="maskLength" style="width:148px;height:20px;border:1px solid #86A3C4;"/></td>
	                <td><span></span></td>
	            </tr>
			</table>
	</form>
</div>

<div id="dialog-form1" style="display:none;" >
     <form id="myform1">
			<table>
	            <tr>
	                <td><span class="color_red">*</span>IP地址：</td>
	                <td><input id="ipSeg1" name="ipSeg1" style="width:148px;height:20px;border:1px solid #86A3C4;"/></td>
	                <td><span></span></td>
	            </tr>
			</table>
		</form>
	</div>					
</body>
</html>
