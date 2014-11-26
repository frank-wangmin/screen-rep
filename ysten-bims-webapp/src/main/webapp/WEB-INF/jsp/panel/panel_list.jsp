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
<script type="text/javascript" src="${cxp}/js/panel/panel_list.js"></script>
</head>
<body>
<div id="center-tab">
    <ul>
        <li><a href="#tab1">面板信息列表:</a></li>
    </ul>
</div>
<table>
    <tr align="left">
        <c:if test="${sessionScope.add_panel != null }">
            <td>
                <div id="save"></div>
            </td>
        </c:if>
        <c:if test="${sessionScope.update_panel != null }">
            <td>
                <div id="update"></div>
            </td>
        </c:if>
        <c:if test="${sessionScope.delete_panel != null }">
            <td>
                <div id="delete"></div>
            </td>
        </c:if>
        <c:if test="${sessionScope.bind_panel_package != null }">
            <td>
                <div id="bindPackage"/>
            </td>
        </c:if>
        <c:if test="${sessionScope.panel_config != null }">
            <td>
                <div id="previewPanel"/>
            </td>
        </c:if>

        <c:if test="${sessionScope.syns_epg_panel_data != null }">
            <td>
                <div id="synsPanel"/>
            </td>
        </c:if>

        <c:if test="${sessionScope.push_province_panel_data != null }">
            <td>
                <div id="pushPanel"/>
            </td>
        </c:if>
        <%--
      <c:if test="${sessionScope.syns_center_panel_data != null }">
          <td>
              <div id="syncCenterPanel"/>
          </td>
      </c:if>
     <c:if test="${sessionScope.panel_panel_distribute != null }">
            <td>
                <div id="panelDistribute"/>
            </td>
        </c:if>--%>
        <c:if test="${sessionScope.config_online_panel != null }">
            <td>
                <div id="panelOnline"/>
            </td>
        </c:if>
        <c:if test="${sessionScope.config_downline_panel != null }">
            <td>
                <div id="panelDownline"/>
            </td>
        </c:if>
    </tr>
</table>
<table>
    <tr>
        <td>ID：</td>
        <td><input id="queryId" class="query-title-input"/></td>
        <td>&nbsp;面板名称：</td>
        <td><input id="queryPanelName" class="query-title-input"/></td>
        <td>&nbsp;面板标题：</td>
        <td><input id="queryPanelTitle" class="query-title-input"/></td>
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
            <tr id="panelNameTr">
                <td class="form-add-title"><span class="color_red">*</span>面板名称：</td>
                <td><input type="text" name="panelName" id="panelName" class="form-add-input" onblur="checkNameExists()"
                        /></td>
                <td style="width: 170px;"><span></span>

                    <div id="showResult" class="color_red" style="display: inline;"/>
                </td>
            </tr>
            <tr>
                <td class="form-add-title"><span class="color_red">*</span>分辨率：</td>
                <td><input name="resolution" id="resolution" class="form-add-input"/></td>
                <td style="width: 230px;"><span></span></td>
            </tr>
            <tr id="isCustomTr">
                <td class="form-add-title">是否自定义：</td>
                <td><input type="text" name="isCustom" id="isCustom"
                           class="form-border-style"/></td>
            </tr>
            <tr id="panelTitleTr">
                <td class="form-add-title"><span class="color_red">*</span>面板标题：</td>
                <td><input name="panelTitle" id="panelTitle" class="form-add-input"/></td>
                <td style="width: 170px;"><span></span></td>
            </tr>
            <tr id="panelStyleTr">
                <td class="form-add-title"><span class="color_red">*</span>面板式样：</td>
                <td><input type="text" name="panelStyle" id="panelStyle"
                           class="form-add-input"/></td>
                <td style="width: 170px;"><span></span></td>
            </tr>
            <tr id="templateIdTr">
                <td class="form-add-title"><span class="color_red">*</span>面板模板：</td>
                <td><input type="text" name="templateId" id="templateId"
                           class="form-add-input"/></td>
                <td style="width: 170px;"><span></span></td>
            </tr>
            <tr id="panelIconTr">
                <td class="form-add-title">面板图标：</td>
                <td><input type="text" name="panelIcon" id="panelIcon"
                           class="form-add-input"
                           onchange="checkImageFormatIsValid('#panelIcon','#showResultpanelIcon')"/></td>
                <td style="width: 170px;"><span></span>
                    <input type="file" name="fileField1" class="file1" id="fileField1" size="28" style="width: 63px;"
                           onchange="document.getElementById('panelIcon').value=this.value"/>

                    <div id="showResultpanelIcon" class="color_red" style="display: inline;"/>
                </td>
            </tr>

            <tr id="linkUrlTr">
                <td class="form-add-title">链接地址：</td>
                <td><input type="text" name="linkUrl" id="linkUrl"
                           class="form-add-input"/></td>
                <td style="width: 170px;"><span></span>

                    <div id="showResultLinkUrl" class="color_red" style="display: inline;"/>
                </td>
            </tr>
            <tr id="imgUrlTr">
                <td class="form-add-title">背景图片：</td>
                <td><input type="text" name="imgUrl" id="imgUrl"
                           class="form-add-input" onchange="checkImageFormatIsValid('#imgUrl','#showResultImageUrl')"/>
                </td>
                <td style="width: 170px;"><span></span>
                    <input type="file" name="fileField2" class="file2" id="fileField2" size="28" style="width: 63px;"
                           onchange="document.getElementById('imgUrl').value=this.value"/>

                    <div id="showResultImageUrl" class="color_red" style="display: inline;"/>
                </td>
            </tr>
            <tr id="bigimgTr">
                <td class="form-add-title"><span class="color_red">*</span>定制页大图：</td>
                <td><input type="text" name="bigimg" id="bigimg"
                           class="form-add-input" onchange="checkImageFormatIsValid('#bigimg','#showResultbigimg')"/>
                </td>
                <td style="width: 170px;"><span></span>
                    <input type="file" name="fileField3" class="file3" id="fileField3" size="28" style="width: 63px;"
                           onchange="document.getElementById('bigimg').value=this.value"/>

                    <div id="showResultbigimg" class="color_red" style="display: inline;"/>
                </td>
            </tr>
            <tr id="smallimgTr">
                <td class="form-add-title"><span class="color_red">*</span>定制页小图：</td>
                <td><input type="text" name="smallimg" id="smallimg"
                           class="form-add-input"
                           onchange="checkImageFormatIsValid('#smallimg','#showResultsmallimg')"/></td>
                <td style="width: 170px;"><span></span>
                    <input type="file" name="fileField" class="file1" id="fileField" size="28" style="width: 63px;"
                           onchange="document.getElementById('smallimg').value=this.value"/>

                    <div id="showResultsmallimg" class="color_red" style="display: inline;"/>
                </td>
            </tr>
        </table>
    </form>
