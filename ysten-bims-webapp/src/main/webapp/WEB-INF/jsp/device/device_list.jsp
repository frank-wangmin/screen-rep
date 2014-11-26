<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<%@ include file="/include/taglibs.jsp" %>
<%@ include file="/include/operamasks-ui-2.0.jsp" %>
<%@ include file="/include/css.jsp" %>
<%@ include file="/include/ysten.jsp" %>
<style type="text/css">
    .file-box {
        position: relative;
        width: 340px
    }

    .txt {
        height: 22px;
        border: 1px solid #cdcdcd;
        width: 180px;
    }

    .btn {
        background-color: #FFF;
        border: 1px solid #CDCDCD;
        height: 24px;
        width: 70px;
    }

    .file {
        position: absolute;
        top: 0;
        right: 80px;
        height: 24px;
        filter: alpha(opacity : 0);
        opacity: 0;
        width: 260px
    }
</style>
<script type="text/javascript">
$(document).ready(function() {
    $('#center-tab').omTabs({height:"33",border:false});
    $('#grid').omGrid({
        dataSource : 'device_list.json',
        width : '100%',
        height : rFrameHeight-30,
        singleSelect : false,
        limit :limit,
        colModel : [
            {header : '<b>设备编号</b>', name : 'ystenId', align : 'center', width : 150},
            {header : '<b>设备序列号</b>', name : 'sno',align : 'center',width: 150},
            {header : '<b>生产批次号</b>', name : 'productNo',align : 'center',width: 80},
            {header : '<b>MAC地址</b>', name : 'mac',align : 'center',width: 120},
            {header : '<b>设备厂商</b>', name : 'deviceVendor',align : 'center',width: 70,renderer:function(value){
                if(value==null || value==""){
                    return "";
                }else{
                    return value.name;
                }
            }},
            {header : '<b>设备型号</b>', name : 'deviceType',align : 'center',width:70,renderer:function(value){
                if(value==null || value==""){
                    return "";
                }else{
                    return value.name;
                }
            }},
            {header : '<b>设备状态</b>', name : 'state',align : 'center',width: 50},
            {header : '<b>绑定状态</b>', name : 'bindType',align : 'center',width: 50},
            {header : '<b>是否锁定</b>', name : 'isLock',align : 'center',width: 50},
            //{header : '<b>分发状态</b>', name : 'distributeState',align : 'center',width: 50},
            //{header : '<b>同步状态</b>', name : 'isSync', align : 'center', width : 50},
            {header : '<b>区域</b>', name : 'area',align : 'center',width: 50,renderer:function(value){
                if(value==null || value==""){
                    return "";
                }else{
                    return value.name;
                }
            }},
            {header : '<b>地市</b>', name : 'city',align : 'center',width: 50,renderer:function(value){
                if(value==null || value==""){
                    return "";
                }else{
                    return value.name;
                }
            }},
            {header : '<b>运营商</b>', name : 'spDefine',align : 'center',width: 80,renderer:function(value){
                if(value==null || value==""){
                    return "";
                }else{
                    return value.name;
                }
            }},
            {header : '<b>软件号</b>', name : 'softCode',align : 'center',width: 110
                /**
                 ,renderer:function(value){
                   if(value==null || value==""){
                   	return "";
                   }else{
                       if(value.name==null || value.name==""){
                           return "";
                       }
                       	return value.name;
                   }
               }*/
            },
            {header : '<b>软件版本号</b>', name : 'versionSeq',align : 'center',width: 50},
            {header : '<b>设备分组</b>', name : 'groups',align : 'center',width: 200},
            {header : '<b>绑定的用户编号</b>', name : 'customerCode',align : 'center',width: 200},
            {header : '<b>创建时间</b>', name : 'createDate',align : 'center',width: 110},
            {header : '<b>激活时间</b>', name : 'activateDate',align : 'center',width: 110},
            {header : '<b>状态变更时间</b>', name : 'stateChangeDate',align : 'center',width: 110},
            {header : '<b>到期时间</b>', name : 'expireDate',align : 'center',width: 110},
                <!--{header : '<b>IP地址</b>', name : 'ipAddress',align : 'center',width: 100},-->
    {header : '<b>设备描述</b>', name : 'description',align : 'center',width: 220}

    ],
    rowDetailsProvider:function(rowData,rowIndex){
        var info = "";
        if(rowData.ystenId!= null){
            info += ", 设备编号="+rowData.ystenId;//updated by joyce on 2014/07/18
        }
        if(rowData.sno != null){
            info += ", 设备序列号="+rowData.sno;
        }
        if(rowData.mac != null){
            info += ", MAC地址="+rowData.mac;
        }
        if(rowData.deviceVendor && rowData.deviceVendor.name){
            info += ", 设备厂商="+rowData.deviceVendor.name;
        }
        if(rowData.deviceType && rowData.deviceType.name){
            info += ", 设备型号="+rowData.deviceType.name;
        }
        if(rowData.state != null){
            info += ", 设备状态="+rowData.state;
        }
        if(rowData.isLock!= null){
            info += ", 是否锁定="+rowData.isLock;
        }
        if(rowData.area && rowData.area.name){
            info += "</br>"+"所属区域="+rowData.area.name;
        }
        if(rowData.city && rowData.city.name){
            info += ", 所属地市="+rowData.city.name;
        }
        if(rowData.spDefine && rowData.spDefine.name){
            info += ", 所属运营商="+rowData.spDefine.name;
        }
        if(rowData.softCode && rowData.softCode.name){
            info += ", 软件号="+rowData.softCode.name;
        }
        if(rowData.createDate != null){
            info += ", 创建时间="+rowData.createDate;
        }
        if(rowData.activateDate != null){
            info += ", 激活时间="+rowData.activateDate;
        }
        if(rowData.stateChangeDate != null){
            info += ", 状态变更时间="+rowData.stateChangeDate;
        }
        if(rowData.expireDate != null){
            info += ", 到期时间="+rowData.expireDate;
        }
        if(rowData.ipAddress != null){
            info += "<br/> IP地址="+rowData.ipAddress;
        }
        if(rowData.description != null){
            info +=  ", 设备描述="+rowData.description;
        }
        return '第'+rowIndex+'行, 主键ID='+rowData.id+info;
    }
});
$('#deviceVendor2').omCombo({dataSource : 'device_vendor.json?par=0',editable:false,width:130,listMaxHeight:150,value:'0',
    onValueChange : function(target, newValue, oldValue, event){getDeviceType(); }});
$('#selectArea').omCombo({dataSource : 'area.json?par=0',editable:false,width:80,listMaxHeight:160,value:'0'});
//	    $('#areaId2').omCombo({dataSource : 'area.json?par=0',editable:false,width:130,listMaxHeight:160,value:'0',onValueChange : function(target, newValue, oldValue, event){getProductNo();}});
//	    $('#productNo2').omCombo({dataSource:'device_product_no.json?par=0',editable:false,width:130,listMaxHeight:150,value:'0'});
//$('#deviceDistributeState2').omCombo({dataSource:'device_distribute_state.json?par=0',editable:false,width:130,listMaxHeight:150,value:'0'});
$('#deviceState2').omCombo({dataSource : 'device_state.json?par=0',editable:false,width:130,listMaxHeight:150,value:'0'});
$('#sp2').omCombo({dataSource : 'sp_define.json?par=0',editable:false,width:130,listMaxHeight:150,value:'0'});

$('#deviceVendor3').omCombo({dataSource : 'device_vendor.json?par=0',editable:false,width:180,listMaxHeight:150,value:'0',
    onValueChange : function(target, newValue, oldValue, event){getDeviceType(); }});
$('#areaId3').omCombo({dataSource : 'area.json?par=0',editable:false,width:180,listMaxHeight:160,value:'0'});
$('#deviceState3').omCombo({dataSource : 'device_state.json?par=0',editable:false,width:180,listMaxHeight:150,value:'0'});

var type;
$('#areaId4').omCombo({dataSource : 'area.json?par=0',editable:false,width:180,listMaxHeight:160,value:'0',onValueChange : function(target, newValue, oldValue, event){getDeviceType(); }});

//暂时无此类型的需求
$("#closeCommon").hide();

//$("closeDistributeState").hide();
$('#update').omButtonbar({btns : [{label:"修改",icons : {left : opPath+'op-edit.png'}}]});
$('#delete').omButtonbar({btns : [{label:"删除",icons : {left : opPath+'remove.png'}}]});
$('#query').omButtonbar({btns : [{label:"查询",icons : {left : opPath+'search.png'}}]});
$('#heightQuery').omButtonbar({btns : [{label:"高级查询",icons : {left : opPath+'search.png'}}]});
$('#bulkQuery').omButtonbar({btns : [{label:"批量查询",icons : {left : opPath+'search.png'}}]});
$('#import').omButtonbar({btns : [{label:"导入",icons : {left : opPath+'op-btn-icon.png'}}]});
$('#export').omButtonbar({btns : [{label:"导出",icons : {left : opPath+'op-btn-icon.png'}}]});
$('#lock').omButtonbar({btns : [{label:"锁定",icons : {left : opPath+'op-edit.png'}}]});
$('#unlock').omButtonbar({btns : [{label:"解锁",icons : {left : opPath+'op-edit.png'}}]});
$('#distribute').omButtonbar({btns : [{label:"终端下发(批次号)",icons : {left : opPath+'op-btn-icon.png'}}]});
$('#distributeByIds').omButtonbar({btns : [{label:"选中下发",icons : {left : opPath+'op-btn-icon.png'}}]});
$('#pickup').omButtonbar({btns : [{label:"终端领用",icons : {left : opPath+'op-btn-icon.png'}}]});
$('#sync').omButtonbar({btns : [{label:"终端同步",icons : {left : opPath+'op-btn-icon.png'}}]});
$('#bindGroup').omButtonbar({btns : [{label:"绑定分组",icons : {left : opPath+'op-edit.png'}}]});
$('#unbind_business').omButtonbar({btns : [{label:"解绑业务",icons : {left : opPath+'remove.png'}}]});
$('#unbindGroup').omButtonbar({btns : [{label:"解绑分组",icons : {left : opPath+'remove.png'}}]});
$('#temp').omButtonbar({btns : [{label:"下载导入模板",icons : {left : opPath+'icon-help.png'}}]});
$('#relate_business').omButtonbar({btns : [{label:"关联业务",icons : {left : opPath+'op-edit.png'}}]});

$("#expireDate").omCalendar(({dateFormat : 'yy-mm-dd H:i:s'}));

$('#query').bind('click', function(e) {
    var ystenId = $('#ystenId1').val();
    var mac = $('#mac1').val();
    var sno = $('#sno1').val();
    var areaId = $("#selectArea").omCombo('value');
    $('#ystenValue').val(ystenId);
    $('#snoValue').val(sno);
    $('#macValue').val(mac);
    $('#areaValue').val(areaId);
    $('#spValue').val("");
    $('#deviceVendorValue').val("");
    $('#deviceTypeValue').val("");
    $('#areaHvalue').val("");
    $('#productNoValue').val("");
    $('#deviceStateValue').val("");
    $('#macHvalue').val("");
    $('#softCodeValue').val("");
    $('#versionSeqValue').val("");
    $('#snoHvalue').val("");
    $('#deviceCodeHvalue').val("");
    $('#ystenIdHvalue').val("");
    $('#startDateValue').val("");
    $('#endDateValue').val("");
    $('#startDateAvtiveValue').val("");
    $('#endDateAvtiveValue').val("");
    $('#grid').omGrid("setData", 'device_list.json?ystenId='+encodeURIComponent(ystenId) +'&mac='+encodeURIComponent(mac)+'&sno='+encodeURIComponent(sno)+'&areaId='+areaId);
});

$('#bulkQuery').bind('click', function(e) {
    var dialog7 = $("#dialog-form7").omDialog({
        width: 700,
        height: 320,
        autoOpen : false,
        modal : true,
        resizable : false,
        draggable : false,
        buttons : {
            "提交" : function(){
                var snos = filterStartEndSpaceTrim($('#snos').val());
                submitData={
                    snos : filterStartEndSpaceTrim($('#snos').val())
                };
                $.post('validator_sno.json', submitData, function(result){
                    if(result.indexOf('合法') > 0){
                        $('#grid').omGrid("setData", 'device_list_by_snos.json?snos='+encodeURIComponent(snos));
                    }else{
                        $.omMessageBox.confirm({
                            title:'确认继续批量查询',
                            content:result,
                            onClose:function(v){
                                if(v==true){
                                    $('#grid').omGrid("setData", 'device_list_by_snos.json?snos='+encodeURIComponent(snos));
                                }
                            }
                        });
                    }
                    $("#dialog-form7").omDialog("close");
                });
                return false;
            },
            "取消" : function() {
                $("#dialog-form7").omDialog("close");
            }
        },onClose:function(){
            $('#myform7').validate().resetForm();
//            		$("#dialog-form7").omDialog("close");
        }
    });
    dialog7.omDialog("option", "title", "设备信息批量查询");
    dialog7.omDialog("open");
});

$('#delete').click(function(){
    var selections=$('#grid').omGrid('getSelections',true);
    if (selections.length == 0) {
        $.omMessageBox.alert({
            type:'alert',
            title:'温馨提示',
            content:'删除操作至少选择一行记录！',
        });
        return false;
    }
    if(selections[0].isLock=='锁定'){
        $.omMessageBox.alert({
            type:'alert',
            title:'温馨提示',
            content:'该条记录被锁定，请解锁后再做操作！',
        });
        return false;
    }
    if(selections[0].bindType=='绑定'){
        $.omMessageBox.alert({
            type:'alert',
            title:'温馨提示',
            content:'该终端设备绑定了用户，不可删除！',
        });
        return false;
    }
    $.omMessageBox.confirm({
        title:'确认删除',
        content:'执行删除后数据将移植设备历史表，你确定要执行该操作吗？',
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
                $.post('delete_devices.json',{ids:toDeleteRecordID.toString()},function(result){
                    $('#grid').omGrid('reload');
                    if(result=='success'){
                        $.omMessageTip.show({title: tip, content: "删除设备信息成功！", timeout: time,type:'success'});
                    }else{
                        $.omMessageTip.show({title: tip, content: result, timeout: time,type:'error'});
                    }
                    $('#dialog-form').omDialog('close');
                });
            }
        }
    });
});
$('#unbind_business').bind('click', function(e) {
    var selections=$('#grid').omGrid('getSelections',true);
    if (selections.length == 0) {
        $.omMessageBox.alert({
            type:'alert',
            title:'温馨提示',
            content:'解绑操作至少选择一行记录！',
        });
        return false;
    }
    if(selections[0].isLock=='锁定'){
        $.omMessageBox.alert({
            type:'alert',
            title:'温馨提示',
            content:'该条记录被锁定，请解锁后再做操作！',
        });
        return false;
    }
    $.omMessageBox.confirm({
        title:'确认解绑',
        content:'解绑后该终端与业务的绑定关系将删除且无法恢复，你确定要执行该操作吗？',
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
                $.post('device_business_map_delete.json',{ids:toDeleteRecordID.toString()},function(result){
                    $('#grid').omGrid('reload');
                    if(result=='success'){
                        $.omMessageTip.show({title: tip, content: "解绑业务成功！", timeout: time,type:'success'});
                    }else{
                        $.omMessageTip.show({title: tip, content: "解绑业务失败！", timeout: time,type:'error'});
                    }
                });
            }
        }
    });
});
$('#unbindGroup').bind('click', function(e) {
    var selections=$('#grid').omGrid('getSelections',true);
    if (selections.length == 0) {
        $.omMessageBox.alert({
            type:'alert',
            title:'温馨提示',
            content:'解绑操作至少选择一行记录！',
        });
        return false;
    }
    if(selections[0].isLock=='锁定'){
        $.omMessageBox.alert({
            type:'alert',
            title:'温馨提示',
            content:'该条记录被锁定，请解锁后再做操作！',
        });
        return false;
    }
    $.omMessageBox.confirm({
        title:'确认解绑',
        content:'解绑后设备与设备分组绑定关系将删除且无法恢复，你确定要执行该操作吗？',
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
                $.post('device_deviceGroup_map_delete.json',{ids:toDeleteRecordID.toString()},function(result){
                    $('#grid').omGrid('reload');
                    if(result=='success'){
                        $.omMessageTip.show({title: tip, content: "解绑设备分组成功！", timeout: time,type:'success'});
                    }else{
                        $.omMessageTip.show({title: tip, content: "解绑设备分组失败！", timeout: time,type:'error'});
                    }
                });
            }
        }
    });
});

