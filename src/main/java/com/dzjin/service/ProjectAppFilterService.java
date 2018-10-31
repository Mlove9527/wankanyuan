package com.dzjin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dzjin.dao.ProjectAppFilterDao;
import com.dzjin.model.ProjectAppFilterRequestModel;
import com.dzjin.model.QueryCondition;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liutianjun.pojo.Application;

/**
 * 
 * 项目名称：wankangyuan 
 * 类名称：ProjectAppFilterService 
 * 类描述： 项目内应用筛选service
 * 创建人：dzjin 
 * 创建时间：2018年7月15日 下午5:09:05 
 * 修改人：dzjin 
 * 修改时间：2018年7月15日 下午5:09:05 
 * 修改备注： 
 * @version 
 *
 */
@Service
public class ProjectAppFilterService {
	
	@Autowired
	ProjectAppFilterDao projectAppFilterDao;
	
	/**
	 * 根据过滤条件获得筛选值
	 * @param columnName
	 * @param project_id
	 * @param filter
	 * @return
	 */
	public List<String> selectAppDistinctColumnValue(String columnName , Integer project_id , String filter,ProjectAppFilterRequestModel projectAppFilterRequest){
		
		Map<String, Object> map = new HashMap<String , Object>();
		map.put("columnName", columnName);
		map.put("project_id", String.valueOf(project_id));
		map.put("filter", filter);
		
		map.put("isDisplay", projectAppFilterRequest.getIsDisplay());
		map.put("isAsync", projectAppFilterRequest.getIsAsync());
		map.put("appName", projectAppFilterRequest.getAppName());
		map.put("creator", projectAppFilterRequest.getCreator());
		map.put("createTime", projectAppFilterRequest.getCreateTime());
		map.put("keywords", projectAppFilterRequest.getKeywords());
		map.put("appIntro", projectAppFilterRequest.getAppIntro());
		
		switch(columnName){
			case "isDisplay":
				List<String> strings = new ArrayList<String>();
				/*strings.add("公开");
				strings.add("私有");*/
				map.put("columnName", "is_display");
				List<String> isDisplays=projectAppFilterDao.selectAppDistinctColumnValue(map);
				if(isDisplays.contains(null)) {
					strings.add(null);
				}
				if(isDisplays.contains("1")) {
					strings.add("公开");
				}
				if(isDisplays.contains("0")) {
					strings.add("私有");
				}
				return strings;
			case "isAsync":
				List<String> strings1 = new ArrayList<String>();
				/*strings1.add("异步");
				strings1.add("同步");
				return strings1;*/
				map.put("columnName", "is_async");
				List<String> isAsyncs=projectAppFilterDao.selectAppDistinctColumnValue(map);
				if(isAsyncs.contains(null)) {
					strings1.add(null);
				}
				if(isAsyncs.contains("1")) {
					strings1.add("异步");
				}
				if(isAsyncs.contains("0")) {
					strings1.add("同步");
				}
				return strings1;
			case "appName":
				map.put("columnName", "app_name");
				return projectAppFilterDao.selectAppDistinctColumnValue(map);
			case "creator":
				return projectAppFilterDao.selectAppDistinctColumnValue(map);
			case "createTime":
				map.put("columnName", "create_time");
				return projectAppFilterDao.selectAppDistinctColumnValue(map);
			case "keywords":
				map.put("columnName", "keywords");
				return projectAppFilterDao.selectAppDistinctColumnValue(map);
			case "appIntro":
				map.put("columnName", "app_intro");
				return projectAppFilterDao.selectAppDistinctColumnValue(map);
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
	public Map<String, Object> selectAppByFilterCondition(
			Integer page , Integer strip , 
			String searchWord ,QueryCondition projectQueryCondition , Integer project_id){
		PageHelper.startPage(page, strip);
		Map<String, Object> map = new HashMap<String , Object>();
		//将相关的筛选条件放入到map中，方便动态构造sql语句的时候使用
		map.put("project_id", String.valueOf(project_id));//项目ID
		map.put("searchWord", searchWord);//搜索关键字，对应前端右上角的搜索框中的值
		
		switch(projectQueryCondition.getColumnName()){
		
			case "isDisplay":
				map.put("columnName", "is_display");//筛选的字段名
				break;
			case "isAsync":
				map.put("columnName", "is_async");//筛选的字段名
				break;
			case "appName":
				map.put("columnName", "app_name");//筛选的字段名
				break;
			case "createTime":
				map.put("columnName", "create_time");//筛选的字段名
				break;
			case "keywords":
				map.put("columnName", "keywords");//筛选的字段名
				break;
			case "creator":
				map.put("columnName", "creator");//筛选的字段名
				break;
			case "appIntro":
				map.put("columnName", "app_intro");//筛选的字段名
				break;
			default:
				Map<String, Object> result = new HashMap<String , Object>();
				result.put("total", 0);
				result.put("list", null);
				return result;
		}
		
		map.put("order", projectQueryCondition.getOrder());//筛选字段排序方式
		map.put("filter", projectQueryCondition.getFilter());//筛选字段过滤条件
		map.put("values", projectQueryCondition.getStrings());//筛选值，list形式
		
		List<Application> applications = projectAppFilterDao.selectProjectAppByFilterCondition(map);
		PageInfo<Application> pageInfo = new PageInfo<Application>(applications);
		Map<String, Object> result = new HashMap<String , Object>();
		result.put("total", pageInfo.getTotal());
		result.put("list", applications);
		return result;
	}

}
