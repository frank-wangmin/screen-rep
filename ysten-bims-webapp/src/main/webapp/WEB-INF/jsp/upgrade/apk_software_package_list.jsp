<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title></title>
<%@ include file="/include/taglibs.jsp"%>
<%@ include file="/include/operamasks-ui-2.0.jsp"%>
<%@ include file="/include/css.jsp"%>
<%@ include file="/include/ysten.jsp"%>
<style type="text/css">
    .file-box{ position:relative;width:340px}
    .txt{ height:22px; border:1px solid #cdcdcd; width:180px;}
    .btn{ background-color:#FFF; border:1px solid #CDCDCD;height:24px; width:70px;}
    .file{ position:absolute; top:0; right:80px; height:24px; filter:alpha(opacity:0);opacity: 0;width:260px }
</style>
<script type="text/javascript">
$(document).ready(function() {
	$('#center-tab').omTabs({height:"33",border:false});
    $('#grid').omGrid({
        dataSource : 'apk_software_package_list.json',
        width : '100%',
        height : rFrameHeight,
        singleSelect : false,
        limit : limit,
        colModel : [
            {header:'<b>软件版本序号</b>',name:'versionSeq',align:'center',width:80},
            {header:'<b>软件版本名称</b>',name:'versionName',align:'center',width:120},
            {header:'<b>终端当前版本序号</b>',name:'deviceVersionSeq',align:'center',width:100},
            {header:'<b>软件包类型</b>',name:'packageType',align:'center',width:60},
            {header:'<b>APK软件号名称</b>',name:'softCodeId',align : 'center', width :130,renderer:function(value){
                if(value==null || value==""){
                    return "";
                }else{
                    return value.name;
                }
            }
            },

            {header:'<b>软件号编码</b>',name:'softCodeId',align:'center',width:200,renderer:function(value){
                if(value==null || value==""){
                    return "";
                }else{
                    return value.code;
                }
            }
            },
            {header:'<b>软件包绝对路径</b>',name:'packageLocation',align:'center',width:250},
            {header:'<b>软件包状态</b>',name:'packageStatus',align:'center',width:60},
            {header:'<b>是否强制升级</b>',name:'mandatoryStatus',align:'center',width:80},
            {header:'<b>md5</b>',name:'md5',align:'center',width:250},
            {header:'<b>全量包ID</b>',name:'fullSoftwareId',align:'center',width:50, renderer:function(value, row, index, options){
                var text = value;
                $.each(options.fullPackage, function(i, item){
                    if(item.value == value){
                        text =  item.text;
                    }
                })
                return text;
            }},
            {header:'<b>创建时间</b>',name:'createDate',align:'center',width:120},
            {header:'<b>最后操作时间</b>',name:'lastModifyTime',align:'center',width:120},
            {header:'<b>操作者</b>',name:'operUser',align:'center',width:80}
        ],extraDataSource : [{name:"fullPackage", url:"soft_package.json"}]
    });
    $('#packageStatus').omCombo({
        dataSource : [
            {text : '测试', value : 'TEST'},
            {text : '发布', value : 'RELEASE'}
        ],value:'TEST'
    });
    $('#packageType').omCombo({
        dataSource : [
            {text : '增量', value : 'INCREMENT'},
            {text : '全量', value : 'FULL'}
        ],value:'INCREMENT'
    });
    $('#mandatoryStatus').omCombo({
        dataSource : [
            {text : '强制', value : 'MANDATORY'},
            {text : '不强制', value : 'NOTMANDATORY'}
        ],value:'MANDATORY'
    });
    $('#center-tab').omTabs({height:"33",border:false});
    $('#create').omButtonbar({btns : [{label:"新增",icons : {left : opPath+'add.png'}}]});
    $('#update').omButtonbar({btns : [{label:"修改",icons : {left : opPath+'op-edit.png'}}]});
    $('#query').omButtonbar({btns : [{label:"查询",icons : {left : opPath+'search.png'}}]});
    $("#softPackageQuery").omButton();
    $("#selectFullSoftCode").omButton();
    
    $('#query').bind('click', function(e) {
        var versionName = filterStartEndSpaceTrim($('#versionName').val());
        var softCodeName = filterStartEndSpaceTrim($('#softCodeName').val());
        $('#grid').omGrid("setData", 'apk_software_package_list.json?versionName='+encodeURIComponent(versionName)+'&softCodeName='+encodeURIComponent(softCodeName));
    });

    var table1 = $("#soft-package-form").omDialog({
        width: 1000,height:440, autoOpen : false,resizable : false,
        draggable : false,modal : true,
        buttons : {
            "确定" : function(){
                var selections=$('#soft-package-grid').omGrid('getSelections',true);
                softPackages = "";
                softPackage = "";
                if(selections != null && selections.length > 0){
                    for(var i=0;i<selections.length;i++){
                        if(i != selections.length - 1){
                            softPackages += selections[i].id+",";
                            softPackage += selections[i].versionName+",";
                        }else{
                            softPackages += selections[i].id;
                            softPackage += selections[i].versionName;
                        }
                    }
                }
                $("#fullSoftwareId").val(softPackages);
                $("#fullSoftware").val(softPackage);
                $("#soft-package-form").omDialog("close");
            },
            "取消" : function(){
                $("#soft-package-form").omDialog("close");
            }
        }
    });

    function showTable1(title){
        table1.omDialog("option", "title", title);
        table1.omDialog("open");
    }
    $('#softPackageQuery').bind('click', function(e) {
    	var softCodeIds = filterStartEndSpaceTrim($("#softCodeId").val());
        var versionName = filterStartEndSpaceTrim($('#versionName2').val());
        $('#soft-package-grid').omGrid("setData", 'find_apk_software_package_list.json?softCodeId='+encodeURIComponent(softCodeIds)+'&packageType=FULL&name='+encodeURIComponent(versionName));
    });
    $('#selectFullSoftCode').click(function(){
        var softCodeIds = $("#softCodeId").val();
        $('#soft-package-grid').omGrid({
            dataSource : 'find_apk_software_package_list.json?softCodeId='+encodeURIComponent(softCodeIds)+'&packageType=FULL',
            width : 980, height : "99%",
            singleSelect : true,
            limit : 10,
            colModel : [
                {header:'APK软件号',name:'softCodeId',align:'center',align : 'center', width :130,renderer:function(value){
                    if(value.name !=null && value.name != ""){
                        return value.name;
                    }
                    else{
                        return value;
                    }
                }
                },

                {header:'软件号编码',name:'softCodeId',align:'center',width:130,renderer:function(value){
                    if(value.code !=null && value.code != ""){
                        return value.code;
                    }
                    else{
                        return value;
                    }
                }
                },
                {header:'软件版本序号',name:'versionSeq',align:'center',width:120},
                {header:'软件版本名称',name:'versionName',align:'center',width:200},
                {header:'终端当前版本序号',name:'deviceVersionSeq',align:'center',width:120},
                {header:'全量包ID',name:'fullSoftwareId',align:'center',width:120},
                {header:'软件包类型',name:'packageType',align:'center',width:200},
                {header:'软件包绝对路径',name:'packageLocation',align:'center',width:250},
                {header:'软件包状态',name:'packageStatus',align:'center',width:120},
                {header:'是否强制升级',name:'mandatoryStatus',align:'center',width:120},
                {header:'md5',name:'md5',align:'center',width:250},
                {header:'创建时间',name:'createDate',align:'center',width:250}
            ]
        });
        showTable1('设备软件包信息');
    });
    var dialog = $("#dialog-form").omDialog({
        width: 650,
        height:470,
        autoOpen : false,
        modal : true,
        resizable : false,
        draggable : false,
        buttons : {
            "提交" : function(){
                var packageType = $("#packageType").val();
                var fullSoftwareId = $("#fullSoftwareId").val();
                var sr = $("#showResult").html();
                if(packageType == "INCREMENT" && (fullSoftwareId == ""||fullSoftwareId == null)){
                    $("#showResult1").text("请选择全量的软件包！");
                    return false;
                }
                if(sr=='' || sr =='可用!'){
                    submitDialog();
                }
                return false;
            },
            "取消" : function() {
                $("#showResult").text("");
                $("#showResult1").text("");
                $("#dialog-form").omDialog("close");
            }
        },onClose:function(){$("#showResult").text("");$("#showResult1").text("");}
    });

    var showDialog = function(title,rowData){
        validator.resetForm();
        rowData = rowData || {};
        dialog.omDialog("option", "title", title);
        dialog.omDialog("open");
    };
    var submitDialog = function(){
        var submitData;
        if ($("#packageType").val() == "INCREMENT") {
            $("#deviceVersionSeq").rules("add", "required");
        } else {
            $("#deviceVersionSeq").rules("remove", "required");
        }
        if (validator.form()) {
            if(isAdd){
                submitData={
                    packageType:$("#packageType").val(),
                    softCodeId:$("#softCodeId").val(),
                    versionSeq:$("#versionSeq").val(),
                    versionName:$("#versionName1").val(),
                    packageStatus:$("#packageStatus").val(),
                    mandatoryStatus:$("#mandatoryStatus").val(),
                    deviceVersionSeq:$("#deviceVersionSeq").val(),
                    md5:$("#md5").val(),
                    packageLocation:$("#packageLocation").val(),
                    fullSoftwareId:$("#fullSoftwareId").val()
                };
                $.post('apk_soft_package_add.json',submitData,function(result){
                    $('#grid').omGrid('reload');
                    if("success" == result){
                        $.omMessageTip.show({title: tip, content: "新增成功！", type:"success" ,timeout: time});
                    }else{
                        $.omMessageTip.show({title: tip, content: "新增失败！", type:"error" ,timeout: time});
                    }
                    $("#dialog-form").omDialog("close");
                });
            }else{
                submitData={
                    id:$("#id").val(),
                    packageType:$("#packageType").val(),
                    softCodeId:$("#softCodeId").val(),
                    versionSeq:$("#versionSeq").val(),
                    versionName:$("#versionName1").val(),
                    packageStatus:$("#packageStatus").val(),
                    mandatoryStatus:$("#mandatoryStatus").val(),
                    deviceVersionSeq:$("#deviceVersionSeq").val(),
                    md5:$("#md5").val(),
                    packageLocation:$("#packageLocation").val(),
                    fullSoftwareId:$("#fullSoftwareId").val()
                };
                $.post('apk_soft_package_update.json',submitData,function(result){
                    $('#grid').omGrid('reload');
                    if("success" == result){
                        $.omMessageTip.show({title: tip, content: "修改成功！", type:"success" ,timeout: time});
                    }else{
                        $.omMessageTip.show({title: tip, content: "修改失败！", type:"error" ,timeout: time});
                    }
                    $("#dialog-form").omDialog("close");
                });
            }
        }
    };
    var isAdd = true;
    $('#create').bind('click', function() {
    	$('#softCodeId').omCombo({
            dataSource : 'apk_soft_code.json',editable:false,width:181,listMaxHeight:150,value:''});
        isAdd = true;
        showDialog('新增APK升级软件包信息');//显示dialog
    });
    $('#update').bind('click', function() {
        var selections=$('#grid').omGrid('getSelections',true);
        if (selections.length == 0 || selections.length > 1) {
            $.omMessageBox.alert({
                type:'alert',
                title:'温馨提示',
                content:'修改操作至少且只能选择一行记录！',
            });
            return false;
        }
        isAdd = false;
        $.ajax({
            type:"post",
            url:"apk_soft_package_to_update.json?id="+selections[0].id,
            dataType:"json",
            success:function(msg){
                $("#id").val(msg['id']);
                $('#packageType').omCombo({
                    dataSource : [
                        {text : '增量', value : 'INCREMENT'},
                        {text : '全量', value : 'FULL'}
                    ],value:msg['packageType']
                });
                $('#softCodeId').omCombo({
                    dataSource : 'apk_soft_code.json',editable:false,width:180,listMaxHeight:150,value:msg['softCodeId'].id});
                $("#versionSeq").val(msg['versionSeq']);
                $("#versionName1").val(msg['versionName']);
                $('#packageStatus').omCombo({
                    dataSource : [
                        {text : '测试', value : 'TEST'},
                        {text : '发布', value : 'RELEASE'}
                    ],value:msg['packageStatus']
                });
                $('#mandatoryStatus').omCombo({
                    dataSource : [
                        {text : '强制', value : 'MANDATORY'},
                        {text : '不强制', value : 'NOTMANDATORY'}
                    ],value:msg['mandatoryStatus']
                });
                $("#deviceVersionSeq").val(msg['deviceVersionSeq']);
                $("#md5").val(msg['md5']);
                $("#packageLocation").val(msg['packageLocation']);
                $("#fullSoftwareId").val(msg['fullSoftwareId']);
            }
        });
        $.ajax({
            type:"post",
            url:"apk_soft_package_to_update.json?id="+selections[0].fullSoftwareId,
            dataType:"json",
            success:function(msg){
                $('#fullSoftware').val(msg['versionName']);
            }
        });
        showDialog('修改APK升级软件包信息',selections[0]);
    });
    var validator = $('#myform').validate({
        rules : {
            packageType:{required: true},
            softCodeId:{required: true},
            versionName1:{required: true,maxlength:32},
            versionSeq:{required: true,digits:true,maxlength:9},
            packageStatus:{required: true},
            mandatoryStatus:{required: true},
            deviceVersionSeq:{digits:true,maxlength:9},
            md5:{required: true,maxlength:32},
            packageLocation:{required: true,maxlength:255}
        },
        messages : {
            packageType:{required:"软件包类型不能为空！"},
            softCodeId:{required:"软件号不能为空！"},
            versionName1:{required:"软件版本名称不能为空！",maxlength:"最大长度为32位字符"},
            versionSeq:{required:"软件版本序号不能为空！",digits:"请输入数字！",maxlength:"最大长度为9位字符"},
            packageStatus:{required:"软件包状态不能为空！"},
            mandatoryStatus:{required:"是否强制升级不能为空！"},
            deviceVersionSeq:{required:"终端当前版本号不能为空！", digits:"请输入数字！",maxlength:"最大长度为9位字符"},
            md5:{required:"MD5值不能为空！",maxlength:"最大长度为32位字符"},
            packageLocation:{required:"软件包绝对路径不能为空！",maxlength:"最大长度为255位字符"}
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
})
function checkNameExists(){
    if($.trim($("#versionName1").val())!=""){
        var par = new Object();
        par['versionName'] = $("#versionName1").val();
        par['id'] = $("#id").val();
        $.ajax({
            type:"post",
            url:"apk_software_package_name_exists.shtml?s="+Math.random(),
            dataType:"html",
            data:par,
            success:function(msg){
                //alert(msg);
                $("#showResult").html(msg);
            }
        });
    }else if($.trim($("#name1").val())=="" && $("#showResult").html() != ""){
        $("#showResult").html("");
    }
}
function resetData(){
    $("#showResult").html('');
}
</script>
</head>
<body>
<div id="center-tab">
    <ul>
        <li><a href="#tab1">APK软件包信息列表:</a></li>
    </ul>
</div>
<table >
    <tbody>
    <tr>
        <c:if test="${sessionScope.add_apk_software_package != null }">
            <td><div id="create"></div></td>
        </c:if>
        <c:if test="${sessionScope.update_apk_software_package != null }">
            <td><div id="update"></div></td>
        </c:if>
        <td style="text-align:center;">APK软件版本名称：</td>
        <td>
            <input type="text" name="versionName" id="versionName" style="width:200px;height: 20px;border:1px solid #86A3C4;"/>
        </td>
        <td style="text-align:center;">APK软件信息号名称：</td>
        <td>
            <input type="text" name="softCodeName" id="softCodeName" style="width:200px;height: 20px;border:1px solid #86A3C4;"/>
        </td>
        <td>
            <div id="query"/>
        </td>
    </tr>
    </tbody>
</table>
<table id="grid" ></table>
<div id="soft-package-div" style="display: none;">
    <form id="soft-package-form">
        <table>
            <tr>
                <td>APK软件版本名称：</td>
                <td>
                    <input type="text" name="versionName2" id="versionName2" style="width:200px;height: 20px;border:1px solid #86A3C4;"/>
                </td>
                <td>
                    <input id="softPackageQuery" type="button" value="查询"/>
                </td>
            </tr>
        </table>
        <table id="soft-package-grid" ></table>
    </form>
</div>
<div id="dialog-form">
    <form id="myform">
        <input type="hidden" value="" name="id" id="id"/>
        <table>
            <tr>
                <td style="width: 180px;text-align: right;"><span class="color_red">*</span>软件包类型：</td>
                <td><input id="packageType" name="packageType" style="width:161px"/></td>
                <td><span></span></td>
            </tr>
            <tr>
                <td style="width: 100px;text-align: right;"><span class="color_red">*</span>APK软件信息号：</td>
                <td><input id="softCodeId" name="softCodeId" style="width:161px" readonly="readonly"/>
                </td>
                <td><span></span></td>

            </tr>
            <tr>
                <td style="width: 100px;text-align: right;"><span class="color_red">*</span>软件版本序号： </td>
                <td><input id="versionSeq" name="versionSeq" style="width:180px;height:20px;border:1px solid #86A3C4;"/></td>
                <td><span></span></td>
            </tr>
            <tr>
                <td style="width: 100px;text-align: right;"><span class="color_red">*</span>软件版本名称：</td>
                <td><input id="versionName1" name="versionName1" style="width:180px;height:20px;border:1px solid #86A3C4;"onblur="checkNameExists()"/></td>
                <td><span></span><div id="showResult" class="color_red" style="display: inline;"/></td>
            </tr>
            <tr>
                <td style="width: 100px;text-align: right;"><span class="color_red">*</span>软件包状态：</td>
                <td><input id="packageStatus" name="packageStatus" style="width:161px"/></td>
                <td><span></span></td>
            </tr>
            <tr>
                <td style="width: 100px;text-align: right;"><span class="color_red">*</span>是否强制升级：</td>
                <td><input id="mandatoryStatus" name="mandatoryStatus" style="width:161px"/></td>
                <td><span></span></td>
            </tr>
            <tr>
                <td style="width: 100px;text-align: right;">终端当前版本序号：</td>
                <td><input id="deviceVersionSeq" name="deviceVersionSeq" style="width:180px;height:20px;border:1px solid #86A3C4;"/></td>
                <td><span></span></td>
            </tr>
            <tr>
                <td style="width: 100px;text-align: right;"><span class="color_red">*</span>MD5：</td>
                <td><input id="md5" name="md5" style="width:180px;height:20px;border:1px solid #86A3C4;" maxlength="32"/></td>
                <td><span></span></td>
            </tr>
            <tr>
                <td style="width: 100px;text-align: right;"><span class="color_red">*</span>软件包绝对路径：</td>
                <td >
                    <textarea id="packageLocation" name="packageLocation" cols="28" rows="8" style="border:1px solid #86A3C4;" maxlength="255"></textarea>
                </td><td><span></span></td>
            </tr>
            <tr>
                <td style="width: 100px;text-align: right;">全量软件包：</td>
                <td><input id="fullSoftware" name="fullSoftware" style="width:180px;height: 20px;border:1px solid #86A3C4;" readonly="readonly"/>
                    <input id="selectFullSoftCode" type="button" value="选择"/><input id="fullSoftwareId" name="fullSoftwareId" hidden="ture"></input></td>
                <td><span></span><div id="showResult1" class="color_red" style="display: inline;"/></td>

            </tr>
        </table>
    </form>
</div>
</body>
</html>