var dialog = $("#dialog-form").omDialog({
    width: 550, autoOpen : false,
    modal : true, resizable : false,
    draggable : false,
    buttons : {
        "提交" : function(){
            var mac = $.trim($("#mac").val());
            var reg = /[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}/;
            if (mac!=null && mac!='' ){
                if(!reg.test(mac) || mac.length<17){
                    $.omMessageBox.alert({type:'alert',title:tip,content:'MAC地址不合法！',});
                    return false;
                }
            }
            submitDialog();
            return false;
        },
        "取消" : function() {
            $("#dialog-form").omDialog("close");
        }
    },onClose:function() {
        $('#myform').validate().resetForm();
    }
});

var dialogYstenId = $("#dialog-form9").omDialog({
    width: 700,
    height: 320,
    autoOpen : false,
    modal : true, resizable : false,
    draggable : false,
    buttons : {
        "确定" : function(){
            $("#dialog-form9").omDialog("close");
            return false;
        }
    }
});

var showDialog = function(title,rowData){
    rowData = rowData || {};
    dialog.omDialog("option", "title", title);
    dialog.omDialog("open");
};

var showYstenIdDialog = function(title){
    dialogYstenId.omDialog("option", "title", title);
    dialogYstenId.omDialog("open");
};

var submitDialog = function(){
    if(validator.form()){
        var submitData={
            id:$("#deviceId").val(),
            mac:$("#mac").val(),
            spCode:$("#spCode").val(),
            deviceState:$("#deviceState").omCombo('value'),
            deviceVendorId:$('#deviceVendor').omCombo('value'),
            deviceTypeId:$("#deviceTypeId").omCombo('value'),
            area:$("#area").val(),
            city:$("#city").val(),
            expireDate:$("#expireDate").val(),
            sno:$("#sno").val()
        };
        $.post('device_update.shtml',submitData,function(result){
            $('#grid').omGrid('reload');
            if("success" == result){
                $.omMessageTip.show({title: tip, content: "修改设备信息成功！", type:"success" ,timeout: time});
            }
            if("isExists" == result){
                $.omMessageTip.show({title: tip, content: "修改设备信息失败！MAC地址已存在!", type:"error" ,timeout: time});
            }
            if("difference" == result){
                $.omMessageTip.show({title: tip, content: "修改设备信息失败！地市与区域不符!", type:"error" ,timeout: time});
            }
            if("failed" == result){
                $.omMessageTip.show({title: tip, content: "修改设备信息失败！", type:"error" ,timeout: time});
            }
            $("#dialog-form").omDialog("close");
        });
    }
};

