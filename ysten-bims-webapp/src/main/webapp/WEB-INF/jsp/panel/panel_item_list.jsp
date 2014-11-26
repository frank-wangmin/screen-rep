<%@ page language="java" pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title></title>
<%@ include file="/include/taglibs.jsp" %>
<%@ include file="/include/operamasks-ui-2.0.jsp" %>
<%@ include file="/include/css.jsp" %>
<%@ include file="/include/ysten.jsp" %>
<script type="text/javascript" src="${cxp}/js/panel-lib.js"></script>
<script type="text/javascript" src="${cxp}/js/panel/panel_item.js"></script>
</head>
<body>
<div id="center-tab">
    <ul>
        <li><a href="#tab1">面板项信息列表:</a></li>
    </ul>
</div>
<table>
    <tr align="left">
        <c:if test="${sessionScope.add_panel_item != null }">
            <td>
                <div id="save"></div>
            </td>
        </c:if>
        <c:if test="${sessionScope.update_panel_item != null }">
            <td>
                <div id="update"></div>
            </td>
        </c:if>
        <%--<c:if test="${sessionScope.delete_panel_item != null }">
            <td>
                <div id="delete"></div>
            </td>
        </c:if>--%>
        <td>ID：</td>
        <td><input id="queryId" class="query-title-input"/></td>
        <td>&nbsp;面板项标题：</td>
        <td><input id="queryPanelTitle" class="query-title-input"/></td>
        <td>&nbsp;面板项名称：</td>
        <td><input id="queryPanelName" class="query-title-input"/></td>
        <td>
            <div id="query"></div>
        </td>
    </tr>
</table>
<table id="grid"></table>

