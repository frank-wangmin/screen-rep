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
<script type="text/javascript" src="${cxp}/js/panel/panel_package_list.js"></script>
</head>
<body>
<div id="center-tab">
    <ul>
        <li><a href="#tab1">面板包信息列表:</a></li>
    </ul>
</div>
<div style="overflow:auto;overflow-y:hidden">
    <table>
        <tr align="left">
            <c:if test="${sessionScope.add_panel_package != null }">
                <td>
                    <div id="save"></div>
                </td>
            </c:if>
            <c:if test="${sessionScope.update_panel_package != null }">
                <td>
                    <div id="update"></div>
                </td>
            </c:if>
            <%--<c:if test="${sessionScope.delete_panel_package != null }">
                <td>
                    <div id="delete"></div>
                </td>
            </c:if>--%>
            <c:if test="${sessionScope.bind_panel_package_business != null }">
                <td>
                    <div id="bind"></div>
                </td>
            </c:if>

            <c:if test="${sessionScope.unbind_panel_package_business != null }">
                <td>
                    <div id="unbind"></div>
                </td>
            </c:if>
            <c:if test="${sessionScope.bind_panel_package_business_user != null }">
                <td>
                    <div id="bindUser"></div>
                </td>
            </c:if>

            <c:if test="${sessionScope.unbind_panel_package_business_user != null }">
                <td>
                    <div id="unbindUser"></div>
                </td>
            </c:if>

            <c:if test="${sessionScope.panel_package_preview != null }">
                <td>
                    <div id="package_preview"></div>
                </td>
            </c:if>
            <c:if test="${sessionScope.config_panel_sort != null }">
                <td>
                    <div id="config_panel_sort"></div>
                </td>
            </c:if>

            <td>
                <div id="createZips"></div>
            </td>

            <c:if test="${sessionScope.panel_package_device_list != null }">
                <td>
                    <div id="device_list_of_group"/>
                </td>
            </c:if>
            <c:if test="${sessionScope.panel_package_user_list != null }">
                <td>
                    <div id="customer_list_of_group"/>
                </td>
            </c:if>
        </tr>
    </table>
    <table>
        <tr>
            <td>ID:</td>
            <td><input id="queryId" class="query-title-input"/></td>
            <td>&nbsp;面板包名称：</td>
            <td><input id="queryPanelName" class="query-title-input" style="width:110px;"/></td>
            <td>
                <div id="query"></div>
            </td>
        </tr>
    </table>
</div>
<table id="grid"></table>

<div id="epg_panel-review">
    <table id="epg_panel-grid"></table>
</div>

