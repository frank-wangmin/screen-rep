package com.ysten.local.bss.login.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ysten.boss.systemconfig.service.SystemConfigService;
import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.system.domain.Authority;
import com.ysten.local.bss.system.domain.Operator;
import com.ysten.local.bss.system.domain.Operator.State;
import com.ysten.local.bss.system.domain.OperatorRoleMap;
import com.ysten.local.bss.system.domain.Role;
import com.ysten.local.bss.system.domain.RoleAuthorityMap;
import com.ysten.local.bss.system.domain.SysMenu;
import com.ysten.local.bss.system.service.SystemService;
import com.ysten.local.bss.web.service.ILoggerWebService;
import com.ysten.utils.http.RenderUtils;
import com.ysten.utils.json.JsonResult;
import com.ysten.utils.json.JsonUtils;

@Controller
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
    private static final String NO_AUTHORITY = "温馨提示: 你没有权限登录，请联系管理员!";
    private static final String STATE_LOCK = "温馨提示: 该用户已锁定，请联系管理员!";
    private static final String STATE_CANCEL = "温馨提示: 该用户已注销，请联系管理员!";
    private static final String PASS = "温馨提示: 恭喜您!用户名密码正确!";
    private static final String NO_PASS = "温馨提示: 用户名密码不正确!";
    private static final String NAME_NOT_EXISTS = "温馨提示: 用户名不存在!";
    private static final String PASSWORD_ERROR = "温馨提示: 密码不正确!";
    private static final String REDIRECT = "redirect:";
    private static final String USER_NAME = "userName";
    @Autowired
    private SystemConfigService systemConfigService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private ILoggerWebService loggerWebService;
    
    @RequestMapping(value = "/to_welcome")
    public String toWelcome(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
    	LOGGER.info("system controller: user login in..");
        LOGGER.info("system controller: check login ,userName{},pwd{}");
        return "welcome";
    }

    @RequestMapping(value = "/check_login")
    public void checkLoginNameExists(@RequestParam(value = USER_NAME, defaultValue = "") String userName,
            @RequestParam(value = "pwd", defaultValue = "") String pwd, HttpServletRequest request,
            HttpServletResponse response, ModelMap model) {
        LOGGER.info("system controller: user login in..");
        LOGGER.info("system controller: check login ,userName{},pwd{}", userName, pwd);

        JsonResult jr;

        if ((userName != null && !"".equals(userName)) && (pwd != null && !"".equals(pwd))) {
            Operator operator = this.systemService.getOperatorByLoginName(userName);
            if (operator != null) {
                if (userName.trim().equals(operator.getLoginName()) && (operator.checkPassword(pwd))) {
                    jr = getResultStr(operator);
                } else {
                    jr = new JsonResult(false, PASSWORD_ERROR);
                }
            } else {
                jr = new JsonResult(false, NAME_NOT_EXISTS);
            }
        } else {
            jr = new JsonResult(false, NO_PASS);
        }

        RenderUtils.renderJson(JsonUtils.toJson(jr), response);
    }

    /**
     * 用户登录
     * 
     * @param userName
     * @param pwd
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/login_in")
    public String loginIn(@RequestParam(value = USER_NAME, defaultValue = "") String userName,
            @RequestParam(value = "pwd", defaultValue = "") String pwd, HttpServletRequest request, ModelMap model) {
        LOGGER.info("system controller: check login ,userName{},pwd{}", userName, pwd);
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
                + "/";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(new Date());
        String result = REDIRECT + basePath;
        try {
            if ((userName != null && !"".equals(userName)) && (pwd != null && !"".equals(pwd))) {
                Operator operator = this.systemService.getOperatorByLoginName(userName);
                if (operator != null) {
                    if (userName.trim().equals(operator.getLoginName()) && (operator.checkPassword(pwd))) {
                        // 登录成功
                        // 1.首先找到该操作员是什么角色
                        String getRedirect = getRedirectStr(request, path, basePath, operator);
                        if (!result.equals(getRedirect)) {
                            // 记录系统操作日志
                            loggerWebService.saveOperateLog(Constants.LOGIN_MODULE, Constants.LOGIN, operator.getId()
                                    + "", "用户 " + userName + " 在" + date + "登陆系统成功", request);
                            return getRedirect;
                        } else {
                            // 记录系统操作日志
                            loggerWebService.saveOperateLog(Constants.LOGIN_MODULE, Constants.LOGIN, operator.getId()
                                    + "", "用户 " + userName + " 在" + date + "登陆系统失败,无相关权限", request);
                        }
                    }
                }
            }
            return result;
        } catch (Exception e) {
            loggerWebService.saveOperateLog(Constants.LOGIN_MODULE, Constants.LOGIN, "", "登陆异常：" + e.getMessage(),
                    request);
            LOGGER.error("login in exception:{}", e.getMessage());
        }
        return "index";
    }

    private JsonResult getResultStr(Operator operator) {
        boolean status = false;
        String str;
        if (operator.getState() != State.CANCEL) {
            if (operator.getState() != State.LOCK) {
                List<OperatorRoleMap> operatorRoleMapList = this.systemService.findByOperatorId(operator.getId());
                if (operatorRoleMapList.size() != 0) {
                    List<Role> listRole = new ArrayList<Role>();
                    List<Authority> listAuthority = new ArrayList<Authority>();
                    if (operatorRoleMapList.size() != 0) {
                        for (int i = 0; i < operatorRoleMapList.size(); i++) {
                            // 2.找到该角色对应的权限
                            OperatorRoleMap operatorRoleMap = (OperatorRoleMap) operatorRoleMapList.get(i);
                            listRole.add(operatorRoleMap.getRole());
                        }
                        for (int j = 0; j < listRole.size(); j++) {
                            Role role = listRole.get(j);
                            List<RoleAuthorityMap> roleAuthorityMapList = this.systemService.findByRoleId(role.getId());
                            if (roleAuthorityMapList.size() != 0) {
                                for (int k = 0; k < roleAuthorityMapList.size(); k++) {
                                    RoleAuthorityMap roleAuthorityMap = (RoleAuthorityMap) roleAuthorityMapList.get(k);
                                    listAuthority.add(roleAuthorityMap.getAuthority());
                                }
                            }
                        }
                        if (listAuthority.size() <= 0) {
                            str = NO_AUTHORITY;
                        } else {
                            str = PASS;
                            status = true;
                        }
                    } else {
                        str = NO_AUTHORITY;
                    }
                } else {
                    str = NO_AUTHORITY;
                }
            } else {
                str = STATE_LOCK;
            }
        } else {
            str = STATE_CANCEL;
        }
        return new JsonResult(status, str);
    }

    private String getRedirectStr(HttpServletRequest request, String path, String basePath, Operator operator) {
        List<OperatorRoleMap> operatorRoleMapList = this.systemService.findByOperatorId(operator.getId());
        if (operatorRoleMapList.size() != 0) {
            List<Long> listRoleId = new ArrayList<Long>();
            List<Authority> listAuthority = new ArrayList<Authority>();
            if (operatorRoleMapList.size() != 0) {
                for (int i = 0; i < operatorRoleMapList.size(); i++) {
                    // 2.找到该角色对应的权限
                    OperatorRoleMap operatorRoleMap = (OperatorRoleMap) operatorRoleMapList.get(i);
                    listRoleId.add(operatorRoleMap.getRole().getId());
                }
                listAuthority = this.systemService.findByRoleIds(listRoleId);
            }
            List<SysMenu> lm = this.systemService.findSysMenus();
            List<SysMenu> listSysMenu = new ArrayList<SysMenu>();
            List<SysMenu> listSysMenuHeader = new ArrayList<SysMenu>();
            List<SysMenu> listSysMenu3 = new ArrayList<SysMenu>();
            createMenus(listAuthority, lm, listSysMenu, listSysMenuHeader, listSysMenu3);
            // 3.找到所有权限对应的操作菜单
            String treeNode = createTree(listAuthority, lm, listSysMenu, listSysMenuHeader, listSysMenu3, path);
            request.getSession().setAttribute(Constants.LOGIN_SESSION_OPERATOR, operator);
            String picUrl = this.systemConfigService.getSystemConfigByConfigKey("picUrl");
            String videoUrl = this.systemConfigService.getSystemConfigByConfigKey("videoUrl");
            request.getSession().setAttribute("picUrl", picUrl);
            request.getSession().setAttribute("videoUrl", videoUrl);
            request.getSession().setAttribute("listSysMenu", treeNode);
            request.getSession().setAttribute("listSysMenuHeader", listSysMenuHeader);
            this.getAuthority(listAuthority, request);
            return "index";
        } else {
            return REDIRECT + basePath;
        }
    }

    private void getAuthority(List<Authority> listAuthority, HttpServletRequest request) {
        if (listAuthority != null && listAuthority.size() > 0) {
            for (int i = 0; i < listAuthority.size(); i++) {
                Authority authority = listAuthority.get(i);
                if (authority != null) {
                    request.getSession().setAttribute(authority.getCode(), authority.getCode());
                }
            }
        }
    }

    /**
     * @param listAuthority
     * @param lm
     * @param listSysMenu
     * @param listSysMenuHeader
     */
    private void createMenus(List<Authority> listAuthority, List<SysMenu> lm, List<SysMenu> listSysMenu,
            List<SysMenu> listSysMenuHeader, List<SysMenu> listSysMenu3) {
        for (SysMenu menu : lm) {
            if ("#".equals(menu.getUrl()) && haveAuth(listAuthority, menu)) {
                listSysMenuHeader.add(menu);
                for (SysMenu m : lm) {
                    if (m.getParentId().longValue() == menu.getId().longValue() && haveAuth(listAuthority, m)) {
                        listSysMenu.add(m);
                        for (int ii = 0; ii < lm.size(); ii++) {
                            SysMenu s = (SysMenu) lm.get(ii);
                            if (s.getParentId().longValue() == m.getId().longValue() && haveAuth(listAuthority, s)) {
                                listSysMenu3.add(s);
                            }
                        }
                    }
                }
            }
        }
    }

    private String createTree(List<Authority> listAuthority, List<SysMenu> lm, List<SysMenu> listSysMenu,
            List<SysMenu> listSysMenuHeader, List<SysMenu> listSysMenu3, String path) {
        StringBuffer sb = new StringBuffer();
        String str = "";
        if (listSysMenuHeader.size() != 0) {
            for (int a = 0; a < listSysMenuHeader.size(); a++) {
                // 获取有权限的父节点
                SysMenu smu1 = (SysMenu) listSysMenuHeader.get(a);
                boolean bool = true;
                sb.append("var treedata").append(a).append(" = [");
                sb.append("{id:'").append(smu1.getId()).append("',text:'").append(smu1.getName())
                        .append("',expanded:true,value:'").append(smu1.getUrl()).append("'},");
                for (int b = 0; b < listSysMenu.size(); b++) {
                    SysMenu sMenu = (SysMenu) listSysMenu.get(b);

                    for (int o = 0; o < lm.size(); o++) {
                        SysMenu sMenu1 = (SysMenu) lm.get(o);
                        if (smu1.getId().longValue() == sMenu.getParentId().longValue()
                                && sMenu.getAuthorityId().longValue() == sMenu1.getAuthorityId().longValue()) {
                            if (bool && StringUtils.isBlank(str)) {
                                str = "onLoad('" + sMenu1.getUrl() + "');";
                                bool = false;
                            }
                            sb.append("{id:'").append(sMenu1.getId()).append("',pid:'").append(sMenu1.getParentId())
                                    .append("',text:'").append(sMenu1.getName()).append("',value:'")
                                    .append(sMenu1.getUrl()).append("'},");
                            appendMenu3(listAuthority, listSysMenu3, path, sb, sMenu1);
                        }

                    }
                }
                sb.append("];");
                sb.append("$('#navtree")
                        .append(a)
                        .append("').omTree({dataSource : treedata")
                        .append(a)
//                        .append(",simpleDataModel: true,onSelect: function(nodeData){if(nodeData.value!='#'){document.getElementById('rFrame').src = nodeData.value;}},")
//                        .append("onClick : function(nodeData , event){")
//                        .append("if(nodeData.value){")
//                        .append("var tabId = tabElement.omTabs('getAlter', 'tab_'+nodeData.id);")
//                        .append("if(tabId){")
//                        .append("tabElement.omTabs('activate', tabId);")
//                        .append("}else{")
//                        .append("tabElement.omTabs('add',{")
//                        .append(" title : nodeData.text, ")
//                        .append("tabId : 'tab_'+nodeData.id,")
//                        .append(" scrollable: true,")
//                        .append("switchMode : 'mouseover',")
//                        .append("content : '<iframe id='+nodeData.id+' border=0 frameBorder=no scrolling=no name='+nodeData.id+' height='+ifh+' src='+nodeData.value+' width=100%></iframe>'")
//                        //.append("content : '<iframe id='+nodedata.id+' border=0 frameBorder='no' name='inner-frame' src='+nodedata.url+' height='+ifh+' width='100%'></iframe>'")
//                        .append(",closable : true")
//                        .append("});")
//                        .append("}}}")
//                        .append("});");
                        .append(",simpleDataModel: true,onSelect: function(nodedata){if(nodedata.value!='#'){document.getElementById('rFrame').src = nodedata.value;}}});");
            }
        }
        sb.append(str);
        return sb.toString();
    }

    private void appendMenu3(List<Authority> listAuthority, List<SysMenu> listSysMenu3, String path, StringBuffer sb,
            SysMenu sMenu1) {
        for (int jj = 0; jj < listSysMenu3.size(); jj++) {
            SysMenu sm = (SysMenu) listSysMenu3.get(jj);
            if (sMenu1.getId().longValue() == sm.getParentId().longValue() && haveAuth(listAuthority, sm)) {
                sb.append("{id:'").append(sm.getId()).append("',pid:'").append(sm.getParentId()).append("',text:'")
                        .append(sm.getName()).append("',value:'").append(sm.getUrl()).append("'},");
            }
        }
    }

    /*
     * 获取请求路径
     */
    private String getBasePath(HttpServletRequest request) {
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
                + "/";
        return basePath;
    }

    /*
     * 菜单是否有权限
     */
    private boolean haveAuth(List<Authority> listAuthority, SysMenu menu) {

        for (Authority au : listAuthority) {
            if (au != null) {
                if (au.getId().longValue() == menu.getAuthorityId().longValue()) {
                    return true;
                }
            }
        }
        return false;
    }

    @RequestMapping(value = "/top")
    public ModelAndView top(HttpServletRequest request, ModelMap model) {

        return new ModelAndView("top");
    }

    @RequestMapping(value = "/middle")
    public ModelAndView middle(HttpServletRequest request, ModelMap model) {

        return new ModelAndView("middle");
    }

    @RequestMapping(value = "/bottom")
    public ModelAndView bottom(HttpServletRequest request, ModelMap model) {

        return new ModelAndView("bottom");
    }

    /**
     * 操作登出
     */
    @RequestMapping(value = "/login_out")
    public ModelAndView loginOut(HttpServletRequest request) {

        String basePath = getBasePath(request);
        request.getSession().invalidate();
        return new ModelAndView("redirect:" + basePath);
    }

}
