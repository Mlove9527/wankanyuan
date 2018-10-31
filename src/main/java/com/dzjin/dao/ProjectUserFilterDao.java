package com.dzjin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.SelectProvider;

import com.dzjin.model.ProjectUser;

/**
 * 
 * 项目名称：wankangyuan 
 * 类名称：ProjectUserFilterDao 
 * 类描述： 项目成员筛选dao
 * 创建人：dzjin 
 * 创建时间：2018年7月15日 下午3:11:35 
 * 修改人：dzjin 
 * 修改时间：2018年7月15日 下午3:11:35 
 * 修改备注： 
 * @version 
 *
 */
public interface ProjectUserFilterDao {
	
	/**
	 * 筛选查询
	 * @param map
	 * @return
	 */
	@SelectProvider(type=ProjectUserSqlBuilder.class , method="buildSelectProjectMembertByFilterCondition")
	public List<ProjectUser> selectProjectMemberByFilterCondition(Map<String, Object> map);
	
	/**
	 * 过滤条件获得筛选值
	 * @param map
	 * @return
	 */
	@SelectProvider(type=ProjectUserSqlBuilder.class , method="buildProjectMemberColumnFilterSql")
	public List<String> selectProjectMemberDistinctColumnValue(Map<String, Object> map);

}
