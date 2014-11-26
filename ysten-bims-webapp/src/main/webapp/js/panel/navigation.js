/**
 * Created by frank on 14-11-19.
 */
$(document).ready(function () {
    $('#center-tab').omTabs({height: "33", border: false});
    var renderActionTypeFunc = function (value) {
        if (value.toUpperCase() == '1') {
            return "播放视频";
        }
        if (value.toUpperCase() == '2') {
            return "跳转其他Launcher页面";
        }
        if (value.toUpperCase() == '3') {
            return "打开指定网页";
        }
        if (value.toUpperCase() == '4') {
            return "跳转其他应用";
        }
        if (value.toUpperCase() == '5') {
            return "widget动作";
        }
        return "";
    };

    var topNavTypeArr = [
        //image/NAV_CLOCK_WEATHER/NAV_USER_SUBSCRIPTION/NAV_USER_LOGIN/NAV_USER_MESSAGE/NAV_SHOW_WIFI
        {text: '图片类型', value: 'IMAGE'},
        {text: '时钟和天气', value: 'NAV_CLOCK_WEATHER'},
        {text: '牌照方和用户已订购套餐', value: 'NAV_USER_SUBSCRIPTION'},
        {text: '用户登录状态', value: 'NAV_USER_LOGIN'},
        {text: '用户的信息数', value: 'NAV_USER_MESSAGE'},
        {text: '当前WIFI连接状态', value: 'NAV_SHOW_WIFI'}
    ];

    var alignArr = [
        //left、center或right
        {text: '左对齐', value: 'left'},
        {text: '居中对齐', value: 'center'},
        {text: '右对齐', value: 'right'}
    ];

    var navActionTypeArr = [
        {text: '播放视频', value: '1'},
        {text: '跳转其他Launcher页面', value: '2'},
        {text: '打开指定网页', value: '3'},
        {text: '跳转其他应用', value: '4'},
        {text: 'widget动作', value: '5'}
    ];

    var navTypeArr = [
        {text: '上部导航', value: '1'},
        {text: '下部导航', value: '2'}
    ];

    var canFocusArr = [
        {text: "是", value: "true"},
        {text: "否", value: "false"}
    ];

    var showTitleArr = [
        {text: '显示', value: '1'},
        {text: '不显示', value: '0'}
    ];

    var renderTypeFunc = function (value) {
        if (value == 1) {
            return "上部导航";
        }
        if (value == 2) {
            return "下部导航";
        }
        return "";
    };
    var renderShowTitleFunc = function (value) {
        if (value == 1) {
            return "显示";
        }
        if (value == 0) {
            return "不显示";
        }
        return "";
    };

    $('#queryDatasource').omCombo({
        dataSource: BIMS_PANEL.config.datasource_query, width: 80, value: ''
    });
    $('#grid').omGrid({
        dataSource: 'nav_define_list.json',
        width: '100%',
        height: window.innerHeight - 85,
        singleSelect: true,
        limit: limit,
        colModel: [
            {header: '<b>ID</b>', name: 'id', align: 'center', width: 40},
            {header: '<b>导航名称</b>', name: 'navName', align: 'center', width: 100},
            {header: '<b>导航标题</b>', name: 'title', align: 'center', width: 100},
            {header: '<b>导航类型</b>', name: 'navType', align: 'center', width: 50, renderer: renderTypeFunc},
            {header: '<b>分辨率</b>', name: 'resolution', align: 'center', width: 50},
            {header: '<b>动作类型</b>', name: 'actionType', align: 'center', width: 100, renderer: renderActionTypeFunc},
            {header: '<b>动作地址</b>', name: 'actionUrl', align: 'center', width: 200},
            {header: '<b>图片地址</b>', name: 'imageUrl', align: 'center', width: 200},
            {header: '<b>是否显示标题</b>', name: 'showTitle', align: 'center', width: 70, renderer: renderShowTitleFunc},
            {header: '<b>创建时间</b>', name: 'createTime', align: 'center', width: 120, renderer: YST_APP.showDate},
            {header: '<b>最后操作时间</b>', name: 'updateTime', align: 'center', width: 120, renderer: YST_APP.showDate}
        ]
    });

    $('#create').omButtonbar({btns: [
        {label: "新增", icons: {left: opPath + 'add.png'}}
    ]});
    $('#update').omButtonbar({btns: [
        {label: "修改", icons: {left: opPath + 'op-edit.png'}}
    ]});
    $('#query').omButtonbar({btns: [
        {label: "查询", icons: {left: opPath + 'search.png'}}
    ]});
    $('#delete').omButtonbar({btns: [
        {label: "删除", icons: {left: opPath + 'remove.png'}}
    ]});
    $('#navType1').omCombo({
        dataSource: navTypeArr, width: 80, value: '0'
    });
    $('#actionType1').omCombo({
        //1:播放视频 2:跳转其他Launcher页面 3:打开指定网页 4:跳转其他应用 5:widget动作
        dataSource: navActionTypeArr, width: 140, value: ''
    });

    $('#query').bind('click', function (e) {
        var Id = $.trim($('#queryId').val());
        if (Id != '' && !YST_APP.isInteger(Id)) {
            YST_APP.alertMsg("ID必须为正整数类型");
            return false;
        }
        var title = $.trim($('#queryPanelTitle').val());
        var name = $.trim($('#queryPanelName').val());
        var navType = $('#navType1').omCombo('value');
        var actionType = $('#actionType1').omCombo('value');
        $('#grid').omGrid("setData", 'nav_define_list.json?title=' + encodeURIComponent(title) + '&navType=' + encodeURIComponent(navType) + '&actionType=' + encodeURIComponent(actionType) +
            '&name=' + name + "&Id=" + Id);
    });

    $('#delete').bind('click', function (e) {
        var selections = $('#grid').omGrid('getSelections', true);
        if (selections.length == 0) {
            YST_APP.alertMsg("删除操作至少选择一行记录！");
            return false;
        }
        for (var i = 0; i < selections.length; i++) {
            if (selections[i].epgNavId) {
                YST_APP.alertMsg("不可以删除外部系统数据！");
                return;
            }
        }
        $.omMessageBox.confirm({
            title: '确认删除',
            content: '批量删除后数据将无法恢复，你确定要执行该操作吗？',
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
                    $.post('nav_define_delete.json', {ids: toDeleteRecordID.toString()}, function (result) {
                        $('#grid').omGrid('reload');
                        if (result.indexOf('成功') > 0) {
                            YST_APP.show_message(result, "success");
                        } else {
                            YST_APP.show_message(result, "error");
                        }
                        $('#dialog-form').omDialog('close');
                    });
                }
            }
        });
    });
    var dialog = $("#dialog-form").omDialog({
        width: 650,
        autoOpen: false,
        modal: true,
        resizable: false,
        draggable: false,
        buttons: {
            "提交": function () {
                var navType = $("#navType").omCombo('value');
                var showTile = $("#showTitle").omCombo('value');
                var imageUrl = $("#imageUrl").val();
                var sr = $("#showResult").html();
                var info = new String(imageUrl.substring(imageUrl.lastIndexOf(".") + 1, imageUrl.length));//图片扩展名
                if (navType == 2 && showTile == 0) {
                    //alert("下部导航必须显示标题！");
                    $("#showResult2").text("下部导航必须显示标题！");
                    return false;
                }
                //江苏移动 && !$("#showResultFocusImg").html() && !$("#showResultCurrentPageImg").html()
                if (!$("#showResult1").html() && !$("#showResultParams").html() && !$("#showResultFocusImg").html() && !$("#showResultCurrentPageImg").html() && !$("#showResultImageDistr").html() && !$("#showResultActionUrl").html() && ( sr == '' || sr == '可用!')) {
                    submitDialog();
                    if (navType == 1 || (navType == 2 && showTile == 1)) {
                        $("#showResult2").text("");
                    }

                } else {
                    return false;
                }
            },
            "取消": function () {
                $("#showResult").text("");
                $("#showResult1").text("");
                $("#showResult2").text("");
                $("#dialog-form").omDialog("close");
            }
        }, onClose: function () {
            $("#showResult").text("");
            $("#showResult1").text("");
            $("#showResult2").text("");
            validator.resetForm();
        }
    });

    var showDialog = function (title, rowData) {
        rowData = rowData || {};
        dialog.omDialog("option", "title", title);
        dialog.omDialog("open");
    };

    var submitDialog = function () {
        var submitData;
        if (validator.form()) {
            if (isAdd) {
                submitData = {
                    title: $("#title").val(),
                    navName: $("#navName").val(),
                    titleComment: $("#titleComment").val(),
                    showTitle: $("#showTitle").omCombo('value'),
                    navType: $("#navType").omCombo('value'),
                    actionType: $("#actionType").omCombo('value'),
                    actionUrl: $("#actionUrl").val(),
                    imageUrl: $("#imageUrl").val(),
                    imageDisturl: $("#imageDisturl").val(),
                    //江苏移动
                    focusImg: $("#focusImg").val(),
                    currentPageImg: $("#currentPageImg").val(),
                    topNavType: $("#topNavType").val(),
                    align: $("#align").val(),
                    canfocus: $("#canfocus").val(),
                    params: $("#params").val(),
                    resolution: $("#resolution").val()
//                    sortNum: $("#sortNum").val()
                };

                $.ajaxFileUpload({
                    url: 'nav_define_add.shtml',
                    secureuri: false,
                    fileElementId: ['imageFile', 'fileField2', 'focusImgFile', 'currentImageFile'],
                    data: submitData,
                    dataType: 'JSON',
                    success: function (result) {
                        $('#grid').omGrid('reload');
                        if ("success" == result) {
                            YST_APP.show_message("新增导航信息成功！", "success");
//                        $.omMessageTip.show({title: tip, content: "新增导航信息成功！", type: "success", timeout: time});
                        } else {
                            YST_APP.show_message("新增导航信息失败！", "error");
                        }
                        $("#dialog-form").omDialog("close");
                        $("#showResult").text("");
                        $("#showResult1").text("");
                        $("#showResult2").text("");
                    }
                });


            } else {
                submitData = {
                    id: $("#id").val(),
                    title: $("#title").val(),
                    navName: $("#navName").val(),
                    titleComment: $("#titleComment").val(),
                    showTitle: $("#showTitle").omCombo('value'),
                    navType: $("#navType").omCombo('value'),
                    actionType: $("#actionType").omCombo('value'),
                    actionUrl: $("#actionUrl").val(),
                    imageUrl: $("#imageUrl").val(),
                    imageDisturl: $("#imageDisturl").val(),
                    //江苏移动
                    focusImg: $("#focusImg").val(),
                    currentPageImg: $("#currentPageImg").val(),
                    topNavType: $("#topNavType").val(),
                    align: $("#align").val(),
                    canfocus: $("#canfocus").val(),
                    params: $("#params").val(),
                    resolution: $("#resolution").val()
//                    sortNum: $("#sortNum").val()
                    //epgNavId: $("#epgNavId").val()
                };
                $.ajaxFileUpload({
                    url: 'nav_define_update.shtml',
                    secureuri: false,
                    fileElementId: ['imageFile', 'fileField2', 'focusImgFile', 'currentImageFile'],
                    data: submitData,
                    dataType: 'JSON',
                    success: function (result) {
                        $('#grid').omGrid('reload');
                        if ("success" == result) {
                            YST_APP.show_message("修改导航成功！", "success");
                        } else {
                            YST_APP.show_message("修改导航失败！", "error");
                        }
                        $("#dialog-form").omDialog("close");
                        $("#showResult").text("");
                        $("#showResult1").text("");
                        $("#showResult2").text("");
                    }
                });
            }
        }
    };

    var navTypeChange = function (target, newValue) {
        if (newValue == 2) {
            $("#topNavTypeTr").hide();
        } else {
            $("#topNavTypeTr").show();
        }
    };

    var isAdd = true;
    $('#create').bind('click', function () {
        $("#showResult").html("");
        $("#showResult1").html("");
        $("#showResultActionUrl").html("");
        $("#showResultImageDistr").html("");
        $("#showResultFocusImg").html("");
        $("#showResultCurrentPageImg").html("");
        $("#showResultParams").html("");
        isAdd = true;
        $('#navType').omCombo({
            dataSource: navTypeArr, value: '1', disabled: false, onValueChange: navTypeChange
        });
        $('#actionType').omCombo({
            dataSource: navActionTypeArr, value: '1'
        });
        $("#showTitle").omCombo({
            dataSource: showTitleArr, value: '1', disabled: false
        });
        //江苏移动
        $("#topNavType").omCombo({
            dataSource: topNavTypeArr, value: 'IMAGE', disabled: false
        });
        $("#align").omCombo({
            dataSource: alignArr, value: 'right', disabled: false
        });
        $("#canfocus").omCombo({
            dataSource: canFocusArr, value: 'true', disabled: false
        });
        $("#resolution").omCombo({dataSource: YST_APP.RESOLUTION, value: '720p', disabled: false});
        showDialog('新增导航信息');//显示dialog
    });
    $('#update').bind('click', function() {
        $("#showResult").html("");
        $("#showResult1").html("");
        $("#showResultActionUrl").html("");
        $("#showResultImageDistr").html("");
        $("#showResultFocusImg").html("");
        $("#showResultCurrentPageImg").html("");
        $("#showResultParams").html("");
        var selections = $('#grid').omGrid('getSelections', true);
        if (selections.length == 0 || selections.length > 1) {
            YST_APP.alertMsg("修改操作至少且只能选择一行记录！");
            return false;
        }
        isAdd = false;
        $.ajax({
            type: "post",
            url: "nav_define_to_update.json?navId=" + selections[0].id,
            dataType: "json",
            success: function (msg) {
                $("#id").val(msg['id']);
                $("#title").val(msg['title']);
                $("#navName").val(msg['navName']);
                $("#titleComment").val(msg['titleComment']);
                $('#navType').omCombo({
                    dataSource: navTypeArr, value: msg['navType'], disabled: true
                });
                $('#actionType').omCombo({
                    dataSource: navActionTypeArr, value: msg['actionType']
                });
                $("#showTitle").omCombo({
                    dataSource: showTitleArr, value: msg['showTitle']
                });
                //江苏移动
                $("#topNavType").omCombo({
                    dataSource: topNavTypeArr, value: msg['topNavType'], disabled: false, onValueChange: navTypeChange
                });
                $("#align").omCombo({
                    dataSource: alignArr, value: msg['align'], disabled: false
                });
                $("#canfocus").omCombo({
                    dataSource: canFocusArr, value: msg['canfocus'], disabled: false
                });
                $("#resolution").omCombo({dataSource: YST_APP.RESOLUTION, value: msg['resolution'], disabled: true});
                $("#focusImg").val(msg['focusImg']),
                    $("#currentPageImg").val(msg['currentPageImg']);
                $("#params").val(msg['params']);
                $("#actionUrl").val(msg['actionUrl']);
                $("#imageUrl").val(msg['imageUrl']);
                $("#imageDisturl").val(msg['imageDisturl']);
            }
        });


        showDialog('修改导航信息', selections[0]);
    });
    var validator = $('#myform').validate({
        rules: {
            navType: {required: true},
            titleComment: {maxlength: 1024},
            imageDisturl: {maxlength: 256},
            title: {required: true, maxlength: 256},
            navName: {required: true, maxlength: 256},
            // actionType: {required: true},
            imageUrl: {maxlength: 256},
//            sortNum: {required: true, digits: true},
            showTitle: {required: true, digits: true},
            resolution: {required: true},
            actionType: {required: true},
            params: {maxlength: 1024}

            //epgNavId: {digits: true}
        },
        messages: {
            navType: {required: "导航类型不能为空！"},
            titleComment: {maxlength: "标题说明最长1024位字符！"},
            imageDisturl: {maxlength: "图片分发地址最长256位字符！"},
            title: {required: "导航标题不能为空！", maxlength: "导航标题最长256位字符！"},
            navName: {required: "导航名称不能为空！", maxlength: "导航名称最长256位字符！"},
            // actionType: {required: "动作类型不能为空！"},
            imageUrl: {maxlength: "图片地址最长256位字符！"},
//            sortNum: {required: "排序号不能为空！", digits: "请输入数字类型！"},
            showTitle: {required: "是否显示标题不能为空！", digits: "请输入数字类型！"},
            resolution: {required: "分辨率不能为空！"},
            params: {maxlength: "动作参数最长1024位字符！"}
            //epgNavId: {digits: "请输入数字类型！"}
        },
        errorPlacement: YST_APP.Error.errorPlacement,
        showErrors: YST_APP.Error.showErrors
    });
});
function checkNameExists() {
    if ($.trim($("#navName").val()) != "") {
        var par = new Object();
        par['navName'] = $("#navName").val();
        par['id'] = $("#id").val();
        $.ajax({
            type: "post",
            url: "nav_name_exists.shtml?s=" + Math.random(),
            dataType: "html",
            data: par,
            success: function (msg) {
                $("#showResult").html(msg);
            }
        });
    } else if ($.trim($("#navName").val()) == "" && $("#showResult").html() != "") {
        $("#showResult").html("");
    }
}
function checkIsShow() {
    if ($("#navType").val() == "2") {
        $("#showTitle").omCombo({
            dataSource: [
                {text: '显示', value: '1'},
                {text: '不显示', value: '0'}
            ], value: '1', disabled: true
        });
    } else {
        $("#showTitle").attr('disabled', false);
    }
}
