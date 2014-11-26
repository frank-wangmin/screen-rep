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
    <script type="text/javascript">
    $(function () {
        $('#center-tab').omTabs({height: "33", border: false});
    });
        var status_func = function (value) {
            if (value == 1) {
                return "有效";
            }
            if (value == 0) {
                return "无效";
            }
            return "";
        }

        function showInitData(url) {
            $('#grid').omGrid({
                dataSource: url,
                width: '100%',
                height: rFrameHeight,
                singleSelect: true,
                limit: limit,
                colModel: [
                    {header: '<b>面板项标题</b>', name: 'title', align: 'center', width: 120},
                    {header: '<b>动作类型</b>', name: 'actionType', align: 'center', width: 120, renderer: BIMS_PANEL.renderActionTypeFunc},
                    {header: '<b>是否自动打开</b>', name: 'autoRun', align: 'center', width: 80, renderer: YST_APP.YES_NO_FUNC},
                    {header: '<b>焦点是否进入</b>', name: 'focusRun', align: 'center', width: 80, renderer: YST_APP.YES_NO_FUNC},
                    {header: '<b>是否显示标题</b>', name: 'showTitle', align: 'center', width: 80, renderer: YST_APP.YES_NO_FUNC},
                    {header: '<b>内容</b>', name: 'content', align: 'center', width: 200}
                ],
                rowDetailsProvider: function (rowData, rowIndex) {
                    return '第' + (rowIndex + 1) + '行'
                            + '</br>主键ID=' + rowData.id
                            + '</br>面板项标题=' + rowData.title
                            + '</br>动作类型=' + BIMS_PANEL.renderActionTypeFunc(rowData.actionType)
                            + '</br>是否自动打开=' + YST_APP.YES_NO_FUNC(rowData.autoRun)
                            + '</br>焦点是否进入=' + YST_APP.YES_NO_FUNC(rowData.focusRun)
                            + '</br>是否显示标题=' + YST_APP.YES_NO_FUNC(rowData.showTitle)
                            + '</br>内容=' + rowData.content;
                }
            });
        }
    </script>
</head>
<body>
<div id="center-tab">
    <ul>
        <li><a href="#tab1">关联面板项信息列表:</a></li>
    </ul>

</div>
<div style="height: 56px"></div>
<table id="grid"></table>
</body>
</html>