package com.dzjin.dao;

import java.util.List;
import java.util.Map;

/**
 * 
 * 项目名称：wankangyuan
 * 类名称：ProjectSqlBuilder 
 * 类描述： 项目筛选SQL构建
 * 创建人：dzjin 
 * 创建时间：2018年7月13日 下午1:27:39 
 * 修改人：dzjin 
 * 修改时间：2018年7月13日 下午1:27:39 
 * 修改备注： 
 * @version 
 *
 */
public class ProjectSqlBuilder {
	
	/**
	 * 根据筛选条件查询我创建的项目
	 * @param map 筛选条件
	 * @return
	 */
	public String buildSelectCreatedProjectByFilterCondition(final Map<String, Object> map){
		String creator = (String)map.get("creator");//创建者ID
		String searchWord = (String)map.get("searchWord");//搜索关键字，搜索的是p_name
		String columnName = (String)map.get("columnName");//字段名
		String filter = (String)map.get("filter");//过滤内容
		String order = (String)map.get("order");//是否排序
		@SuppressWarnings("unchecked")
		List<String> valueList = (List<String>)map.get("values");
		
		if(columnName.equals("creator")){//因为搜索值以及过滤值都是真实的姓名，而不是ID，所以此处转换一下，字段改为user表中的username
			columnName = new String("username");
		}
		if(columnName.equals("is_open")){//如果是公开状态，需要将筛选条件转换成对应的数据库中0-1值
			if(filter.contains("公") || filter.contains("开") || filter.contains("已")){
				//如果筛选条件中包含以上三个字，认为是要筛选公开的项目，设置为数据库中is_open的数值1
				filter = "1";
			}
			if(filter.contains("未")){
				//如果筛选条件中包含未，认为是要筛选未公开的项目，设置为数据库中is_open的数值0
				filter = "0";
			}
		}
		
		//构建动态sql
		String sql = "";
		
		sql += "select project.* , user.username as creatorName from project,user where ";
		sql += "project.creator=user.id and creator="+creator+" and p_name like '"+"%"+searchWord+"%' ";//创建者ID以及搜索条件
		sql += "and "+columnName+" like '%"+filter+"%' ";//某字段过滤条件
		
		
		if(valueList.size() > 0){//值筛选
			sql += "and (";
			for(int i=0;i<valueList.size();i++){
				//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
				if(columnName.equals("is_open")){
					if(String.valueOf(valueList.get(i)).equals("已公开")){
						sql += columnName+"=1";
						if(i != valueList.size()-1){
							sql += " or ";	
						}
					}
					if(String.valueOf(valueList.get(i)).equals("未公开")){
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
	 * 根据筛选条件查询我的项目
	 * @param map 筛选条件
	 * @return
	 */
	public String buildSelectMineProjectByFilterCondition(final Map<String, Object> map){
		String user_id = (String)map.get("user_id");//创建者ID
		String searchWord = (String)map.get("searchWord");//搜索关键字，搜索的是p_name
		String columnName = (String)map.get("columnName");//字段名
		String filter = (String)map.get("filter");//过滤内容
		String order = (String)map.get("order");//是否排序
		@SuppressWarnings("unchecked")
		List<String> valueList = (List<String>)map.get("values");
		
		if(columnName.equals("creator")){//因为搜索值以及过滤值都是真实的姓名，而不是ID，所以此处转换一下，字段改为user表中的username
			columnName = new String("username");
		}
		if(columnName.equals("is_open")){//如果是公开状态，需要将筛选条件转换成对应的数据库中0-1值
			if(filter.contains("公") || filter.contains("开") || filter.contains("已")){
				//如果筛选条件中包含以上三个字，认为是要筛选公开的项目，设置为数据库中is_open的数值1
				filter = "1";
			}
			if(filter.contains("未")){
				//如果筛选条件中包含未，认为是要筛选未公开的项目，设置为数据库中is_open的数值0
				filter = "0";
			}
		}
		
		//构建动态sql
		String sql = "";
		
		sql += "select project.*,user.username as creatorName from project , project_user,user where ";
		sql += "project.id=project_user.project_id and project.creator=user.id and project_user.user_id="+user_id+" and p_name like '"+"%"+searchWord+"%' ";//创建者ID以及搜索条件
		sql += "and "+columnName+" like '%"+filter+"%' ";//某字段过滤条件
		
		
		if(valueList.size() > 0){//值筛选
			sql += "and (";
			for(int i=0;i<valueList.size();i++){
				//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
				if(columnName.equals("is_open")){
					if(String.valueOf(valueList.get(i)).equals("已公开")){
						sql += columnName+"=1";
						if(i != valueList.size()-1){
							sql += " or ";	
						}
					}
					if(String.valueOf(valueList.get(i)).equals("未公开")){
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
	 * 根据筛选条件查询公开的项目
	 * @param map 筛选条件
	 * @return
	 */
	public String buildSelectPublicProjectByFilterCondition(final Map<String, Object> map){
		String searchWord = (String)map.get("searchWord");//搜索关键字，搜索的是p_name
		String columnName = (String)map.get("columnName");//字段名
		String filter = (String)map.get("filter");//过滤内容
		String order = (String)map.get("order");//是否排序
		@SuppressWarnings("unchecked")
		List<String> valueList = (List<String>)map.get("values");
		
		if(columnName.equals("creator")){//因为搜索值以及过滤值都是真实的姓名，而不是ID，所以此处转换一下，字段改为user表中的username
			columnName = new String("username");
		}
		if(columnName.equals("is_open")){//如果是公开状态，需要将筛选条件转换成对应的数据库中0-1值
			if(filter.contains("公") || filter.contains("开") || filter.contains("已")){
				//如果筛选条件中包含以上三个字，认为是要筛选公开的项目，设置为数据库中is_open的数值1
				filter = "1";
			}
			if(filter.contains("未")){
				//如果筛选条件中包含未，认为是要筛选未公开的项目，设置为数据库中is_open的数值0
				filter = "0";
			}
		}
		
		//构建动态sql
		String sql = "";
		
		sql += "select project.* , user.username as creatorName from project,user ";
		sql += "where project.creator=user.id and is_open=1 and p_name like '"+"%"+searchWord+"%' ";//创建者ID以及搜索条件
		sql += "and "+columnName+" like '%"+filter+"%' ";//某字段过滤条件
		
		
		if(valueList.size() > 0){//值筛选
			sql += "and (";
			for(int i=0;i<valueList.size();i++){
				//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
				if(columnName.equals("is_open")){
					if(String.valueOf(valueList.get(i)).equals("已公开")){
						sql += columnName+"=1";
						if(i != valueList.size()-1){
							sql += " or ";	
						}
					}
					if(String.valueOf(valueList.get(i)).equals("未公开")){
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


}
