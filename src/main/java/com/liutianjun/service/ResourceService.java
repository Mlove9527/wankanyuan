package com.liutianjun.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.liutianjun.pojo.Resource;

/**
 * 资源服务
 * @author Administrator
 *
 */
public interface ResourceService {

    //添加新资源
    int insert(Resource record);
    
    //根据主键删除资源
    int deleteByPrimaryKey(Integer id);
    
    //更新资源信息
    int updateByPrimaryKey(Resource record);
    
    //根据主键查找资源
    Resource selectByPrimaryKey(Integer id);
    
    //查找所有资源
    Map<String,Object> findAll();
    
    //查找所有资源,带分页搜索
    Map<String,Object> findAll(Integer page, Integer rows, String name);
    
    //得到资源对应的权限字符串
    Set<String> findPermissions(Set<Integer> sysResourceIds);
    
    //根据用户权限得到菜单
    List<Resource> findMenus(Set<String> permissions);

    //批量删除
	int deleteByIds(Integer[] ids);
    
}
