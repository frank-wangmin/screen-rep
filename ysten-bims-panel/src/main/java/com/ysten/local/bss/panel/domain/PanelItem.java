package com.ysten.local.bss.panel.domain;

import com.ysten.local.bss.util.bean.Required;
import com.ysten.utils.bean.IEnumDisplay;

import java.util.List;

/**
 * Created by frank on 14-5-9.
 */
public class PanelItem extends BaseDomain {

    private static final long serialVersionUID = -8283188677505832191L;

    private String name;

    private Long panelItemId;

    private String title;

    private String areaName;

    private String districtCode;

    //应用总入口
    private String appEnterUrl;

    //面板项内容类型（自定义，栏目，节目）
    private String itemContentType;

    // 面板标题说明
    private String titleComment;

    // 动作类型
    private Integer actionType;

    // 动作地址
    private String actionUrl;

    // 图片地址
    private String imageUrl;

    // 图片分发地址
    private String imageDistUrl;

    // 视频地址
    private String videoUrl;

    // 内容ID(节目集ID)
    private Long contentId;

    private Long categoryId;

    public String getAppEnterUrl() {
        return appEnterUrl;
    }

    public void setAppEnterUrl(String appEnterUrl) {
        this.appEnterUrl = appEnterUrl;
    }

    public String getItemContentType() {
        return itemContentType;
    }

    public void setItemContentType(String itemContentType) {
        this.itemContentType = itemContentType;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    // 内容
    private String content;

    // 内容类型
    private String contentType;

    // 关联item_id
    private Long refItemId;

    // 父面板项ID
    private Long panelItemParentId;

    // 是否自动打开
    private Integer autoRun;

    // 焦点是否进入
    private Integer focusRun;

    // 是否显示标题
    private Integer showTitle;

    // 动画
    private Integer animationRun;

    // 状态
    private Integer status;

    // 面板项类型
    private Long type;

    private String installUrl;

    private Integer hasSubItem;

    // epg的面板项ID
    private Long epgPanelitemId;

    // epg的内容ID
    private Long epgContentId;

    // epg的关联item_id
    private Long epgRefItemId;

    // epg的父面板项ID
    private Long epgPanelitemParentid;

    //epg的hasSubitem
    private Integer hasSubitem;

    //兼容epg
    private Long userId;

    //兼容epg
    private Long parentId;

    public Integer getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(Integer onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    //数据的有效性（依据播控数据的删除判断）
    private Integer onlineStatus;

    /* 冗余 */

    //接受请求参数
    private Long panelId;

    //是否自动播放
    @Required
    private int autoPlay;

    //子面板项列表
    private List<PanelItem> childrenList;

    private String relatedItemTitle;

    private String parentItemTitle;
    /* 冗余 */


     /*江苏移动*/

    //版本
    private Long version;

    //是否是默认焦点 值为true或false，默认为false
    private String defaultfocus;

    //通用的上部导航
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

    public String getDefaultfocus() {
        return defaultfocus;
    }

    public void setDefaultfocus(String defaultfocus) {
        this.defaultfocus = defaultfocus;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getInstallUrl() {
        return installUrl;
    }

    public void setInstallUrl(String installUrl) {
        this.installUrl = installUrl;
    }

    public String getRelatedItemTitle() {
        return relatedItemTitle;
    }

    public void setRelatedItemTitle(String relatedItemTitle) {
        this.relatedItemTitle = relatedItemTitle;
    }

    public String getParentItemTitle() {
        return parentItemTitle;
    }

    public void setParentItemTitle(String parentItemTitle) {
        this.parentItemTitle = parentItemTitle;
    }

    public List<PanelItem> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(List<PanelItem> childrenList) {
        this.childrenList = childrenList;
    }

    public Integer getHasSubItem() {
        return hasSubItem;
    }

    public void setHasSubItem(Integer hasSubItem) {
        this.hasSubItem = hasSubItem;
    }

    public Long getPanelId() {
        return panelId;
    }

    public void setPanelId(Long panelId) {
        this.panelId = panelId;
    }

    public Long getPanelItemId() {
        return panelItemId;
    }

    public void setPanelItemId(Long panelItemId) {
        this.panelItemId = panelItemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getActionType() {
        return actionType;
    }

    public void setActionType(Integer actionType) {
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

    public String getImageDistUrl() {
        return imageDistUrl;
    }

    public void setImageDistUrl(String imageDistUrl) {
        this.imageDistUrl = imageDistUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getRefItemId() {
        return refItemId;
    }

    public void setRefItemId(Long refItemId) {
        this.refItemId = refItemId;
    }

    public Long getPanelItemParentId() {
        return panelItemParentId;
    }

    public void setPanelItemParentId(Long panelItemParentId) {
        this.panelItemParentId = panelItemParentId;
    }

    public Integer getAutoRun() {
        return autoRun;
    }

    public void setAutoRun(Integer autoRun) {
        this.autoRun = autoRun;
    }

    public Integer getFocusRun() {
        return focusRun;
    }

    public void setFocusRun(Integer focusRun) {
        this.focusRun = focusRun;
    }

    public Integer getShowTitle() {
        return showTitle;
    }

    public void setShowTitle(Integer showTitle) {
        this.showTitle = showTitle;
    }

    public Integer getAnimationRun() {
        return animationRun;
    }

    public void setAnimationRun(Integer animationRun) {
        this.animationRun = animationRun;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getEpgPanelitemId() {
        return epgPanelitemId;
    }

    public void setEpgPanelitemId(Long epgPanelitemId) {
        this.epgPanelitemId = epgPanelitemId;
    }

    public Long getEpgContentId() {
        return epgContentId;
    }

    public void setEpgContentId(Long epgContentId) {
        this.epgContentId = epgContentId;
    }

    public Long getEpgRefItemId() {
        return epgRefItemId;
    }

    public void setEpgRefItemId(Long epgRefItemId) {
        this.epgRefItemId = epgRefItemId;
    }

    public Long getEpgPanelitemParentid() {
        return epgPanelitemParentid;
    }

    public void setEpgPanelitemParentid(Long epgPanelitemParentid) {
        this.epgPanelitemParentid = epgPanelitemParentid;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public enum ActionType implements IEnumDisplay {
        OpenUrl("OpenUrl"), OpenApp("OpenApp"), Install("Install");
        private String msg;

        private ActionType(String msg) {
            this.msg = msg;
        }

        public String getDisplayName() {
            return this.msg;
        }
    }

    public int getAutoPlay() {
        return autoPlay;
    }

    public void setAutoPlay(int autoPlay) {
        this.autoPlay = autoPlay;
    }

    public Integer getHasSubitem() {
        return hasSubitem;
    }

    public void setHasSubitem(Integer hasSubitem) {
        this.hasSubitem = hasSubitem;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}
