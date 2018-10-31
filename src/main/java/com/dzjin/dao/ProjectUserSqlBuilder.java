package com.dzjin.dao;

import java.util.List;
import java.util.Map;


/**
 * 
 * 项目名称：wankangyuan 
 * 类名称：ProjectUserSqlBuilder 
 * 类描述： 项目内成员筛选sql构建
 * 创建人：dzjin 
 * 创建时间：2018年7月15日 下午3:11:01 
 * 修改人：dzjin 
 * 修改时间：2018年7月15日 下午3:11:01 
 * 修改备注： 
 * @version 
 *
 */
public class ProjectUserSqlBuilder {
	
	/**
	 * 根据筛选条件查询项目成员
	 * @param map
	 * @return
	 */
	public String buildSelectProjectMembertByFilterCondition(final Map<String, Object> map){
		
		String project_id = (String)map.get("project_id");//项目ID
		String searchWord = (String)map.get("searchWord");//搜索关键字，搜索的是u1.username
		String columnName = (String)map.get("columnName");//字段名
		String filter = (String)map.get("filter");//过滤内容
		String order = (String)map.get("order");//是否排序
		@SuppressWarnings("unchecked")
		List<String> valueList = (List<String>)map.get("values");
		
		if(columnName.equals("username")){//成员姓名
			columnName = new String("u1.username");
		}
		if(columnName.equals("linkman")){//联系人姓名
			columnName = new String("u2.username");
		}
		if(columnName.equals("rolename")){//角色名
			columnName = new String("project_custom_role.rolename");
		}
		
		//构建动态sql
		String sql = "";
		
		sql += "select project_user.* ,project_custom_role.rolename as role_name , u1.username , u2.username as linkman_username "
				+ "from project_user,user u1 , user u2 , project_custom_role "
				+ "where project_user.user_id = u1.id and project_user.linkman_id = u2.id "
				+ "and project_user.role_id = project_custom_role.id and project_id="+project_id+" and u1.username like '"+"%"+searchWord+"%' ";
				//项目ID以及搜索条件
		sql += "and "+columnName+" like '%"+filter+"%' ";//某字段过滤条件
		
		
		if(valueList.size() > 0){//值筛选
			sql += "and (";
			for(int i=0;i<valueList.size();i++){
				//其他字段不需要进行转换
				sql += columnName+"='"+String.valueOf(valueList.get(i))+"'";
				if(i != valueList.size()-1){
					sql += " or ";	
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
	public String buildProjectMemberColumnFilterSql(final Map<String, Object> map){
		
		String project_id = (String)map.get("project_id");
		String columnName = (String)map.get("columnName");
		String filter = (String)map.get("filter");
		
		if(columnName.equals("username")){//成员姓名
			columnName = new String("u1.username");
		}
		if(columnName.equals("linkman")){//联系人姓名
			columnName = new String("u2.username");
		}
		if(columnName.equals("rolename")){//角色名
			columnName = new String("project_custom_role.rolename");
		}
		
		String sql = "select distinct "+columnName+" "
				+ "from project_user,user u1 , user u2 , project_custom_role "
				+ "where project_user.user_id = u1.id and project_user.linkman_id = u2.id "
				+ "and project_user.role_id = project_custom_role.id and project_id="+project_id+" and "+columnName+" like '%"+filter+"%' ";//某字段过滤条件

		return sql;
	}

}
