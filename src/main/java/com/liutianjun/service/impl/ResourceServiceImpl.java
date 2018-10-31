package com.liutianjun.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liutianjun.dao.ResourceDao;
import com.liutianjun.pojo.Resource;
import com.liutianjun.pojo.ResourceQuery;
import com.liutianjun.pojo.ResourceQuery.Criteria;
import com.liutianjun.service.ResourceService;

/**
 * 资源服务
 * @author Administrator
 *
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceDao resourceDao;
    
    /**
     * 插入新资源
     * @Title: insert  
     * @Description:
     * @param record
     * @return 
     * @throws
     */
    @Override
    public int insert(Resource record) {
    	record.setType("button");
    	record.setParentIds(selectByPrimaryKey(record.getParentId()).getParentIds()+record.getParentId()+"/");
    	record.setCreateTime(new Date());
        return resourceDao.insert(record);
    }

    /**
     * 根据主键删除资源
     * @Title: deleteByPrimaryKey  
     * @Description:
     * @param id
     * @return 
     * @throws
     */
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return resourceDao.deleteByPrimaryKey(id);
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
		ResourceQuery example = new ResourceQuery();
		Criteria criteria = example.createCriteria();
		if(null == ids || ids.length == 0) {
			return 0;
		}
		criteria.andIdIn(Arrays.asList(ids));
		return resourceDao.deleteByExample(example);
	}

    /**
     * 更新资源信息
     * @Title: updateByPrimaryKey  
     * @Description:
     * @param record
     * @return 
     * @throws
     */
    @Override
    public int updateByPrimaryKey(Resource record) {
    	record.setParentIds(selectByPrimaryKey(record.getParentId()).getParentIds()+record.getParentId()+"/");
    	record.setUpdateTime(new Date());
        return resourceDao.updateByPrimaryKeySelective(record);
    }

    /**
     * 根据主键查找资源
     * @Title: selectByPrimaryKey  
     * @Description:
     * @param id
     * @return 
     * @throws
     */
    @Override
    public Resource selectByPrimaryKey(Integer id) {
        return resourceDao.selectByPrimaryKey(id);
    }

    /**
     * 查找所有资源
     * @Title: findAll  
     * @Description:
     * @return 
     * @throws
     */
    @Override
    public Map<String, Object> findAll() {
        ResourceQuery example = new ResourceQuery();
        int total = resourceDao.countByExample(example);
        List<Resource> list = resourceDao.selectByExample(example);
        
        Map<String,Object> map = new HashMap<>();
        map.put("total", total);
        map.put("list", list);
        
        return map;
    }

    /**
     * 查找所有资源，带分页查询
     * @Title: findAll  
     * @Description:
     * @param page
     * @param rows
     * @param name
     * @return 
     * @throws
     */
    @Override
    public Map<String, Object> findAll(Integer page, Integer rows, String name) {
        ResourceQuery example = new ResourceQuery();
        Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(name)) {
            criteria.andNameLike("%"+name+"%");
        }
        int total = resourceDao.countByExample(example);
        example.setPageNo(page);
        example.setPageSize(rows);
        List<Resource> list = resourceDao.selectByExample(example);
        
        Map<String,Object> map = new HashMap<>();
        map.put("total", total);
        map.put("list", list);
        
        return map;
    }

    @Override
    public Set<String> findPermissions(Set<Integer> resourceIds) {
        Set<String> permissions = new HashSet<>();
        for (Integer resourceId : resourceIds) {
            Resource resource = selectByPrimaryKey(resourceId);
            if(resource != null && !StringUtils.isEmpty(resource.getPermission()) && resource.getAvailable() == 1) {
                permissions.add(resource.getPermission());
            }
        }
        return permissions;
    }

    /**
     * 根据权限Set查找菜单列表
     * <p>Title: findMenus</p>  
     * <p>Description: </p>  
     * @param permissions
     * @return
     */
    @Override
    public List<Resource> findMenus(Set<String> permissions) {
        @SuppressWarnings("unchecked")
        List<Resource> allResources = (List<Resource>) findAll().get("list");
        List<Resource> menus = new ArrayList<Resource>();
        for (Resource resource : allResources) {
            if(resource.getParentId() == 0) {
                continue;
            }
            if(!resource.getType().equals("menu")) {
                continue;
            }
            if(!hasPermission(permissions, resource)) {
                continue;
            }
            menus.add(resource);
        }
        return menus;
    }
    
    /**
     * 判断是否有权限
     * @Title: hasPermission 
     * @param permissions
     * @param resource
     * @return 
     * boolean
     */
    private boolean hasPermission(Set<String> permissions, Resource resource) {
        if(StringUtils.isEmpty(resource.getPermission())) {
            return true;
        }
        for(String permission : permissions) {
            WildcardPermission p1 = new WildcardPermission(permission);
            WildcardPermission p2 = new WildcardPermission(resource.getPermission());
            if(p1.implies(p2) || p2.implies(p1)) {
                return true;
            }
        }
        return false;
    }

}