package com.dzjin.dao;

import java.util.List;
import java.util.Map;

/**
 * 
 * 项目名称：wankangyuan 
 * 类名称：ProjectAppTaskSqlBuilder 
 * 类描述： 项目内应用运行结果sql构建
 * 创建人：dzjin 
 * 创建时间：2018年7月14日 下午10:23:54 
 * 修改人：dzjin 
 * 修改时间：2018年7月14日 下午10:23:54 
 * 修改备注： 
 * @version 
 *
 */
public class ProjectAppTaskSqlBuilder {
	
	/**
	 * 根据筛选条件查询项目内应用运行结果
	 * @param map
	 * @return
	 */
	public String buildSelectProjectAppTaskByFilterCondition(final Map<String, Object> map){
		String project_id = (String)map.get("project_id");//项目ID，用于查询当前项目内的成员
		String searchWord = (String)map.get("searchWord");//搜索关键字，搜索的是taskName
		String columnName = (String)map.get("columnName");//字段名
		String filter = (String)map.get("filter");//过滤内容
		String order = (String)map.get("order");//是否排序
		@SuppressWarnings("unchecked")
		List<String> valueList = (List<String>)map.get("values");//筛选值
		
		if(columnName.equals("isRelease")){//如果是发布状态，需要将筛选条件转换成对应的数据库中0-1值
			if(filter.contains("发") || filter.contains("布") || filter.contains("已")){
				//如果筛选条件中包含以上三个字，认为是要筛选发布的运行结果，设置为数据库中isRelease的数值1
				filter = "1";
			}
			if(filter.contains("未")){
				//如果筛选条件中包含未，认为是要筛选未发布的运行结果，设置为数据库中isRelease的数值0
				filter = "0";
			}
		}
		
		if(columnName.equals("is_async")){//如果是异步、即时，需要将筛选条件转换成对应的数据库中0-1值
			if(filter.contains("异") || filter.contains("布")){
				//如果筛选条件中包含以上两个字，认为是要筛选异步的运行结果，设置为数据库中is_async的数值1
				filter = "1";
			}
			if(filter.contains("即") || filter.contains("时")){
				//如果筛选条件中包含以上两个字，认为是要筛选即时的运行结果，设置为数据库中is_async的数值0
				filter = "0";
			}
		}
		
		//构建动态sql
		String sql = "";
		
		sql += "select project_app_task.*,application.app_name , application.is_async from project_app_task , application where ";
		sql += "project_id="+project_id+" and application.id = project_app_task.app_id and taskName like '"+"%"+searchWord+"%' ";//所属项目ID以及搜索条件
		sql += "and "+columnName+" like '%"+filter+"%' ";//某字段过滤条件
		
		
		if(valueList.size() > 0){//值筛选
			sql += "and (";
			for(int i=0;i<valueList.size();i++){
				//如果筛选字段是isRelease，同样需要将可视的字段值转换成数据库中对应的0-1值
				if(columnName.equals("isRelease")){
					if(String.valueOf(valueList.get(i)).equals("已发布")){
						sql += columnName+"=1";
						if(i != valueList.size()-1){
							sql += " or ";	
						}
					}
					if(String.valueOf(valueList.get(i)).equals("未发布")){
						sql += columnName+"=0";
						if(i != valueList.size()-1){
							sql += " or ";	
						}
					}
				}else if(columnName.equals("is_async")){
					//如果筛选字段是is_async，同样需要将可视的字段值转换成数据库中对应的0-1值
					if(String.valueOf(valueList.get(i)).equals("异步")){
						sql += columnName+"=1";
						if(i != valueList.size()-1){
							sql += " or ";	
						}
					}
					if(String.valueOf(valueList.get(i)).equals("即时")){
						sql += columnName+"=0";
						if(i != valueList.size()-1){
							sql += " or ";	
						}
					}
				}else{
					//其他字段不需要进行转换
					sql += columnName+"='"+String.valueOf(valueList.get(i))+"'";
					if(i != valueList.size()-1){
						sql += " or ";	
					}
				}
			}
			sql += ")";
		}
		//字段是否需要排序
		if(order != null && (order.equals("desc") || order.equals("asc"))){
			sql += "order by "+columnName+" "+order;
		}
		return sql;
	}	
	
	/**
	 * 根据筛选字段名以及过滤条件筛选值
	 * @param map
	 * @return
	 */
	public String buildAppTaskColumnFilterSql(final Map<String, Object> map){
		
		String project_id = (String)map.get("project_id");
		String columnName = (String)map.get("columnName");
		String filter = (String)map.get("filter");
		
		String sql = "select distinct "+columnName+" from project_app_task , application "
				+ "where project_id="+project_id+" and app_id=application.id and "+columnName+" like '%"+filter+"%'";
		
		return sql;
	}

}
