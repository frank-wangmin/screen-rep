package com.ysten.local.bss.system.web.controller;

import com.ysten.boss.systemconfig.service.SystemConfigService;
import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.device.domain.ApkSoftwareCode;
import com.ysten.local.bss.panel.domain.LvomsDistrictCodeUrl;
import com.ysten.local.bss.system.constants.ResultInfos;
import com.ysten.local.bss.system.domain.*;
import com.ysten.local.bss.system.domain.Operator.State;
import com.ysten.local.bss.system.exception.SystemException;
import com.ysten.local.bss.system.service.SystemService;
import com.ysten.local.bss.system.vo.ResultInfo;
import com.ysten.local.bss.system.vo.Tree;
import com.ysten.local.bss.utils.ControllerUtil;
import com.ysten.local.bss.web.service.ILoggerWebService;
import com.ysten.local.bss.web.service.ILvomsDistrictCodeUrlService;
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
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class SystemController {

	private static final String ROLE = "role";
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SystemController.class);
	private static final String OPERATOR = "operator";
	private static final String USER_NAME = "userName";
	private static final String R_ID = "rId";
	private static final String DESCRIPTION = "description";
    private static Logger logger = LoggerFactory.getLogger(SystemController.class);
	
	@Autowired
	private SystemService systemService;
    @Autowired
    private SystemConfigService systemConfigService;
	@Autowired
	private ILoggerWebService loggerWebService;
	@Autowired
	private ILvomsDistrictCodeUrlService lvomsDistrictCodeUrlService;
	// 操作员状态
	@RequestMapping(value = "/operator_state.json")
	public void getOperatorState(HttpServletResponse response) {
		List<TextValue> tv = new ArrayList<TextValue>();
		for (State state : State.values()) {
			tv.add(new TextValue(state.toString(), state.getDisplayName()));
		}
		RenderUtils.renderJson(JsonUtils.toJson(tv), response);
	}

	/**
	 * 系统角色列表
	 */
	@RequestMapping(value = "/system_role_list.json")
	public void getSystemRole(HttpServletResponse response) {
		List<Role> roleList = this.systemService.findAllRole();
		List<TextValue> tv = new ArrayList<TextValue>();
		for (Role role : roleList) {
			tv.add(new TextValue(role.getId().toString(), role.getName()));
		}
		RenderUtils.renderJson(JsonUtils.toJson(tv), response);
	}
	@RequestMapping(value = "/operator_to_add")
	public String toAddOperator(ModelMap model) {
		List<Role> listRole = this.systemService.findAllRole();
		model.put("listRole", listRole);
		model.put("operatorState", State.values());
		return "/system/operator_add";
	}

	@RequestMapping(value = "/check_login_name_exists")
	public void checkLoginNameExists(
			@RequestParam(value = "loginName", defaultValue = "") String loginName,
			HttpServletRequest request, HttpServletResponse response) {
		if (loginName != null && !"".equals(loginName)) {
			try {
				Operator operator = this.systemService
						.getOperatorByLoginName(loginName);
				if (operator != null) {
					RenderUtils.renderText("已存在！", response);
				} else {
					RenderUtils.renderText("可用！", response);
				}
			} catch (Exception e) {
				LOGGER.debug("system controller: check login name exists exception:"
						+ e.getMessage());
			}
		}
	}

	@RequestMapping(value = "/operator_add")
	public void addOperator(
			@RequestParam(value = USER_NAME, defaultValue = "") String userName,
			@RequestParam(value = "loginName", defaultValue = "") String loginName,
			@RequestParam(value = "email", defaultValue = "") String email,
			@RequestParam(value = "operatorState", defaultValue = "") String operatorState,
			@RequestParam(value = "pwd1", defaultValue = "") String pwd,
			@RequestParam(value = R_ID, defaultValue = "") String rId,
			@RequestParam(value = "spId", defaultValue = "") Integer spId,
			@RequestParam(value = "isRole", defaultValue = "") String isRole,
			@RequestParam(value = "cpId", defaultValue = "") String cpId,
			@RequestParam(value = "catgId", defaultValue = "") String catgId,
			@RequestParam(value = "roles", defaultValue = "") String roles,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		try {
			if ((userName != null && !"".equals(userName))
					&& (pwd != null && !"".equals(pwd))) {
				Operator operator = new Operator();
//				operator.setCatgId(catgId);
//				operator.setCpId(cpId);
				operator.setDisplayName(userName)
                        ;
				operator.setLoginName(loginName);
				operator.setEmail(email);
//				operator.setSpId(spId);
                      State opState = null;
                      if (operatorState != null && !operatorState.isEmpty()) {
                          opState = State.valueOf(State.class, operatorState);
                          operator.setState(opState);
                      }
                      operator.setNoEncryptPassword(pwd);
				boolean operatorBool = this.systemService.addOperator(operator);
				//保存操作员与角色关系
				boolean operatorRoleBool = this.systemService.addOperatorRoleMap(operator.getId(), StringUtil.splitToLong(roles));
				
				boolean bool = operatorBool && operatorRoleBool;
				if (bool) {
					RenderUtils.renderText(Constants.SUCCESS, response);
					LOGGER.info("system controller: add operator success...");
				} else {
					RenderUtils.renderText(Constants.FAILED, response);
					LOGGER.info("system controller: add operator failed....");
				}
				List<String> nameList = new ArrayList<String>();
				nameList.add(operator.getLoginName());
				//记录系统操作日志
				String logDescription = ((bool==true)?"添加操作员成功":"添加操作员失败")+"。操作员信息："+JsonUtils.toJson(operator);
				loggerWebService.saveOperateLog(Constants.OPERATOR_INFO_MAINTAIN, Constants.ADD, operator.getId()+"",logDescription, request);
			}
		} catch (Exception e) {
			RenderUtils.renderText(Constants.FAILED, response);
			LOGGER.error("system controller: add operator exception:"
					+ e.getMessage());
			//记录系统操作日志
			loggerWebService.saveOperateLog(Constants.OPERATOR_INFO_MAINTAIN, Constants.ADD, "","异常信息："+e.getMessage(), request);
		}

	}

	@RequestMapping(value = "/operator_info")
	public String findOperatorInfo(
			@RequestParam(value = "id", defaultValue = "") String id,
			ModelMap model) {
		if (id != null && !"".equals(id)) {
			Operator operator = this.systemService.getOperatorById(Long
					.parseLong(id));
			if (operator != null) {
//				SpDefine sp = this.configService.getSpDefineById(operator
//						.getSpId());
//				model.put("sp", sp);
				model.put(OPERATOR, operator);
				List<Role> allRoleList = this.systemService.findAllRole();
				List<Role> currentList = new ArrayList<Role>();
				List<OperatorRoleMap> operatorRoleMapList = this.systemService
						.findByOperatorId(operator.getId());
				if (operatorRoleMapList.size() != 0) {
					for (int j = 0; j < allRoleList.size(); j++) {
						Role role = (Role) allRoleList.get(j);
						for (int i = 0; i < operatorRoleMapList.size(); i++) {
							OperatorRoleMap operatorRoleMap = (OperatorRoleMap) operatorRoleMapList
									.get(i);
							if (operatorRoleMap.getRole().getId().longValue() == role
									.getId().longValue()) {
								currentList.add(role);
							}
						}
					}
				}
				model.put("currentList", currentList);
			}
		}
		LOGGER.debug("system controller: to update operator by id {}", id);
		return "/system/operator_info";
	}

	@RequestMapping(value = "/to_authority_info")
	public String toFindAuthorityInfo(
			@RequestParam(value = "id", defaultValue = "") String id,
			ModelMap model) {
		if (id != null && !"".equals(id)) {
			List<Authority> listAuthority = new ArrayList<Authority>();
			Role role = this.systemService.getRoleById(Long.parseLong(id));
			List<RoleAuthorityMap> roleAuthorityMapList = this.systemService
					.findByRoleId(Long.parseLong(id));
			if (roleAuthorityMapList.size() != 0) {
				for (int i = 0; i < roleAuthorityMapList.size(); i++) {
					RoleAuthorityMap roleAuthorityMap = (RoleAuthorityMap) roleAuthorityMapList
							.get(i);
					Authority authority = this.systemService
							.getAuthorityById(roleAuthorityMap.getAuthority()
									.getId());
					if (authority != null) {
						listAuthority.add(authority);
					}
				}
			}
			model.put(ROLE, role);
			model.put("listAuthority", listAuthority);
		}
		LOGGER.debug("system controller: to authority info by roleId {}", id);
		return "/system/authority_info";
	}

	@RequestMapping(value = "/operator_to_update")
	public void toUpdateOperator(
			@RequestParam(value = "id", defaultValue = "") String id,
			HttpServletResponse response, ModelMap model) {
		if (id != null && !"".equals(id)) {
			Operator operator = this.systemService.getOperatorById(Long
					.parseLong(id));
			RenderUtils.renderJson(JsonUtils.toJson(operator), response);
		}
	}

	@RequestMapping(value = "/operator_update")
	public void updateOperator(
			@RequestParam(value = "op", defaultValue = "") String op,
			@RequestParam(value = "id", defaultValue = "") String operatorId,
			@RequestParam(value = USER_NAME, defaultValue = "") String userName,
			@RequestParam(value = "email", defaultValue = "") String email,
			@RequestParam(value = "operatorState", defaultValue = "") String operatorState,
			@RequestParam(value = "pwd1", defaultValue = "") String pwd,
			@RequestParam(value = R_ID, defaultValue = "") String rId,
			@RequestParam(value = "spId", defaultValue = "") Integer spId,
			@RequestParam(value = "isRole", defaultValue = "") String isRole,
			@RequestParam(value = "cpId", defaultValue = "") String cpId,
			@RequestParam(value = "catgId", defaultValue = "") String catgId,
			@RequestParam(value = "roles", defaultValue = "") String roles,
			HttpServletResponse response, HttpServletRequest request,
			ModelMap model) {
		try {
			if (operatorId != null && !"".equals(operatorId)) {
				Operator operator = this.systemService.getOperatorById(Long
						.parseLong(operatorId));
				operator.setDisplayName(userName);
				operator.setEmail(email);
				State opState = null;
				if (operatorState != null && !operatorState.isEmpty()) {
					opState = State
							.valueOf(State.class, operatorState);
					operator.setState(opState);
				}
				if (pwd != null && !"".equals(pwd)) {
					operator.setNoEncryptPassword(pwd);
				}
				boolean operatorBool = this.systemService.updateOperator(operator);
				//保存操作员与角色关系
				boolean operatorRoleBool = this.systemService.addOperatorRoleMap(Long.parseLong(operatorId), StringUtil.splitToLong(roles));
				boolean bool = operatorBool&&operatorRoleBool;
				if (bool) {
					RenderUtils.renderText(Constants.SUCCESS, response);
					LOGGER.info("system controller: update operator success...");
				} else {
					RenderUtils.renderText(Constants.FAILED, response);
					LOGGER.info("system controller: update operator failed....");
				}
				List<String> nameList = new ArrayList<String>();
				nameList.add(operator.getLoginName());
				//记录系统操作日志
				String logDescription = ((bool==true)?"修改操作员成功":"修改操作员失败")+"。操作员信息："+JsonUtils.toJson(operator);
				loggerWebService.saveOperateLog(Constants.OPERATOR_INFO_MAINTAIN, Constants.MODIFY, operatorId+"",logDescription, request);
			}
		} catch (Exception e) {
			RenderUtils.renderText(Constants.FAILED, response);
			LOGGER.error("system controller: set password md5 exception{}",
					e.getMessage());
			//记录系统操作日志
			loggerWebService.saveOperateLog(Constants.OPERATOR_INFO_MAINTAIN, Constants.MODIFY, "","异常信息："+e.getMessage(), request);
		}
	}
	
	//get_operator_roleIds.json
	@RequestMapping(value = "/get_operator_roleIds.json")
	public void getOperatorRoleIds(@RequestParam(value = "id", defaultValue = "") Long id,HttpServletResponse response) {
			List<OperatorRoleMap> operatorRoleMapList = this.systemService.findByOperatorId(id);
			StringBuffer buffer = new StringBuffer();
			if(operatorRoleMapList!=null&&operatorRoleMapList.size()>0){
				OperatorRoleMap operatorRoleMap = operatorRoleMapList.get(0);
				buffer.append(operatorRoleMap.getRole().getId());
				for(int i=1;i<operatorRoleMapList.size();i++){
					OperatorRoleMap operatorRole = operatorRoleMapList.get(i);
					buffer.append(",").append(operatorRole.getRole().getId());
				}
			}
			RenderUtils.renderText(buffer.toString(), response);
	}
	
	@RequestMapping(value = "/update_password")
	public void updateOperatorPassword(
			@RequestParam(value = "oldPassword", defaultValue = "") String oldPassword,
			@RequestParam(value = "newPassword1", defaultValue = "") String newPassword1,
			HttpServletResponse response, HttpServletRequest request,
			ModelMap model) {
		try {
			String flag = "failed";
			Operator operator = ControllerUtil.getLoginOperator(request);
			if(oldPassword!=null && !oldPassword.isEmpty() && !(operator.checkPassword(oldPassword))){
				flag = "oldPassError";
			}else{
				operator.setNoEncryptPassword(newPassword1.trim());
				boolean bool = this.systemService.updateOperator(operator);
				if(bool){
					flag = "success";
				}
				List<String> nameList = new ArrayList<String>();
				nameList.add(operator.getLoginName());
				//记录系统操作日志
				String logDescription = ((bool==true)?"修改密码成功":"修改密码失败");
				loggerWebService.saveOperateLog(Constants.OPERATOR_INFO_MAINTAIN, Constants.MODIFY, "",logDescription, request);
			}
			RenderUtils.renderText(flag, response);
		
		} catch (Exception e) {
			RenderUtils.renderText(Constants.FAILED, response);
			LOGGER.error("system controller: set password md5 exception{}",e.getMessage());
			//记录系统操作日志
			String logDescription = "修改密码失败."+"异常信息："+e.getMessage();
			loggerWebService.saveOperateLog(Constants.OPERATOR_INFO_MAINTAIN, Constants.MODIFY, "",logDescription, request);
		
		}
	}

	public void saveOperatorRole(String rId, Operator operator) {
		if (rId != null && !"".equals(rId)) {
			String arr[] = rId.split(",");
			if (arr != null) {
				for (int i = 0; i < arr.length; i++) {
					String str = arr[i];
					OperatorRoleMap operatorRoleMap = new OperatorRoleMap();
					operatorRoleMap.setOperator(operator);
					Role role = new Role();
					role.setId(Long.parseLong(str));
					operatorRoleMap.setRole(role);
					this.systemService.addOperatorRoleMap(operatorRoleMap);
				}
			}
		}
	}

	@RequestMapping(value = "/operator_list")
	public void findOperators(
			@RequestParam(value = "operatorName", defaultValue = "") String operatorName,
			@RequestParam(value = "loginName", defaultValue = "") String loginName,
			@RequestParam(value = "start", defaultValue = "1") String pageNo,
			@RequestParam(value = "limit", defaultValue = "15") String pageSize,
			HttpServletResponse response, ModelMap model) {
		Pageable<Operator> pageable = this.systemService.findOperators(
				operatorName, loginName, Integer.parseInt(pageNo),
				Integer.parseInt(pageSize));
		RenderUtils.renderJson(JsonUtils.toJson(pageable), response);
	}

	/**
	 * 角色操作
	 */
	@RequestMapping(value = "/role_to_add")
	public String toAddRole(HttpServletRequest request, ModelMap model) {
		List<Authority> listAuthority = this.systemService.findAllAuthority();
		model.put("listAuthority", listAuthority);
		return "/system/role_add";
	}

	@RequestMapping(value = "/role_add")
	public void addRole(
			@RequestParam(value = "roleName", defaultValue = "") String roleName,
			@RequestParam(value = "description", defaultValue = "") String description,
			@RequestParam(value = R_ID, defaultValue = "") String rId,
			@RequestParam(value = "isAuthority", defaultValue = "") String isAuthority,
			HttpServletResponse response, HttpServletRequest request,
			ModelMap model) {
		try {
			Role role = new Role();
			role.setName(roleName);
			role.setDescription(description);
			role.setCreateDate(new Date());
			boolean bool = this.systemService.addRole(role);
			if (bool) {
				RenderUtils.renderText(Constants.SUCCESS, response);
				LOGGER.info("system controller: add role success...");
			} else {
				RenderUtils.renderText(Constants.FAILED, response);
				LOGGER.info("system controller: add role failed....");
			}
			List<String> nameList = new ArrayList<String>();
			nameList.add(role.getName());
			//记录系统操作日志
			String logDescription = ((bool==true)?"添加角色成功":"添加角色失败")+"。角色信息："+JsonUtils.toJson(role);
			loggerWebService.saveOperateLog(Constants.ROLE_INFO_MAINTAIN, Constants.ADD, role.getId()+"",logDescription, request);
		} catch (Exception e) {
			RenderUtils.renderText(Constants.FAILED, response);
			LOGGER.debug("system controller: add role exception:"
					+ e.getMessage());
			//记录系统操作日志
			loggerWebService.saveOperateLog(Constants.ROLE_INFO_MAINTAIN, Constants.ADD, "","异常信息："+e.getMessage(), request);
		}
	}

	@RequestMapping(value = "/role_to_update")
	public String toUpdateRole(
			@RequestParam(value = "id", defaultValue = "") String id,
			ModelMap model) {
		if (id != null && !"".equals(id)) {
			Role role = this.systemService.getRoleById(Long.parseLong(id));
			model.put(ROLE, role);

			List<Authority> allAuthorityList = this.systemService
					.findAllAuthority();
			List<Authority> currentList = new ArrayList<Authority>();
			List<Authority> lastList = new ArrayList<Authority>();
			if (role != null) {
				List<RoleAuthorityMap> roleAuthorityMapList = this.systemService
						.findByRoleId(role.getId());
				if (roleAuthorityMapList.size() != 0) {
					for (int j = 0; j < allAuthorityList.size(); j++) {
						Authority authority = (Authority) allAuthorityList
								.get(j);
						boolean bool = false;
						for (int i = 0; i < roleAuthorityMapList.size(); i++) {
							RoleAuthorityMap roleAuthorityMap = (RoleAuthorityMap) roleAuthorityMapList
									.get(i);
							if (roleAuthorityMap.getAuthority().getId()
									.longValue() == authority.getId()
									.longValue()) {
								currentList.add(authority);
								bool = true;
							}
						}
						if (!bool) {
							lastList.add(authority);
						}
					}
				} else {
					if (role.getId() != 0) {
						lastList = allAuthorityList;
					}
				}
			}
			model.put("listCurrentAuthority", currentList);
			model.put("lastAuthority", lastList);
		}
		LOGGER.debug("system controller: to update operator by id {}", id);
		return "/system/role_update";
	}

	@RequestMapping(value = "/role_update")
	public void updateRole(
			@RequestParam(value = "op", defaultValue = "") String op,
			@RequestParam(value = "roleId", defaultValue = "") String roleId,
			@RequestParam(value = "roleName", defaultValue = "") String roleName,
			@RequestParam(value = "description", defaultValue = "") String description,
			@RequestParam(value = R_ID, defaultValue = "") String rId,
			@RequestParam(value = "isAuthority", defaultValue = "") String isAuthority,
			HttpServletResponse response, HttpServletRequest request,
			ModelMap model) {

		if (roleId != null && !"".equals(roleId)) {
			Role role = this.systemService.getRoleById(Long.parseLong(roleId));
			role.setName(roleName);
			role.setDescription(description);
			boolean bool = this.systemService.updateRole(role);
			if (bool) {
				RenderUtils.renderText(Constants.SUCCESS, response);
				LOGGER.info("system controller: update role success...");
				//记录系统操作日志
				String logDescription = "修改角色成功"+"。角色信息："+JsonUtils.toJson(role);
				loggerWebService.saveOperateLog(Constants.ROLE_INFO_MAINTAIN, Constants.MODIFY, roleId+"",logDescription, request);
				
			} else {
				RenderUtils.renderText(Constants.FAILED, response);
				LOGGER.info("system controller: update role failed....");
				//记录系统操作日志
				String logDescription = "修改角色失败"+"。角色信息："+JsonUtils.toJson(role);
				loggerWebService.saveOperateLog(Constants.ROLE_INFO_MAINTAIN, Constants.MODIFY, roleId+"",logDescription, request);
				
			}
			List<String> nameList = new ArrayList<String>();
			nameList.add(role.getName());
		}
	}

	public void saveRoleAuthority(String rId, Role role) {
		if (rId != null && !"".equals(rId)) {
			String arr[] = rId.split(",");
			if (arr != null) {
				for (int i = 0; i < arr.length; i++) {
					String str = arr[i];
					RoleAuthorityMap roleAuthorityMap = new RoleAuthorityMap();
					Authority authority = new Authority();
					authority.setId(Long.parseLong(str));
					roleAuthorityMap.setAuthority(authority);
					roleAuthorityMap.setRole(role);
					this.systemService.addRoleAuthorityMap(roleAuthorityMap);
				}
			}
		}
	}

	@RequestMapping(value = "/role_list")
	public void findRoles(
			@RequestParam(value = "roleName", defaultValue = "") String roleName,
			@RequestParam(value = "start", defaultValue = "1") String pageNo,
			@RequestParam(value = "limit", defaultValue = "15") String pageSize,
			HttpServletResponse response, ModelMap model) {
		Pageable<Role> pageable = this.systemService.findRoles(roleName,
				Integer.parseInt(pageNo), Integer.parseInt(pageSize));
		RenderUtils.renderJson(JsonUtils.toJson(pageable), response);
	}

	/**
	 * 权限操作
	 */
	@RequestMapping(value = "/authority_to_add")
	public String toAdAuthority() {
		return "/system/authority_add";
	}

	@RequestMapping(value = "/authority_add")
	public void addAuthority(
			@RequestParam(value = "parentId", defaultValue = "") Long parentId,
			@RequestParam(value = "authType", defaultValue = "") String authType,
			@RequestParam(value = "name", defaultValue = "") String authorityName,
			@RequestParam(value = DESCRIPTION, defaultValue = "") String description,
			HttpServletResponse response, HttpServletRequest request,
			ModelMap model) {
		try {
			Authority authority = new Authority();
			authority.setParentId(parentId);
			authority.setName(authorityName);
			authority.setDescription(description);
			boolean bool = this.systemService.addAuthority(authority);
			if (bool) {
				RenderUtils.renderText(Constants.SUCCESS, response);
			} else {
				RenderUtils.renderText(Constants.FAILED, response);
			}
			List<String> nameList = new ArrayList<String>();
			nameList.add(authority.getName());
			//记录系统操作日志
			String logDescription = ((bool==true)?"添加权限成功":"添加权限失败")+"。权限信息："+JsonUtils.toJson(authority);
			loggerWebService.saveOperateLog(Constants.AUTHORITY_INFO_MAINTAIN, Constants.ADD, authority.getId()+"",logDescription, request);
		} catch (Exception e) {
			RenderUtils.renderText(Constants.FAILED, response);
			LOGGER.debug("system controller: add authority exception:"
					+ e.getMessage());
			//记录系统操作日志
			loggerWebService.saveOperateLog(Constants.AUTHORITY_INFO_MAINTAIN, Constants.ADD, "","添加权限失败."+"异常信息："+e.getMessage(), request);
		}
	}
	
	/**
	 * 权限修改时填充名称和描述信息
	 */
	@RequestMapping(value = "/to_update_authority.json")
	public void fillUpdateAuthority(
			@RequestParam(value = "id", defaultValue = "") String id,
			HttpServletResponse response) {
		Authority authority = this.systemService.getAuthorityById(Long
					.parseLong(id));
		String name = authority.getName();
		name = name.substring(name.indexOf("】")+1, name.length());
		authority.setName(name);
		RenderUtils.renderJson(JsonUtils.toJson(authority), response);
	}
	
	@RequestMapping(value = "/authority_to_update")
	public String toUpdateAuthority(
			@RequestParam(value = "id", defaultValue = "") String id,
			ModelMap model) {
		if (id != null && !"".equals(id)) {
			Authority authority = this.systemService.getAuthorityById(Long
					.parseLong(id));
			model.put("authority", authority);
		}
		LOGGER.debug("system controller: to update authority by id {}", id);
		return "/system/authority_update";
	}

	@RequestMapping(value = "/authority_update")
	public void updateAuthority(
			@RequestParam(value = "id", defaultValue = "") String authorityId,
			@RequestParam(value = "authType", defaultValue = "") String authType,
			@RequestParam(value = "name", defaultValue = "") String authorityName,
			@RequestParam(value = DESCRIPTION, defaultValue = "") String description,
			HttpServletResponse response, HttpServletRequest request,
			ModelMap model) {
		if (authorityId != null && !"".equals(authorityId)) {
			Authority authority = this.systemService.getAuthorityById(Long
					.parseLong(authorityId));
			authority.setName(authorityName);
			authority.setDescription(description);
			boolean bool = this.systemService.updateAuthority(authority);
			if (bool) {
				RenderUtils.renderText(Constants.SUCCESS, response);
				//记录系统操作日志
				String logDescription = "修改权限成功"+"。权限信息："+JsonUtils.toJson(authority);
				loggerWebService.saveOperateLog(Constants.AUTHORITY_INFO_MAINTAIN, Constants.MODIFY, authorityId,logDescription, request);
			} else {
				RenderUtils.renderText(Constants.FAILED, response);
				//记录系统操作日志
				String logDescription = ((bool==true)?"修改权限成功":"修改权限失败")+"。权限信息："+JsonUtils.toJson(authority);
				loggerWebService.saveOperateLog(Constants.AUTHORITY_INFO_MAINTAIN, Constants.MODIFY, authorityId,logDescription, request);
				
			}
			List<String> nameList = new ArrayList<String>();
			nameList.add(authority.getName());
			LOGGER.info("system controller: update authority success...result:"
					+ bool);
		}
	}

	@RequestMapping(value = "/to_authority_list")
	public String toAuthorityList() {
		return "/system/authority_list";
	}

	@RequestMapping(value = "/to_role_list")
	public String toRoleList() {
		return "/system/role_list";
	}

	@RequestMapping(value = "/to_operator_list")
	public String toOperatorList() {
		return "/system/operator_list";
	}

    @RequestMapping(value = "/to_sys_version_list")
    public String toSysVersionList(ModelMap model) {
        return "/system/sys_version_list";
    }
    
    @RequestMapping(value = "/to_lvoms_district_code_url_list")
    public String toLvomsList(ModelMap model) {
        return "/system/lvoms_district_code_url_list";
    }

    //系统版本
    @RequestMapping(value = "/sys_version_list.json")
    public void getSysVersionList(
            @RequestParam(value = "versionId", defaultValue = "") String versionId,
            @RequestParam(value = "versionName", defaultValue = "") String versionName,
            @RequestParam(value = Constants.START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) String pageNo,
            @RequestParam(value = Constants.LIMIT, defaultValue = Constants.PAGE_NUMBER) String pageSize,
            HttpServletRequest request, HttpServletResponse response) {

        Pageable<SysVersion> page = this.systemService.findSysVersions(versionId, versionName,
                Integer.valueOf(pageNo), Integer.parseInt(pageSize));
        RenderUtils.renderJson(EnumDisplayUtil.toJson(page), response);
    }
    @RequestMapping(value = "/lvoms_district_code_url_list.json")
    public void getLvomsAreaUrlList(
            @RequestParam(value = "packageId", defaultValue = "") String packageId,
            @RequestParam(value = "distCode", defaultValue = "") String distCode,
            @RequestParam(value = "status", defaultValue = "") String status,
            @RequestParam(value = Constants.START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) Integer pageNo,
            @RequestParam(value = Constants.LIMIT, defaultValue = Constants.PAGE_NUMBER) Integer pageSize,
            HttpServletRequest request, HttpServletResponse response) {

        Pageable<LvomsDistrictCodeUrl> page = this.lvomsDistrictCodeUrlService.findListByConditions(distCode, packageId.equals("")?null:Long.parseLong(packageId), status, pageNo, pageSize);
        RenderUtils.renderJson(EnumDisplayUtil.toJson(page), response);
    }
    @RequestMapping(value = "/lvmos_districtCode_url_add.json")
    public void addLvomsAreaUrl(LvomsDistrictCodeUrl lvomsDistrictCodeUrl,
            HttpServletRequest request, HttpServletResponse response) {
    	 boolean bool = this.lvomsDistrictCodeUrlService.saveLvomsUrl(lvomsDistrictCodeUrl);
    	 RenderUtils.renderText(ControllerUtil.returnString(bool), response);
    	 loggerWebService.saveOperateLog(Constants.LVMOS_DISTRICTCODE_URL_MAINTAIN, Constants.ADD, lvomsDistrictCodeUrl.getId() + "",
                 bool==true?"新增电视看点Url成功!"+JsonUtils.toJson(lvomsDistrictCodeUrl):"新增电视看点Url失败!", request);
    }
    @RequestMapping(value = "/lvoms_district_code_url_to_update.json")
    public void getLvomsAreaUrl(@RequestParam("id") Long id,HttpServletResponse response) {
    	if (id != null && !"".equals(id.toString())) {
    	LvomsDistrictCodeUrl lvomsDistrictCodeUrl = this.lvomsDistrictCodeUrlService.getLvomsDistrictCodeUrlById(id);
    	 RenderUtils.renderJson(EnumDisplayUtil.toJson(lvomsDistrictCodeUrl), response);
    	}else{
    		RenderUtils.renderJson(null, response);
    	}
    }
    @RequestMapping(value = "/lvmos_districtCode_url_update.json")
    public void updateLvomsAreaUrl(LvomsDistrictCodeUrl lvomsDistrictCodeUrl,
            HttpServletRequest request, HttpServletResponse response) {
    	 boolean bool = this.lvomsDistrictCodeUrlService.updateLvomsUrl(lvomsDistrictCodeUrl);
    	 RenderUtils.renderText(ControllerUtil.returnString(bool), response);
    	 loggerWebService.saveOperateLog(Constants.LVMOS_DISTRICTCODE_URL_MAINTAIN, Constants.MODIFY, lvomsDistrictCodeUrl.getId() + "",
                 bool==true?"修改电视看点Url成功!"+JsonUtils.toJson(lvomsDistrictCodeUrl):"修改电视看点Url失败!", request);
    }
    @RequestMapping(value = "/lvmos_districtCode_url_delete.json")
    public void deleteBootAnimation(@RequestParam(Constants.IDS) String ids, HttpServletRequest request,HttpServletResponse response) {
        List<Long> idsList = StringUtil.splitToLong(ids);
        boolean bool = this.lvomsDistrictCodeUrlService.deleteLvomsUrl(idsList);
        RenderUtils.renderText(ControllerUtil.returnString(bool), response);
        loggerWebService.saveOperateLog(Constants.LVMOS_DISTRICTCODE_URL_MAINTAIN, Constants.DELETE, ids + "",
                bool==true?"删除电视看点Url成功!":"删除电视看点Url失败!", request);
    }
    @RequestMapping(value = "/lvmos_url_of_distCode_exists")
    public void checkLvmosUrlDistCodeExists(@RequestParam(value = "id", defaultValue = "") String id,
            @RequestParam("districtCode") String districtCode,
            @RequestParam("packageId") String packageId,HttpServletResponse response) {
        String str = Constants.UN_USABLE;
        if (districtCode != null && !"".equals(districtCode) && packageId != null && !"".equals(packageId)) {
        	LvomsDistrictCodeUrl lvomsDistrictCodeUrl = this.lvomsDistrictCodeUrlService.getLvomsUrlByDistCodeAndPackageId(districtCode, Long.parseLong(packageId));
            if (lvomsDistrictCodeUrl != null && !lvomsDistrictCodeUrl.getId().toString().equals(id)) {
                str = Constants.UN_USABLE;
            } else {
                str = Constants.USABLE;
            }
        }
        RenderUtils.renderText(str, response);
    }
	/*@RequestMapping(value = "/authority_list")
	public void findAuthoritys(
			@RequestParam(value = "start", defaultValue = "1") String pageNo,
			@RequestParam(value = "limit", defaultValue = "15") String pageSize,
			HttpServletResponse response, ModelMap model) {
		Pageable<Authority> pageable = this.systemService.findAuthoritys(
				Integer.parseInt(pageNo), Integer.parseInt(pageSize));
		RenderUtils.renderJson(JsonUtils.toJson(pageable), response);
	}*/
	
	/**
	 * 以树状结构显示权限
	 */
	@RequestMapping(value = "/authority_list")
	public void findAuthoritysNew(
			HttpServletResponse response, ModelMap model) {
		List<Tree> authList = this.systemService.getAuthorityTree();
		if(LOGGER.isDebugEnabled()){
			LOGGER.info("权限信息："+EnumDisplayUtil.toJson(authList));
		}
		RenderUtils.renderJson(EnumDisplayUtil.toJson(authList), response);
	}

	@RequestMapping(value = "/operator_role_map_to_manage")
	public String toOperatorRoleMap(ModelMap model) {
		List<Operator> operator = this.systemService.findAllOperator();
		model.put(OPERATOR, operator);
		return "/system/operator_role_map_manage";
	}

	@RequestMapping(value = "/dynamicOperatorRole")
	public void dynamicOperatorRole(
			@RequestParam(value = "operatorId", defaultValue = "") String operatorId,
			HttpServletResponse response, ModelMap model) {
		LOGGER.info("system controller: dynamic operator role");
		List<Role> allRoleList = this.systemService.findAllRole();
		List<Role> currentList = new ArrayList<Role>();
		List<Role> lastList = new ArrayList<Role>();
		if (operatorId != null && !"".equals(operatorId)) {
			List<OperatorRoleMap> operatorRoleMapList = this.systemService
					.findByOperatorId(Long.parseLong(operatorId));
			if (operatorRoleMapList.size() != 0) {
				for (int j = 0; j < allRoleList.size(); j++) {
					Role role = (Role) allRoleList.get(j);
					boolean bool = false;
					for (int i = 0; i < operatorRoleMapList.size(); i++) {
						OperatorRoleMap operatorRoleMap = (OperatorRoleMap) operatorRoleMapList
								.get(i);
						if (operatorRoleMap.getRole().getId().longValue() == role
								.getId().longValue()) {
							currentList.add(role);
							bool = true;
						}
					}
					if (!bool) {
						lastList.add(role);
					}
				}
			} else {
				if (!"0".equals(operatorId)) {
					lastList = allRoleList;
				}
			}
		}

		Object[] o = new Object[2];
		o[0] = currentList;
		o[1] = lastList;

		RenderUtils.renderJson(JsonUtils.toJson(o), response);
	}

	@RequestMapping(value = "/operator_role_map_manage")
	public void addOperatorRoleMap(
			@RequestParam(value = R_ID, defaultValue = "") String rId,
			@RequestParam(value = "operatorId", defaultValue = "") String operatorId,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		LOGGER.info(
				"system controller: operator role map manage ,roleId{},operatorId{}",
				rId, operatorId);
		boolean bool = false;
		if (operatorId != null && !"".equals(operatorId)) {
			bool = this.systemService.deleteOperatorRoleMapByOperatorId(Long
					.parseLong(operatorId));
			if (rId != null && !"".equals(rId)) {
				String arr[] = rId.split(",");
				if (arr != null) {
					for (int i = 0; i < arr.length; i++) {
						bool = saveOperatorRoleMap(arr[i], operatorId);
					}
				}
			}
		} else {
			bool = this.systemService.deleteOperatorRoleMapByOperatorId(Long
					.parseLong(operatorId));
		}
		logger.debug("bool is "+bool);
		List<String> nameList = new ArrayList<String>();
		Operator operator = this.systemService.getOperatorById(Long
				.parseLong(operatorId));
		if (operator != null) {
			nameList.add(operator.getLoginName());
		}
		//记录系统操作日志
		String logDescription = ((bool==true)?"操作员角色关联成功":"操作员角色关联失败")+".操作员ID："+operatorId+",角色ID："+rId;
		RenderUtils.renderText(ControllerUtil.returnString(true), response);
		loggerWebService.saveOperateLog(Constants.OPERATOR_ROLE_RELEVANCE, Constants.ADD, operatorId,logDescription, request);

	}

	private boolean saveOperatorRoleMap(String str, String operatorId) {
		OperatorRoleMap operatorRoleMap = new OperatorRoleMap();
		Operator operator = new Operator();
		operator.setId(Long.parseLong(operatorId));
		operatorRoleMap.setOperator(operator);
		Role role = new Role();
		role.setId(Long.parseLong(str));
		operatorRoleMap.setRole(role);
		return this.systemService.addOperatorRoleMap(operatorRoleMap);
	}

	@RequestMapping(value = "/role_authority_map_to_manage")
	public String toRoleAuthorityMap(HttpServletRequest request, ModelMap model) {
		List<Role> role = this.systemService.findAllRole();
		model.put(ROLE, role);
		return "/system/role_authority_map_manage";
	}

	@RequestMapping(value = "/dynamicRoleAuthority")
	public void dynamicRoleAuthority(
			@RequestParam(value = "roleId", defaultValue = "") String roleId,
			HttpServletResponse response, ModelMap model) {
		LOGGER.info("system controller: dynamic role authority ->roleId{}",
				roleId);
		List<Authority> allAuthorityList = this.systemService
				.findAllAuthority();
		List<Authority> currentList = new ArrayList<Authority>();
		List<Authority> lastList = new ArrayList<Authority>();
		if (roleId != null && !"".equals(roleId)) {
			List<RoleAuthorityMap> roleAuthorityMapList = this.systemService
					.findByRoleId(Long.parseLong(roleId));
			if (roleAuthorityMapList.size() != 0) {
				for (int j = 0; j < allAuthorityList.size(); j++) {
					Authority authority = (Authority) allAuthorityList.get(j);
					boolean bool = false;
					for (int i = 0; i < roleAuthorityMapList.size(); i++) {
						RoleAuthorityMap roleAuthorityMap = (RoleAuthorityMap) roleAuthorityMapList
								.get(i);
						if (roleAuthorityMap.getAuthority().getId().longValue() == authority
								.getId().longValue()) {
							currentList.add(authority);
							bool = true;
						}
					}
					if (!bool) {
						lastList.add(authority);
					}
				}
			} else {
				if (!"0".equals(roleId)) {
					lastList = allAuthorityList;
				}
			}
		}
		Object[] o = new Object[2];
		o[0] = currentList;
		o[1] = lastList;

		RenderUtils.renderJson(JsonUtils.toJson(o), response);

	}

	@RequestMapping(value = "/role_authority_map_manage")
	public void addRoleAuthorityMap(
			@RequestParam(value = R_ID, defaultValue = "") String rId,
			@RequestParam(value = "roleId", defaultValue = "") String roleId,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {

		boolean bool = false;
		if (roleId != null && !"".equals(roleId)) {
			bool = this.systemService.deleteRoleAuthorityMapByRoleId(Long
					.parseLong(roleId));
			if (rId != null && !"".equals(rId)) {
				String arr[] = rId.split(",");
				if (arr != null) {
					for (int i = 0; i < arr.length; i++) {
						bool = saveRoleAuthorityMap(arr[i], roleId);
					}
				}
			}
		}
          logger.debug("bool is "+bool);
		//记录系统操作日志
		String logDescription = ((bool==true)?"角色权限关联成功":"角色权限关联失败")+".角色ID："+roleId+",权限ID："+rId;
		RenderUtils.renderText(ControllerUtil.returnString(true), response);
		loggerWebService.saveOperateLog(Constants.ROLE_AUTHORITY_RELEVANCE, Constants.ADD, roleId,logDescription, request);

	}

	private boolean saveRoleAuthorityMap(String str, String roleId) {
		RoleAuthorityMap roleAuthorityMap = new RoleAuthorityMap();
		Role role = new Role();
		role.setId(Long.parseLong(roleId));
		Authority authority = new Authority();
		authority.setId(Long.parseLong(str));
		roleAuthorityMap.setAuthority(authority);
		roleAuthorityMap.setRole(role);
		return this.systemService.addRoleAuthorityMap(roleAuthorityMap);
	}

	@RequestMapping(value = "/authority_menu_map_to_manage")
	public String toAuthorityMenuMap(ModelMap model) {
		List<Authority> authority = this.systemService.findAllAuthority();
		List<SysMenu> listSysMenu = this.systemService.findSysMenus();
		model.put("authority", authority);
		model.put("listSysMenu", listSysMenu);
		return "/system/authority_menu_map_manage";
	}

	@RequestMapping(value = "/dynamicAuthorityMenu")
	public void dynamicAuthorityMenu(
			@RequestParam(value = R_ID, defaultValue = "") String sysMenuId,
			@RequestParam(value = "authorityId", defaultValue = "") String authorityId,
			HttpServletResponse response) {
		if ((authorityId != null && !"".equals(authorityId))
				&& (sysMenuId != null && !"".equals(sysMenuId))) {
			try {
				PrintWriter out = response.getWriter();
				SysMenu sysMenu = this.systemService
						.getSysMenuBySysIdAndAuthorityId(sysMenuId, authorityId);
				if (sysMenu != null) {
					out.print("1");
				} else {
					SysMenu sm = this.systemService
							.getSysMenuByAuthorityId(Long
									.parseLong(authorityId));
					if (sm != null) {
						out.print("3");
					} else {
						SysMenu s = this.systemService
								.getSysMenuBySysMenuId(Long
										.parseLong(sysMenuId));
						if (s.getAuthorityId() == null) {
							out.print("0");
						} else {
							out.print("2");
						}
					}
				}
			} catch (NumberFormatException e) {
				LOGGER.debug("system controller: dynamic authority menu Exception:{}",e);
			} catch (IOException e) {
				LOGGER.debug("system controller: dynamic authority menu Exception:{}",e);
			}
		}
	}

	@RequestMapping(value = "/update_authority_menu")
	public String updateAuthorityMen(
			@RequestParam(value = R_ID, defaultValue = "") String sysMenuId,
			@RequestParam(value = "authorityId", defaultValue = "") String authorityId,
			HttpServletResponse response, ModelMap model) {
		if ((authorityId != null && !"".equals(authorityId))
				&& (sysMenuId != null && !"".equals(sysMenuId))) {
			SysMenu sysMenu = this.systemService.getSysMenuBySysMenuId(Long
					.parseLong(sysMenuId));
			if (sysMenu != null) {
				sysMenu.setAuthorityId(Long.parseLong(authorityId));
				this.systemService.updateSysMenu(sysMenu);
			}
		}
		this.toAuthorityMenuMap(model);
		return "/system/authority_menu_map_manage";
	}
	
    @RequestMapping(value = "/get_system_title")
    public void getSystemTitle(HttpServletRequest request,HttpServletResponse response,Model model) {
        String systemTitle = this.systemConfigService.getSystemConfigByConfigKey("systemTitle");
        request.getSession().setAttribute("systemTitle", systemTitle);
        model.addAttribute("systemTitle", systemTitle);
        RenderUtils.renderText(systemTitle, response);
    }
    
    @RequestMapping(value = "/delete_authority")
    public void delete_authority(
    		@RequestParam(value = "id", defaultValue = "") Long id,
    		HttpServletRequest request,HttpServletResponse response) {
    	boolean bool = this.systemService.deleteAuthority(id);
    	//记录系统操作日志
    	String logDescription = ((bool==true)?"权限删除成功":"权限删除失败");
        RenderUtils.renderText(ControllerUtil.returnString(bool), response);
    	loggerWebService.saveOperateLog(Constants.AUTHORITY_INFO_MAINTAIN, Constants.DELETE, id+"",logDescription, request);

    }
    
    /**
     * 查找角色所有权限id
     * @param id
     * @param request
     * @param response
     */
    @RequestMapping(value = "/selected_authority_for_role.json")
    public void getRoleAuthority(
    		@RequestParam(value = "id", defaultValue = "") Long id,
    		HttpServletRequest request,HttpServletResponse response) {
    	List<RoleAuthorityMap> roleAuthMapList = this.systemService.findAuthorityForDisplayTree(id);
    	StringBuffer strB  = new StringBuffer();
    	int  listSize = roleAuthMapList.size();
    	for(int i=0;i<listSize;i++){
    		RoleAuthorityMap roleAuthMap = roleAuthMapList.get(i);
    		if(i==listSize-1){
    			strB.append(roleAuthMap.getAuthority().getId());
    		}else{
    			strB.append(roleAuthMap.getAuthority().getId()).append(",");
    		}
    	}
        RenderUtils.renderText(strB.toString(), response);
    }

	/**
	 * 角色权限分配
	 * 
	 * @param authIds
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/role_authority_assign")
	public void assignAuthorityForRole(@RequestParam(value = "authIds", defaultValue = "") String authIds,
			@RequestParam(value = "roleId", defaultValue = "") Long roleId, HttpServletRequest request, HttpServletResponse response) {
		try {
			ResultInfo result = this.systemService.assignAuthorityForRole(roleId, StringUtil.splitToLong(authIds));
			
			String logDescription =null;
			if(ResultInfos.OK.equals(result)){
				logDescription = "角色权限分配成功";
				RenderUtils.renderText(ControllerUtil.returnString(true), response);
			}else{
				logDescription= "角色权限分配失败";
				RenderUtils.renderText(result.getInfo(), response);
			}
			// 记录系统操作日志
			loggerWebService.saveOperateLog(Constants.ROLE_AUTHORITY_RELEVANCE, Constants.ADD, roleId+"", logDescription+".角色ID：" + roleId + ",权限ID：" + authIds, request);
		} catch (SystemException e) {
			RenderUtils.renderText(e.getMessage(), response);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.error(e.getMessage());
			}
			// 记录系统操作日志
			String logDescription = "角色权限分配失败。" + e.getMessage();
			loggerWebService.saveOperateLog(Constants.ROLE_AUTHORITY_RELEVANCE, Constants.ADD, roleId+"", logDescription+".角色ID：" + roleId + ",权限ID：" + authIds, request);
		}
	}
}
