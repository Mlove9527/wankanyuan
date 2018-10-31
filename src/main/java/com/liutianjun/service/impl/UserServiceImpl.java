package com.liutianjun.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liutianjun.dao.UserDao;
import com.liutianjun.pojo.Friends;
import com.liutianjun.pojo.User;
import com.liutianjun.pojo.UserQuery;
import com.liutianjun.pojo.UserQuery.Criteria;
import com.liutianjun.service.FriendsService;
import com.liutianjun.service.PasswordHelper;
import com.liutianjun.service.RoleService;
import com.liutianjun.service.UserService;

/**
 * 用户服务实现
 * @author Administrator
 *
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    
    @Autowired
    private RoleService roleService;
    
    @Autowired
    private PasswordHelper passwordHelper;
    
    @Autowired
    private FriendsService friendsService;
    
    /**
     * 注册新用户
     * <p>Title: insert</p>  
     * <p>Description: </p>  
     * @param record
     * @return
     */
    @Override
    public int insert(User record) {
        //加密密码
        passwordHelper.encryptPassword(record);
        //设置注册时间
        record.setCreateTime(new Date());
        //状态设为有效
        record.setStatus(1);
        record.setOrganizationId(0);
        
        record.setHeadimg("/wankangyuan/static/img/head.jpg");
        return userDao.insert(record);
    }

    /**
     * 根据主键删除用户
     * <p>Title: deleteByPrimaryKey</p>  
     * <p>Description: </p>  
     * @param id
     * @return
     */
    @Override
    public int deleteByPrimaryKey(Integer id) {
    	if(id == 1) {
			return 0;
		}
        return userDao.deleteByPrimaryKey(id);
    }

    /**
     * 更新用户信息
     * <p>Title: updateByPrimaryKey</p>  
     * <p>Description: </p>  
     * @param record
     * @return
     */
    @Override
    public int updateByPrimaryKey(User record) {
        return userDao.updateByPrimaryKeySelective(record);
    }

    /**
     * 修改密码
     * <p>Title: changePassword</p>  
     * <p>Description: </p>  
     * @param userId
     * @param newPassword
     */
    @Override
    public void changePassword(Integer userId, String newPassword) {
        User user = userDao.selectByPrimaryKey(userId);
        user.setPassword(newPassword);
        passwordHelper.encryptPassword(user);
        userDao.updateByPrimaryKey(user);
    }

    /**
     * 根据主键查找用户
     * <p>Title: selectByPrimaryKey</p>  
     * <p>Description: </p>  
     * @param id
     * @return
     */
    @Override
    public User selectByPrimaryKey(Integer id) {
        return userDao.selectByPrimaryKey(id);
    }

    /**
     * 根据用户名查找用户
     * <p>Title: selectByUsername</p>  
     * <p>Description: </p>  
     * @param username
     * @return
     */
    @Override
    public User selectByUsername(String username) {
        UserQuery example = new UserQuery();
        Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<User> list = userDao.selectByExample(example);
        if(list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 根据手机号查找用户
     * <p>Title: selectByPhone</p>  
     * <p>Description: </p>  
     * @param phone
     * @return
     */
	@Override
	public User selectByPhone(String phone) {
		UserQuery example = new UserQuery();
        Criteria criteria = example.createCriteria();
        criteria.andPhoneEqualTo(phone);
        List<User> list = userDao.selectByExample(example);
        if(list != null && list.size() > 0) {
            return list.get(0);
        }
		return null;
	}

	/**
	 * 根据邮箱查找用户
	 * <p>Title: selectByEmail</p>  
	 * <p>Description: </p>  
	 * @param email
	 * @return
	 */
	@Override
	public User selectByEmail(String email) {
		UserQuery example = new UserQuery();
        Criteria criteria = example.createCriteria();
        criteria.andEmailEqualTo(email);
        List<User> list = userDao.selectByExample(example);
        if(list != null && list.size() > 0) {
            return list.get(0);
        }
		return null;
	}


    /**
     * 查找所有用户
     * <p>Title: findAll</p>  
     * <p>Description: </p>  
     * @return
     */
    @Override
    public Map<String,Object> findAll() {
        UserQuery example = new UserQuery();
        int total = userDao.countByExample(example);
        List<User> list = userDao.selectByExample(example);
        Map<String,Object> map = new HashMap<>();
        map.put("list", list);
        map.put("total", total);
        return map;
    }

    /**
     * 查找所有用户，带分页
     * <p>Title: findAll</p>  
     * <p>Description: </p>  
     * @param page
     * @param rows
     * @param username
     * @return
     */
    @Override
    public Map<String,Object> findAll(Integer page, Integer rows, String username) {
        UserQuery example = new UserQuery();
        Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(username)) {
            criteria.andUsernameLike("%"+username+"%");
        }
        
        int total = userDao.countByExample(example);
        example.setPageNo(page);
        example.setPageSize(rows);
        
        List<User> list = userDao.selectByExample(example);
        Map<String,Object> map = new HashMap<>();
        map.put("list", list);
        map.put("total", total);
        return map;
    }

    /**
     * 根据用户名查找权限
     * <p>Title: findPermissions</p>  
     * <p>Description: </p>  
     * @param username
     * @return
     */
    @Override
    public Set<String> findPermissions(String username) {
        User user = selectByUsername(username);
        if(user == null || null == user.getRoleIds()) {
            return Collections.emptySet();
        }
        String roleIdsStr = user.getRoleIds();
        
        List<Integer> list = new ArrayList<>();
        
        String[] roleIdStrs = roleIdsStr.split(",");
        for(String roleIdStr : roleIdStrs) {
            if(StringUtils.isEmpty(roleIdStr)) {
                continue;
            }
            list.add(Integer.valueOf(roleIdStr));
        }
        
        return roleService.findPermissions(list.toArray(new Integer[0]));
    }

    /**
     * 根据用户名查找角色
     * <p>Title: findRoles</p>  
     * <p>Description: </p>  
     * @param username
     * @return
     */
    @Override
    public Set<String> findRoles(String username) {
        User user = selectByUsername(username);
        if(user == null || null == user.getRoleIds() ) {
            return Collections.emptySet();
        }
        
        String roleIdsStr = user.getRoleIds();
        List<Integer> list = new ArrayList<>();
        String[] roleIdStrs = roleIdsStr.split(",");
        for(String roleIdStr : roleIdStrs) {
            if(StringUtils.isEmpty(roleIdStr)) {
                continue;
            }
            list.add(Integer.valueOf(roleIdStr));
        }
        
        return roleService.findRoles(list.toArray(new Integer[0]));
    }

    /**
     * 批量禁用用户
     * <p>Title: forbidUserByIds</p>  
     * <p>Description: </p>  
     * @param ids
     * @param cmd 0-禁用 1-启用
     * @return
     */
	@Override
	public int forbidUserByIds(Integer[] ids, String cmd) {
		UserQuery example = new UserQuery();
        Criteria criteria = example.createCriteria();
        criteria.andIdIn(Arrays.asList(ids));
        User user = new User();
        user.setStatus(Integer.valueOf(cmd));
		return userDao.updateByExampleSelective(user, example);
	}

	/**
	 * 批量重置密码
	 * <p>Title: resetPasswordByIds</p>  
	 * <p>Description: </p>  
	 * @param ids
	 * @return
	 */
	@Override
	public int resetPasswordByIds(Integer[] ids) {
		int i = 0;
		for (Integer id : ids) {
			User user = userDao.selectByPrimaryKey(id);
			user.setPassword(user.getPhone());
			passwordHelper.encryptPassword(user);
	        i += userDao.updateByPrimaryKey(user);
		}
		return i;
	}

	/**
	 * 批量修改用户组织
	 * <p>Title: updateUserOrg</p>  
	 * <p>Description: </p>  
	 * @param organizationId
	 * @param ids
	 * @return
	 */
	@Override
	public int updateUserOrg(Integer organizationId, Integer[] ids) {
		if(null != ids && ids.length > 0) {
			UserQuery example = new UserQuery();
			Criteria criteria = example.createCriteria();
			criteria.andIdIn(Arrays.asList(ids));
			
			User user = new User();
			user.setOrganizationId(organizationId);
			
			int i = userDao.updateByExampleSelective(user, example);
			return i;
		}
        
		return 0;
	}

	/**
	 * 根据分组获取数量
	 * <p>Title: findAll</p>  
	 * <p>Description: </p>  
	 * @param organizationId
	 * @return
	 */
	@Override
	public List<User> findAll(Integer organizationId) {
		UserQuery example = new UserQuery();
		Criteria criteria = example.createCriteria();
		criteria.andOrganizationIdEqualTo(organizationId);
		return userDao.selectByExample(example);
	}

	/**
	 * 批量移除成员组ID
	 * <p>Title: removeOrgByIds</p>  
	 * <p>Description: </p>  
	 * @param ids
	 * @return
	 */
	@Override
	public int removeOrgByIds(Integer[] ids) {
		UserQuery example = new UserQuery();
		Criteria criteria = example.createCriteria();
		criteria.andIdIn(Arrays.asList(ids));
		User user = new User();
		user.setOrganizationId(0);
		
		return userDao.updateByExampleSelective(user, example);
	}

	/**
	 * 根据用户名获取列表
	 * <p>Title: findAll</p>  
	 * <p>Description: </p>  
	 * @param username
	 * @return
	 */
	@Override
	public List<User> findOrgAll(Integer isOrg, String username) {
		UserQuery example = new UserQuery();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameLike("%"+username+"%");
		if(-1 == isOrg) {
			criteria.andOrganizationIdNotEqualTo(0);
			//获取用户名
		    String name = (String)SecurityUtils.getSubject().getPrincipal();
		    //获取用户
		    User user = selectByUsername(name);
		    //获取好友id列表
		    List<Integer> friendIdList = new ArrayList<>();
		    List<Friends> friendsList = friendsService.findAllMyFriends(user.getId(),username,null);
		    for (Friends friends : friendsList) {
		    	friendIdList.add(friends.getFriendId());
			}
		    //排除自己ID
		    friendIdList.add(user.getId());
		    
		    //排除好友ID
		    criteria.andIdNotIn(friendIdList);
			
		}else {
			criteria.andOrganizationIdEqualTo(isOrg);
		}
		
		return userDao.selectByExample(example);
	}
	
	/**
	 * 获取所有组内成员
	 * <p>Title: findOrgAll</p>  
	 * <p>Description: </p>  
	 * @param username
	 * @return
	 */
	@Override
	public List<User> findOrgAll(String username) {
		UserQuery example = new UserQuery();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameLike("%"+username+"%");
		return userDao.selectByExample(example);
	}

	/*
	 * (non-Javadoc)
	 * @see com.liutianjun.service.UserService#selectUseridByusername(java.lang.String)
	 */
	@Override
	public int selectUseridByusername(String username) {
		int selectUseridByusername = userDao.selectUseridByusername(username);
		return selectUseridByusername;
	}
	

}
