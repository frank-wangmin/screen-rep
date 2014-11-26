<%@ page language="java" import="com.ysten.local.bss.device.domain.City,java.util.*,org.apache.commons.lang.StringUtils,java.text.SimpleDateFormat" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>地方终端设备激活数据</title>
<%@ include file="/include/taglibs.jsp"%>
<%@ include file="/include/operamasks-ui-2.0.jsp"%>
<%@ include file="/include/css.jsp"%>
<%@ include file="/include/ysten.jsp"%>
<script type="text/javascript">
$(function(){
	$('#center-tab').omTabs({height:"33",border:false});
	$('#query').omButtonbar({btns : [{label:"查询",icons : {left : opPath+'search.png'}}]});
	$('#export').omButtonbar({btns : [{label:"导出",icons : {left : opPath+'op-btn-icon.png'}}]});
	$('#activeTime').omCalendar();
	$("#activeTime").val(getCurrentDate());
	$('#province').omCombo({dataSource:'area.json?par=0',editable:false, width:130, listMaxHeight:160, value:'0'});
	$('#export').bind('click', function(e) {
		var activeTime = $("#activeTime").val();
		var province = $("#province").val();
    	$.omMessageBox.confirm({
        	title:'确认',
        	content:'确定要导出这些数据吗？',
        	onClose:function(v){
        		if(v==true){
            		location.href="active_device_by_city_export.json?activeTime="+encodeURIComponent(activeTime)+"&province="+encodeURIComponent(province);               	   
        		}
        	}
    	});  
	});
	$("#query").click(function(){
		var activeTime = $("#activeTime").val();
		var province = $("#province").val();
		if(activeTime == null || activeTime == ""){
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
		var areaname = "";
     	$.post('area_id.json',{id:province},function(result){
        	areaname = result['name'];
     	});		
		$.ajax({
			url : "active_device_by_city_report.json",
			data : "activeTime="+encodeURIComponent(activeTime)+"&province="+encodeURIComponent(province),
			type : "post",
			dataType : "json",
			success : function(data){
				if(data == null || data.length == 0){
					var header = "<tr><td colspan=\"5\" ><span id=\"topic\">"+activeTime+areaname+"设备激活数据</span></td></tr>";
					var body = "<tr><td>总计</td><td style=\"width: 200\" colspan=\"4\"><font id=\"checkfont\" color=\"red\">暂无数据！</font></td></tr>";
					$("#grid").html(header+body);
				}else{
					var colspan = 2;
					for(var i=1; i++; i<data.length){
						if(data[i-1]['cityName'] != data[i]['cityName']){
							break;
						}		
						colspan = i + 1;
					}
					var isTypeTitle = true; 
					var header = "<tr><td colspan=\""+(colspan+1)+"\" ><span id=\"topic\">"+activeTime+areaname+"设备激活数据</span></td></tr>";
					header += "<tr><td rowspan=\"2\">&nbsp;</td><td colspan=\""+(colspan/2)+"\" >当日激活设备</td><td colspan=\""+(colspan/2)+"\" >激活总设备</td></tr>";
					var title = "<tr>";
					var body = "";
					$.each(data, function(i, item){
						var preIndex = i - 1;
						if(i == 0){
							preIndex = 0;
						}						
						if(item['cityName'] == 'type-total'){
							if(colspan > 2){
								if(data[preIndex]['cityName'] != item['cityName']){
									body += "</tr><tr><td rowspan=\"2\">合计</td><td>"+item['rowTotal']+"</td>";
								}else{
									body += "<td>"+item['rowTotal']+"</td>";
								}								
							}
						}else if(item['cityName'] == 'total'){
							if(data[preIndex]['cityName'] != item['cityName']){
								if(colspan < 3){
									body += "</tr><tr><td>合计</td><td>"+item['rowTotal']+"</td>";
								}else{
									body += "</tr><tr><td colspan=\""+(colspan/2)+"\">"+item['rowTotal']+"</td>";
								}								
							}else{
								body += "<td colspan=\""+(colspan/2)+"\">"+item['rowTotal']+"</td></tr>";
							}
						}else{
							if(data[preIndex]['cityName'] != item['cityName']){
								isTypeTitle = false;
								body += "</tr><tr><td>"+item['cityName']+"</td><td>"+item['rowTotal']+"</td>";
							}else{
								if(i < colspan){
									if(0 == i){
										body += "</tr><tr><td>"+item['cityName']+"</td>";
									}
									body += "<td>"+item['rowTotal']+"</td>";
								}								
								if(isTypeTitle){
									title += "<td>"+item['deviceTypeName']+"</td>";
								}else{
									body += "<td>"+item['rowTotal']+"</td>";
								}								
							}	
						}
					});
					$("#grid").html(header+title+body);
				}
			}
		});
	});
});

</script>
<style type="text/css">
	.ys {border-collapse:collapse; width:80%}
	.ys tr {width: 700px; height:26px}
	.ys td {text-align:center; border:#C5CDDA 1px solid; width:100px}
</style> 
</head>

<body>
<div id="center-tab">
<ul>
	 <li><a href="#tab1">地方终端设备激活数据:</a></li>
</ul>
</div>
<div style="overflow-y: auto; height: 560px;">
	<center>
		<br />
	    <table>       		
	        <tr>
	       	<td>激活时间：</td>
	           <td><input id="activeTime" name="activeTime" style="width:163px;" value="" /></td>
	           <td>查询省份：</td>
	           <td><input id="province" name="province" style="width:110px;"/></td> 
	           <td colspan="13"><div id="query"/></td>
	           <c:if test="${sessionScope.export_device_statistics != null }">
	           <td colspan="13"><div id="export"/></td>
	           </c:if>
	         </tr>
	     </table>
	     <br />
		 <table class="ys" id="grid" style="height: 99%;width: 80%;">
			<tr><td colspan="5" ><span id="topic">终端设备激活数据</span></td></tr>	
			<tr>
				<td>总计</td>
				<td style="width: 200" colspan="4"><font id="checkfont" color="red">请输入查询条件</font></td>
			</tr>
		 </table>     
	</center>
</div>
</body>
</html>