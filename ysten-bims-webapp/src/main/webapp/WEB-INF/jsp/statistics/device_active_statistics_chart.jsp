<%@ page language="java" import="com.ysten.local.bss.device.domain.City,java.util.*,org.apache.commons.lang.StringUtils,java.text.SimpleDateFormat" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="/include/taglibs.jsp" %>
<%@ include file="/include/operamasks-ui-2.0.jsp" %>
<%@ include file="/include/css.jsp" %>
<%@ include file="/include/ysten.jsp" %>
<%@ include file="/include/nvd3-org-chart.jsp"%>
<style>
body {
  overflow-y:scroll;
}

text {
  font: 12px sans-serif;
}

svg {
  display: block;
}

#chart1 svg{
  height: 350px;
  min-width: 1000px;
  min-height: 100px;

}
#chart2 svg{
  height: 350px;
  min-width: 1000px;
  min-height: 100px;

}
.mypiechart {
  width: 600px;
  border: 2px;
}
</style>
<script type="text/javascript">
$(function(){
	$('#center-tab').omTabs({height:"33",border:false});
	$('#query').omButtonbar({btns : [{label:"查询",icons : {left : opPath+'search.png'}}]});
	$('#activeTime').omCalendar();
	$("#activeTime").val(getCurrentDate());
	$('#chartStyle').omCombo({
        dataSource : [ 
                      {text : '柱状', value : 'discrete'}, 
                       {text : '饼状', value : 'pie'}
                      ],value:'discrete'
    });
	$('#province').omCombo({dataSource:'area.json?par=0',editable:false, width:130, listMaxHeight:160, value:'0'});
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
		$.ajax({
			url : "active_device_by_city_chart.json",
			data : "activeTime="+encodeURIComponent(activeTime)+"&province="+encodeURIComponent(province),
			type : "post",
			dataType : "json",
			success : function(data){
				
				var json = [];
				var json1 = [];
				var json2 = [];
				var json3 = [];
				var j = 0;
				var n = 0;
				var m = 0;
				var p = 0;
				var s = 0;
				var activeTotal = 0;
				var activesTotal = 0;
				$.each(data,function(i, item){
					if(item['flag']=="one"){
						json[j++] = {"label" : item['cityName'], "value" : item['rowTotal']} ;
						activeTotal +=  item['rowTotal'];
					}
				});
				$.each(data,function(i, item){
					if(item['flag']=="all"){
						json1[n++] = {"label" : item['cityName'], "value" : item['rowTotal']} ;
						activesTotal += item['rowTotal'];
					}
				});
				$.each(data,function(i, item){
					if(item['flag']=="one"){
						json2[m++] = {key : item['cityName'], y : item['rowTotal']} ;
						s = s + item['rowTotal'];
					}
				});
				$.each(data,function(i, item){
					if(item['flag']=="all"){
						json3[p++] = {key : item['cityName'], y : item['rowTotal']} ;
					}
				});

				actives.innerText = actives1.innerText = "激活总设备     "+"(总计:"+activesTotal+")";
				active.innerText = active1.innerText = "当日激活设备     "+"(总计:"+activeTotal+")";
				
				historicalBarChart_today = [ 
		  		                             {
		  		                               key: "Cumulative Return",
		  		                               values: json
		  		                             }
		  		                           ];
		  		 historicalBarChart_total =[ 
			  		                           {
				  		                         key: "Cumulative Return",
				  		                         values: json1
				  		                        }
				  		                         ];
                   if($("#chartStyle").val()=='discrete'){
                	   document.getElementById("discrete").style.display="";
                	   document.getElementById("pie").style.display="none";
                	   nv.addGraph(function() {  
       		  	        var chart = nv.models.discreteBarChart()
       		  	            .x(function(d) { return d.label })
       		  	            .y(function(d) { return d.value })
       		  	            .staggerLabels(true)
       		  	            .tooltips(false)
       		  	            .showValues(true)
       		  	            .transitionDuration(250)
       		  	            ;
       		  			d3.select('#chart1 svg')
       		  		   .datum(historicalBarChart_today)
       		  		   .call(chart);

       		  		    d3.select('#chart2 svg')
       		  		    .datum(historicalBarChart_total)
       		  		    .call(chart);

       		  		    nv.utils.windowResize(chart.update);

       		  		    return chart;
       		  		     });
                       }
               if($("#chartStyle").val()=='pie'){
                	   document.getElementById("pie").style.display="";
                	   document.getElementById("discrete").style.display="none";
                nv.addGraph(function() {  
                	   var width = 350,
                       height = 350;

                   var chart = nv.models.pieChart()
                       .x(function(d) { return d.key })
                       .y(function(d) { return d.y })
                       .color(d3.scale.category10().range())
                       .width(width)
                       .height(height);

                   var chart1 = nv.models.pieChart()
                   .x(function(d) { return d.key })
                   .y(function(d) { return d.y })
                   .color(d3.scale.category10().range())
                   .width(width)
                   .height(height);
				
					if(s==0){
						info = [ 
                             {
                               key: "当日暂无激活终端",
                               y: 1}
                           ];
						d3.select("#chart3")
                        .datum(info)
                      .transition().duration(1200)
                        .attr('width', width)
                        .attr('height', height)
                        .call(chart);
						}else{
							d3.select("#chart3")
	                         .datum(json2)
	                       .transition().duration(1200)
	                         .attr('width', width)
	                         .attr('height', height)
	                         .call(chart);
							}
                     
                     d3.select("#chart4")
                     .datum(json3)
                   .transition().duration(1200)
                     .attr('width', width)
                     .attr('height', height)
                     .call(chart1);

                   chart.dispatch.on('stateChange', function(e) { nv.log('New State:', JSON.stringify(e)); });

                   chart1.dispatch.on('stateChange', function(e) { nv.log('New State:', JSON.stringify(e)); });
                   return chart;
                   return chart1;
                });
                       }
		  	}
		});		
	});
});

