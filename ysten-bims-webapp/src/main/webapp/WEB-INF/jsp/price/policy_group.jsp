<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/include/taglibs.jsp"%>
<%@ include file="/include/operamasks-ui-2.0.jsp"%>
<%@ include file="/include/css.jsp"%>
<%@ include file="/include/ysten.jsp"%>
<title></title>
<script language="javascript">
$(document).ready(function() {
	var rows;
    $('#grid').omGrid({
    	dataSource : 'policy_group_list.json',
    	width : '100%',
        height : rFrameHeight,
        singleSelect : true,
        limit : limit,
        colModel : [ 
                     {header : '<b>价格策略名称</b>', name : 'name', align : 'center', width : 110},
                     {header : '<b>创建时间</b>', name : 'createDate', align : 'center', width : 120},
                     {header : '<b>描述</b>', name : 'description', align : 'center', width : 200}
		],
		onRowClick : function(index,rowData,event){
			parent.showPolicyDefineProduct(rowData.id);
			
		},
		onSuccess:function(data,testStatus,XMLHttpRequest,event){
			rows = data.rows;
			if(rows!=''&&typeof(rows)!="undefined"){
				parent.showPolicyDefineProduct(rows[0]['id']);
			}else{
				parent.showPolicyDefineProduct('');
			}
	     }
    });
    
    $('#create').omButtonbar({btns : [{label:"新增",icons : {left : opPath+'add.png'}}]});
    $('#update').omButtonbar({btns : [{label:"修改",icons : {left : opPath+'op-edit.png'}}]});
    $('#delete').omButtonbar({btns : [{label:"删除",icons : {left : opPath+'remove.png'}}]});
    $('#query').omButtonbar({btns : [{label:"查询",icons : {left : opPath+'search.png'}}]});
    
    //搜索按钮提交事件
    $('#query').bind('click', function(e) {
        var discountPolicyName = filterStartEndSpaceTrim($('#discountPolicyName').val());
        $('#grid').omGrid("setData", 'policy_group_list.json?discountPolicyName='+encodeURIComponent(discountPolicyName));
    });
    
    var isAdd = true;
    $('#create').bind('click', function() {
        isAdd = true;
        parent.showDialog('新增优惠策略组信息','',isAdd);
    });
    
    $('#update').bind('click', function() {
        var selections=$('#grid').omGrid('getSelections',true);
        if (selections.length == 0 || selections.length > 1) {
        	 window.top.$.omMessageBox.alert({
                type:'alert',
                title:'温馨提示',
                content:'修改操作至少且只能选择一行记录！',
            });
            return false;
        }
        isAdd = false;
        parent.showDialog('修改优惠策略组信息',selections[0],isAdd);
        $.ajax({
           	type:"post",
           	url:"policy_group_to_update.json?id="+selections[0].id,
           	dataType:"json",
          	success:function(msg){
          		parent.fillDiscountPolicyGroupData(msg);
          		}
          });
    });
    
    $('#delete').bind('click', function(e) {
    	var selections=$('#grid').omGrid('getSelections',true);
    	var flag = parent.deleteDiscountPolicyGroupById(selections);
    	if(flag==false){
    		return false;
    	}
    });
});

function reloadFrame(){
	$('#grid').omGrid('reload');
}
   
   
</script>
</head>
<body>
	<table>
		<tbody>
			<tr>
				<c:if test="${sessionScope.add_discount_policy_group != null }">
					<td><div id="create"/></td>
				</c:if>
				<c:if test="${sessionScope.update_discount_policy_group != null }">
					<td><div id="update"/></td>
				</c:if>
				<c:if test="${sessionScope.delete_discount_policy_group != null }">
					<td><div id="delete"/></td>
				</c:if>
				<td style="text-align: center;">策略名称：</td>
				<td><input name="discountPolicyName" id="discountPolicyName"
					style="height: 20px; border: 1px solid #86A3C4;" /></td>
				<td>
				<td><div id="query"/></td>
			</tr>
		</tbody>
	</table>
	<table id="grid"></table>
</body>
</html>