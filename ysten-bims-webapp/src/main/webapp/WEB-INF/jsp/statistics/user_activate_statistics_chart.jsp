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
#chart3 svg{
  height: 350px;
  min-width: 1000px;
  min-height: 100px;

}
#chart4 svg{
  height: 350px;
  min-width: 1000px;
  min-height: 100px;

}
.mypiechart {
  width: 500px;
  border: 2px;
}
</style>
<script type="text/javascript">
$(function(){
	$('#center-tab').omTabs({height:"33",border:false});
	$('#query').omButtonbar({btns : [{label:"查询",icons : {left : opPath+'search.png'}}]});
	$('#activeTime').omCalendar();
	$("#activeTime").val(getCurrentDate());
	$('#province').omCombo({dataSource:'area.json?par=0',editable:false, width:130, listMaxHeight:160, value:'0'});
	$('#chartStyle').omCombo({
        dataSource : [ 
                      {text : '柱状', value : 'discrete'}, 
                       {text : '饼状', value : 'pie'}
                      ],value:'discrete'
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
		$.ajax({
			url : "active_usr_by_city_report.json",
			data : "activeDate="+encodeURIComponent(activeTime)+"&province="+encodeURIComponent(province),
			type : "post",
			dataType : "json",
			success : function(data){
				var json = [];
				var json1 = [];
				var json2 = [];
				var json3 = [];
				var json4 = [];
				var json5 = [];
				var json6 = [];
				var json7 = [];
				var s=0;
				var s1=0;
				var cancelUserTotal = 0;
				var cancelUsersTotal = 0;
				var activeTotal = 0;
				var activesTotal = 0;
				$.each(data,function(i, item){
						json[i] = {"label" : item['cityName'], "value" : item['activeUser']} ;
						activeTotal +=  item['activeUser'];
				});
				$.each(data,function(i, item){
						json1[i] = {"label" : item['cityName'], "value" : item['activeUsers']} ;
						activesTotal +=  item['activeUsers'];
				});
				$.each(data,function(i, item){
					json2[i] = {"label" : item['cityName'], "value" : item['cancelUser']} ;
					cancelUserTotal += item['cancelUser']
			    });
				$.each(data,function(i, item){
					json3[i] = {"label" : item['cityName'], "value" : item['cancelUsers']} ;
					cancelUsersTotal += item['cancelUsers'];
			    });
			
			$.each(data,function(i, item){
					json6[i] = {key : item['cityName'], y : item['activeUser']} ;
					s1 = s1+item['activeUser'];
			});
			$.each(data,function(i, item){
					json7[i] = {key : item['cityName'], y : item['activeUsers']} ;
			});
			$.each(data,function(i, item){
				json4[i] = {key : item['cityName'], y : item['cancelUser']} ;
				s = s+item['cancelUser'];
		    });
			$.each(data,function(i, item){
				json5[i] = {key : item['cityName'], y : item['cancelUsers']} ;
		    });
		    
			actives.innerText = actives1.innerText ="总激活用户     "+"(总计:"+activesTotal+")";
			active.innerText = active1.innerText ="当日激活用户     "+"(总计:"+activeTotal+")";
			accounts.innerText = accounts1.innerText ="总销户用户     "+"(总计:"+cancelUsersTotal+")";
			account.innerText = account1.innerText ="当日销户用户     "+"(总计:"+cancelUserTotal+")";
		    
				historicalBarChart_activeUser = [ 
			  		                             {
			  		                               key: "Cumulative Return",
			  		                               values:json
			  		                             }
			  		                           ];
			  	historicalBarChart_activeUsers =[ 
				  		                             {
					  		                               key: "Cumulative Return",
					  		                               values:json1
					  		                             }
					  		                           ];
				historicalBarChart_account = [ 
		  		                             {
		  		                               key: "Cumulative Return",
		  		                               values:json2
		  		                             }
		  		                           ];
		  		 historicalBarChart_accounts =[ 
			  		                             {
				  		                               key: "Cumulative Return",
				  		                               values:json3
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
			  		   .datum(historicalBarChart_account)
			  		   .call(chart);

			  		    d3.select('#chart2 svg')
			  		    .datum(historicalBarChart_accounts)
			  		    .call(chart);
			  		    
			  		    d3.select('#chart3 svg')
			  		   .datum(historicalBarChart_activeUser)
			  		   .call(chart);

			  		    d3.select('#chart4 svg')
			  		    .datum(historicalBarChart_activeUsers)
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
               
               var chart2 = nv.models.pieChart()
               .x(function(d) { return d.key })
               .y(function(d) { return d.y })
               .color(d3.scale.category10().range())
               .width(width)
               .height(height);
               
               var chart3 = nv.models.pieChart()
               .x(function(d) { return d.key })
               .y(function(d) { return d.y })
               .color(d3.scale.category10().range())
               .width(width)
               .height(height);
               
               if(s==0){
            	   var testdata = [
            	                   {
            	                     key: "当日没有销户用户",
            	                     y: 1}
            	                 ];
            	                                  
            	   d3.select("#chart5")
                   .datum(testdata)
                 .transition().duration(1200)
                   .attr('width', width)
                   .attr('height', height)
                   .call(chart);
                   }
            	else{
            		d3.select("#chart5")
                    .datum(json4)
                  .transition().duration(1200)
                    .attr('width', width)
                    .attr('height', height)
                    .call(chart);
                	}
                 
                 d3.select("#chart6")
                 .datum(json5)
               .transition().duration(1200)
                 .attr('width', width)
                 .attr('height', height)
                 .call(chart1);
                 
                 if(s1==0){
              	   var testdata = [
              	                   {
              	                     key: "当日暂无激活的用户",
              	                     y: 1}
              	                 ];
              	                                  
              	   d3.select("#chart7")
                     .datum(testdata)
                   .transition().duration(1200)
                     .attr('width', width)
                     .attr('height', height)
                     .call(chart);
                     }
              	else{
                 d3.select("#chart7")
                     .datum(json6)
                   .transition().duration(1200)
                     .attr('width', width)
                     .attr('height', height)
                     .call(chart2);
              	}
                 
                 d3.select("#chart8")
                 .datum(json7)
               .transition().duration(1200)
                 .attr('width', width)
                 .attr('height', height)
                 .call(chart3);

                 chart.dispatch.on('stateChange', function(e) { nv.log('New State:', JSON.stringify(e)); });
                 chart1.dispatch.on('stateChange', function(e) { nv.log('New State:', JSON.stringify(e)); });
                 chart1.dispatch.on('stateChange', function(e) { nv.log('New State:', JSON.stringify(e)); });
                 chart3.dispatch.on('stateChange', function(e) { nv.log('New State:', JSON.stringify(e)); });
               
               return chart;
               return chart1;
               return chart2;
               return chart3;
               
              });
			  		}
		  		 
		  	}
		});		
	});
});

