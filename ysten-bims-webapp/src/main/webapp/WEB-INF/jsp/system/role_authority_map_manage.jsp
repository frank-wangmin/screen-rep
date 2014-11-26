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
<style>
label {
	cursor: pointer;
}
</style>
<script type="text/javascript">
function Select()
{
    var nLen = 0;
    var objFrom = document.all("selFrom");
    var objTo = document.all("selTo");
    var bValue;
    for(nLen; nLen<objFrom.length;)
    {
        bValue = objFrom.options.item(nLen).selected;
        if(bValue)
        {
            var objOption = document.createElement("OPTION");
            objOption.text =  objFrom.options.item(nLen).text;
            objOption.value = objFrom.options.item(nLen).value;
            objOption.selected = true;
            objTo.add(objOption);
            objFrom.remove(objFrom.options.selectedIndex);
            nLen = 0;
        }
        else
        {
            nLen++;
        }
    }
}

function SelectAll()
{
    var i,j,ba;
    var objFrom = document.all("selFrom");
    var objTo = document.all("selTo");
    var fromLen = objFrom.length;
    var toLen = objTo.length;
    var nLen = 0;
    var k=0;
    for(nLen; nLen<fromLen;nLen++)
    {
        ba=0;
        if(ba==0)
        {
            var objOption = document.createElement("OPTION");
            if(nLen==0)
            {
            	objOption.selected = true;
            }
            objOption.text =  objFrom.options.item(nLen).text;
            objOption.value = objFrom.options.item(nLen).value;
            objTo.options.add(objOption);
        }
    }
    for(k; k<fromLen;k++)
    {
        objFrom.remove(objFrom.options.item[k]);

    }
}
function SelectNone()
{
    var i,j,ba;
    var objFrom = document.all("selFrom");
    var objTo = document.all("selTo");
    var fromLen = objFrom.length;
    var toLen = objTo.length;
    var nLen = 0;
    var k=0;
    for(nLen; nLen<toLen;nLen++)
    {
        ba=0;
        if(ba==0)
        {
            var objOption = document.createElement("OPTION");
            if(nLen==0)
            {
            	objOption.selected = true;
            }
            objOption.text =  objTo.options.item(nLen).text;
            objOption.value = objTo.options.item(nLen).value;
            objFrom.options.add(objOption);
        }
    }
    for(k; k<toLen;k++)
    {
        objTo.remove(objTo.options.item[k]);
    }
}

function DeSelect()
{
    var nLen = 0;
    var objFrom = document.all("selFrom");
    var objTo = document.all("selTo");
    var bValue;
    for(nLen; nLen<objTo.options.length;)
    {
        bValue = objTo.options.item(nLen).selected;
        if(bValue)
        {
            var objOption = document.createElement("OPTION");
            objOption.text = objTo.options.item(nLen).text;
            objOption.value = objTo.options.item(nLen).value;
            objOption.selected = true;
            objFrom.add(objOption);
            objTo.remove(objTo.options.selectedIndex);
            nLen=0;
        }
        else
        {
            nLen++;
        }
    }
}



function doSubmit()
{
	var roleId = $("#roleId").val();
	var n = $("#selFrom")[0];
	var s=$("#selFrom").find("option").length;
	if(roleId==0)
	{
		$.omMessageBox.alert({type:'alert',title:tip,content:'请选择角色！',});
		return false;
	}
	else
	{
		var v = '';
		var t = '';
		for(var i = 0; i < s; i++)
		{
			if(i==0)
			{
				t = n.options[i].text;
				v = n.options[i].value;
			}else
			{
				t += ','+n.options[i].text;
				v += ','+n.options[i].value;
			}
		}
		$("#rId").attr("value",v);
		$.post('role_authority_map_manage.shtml',{roleId:$("#roleId").val(),rId:$("#rId").val()},function(result){
            if(result=='success'){
            	$.omMessageTip.show({title: tip, content: "角色与权限信息关联成功！", timeout: time,type:'success'});
            }else{
            	$.omMessageTip.show({title: tip, content: "角色与权限信息关联失败！", timeout: time,type:'error'});
            }
        });
	}
	
}
function dynamicRoleAuthority()
{
    $.ajax({
    type:"post",
    url:"dynamicRoleAuthority.shtml",
    async:false,
   dataType:"json",
   data:"roleId="+$("#roleId").val(),
   success:function(json)
        { 
            $("#selFrom option").each(function(i,s){
                    $(s).remove();
                });
            var d;
            if(json[0]!=null)
            {
	            var len=json[0].length;
	            var dt= '${param.selFrom}';
	            if(len!=0)
	             {
	                for(var i=0;i<len;i++)
	                {   
	                    if(json[0][i]!=null)
	                    {
		                  if(json[0][i].id == dt)
		                  {
		                     d=d +"<option selected value="+json[0][i].id+">"+json[0][i].name+"</option>";
		                  }else
		                  {
		                     d=d+"<option value="+json[0][i].id+">"+json[0][i].name+"</option>";
		                  }
	                    }
	                }
	            }
	            $(d).appendTo("#selFrom");
            }
            
            $("#selTo option").each(function(i,s){
                $(s).remove();
            });
        var d1;
        if(json[1]!=null)
        {
	        var len1=json[1].length;
	        var dt1= '${param.selTo}';
	        if(len1!=0)
	         {
	            for(var i=0;i<len1;i++)
	            {   
	                if(json[1][i]!=null)
	                {
	                  if(json[1][i].id == dt1)
	                  {
	                     d1 = d1 +"<option selected value="+json[1][i].id+">"+json[1][i].name+"</option>";
	                  }else
	                  {
	                     d1 = d1 +"<option value="+json[1][i].id+">"+json[1][i].name+"</option>";
	                  }
	                }
	            }
	        }
	        $(d1).appendTo("#selTo");
	     }                       
        }
    });
    $("#roleId").blur();
}

