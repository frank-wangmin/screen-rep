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
<script type="text/javascript" src="${cxp}/js/panel/navigation.js"></script>
</head>
<body>
<div id="center-tab">
    <ul>
        <li><a href="#tab1">导航信息列表:</a></li>
    </ul>
</div>
<table>
    <tr>
        <c:if test="${sessionScope.add_panel_nav != null }">
            <td>
                <div id="create"/>
            </td>
        </c:if>
        <c:if test="${sessionScope.update_panel_nav != null }">
            <td>
                <div id="update"/>
            </td>
        </c:if>
        <%-- <c:if test="${sessionScope.delete_panel_nav != null }">
             <td>
                 <div id="delete"/>
             </td>
         </c:if>--%>
    </tr>
</table>
<table>
    <tr>
        <td>&nbsp;ID：</td>
        <td><input id="queryId" class="query-title-input"/></td>
        <td>&nbsp;导航名称：</td>
        <td><input id="queryPanelName" class="query-title-input"/></td>
        <td>&nbsp;导航标题：</td>
        <td><input id="queryPanelTitle" class="query-title-input"/></td>
        <td>&nbsp;导航类型：</td>
        <td><input id="navType1" name="navType1" class="query-title-input"/></td>
        <td>&nbsp;动作类型：</td>
        <td><input id="actionType1" name="actionType1" class="query-title-input"/></td>
        <td>
            <div id="query"></div>
        </td>
    </tr>
