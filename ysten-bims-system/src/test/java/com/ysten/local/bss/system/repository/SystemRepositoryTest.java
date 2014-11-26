package com.ysten.local.bss.system.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ysten.local.bss.system.domain.Authority;
import com.ysten.local.bss.system.domain.Operator;
import com.ysten.local.bss.system.domain.OperatorRoleMap;
import com.ysten.local.bss.system.domain.Role;
import com.ysten.local.bss.system.repository.ISystemRepository;

/**
 * SystemRepository 单元测试
 * @author sunguangqi
 * @date 2011-06-22
 * @fileName SystemRepositoryTest.java
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="SystemRepositoryTest-context.xml")
public class SystemRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests{
    
    private static int index = 0 ;
    
    @Autowired
    private ISystemRepository systemRepository;
    
    private Operator addOperator(){
        Operator o = new Operator();
        o.setDisplayName("ysten"+index++);
        o.setEmail("sunguangqi@ysten.com");
        o.setLoginName("ysten"+index++);
        o.setPassword("ysten");
        this.systemRepository.addOperator(o);
        return o;
    }
    
    @Test
    public void testGetOperatorById(){
        Operator o = addOperator();
        Assert.assertNotNull(this.systemRepository.getOperatorById(o.getId()));
    }
    
    @Test
    public void testGetOperatorByLoginName(){
        Operator o = addOperator();
        Assert.assertNotNull(this.systemRepository.getOperatorByLoginName(o.getLoginName()));
    }
    
    @Test
    public void testFindOperators(){
        for(int i=0;i<24;i++){
            addOperator();
        }
        Assert.assertTrue(this.systemRepository.findOperators("ysten", "ysten", 1, 10).getData().size()>0);
    }
    
    @Test
    public void testFindAllOperator(){
        for(int i=0;i<24;i++){
            addOperator();
        }
        Assert.assertTrue(this.systemRepository.findAllOperator().size()>0);
    }
    
//    @Test
//    public void testDeleteOperator(){
//        Operator o = addOperator();
//        Assert.assertTrue(this.systemRepository.deleteOperator(o.getId())==1);
//    }
    
    @Test
    public void testUpdateOperator(){
        Operator o = addOperator();
        o.setDisplayName("albert"+index++);
        o.setEmail("albert@ysten.com");
        o.setLoginName("albert"+index++);
        o.setPassword("albert");
        this.systemRepository.updateOperator(o);
        Operator o1 = this.systemRepository.getOperatorById(o.getId());
        Assert.assertEquals(o1.getDisplayName(), o.getDisplayName());
        Assert.assertEquals(o1.getLoginName(), o.getLoginName());
    }
    
    private Role addRole(){
        Role r = new Role();
        r.setName("系统管理员"+index++);
        r.setDescription("测试数据");
        this.systemRepository.addRole(r);
        return r;
    }
    
    @Test
    public void testGetRoleById(){
        Role r = addRole();
        Assert.assertNotNull(this.systemRepository.getRoleById(r.getId()));
    }
    
    @Test
    public void testFindRoles(){
        for(int i=0;i<24;i++){
            addRole();
        }
        Assert.assertTrue(this.systemRepository.findRoles("测试数据",1, 12).getData().size()>0);
    }
    
    @Test
    public void testFindAllRole(){
        for(int i=0;i<24;i++){
            addRole();
        }
        Assert.assertTrue(this.systemRepository.findAllRole().size()>0);
    }
    
    @Test
    public void testDeleteRole(){
        Role r = addRole();
        Assert.assertTrue(this.systemRepository.deleteRole(r.getId()));
    }
    
    @Test
    public void testUpdateRole(){
        Role r = addRole();
        r.setName("易视腾");
        this.systemRepository.updateRole(r);
        Role r1 = this.systemRepository.getRoleById(r.getId());
        Assert.assertEquals(r1.getName(), r.getName());
    }
    
    private OperatorRoleMap addOperatorRoleMap(){
        OperatorRoleMap map = new OperatorRoleMap();
        Operator o = new Operator();
        o.setId(addOperator().getId());
        map.setOperator(o);
        Role r = new Role();
        r.setId(addRole().getId());
        map.setRole(r);
        this.systemRepository.addOperatorRoleMap(map);
        return map;
    }
    
    @Test
    public void testGetOperatorRoleMapByRoleId(){
        OperatorRoleMap map = addOperatorRoleMap();
        Assert.assertNotNull(this.systemRepository.getOperatorRoleMapByRoleId(map.getOperator().getId().toString(), map.getRole().getId().toString()));
    }
    
    @Test
    public void testDeleteOperatorRoleMapBy(){
        OperatorRoleMap map = addOperatorRoleMap();
        Assert.assertTrue(this.systemRepository.deleteOperatorRoleMapByOperatorId(map.getOperator().getId()));
    }
    
    private Authority addAuthority(){
        Authority a = new Authority();
        //a.setCode("add");
        a.setDescription("add");
        a.setName("add");
        this.systemRepository.addAuthority(a);
        return a;
    }
    
    @Test
    public void testGetAuthorityById(){
        Authority a = addAuthority();
        Assert.assertNotNull(this.systemRepository.getAuthorityById(a.getId()));
    }
    
    @Test
    public void testFindAuthoritys(){
        for(int i=0;i<24;i++){
            addAuthority();
        }
        Assert.assertTrue(this.systemRepository.findAuthoritys(1, 12).getData().size()>0);
    }
    
    @Test
    public void testFindAllAuthority(){
        for(int i=0;i<24;i++){
            addAuthority();
        }
        Assert.assertTrue(this.systemRepository.findAllAuthority().size()>0);
    }
    
    @Test
    public void testDeleteAuthority(){
        Authority a = addAuthority();
        Assert.assertTrue(this.systemRepository.deleteAuthority(a.getId()));
    }
    
    @Test
    public void testUpdateAuthority(){
        Authority a = addAuthority();
        a.setCode("albert");
        a.setDescription("albert");
        a.setName("albert");
        this.systemRepository.updateAuthority(a);
        Authority a1 = this.systemRepository.getAuthorityById(a.getId());
        Assert.assertEquals(a1.getCode(),a.getCode());
        Assert.assertEquals(a1.getName(), a.getName());
    }
    
    
//    private RoleAuthorityMap addRoleAuthorityMap(){
//        RoleAuthorityMap map = new RoleAuthorityMap();
//        Authority a = new Authority();
//        a.setId(addAuthority().getId());
//        Role r = new Role();
//        r.setId(addRole().getId());
//        map.setAuthority(a);
//        map.setRole(r);
//        this.systemRepository.addRoleAuthorityMap(map);
//        return map;
//    }
}