<div id="dialog-form" style="display: none;">
    <form id="myform">
        <input type="hidden" value="" name="id" id="id"/>
        <input type="hidden" value="${picUrl}" name="httpUrl" id="httpUrl"/>
        <table>
            <tr>
                <td class="form-add-title"><span class="color_red">*</span>面板包名称：</td>
                <td><input type="text" name="packageName" id="packageName" class="form-add-input"
                           maxlength="17"/></td>
                <td style="width: 210px;"><span></span></td>
            </tr>
            <tr>
                <td class="form-add-title">面板包描述：</td>
                <td><input name="packageDesc" id="packageDesc" class="form-add-input"/></td>
            </tr>
            <tr>
                <td class="form-add-title">是否为默认：</td>
                <td><input type="text" name="isDefault" id="isDefault"
                           class="form-add-input"/></td>
            </tr>
            <tr>
                <td class="form-add-title"><span class="color_red">*</span>最大栏目页数：</td>
                <td><input name="maxPageNumber" id="maxPageNumber" class="form-add-input"/></td>
                <td style="width: 230px;"><span></span></td>
            </tr>

            <tr>
                <td class="form-add-title"><span class="color_red">*</span>720p默认背景：</td>
                <td><input type="text" name="defaultBackground720p" id="defaultBackground720p"
                           class="form-add-input"
                           onchange="checkImageFormatIsValid('#defaultBackground720p','#showResultdefaultBackground720p')"/>
                </td>
                <td style="width: 230px;"><span></span>
                    <input type="file" name="backgroundImg720p" class="file1" id="backgroundImg720p" size="28" style="width: 63px;"
                           onchange="document.getElementById('defaultBackground720p').value=this.value"/>

                    <div id="showResultdefaultBackground720p" class="color_red" style="display: inline;"/>
                </td>
            </tr>
            <tr>
                <td class="form-add-title"><span class="color_red">*</span>1080p默认背景：</td>
                <td><input type="text" name="defaultBackground1080p" id="defaultBackground1080p"
                           class="form-add-input"
                           onchange="checkImageFormatIsValid('#defaultBackground1080p','#showResultdefaultBackground1080p')"/>
                </td>
                <td style="width: 230px;"><span></span>
                    <input type="file" name="backgroundImg1080p" class="file2" id="backgroundImg1080p" size="28" style="width: 63px;"
                           onchange="document.getElementById('defaultBackground1080p').value=this.value"/>

                    <div id="showResultdefaultBackground1080p" class="color_red" style="display: inline;"/>
                </td>
            </tr>

            <tr>
                <td class="form-add-title">通用的上部导航：</td>
                <td><input name="commonTopNav" id="commonTopNav" class="form-add-input"/></td>
            </tr>

        </table>
    </form>
</div>
<div id="dialog-form1">
    <form id="myform1">
        <table>
            <tr>
                <td align="right">设备编号：</td>
                <td><input type='text' name='textfield' id='textfield'
                           class='file_txt'/>
                    <input type='button' class='file_btn' value='浏览...'/>
                    <input type="file" name="deviceCodes3" class="file_import" id="deviceCodes3"
                           size="40"
                           onchange="document.getElementById('textfield').value=this.value"/>
                </td>
            </tr>
            <tr>
                <td style="width: 100px; text-align: right;">区域：</td>
                <td><input name="districtCode" id="districtCode" style="width:205px;"/></td>
                <td><span></span></td>
            </tr>
            <tr>
                <td align="right">设备分组：</td>
                <td><input id="deviceGroupIds3" name="deviceGroupIds3" style="width:180px;"/></td>
                <td><span></span></td>
            </tr>

        </table>
    </form>
</div>

<div id="dialog-user-group" style="display:none">
    <form id="user-group-form" method="post">
        <table>
            <tr>
                <td align="right">用户编号：</td>
                <td><input type='text' name='textfield1' id='textfield1'
                           class='file_txt'/>
                    <input type='button' class='file_btn' value='浏览...'/>
                    <input type="file" name="userIds3" class="file_import" id="userIds3"
                           size="40"
                           onchange="document.getElementById('textfield1').value=this.value"/>
                </td>
            </tr>
            <tr>
                <td style="width: 100px; text-align: right;">区域：</td>
                <td><input name="areaId" id="areaId" style="width:205px;"/></td>
                <td><span></span></td>
            </tr>
            <tr>
                <td align="right">用户分组：</td>
                <td><input id="userGroupIds3" name="deviceGroupIds3" style="width:205px;"/></td>
                <td><span></span></td>
            </tr>
        </table>
    </form>
</div>

<div id="panel-sort">
    <div id="config-package-tab">
        <ul>
            <li>
                <a href="#config_720p">720p</a>
            </li>
            <li>
                <a href="#config_1080p">1080p</a>
            </li>
        </ul>
        <div id="config_720p">
            <table>
                <tr>
                    <td>
                        <div id="add720p"></div>
                    </td>
                    <td>
                        <div id="del720p"></div>
                    </td>
                    <td>
                        <div id="saveChanges720p"></div>
                    </td>
                </tr>
            </table>
            <table id="panel-sort-grid720p"></table>
        </div>
        <div id="config_1080p">
            <table>
                <tr>
                    <td>
                        <div id="add1080p"></div>
                    </td>
                    <td>
                        <div id="del1080p"></div>
                    </td>
                    <td>
                        <div id="saveChanges1080p"></div>
                    </td>
                </tr>
            </table>
            <table id="panel-sort-grid1080p"></table>
        </div>
    </div>