<div id="dialog-form" style="display: none;">
    <form id="myform">
        <input type="hidden" value="" name="id" id="id"/>
        <input type="hidden" value="${picUrl}" name="httpUrl" id="httpUrl"/>
        <input type="hidden" value="${videoUrl}" name="ackVideoUrl" id="ackVideoUrl"/>
        <table>
            <tr>
                <td class="form-add-title"><span class="color_red">*</span>面板项名称：</td>
                <td><input type="text" name="name" id="name" class="form-add-input" onblur="checkNameExists()"/></td>
                <td style="width: 110px;"><span></span>
                    <div id="showResult" class="color_red" style="display: inline;"/>
                </td>
            </tr>
            <tr>
                <td class="form-add-title"><span class="color_red">*</span>面板项标题：</td>
                <td><input name="title" id="title" class="form-add-input"/></td>
                <td style="width: 180px;"><span></span></td>
            </tr>
            <tr>
                <td class="form-add-title"><span class="color_red">*</span>分辨率：</td>
                <td><input id="resolution" name="resolution" class="form-add-input" readonly="readonly"/></td>
                <td style="width: 230px;"><span></span></td>
                <td></td>
            </tr>
            <tr>
                <td class="form-add-title">面板标题说明：</td>
                <td><input type="text" name="titleComment" id="titleComment" class="form-add-input"/></td>
                <td style="width: 110px;"><span></span></td>
            </tr>
            <tr>
                <td class="form-add-title"><span class="color_red">*</span>面板项类型：</td>
                <td><input type="text" name="contentType" id="contentType" class="form-add-input"/></td>
                <td style="width: 110px;"><span></span></td>
            </tr>
            <tr>
                <td class="form-add-title"><span class="color_red">*</span>动作类型：</td>
                <td><input type="text" name="actionType" id="actionType" class="form-add-input"/></td>
            </tr>
            <tr>
                <td class="form-add-title">动作地址：</td>
                <td><input type="text" name="actionUrl" id="actionUrl" class="form-add-input"/></td>
                <td style="width: 110px;"><span></span>

                    <div id="showResultActionUrl" class="color_red" style="display: inline;"/>
                </td>
            </tr>
            <tr style="width: 50px;">
                <td class="form-add-title">动作参数：</td>
                <td style="width: 26px;"><textarea id="params" name="params" cols="31" rows="3"
                                                   onchange="checkParamsRegValid('#params','#showResultParams')"
                                                   style="border:1px solid #86A3C4;"></textarea></td>
                <td style="text-align: left;"><span class="color_error_red">格式:[key:value;]</span>
                    <div id="showResultParams" class="color_red" style="display: inline;"/>
                </td>
            </tr>
            <tr>
                <td class="form-add-title"><span class="color_red">*</span>图片地址：</td>
                <td><input type="text" name="imageUrl" id="imageUrl" class="form-add-input"
                           onchange="checkImageFormatIsValid('#imageUrl','#showResultImageDistr')"/></td>
                <td style="width: 110px;"><span></span>
                    <input type="file" name="fileField2" class="file1" id="fileField2" size="28" style="width: 63px;"
                           onchange="document.getElementById('imageUrl').value=httpUrl.value+this.value"/>

                    <div id="showResultImageDistr" class="color_red" style="display: inline;"/>
                </td>
            </tr>
            <tr id="videoUrlTr">
                <td class="form-add-title"><span class="color_red">*</span>视频地址：</td>
                <td><input type="text" name="videoUrl" id="videoUrl" class="form-add-input"
                           onchange="checkAddressFormatIsValid('#videoUrl','#showResultVideoUrl')"/></td>
                <td><span></span>
                    <input type="file" name="fileField3" class="file1" id="fileField3" size="28" style="width: 63px;"
                           onchange="document.getElementById('videoUrl').value=ackVideoUrl.value+this.value"/>

                    <div id="showResultVideoUrl" class="color_red" style="display: inline;"/>
                </td>
            </tr>

            <tr>
                <td class="form-add-title">内容：</td>
                <td><input type="text" name="content" id="panel_item_content" class="form-add-input"/></td>
                <td style="width: 110px;"><span></span></td>
            </tr>
            <tr>
                <td class="form-add-title"><span class="color_red">*</span>是否包含子项：</td>
                <td><input type="text" name="hasSubItem" id="hasSubItem" class="form-add-input"/></td>
                <td></td>
            </tr>

            <tr id="refItemIdTr">
                <td class="form-add-title" id="refItemIdTitle">关联面板项：</td>
                <td><input type="text" name="refItemId" id="refItemId" class="form-add-input"/></td>
                <td></td>
            </tr>
            <tr id="panelItemParentIdTr">
                <td class="form-add-title" id="panelItemParentIdTitle">父面板项：</td>
                <td><input type="text" name="panelItemParentId" id="panelItemParentId" class="form-add-input"/></td>
                <td></td>
            </tr>
            <tr>
                <td class="form-add-title"><span class="color_red">*</span>是否自动打开：</td>
                <td><input type="text" name="autoRun" id="autoRun"
                           class="form-add-input"/></td>
                <td></td>
            </tr>
            <tr>
                <td class="form-add-title"><span class="color_red">*</span>焦点是否进入：</td>
                <td><input type="text" name="focusRun" id="focusRun"
                           class="form-add-input"/></td>
                <td></td>
            </tr>
            <tr>
                <td class="form-add-title"><span class="color_red">*</span>是否显示标题：</td>
                <td><input type="text" name="showTitle" id="showTitle"
                           class="form-add-input"/></td>
                <td></td>
            </tr>
            <tr>
                <td class="form-add-title"><span class="color_red">*</span>动画：</td>
                <td><input type="text" name="animationRun" id="animationRun"
                           class="form-add-input"/></td>
                <td></td>
            </tr>
            <tr>
                <td class="form-add-title">是否是默认焦点：</td>
                <td><input type="text" name="defaultfocus" id="defaultfocus" class="form-add-input"/></td>
                <td></td>
            </tr>
        </table>
    </form>
</div>

<div id="dialog-form-category-list" style="display: none;">
    <form id="category-form">
        <table>
            <tr>
                <td>栏目ID：</td>
                <td><input name="catgId" id="catgId" class="query-title-input" style="width:120px;"/></td>
                <td>&nbsp;栏目名称：</td>
                <td><input name="name" id="cateName" class="query-title-input" style="width:120px;"/></td>
                <td>
                    <div id="queryCategory"/>
                </td>
                <td>
                    <div id="selectedCategory"/>
                </td>
            </tr>
        </table>
        <table id="gridCategory"></table>
    </form>
</div>
</body>
</html>