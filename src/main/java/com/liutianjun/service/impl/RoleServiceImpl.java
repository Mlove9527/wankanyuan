package com.liutianjun.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liutianjun.dao.RoleDao;
import com.liutianjun.pojo.Role;
import com.liutianjun.pojo.RoleQuery;
import com.liutianjun.pojo.RoleQuery.Criteria;
import com.liutianjun.service.ResourceService;
import com.liutianjun.service.RoleService;

/**
 * 角色服务实现
 * @author Administrator
 *
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;
    
    @Autowired
    private ResourceService resourceService;
    
    @Override
    public int insert(Role record) {
    	record.setCreateTime(new Date());
        return roleDao.insert(record);
    }

    /**
     * 根据主键删除角色
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
        return roleDao.deleteByPrimaryKey(id);
    }
    
    /**
     * 批量删除
     * <p>Title: deleteByIds</p>  
     * <p>Description: </p>  
     * @param ids
     * @return
     */
	@Override
	public int deleteByIds(Integer[] ids) {
		if(null == ids || ids.length == 0) {
			return 0;
		}
		if(Arrays.asList(ids).contains(1)) {
			return 0;
		}
		RoleQuery example = new RoleQuery();
		Criteria criteria = example.createCriteria();
		criteria.andIdIn(Arrays.asList(ids));
		return roleDao.deleteByExample(example);
	}


    /**
     * 根据主键修改角色
     * <p>Title: updateByPrimaryKey</p>  
     * <p>Description: </p>  
     * @param record
     * @return
     */
    @Override
    public int updateByPrimaryKey(Role record) {
    	record.setUpdateTime(new Date());
        return roleDao.updateByPrimaryKeySelective(record);
    }

    /**
     * 根据主键查找角色
     * <p>Title: selectByPrimaryKey</p>  
     * <p>Description: </p>  
     * @param id
     * @return
     */
    @Override
    public Role selectByPrimaryKey(Integer id) {
        return roleDao.selectByPrimaryKey(id);
    }

    /**
     * 查找全部角色
     * <p>Title: findAll</p>  
     * <p>Description: </p>  
     * @return
     */
    @Override
    public Map<String, Object> findAll() {
        RoleQuery example = new RoleQuery();
        int total = roleDao.countByExample(example);
        List<Role> list = roleDao.selectByExample(example);
        
        Map<String,Object> map = new HashMap<>();
        map.put("total", total);
        map.put("list", list);
        return map;
    }

    /**
     * 查找全部角色带分页
     * <p>Title: findAll</p>  
     * <p>Description: </p>  
     * @param page
     * @param rows
     * @param role
     * @return
     */
    @Override
    public Map<String, Object> findAll(Integer page, Integer rows, String role) {
        RoleQuery example = new RoleQuery();
        Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(role)) {
            criteria.andRoleLike("%"+role+"%");
        }
        int total = roleDao.countByExample(example);
        
        example.setPageNo(page);
        example.setPageSize(rows);
        
        List<Role> list = roleDao.selectByExample(example);
        
        Map<String,Object> map = new HashMap<>();
        map.put("total", total);
        map.put("list", list);
        return map;
    }

    /**
     * 根据角色ids查找权限
     * <p>Title: findPermissions</p>  
     * <p>Description: </p>  
     * @param roleIds
     * @return
     */
    @Override
    public Set<String> findPermissions(Integer[] roleIds) {
        Set<Integer> ResourceIds = new HashSet<>();
        for (Integer roleId : roleIds) {
            Role role = selectByPrimaryKey(roleId);
            if(role != null) {
                String resourceIds = role.getResourceIds();
                String[] resourceIdc = resourceIds.split(",");
                for (String resourceId : resourceIdc) {
                    if(StringUtils.isEmpty(resourceId)) {
                        continue;
                    }
                    ResourceIds.add(Integer.valueOf(resourceId));
                }
            }
        }
        return resourceService.findPermissions(ResourceIds);
    }

    /**
     * 根据角色ids查找角色Set
     * <p>Title: findRoles</p>  
     * <p>Description: </p>  
     * @param roleIds
     * @return
     */
    @Override
    public Set<String> findRoles(Integer... roleIds) {
        Set<String> roles = new HashSet<String>();
        for(Integer roleId : roleIds) {
            Role role = selectByPrimaryKey(roleId);
            if(role != null) {
                roles.add(role.getRole());
            }
        }
        return roles;
    }


}