</div>

<div id="panel_layout">
    <div id="package-tab">
        <ul>
            <li>
                <a href="#preview_720p">720p</a>
            </li>
            <li>
                <a href="#preview_1080p">1080p</a>
            </li>
        </ul>
        <div id="preview_720p">
            <div class="panel_head_nav">
                <div id="panel_head_nva_content720p">
                </div>
            </div>
            <div class="preview_panel_cotent">
                <div id="panel_content720p" class="panel_content">
                </div>
                <div class="carousel-control-left"><img src="${cxp}/images/lbpre.gif" alt=""/></div>
                <div class="carousel-control-right"><img src="${cxp}/images/lbnext.gif" alt=""/></div>
            </div>
            <div class="panel_root_nav">
                <div id="panel_root_nav_content720p" class="panel_root_nav_content">
                </div>
            </div>
        </div>
        <div id="preview_1080p">
            <div class="panel_head_nav">
                <div id="panel_head_nva_content1080p">
                </div>
            </div>
            <div class="preview_panel_cotent">
                <div id="panel_content1080p" class="panel_content">
                </div>
                <div class="carousel-control-left"><img src="${cxp}/images/lbpre.gif" alt=""/></div>
                <div class="carousel-control-right"><img src="${cxp}/images/lbnext.gif" alt=""/></div>
            </div>
            <div class="panel_root_nav">
                <div id="panel_root_nav_content1080p" class="panel_root_nav_content">
                </div>
            </div>
        </div>
    </div>
</div>


<div id="dialog-form3" style="display: none;">
    <form id="myform3">
        <table>
            <tr>
                <td style="width: 100px; text-align: right;">绑定结果：</td>
                <td>
                    <div id="message" style="overflow-y: auto;"></div>
                </td>
            </tr>
        </table>
    </form>
</div>

<div id="dialog-form-device-list" style="display: none;">
    <form id="device-form">
        <table>
            <tr>
                <td>设备编号：</td>
                <td><input name="ystenIds" id="ystenIds" class="query-title-input" style="width:110px;"/></td>
                <td>设备序列号：</td>
                <td><input name="snos" id="snos" class="query-title-input" style="width:110px;"/></td>
                <td>MAC地址：</td>
                <td><input name="macs" id="macs" class="query-title-input" style="width:110px;"/></td>
                <td>
                    <div id="deviceQuery"></div>
                </td>
                <td>
                    <div id="delDevice"></div>
                </td>

            </tr>
        </table>
        <table id="gridDeviceList"></table>
    </form>
</div>

<div id="dialog-form-customer-list" style="display: none;">
    <form id="customer-form">
        <table>
            <tr>
                <td>用户编号：</td>
                <td><input name="code1" id="code1" class="query-title-input" style="width:110px;"/></td>
                <td>用户电话：</td>
                <td><input name="phone1" id="phone1" class="query-title-input" style="width:110px;"/></td>
                <td>用户外部编号：</td>
                <td><input name="userId1" id="userId1" class="query-title-input" style="width:110px;"/></td>
                <td>所属地市：</td>
                <td><input id="region1" name="region1"/></td>
                <td>
                    <div id="queryUser"/>
                </td>
                <td>
                    <div id="delUser"/>
                </td>
            </tr>
        </table>
        <table id="gridUserList"></table>
    </form>
</div>
<div id="del-dialog-form" style="display: none;">
    <form id="delDeviceMyform">
        <table>
            <tr>
                <td>移除方式：</td>
                <td>
                    <input name="deleteType" id="deleteType"/>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>