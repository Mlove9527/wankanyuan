package com.liutianjun.service;

import java.util.List;
import java.util.Map;

import com.liutianjun.pojo.Organization;

/**
 * 组织结构
 * @Title: OrganizationService.java  
 * @Package com.liutianjun.service  
 * @Description: TODO
 * @author LiuTianJun  
 * @date 2018年6月1日  
 * @version V1.0
 */
public interface OrganizationService {

	//添加新组织结构
	int addNewOrg(Organization record);
	
	//添加新组
	int addNewGroup(Integer parentId,String organizationName);
	
	//修改组
	int undateGroup(Organization record);
	
	//删除组
	int deleteGroupById(Integer id);
	
	//根据父id显示组列表
	List<Organization> findOrgList(Integer parentId);
	
	List<Organization> findOrgList(Integer parentId,List<Integer> list);
	
	Map<String,Object> findOrgList(Integer page,Integer rows,String organizationName);
	
	//处理添加组织结构请求
	int dealAddOrgRequest(Integer[] ids, Integer cmd);
	
	//根据id获取组织
	Organization selectByPrimaryKey(Integer id);
	
	//根据用户名获取组织结构IDS
	List<Integer> fingOrgIds();

	
}
