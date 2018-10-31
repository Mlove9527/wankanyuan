package com.liutianjun.service;

import java.util.List;

/**
 * 项目应用关系
 * @Title: ProjectAppRelationService.java  
 * @Package com.liutianjun.service  
 * @Description: TODO
 * @author LiuTianJun  
 * @date 2018年5月17日  
 * @version V1.0
 */
public interface ProjectAppRelationService {

	//添加项目应用关系
	int insert(Integer projectId, Integer[] appIds);
	
	//删除项目应用关系
	int delete(Integer projectId, Integer[] appIds);
	
	//查询项目下的应用列表
	List<Integer> findByProjectId(Integer projectId);
	
}
