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
    $('#create').omButtonbar({btns : [{label:"新增",icons : {left : opPath+'add.png'}}]});
    $('#update').omButtonbar({btns : [{label:"修改",icons : {left : opPath+'op-edit.png'}}]});
    $('#delete').omButtonbar({btns : [{label:"删除",icons : {left : opPath+'remove.png'}}]});
    
    var isAdd = true;
    $('#create').bind('click', function() {
        isAdd = true;
        parent.showDefineDialog('新增价格规则','',isAdd,$("#policyGroupId").val());
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
        parent.showDefineDialog('修改价格规则信息',selections[0],isAdd,$("#policyGroupId").val());
        $.ajax({
           	type:"post",
           	url:"policy_define_to_update.json?id="+selections[0].id,
           	dataType:"json",
          	success:function(msg){
          		parent.fillDiscountPolicyDefineProductData(msg);
          		}
          });
    });
    
    $('#delete').bind('click', function(e) {
    	var selections=$('#grid').omGrid('getSelections',true);
    	var flag = parent.deleteDiscountPolicyDefineByIds(selections);
    	if(flag==false){
    		return false;
    	}
    });
    
});

function showPolicyDefine(policyGroupId){
	fillPageList(policyGroupId);
}
function fillPageList(policyGroupId){
	$("#policyGroupId").val(policyGroupId);
	$('#grid').omGrid({
    	dataSource : 'policy_define_product.json?policyGroupId='+policyGroupId,
    	width : '100%',
        height : rFrameHeight,
        singleSelect : false,
        limit : limit,
        colModel : [ 
                     {header : '<b>检查类型</b>', name : 'checkType', align : 'center', width : 80},
                     {header : '<b>检查值</b>', name : 'checkPar1', align : 'center', width : 80}, 
                     {header : '<b>折扣类型</b>', name : 'discountType', align : 'center', width : 80},
                     {header : '<b>折扣值</b>', name : 'discountPar1', align : 'center', width : 80},
                     {header : '<b>描述</b>', name : 'description', align : 'center', width : 190}
		]
    });
}

function reloadFrame(){
	$('#grid').omGrid('reload');
}
</script>
</head>
<body>
<table >
     <tbody>
			<tr>
				<c:if test="${sessionScope.add_discount_policy != null }">
					<td><div id="create"/></td>
				</c:if>
				<c:if test="${sessionScope.update_discount_policy != null }">
					<td><div id="update"/></td>
				</c:if>
				<c:if test="${sessionScope.delete_discount_policy != null }">
					<td><div id="delete"/></td>
				</c:if>
			</tr>
		</tbody>
</table>

<table id="grid" ></table>
<input type = "hidden" value = "" id = "policyGroupId">
</body>
</html>