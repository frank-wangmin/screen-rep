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
    	dataSource : 'lvoms_district_code_url_list.json',
    	width : '100%',
        height : rFrameHeight,
        singleSelect : false,
        limit : limit,
        colModel : [ 
                     {header : '<b>区域</b>', name : 'districtCode', align : 'center', width : 190},
                     {header : '<b>模版Id</b>', name : 'packageId', align : 'center', width : 200},
                     {header : '<b>模版名称</b>', name : 'packageName', align : 'center', width : 200},
                     {header : '<b>状态</b>', name : 'status',align : 'center',width:150, renderer: function (value) {
                         if (value == 'ONLINE') {
                             return "已上线";
                         }
                         if (value == 'OFFLINE') {
                             return "已下线";
                         }
                         else {
                             return "";
                         }
                     }},
                     {header : '<b>地址URL</b>', name : 'url',align : 'center',width: 'autoExpand'}
                     
		],
    	rowDetailsProvider:function(rowData,rowIndex){
        	var status = "";
        	if (rowData.status == 'ONLINE') {
        		status = "已上线";
            }else{
            	status = "已下线";
            }
        	return '第'+rowIndex+'行'
        		+'</br>模版Id='+rowData.packageId
        		+'</br>模版名称='+rowData.packageName
        		+'</br>地址URL='+rowData.url
        		+', 区域='+rowData.districtCode
        		+', 状态='+status
    	}
    });
    $('#center-tab').omTabs({height:"33",border:false});
    $('#query').omButtonbar({btns : [{label:"查询",icons : {left : opPath+'search.png'}}]});
    $('#create').omButtonbar({btns : [{label:"新增",icons : {left : opPath+'add.png'}}]});
	$('#update').omButtonbar({btns: [{label: "修改", icons: {left: opPath + 'op-edit.png'}}]});
    $('#delete').omButtonbar({btns: [{label: "删除", icons: {left: opPath + 'remove.png'}}]});
    $('#packageId1').omCombo({dataSource : 'get_panel_package_list_of_area.json',editable:false,listMaxHeight:150,width:180,value:''});
    $('#distCode1').omCombo({dataSource : 'city_province.json',editable:false,listMaxHeight:150,width:180,value:'',onValueChange: function (target, newValue, oldValue, event) {
    	getPackage();
    }});
    $('#status1').omCombo({
        dataSource : [ 
                      {text : '已上线', value : 'ONLINE'}, 
                       {text : '已下线', value : 'OFFLINE'}
                      ],value:'',width:180
    });
    $('#query').bind('click', function(e) {
        var packageId = $('#packageId1').val();
        var distCode = $('#distCode1').val();
        var status = $('#status1').val();
        $('#grid').omGrid("setData", 'lvoms_district_code_url_list.json?packageId='+encodeURIComponent(packageId)+'&distCode='+encodeURIComponent(distCode)+'&status='+encodeURIComponent(status));
    });
    var isAdd = true;
    $('#create').bind('click', function() {
        isAdd = true;
        $('#status').omCombo({
            dataSource : [ 
                          {text : '已上线', value : 'ONLINE'}, 
                           {text : '已下线', value : 'OFFLINE'}
                          ],value:'',width:180
        });
        $('#districtCode').omCombo({dataSource : 'city_province.json',editable:false,listMaxHeight:160,width:180,value:'',onValueChange: function (target, newValue, oldValue, event) {
        	getPackage();
        }});
        $('#packageId').omCombo({dataSource : 'get_panel_package_list_of_area.json',editable:false,listMaxHeight:150,width:180,value:''});
        showDialog('新增电视看点区域URL信息');//显示dialog
    });
    $('#update').bind('click', function () {
        var selections = $('#grid').omGrid('getSelections', true);
        if (selections.length == 0 || selections.length > 1) {
            $.omMessageBox.alert({
                type: 'alert',
                title: '温馨提示',
                content: '修改操作至少且只能选择一行记录！',
            });
            return false;
        }
        isAdd = false;
        $.ajax({
            type: "post",
            url: "lvoms_district_code_url_to_update.json?id=" + selections[0].id,
            dataType: "json",
            success: function (msg) {
                $("#id").val(msg['id']);
                $('#status').omCombo({
                    dataSource : [ 
                                  {text : '已上线', value : 'ONLINE'}, 
                                   {text : '已下线', value : 'OFFLINE'},
                                  ],value:msg['status'],width:180
                });
                $('#districtCode').omCombo({dataSource : 'city_province.json',editable:false,listMaxHeight:150,width:180,value:msg['districtCode'], onValueChange: function (target, newValue, oldValue, event) {
                	getPackageUpdate(msg['districtCode'], msg['packageId']);
                }});
                $('#packageId').omCombo({dataSource : 'get_panel_package_list_of_area.json',editable:false,listMaxHeight:150,width:180,value:msg['packageId']});
            	$("#url").val(msg['url']);
            }
        });

        showDialog('修改电视看点区域URL信息', selections[0]);
    });
    var showDialog = function(title,rowData){
        validator.resetForm();
        rowData = rowData || {};
        dialog.omDialog("option", "title", title);
        dialog.omDialog("open");
    };
    var dialog = $("#dialog-form").omDialog({
        width: 500,
        height: 250,
        autoOpen : false,
        modal : true,
        resizable : false,
        draggable : false,
        buttons : {
            "提交" : function(){
                	var result = $("#showResult").val();
                	if(result=='' || result =='可用!'){
                		submitDialog();
                    	}
                	return false; 
            },
            "取消" : function() {
                $("#dialog-form").omDialog("close");
            }
        },onClose:function(){
            $("#dialog-form").omDialog("close");
            $("#showResult").text("");
            $('#dialog-form').validate().resetForm();
        }
    }); 
    var submitDialog = function(){
        var submitData;
        if (validator.form()) {
            if(isAdd){
            	submitData={
            			districtCode:$("#districtCode").val(),
                    	packageId:$("#packageId").val(),
                    	status:$("#status").val(),
                    	url:$("#url").val()
                    };
            	$.post('lvmos_districtCode_url_add.json',submitData,function(result){
                    $('#grid').omGrid('reload');
                    if("success" == result){
                    	$.omMessageTip.show({title: tip, content: "新增电视看点区域URL信息成功！", type:"success" ,timeout: time});
                    }else{
                    	$.omMessageTip.show({title: tip, content: "新增电视看点区域URL信息失败！", type:"error" ,timeout: time});
                    }
                    $("#dialog-form").omDialog("close");
                });
            }else{
            	submitData={
            			id:$("#id").val(),
            			districtCode:$("#districtCode").val(),
                    	packageId:$("#packageId").val(),
                    	status:$("#status").val(),
                    	url:$("#url").val()         			
                    };
            	$.post('lvmos_districtCode_url_update.json',submitData,function(result){
                    $('#grid').omGrid('reload');
                    if("success" == result){
                    	$.omMessageTip.show({title: tip, content: "修改电视看点区域URL信息成功！", type:"success" ,timeout: time});
                    }else{
                    	$.omMessageTip.show({title: tip, content: "修改电视看点区域URL信息失败！"+result, type:"error" ,timeout: time});
                    }
                    $("#dialog-form").omDialog("close"); 
                });
            }
        }
    };
    $('#delete').bind('click', function (e) {
        var selections = $('#grid').omGrid('getSelections', true);
        if (selections.length == 0) {
            $.omMessageBox.alert({
                type: 'alert',
                title: '温馨提示',
                content: '删除操作至少选择一行记录！',
            });
            return false;
        }
        $.omMessageBox.confirm({
            title: '确认删除',
            content: '批量删除后数据将无法恢复，你确定要执行该操作吗？',
            onClose: function (v) {
                if (v == true) {
                    var toDeleteRecordID = "";
                    for (var i = 0; i < selections.length; i++) {
                        if (i != selections.length - 1) {
                            toDeleteRecordID += selections[i].id + ",";
                        } else {
                            toDeleteRecordID += selections[i].id;
                        }
                    }
                    $.post('lvmos_districtCode_url_delete.json', {ids: toDeleteRecordID.toString()}, function (result) {
                        if ("success" == result) {
                            $.omMessageTip.show({title: tip, content: "删除电视看点Url成功!", type: "success", timeout: time});
                        } else {
                            $.omMessageTip.show({title: tip, content: "删除电视看点Url失败!", type: "error", timeout: time});
                        }
                        $('#grid').omGrid('reload');
                        $('#dialog-form').omDialog('close');
                    });
                }
            }
        });
    });
    var validator = $('#myform').validate({
        rules : {      	   
        	districtCode:{required: true},
        	packageId:{required: true},
        	status:{required: true},
        	url:{required: true,maxlength: 250}
        }, 
        messages : {        	
        	districtCode:{required: "区域不能为空!"},
        	packageId:{required: "模版不能为空!"},
        	status:{required: "状态不能为空!"},
        	url:{required: "地址URL不能为空!",maxlength: 250}
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
});
function getPackage() {
    var distCode1 = $('#distCode1').omCombo('value');
    $('#packageId1').omCombo({dataSource: 'get_panel_package_list_of_area.json?par=0&distCode=' + distCode1, editable: false, width: 180, listMaxHeight: 150, value: ''});
    var distCode = $('#districtCode').omCombo('value');
    $('#packageId').omCombo({dataSource: 'get_panel_package_list_of_area.json?par=0&distCode=' + distCode, editable: false, width: 180, listMaxHeight: 150, value: ''});

}

function getPackageUpdate(distCode, packageId) {
    var districtCode = $('#districtCode').omCombo('value');
    if (districtCode == distCode) {
        $('#packageId').omCombo({dataSource: 'get_panel_package_list_of_area.json?par=0&distCode=' + districtCode, editable: false, width: 180, listMaxHeight: 150, value: packageId});
    } else {
        $('#packageId').omCombo({dataSource: 'get_panel_package_list_of_area.json?par=0&distCode=' + districtCode, editable: false, width: 180, listMaxHeight: 150, value: ''});
    }
}
function checkExist(){
	if($.trim($("#districtCode").val())!=""){
		   var par = new Object();
		   par['districtCode'] = $("#districtCode").val();	 
		   par['packageId'] = $("#packageId").val();	  
		   par['id'] = $("#id").val();
	       $.ajax({
	       type:"post",
	       url:"lvmos_url_of_distCode_exists.shtml?s="+Math.random(),
	       dataType:"html",
	       data:par,
	       success:function(msg){
	    	 //alert(msg);   
	           $("#showResult").html(msg);
	       }
	      });
	}else{
		$("#showResult").text("");
		}
}
</script>
</head>
<body>
<div id="center-tab">
<ul>
	 <li><a href="#tab1">电视看点栏目运营商各省推荐URL信息列表:</a></li>
</ul>
</div>
<table >
    <tr>
          <td style="text-align:center;">&nbsp;区域：</td>
          <td>
          		<input type="text" name="distCode1" id="distCode1"/></td>
          <td style="text-align:center;">&nbsp;模版：</td>
          <td>
          		<input name="packageId1" id="packageId1" /></td>
          <td style="text-align:center;">&nbsp;状态：</td>
          <td>
          		<input name="status1" id="status1"/></td>		
           <td><div id="query"/></td>
           <c:if test="${sessionScope.add_lvoms_district_code_url != null }">
            <td>
                <div id="create"/>
            </td>
            </c:if>
            <c:if test="${sessionScope.update_lvoms_district_code_url != null }">
	            <td>
	                <div id="update"/>
	            </td>
	         </c:if>
	         <c:if test="${sessionScope.delete_lvoms_district_code_url != null }">
	            <td>
	                <div id="delete"/>
	            </td>
	         </c:if>
  </tr>
</table>
<table id="grid" ></table>
 <div id="dialog-form">
     <form id="myform">
     <input type="hidden" value="" name="id" id="id"/>
			<table>
	            <tr>
	                <td><span class="color_red">*</span>区域：</td>
	                <td><input type="text" name="districtCode" id="districtCode"/></td>
	                <td><span></span></td>
	            </tr>	            
	            <tr>
	                <td><span class="color_red">*</span>模版：</td>
	               	<td><input name="packageId" id="packageId" onblur="checkExist();"/></td>
	               	<td><span></span><div id="showResult" class="color_red" style="display: inline;"/></td>
	            </tr>	            
	            <tr>
	                <td><span class="color_red">*</span>状态：</td>
	                <td><input id="status" name="status" style="width:129px"/></td>
	                <td><span></span></td>
	            </tr>	            
	            <tr>
	                <td><span class="color_red">*</span>地址URL：</td>
	                <td><textarea id="url" name="url" cols="28" rows="6" style="border:1px solid #86A3C4;" maxlength="250"></textarea></td>
	                <td><span></span></td>
	            </tr>
			</table>
	</form>
</div>
</body>
</html>
