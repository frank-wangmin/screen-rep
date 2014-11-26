package com.ysten.local.bss.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ysten.local.bss.system.domain.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysten.local.bss.system.constants.ResultInfos;
import com.ysten.local.bss.system.exception.SystemException;
import com.ysten.local.bss.system.repository.ISystemRepository;
import com.ysten.local.bss.system.service.SystemService;
import com.ysten.local.bss.system.vo.ResultInfo;
import com.ysten.local.bss.system.vo.Tree;
import com.ysten.local.bss.system.vo.TreeNode;
import com.ysten.utils.json.JsonUtils;
import com.ysten.utils.page.Pageable;

@Service
public class SystemServiceImpl implements SystemService {

    @Autowired
    private ISystemRepository systemRepository;
    private Logger log = Logger.getLogger(SystemServiceImpl.class);

    @Override
    public boolean addOperator(Operator operator) {
        return this.systemRepository.addOperator(operator);
    }

    @Override
    public boolean updateOperator(Operator operator) {
        return this.systemRepository.updateOperator(operator);
    }

    @Override
    public boolean deleteOperator(Long id) {
        return this.systemRepository.deleteOperator(id);
    }

    @Override
    public Pageable<Operator> findOperators(String operatorName, String loginName, int pageNo, int pageSize) {
        return this.systemRepository.findOperators(operatorName, loginName, pageNo, pageSize);
    }

    @Override
    public Pageable<SysVersion> findSysVersions(String versionId, String versionName, Integer pageNo, Integer pageSize) {
        return this.systemRepository.findSysVersions(versionId, versionName, pageNo, pageSize);
    }

    @Override
    public Operator getOperatorById(Long id) {
        return this.systemRepository.getOperatorById(id);
    }

    @Override
    public boolean addRole(Role role) {
        return this.systemRepository.addRole(role);
    }

    @Override
    public boolean updateRole(Role role) {
        return this.systemRepository.updateRole(role);
    }

    @Override
    public boolean deleteRole(Long id) {
        return this.systemRepository.deleteRole(id);
    }

    @Override
    public Pageable<Role> findRoles(String roleName,int pageNo, int pageSize) {
        return this.systemRepository.findRoles(roleName,pageNo, pageSize);
    }

    @Override
    public Role getRoleById(Long id) {
        return this.systemRepository.getRoleById(id);
    }

    @Override
    public boolean addAuthority(Authority authority) {
        return this.systemRepository.addAuthority(authority);
    }

    @Override
    public boolean updateAuthority(Authority authority) {
        return this.systemRepository.updateAuthority(authority);
    }

    @Override
    public boolean deleteAuthority(Long id) {
    	List<Authority> authList = new ArrayList<Authority>();
    	List<Authority> condition = new ArrayList<Authority>();
    	condition.add(this.systemRepository.getAuthorityById(id));
    	authList= this.systemRepository.findRelatedAuthorities(condition);
    	authList.addAll(condition);
    	
    	List<Long> needDel = new ArrayList<Long>();
    	for(Authority au: authList){
    		needDel.add(au.getId());
    	}
    	if(log.isDebugEnabled()){
    		log.info("本次删除权限的总量"+needDel.size());
    		log.info("本次删除的权限ID有："+ JsonUtils.toJson(needDel));
    	}
    	boolean bool = this.systemRepository.deleteAuthorityByIds(needDel);
        return bool;
    }

    @Override
    public Pageable<Authority> findAuthoritys(int pageNo, int pageSize) {
        return this.systemRepository.findAuthoritys(pageNo, pageSize);
    }

    @Override
    public Authority getAuthorityById(Long id) {
        return this.systemRepository.getAuthorityById(id);
    }

    @Override
    public Operator getOperatorByLoginName(String loginName) {
        return this.systemRepository.getOperatorByLoginName(loginName);
    }

    @Override
    public List<Operator> findAllOperator() {
        return this.systemRepository.findAllOperator();
    }

    @Override
    public List<OperatorRoleMap> findByOperatorId(Long operatorId) {
        return this.systemRepository.findByOperatorId(operatorId);
    }

    @Override
    public List<Role> findAllRole() {
        return this.systemRepository.findAllRole();
    }

    @Override
    public boolean addOperatorRoleMap(OperatorRoleMap operatorRoleMap) {
        return this.systemRepository.addOperatorRoleMap(operatorRoleMap);
    }

    @Override
    public OperatorRoleMap getOperatorRoleMapByRoleId(String operatorId, String roleId) {
        return this.systemRepository.getOperatorRoleMapByRoleId(operatorId, roleId);
    }

    @Override
    public boolean deleteOperatorRoleMapByOperatorId(Long operatorId) {
        return this.systemRepository.deleteOperatorRoleMapByOperatorId(operatorId);
    }

    @Override
    public List<Authority> findAllAuthority() {
        return this.systemRepository.findAllAuthority();
    }

    @Override
    public List<RoleAuthorityMap> findByRoleId(Long roleId) {
        return this.systemRepository.findByRoleId(roleId);
    }

    @Override
    public boolean deleteRoleAuthorityMapByRoleId(Long roleId) {
        return this.systemRepository.deleteRoleAuthorityMapByRoleId(roleId);
    }

    @Override
    public boolean addRoleAuthorityMap(RoleAuthorityMap roleAuthorityMap) {
        return this.systemRepository.addRoleAuthorityMap(roleAuthorityMap);
    }