</script>


<body>
<div id="center-tab">
<ul>
	 <li><a href="#tab1">用户开户数据统计报表:</a></li>
</ul>
</div>
<div style="overflow-y: auto;">
	<center>
		<br />
	    <table>       		
	        <tr>
	       	<td>激活 | 销户时间：</td>
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
<div id="pie" style="overflow-y:scroll;height: 600px; display: none ">
<center>
<br/>
	<table style="height: 1200px;">
		<tr style="height: 350px;">
				<td  style="height: 350px">
				<h2>销户用户</h2>
				    <svg id="chart5" class="mypiechart"></svg>
				</td>
		</tr>
		<tr style="height: 10px">
			<td id="account1" style="width: 100px; text-align: center;">
				
			</td>
		</tr>
		<tr style= "height: 1px;background-color: #2763ad">
			<td style="width: 50px;"></td>
			<td ></td>
	    </tr>
		<tr style="height: 350px;">
				<td style="height: 350px">
				<h2>总销户用户</h2>
				    <svg id="chart6" class="mypiechart"></svg>
				</td>
		</tr>
		
		
		<tr>
			<td id="accounts1" style="width: 100px; text-align: center;">
				
			</td>
		</tr>
		<tr style= "height: 1px;background-color: #2763ad">
			<td style="width: 50px;"></td>
			<td ></td>
	    </tr>
		<tr style="height: 350px;">
			<td  style="height: 350px">
			<h2>当日激活用户</h2>
			    <svg id="chart7" class="mypiechart"></svg>
			</td>
		</tr>
		<tr style="height: 10px">
			<td id="active1" style="width: 100px; text-align: center;">
				
			</td>
		</tr>
		<tr style= "height: 1px;background-color: #2763ad">
			<td style="width: 50px;"></td>
			<td ></td>
	    </tr>
		<tr  style="height: 350px;">	
			<td style="height: 350px">
			<h2>总激活用户</h2>
			    <svg id="chart8" class="mypiechart"></svg>
			</td>
		</tr>
		
		<tr>
			<td id="actives1" style="width: 100px; text-align: center;">
				
			</td>
		</tr>
		<tr style="height: 100px;"></tr>
	</table>
  
</div>
<div id="discrete" style="overflow-y:scroll;height: 600px; display: none ">
   <table style="height: 1673px">
   <tr style= "height: 10px">
			<td style="width: 50px;"></td>
			<td id="account" style="width: 100px; text-align: center;"></td>
	</tr>
	
	<tr style= "height: 350px;">
			<td style="width: 50px;"></td>
			<td>
				<div id="chart1">
			    	<svg></svg>
			  	</div>
			</td>
	</tr>
	<tr style= "height: 1px;background-color: #2763ad">
		<td style="width: 50px;"></td>
		<td ></td>
	</tr>
	<tr style= "height: 30px">
			</td>
			<td style="width: 100px;"></td>
			<td id="accounts" style="width: 100px; text-align: center;">
				
			</td>
	</tr>
	
	<tr style= "height: 350px">	
			<td style="width: 100px;"></td>
			<td>
				<div id="chart2">
			    	<svg></svg>
			  	</div>
			</td>
	</tr>
	<tr style= "height: 1px;background-color: #2763ad">
		<td style="width: 50px;"></td>
		<td ></td>
	</tr>
	<tr style= "height: 30px">
			<td style="width: 50px;"></td>
			<td id="active" style="width: 100px; text-align: center;">
				
			</td>
	</tr>
	
	<tr  style= "height: 350px">
			<td style="width: 50px;"></td>
			<td>
				<div id="chart3">
			    	<svg></svg>
			  	</div>
			</td>
	</tr>
	<tr style= "height: 1px;background-color: #2763ad">
		<td style="width: 50px;"></td>
		<td ></td>
	</tr>
	<tr style= "height: 30px">
			<td style="width: 100px;"></td>
			<td id="actives" style="width: 100px; text-align: center;">
				
			</td>
	</tr>
		
	<tr style="height: 350px">
			<td style="width: 100px;"></td>
			<td>
				<div id="chart4">
			    	<svg></svg>
			  	</div>
			</td>
	</tr>
	<tr style= "height: 100px;">
		<td style="width: 50px;"></td>
		<td ></td>
	</tr>
	</table>
	</center>
</div>
