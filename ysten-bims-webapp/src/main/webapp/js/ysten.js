var tip = "温馨提示：";
var time = 5000;
//var rFrameHeight = window.innerHeight-28;
var rFrameHeight = window.innerHeight - 60;
var limit = 20;

var districtCode = "100000";

var srcElement = null;
var valueElement = null;

function showTree(item, valueId, combdtree) {
    srcElement = item;
    valueElement = document.getElementById(valueId);
    var x = getLeft(item);
    var y = getTop(item) + item.offsetHeight;
    var w = item.offsetWidth;
    //blockDTree(x,y,w);
    var show = document.getElementById(combdtree);
    show.style.position = "absolute";
    show.style.left = x;
    show.style.top = y;
    show.style.width = w + 20;
    show.style.display = "block";
}

function hiddenDTree(combdtree) {
    var item = document.getElementById(combdtree);
    if (item) {
        item.style.display = 'none';
    }
}

function setSrcValue(text, value, combdtree) {
    srcElement.value = text;
    valueElement.value = value;
    hiddenDTree(combdtree);
}

function getTop(e) {
    var offset = e.offsetTop;
    if (e.offsetParent != null) offset += getTop(e.offsetParent);
    return offset;
}


function getLeft(e) {
    var offset = e.offsetLeft;
    if (e.offsetParent != null) offset += getLeft(e.offsetParent);
    return offset;
}
function isChinese(s) {
    // 正则表达式对象
    var re = new RegExp("[\\u4e00-\\u9fa5]", "");
    // 验证是否刚好匹配
    return re.test(s);
}

var maxtime = "1";
function countDown() {
    if (maxtime < 30) {
        msg = "温馨提示：数据正在处理中，请耐心等待... " + maxtime + " 秒";
        $("#show").text(msg);
        ++maxtime;
    }
}

function showDiv() {
    $("#bg").fadeIn(1000);
    var $loader;
    var totalKb = 100;
    var kb = 0;
    $loader = $("#show").percentageLoader({
        width: 180,
        height: 180,
        progress: 0
    });
    var animateFunc = function () {
        kb += 1;
        if (kb > totalKb) {
            kb = totalKb;
        }
        $loader.setProgress(kb / totalKb);
        $loader.setValue(kb.toString() + '%');
        if (kb < totalKb) {
            setTimeout(animateFunc, 1000);
        }
    };
    setTimeout(animateFunc, 1000);
    $("#show").fadeIn();
}
function hideDiv() {
    $("#bg").fadeOut(1000);
    $("#show").fadeOut(1000);
}

function getCurrentDate() {
    var now = new Date();
    return now.getFullYear()
        + "-" + ((now.getMonth() + 1) < 10 ? "0" : "") + (now.getMonth() + 1)
        + "-" + (now.getDate() < 10 ? "0" : "") + now.getDate();
}

function getCurrentTime() {
    var now = new Date();
    return now.getFullYear() + "-"
        + ((now.getMonth() + 1) < 10 ? "0" : "")
        + (now.getMonth() + 1) + "-"
        + (now.getDate() < 10 ? "0" : "") + now.getDate()
        + " " + (now.getHours() < 10 ? "0" : "") + now.getHours() + ":"
        + (now.getMinutes() < 10 ? "0" : "") + now.getMinutes() + ":"
        + (now.getSeconds() < 10 ? "0" : "") + now.getSeconds();
}

var LOCK = {};

$(function () {
    $(document).ajaxSend(function (e, xhr, settings) {
        var url = settings.url;
        var index = url.indexOf("?");
        var key = url;
        if (index > 7) {
            key = url.substring(7, index);
        }
        if (url.indexOf("verify") == 0) {
            if (LOCK[key]) {
                $.omMessageBox.alert({
                    type: 'alert',
                    title: '温馨提示',
                    content: '请不要频繁提交数据！'
                });
                xhr.abort();
            } else {
                settings.url = url.substring(7);
                LOCK[key] = new Date().getTime();
            }
        }
    }).ajaxComplete(function (e, xhr, setting) {
            var url = setting.url;
            var index = url.indexOf("?");
            var key = url;
            if (index > 0) {
                key = url.substring(0, index);
            }
            LOCK[key] = undefined;
            LOCK["verify_" + key] = undefined;
        })
})

$.ajaxSetup({
    timeout: 60000
});