</table>
<table id="grid"></table>
<div id="dialog-form">
    <form id="myform">
        <input type="hidden" value="" name="id" id="id"/>
        <input type="hidden" value="${picUrl}" name="httpUrl" id="httpUrl"/>
        <table>
            <tr>
                <td class="form-add-title"><span class="color_red">*</span>导航名称：</td>
                <td><input id="navName" name="navName" style="width:180px;height:20px;border:1px solid #86A3C4;"
                           onblur="checkNameExists()"/></td>
                <td><span></span>
                    <div id="showResult" class="color_red" style="display: inline;"/>
                </td>
            </tr>
            <tr>
                <td class="form-add-title"><span class="color_red">*</span>导航标题：</td>
                <td><input id="title" name="title" style="width:180px;height:20px;border:1px solid #86A3C4;"/></td>
            </tr>
            <tr>
                <td class="form-add-title"><span class="color_red">*</span>分辨率：</td>
                <td><input id="resolution" name="resolution" style="width:161px;" readonly="readonly"/></td>
                <td style="width: 230px;"><span></span></td>
            </tr>
            <tr>
                <td class="form-add-title">面板标题说明：</td>
                <td><input id="titleComment" name="titleComment"
                           style="width:180px;height:20px;border:1px solid #86A3C4;"/></td>
                <td><span></span></td>
            </tr>
            <tr>
                <td class="form-add-title"><span class="color_red">*</span>导航类型：</td>
                <td><input id="navType" name="navType" style="width:161px;" readonly="readonly"/></td>
                <td><span></span></td>
            </tr>
            <tr id="topNavTypeTr">
                <td class="form-add-title">快捷入口类型：</td>
                <td><input name="topNavType" id="topNavType" style="width:161px;" readonly="readonly"/></td>
                <td style="width: 230px;"><span></span></td>
            </tr>
            <tr>
                <td class="form-add-title"><span class="color_red">*</span>是否显示标题：</td>
                <td><input id="showTitle" name="showTitle" style="width:161px;" readonly="readonly"/></td>
                <td><span></span>

                    <div id="showResult2" class="color_red" style="display: inline;"/>
                </td>
            </tr>
            <tr>
                <td class="form-add-title">动作类型：</td>
                <td><input id="actionType" name="actionType" style="width:161px;" readonly="readonly"/></td>
                <td><span></span></td>
            </tr>
            <tr>
                <td class="form-add-title">动作地址：</td>
                <td><input type="text" name="actionUrl" id="actionUrl"
                           style="width:180px;height:20px;border:1px solid #86A3C4;"/></td>
                <td><span></span>

                    <div id="showResultActionUrl" class="color_red" style="display: inline;"/>
                </td>
            </tr>
            <tr style="width: 50px;">
                <td class="form-add-title">动作参数：</td>
                <td style="width: 26px;"><textarea id="params" name="params" cols="28" rows="3"
                                                   onchange="checkParamsRegValid('#params','#showResultParams')"
                                                   style="border:1px solid #86A3C4;"></textarea></td>
                <td style="text-align: left;"><span class="color_error_red">格式:[key:value;]</span>

                    <div id="showResultParams" class="color_red" style="display: inline;"/>
                </td>
            </tr>
            <tr>
                <td class="form-add-title">图片地址：</td>
                <td><input type="text" name="imageUrl" id="imageUrl"
                           style="width:180px;height:20px;border:1px solid #86A3C4;" onchange="checkImageFormatIsValid('#imageUrl','#showResult1')
                   "/></td>
                <td><span></span>
                    <input type="file" name="imageFile" class="file1" id="imageFile" size="28" style="width: 63px;"
                           onchange="document.getElementById('imageUrl').value=this.value"/>
                    <div id="showResult1" class="color_red" style="display: inline;"/>
                </td>
            </tr>
            <%--<tr>
                <td class="form-add-title">图片分发地址：</td>
                <td><input type="text" name="imageDisturl" id="imageDisturl"
                           style="width:180px;height:20px;border:1px solid #86A3C4;"
                           onchange="checkImageFormatIsValid('#imageDisturl','#showResultImageDistr')"/></td>
                <td><span></span>
                    <input type="file" name="fileField2" class="file2" id="fileField2" size="28" style="width: 63px;"
                           onchange="document.getElementById('imageDisturl').value=httpUrl.value+this.value"/>

                    <div id="showResultImageDistr" class="color_red" style="display: inline;"/>
                </td>
            </tr>--%>
            <tr>
                <td class="form-add-title">高亮显示图片：</td>
                <td><input type="text" name="focusImg" id="focusImg"
                           style="width:180px;height:20px;border:1px solid #86A3C4;"
                           onchange="checkImageFormatIsValid('#focusImg','#showResultFocusImg')"/></td>
                <td><span></span>
                    <input type="file" name="focusImgFile" class="file3" id="focusImgFile" size="28" style="width: 63px;"
                           onchange="document.getElementById('focusImg').value=this.value"/>
                    <div id="showResultFocusImg" class="color_red" style="display: inline;"/>
                </td>
            </tr>
            <tr>
                <td class="form-add-title">焦点停留图片：</td>
                <td><input type="text" name="currentPageImg" id="currentPageImg"
                           style="width:180px;height:20px;border:1px solid #86A3C4;"
                           onchange="checkImageFormatIsValid('#currentPageImg','#showResultCurrentPageImg')"/></td>
                <td><span></span>
                    <input type="file" name="currentImageFile" class="file4" id="currentImageFile" size="28" style="width: 63px;"
                           onchange="document.getElementById('currentPageImg').value=this.value"/>
                    <div id="showResultCurrentPageImg" class="color_red" style="display: inline;"/>
                </td>
            </tr>
            <tr>
                <td class="form-add-title">对齐方式：</td>
                <td><input name="align" id="align" style="width:161px;" readonly="readonly"/></td>
                <td style="width: 230px;"><span></span></td>
            </tr>
            <tr>
                <td class="form-add-title">是否允许落焦点：</td>
                <td><input name="canfocus" id="canfocus" style="width:161px;" readonly="readonly"/></td>
                <td style="width: 230px;"><span></span></td>
            </tr>
            <%--<tr>
                <td class="form-add-title"><span class="color_red">*</span>排序号：</td>
                <td><input id="sortNum" name="sortNum" style="width:180px;height:20px;border:1px solid #86A3C4;"/></td>
                <td><span></span></td>
            </tr>--%>
        </table>
    </form>
</div>
</body>
</html>