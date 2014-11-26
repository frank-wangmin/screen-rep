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
    <script type="text/javascript">

        var panel_package_id,is_epg_flag = false;
        var show_online_status = function (val) {
            if (val == 99) {
                return "上线";
            } else if(val == -99) {
                return "下线";
            } else {
                return "初始";
            }
        };
        function showInitData(url) {
            $('#grid').omGrid({
                dataSource: url,
                width: '100%',
                height: rFrameHeight,
                singleSelect: true,
                limit: limit,
                colModel: [
					{header: '<b>面板标题</b>', name: 'panelTitle', align: 'center', width: 100},
                    {header: '<b>下部导航</b>', name: 'rootNavTitle', align: 'center', width: 100},
                    {header: '<b>在线状态</b>', name: 'onlineStatus', align: 'center', width: 60, renderer: show_online_status},
                    {header: '<b>创建时间</b>', name: 'createTime', align: 'center', width: 120,renderer:YST_APP.showDate},
                    {header: '<b>链接地址</b>', name: 'linkUrl', align: 'center', width: 120}
                ]
            });
        }

        $(function () {
        	$('#center-tab').omTabs({height: "33", border: false});
            $('#unbind_package').omButtonbar({btns: [
                {label: "解绑面板包", icons: {left: opPath + 'op-edit.png'}}
            ]});

            $("#unbind_package").click(function(){
                var selections = $('#grid').omGrid('getSelections', true);
                if (selections.length == 0) {
                    YST_APP.alertMsg("解绑操作至少选择一行记录！");
                    return;
                }
                if (selections[0].epgPanelId && is_epg_flag) {
                    YST_APP.alertMsg("不可以编辑外部系统数据！");
                    return;
                }
                $.omMessageBox.confirm({
                    title: '确认解绑',
                    content: '你确定要执行该操作吗？',
                    onClose: function (v) {
                        if (v == true) {
                            var toDeleteRecordID = "";
                            for (var i = 0; i < selections.length; i++) {
                                if (i != selections.length - 1) {
                                    toDeleteRecordID += selections[i].id + ",";
                                } else {
                                    toDeleteRecordID += selections[i].id;
                                }
                            }
                            $.post('unbind_panel_package.json', {panelIds: toDeleteRecordID.toString(),packageId:panel_package_id}, function (result) {
                                $('#grid').omGrid('reload');
                                if (result == 'success') {
                                    YST_APP.show_message("解绑面板包信息成功！","success");
                                } else {
                                    YST_APP.show_message("解绑面板包信息失败！","error");
                                }
                            });
                        }
                    }
                });
            });
        });

    </script>
</head>
<body>
<div id="center-tab">
    <ul>
        <li><a href="#tab1">关联面板信息列表:</a></li>
    </ul>
</div>
<table>
    <tr>
        <td style="height: 24px;" >&nbsp;&nbsp;</td>
    </tr>
</table>
<table>
    <tr align="left">
        <td>
            <div id="unbind_package"></div>
        </td>
    </tr>
</table>
<table id="grid"></table>
</body>
</html>