    @Override
    public List<SysMenu> findSysMenus() {
        return this.systemRepository.findSysMenus();
    }

    @Override
    public SysMenu getSysMenuBySysIdAndAuthorityId(String sysMenuId, String authorityId) {
        return this.systemRepository.getSysMenuBySysIdAndAuthorityId(sysMenuId, authorityId);
    }

    @Override
    public SysMenu getSysMenuBySysMenuId(Long sysMenuId) {
        return this.systemRepository.getSysMenuBySysMenuId(sysMenuId);
    }

    @Override
    public boolean updateSysMenu(SysMenu sysMenu) {
        return this.systemRepository.updateSysMenu(sysMenu);
    }

    @Override
    public SysMenu getSysMenuByAuthorityId(Long authorityId) {
        return this.systemRepository.getSysMenuByAuthorityId(authorityId);
    }

    @Override
    public SysMenu getSysMenuByParentId(Long parentId, Long authorityId) {
        return this.systemRepository.getSysMenuByParentId(parentId, authorityId);
    }

    @Override
    public Pageable<SysMenu> findSysMenus(int pageNo, int pageSize) {
        return this.systemRepository.findSysMenus(pageNo, pageSize);
    }

    @Override
    public List<Authority> findByRoleIds(List<Long> roleIds) {
        return this.systemRepository.findByRoleIds(roleIds);
    }

    @Override
    public int getOperatorLastId() {
        return this.systemRepository.getOperatorLastId();
    }

    @Override
    public int getRoleLastId() {
        return this.systemRepository.getRoleLastId();
    }

	@Override
	public List<Tree> getAuthorityTree() {
		List<Tree> treeList = new ArrayList<Tree>();
		List<Authority> authList = this.systemRepository.findAllAuthority();
		for(Authority auth : authList){
				boolean expanded = auth.getParentId()==1L?false:true;
				TreeNode treeNode = new TreeNode(auth.getId(), auth.getParentId(), auth.getName(),expanded);
				treeList.add(treeNode);
		}
		return treeList;
	}

	@Override
	public Boolean addOperatorRoleMap(Long operatorId, List<Long> roleIds) {
		boolean operatorRoleBool = false;
		if (operatorId != null && !"".equals(operatorId)) {
			operatorRoleBool = this.systemRepository.deleteOperatorRoleMapByOperatorId(operatorId);
			if (roleIds != null && roleIds.size()>0) {
					for (Long role:roleIds) {
						operatorRoleBool = saveOperatorRoleMap(role.toString(), operatorId.toString());
						if(operatorRoleBool==false){
							break;
						}
					}
			}
		} else {
			operatorRoleBool = this.systemRepository.deleteOperatorRoleMapByOperatorId(operatorId);
		}
		return operatorRoleBool;
	}
	
	private boolean saveOperatorRoleMap(String str, String operatorId) {
		OperatorRoleMap operatorRoleMap = new OperatorRoleMap();
		Operator operator = new Operator();
		operator.setId(Long.parseLong(operatorId));
		operatorRoleMap.setOperator(operator);
		Role role = new Role();
		role.setId(Long.parseLong(str));
		operatorRoleMap.setRole(role);
		return this.systemRepository.addOperatorRoleMap(operatorRoleMap);
	}

	@Override
	public ResultInfo assignAuthorityForRole(Long roleId, List<Long> authorityIds) throws SystemException {
		boolean bool  = false ;
		bool = this.systemRepository.deleteRoleAuthorityMapByRoleId(roleId);
		if(!bool){
			throw new SystemException(ResultInfos.SAVE_ERROR);
		}
		for (Long authId :authorityIds) {
			bool = saveRoleAuthorityMap(authId, roleId);
			if(!bool){
				throw new SystemException(ResultInfos.SAVE_ERROR);
			}
		}
		return ResultInfos.OK;
	}
	
	private boolean saveRoleAuthorityMap(Long authId, Long roleId) {
		RoleAuthorityMap roleAuthorityMap = new RoleAuthorityMap();
		Role role = new Role();
		role.setId(roleId);
		Authority authority = new Authority();
		authority.setId(authId);
		roleAuthorityMap.setAuthority(authority);
		roleAuthorityMap.setRole(role);
		return this.systemRepository.addRoleAuthorityMap(roleAuthorityMap);
	}

	@Override
	public List<RoleAuthorityMap> findAuthorityForDisplayTree(Long roleId) {
		List<RoleAuthorityMap>  ramList = this.systemRepository.findByRoleId(roleId);
		List<RoleAuthorityMap>  resultList = new ArrayList<RoleAuthorityMap>();
		//ids: 结点ID, pIds: 父节点ID
		List<Long> ids = new ArrayList<Long>();
		List<Long> pIds = new ArrayList<Long>();
		for(RoleAuthorityMap ram : ramList){
			Authority auth = this.systemRepository.getAuthorityById(ram.getAuthority().getId());
			ids.add(auth.getId());
			pIds.add(auth.getParentId());
		}
		//排除所有结点中是父节点的ID, 剩余叶子结点ID
		ids.removeAll(pIds);
		for(RoleAuthorityMap ram : ramList){
			if(ids.contains(ram.getAuthority().getId())){
				resultList.add(ram);
			}
		}
		return resultList;
	}

}
