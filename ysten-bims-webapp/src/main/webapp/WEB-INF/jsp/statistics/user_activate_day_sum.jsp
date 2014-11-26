<%@ page language="java" import="java.util.*,org.apache.commons.lang.StringUtils,java.text.SimpleDateFormat" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>终端激活定时统计信息</title>
<%@ include file="/include/taglibs.jsp"%>
<%@ include file="/include/operamasks-ui-2.0.jsp"%>
<%@ include file="/include/css.jsp"%>
<%@ include file="/include/ysten.jsp"%>
<script type="text/javascript">
$(document).ready(function() {
	$('#province').omCombo({dataSource : 'area.json?par=0',editable:false,width:130,listMaxHeight:160,value:'0'});
	var provinceDataSource = null;
	$.get('area.json?par=0', function(data){
		provinceDataSource = data;
	});
	
	var spDataSource = null;
	$.get('sp_define.json', function(data){
		provinceDataSource = data;
	});
	
	$('#date').omCalendar(({dateFormat : 'yy-mm-dd'}));
	$('#sync').omCombo({
         dataSource : [ 
				       {text : '全部', value : ''},
                       {text : '未同步', value : 'NOSYNC'}, 
                        {text : '已同步', value : 'SYNC'}
                       ],value:''
     });
    
	$('#grid').omGrid({
	    dataSource : 'usr_active_sum_list.json',
    	width : '100%',
        height : rFrameHeight,
        singleSelect : true,
        limit : limit,
        colModel : [ 
					{header : '<b>统计日期</b>', name : 'date', align : 'center', width : 120},
					{header : '<b>省份</b>', name : 'provinceId', align : 'center', width : 50, renderer:function(value, row, index, options){
						var text = value;
						$.each(options.area, function(i, item){
							if(item.value == value){
								text =  item.text;
							}
						})
						return text;
					}}, 
					{header : '<b>地市</b>', name : 'cityId',align : 'center',width: 50,renderer:function(value, row, index, options){
						var text = value;
						$.each(options.city, function(i, item){
							if(item.value == value){
								text =  item.text;
							}
						})
						return text;
					}},
					{header : '<b>运营商</b>', name : 'telecomId',align : 'center',width: 80, renderer:function(value){
						/**var text = "";
						$.each(spDataSource, function(index, item){
							if(item.value === value){
								text =  item.text;
							}
						})
						return text; */
						return '移动';
						}},
					{header : '<b>厂商</b>', name : 'vendorId',align : 'center',width: 80},
					{header : '<b>终端类型</b>', name : 'terminalId',align : 'center',width: 60},
					{header : '<b>是否同步</b>', name : 'sync',align : 'center',width: 60},
					{header : '<b>同步时间</b>', name : 'syncDate',align : 'center',width: 150},
					{header : '<b> 当日激活终端数 </b>', name : 'activateDay',align : 'center',width: 120},
					{header : '<b>总激活数</b>', name : 'activateAll',align : 'center',width: 150},
					{header : '<b>新增开户数</b>', name : 'userDay',align : 'center',width: 120},
					{header : '<b>总开户数</b>', name : 'userAll',align : 'center',width: 150},
					{header : '<b>退订用户数</b>', name : 'stbReturnDay',align : 'center',width: 150},
					{header : '<b>到货终端数</b>', name : 'stbReceiveDay',align : 'center',width: 150}
		],
		extraDataSource : [{name:"area", url:"area.json"},{name:"city", url:"city.json"}]
    });	
	
	$('#query').omButtonbar({btns : [{label:"查询",icons : {left : opPath+'search.png'}}]});
	$('#export').omButtonbar({btns : [{label:"导出",icons : {left : opPath+'op-btn-icon.png'}}]});
	$('#query').bind('click', function(e) {
    	var date = $('#date').val();
    	//var date = $('#date').omCalendar('getDate');
    	var province = $('#province').val();
    	var sync = $('#sync').val();
   	    $('#grid').omGrid("setData", 'usr_active_sum.json?date='+encodeURIComponent(date)+'&province='+encodeURIComponent(province)+'&sync='+encodeURIComponent(sync));
    });	
	//$("#date").val(getCurrentDate());
	$('#export').bind('click', function(e) {
		var random = Math.random();
		location.href="usr_active_sum_export.json?random="+random;
		//showDiv();
	});
	$('#center-tab').omTabs({height:"33",border:false});
});
</script>
     
</head>
<body>
<div id="center-tab">
<ul>
	 <li><a href="#tab1">终端激活定时统计信息:</a></li>
</ul>
</div>
  <table >
            <tr>           	
           		<td>统计日期：</td>
               <td>
               		<input name="date" id="date" style="width:162px;"/>
               </td>
               <td>省份：</td>
               <td>               
               		<input name="province" id="province"/>
               </td>
               <td>是否同步：</td>
               <td>
               		<input name="sync" id="sync" style="width:80px;"/>
               </td>
                <td><div id="query"/></td>
                <td></td>
                <c:if test="${sessionScope.export_user_activate_day_sum_statistics != null }">
                <td colspan="13"><div id="export"/></td>
                </c:if>
             </tr>
        </table>     
<table id="grid"></table>

</body>
</html>