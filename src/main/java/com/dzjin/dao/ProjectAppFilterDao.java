package com.dzjin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;

import com.dzjin.model.ProjectAppFilterRequestModel;
import com.liutianjun.pojo.Application;

/**
 * 
 * 项目名称：wankangyuan 
 * 类名称：ProjectAppFilterDao 
 * 类描述： 项目内应用筛选dao
 * 创建人：dzjin 
 * 创建时间：2018年7月15日 下午5:07:49 
 * 修改人：dzjin 
 * 修改时间：2018年7月15日 下午5:07:49 
 * 修改备注： 
 * @version 
 *
 */
public interface ProjectAppFilterDao {
	
	/**
	 * 筛选查询
	 * @param map
	 * @return
	 */
	@SelectProvider(type=ProjectAppSqlBuilder.class , method="buildSelectProjectAppByFilterCondition")
	@Results({
		@Result(property = "appName" , column = "app_name"),
		@Result(property = "appType" , column = "app_type"),
		@Result(property = "isAsync" , column = "is_async"),
		@Result(property = "isSaveSystem" , column = "is_save_system"),
		@Result(property = "isDisplay" , column = "is_display"),
		@Result(property = "computeNodeName" , column = "compute_node_name"),
		@Result(property = "paraAddress" , column = "para_address"),
		@Result(property = "resultAddress" , column = "result_address"),
		@Result(property = "appIntro" , column = "app_intro"),
		@Result(property = "fileResult" , column = "file_result"),
		@Result(property = "fileResultAddress" , column = "file_result_address"),
		@Result(property = "createTime" , column = "create_time"),
		@Result(property = "updateTime" , column = "update_time"),
		
	})
	public List<Application> selectProjectAppByFilterCondition(Map<String, Object> map);
	
	/**
	 * 过滤条件获得筛选值
	 * @param map
	 * @return
	 */
	@SelectProvider(type=ProjectAppSqlBuilder.class , method="buildAppColumnFilterSql")
	public List<String> selectAppDistinctColumnValue(Map<String, Object> map);


}
