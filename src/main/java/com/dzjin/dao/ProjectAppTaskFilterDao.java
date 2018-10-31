package com.dzjin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.SelectProvider;

import com.dzjin.model.ProjectAppTask;

/**
 * 
 * 项目名称：wankangyuan 
 * 类名称：ProjectAppTaskFilterDao 
 * 类描述： 项目内应用运行结果筛选dao
 * 创建人：dzjin 
 * 创建时间：2018年7月14日 下午10:22:50 
 * 修改人：dzjin 
 * 修改时间：2018年7月14日 下午10:22:50 
 * 修改备注： 
 * @version 
 *
 */
public interface ProjectAppTaskFilterDao {
	
	/**
	 * 筛选查询
	 * @param map
	 * @return
	 */
	@SelectProvider(type=ProjectAppTaskSqlBuilder.class , method="buildSelectProjectAppTaskByFilterCondition")
	public List<ProjectAppTask> selectProjectAppTaskByFilterCondition(Map<String, Object> map);
	
	/**
	 * 过滤条件获得筛选值
	 * @param map
	 * @return
	 */
	@SelectProvider(type=ProjectAppTaskSqlBuilder.class , method="buildAppTaskColumnFilterSql")
	public List<String> selectAppTaskDistinctColumnValue(Map<String, Object> map);

}
