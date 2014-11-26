package com.ysten.local.bss.customer.web.controller;

import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.device.domain.ProductPackageInfo;
import com.ysten.local.bss.device.domain.UserGroup;
import com.ysten.local.bss.device.domain.UserGroupMap;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;
import com.ysten.local.bss.device.utils.EnumConstantsInterface.UserGroupType;
import com.ysten.local.bss.util.FileUtils;
import com.ysten.local.bss.util.LoggerUtils;
import com.ysten.local.bss.util.bean.Constant;
import com.ysten.local.bss.utils.ControllerUtil;
import com.ysten.local.bss.web.service.ICustomerWebService;
import com.ysten.local.bss.web.service.ILoggerWebService;
import com.ysten.utils.http.RenderUtils;
import com.ysten.utils.json.EnumDisplayUtil;
import com.ysten.utils.json.JsonUtils;
import com.ysten.utils.json.TextValue;
import com.ysten.utils.page.Pageable;
import com.ysten.utils.string.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class UserGroupController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserGroupController.class);
    @Autowired
    private ILoggerWebService loggerWebService;
    @Autowired
    private ICustomerWebService customerWebService;

    @RequestMapping(value = "/to_user_group_list")
    public String toUserGroupList(ModelMap model) {
        return "/customer/user_group_list";
    }

    @RequestMapping(value = "/user_group_list.json")
    public void findUserGroupList(@RequestParam(value = "type", defaultValue = "") String type,
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "areaId", defaultValue = "") Long areaId,
            @RequestParam(value = Constants.START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) String pageNo,
            @RequestParam(value = Constants.LIMIT, defaultValue = Constants.PAGE_NUMBER) String pageSize,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            Pageable<UserGroup> pageable = this.customerWebService.findUserGroupByNameAndType(name, type,areaId,
                    Integer.parseInt(pageNo), Integer.parseInt(pageSize));
            RenderUtils.renderJson(EnumDisplayUtil.toJson(pageable), response);
        } catch (Exception e) {
            LOGGER.error("Find User Group Error...", e);
        }
    }

    @RequestMapping(value = "/user_group_list_by_type_area.json")
    public void getDeviceGroupByDistrictCode(@RequestParam(value = "type", defaultValue = "") String type,
                                             @RequestParam(value = "areaId", defaultValue = "") String areaId,
                                             @RequestParam(value = "tableName", defaultValue = "") String tableName,
                                             @RequestParam(value = "character", defaultValue = "") String character,
                                             @RequestParam(value = "id", defaultValue = "") String id,
                                             HttpServletResponse response) {
        List<TextValue> tv = new ArrayList<TextValue>();
        List<UserGroup> list = this.customerWebService.findUserGroupByArea(
                EnumConstantsInterface.UserGroupType.valueOf(type), tableName, character, areaId, id);
        if (list != null && list.size() > 0) {
            for (UserGroup group : list) {
                tv.add(new TextValue(group.getId().toString(), group.getAreaName() + ":" + group.getName()));
            }
            RenderUtils.renderJson(JsonUtils.toJson(tv), response);
        }
    }

    @RequestMapping(value = "/get_areaIds_by_bussId.json")
    public void getAreaIds(@RequestParam(value = Constants.ID, defaultValue = "") String id,
                                 @RequestParam(value = "tableName", defaultValue = "") String tableName,
                                 @RequestParam(value = "character", defaultValue = "") String character,
                                 HttpServletResponse response) {
        List<Long> groupList = this.customerWebService.findUserGroupById(id, character, tableName);
        List<Long> userList = this.customerWebService.findAreaByBusiness(id, character, tableName);
        StringBuilder sb = new StringBuilder("");
        if (!CollectionUtils.isEmpty(groupList)) {
            for (Long group : groupList) {
                sb.append(group).append(",");
            }
        }
        if (!CollectionUtils.isEmpty(userList)) {
            for (Long device : userList) {
                boolean bool = true;
                if (!CollectionUtils.isEmpty(groupList)) {
                    for (int i = 0; i < groupList.size(); i++) {
                        if (groupList.get(i).equals(device)) {
                            bool = false;
                        }
                    }
                    if (bool) {
                        sb.append(device).append(",");
                    }
                } else {
                    sb.append(device).append(",");
                }
            }
        }
        if (StringUtils.isBlank(sb.toString())) {
            RenderUtils.renderText(sb.toString(), response);
        } else {
            RenderUtils.renderText(sb.toString().substring(0, sb.toString().length() - 1), response);
        }
    }

    // 用户分组类型
    @RequestMapping(value = "/user_group_type.json")
    public void getUserGroup(@RequestParam(value = Constants.PAR, defaultValue = "") String par,
            HttpServletResponse response) {
        List<TextValue> tv = new ArrayList<TextValue>();
        if (par != null && !par.isEmpty()) {
            tv.add(new TextValue("", "全部"));
        }
        for (UserGroupType ugt : UserGroupType.values()) {
            tv.add(new TextValue(ugt.name(), ugt.getDisplayName()));
        }
        RenderUtils.renderJson(JsonUtils.toJson(tv), response);
    }

    @RequestMapping(value = "/user_group_list_by_type.json")
    public void getUserGroupByType(@RequestParam(value = "type", defaultValue = "") String type,
            @RequestParam(value = "dynamicFlag", defaultValue = "") String dynamicFlag,
            @RequestParam(value = "areaId", defaultValue = "") Long areaId,
            HttpServletResponse response) {
        List<TextValue> tv = new ArrayList<TextValue>();
        if (StringUtils.isNotBlank(dynamicFlag)) {
            tv.add(new TextValue("", "请选择"));
        }
        List<UserGroup> list = this.customerWebService.findUserGroupListByType(EnumConstantsInterface.UserGroupType
                .valueOf(type),dynamicFlag);
        try{
        	 if (list != null && list.size() > 0) {
                 for (UserGroup userGroup : list) {
                	 if(userGroup.getAreaId() != null){
                		 if( areaId != null && userGroup.getAreaId().equals(areaId)){
                			 tv.add(new TextValue(userGroup.getId().toString(), userGroup.getName()));
                		 }
                		 if(areaId == null){
                			 tv.add(new TextValue(userGroup.getId().toString(), userGroup.getName()));
                		 }
                	 }
                     
                 }
                 
             }
        	 RenderUtils.renderJson(JsonUtils.toJson(tv), response);
        }catch(Exception e){
        	 LOGGER.error("Get user Group by type Exception: " + LoggerUtils.printErrorStack(e));
        }
       
    }

    @RequestMapping(value = "/get_user_group_list.json")
    public void getUserGroupInfo(HttpServletResponse response) {
        List<TextValue> tv = new ArrayList<TextValue>();
        List<UserGroup> list = this.customerWebService.getUserGroupList();
        if (list != null && list.size() > 0) {
            for (UserGroup userGroup : list) {
                tv.add(new TextValue(userGroup.getId().toString(), userGroup.getName()));
            }
            RenderUtils.renderJson(JsonUtils.toJson(tv), response);
        }
    }

    @RequestMapping(value = "/get_user_group_relate_business.json")
    public void getDeviceRelateBusinessByGroupId(@RequestParam(value = "groupId", defaultValue = "") String groupId,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response) {

        Map<String, Object> json = customerWebService.findCustomerRelateBusinessByCodeOrGroupId(null, Long.parseLong(groupId));

        RenderUtils.renderJson(EnumDisplayUtil.toJson(json), response);
    }
    
    @RequestMapping(value = "/user_group_name_exists")
    public void checkBootAnimationNameExists(@RequestParam(value = "id", defaultValue = "") String id,
    		@RequestParam(value = "type", defaultValue = "") String type,
            @RequestParam("name") String name, HttpServletResponse response) {
        String str = Constants.UN_USABLE;
        if (name != null && !"".equals(name)) {
        	UserGroup userGroup = this.customerWebService.findUserGroupByNameAndType(name,type);
            if (userGroup != null && !userGroup.getId().toString().equals(id)) {
                str = Constants.UN_USABLE;
            } else {
                str = Constants.USABLE;
            }
        }
        RenderUtils.renderText(str, response);
    }

    @RequestMapping(value = "/user_group_add")
    public void addUserGroupInfo(UserGroup userGroup, HttpServletResponse response, HttpServletRequest request) {
        try {
            if (userGroup != null) {
                userGroup.setCreateDate(new Date());
                userGroup.setUpdateDate(new Date());
                String result = this.customerWebService.saveUserGroup(userGroup);
                String infos = result.equals(Constants.SUCCESS) ? "新增用户组成功！" : "新增用户组失败！"+result;
                RenderUtils.renderText(infos, response);
                String info = "新增用户组名称：" + userGroup.getName() + ";类型" + userGroup.getType().getDisplayName()
                        + userGroup.getDescription();
                result = result.equals(Constants.SUCCESS) ? "新增用户组成功！" + info : "新增用户组失败！"+result;
                this.loggerWebService.saveOperateLog(Constants.USER_GROUP_INFO_MAINTAIN, Constants.ADD, "分组名称："
                        + userGroup.getName(), result, request);
            }
        } catch (Exception e) {
        	RenderUtils.renderText("新增用户分组异常!", response);
            LOGGER.error("Save User Group Error", e);
            this.loggerWebService.saveOperateLog(Constants.USER_GROUP_INFO_MAINTAIN, Constants.ADD, "分组名称："
                    + userGroup.getName(), "新增异常!"+e.getMessage(), request);
        }
    }

    @RequestMapping(value = "/user_group_to_update")
    public void findUserGroupInfo(@RequestParam(value = "id", defaultValue = "") String id, HttpServletResponse response) {
        if (id != null && !"".equals(id)) {
            UserGroup userGroup = this.customerWebService.getUserGroupById(Long.parseLong(id));
            RenderUtils.renderJson(JsonUtils.toJson(userGroup), response);
        }
    }

    @RequestMapping(value = "/user_group_update")
    public void updateUserGroup(UserGroup userGroup, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (userGroup != null) {
                userGroup.setUpdateDate(new Date());
                String infos = this.customerWebService.updateUserGroupById(userGroup);
                String result = infos.equals(Constants.SUCCESS)?"修改用户组成功！":"修改用户组失败！"+infos;
                RenderUtils.renderText(result, response);
                String info = "修改用户组名称：" + userGroup.getName() + ";类型" + userGroup.getType().getDisplayName()
                        + userGroup.getDescription();
                result = result.equals(Constants.SUCCESS) ? "修改用户组成功！" + info : "修改用户组失败！"+result;
                this.loggerWebService.saveOperateLog(Constants.USER_GROUP_INFO_MAINTAIN, Constants.MODIFY,
                        userGroup.getId() + "", result, request);
            }
        } catch (Exception e) {
        	RenderUtils.renderText("修改用户分组异常!", response);
            LOGGER.error("Update User Group Error", e);
            this.loggerWebService.saveOperateLog(Constants.USER_GROUP_INFO_MAINTAIN, Constants.MODIFY,
                    userGroup.getId() + "", "修改用户组异常！"+e.getMessage(), request);
        }
    }

    @RequestMapping(value = "/check_Input_Sql_validate")
    public void checkInputSql(@RequestParam(value = "sql", defaultValue = "") String sql, HttpServletResponse response) {
        String result = "";
        try {
            result = this.customerWebService.checkInputSql(sql.replace(";", ""));

        } catch (Exception e) {
        	LOGGER.error("customer checkInputSql exception : ", e);
            String table = "Table";
            String column = "Unknown column";
            String notExisted = "doesn't exist";
            String whereClause = "in 'where clause'";
            String fieldList = "in 'field list'";
            String keyWords = "right syntax to use near";
            String param = "No value specified for parameter";
            if (StringUtils.isBlank(e.getMessage()) || e.getLocalizedMessage().indexOf(keyWords) >= 0
                    || e.getLocalizedMessage().indexOf(param) >= 0) {
                result = "请输入正确的查询语句!";
            } else if (e.getLocalizedMessage().indexOf(table) >= 0 && e.getLocalizedMessage().indexOf(notExisted) >= 0) {
                result = "你输入的表名"
                        + e.getLocalizedMessage().substring(e.getLocalizedMessage().indexOf(table) + table.length(),
                                e.getLocalizedMessage().indexOf(notExisted)) + "不存在！";
            } else if (e.getLocalizedMessage().indexOf(column) >= 0) {
                if (e.getLocalizedMessage().indexOf(whereClause) >= 0) {
                    result = "你输入的字段"
                            + e.getLocalizedMessage().substring(
                                    e.getLocalizedMessage().indexOf(column) + column.length(),
                                    e.getLocalizedMessage().indexOf(whereClause)) + "不存在！";
                } else if (e.getLocalizedMessage().indexOf(fieldList) >= 0) {
                    result = "你输入的字段"
                            + e.getLocalizedMessage().substring(
                                    e.getLocalizedMessage().indexOf(column) + column.length(),
                                    e.getLocalizedMessage().indexOf(fieldList)) + "不存在！";
                }
            } else {
                result = e.getLocalizedMessage();
            }
        } finally {
            RenderUtils.renderText(result, response);
        }
    }

    @RequestMapping(value = "/user_group_delete")
    public void deleteUserGroup(@RequestParam(Constants.IDS) String ids, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            if (StringUtils.isNotBlank(ids)) {
                List<Long> idsList = StringUtil.splitToLong(ids);
                // boolean bool =
                // this.customerWebService.deleteUserGroupByIds(idsList);
                String rsp = this.customerWebService.deleteUserGroupByCondition(idsList);
                String logDescription = null;
                if (StringUtils.isBlank(rsp)) {
                    logDescription = "删除用户分组成功!";
                } else {
                    logDescription = "删除用户分组失败!<br/>" + rsp;
                }
                RenderUtils.renderText(logDescription, response);
                // RenderUtils.renderText(ControllerUtil.returnString(bool),
                // response);
                // String result = (bool == true)?"删除用户组成功！":"删除用户组失败！";
                this.loggerWebService.saveOperateLog(Constants.USER_GROUP_INFO_MAINTAIN, Constants.DELETE, ids,
                        logDescription, request);
            }
        } catch (Exception e) {
        	RenderUtils.renderText("删除用户分组异常!", response);
            LOGGER.error("Delete User Group Error", e);
            this.loggerWebService.saveOperateLog(Constants.USER_GROUP_INFO_MAINTAIN, Constants.DELETE, ids, "删除用户组异常！"+e.getMessage(),
                    request);
        }
    }

    @RequestMapping(value = "/userGroup_user_map_delete")
    public void deleteUserGroupMap(@RequestParam(Constants.IDS) String ids, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            if (StringUtils.isNotBlank(ids)) {
                List<Long> idsList = StringUtil.splitToLong(ids);
                boolean bool = this.customerWebService.deleteUserGroupMap(idsList);
                RenderUtils.renderText(ControllerUtil.returnString(bool), response);
                this.loggerWebService.saveOperateLog(Constants.USER_GROUP_INFO_MAINTAIN, "解绑用户", ids,
                        (bool == true) ? "解绑用户成功!" : "解绑用户失败!", request);
            }
        } catch (Exception e) {
        	RenderUtils.renderText("解绑用户异常!", response);
            LOGGER.error("Delete Unbind Group User Map Error", e);
            this.loggerWebService.saveOperateLog(Constants.USER_GROUP_INFO_MAINTAIN, "解绑用户", ids,
                    "解绑用户异常!"+e.getMessage(), request);
        }
    }

    @RequestMapping(value = "/delete_user_by_userGroupId.json")
    public void deleteDeviceByGroupId(@RequestParam("userGroupId") Long userGroupId,
                                      @RequestParam("userCodes") String userCodes,
                                      HttpServletRequest request,
                                      HttpServletResponse response) {
        try {
            List<String> idsList = new ArrayList<String>();
            if (StringUtils.isNotBlank(userCodes)) {
                idsList = StringUtil.split(userCodes);
            }
            boolean bool = this.customerWebService.deleteUserByGroupId(userGroupId, idsList);
            RenderUtils.renderText(ControllerUtil.returnString(bool), response);
            this.loggerWebService.saveOperateLog(Constants.USER_GROUP_INFO_MAINTAIN, "移除用户", "从用户组ID:"+userGroupId+"中移除用户编号为【"+userCodes+"】的用户",
                    (bool == true) ? "移除用户成功!" : "移除用户失败!", request);
        } catch (Exception e) {
        	RenderUtils.renderText(ControllerUtil.returnString(false), response);
            LOGGER.error("Delete user By userGroupId Error", e);
            this.loggerWebService.saveOperateLog(Constants.USER_GROUP_INFO_MAINTAIN, "移除用户", "从用户组ID:"+userGroupId+"中移除用户编号为【"+userCodes+"】的用户",
                    "移除用户异常!"+e.getMessage(), request);
        }
    }

    @RequestMapping(value = "/userGroup_business_map_delete")
    public void deleteUserGroupBusinessMap(@RequestParam(Constants.IDS) String ids, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            if (StringUtils.isNotBlank(ids)) {
                List<Long> idsList = StringUtil.splitToLong(ids);
                boolean bool = this.customerWebService.deleteUserGroupBusiness(idsList);
                RenderUtils.renderText(ControllerUtil.returnString(bool), response);
                this.loggerWebService.saveOperateLog(Constants.USER_GROUP_INFO_MAINTAIN, "解绑业务", ids,
                        (bool == true) ? "解绑用户分组与业务关系成功!" : "解绑用户分组与业务关系失败!", request);
            }
        } catch (Exception e) {
        	RenderUtils.renderText(ControllerUtil.returnString(false), response);
            LOGGER.error("Delete Unbind Group Business Map Error", e);
            this.loggerWebService.saveOperateLog(Constants.USER_GROUP_INFO_MAINTAIN, "解绑业务", ids,
                    "解绑用户分组与业务关系异常!"+e.getMessage(), request);
        }
    }

    @RequestMapping(value = "/bind_users.json")
    public void bindUserGroup(@RequestParam(value = Constants.IDS) String ids,
                              @RequestParam(value = "fileField", required = false) MultipartFile userIdFile, HttpServletRequest request,
                              HttpServletResponse response) {
        try {
            String path = request.getSession().getServletContext().getRealPath(Constant.UPLOAD_PATH);
            String userIds = FileUtils.getDeviceCodesFromUploadFile(userIdFile, path);
            StringBuilder sb = new StringBuilder();
            String description = null;
            if (StringUtils.isNotBlank(userIds)) {
                description = this.customerWebService.bindUserGroupMap(ids, userIds);
            }else{
            	String info = "文件不合法：未导入要绑定的用户编号，请确认!";
            	String users = "";
            	description = "{\"info\":\"" + info + "\",\"userIds\":\"" + users + "\"}";
            }
            this.appendDescriptions(sb, description);
            RenderUtils.renderJson(sb.toString(), response);
//            if (sb.substring(0, 7).equals("success")) {
//                RenderUtils.renderText("关联用户成功!\n", response);
//            } else {
//                RenderUtils.renderText("关联用户失败!\n" + sb.toString(), response);
//            }
            this.loggerWebService.saveOperateLog(Constants.USER_GROUP_INFO_MAINTAIN, "关联用户", ids, description, request);
        } catch (Exception e) {
            LOGGER.error("Bind device exception{}", e);
            String description = "关联用户异常";
            RenderUtils.renderText(description, response);
            this.loggerWebService.saveOperateLog(Constants.USER_GROUP_INFO_MAINTAIN, "关联用户", ids, description+e.getMessage(), request);
        }
    }

    @RequestMapping(value = "/user_group_bind_business.json")
    public void bindBusinesses(@RequestParam(Constants.IDS) String ids,
                               @RequestParam(value = "animation", defaultValue = "") String animationId,
                               @RequestParam(value = "panel", defaultValue = "") String panelId,
                               @RequestParam(value = "notice", defaultValue = "") String noticeIds,
                               @RequestParam(value = "background", defaultValue = "") String backgroundIds,
                               HttpServletRequest request,HttpServletResponse response) {
        String message = "";
        if ( StringUtils.isBlank(backgroundIds) && StringUtils.isBlank(animationId)
                && StringUtils.isBlank(panelId) && StringUtils.isBlank(noticeIds) ) {
            message = "至少选择一种类型的业务做绑定，请确认！";
        } else {
            message = this.customerWebService.saveGroupBusinessBind(ids, animationId, panelId, noticeIds, backgroundIds);
        }
        RenderUtils.renderText(message, response);
        String info = "分组ID：" + ids + "绑定了业务ID："  + animationId + ","
                + panelId + "," + noticeIds + "," + backgroundIds ;
        String result = message +info;
        this.loggerWebService.saveOperateLog(Constants.USER_GROUP_INFO_MAINTAIN, "分组绑定业务", ids, result, request);
    }

    @RequestMapping(value = "/add_customer_group_maps.json")
    public void addUserGroupMapDeleteSameTypeMap(@RequestParam(value = Constants.ID) String id,@RequestParam(value = "userIds", defaultValue = "") String userIds, HttpServletRequest request,
            HttpServletResponse response){
    	String description = "";
    	if (StringUtils.isNotBlank(userIds) && StringUtils.isNotBlank(id)) {
            description = this.customerWebService.addUserGroupMap(id, userIds);
        } else {
            description = "文件不合法：未导入要绑定的用户外部编号，请确认!";
        }
    	if (StringUtils.isBlank(description)) {
    		RenderUtils.renderText("关联用户成功!", response);
        } else {
        	RenderUtils.renderText(description, response);
        }
    	 this.loggerWebService.saveOperateLog(Constants.USER_GROUP_INFO_MAINTAIN, "关联用户", id, StringUtils.isNotBlank(description)?"关联用户失败!"+description:"关联用户成功!", request);
    }

    @RequestMapping(value = "/get_user_group_ids.json")
    public void getUserGroupUserIds(@RequestParam(Constants.ID) String id, HttpServletRequest request,
                                    HttpServletResponse response) {
        List<UserGroupMap> map = this.customerWebService.findAllUserGroupMapsByUserGroupId(id);
        StringBuffer buffer = new StringBuffer("");
        if (map != null) {
            for (UserGroupMap userGroupMap : map) {
                if (StringUtils.isNotBlank(userGroupMap.getCode())) {
                    buffer.append(userGroupMap.getCode()).append(",");
                }
            }
        }
        if(StringUtils.isBlank(buffer.toString())){
            RenderUtils.renderText(buffer.toString(),response);
        }else{
            RenderUtils.renderText(buffer.substring(0,buffer.length()-1).toString(),response);
        }
    }

    @RequestMapping(value = "/get_ppList_infos.json")
    public void getPpInfos(HttpServletRequest request,HttpServletResponse response) {
        try {
            Pageable<ProductPackageInfo> pageable = this.customerWebService.findPpList();
            RenderUtils.renderJson(EnumDisplayUtil.toJson(pageable), response);
        } catch (Exception e) {
            LOGGER.error("Find Product Package List Error...", e);
        }
    }

    private void appendDescriptions(StringBuilder sb, String description) {
        if (StringUtils.isNotBlank(description)) {
            sb.append(description);
        }
    }
}
