<%@ page language="java" import="com.ysten.local.bss.device.domain.UserActiveStatistics,java.util.*,org.apache.commons.lang.StringUtils,java.text.SimpleDateFormat" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>用户开户数据统计报表</title>
<%@ include file="/include/taglibs.jsp"%>
<%@ include file="/include/operamasks-ui-2.0.jsp"%>
<%@ include file="/include/css.jsp"%>
<%@ include file="/include/ysten.jsp"%>
<script type="text/javascript">
	$(function(){
		$('#center-tab').omTabs({height:"33",border:false});
		$('#query').omButtonbar({btns : [{label:"查询",icons : {left : opPath+'search.png'}}]});
		$('#export').omButtonbar({btns : [{label:"导出",icons : {left : opPath+'op-btn-icon.png'}}]});
	    $('#activeDate').omCalendar();
	    $('#province').omCombo({dataSource : 'area.json?par=0',editable:false,width:130,listMaxHeight:160,value:'0'});
	    $("#activeDate").val(getCurrentDate());
	    $('#export').bind('click', function(e) {
	    	var activeDate = $("#activeDate").val();
	    	var province = $("#province").val();
	    	$.omMessageBox.confirm({
	        	title:'确认',
	        	content:'确定要导出这些数据吗？',
	        	onClose:function(v){
	        		if(v==true){
	            		location.href="active_usr_by_city_export.json?activeDate="+encodeURIComponent(activeDate)+"&province="+encodeURIComponent(province);               	   
	        		}
	        	}
	    	});  
		});
	    
	    $('#query').click(function(){
	    	var activeDate = $("#activeDate").val();
	    	var province = $("#province").val();
			if(activeDate == null || activeDate == ""){
		        $.omMessageBox.alert({
		        	type:'alert',
		            title:'温馨提示',
		            content:'请输入查询日期！',
		        });			
				return false;
			}
			if(province == null || province == "0"){
		        $.omMessageBox.alert({
		        	type:'alert',
		            title:'温馨提示',
		            content:'请输入查询省份！',
		        });				
				return false;
			}	    	
			$("#checkfont").html("正在查询中请稍后...");
	    	$.ajax({
	    		url : "active_usr_by_city_report.json",
	    		type : "post",
	    		data : "activeDate="+encodeURIComponent(activeDate)+"&province="+encodeURIComponent(province),
	    		dataType : "json",
	    		success : function(data){
    				var title = "<tr><td colspan=\"5\" align=\"center\" ><span id=\"topic\">用户数据</td></tr>";
    				title += "<tr>";
					title += "<td style=\"width: 100\" align=\"center\">地市</td>";
					title += "<td>销户用户</td>";
					title += "<td>总销户用户</td>";
					title += "<td>激活用户</td>";
					title += "<td>总激活用户</td>";
					title += "</tr>";	
					var body = "";
					var foot = "";
	    			if(data != null && data.length > 0){
	    				var cancelUserTotal = 0;
	    				var cancelUsersTotal = 0;
	    				var activeTotal = 0;
	    				var activesTotal = 0;
	    				$.each(data, function(i, item){
	    					var cancelUser = Number(item['cancelUser'] == null ? 0 : item['cancelUser']);
	    					var cancelUsers = Number(item['cancelUsers'] == null ? 0 : item['cancelUsers']);
	    					var active = Number(item['activeUser'] == null ? 0 : item['activeUser']);
	    					var actives = Number(item['activeUsers'] == null ? 0 : item['activeUsers']);
	    					cancelUserTotal += cancelUser;
	    					cancelUsersTotal += cancelUsers;
	    					activeTotal += active;
	    					activesTotal += actives;
	    					var tr = "<tr>";
	    					tr += "<td style=\"width: 100\" align=\"center\">"+item['cityName']+"</td>";
	    					tr += "<td>"+cancelUser+"</td>";
	    					tr += "<td>"+cancelUsers+"</td>";
	    					tr += "<td>"+active+"</td>";
	    					tr += "<td>"+actives+"</td>";
	    					tr += "</tr>";
	    					body += tr;
	    				});
	    			 	foot = "<tr>";
	    				foot += "<td style=\"width: 100\" align=\"center\">总计</td>";
	    				foot += "<td>"+cancelUserTotal+"</td>";
	    				foot += "<td>"+cancelUsersTotal+"</td>";
	    				foot += "<td>"+activeTotal+"</td>";
	    				foot += "<td>"+activesTotal+"</td>";
	    				foot + "</tr>";
	    			}else{
	    			 	foot = "<tr>";
	    				foot += "<td style=\"width: 100\" align=\"center\">总计</td>";
	    				foot += "<td colspan=\"4\"><font color=\"red\" id=\"checkfont\">暂无数据！</font></td>";
	    				foot + "</tr>";
	    			}
	    			$("#grid").html(title + body + foot);
	    		}
	    	});
	    });
	    
	});
</script>
<style type="text/css">
	.ys {border-collapse:collapse; width:80%}
	.ys tr {width: 700px; height:26px}
	.ys td {text-align:center; border:#C5CDDA 1px solid; width:200px}
</style> 
</head>
<body>
<div id="center-tab">
	<ul>
		 <li><a href="#tab1">用户激活|销户数据统计:</a></li>
	</ul>
	</div>
<div style="overflow-y: auto; height: 560px;">
	<center>
		<br/>
		<table >  
			<tr>
				<td>激活  | 销户时间：</td>
				<td><input id="activeDate" name="activeDate" style="width:162px;"/></td>
				<td>查询省份：</td>
				<td><input id="province" name="province" style="width:110px"/></td> 
				<td><div id="query"/></td>
				<c:if test="${sessionScope.export_user_statistics != null }">
				<td><div id="export"/></td>
				</c:if>
			</tr>     		          
		</table>
		
	    <br/>
		<table class="ys" id="grid" border-collapse="collapse" style="height: 99%;width: 80%;">
			<tr><td colspan="5" align="center" ><span id="topic">用户数据</td></tr>
			<tr>
				<td style="width: 100" align="center">地市</td>
				<td>销户用户</td>
				<td>总销户用户</td>
				<td>激活用户</td>
				<td>总激活用户</td>
			</tr>
			
			<tr>
				<td style="width: 100" align="center">总计</td>
				<td colspan="4"><font color="red" id="checkfont">请输入查询条件</font></td>
			</tr>		
		</table>
	</center>
</div>	
</body>
</html>