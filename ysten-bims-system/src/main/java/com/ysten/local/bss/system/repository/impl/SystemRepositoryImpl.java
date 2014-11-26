package com.ysten.local.bss.system.repository.impl;


import java.util.ArrayList;
import java.util.List;

import com.ysten.local.bss.system.domain.*;
import com.ysten.local.bss.system.repository.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysten.local.bss.system.repository.ISystemRepository;
import com.ysten.utils.page.Pageable;

 
@Repository
public class SystemRepositoryImpl implements ISystemRepository {
	
	@Autowired
	private OperatorMapper operatorMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private AuthorityMapper authorityMapper;
	@Autowired
	private OperatorRoleMapMapper operatorRoleMapMapper;
	@Autowired
	private RoleAuthorityMapMapper roleAuthorityMapMapper;
	@Autowired
	private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysVersionMapper sysVersionMapper;
	
	@Override
	public boolean addOperator(Operator operator) {
		  return 1 == this.operatorMapper.save(operator);
	}

	@Override
	public boolean updateOperator(Operator operator) {
		return 1 == this.operatorMapper.update(operator);
	}

	@Override
	public boolean deleteOperator(Long id) {
		  return 0 <= this.operatorMapper.delete(id);
	}

	@Override
	public Pageable<Operator> findOperators(String operatorName,String loginName,int pageNo, int pageSize) {
        List<Operator> operatorList = this.operatorMapper.findOperators(operatorName,loginName,pageNo, pageSize);
        int total = this.operatorMapper.getCountByName(operatorName,loginName);
        return new Pageable<Operator>().instanceByPageNo(operatorList, total, pageNo, pageSize);
	}

    @Override
    public Pageable<SysVersion> findSysVersions(String versionId, String versionName, Integer pageNo, Integer pageSize) {
        List<SysVersion> sysVersionList = this.sysVersionMapper.findSysVersions(versionId,versionName,pageNo, pageSize);
        int total = this.operatorMapper.getCountByName(versionId, versionName);
        return new Pageable<SysVersion>().instanceByPageNo(sysVersionList, total, pageNo, pageSize);
    }

    @Override
	public Operator getOperatorById(Long id) {
		return this.operatorMapper.get(id);
	}

	@Override
	public boolean addRole(Role role) {
		 return 1 == this.roleMapper.save(role);
	}

	@Override
	public boolean updateRole(Role role) {
		return 1 == this.roleMapper.update(role);
	}

	@Override
	public boolean deleteRole(Long id) {
		  return 0 <= this.roleMapper.delete(id);
	}

	@Override
	public Pageable<Role> findRoles(String roleName,int pageNo, int pageSize) {
        List<Role> roleList = this.roleMapper.findRoles(roleName,pageNo, pageSize);
        int total = this.roleMapper.getCountByCondition(roleName);
        return new Pageable<Role>().instanceByPageNo(roleList, total, pageNo, pageSize);
	}

	@Override
	public Role getRoleById(Long id) {
		return this.roleMapper.get(id);
	}

	@Override
	public boolean addAuthority(Authority authority) {
		  return 1 == this.authorityMapper.save(authority);
	}

	@Override
	public boolean updateAuthority(Authority authority) {
		return 1 == this.authorityMapper.update(authority);
	}

	@Override
	public boolean deleteAuthority(Long id) {
		 return 0 <= this.authorityMapper.delete(id);
	}
	
	@Override
	public boolean deleteAuthorityByIds(List<Long> ids) {
		 boolean bool = ids.size()==this.authorityMapper.deleteByIds(ids);
		 return bool;
	}

	@Override
	public Pageable<Authority> findAuthoritys(int pageNo, int pageSize) {
        List<Authority> authorityList = this.authorityMapper.findAuthoritys(pageNo, pageSize);
        int total = this.authorityMapper.getCount();
        return new Pageable<Authority>().instanceByPageNo(authorityList, total, pageNo, pageSize);
	}

	@Override
	public Authority getAuthorityById(Long id) {
		return this.authorityMapper.get(id);
	}

	@Override
	public Operator getOperatorByLoginName(String loginName) {
		return this.operatorMapper.getOperatorByLoginName(loginName);
	}