$(document).ready(function() {
	$("#save").omButton();
	$("#back").omButton();
	$("#allRemove").omButton();
	$("#rightRemove").omButton();
	$("#leftRemove").omButton();
	$("#allCancel").omButton();
});
</script>
</head>
<body>
	<%
		try {
	%>
			<form id="myform" action="${cxp}/role_authority_map_manage.shtml" method="post">
					<h2 class="underline margintop"> 角色与权限关联关系管理：</h2>
					<input type="hidden" value="" name="rId" id ="rId" />
						<center>
						<table width="50%" style="border: solid 1px;border-color: #BAD5EC;">
							<thead>
							<tr style="height: 20px;">
									<td colspan="3" style="text-align: center;"></td>
								</tr>
								<tr style="height: 20px;">
									<td colspan="3" style="text-align: center;">角色名称：
										<select name="roleId" id="roleId"  style="width:140px;height:24px;border:1px solid #86A3C4;" onchange="dynamicRoleAuthority();">
                                           <option value="0">==请选择角色==</option> 
                                            <c:forEach items="${role}" var="role">
                                                <option <c:if test="${role.id == param.roleId }">selected</c:if> value="${role.id}">${role.name}</option>
                                            </c:forEach>
                                    	</select>
									</td>
								</tr>
								<tr style="height: 20px;">
									<td style="text-align: center;">全部权限</td>
									<td style="text-align: center;"></td>
									<td style="text-align: center;">已选权限</td>
								</tr>
								<tr style="height: 100px;text-align: center;">
									<td>
								<select size="8"  id="selTo" name="selTo" style="width: 150px; height: 290px" ondblclick="DeSelect()" multiple="multiple">
                				</select>
									</td>
									<td align=center valign="middle" style="width: 80px;">
									<input id="allRemove" type="button" value="全部选中" onclick="SelectNone();"/><br/><br/>
									<input id="rightRemove" type="button" value="添加 >> " onclick="DeSelect();"/><br/><br/>
									<input id="leftRemove" type="button" value=" &lt;&lt; 移除" onclick="Select();"/><br/><br/>
									<input id="allCancel" type="button" value="全部取消" onclick="SelectAll();"/>
            						</td>

									<td style="text-align: center;">
										<select size="10"  id="selFrom" name="selFrom" style="width: 150px; height: 290px;" ondblclick="Select()" multiple="multiple">
						                    
	                					</select>
									</td>
								</tr>
								<tr style="height: 20px;">
									<td style="text-align: center;"></td>
									<td style="text-align: center;">
										<input id="save" type="button" value="保存" onclick="return doSubmit();"/>
										<input id="back" type="button" value="重置" onclick="window.location.href='${cxp}/role_authority_map_to_manage.shtml'"/>
									</td>
									<td style="text-align: center;"></td>
								</tr>
								<tr style="height: 20px;">
									<td style="text-align: center;"></td>
									<td style="text-align: center;"></td>
									<td style="text-align: center;"></td>
								</tr>
							</thead>
						</table>
						</center>
			</form>
	<%
		} catch (Exception e) {
			e.printStackTrace();
		}
	%>

</body>
</html>