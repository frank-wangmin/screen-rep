package com.ysten.local.bss.panel.domain;

import com.ysten.local.bss.util.bean.Required;

/**
 * @author cwang
 * @version 2014-5-21 下午3:45:58
 */
public class Navigation extends BaseDomain {

    private static final long serialVersionUID = 8615802830547498493L;
    // 适配epg用
    private Long navId;
    // 导航类型
    @Required
    private Integer navType;
    // 标题
    private String title;
    //名称
    private String navName;
    // 面板标题说明
    private String titleComment;
    // ACTION_TYPE 动作类型
    @Required
    private String actionType;
    // ACTION_URL 动作地址
    private String actionUrl;
    // IMAGE_URL 图片地址
    private String imageUrl;
    // IMAGE_DISTURL 图片分发地址
    private String imageDisturl;
    // SHOW_TITLE 是否显示标题
    @Required
    private Integer showTitle;
    // STATUS 状态
    @Required
    private Integer status;
    // SORT_NUM 排序号
    private Integer sortNum;
    // epg_nav_id
    private Long epgNavId;

    //数据的有效性（依据播控数据的删除判断）
    private Integer onlineStatus;

    /*江苏移动*/

    //版本
    private Long version;

    //导航元素高亮显示图片
    private String focusImg;

    //焦点还停留在当前页面导航栏显示图片
    private String currentPageImg;

    //快捷入口控件类型
    private String topNavType;

    //快捷入口对齐方式 值为left、center或right，默认为right，表示右对齐
    private String align;

    //是否允许落焦点 值为true或false，默认为true。
    private String canfocus;

    //（可选） 其他动作参数
    private String params;
    //分辨率
    private String resolution;

    /*江苏移动*/

    public Long getVersion() {
        return version;
    }

    public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public void setVersion(Long version) {
        this.version = version;
    }

    public String getFocusImg() {
        return focusImg;
    }

    public void setFocusImg(String focusImg) {
        this.focusImg = focusImg;
    }

    public String getCurrentPageImg() {
        return currentPageImg;
    }

    public void setCurrentPageImg(String currentPageImg) {
        this.currentPageImg = currentPageImg;
    }

    public String getTopNavType() {
        return topNavType;
    }

    public void setTopNavType(String topNavType) {
        this.topNavType = topNavType;
    }

    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public String getCanfocus() {
        return canfocus;
    }

    public void setCanfocus(String canfocus) {
        this.canfocus = canfocus;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public Integer getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(Integer onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public Long getNavId() {
        return navId;
    }

    public void setNavId(Long navId) {
        this.navId = navId;
    }

    public Integer getNavType() {
        return navType;
    }

    public void setNavType(Integer navType) {
        this.navType = navType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleComment() {
        return titleComment;
    }

    public void setTitleComment(String titleComment) {
        this.titleComment = titleComment;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageDisturl() {
        return imageDisturl;
    }

    public void setImageDisturl(String imageDisturl) {
        this.imageDisturl = imageDisturl;
    }

    public Integer getShowTitle() {
        return showTitle;
    }

    public void setShowTitle(Integer showTitle) {
        this.showTitle = showTitle;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public Long getEpgNavId() {
        return epgNavId;
    }

    public void setEpgNavId(Long epgNavId) {
        this.epgNavId = epgNavId;
    }

    public String getNavName() {
        return navName;
    }

    public void setNavName(String navName) {
        this.navName = navName;
    }
}
