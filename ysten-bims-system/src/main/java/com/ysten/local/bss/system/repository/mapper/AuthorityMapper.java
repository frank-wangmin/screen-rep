package com.ysten.local.bss.system.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.system.domain.Authority;

/**
 * 
 * @author xuyesheng
 * @date 2011-12.23
 * 
 */
public interface AuthorityMapper extends BaseMapper<Authority, Long>{
	/**
     * 查询系统操作员信息(支持分页)
     * @param pageNo
     *            起始页
     * @param pageSize
     *            每页显示多少条
     * @return List<Authority>
     * 
     */
    List<Authority> findAuthoritys(@Param("currentPage")int currentPage, @Param("pageSize")int pageSize);
    
    /**
     * 获取角色的权限信息
     * @param roleIds
     *          角色Ids
     * @return
     */
    List<Authority> findByRoleIds(@Param("roleIds")List<Long> roleIds);

    /**
     * 查询父节点下的子节点
     * @param id
     * @return
     */
	List<Authority> findAuthoritiesByParentId(@Param("parentId")Long id);

	/**
	 * 批量删除权限
	 * @param ids
	 * @return
	 */
	int deleteByIds(@Param("ids")List<Long> ids);
   
     
}