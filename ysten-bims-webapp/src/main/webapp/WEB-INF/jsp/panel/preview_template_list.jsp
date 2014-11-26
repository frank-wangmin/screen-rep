<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>

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
<script type="text/javascript" src="${cxp}/js/panel/preview_template.js"></script>
</head>
<body>
<div id="center-tab">
    <ul>
        <li><a href="#tab1">预览模板信息列表:</a></li>
    </ul>
</div>
<div id="panel_layout" style="display: none">
    <%--<div class='panel_box' style="width:200px;height: 200px"><a class="edit_box_link" href="#" onclick="editBox(1)">编辑</a><img style="width:200px;height: 200px" src="/images/404.jpg" alt=""/></div>--%>
</div>
<table>
    <tr align="left">
        <c:if test="${sessionScope.add_preview_template != null }">
            <td>
                <div id="save"></div>
            </td>
        </c:if>
        <c:if test="${sessionScope.update_preview_template != null }">
            <td>
                <div id="update"></div>
            </td>
        </c:if>
        <%--<c:if test="${sessionScope.delete_preview_template != null }">
            <td>
                <div id="delete"></div>
            </td>
        </c:if>--%>
        <c:if test="${sessionScope.panel_template_config != null }">
            <td>
                <div id="preview_template"></div>
            </td>
        </c:if>
        <td>&nbsp;ID：</td>
        <td><input name="queryId" id="queryId" class="query-title-input"/></td>
        <td>&nbsp;模板名称：</td>
        <td><input name="content2" id="queryPanelName" class="query-title-input"/></td>
        <td>
            <div id="query"></div>
        </td>
    </tr>
</table>
<table id="grid"></table>

<div id="dialog-form" style="display: none;">
    <input type="hidden" value="" id="templateId"/>
    <form id="myform">
        <input type="hidden" value="" name="id" id="id"/>
        <table>
            <tr>
                <td class="form-add-title"><span class="color_red">*</span>模板名称：</td>
                <td><input type="text" name="name" id="name" class="form-add-input" maxlength="17"/></td>
                <td style="width: 170px;"><span></span></td>
            </tr>
            <%--<tr>
                <td class="form-add-title">平台ID：</td>
                <td><input type="text" name="platformId" id="platformId"  class="form-add-input"/></td>
            </tr>--%>
            <tr>
                <td class="form-add-title">是否包含节目集：</td>
                <td><input type="text" name="containsPs" id="containsPs" class="form-add-input"/></td>
            </tr>
            <tr>
                <td class="form-add-title">描述：</td>
                <td><textarea id="description" name="description" cols="31" rows="4" class="form-border-style"></textarea>
                </td>
            </tr>
        </table>
    </form>
</div>

<div id="box-form" style="display: none;">
    <input type="hidden" value="" id="templateBoxIndex"/>
    <form id="boxForm">
        <table>
            <tr>
                <td class="form-add-title">上（top）：</td>
                <td><input type="text" name="top" id="top" class="form-add-input" maxlength="17"/></td>
            </tr>
            <tr>
                <td class="form-add-title">左（left）：</td>
                <td><input type="text" name="left" id="boxLeft" class="form-add-input"/></td>
            </tr>
            <tr>
                <td class="form-add-title">宽（width）：</td>
                <td><input type="text" name="width" id="width"  class="form-add-input"/></td>
            </tr>
            <tr>
                <td class="form-add-title">高（height）：</td>
                <td><input type="text" name="height" id="height" class="form-add-input"/></td>
            </tr>
            <tr>
                <td class="form-add-title">顺序：</td>
                <td><input type="text" name="sort" id="sort" class="form-add-input" style="width:120px;"/></td>
            </tr>
        </table>
    </form>
</div>

</body>
</html>