</div>

<div id="bind-package-form" style="display: none;">
    <form id="bindPackageForm">
        <input type="hidden" value="" name="panelId" id="panelId"/>
        <table>
            <tr>
                <td class="form-add-title"><span class="color_red">*</span>面板包：</td>
                <td><input type="text" name="packageId" id="packageId"
                           class="form-border-style"/></td>
                <td style="width: 170px;"><span></span></td>
            </tr>
            <tr>
                <td class="form-add-title">上部导航：</td>
                <td><input type="text" name="upNavIds" id="upNavIds"
                           class="form-add-input"/></td>
            </tr>
            <tr>
                <td class="form-add-title"><span class="color_red">*</span>下部导航：</td>
                <td><input type="text" name="downNavId" id="downNavId"
                           class="form-add-input"/></td>
                <td style="width: 170px;"><span></span></td>
            </tr>
            <tr>
                <td class="form-add-title">面板LOGO：</td>
                <td><input type="text" name="panelLogo" id="panelLogo"
                           class="form-add-input" style="width:178px;"/></td>
            </tr>
            <tr id="displayTr">
                <td class="form-add-title">是否显示：</td>
                <td><input type="text" name="display" id="display"
                           class="form-border-style"/></td>
            </tr>
            <tr id="isLockTr">
                <td class="form-add-title">是否锁定：</td>
                <td><input type="text" name="isLock" id="isLock"
                           class="form-border-style"/></td>
            </tr>
            <tr>
                <td class="form-add-title">排序：</td>
                <td><input type="text" name="sortNum" id="sortNum"
                           class="form-add-input" style="width:178px;"/></td>
            </tr>

        </table>
    </form>
</div>

