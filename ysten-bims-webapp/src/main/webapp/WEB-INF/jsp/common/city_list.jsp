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
    <script type="text/javascript">
        $(document).ready(function() {
            $("#mytree").omTree({
                dataSource : "city_list.json",
                simpleDataModel: true,
                classes:'folder',
                draggable : false,
                showIcon:true
            });

            $("#add").omButton();
            $("#update").omButton();

            var type =null;
            var selection = null;
            var pselection = null;

            $('#add').bind('click', function() {
                type = 'add';
                var bool = checkSelect();
                if(bool == false) return false;
                $("#cityParentId").val(selection.id);
                showDialog('新增地市信息');
            });

            $('#update').bind('click', function() {
                type='update';
                var bool = checkSelect();
                if(bool == false) return false;
                if(selection.pid==0||selection.pid==null){
                    $.omMessageBox.alert({
                        type:'alert',
                        title:'温馨提示',
                        content:'不能修改根节点！',
                    });
                    return false;
                }
                showDialog('修改地市信息');
            });

            var checkSelect= function(){
                selection=$('#mytree').omTree('getSelected');
                if (selection == null || selection.length == 0) {
                    $.omMessageBox.alert({
                        type:'alert',
                        title:'温馨提示',
                        content:'至少得选择一个节点！',
                    });
                    return false;
                }
                pselection = $('#mytree').omTree('getParent', selection);
            };

            var showDialog = function(title){
                validator.resetForm();
                if(type=='update'){
                    //修改时填充信息
                    $.ajax({
                        type:"post",
                        url:"to_city_update.json?id="+selection.id,
                        dataType:"json",
                        async:false,
                        success:function(msg){
                            $("#name").val(msg['name']);
                            $("#code").val(msg['code']);
                            $("#distCode").val(msg['distCode']);
                        }
                    });
                }
                dialog.omDialog("option", "title", title);
                dialog.omDialog("open");
            };

            var dialog = $("#dialog-form").omDialog({
                width: 520, autoOpen : false,
                modal : true, resizable : false,
                draggable : false,
                buttons : {
                    "提交" : function(){
                        if(validator.form()){
                            submitDialog();
                        }
                        return false;
                    },
                    "取消" : function() {
                        resetData();
                        $("#dialog-form").omDialog("close");
                    }
                },onClose:function(){resetData();}
            });

            function resetData(){
                $("#showResult").html('');
                $("#showResult2").html('');
            }

            var submitDialog = function(){
                var submitData;
                if (validator.form()) {
                    if(type=='add'){
                        submitData={
                            leaderId: $("#cityParentId").val(),
                            name:$("#name").val(),
                            code:$("#code").val(),
                            distCode:$("#distCode").val()
                        };
                        $.post('verify_city_add.shtml',submitData,function(result){
                            $('#mytree').omTree('refresh');
                            if("success" == result){
                                $.omMessageTip.show({title: tip, content: "新增地市信息成功！", type:"success" ,timeout: time});
                            }else{
                                $.omMessageTip.show({title: tip, content: "新增地市信息失败！", type:"error" ,timeout: time});
                            }
                            $("#dialog-form").omDialog("close");
                        });
                    }else if(type=='update'){
                        submitData={
                            id: selection.id,
                            name:$("#name").val(),
                            code:$("#code").val(),
                            distCode:$("#distCode").val()
                        };
                        $.post('verify_city_update.shtml',submitData,function(result){
                            $('#mytree').omTree('refresh');
                            if("success" == result){
                                $.omMessageTip.show({title: tip, content: "修改地市信息成功！", type:"success" ,timeout: time});
                            }else{
                                $.omMessageTip.show({title: tip, content: "修改地市信息失败！", type:"error" ,timeout: time});
                            }
                            $("#dialog-form").omDialog("close");
                        });
                    }
                }
            };

            var validator = $('#myform').validate({
                rules : {
                    name : {required: true},
                    code : {required: true},
                    distCode : {required : true}
                },
                messages : {
                    name : {required : "地市名称不能为空！"},
                    code : {required : "地市编号不能为空！"},
                    distCode : {required : "地市标准编号不能为空！"}
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
            $("#tree_wrap").css("height", $(document).height() - 33);
            $('#center-tab').omTabs({height:"33",border:false});
        });
    </script>
</head>
<body>
<div id="center-tab">
    <ul>
        <li><a href="#tab1">地市信息:</a></li>
    </ul>
</div>
<div id="tree_wrap" style="overflow-y: auto;">
    <table>
        <tr>
            <td>
                <c:if test="${sessionScope.add_city != null }">
                    <input id="add" type="button" value="新增" />
                </c:if>
                <c:if test="${sessionScope.update_city != null }">
                    <input id="update" type="button" value="修改" />
                </c:if>
            </td>
        </tr>
        <tr valign="top">
            <td ><ul id="mytree"></ul></td>
        </tr>
    </table>
</div>

<div id="dialog-form">
    <input type="hidden" value="" name="cityParentId" id="cityParentId" />
    <form id="myform">
        <table>
            <tr>
                <td style="text-align: right;"><span class="color_red">*</span>地市名称：</td>
                <td><input id="name" name="name" style="width: 150px; height: 20px; border: 1px solid #86A3C4;"
                           maxlength="20" /></td>
                <td><span></span>
                    <div id="showResult" style="display: inline;" /></td>
            </tr>
            <tr>
                <td style="text-align: right;"><span class="color_red">*</span>地市编号：</td>
                <td><input id="code" name="code" style="width: 150px; height: 20px; border: 1px solid #86A3C4;"
                           maxlength="20" /></td>
                <td><span></span>
                    <div id="showResult2" style="display: inline;" /></td>
            </tr>
            <tr>
                <td style="text-align: right;"><span class="color_red">*</span>国标编号：</td>
                <td><input id="distCode" name="distCode" style="width: 150px; height: 20px; border: 1px solid #86A3C4;"
                           maxlength="20" /></td>
                <td><span></span>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>