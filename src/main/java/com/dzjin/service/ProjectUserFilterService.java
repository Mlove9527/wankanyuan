package com.dzjin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dzjin.dao.ProjectUserFilterDao;
import com.dzjin.model.ProjectUser;
import com.dzjin.model.QueryCondition;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * 项目名称：wankangyuan 
 * 类名称：ProjectMemberFilterService 
 * 类描述： 项目内成员筛选service
 * 创建人：dzjin 
 * 创建时间：2018年7月15日 下午3:12:26 
 * 修改人：dzjin 
 * 修改时间：2018年7月15日 下午3:12:26 
 * 修改备注： 
 * @version 
 *
 */
@Service
public class ProjectUserFilterService {
	
	@Autowired
	ProjectUserFilterDao projectUserFilterDao;
	
	/**
	 * 根据过滤条件获得筛选值
	 * @param columnName
	 * @param project_id
	 * @param filter
	 * @return
	 */
	public List<String> selectProjectMemberDistinctColumnValue(String columnName , Integer project_id , String filter){
		
		Map<String, Object> map = new HashMap<String , Object>();
		map.put("columnName", columnName);
		map.put("project_id", String.valueOf(project_id));
		map.put("filter", filter);
		
		switch(columnName){
		
			case "bind_date_time":
				return projectUserFilterDao.selectProjectMemberDistinctColumnValue(map);
			case "username":
				return projectUserFilterDao.selectProjectMemberDistinctColumnValue(map);
			case "linkman":
				return projectUserFilterDao.selectProjectMemberDistinctColumnValue(map);
			case "rolename":
				return projectUserFilterDao.selectProjectMemberDistinctColumnValue(map);
			default:
				return null;
		}
	}
	
	/**
	 * 筛选数据
	 * @param page
	 * @param strip
	 * @param searchWord
	 * @param projectQueryCondition
	 * @param project_id
	 * @return
	 */
	public Map<String, Object> selectProjectMemberByFilterCondition(
			Integer page , Integer strip , 
			String searchWord ,QueryCondition projectQueryCondition , Integer project_id){
		PageHelper.startPage(page, strip);
		Map<String, Object> map = new HashMap<String , Object>();
		//将相关的筛选条件放入到map中，方便动态构造sql语句的时候使用
		map.put("project_id", String.valueOf(project_id));//项目ID
		map.put("searchWord", searchWord);//搜索关键字，对应前端右上角的搜索框中的值
		map.put("columnName", projectQueryCondition.getColumnName());//筛选的字段名
		map.put("order", projectQueryCondition.getOrder());//筛选字段排序方式
		map.put("filter", projectQueryCondition.getFilter());//筛选字段过滤条件
		map.put("values", projectQueryCondition.getStrings());//筛选值，list形式
		
		List<ProjectUser> projectUsers = projectUserFilterDao.selectProjectMemberByFilterCondition(map);
		PageInfo<ProjectUser> pageInfo = new PageInfo<ProjectUser>(projectUsers);
		Map<String, Object> result = new HashMap<String , Object>();
		result.put("total", pageInfo.getTotal());
		result.put("list", projectUsers);
		return result;
		
	}

}