$('#update').bind('click', function() {
    //$('#area').omCombo({dataSource : 'area.json',editable:false,listMaxHeight:150,width:180,onValueChange : function(target, newValue, oldValue, event){getCity(); }});
    var selections=$('#grid').omGrid('getSelections',true);
    if (selections.length == 0 || selections.length > 1) {
        $.omMessageBox.alert({type:'alert',title:'温馨提示',content:'修改操作至少且只能选择一行记录！',});
        return false;
    }
    if(selections[0].isLock=='锁定'){
        $.omMessageBox.alert({type:'alert',title:'温馨提示',content:'该条记录被锁定，请解锁后再做操作！',});
        return false;
    }
    $.ajax({
        type:"post",
        url:"device_to_update.json?id="+selections[0].id,
        dataType:"json",
        success:function(msg){
            if(msg['state'] != null){
                $('#deviceState').omCombo({dataSource : 'device_state.json',editable:false,width:180,value:msg['state']});
            }else{
                $('#deviceState').omCombo({dataSource : 'device_state.json',editable:false,width:180,value:''});
            }
            var deviceV;

            if(msg['deviceVendor'] == null){
                $('#deviceVendor').omCombo({dataSource : 'device_vendor.json',editable:false,width:180,value:'',
                    onValueChange : function(target, newValue, oldValue) {
                        $('#deviceTypeId').omCombo({dataSource : 'dynamicCascade.json?par=0&deviceVendor='+newValue,editable:false,listMaxHeight:150,width:180, value:''});
                    }});
            }else{
                var par1 = "";
                if(msg['deviceType'] != null){
                    par1 = msg['deviceType']['id'];
                }
                $('#deviceVendor').omCombo({dataSource : 'device_vendor.json',editable:false,width:180,value:msg['deviceVendor']['id'],
                    onValueChange : function(target, newValue, oldValue) {
                        $('#deviceTypeId').omCombo({dataSource : 'dynamicCascade.json?par='+par1+'&deviceVendor='+newValue,editable:false,listMaxHeight:150,width:180, value:par1});
                    }});
                if(msg['deviceTypeId'] == null){
                    $('#deviceTypeId').omCombo({dataSource:'dynamicCascade.json?par=0&deviceVendor=' + msg['deviceVendor']['id'],editable:false,width:180,listMaxHeight:150,value:''});
                }else{
                    $('#deviceTypeId').omCombo({dataSource:'dynamicCascade.json?par='+msg['deviceType']['id']+'&deviceVendor=' + msg['deviceVendor']['id'],editable:false,width:180,listMaxHeight:150,value:msg['deviceType']['id']});
                }
            }

            if(msg['spDefine'] == null){
                $('#spCode').omCombo({dataSource : 'sp_define.json',editable:false,listMaxHeight:150,width:180,value:''});
            }else{
                $('#spCode').omCombo({dataSource : 'sp_define.json',editable:false,listMaxHeight:150,width:180,value:msg['spDefine']['id']});
            }
            //$('#area').omCombo({dataSource : 'area.json',editable:false,listMaxHeight:150,width:180,onValueChange : function(target, newValue, oldValue) {
            //$('#city').omCombo({dataSource : 'city_by_area.json?areaId='+msg['area']['id'],editable:false,listMaxHeight:150,width:180, value:''});}});

            if(msg['area'] == null){
                $('#area').omCombo({dataSource : 'area.json',editable:false,listMaxHeight:150,width:180, value:'',
                    onValueChange : function(target, newValue, oldValue) {
                        $('#city').omCombo({dataSource : 'city_by_area.json?areaId='+newValue,editable:false,listMaxHeight:150,width:180, value:''});
                    }
                });
            }else{
                var par = "";
                if(msg['city'] != null){
                    par = msg['city']['id'];
                }
                $('#area').omCombo({dataSource : 'area.json',editable:false,listMaxHeight:150,width:180,value:msg['area']['id'],
                    onValueChange : function(target, newValue, oldValue) {
                        $('#city').omCombo({dataSource : 'city_by_area.json?areaId='+newValue+'&par='+par,editable:false,listMaxHeight:150,width:180, value:par});
                    }
                });
                if(msg['city'] == null){
                    $('#city').omCombo({dataSource : 'city_by_area.json?areaId='+msg['area']['id'],editable:false,listMaxHeight:150,width:180, value:''});
                }else{
                    $('#city').omCombo({dataSource : 'city_by_area.json?areaId='+msg['area']['id']+'&par='+msg['city']['id'],editable:false,listMaxHeight:150,width:180,value:msg['city']['id']});
                }
            }

            $("#mac").val(msg['mac']);
            $("#deviceId").val(msg['id']);
            $("#expireDate").val(msg['expireDate']);
            $("#sno").val(msg['sno']);
        }
    });
    showDialog('修改设备信息',selections[0]);
});

$('#lock').bind('click', function(e) {
    var selections=$('#grid').omGrid('getSelections',true);
    if (selections.length == 0) {
        $.omMessageBox.alert({
            type:'alert',
            title:'温馨提示',
            content:'锁定操作至少选择一行记录！',
        });
        return false;
    }
    $.omMessageBox.confirm({
        title:'确认锁定',
        content:'锁定后将无法对该批设备数据进行修改，你确定要执行该操作吗？',
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
                $.post('device_lock.json?par=lock',{ids:toDeleteRecordID.toString()},function(result){
                    $('#grid').omGrid('reload');
                    if(result=='success'){
                        $.omMessageTip.show({title: tip, content: "批量锁定设备成功！", timeout: time,type:'success'});
                    }else{
                        $.omMessageTip.show({title: tip, content: "批量锁定设备失败！", timeout: time,type:'error'});
                    }
                    $('#dialog-form').omDialog('close');
                });
            }
        }
    });
});

$('#unlock').bind('click', function(e) {
    var selections=$('#grid').omGrid('getSelections',true);
    if (selections.length == 0) {
        $.omMessageBox.alert({
            type:'alert',
            title:'温馨提示',
            content:'锁定操作至少选择一行记录！',
        });
        return false;
    }
    $.omMessageBox.confirm({
        title:'确认解锁',
        content:'解锁后将可以该批设备数据进行修改，你确定要执行该操作吗？',
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
                $.post('device_lock.json?par=unlock',{ids:toDeleteRecordID.toString()},function(result){
                    $('#grid').omGrid('reload');
                    if(result=='success'){
                        $.omMessageTip.show({title: tip, content: "解锁设备成功！", timeout: time,type:'success'});
                    }else{
                        $.omMessageTip.show({title: tip, content: "解锁设备失败！", timeout: time,type:'error'});
                    }
                    $('#dialog-form').omDialog('close');
                });
            }
        }
    });
});

$('#heightQuery').bind('click', function(e) {
    $('#bulkSelType').omCombo({dataSource : [
        {text:'设备编号',value:'1'},
        {text:'设备序列号',value:'2'}
    ],
        value:'0',
        editable:false,
        width:180,
        listMaxHeight:160,
        onValueChange : function(target, newValue){
            if(newValue == 0){
                $("#openDeviceCode").show();
                $("#openYstenId").hide();
                $("#openSno").hide();
                $('#ystenId2').val("");
                $('#sno2').val("");
            }
            if(newValue == 1){
                $("#openDeviceCode").hide();
                $("#openYstenId").show();
                $("#openSno").hide();
                $('#deviceCode2').val("");
                $('#sno2').val("");
            }
            if(newValue == 2){
                $("#openDeviceCode").hide();
                $("#openYstenId").hide();
                $("#openSno").show();
                $('#ystenId2').val("");
                $('#deviceCode2').val("");
            }
        }
    });

    $("#startDateExport").omCalendar();
    $("#endDateExport").omCalendar();
    $("#startDateAvtive").omCalendar();
    $("#endDateAvtive").omCalendar();

    $('#productNo2').omCombo({dataSource:'device_product_no.json?par=0',editable:false,width:130,listMaxHeight:150,value:'0'});
    $('#areaId2').omCombo({dataSource : 'area.json?par=0',editable:false,width:130,listMaxHeight:160,value:'0',onValueChange : function(target, newValue, oldValue, event){getProductNo();}});
    var dialog1 = $("#dialog-form1").omDialog({
        width: 900,
        height: 500,
        autoOpen : false,
        modal : true,
        resizable : false,
        draggable : false,
        buttons : {
            "提交" : function(){
                if(validator_heightQuery.form()){
                    var ystenId = $('#ystenId2').val();
                    var deviceCode = $('#deviceCode2').val();
                    var mac = $('#mac2').val();
                    var sno = $('#sno2').val();
                    var deviceVendorId = $("#deviceVendor2").omCombo('value');
                    var deviceTypeId = $("#deviceType2").omCombo('value');
                    var areaId = $("#areaId2").omCombo('value');
                    var deviceState = $("#deviceState2").omCombo('value');
                    var sp = $("#sp2").omCombo('value');
                    var productNo = $("#productNo2").omCombo('value');
                    var softCode = $('#softCode2').val();
                    var versionSeq = $('#versionSeq2').val();
                    var startDate = $("#startDateExport").val();
                    var endDate = $("#endDateExport").val();
                    var startDateAvtive = $("#startDateAvtive").val();
                    var endDateAvtive = $("#endDateAvtive").val();

                    if((startDate !=null && endDate == null) || (startDate ==null && endDate != null)){
                        $.omMessageBox.alert({
                            type:'alert',
                            title:'温馨提示',
                            content:'请将创建时间填写完整！',
                        });
                        return false;
                    }

                    if((startDateAvtive !=null && endDateAvtive == null) || (startDateAvtive ==null && endDateAvtive != null)){
                        $.omMessageBox.alert({
                            type:'alert',
                            title:'温馨提示',
                            content:'请将激活时间填写完整！',
                        });
                        return false;
                    }
                    if(startDate > endDate || startDateAvtive > endDateAvtive){
                        $.omMessageBox.alert({
                            type:'alert',
                            title:'温馨提示',
                            content:'结束时间要大于等于起始时间！',
                        });
                        return false;
                    }

                    $('#grid').omGrid("setData", 'device_list_conditions.json?ystenId='+encodeURIComponent(ystenId)+'&deviceCode='+encodeURIComponent(deviceCode)+'&mac='+encodeURIComponent(mac)+
                            '&sno='+encodeURIComponent(sno)+'&deviceVendorId='+deviceVendorId+'&deviceTypeId='+deviceTypeId+'&areaId='+areaId+
                            '&deviceState='+deviceState+'&sp='+sp+'&productNo='+productNo+'&softCode='+softCode+'&versionSeq='+versionSeq+"&startDate="+encodeURIComponent(startDate)
                            +"&endDate="+encodeURIComponent(endDate)
                            +"&startDateAvtive="+encodeURIComponent(startDateAvtive)
                            +"&endDateAvtive="+encodeURIComponent(endDateAvtive));
                    $("#dialog-form1").omDialog("close");
                    $('#ystenValue').val("");
                    $('#snoValue').val("");
                    $('#macValue').val("");
                    $('#areaValue').val("");
                    $('#spValue').val(sp);
                    $('#deviceVendorValue').val(deviceVendorId);
                    $('#deviceTypeValue').val(deviceTypeId);
                    $('#areaHvalue').val(areaId);
                    $('#productNoValue').val(productNo);
                    $('#deviceStateValue').val(deviceState);
                    $('#macHvalue').val(mac);
                    $('#softCodeValue').val(softCode);
                    $('#versionSeqValue').val(versionSeq);
                    $('#snoHvalue').val(sno);
                    $('#deviceCodeHvalue').val(deviceCode);
                    $('#ystenIdHvalue').val(ystenId);
                    $('#startDateValue').val(startDate);
                    $('#endDateValue').val(endDate);
                    $('#startDateAvtiveValue').val(startDateAvtive);
                    $('#endDateAvtiveValue').val(endDateAvtive);
                }
            },
            "取消" : function() {
                $("#dialog-form1").omDialog("close");
            }
        },onClose:function(){$('#myform1').validate().resetForm();}
    });
    dialog1.omDialog("option", "title", "设备信息高级查询");
    dialog1.omDialog("open");
});

