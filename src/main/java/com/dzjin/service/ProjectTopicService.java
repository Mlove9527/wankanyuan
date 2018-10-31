package com.dzjin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dzjin.dao.ProjectTopicDao;
import com.dzjin.model.ProjectTopic;
import com.dzjin.model.ProjectTopicFollow;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * 项目名称：wankangyuan 
 * 类名称：ProjectTopicService 
 * 类描述： 项目内主题service
 * 创建人：dzjin 
 * 创建时间：2018年6月14日 下午1:47:26 
 * 修改人：dzjin 
 * 修改时间：2018年6月14日 下午1:47:26 
 * 修改备注： 
 * @version 
 *
 */
@Service
public class ProjectTopicService {
	
	@Autowired
	ProjectTopicDao projectTopicDao;

	public int insertProjectTopic(ProjectTopic projectTopic){
		return projectTopicDao.insertProjectTopic(projectTopic);
	}
	
	public ProjectTopic getProjectTopicById(Integer id){
		return projectTopicDao.getProjectTopicById(id);
	}
	
	public Map<String, Object> selectProjectTopic(Integer page , Integer strip , Integer project_id , 
			String timeRadio , String followRadio , String lookRadio){
		Map<String, Object> map = new HashMap<String , Object>();
		PageHelper.startPage(page, strip);
		List<ProjectTopic> projectTopics = projectTopicDao.selectProjectTopic(
				project_id , timeRadio , followRadio , lookRadio);
		PageInfo<ProjectTopic> pageInfo = new PageInfo<>(projectTopics);
		map.put("list", projectTopics);
		map.put("total", pageInfo.getTotal());
		return map;
	}
	
	public int deleteProjectTopic(Integer project_topic_id){
		return projectTopicDao.deleteProjectTopic(project_topic_id);
	}
	
	//更新浏览或者回复数量
	public int upLookNum(@Param("id")Integer id){
		return projectTopicDao.upLookNum(id);
	}
	public int upFollowNum(@Param("id")Integer id){
		return projectTopicDao.upFollowNum(id);
	}
	public int downFollowNum(@Param("id")Integer id){
		return projectTopicDao.downFollowNum(id);
	}
	public int updateLastDatetime(ProjectTopic projectTopic){
		return projectTopicDao.updateLastDatetime(projectTopic);
	}
	
	
	
	public int insertProjectTopicFollow(ProjectTopicFollow projectTopicFollow){
		return projectTopicDao.insertProjectTopicFollow(projectTopicFollow);
	}
	
	public Map<String, Object> selectProjectTopicFollow(Integer project_topic_id , Integer page , Integer strip){
		PageHelper.startPage(page, strip);
		List<ProjectTopicFollow> projectTopicFollows = projectTopicDao.selectProjectTopicFollow(project_topic_id);
		PageInfo<ProjectTopicFollow> pageInfo = new PageInfo<ProjectTopicFollow>(projectTopicFollows);
		Map<String, Object> map = new HashMap<String , Object>();
		map.put("list", projectTopicFollows);
		map.put("total", pageInfo.getTotal());
		return map;
	}
	
	/**
	 * 删除主题回复消息
	 * @param id
	 * @return
	 */
	public int deleteProjectTopicFollow(Integer id){
		return projectTopicDao.deleteProjectTopicFollow(id);
	}
	
}
