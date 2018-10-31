package com.liutianjun.service;

import java.util.Map;
import java.util.Set;

import com.liutianjun.pojo.Role;

/**
 * 角色服务
 * @author Administrator
 *
 */
public interface RoleService {

    //增加新角色
    int insert(Role record);
    
    //根据主键删除角色
    int deleteByPrimaryKey(Integer id);
    
    //修改角色信息
    int updateByPrimaryKey(Role record);
    
    //根据主键查找角色
    Role selectByPrimaryKey(Integer id);
    
    //查找所有角色
    Map<String,Object> findAll();
    
    //查找所有角色,带分页查询
    Map<String,Object> findAll(Integer page, Integer rows, String role);
    
    //根据角色编号得到权限字符串列表
    Set<String> findPermissions(Integer[] roleIds);
    
    //根据角色编号得到角色标识符列表
    Set<String> findRoles(Integer... roleIds);

    //批量删除
	int deleteByIds(Integer[] ids);
    
}