var validator_heightQuery = $('#myform1').validate({
    rules : {
        versionSeq2:{digits:true}
    },
    messages : {
        versionSeq2:{digits:"请输入数字！"}
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
var validator = $('#myform').validate({
    rules : {
        deviceState : {required: true},
        city : {required : true},
        spCode: {required : true},
    },
    messages : {
        deviceState : {required : "请选择设备状态！"},
        city : {required : "请选择地市！"},
        spCode: {required : "请选择运营商！"}
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

$('#import').bind('click', function(e) {
    var dialog2 = $("#dialog-form2").omDialog({
        width: 400,
        autoOpen : false,
        modal : true,
        resizable : false,
        draggable : false,
        buttons : {
            "提交" : function(){
                if($("#textfield").val().length==0){
                    $.omMessageBox.alert({type:'alert',title:tip,content:'请选择文件后再提交！',});
                    return false;
                }
                var FileName=new String($("#textfield").val());//文件名
                var extension=new String(FileName.substring(FileName.lastIndexOf(".")+1,FileName.length));//文件扩展名
                if(extension == 'xls' || extension == 'xlsx'){
                }else{
                    alert('导入的文件格式不支持,只可以导入EXCEL文件！');
                    return false;
                }
                $('#myform2').submit();
                $("#dialog-form2").omDialog("close");
                showDiv();
            },
            "取消" : function() {
                $("#dialog-form2").omDialog("close");
            }
        },onClose:function(){}
    });
    dialog2.omDialog("option", "title", "终端设备导入");
    dialog2.omDialog("open");
});
/**********************************************导出*************************************************/
$('#export').bind('click', function(e) {
    var selections=$('#grid').omGrid('getSelections',true);
    $('#exportType').omCombo({dataSource : [
        {text:'选中导出',value:'0'},
        {text:'全部导出',value:'1'}
    ],
        value:'0',
        width:180,
        listMaxHeight:160
    });

    var dialog3 = $("#dialog-form3").omDialog({
        width: 300,
        autoOpen : false,
        modal : true,
        resizable : false,
        draggable : false,
        buttons : {
            "提交" : function(){
                //var info = "你确定要导出查询出的全部数据吗？";
                if($('#exportType').omCombo('value') == 0){
                    if (selections.length == 0) {
                        $.omMessageBox.alert({
                            type:'alert',
                            title:'温馨提示',
                            content:'选中导出至少选择一行记录！',
                        });
                        return false;
                    }
                }
                if (selections.length > 0 && $('#exportType').omCombo('value') == 0){
                    var toDeleteRecordID = "";
                    for(var i=0;i<selections.length;i++){
                        if(i != selections.length - 1){
                            toDeleteRecordID  += selections[i].id+",";
                        }else{
                            toDeleteRecordID  += selections[i].id;
                        }
                    }
                    location.href="device_export_byId.json?ids="+encodeURIComponent(toDeleteRecordID.toString());
                }
                if($('#exportType').omCombo('value') == 1){
                    var random = Math.random();
                    var ystenValue = $('#ystenValue').val();
                    var snoValue = $('#snoValue').val();
                    var macValue = $('#macValue').val();
                    var areaValue = $('#areaValue').val();

                    var spValue = $('#spValue').val();
                    var deviceVendorValue = $("#deviceVendorValue").val();
                    var deviceTypeValue = $("#deviceTypeValue").val();
                    var areaHvalue = $("#areaHvalue").val();
                    var productNoValue = $("#productNoValue").val();
                    var deviceStateValue = $("#deviceStateValue").val();
                    var macHvalue = $("#macHvalue").val();
                    var softCodeValue = $("#softCodeValue").val();
                    var versionSeqValue = $("#versionSeqValue").val();
                    var snoHvalue = $("#snoHvalue").val();
                    var deviceCodeHvalue = $('#deviceCodeHvalue').val();
                    var ystenIdHvalue = $("#ystenIdHvalue").val();
                    var startDateValue = $("#startDateValue").val();
                    var endDateValue = $("#endDateValue").val();
                    var startDateAvtiveValue = $("#startDateAvtiveValue").val();
                    var endDateAvtiveValue = $("#endDateAvtiveValue").val();
                    location.href="device_export_by_query.json?random="+random
                            +"&ystenValue="+encodeURIComponent(ystenValue)
                            +"&snoValue="+encodeURIComponent(snoValue)
                            +"&macValue="+encodeURIComponent(macValue)
                            +"&areaValue="+encodeURIComponent(areaValue)

                            +"&spValue="+encodeURIComponent(spValue)
                            +"&deviceVendorValue="+encodeURIComponent(deviceVendorValue)
                            +"&deviceTypeValue="+encodeURIComponent(deviceTypeValue)
                            +"&areaHvalue="+encodeURIComponent(areaHvalue)
                            +"&productNoValue="+encodeURIComponent(productNoValue)
                            +"&deviceStateValue="+encodeURIComponent(deviceStateValue)
                            +"&macHvalue="+encodeURIComponent(macHvalue)
                            +"&softCodeValue="+encodeURIComponent(softCodeValue)
                            +"&versionSeqValue="+encodeURIComponent(versionSeqValue)
                            +"&snoHvalue="+encodeURIComponent(snoHvalue)
                            +"&deviceCodeHvalue="+encodeURIComponent(deviceCodeHvalue)
                            +"&ystenIdHvalue="+encodeURIComponent(ystenIdHvalue)
                            +"&startDateValue="+encodeURIComponent(startDateValue)
                            +"&endDateValue="+encodeURIComponent(endDateValue)
                            +"&startDateAvtiveValue="+encodeURIComponent(startDateAvtiveValue)
                            +"&endDateAvtiveValue="+encodeURIComponent(endDateAvtiveValue);
                    /*
                     location.href="device_export.json?random="+random
                     +"&deviceCode="+encodeURIComponent(deviceCode)
                     +"&mac="+encodeURIComponent(mac)
                     +"&sno="+encodeURIComponent(sno)
                     +"&deviceVendorId="+encodeURIComponent(deviceVendorId)
                     +"&deviceTypeId="+encodeURIComponent(deviceTypeId)
                     +"&areaId="+encodeURIComponent(areaId)
                     +"&deviceState="+encodeURIComponent(deviceState)
                     +"&startDate="+encodeURIComponent(startDate)
                     +"&endDate="+encodeURIComponent(endDate)
                     +"&startDateAvtive="+encodeURIComponent(startDateAvtive)
                     +"&endDateAvtive="+encodeURIComponent(endDateAvtive);*/
                }
                $("#dialog-form3").omDialog("close");
            },
            "取消" : function() {
                $("#dialog-form3").omDialog("close");
            }
        },onClose:function(){
            $('#myform3').validate().resetForm();
            $("#dialog-form3").omDialog("close");
        }
    });
    dialog3.omDialog("option", "title", "设备信息导出");
    dialog3.omDialog("open");
});
/**************************************************************************************/
/**
 $('#export').bind('click', function(e) {
   	 	$("#startDateExport").omCalendar();
	 	$("#endDateExport").omCalendar();
	 	$("#startDateAvtive").omCalendar();
	 	$("#endDateAvtive").omCalendar();
    		var dialog3 = $("#dialog-form3").omDialog({
            	width: 300,
            	autoOpen : false,
            	modal : true,
            	resizable : false,
            	draggable : false,
            	buttons : {
                	"提交" : function(){
                		var random = Math.random();
                		var deviceCode = $('#deviceCode3').val();
                    	var mac = $('#mac3').val();
                    	var sno = $('#sno3').val();
                    	var deviceVendorId = $("#deviceVendor3").omCombo('value');
                    	var deviceTypeId = $("#deviceType3").omCombo('value');
                    	var areaId = $("#areaId3").omCombo('value');
                    	var deviceState = $("#deviceState3").omCombo('value');
                    	var startDate = $("#startDateExport").val();
	                	var endDate = $("#endDateExport").val();
	                	var startDateAvtive = $("#startDateAvtive").val();
	                	var endDateAvtive = $("#endDateAvtive").val();
	                	if(((startDate==null || startDate=="") && (endDate == null || endDate == "" ))&& ((startDateAvtive !=null || startDateAvtive !="") && (endDateAvtive == null || endDateAvtive == ""))){
	                		$.omMessageBox.alert({
                            		type:'alert',
                            		title:'温馨提示',
                            		content:'创建时间或激活时间至少选填一项！',
                        		});
	                	 	return false;
		           	}
	                	if(startDateAvtive ==null || startDateAvtive ==""){
	                		if((startDate ==null || startDate =="")){
		                		$.omMessageBox.alert({
	                            		type:'alert',
	                            		title:'温馨提示',
	                            		content:'请填写创建的起始时间！',
	                        		});
		                	 	return false;
		                	}
		                	if(endDate == null || endDate == ""){
		                		$.omMessageBox.alert({
	                            		type:'alert',
	                            		title:'温馨提示',
	                            		content:'请填写创建的结束时间！',
	                        		});
		                	 	return false;
			           	}
	                	}
	                	if(startDate ==null || startDate =="" ){
	                		if((startDateAvtive ==null && startDateAvtive =="")){
		                		$.omMessageBox.alert({
	                            		type:'alert',
	                            		title:'温馨提示',
	                            		content:'请填写激活的起始时间！',
	                        		});
		                	 	return false;
		                	}
		                	if(endDateAvtive == null && endDateAvtive == ""){
		                		$.omMessageBox.alert({
	                            		type:'alert',
	                            		title:'温馨提示',
	                            		content:'请填写激活的结束时间！',
	                        		});
		                	 	return false;
			            	}
	                	}
	                	if(startDate > endDate || startDateAvtive > endDateAvtive){
	                		$.omMessageBox.alert({
                            		type:'alert',
                            		title:'温馨提示',
                            		content:'结束时间要大于等于起始时间！',
                        		});
	                	 	return false;
		               }
            			location.href="device_export.json?random="+random
            				+"&deviceCode="+encodeURIComponent(deviceCode)
            				+"&mac="+encodeURIComponent(mac)
            				+"&sno="+encodeURIComponent(sno)
            				+"&deviceVendorId="+encodeURIComponent(deviceVendorId)
            				+"&deviceTypeId="+encodeURIComponent(deviceTypeId)
            				+"&areaId="+encodeURIComponent(areaId)
            				+"&deviceState="+encodeURIComponent(deviceState)
            				+"&startDate="+encodeURIComponent(startDate)
	        				+"&endDate="+encodeURIComponent(endDate)
	        				+"&startDateAvtive="+encodeURIComponent(startDateAvtive)
	        				+"&endDateAvtive="+encodeURIComponent(endDateAvtive);
            			$("#dialog-form3").omDialog("close");
                	},
                	"取消" : function() {
                    	$("#dialog-form3").omDialog("close");
                	}
            	},onClose:function(){}
        	});
    	   	dialog3.omDialog("option", "title", "设备信息导出");
    	   	dialog3.omDialog("open");
    	});
 */
$('#temp').bind('click', function(e) {
    var random = Math.random();
    location.href="device_export_template.json?random="+random;
});

$('#distribute').bind('click', function(e) {
    $('#productNo').omCombo({dataSource:'device_product_no.json?par=0&area=',editable:false,width:180,listMaxHeight:150,value:'0'});
    type = "distribute";
    var dialog4 = $("#dialog-form4").omDialog({
        width: 530,
        autoOpen : false,
        modal : true,
        resizable : false,
        draggable : false,
        buttons : {
            "提交" : function(){
                if(validator4.form()){
                    $.post('device_distribute.json?par=distribute',{productNo:$('#productNo').omCombo('value')},function(result){
                        if(result=='success'){
                            $.omMessageTip.show({title: tip, content: "终端下发成功！", timeout: time,type:'success'});
                            $('#grid').omGrid('reload');
                        }else{
                            $.omMessageTip.show({title: tip, content: "终端下发失败！", timeout: time,type:'error'});
                        }
                    });
                    $("#dialog-form4").omDialog("close");
                }
            },
            "取消" : function() {
                $("#dialog-form4").omDialog("close");
            }
        },onClose:function(){}
    });
    dialog4.omDialog("option", "title", "终端下发");
    dialog4.omDialog("open");
});

$('#distributeByIds').bind('click', function(e) {
    var selections=$('#grid').omGrid('getSelections',true);
    if (selections.length == 0) {
        $.omMessageBox.alert({
            type:'alert',
            title:'温馨提示',
            content:'至少选择一行记录！',
        });
        return false;
    }
    if(selections[0].distributeState=='已分发'){$.omMessageBox.alert({
        type:'alert',
        title:'温馨提示',
        content:'您选中的数据中有分发过的终端，请选择未分发的终端！',
    });
//     			alert("您选中的数据中有分发过的终端，请选择未分发的终端！");
        return false;
    }
    $.omMessageBox.confirm({
        title:'确认下发',
        content:'批量操作后数据将下发给省级，你确定要执行该操作吗？',
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
                $.post('devices_rend.json',{ids:toDeleteRecordID.toString()},function(result){
                    $('#grid').omGrid('reload');
                    if(result=='success'){
                        $.omMessageTip.show({title: tip, content: "终端下发成功！", timeout: time,type:'success'});
                    }else{
                        $.omMessageTip.show({title: tip, content: "终端下发失败！", timeout: time,type:'error'});
                    }
                    $('#dialog-form').omDialog('close');
                });
            }
        }
    });
});

$('#pickup').bind('click', function(e) {
    type = "pickup";
    $('#areaId4').omCombo({dataSource : 'area.json?par=0',editable:false,width:180,listMaxHeight:160,value:'0',
        onValueChange : function(target, newValue, oldValue, event){getDeviceType('pickup');}});
    $('#productNo').omCombo({dataSource:'device_product_no.json?par=0&area=',editable:false,width:180,listMaxHeight:150});
    var dialog4 = $("#dialog-form4").omDialog({
        width: 530,
        autoOpen : false,
        modal : true,
        resizable : false,
        draggable : false,
        buttons : {
            "提交" : function(){
                if(validator4.form()){
                    $.post('device_pickup.json?par=distribute',{productNo:$('#productNo').omCombo('value')},function(result){
                        if(result=='success'){
                            $.omMessageTip.show({title: tip, content: "终端领用成功！", timeout: time,type:'success'});
                            $('#grid').omGrid('reload');
                        }else{
                            $.omMessageTip.show({title: tip, content: "终端领用失败！", timeout: time,type:'error'});
                        }
                    });
                    $("#dialog-form4").omDialog("close");
                }
            },
            "取消" : function() {
                $("#dialog-form4").omDialog("close");
            }
        },onClose:function(){}
    });
    dialog4.omDialog("option", "title", "终端领用");
    dialog4.omDialog("open");
});

$('#sync').bind('click', function(e) {
    $.omMessageBox.confirm({
        title:'确认同步',
        content:'设备同步将同步所有状态改变的设备，你确定要执行该操作吗？',
        onClose:function(v){
            if(v==true){
                $.post('device_sync.shtml',function(result){
                    $('#grid').omGrid('reload');
                    /**
                     if(result=='success'){
                               	$.omMessageTip.show({title: tip, content: "设备同步信息成功！", timeout: time,type:'success'});
                               }else{
                               	$.omMessageTip.show({title: tip, content: "设备同步信息失败！", timeout: time,type:'error'});
                               }*/
                    if(result.indexOf('成功') > 0){
                        $.omMessageTip.show({title: tip, content: "设备同步信息成功！", type:"success" ,timeout: time});
                    }else{
                        $.omMessageTip.show({title: tip, content: result, type:"error" ,timeout: time});
                    }
                    $('#dialog-form').omDialog('close');
                });
                $('#grid').omGrid('reload');
            }
        }
    });
});

var validator4 = $('#myform4').validate({
    rules : {
        areaId4 : {required: true},
        productNo : {required: true}
    },
    messages : {
        areaId4 : {required : "请选择区域！"},
        productNo : {required : "请选择生产批次！"}
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

$('#bindGroup').click(function(){
    var selections=$('#grid').omGrid('getSelections',true);
    if (selections.length == 0 || selections.length > 1) {
        $.omMessageBox.alert({
            type:'alert',
            title:'温馨提示',
            content:'绑定分组操作至少且只能选择一行记录！',
        });
        return false;
    }
    if (selections[0].ystenId=="") {
        $.omMessageBox.alert({
            type:'alert',
            title:'温馨提示',
            content:'STBID为空，不可做绑定操作！',
        });
        return false;
    }
    /**
     if(selections[0].isLock=='锁定'){
		        $.omMessageBox.alert({
                type:'alert',
                title:'温馨提示',
                content:'该条记录被锁定，请解锁后再做操作！',
            		});
				return false;
	        }
     if(selections[0].bindType=='未绑定'){
				$.omMessageBox.alert({
	                type:'alert',
	                title:'温馨提示',
	                content:'该终端设备未激活,需绑定用户激活后才可绑定业务组！',
	            });
				return false;
	            }*/
    $.get("get_device_groups.json",{code:selections[0].ystenId},function(result){
        if(!selections[0].area){
            areaId = "";
        }else{
            areaId = selections[0].area.id;
        }
        if(result != null){
            $('#boot5').omCombo({dataSource : 'device_group_list_by_type.json?type=BOOTSTRAP&isDynamic=true&areaId='+areaId,editable:false,width:182,value:result['BOOTSTRAP']});
            $('#animation5').omCombo({dataSource : 'device_group_list_by_type.json?type=ANIMATION&isDynamic=true&areaId='+areaId,editable:false,width:182,value:result['ANIMATION']});
            $('#upgrade5').omCombo({dataSource : 'device_group_list_by_type.json?type=UPGRADE&isDynamic=true&areaId='+areaId,editable:false,width:182,value:result['UPGRADE']});
            $('#appUpgrade5').omCombo({dataSource : 'device_group_list_by_type.json?type=APPUPGRADE&isDynamic=true&areaId='+areaId,editable:false,width:182,value:result['APPUPGRADE']});
            $('#panel5').omCombo({dataSource : 'device_group_list_by_type.json?type=PANEL&isDynamic=true&areaId='+areaId,editable:false,width:182,value:result['PANEL']});
            $('#notice5').omCombo({dataSource : 'device_group_list_by_type.json?type=NOTICE&isDynamic=true&areaId='+areaId,editable:false,width:182,value:result['NOTICE']});
            $('#background5').omCombo({dataSource : 'device_group_list_by_type.json?type=BACKGROUND&isDynamic=true&areaId='+areaId,editable:false,width:182,value:result['BACKGROUND']});
            //$('#common5').omCombo({dataSource : 'device_group_list_by_type.json?type=COMMON&isDynamic=true',editable:false,width:182,value:result['COMMON']||''});

        }else{
            $('#boot5').omCombo({dataSource : 'device_group_list_by_type.json?type=BOOTSTRAP&isDynamic=true&areaId='+areaId,editable:false,width:182,value:''});
            $('#animation5').omCombo({dataSource : 'device_group_list_by_type.json?type=ANIMATION&isDynamic=true&areaId='+areaId,editable:false,width:182,value:''});
            $('#upgrade5').omCombo({dataSource : 'device_group_list_by_type.json?type=UPGRADE&isDynamic=true&areaId='+areaId,editable:false,width:182,value:''});
            $('#appUpgrade5').omCombo({dataSource : 'device_group_list_by_type.json?type=APPUPGRADE&isDynamic=true&areaId='+areaId,editable:false,width:182,value:''});
            $('#panel5').omCombo({dataSource : 'device_group_list_by_type.json?type=PANEL&isDynamic=true&areaId='+areaId,editable:false,width:182,value:''});
            $('#notice5').omCombo({dataSource : 'device_group_list_by_type.json?type=NOTICE&isDynamic=true&areaId='+areaId,editable:false,width:182,value:''});
            $('#background5').omCombo({dataSource : 'device_group_list_by_type.json?type=BACKGROUND&isDynamic=true&areaId='+areaId,editable:false,width:182,value:''});

        }

    });

    var dialog = $("#dialog-form6").omDialog({
        width: 400,
        height: 300,
        autoOpen : false,
        modal : true,
        resizable : false,
        draggable : false,
        buttons : {
            "提交" : function(result){
                submitData={
                    boot:$('#boot5').val(),
                    animation:$('#animation5').val(),
                    upgrade:$('#upgrade5').val(),
                    appUpgrade:$('#appUpgrade5').val(),
                    panel:$('#panel5').val(),
                    notice:$('#notice5').val(),
                    background:$('#background5').val(),
                    common:$('#common5').val(),
                    ids:selections[0].id
                };
                $.post('devices_bind_deviceGroup.json', submitData, function(result){
                    $('#grid').omGrid('reload');
                    if(result.indexOf('成功') > 0){
                        $.omMessageTip.show({title: tip, content: result, timeout: time,type:'success'});
                    }
                    if(result.indexOf('失败') > 0){
                        $.omMessageTip.show({title: tip, content: result, timeout: time,type:'error'});
                    }
                    if(!result.indexOf('成功') > 0 && !result.indexOf('失败') > 0){
                        $.omMessageTip.show({title: tip, content: result, timeout: time,type:'alert'});
                    }
                    $("#dialog-form6").omDialog("close");
                });

                return false;
            },
            "取消" : function() {
                $("#dialog-form6").omDialog("close");
            }
        },onClose:function(){
            $('#myform6').validate().resetForm();
        }
    });
    dialog.omDialog("option", "title", "绑定终端分组信息");
    dialog.omDialog("open");
});

$('#relate_business').click(function(){
    var selections=$('#grid').omGrid('getSelections',true);
    if (selections.length == 0 || selections.length > 1) {
        $.omMessageBox.alert({
            type:'alert',
            title:'温馨提示',
            content:'关联业务操作至少且只能选择一行记录！',
        });
        return false;
    }
    if (selections[0].ystenId=="") {
        $.omMessageBox.alert({
            type:'alert',
            title:'温馨提示',
            content:'STBID为空，不可做关联业务操作！',
        });
        return false;
    }

    $.get("get_device_relate_business.json",{ystenId:selections[0].ystenId},function(result){
        if(result != null){
            $('#notice10').omCombo({dataSource: 'get_sys_notice_list.json', editable: false, width: 182, multi: true, value: result['NOTICE'] || ''});
            $('#upgrade10').omCombo({dataSource: 'get_upgrade_task_list.json', editable: false, width: 182, multi: true, value: result['UPGRADE'] || ''});
            $('#boot10').omCombo({dataSource : 'get_service_collect_list.json?',editable:false,width:182,value:result['BOOTSTRAP']});
            $('#background10').omCombo({dataSource : 'get_background_image_list.json?',editable:false,width:182,multi: true, value:result['BACKGROUND'] || ''});
            $('#animation10').omCombo({dataSource : 'get_boot_animation_list.json?',editable:false,width:182,value:result['ANIMATION']});
            $('#panel10').omCombo({dataSource : 'get_panel_package_list.json?',editable:false,width:182,value:result['PANEL']});
            $('#apkUpgrade10').omCombo({dataSource: 'get_apk_upgrade_task_list.json', editable: false, width: 182, multi: true, value: result['APKUPGRADE'] || ''});
        }else{
            $('#notice10').omCombo({dataSource: 'get_sys_notice_list.json', editable: false, width: 184, multi: true, value:''});
            $('#upgrade10').omCombo({dataSource: 'get_upgrade_task_list.json', editable: false, width: 182, multi: true, value: ''});
            $('#boot10').omCombo({dataSource : 'get_service_collect_list.json?',editable:false,width:182,value:''});
            $('#background10').omCombo({dataSource : 'get_background_image_list.json?',editable:false,width:182,multi: true, value:''});
            $('#animation10').omCombo({dataSource : 'get_boot_animation_list.json?',editable:false,width:182,value:''});
            $('#panel10').omCombo({dataSource : 'get_panel_package_list.json?',editable:false,width:182,value:''});
            $('#apkUpgrade10').omCombo({dataSource: 'get_apk_upgrade_task_list.json', editable: false, width: 182, multi: true, value: ''});
        }
    });

    var dialog = $("#dialog-form10").omDialog({
        width: 400,
        height: 300,
        autoOpen : false,
        modal : true,
        resizable : false,
        draggable : false,
        buttons : {
            "提交" : function(result){
                submitData={
                    boot:$('#boot10').val(),
                    animation:$('#animation10').val(),
                    panel:$('#panel10').val(),
                    notice: $("#notice10").omCombo('value'),
                    background:$('#background10').val(),
                    upgrade: $('#upgrade10').omCombo('value'),
                    apkUpgrade: $('#apkUpgrade10').omCombo('value'),
                    ids:selections[0].id.toString()
                };

                $.post('device_bind_business.json', submitData, function(result){
                    $('#grid').omGrid('reload');
                    if(result.indexOf('成功') > 0){
                        $.omMessageTip.show({title: tip, content: result, timeout: time,type:'success'});
                    }
                    else if(result.indexOf('失败') > 0){
                        $.omMessageTip.show({title: tip, content: result, timeout: time,type:'error'});
                    }
                    else{
                        $.omMessageTip.show({title: tip, content: result, timeout: time,type:'alert'});
                    }
                    $("#dialog-form10").omDialog("close");
                });

                return false;
            },

            "解绑全部业务" : function(result){
                if(selections[0].isLock=='锁定'){
                    $.omMessageBox.alert({
                        type:'alert',
                        title:'温馨提示',
                        content:'该条记录被锁定，请解锁后再做操作！',
                    });
                    return false;
                }
                $.omMessageBox.confirm({
                    title:'确认解绑',
                    content:'解绑后该终端与业务的绑定关系将删除且无法恢复，你确定要执行该操作吗？',
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
                            $.post('device_business_map_delete.json',{ids:toDeleteRecordID.toString()},function(result){
                                $('#grid').omGrid('reload');
                                if(result=='success'){
                                    $.omMessageTip.show({title: tip, content: "解绑业务成功！", timeout: time,type:'success'});
                                }else{
                                    $.omMessageTip.show({title: tip, content: "解绑业务失败！", timeout: time,type:'error'});
                                }
                                $("#dialog-form10").omDialog("close");
                            });
                        }
                    }
                });
            },
            "取消" : function() {
                $("#dialog-form10").omDialog("close");
            }
        },onClose:function(){
            $('#myform10').validate().resetForm();
        }
    });
    dialog.omDialog("option", "title", "关联业务信息");
    dialog.omDialog("open");
});

/* $.omMessageBox.confirm({
 title:'确认绑定分组',
 content:'你确定要执行该操作吗？',
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
 var dialog = $("#dialog-form6").omDialog({
 width: 400,
 height: 300,
 autoOpen : false,
 modal : true,
 resizable : false,
 draggable : false,
 buttons : {
 "提交" : function(result){
 submitData={
 boot:$('#boot5').val(),
 animation:$('#animation5').val(),
 upgrade:$('#upgrade5').val(),
 appUpgrade:$('#appUpgrade5').val(),
 panel:$('#panel5').val(),
 notice:$('#notice5').val(),
 background:$('#background5').val(),
 common:$('#common5').val(),
 ids:toDeleteRecordID.toString()
 };
 $.post('devices_bind_deviceGroup.json', submitData, function(result){
 *//**
 if(result=="success"){
	        	                        	$.omMessageTip.show({title: tip, content: "终端绑定终端分组信息成功！", timeout: time,type:'success'});
	        	                        }else{
	        	                        	$.omMessageTip.show({title: tip, content: "终端绑定终端分组信息失败！", timeout: time,type:'error'});
	        	                        }*//*
 if(result.indexOf('成功') > 0){
 $.omMessageTip.show({title: tip, content: result, timeout: time,type:'success'});
 }else{
 $.omMessageTip.show({title: tip, content: result, timeout: time,type:'error'});
 }
 $("#dialog-form6").omDialog("close");
 });
 $('#grid').omGrid('reload');
 return false;
 },
 "取消" : function() {
 $("#dialog-form6").omDialog("close");
 }
 },onClose:function(){}
 });
 dialog.omDialog("option", "title", "绑定终端分组信息");
 dialog.omDialog("open");
 }
 }
 });
 }); */
/*$('#bind').bind('click', function(e) {
 $('#productNo').val('');
 var dialog5 = $("#dialog-form5").omDialog({
 width: 1030,
 height:400,
 autoScroll : true,
 autoOpen : false,
 modal : true,
 resizable : false,
 draggable : false,
 buttons : {
 "提交" : function(){
 var submitData = {
 boot:$('#boot5').val(), bootFrom: $('#bootFrom').val(), bootTo: $('#bootTo').val(),deviceCodes1: $('#deviceCodes1').val(),
 animation:$('#animation5').val(),animationFrom: $('#animationFrom').val(), animationTo: $('#animationTo').val(),deviceCodes2: $('#deviceCodes2').val(),
 upgrade:$('#upgrade5').val(),upgradeFrom: $('#upgradeFrom').val(), upgradeTo: $('#upgradeTo').val(),deviceCodes3: $('#deviceCodes3').val(),
 appUpgrade:$('#appUpgrade5').val(),appUpgradeFrom: $('#appUpgradeFrom').val(), appUpgradeTo: $('#appUpgradeTo').val(),deviceCodes4: $('#deviceCodes4').val(),
 panel:$('#panel5').val(),panelFrom: $('#panelFrom').val(), panelTo: $('#panelTo').val(),deviceCodes5: $('#deviceCodes5').val(),
 notice:$('#notice5').val(),noticeFrom: $('#noticeFrom').val(), noticeTo: $('#noticeTo').val(),deviceCodes6: $('#deviceCodes6').val(),
 background:$('#background5').val(),backgroundFrom: $('#backgroundFrom').val(), backgroundTo: $('#backgroundTo').val(),deviceCodes7: $('#deviceCodes7').val(),
 common:$('#common5').val(),commonFrom: $('#commonFrom').val(), commonTo: $('#commonTo').val(),deviceCodes8: $('#deviceCodes8').val()};
 $.post('device_group_bind.json',submitData,function(result){
 if(result.indexOf('成功') > 0){
 $.omMessageTip.show({title: tip, content: result, timeout: time,type:'success'});
 }else{
 $.omMessageTip.show({title: tip, content: result, timeout: time,type:'error'});
 }
 });
 $("#dialog-form5").omDialog("close");
 },
 "取消" : function() {
 $("#dialog-form5").omDialog("close");
 }
 },onClose:function(){}
 });
 dialog5.omDialog("option", "title", "终端分组绑定");
 dialog5.omDialog("open");
 });*/

function getDeviceType(){
    var deviceVendor2 = $('#deviceVendor2').omCombo('value');
    var deviceVendor = $('#deviceVendor').omCombo('value');
    var deviceVendor3 = $('#deviceVendor3').omCombo('value');
    var areaId4 = $('#areaId4').omCombo('value');
    $('#deviceTypeId').omCombo({dataSource:'dynamicCascade.json?par=0&deviceVendor=' + deviceVendor,editable:false,width:180,listMaxHeight:150});
    $('#deviceType2').omCombo({dataSource:'dynamicCascade.json?par=0&deviceVendor=' + deviceVendor2,editable:false,width:130,listMaxHeight:150,value:'0'});
    $('#deviceType3').omCombo({dataSource:'dynamicCascade.json?par=0&deviceVendor=' + deviceVendor3,editable:false,width:130,listMaxHeight:150,value:'0'});
    if(type == 'pickup'){
        $('#productNo').omCombo({dataSource:'device_product_no.json?par=0&area=' + areaId4+'&type=pickup',editable:false,width:180,listMaxHeight:150,value:'0'});
    }else if(type == 'distribute'){
        $('#productNo').omCombo({dataSource:'device_product_no.json?par=0&area=' + areaId4+'&type=distribute',editable:false,width:180,listMaxHeight:150,value:'0'});
    }
}
function getProductNo(){
    var areaId = $('#areaId2').omCombo('value');
    $('#productNo2').omCombo({dataSource:'device_product_no.json?par=0&area=' + areaId,editable:false,width:130,listMaxHeight:150,value:'0'});
}
/**
 function getCity(){
    		var areaId = $('#area').omCombo('value');
    		if(areaId != null){
          	  	$('#city').omCombo({dataSource : 'city_by_area.json?areaId='+areaId,editable:false,listMaxHeight:150,width:180});
          	}
    	}*/
});


</script>
</head>
<body>
<div id="center-tab">
    <ul>
        <li><a href="#tab1">设备信息列表:</a></li>
    </ul>
</div>
<div style="overflow:auto;overflow-y:hidden">
    <table>
        <tr align="left">
            <c:if test="${sessionScope.update_device != null }">
                <td>
                    <div id="update"></div>
                </td>
            </c:if>

            <c:if test="${sessionScope.delete_device != null }">
                <td>
                    <div id="delete"></div>
                </td>
            </c:if>
            <c:if test="${sessionScope.lock_device != null }">
                <td><div id="lock"></div></td>
            </c:if>
            <c:if test="${sessionScope.unlock_device != null }">
                <td><div id="unlock"></div></td>
            </c:if>
            <c:if test="${sessionScope.import_device != null }">
                <td><div id="import"></div></td>
            </c:if>
            <c:if test="${sessionScope.export_device != null }">
                <td><div id="export"></div></td>
            </c:if>
            <!--
			<c:if test="${sessionScope.export_device1 != null }">
				<td><div id="export1"></div></td>
			</c:if>
			 -->
            <c:if test="${sessionScope.device_distribute != null }">
                <td><div id="distribute"></div></td>
            </c:if>
            <c:if test="${sessionScope.device_distribute1 != null }">
                <td><div id="distributeByIds"></div></td>
            </c:if>
            <c:if test="${sessionScope.device_pickup != null }">
                <td><div id="pickup"></div></td>
            </c:if>
            <c:if test="${sessionScope.device_sync != null }">
                <td><div id="sync"></div></td>
            </c:if>
            <c:if test="">
                <td><div id="bind"></div></td>
            </c:if>
            <c:if test="${sessionScope.device_bind_group != null }">
                <td><div id="bindGroup"/></td>
            </c:if>
            <c:if test="${sessionScope.unbind_device_group != null }">
                <td><div id="unbindGroup"></div></td>
            </c:if>
            <c:if test="${sessionScope.unbind_business_device != null }">
                <td><div id="unbind_business"></div></td>
            </c:if>
            <td><div id="relate_business"></div></td>
            <c:if test="${sessionScope.device_import_template != null }">
                <td><div id="temp"></div></td>
            </c:if>
            <td>
                <div id="bulkQuery"></div>
            </td>
        </tr>
    </table>
    <table>
        <tr>
            <td>设备编号：</td>
            <td><input type="text" name="ystenId1" id="ystenId1" class="query-title-input" /></td>
            <td>&nbsp;设备序列号：</td>
            <td><input name="sno1" id="sno1" class="query-title-input"/></td>
            <td>&nbsp;MAC地址：</td>
            <td><input type="text" name="mac1" id="mac1" class="query-title-input" /></td>
            <td>&nbsp;区域：</td>
            <td><input name="selectArea"id="selectArea" /></td>
            <td>
                <div id="query"></div>
            </td>
            <td>
                <div id="heightQuery"></div>
            </td>
        </tr>
    </table>
</div>
<table id="grid"></table>
<div id="dialog-form">
    <form id="myform">
        <input type="hidden" value="" name="deviceId" id="deviceId" />
        <table>
            <tr>
                <td style="width: 100px; text-align: right;"><span
                        class="color_red">*</span>设备状态：</td>
                <td><input id="deviceState" name="deviceState" /></td>
                <td style="width: 200px;"><span></span></td>
            </tr>
            <tr>
                <td style="width: 100px; text-align: right;"><span
                        class="color_red">*</span>所属区域：</td>
                <td><input id="area" name="area" style="width: 180px;" /></td><!-- onchange="getCity();"  -->
                <td style="width: 200px;"><span></span></td>
            </tr>
            <tr>
                <td style="width: 100px; text-align: right;"><span
                        class="color_red">*</span>所属地市：</td>
                <td><input id="city" name="city" style="width: 180px;" /></td>
                <td style="width: 200px;"><span></span></td>
            </tr>
            <tr>
                <td style="width: 100px; text-align: right;"><span
                        class="color_red">*</span>所属运营商：</td>
                <td><input id="spCode" name="spCode" style="width: 180px;" /></td>
                <td style="width: 200px;"><span></span></td>
            </tr>
            <tr>
                <td style="width: 100px; text-align: right;">到期时间：</td>
                <td><input id="expireDate" name="expireDate"
                           style="width: 160px;" /></td>
                <td style="width: 200px;"><span></span></td>
            </tr>
            <tr>
                <td style="width: 100px; text-align: right;">设备厂商：</td>
                <td><input name="deviceVendor" id="deviceVendor"
                           onchange="getDeviceType();" /></td>
                <td style="width: 200px;"><span></span></td>
            </tr>
            <tr>
                <td style="width: 100px; text-align: right;">设备型号：</td>
                <td><input id="deviceTypeId" name="deviceTypeId" /></td>
                <td style="width: 200px;"><span></span></td>
            </tr>
            <tr>
                <td style="width: 100px; text-align: right;">MAC地址：</td>
                <td><input id="mac" name="mac"
                           style="width: 180px; height: 20px; border: 1px solid #86A3C4;"
                           maxlength="17" /></td>
                <td style="width: 200px;"><span></span></td>
            </tr>
            <tr>
                <td style="width: 100px; text-align: right;">设备序列号：</td>
                <td><input id="sno" name="sno"
                           style="width: 180px; height: 20px; border: 1px solid #86A3C4;"
                           maxlength="20" /></td>
                <td style="width: 200px;"><span></span></td>
            </tr>



        </table>
    </form>
</div>

<div id="dialog-form1" style="display: none;">
    <form id="myform1">
        <table>
            <tr>
                <td style="width: 100px; text-align: right;">创建时间：</td>
                <td><input type="text" name="startDateExport"
                           id="startDateExport" style="width: 160px;" /></td>
                <td></td>
                <td>至<input type="text" name="endDateExport"
                            id="endDateExport" style="width: 160px;"/>
                </td>
            </tr>
            <tr>
                <td style="width: 100px; text-align: right;">激活时间：</td>
                <td><input type="text" name="startDateAvtive"
                           id="startDateAvtive" style="width: 160px;" /></td>
                <td></td>
                <td>至<input type="text" name="endDateAvtive"
                            id="endDateAvtive" style="width: 160px;" />
                </td>
            </tr>
        </table>
        <table>
            <tr>
                <td style="width: 100px; text-align: right;">批量查询字段名：</td>
                <td><input name="bulkSelType" id="bulkSelType"/></td>
                <td><span></span></td>
                <td></td>
            </tr>
            <tr id="openYstenId" style="display: none;">
                <td style="width: 100px; text-align: right;">设备编号：</td>
                <td><textarea id="ystenId2" name="ystenId2" cols="70" rows="15" style="border:1px solid #86A3C4;"></textarea></td>
                <td></td>
                <td><span>请输入STBID，如有多个请用英文逗号分隔</span></td>
            </tr>
            <tr id="openSno" style="display: none;">
                <td style="width: 100px; text-align: right;">设备序列号：</td>
                <td><textarea id="sno2" name="sno2" cols="70" rows="15" style="border:1px solid #86A3C4;"></textarea></td>
                <td></td>
                <td><span>请输入设备序列号，如有多个请用英文逗号分隔</span></td>
            </tr>
        </table>
        <table>
            <tr>
                <td style="width: 100px; text-align: right;">运营商：</td>
                <td style="text-align: left;"><input name="sp2" id="sp2" /></td>

                <td style="width: 100px; text-align: left;">设备厂商：</td>
                <td style="text-align: left;"><input name="deviceVendor2"
                                                     id="deviceVendor2" onchange="getDeviceType();" /></td>

                <td style="width: 100px; text-align: left;">设备型号：</td>
                <td style="text-align: left;"><input name="deviceType2"
                                                     id="deviceType2" /></td>
                <td><span></span></td>
            </tr>
            <tr>
                <td style="width: 100px; text-align: right;">所属区域：</td>
                <td style="text-align: left;"><input name="areaId2"
                                                     id="areaId2" /></td>

                <td style="width: 100px; text-align: left;">生产批次号：</td>
                <td style="text-align: left;"><input name="productNo2" id="productNo2"/></td>

                <td style="width: 100px; text-align: left;">设备状态：</td>
                <td style="text-align: left;"><input name="deviceState2"
                                                     id="deviceState2" /></td>
                <td><span></span></td>
            </tr>
            <!--
            <tr id= "closeDistributeState">
                <td>设备分发状态：</td>
                <td style="text-align: left;"><input name="deviceDistributeState2"
                    id="deviceDistributeState2" /></td>
            </tr> -->
            <tr>
                <td style="width: 100px; text-align: right;">MAC地址：</td>
                <td><input type="text" name="mac2" id="mac2"
                           style="width: 130px; height: 20px; border: 1px solid #86A3C4;" /></td>

                <td style="width: 100px; text-align: left;">软件号：</td>
                <td><input type="text" name="softCode2" id="softCode2"
                           style="width: 130px; height: 20px; border: 1px solid #86A3C4;" /></td>

                <td style="width: 100px; text-align: left;">软件版本号：</td>
                <td><input type="text" name="versionSeq2" id="versionSeq2"
                           style="width: 130px; height: 20px; border: 1px solid #86A3C4;" /></td>
                <td><span></span></td>
            </tr>
        </table>
    </form>
</div>
<div id="dialog-form2" class="file-box" style="display: none;">
    <form id="myform2" action="device_import.json" method="post"
          enctype="multipart/form-data">
        <table>
            <tr>
                <td>Excel文件：</td>
                <td><input type='text' name='textfield' id='textfield'
                           class='txt' /> <input type='button' class='btn' value='浏览...' />
                    <input type="file" name="fileField" class="file" id="fileField"
                           size="28"
                           onchange="document.getElementById('textfield').value=this.value" />
                </td>
            </tr>
        </table>
    </form>
</div>
<div id="dialog-form3" style="display: none;">
    <form id="myform3">
        <table>
            <tr>
                <td>导出方式：</td>
                <td>
                    <input name="exportType" id="exportType"/>
                </td>
            </tr>
        </table>
    </form>
</div>
<div id="dialog-form4" style="display: none;">
    <form id="myform4">
        <table>
            <tr>
                <td style="width: 100px; text-align: right;"><span class="color_red">*</span>区域：</td>
                <td><input name="areaId4" id="areaId4"/></td>
                <td style="width: 200px;"><span></span></td>
            </tr>
            <tr>
                <td style="width: 100px; text-align: right;"><span class="color_red">*</span>生产批次：</td>
                <td><input name="productNo" id="productNo"/></td>
                <td style="width: 200px;"><span></span></td>
            </tr>
        </table>
    </form>
</div>
<div id="dialog-form6">
    <form id="myform6">
        <table>
            <tr>
                <td align="right">终端--开机引导初始化：</td>
                <td><input name="boot5" id="boot5" style="width:180px;"/></td>
                <td><span></span></td>
            </tr>
            <tr></tr>
            <tr>
                <td align="right">终端--消息分组：</td>
                <td><input name="notice5" id="notice5" style="width:180px;"/></td>
                <td><span></span></td>
            </tr>
            <tr></tr>
            <tr>
                <td align="right">终端--动画分组：</td>
                <td><input  name="animation5" id="animation5" style="width:180px;"/></td>
                <td><span></span></td>
            </tr>
            <tr></tr>
            <tr>
                <td align="right">终端--背景：</td>
                <td><input name="background5" id="background5" style="width:180px;"/></td>
                <td><span></span></td>
            </tr>
            <tr></tr>
            <tr>
                <td align="right">终端--面板分组：</td>
                <td><input  name="panel5" id="panel5" style="width:180px;"/></td>
                <td><span></span></td>
            </tr>
            <tr></tr>
            <tr>
                <td align="right">终端--升级分组：</td>
                <td><input name="upgrade5" id="upgrade5" style="width:180px;"/></td>
                <td><span></span></td>
            </tr>
            <tr></tr>
            <tr>
                <td align="right">终端--应用升级分组：</td>
                <td><input name="appUpgrade5" id="appUpgrade5" style="width:180px;"/></td>
                <td><span></span></td>
            </tr>
            <tr></tr>
            <tr id = "closeCommon">
                <td align="right">通用：</td>
                <td><input name="common5" id="common5" style="width:180px;"/> </td>
                <td><span></span></td>
            </tr>
        </table>
    </form>
</div>
<div id="dialog-form7" style="display: none;">
    <form id="myform7">
        <table>
            <tr>
                <td style="width: 100px; text-align: right;">设备序列号：</td>
                <td><textarea id="snos" name="snos" cols="70" rows="15" style="border:1px solid #86A3C4;"></textarea></td>
                <td><span>请输入设备序列号，如有多个请用英文逗号分隔</span></td>
            </tr>
        </table>
    </form>
</div>
<div id="dialog-form9" style="display: none;">
    <form id="myform9">
        <table>
            <tr>
                <td style="width: 100px; text-align: right;">STBID结果：</td>
                <td><textarea id="ystenIds" name="ystenIds" cols="70" rows="15" style="border:1px solid #86A3C4;"></textarea></td>
            </tr>
        </table>
    </form>
</div>
<div id="dialog-form10">
    <form id="myform10">
        <table>
            <tr>
                <td align="right">开机引导初始化：</td>
                <td><input name="boot10" id="boot10" style="width:180px;"/></td>
                <td><span></span></td>
            </tr>
            <tr></tr>
            <tr>
                <td align="right">消息信息：</td>
                <td><input name="notice10" id="notice10" style="width:180px;"/></td>
                <td><span></span></td>
            </tr>
            <tr></tr>
            <tr>
                <td align="right">开机动画：</td>
                <td><input  name="animation10" id="animation10" style="width:180px;"/></td>
                <td><span></span></td>
            </tr>
            <tr></tr>
            <tr>
                <td align="right">背景轮换：</td>
                <td><input name="background10" id="background10" style="width:180px;"/></td>
                <td><span></span></td>
            </tr>
            <tr></tr>
            <tr>
                <td align="right">面板包：</td>
                <td><input  name="panel10" id="panel10" style="width:180px;"/></td>
                <td><span></span></td>
            </tr>
            <tr></tr>
            <tr>
                <td align="right">终端升级：</td>
                <td><input name="upgrade10" id="upgrade10" style="width:180px;"/></td>
                <td><span></span></td>
            </tr>
            <tr></tr>
            <tr>
                <td align="right">APK升级：</td>
                <td><input name="apkUpgrade10" id="apkUpgrade10" style="width:180px;"/></td>
                <td><span></span></td>
            </tr>
        </table>
    </form>
</div>
<input type="hidden" id="ystenValue" name="ystenValue"/>
<input type="hidden" id="snoValue" name="snoValue"/>
<input type="hidden" id="macValue" name="macValue"/>
<input type="hidden" id="areaValue" name="areaValue"/>

<input type="hidden" id="spValue" name="spValue"/>
<input type="hidden" id="deviceVendorValue" name="deviceVendorValue"/>
<input type="hidden" id="deviceTypeValue" name="deviceTypeValue"/>
<input type="hidden" id="areaHvalue" name="areaHvalue"/>
<input type="hidden" id="productNoValue" name="productNoValue"/>
<input type="hidden" id="deviceStateValue" name="deviceStateValue"/>
<input type="hidden" id="macHvalue" name="macHvalue"/>
<input type="hidden" id="softCodeValue" name="softCodeValue"/>
<input type="hidden" id="versionSeqValue" name="versionSeqValue"/>
<input type="hidden" id="snoHvalue" name="snoHvalue"/>
<input type="hidden" id="deviceCodeHvalue" name="deviceCodeHvalue"/>
<input type="hidden" id="ystenIdHvalue" name="ystenIdHvalue"/>
<input type="hidden" id="startDateValue" name="startDateValue"/>
<input type="hidden" id="endDateValue" name="endDateValue"/>
<input type="hidden" id="startDateAvtiveValue" name="startDateAvtiveValue"/>
<input type="hidden" id="endDateAvtiveValue" name="endDateAvtiveValue"/>
</body>
<div id="bg"></div>
<div id="show" align="center"></div>
</html>