//added by frank
var YST_APP = (function () {
    var APP = {};
    APP.Error = {};
    APP.YES_NO = [
        {text: '是', value: '1'},
        {text: '否', value: '0'}
    ];

    APP.PanelFrameHeight = window.innerHeight - 88;
    APP.STATUS = [
        {
            text: "有效", value: "1"
        },
        {
            text: "无效", value: "0"
        }
    ];
    APP.ON_OFF = [
        {text: "请选择", value: ""},
        {text: "开机", value: "ON"},
        {text: "关机", value: "OFF"}
    ];
    //最大栏目页数
    APP.MAX_PAGE = [
        {text: "0", value: "0"},
        {text: "1", value: "1"},
        {text: "2", value: "2"},
        {text: "3", value: "3"},
        {text: "4", value: "4"},
        {text: "5", value: "5"},
        {text: "6", value: "6"},
        {text: "7", value: "7"},
        {text: "8", value: "8"},
        {text: "9", value: "9"},
        {text: "10", value: "10"},
        {text: "11", value: "11"},
        {text: "12", value: "12"},
        {text: "13", value: "13"},
        {text: "14", value: "14"},
        {text: "15", value: "15"}
    ];
    //分辨率
    APP.RESOLUTION = [
        {text: "720p", value: "720p"},
        {text: "1080p", value: "1080p"}
    ];

    APP.YES_NO_FUNC = function (value) {
        if (value == 1) {
            return "是";
        }
        if (value == 0) {
            return "否";
        }
        return "";
    };

    APP.Error.errorPlacement = function (error, element) {
        if (error.html()) {
            $(element).parents().map(function () {
                if (this.tagName.toLowerCase() == 'td') {
                    var attentionElement = $(this).next().children().eq(0);
                    attentionElement.html(error);
                }
            });
        }
    };

    APP.Error.showErrors = function (errorMap, errorList) {
        if (errorList && errorList.length > 0) {
            $.each(errorList, function (index, obj) {
                var msg = this.message;
                $(obj.element).parents().map(function () {
                    if (this.tagName.toLowerCase() == 'td') {
                        var attentionElement = $(this).next().children().eq(0);
                        attentionElement.show();
                        attentionElement.html(msg);
                    }
                });
            });
        } else {
            $(this.currentElements).parents().map(function () {
                if (this.tagName.toLowerCase() == 'td') {
                    $(this).next().children().eq(0).hide();
                }
            });
        }
        this.defaultShowErrors();
    };

    APP.alertMsg = function (msg) {
        $.omMessageBox.alert({
            type: 'alert',
            title: "温馨提示",
            content: msg
        });
    };

    APP.convertIds2Str = function (selections) {
        var toDeleteRecordID = "";
        if (!$.isArray(selections)) return toDeleteRecordID;
        $.each(selections, function (i, value) {
            if (i != selections.length - 1) {
                toDeleteRecordID += selections[i].id + ",";
            } else {
                toDeleteRecordID += selections[i].id;
            }
        });
        return toDeleteRecordID;
    };

    APP.show_message = function (msg, type) {
        $.omMessageTip.show({title: tip, content: msg, type: type, timeout: time});
    };

    APP.showDate = function (value) {
        return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
    };

    APP.checkFileText = function (fileName) {
        if (fileName) {
            var extension = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length);//文件扩展名
            if (extension != 'txt') {
                alert('导入的文件格式不支持,只可以导入文本文件！');
                return false;
            }
        }
        return true;
    };

    APP.isInteger = function (str) {
        var regu = /^[0-9]{1,}$/;
        return regu.test(str);
    }


    return APP;
}());

Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
}

//add by mwl
/*过滤字符串的前后空格*/
function filterStartEndSpaceTrim(str) {
    str = str.replace(/^(\s|\u00A0)+/, '');
    for (var i = str.length - 1; i >= 0; i--) {
        if (/\S/.test(str.charAt(i))) {
            str = str.substring(0, i + 1);
            break;
        }
    }
    return str;
}

/*
 替换空字符串为空值
 */
function replaceToNullValue(submitData) {
    var pattern = new RegExp("&[^=]*=&");
    while (pattern.test(submitData)) {
        submitData = submitData.replace(/&[^=]*=&/g, '&');
    }
    return submitData;
}

/**
 * 替换Json对象里的空字符串
 * @param submitData
 */
function replaceJsonToNullValue(submitData) {
    for (var key in submitData) {
        if ("" == submitData[key]) {
            delete submitData[key];
        }
    }
}

function checkImageFormatIsValid(componentId, showResultId) {
    if ($.trim($(componentId).val())) {
        var imageUrl = $.trim($(componentId).val());
        var imageFormat = imageUrl.substring(imageUrl.lastIndexOf("."), imageUrl.length).toLowerCase();
//        alert(imageFormat);
        if (imageFormat == '.bmp' || imageFormat == '.gif' || imageFormat == '.jpeg' || imageFormat == '.png' || imageFormat == '.jpg') {
            $(showResultId).html("");
        } else {
            $(showResultId).html('图片地址格式不正确，请确认');
        }
    } else {
        $(showResultId).html("");
    }
}

function checkAddressFormatIsValid(componentId, showResultId) {
    var addressUrl = $.trim($(componentId).val());
    if (addressUrl) {
        if (addressUrl.indexOf('http://') != 0) {
            $(showResultId).html('地址格式不正确，请确认');
        } else {
            if (addressUrl.length > "http://".length) {
                $(showResultId).html("");
            } else {
                $(showResultId).html('该地址是无效的，请确认');
            }
        }
    } else {

        $(showResultId).html("");
    }
}

//检查params 参数类型 是否符合：key:value;key:value;
function checkParamsRegValid(paramsId, showResultId) {
    var params = $(paramsId).val();
    var regexp = new RegExp("^([\\w\\.\\b]+\\:[\\w\\.\\b]*\\;)+$");
    if (!regexp.test(params)) {
        $(showResultId).html('动作参数格式不正确，请确认![key:value;key:value;key:value;]');
    } else {
        $(showResultId).html('');
    }
}
