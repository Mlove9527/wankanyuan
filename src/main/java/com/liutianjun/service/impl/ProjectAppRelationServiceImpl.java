package com.liutianjun.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liutianjun.dao.ProjectAppRelationDao;
import com.liutianjun.pojo.ProjectAppRelation;
import com.liutianjun.pojo.ProjectAppRelationQuery;
import com.liutianjun.pojo.ProjectAppRelationQuery.Criteria;
import com.liutianjun.service.ProjectAppRelationService;

/**
 * 项目应用关系业务实现
 * @Title: ProjectAppRelationServiceImpl.java  
 * @Package com.liutianjun.service.impl  
 * @Description: TODO
 * @author LiuTianJun  
 * @date 2018年5月17日  
 * @version V1.0
 */
@Service
public class ProjectAppRelationServiceImpl implements ProjectAppRelationService {

	@Autowired
	private ProjectAppRelationDao projectAppRelationDao;
	
	/**
	 * 批量添加项目应用关系
	 * <p>Title: insert</p>  
	 * <p>Description: </p>  
	 * @param projectId
	 * @param appIds
	 * @return
	 */
	@Override
	public int insert(Integer projectId, Integer[] appIds) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ProjectAppRelation projectAppRelation = new ProjectAppRelation();
		int i = 0;
		for (Integer appId : appIds) {
			projectAppRelation.setProjectId(projectId);
			projectAppRelation.setAppId(appId);
			projectAppRelation.setBindDatetime(fmt.format(new Date()));
			i += projectAppRelationDao.insert(projectAppRelation);
		}
		return i;
	}

	/**
	 * 批量删除项目应用关系
	 * <p>Title: delete</p>  
	 * <p>Description: </p>  
	 * @param projectId
	 * @param appIds
	 * @return
	 */
	@Override
	public int delete(Integer projectId, Integer[] appIds) {
		ProjectAppRelationQuery example = new ProjectAppRelationQuery();
		Criteria criteria = example.createCriteria();
		criteria.andProjectIdEqualTo(projectId);
		criteria.andAppIdIn(Arrays.asList(appIds));
		return projectAppRelationDao.deleteByExample(example);
	}

	/**
	 * 查询项目下的应用Ids
	 * <p>Title: findByProjectId</p>  
	 * <p>Description: </p>  
	 * @param projectId
	 * @return
	 */
	@Override
	public List<Integer> findByProjectId(Integer projectId) {
		ProjectAppRelationQuery example = new ProjectAppRelationQuery();
		Criteria criteria = example.createCriteria();
		criteria.andProjectIdEqualTo(projectId);
		List<ProjectAppRelation> oldList = projectAppRelationDao.selectByExample(example);
		List<Integer> list = new ArrayList<>();
		for (ProjectAppRelation projectAppRelation : oldList) {
			list.add(projectAppRelation.getAppId());
		}
		return list;
	}

}