</script>


<body class='with-3d-shadow with-transitions'>
	<div id="center-tab">
	<ul>
		 <li><a href="#tab1">地方终端设备激活数据:</a></li>
	</ul>
	</div>
<div style="overflow-y: auto;">
<center>
		<br />
	    <table>       		
	        <tr>
	       	<td>激活时间：</td>
	           <td><input id="activeTime" name="activeTime" style="width:163px;" value="" /></td>
	           <td>查询省份：</td>
	           <td><input id="province" name="province" style="width:110px;"/></td> 
	           <td>展现形式：</td>
               <td>
               		<input name="chartStyle" id="chartStyle" style="width:80px;"/>
               </td>
	           <td colspan="13"><div id="query"/></td>
	         </tr>
	     </table>
	     <br />
	</center>
</div>
<div id="discrete" style="overflow-y:scroll;height: 600px; display: none;">
<center>
   <table  style="height: 400px; border-bottom: 1px solid #2763ad; ">
   <tr style="height: 20px;">
			<td style="width: 50px;"></td>
			<td id="active" style="width: 100px; text-align: center;">
				
			</td>
		</tr>
		<tr  style="height: 350px;">
			<td style="width: 50px;"></td>
			<td>
				<div id="chart1"">
			    	<svg></svg>
			  	</div>
			</td>
		</tr>
		<tr  style="height: 30px;">
			<td style="width: 50px;"></td>
			<td>
			</td>
		</tr>
</table>
<table  style="height: 500px;">	
		<tr style="height: 20px;">
			<td style="width: 100px;"></td>
			<td>
				
			</td>
		</tr>	
		<tr style="height: 10px;">
			<td style="width: 100px;"></td>
			<td id="actives" style="width: 100px; text-align: center;">
				
			</td>
		</tr>
		<tr  style="height: 350px;">
			<td style="width: 100px;"></td>
			<td>
				<div id="chart2">
			    	<svg></svg>
			  	</div>
			</td>
		</tr>
		<tr  style="height: 130px;">
			<td style="width: 50px;"></td>
			<td>
			</td>
		</tr>
	</table>
	</center>
</div>
<div id="pie" style="overflow-y:scroll;height: 600px; display: none ">
<center>
<table  style="height: 370px; border-bottom: 1px solid #2763ad; ">
		<tr style="height: 20px;">
		<td id="active1" type="text-align: center;"></td>
		</tr>
		<tr style="height: 350px;">
		<td  style="height: 350px">
			<h2>当日激活设备</h2>
			<svg id="chart3" class="mypiechart"></svg>
		</td>
		</tr>
</table>
<table   style="height: 500px;">
		<tr style="height: 10px;">
		<td id="actives1"  type="text-align: center;"></td>
		</tr>
		<tr style="height: 400px;">
		<td style="height: 350px;">
			<h2>激活总设备</h2>
			<svg id="chart4" class="mypiechart"></svg>
		</td>
		</tr>
		<tr  style="height: 30px;">
			<td style="width: 50px;"></td>
			<td>
			</td>
		</tr>
	</table>
	
	</center>
</div>
