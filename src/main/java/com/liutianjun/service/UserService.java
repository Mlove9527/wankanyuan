package com.liutianjun.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.liutianjun.pojo.User;

/**
 * 用户服务
 * @author Administrator
 *
 */
public interface UserService {

    //添加新用户
    int insert(User record);
    
    //根据主键删除用户
    int deleteByPrimaryKey(Integer id);
    
    //更新用户信息
    int updateByPrimaryKey(User record);
    
    //修改密码
    void changePassword(Integer userId, String newPassword);
    
    //根据主键查找用户
    User selectByPrimaryKey(Integer id);
    
    //根据用户名查找用户
    User selectByUsername(String username);
    
    //根据手机号查找用户
    User selectByPhone(String phone);
    
    //根据邮箱查找用户
    User selectByEmail(String email);
    
    //查找所有用户
    Map<String,Object> findAll();
    
    //查找所有用户，带分页查询
    Map<String,Object> findAll(Integer page, Integer rows, String username);
    
    //根据用户名查找其权限
    Set<String> findPermissions(String username);

    //根据用户名查找其角色
    Set<String> findRoles(String username);
    
    //批量禁用
	int forbidUserByIds(Integer[] ids, String cmd);

	//批量重置密码
	int resetPasswordByIds(Integer[] ids);
	
	//修改用户组织结构
	int updateUserOrg(Integer organizationId,Integer[] ids);
	
	//根据分组获取用户
    List<User> findAll(Integer organizationId);
    
    //根据用户名获取用户
    List<User> findOrgAll(Integer isOrg, String username);
    
    //获取所有组内成员
    List<User> findOrgAll(String username);

    //批量移除成员组ID
	int removeOrgByIds(Integer[] ids);
	//获取用户id
	int selectUseridByusername(String username);
}