	@Override
	public List<Operator> findAllOperator() {
		return this.operatorMapper.findAll();
	}

	@Override
	public List<OperatorRoleMap> findByOperatorId(Long operatorId) {
		return this.operatorRoleMapMapper.findByOperatorId(operatorId);
	}

	@Override
	public List<Role> findAllRole() {
		return this.roleMapper.findAll();
	}

	@Override
	public boolean addOperatorRoleMap(OperatorRoleMap operatorRoleMap) {
		 return 1 == this.operatorRoleMapMapper.save(operatorRoleMap);
	}

	@Override
	public OperatorRoleMap getOperatorRoleMapByRoleId(String operatorId,
			String roleId) {
		return this.operatorRoleMapMapper.getOperatorRoleMapByRoleId(operatorId,roleId);
	}

	@Override
	public boolean deleteOperatorRoleMapByOperatorId(Long operatorId) {
		  return 0 <= this.operatorRoleMapMapper.deleteOperatorRoleMapByOperatorId(operatorId);
	}

	@Override
	public List<Authority> findAllAuthority() {
		return this.authorityMapper.findAll();
	}

	@Override
	public List<RoleAuthorityMap> findByRoleId(Long roleId) {
		return this.roleAuthorityMapMapper.findByRoleId(roleId);
	}

	@Override
	public boolean deleteRoleAuthorityMapByRoleId(Long roleId) {
		 return 0 <= this.roleAuthorityMapMapper.deleteRoleAuthorityMapByRoleId(roleId);
	}

	@Override
	public boolean addRoleAuthorityMap(RoleAuthorityMap roleAuthorityMap) {
		  return 0 <= this.roleAuthorityMapMapper.save(roleAuthorityMap);
	}

	@Override
	public List<SysMenu> findSysMenus() {
		return this.sysMenuMapper.findAll();
	}

	@Override
	public SysMenu getSysMenuBySysIdAndAuthorityId(String sysMenuId,
			String authorityId) {
		return this.sysMenuMapper.getSysMenuBySysIdAndAuthorityId(sysMenuId, authorityId);
	}

	@Override
	public SysMenu getSysMenuBySysMenuId(Long sysMenuId) {
		return this.sysMenuMapper.get(sysMenuId);
	}

	@Override
	public boolean  updateSysMenu(SysMenu sysMenu) {
		return 1 == this.sysMenuMapper.update(sysMenu);
	}

	@Override
	public SysMenu getSysMenuByAuthorityId(Long authorityId) {
		return this.sysMenuMapper.getSysMenuByAuthorityId(authorityId);
	}

	@Override
	public SysMenu getSysMenuByParentId(Long parentId,Long authorityId) {
		return this.sysMenuMapper.getSysMenuByParentId(parentId,authorityId);
	}

    @Override
    public Pageable<SysMenu> findSysMenus(int pageNo, int pageSize) {
        List<SysMenu> sysMenuList = this.sysMenuMapper.findSysMenus(pageNo, pageSize);
        int total = this.sysMenuMapper.getCount();
        return new Pageable<SysMenu>().instanceByPageNo(sysMenuList, total, pageNo, pageSize);
    }

    @Override
    public List<Authority> findByRoleIds(List<Long> roleIds) {
        return this.authorityMapper.findByRoleIds(roleIds);
    }

	@Override
	public int getOperatorLastId() {
		 return this.operatorMapper.getOperatorLastId();
	}

	@Override
	public int getRoleLastId() {
		return this.roleMapper.getRoleLastId();
	}

	@Override
	public List<Authority> findRelatedAuthorities(List<Authority> condition) {
		if(condition!=null&&condition.size()>0){
			List<Authority> subResult= new ArrayList<Authority>();
			for(Authority authority : condition){
				List<Authority> auth= new ArrayList<Authority>();
				auth = this.authorityMapper.findAuthoritiesByParentId(authority.getId());
				subResult.addAll(auth);
			}
			subResult.addAll(this.findRelatedAuthorities(subResult));
			return subResult;
		}else{
			return new ArrayList<Authority>();
		}
	}

	@Override
	public List<Authority> findAuthoritiesByParentId(Long parentId) {
		return this.authorityMapper.findAuthoritiesByParentId(parentId);
	}
	
	
	
}
