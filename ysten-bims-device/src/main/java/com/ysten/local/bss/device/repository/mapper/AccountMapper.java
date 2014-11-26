package com.ysten.local.bss.device.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.device.domain.Account;
import com.ysten.local.bss.device.domain.Account.State;

/**
 * AccountMapper接口
 * 
 * @fileName AccountMapper.java
 */
public interface AccountMapper {
    /**
     * 通过id查询账户信息
     * 
     * @param id
     * @return
     */
    Account getById(Long id);

    /**
     * 根据账号编码获取账号
     * 
     * @param code
     *            账号编码
     * @return
     */
    Account getByAccountCode(String code);

    /**
     * 通过id删除账户信息
     * 
     * @param id
     * @return
     */
    int deleteById(Long id);

    /**
     * 修改账户信息
     * 
     * @param account
     * @return
     */
    int update(Account account);

    /**
     * 新增账户信息
     * 
     * @param account
     * @return
     */
    int save(Account account);

    /**
     * 批量更新账号状态信息
     * 
     * @param ids
     *            待更新的ID
     * @param state
     *            更新的状态
     * @return
     */
    int updateState(@Param("ids") Long[] ids, @Param("state") State state);

    /**
     * 批量更新账号状态
     * 
     * @param state
     *            新状态
     * @param ids
     *            待修改的id集合
     * @return 影响的行数
     * 
     */
    int updateState(@Param("state") Account.State state, @Param("ids") List<Long> ids);

    /**
     * 通过用户id查询账户
     * 
     * @param customerId
     * @return
     */
    Account getAccountByCustomerId(@Param("customerId") String customerId);
}