<div id="panel-item-form" style="display: none;">
    <input type="hidden" value="" id="previewItemDataIndex"/>

    <form id="panelItemForm">
        <input type="hidden" value="" id="panelItemId"/>
        <table>
            <tr id="selectExistTr">
                <td class="form-add-title">是否选择已有面板项：</td>
                <td><input type="text" name="selectExist" id="selectExist" class="form-add-input"
                           maxlength="17"/></td>
            </tr>
            <tr style="display: none" id="existPanelItemIdTr">
                <td class="form-add-title"><span class="color_red">*</span>已存在面板项：</td>
                <td><input type="text" name="existPanelItemId" id="existPanelItemId" class="form-add-input"
                           maxlength="17"/></td>
                <td style="width: 210px;"><span></span></td>
            </tr>
            <tr>
                <td class="form-add-title"><span class="color_red">*</span>面板项名称：</td>
                <td><input type="text" name="name" id="name" class="form-add-input"
                           onblur="checkPanelItemNameExists()"/></td>
                <td style="width: 110px;"><span></span>
                    <div id="showResult1" class="color_red" style="display: inline;"/>
                </td>
            </tr>
            <tr>
                <td class="form-add-title"><span class="color_red">*</span>分辨率：</td>
                <td><input id="resolution_panelItem" name="resolution_panelItem" class="form-add-input"
                           readonly="readonly"/></td>
                <td style="width: 230px;"><span></span></td>
            </tr>
            <tr>
                <td class="form-add-title"><span class="color_red">*</span>面板项标题：</td>
                <td><input name="title" id="title" class="form-add-input"/></td>
                <td style="width: 180px;"><span></span></td>
            </tr>
            <tr>
                <td class="form-add-title">面板标题说明：</td>
                <td><input type="text" name="titleComment" id="titleComment" class="form-add-input"/></td>
                <td style="width: 110px;"><span></span></td>
            </tr>
            <tr id="contentTypeTr" style="display: none">
                <td class="form-add-title"><span class="color_red">*</span>面板项类型：</td>
                <td><input type="text" name="contentType" id="contentType" class="form-add-input"/></td>
                <td style="width: 110px;"><span></span></td>
            </tr>
            <tr id="actionTypeTr">
                <td class="form-add-title"><span class="color_red">*</span>动作类型：</td>
                <td><input type="text" name="actionType" id="actionType" class="form-add-input"/></td>
            </tr>
            <tr>
                <td class="form-add-title">动作地址：</td>
                <td><input type="text" name="actionUrl" id="actionUrl" class="form-add-input"/></td>
                <td style="width: 110px;"><span></span>

                    <div id="showResultActionUrl2" class="color_red" style="display: inline;"/>
                </td>
            </tr>
            <tr style="width: 50px;">
                <td class="form-add-title">动作参数：</td>
                <td style="width: 26px;"><textarea id="params" name="params" cols="31" rows="3"
                                                   onchange="checkParamsRegValid('#params','#showResultParams')"
                                                   style="border:1px solid #86A3C4;"></textarea></td>
                <td style="text-align: left;"><span class="color_error_red" id="paramsTitle">格式:[key:value;]</span>

                    <div id="showResultParams" class="color_red" style="display: inline;"/>
                </td>
            </tr>
            <tr>
                <td class="form-add-title"><span class="color_red">*</span>图片地址：</td>
                <td><input type="text" name="imageUrl" id="imageUrl" class="form-add-input"
                           onchange="checkImageFormatIsValid('#imageUrl','#showResultImageDistr2')"/></td>
                <td style="width: 110px;"><span></span>
                    <input type="file" name="fileField6" class="file1" id="fileField6" size="28" style="width: 63px;"
                           onchange="document.getElementById('imageUrl').value=httpUrl.value+this.value"/>

                    <div id="showResultImageDistr2" class="color_red" style="display: inline;"/>
                </td>
            </tr>
            <tr id="videoUrlTr">
                <td class="form-add-title"><span class="color_red">*</span>视频地址：</td>
                <td><input type="text" name="videoUrl" id="videoUrl" class="form-add-input"
                           onchange="checkAddressFormatIsValid('#videoUrl','#showResultVideoUrl2')"/></td>
                <td><span></span>
                    <input type="file" name="fileField7" class="file1" id="fileField7" size="28" style="width: 63px;"
                           onchange="document.getElementById('videoUrl').value=ackVideoUrl.value+this.value"/>

                    <div id="showResultVideoUrl2" class="color_red" style="display: inline;"/>
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
            </tr>

            <tr id="refItemIdTr">
                <td class="form-add-title" id="refItemIdTitle">关联面板项：</td>
                <td><input type="text" name="refItemId" id="refItemId" class="form-add-input"/></td>
            </tr>
            <tr id="panelItemParentIdTr">
                <td class="form-add-title" id="panelItemParentIdTitle">父面板项：</td>
                <td><input type="text" name="panelItemParentId" id="panelItemParentId" class="form-add-input"/></td>
            </tr>
            <tr>
                <td class="form-add-title"><span class="color_red">*</span>是否自动打开：</td>
                <td><input type="text" name="autoRun" id="autoRun"
                           class="form-add-input"/></td>
            </tr>
            <tr>
                <td class="form-add-title"><span class="color_red">*</span>焦点是否进入：</td>
                <td><input type="text" name="focusRun" id="focusRun"
                           class="form-add-input"/></td>
            </tr>
            <tr>
                <td class="form-add-title"><span class="color_red">*</span>是否显示标题：</td>
                <td><input type="text" name="showTitle" id="showTitle"
                           class="form-add-input"/></td>
            </tr>
            <tr>
                <td class="form-add-title"><span class="color_red">*</span>动画：</td>
                <td><input type="text" name="animationRun" id="animationRun"
                           class="form-add-input"/></td>
            </tr>
            <tr>
                <td class="form-add-title">是否是默认焦点：</td>
                <td><input type="text" name="defaultfocus" id="defaultfocus" class="form-add-input"/></td>
            </tr>
        </table>
    </form>
</div>
<div id="panel_layout" style="display: none"></div>

<div id="panel-distribute-form" style="display: none;">
    <form id="panelDistributeForm">
        <table>
            <tr>
                <td style="width: 100px; text-align: right;"><span class="color_red">*</span>区域：</td>
                <td><input name="areaId" id="areaId"/></td>
                <td style="width: 200px;"><span></span></td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>